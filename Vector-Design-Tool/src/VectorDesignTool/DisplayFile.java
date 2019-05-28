package VectorDesignTool;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class DisplayFile {
    private GraphicsContext g;
    private Canvas canvas;
    private String[][] fileLines;
    private Shapes shape;
    private DrawPolygon drawPolygon;

    /**
     * Displays the drawing on the canvas using the read coordinates from the file for each shape.
     * @param g - Graphics context of drawing canvas
     * @param canvas - Drawing canvas
     * @param fileLines - Scanned file lines from opened file
     */
    public DisplayFile(GraphicsContext g, Canvas canvas, String[][] fileLines) {
        this.g = g;
        this.canvas = canvas;
        this.fileLines = fileLines;
        this.shape = new Shapes("", g, new double[0][0]);
        this.drawPolygon = new DrawPolygon(g);
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
                    shape.setIsFill(true);
                    drawPolygon.setIsFill(true);
                    changeFillColour(t[1]);
                } else if(b.contains("PEN") && !b.contains("PEN-WIDTH")){
                    shape.setIsFill(false);
                    drawPolygon.setIsFill(false);
                    changePenColour(t[1]);
                } else if(b.contains("PEN-WIDTH")){
                    changePenWidth(t[1]);
                } else if(b.contains("LINE") || b.contains("RECTANGLE")){
                    displayShape(t);
                }
                break;
            }
        }
        shape.setIsFill(false);
        drawPolygon.setIsFill(false);
    }

    public void changeFillColour(String t){
        try{
            g.setFill(Color.web(t));
        } catch(Exception e){
            g.setFill(Color.BLACK);
            System.out.println("Error in change fill colour in DisplayFile (64): " + e);
        }
    }

    public void changePenColour(String t){
        try{
            g.setStroke(Color.web(t));
        } catch(Exception e){
            g.setStroke(Color.BLACK);
            System.out.println("Error in change stroke colour in DisplayFile (72): " + e);
        }
    }

    public void changePenWidth(String t){
        try{
            g.setLineWidth(Integer.parseInt(t));
        } catch(Exception e){
            g.setLineWidth(5);
            System.out.println("Error in change stroke width in DisplayFile (80): " + e);
        }
    }

    /**
     * Draws a plot on the canvas.
     * @param t - Array of words from current file line
     * @param b - single word from current file line
     */
    public void displayPlot(String[] t, String b){
        try {
            double[][] coords = {{Double.parseDouble(t[1]) * canvas.getWidth(), Double.parseDouble(t[2]) * canvas.getHeight()},
                    {Double.parseDouble(t[1]) * canvas.getWidth(), Double.parseDouble(t[2]) * canvas.getHeight()}};
            shape.setCoords(coords);
            shape.setSelectedShape(b);
            shape.drawShape();
        } catch (Exception e) {
            System.out.println("Error in PLOT read: " + e);
        }
    }

    /**
     * Draws a polygon on the canvas.
     * @param t - Array of words from current file line
     */
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
            drawPolygon.setCoord(xCoords, yCoords);
            drawPolygon.setEdges(edges);
            drawPolygon.drawPolygon();
        } catch (Exception e) {
            System.out.println("Error in DisplayFile displayPolygon (76): " + e);
        }
    }

    /**
     * Draws a line, rectangle or ellipse on canvas.
     * @param t - Array of words from current file line
     */
    public void displayShape(String[] t){
        try {
            double[][] coords = {{Double.parseDouble(t[1]) * canvas.getWidth(), Double.parseDouble(t[2]) * canvas.getHeight()},
                    {Double.parseDouble(t[3]) * canvas.getWidth(), Double.parseDouble(t[4]) * canvas.getHeight()}};
            shape.setCoords(coords);
            shape.setSelectedShape(t[0]);
            shape.drawShape();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
