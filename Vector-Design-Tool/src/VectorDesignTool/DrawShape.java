package VectorDesignTool;

import javafx.scene.canvas.GraphicsContext;

public class DrawShape {
    private String selectedShape;
    private GraphicsContext g;
    private double[][] coords;

    /**
     * When the rectangle button is pressed, the object is created.
     * @param selectedShape - current shape selected on interface
     */
    public DrawShape(String selectedShape, GraphicsContext g, double[][] coords){
        this.selectedShape = selectedShape;
        this.g = g;
        this.coords = coords;
    }

    public void drawShape(){
        Shapes shape = new Shapes(coords, g);

        switch (selectedShape){
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
                    shape.drawLine();
        }

    }


}
