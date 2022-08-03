module com.example.sheridanbagelhouseproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;

    opens com.example.sheridanbagelhouseproject to javafx.fxml;
    exports com.example.sheridanbagelhouseproject;
}