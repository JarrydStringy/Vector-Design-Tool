package VectorDesignTool;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;

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

        // Instantiate the fxml Window
        Pane root = FXMLLoader.load(getClass().getResource("UI.fxml"));
        Scene scene = new Scene(root);

        // Stage/Program name
        stage.setTitle("Vector Design Tool");
        // Creating a scene object from the UI file
        stage.setScene(scene);
        // Displays the contents of the stage
        stage.show();


        root.prefWidthProperty().bind(scene.widthProperty());
        root.prefHeightProperty().bind(scene.heightProperty());

        // Listen to key presses
        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.Z && ke.isControlDown()) {
                    System.out.println("Key Pressed: " + ke.getCode());
                    Controller.undoRedo.Undo();
                } else if (ke.getCode() == KeyCode.Y && ke.isControlDown()){
                    System.out.println("Key Pressed: " + ke.getCode());
                    Controller.undoRedo.Redo();
                }
            }
        });

        stage.setOnCloseRequest(event -> {
            File file = new File("currentFile.vec");
            file.delete();
            System.out.println("Stage is closing, deleted current file");
        });
    }
}