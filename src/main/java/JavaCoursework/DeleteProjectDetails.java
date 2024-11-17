package JavaCoursework;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class DeleteProjectDetails {

    @FXML
    TextField proID;

    public static String FILENAME = "project_details.txt";
    private static List<Project> projectsList = WelcomeScreen.getProjectsFromFile(FILENAME);

    @FXML
    public void deleteProject() {
        try {
            int delProID = parseProjectID();
            // Validate the project ID before attempting to delete
            if (AddValidator.isPositiveInteger(delProID)) {
                if (AddValidator.isProjectIDValid(projectsList, delProID)) {
                    boolean removed = removeProjectFromList(projectsList, delProID);
                    if (removed) {
                        updateProjectFile(projectsList);
                        showAlert(Alert.AlertType.INFORMATION, "Project Deleted",
                                "Project with ID " + delProID + " has been deleted.");
                    } else {
                        showAlert(Alert.AlertType.WARNING, "Deletion Error",
                                "Error occurred while deleting the project with ID " + delProID + ".");
                    }
                } else {
                    showAlert(Alert.AlertType.WARNING, "No Project Found",
                            "The project ID you entered was not found.");
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "Invalid Project ID",
                        "The project ID must be a positive integer.");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input",
                    "Please enter a valid integer for project ID.");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "File Error",
                    "There was an error accessing the project details file.");
        } finally {
            proID.clear();
        }
    }

    // Parse the project ID from the text field
    int parseProjectID() {
        return Integer.parseInt(proID.getText().trim());
    }

    // Remove the project with the given ID from the list
    boolean removeProjectFromList(List<Project> projectsList, int delProID) {
        for (Project project : projectsList) {
            if (project.getProjectID() == delProID) {
                projectsList.remove(project);
                return true;
            }
        }
        return false; // Indicate that the project was not found
    }

    // Update the project details file
    void updateProjectFile(List<Project> projectsList) throws IOException {
        AddValidator.saveProjectsToFile(FILENAME, projectsList);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header text
        alert.setContentText(message);
        alert.showAndWait(); // Wait for the user to close the alert
    }

    @FXML
    public void onclickDelete() {
        deleteProject(); // Call deleteProject() method when the button is clicked
    }
}
