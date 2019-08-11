package tiendaclub.control.table;

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
import tiendaclub.control.editor.UsuarioEditorPaneControl;
import tiendaclub.data.DataStore;
import tiendaclub.model.models.Acceso;
import tiendaclub.model.models.Usuario;
import tiendaclub.view.FXMLStage;

import java.io.IOException;

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
        fxTable.setItems(usuarios);
        usuarios.addAll(DataStore.getUsuarios().getActive(true));
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
    }

    @FXML
    private void fxBtnEliminarAction(ActionEvent actionEvent) {
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
            fxTable.refresh();
        }
    }

    @FXML
    private void fxBtnRefreshAction(ActionEvent actionEvent) {
        fxTable.refresh();
    }

    @FXML
    private void fxBtnShowHideAction(ActionEvent actionEvent) {
        if (showInactive) {
            showInactive = false;
            usuarios.clear();
            usuarios.addAll(DataStore.getUsuarios().getActive(true));
            fxBtnShowHide.setText("Todos");
        } else {
            showInactive = true;
            usuarios.clear();
            usuarios.addAll(DataStore.getUsuarios().getCache().values());
            fxBtnShowHide.setText("Activos");
        }
    }
}
