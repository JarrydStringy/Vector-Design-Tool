package VectorDesignTool;

import javafx.scene.canvas.GraphicsContext;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;


public class SaveFile {
    public static StringBuilder saveFile = new StringBuilder();
    public static DecimalFormat df = new DecimalFormat("0.000000");
    private GraphicsContext g;


    /**
     * Saves file to chosen directory based on user selection, of file type 'VEC'.
     *
     * @param g - Graphics Context of canvas
     */
    public SaveFile(GraphicsContext g) {
        this.g = g;
    }

    public void saveFile() {
        String savefile = saveFile.toString();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Resource File");
        // Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("VEC files (*.vec)", "*.vec");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            saveToFile(file, savefile);
        }
    }

    /**
     * Writes the file using FileWriter class.
     *
     * @param file   - file being saved to
     * @param result - string of contents being written to file
     */
    private void saveToFile(File file, String result) {
        try {
            FileWriter vecFile;
            vecFile = new FileWriter(file);
            vecFile.write(result);
            vecFile.close();
        } catch (Exception e) {
            System.out.println("Failed to save file: " + e);
        }
    }

    /**
     * Writes the file using FileWriter class.
     *
     * @param file   - file being saved to
     * @param result - string of contents being written to file
     */
    public void saveCurrentFile(String file, String result) {
        try {
            FileWriter vecFile;
            vecFile = new FileWriter(file);
            vecFile.write(result);
            vecFile.close();
        } catch (Exception e) {
            System.out.println("Failed to save file: " + e);
        }
    }
}


