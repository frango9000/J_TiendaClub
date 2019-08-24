package tiendaclub.control.editor;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import tiendaclub.data.DataStore;
import tiendaclub.misc.StaticHelpers;
import tiendaclub.misc.TabTraversalEventHandler;
import tiendaclub.model.models.Categoria;
import tiendaclub.model.models.Producto;

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


    public static EditorControl<Producto> getPane() {
        EditorControl<Producto> control = new EditorControl<>();
        FXMLLoader loader = new FXMLLoader(EditorControl.class.getResource("/fxml/editor/ProductoEditorGridPane.fxml"));
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
        fxCbxCategoria.getItems().addAll(DataStore.getCategorias().getIndexId().getCacheValues());
        fxDescripcion.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversalEventHandler());
    }

    @Override
    public void updateEditee(Producto editee) {
        editee.setNombre(StaticHelpers.getTextOrNull(fxNombre));
        editee.setPrecioVenta(Integer.parseInt(fxPrecio.getText()));
        editee.setIva(fxIva.getText().trim().length() > 0 ? Integer.parseInt(fxIva.getText().trim()) : 0);
        editee.setCategoria(fxCbxCategoria.getSelectionModel().getSelectedItem());
        editee.setDescripcion(StaticHelpers.getTextOrNull(fxDescripcion));
        editee.setActivo(fxCheckActivo.isSelected());
    }

    @Override
    public Producto buildNew() {
        Producto editee = new Producto(StaticHelpers.getTextOrNull(fxNombre), fxCbxCategoria.getSelectionModel()
                                                                                            .getSelectedItem());
        updateEditee(editee);
        return editee;
    }

    @Override
    public void setFields(Producto editee) {
        if (editee.getId() > 0) {
            fxId.setText((editee.getId() + ""));
        }
        fxNombre.setText(StaticHelpers.getNotNullText(editee.getNombre()));
        fxPrecio.setText(Integer.toString(editee.getPrecioVenta()));
        fxIva.setText(Integer.toString(editee.getIva()));
        fxCbxCategoria.getSelectionModel().select(editee.getCategoria());
        fxDescripcion.setText(StaticHelpers.getNotNullText(editee.getDescripcion()));
        fxCheckActivo.setSelected(editee.isActivo());
    }

    public boolean validFields() {
        return fxNombre.getText().trim().length() > 0 &&
               fxPrecio.getText().trim().length() > 0 &&
               fxCbxCategoria.getSelectionModel().getSelectedItem() != null &&
               StaticHelpers.isInteger(fxPrecio.getText().trim());
    }

}
