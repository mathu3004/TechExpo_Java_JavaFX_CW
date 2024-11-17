package JavaCoursework;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class FinalEventLayout {

    @FXML
    private StackPane contentArea;

    public void initialize() {
        try {
            // Load the initial scene
            loadFXML("FinalEventStart.fxml", "Welcome to the Finals of the TechExpo!!!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadFXML(String fxmlFileName, String alertMessage) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource(fxmlFileName));
        contentArea.getChildren().clear();
        contentArea.getChildren().add(fxml);
        // Show the alert message
        showAlert(Alert.AlertType.INFORMATION, "Information", alertMessage);
    }

    @FXML
    public void FINALS() throws IOException {
        loadFXML("FinalEventStart.fxml", "Are you ready to start the finals of the TechExpo???");
    }

    public void VPD() throws IOException {
        loadFXML("ViewProjectDetails.fxml",
                "You can view your project details in ascending order based on proID of all projects.");
    }

    @FXML
    public void RSS() throws IOException {
        RandomSpotlightSelect.onClickRandomSelection();
        if (!AddValidator.validateSelectedProjects(RandomSpotlightSelect.getSelectedProjects())) {
            showAlert(Alert.AlertType.WARNING, "Data Incomplete", "Please ensure at least 3 projects are available.");
            return;
        }
        loadFXML("AwardWinningProjects.fxml", "For each category, " +
                "Click on the top button and judge can give the points for the selected project using '*'.");
    }

    @FXML
    public void VAP() throws IOException {
        if (!AddValidator.validateAwardWinningProjects(AwardWinningProjects.getSelectedProjects())) {
            showAlert(Alert.AlertType.WARNING, "Data Incomplete", "Please ensure at least 3 projects are available.");
            return;
        }
        loadFXML("VisualizingAwardWinningProjects.fxml",
                "Here, the awarded projects will be visualized in a bar chart for graphical view.");
    }

    @FXML
    public void END() throws IOException {
        if (AwardWinningProjects.getSelectedProjects() == null || AwardWinningProjects.getSelectedProjects().size() < 3) {
            showAlert(Alert.AlertType.WARNING, "Incomplete Event!", "Please complete the events.");
            return;
        }
        loadFXML("ThankYou.fxml", "Thank you for participating in TechExpo!!!");
    }

    @FXML
    public void Exit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Press OK to exit or Cancel to stay.");

        // Customizing the buttons in the alert dialog
        ButtonType okButton = new ButtonType("OK");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton, cancelButton);

        // Handling user's choice
        alert.showAndWait().ifPresent(response -> {
            if (response == okButton) {
                // Close the application
                Stage stage = (Stage) contentArea.getScene().getWindow();
                stage.close();
            }
        });
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
