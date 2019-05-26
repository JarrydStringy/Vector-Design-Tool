package VectorDesignTool;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public abstract class DrawShape {
    private String selectedShape;
    private GraphicsContext g;
    private double[][] coords;
    private double brushSize;

    /**
     * When the rectangle button is pressed, the object is created.
     * @param selectedShape - current shape selected on interface
     */
    public DrawShape(String selectedShape, GraphicsContext g, double[][] coords, double brushSize){
        this.selectedShape = selectedShape;
        this.g = g;
        this.coords = coords;
        this.brushSize = brushSize;
    }

    public void drawShape(){
        switch (selectedShape){
            case "LINE":
                drawLine();
                break;
            case "RECTANGLE":
                drawRectangle();
                break;
            case "ELLIPSE":
                drawEllipse();
                break;
            case "POLYGON":
                drawPolygon();
                break;
                default:
                    drawPlot();
        }

    }

    public abstract void drawPlot();
    public abstract void drawLine();
    public abstract void drawRectangle();
    public abstract void drawEllipse();
    public abstract void drawPolygon();

}
