module com.example.projet{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // si tu utilises MySQL

    opens controller to javafx.fxml;
    opens module to javafx.fxml;
    opens com.example.projet to javafx.fxml;
    exports  controller;
    exports module;

    exports com.example.projet;
}
