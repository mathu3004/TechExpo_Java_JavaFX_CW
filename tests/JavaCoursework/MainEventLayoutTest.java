package JavaCoursework;

import javafx.application.Application;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainEventLayoutTest {

    private static final String TEST_FILE_PATH = "test_project_details.txt";

    @BeforeAll
    public static void initJavaFX() {
        // Ensure JavaFX is initialized
        Application.launch(TechExpoApplication.class);
    }

    @Test
    public void testReadProjectsFromFile() {
        // Arrange
        String testData = """
                ---Project Details---
                Project ID: 1
                Project Name: Project A
                Category: ML
                Team Members: Alice, Bob
                Brief Description: A machine learning project
                Country: USA
                Logo URL: http://example.com/logoA.png
                ***********
                ---Project Details---
                Project ID: 2
                Project Name: Project B
                Category: AI
                Team Members: Charlie, Dave
                Brief Description: An artificial intelligence project
                Country: Canada
                Logo URL: http://example.com/logoB.png
                """;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_PATH))) {
            writer.write(testData);
        } catch (IOException e) {
            fail("Failed to write test data to file: " + e.getMessage());
        }

        // Act
        List<Project> projects = MainEventLayout.readProjectsFromFile(TEST_FILE_PATH);

        // Assert
        assertNotNull(projects, "Projects list should not be null");
        assertEquals(2, projects.size(), "There should be 2 projects");

        Project project1 = projects.get(0);
        assertEquals(1, project1.getProjectID(), "Project ID should be 1");
        assertEquals("Project A", project1.getProjectName(), "Project Name should be Project A");
        assertEquals("ML", project1.getCategory(), "Category should be ML");
        assertEquals(List.of("Alice", "Bob"), project1.getTeamMembers(), "Team Members should be Alice, Bob");
        assertEquals("A machine learning project", project1.getDescription(), "Description should be correct");
        assertEquals("USA", project1.getCountry(), "Country should be USA");
        assertEquals("http://example.com/logoA.png", project1.getTeamLogoUrl(), "Logo URL should be correct");

        Project project2 = projects.get(1);
        assertEquals(2, project2.getProjectID(), "Project ID should be 2");
        assertEquals("Project B", project2.getProjectName(), "Project Name should be Project B");
        assertEquals("AI", project2.getCategory(), "Category should be AI");
        assertEquals(List.of("Charlie", "Dave"), project2.getTeamMembers(), "Team Members should be Charlie, Dave");
        assertEquals("An artificial intelligence project", project2.getDescription(), "Description should be correct");
        assertEquals("Canada", project2.getCountry(), "Country should be Canada");
        assertEquals("http://example.com/logoB.png", project2.getTeamLogoUrl(), "Logo URL should be correct");

        // Cleanup
        new java.io.File(TEST_FILE_PATH).delete();
    }
}
