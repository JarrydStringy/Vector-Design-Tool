package VectorDesignTool;

import javafx.scene.canvas.GraphicsContext;

public class SaveFile extends ReadFile {
    private double[][] coords;

    public SaveFile(double[][] coords, GraphicsContext g){
        super(g);
    }
}
