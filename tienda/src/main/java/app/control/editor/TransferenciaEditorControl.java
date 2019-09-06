package app.control.editor;

import app.data.DataStore;
import app.misc.StaticHelpers;
import app.model.Categoria;
import app.model.Producto;
import app.model.Sede;
import app.model.Transferencia;
import app.model.Usuario;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import jfxtras.scene.control.LocalDateTimeTextField;

public class TransferenciaEditorControl extends GridControl<Transferencia> {

    @FXML
    public ComboBox<Usuario> fxBoxUsuario;
    @FXML
    public ComboBox<Sede> fxBoxSedeOrigen;
    @FXML
    public ComboBox<Sede> fxBoxSedeDestino;
    @FXML
    public ComboBox<Producto> fxBoxProducto;
    @FXML
    public ComboBox<Categoria> fxBoxCategoria;
    @FXML
    public TextField fxCantidad;
    @FXML
    public LocalDateTimeTextField fxFieldDate;
    @FXML
    private TextField fxId;

    @FXML
    void initialize() {
        fxBoxUsuario.getItems().addAll(DataStore.getSessionStore().getUsuarios().getAllCache());
        fxBoxUsuario.getSelectionModel().select(DataStore.getSessionStore().getUsuario());

        fxBoxSedeOrigen.getItems().addAll(DataStore.getSessionStore().getSedes().getAllCache());
        fxBoxSedeDestino.getItems().addAll(DataStore.getSessionStore().getSedes().getAllCache());

        fxBoxCategoria.getItems().addAll(DataStore.getSessionStore().getCategorias().getAllCache());
        fxBoxCategoria.setOnAction(event -> fxBoxProducto.setItems(FXCollections.observableList(new ArrayList<>(fxBoxCategoria.getSelectionModel().getSelectedItem().getProductos()))));

        fxBoxProducto.getItems().addAll(DataStore.getSessionStore().getProductos().getAllCache());
    }

    @Override
    public void updateEditee(Transferencia editee) {
        editee.setUsuario(fxBoxUsuario.getSelectionModel().getSelectedItem());
        editee.setSedeOrigen(fxBoxSedeOrigen.getSelectionModel().getSelectedItem());
        editee.setSedeDestino(fxBoxSedeDestino.getSelectionModel().getSelectedItem());
        editee.setProducto(fxBoxProducto.getSelectionModel().getSelectedItem());
        editee.setCantidad(Integer.parseInt(fxCantidad.getText()));
        editee.setFechahora(fxFieldDate.getText().length() < 1 ? LocalDateTime.now() : fxFieldDate.getLocalDateTime());
    }

    @Override
    public Transferencia buildNew() {
        return new Transferencia(fxBoxUsuario.getSelectionModel().getSelectedItem(),
                                 fxBoxSedeOrigen.getSelectionModel().getSelectedItem(),
                                 fxBoxSedeDestino.getSelectionModel().getSelectedItem(),
                                 fxBoxProducto.getSelectionModel().getSelectedItem(),
                                 Integer.parseInt(fxCantidad.getText()),
                                 (fxFieldDate.getText().length() < 1 ? LocalDateTime.now() : fxFieldDate.getLocalDateTime()));
    }

    @Override
    public void setFields(Transferencia editee) {
        if (editee.getId() > 0)
            fxId.setText((Integer.toString(editee.getId())));
        fxBoxUsuario.getSelectionModel().select(editee.getUsuario());
        fxBoxSedeOrigen.getSelectionModel().select(editee.getSedeOrigen());
        fxBoxSedeDestino.getSelectionModel().select(editee.getSedeDestino());
        fxBoxProducto.getSelectionModel().select(editee.getProducto());
        fxBoxCategoria.getSelectionModel().select(editee.getProducto().getCategoria());
        fxCantidad.setText(editee.getCantidad() + "");
        fxFieldDate.setLocalDateTime(editee.getFechahora());
    }

    @Override
    public boolean validFields() {
        return fxBoxUsuario.getSelectionModel().getSelectedItem() != null &&
               fxBoxSedeOrigen.getSelectionModel().getSelectedItem() != null &&
               fxBoxSedeDestino.getSelectionModel().getSelectedItem() != null &&
               !fxBoxSedeOrigen.getSelectionModel().getSelectedItem().getId().equals(fxBoxSedeDestino.getSelectionModel().getSelectedItem().getId()) &&
               fxBoxProducto.getSelectionModel().getSelectedItem() != null &&
               fxCantidad.getText().trim().length() > 0 &&
               StaticHelpers.isInteger(fxCantidad.getText().trim()) &&
               Integer.parseInt(fxCantidad.getText().trim()) > 0;
    }
}
