package app.control.main;

import app.control.MainPaneControl;
import app.data.DataStore;
import app.misc.Flogger;
import app.misc.FxDialogs;
import app.model.Caja;
import app.model.Compra;
import app.model.Proveedor;
import app.model.Sede;
import app.model.Usuario;
import java.io.IOException;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class SedeControl extends BorderPane {

    private final ObservableList<Compra> listedCompras = FXCollections.observableArrayList();

    @FXML
    public ComboBox<Caja> fxBoxCajas;
    @FXML
    public Button fxBtnVerCaja;
    @FXML
    private Button fxBtnNuevaCompra;
    @FXML
    private ComboBox<Usuario> fxBoxUsuario;
    @FXML
    private ComboBox<Sede> fxBoxSede;
    @FXML
    private Button fxBtnInventario;
    @FXML
    private TableView<Compra> fxTableCompras;
    @FXML
    private TableColumn<Compra, Integer> fxTableColumnCompraId;
    @FXML
    private TableColumn<Compra, Usuario> fxTableColumnCompraUsuario;
    @FXML
    private TableColumn<Compra, Sede> fxTableColumnCompraSede;
    @FXML
    private TableColumn<Compra, Proveedor> fxTableColumnCompraProveedor;
    @FXML
    private TableColumn<Compra, LocalDateTime> fxTableColumnCompraFecha;
    @FXML
    private TableColumn<Compra, Integer> fxTableColumnCompraTotal;
    @FXML
    private TableColumn<Compra, String> fxTableColumnCompraAbrir;

    private Sede sede;


    {
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SedePane.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (final IOException e) {
            Flogger.atSevere().withCause(e).log();
        }

        fxBoxUsuario.getItems().addAll(DataStore.getSessionStore().getUsuarios().getAllCache());
        fxBoxSede.getItems().addAll(DataStore.getSessionStore().getSedes().getAllCache());
        fxBoxSede.setOnAction(event -> {
            fxBoxCajas.getItems().setAll(fxBoxSede.getSelectionModel().getSelectedItem().getCajas());
            listedCompras.setAll(fxBoxSede.getSelectionModel().getSelectedItem().getCompras());
        });

        fxTableColumnCompraId.setSortType(SortType.DESCENDING);
        fxTableColumnCompraId.setCellValueFactory(new PropertyValueFactory<>("id"));
        fxTableColumnCompraUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        fxTableColumnCompraSede.setCellValueFactory(new PropertyValueFactory<>("sede"));
        fxTableColumnCompraProveedor.setCellValueFactory(new PropertyValueFactory<>("proveedor"));
        fxTableColumnCompraFecha.setCellValueFactory(new PropertyValueFactory<>("fechahora"));
        fxTableColumnCompraTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        fxTableColumnCompraAbrir.setCellValueFactory(new PropertyValueFactory<>(""));
        fxTableColumnCompraAbrir.setCellFactory(getCompraAbrirCallback());

        fxTableCompras.setItems(listedCompras);
        fxTableCompras.getSortOrder().add(fxTableColumnCompraId);
    }


    public SedeControl(Sede sede) {
        this.sede = sede;
        listedCompras.addAll(sede.getCompras());

        fxBoxUsuario.getSelectionModel().select(DataStore.getSessionStore().getUsuario());
        fxBoxSede.getSelectionModel().select(sede);
        fxBoxCajas.getItems().setAll(fxBoxSede.getSelectionModel().getSelectedItem().getCajas());
    }

    @FXML
    void fxBtnInventarioAction(ActionEvent event) {
    }

    @FXML
    void fxBtnNuevaCompraAction(ActionEvent event) {
        CompraControl compraControl = new CompraControl();
        MainPaneControl.setCenter(compraControl);
    }

    @FXML
    void fxBtnVerCajaAction(ActionEvent event) {
        Caja caja = fxBoxCajas.getSelectionModel().getSelectedItem();
        if (caja != null) {
            CajaControl cajaControl = new CajaControl(caja);
            MainPaneControl.setCenter(cajaControl);
        } else
            FxDialogs.showWarning(null, "Debes elegir una caja");
    }

    @FXML
    void initialize() {
    }

    private Callback<TableColumn<Compra, String>, TableCell<Compra, String>> getCompraAbrirCallback() {
        return new Callback<TableColumn<Compra, String>, TableCell<Compra, String>>() {
            @Override
            public TableCell call(final TableColumn<Compra, String> param) {
                return new TableCell<Compra, String>() {
                    final Button btn = new Button("Abrir");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                Compra compra = getTableView().getItems().get(getIndex());
                                System.out.println(compra.fullToString());
                                CompraControl compraControl = new CompraControl(compra);
                                MainPaneControl.setCenter(compraControl);
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
            }
        };
    }
}
