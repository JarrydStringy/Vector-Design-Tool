package VectorDesignTool;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;

public class Controller {
    // References to UI objects
    @FXML
    private Canvas canvas;
    @FXML
    private TextField brushSize;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private CheckBox eraser;

    /**
     * Initialize the application and attach listener to canvas for all methods to draw
     */
    public void initialize(){
        // Sets graphics context for drawing
        GraphicsContext g = canvas.getGraphicsContext2D();

        // Set initial value of colour picker
        colorPicker.setValue(Color.BLACK);

        // Listener for when mouse is clicked and moved
        canvas.setOnMouseDragged(e -> {
            // Size of drag assuming input valid
            double size = Double.parseDouble(brushSize.getText());
            // Get x and y for mouse event
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;

            // If eraser is selected
            if (eraser.isSelected()){
                // Clear rectangle
                g.clearRect(x, y, size, size);
            } else {
                // Draw using selected colour
                g.setFill(colorPicker.getValue());
                g.fillRect(x, y, size, size);
            }
        });
    }

    /**
     * Clears the canvas
     */
    public void clearCanvas(){
        canvas.getGraphicsContext2D().clearRect(0,0,600,600);
    }

    /**
     * Saves a snapshot of the canvas as a '.png' file
     */
    public void onSave(){
        //Take snapshot of canvas
        try{
            //Record what is in canvas
            Image snapshot = canvas.snapshot(null, null);
            //Save to .png file
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("VectorDesign.png"));
        } catch (Exception e){
            //Display if any errors occur
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
        /*final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(this);
        if(returnVal==JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String filename = file.getAbsolutePath();
        } else if(returnVal==JFileChooser.CANCEL_OPTION) {
        }*/
    }

    /**
     * Draw a rectangle
     */
    public void createRectangle(){
        /*int[] coords = {0,0};
        Rectangle rectangle = new Rectangle(5, 4, "brown", coords);
        System.out.println(colorPicker.getValue());*/
        brushSize.setText(" ");
    }
}
