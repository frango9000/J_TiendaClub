package app.control.table;

import app.data.DataStore;
import app.model.Compra;
import app.model.Sede;
import app.model.Socio;
import app.model.Usuario;
import casteldao.datasource.DataSourceIdImpl;
import java.time.LocalDateTime;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class ComprasTableControl extends TableControl<Compra> {


    @Override
    public void initialize() {
        super.initialize();
        TableColumn<Compra, Usuario> fxColumnUsuario = new TableColumn<>("Usuario");
        fxColumnUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        fxTable.getColumns().add(fxColumnUsuario);

        TableColumn<Compra, Sede> fxColumnSede = new TableColumn<>("Sede");
        fxColumnSede.setCellValueFactory(new PropertyValueFactory<>("sede"));
        fxTable.getColumns().add(fxColumnSede);

        TableColumn<Compra, Socio> fxColumnProveedor = new TableColumn<>("Proveedor");
        fxColumnProveedor.setCellValueFactory(new PropertyValueFactory<>("proveedor"));
        fxTable.getColumns().add(fxColumnProveedor);

        TableColumn<Compra, LocalDateTime> fxColumnFechaHora = new TableColumn<>("Fecha");
        fxColumnFechaHora.setCellValueFactory(new PropertyValueFactory<>("fechahora"));
        fxTable.getColumns().add(fxColumnFechaHora);

        fxTable.setItems(listedObjects);
        addContent();
    }


    @Override
    protected String fxmlLocation() {
        return "/fxml/editor/CompraEditorGridPane.fxml";
    }

    @Override
    protected DataSourceIdImpl<Compra> getDataOrigin() {
        return DataStore.getSessionStore().getCompras();
    }

//    @Override
//    protected void fxBtnAddAction(ActionEvent actionEvent) throws IOException {
//        CompraControl compraControl = new CompraControl();
//        FXMLStage stage = new FXMLStage(compraControl, "Creator");
//        stage.showAndWait();
//        fxTable.refresh();
//        addContent();
//    }
//
//    @Override
//    protected void fxBtnEditAction(ActionEvent actionEvent) throws IOException {
//        Compra selected = fxTable.getSelectionModel().getSelectedItem();
//        if (selected != null) {
//            CompraControl compraControl = new CompraControl(selected);
//            FXMLStage stage = new FXMLStage(compraControl, "Editor");
//            stage.showAndWait();
//            fxTable.refresh();
//            addContent();
//        }
//    }
}
