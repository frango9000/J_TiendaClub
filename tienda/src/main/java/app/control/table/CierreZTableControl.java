package app.control.table;

import app.data.DataStore;
import app.data.appdao.CierreZDao;
import app.model.Caja;
import app.model.CierreZ;
import app.model.Usuario;
import java.time.LocalDateTime;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class CierreZTableControl extends TableControl<CierreZ> {


    {
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
    }

    public CierreZTableControl() {
        addContent();
    }

    public CierreZTableControl(Caja caja) {
        listedObjects.addAll(caja.getCierreZs());
    }

    public CierreZTableControl(Usuario usuario) {
        listedObjects.addAll(usuario.getCierreZs());
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
