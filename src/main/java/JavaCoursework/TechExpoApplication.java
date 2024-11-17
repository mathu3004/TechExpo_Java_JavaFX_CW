package JavaCoursework;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/*** Main application class for the TechExpo application. This class extends the JavaFX Application class and sets up the main stage. */
public class TechExpoApplication extends Application {

    @ Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("WelcomeScreen.fxml"));
        // Create a new scene with the loaded layout and set its size
        Scene scene = new Scene(root, 980, 700);

        // Load the application icon
        Image icon = new Image("/projectIcon.png");
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Welcome to TechExpo!!!");
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.getStylesheets().add(getClass().getResource("JavaCoursework.css").toExternalForm());
    }

    public static void main(String[] args) {
        launch();
    } // Launch the JavaFX application
}