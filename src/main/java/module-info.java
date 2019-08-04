module tiendaclub {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens tiendaclub.control to javafx.fxml;
    exports tiendaclub;
}