package JavaCoursework;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ThankYou {

    @FXML
    public ImageView ThankYouIcon;

    @FXML
    public void initialize() {
        // Load images for all ImageViews
        loadImage(ThankYouIcon, "THANKYOU.png");
    }

    private void loadImage(ImageView imageView, String imagePath) {
        // Use the path relative to the resources folder
        Image image = new Image(getClass().getResourceAsStream("/" + imagePath));
        if (image != null) {
            imageView.setImage(image);
        } else {
            System.err.println("Image resource not found: " + imagePath);
        }
    }
}
