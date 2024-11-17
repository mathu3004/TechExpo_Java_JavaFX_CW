package JavaCoursework;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.collections.ObservableList;

import java.net.URL;
import java.util.ResourceBundle;

public class VisualizingAwardWinningProjects implements Initializable {

    @FXML
    private BarChart<Number, String> pointsBarChart;

    private ObservableList<Project> topProjects; // Use ObservableList directly

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Fetch selected top projects directly into ObservableList
        topProjects = fetchTopProjects();
        plotData(); // Plot data on the bar chart
    }

    private ObservableList<Project> fetchTopProjects() {
        // Fetch the award-winning projects from AwardWinningProjects
        return AwardWinningProjects.getSelectedProjects();
    }

    private void plotData() {
        XYChart.Series<Number, String> series = new XYChart.Series<>();
        series.setName("Points"); // Set series name for the bar chart
        // Clear any existing data in the chart
        pointsBarChart.getData().clear();
        // Use the provided order from selectedProjects without sorting
        for (int i = 0; i < Math.min(3, topProjects.size()); i++) {
            Project project = topProjects.get(i); // Get each top project
            String label = project.getProjectName() + " (" + project.getCountry() + ")"; // Generate label for the bar
            series.getData().add(new XYChart.Data<>(project.getJudgesPoints(),label)); // Add data to series
        }
        pointsBarChart.getData().add(series); // Add series to the bar chart
    }
}
