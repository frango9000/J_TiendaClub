package tiendaclub.control.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import tiendaclub.data.DataStore;
import tiendaclub.data.SessionStore;
import tiendaclub.model.models.Acceso;
import tiendaclub.model.models.Usuario;
import tiendaclub.view.FXMLStage;
import tiendaclub.view.FxDialogs;

public class UsuarioEditorPaneControl extends EditorControl<Usuario> {

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

    public static Pane loadFXML() {
        return FXMLStage.getPane("/fxml/editor/UserEditorPane.fxml");
    }


    @FXML
    void initialize() {
        cbAcceso.getItems().addAll(DataStore.getAccesos().getCache().values());
        cbAcceso.getSelectionModel().select(0);
        txButtonPassword.setVisible(false);
    }

    @Override
    public void setEditee(Usuario editee) {
        this.editee = editee;
        if (editee != null) {
            creating = false;
            txUsername.setEditable(false);
            txButtonPassword.setVisible(true);
            setFields();
        }
    }

    @Override
    protected void updateEditee() {
        String txt = "";
        editee.setNombre((txt = txNombre.getText().trim()).length() > 0 ? txt : null);
        editee.setTelefono((txt = txTelefono.getText().trim()).length() > 0 ? txt : null);
        editee.setEmail((txt = txEmail.getText().trim()).length() > 0 ? txt : null);
        editee.setDireccion((txt = txDireccion.getText().trim()).length() > 0 ? txt : null);
        editee.setDescripcion((txt = txDescripcion.getText().trim()).length() > 0 ? txt : null);
        if (!creating) {
            editee.setAcceso(cbAcceso.getSelectionModel().getSelectedItem());
            editee.setActivo(fxCheckActivo.isSelected());
        }
    }

    @Override
    protected Usuario buildEditee() {
        String pass = askPass();
        editee = new Usuario(txUsername.getText().trim(), pass, cbAcceso.getSelectionModel().getSelectedItem(), fxCheckActivo.isSelected());
        updateEditee();
        return editee;
    }

    @Override
    protected void setFields() {
        String txt = "";
        txId.setText((txt = editee.getId() + "").length() > 0 ? txt : "");
        txUsername.setText((txt = editee.getUsername()) != null ? txt : "");
        txNombre.setText((txt = editee.getNombre()) != null ? txt : "");
        txTelefono.setText((txt = editee.getTelefono()) != null ? txt : "");
        txEmail.setText((txt = editee.getEmail()) != null ? txt : "");
        txDireccion.setText((txt = editee.getDireccion()) != null ? txt : "");
        txDescripcion.setText((txt = editee.getDescripcion()) != null ? txt : "");
        cbAcceso.getSelectionModel().select(editee.getAcceso());
        fxCheckActivo.setSelected(editee.isActivo());
    }

    protected boolean validFields() {
        if (txUsername.getText().trim().length() < 1) return false;
        return cbAcceso.getSelectionModel().getSelectedItem().getId() >= SessionStore.getUsuario().getIdAcceso();
    }

    @FXML
    void fxBtnPasswordAction(ActionEvent event) {
        String pass = askPass();
        if (!pass.equals(editee.getPass())) {
            editee.setPass(pass);
            editee.updateOnDb();
        }
    }

    private String askPass() {
        String pass1;
        String pass2;
        do {
            pass1 = FxDialogs.showTextInput("Enter password", "Enter password").trim();
            pass2 = FxDialogs.showTextInput("Verify password", "Verify password").trim();
        } while (!pass1.equals(pass2) && pass1.length() < 1);
        return pass1;
    }
}
