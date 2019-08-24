package tiendaclub.control.editor;

import java.io.IOException;
import java.time.LocalDateTime;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jfxtras.scene.control.LocalDateTimeTextField;
import tiendaclub.misc.StaticHelpers;
import tiendaclub.model.models.Socio;

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

    public static EditorControl<Socio> getPane() {
        EditorControl<Socio> sedeControl = new EditorControl<>();
        FXMLLoader loader = new FXMLLoader(UsuarioEditorControl.class.getResource("/fxml/editor/SocioEditorGridPane.fxml"));
        try {
            sedeControl.setGridPane(loader.load());
            sedeControl.setGridControl(loader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sedeControl;
    }

    @FXML
    public void initialize() {
    }


    @Override
    public void updateEditee(Socio editee) {
        editee.setDni(StaticHelpers.getTextOrNull(fxDni));
        editee.setNombre(StaticHelpers.getTextOrNull(fxNombre));
        editee.setTelefono(StaticHelpers.getTextOrNull(fxTelefono));
        editee.setEmail(StaticHelpers.getTextOrNull(fxEmail));
        editee.setDireccion(StaticHelpers.getTextOrNull(fxDireccion));
        editee.setDescripcion(StaticHelpers.getTextOrNull(fxDescripcion));
        editee.setFechaIn(
            fxPickerFechaIn.getText().length() < 1 ? LocalDateTime.now() : fxPickerFechaIn.getLocalDateTime());
        editee.setActivo(fxCheckActivo.isSelected());
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
            fxId.setText((editee.getId() + ""));
        fxDni.setText(StaticHelpers.getNotNullText(editee.getDni()));
        fxNombre.setText(StaticHelpers.getNotNullText(editee.getNombre()));
        fxTelefono.setText(StaticHelpers.getNotNullText(editee.getTelefono()));
        fxEmail.setText(StaticHelpers.getNotNullText(editee.getEmail()));
        fxDireccion.setText(StaticHelpers.getNotNullText(editee.getDireccion()));
        fxDescripcion.setText(StaticHelpers.getNotNullText(editee.getDescripcion()));
        fxPickerFechaIn.setLocalDateTime(editee.getFechaIn());
        fxCheckActivo.setSelected(editee.isActivo());
    }

    @Override
    public boolean validFields() {
        return fxNombre.getText().trim().length() > 0 && fxDni.getText().trim().length() > 0;
    }
}
