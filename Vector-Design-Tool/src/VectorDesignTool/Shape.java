package VectorDesignTool;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Shape extends Shapes {
    private GraphicsContext g;
    private double[][] coords;
    private int edges = 4;
    private int edgeCount = 0;
    private List<Double> xCoords;
    private List<Double> yCoords;

    public Shape(String selectedShape, GraphicsContext g, double[][] coords){
        super(selectedShape,g, coords);
        this.g = g;
        this.coords = coords;
        xCoords = new ArrayList<>();
        yCoords = new ArrayList<>();
    }

    /**
     * Draws a polygon
     * */
    public void drawPolygon(){
        double[] x = new double[xCoords.size()];
        double[] y = new double[yCoords.size()];

        for(int i = 0; i < xCoords.size(); i++){
            x[i] = xCoords.get(i);
            y[i] = yCoords.get(i);
        }

        g.strokePolygon(x, y, edges);
    }

    /**
     * Draws a plot to show the user where the vertices for their polygon will be placed
     * @param coords - gets the current coordinate of the mouse click
     * */
    public void drawPlot(double[] coords){
        if(edgeCount < edges){
            g.strokeLine(coords[0], coords[1], coords[0], coords[1]);
            xCoords.add(coords[0]);
            yCoords.add(coords[1]);
            edgeCount += 1;
        } else {
            drawPolygon();
        }
    }

    /**
     * Prompts the user to input how many edges their polygon will have.
     * Input cannot be negative or not a number
     * @return returns the number of edges in integer form
     * */
    public int getUserInput(){
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
                        return edges;
                    }
                } catch (Exception e) {
                    // Display if any errors occur
                    System.out.println("Failed to open file: " + e);
                }
            } else {
                correctInput = true;
            }
        } while(!correctInput);
        return 4;
    }
}
