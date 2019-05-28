package VectorDesignTool;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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
        double a = canvas.getWidth();
        double b = canvas.getHeight();

        // Set Pen and Fill Colour
        fill.setOnAction(click -> {
            pen.setSelected(!fill.isSelected());
        });

        pen.setOnAction(click -> {
            fill.setSelected(!pen.isSelected());
            g.setStroke(colorPicker.getValue());
            g2.setStroke(colorPicker.getValue());

        });
        colorPicker.setOnAction(click -> {
            if (fill.isSelected()) {
                g.setFill(colorPicker.getValue());
            }

            if (pen.isSelected()) {
                g.setStroke(colorPicker.getValue());
                g2.setStroke(colorPicker.getValue());
            }
        });

        // Listener for when mouse is pressed
        canvas.setOnMousePressed(e -> {
            if (pen.isSelected() || fill.isSelected()) {
                coords[0][0] = coords[1][0] = e.getX();
                coords[0][1] = coords[1][1] = e.getY();
                if (shapeSelected != "POLYGON" || shapeSelected == "")
                    result = df.format(coords[0][0] / canvas.getWidth()) + " " + df.format(coords[0][1] / canvas.getHeight());
            } else {
                alert.selectDraw();
            }
        });

        // Listener for when mouse is released
        canvas.setOnMouseReleased(e -> {
            coords[1][0] = e.getX();
            coords[1][1] = e.getY();
            g2.clearRect(0, 0, 600, 600);
            Shapes shape = new Shapes(shapeSelected, g, coords);


            if(shapeSelected == "PLOT")
            {
                result = "\n" + shapeSelected + " " + result;
                savefile.append(result);
            }

            if (shapeSelected != "PLOT" && shapeSelected != "POLYGON" && shapeSelected != "") {
                result = "\n" + shapeSelected + " " + result;
                savefile.append(result);
                savefile.append(" " + df.format(coords[1][0] / canvas.getWidth()) + " " + df.format(coords[1][1] / canvas.getHeight()));
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

                    shapeSelected = "";
                    if (fill.isSelected()) {
                        g.fillPolygon(x, y, edges);
                        String hexF = "\nFILL " + "#" + RGBtoHex();
                        String fillColour = RGBtoHex();
                        String hexP = "\nPEN " + "#" + RGBtoHex();
                        if(hexP != "000000" && pen.isSelected())
                        {
                            savefile.append(hexP);
                        }
                        if(fillColour != "000000" && fill.isSelected())
                        {
                            hexF = "\nFILL " + "#" + RGBtoHex();
                            savefile.append(hexF);
                            savefile.append("\nPOLYGON");
                            for (int i = 0; i < x.length; i++) {
                                savefile.append(" " + df.format(x[i] / canvas.getWidth()) +
                                        " " + df.format(y[i] / canvas.getHeight()));
                            }
                        }
                        else
                        {
                            savefile.append("\nPOLYGON");
                            for (int i = 0; i < x.length; i++) {
                                savefile.append(" " + df.format(x[i] / canvas.getWidth()) +
                                        " " + df.format(y[i] / canvas.getHeight()));
                            }
                        }
                        System.out.println(savefile);
                    }
                    edgeCount = 0;
                }

            }

            if(shapeSelected != "POLYGON")
                shape.drawShape();

            //Fill Shapes
            if (fill.isSelected()) {
                if (shapeSelected == "RECTANGLE") {
                    g.fillRect(coords[0][0], coords[0][1], abs(coords[1][0] - coords[0][0]), abs(coords[1][1] - coords[0][1]));
                } else if (shapeSelected == "ELLIPSE") {
                    g.fillOval(coords[0][0], coords[0][1], abs(coords[1][0] - coords[0][0]), abs(coords[1][1] - coords[0][1]));
                }
            }
        });


        // Listener for when mouse is dragged
        canvas.setOnMouseDragged(e -> {
            coords[1][0] = e.getX();
            coords[1][1] = e.getY();
            g2.clearRect(0, 0, 600, 600);
            if (shapeSelected != "POLYGON") {
                Shapes shape = new Shapes(shapeSelected, g2, coords);
                shape.drawShape();
            }
        });

    }

    /**
     * Clears the canvas if user selects yes in confirmation dialogue
     */
    public void clearCanvas() {
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
            // Open file and read lines
        } catch (Exception e) {
            // pass
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
            System.out.println("Failed to save image: " + e);
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
