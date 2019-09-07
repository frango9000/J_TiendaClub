package app.control.table;

import app.data.DataStore;
import app.data.appdao.CierreZDao;
import app.model.CierreZ;
import java.time.LocalDateTime;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class CierreZTableControl extends TableControl<CierreZ> {


    @Override
    public void initialize() {
        super.initialize();

        TableColumn<CierreZ, String> fxColumnCaja = new TableColumn<>("Caja");
        fxTable.getColumns().add(fxColumnCaja);
        fxColumnCaja.setCellValueFactory(new PropertyValueFactory<>("caja"));

        TableColumn<CierreZ, LocalDateTime> fxColumnApertura = new TableColumn<>("Apertura");
        fxColumnApertura.setCellValueFactory(new PropertyValueFactory<>("apertura"));
        fxTable.getColumns().add(fxColumnApertura);

        TableColumn<CierreZ, LocalDateTime> fxColumnCierre = new TableColumn<>("Cierre");
        fxColumnCierre.setCellValueFactory(new PropertyValueFactory<>("cierre"));
        fxTable.getColumns().add(fxColumnCierre);

        fxTable.setItems(listedObjects);
        addContent();
    }

    @Override
    protected String fxmlLocation() {
        return "/fxml/editor/CierreZEditorGridPane.fxml";
    }


    @Override
    protected CierreZDao getDataOrigin() {
        return DataStore.getSessionStore().getCierreZs();
    }
}
