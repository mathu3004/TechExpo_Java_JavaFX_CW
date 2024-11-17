package JavaCoursework;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;

public class AwardWinningProjects {

    @FXML
    TableView<Project> projectData;

    @FXML
    TableColumn<Project, Integer> proID;

    @FXML
    TableColumn<Project, String> proName;

    @FXML
    TableColumn<Project, String> proCategory;

    @FXML
    TableColumn<Project, String> proMembers;

    @FXML
    TableColumn<Project, String> proCountry;

    @FXML
    TableColumn<Project, String> proDescription;

    @FXML
    TableColumn<Project, Integer> judgesPoints;

    @FXML
    TableColumn<Project, String> place; // TableColumn for places

    private static ObservableList<Project> selectedProjects = FXCollections.observableArrayList();

    public AwardWinningProjects() {
        // Clear the list before adding new projects
        selectedProjects.clear();
        // Fetch the randomly selected projects from RandomSpotlightSelection
        //used as intermediate between selectedProjects and RSS
        ObservableList<Project> selected = RandomSpotlightSelect.getSelectedProjects();

        // Convert SelectedProjects to Projects and add to selectedProjects list
        for (Project project : selected) {
            Project newProject = new Project(
                    project.getProjectID(),
                    project.getProjectName(),
                    project.getCategory(),
                    project.getTeamMembers(),
                    project.getDescription(),
                    project.getCountry()
            );
            selectedProjects.add(newProject);
        }
    }

    public static ObservableList<Project> getSelectedProjects() {
        return selectedProjects;
    }

    @FXML
    public void initialize() {
        setupTableColumns();
        projectData.setItems(selectedProjects);
    }

    private void setupTableColumns() {
        projectData.setVisible(false);
        proID.setCellValueFactory(new PropertyValueFactory<>("projectID"));
        proName.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        proCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        proMembers.setCellValueFactory(new PropertyValueFactory<>("teamMembersAsString"));
        proCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        proDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        judgesPoints.setCellValueFactory(new PropertyValueFactory<>("judgesPoints"));
        place.setCellValueFactory(cellData -> new SimpleStringProperty(getPlaceString(selectedProjects.indexOf(cellData.getValue()))));
    }

    String getPlaceString(int index) {
        return switch (index) {
            case 0 -> "1st";
            case 1 -> "2nd";
            case 2 -> "3rd";
            default -> "";
        };
    }

    @FXML
    public void onClickJudgeScoring() {
        scoreProjects();
        determineWinners(); //Call the function to determine winners
        projectData.setVisible(true); // Show TableView after scoring is complete
    }

    void scoreProjects() {
        for (Project project : selectedProjects) {
            int totalScore = getJudgeScore(project);
            project.setJudgesPoints(totalScore);
        }
        // Update TableView
        projectData.setItems(selectedProjects);
        projectData.refresh(); // Refresh TableView to reflect updated values
    }

    private int getJudgeScore(Project project) {
        int totalScore = 0;
        for (int i = 1; i <= 4; i++) {
            boolean validInput = false;
            while (!validInput) {
                TextInputDialog dialog = createScoreDialog(i, project);
                dialog.showAndWait();
                String rating = dialog.getEditor().getText();
                if (AddValidator.isValidRating(rating)) {
                    totalScore += rating.length();
                    validInput = true;
                } else {
                    showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please enter between 1 and 5 '*' characters.");
                }
            }
        }
        return totalScore;
    }

    private TextInputDialog createScoreDialog(int judgeNumber, Project project) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Judge Scoring");
        dialog.setHeaderText("Judge " + judgeNumber + ": Scoring for " + project.getCategory() + " Project with ProID - " + project.getProjectID()
                + "  & ProName - " + project.getProjectName());
        dialog.setContentText("Enter rating using stars only (1-5):");
        return dialog;
    }

    void determineWinners() {
        AddValidator.bubbleSortProjects(selectedProjects);
        String resultMessage = getResultMessage();
        showAlert(Alert.AlertType.INFORMATION, "Winners", resultMessage);
    }

    private String getResultMessage() {
        StringBuilder resultMessage = new StringBuilder("Overall Ranking:\n");
        if (!selectedProjects.isEmpty()) {
            resultMessage.append("1st Place: ").append(selectedProjects.get(0).getCategory()).append(" with ").
                    append(selectedProjects.get(0).getJudgesPoints()).append(" points\n");
        }
        if (selectedProjects.size() > 1) {
            resultMessage.append("2nd Place: ").append(selectedProjects.get(1).getCategory()).append(" with ").
                    append(selectedProjects.get(1).getJudgesPoints()).append(" points\n");
        }
        if (selectedProjects.size() > 2) {
            resultMessage.append("3rd Place: ").append(selectedProjects.get(2).getCategory()).append(" with ").
                    append(selectedProjects.get(2).getJudgesPoints()).append(" points\n");
        }
        return resultMessage.toString();
    }

    // Utility method to show alert with specified type, title, and message
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}