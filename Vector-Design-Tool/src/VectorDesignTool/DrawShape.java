package VectorDesignTool;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public abstract class DrawShape {
    private String selectedShape;

    /**
     * Calls the functions to draw the shape based on the selectedShape value
     * @param selectedShape - current shape selected on interface
     */
    public DrawShape(String selectedShape){
        this.selectedShape = selectedShape;
    }

    public void drawShape(){
        switch (selectedShape){
            case "PLOT":
                drawPlot();
                break;
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
                    break;
        }

    }

    public abstract void drawPlot();
    public abstract void drawLine();
    public abstract void drawRectangle();
    public abstract void drawEllipse();
    public abstract void drawPolygon();

}
