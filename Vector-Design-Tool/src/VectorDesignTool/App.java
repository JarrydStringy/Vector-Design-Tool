package VectorDesignTool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    /**
     * Launches and sets up JavaFX application
     * @param args - commandline arguments as an array of String objects
     */
    public static void main(String[] args) { launch(args); }

    /**
     * Prepares stage and sets scene with UI content
     * @param stage - the main window of the application
     */
    public void start(Stage stage) throws Exception {
        // Stage/Program name
        stage.setTitle("Vector Design Tool");
        // Creating a scene object from the UI file
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("UI.fxml"))));
        // Displays the contents of the stage
        stage.show();
    }
}