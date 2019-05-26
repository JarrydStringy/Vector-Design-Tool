package VectorDesignTool;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Shapes extends DrawShape {
    private double[][] coords;
    private GraphicsContext g;
    private double brushSize;

    /**
     * When the rectangle button is pressed, the object is created.
     * @param coords - {(x,y)(x,y)} coordinates of start and finish of rectangle
     * @param g - canvas graphics context
     */
    public Shapes(String selectedShape, GraphicsContext g, double[][] coords, double brushSize){
        super(selectedShape, g, coords, brushSize);
        this.coords = coords;
        this.g = g;
        this.brushSize = brushSize;
    }

    // ------------------------------------------------------------ Drawing Shape Code
    /**
     * Draws a single point (plot)
     * */
    public void drawPlot(){ g.strokeLine(coords[0][0], coords[0][1], coords[0][0], coords[0][1]); }

    /**
     * Draws a rectangle with an initial point x,y then a width and height
     * */
    public void drawRectangle(){
        g.strokeRect(coords[0][0], coords[0][1], abs(coords[1][0] - coords[0][0]), abs(coords[1][1] - coords[0][1]));
    }

    /**
     * Draws a line from x1,y1 to x2,y2
     * */
    public void drawLine(){
        g.strokeLine(coords[0][0], coords[0][1], coords[1][0], coords[1][1]);
    }

    public void drawEllipse(){
        g.strokeOval(coords[0][0], coords[0][1],abs(coords[1][0] - coords[0][0]), abs(coords[1][1] - coords[0][1]));
    }

    /**
     * Draws a polygon
     * */
    public void drawPolygon(){
        // IMPLEMENT
    }
}
