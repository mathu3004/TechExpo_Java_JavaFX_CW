package JavaCoursework;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class Home {
    @FXML
    public ImageView IconDPD;
    @FXML
    public ImageView IconUPD;
    @FXML
    public ImageView IconVPD;
    @FXML
    public ImageView IconNEXT;
    @FXML
    public ImageView IconEXIT;
    @FXML
    private ImageView IconAPD;

    @FXML
    public void initialize() {
        // Load images for all ImageViews
        loadImage(IconAPD, "APD.png");
        loadImage(IconDPD, "DPD.png");
        loadImage(IconUPD, "UPD.png");
        loadImage(IconVPD, "VPD.png");
        loadImage(IconNEXT, "NEXT.png");
        loadImage(IconEXIT, "EXIT.png");
    }

    private void loadImage(ImageView imageView, String imagePath) {
        // Use the path relative to the resources folder
        Image image = new Image(getClass().getResourceAsStream("/" + imagePath));
        if (image != null) {
            imageView.setImage(image);
        } else {
            System.err.println("Image resource not found: " + imagePath);
        }
    }

    public void onClickBackHome(ActionEvent event) {
        try {
            // Load AddProjectDetails.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomeScreen.fxml"));
            Parent root = loader.load();

            // Get the Stage from the current scene and set new scene
            Stage stage = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            root.getStylesheets().add(getClass().getResource("JavaCoursework.css").toExternalForm());
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to load WelcomeScreen.fxml", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}