package app.control.editor;

import app.data.DataStore;
import app.misc.StaticHelpers;
import app.model.Producto;
import app.model.Vendido;
import app.model.Venta;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class VendidoEditorControl extends GridControl<Vendido> {


    @FXML
    private TextField fxFieldCantidad;
    @FXML
    private TextField fxId;
    @FXML
    private TextField fxFieldPrecioUnidad;
    @FXML
    private ComboBox<Venta> fxBoxVenta;
    @FXML
    private ComboBox<Producto> fxBoxProducto;

    @FXML
    void initialize() {
        fxBoxVenta.getItems().addAll(DataStore.getSessionStore().getVentas().getAllCache());
        fxBoxProducto.getItems().addAll(DataStore.getSessionStore().getProductos().getAllCache());
    }


    @Override
    public void updateEditee(Vendido editee) {
        editee.setVenta(fxBoxVenta.getSelectionModel().getSelectedItem());
        editee.setProducto(fxBoxProducto.getSelectionModel().getSelectedItem());
        editee.setCantidad(Integer.parseInt(fxFieldCantidad.getText()));
        editee.setPrecioUnidad(Integer.parseInt(fxFieldPrecioUnidad.getText()));
    }

    @Override
    public Vendido buildNew() {
        Vendido editee = new Vendido();
        updateEditee(editee);
        return editee;
    }

    @Override
    public void setFields(Vendido editee) {
        if (editee.getId() > 0)
            fxId.setText((Integer.toString(editee.getId())));
        fxBoxVenta.getSelectionModel().select(editee.getVenta());
        fxBoxProducto.getSelectionModel().select(editee.getProducto());
        fxFieldCantidad.setText(Integer.toString(editee.getCantidad()));
        fxFieldPrecioUnidad.setText(Integer.toString(editee.getPrecioUnidad()));
    }

    @Override
    public boolean validFields() {
        return fxBoxVenta.getSelectionModel().getSelectedItem() != null &&
               fxBoxProducto.getSelectionModel().getSelectedItem() != null &&
               StaticHelpers.isInteger(fxFieldCantidad.getText()) &&
               Integer.parseInt(fxFieldCantidad.getText()) > 0 &&
               StaticHelpers.isInteger(fxFieldPrecioUnidad.getText()) &&
               Integer.parseInt(fxFieldPrecioUnidad.getText()) > 0;
    }
}
