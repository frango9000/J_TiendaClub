package tiendaclub.control.table;

import java.io.IOException;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import tiendaclub.MainFX;
import tiendaclub.control.editor.EditorControl;
import tiendaclub.data.DataStore;
import tiendaclub.data.framework.dao.IndexIdDao;
import tiendaclub.model.models.abstracts.Persistible;
import tiendaclub.view.FXMLStage;
import tiendaclub.view.FxDialogs;

public abstract class TableControl<T extends Persistible> extends BorderPane {

    protected final ObservableList<T> listedObjects = FXCollections.observableArrayList();

    protected EditorControl<T> editorControl;

    @FXML
    protected TableView<T> fxTable;

    @FXML
    protected TableColumn<T, Integer> fxColumnId;


    @FXML
    protected void fxButtonBackAction(ActionEvent actionEvent) {
        ((BorderPane) MainFX.getMainStage().getScene().getRoot()).setCenter(null);
    }

    @FXML
    protected void fxBtnEliminarAction(ActionEvent actionEvent) {
        T selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (FxDialogs.showConfirmBoolean("Cuidado", "Deseas eliminar el Id " + selected.getId() + " ?")) {
                boolean success = selected.deleteFromDb() == 1;
                FxDialogs.showInfo("", "Id " + selected.getId() + (success ? " " : "NO ") + "eliminado");
                if (success) {
                    listedObjects.remove(selected);
                }
            }
        }
    }

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
        DataStore.getUsuarios().getDataSource().queryAll();
        addContent(true);
    }

    protected void addContent(boolean clean) {
        if (clean) {
            listedObjects.clear();
        }

        Set<T> list = getDataOrigin().getIdIndex().getCacheValues();

        list.forEach(e -> {
            if (!listedObjects.contains(e)) {
                listedObjects.add(e);
            }
        });
    }

    protected void addContent() {
        addContent(false);
    }

    @FXML
    protected void fxBtnAddAction(ActionEvent actionEvent) throws IOException {
        FXMLStage stage = new FXMLStage(getEditorPane(), "((Stage)getScene().getWindow()).getTitle()");
        stage.showAndWait();
        fxTable.refresh();
        addContent();
    }

    @FXML
    protected void fxBtnEditAction(ActionEvent actionEvent) throws IOException {
        T selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            FXMLStage stage = new FXMLStage(getEditorPane(), "((Stage)getScene().getWindow()).getTitle()");
            getEditorControl().setEditee(selected);
            stage.showAndWait();
            fxTable.refresh();
        }
    }

    protected abstract IndexIdDao<T> getDataOrigin();

    protected abstract Pane getEditorPane() throws IOException;

    protected EditorControl<T> getEditorControl() {
        return editorControl;
    }
}
