package VectorDesignTool;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UndoRedo {
    StringBuilder savefile = SaveFile.saveFile;
    private String undoFile = "UndoRedo/undoFile.vec";
    private String redoFile = "UndoRedo/redoFile.vec";
    private String currentFileName = "currentFile.vec";
    private File currentFile;
    private ReadFile readFile;
    private SaveFile saveFile;
    private List<String[]> currentFileLines;
    private List<String[]> removedLine;
    private GraphicsContext g;
    private Canvas canvas;

    public UndoRedo(GraphicsContext g, Canvas canvas) {
        this.g = g;
        this.canvas = canvas;
        readFile = new ReadFile(g, canvas);
        saveFile = new SaveFile(g);
        currentFile = new File(currentFileName);
        removedLine = new ArrayList<>();
    }

    public boolean getCurrentFileLines() {
        if (currentFile.exists()) {
            readFile.setSelectedFile(currentFileName);
            readFile.scanFile();
            currentFileLines = new LinkedList<>(Arrays.asList(readFile.getFileLines()));
            return true;
        } else {
            currentFileLines = null;
            return false;
        }
    }

    //Appended savefile on undo
    public void Undo() {
        if (getCurrentFileLines()) {
            try {
                String[] removed = currentFileLines.get(currentFileLines.size() - 1);
                removedLine.add(removed);
                currentFileLines.remove(currentFileLines.size() - 1);
                int start = savefile.lastIndexOf("\n" + removed[0] + " " + removed[1]);
                savefile.delete(start, savefile.length());
                displayChange();
            } catch (Exception e) {
                System.out.println("Error in undo (51): " + e);
            }
        }
    }

    //Appended savefile on redo
    public void Redo() {
        try {
            currentFileLines.add(removedLine.get(removedLine.size() - 1));
            savefile.append("\n" + String.join(" ", removedLine.get(removedLine.size() - 1)));
            removedLine.remove(removedLine.get(removedLine.size() - 1));
            displayChange();
        } catch (Exception e) {
            System.out.println("Error in redo (61): " + e);
        }
    }

    public void displayChange() {
        String[][] newFileLines = new String[currentFileLines.size()][];
        String saveFileResult = "";
        for (int i = 0; i < currentFileLines.size(); i++) {
            newFileLines[i] = currentFileLines.get(i);
            for (int j = 0; j < currentFileLines.get(i).length; j++) {
                saveFileResult += currentFileLines.get(i)[j] + " ";
            }
            saveFileResult += "\n";
        }
        readFile.setFileLines(newFileLines);
        saveFile.saveCurrentFile(currentFileName, saveFileResult);
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        readFile.displayFile();
    }

}
