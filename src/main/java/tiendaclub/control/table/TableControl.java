package tiendaclub.control.table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import tiendaclub.MainFX;
import tiendaclub.data.DataStore;
import tiendaclub.data.framework.dao.IdentifiableDao;
import tiendaclub.model.models.abstracts.Identifiable;
import tiendaclub.view.FxDialogs;

import java.io.IOException;
import java.util.Collection;

public abstract class TableControl<T extends Identifiable> extends BorderPane {
    protected final ObservableList<T> listedObjects = FXCollections.observableArrayList();

    protected IdentifiableDao<T> dataOrigin;

    @FXML
    protected TableView<T> fxTable;

    @FXML
    protected TableColumn<T, Integer> fxColumnId;


    @FXML
    protected void fxButtonBackAction(ActionEvent actionEvent) {
        ((BorderPane) MainFX.getMainStage().getScene().getRoot()).setCenter(null);
    }

    protected abstract void fxBtnAddAction(ActionEvent actionEvent) throws IOException;

    @FXML
    protected void fxBtnEliminarAction(ActionEvent actionEvent) {
        T selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (FxDialogs.showConfirmBoolean("Cuidado", "Deseas eliminar el Id " + selected.getId() + " ?")) {
                boolean success = selected.deleteFromDb() == 1;
                FxDialogs.showInfo("", "Id " + selected.getId() + (success ? " " : "NO ") + "eliminado");
                if (success)
                    listedObjects.remove(selected);
            }
        }
    }

    protected abstract void fxBtnEditAction(ActionEvent actionEvent) throws IOException;

    @FXML
    protected void fxBtnRefreshAction(ActionEvent actionEvent) {
        fxTable.refresh();
    }

    @FXML
    protected void fxBtnReloadAction(ActionEvent actionEvent) {
        addContent(true);
    }

    @FXML
    protected void fxBtnPullAction(ActionEvent actionEvent) {
        DataStore.getUsuarios().queryAll();
        addContent(true);
    }

    protected void addContent(boolean clean) {
        if (clean)
            listedObjects.clear();

        Collection<T> list = dataOrigin.getCache().values();

        list.forEach(e -> {
            if (!listedObjects.contains(e))
                listedObjects.add(e);
        });
    }

    protected void addContent() {
        addContent(false);
    }
}
