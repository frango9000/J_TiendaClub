package app.control.table;

import app.control.editor.EditorControl;
import app.control.editor.GridControl;
import app.data.casteldao.dao.DataSource;
import app.data.casteldao.dao.IndexIdDataSource;
import app.data.casteldao.model.AbstractPersistible;
import app.misc.FXMLStage;
import app.misc.Flogger;
import app.misc.FxDialogs;
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

public abstract class TableControl<T extends AbstractPersistible> extends BorderPane {

    protected final ObservableList<T> listedObjects = FXCollections.observableArrayList();

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
        EditorControl<T> editorControl = getEditorControl(fxmlLocation(), null, getDataOrigin());
        FXMLStage stage = new FXMLStage(editorControl, "Creator");
        stage.showAndWait();
        fxTable.refresh();
        addContent();
    }

    @FXML
    protected void fxBtnEditAction(ActionEvent actionEvent) throws IOException {
        T selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            EditorControl<T> editorControl = getEditorControl(fxmlLocation(), selected, getDataOrigin());
            FXMLStage stage = new FXMLStage(editorControl, selected.getClass().getSimpleName() + " Editor");
            stage.showAndWait();
            fxTable.refresh();
        }
    }

    @FXML
    protected void fxBtnDisableAction(ActionEvent actionEvent) {

    }

    @FXML
    protected void fxBtnShowHideAction(ActionEvent actionEvent) {

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
        getDataOrigin().getDao().queryAll();
        addContent(true);
    }

    @FXML
    protected void fxButtonBackAction(ActionEvent actionEvent) {
        this.setVisible(false);
    }


    protected void addContent(boolean clean) {
        if (clean) {
            listedObjects.clear();
        }
        listedObjects.setAll(getDataOrigin().getAllCache());
    }

    protected void addContent() {
        addContent(false);
    }

    public <T extends AbstractPersistible> EditorControl<T> getEditorControl(String fxml, T editee, DataSource<T> dataOrigin) {
        FXMLLoader loader = new FXMLLoader(TableControl.class.getResource(fxml));
        Pane pane = null;
        GridControl<T> tGridControl = null;
        try {
            pane         = loader.load();
            tGridControl = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new EditorControl<>(editee, dataOrigin, tGridControl, pane);

    }

    protected abstract String fxmlLocation();

    protected abstract IndexIdDataSource<T> getDataOrigin();
}
