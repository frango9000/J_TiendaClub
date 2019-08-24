package tiendaclub.control.editor;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import tiendaclub.misc.StaticHelpers;
import tiendaclub.misc.TabTraversalEventHandler;
import tiendaclub.model.models.Proveedor;

public class ProveedorEditorControl extends GridControl<Proveedor> {

    @FXML
    public TextField fxNif;
    @FXML
    public TextField fxEmail;
    @FXML
    public TextArea fxDescripcion;
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

    public static EditorControl<Proveedor> getPane() {
        EditorControl<Proveedor> sedeControl = new EditorControl<>();
        FXMLLoader loader = new FXMLLoader(EditorControl.class.getResource("/fxml/editor/ProveedorEditorGridPane.fxml"));
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
        fxDescripcion.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversalEventHandler());
        fxDireccion.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversalEventHandler());

    }


    @Override
    public void updateEditee(Proveedor editee) {
        editee.setNif(StaticHelpers.getTextOrNull(fxNif));
        editee.setNombre(StaticHelpers.getTextOrNull(fxNombre));
        editee.setTelefono(StaticHelpers.getTextOrNull(fxTelefono));
        editee.setEmail(StaticHelpers.getTextOrNull(fxEmail));
        editee.setDireccion(StaticHelpers.getTextOrNull(fxDireccion));
        editee.setDescripcion(StaticHelpers.getTextOrNull(fxDescripcion));
        editee.setActivo(fxCheckActivo.isSelected());
    }

    @Override
    public Proveedor buildNew() {
        Proveedor editee = new Proveedor(fxNif.getText().trim());
        updateEditee(editee);
        return editee;
    }

    @Override
    public void setFields(Proveedor editee) {
        if (editee.getId() > 0)
            fxId.setText((editee.getId() + ""));
        fxNif.setText(StaticHelpers.getNotNullText(editee.getNif()));
        fxNombre.setText(StaticHelpers.getNotNullText(editee.getNombre()));
        fxTelefono.setText(StaticHelpers.getNotNullText(editee.getTelefono()));
        fxEmail.setText(StaticHelpers.getNotNullText(editee.getEmail()));
        fxDireccion.setText(StaticHelpers.getNotNullText(editee.getDireccion()));
        fxDescripcion.setText(StaticHelpers.getNotNullText(editee.getDescripcion()));
        fxCheckActivo.setSelected(editee.isActivo());
    }

    @Override
    public boolean validFields() {
        return fxNombre.getText().trim().length() > 0 && fxNif.getText().trim().length() > 0;
    }
}
