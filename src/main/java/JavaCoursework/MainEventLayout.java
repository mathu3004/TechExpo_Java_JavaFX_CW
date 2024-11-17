package JavaCoursework;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainEventLayout implements Initializable  {

    @FXML
    public Button button;

    @FXML
    public StackPane contentArea;

    protected void loadFXML(String fxmlFileName, String alertMessage) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource(fxmlFileName));
        // Clear the current content
        contentArea.getChildren().clear();
        // Set the loaded FXML as the new content
        contentArea.getChildren().setAll(fxml);

        // Show the alert message after loading the FXML
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            //projectsList = WelcomeScreen.getProjectsFromFile("project_details.txt");
            // Loading the initial scene
            loadFXML("home.fxml", "Hello Sarah!!! Welcome to TechExpo!!!");
        } catch (IOException ex) {
            // Print stack trace for debugging if the initial scene cannot be loaded
            ex.printStackTrace();
        }
    }

    @FXML
    public void Home() throws IOException {
        loadFXML("Home.fxml", "Welcome to our Home page.");
    }

    @FXML
    public void APD() throws IOException {
        loadFXML("AddProjectDetails.fxml", "This is for adding project details. " +
                "You can add your project details here.");
    }

    @FXML
    public void DPD() throws IOException {
        loadFXML("DeleteProjectDetails.fxml", "This is for deleting project details. " +
                "You can delete your project details here.");
    }

    @FXML
    public void UPD() throws IOException {
        loadFXML("UpdateProjectDetails.fxml", "This is for updating project details. " +
                "You can update your project details here.");
    }

    @FXML
    public void VPD() throws IOException {
        // Check if no projects were loaded
        loadFXML("ViewProjectDetails.fxml",
                "You can view your project details in ascending order based on proID of all projects.");
    }

    @FXML
    public void Final() throws IOException {
        loadFXML("Start.fxml", "This will navigate you to the finals of the project. " +
                "Click that button for navigation.");
    }

    @FXML
    public void Exit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
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

    public static List<Project> readProjectsFromFile(String filePath) {
        List<Project> loadedProjects = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Project currentProject = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("*********** ")) {
                    continue; //skip it
                } else if (line.startsWith("---Project Details---")) {
                    // New project, create a new Project object
                    currentProject = new Project(0, "", "",
                            new ArrayList<>(), "", "", "");
                } else if (line.startsWith("Project ID: ")) {
                    if (currentProject != null) {
                        // Set the project ID of the current project
                        currentProject.setProjectID(Integer.parseInt(line.substring(11).trim()));
                    }
                } else if (line.startsWith("Project Name: ")) {
                    if (currentProject != null) {
                        currentProject.setProjectName(line.substring(13).trim());
                    }
                } else if (line.startsWith("Category: ")) {
                    if (currentProject != null) {
                        currentProject.setCategory(line.substring(9).trim());
                    }
                } else if (line.startsWith("Team Members: ")) {
                    if (currentProject != null) {
                        String[] teamMembers = line.substring(14).trim().split(",\\s*");
                        currentProject.setTeamMembers(List.of(teamMembers));
                    }
                } else if (line.startsWith("Brief Description: ")) {
                    if (currentProject != null) {
                        currentProject.setDescription(line.substring(18).trim());
                    }
                } else if (line.startsWith("Country: ")) {
                    if (currentProject != null) {
                        currentProject.setCountry(line.substring(9).trim());
                    }
                } else if (line.startsWith("Logo URL: ")) {
                    if (currentProject != null) {
                        currentProject.setTeamLogoUrl(line.substring(10).trim());
                        // Add the project to the list
                        loadedProjects.add(currentProject);
                        currentProject = null; // Reset currentProject to prepare for next project
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedProjects;
    }
}
