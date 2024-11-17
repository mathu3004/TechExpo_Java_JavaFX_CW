package JavaCoursework;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddProjectDetails implements Initializable {

    @FXML
    private TextField proID;

    @FXML
    private TextField proName;

    @FXML
    private ChoiceBox<String> proCategory;

    @FXML
    private TextField proMembers;

    @FXML
    private TextField proCountry;

    @FXML
    private TextArea proDescription;

    @FXML
    private TextArea logoTextView;

    @FXML
    private ImageView logoImageView;

    private static final String FILENAME = "project_details.txt";
    private static List<Project> projectsList = WelcomeScreen.getProjectsFromFile(FILENAME);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        proCategory.getItems().addAll("AI", "ML", "RT");
    }

    @FXML
    private void onClickCheck() {
        try {
            int projectID = Integer.parseInt(proID.getText().trim());
            String result = AddValidator.validateProjectID(projectID, projectsList);
            if (result.contains("Error") || result.contains("Warning")) {
                showAlert(Alert.AlertType.ERROR, "Validation", result);
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Validation", result);
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid project ID. Please enter a valid integer.");
        }
    }

    @FXML
    private void onClickAdd() {
        String result = addProjectDetails();
        if (result.contains("Error") || result.contains("Warning")) {
            showAlert(Alert.AlertType.ERROR, "Validation", result);
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Validation", result);
        }
    }

    private String addProjectDetails() {
        try {
            // Validate Project ID
            int projectID = parseProjectID();
            if (projectID == -1) {
                return "Error: Invalid project ID.";
            } else if (AddValidator.isDuplicateProjectID(projectID, projectsList)) {
                return "Warning: The project ID already exists.";
            }

            // Validate Project Name
            String projectName = AddValidator.validateAndFormatProjectName(proName.getText().trim());
            if (projectName == null) {
                return "Error: Project name cannot be empty.";
            }

            // Validate Category
            String category = proCategory.getValue();
            if (category == null || category.trim().isEmpty()) {
                return "Error: Project category cannot be empty.";
            } else {
                category = AddValidator.validateCategory(category.trim());
                if (category == null) {
                    return "Error: Invalid project category.";
                }
            }

            // Validate Country
            String country = AddValidator.validateAndFormatCountry(proCountry.getText().trim());
            if (country == null) {
                return "Error: Country cannot be empty.";
            }

            // Validate Team Members
            List<String> teamMembers = AddValidator.validateAndFormatTeamMembers(proMembers.getText().trim());
            if (teamMembers == null) {
                return "Error: Please enter exactly three team members separated by commas.";
            }

            // Validate Description
            String description = AddValidator.validateAndFormatDescription(proDescription.getText().trim());
            if (description == null) {
                return "Error: Project description cannot be empty.";
            }

            // Validate Logo URL
            String logoUrl = AddValidator.validateAndFormatLogoUrl(logoTextView.getText().trim(), projectsList);
            if (logoUrl == null) {
                return "Error: Invalid logo URL.";
            }

            // If all validations pass, add the project
            Project newProject = new Project(projectID, projectName, category, teamMembers, description, country, logoUrl);
            projectsList.add(newProject);
            AddValidator.saveProjectsToFile(FILENAME, projectsList);
            clearFields();
            return "Success: Project details have been added successfully.";

        } catch (NumberFormatException e) {
            return "Error: Invalid project ID. Please enter a valid integer.";
        }
    }

    private int parseProjectID() {
        try {
            int projectID = Integer.parseInt(proID.getText().trim());
            if (projectID > 0) {
                return projectID;
            }
        } catch (NumberFormatException e) {
            return -1;
        }
        return -1;
    }

    @FXML
    private void onClickBrowse() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Logo Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();
            if (!AddValidator.isDuplicateLogoUrl(imagePath, projectsList)) {
                logoTextView.setText(imagePath);
                logoImageView.setImage(new Image(imagePath));
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "The logo URL must be unique for each project.");
            }
        }
    }

    static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        proID.clear();
        proName.clear();
        proCategory.setValue(null);
        proMembers.clear();
        proDescription.clear();
        proCountry.clear();
        logoTextView.clear();
        logoImageView.setImage(null);
    }

    public void onClickBack(ActionEvent event) { try {
        // Load AddProjectDetails.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomeScreen.fxml"));
        Parent root = loader.load();
        // Get the Stage from the current scene and set new scene
        Stage stage = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        root.getStylesheets().add(getClass().getResource("JavaCoursework.css").toExternalForm());
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}
