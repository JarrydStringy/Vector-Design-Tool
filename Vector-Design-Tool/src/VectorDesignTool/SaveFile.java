package VectorDesignTool;

import javafx.scene.canvas.GraphicsContext;

import java.io.File;
import java.text.DecimalFormat;

import java.io.FileWriter;
import javafx.stage.FileChooser;


public class SaveFile{

    public static StringBuilder saveFile = new StringBuilder("");
    public static DecimalFormat df = new DecimalFormat("0.000000");
    private String selectedFile;
    private GraphicsContext g;

    public SaveFile(GraphicsContext g){
        this.g = g;
        saveFile.deleteCharAt(0);
        String savefile = saveFile.toString();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Resource File");

        // Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("VEC files (*.vec)", "*.vec");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(null);

        if (file != null)
        {
            saveToFile(file, savefile);
        }

    }
    private void saveToFile(File file, String result)
    {
        try {
            FileWriter vecFile;
            vecFile = new FileWriter(file);
            vecFile.write(result);
            vecFile.close();
        }
        catch(Exception e) {
            System.out.println("Failed to save file: " + e);
        }
    }

}


