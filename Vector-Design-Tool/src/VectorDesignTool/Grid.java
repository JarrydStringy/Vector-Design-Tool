package VectorDesignTool;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Grid {
    private GraphicsContext g;
    private Canvas canvas3;

    public Grid(GraphicsContext g, Canvas canvas){
        this.g = g;
        this.canvas3 = canvas;
    }

    public void drawGrid(){
        // first clear existing grid
        g.clearRect(0, 0, canvas3.getWidth(), canvas3.getHeight());
        // Vertical Lines
        g.setStroke(Color.BLACK);
        for (int i = 0; i < canvas3.getWidth(); i += Controller.gridSizeNow) {
            g.strokeLine(i, 0, i, canvas3.getHeight() - (canvas3.getHeight() % 30));
        }

        // Horizontal Lines
        for (int i = 15; i < canvas3.getHeight(); i += Controller.gridSizeNow) {
            g.strokeLine(1, i, canvas3.getWidth(), i);
        }
    }

    public void clearGrid(){
        g.clearRect(0, 0, canvas3.getWidth(), canvas3.getHeight());
    }

    public double getGridCoords(){
        return 0.0;
    }
}
