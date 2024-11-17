package JavaCoursework;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.io.*;
import java.util.List;

public class AddValidator {

    // Method to check if a given integer is a positive integer
    public static boolean isPositiveInteger(int value) {
        return value > 0;
    }

    public static String validateProjectID(int projectID, List<Project> projectsList) {
        if (!isPositiveInteger(projectID)) {
            return "Warning: The project ID must be a positive integer.";
        }
        if (isDuplicateProjectID(projectID, projectsList)) {
            return "Warning: The project ID already exists.";
        }
        return "Success: The project ID is available.";
    }

    static boolean isDuplicateProjectID(int projectID, List<Project> projectsList) {
        for (Project project : projectsList) {
            if (project.getProjectID() == projectID) {
                return true;
            }
        }
        return false;
    }

    public static String validateAndFormatProjectName(String projectNameText) {
        String projectName = projectNameText.trim();
        if (projectName.isEmpty()) {
            return null;
        }
        return Project.capitalizeFirstLetter(projectName);
    }

    public static String validateCategory(String category) {
        if (category.equals("AI") || category.equals("ML") || category.equals("RT")) {
            return category;
        }
        return null;
    }

    public static List<String> validateAndFormatTeamMembers(String membersText) {
        List<String> teamMembers = List.of(membersText.split(","));
        if (teamMembers.size() == 3) {
            return Project.formatTeamMembers(membersText.trim());
        }
        return null;
    }

    public static String validateAndFormatDescription(String descriptionText) {
        String description = descriptionText.trim();
        if (description.isEmpty()) {
            return null;
        }
        return Project.formatDescription(description);
    }

    public static String validateAndFormatCountry(String countryText) {
        String country = countryText.trim();
        if (country.isEmpty()) {
            return null;
        }
        return Project.capitalizeFirstLetter(country);
    }

    public static String validateAndFormatLogoUrl(String logoUrl, List<Project> projectsList) {
        if (isUrlEmpty(logoUrl) || isDuplicateLogoUrl(logoUrl, projectsList) || !isValidLogoUrl(logoUrl)) {
            return null;
        }
        return logoUrl;
    }

    static boolean isUrlEmpty(String url) {
        if (url.isEmpty()) {
            return true;
        }
        return false;
    }

    static boolean isDuplicateLogoUrl(String logoUrl, List<Project> projectsList) {
        for (Project project : projectsList) {
            if (project.getTeamLogoUrl().equals(logoUrl)) {
                return true;
            }
        }
        return false;
    }

    static boolean isValidLogoUrl(String logoUrl) {
        try {
            new Image(logoUrl);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    static void saveProjectsToFile(String filePath, List<Project> projectsList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writeProjects(writer, "AI Projects", projectsList, "AI");
            writeProjects(writer, "ML Projects", projectsList, "ML");
            writeProjects(writer, "RT Projects", projectsList, "RT");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeProjects(BufferedWriter writer, String categoryHeader, List<Project> projects, String category) throws IOException {
        writer.write("\n");
        writer.write("*********** " + categoryHeader + " ***********\n");
        writer.write("\n");
        for (Project project : projects) {
            if (project.getCategory().equals(category)) {
                writer.write("---Project Details---\n");
                writer.write("\n");
                writer.write("Project ID: " + project.getProjectID() + "\n");
                writer.write("Project Name: " + project.getProjectName() + "\n");
                writer.write("Category: " + project.getCategory() + "\n");
                writer.write("Team Members: " + project.getTeamMembersAsString() + "\n");
                writer.write("Brief Description: " + project.getDescription() + "\n");
                writer.write("Country: " + project.getCountry() + "\n");
                writer.write("Logo URL: " + project.getTeamLogoUrl() + "\n");
                writer.write("\n");
            }
        }
    }


    //For Delete Part
    // Method to validate if the project ID exists in the list
    public static boolean isProjectIDValid(List<Project> projectsList, int delProID) {
        // Check if the provided list is null
        if (projectsList == null) {
            throw new IllegalArgumentException("The list of projects cannot be null.");
        }
        // Check if the project ID is a positive integer
        if (delProID <= 0) {
            return false;
        }
        // Iterate through the list of projects to find a match
        for (Project project : projectsList) {
            // Check if the current project's ID matches the given ID
            if (project.getProjectID() == delProID) {
                return true; // Project ID found in the list
            }
        }
        // Project ID not found in the list
        return false;
    }

    // Method to validate project list
    public static boolean validateProjectList(List<Project> projects) {
        return projects != null && !projects.isEmpty();
    }

    // Bubble sort method to sort projects by projectID in ascending order
    static void bubbleSortProjects(List<Project> projects) {
        int n = projects.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Project project1 = projects.get(j);
                Project project2 = projects.get(j + 1);

                if (project1.getProjectID() > project2.getProjectID()) {
                    // Swap projects
                    projects.set(j, project2);
                    projects.set(j + 1, project1);
                }
            }
        }
    }

    // Method to validate category projects and return a boolean
    public static boolean validateCategoryProjects(List<Project> categoryProjects, String category) {
        // Check if the categoryProjects list is null
        if (categoryProjects == null) {
            return false; // Indicate that validation failed
        }

        // Check if the categoryProjects list is empty
        if (categoryProjects.isEmpty()) {
            return false; // Indicate that validation failed
        }

        // If all validations pass
        return true; // Indicate that validation passed
    }

    // Additional method to generate warning message
    public static String getValidationMessage(List<Project> categoryProjects, String category) {
        // Check if the categoryProjects list is null
        if (categoryProjects == null) {
            return "Warning: The category list for " + category + " is null.";
        }

        // Check if the categoryProjects list is empty
        if (categoryProjects.isEmpty()) {
            return "Warning: No projects found for category: " + category;
        }

        // If all validations pass
        return null;
    }

    // Method to validate project ratings
    public static boolean isValidRating(String rating) {
        return rating != null && rating.matches("[*]{1,5}");
    }

    // Method to sort projects by judgesPoints using bubble sort
    public static void bubbleSortProjects(ObservableList<Project> projects) {
        int n = projects.size();
        Project temp;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (projects.get(j).getJudgesPoints() < projects.get(j + 1).getJudgesPoints()) {
                    // Swap projects if current is less than next
                    temp = projects.get(j);
                    projects.set(j, projects.get(j + 1));
                    projects.set(j + 1, temp);
                }
            }
        }
    }

    // Method to validate that the selected projects are not null and the size is at least 3
    public static boolean validateSelectedProjects(ObservableList<Project> selectedProjects) {
        return selectedProjects != null && selectedProjects.size() >= 3;
    }

    // Method to validate that the selected projects are not null and the size is at least 3 for AwardWinningProjects
    public static boolean validateAwardWinningProjects(ObservableList<Project> selectedProjects) {
        return selectedProjects != null && selectedProjects.size() >= 3;
    }

    /*public static List<Project> readProjectsFromFile(String filePath) {
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
    }*/

    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
