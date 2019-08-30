package tiendaclub.control.editor;

import com.google.common.flogger.StackSize;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import tiendaclub.data.casteldao.daomodel.Persistible;
import tiendaclub.data.casteldao.daomodel.PersistibleDao;
import tiendaclub.misc.Flogger;
import tiendaclub.misc.FxDialogs;

public class EditorControl<T extends Persistible> extends BorderPane {

    @FXML
    public BorderPane fxGenericEditorBorderPane;
    @FXML
    public MenuButton fxButtonMenu;
    protected T editee;
    protected boolean creating = true;
    protected GridControl<T> gridControl;
    protected PersistibleDao<T> dataOrigin;

    {
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/editor/GenericEditorPane.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (final IOException e) {
            Flogger.atSevere().withCause(e).log();
        }
    }

    public EditorControl(T editee, PersistibleDao<T> dataOrigin, GridControl<T> gridControl, Pane gridpane) {
        this.dataOrigin  = dataOrigin;
        this.gridControl = gridControl;
        setCenter(gridpane);
        if (editee != null) {
            creating    = false;
            this.editee = editee;
            gridControl.setFields(this.editee);
        }
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