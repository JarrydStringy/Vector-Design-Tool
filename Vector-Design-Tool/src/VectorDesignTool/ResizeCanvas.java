package VectorDesignTool;

import javafx.scene.CacheHint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

import java.io.File;

public class ResizeCanvas {
    private String fileName = "currentFile.vec";

    public void resize(Pane canvasPane, GraphicsContext g, StringBuilder savefile, SaveFile save, ReadFile readFile, Canvas canvas, Canvas canvas2, File file){

        canvasPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> {
            if(Controller.isDrawing){
                save.saveCurrentFile(fileName, savefile.toString());
                readFile.setSelectedFile(fileName);
                Controller.isDrawing = false;
            }
            canvas.setWidth(newValue.doubleValue());
            // Check if file exists
            if(file.exists()){
                g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                readFile.scanFile();
                readFile.displayFile();
            }
        });
        canvasPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> {
            canvas.setHeight(newValue.doubleValue());
        });
        canvasPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> {
            canvas2.setWidth(newValue.doubleValue());
        });
        canvasPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> {
            canvas2.setHeight(newValue.doubleValue());
        });
    }
}
