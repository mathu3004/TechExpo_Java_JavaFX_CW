package JavaCoursework;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateProjectDetails implements Initializable {

    @FXML
    public TextArea updateLogo;
    @FXML
    public TextArea updateMembers;
    @FXML
    public TextField updateCountry;
    @FXML
    public TextField updateCategory;
    @FXML
    public TextField updateName;
    @FXML
    public Label alertUpdate;

    @FXML
    TextField proID;

    @FXML
    TextArea updateDetails;

    @FXML
    ImageView logoImageView;

    private static final String FILENAME = "project_details.txt";
    private static List<Project> projectsList = WelcomeScreen.getProjectsFromFile(FILENAME);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void onClickCheck() {
        // Parse project ID from the text field
        int projectID = parseProjectID();
        if (projectID == -1) {clearFields();
        return;}
        // Find the project by ID in the projects list
        Project project = findProjectByID(projectID, projectsList);
        if (project == null) {
            // Show error if project not found
            showAlert(Alert.AlertType.ERROR, "Error", "Project with ID " + projectID + " is not found.");
            clearFields();
            return;
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Project found with ID " + projectID + ".");
            alertUpdate.setText("Now, you can update the fields you wish to update!!!");
            updateFieldsWithProjectDetails(project);
        }
    }

    @FXML
    public void onClickUpdate() {
        int projectID = parseProjectID();
        if (projectID == -1) return;

        Project projectToUpdate = findProjectByID(projectID, projectsList);
        if (projectToUpdate == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Project with ID " + projectID + " is not found.");
            clearFields();
            return;
        }

        // Validate and update project details
        String projectName = AddValidator.validateAndFormatProjectName(updateName.getText());
        if (projectName == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Project name cannot be empty.");
            return;
        }
        projectToUpdate.setProjectName(projectName);

        String category = AddValidator.validateCategory(updateCategory.getText().trim().toUpperCase());
        if (category == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid category! Please enter AI, ML, or RT.");
            return;
        }
        projectToUpdate.setCategory(category);

        String country = AddValidator.validateAndFormatCountry(updateCountry.getText());
        if (country == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Country cannot be empty.");
            return;
        }
        projectToUpdate.setCountry(country);

        String description = AddValidator.validateAndFormatDescription(updateDetails.getText());
        if (description == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Description cannot be empty.");
            return;
        }
        projectToUpdate.setDescription(description);

        List<String> teamMembers = AddValidator.validateAndFormatTeamMembers(updateMembers.getText());
        if (teamMembers == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter exactly three team members separated by commas.");
            return;
        }
        projectToUpdate.setTeamMembers(teamMembers);

        // Check logo URL uniqueness only if it is being updated
        if (!AddValidator.isUrlEmpty(updateLogo.getText().trim())) {
            String newLogoUrl = updateLogo.getText().trim();
            for (Project p : projectsList) {
                if (p.getProjectID() != projectToUpdate.getProjectID() && p.getTeamLogoUrl().equals(newLogoUrl)) {
                    showAlert(Alert.AlertType.ERROR, "Error", "The logo URL must be unique for each project.");
                    return;
                }
            }
            projectToUpdate.setTeamLogoUrl(newLogoUrl);
            Image image = new Image(newLogoUrl);
            logoImageView.setImage(image);
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid logo URL.");
            return;
        }

        // Save the updated projects list to the file
        saveUpdatedProjectsList();
        showAlert(Alert.AlertType.INFORMATION, "Success", "Project details have been updated successfully.");
        clearFields();
    }

    @FXML
    public void onClickBrowse() {
        // Open a file chooser to select an image file for the logo
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Logo Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString(); // Convert file path to URI
            // Check if the selected image path is already used by another project
            if (AddValidator.isDuplicateLogoUrl(imagePath, projectsList)) {
                showAlert(Alert.AlertType.ERROR, "Error", "The logo URL must be unique for each project.");
                return;
            }
            updateLogo.setText(imagePath); // Set URI in text area
            Image image = new Image(imagePath); // Load image from URI
            logoImageView.setImage(image); // Display image in ImageView
        }
    }

    private int parseProjectID() {
        try {
            int id = Integer.parseInt(proID.getText().trim());
            if (!AddValidator.isPositiveInteger(id)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Project ID must be a positive integer.");
                return -1;
            }
            return id;
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid project ID. Please enter a valid integer.");
            return -1;
        }
    }

    private Project findProjectByID(int projectID, List<Project> projects) {
        for (Project project : projects) {
            if (project.getProjectID() == projectID) {
                return project; // Return project if ID matches
            }
        }
        return null; // Project not found
    }

    private void updateFieldsWithProjectDetails(Project project) {
        updateName.setText(project.getProjectName());
        updateCategory.setText(project.getCategory());
        updateCountry.setText(project.getCountry());
        updateDetails.setText(project.getDescription());
        updateMembers.setText(String.join(", ", project.getTeamMembers()));
        updateLogo.setText(project.getTeamLogoUrl());
        logoImageView.setImage(new Image(project.getTeamLogoUrl()));
    }

    private void saveUpdatedProjectsList() {
        AddValidator.saveProjectsToFile(FILENAME, projectsList);
    }

    private void clearFields() {
        proID.clear();
        updateName.clear();
        updateCategory.clear();
        updateCountry.clear();
        updateDetails.clear();
        updateMembers.clear();
        updateLogo.clear();
        logoImageView.setImage(null);
        alertUpdate.setText("");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
