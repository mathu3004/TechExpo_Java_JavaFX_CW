package JavaCoursework;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Project implements Serializable {
    private int projectID;
    private String projectName;
    private String category;
    private List<String> teamMembers;
    private String description;
    private String country;
    private String teamLogoUrl; // Store URL instead of Image directly
    private Image teamLogo;
    private int judgesPoints;

    public Project(int projectID, String projectName, String category, List<String> teamMembers, String description, String country, String teamLogoUrl) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.category = category;
        this.teamMembers = teamMembers;
        this.description = description;
        this.country = country;
        this.teamLogoUrl = teamLogoUrl;
    }

    public Project(int projectID, String projectName, String category, List<String> teamMembers, String description, String country) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.category = category;
        this.teamMembers = teamMembers;
        this.description = description;
        this.country = country;
        this.judgesPoints = 0;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTeamMembersAsString() {
        return String.join(", ", teamMembers);
    }

    public List<String> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<String> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTeamLogoUrl() {
        return teamLogoUrl;
    }

    public void setTeamLogoUrl(String teamLogoUrl) {
        this.teamLogoUrl = teamLogoUrl;
        this.teamLogo = loadTeamLogo();
    }

    public void setTeamLogo(Image teamLogoImage) {
        this.teamLogo = teamLogoImage;
    }

   public Image getTeamLogo() {
        return teamLogo;
    }

    public int getJudgesPoints() { return judgesPoints; }

    public void setJudgesPoints(int judgesPoints) { this.judgesPoints = judgesPoints; }

    @Override
    public String toString() {
        return "Project{" +
                "projectID=" + projectID +
                ", projectName='" + projectName + '\'' +
                ", category='" + category + '\'' +
                ", teamMembers=" + teamMembers +
                ", description='" + description + '\'' +
                ", country='" + country + '\'' +
                ", teamLogoUrl='" + teamLogoUrl + '\'' +
                ", judgesPoints=" + judgesPoints +
                '}';
    }

    public Image loadTeamLogo() {
        try {
            return new Image(teamLogoUrl, true);
        } catch (Exception e) {
            System.err.println("Failed to load team logo: " + e.getMessage());
            return null; // Return null or a default image
        }
    }

    public static List<String> formatTeamMembers(String input) {
        String[] membersArray = input.trim().split(",");
        List<String> teamMembers = new ArrayList<>();
        for (String member : membersArray) {
            teamMembers.add(capitalizeFirstLetter(member.trim()));
        }
        return teamMembers;
    }

    static String capitalizeFirstLetter(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    static boolean isValidCategory(String category) {
        return category.equalsIgnoreCase("AI") || category.equalsIgnoreCase("ML") || category.equalsIgnoreCase("RT");
    }

    static String formatDescription(String description) {
        String[] sentences = description.split("(?<=[.!?])\\s*");
        StringBuilder capitalizedDescription = new StringBuilder();
        for (String sentence : sentences) {
            capitalizedDescription.append(sentence.substring(0, 1).toUpperCase())
                    .append(sentence.substring(1).toLowerCase())
                    .append(" ");
        }
        return capitalizedDescription.toString().trim();
    }
}