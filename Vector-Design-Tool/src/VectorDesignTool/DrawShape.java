package VectorDesignTool;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class DrawShape {
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
        Shapes shape = new Shapes(coords, g, brushSize);

        switch (selectedShape){
            case "LINE":
                shape.drawLine();
                break;
            case "RECTANGLE":
                shape.drawRectangle();
                break;
            case "ELLIPSE":
                shape.drawEllipse();
                break;
            case "POLYGON":
                shape.drawPolygon();
                break;
                default:
                    shape.drawPlot();
        }

    }

    public void removeShape(){
        Shapes shape = new Shapes(coords, g, brushSize);

        switch (selectedShape){
            case "LINE":
                shape.removeLine();
                break;
           /* case "RECTANGLE":
                shape.drawRectangle();
                break;
            case "ELLIPSE":
                shape.drawEllipse();
                break;
            case "POLYGON":
                shape.drawPolygon();
                break;*/
            default:
                shape.removeLine();
        }
    }


}
