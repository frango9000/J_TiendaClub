package app.control.editor;

import app.data.DataStore;
import app.model.Compra;
import app.model.Proveedor;
import app.model.Sede;
import app.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import jfxtras.scene.control.LocalDateTimeTextField;

public class CompraEditorControl extends GridControl<Compra> {

    @FXML
    public ComboBox<Usuario> fxBoxUsuario;
    @FXML
    public ComboBox<Sede> fxBoxSede;
    @FXML
    public ComboBox<Proveedor> fxBoxProveedor;
    @FXML
    public LocalDateTimeTextField fxDateFechHora;
    @FXML
    private TextField fxId;

    @FXML
    void initialize() {

        fxBoxUsuario.getItems().addAll(DataStore.getSessionStore().getUsuarios().getAllCache());
        fxBoxSede.getItems().addAll(DataStore.getSessionStore().getSedes().getAllCache());
        fxBoxProveedor.getItems().addAll(DataStore.getSessionStore().getProveedores().getAllCache());

    }

    @Override
    public void updateEditee(Compra editee) {
        editee.setUsuario(fxBoxUsuario.getSelectionModel().getSelectedItem());
        editee.setSede(fxBoxSede.getSelectionModel().getSelectedItem());
        editee.setProveedor(fxBoxProveedor.getSelectionModel().getSelectedItem());
        editee.setFechahora(fxDateFechHora.getLocalDateTime());
    }

    @Override
    public Compra buildNew() {
        Compra editee = new Compra();
        updateEditee(editee);
        return editee;
    }

    @Override
    public void setFields(Compra editee) {
        if (editee.getId() > 0)
            fxId.setText((Integer.toString(editee.getId())));
        fxBoxUsuario.getSelectionModel().select(editee.getUsuario());
        fxBoxSede.getSelectionModel().select(editee.getSede());
        fxBoxProveedor.getSelectionModel().select(editee.getProveedor());
        fxDateFechHora.setLocalDateTime(editee.getFechahora());
    }

    @Override
    public boolean validFields() {
        return fxBoxUsuario.getSelectionModel().getSelectedItem() != null &&
               fxBoxSede.getSelectionModel().getSelectedItem() != null &&
               fxBoxProveedor.getSelectionModel().getSelectedItem() != null &&
               fxDateFechHora.getText().length() > 0;
    }
}
