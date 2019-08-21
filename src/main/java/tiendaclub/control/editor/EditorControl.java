package tiendaclub.control.editor;

import com.google.common.flogger.StackSize;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.BorderPane;
import org.checkerframework.checker.nullness.qual.NonNull;
import tiendaclub.misc.Flogger;
import tiendaclub.model.models.core.Persistible;
import tiendaclub.view.FxDialogs;

public abstract class EditorControl<T extends Persistible> extends BorderPane {

    private static EditorControl controller;
    protected T editee;
    protected boolean creating = true;

    protected EditorControl() {
        controller = this;
    }

    public static EditorControl getController() {
        return controller;
    }

    protected static String getTextOrNull(TextInputControl control) {
        String txt = control.getText().trim();
        return txt.length() > 0 ? txt : null;
    }

    protected static String getNotNullText(String txt) {
        return txt != null ? txt : "";
    }

    public void setEditee(@NonNull T editee) {
        this.editee = editee;
        creating = false;
        setFields();
    }

    protected abstract void updateEditee();

    protected abstract T buildNew();

    protected abstract void setFields();

    protected abstract boolean validFields();

    @FXML
    protected void fxBtnDiscardAction(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }

    @FXML
    protected void fxBtnSaveAction(ActionEvent event) {
        if (validFields()) {
            int rows = 0;
            if (creating) {
                buildNew();
                rows = editee.insertIntoDB();
            } else {
                try {
                    editee.setBackup();
                    updateEditee();
                    rows = editee.updateOnDb();
                } catch (CloneNotSupportedException e) {
                    Flogger.atSevere().withStackTrace(StackSize.FULL).log("Failed creating backup");
                }
            }
            if (rows > 0) {
                FxDialogs.showInfo("Success",
                        editee.getClass().getSimpleName() + " " + (creating ? "creado" : "modificado"));
                ((Node) event.getSource()).getScene().getWindow().hide();
            } else {
                FxDialogs.showError("Fail!",
                        editee.getClass().getSimpleName() + " no " + (creating ? "creado" : "modificado"));
                setFields();
            }
        } else {
            Flogger.atWarning().log("Invalid Fields");
            FxDialogs.showError("Fail!", "Invalid Fields");
        }
    }
}
