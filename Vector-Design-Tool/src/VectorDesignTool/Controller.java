package VectorDesignTool;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.Optional;

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
        // draw
        draw(g);
    }

    /**
     * Listener for when mouse is clicked or dragged
     * @param g - contains the GraphicsContext of the canvas for drawing
     */
    public void draw(GraphicsContext g){
        // Listener for when mouse is clicked
        canvas.setOnMousePressed(e ->{
            // if eraser is not selected
            if(eraser.isSelected() == false){
                // Begin drawing
                g.beginPath();
                // draw
                g.lineTo(e.getX(), e.getY());
                g.stroke();
            }
        });
        // check if mouse is clicked then dragged
        canvas.setOnMouseDragged(e -> {
            // Size of drag assuming input valid
            double size = Double.parseDouble(brushSize.getText());

            // If eraser is selected
            if (eraser.isSelected()){
                // Clear rectangle
                g.clearRect(e.getX(), e.getY(), size, size);
            } else {
                // Draw using selected colour and brush size
                g.setStroke(colorPicker.getValue());
                g.setLineWidth(size);
                // draw continuously
                g.lineTo(e.getX(), e.getY());
                g.stroke();
            }
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
        // Take snapshot of canvas
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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        // Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TEXT files (*.VEC)", "*.VEC");
        fileChooser.getExtensionFilters().add(extFilter);

        fileChooser.showOpenDialog(null);
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
