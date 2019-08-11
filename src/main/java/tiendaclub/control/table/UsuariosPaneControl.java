package tiendaclub.control.table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import tiendaclub.MainFX;
import tiendaclub.control.editor.UsuarioEditorPaneControl;
import tiendaclub.data.DataStore;
import tiendaclub.model.models.Acceso;
import tiendaclub.model.models.Usuario;
import tiendaclub.view.FXMLStage;
import tiendaclub.view.FxDialogs;

import java.io.IOException;
import java.util.Collection;

public class UsuariosPaneControl extends BorderPane {

    private final ObservableList<Usuario> usuarios = FXCollections.observableArrayList();

    private boolean showInactive = false;

    @FXML
    private TableView<Usuario> fxTable;
    @FXML
    private TableColumn<Usuario, Integer> fxColumnId;
    @FXML
    private TableColumn<Usuario, String> fxColumnUser;
    @FXML
    private TableColumn<Usuario, String> fxColumnName;
    @FXML
    private TableColumn<Usuario, Acceso> fxColumnLevel;
    @FXML
    private Button fxBtnShowHide;
    @FXML
    private TableColumn<Usuario, Boolean> fxColumnIsActive;

    public static Pane getPane() {
        String url = "/fxml/tables/UsuariosPane.fxml";
        Pane root = null;
        try {
            root = FXMLLoader.load(FXMLStage.class.getResource(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    @FXML
    public void initialize() {
        fxColumnId.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("id"));
        fxColumnUser.setCellValueFactory(new PropertyValueFactory<Usuario, String>("username"));
        fxColumnName.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nombre"));
        fxColumnLevel.setCellValueFactory(new PropertyValueFactory<Usuario, Acceso>("acceso"));
        fxColumnIsActive.setCellValueFactory(f -> f.getValue().activeProperty());
        fxColumnIsActive.setCellFactory(tc -> new CheckBoxTableCell<>());
        fxTable.setItems(usuarios);

        addContent();
    }

    @FXML
    private void fxButtonBackAction(ActionEvent actionEvent) {
        ((BorderPane) MainFX.getMainStage().getScene().getRoot()).setCenter(null);
    }

    @FXML
    private void fxBtnAddAction(ActionEvent actionEvent) throws IOException {
        Pane pane = UsuarioEditorPaneControl.loadFXML();
        FXMLStage stage = new FXMLStage(pane, "Usuario");
        stage.showAndWait();
        fxTable.refresh();
        addContent();
    }

    @FXML
    private void fxBtnEliminarAction(ActionEvent actionEvent) {
        Usuario selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (FxDialogs.showConfirmBoolean("Cuidado", "Deseas eliminar el usuario " + selected.getUsername() + " ?")) {
                boolean success = selected.deleteFromDb() == 1;
                FxDialogs.showInfo("", "Usuario " + selected.getUsername() + (success ? " " : "NO ") + "eliminado");
                if (success)
                    usuarios.remove(selected);
            }
        }
    }

    @FXML
    private void fxBtnEditAction(ActionEvent actionEvent) throws IOException {
        Usuario selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Pane pane = UsuarioEditorPaneControl.loadFXML();
            UsuarioEditorPaneControl control = UsuarioEditorPaneControl.getController();
            control.setUsuario(selected);
            FXMLStage stage = new FXMLStage(pane, "Usuario");
            stage.showAndWait();
            if (!showInactive && !selected.isActivo())
                usuarios.remove(selected);
            fxTable.refresh();
        }
    }

    @FXML
    private void fxBtnRefreshAction(ActionEvent actionEvent) {
        fxTable.refresh();
    }

    @FXML
    private void fxBtnShowHideAction(ActionEvent actionEvent) {
        showInactive = !showInactive;
        addContent(true);
    }

    @FXML
    private void fxBtnReloadAction(ActionEvent actionEvent) {
        addContent(true);
    }

    @FXML
    private void fxBtnDisableAction(ActionEvent actionEvent) {
        Usuario selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            boolean isActive = selected.isActivo();
            if (FxDialogs.showConfirmBoolean("Cuidado", "Deseas " + (isActive ? "des" : "") + "activar el usuario " + selected.getUsername() + " ?")) {
                selected.toggleActivo();
                boolean success = selected.updateOnDb() == 1;
                FxDialogs.showInfo("", "Usuario " + selected.getUsername() + (success ? " " : "NO ") + (isActive ? "des" : "") + "activado");
                if (!success)
                    selected.toggleActivo();
                else if (!showInactive && !selected.isActivo())
                    usuarios.remove(selected);
            }
        }
    }

    @FXML
    private void fxBtnPullAction(ActionEvent actionEvent) {
        DataStore.getUsuarios().queryAll();
        addContent(true);
    }

    private void addContent(boolean clean) {
        if (clean)
            usuarios.clear();

        Collection<Usuario> list;
        if (showInactive) {
            list = DataStore.getUsuarios().getCache().values();
            fxBtnShowHide.setText("Todos");
        } else {
            list = DataStore.getUsuarios().getActive(true);
            fxBtnShowHide.setText("Activos");
        }

        list.forEach(e -> {
            if (!usuarios.contains(e))
                usuarios.add(e);
        });
    }

    private void addContent() {
        addContent(false);
    }
}
