package app.control.editor;

import app.data.DataStore;
import app.model.Caja;
import app.model.Sede;
import app.model.Socio;
import app.model.Usuario;
import app.model.Venta;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import jfxtras.scene.control.LocalDateTimeTextField;

public class VentaEditorControl extends GridControl<Venta> {

    @FXML
    public ComboBox<Usuario> fxBoxUsuario;
    @FXML
    public ComboBox<Sede> fxBoxSede;
    @FXML
    public ComboBox<Caja> fxBoxCaja;
    @FXML
    public ComboBox<Socio> fxBoxSocio;
    @FXML
    public LocalDateTimeTextField fxDateFechHora;
    @FXML
    private TextField fxId;

    @FXML
    void initialize() {

        fxBoxUsuario.getItems().addAll(DataStore.getSessionStore().getUsuarios().getAllCache());
        fxBoxSede.getItems().addAll(DataStore.getSessionStore().getSedes().getAllCache());
        fxBoxSede.setOnAction(event -> fxBoxCaja.getItems().setAll(fxBoxSede.getSelectionModel().getSelectedItem().getCajas()));
        fxBoxSocio.getItems().addAll(DataStore.getSessionStore().getSocios().getAllCache());

    }

    @Override
    public void updateEditee(Venta editee) {
        editee.setUsuario(fxBoxUsuario.getSelectionModel().getSelectedItem());
        editee.setCaja(fxBoxCaja.getSelectionModel().getSelectedItem());
        editee.setSocio(fxBoxSocio.getSelectionModel().getSelectedItem());
        editee.setFechahora(fxDateFechHora.getLocalDateTime());
    }

    @Override
    public Venta buildNew() {
        Venta editee = new Venta();
        updateEditee(editee);
        return editee;
    }

    @Override
    public void setFields(Venta editee) {
        if (editee.getId() > 0)
            fxId.setText((Integer.toString(editee.getId())));

        fxBoxUsuario.getSelectionModel().select(editee.getUsuario());
        fxBoxSede.getSelectionModel().select(editee.getCaja().getSede());
        fxBoxCaja.getItems().setAll(editee.getCaja().getSede().getCajas());
        fxBoxCaja.getSelectionModel().select(editee.getCaja());
        fxBoxSocio.getSelectionModel().select(editee.getSocio());
        fxDateFechHora.setLocalDateTime(editee.getFechahora());
    }

    @Override
    public boolean validFields() {
        return fxBoxUsuario.getSelectionModel().getSelectedItem() != null &&
               fxBoxCaja.getSelectionModel().getSelectedItem() != null &&
               fxBoxSocio.getSelectionModel().getSelectedItem() != null &&
               fxDateFechHora.getText().length() > 0;
    }
}
