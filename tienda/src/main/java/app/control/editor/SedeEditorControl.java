package app.control.editor;

import app.misc.StaticHelpers;
import app.misc.TabTraversalEventHandler;
import app.model.Sede;
import com.google.common.base.Strings;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class SedeEditorControl extends GridControl<Sede> {

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
        fxDireccion.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversalEventHandler());
    }


    @Override
    public void updateEditee(Sede editee) {
        editee.setNombre(fxNombre.getText().trim());
        editee.setTelefono(StaticHelpers.textInputEmptyToNull(fxTelefono));
        editee.setDireccion(StaticHelpers.textInputEmptyToNull(fxDireccion));
        editee.setActivo(fxCheckActivo.isSelected());
    }

    @Override
    public Sede buildNew() {
        Sede editee = new Sede(fxNombre.getText().trim());
        updateEditee(editee);
        return editee;
    }

    @Override
    public void setFields(Sede editee) {
        if (editee.getId() > 0)
            fxId.setText((Integer.toString(editee.getId())));
        fxNombre.setText(Strings.nullToEmpty(editee.getNombre()));
        fxTelefono.setText(Strings.nullToEmpty(editee.getTelefono()));
        fxDireccion.setText(Strings.nullToEmpty(editee.getDireccion()));
        fxCheckActivo.setSelected(editee.isActivo());
    }

    @Override
    public boolean validFields() {
        return (fxNombre.getText().trim().length() > 0);
    }
}
