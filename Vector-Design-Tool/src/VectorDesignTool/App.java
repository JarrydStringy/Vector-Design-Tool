package VectorDesignTool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    //Launches application
    public static void main(String[] args) { launch(args); }

    //Prepares stage and sets scene with UI content
    public void start(Stage stage) throws Exception {
        //Creating a scene object from the UI file
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("UI.fxml"))));
        //Program name
        stage.setTitle("Vector Design Tool");
        //Displays the contents of the stage
        stage.show();
    }
}