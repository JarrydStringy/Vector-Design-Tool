package VectorDesignTool;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DrawPolygon{
    private GraphicsContext g;
    private int edges = 4;
    public static int edgeCount = 0;
    public static List<Double> xCoords;
    public double[] xArr;
    public double[] yArr;
    public static List<Double> yCoords;

    /**
     * Draws a polygon based on given plot coordinates from user.
     * User must predefine number of edges in the polygon when prompted.
     * Edges must be greater than 2 and up to 100.
     * @param g - the GraphicsContext of the canvas being drawn on
     * */
    public DrawPolygon(GraphicsContext g){
        this.g = g;
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
        setCoord(x, y);
        g.strokePolygon(x, y, edges);
    }
    public void setCoord(double[] arrayX, double[] arrayY)
    {
        this.xArr = arrayX;
        this.yArr = arrayY;
    }

    public double[] getxArr()
    {
        return this.xArr;
    }
    public double[] getyArr()
    {
        return this.yArr;
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
            if(edgeCount == edges){
                drawPolygon();
            }
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
                    if (result.get().matches("[0-9]*") == false || Integer.parseInt(result.get()) < 3 || Integer.parseInt(result.get()) > 100) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Input");
                        alert.setHeaderText(null);
                        alert.setContentText("Please enter a positive integer between 3 and 100.");
                        alert.showAndWait();
                    } else {
                        edges = Integer.parseInt(result.get());

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Draw Polygon");
                        alert.setHeaderText(null);
                        alert.setContentText("You have set the number of edges to " + edges +
                                ". \nPlace " + edges + " points on the canvas in the order which the edges will be drawn.");
                        alert.showAndWait();
                        return edges;
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter a positive integer between 3 and 100.");
                    alert.showAndWait();
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
