package Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("UI.fxml"))));
        stage.setTitle("Vector Design Tool");
        stage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
