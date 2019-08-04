module tiendaclub {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens tiendaclub.control;
    exports tiendaclub;
}