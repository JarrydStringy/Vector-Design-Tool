package VectorDesignTool;

import javafx.scene.canvas.GraphicsContext;

public class Shapes {
    private double[][] coords;
    private GraphicsContext g;

    /**
     * When the rectangle button is pressed, the object is created.
     * @param coords - {(x,y)(x,y)} coordinates of start and finish of rectangle
     * @param g - canvas graphics context
     */
    public Shapes(double[][] coords, GraphicsContext g){
        this.coords = coords;
        this.g = g;
    }

    public void drawRectangle(){ g.strokeRect(coords[0][0], coords[0][1], coords[1][0], coords[1][1]); }

    public void drawLine(){
        g.strokeLine(coords[0][0], coords[0][1], coords[1][0], coords[1][1]);
    }

    public void drawEllipse(){
        g.strokeOval(coords[0][0], coords[0][1], coords[1][0], coords[1][1]);
    }

    public void drawPolygon(){
        // IMPLEMENT
    }
}
