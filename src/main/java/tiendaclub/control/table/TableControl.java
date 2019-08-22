package tiendaclub.control.table;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import tiendaclub.MainFX;
import tiendaclub.control.editor.EditorControl;
import tiendaclub.data.DataStore;
import tiendaclub.data.framework.dao.core.IndexIdDao;
import tiendaclub.misc.Flogger;
import tiendaclub.model.models.core.Persistible;
import tiendaclub.view.FXMLStage;
import tiendaclub.view.FxDialogs;

public abstract class TableControl<T extends Persistible> extends BorderPane {

    protected final ObservableList<T> listedObjects = FXCollections.observableArrayList();

    protected EditorControl<T> editorControl;

    @FXML
    protected TableView<T> fxTable;
    @FXML
    protected TableColumn<T, Boolean> fxColumnIsActive;
    @FXML
    protected TableColumn<T, Integer> fxColumnId;
    @FXML
    protected Button fxBtnDisable;
    @FXML
    protected Button fxBtnShowHide;

    public TableControl() {
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tables/GenericActivableTablePane.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (final IOException e) {
            Flogger.atSevere().withCause(e).log();
        }
    }

    @FXML
    void initialize() {
        fxTable.getColumns().remove(fxColumnIsActive);
        fxBtnDisable.setVisible(false);
        fxBtnShowHide.setVisible(false);

        fxColumnId.setCellValueFactory(new PropertyValueFactory<T, Integer>("id"));
    }

    @FXML
    protected void fxBtnAddAction(ActionEvent actionEvent) throws IOException {
        FXMLStage stage = new FXMLStage(getEditorPane(), "Creator");
        stage.showAndWait();
        fxTable.refresh();
        addContent();
    }

    @FXML
    protected void fxBtnEditAction(ActionEvent actionEvent) throws IOException {
        T selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            FXMLStage stage = new FXMLStage(getEditorPane(), selected.getClass().getSimpleName() + " Editor");
            getEditorControl().setEditee(selected);
            stage.showAndWait();
            fxTable.refresh();
        }
    }

    @FXML
    protected abstract void fxBtnDisableAction(ActionEvent actionEvent);

    @FXML
    protected abstract void fxBtnShowHideAction(ActionEvent actionEvent);

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
    protected void fxBtnLimpiarAction(ActionEvent actionEvent) {
        listedObjects.clear();
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

    @FXML
    protected void fxButtonBackAction(ActionEvent actionEvent) {
        ((BorderPane) MainFX.getMainStage().getScene().getRoot()).setCenter(null);
    }


    protected void addContent(boolean clean) {
        if (clean) {
            listedObjects.clear();
        }
        listedObjects.retainAll(getDataOrigin().getIndexId().getCacheValues());
    }

    protected void addContent() {
        addContent(false);
    }

    protected abstract Pane getEditorPane() throws IOException;

    protected EditorControl<T> getEditorControl() {
        return editorControl;
    }

    protected abstract IndexIdDao<T> getDataOrigin();


}
