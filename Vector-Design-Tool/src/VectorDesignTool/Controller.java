package VectorDesignTool;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.abs;

public class Controller {
    // References to UI objects
    @FXML
    Pane canvasPane;
    // Sets graphics context for drawing
    GraphicsContext g;
    GraphicsContext g2;
    StringBuilder savefile = SaveFile.saveFile;
    List<Double> xCoords = DrawPolygon.xCoords;
    List<Double> yCoords = DrawPolygon.yCoords;
    DecimalFormat df = SaveFile.df;
    String result = "";
    @FXML
    private Canvas canvas;
    private Canvas canvas2;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private TextField brushSize;
    @FXML
    private CheckBox pen;
    @FXML
    private CheckBox fill;
    // Stores Mouse coordinates
    private double[][] coords = {{0, 0}, {0, 0}};
    // Store polygon edges
    private int edges;
    private int edgeCount = 0;
    private DrawPolygon polygon;
    // Current shape selection
    private String shapeSelected = "PLOT";
    private Alerts alert;

    /**
     * Initialize the application and attach listener to canvas for all methods to draw
     */
    public void initialize() {

        // Readjust canvas values
        canvasPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> {
            canvas.setWidth(newValue.doubleValue());
        });
        canvasPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> {
            canvas.setHeight(newValue.doubleValue());
        });
        canvasPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> {
            canvas2.setWidth(newValue.doubleValue());
        });
        canvasPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> {
            canvas2.setHeight(newValue.doubleValue());
        });



        // Sets graphics context for drawing
        g = canvas.getGraphicsContext2D();

        // Sets graphics context for drawing on layer 2
        canvas2 = new Canvas(canvas.getWidth(), canvas.getHeight());
        g2 = canvas2.getGraphicsContext2D();
        canvasPane.getChildren().add(canvas2);
        canvas2.toBack();

        // Set initial value of colour picker
        colorPicker.setValue(Color.BLACK);

        // Instantiate Alerts
        alert = new Alerts();

        // Check brush input
        checkBrushInput();

        // draw
        draw();
    }

    /**
     * Gets colour picker RGB value and converts it to HEX format.
     * @return the HEX value in a String format.
     */
    public String RGBtoHex() {
        String hex1 = Integer.toHexString(colorPicker.getValue().hashCode());
        String hex2;
        switch (hex1.length()) {
            case 2:
                hex2 = "000000";
                break;
            case 3:
                hex2 = String.format("00000%s", hex1.substring(0, 1));
                break;
            case 4:
                hex2 = String.format("0000%s", hex1.substring(0, 2));
                break;
            case 5:
                hex2 = String.format("000%s", hex1.substring(0, 3));
                break;
            case 6:
                hex2 = String.format("00%s", hex1.substring(0, 4));
                break;
            case 7:
                hex2 = String.format("0%s", hex1.substring(0, 5));
                break;
            default:
                hex2 = hex1.substring(0, 6);
        }
        return hex2.toUpperCase();
    }

    /**
     * Method to test -
     * Outputs the position of the mouse when clicked
     * Also outputs the width and height of the canvas
     */
    public void MousePosition(MouseEvent mouseEvent)
    {
        System.out.println("X: " + canvasPane.getHeight() + " Y:" + canvasPane.getWidth() );
        System.out.println("Current mouse position: " + mouseEvent.getX() + ":" + mouseEvent.getY());
    }

    /**
     * Listener for when mouse is clicked or dragged
     */
    public void draw() {

        // Set double variables for canvas width and height
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();

        // ------------------------------------ Listener for fill checkbox
        fill.setOnAction(click -> {
            pen.setSelected(false);
            fill.setSelected(true);
        });
        // ------------------------------------ Listener for pen checkbox
        pen.setOnAction(click -> {
            pen.setSelected(true);
            fill.setSelected(false);
            g.setStroke(colorPicker.getValue());
            g2.setStroke(colorPicker.getValue());
        });
        // ------------------------------------ Listener for colour picker change
        colorPicker.setOnAction(click -> {
            if (fill.isSelected()) {
                g.setFill(colorPicker.getValue());
                savefile.append("\nPEN " + "#" + RGBtoHex());
            }

            if (pen.isSelected()) {
                g.setStroke(colorPicker.getValue());
                g2.setStroke(colorPicker.getValue());
                savefile.append("\nPEN " + "#" + RGBtoHex());
            }
        });
        // ------------------------------------ Listener for when mouse is pressed
        canvas.setOnMousePressed(e -> {
            if (pen.isSelected() || fill.isSelected()) {
                coords[0][0] = coords[1][0] = e.getX();
                coords[0][1] = coords[1][1] = e.getY();
                if (shapeSelected != "POLYGON" || shapeSelected == "")
                    result = df.format(coords[0][0] / canvasWidth) + " " + df.format(coords[0][1] / canvasHeight);
            } else {
                alert.selectDraw();
            }
        });
        // ------------------------------------ Listener for when mouse is released
        canvas.setOnMouseReleased(e -> {
            coords[1][0] = e.getX();
            coords[1][1] = e.getY();
            g2.clearRect(0, 0, 600, 600);
            Shapes shape = new Shapes(shapeSelected, g, coords);
            if(shapeSelected == "PLOT") {
                result = "\nPLOT " + result;
                savefile.append(result);
                shape.drawShape();
            }
            if (shapeSelected != "PLOT" && shapeSelected != "POLYGON" && shapeSelected != "") {
                // Check if Fill is selected
                if (fill.isSelected()) {
                    if (shapeSelected == "RECTANGLE") {
                        shape.setIsFill(true);
                        shape.drawRectangle();
                        shape.setIsFill(false);
                        savefile.append("\nFILL " + "#" + RGBtoHex());
                    } else if (shapeSelected == "ELLIPSE") {
                        shape.setIsFill(true);
                        shape.drawEllipse();
                        shape.setIsFill(false);
                        savefile.append("\nFILL " + "#" + RGBtoHex());
                    }
                }
                result = "\n" + shapeSelected + " " + result;
                savefile.append(result);
                savefile.append(" " + df.format(coords[1][0] / canvasWidth) + " " + df.format(coords[1][1] / canvasHeight));
                shape.drawShape();
            }
            if (shapeSelected == "POLYGON") {
                polygon.drawPlot(coords[1]);
                edgeCount++;
                if (edgeCount >= edges && edges != 0) {
                    double[] x = new double[DrawPolygon.xCoords.size()];
                    double[] y = new double[DrawPolygon.yCoords.size()];
                    for (int i = 0; i < DrawPolygon.xCoords.size(); i++) {
                        x[i] = DrawPolygon.xCoords.get(i);
                        y[i] = DrawPolygon.yCoords.get(i);
                    }
                    if (fill.isSelected()) {
                        g.fillPolygon(x, y, edges);
                        savefile.append("\nFILL " + "#" + RGBtoHex());
                        savefile.append("\nPOLYGON");
                        for (int i = 0; i < x.length; i++) {
                            savefile.append(" " + df.format(x[i] / canvasWidth) + " " + df.format(y[i] / canvasHeight));
                        }
                    }
                    else {
                        savefile.append("\nPEN " + "#" + RGBtoHex());
                        savefile.append("\nPOLYGON");
                        for (int i = 0; i < x.length; i++) {
                            savefile.append(" " + df.format(x[i] / canvasWidth) + " " + df.format(y[i] / canvasHeight));
                        }
                    }
                    edgeCount = 0;
                    polygon.resetPolygon();
                }
            }
        });
        // ------------------------------------ Listener for when mouse is dragged
        canvas.setOnMouseDragged(e -> {
            coords[1][0] = e.getX();
            coords[1][1] = e.getY();
            g2.clearRect(0, 0, 600, 600);
            if (shapeSelected != "POLYGON") {
                Shapes shape = new Shapes(shapeSelected, g2, coords);
                shape.drawShape();
            }
        });
        // ------------------------------------ Listener for Key presses
        canvas.addEventHandler(KeyEvent.KEY_PRESSED, (key) ->{
            // Check if keyboard input is ctrl + z
            if(key.getCode()== KeyCode.Z && key.isControlDown()) {
                onUndo();
            } else if(key.getCode()== KeyCode.Y && key.isControlDown()){
                onRedo();
            }
        });
    }

    /**
     * Removes most recent drawing and stashes it for later redo.
     * User can also press "ctrl" + "z" to perform this action
     */
    public void onUndo(){

    }

    /**
     * Gets most recent undo from stash and draws it.
     * Stores this in undo stash for later undo if needed.
     * User can also press "ctrl" + "y" to perform this action
     */
    public void onRedo(){

    }

    /**
     * Clears the canvas if user selects yes in confirmation dialogue
     */
    public void onClearCanvas() {
        Optional<ButtonType> result = alert.clearCanvasCheck();
        if (result.get().getText() == "Yes") {
            g.clearRect(0, 0, 600, 600);
        }
    }

    /**
     * Saves a snapshot of the canvas as a '.png' file
     */
    public void onSave() {
        try {
            SaveFile savefile = new SaveFile(g);
        } catch (Exception e) {
            System.out.println("Error in Controller, saving file (261): " + e);
        }
    }

    /**
     * Saves a snapshot of the canvas as a '.png' file
     */
    public void onExport() {
        try {
            // Record what is in canvas
            Image snapshot = canvas.snapshot(null, null);
            // Save to .png file
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "VEC", new File("VectorDesign.VEC"));
        } catch (Exception e) {
            // Display if any errors occur
            System.out.println("Error in Controller, export (276): " + e);
        }
    }

    /**
     * Exits program and shuts down the JavaFX application
     */
    public void onExit() {
        //Shutdown JavaFX application
        Platform.exit();
    }

    /**
     * Open a text file which contains coordinates of drawing
     * Plot the coordinates and redraw image in application ready to edit
     */
    public void openFile() {
        try {
            // Open file and read lines
            ReadFile r = new ReadFile(g, canvas);
            r.scanFile();
            g.clearRect(0, 0, 600, 600);
            r.displayFile();
        } catch (Exception e) {
            // pass
        }
    }

    /**
     * Checks that the user input for brush size is a valid positive integer between 1 and 200.
     */
    public void checkBrushInput() {
        try {
            if (brushSize.getText().matches("[0-9]*") == false || Integer.parseInt(brushSize.getText()) < 1 || Integer.parseInt(brushSize.getText()) > 200) {
                alert.brushSizeError();
                brushSize.setText("5");
                g.setLineWidth(5);
                g2.setLineWidth(5);
            } else {
                g.setLineWidth(Integer.parseInt(brushSize.getText()));
                g2.setLineWidth(Integer.parseInt(brushSize.getText()));
            }
        } catch (Exception e) {
            // Display if any errors occur
            System.out.println("Invalid brushSize input: " + e);
        }
        savefile.append("\nPEN-WIDTH " + brushSize.getText());
    }

    /**
     * Draw a plot
     */
    public void createPlot() {
        shapeSelected = "PLOT";
    }

    /**
     * Draw a line
     */
    public void createLine() {
        shapeSelected = "LINE";
    }

    /**
     * Draw a rectangle
     */
    public void createRectangle() {
        shapeSelected = "RECTANGLE";
    }

    /**
     * Draw a ellipse
     */
    public void createEllipse() {
        shapeSelected = "ELLIPSE";
    }

    /**
     * Draw a polygon
     */
    public void createPolygon() {
        shapeSelected = "POLYGON";
        polygon = new DrawPolygon(g);
        edges = polygon.getUserInput();
    }
}
