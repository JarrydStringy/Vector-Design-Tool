package VectorDesignTool;

import javafx.scene.canvas.GraphicsContext;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Scanner;

public class ReadFile {
    private Scanner x;
    private String selectedFile;
    private String[][] fileLines;
    private GraphicsContext g;
    private double brushSize;

    /**
     * Opens file selection window with '.txt' file filter
     * and gets selected file from user selection
     */
    public ReadFile(GraphicsContext g, double brushSize){
        this.g = g;
        this.brushSize = brushSize;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        // Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
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
                System.out.println(b);
            }
        }
        // Close scanner
        x.close();
    }

    /**
     * Gets the read file lines stored in an array
     * @return returns read file lines array
     */
    public void displayFile(){

        // Go through each line then each command
        for(String[] t:fileLines){
            for(int i = 0; i < t.length; i++){
                if(t[i] != "PLOT"){
                    try {
                        double[][] coords = {{Double.parseDouble(t[i + 1]) * 600, Double.parseDouble(t[i + 2]) * 600},
                                {Double.parseDouble(t[i + 3]) * 600, Double.parseDouble(t[i + 4]) * 600}};
                        DrawShape shape = new DrawShape(t[i], g, coords ,brushSize);
                        shape.drawShape();
                    } catch(Exception e){
                        System.out.println(e);
                    }
                    i = t.length;
                }
                i = t.length;
            }
        }
    }
}
