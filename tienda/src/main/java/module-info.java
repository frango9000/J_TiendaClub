module tienda {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    requires com.google.common;
    requires org.mariadb.jdbc;
    requires org.checkerframework.checker.qual;
    requires flogger;
    requires jfxtras.controls;

    opens app.control to javafx.fxml;
    opens app.control.editor to javafx.fxml;
    opens app.control.table to javafx.fxml;
    opens app.data.casteldao.model to javafx.base;
    opens app.model to javafx.base;

    opens app;
}