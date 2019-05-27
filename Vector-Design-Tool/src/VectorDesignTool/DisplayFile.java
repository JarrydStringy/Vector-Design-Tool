package VectorDesignTool;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

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
            String xs = "";
            String ys = "";
            for (int i = 1; i < t.length - 2; i++) {
                xs += t[i] + " ";
                ys += t[i + 1] + " ";
            }
            String[] xss = xs.split(" ");
            String[] yss = ys.split(" ");
            double[] x = new double[xss.length];
            double[] y = new double[yss.length];
            for (int i = 0; i < xss.length; i++) {
                x[i] = Double.parseDouble(xss[i]) * canvas.getWidth();
                y[i] = Double.parseDouble(yss[i]) * canvas.getHeight();
            }
            DrawPolygon drawPolygon = new DrawPolygon(g);
            drawPolygon.setCoord(x, y);
            drawPolygon.setEdges(edges);
            drawPolygon.drawPolygon();
        } catch (Exception e) {
            System.out.println("Error in POLYGON read: " + e);
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
