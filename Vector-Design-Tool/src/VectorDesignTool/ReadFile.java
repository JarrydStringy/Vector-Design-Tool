package VectorDesignTool;

import javafx.stage.FileChooser;

import java.io.File;
import java.util.Scanner;

public class ReadFile {
    private Scanner x;
    private String selectedFile;
    private String[] fileLines;

    /**
     * Opens file selection window with '.txt' file filter
     * and gets selected file from user selection
     */
    public ReadFile(){
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
        // store each line in array
        fileLines = line.split("\n");
        // Print each line to console
        for(String a:fileLines){
            System.out.println(a);
        }
        // Close scanner
        x.close();
    }

    /**
     * Gets the read file lines stored in an array
     * @return returns read file lines array
     */
    public String[] getFileLines(){ return fileLines; }
}
