package tiendaclub.control.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.checkerframework.checker.nullness.qual.NonNull;
import tiendaclub.data.DataStore;
import tiendaclub.data.SessionStore;
import tiendaclub.model.models.Acceso;
import tiendaclub.model.models.Usuario;
import tiendaclub.view.FXMLStage;
import tiendaclub.view.FxDialogs;

public class UsuarioEditorPaneControl extends EditorControl<Usuario> {

    @FXML
    private TextField fxUsername;
    @FXML
    private TextField fxNombre;
    @FXML
    private TextField fxTelefono;
    @FXML
    private TextField fxEmail;
    @FXML
    private ComboBox<Acceso> fxCbxAcceso;
    @FXML
    private TextArea fxDireccion;
    @FXML
    private TextArea fxDescripcion;
    @FXML
    private TextField fxId;
    @FXML
    private Button fxButtonPassword;
    @FXML
    private CheckBox fxCheckActivo;

    public static Pane loadFXML() {
        return FXMLStage.getPane("/fxml/editor/UserEditorPane.fxml");
    }


    @FXML
    void initialize() {
        fxCbxAcceso.getItems().addAll(DataStore.getAccesos().getIndexId().getCacheValues());
        fxCbxAcceso.getSelectionModel().select(0);
        fxButtonPassword.setVisible(false);
    }

    @Override
    public void setEditee(@NonNull Usuario editee) {
        fxUsername.setEditable(false);
        fxButtonPassword.setVisible(true);
        super.setEditee(editee);
    }

    @Override
    protected void updateEditee() {
        editee.setNombre(getTextOrNull(fxNombre));
        editee.setTelefono(getTextOrNull(fxTelefono));
        editee.setEmail(getTextOrNull(fxEmail));
        editee.setDireccion(getTextOrNull(fxDireccion));
        editee.setDescripcion(getTextOrNull(fxDescripcion));
        editee.setAcceso(fxCbxAcceso.getSelectionModel().getSelectedItem());
        editee.setActivo(fxCheckActivo.isSelected());
    }

    @Override
    protected Usuario buildNew() {
        String pass = askPass();
        editee = new Usuario(getTextOrNull(fxUsername), fxCbxAcceso.getSelectionModel().getSelectedItem());
        updateEditee();
        return editee;
    }

    @Override
    protected void setFields() {
        fxId.setText((editee.getId() + ""));
        fxUsername.setText(getNotNullText(editee.getUsername()));
        fxNombre.setText(getNotNullText(editee.getNombre()));
        fxTelefono.setText(getNotNullText(editee.getTelefono()));
        fxEmail.setText(getNotNullText(editee.getEmail()));
        fxDireccion.setText(getNotNullText(editee.getDireccion()));
        fxDescripcion.setText(getNotNullText(editee.getDescripcion()));
        fxCbxAcceso.getSelectionModel().select(editee.getAcceso());
        fxCheckActivo.setSelected(editee.isActivo());
    }

    protected boolean validFields() {
        if (fxUsername.getText().trim().length() < 1) {
            return false;
        }
        return fxCbxAcceso.getSelectionModel().getSelectedItem().getId() >= SessionStore.getUsuario().getIdAcceso();
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
