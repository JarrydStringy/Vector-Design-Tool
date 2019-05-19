package sample;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override public void start(Stage stage) {
        RadioButton rdoPlot = new RadioButton("Plot");
        RadioButton rdoLine = new RadioButton("Line");
        RadioButton rdoRectangle = new RadioButton("Rectangle");
        RadioButton rdoEllipse = new RadioButton("Ellipse");
        RadioButton rdoPolygon = new RadioButton("Polygon");
        ToggleGroup groupDifficulty = new ToggleGroup();
        groupDifficulty.getToggles().addAll(
                rdoPlot,
                rdoLine,
                rdoRectangle,
                rdoEllipse,
                rdoPolygon);
        ToolBar toolBarTools = new ToolBar();
        toolBarTools.setOrientation(Orientation.VERTICAL);
        toolBarTools.getItems().addAll(
                new Separator(),
                rdoPlot,
                rdoLine,
                rdoRectangle,
                rdoEllipse,
                rdoPolygon,
                new Separator());
        BorderPane pane = new BorderPane();
        pane.setLeft(toolBarTools);
        Scene scene = new Scene(pane, 600, 400);
        stage.setScene(scene);
        stage.setTitle("ToolBar Sample");
        stage.show();
    }
}