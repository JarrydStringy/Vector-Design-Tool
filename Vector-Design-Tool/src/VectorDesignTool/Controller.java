package VectorDesignTool;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.event.*;
import java.io.File;
import java.util.Optional;

public class Controller {
    // References to UI objects
    @FXML
    private Canvas canvas;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private TextField brushSize;
    @FXML
    private CheckBox eraser;
    // Selected file lines
    private String[][] fileLineCommands;
    // Stores Mouse coordinates
    private double[][] coords = {{0,0},{0,0}};
    // Current shape selection
    private String shapeSelected = "LINE";
    // Sets graphics context for drawing
    GraphicsContext g;

    /**
     * Initialize the application and attach listener to canvas for all methods to draw
     */
    public void initialize(){
        // Sets graphics context for drawing
        g = canvas.getGraphicsContext2D();
        // Set initial value of colour picker
        colorPicker.setValue(Color.BLACK);
        // draw
        draw(g);
    }

    /**
     * Listener for when mouse is clicked or dragged
     * @param g - contains the GraphicsContext of the canvas for drawing
     */
    public void draw(GraphicsContext g){
        // Listener for when mouse is pressed
        canvas.setOnMousePressed(e ->{
            coords[0][0] = coords[1][0] = e.getX();
            coords[0][1] = coords[1][1] = e.getY();
            g.setStroke(colorPicker.getValue());
            checkBrushInput();
            DrawShape shape = new DrawShape(shapeSelected, g, coords);
            shape.drawShape();
        });

        // Listener for when mouse is released
        canvas.setOnMouseReleased(e ->{
            coords[1][0] = e.getX();
            coords[1][1] = e.getY();
            DrawShape shape = new DrawShape(shapeSelected, g, coords);
            shape.drawShape();
        });

        // Listener for when mouse is dragged
        canvas.setOnMouseDragged(e -> {
            coords[1][0] = e.getX();
            coords[1][1] = e.getY();
            DrawShape shape = new DrawShape(shapeSelected, g, coords);
            shape.drawShape();
        });
    }

    /**
     * Clears the canvas if user selects yes in confirmation dialogue
     */
    public void clearCanvas(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Clear Canvas");
        alert.setHeaderText("Are you sure you want to clear the canvas?");
        alert.setContentText("");

        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonCancel = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(buttonYes, buttonCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonYes){
            canvas.getGraphicsContext2D().clearRect(0,0,600,600);
        }
    }

    /**
     * Saves a snapshot of the canvas as a '.png' file
     */
    public void onSave(){
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
    public  void onExit(){
        //Shutdown JavaFX application
        Platform.exit();
    }

    /**
     * Open a text file which contains coordinates of drawing
     * Plot the coordinates and redraw image in application ready to edit
     */
    public void openFile(){
        // Open file and read lines
        ReadFile r = new ReadFile();
        r.scanFile();
        fileLineCommands = r.getFileLines();
    }

    public void checkBrushInput(){
        try{
            assert Double.parseDouble(brushSize.getText()) > 0;
            assert brushSize.getText().matches("[0-9]*");
            g.setLineWidth(Double.parseDouble(brushSize.getText()));
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please enter a positive integer.");
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
