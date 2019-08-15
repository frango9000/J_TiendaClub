package tiendaclub.control.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.BorderPane;
import tiendaclub.model.models.abstracts.Persistible;
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

    public T getEditee() {
        return editee;
    }

    public void setEditee(T editee) {
        this.editee = editee;
        if (editee != null) {
            creating = false;
            setFields();
        }
    }

    protected abstract void updateEditee();

    protected abstract T buildEditee();

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
                buildEditee();
                rows = editee.insertIntoDB();
            } else {
                updateEditee();
                rows = editee.updateOnDb();
            }
            if (rows > 0) {
                FxDialogs.showInfo("Success",
                        editee.getClass().getSimpleName() + " " + (creating ? "creado" : "modificado"));
                ((Node) event.getSource()).getScene().getWindow().hide();
            } else {
                FxDialogs.showError("Fail!",
                        editee.getClass().getSimpleName() + " no " + (creating ? "creado" : "modificado"));
            }
        } else {
            FxDialogs.showError("Fail!", "Invalid Fields");
        }
    }
}
