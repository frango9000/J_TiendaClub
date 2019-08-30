package tiendaclub.control.editor;

import com.google.common.base.Strings;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import tiendaclub.misc.StaticHelpers;
import tiendaclub.model.Categoria;

public class CategoriaEditorControl extends GridControl<Categoria> {

    @FXML
    public TextField fxNombre;
    @FXML
    private TextField fxId;
    @FXML
    private CheckBox fxCheckActivo;

    @FXML
    void initialize() {
    }

    @Override
    public void updateEditee(Categoria editee) {
        editee.setNombre(StaticHelpers.textInputEmptyToNull(fxNombre));
        editee.setActivo(fxCheckActivo.isSelected());
    }

    @Override
    public Categoria buildNew() {
        Categoria editee = new Categoria(StaticHelpers.textInputEmptyToNull(fxNombre));
        editee.setActivo(fxCheckActivo.isSelected());
        return editee;
    }

    @Override
    public void setFields(Categoria editee) {
        if (editee.getId() > 0)
            fxId.setText((Integer.toString(editee.getId())));
        fxNombre.setText(Strings.nullToEmpty(editee.getNombre()));
        fxCheckActivo.setSelected(editee.isActivo());
    }

    @Override
    public boolean validFields() {
        return fxNombre.getText().trim().length() > 1;
    }
}
