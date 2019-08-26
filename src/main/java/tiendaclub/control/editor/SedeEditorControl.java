package tiendaclub.control.editor;

import com.google.common.base.Strings;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import tiendaclub.misc.StaticHelpers;
import tiendaclub.misc.TabTraversalEventHandler;
import tiendaclub.model.models.Sede;

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

    public static EditorControl<Sede> getPane() {
        EditorControl<Sede> sedeControl = new EditorControl<>();
        FXMLLoader loader = new FXMLLoader(EditorControl.class.getResource("/fxml/editor/SedeEditorGridPane.fxml"));
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
