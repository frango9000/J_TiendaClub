package tiendaclub.control.editor;

import com.google.common.base.Strings;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import tiendaclub.data.DataStore;
import tiendaclub.data.SessionStore;
import tiendaclub.misc.StaticHelpers;
import tiendaclub.misc.TabTraversalEventHandler;
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


    @FXML
    void initialize() {
        btnPassword = new MenuItem("Password");
        btnPassword.setVisible(false);

        fxCbxAcceso.getItems().addAll(DataStore.getAccesos().getIndexId().getCacheValues());
        fxCbxAcceso.getSelectionModel().select(SessionStore.getUsuario().getAcceso());
        fxDireccion.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversalEventHandler());
        fxDescripcion.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversalEventHandler());
    }

    @Override
    public void updateEditee(Usuario editee) {
        editee.setNombre(StaticHelpers.textInputEmptyToNull(fxNombre));
        editee.setTelefono(StaticHelpers.textInputEmptyToNull(fxTelefono));
        editee.setEmail(StaticHelpers.textInputEmptyToNull(fxEmail));
        editee.setDireccion(StaticHelpers.textInputEmptyToNull(fxDireccion));
        editee.setDescripcion(StaticHelpers.textInputEmptyToNull(fxDescripcion));
        editee.setAcceso(fxCbxAcceso.getSelectionModel().getSelectedItem());
        editee.setActivo(fxCheckActivo.isSelected());
    }

    @Override
    public Usuario buildNew() {
        Usuario editee = new Usuario(StaticHelpers.textInputEmptyToNull(fxUsername), fxCbxAcceso.getSelectionModel()
                                                                                                .getSelectedItem());
        editee.setPass(askPass());
        updateEditee(editee);
        return editee;
    }

    @Override
    public void setFields(Usuario editee) {
        if (editee.getId() > 0) {
            fxId.setText((Integer.toString(editee.getId())));
            fxUsername.setEditable(false);
            btnPassword.setVisible(true);
            fxButtonMenu.getItems().add(btnPassword);
            btnPassword.setOnAction(event -> fxBtnPasswordAction(editee));
        }
        fxUsername.setText(Strings.nullToEmpty(editee.getUsername()));
        fxNombre.setText(Strings.nullToEmpty(editee.getNombre()));
        fxTelefono.setText(Strings.nullToEmpty(editee.getTelefono()));
        fxEmail.setText(Strings.nullToEmpty(editee.getEmail()));
        fxDireccion.setText(Strings.nullToEmpty(editee.getDireccion()));
        fxDescripcion.setText(Strings.nullToEmpty(editee.getDescripcion()));
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
