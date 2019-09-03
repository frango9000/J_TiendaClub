package app.control.table;

import app.control.editor.VentaControl;
import app.data.DataStore;
import app.data.casteldao.dao.DataSourceIdImpl;
import app.misc.FXMLStage;
import app.model.Caja;
import app.model.Socio;
import app.model.Usuario;
import app.model.Venta;
import java.io.IOException;
import java.time.LocalDateTime;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class VentasTableControl extends TableControl<Venta> {


    @Override
    public void initialize() {
        super.initialize();
        TableColumn<Venta, Usuario> fxColumnUsuario = new TableColumn<>("Usuario");
        fxColumnUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        fxTable.getColumns().add(fxColumnUsuario);

        TableColumn<Venta, Caja> fxColumnCaja = new TableColumn<>("Caja");
        fxColumnCaja.setCellValueFactory(new PropertyValueFactory<>("caja"));
        fxTable.getColumns().add(fxColumnCaja);

        TableColumn<Venta, Socio> fxColumnSocio = new TableColumn<>("Socio");
        fxColumnSocio.setCellValueFactory(new PropertyValueFactory<>("socio"));
        fxTable.getColumns().add(fxColumnSocio);

        TableColumn<Venta, LocalDateTime> fxColumnFechaHora = new TableColumn<>("Fecha");
        fxColumnFechaHora.setCellValueFactory(new PropertyValueFactory<>("fechahora"));
        fxTable.getColumns().add(fxColumnFechaHora);

        fxTable.setItems(listedObjects);
        addContent();
    }


    @Override
    protected String fxmlLocation() {
        return "/fxml/editor/ProductoEditorGridPane.fxml";
    }

    @Override
    protected DataSourceIdImpl<Venta> getDataOrigin() {
        return DataStore.getVentas();
    }

    @Override
    protected void fxBtnAddAction(ActionEvent actionEvent) throws IOException {
        VentaControl ventaControl = new VentaControl();
        FXMLStage stage = new FXMLStage(ventaControl, "Creator");
        stage.showAndWait();
        fxTable.refresh();
        addContent();
    }

    @Override
    protected void fxBtnEditAction(ActionEvent actionEvent) throws IOException {
        Venta selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            VentaControl ventaControl = new VentaControl(selected);
            FXMLStage stage = new FXMLStage(ventaControl, "Editor");
            stage.showAndWait();
            fxTable.refresh();
            addContent();
        }
    }
}
