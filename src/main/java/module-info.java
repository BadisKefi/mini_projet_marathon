module com.projet.marathon {
    requires javafx.controls;
    requires javafx.fxml;
    /*requires mysql.connector.java;*/
    requires java.sql;

    opens com.projet.marathon to javafx.fxml;
    exports com.projet.marathon;
}