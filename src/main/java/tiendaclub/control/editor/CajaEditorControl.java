package tiendaclub.control.editor;

import com.google.common.base.Strings;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tiendaclub.data.DataStore;
import tiendaclub.misc.StaticHelpers;
import tiendaclub.model.Caja;
import tiendaclub.model.Sede;

public class CajaEditorControl extends GridControl<Caja> {

    @FXML
    public ComboBox<Sede> fxCbxSede;
    @FXML
    public TextField fxNombre;
    @FXML
    private TextField fxId;
    @FXML
    private CheckBox fxCheckActivo;


    @FXML
    void initialize() {
        fxCbxSede.getItems().addAll(DataStore.getSedes().getById().getCacheValues());
        fxCbxSede.getSelectionModel().select(0);
    }

    @Override
    public void updateEditee(Caja editee) {
        editee.setSede(fxCbxSede.getSelectionModel().getSelectedItem());
        editee.setNombre(StaticHelpers.textInputEmptyToNull(fxNombre));
        editee.setActivo(fxCheckActivo.isSelected());
    }

    @Override
    public Caja buildNew() {
        Caja editee = new Caja(fxCbxSede.getSelectionModel()
                                        .getSelectedItem(), StaticHelpers.textInputEmptyToNull(fxNombre));
        editee.setActivo(fxCheckActivo.isSelected());
        return editee;
    }

    @Override
    public void setFields(Caja editee) {
        if (editee.getId() > 0)
            fxId.setText((Integer.toString(editee.getId())));
        fxCbxSede.getSelectionModel().select(editee.getSede());
        fxNombre.setText(Strings.nullToEmpty(editee.getNombre()));
        fxCheckActivo.setSelected(editee.isActivo());
    }

    @Override
    public boolean validFields() {
        return fxNombre.getText().trim().length() > 1 && fxCbxSede.getSelectionModel().getSelectedItem() != null;
    }
}
