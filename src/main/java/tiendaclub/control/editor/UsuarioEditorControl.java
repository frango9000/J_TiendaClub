package tiendaclub.control.editor;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tiendaclub.data.DataStore;
import tiendaclub.data.SessionStore;
import tiendaclub.misc.StaticHelpers;
import tiendaclub.model.models.Acceso;
import tiendaclub.model.models.Usuario;
import tiendaclub.view.FxDialogs;

public class UsuarioEditorControl extends GridControl<Usuario> {


    @FXML
    private TextField fxId;
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
    private CheckBox fxCheckActivo;

    private MenuItem btnPassword;

    public static EditorControl<Usuario> getPane() {
        EditorControl<Usuario> userControl = new EditorControl<>();
        FXMLLoader loader = new FXMLLoader(UsuarioEditorControl.class.getResource("/fxml/editor/UsuarioEditorGridPane.fxml"));
        try {
            userControl.setGridPane(loader.load());
            userControl.setGridControl(loader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userControl;
    }

    @FXML
    void initialize() {
        btnPassword = new MenuItem("Password");
        btnPassword.setVisible(false);

        fxCbxAcceso.getItems().addAll(DataStore.getAccesos().getIndexId().getCacheValues());
        fxCbxAcceso.getSelectionModel().select(SessionStore.getUsuario().getAcceso());
    }

    @Override
    public void updateEditee(Usuario editee) {
        editee.setNombre(StaticHelpers.getTextOrNull(fxNombre));
        editee.setTelefono(StaticHelpers.getTextOrNull(fxTelefono));
        editee.setEmail(StaticHelpers.getTextOrNull(fxEmail));
        editee.setDireccion(StaticHelpers.getTextOrNull(fxDireccion));
        editee.setDescripcion(StaticHelpers.getTextOrNull(fxDescripcion));
        editee.setAcceso(fxCbxAcceso.getSelectionModel().getSelectedItem());
        editee.setActivo(fxCheckActivo.isSelected());
    }

    @Override
    public Usuario buildNew() {
        Usuario editee = new Usuario(StaticHelpers.getTextOrNull(fxUsername), fxCbxAcceso.getSelectionModel()
                                                                                         .getSelectedItem());
        editee.setPass(askPass());
        updateEditee(editee);
        return editee;
    }

    @Override
    public void setFields(Usuario editee) {
        if (editee.getId() > 0) {
            fxUsername.setEditable(false);
            btnPassword.setVisible(true);
            fxId.setText((editee.getId() + ""));
            fxButtonMenu.getItems().add(btnPassword);
            btnPassword.setOnAction(event -> fxBtnPasswordAction(editee));
        }
        fxUsername.setText(StaticHelpers.getNotNullText(editee.getUsername()));
        fxNombre.setText(StaticHelpers.getNotNullText(editee.getNombre()));
        fxTelefono.setText(StaticHelpers.getNotNullText(editee.getTelefono()));
        fxEmail.setText(StaticHelpers.getNotNullText(editee.getEmail()));
        fxDireccion.setText(StaticHelpers.getNotNullText(editee.getDireccion()));
        fxDescripcion.setText(StaticHelpers.getNotNullText(editee.getDescripcion()));
        fxCbxAcceso.getSelectionModel().select(editee.getAcceso());
        fxCheckActivo.setSelected(editee.isActivo());
    }

    public boolean validFields() {
        if (fxUsername.getText().trim().length() < 1) {
            return false;
        }
        return fxCbxAcceso.getSelectionModel().getSelectedItem().getId() >= SessionStore.getUsuario().getIdAcceso();
    }

    public void fxBtnPasswordAction(Usuario editee) {
        String pass = askPass();
        if (pass.length() > 4 && !pass.equals(editee.getPass())) {
            editee.setPass(pass);
        }
    }

    public String askPass() {
        String pass1 = FxDialogs.showTextInput("Enter password", "Enter password").trim();
        String pass2 = FxDialogs.showTextInput("Verify password", "Verify password").trim();
        if (pass1.equals(pass2) && pass1.length() > 4)
            return pass1;
        else
            FxDialogs.showInfo(null, "Invalid Password\nPasswords must be more than 4 digits.");
        return "";
    }
}
