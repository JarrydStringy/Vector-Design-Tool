package VectorDesignTool;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class Alerts {
    /**
     * Informs the user to select pen or fill to draw on the canvas.
     * */
    public void selectDraw(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Want to draw...?");
        alert.setHeaderText(null);
        alert.setContentText("Please select Pen or Fill tool to draw");
        alert.showAndWait();
    }

    /**
     * Checks if the user would like to clear the canvas.
     * @return the result
     * */
    public Optional<ButtonType> clearCanvasCheck(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Clear Canvas");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to clear the canvas?");
        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonCancel = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(buttonYes, buttonCancel);
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }

    /**
     * Alerts the user with an error dialogue stating they have entered an incorrect brush size value.
     * */
    public void brushSizeError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a positive integer between 1 and 200.");
        alert.showAndWait();
    }

    /**
     * Alerts the user with an error dialogue stating they have entered an incorrect brush size value.
     * */
    public Optional<String> polygonPromptEdgeInput(){
        TextInputDialog dialog = new TextInputDialog("4");
        dialog.setTitle("Polygon Edges");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter the number of edges:");
        Optional<String> result = dialog.showAndWait();
        return result;
    }

    /**
     * Alerts the user with an error dialogue stating they have entered an incorrect polygon edge value.
     * */
    public void polygonEdgeError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a positive integer between 3 and 100.");
        alert.showAndWait();
    }

    /**
     * Informs the user on how to draw a polygon.
     * @param edges - the number of edges the user entered for the polygon.
     * */
    public void polygonDrawInfo(int edges){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Draw Polygon");
        alert.setHeaderText(null);
        alert.setContentText("You have set the number of edges to " + edges +
                ". \nPlace " + edges + " points on the canvas in the order which the edges will be drawn.");
        alert.showAndWait();
    }

    /**
     * Alerts the user with an error dialogue stating they have entered an incorrect grid size value.
     * */
    public void gridSizeError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a positive integer between 1 and 1000.");
        alert.showAndWait();
    }
}
