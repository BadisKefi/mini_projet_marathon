module com.projet.marathon {
    requires javafx.controls;
    requires javafx.fxml;
    /*requires mysql.connector.java;*/
    requires java.sql;

    opens com.projet.marathon to javafx.fxml;
    exports com.projet.marathon;
    exports com.projet.marathon.controllers;
    opens com.projet.marathon.controllers to javafx.fxml;
    exports com.projet.marathon.entities;
    opens com.projet.marathon.entities to javafx.fxml;

    exports com.projet.marathon.Statique;
    opens com.projet.marathon.Statique to javafx.fxml;

}