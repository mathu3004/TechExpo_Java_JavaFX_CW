package JavaCoursework;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomSpotlightSelect {

    static List<List<Project>> categories; // List to hold categories of projects
    // ObservableList to hold selected projects
    private static ObservableList<Project> selectedProjects = FXCollections.observableArrayList();

    public static ObservableList<Project> getSelectedProjects() {
        return selectedProjects;
    }

    public static void onClickRandomSelection() {
        categories = Start.getCategories();
        selectedProjects.clear(); // Clear the list before adding new projects
        // Select random project for each category
        selectRandomProjectForCategory("AI", selectedProjects);
        selectRandomProjectForCategory("ML", selectedProjects);
        selectRandomProjectForCategory("RT", selectedProjects);
    }

    // Method to select a random project for a given category and add it to selectedProjects list
    private static void selectRandomProjectForCategory(String category, ObservableList<Project> selectedProjects) {
        List<Project> categoryProjects = getCategoryProjects(category); // Get projects for the specified category (Temporary List to store)
        // Check if categoryProjects is not empty and select a random project
        if  (AddValidator.validateCategoryProjects(categoryProjects, category)) {
            int randomIndex = generateRandomIndex(categoryProjects.size());
            Project selectedProject = categoryProjects.get(randomIndex);
            selectedProjects.add(selectedProject); // Add the selected project to selectedProjects list
        }else {
            // Show warning message based on the validation result
            String message = AddValidator.getValidationMessage(categoryProjects, category);
            if (message != null) {
                showAlert(Alert.AlertType.WARNING, "Warning", message);
            }
        }
    }

    // Method to fetch projects belonging to a specific category
    static List<Project> getCategoryProjects(String category) {
        List<Project> categoryProjects = new ArrayList<>();
        for (List<Project> categoryList : categories) {
            for (Project project : categoryList) {
                if (project.getCategory().equals(category)) {
                    categoryProjects.add(project);
                }
            }
        }
        return categoryProjects;
    }

    // Method to generate a random index within a given range
    static int generateRandomIndex(int upperBound) {
        Random random = new Random();
        return random.nextInt(upperBound);
    }

    // Method to display an alert with specified type, title, and message
    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}