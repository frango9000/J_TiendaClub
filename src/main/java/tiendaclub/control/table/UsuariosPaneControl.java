package tiendaclub.control.table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import tiendaclub.MainFX;
import tiendaclub.control.editor.UsuarioEditorPaneControl;
import tiendaclub.data.DataStore;
import tiendaclub.model.models.Acceso;
import tiendaclub.model.models.Usuario;
import tiendaclub.view.FXMLStage;

import java.io.IOException;

public class UsuariosPaneControl extends BorderPane {

    final private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();

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

    public static Parent getRoot() {
        return FXMLStage.getRoot("/fxml/tables/UsuariosPane.fxml");
    }

    @FXML
    public void initialize() {
        fxColumnId.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("id"));
        fxColumnUser.setCellValueFactory(new PropertyValueFactory<Usuario, String>("username"));
        fxColumnName.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nombre"));
        fxColumnLevel.setCellValueFactory(new PropertyValueFactory<Usuario, Acceso>("acceso"));
        fxTable.setItems(usuarios);
        usuarios.addAll(DataStore.getUsuarios().getCache().values());
    }

    @FXML
    private void fxButtonBackAction(ActionEvent actionEvent) {
        ((BorderPane) MainFX.getMainStage().getScene().getRoot()).setCenter(null);
    }

    @FXML
    private void fxButtonAddAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editor/UserEditorPane.fxml"));
        BorderPane pane = loader.load();
        UsuarioEditorPaneControl control = loader.getController();
        FXMLStage stage = new FXMLStage(pane, "Usuario");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainFX.getMainStage());
        stage.showAndWait();
    }

    @FXML
    private void fxButtonDeleteAction(ActionEvent actionEvent) {
    }

    @FXML
    private void fxButtonEditAction(ActionEvent actionEvent) throws IOException {
        Usuario selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editor/UserEditorPane.fxml"));
            BorderPane pane = loader.load();
            UsuarioEditorPaneControl control = loader.getController();
            control.setUsuario(selected);
            FXMLStage stage = new FXMLStage(pane, "Usuario");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(MainFX.getMainStage());
            stage.showAndWait();
        } else actionEvent.consume();

    }

    @FXML
    private void fxButtonRefreshAction(ActionEvent actionEvent) {
    }
}
