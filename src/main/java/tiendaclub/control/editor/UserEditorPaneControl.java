/**
 * Sample Skeleton for 'UserEditorPane.fxml' Controller Class
 */

package tiendaclub.control.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tiendaclub.data.DataStore;
import tiendaclub.model.models.Acceso;
import tiendaclub.model.models.Usuario;
import tiendaclub.view.FxDialogs;

public class UserEditorPaneControl extends Stage {

    private Usuario usuario;

    @FXML
    private TextField txUsername;
    @FXML
    private TextField txNombre;
    @FXML
    private TextField txTelefono;
    @FXML
    private TextField txEmail;
    @FXML
    private ComboBox<Acceso> cbAcceso;
    @FXML
    private TextArea txDireccion;
    @FXML
    private TextArea txDescripcion;
    @FXML
    private TextField txId;
    @FXML
    private Button txButtonPassword;
    @FXML
    private CheckBox fxCheckActivo;

    @FXML
    void SaveOnAct(ActionEvent event) {
        if (txId.getText().length() < 1) {
            if (validFields()) {
                String pass = askPass();
                Usuario usuario = new Usuario(txUsername.getText().trim(), pass, cbAcceso.getSelectionModel().getSelectedItem(), fxCheckActivo.isSelected());
                usuario.setNombre(txUsername.getText().trim());
                usuario.setTelefono(txTelefono.getText().trim());
                usuario.setEmail(txEmail.getText().trim());
                usuario.setDireccion(txDireccion.getText().trim());
                usuario.setDescripcion(txDescripcion.getText().trim());
                if (usuario.insertIntoDB() > 0) {
                    FxDialogs.showInfo("Success", "Usuario insertado");
                    ((Node) event.getSource()).getScene().getWindow().hide();
                } else {
                    FxDialogs.showError("Fail!", "Insercion rechazada");
                }
            }
        } else {
            usuario.setNombre((txUsername.getText() + "").trim());
            usuario.setTelefono((txTelefono.getText() + "").trim());
            usuario.setEmail((txEmail.getText() + "").trim());
            usuario.setDireccion((txDireccion.getText() + "").trim());
            usuario.setDescripcion((txDescripcion.getText() + "").trim());
            usuario.setAcceso(cbAcceso.getSelectionModel().getSelectedItem());
            usuario.setActivo(fxCheckActivo.isSelected());
            if (usuario.updateOnDb() > 0) {
                FxDialogs.showInfo("Success", "Usuario modificado");
                ((Node) event.getSource()).getScene().getWindow().hide();
            } else {
                FxDialogs.showError("Fail!", "Modificacion rechazada");
            }
        }

    }

    @FXML
    void passwordOnAct(ActionEvent event) {
        String pass = askPass();
        if (!pass.equals(usuario.getPass())) {
            usuario.setPass(pass);
            usuario.updateOnDb();
        }

    }

    @FXML
    private void discardAct(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }

    @FXML
    void initialize() {
        cbAcceso.getItems().addAll(DataStore.getAccesos().getCache().values());
        txButtonPassword.setVisible(false);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (usuario != null) {
            txUsername.setEditable(false);
            txButtonPassword.setVisible(true);
            txId.setText(usuario.getId() + "");
            txUsername.setText(usuario.getUsername());
            txNombre.setText(usuario.getNombre());
            txTelefono.setText(usuario.getTelefono());
            txEmail.setText(usuario.getEmail());
            txDireccion.setText(usuario.getDireccion());
            txDescripcion.setText(usuario.getDescripcion());
            cbAcceso.getSelectionModel().select(usuario.getAcceso());
        }
    }

    private boolean validFields() {
        if (txUsername.getText().trim().length() < 1) return false;
        return cbAcceso.getSelectionModel().getSelectedItem().getId() >= DataStore.getUser().getIdAcceso();
    }

    public String askPass() {
        String pass1;
        String pass2;
        do {
            pass1 = FxDialogs.showTextInput("Enter password", "Enter password");
            pass2 = FxDialogs.showTextInput("Verify password", "Verify password");
        } while (!pass1.equals(pass2) && pass1.length() < 1);
        return pass1;
    }

}
