package app.control.table;

import app.data.DataStore;
import app.data.appdao.TransferenciaDao;
import app.model.Sede;
import app.model.Transferencia;
import app.model.Usuario;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class TransferenciasTableControl extends TableControl<Transferencia> {


    @Override
    public void initialize() {
        super.initialize();
        TableColumn<Transferencia, Usuario> fxColumnUsuario = new TableColumn<>("Usuario");
        fxColumnUsuario.setCellValueFactory(new PropertyValueFactory<Transferencia, Usuario>("usuario"));
        fxTable.getColumns().add(fxColumnUsuario);

        TableColumn<Transferencia, Sede> fxColumnSedeOrigen = new TableColumn<>("Sede Orig");
        fxColumnSedeOrigen.setCellValueFactory(new PropertyValueFactory<Transferencia, Sede>("sedeOrigen"));
        fxTable.getColumns().add(fxColumnSedeOrigen);

        TableColumn<Transferencia, Sede> fxColumnSedeDestino = new TableColumn<>("Sede Orig");
        fxColumnSedeDestino.setCellValueFactory(new PropertyValueFactory<Transferencia, Sede>("sedeOrigen"));
        fxTable.getColumns().add(fxColumnSedeDestino);

        TableColumn<Transferencia, Usuario> fxColumnProducto = new TableColumn<>("Producto");
        fxColumnProducto.setCellValueFactory(new PropertyValueFactory<Transferencia, Usuario>("producto"));
        fxTable.getColumns().add(fxColumnProducto);

        TableColumn<Transferencia, Integer> fxColumnCantidad = new TableColumn<>("Cantidad");
        fxColumnCantidad.setCellValueFactory(new PropertyValueFactory<Transferencia, Integer>("cantidad"));
        fxTable.getColumns().add(fxColumnCantidad);

        fxTable.setItems(listedObjects);
        addContent();
    }

    @Override
    protected String fxmlLocation() {
        return "/fxml/editor/TransferenciaEditorGridPane.fxml";
    }


    @Override
    protected TransferenciaDao getDataOrigin() {
        return DataStore.getSessionStore().getTransferencias();
    }


}
