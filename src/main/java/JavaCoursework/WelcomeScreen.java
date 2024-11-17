package JavaCoursework;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class WelcomeScreen implements Initializable {

    private static List<Project> projectsList = new ArrayList<>();
    private final String FILENAME = "project_details.txt";
    public ImageView LoveIcon;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectsList = MainEventLayout.readProjectsFromFile(FILENAME);
        loadImage(LoveIcon, "THANKYOU.png");
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

    @FXML
    public void onClickSarah(ActionEvent event) {
        // Define the correct password
        String correctPassword = "sarah123";

        // Create a TextInputDialog for password input
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Password Required");
        dialog.setHeaderText("Enter Password for Sarah");
        dialog.setContentText("Password:");

        // Show the dialog and capture the user input
        Optional<String> result = dialog.showAndWait();

        // Check if the password is correct
        if (result.isPresent() && result.get().equals(correctPassword)) {
            try {
                // Load MainEventsLayout.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainEventsLayout.fxml"));
                Parent root = loader.load();

                // Get the Stage from the current scene and set new scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root, 980, 700));
                root.getStylesheets().add(getClass().getResource("JavaCoursework.css").toExternalForm());
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Show an error alert if the password is incorrect
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Password");
            alert.setHeaderText("Access Denied");
            alert.setContentText("The password you entered is incorrect.");
            alert.showAndWait();
        }
    }


    @FXML
    public void onClickParticipant(ActionEvent event) {
        try {
            loadFXML("AddProjectDetails.fxml", "Hello Participant!! You can add your project details", event);
        } catch (IOException e) {
            showAlert("Error", "Failed to load AddProjectDetails.fxml", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void loadFXML(String fxmlFileName, String alertMessage, ActionEvent event) throws IOException {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
        Parent fxml = loader.load();

        // Get the Stage from the current scene and set new scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(fxml));
        fxml.getStylesheets().add(getClass().getResource("JavaCoursework.css").toExternalForm());

        // Show the alert message after loading the FXML
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static List<Project> getProjectsFromFile(String filePath) {
        return projectsList;
    }
}
