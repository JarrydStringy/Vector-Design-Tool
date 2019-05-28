package VectorDesignTool;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.text.DecimalFormat;
import static java.lang.Math.abs;

public class Shapes extends DrawShape {
    private double[][] coords;
    private GraphicsContext g;
    private boolean isFill = false;

    /**
     * Draws shapes (Plot, Rectangle, Line, Ellipse or Polygon) based on selectedShape input.
     * @param coords - {(x,y)(x,y)} coordinates of start and finish of rectangle
     * @param g - canvas graphics context
     */
    public Shapes(String selectedShape, GraphicsContext g, double[][] coords){
        super(selectedShape);
        this.coords = coords;
        this.g = g;
    }

    public void setCoords(double[][] coords){ this.coords = coords; }

    /**
     * Sets isFill boolean to input boolean value
     * */
    public void setIsFill(boolean isFill){
        this.isFill = isFill;
    }

    /**
     * Draws a single point
     * */
    public void drawPlot(){ g.strokeLine(coords[0][0], coords[0][1], coords[0][0], coords[0][1]); }

    /**
     * Draws a rectangle with an initial point x,y then a width and height
     * */
    public void drawRectangle(){
        double x1 = coords[0][0]; double x2 = coords[1][0];
        double y1 = coords[0][1]; double y2 = coords[1][1];
        if(x1 > x2){ x1 = coords[1][0]; x2 = coords[0][0];}
        if(y1 > y2){ y1 = coords[1][1]; y2 = coords[0][1];}
        if(isFill){
            g.fillRect(x1, y1, abs(x2 - x1), abs(y2 - y1));
        }
        g.strokeRect(x1, y1, abs(x2 - x1), abs(y2 - y1));
    }

    /**
     * Draws a line from x1,y1 to x2,y2
     * */
    public void drawLine(){g.strokeLine(coords[0][0], coords[0][1], coords[1][0], coords[1][1]);}

    /**
     * Draws an ellipse from start point x,y with a width w and height h
     * */
    public void drawEllipse(){
        double x1 = coords[0][0]; double x2 = coords[1][0];
        double y1 = coords[0][1]; double y2 = coords[1][1];
        if(x1 > x2){ x1 = coords[1][0]; x2 = coords[0][0];}
        if(y1 > y2){ y1 = coords[1][1]; y2 = coords[0][1];}
        if(isFill){
            g.fillOval(x1, y1, abs(x2 - x1), abs(y2 - y1));
        }
        g.strokeOval(x1, y1, abs(x2 - x1), abs(y2 - y1));
    }

    /**
     * Draws a polygon (this function is overridden in DrawPolygon class)
     * */
    public void drawPolygon(){};
}
