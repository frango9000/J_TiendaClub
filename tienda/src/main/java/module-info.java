module tienda {
    requires casteldao;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.google.common;
    requires org.checkerframework.checker.qual;
    requires flogger;
    requires jfxtras.controls;
    requires java.sql;

    opens app.model to javafx.base, casteldao;
    opens app to javafx.graphics;
    opens app.control to javafx.fxml;
    opens app.control.table to javafx.fxml;
    opens app.control.editor to javafx.fxml;
}