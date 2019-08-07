package tiendaclub.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import tiendaclub.MainFX;
import tiendaclub.control.editor.UserEditorPaneControl;
import tiendaclub.data.DataStore;
import tiendaclub.model.models.Acceso;
import tiendaclub.model.models.Usuario;
import tiendaclub.view.FxmlStage;

import java.io.IOException;

public class UsuariosPaneControl extends BorderPane {

    @FXML
    public static BorderPane usersPane;
    final public ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
    @FXML
    private TableView<Usuario> usuariosTable;
    @FXML
    private TableColumn<Usuario, Integer> tableColumnID;
    @FXML
    private TableColumn<Usuario, String> tableColumnUser;
    @FXML
    private TableColumn<Usuario, String> tableColumnName;
    @FXML
    private TableColumn<Usuario, Acceso> tableColumnLevel;

    @FXML
    public void initialize() {
        tableColumnID.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("id"));
        tableColumnUser.setCellValueFactory(new PropertyValueFactory<Usuario, String>("username"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nombre"));
        tableColumnLevel.setCellValueFactory(new PropertyValueFactory<Usuario, Acceso>("acceso"));
        usuariosTable.setItems(usuarios);
        usuarios.addAll(DataStore.getUsuarios().getCache().values());
    }


    @FXML
    private void usersBackAct(ActionEvent actionEvent) {
    }

    @FXML
    private void fxButtonBackAct(ActionEvent actionEvent) {
        ((BorderPane) MainFX.getMainStage().getScene().getRoot()).setCenter(null);
    }

    @FXML
    private void fxButtonAddAct(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editor/UserEditorPane.fxml"));
        BorderPane pane = loader.load();
        UserEditorPaneControl control = loader.getController();
        FxmlStage stage = new FxmlStage(pane, "Usuario");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainFX.getMainStage());
        stage.showAndWait();
    }

    @FXML
    private void fxButtonDeleteAct(ActionEvent actionEvent) {
    }

    @FXML
    private void fxButtonEditAct(ActionEvent actionEvent) throws IOException {
        Usuario selected = usuariosTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editor/UserEditorPane.fxml"));
            BorderPane pane = loader.load();
            UserEditorPaneControl control = loader.getController();
            control.setUsuario(selected);
            FxmlStage stage = new FxmlStage(pane, "Usuario");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(MainFX.getMainStage());
            stage.showAndWait();
        } else actionEvent.consume();

    }

    @FXML
    private void fxButtonRefreshAct(ActionEvent actionEvent) {
    }
}
