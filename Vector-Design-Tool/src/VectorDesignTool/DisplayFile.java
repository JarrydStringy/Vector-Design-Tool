package VectorDesignTool;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class DisplayFile{
    private GraphicsContext g;
    private Canvas canvas;
    private String[][] fileLines;

    public DisplayFile(GraphicsContext g, Canvas canvas, String[][] fileLines) {
        this.g = g;
        this.canvas = canvas;
        this.fileLines = fileLines;
    }

    /**
     * Displays the drawing on the canvas using the read coordinates from the file
     */
    public void displayFile() {
        // Go through each line then each command
        for (String[] t : fileLines) {
            for (String b : t) {
                if (b.contains("PLOT")) {
                    displayPlot(t,b);
                } else if (b.contains("POLYGON")) {
                    displayPolygon(t);
                } else if(b.contains("FILL")){
                    //IMPLEMENT
                } else if(b.contains("PEN")){
                    //IMPLEMENT
                } else {
                    displayShape(t,b);
                }
                break;
            }
        }
    }

    public void displayPlot(String[] t, String b){
        try {
            double[][] coords = {{Double.parseDouble(t[1]) * canvas.getWidth(), Double.parseDouble(t[2]) * canvas.getHeight()},
                    {Double.parseDouble(t[1]) * canvas.getWidth(), Double.parseDouble(t[2]) * canvas.getHeight()}};
            Shapes shape = new Shapes(b, g, coords);
            shape.drawShape();
        } catch (Exception e) {
            System.out.println("Error in PLOT read: " + e);
        }
    }

    public void displayPolygon(String[] t){
        try {
            int edges = t.length/2;
            List<Double> xCoords = new ArrayList<>();
            List<Double> yCoords = new ArrayList<>();
            for (int i = 1; i < t.length; i++) {
                if(( i % 2 != 0 )){
                    xCoords.add(Double.parseDouble(t[i]) * canvas.getWidth());
                } else {
                    yCoords.add(Double.parseDouble(t[i]) * canvas.getHeight());
                }
            }
            DrawPolygon drawPolygon = new DrawPolygon(g);
            drawPolygon.setCoord(xCoords, yCoords);
            drawPolygon.setEdges(edges);
            drawPolygon.drawPolygon();
        } catch (Exception e) {
            System.out.println("Error in DisplayFile displayPolygon (76): " + e);
        }
    }

    public void displayShape(String[] t, String b){
        try {
            double[][] coords = {{Double.parseDouble(t[1]) * canvas.getWidth(), Double.parseDouble(t[2]) * canvas.getHeight()},
                    {Double.parseDouble(t[3]) * canvas.getWidth(), Double.parseDouble(t[4]) * canvas.getHeight()}};
            Shapes shape = new Shapes(b, g, coords);
            shape.drawShape();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
