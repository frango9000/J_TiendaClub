package app.control.editor;

import app.data.DataStore;
import app.misc.StaticHelpers;
import app.misc.TabTraversalEventHandler;
import app.model.Categoria;
import app.model.Producto;
import com.google.common.base.Strings;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class ProductoEditorControl extends GridControl<Producto> {


    @FXML
    private TextField fxId;
    @FXML
    private TextField fxNombre;
    @FXML
    private TextField fxPrecio;
    @FXML
    private TextField fxIva;
    @FXML
    private ComboBox<Categoria> fxCbxCategoria;
    @FXML
    private TextArea fxDescripcion;
    @FXML
    private CheckBox fxCheckActivo;


    @FXML
    void initialize() {
        fxCbxCategoria.getItems().addAll(DataStore.getCategorias().getById().getCacheValues());
        fxDescripcion.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversalEventHandler());
    }

    @Override
    public void updateEditee(Producto editee) {
        editee.setNombre(StaticHelpers.textInputEmptyToNull(fxNombre));
        editee.setPrecioVenta(Integer.parseInt(fxPrecio.getText()));
        editee.setIva(fxIva.getText().trim().length() > 0 ? Integer.parseInt(fxIva.getText().trim()) : 0);
        editee.setCategoria(fxCbxCategoria.getSelectionModel().getSelectedItem());
        editee.setDescripcion(StaticHelpers.textInputEmptyToNull(fxDescripcion));
        editee.setActivo(fxCheckActivo.isSelected());
    }

    @Override
    public Producto buildNew() {
        Producto editee = new Producto(StaticHelpers.textInputEmptyToNull(fxNombre), fxCbxCategoria.getSelectionModel()
                                                                                                   .getSelectedItem());
        updateEditee(editee);
        return editee;
    }

    @Override
    public void setFields(Producto editee) {
        if (editee.getId() > 0) {
            fxId.setText((Integer.toString(editee.getId())));
        }
        fxNombre.setText(Strings.nullToEmpty(editee.getNombre()));
        fxPrecio.setText(Integer.toString(editee.getPrecioVenta()));
        fxIva.setText(Integer.toString(editee.getIva()));
        fxCbxCategoria.getSelectionModel().select(editee.getCategoria());
        fxDescripcion.setText(Strings.nullToEmpty(editee.getDescripcion()));
        fxCheckActivo.setSelected(editee.isActivo());
    }

    public boolean validFields() {
        return fxNombre.getText().trim().length() > 0 &&
               fxPrecio.getText().trim().length() > 0 &&
               fxCbxCategoria.getSelectionModel().getSelectedItem() != null &&
               StaticHelpers.isInteger(fxPrecio.getText().trim());
    }

}
