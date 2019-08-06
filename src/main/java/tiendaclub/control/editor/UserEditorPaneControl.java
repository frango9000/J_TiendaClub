/**
 * Sample Skeleton for 'UserEditorPane.fxml' Controller Class
 */

package tiendaclub.control.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import tiendaclub.data.DataStore;
import tiendaclub.model.models.Acceso;
import tiendaclub.model.models.Usuario;

import java.net.URL;
import java.util.ResourceBundle;

public class UserEditorPaneControl extends BorderPane {

    private Usuario usuario;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML // fx:id="txUsername"
    private TextField txUsername; // Value injected by FXMLLoader
    @FXML // fx:id="txNombre"
    private TextField txNombre; // Value injected by FXMLLoader
    @FXML // fx:id="txTelefono"
    private TextField txTelefono; // Value injected by FXMLLoader
    @FXML // fx:id="txEmail"
    private TextField txEmail; // Value injected by FXMLLoader
    @FXML // fx:id="cbAcceso"
    private ComboBox<Acceso> cbAcceso; // Value injected by FXMLLoader
    @FXML // fx:id="txDireccion"
    private TextArea txDireccion; // Value injected by FXMLLoader
    @FXML // fx:id="txDescrippcion"
    private TextArea txDescripcion; // Value injected by FXMLLoader
    @FXML // fx:id="txId"
    private TextField txId; // Value injected by FXMLLoader

    public UserEditorPaneControl(Usuario usuario) {
        this.usuario = usuario;
    }

    @FXML
    void SaveOnAct(ActionEvent event) {

    }

    @FXML
    void passwordOnAct(ActionEvent event) {

    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        if (usuario != null) {
            txId.setText(usuario.getId() + "");
            txNombre.setText(usuario.getNombre());
            txTelefono.setText(usuario.getTelefono());
            txEmail.setText(usuario.getEmail());
            txDireccion.setText(usuario.getDireccion());
            txDescripcion.setText(usuario.getDescripcion());
            cbAcceso.getItems().addAll(DataStore.getAccesos().getCache().values());
            cbAcceso.getSelectionModel().select(usuario.getAcceso());
        }

    }
}
