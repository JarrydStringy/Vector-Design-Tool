package VectorDesignTool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

public class App extends Application {
    Scene sceneStar;

    @Override
    public void start(Stage stage) throws Exception {

//        Comment out sections of code and uncomment below for original
        //===========================

        //Test
//        drawStar();
//
//        //Setting title to the Stage
//        stage.setTitle("Sample Program");
//        //Adding scene to the stage
//        stage.setScene(sceneStar);
//        //Displaying the contents of the stage
//        stage.show();

        //============================================= Testing vertical toolbar buttons

        //Name RadioButtons
        RadioButton rdoPlot = new RadioButton("Plot");
        RadioButton rdoLine = new RadioButton("Line");
        RadioButton rdoRectangle = new RadioButton("Rectangle");
        RadioButton rdoEllipse = new RadioButton("Ellipse");
        RadioButton rdoPolygon = new RadioButton("Polygon");

        //Group Buttons
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
        Scene guiScene = new Scene(pane, 600, 400);

        //=====================================================

        //Creating a scene object from the UI file
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("UI.fxml"))));
        //stage.setScene(guiScene);
        //Program name
        stage.setTitle("Vector Design Tool");
        //Displays the contents of the stage
        stage.show();
    }








    //Draw Star
    public void drawStar(){
        //Moving to the starting point
        MoveTo moveTo = new MoveTo(108, 71);
        //Creating 1st line
        LineTo line1 = new LineTo(321, 161);
        //Creating 2nd line
        LineTo line2 = new LineTo(126,232);
        //Creating 3rd line
        LineTo line3 = new LineTo(232,52);
        //Creating 4th line
        LineTo line4 = new LineTo(269, 250);
        //Creating 4th line
        LineTo line5 = new LineTo(108, 71);

        //Creating a Path
        Path path = new Path();
        //Adding all the elements to the path
        path.getElements().add(moveTo);
        path.getElements().addAll(line1, line2, line3, line4, line5);
        //Creating a Group object
        Group root = new Group(path);
        //Creating a scene object
        sceneStar = new Scene(root, 600, 300);
    }

    public static void main(String[] args) { launch(args); }
}