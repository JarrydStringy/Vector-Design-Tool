package VectorDesignTool;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

import java.io.File;

public class ResizeCanvas {
    private String fileName = "currentFile.vec";

    public void resize(Pane canvasPane, GraphicsContext g, GraphicsContext g3, StringBuilder savefile, SaveFile save, ReadFile readFile, Canvas canvas, Canvas canvas2, Canvas canvas3, File file) {
        Grid grid = new Grid(g3,canvas3);
        canvasPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> {
            if(newValue.doubleValue() > 600) {
                if (Controller.isDrawing) {
                    save.saveCurrentFile(fileName, savefile.toString());
                    Controller.isDrawing = false;
                }
                canvas.setWidth(newValue.doubleValue() - 314);
                canvas2.setWidth(newValue.doubleValue() - 314);
                canvas3.setWidth(newValue.doubleValue() - 314);
                // Draw grid if grid is checked
                if (Controller.gridOn) {
                    grid.drawGrid();
                }
                // Check if file exists
                if (file.exists()) {
                    g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    readFile.setSelectedFile(fileName);
                    readFile.scanFile();
                    readFile.displayFile();
                }
            }
        });
        canvasPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> {
            if(newValue.doubleValue() > 668) {
                canvas.setHeight(newValue.doubleValue() - 68);
                canvas2.setHeight(newValue.doubleValue() - 68);
                canvas3.setHeight(newValue.doubleValue() - 68);
                // redraw grid if grid is checked
                if (Controller.gridOn) {
                    grid.drawGrid();
                }
            }
        });
    }
}
