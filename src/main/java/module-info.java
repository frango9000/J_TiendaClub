module tiendaclub {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    requires com.google.common;
    requires org.mariadb.jdbc;
    requires org.checkerframework.checker.qual;
    requires flogger;
    requires jfxtras.controls;

    opens tiendaclub.control to javafx.fxml;
    opens tiendaclub.control.editor to javafx.fxml;
    opens tiendaclub.control.table to javafx.fxml;
    opens tiendaclub.model.models.core to javafx.base;
    opens tiendaclub.model.models to javafx.base;
    opens tiendaclub.data.framework.model to javafx.base;
    exports tiendaclub;
}