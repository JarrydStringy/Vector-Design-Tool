package VectorDesignTool;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Optional;

public class Controller {
    // References to UI objects
    @FXML
    Pane canvasPane;
    @FXML
    private Canvas canvas;
    private Canvas canvas2;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private TextField brushSize;
    @FXML
    private CheckBox eraser;
    // Stores Mouse coordinates
    private double[][] coords = {{0,0},{0,0}};
    // Current shape selection
    private String shapeSelected = "PLOT";
    // Sets graphics context for drawing
    GraphicsContext g;
    GraphicsContext g2;
    StringBuilder savefile = SaveFile.saveFile;
    DecimalFormat df = SaveFile.df;
    String result;

    /**
     * Initialize the application and attach listener to canvas for all methods to draw
     */
    public void initialize(){
        // Sets graphics context for drawing
        g = canvas.getGraphicsContext2D();
        // Sets graphics context for drawing on layer 2
        canvas2 = new Canvas(canvas.getWidth(), canvas.getHeight());
        g2 = canvas2.getGraphicsContext2D();
        canvasPane.getChildren().add(canvas2);
        canvas2.toBack();
        // Set initial value of colour picker
        colorPicker.setValue(Color.BLACK);
        // Check brush input
        checkBrushInput();
        // draw
        draw();
    }

    public String RGBtoHex()
    {
        String hex1 = Integer.toHexString(colorPicker.getValue().hashCode());
        String hex2;

        switch (hex1.length()) {
            case 2:
                hex2 = "000000";
                break;
            case 3:
                hex2 = String.format("00000%s", hex1.substring(0,1));
                break;
            case 4:
                hex2 = String.format("0000%s", hex1.substring(0,2));
                break;
            case 5:
                hex2 = String.format("000%s", hex1.substring(0,3));
                break;
            case 6:
                hex2 = String.format("00%s", hex1.substring(0,4));
                break;
            case 7:
                hex2 = String.format("0%s", hex1.substring(0,5));
                break;
            default:
                hex2 = hex1.substring(0, 6);
        }
        return hex2.toUpperCase();
    }
    /**
     * Listener for when mouse is clicked or dragged
     */
    public void draw(){
        // Set Pen Colour, Will add a reference for Fill once fill is done
        colorPicker.setOnAction(click -> {
            String hex = "#" + RGBtoHex();
            savefile.append("\nPEN " + hex);
        });

        // Listener for when mouse is pressed
        canvas.setOnMousePressed(e ->{
            coords[0][0] = coords[1][0] = e.getX();
            coords[0][1] = coords[1][1] = e.getY();
            result = df.format(coords[0][0]/600) + " " + df.format(coords[0][1]/600);
            g.setStroke(colorPicker.getValue());
            g2.setStroke(colorPicker.getValue());
        });

        // Listener for when mouse is released
        canvas.setOnMouseReleased(e ->{
            coords[1][0] = e.getX();
            coords[1][1] = e.getY();
            g2.clearRect(0,0,600,600);
            Shape shape = new Shape(shapeSelected, g, coords);
            if(shapeSelected != "PLOT")
            {
                result = "\n" + shapeSelected + " " + result;
                savefile.append(result);
                SaveFile.saveFile.append(" " + df.format(coords[1][0]/600) + " " + df.format(coords[1][1]/600));
            }
            else
            {
                result = "\n" + shapeSelected + " " + result;
                savefile.append(result);
            }
            shape.drawShape();
        });

        // Listener for when mouse is dragged
        canvas.setOnMouseDragged(e -> {
            coords[1][0] = e.getX();
            coords[1][1] = e.getY();
            g2.clearRect(0,0,600,600);
            Shape shape = new Shape(shapeSelected, g2, coords);
            shape.drawShape();
        });
    }

    /**
     * Clears the canvas if user selects yes in confirmation dialogue
     */
    public void clearCanvas(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Clear Canvas");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to clear the canvas?");

        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonCancel = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(buttonYes, buttonCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonYes){
            g.clearRect(0,0,600,600);
        }
    }


    public void onSave()
    {
        try {
            SaveFile savefile = new SaveFile(g);
            // Open file and read lines
        } catch(Exception e ){
            // pass
        }
    }


    /**
     * Saves a snapshot of the canvas as a '.png' file
     */
    public void onExport()
    {
        try{
            // Record what is in canvas
            Image snapshot = canvas.snapshot(null, null);
            // Save to .png file
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "VEC", new File("VectorDesign.VEC"));
        } catch (Exception e){
            // Display if any errors occur
            System.out.println("Failed to save image: " + e);
        }
    }
    /**
     * Exits program and shuts down the JavaFX application
     */
    public void onExit(){
        //Shutdown JavaFX application
        Platform.exit();
    }

    /**
     * Open a text file which contains coordinates of drawing
     * Plot the coordinates and redraw image in application ready to edit
     */
    public void openFile(){
        try {
            // Open file and read lines
            ReadFile r = new ReadFile(g);
            r.scanFile();
            r.displayFile();
        } catch(Exception e ){
            // pass
        }
    }

    public void checkBrushInput(){
        try {
            if(brushSize.getText().matches("[0-9]*") == false || Integer.parseInt(brushSize.getText()) <= 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a positive integer.");
                alert.showAndWait();
                brushSize.setText("10");
                g.setLineWidth(10);
                g2.setLineWidth(10);
            } else {
                g.setLineWidth(Integer.parseInt(brushSize.getText()));
                g2.setLineWidth(Integer.parseInt(brushSize.getText()));
            }
        }catch (Exception e){
            // Display if any errors occur
            System.out.println("Invalid brushSize input: " + e);
        }
    }

    /**
     * Draw a plot
     */
    public void createPlot(){ shapeSelected = "PLOT"; }

    /**
     * Draw a line
     */
    public void createLine(){ shapeSelected = "LINE"; }

    /**
     * Draw a rectangle
     */
    public void createRectangle(){ shapeSelected = "RECTANGLE"; }

    /**
     * Draw a ellipse
     */
    public void createEllipse(){ shapeSelected = "ELLIPSE"; }

    /**
     * Draw a polygon
     */
    public void createPolygon(){ shapeSelected = "POLYGON"; }
}
