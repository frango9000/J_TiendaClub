package app.control.editor;

import app.data.DataStore;
import app.model.Caja;
import app.model.CierreZ;
import app.model.Sede;
import java.time.LocalDateTime;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import jfxtras.scene.control.LocalDateTimeTextField;

public class CierreZEditorControl extends GridControl<CierreZ> {


    @FXML
    public ComboBox<Sede> fxBoxSede;
    @FXML
    public ComboBox<Caja> fxBoxCaja;
    @FXML
    public LocalDateTimeTextField fxDateApertura;
    @FXML
    public LocalDateTimeTextField fxDateCierre;
    @FXML
    private TextField fxId;

    @FXML
    public void initialize() {
        fxBoxSede.getItems().addAll(DataStore.getSessionStore().getSedes().getAllCache());
        fxBoxSede.setOnAction(event -> fxBoxCaja.getItems().setAll(fxBoxSede.getSelectionModel().getSelectedItem().getCajas()));

    }


    @Override
    public void updateEditee(CierreZ editee) {
        editee.setCaja(fxBoxCaja.getSelectionModel().getSelectedItem());
        editee.setApertura(fxDateApertura.getText().length() < 1 ? LocalDateTime.now() : fxDateApertura.getLocalDateTime());
        editee.setCierre(fxDateCierre.getText().length() < 1 ? null : fxDateCierre.getLocalDateTime());
    }

    @Override
    public CierreZ buildNew() {
        CierreZ editee = new CierreZ(fxBoxCaja.getSelectionModel().getSelectedItem(),
                                     fxDateApertura.getText().length() < 1 ? LocalDateTime.now() : fxDateApertura.getLocalDateTime());
        updateEditee(editee);
        return editee;
    }

    @Override
    public void setFields(CierreZ editee) {
        if (editee.getId() > 0)
            fxId.setText((Integer.toString(editee.getId())));
        fxBoxSede.getSelectionModel().select(editee.getCaja().getSede());
        fxBoxCaja.getSelectionModel().select(editee.getCaja());
        fxDateApertura.setLocalDateTime(editee.getApertura());
        fxDateCierre.setLocalDateTime(editee.getCierre());
    }

    @Override
    public boolean validFields() {
        return fxBoxCaja.getSelectionModel().getSelectedItem() != null;
    }
}
