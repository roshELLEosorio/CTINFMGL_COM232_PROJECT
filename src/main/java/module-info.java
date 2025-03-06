module kevs.dev.airbnb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;

    opens kevs.dev.airbnb to javafx.fxml;

    exports kevs.dev.airbnb;
}