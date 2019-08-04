module tiendaclub {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;

    opens tiendaclub.control to javafx.fxml;
    opens tiendaclub.model.models.abstracts to javafx.base;
    exports tiendaclub;
}