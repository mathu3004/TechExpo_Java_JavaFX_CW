package JavaCoursework;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AddValidatorTest {

    @Test
    public void testValidateProjectID() {
        List<Project> projectsList = new ArrayList<>();
        projectsList.add(new Project(1, "Test Project", "AI", List.of("Member1", "Member2", "Member3"), "Description", "Country", "http://example.com/logo.png"));

        assertEquals("Warning: The project ID must be a positive integer.", AddValidator.validateProjectID(0, projectsList));
        assertEquals("Warning: The project ID already exists.", AddValidator.validateProjectID(1, projectsList));
        assertEquals("Success: The project ID is available.", AddValidator.validateProjectID(2, projectsList));
    }

    @Test
    public void testIsDuplicateProjectID() {
        List<Project> projectsList = new ArrayList<>();
        projectsList.add(new Project(1, "Test Project", "AI", List.of("Member1", "Member2", "Member3"), "Description", "Country", "http://example.com/logo.png"));

        assertTrue(AddValidator.isDuplicateProjectID(1, projectsList));
        assertFalse(AddValidator.isDuplicateProjectID(2, projectsList));
    }

    @Test
    public void testValidateAndFormatProjectName() {
        assertNull(AddValidator.validateAndFormatProjectName(""));
        assertEquals("Test project", AddValidator.validateAndFormatProjectName("test project"));
    }

    @Test
    public void testValidateCategory() {
        assertNull(AddValidator.validateCategory("InvalidCategory"));
        assertEquals("AI", AddValidator.validateCategory("AI"));
        assertEquals("ML", AddValidator.validateCategory("ML"));
        assertEquals("RT", AddValidator.validateCategory("RT"));
    }

    @Test
    public void testValidateAndFormatTeamMembers() {
        assertNull(AddValidator.validateAndFormatTeamMembers("Member1,Member2"));
        assertEquals(List.of("Member1", "Member2", "Member3"), AddValidator.validateAndFormatTeamMembers("Member1,Member2,Member3"));
    }

    @Test
    public void testValidateAndFormatDescription() {
        assertNull(AddValidator.validateAndFormatDescription(""));
        assertEquals("This is a project description.", AddValidator.validateAndFormatDescription("this is a project description."));
    }

    @Test
    public void testValidateAndFormatCountry() {
        assertNull(AddValidator.validateAndFormatCountry(""));
        assertEquals("Country", AddValidator.validateAndFormatCountry("country"));
    }

    @Test
    public void testIsDuplicateLogoUrl() {
        List<Project> projectsList = new ArrayList<>();
        projectsList.add(new Project(1, "Test Project", "AI", List.of("Member1", "Member2", "Member3"), "Description", "Country", "http://example.com/logo.png"));

        assertTrue(AddValidator.isDuplicateLogoUrl("http://example.com/logo.png", projectsList));
        assertFalse(AddValidator.isDuplicateLogoUrl("http://example.com/newlogo.png", projectsList));
    }


    @Test
    public void testSaveProjectsToFile() {
        String filePath = "testProjects.txt";
        List<Project> projectsList = new ArrayList<>();
        projectsList.add(new Project(1, "AI Project", "AI", List.of("Member1", "Member2", "Member3"), "AI Description", "CountryA", "http://example.com/ai_logo.png"));
        projectsList.add(new Project(2, "ML Project", "ML", List.of("Member4", "Member5", "Member6"), "ML Description", "CountryB", "http://example.com/ml_logo.png"));
        projectsList.add(new Project(3, "RT Project", "RT", List.of("Member7", "Member8", "Member9"), "RT Description", "CountryC", "http://example.com/rt_logo.png"));

        AddValidator.saveProjectsToFile(filePath, projectsList);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean foundAI = false, foundML = false, foundRT = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("AI Projects")) {
                    foundAI = true;
                }
                if (line.contains("ML Projects")) {
                    foundML = true;
                }
                if (line.contains("RT Projects")) {
                    foundRT = true;
                }
            }
            assertTrue(foundAI, "AI Projects header not found in file");
            assertTrue(foundML, "ML Projects header not found in file");
            assertTrue(foundRT, "RT Projects header not found in file");
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException occurred while reading the file");
        }
    }

    @Test
    public void testSaveProjectsToTextFile() throws IOException {
        // Setup
        String testFilePath = "test_projects.txt";
        List<Project> projects = new ArrayList<>();

        // Create sample projects
        projects.add(new Project(1, "AI Project 1", "AI", List.of("Member A", "Member B", "Member C"), "Description 1", "Country A", null));
        projects.add(new Project(2, "ML Project 1", "ML", List.of("Member A", "Member B", "Member C"), "Description 2", "Country B", null));
        projects.add(new Project(3, "RT Project 1", "RT", List.of("Member A", "Member B", "Member C"), "Description 3", "Country C", null));

        // Save projects to file
        AddValidator.saveProjectsToFile(testFilePath, projects);

        // Read the file and verify content
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        }

        String expectedContent =
                "\n*********** AI Projects ***********\n\n" +
                        "---Project Details---\n\n" +
                        "Project ID: 1\n" +
                        "Project Name: AI Project 1\n" +
                        "Category: AI\n" +
                        "Team Members: Member A, Member B, Member C\n" +
                        "Brief Description: Description 1\n" +
                        "Country: Country A\n" +
                        "Logo URL: null\n\n" +
                        "\n*********** ML Projects ***********\n\n" +
                        "---Project Details---\n\n" +
                        "Project ID: 2\n" +
                        "Project Name: ML Project 1\n" +
                        "Category: ML\n" +
                        "Team Members: Member A, Member B, Member C\n" +
                        "Brief Description: Description 2\n" +
                        "Country: Country B\n" +
                        "Logo URL: null\n\n" +
                        "\n*********** RT Projects ***********\n\n" +
                        "---Project Details---\n\n" +
                        "Project ID: 3\n" +
                        "Project Name: RT Project 1\n" +
                        "Category: RT\n" +
                        "Team Members: Member A, Member B, Member C\n" +
                        "Brief Description: Description 3\n" +
                        "Country: Country C\n" +
                        "Logo URL: null\n\n";

        // Print differences for debugging
        String actualContent = fileContent.toString();
        if (!expectedContent.equals(actualContent)) {
            System.out.println("Expected Content:");
            System.out.println(expectedContent);
            System.out.println("Actual Content:");
            System.out.println(actualContent);
        }

        // Assertion
        assertEquals(expectedContent, actualContent, "The file content should match the expected content.");

        // Clean up
        new java.io.File(testFilePath).delete();
    }


    @Test
    public void testIsProjectIDValid_withValidID() {
        List<Project> projects = Arrays.asList(
                new Project(1, "Project1", "AI", Arrays.asList("Member1", "Member2", "Member3"), "Description1", "Country1", "url1"),
                new Project(2, "Project2", "ML", Arrays.asList("Member1", "Member2", "Member3"), "Description2", "Country2", "url2")
        );
        assertTrue(AddValidator.isProjectIDValid(projects, 1));
        assertTrue(AddValidator.isProjectIDValid(projects, 2));
    }

    @Test
    public void testIsProjectIDValid_withInvalidID() {
        List<Project> projects = Arrays.asList(
                new Project(1, "Project1", "AI", Arrays.asList("Member1", "Member2", "Member3"), "Description1", "Country1", "url1")
        );
        assertFalse(AddValidator.isProjectIDValid(projects, 2)); // ID not in the list
        assertFalse(AddValidator.isProjectIDValid(projects, -1)); // Negative ID
    }

    @Test
    public void testIsProjectIDValid_withNullList() {
        assertThrows(IllegalArgumentException.class, () -> {
            AddValidator.isProjectIDValid(null, 1);
        });
    }

    @Test
    public void testIsValidProjectID() {
        assertTrue(AddValidator.isPositiveInteger(1));   // Valid positive integer
        assertTrue(AddValidator.isPositiveInteger(100)); // Valid positive integer
        assertFalse(AddValidator.isPositiveInteger(0));  // Zero is not positive
        assertFalse(AddValidator.isPositiveInteger(-1)); // Negative number is not positive
    }

    @Test
    public void testValidateProjectList_WithValidProjects() {
        // Create a list with some projects
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(1, "Project 1", "Category 1", List.of("Ma", "Ka", "Vi"), "Description 1", "Country 1", "Url.png"));

        // Validate the list
        boolean result = AddValidator.validateProjectList(projects);

        // Assert that the result is true
        assertTrue(result, "The project list should be considered valid.");
    }

    @Test
    public void testValidateProjectList_WithEmptyList() {
        // Create an empty list
        List<Project> projects = new ArrayList<>();

        // Validate the list
        boolean result = AddValidator.validateProjectList(projects);

        // Assert that the result is false
        assertFalse(result, "The project list should be considered invalid.");
    }

    @Test
    public void testValidateProjectList_WithNullList() {
        // Create a null list
        List<Project> projects = null;

        // Validate the list
        boolean result = AddValidator.validateProjectList(projects);

        // Assert that the result is false
        assertFalse(result, "The project list should be considered invalid.");
    }

    // Method to test bubbleSortProjects
    @Test
    public void testBubbleSortProjects() {
        // Create a list of projects with unsorted projectIDs
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(5, "Project5", "CategoryA", Arrays.asList("Member1", "Member2"), "Description5", "Country5", "URL5"));
        projects.add(new Project(2, "Project2", "CategoryB", Arrays.asList("Member3", "Member4"), "Description2", "Country2", "URL2"));
        projects.add(new Project(9, "Project9", "CategoryC", Arrays.asList("Member5", "Member6"), "Description9", "Country9", "URL9"));
        projects.add(new Project(1, "Project1", "CategoryD", Arrays.asList("Member7", "Member8"), "Description1", "Country1", "URL1"));
        projects.add(new Project(6, "Project6", "CategoryE", Arrays.asList("Member9", "Member10"), "Description6", "Country6", "URL6"));

        // Expected sorted list by projectID
        List<Project> expectedProjects = new ArrayList<>();
        expectedProjects.add(new Project(1, "Project1", "CategoryD", Arrays.asList("Member7", "Member8"), "Description1", "Country1", "URL1"));
        expectedProjects.add(new Project(2, "Project2", "CategoryB", Arrays.asList("Member3", "Member4"), "Description2", "Country2", "URL2"));
        expectedProjects.add(new Project(5, "Project5", "CategoryA", Arrays.asList("Member1", "Member2"), "Description5", "Country5", "URL5"));
        expectedProjects.add(new Project(6, "Project6", "CategoryE", Arrays.asList("Member9", "Member10"), "Description6", "Country6", "URL6"));
        expectedProjects.add(new Project(9, "Project9", "CategoryC", Arrays.asList("Member5", "Member6"), "Description9", "Country9", "URL9"));

        // Sort the projects using bubbleSortProjects
        AddValidator.bubbleSortProjects(projects);

        // Assert that the sorted list matches the expected list based on projectID
        for (int i = 0; i < projects.size(); i++) {
            assertEquals(expectedProjects.get(i).getProjectID(), projects.get(i).getProjectID());
        }
    }

    @Test
    public void testValidateCategoryProjectsWithNullCategoryProjects() {
        List<Project> nullProjects = null;
        String category = "AI";
        boolean result = AddValidator.validateCategoryProjects(nullProjects, category);
        assertFalse(result, "Expected validation to fail for null categoryProjects.");
    }

    @Test
    public void testValidateCategoryProjectsWithEmptyCategoryProjects() {
        List<Project> emptyProjects = new ArrayList<>();
        String category = "AI";
        boolean result = AddValidator.validateCategoryProjects(emptyProjects, category);
        assertFalse(result, "Expected validation to fail for empty categoryProjects.");
    }

    @Test
    public void testValidateCategoryProjectsWithValidCategoryProjects() {
        List<Project> validProjects = new ArrayList<>();
        validProjects.add(new Project(1, "Project 1", "AI", new ArrayList<>(), "Description 1", "Country 1", "URL1"));
        String category = "AI";
        boolean result = AddValidator.validateCategoryProjects(validProjects, category);
        assertTrue(result, "Expected validation to pass for valid categoryProjects.");
    }

    @Test
    public void testGetValidationMessageWithNullCategoryProjects() {
        List<Project> nullProjects = null;
        String category = "AI";
        String result = AddValidator.getValidationMessage(nullProjects, category);
        assertEquals("Warning: The category list for AI is null.", result);
    }

    @Test
    public void testGetValidationMessageWithEmptyCategoryProjects() {
        List<Project> emptyProjects = new ArrayList<>();
        String category = "AI";
        String result = AddValidator.getValidationMessage(emptyProjects, category);
        assertEquals("Warning: No projects found for category: AI", result);
    }

    @Test
    public void testGetValidationMessageWithValidCategoryProjects() {
        List<Project> validProjects = new ArrayList<>();
        validProjects.add(new Project(1, "Project 1", "AI", new ArrayList<>(), "Description 1", "Country 1", "URL1"));
        String category = "AI";
        String result = AddValidator.getValidationMessage(validProjects, category);
        assertNull(result, "Expected no validation message for valid categoryProjects.");
    }

    @Test
    void testIsValidRating() {
        assertTrue(AddValidator.isValidRating("**"));
        assertTrue(AddValidator.isValidRating("*****"));
        assertFalse(AddValidator.isValidRating(""));
        assertFalse(AddValidator.isValidRating("******"));
        assertFalse(AddValidator.isValidRating("abc"));
    }

    @Test
    void testBubbleSortPointsProjects() {
        // Create a list of Project without specifying judgesPoints
        ObservableList<Project> projects = FXCollections.observableArrayList(
                new Project(1, "Project A", "ML", List.of("TeamA"), "Description A", "Country A"),
                new Project(2, "Project B", "AI", List.of("TeamB"), "Description B", "Country B"),
                new Project(3, "Project C", "RT", List.of("TeamC"), "Description C", "Country C")
        );

        // Set judgesPoints for each project
        projects.get(0).setJudgesPoints(15);
        projects.get(1).setJudgesPoints(10);
        projects.get(2).setJudgesPoints(20);

        // Perform the bubble sort
        AddValidator.bubbleSortProjects(projects);

        // Validate that the projects are sorted by judgesPoints in descending order
        assertEquals(20, projects.get(0).getJudgesPoints());
        assertEquals(15, projects.get(1).getJudgesPoints());
        assertEquals(10, projects.get(2).getJudgesPoints());
    }

    @Test
    void testGetPlaceString() {
        // Create an instance of the class containing getPlaceString
        AwardWinningProjects awardWinningProjects = new AwardWinningProjects();

        // Test for 1st place
        assertEquals("1st", awardWinningProjects.getPlaceString(0), "Expected 1st for index 0");

        // Test for 2nd place
        assertEquals("2nd", awardWinningProjects.getPlaceString(1), "Expected 2nd for index 1");

        // Test for 3rd place
        assertEquals("3rd", awardWinningProjects.getPlaceString(2), "Expected 3rd for index 2");

        // Test for index greater than 2
        assertEquals("", awardWinningProjects.getPlaceString(3), "Expected empty string for index 3");

        // Test for negative index
        assertEquals("", awardWinningProjects.getPlaceString(-1), "Expected empty string for negative index");
    }

    @Test
    void testValidateSelectedProjects() {
        // Test with null
        assertFalse(AddValidator.validateSelectedProjects(null));

        // Test with empty list
        ObservableList<Project> emptyList = FXCollections.observableArrayList();
        assertFalse(AddValidator.validateSelectedProjects(emptyList));

        // Test with list containing fewer than 3 projects
        ObservableList<Project> lessThanThree = FXCollections.observableArrayList(
                new Project(1, "Project A", "ML", List.of("TeamA"), "Description A", "Country A", null),
                new Project(2, "Project B", "AI", List.of("TeamB"), "Description B", "Country B", null)
        );
        assertFalse(AddValidator.validateSelectedProjects(lessThanThree));

        // Test with list containing exactly 3 projects
        ObservableList<Project> exactlyThree = FXCollections.observableArrayList(
                new Project(1, "Project A", "ML", List.of("TeamA"), "Description A", "Country A", null),
                new Project(2, "Project B", "AI", List.of("TeamB"), "Description B", "Country B", null),
                new Project(3, "Project C", "RT", List.of("TeamC"), "Description C", "Country C", null)
        );
        assertTrue(AddValidator.validateSelectedProjects(exactlyThree));

        // Test with list containing more than 3 projects
        ObservableList<Project> moreThanThree = FXCollections.observableArrayList(
                new Project(1, "Project A", "ML", List.of("TeamA"), "Description A", "Country A", null),
                new Project(2, "Project B", "AI", List.of("TeamB"), "Description B", "Country B", null),
                new Project(3, "Project C", "RT", List.of("TeamC"), "Description C", "Country C", null),
                new Project(4, "Project D", "ML", List.of("TeamD"), "Description D", "Country D", null)
        );
        assertTrue(AddValidator.validateSelectedProjects(moreThanThree));
    }

    @Test
    void testValidateAwardWinningProjects() {
        // Test with null
        assertFalse(AddValidator.validateAwardWinningProjects(null));

        // Test with empty list
        ObservableList<Project> emptyList = FXCollections.observableArrayList();
        assertFalse(AddValidator.validateAwardWinningProjects(emptyList));

        // Test with list containing fewer than 3 projects
        ObservableList<Project> lessThanThree = FXCollections.observableArrayList(
                new Project(1, "Project A", "ML", List.of("TeamA"), "Description A", "Country A"),
                new Project(2, "Project B", "AI", List.of("TeamB"), "Description B", "Country B")
        );
        assertFalse(AddValidator.validateAwardWinningProjects(lessThanThree));

        // Test with list containing exactly 3 projects
        ObservableList<Project> exactlyThree = FXCollections.observableArrayList(
                new Project(1, "Project A", "ML", List.of("TeamA"), "Description A", "Country A"),
                new Project(2, "Project B", "AI", List.of("TeamB"), "Description B", "Country B"),
                new Project(3, "Project C", "RT", List.of("TeamC"), "Description C", "Country C")
        );
        assertTrue(AddValidator.validateAwardWinningProjects(exactlyThree));

        // Test with list containing more than 3 projects
        ObservableList<Project> moreThanThree = FXCollections.observableArrayList(
                new Project(1, "Project A", "ML", List.of("TeamA"), "Description A", "Country A"),
                new Project(2, "Project B", "AI", List.of("TeamB"), "Description B", "Country B"),
                new Project(3, "Project C", "RT", List.of("TeamC"), "Description C", "Country C"),
                new Project(4, "Project D", "ML", List.of("TeamD"), "Description D", "Country D")
        );
        assertTrue(AddValidator.validateAwardWinningProjects(moreThanThree));
    }
}


/*
    @BeforeAll
    public static void setUp() throws IOException {
        // Create a file with test data
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("temp_project_details.txt"))) {
            writer.write("*********** AI Projects ***********\n");
            writer.write("\n");
            writer.write("---Project Details---\n");
            writer.write("\n");
            writer.write("Project ID: 1\n");
            writer.write("Project Name: AI Project 1\n");
            writer.write("Category: AI\n");
            writer.write("Team Members: Member A, Member B, Member C\n");
            writer.write("Brief Description: Description 1\n");
            writer.write("Country: Country A\n");
            writer.write("Logo URL: file:/C:/Users/DELL/Desktop/Tigers.png\n");
            writer.write("\n");
            writer.write("---Project Details---\n");
            writer.write("\n");
            writer.write("Project ID: 2\n");
            writer.write("Project Name: ML Project 1\n");
            writer.write("Category: ML\n");
            writer.write("Team Members: Member A, Member B, Member C\n");
            writer.write("Brief Description: Description 2\n");
            writer.write("Country: Country B\n");
            writer.write("Logo URL: file:/C:/Users/DELL/Downloads/WhatsApp%20Image%202024-07-21%20at%2014.55.36_8efa94eb.jpg\n");
            writer.write("\n");
            writer.write("---Project Details---\n");
            writer.write("\n");
            writer.write("Project ID: 3\n");
            writer.write("Project Name: RT Project 1\n");
            writer.write("Category: RT\n");
            writer.write("Team Members: Member A, Member B, Member C\n");
            writer.write("Brief Description: Description 3\n");
            writer.write("Country: Country C\n");
            writer.write("Logo URL: file:/C:/Users/DELL/Pictures/aquians%20cricket/IMG_9440.JPG\n");
            writer.write("\n");
        }
    }

    @AfterAll
    public static void tearDown() throws IOException {
        // Clean up the test file
        Files.deleteIfExists(Paths.get("temp_project_details.txt"));
    }

    @Test
    public void testReadProjectsFromFile() {
        // Read the file content
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("temp_project_details.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException occurred while reading the file");
        }

        // Print the file content for debugging
        System.out.println("File Content:");
        System.out.println(fileContent.toString());

        // Continue with the test
        List<Project> expectedProjects = new ArrayList<>();
        expectedProjects.add(new Project(1, "AI Project 1", "AI", List.of("Member A", "Member B", "Member C"), "Description 1", "Country A", "file:/C:/Users/DELL/Desktop/Tigers.png"));
        expectedProjects.add(new Project(2, "ML Project 1", "ML", List.of("Member A", "Member B", "Member C"), "Description 2", "Country B", "file:/C:/Users/DELL/Downloads/WhatsApp%20Image%202024-07-21%20at%2014.55.36_8efa94eb.jpg"));
        expectedProjects.add(new Project(3, "RT Project 1", "RT", List.of("Member A", "Member B", "Member C"), "Description 3", "Country C", "file:/C:/Users/DELL/Pictures/aquians%20cricket/IMG_9440.JPG"));

        List<Project> actualProjects = AddValidator.readProjectsFromFile("temp_project_details.txt");

        assertEquals(expectedProjects, actualProjects, "The projects read from the file should match the expected projects.");
    }
*/

