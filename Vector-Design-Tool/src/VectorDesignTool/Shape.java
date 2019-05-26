package VectorDesignTool;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;

public class Shape extends Shapes {
    private GraphicsContext g;
    private double[][] coords;
    private int edges = 4;

    public Shape(String selectedShape, GraphicsContext g, double[][] coords){
        super(selectedShape,g, coords);
        this.g = g;
        this.coords = coords;
    }

    /**
     * Draws a polygon
     * */
    public void drawPolygon(){
        getUserInput();

    }


    public void getUserInput(){
        boolean correctInput = false;
        do {
            TextInputDialog dialog = new TextInputDialog("4");
            dialog.setTitle("Polygon Edges");
            dialog.setHeaderText(null);
            dialog.setContentText("Please enter the number of edges:");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();

            // Check if user input is valid
            if (result.isPresent()) {
                try {
                    if (result.get().matches("[0-9]*") == false || Integer.parseInt(result.get()) <= 0) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Input");
                        alert.setHeaderText(null);
                        alert.setContentText("Please enter a positive integer.");
                        alert.showAndWait();
                    } else {
                        edges = Integer.parseInt(result.get());
                        correctInput = true;
                    }
                } catch (Exception e) {
                    // Display if any errors occur
                    System.out.println("Failed to open file: " + e);
                }
            } else {
                correctInput = true;
            }
        } while(!correctInput);
    }
}
