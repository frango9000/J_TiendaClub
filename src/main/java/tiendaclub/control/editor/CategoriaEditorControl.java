package tiendaclub.control.editor;

import com.google.common.base.Strings;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import tiendaclub.misc.StaticHelpers;
import tiendaclub.model.models.Categoria;

public class CategoriaEditorControl extends GridControl<Categoria> {

    @FXML
    public TextField fxNombre;
    @FXML
    private TextField fxId;
    @FXML
    private CheckBox fxCheckActivo;

    public static EditorControl<Categoria> getPane() {
        EditorControl<Categoria> control = new EditorControl<>();
        FXMLLoader loader = new FXMLLoader(EditorControl.class.getResource("/fxml/editor/CategoriaEditorGridPane.fxml"));
        try {
            control.setGridPane(loader.load());
            control.setGridControl(loader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return control;
    }

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
