package JavaCoursework;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Start {

    @FXML
    public ImageView NextIcon;

    private static final String FILE_PATH = "project_details.txt";

    // List to store categorized projects after reading from the file.
    static List<List<Project>> categories;
    private Stage stage;
    private Scene scene;
    private Parent root;

    // Constructor initializes categories by reading project details from file
    public Start() {
        // Initialize categories list
        categories = readProjectDetails();
    }

    public void initialize() {
        loadImage(NextIcon, "NEXT.png");
    }

    void loadImage(ImageView imageView, String imagePath) {
        // Use the path relative to the resources folder
        Image image = new Image(getClass().getResourceAsStream("/" + imagePath));
        if (image != null) {
            imageView.setImage(image);
        } else {
            System.err.println("Image resource not found: " + imagePath);
        }
    }

    // Method to read project details from a text file and categorize them
    List<List<Project>> readProjectDetails() {
        List<List<Project>> categories = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            List<Project> currentCategory = null;
            Project currentProject = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    if (line.startsWith("Project ID")) {
                        // Start of a new project
                        if (currentProject != null) {
                            // Add the last project to the category before starting a new one
                            if (currentCategory != null) {
                                currentCategory.add(currentProject);
                            }
                        }
                        // Start of a new project
                        currentProject = parseProject(line);
                        if (currentProject != null) {
                            String category = currentProject.getCategory();
                            int index = findCategoryIndex(categories, category);
                            if (index == -1) {
                                currentCategory = new ArrayList<>();
                                categories.add(currentCategory);
                            } else {
                                currentCategory = categories.get(index);
                            }
                        }
                    } else if (currentProject != null) {
                        // Continuation of project data
                        appendProjectData(currentProject, line);
                    }
                }
            }
            // Add the last project if the file ends without an empty line
            if (currentProject != null && currentCategory != null) {
                currentCategory.add(currentProject);
            }
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error reading project details from file: " + e.getMessage());
        }

        return categories;
    }

    // Parse a line from the file and create a SelectedProjects object
    private Project parseProject(String line) {
        Project project = null;
        try {
            String[] parts = line.split(":", 2);
            String key = parts[0].trim();
            String value = parts[1].trim();

            if (key.equals("Project ID")) {
                int projectID = Integer.parseInt(value);
                // Initialize new project with ID; other fields will be set in appendProjectData()
                project = new Project(projectID, "", "", new ArrayList<>(), "", "", "");
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to parse project details: " + e.getMessage());
        }
        return project;
    }

    // Append data from a line to an existing SelectedProjects object
    private void appendProjectData(Project project, String line) {
        String[] parts = line.split(":", 2);
        if (parts.length == 2) {
            String key = parts[0].trim();
            String value = parts[1].trim();

            switch (key) {
                case "Project Name":
                    project.setProjectName(value);
                    break;
                case "Category":
                    project.setCategory(value);
                    break;
                case "Team Members":
                    String[] teamMembersArray = value.split(", ");
                    List<String> teamMembersList = new ArrayList<>();
                    for (String member : teamMembersArray) {
                        teamMembersList.add(member);
                    }
                    project.setTeamMembers(teamMembersList);
                    break;
                case "Country":
                    project.setCountry(value);
                    break;
                case "Brief Description":
                    project.setDescription(value);
                    break;
                case "Logo URL":
                    project.setTeamLogoUrl(value);
                    break;
                default:
                    break;
            }
        }
    }
    // Find the index of a category in the categories list
    private int findCategoryIndex(List<List<Project>> categories, String category) {
        for (int i = 0; i < categories.size(); i++) {
            List<Project> categoryList = categories.get(i);
            if (!categoryList.isEmpty() && categoryList.get(0).getCategory().equals(category)) {
                return i;
            }
        }
        return -1;
    }
    // Method to get the list of categorized projects
    public static List<List<Project>> getCategories() {
        return categories;
    }

    // Handle the start event to check categories and show a confirmation dialog
    @FXML
    public void onClickStart(ActionEvent event) {
        // Check if ML, AI, RT categories are empty
        if (!isCategoryNotEmpty("ML") || !isCategoryNotEmpty("AI") || !isCategoryNotEmpty("RT")) {
            showAlert(Alert.AlertType.WARNING, "Empty Categories", "Some categories are empty. " +
                    "Please make sure all these categories have at least one project.");
        } else {
            showConfirmationDialog(event);
        }
    }

    // Check if a specific category is not empty
    static boolean isCategoryNotEmpty(String categoryName) {
        for (List<Project> category : categories) {
            if (!category.isEmpty() && category.get(0).getCategory().equals(categoryName)) {
                return true;
            }
        }
        return false;
    }

    // Show a confirmation dialog before proceeding to the final event layout
    private void showConfirmationDialog(ActionEvent event) {
        // Alert that the user cannot go back once the event starts
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("You cannot go back once the event starts.");
        confirmationAlert.setContentText("Are you sure you want to proceed?");
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Load FinalEventLayout.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FinalEventLayout.fxml"));
                    root = loader.load();
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root, 1100,630);
                    root.getStylesheets().add(getClass().getResource("JavaCoursework.css").toExternalForm());
                    // Get the current stage
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to load FinalEventLayout.fxml: " + e.getMessage());
                }
            }
        });
    }
    // Method to display an alert dialog with specified type, title, and message
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
