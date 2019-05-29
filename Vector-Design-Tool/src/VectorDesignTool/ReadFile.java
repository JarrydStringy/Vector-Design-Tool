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
    }

    public void readfile(){
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
        scanFile();
    }

    /**
     * Opens and reads scanned file line by line and stores each line in an array
     */
    public void scanFile(){
        // Scan file using Scanner class
        try{
            x = new Scanner(new File(selectedFile));
        } catch(Exception e){ }
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

        // Close scanner
        x.close();
    }

    /**
     * Displays the drawing on the canvas using the read coordinates from the file
     */
    public void displayFile(){
        DisplayFile displayFile = new DisplayFile(g, canvas, fileLines);
        displayFile.displayFile();
    }

    /**
     * Sets the selected file to a given file path
     */
    public void setSelectedFile(String selectedFile){
        this.selectedFile = selectedFile;
    }

    /**
     * Gets the scanned file lines.
     * @return 2 dimensional String array of scanned file lines
     */
    public String[][] getFileLines(){ return fileLines; }
}
