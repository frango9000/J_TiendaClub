package tiendaclub.control.editor;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tiendaclub.misc.StaticHelpers;
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
        FXMLLoader loader = new FXMLLoader(UsuarioEditorControl.class.getResource("/fxml/editor/SedeEditorGridPane.fxml"));
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
    public void updateEditee(Sede editee) {
        editee.setNombre(fxNombre.getText().trim());
        editee.setTelefono(StaticHelpers.getTextOrNull(fxTelefono));
        editee.setDireccion(StaticHelpers.getTextOrNull(fxDireccion));
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
            fxId.setText((editee.getId() + ""));
        fxNombre.setText(StaticHelpers.getNotNullText(editee.getNombre()));
        fxTelefono.setText(StaticHelpers.getNotNullText(editee.getTelefono()));
        fxDireccion.setText(StaticHelpers.getNotNullText(editee.getDireccion()));
        fxCheckActivo.setSelected(editee.isActivo());
    }

    @Override
    public boolean validFields() {
        return (fxNombre.getText().trim().length() > 0);
    }
}
