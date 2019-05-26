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
    // Stores Mouse coordinates
    private double[][] coords = {{0,0},{0,0}};
    // Current shape selection
    private String shapeSelected = "PLOT";
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
        // Check brush input
        checkBrushInput();
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
        });

        // Listener for when mouse is released
        canvas.setOnMouseReleased(e ->{
            coords[1][0] = e.getX();
            coords[1][1] = e.getY();
            DrawShape shape = new DrawShape(shapeSelected, g, coords, Double.parseDouble(brushSize.getText()));
            shape.drawShape();
        });

        // Listener for when mouse is dragged
        canvas.setOnMouseDragged(e -> {
            coords[1][0] = e.getX();
            coords[1][1] = e.getY();
           // DrawShape shape = new DrawShape(shapeSelected, g, coords, Double.parseDouble(brushSize.getText()));
            //shape.drawShape();
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
        try {
            // Open file and read lines
            ReadFile r = new ReadFile(g, Double.parseDouble(brushSize.getText()));
            r.scanFile();
            r.displayFile();
        } catch(Exception e ){
            // pass
        }
    }

    public void checkBrushInput(){
        try {
            if(brushSize.getText().matches("[0-9]*") == false || Double.parseDouble(brushSize.getText()) <= 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a positive integer.");
                alert.showAndWait();
                brushSize.setText("10");
                g.setLineWidth(10);
            } else {
                g.setLineWidth(Double.parseDouble(brushSize.getText()));
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
