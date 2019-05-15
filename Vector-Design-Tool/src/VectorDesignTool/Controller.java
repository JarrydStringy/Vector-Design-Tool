package Test;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.File;

public class Controller {

    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private TextField brushSize;

    @FXML
    private CheckBox eraser;

    //Actions in UI are methods in controller
    public void onSave(){
        //Take snapshot of canvas
        try{
            Image snapshot = canvas.snapshot(null, null);
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("VectorDesign.png"));
        } catch (Exception e){
            System.out.println("Failed to save image: " + e);
        }
    }

    public  void onExit(){
        //Shutdown JavaFX application
        Platform.exit();
    }
}
