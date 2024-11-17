package JavaCoursework;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class ViewProjectDetails {

    @FXML
    public TableView<Project> projectData;
    @FXML
    public TableColumn<Project, Integer> proID;
    @FXML
    public TableColumn<Project, String> proName;
    @FXML
    public TableColumn<Project, String> proCategory;
    @FXML
    public TableColumn<Project, String> proMembers;
    @FXML
    public TableColumn<Project, String> proCountry;
    @FXML
    public TableColumn<Project, String> proDescription;
    @FXML
    public TableColumn<Project, Image> teamLogo; // Changed to Image type

    private static final String FILENAME = "project_details.txt";
    private static List<Project> projectsList;

    @FXML
    private void initialize() {
        projectsList = WelcomeScreen.getProjectsFromFile(FILENAME);
        // Check if no projects were loaded
        if ((!AddValidator.validateProjectList(projectsList))) {
            showAlert(Alert.AlertType.WARNING, "No Projects Found",
                    "No projects were found in the file. Please add more projects.");
            return;
        }
        // Call the method to Sort projects by projectID
        AddValidator.bubbleSortProjects(projectsList);
        initializeTableColumns();
        populateTableWithProjects();
    }

    private void initializeTableColumns() {
        // Initialize table columns
        proID.setCellValueFactory(new PropertyValueFactory<>("projectID"));
        proName.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        proCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        proMembers.setCellValueFactory(new PropertyValueFactory<>("teamMembersAsString"));
        proDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        proCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        teamLogo.setCellValueFactory(new PropertyValueFactory<>("teamLogo")); // Use Image property
        // Set cell factory for teamLogo column to display images
        teamLogo.setCellFactory(column -> new ImageViewTableCell<>());
    }

    private void populateTableWithProjects() {
        // Clear any existing items
        projectData.getItems().clear();
        // Populate table with projects
        projectData.getItems().addAll(projectsList);
        // Load team logos for each project
        projectsList.forEach(project -> project.setTeamLogo(project.loadTeamLogo()));
    }


    // Custom cell factory for displaying images in a TableColumn
    static class ImageViewTableCell<S> extends javafx.scene.control.TableCell<S, Image> {
        private final ImageView imageView = new ImageView();
        @Override
        protected void updateItem(Image item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null); // Clear cell if empty or item is null
            } else {
                imageView.setImage(item); // Set image to display in cell
                imageView.setFitWidth(50); // Set image width (adjust as needed)
                imageView.setPreserveRatio(true); // Preserve image aspect ratio
                setGraphic(imageView); // Set ImageView as cell's graphic
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}