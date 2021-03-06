package VectorDesignTool;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Controller {
    public static boolean isDrawing;
    public static UndoRedo undoRedo;
    // References to UI objects
    @FXML
    Pane canvasPane;
    @FXML
    Pane canvasPane2;
    @FXML
    Canvas canvas;
    Canvas canvas2;
    Canvas canvas3;
    // Sets graphics context for drawing
    GraphicsContext g;
    GraphicsContext g2;
    GraphicsContext g3;
    StringBuilder savefile = SaveFile.saveFile;
    //StringBuilder savebmp = SaveBMP.saveBMPFile;
    List<Double> xCoords = DrawPolygon.xCoords;
    List<Double> yCoords = DrawPolygon.yCoords;
    DecimalFormat df = SaveFile.df;
    String result = "";
    String[] shapes = {"PLOT", "LINE", "RECTANGLE", "ELLIPSE", "POLYGON"};
    @FXML
    private ColorPicker colorPicker;
    @FXML
    public TextField brushSize;
    @FXML
    private ComboBox gridSize;
    @FXML
    private CheckBox pen;
    @FXML
    private CheckBox fill;
    @FXML
    private CheckBox grid;
    @FXML
    private ListView history;
    // Stores Mouse coordinates
    private double[][] coords = {{0, 0}, {0, 0}};
    // Store polygon edges
    private int edges;
    private int edgeCount = 0;
    // Current shape selection
    private String shapeSelected = "PLOT";
    // Dimensions of the canvas saved
    int xor;
    int yor;
    // Instantiations
    private SaveFile save;
    private ResizeCanvas resizeCanvas;
    private DrawPolygon polygon;
    private Alerts alert;
    private List<String[]> currentFileLines;
    boolean undoHistory = false;
    String currentLine = "";
    String currentHistory = "";
    public static boolean gridOn = false;
    public static int gridSizeNow = 1;

    /**
     * Initialize the application and attach listener to canvas for all methods to draw
     */
    public void initialize() {
        // Sets graphics context for drawing
        g = canvas.getGraphicsContext2D();
        // Sets graphics context for drawing on layer 2
        canvas2 = new Canvas(canvas.getWidth(), canvas.getHeight());
        g2 = canvas2.getGraphicsContext2D();
        canvasPane2.getChildren().add(canvas2);
        canvas2.toBack();
        // Sets graphics context for grid on layer 3
        canvas3 = new Canvas(canvas.getWidth(), canvas.getHeight());
        g3 = canvas3.getGraphicsContext2D();
        canvasPane2.getChildren().add(canvas3);
        canvas3.toBack();

        // Get drawing file ready
        File file = new File("currentFile.vec");
        file.delete();

        // Instantiate classes
        save = new SaveFile(g);
        SaveFile save = new SaveFile(g);
        //SaveBMP bmpsave = new SaveBMP(g);
        ReadFile readFile = new ReadFile(g, canvas);
        resizeCanvas = new ResizeCanvas();
        resizeCanvas.resize(canvasPane, g, g3, savefile, save, readFile, canvas, canvas2, canvas3, file);
        alert = new Alerts();
        undoRedo = new UndoRedo(g, canvas);

        // Set initial value of colour picker
        colorPicker.setValue(Color.BLACK);

        // Check brush input
        checkBrushInput();

        // draw
        draw();
    }

    /**
     * Gets colour picker RGB value and converts it to HEX format.
     *
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
     * Listener for when mouse is clicked or dragged
     */
    public void draw() {
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
                savefile.append("\nFILL " + "#" + RGBtoHex());
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
                if (shapeSelected != "POLYGON" || shapeSelected != "") {
                    result = df.format(coords[0][0] / canvas.getWidth()) + " "
                            + df.format(coords[0][1] / canvas.getHeight());
                }
                isDrawing = true;
            } else {
                alert.selectDraw();
            }
        });
        // ------------------------------------ Listener for when mouse is released
        canvas.setOnMouseReleased(e -> {
            coords[1][0] = e.getX();
            coords[1][1] = e.getY();
            g2.clearRect(0, 0, canvas2.getWidth(), canvas2.getHeight());
            Shapes shape = new Shapes(shapeSelected, g, coords);
            if (shapeSelected == "PLOT") {
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
                    } else {
                        if (shapeSelected == "ELLIPSE") {
                            shape.setIsFill(true);
                            shape.drawEllipse();
                            shape.setIsFill(false);
                        }
                    }
                }
                result = "\n" + shapeSelected + " " + result;
                savefile.append(result);
                String coord = " " + df.format(coords[1][0] / canvas.getWidth())
                        + " " + df.format(coords[1][1] / canvas.getHeight());
                savefile.append(coord);
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
                        savefile.append("\nPOLYGON");
                        for (int i = 0; i < x.length; i++) {
                            savefile.append(" " + df.format(x[i] / canvas.getWidth())
                                    + " " + df.format(y[i] / canvas.getHeight()));
                        }
                    } else {
                        savefile.append("\nPEN " + "#" + RGBtoHex());
                        savefile.append("\nPOLYGON");
                        for (int i = 0; i < x.length; i++) {
                            savefile.append(" " + df.format(x[i] / canvas.getWidth())
                                    + " " + df.format(y[i] / canvas.getHeight()));
                        }
                    }
                    polygon.resetPolygon();
                }
            }
            currentLine = "";
            updateHistory();
            save.saveCurrentFile("currentFile.vec", savefile.toString());
        });
        // ------------------------------------ Listener for when mouse is dragged
        canvas.setOnMouseDragged(e -> {
            coords[1][0] = e.getX();
            coords[1][1] = e.getY();
            g2.clearRect(0, 0, canvas2.getWidth(), canvas2.getHeight());
            if (shapeSelected != "POLYGON") {
                Shapes shape = new Shapes(shapeSelected, g2, coords);
                shape.drawShape();
            }
        });
        // ------------------------------------ Listener for Key presses
    }

    public void updateHistory() {
        if (shapeSelected != "POLYGON") {
            history.setMouseTransparent(false);
            history.setFocusTraversable(true);
            String last = savefile.substring(savefile.lastIndexOf("\n"))
                    .replace("\n", "");
            //if (Arrays.stream(shapes).parallel().anyMatch(last::contains)) {
                history.getItems().add(last);
            //}
        } else {
            if (edgeCount >= edges && edges != 0) {
                history.setMouseTransparent(false);
                history.setFocusTraversable(true);
                String last = savefile.substring(savefile.lastIndexOf("\n"))
                        .replace("\n", "");
                //if (Arrays.stream(shapes).parallel().anyMatch(last::contains))
                    history.getItems().add(last);
                edgeCount = 0;
            }
        }
    }

    /**
     * Removes most recent drawing and stashes it for later redo.
     * User can also press "ctrl" + "z" to perform this action
     */

    public void onUndo() {
        try{
            //for()
            String[] a = savefile.toString().split("\n");
            undoRedo.Undo();
            history.getItems().remove(history.getItems().size() - 1);
            currentLine =  "\n" + a[a.length-1] + currentLine;
        }
        catch(Exception e)
        {
            alert.noUndo();
        }
    }

    /**
     * Gets most recent undo from stash and draws it.
     * Stores this in undo stash for later undo if needed.
     * User can also press "ctrl" + "y" to perform this action
     */
    public void onRedo() {
        try {
            if(undoHistory == true)
            {
                String[] a = currentHistory.split("\n");
                for (String b : a) {
                    savefile.append("\n" + b);
                    //if (Arrays.stream(shapes).anyMatch(b::contains)) {
                        history.getItems().add(b);
                    //}
                }
                g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                String[] c = savefile.toString().split("\n");
                // Store each command in an array per line
                String[][] fileLines = new String[c.length][];
                for (int i = 0; i < c.length; i++) {
                    fileLines[i] = c[i].split(" ");
                }

                DisplayFile displayFile = new DisplayFile(g, canvas, fileLines);
                displayFile.displayFile();
                undoHistory = false;
                history.setMouseTransparent( false );
                history.setFocusTraversable(true);
                return;
            }

            String[] a = currentLine.split("\n");
            String choice = a[1];
            history.getItems().add(choice);
            undoRedo.Redo();
            currentLine = currentLine.replace("\n" + choice, "");
        } catch (Exception e) {
            alert.noRedo();
        }
    }

    public void onConfirm() {
        try
        {
            String choice = history.getSelectionModel().getSelectedItem().toString();
            String[] c = savefile.substring(savefile.lastIndexOf("\n" + choice)).split("\n");
            currentHistory = "";
            for(String b : c)
            {
                currentHistory = currentHistory + "\n" + b;
            }
            savefile.delete(savefile.lastIndexOf("\n" + choice), savefile.length());
            history.getItems().clear();
            g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            undoHistory = true;
            String last = savefile.substring(savefile.lastIndexOf("\n"))
                        .replace("\n", "");
            //if (Arrays.stream(shapes).parallel().anyMatch(last::contains)) {
                // Store each line in array
                String[] a = savefile.toString().split("\n");
                // Store each command in an array per line
                String[][] fileLines = new String[a.length][];
                for (int i = 0; i < a.length; i++) {
                    fileLines[i] = a[i].split(" ");
                }

                DisplayFile displayFile = new DisplayFile(g, canvas, fileLines);
                displayFile.displayFile();
            //}
            a = savefile.toString().split("\n");
            for (String b : a) {
               // if (Arrays.stream(shapes).parallel().anyMatch(b::contains)) {
                    history.getItems().add(b);
                //}
            }
            if(history.getItems().size() < 1)
            {
                history.setMouseTransparent( true );
                history.setFocusTraversable(false);
            }
        }
       catch (Exception e)
       {
            alert.noSelect();
       }
    }

    /**
     * Clears the canvas if user selects yes in confirmation dialogue
     */
    public void onClearCanvas() {
        Optional<ButtonType> option = alert.clearCanvasCheck();
        if (option.get().getText() == "Yes") {
            File file = new File("currentFile.vec");
            file.delete();
            currentLine = "";
            currentHistory = "";
            isDrawing = false;
            g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            history.getItems().clear();
            savefile.delete(0, savefile.length());
        }
    }


    /**
     * Saves a snapshot of the canvas as a '.png' file
     */
    public void onSave() {
        if (savefile.toString().chars().filter(line -> line == '\n').count() < 2) {
            alert.nullExportError();
        } else {
            try {
                SaveFile savefile = new SaveFile(g);
                savefile.saveFile();
            } catch (Exception e) {
                System.out.println("Error in Controller, saving file (300): " + e);
            }
        }
    }

    /**
     * Saves a snapshot of the canvas as a '.bmp' file
     */
    public void onBMPSave() {
        if (savefile.toString().chars().filter(line -> line == '\n').count() < 2) {
            alert.nullBMPExportError();
        } else {
            try {
                // Input the height and width dimensions of the image
                defineDimensions();

                // Open a window to save the file
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Resource File");

                // Set extension filter
                FileChooser.ExtensionFilter extFilter =
                        new FileChooser.ExtensionFilter("Bitmap files (*.bmp)", "*.bmp");
                fileChooser.getExtensionFilters().add(extFilter);
                File bfile = fileChooser.showSaveDialog(null);

                // If there is an image, save as a BMP
                if (bfile != null) {
                    // Using simple math to find the ratio of screen size
                    double xratio = xor/canvas.getWidth();
                    double yratio = yor/canvas.getHeight();

                    // Scaling the image based on the values shown
                    WritableImage writableImage = new WritableImage(xor, yor);
                    SnapshotParameters spa = new SnapshotParameters();
                    spa.setTransform(Transform.scale(xratio, yratio));

                    // Save file to directory.
                    canvas.snapshot(spa, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", bfile);
                }

            } catch (Exception e) {
                System.out.println("Error in Controller, saving bmp file (309): " + e);
            }
        }
    }


    /**
     * Saves a snapshot of the canvas as a '.png' file
     */
    public void onExport() {

        if (savefile.toString().chars().filter(line -> line == '\n').count() < 2) {
            alert.nullBMPExportError();
        } else {
            try {
                // Input the height and width dimensions of the image
                defineDimensions();

                // Open a window to save the file
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Resource File");

                // Set extension filter
                FileChooser.ExtensionFilter extFilter =
                        new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
                fileChooser.getExtensionFilters().add(extFilter);
                File bfile = fileChooser.showSaveDialog(null);

                // If there is an image, save as a PNG file
                // Call alert if there is no image
                if (bfile != null) {

                    // Using simple math to find the ratio of screen size
                    double xratio = xor/canvas.getWidth();
                    double yratio = yor/canvas.getHeight();

                    // Scaling the image based on the values shown
                    WritableImage writableImage = new WritableImage(xor, yor);
                    SnapshotParameters spa = new SnapshotParameters();
                    spa.setTransform(Transform.scale(xratio, yratio));

                    // Save file to directory.
                    canvas.snapshot(spa, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", bfile);
                }

            } catch (Exception e) {
                System.out.println("Error in Controller, saving png file (309): " + e);
            }
        }

    }

    /**
     * Pulls up two windows to choose the dimensions when image is saved
     */
    public void defineDimensions() {
        // Reading the window's height and width values
        String x = Integer.toString((int) canvas.getWidth());
        String y = Integer.toString((int) canvas.getHeight());

        // Initialising the dialog boxes
        TextInputDialog dialog = new TextInputDialog(x);
        TextInputDialog dialog2 = new TextInputDialog(y);

        // Message for the first dialogue box
        dialog.setTitle("CChoose image dimension.");
        dialog.setHeaderText("Enter your image's width dimension");
        dialog.setContentText("x:");

        // Message for the second dialogue box
        dialog2.setTitle("Choose image dimension.");
        dialog2.setHeaderText("Enter your image's height value");
        dialog2.setContentText("y:");

        // Returns the integer variables for the dimensions
        // xor and yor are the final image dimensions
        Optional<String> widthval = dialog.showAndWait();
        Optional<String> heightval = dialog2.showAndWait();

        widthval.ifPresent(width -> {
            xor = Integer.parseInt(width);
        });
        heightval.ifPresent(height -> {
            yor =  Integer.parseInt(height);
        });
    }

    /**
     * Exits program and shuts down the JavaFX application
     */
    public void onExit() {
        File file = new File("currentFile.vec");
        file.delete();
        System.out.println("Stage is closing, deleted current file");
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
            r.readfile();
            g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            currentHistory = "";
            currentLine = "";

            history.getItems().clear();
            history.setMouseTransparent(false);
            history.setFocusTraversable(true);
            r.displayFile();
            r.scanFile();
            String[] a = savefile.toString().split("\n");
            for (String b : a) {
                //if (Arrays.stream(shapes).parallel().anyMatch(b::contains)) {
                    history.getItems().add(b);
                //}
            }
            savefile.deleteCharAt(savefile.length());
        } catch (Exception e) {
            // pass
        }
    }

    /**
     * Returns a boolean for the input brush value
     *
     * @param readvalue - the value inputted into
     */
    public boolean inputBrushValue(String readvalue) {
        //Use a boolean to determine if the input is a number or not
        boolean x = readvalue.matches("[0-9]*");
        return x;
    }


    /**
     * Checks that the user input for brush size is a valid positive integer between 1 and 200.
     */
    public void checkBrushInput() {
        try {
            if (brushSize.getText().matches("[0-9]*") == false
                    || Integer.parseInt(brushSize.getText()) < 1
                    || Integer.parseInt(brushSize.getText()) > 200) {
                alert.brushSizeError();
                brushSize.setText("1");
                g.setLineWidth(1);
                g2.setLineWidth(1);
            } else {
                savefile.append("\nPEN-WIDTH " + brushSize.getText());
                g.setLineWidth(Integer.parseInt(brushSize.getText()));
                g2.setLineWidth(Integer.parseInt(brushSize.getText()));
            }
        } catch (Exception e) {
            // Display if any errors occur
            System.out.println("Error in check brushSize (374): " + e);
        }

    }

    /**
     * Draw a plot
     */
    public void createPlot() {
        shapeSelected = shapes[0];
    }

    /**
     * Draw a line
     */
    public void createLine() {
        shapeSelected = shapes[1];
    }

    /**
     * Draw a rectangle
     */
    public void createRectangle() {
        shapeSelected = shapes[2];
    }

    /**
     * Draw a ellipse
     */
    public void createEllipse() {
        shapeSelected = shapes[3];
    }

    /**
     * Draw a polygon
     */
    public void createPolygon() {
        shapeSelected = shapes[4];
        polygon = new DrawPolygon(g);
        edges = polygon.getUserInput();
    }

    /**
     * Checks that the user input for grid size is a valid positive integer between 1 and 1000.
     */
    public void checkGridInput() {
        try {
            this.gridSizeNow = Integer.parseInt(gridSize.getValue().toString())*10;
            if(gridOn){
                setGrid();
            }
        } catch (Exception e) {
            // Display if any errors occur
            System.out.println("Invalid grid size input (431): " + e);
        }
    }

    /**
     * Toggles grid checkbox
     */
    public void onGrid() {
        //Toggle Grid
        gridOn = !gridOn;
        grid.setSelected(gridOn);
        Grid g = new Grid(g3,canvas3);
        if(gridOn == false)
        {
            g.clearGrid();
            gridSize.setValue("Select");
        }
    }

    /**
     * Displays the grid on the canvas
     */
    public void setGrid(){
        //Display grid size from user input
        g3.setLineWidth(1);

        Grid g = new Grid(g3,canvas3);

        if (gridOn == true) {
            g.drawGrid();
        }
        if(gridOn == false)
        {
            g.clearGrid();
        }
    }
}