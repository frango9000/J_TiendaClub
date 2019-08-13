package tiendaclub.control.editor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import tiendaclub.model.models.Sede;
import tiendaclub.view.FXMLStage;

import java.io.IOException;

public class SedeEditorPaneControl extends EditorControl<Sede> {

    @FXML
    private TextField fxId;
    @FXML
    private CheckBox fxCheckActivo;
    @FXML
    private TextField fxNombre;
    @FXML
    private TextField fxTelefono;
    @FXML
    private TextArea fxDireccion;

    public static Pane getRoot() {
        String url = "/fxml/editor/SedeEditorPane.fxml";
        Pane root = null;
        try {
            root = FXMLLoader.load(FXMLStage.class.getResource(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    @FXML
    void initialize() {

    }

    @Override
    protected void updateEditee() {
        String txt = "";
        editee.setNombre(fxNombre.getText().trim());
        editee.setTelefono(getTextOrNull(fxTelefono));
        editee.setDireccion(getTextOrNull(fxDireccion));
        editee.setActivo(fxCheckActivo.isSelected());
    }

    @Override
    protected Sede buildEditee() {
        editee = new Sede(getTextOrNull(fxNombre), getTextOrNull(fxTelefono), getTextOrNull(fxDireccion));
        editee.setActivo(fxCheckActivo.isSelected());
        return editee;
    }

    @Override
    protected void setFields() {
        String txt = "";
        fxId.setText((editee.getId() + ""));
        fxNombre.setText(getNotNullText(editee.getNombre()));
        fxTelefono.setText(getNotNullText(editee.getTelefono()));
        fxDireccion.setText(getNotNullText(editee.getDireccion()));
        fxCheckActivo.setSelected(editee.isActivo());
    }

    @Override
    protected boolean validFields() {
        return (fxNombre.getText().trim().length() > 0);
    }
}
