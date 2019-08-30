package tiendaclub.control.table;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import tiendaclub.data.DataStore;
import tiendaclub.data.dao.core.IndexIdActiveDao;
import tiendaclub.model.models.Categoria;

public class CategoriasTableControl extends ActiveTableControl<Categoria> {


    @Override
    public void initialize() {
        super.initialize();

        TableColumn<Categoria, String> fxColumnName = new TableColumn<>("Nombre");
        fxColumnName.setCellValueFactory(new PropertyValueFactory<Categoria, String>("nombre"));
        fxTable.getColumns().add(fxColumnName);

        fxTable.setItems(listedObjects);
        addContent();
    }

    @Override
    protected String fxmlLocation() {
        return "/fxml/editor/CategoriaEditorGridPane.fxml";
    }


    @Override
    protected IndexIdActiveDao<Categoria> getDataOrigin() {
        return DataStore.getCategorias();
    }


}
