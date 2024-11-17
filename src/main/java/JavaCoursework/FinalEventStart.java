package JavaCoursework;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FinalEventStart {
    @FXML
    public ImageView IconRSS;
    @FXML
    public ImageView IconAWP;
    @FXML
    public ImageView IconVAP;
    @FXML
    public ImageView IconVPD;
    @FXML
    public ImageView IconEND;
    @FXML
    public ImageView IconEXIT;

    @FXML
    public void initialize() {
        // Load images for all ImageViews
        loadImage(IconVPD, "VPD.png");
        loadImage(IconRSS, "RSS.png");
        loadImage(IconAWP, "AWP.png");
        loadImage(IconVAP, "VAP.png");
        loadImage(IconEND, "THANKYOU.png");
        loadImage(IconEXIT, "EXIT.png");
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
