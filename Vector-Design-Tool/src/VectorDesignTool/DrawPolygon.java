package VectorDesignTool;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DrawPolygon{
    private GraphicsContext g;
    public static List<Double> xCoords;
    public static List<Double> yCoords;
    private int edgeCount = 0;
    private int edges = 4;
    public static boolean isFill;

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

    public void setIsFill(boolean isFill){this.isFill = isFill;}

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
        if(isFill){
            g.fillPolygon(x, y, edges);
        } else{
            g.strokePolygon(x, y, edges);
        }
    }
    /**
     * Draws a plot to show the user where the vertices for their polygon will be placed
     * @param coords - Array of double numbers for coordinates where user has clicked canvas
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
        Alerts alert = new Alerts();
        do {
            // Traditional way to get the response value.
            Optional<String> result = alert.polygonPromptEdgeInput();

            // Check if user input is valid
            if (result.isPresent()) {
                try {
                    if (result.get().matches("[0-9]*") == false || Integer.parseInt(result.get()) < 3 || Integer.parseInt(result.get()) > 100) {
                        alert.polygonEdgeError();
                    } else {
                        edges = Integer.parseInt(result.get());
                        alert.polygonDrawInfo(edges);
                        return edges;
                    }
                } catch (Exception e) {
                    alert.polygonEdgeError();
                    // Display if any errors occur
                    System.out.println("Failed to open file: " + e);
                }
            } else {
                correctInput = true;
            }
        } while(!correctInput);
        return 4;
    }

    /**
     * Resets edgeCount and coordinates for new polygon
     */
    public void resetPolygon(){
        edgeCount = 0;
        xCoords.clear();
        yCoords.clear();
    }

    /**
     * Sets edges to given integer of edges
     * @param edges - given integer of edges for polygon
     */
    public void setEdges(int edges){ this.edges = edges; }

    /**
     * Sets coordinates to given list of coordinates
     */
    public void setCoord(List<Double> xCoords, List<Double> yCoords) {
        this.xCoords = xCoords;
        this.yCoords = yCoords;
    }
}
