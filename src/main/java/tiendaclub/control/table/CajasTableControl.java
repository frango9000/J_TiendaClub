package tiendaclub.control.table;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import tiendaclub.control.editor.CajaEditorControl;
import tiendaclub.control.editor.EditorControl;
import tiendaclub.data.DataStore;
import tiendaclub.data.framework.dao.core.IndexIdActiveDao;
import tiendaclub.model.models.Caja;
import tiendaclub.model.models.Sede;

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
    protected IndexIdActiveDao<Caja> getDataOrigin() {
        return DataStore.getCajas();
    }

    @Override
    protected EditorControl<Caja> getEditorControl() {
        return CajaEditorControl.getPane();
    }

}
