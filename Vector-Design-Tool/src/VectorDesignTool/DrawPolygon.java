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
        try{
            g.strokePolygon(xArr, yArr, edges);
        } catch (Exception e){
            System.out.println("Error drawing polygon: " + e);
        }

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
                xArr = new double[xCoords.size()];
                yArr = new double[yCoords.size()];
                for(int i = 0; i < xCoords.size(); i++){
                    xArr[i] = xCoords.get(i);
                    yArr[i] = yCoords.get(i);
                }
                drawPolygon();
                resetPolygon();
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
                }
            } else {
                correctInput = true;
            }
        } while(!correctInput);
        return 4;
    }

    /**
     * This sets the polygon array coordinates to a given array of x and y coordinates
     * @param arrayX - double array of x coordinates
     * @param arrayY - double array of y coordinates
     * */
    public void setCoord(double[] arrayX, double[] arrayY) {
        this.xArr = arrayX;
        this.yArr = arrayY;
    }

    public void setEdges(int edges){ this.edges = edges; }

    public double[] getxArr() { return this.xArr; }
    public double[] getyArr() { return this.yArr; }

    public void resetPolygon(){
        edgeCount = 0;
        xCoords.clear();
        yCoords.clear();
    }
}
