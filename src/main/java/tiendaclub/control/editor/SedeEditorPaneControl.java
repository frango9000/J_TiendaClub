package tiendaclub.control.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import tiendaclub.model.models.Sede;
import tiendaclub.view.FXMLStage;

import java.io.IOException;

public class SedeEditorPaneControl extends BorderPane {

    private Sede sede;

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

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
        if (sede != null) {
            fxId.setText(sede.getId() + "");
            fxNombre.setText(sede.getNombre() + "");
            fxTelefono.setText(sede.getTelefono() + "");
            fxDireccion.setText(sede.getDireccion() + "");
            fxCheckActivo.setSelected(sede.isActivo());
        }
    }

    @FXML
    void fxSaveAction(ActionEvent event) {

    }

    @FXML
    void fxDiscardAction(ActionEvent event) {

    }

}
