/**
 * Sample Skeleton for 'UserEditorPane.fxml' Controller Class
 */

package tiendaclub.control.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import tiendaclub.data.DataStore;
import tiendaclub.data.SessionStore;
import tiendaclub.model.models.Acceso;
import tiendaclub.model.models.Usuario;
import tiendaclub.view.FXMLStage;
import tiendaclub.view.FxDialogs;

import java.io.IOException;

public class UsuarioEditorPaneControl extends BorderPane {

    private Usuario usuario;
    private boolean creating = true;

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

    private static UsuarioEditorPaneControl controller;

    public static UsuarioEditorPaneControl getController() {
        return controller;
    }

    public static Pane loadFXML() {
        String url = "/fxml/editor/UserEditorPane.fxml";
        Pane root = null;
        try {
            root = FXMLLoader.load(FXMLStage.class.getResource(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    @FXML
    void initialize() {
        controller = this;
        cbAcceso.getItems().addAll(DataStore.getAccesos().getCache().values());
        cbAcceso.getSelectionModel().select(0);
        txButtonPassword.setVisible(false);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (usuario != null) {
            creating = false;
            txUsername.setEditable(false);
            txButtonPassword.setVisible(true);
            String txt = "";
            txId.setText((txt = usuario.getId() + "").length() > 0 ? txt : "");
            txUsername.setText((txt = usuario.getUsername()) != null ? txt : "");
            txNombre.setText((txt = usuario.getNombre()) != null ? txt : "");
            txTelefono.setText((txt = usuario.getTelefono()) != null ? txt : "");
            txEmail.setText((txt = usuario.getEmail()) != null ? txt : "");
            txDireccion.setText((txt = usuario.getDireccion()) != null ? txt : "");
            txDescripcion.setText((txt = usuario.getDescripcion()) != null ? txt : "");
            cbAcceso.getSelectionModel().select(usuario.getAcceso());
            fxCheckActivo.setSelected(usuario.isActivo());
        }
    }

    private void updateUsuario(Usuario usuario) {
        String txt = "";
        usuario.setNombre((txt = txNombre.getText().trim()).length() > 0 ? txt : null);
        usuario.setTelefono((txt = txTelefono.getText().trim()).length() > 0 ? txt : null);
        usuario.setEmail((txt = txEmail.getText().trim()).length() > 0 ? txt : null);
        usuario.setDireccion((txt = txDireccion.getText().trim()).length() > 0 ? txt : null);
        usuario.setDescripcion((txt = txDescripcion.getText().trim()).length() > 0 ? txt : null);
        if (!creating) {
            usuario.setAcceso(cbAcceso.getSelectionModel().getSelectedItem());
            usuario.setActivo(fxCheckActivo.isSelected());
        }
    }


    @FXML
    void fxBtnSaveAction(ActionEvent event) {
        if (validFields()) {
            int rows = 0;
            if (creating) {
                String pass = askPass();
                usuario = new Usuario(txUsername.getText().trim(), pass, cbAcceso.getSelectionModel().getSelectedItem(), fxCheckActivo.isSelected());
                updateUsuario(usuario);
                rows = usuario.insertIntoDB();
            } else {
                updateUsuario(usuario);
                rows = usuario.updateOnDb();
            }
            if (rows > 0) {
                FxDialogs.showInfo("Success", "Usuario " + (creating ? "creado" : "modificado"));
                ((Node) event.getSource()).getScene().getWindow().hide();
            } else FxDialogs.showError("Fail!", "Usuario no " + (creating ? "creado" : "modificado"));
        } else FxDialogs.showError("Fail!", "Invalid Fields");
    }
    @FXML
    void fxBtnPasswordAction(ActionEvent event) {
        String pass = askPass();
        if (!pass.equals(usuario.getPass())) {
            usuario.setPass(pass);
            usuario.updateOnDb();
        }
    }

    @FXML
    private void fxBtnDiscardAction(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }

    private boolean validFields() {
        if (txUsername.getText().trim().length() < 1) return false;
        return cbAcceso.getSelectionModel().getSelectedItem().getId() >= SessionStore.getUsuario().getIdAcceso();
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
