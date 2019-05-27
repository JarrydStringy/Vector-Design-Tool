package VectorDesignTool;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Scanner;

public class ReadFile {
    private Scanner x;
    private String selectedFile;
    private String[][] fileLines;
    private GraphicsContext g;
    private Canvas canvas;

    /**
     * Opens file selection window with '.vec' file filter and gets selected file from user selection.
     * Reads the selected file by scanning it, then stores each line in an array. This array is then
     * used to draw the shapes and output the commands in the file onto the canvas.
     * @param g - the GraphicsContext of the canvas being drawn on
     * @param canvas - the canvas being drawn on
     */
    public ReadFile(GraphicsContext g, Canvas canvas) {
        this.g = g;
        this.canvas = canvas;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        // Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("VEC files (*.vec)", "*.vec");
        fileChooser.getExtensionFilters().add(extFilter);

        try {
            File file = fileChooser.showOpenDialog(null);
            selectedFile = file.getAbsolutePath();
        } catch(Exception e){
            // Display if any errors occur
            System.out.println("Failed to open file: " + e);
        }
    }

    /**
     * Opens and reads scanned file line by line and stores each line in an array
     */
    public void scanFile(){
        // Scan file using Scanner class
        try{
            x = new Scanner(new File(selectedFile));
        } catch(Exception e){
            // Display if any errors occur
            System.out.println("Failed to scan file: " + e);
        }
        // Read file line by line
        String line = "";
        while(x.hasNextLine()){
            line += x.nextLine() + "\n";
        }
        // Store each line in array
        String[] a = line.split("\n");
        // Store each command in an array per line
        fileLines = new String[a.length][];
        for(int i = 0; i < a.length; i++){
            fileLines[i] = a[i].split(" ");
        }
        // Print each line to console
        for(String[] t:fileLines){
            for(String b:t){
                //System.out.println(b);
            }
        }
        // Close scanner
        x.close();
    }

    /**
     * Displays the drawing on the canvas using the read coordinates from the file
     */
    public void displayFile(){
        // Go through each line then each command
        for(String[] t:fileLines){
            for(String b:t){
                if (b.contains("PLOT")){
                    try {
                        double[][] coords = {{Double.parseDouble(t[1]) * canvas.getWidth(), Double.parseDouble(t[2]) * canvas.getHeight()},
                                {Double.parseDouble(t[1]) * canvas.getWidth(), Double.parseDouble(t[2]) * canvas.getHeight()}};
                        Shapes shape = new Shapes(b, g, coords);
                        shape.drawShape();
                    } catch(Exception e){
                        System.out.println("Error in PLOT read: " + e);
                    }
                }else if(b.contains("POLYGON")){
                    try {
                        int edges = 0;
                        String xs = "";
                        String ys = "";
                        for (int i = 1; i < t.length-2; i++) {
                            edges++;
                            xs += t[i] + " ";
                            ys += t[i+1] + " ";
                        }
                        String[] xss = xs.split(" ");
                        String[] yss = ys.split(" ");
                        double[] x = new double[xss.length];
                        double[] y = new double[yss.length];
                        for(int i = 0; i < xss.length; i++){
                            x[i] = Double.parseDouble(xss[i]) * canvas.getWidth();
                            y[i] = Double.parseDouble(yss[i]) * canvas.getHeight();
                        }
                        DrawPolygon drawPolygon = new DrawPolygon(g);
                        drawPolygon.setCoord(x,y);
                        drawPolygon.setEdges(edges);
                        drawPolygon.drawPolygon();
                        System.out.println(canvas.getWidth());
                    } catch(Exception e){
                        System.out.println("Error in POLYGON read: " + e);
                    }
                } else{
                    try {
                        double[][] coords = {{Double.parseDouble(t[1]) * canvas.getWidth(), Double.parseDouble(t[2]) * canvas.getHeight()},
                                {Double.parseDouble(t[3]) * canvas.getWidth(), Double.parseDouble(t[4]) * canvas.getHeight()}};
                        Shapes shape = new Shapes(b, g, coords);
                        shape.drawShape();
                    } catch(Exception e){
                        System.out.println(e);
                    }
                }
                break;
            }
        }
    }
}
