package tiendaclub.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import tiendaclub.data.DataStore;
import tiendaclub.model.models.Acceso;
import tiendaclub.model.models.Usuario;

import java.net.URL;
import java.util.ResourceBundle;

public class UsuariosPaneControl implements Initializable {

    final public ObservableList<Usuario> usuarios = FXCollections.observableArrayList();

    @FXML
    public static BorderPane usersPane;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
}
