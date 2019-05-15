package VectorDesignTool;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.File;

public class Controller {
    //References to UI objects
    @FXML
    private Canvas canvas;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private TextField brushSize;
    @FXML
    private CheckBox eraser;

    //To draw, attach listener to canvas for all methods
    public void initialize(){
        //Sets grahics context for drawing
        GraphicsContext g = canvas.getGraphicsContext2D();

        //Listener for when mouse is clicked and moved
        canvas.setOnMouseDragged(e -> {
            //Size of drag assuming input valid
            double size = Double.parseDouble(brushSize.getText());
            //Get x and y for mouse event
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;

            //If eraser is selected
            if (eraser.isSelected()){
                //Clear rectangle
                g.clearRect(x, y, size, size);
            } else {
                //Draw using selected colour
                g.setFill(colorPicker.getValue());
                g.fillRect(x, y, size, size);
            }
        });
    }

    //Actions in UI are methods in controller
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

    public  void onExit(){
        //Shutdown JavaFX application
        Platform.exit();
    }
}
