package app.control.editor;

import app.data.DataStore;
import app.misc.StaticHelpers;
import app.model.Compra;
import app.model.Comprado;
import app.model.Producto;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CompradoEditorControl extends GridControl<Comprado> {


    @FXML
    private TextField fxFieldCantidad;
    @FXML
    private TextField fxId;
    @FXML
    private TextField fxFieldPrecioUnidad;
    @FXML
    private ComboBox<Compra> fxBoxCompra;
    @FXML
    private ComboBox<Producto> fxBoxProducto;

    @FXML
    void initialize() {
        fxBoxCompra.getItems().addAll(DataStore.getSessionStore().getCompras().getAllCache());
        fxBoxProducto.getItems().addAll(DataStore.getSessionStore().getProductos().getAllCache());
    }


    @Override
    public void updateEditee(Comprado editee) {
        editee.setCompra(fxBoxCompra.getSelectionModel().getSelectedItem());
        editee.setProducto(fxBoxProducto.getSelectionModel().getSelectedItem());
        editee.setCantidad(Integer.parseInt(fxFieldCantidad.getText()));
        editee.setPrecioUnidad(Integer.parseInt(fxFieldPrecioUnidad.getText()));
    }

    @Override
    public Comprado buildNew() {
        Comprado editee = new Comprado();
        updateEditee(editee);
        return editee;
    }

    @Override
    public void setFields(Comprado editee) {
        if (editee.getId() > 0)
            fxId.setText((Integer.toString(editee.getId())));
        fxBoxCompra.getSelectionModel().select(editee.getCompra());
        fxBoxProducto.getSelectionModel().select(editee.getProducto());
        fxFieldCantidad.setText(Integer.toString(editee.getCantidad()));
        fxFieldPrecioUnidad.setText(Integer.toString(editee.getPrecioUnidad()));
    }

    @Override
    public boolean validFields() {
        return fxBoxCompra.getSelectionModel().getSelectedItem() != null &&
               fxBoxProducto.getSelectionModel().getSelectedItem() != null &&
               StaticHelpers.isInteger(fxFieldCantidad.getText()) &&
               Integer.parseInt(fxFieldCantidad.getText()) > 0 &&
               StaticHelpers.isInteger(fxFieldPrecioUnidad.getText()) &&
               Integer.parseInt(fxFieldPrecioUnidad.getText()) > 0;
    }
}
