package app.control.table;

import app.data.DataStore;
import app.data.casteldao.dao.IndexIdActiveDao;
import app.model.Caja;
import app.model.Sede;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

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
