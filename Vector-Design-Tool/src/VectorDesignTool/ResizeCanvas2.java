package VectorDesignTool;

import javafx.scene.CacheHint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class ResizeCanvas2 {
    public void resize(Pane canvasPane, GraphicsContext g, StringBuilder savefile, SaveFile save, ReadFile readFile, Canvas canvas, Canvas canvas2){
        // Readjust canvas values
        canvasPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> {
            if(Controller.isDrawing){
                save.saveCurrentFile("currentFile.vec", savefile.toString());
                readFile.setSelectedFile("currentFile.vec");
                Controller.isDrawing = false;
            }
            canvas.setWidth(newValue.doubleValue());
            try {
                g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                readFile.scanFile();
                readFile.displayFile();
            } catch (Exception e){ }
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
