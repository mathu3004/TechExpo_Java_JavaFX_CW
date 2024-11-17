module com.example.cm1601_2410212_20233136 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cm1601_2410212_20233136 to javafx.fxml;
    exports com.example.cm1601_2410212_20233136;
    exports JavaCoursework;
    opens JavaCoursework to javafx.fxml;
}