package tiendaclub.control.table;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import tiendaclub.data.DataStore;
import tiendaclub.data.casteldao.daomodel.IndexIdActiveDao;
import tiendaclub.model.Caja;
import tiendaclub.model.Sede;

public class CajasTableControl extends ActiveTableControl<Caja> {


    @Override
    public void initialize() {
        super.initialize();

        TableColumn<Caja, Sede> fxColumnSede = new TableColumn<>("Sede");
        fxColumnSede.setCellValueFactory(new PropertyValueFactory<Caja, Sede>("sede"));
        fxTable.getColumns().add(fxColumnSede);

        TableColumn<Caja, String> fxColumnName = new TableColumn<>("Nombre");
        fxColumnName.setCellValueFactory(new PropertyValueFactory<Caja, String>("nombre"));
        fxTable.getColumns().add(fxColumnName);

        fxTable.setItems(listedObjects);
        addContent();
    }

    @Override
    protected String fxmlLocation() {
        return "/fxml/editor/CajaEditorGridPane.fxml";
    }


    @Override
    protected IndexIdActiveDao<Caja> getDataOrigin() {
        return DataStore.getCajas();
    }


}
