package app.control.editor;

import app.misc.StaticHelpers;
import app.misc.TabTraversalEventHandler;
import app.model.Socio;
import com.google.common.base.Strings;
import java.time.LocalDateTime;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import jfxtras.scene.control.LocalDateTimeTextField;

public class SocioEditorControl extends GridControl<Socio> {

    @FXML
    public TextField fxDni;
    @FXML
    public TextField fxEmail;
    @FXML
    public TextArea fxDescripcion;
    @FXML
    public LocalDateTimeTextField fxPickerFechaIn;
    @FXML
    private TextField fxId;
    @FXML
    private TextField fxNombre;
    @FXML
    private TextField fxTelefono;
    @FXML
    private TextArea fxDireccion;
    @FXML
    private CheckBox fxCheckActivo;


    @FXML
    public void initialize() {
        fxDescripcion.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversalEventHandler());
        fxDireccion.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversalEventHandler());
    }


    @Override
    public void updateEditee(Socio editee) {
        editee.setDni(StaticHelpers.textInputEmptyToNull(fxDni));
        editee.setNombre(StaticHelpers.textInputEmptyToNull(fxNombre));
        editee.setTelefono(StaticHelpers.textInputEmptyToNull(fxTelefono));
        editee.setEmail(StaticHelpers.textInputEmptyToNull(fxEmail));
        editee.setDireccion(StaticHelpers.textInputEmptyToNull(fxDireccion));
        editee.setDescripcion(StaticHelpers.textInputEmptyToNull(fxDescripcion));
        editee.setFechaIn(fxPickerFechaIn.getText().length() < 1 ? LocalDateTime.now() : fxPickerFechaIn.getLocalDateTime());
        editee.setActive(fxCheckActivo.isSelected());
    }

    @Override
    public Socio buildNew() {
        Socio editee = new Socio(fxDni.getText().trim());
        updateEditee(editee);
        return editee;
    }

    @Override
    public void setFields(Socio editee) {
        if (editee.getId() > 0)
            fxId.setText((Integer.toString(editee.getId())));
        fxDni.setText(Strings.nullToEmpty(editee.getDni()));
        fxNombre.setText(Strings.nullToEmpty(editee.getNombre()));
        fxTelefono.setText(Strings.nullToEmpty(editee.getTelefono()));
        fxEmail.setText(Strings.nullToEmpty(editee.getEmail()));
        fxDireccion.setText(Strings.nullToEmpty(editee.getDireccion()));
        fxDescripcion.setText(Strings.nullToEmpty(editee.getDescripcion()));
        fxPickerFechaIn.setLocalDateTime(editee.getFechaIn());
        fxCheckActivo.setSelected(editee.isActive());
    }

    @Override
    public boolean validFields() {
        return fxNombre.getText().trim().length() > 0 && fxDni.getText().trim().length() > 0;
    }
}
