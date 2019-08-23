package tiendaclub.control.editor;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tiendaclub.data.DataStore;
import tiendaclub.misc.StaticHelpers;
import tiendaclub.model.models.Caja;
import tiendaclub.model.models.Sede;

public class CajaEditorControl extends GridControl<Caja> {

    @FXML
    public ComboBox<Sede> fxCbxSede;
    @FXML
    public TextField fxNombre;
    @FXML
    private TextField fxId;
    @FXML
    private CheckBox fxCheckActivo;

    public static EditorControl<Caja> getPane() {
        EditorControl<Caja> userControl = new EditorControl<>();
        FXMLLoader loader = new FXMLLoader(UsuarioEditorControl.class.getResource("/fxml/editor/CajaEditorGridPane.fxml"));
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
        fxCbxSede.getItems().addAll(DataStore.getSedes().getIndexId().getCacheValues());
        fxCbxSede.getSelectionModel().select(0);
    }

    @Override
    public void updateEditee(Caja editee) {
        editee.setSede(fxCbxSede.getSelectionModel().getSelectedItem());
        editee.setNombre(StaticHelpers.getTextOrNull(fxNombre));
        editee.setActivo(fxCheckActivo.isSelected());
    }

    @Override
    public Caja buildNew() {
        Caja editee = new Caja(fxCbxSede.getSelectionModel().getSelectedItem(), StaticHelpers.getTextOrNull(fxNombre));
        editee.setActivo(fxCheckActivo.isSelected());
        return editee;
    }

    @Override
    public void setFields(Caja editee) {
        if (editee.getId() > 0)
            fxId.setText((editee.getId() + ""));
        fxCbxSede.getSelectionModel().select(editee.getSede());
        fxNombre.setText(StaticHelpers.getNotNullText(editee.getNombre()));
        fxCheckActivo.setSelected(editee.isActivo());
    }

    @Override
    public boolean validFields() {
        if (fxNombre.getText().trim().length() < 1) {
            return false;
        } else
            return fxCbxSede.getSelectionModel().getSelectedItem() != null;
    }
}
