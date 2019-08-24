package tiendaclub.control.editor;

import com.google.common.flogger.StackSize;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.checkerframework.checker.nullness.qual.NonNull;
import tiendaclub.misc.Flogger;
import tiendaclub.model.models.core.Persistible;
import tiendaclub.view.FxDialogs;

public class EditorControl<T extends Persistible> extends BorderPane {

    @FXML
    public BorderPane fxGenericEditorBorderPane;
    @FXML
    public MenuButton fxButtonMenu;
    protected T editee;
    protected boolean creating = true;
    protected GridControl<T> gridControl;

    protected EditorControl() {
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/editor/GenericEditorPane.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (final IOException e) {
            Flogger.atSevere().withCause(e).log();
        }
    }

    public void setGridControl(GridControl<T> gridControl) {
        this.gridControl = gridControl;
        gridControl.setFxButtonMenu(fxButtonMenu);
    }

    public void setGridPane(GridPane gridPane) {
        setCenter(gridPane);
    }

    public void setEditee(@NonNull T editee) {
        this.editee = editee;
        creating    = false;
        gridControl.setFields(editee);
        System.out.println(editee.toString());
    }

    @FXML
    protected void fxBtnDiscardAction(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }

    @FXML
    protected void fxBtnSaveAction(ActionEvent event) {
        if (gridControl.validFields()) {
            int rows = 0;
            if (creating) {
                editee = gridControl.buildNew();
                rows   = editee.insertIntoDB();
            } else {
                try {
                    editee.setBackup();
                    gridControl.updateEditee(editee);
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
                gridControl.setFields(editee);
            }
        } else {
            Flogger.atWarning().log("Invalid Fields");
            FxDialogs.showError("Fail!", "Invalid Fields");
        }
    }
}