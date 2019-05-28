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

    /**
     * Selects which draw function to call based on selectShape string value
     */
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

    /**
     * Draws a plot; single dot on canvas.
     */
    public abstract void drawPlot();

    /**
     * Draws a line on the canvas
     */
    public abstract void drawLine();

    /**
     * Draws a rectangle on the canvas
     */
    public abstract void drawRectangle();

    /**
     * Draws an ellipse on the canvas
     */
    public abstract void drawEllipse();

    /**
     * Draws a polygon on the canvas
     */
    public abstract void drawPolygon();

}
