package app.control.main;

import app.control.MainPaneControl;
import app.data.DataStore;
import app.misc.Flogger;
import app.misc.FxDialogs;
import app.model.Caja;
import app.model.CierreZ;
import app.model.Sede;
import app.model.Socio;
import app.model.Usuario;
import app.model.Venta;
import java.io.IOException;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import jfxtras.scene.control.LocalDateTimeTextField;

public class CajaControl extends BorderPane {

    private final ObservableList<CierreZ> listedCierreZs = FXCollections.observableArrayList();
    private final ObservableList<Venta> listedVentas = FXCollections.observableArrayList();
    @FXML
    public ComboBox<Caja> fxBoxCaja;
    @FXML
    public ComboBox<Sede> fxBoxSede;
    @FXML
    public Label fxLabelCierreZStatus;
    @FXML
    public TableView<CierreZ> fxTableCierreZs;
    @FXML
    public TableColumn<CierreZ, Integer> fxColumnCierreZId;
    @FXML
    public TableColumn<CierreZ, Caja> fxColumnCierreZCaja;
    @FXML
    public TableColumn<CierreZ, LocalDateTime> fxColumnCierreZApertura;
    @FXML
    public TableColumn<CierreZ, LocalDateTime> fxColumnCierreZCierre;
    @FXML
    public TableColumn<CierreZ, Usuario> fxColumnCierreZUsuarioApertura;
    @FXML
    public TableColumn<CierreZ, Usuario> fxColumnCierreZUsuarioCierre;
    @FXML
    public TableColumn<CierreZ, Integer> fxColumnCierreZTotal;
    @FXML
    public TableColumn<CierreZ, String> fxColumnCierreZAbrir;
    @FXML
    public Button fxBtnCierreZOpen;
    @FXML
    public Button fxBtnCierreZClose;
    @FXML
    public ComboBox<Usuario> fxBoxCierreZUsuarioApertura;
    @FXML
    public ComboBox<Usuario> fxBoxCierreZUsuarioCierre;
    @FXML
    public LocalDateTimeTextField fxDateCierreZApertura;
    @FXML
    public LocalDateTimeTextField fxDateCierreZCierre;

    @FXML
    public Button fxBtnVentaNueva;
    @FXML
    public TableView<Venta> fxTableVentas;
    @FXML
    public TableColumn<Venta, Integer> fxTableColumnVentaId;
    @FXML
    public TableColumn<Venta, Usuario> fxTableColumnVentaUsuario;
    @FXML
    public TableColumn<Venta, Caja> fxTableColumnVentaCaja;
    @FXML
    public TableColumn<Venta, Socio> fxTableColumnVentaSocio;
    @FXML
    public TableColumn<Venta, LocalDateTime> fxTableColumnVentaFecha;
    @FXML
    public TableColumn<Venta, Integer> fxTableColumnVentaTotal;
    @FXML
    public TableColumn<Venta, String> fxTableColumnVentaAbrir;
    @FXML
    public TextField fxFieldVentaTotal;
    @FXML
    public Button fxBtnVentaVolver;
    @FXML
    public VBox fxPaneVentas;

    private Caja caja;
    private CierreZ cierreZ;

    {
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CajaPane.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (final IOException e) {
            Flogger.atSevere().withCause(e).log();
        }
        fxBtnVentaNueva.disableProperty().bind(fxBtnCierreZClose.disableProperty());

        fxBoxSede.getItems().addAll(DataStore.getSessionStore().getSedes().getAllCache());
        fxBoxSede.setOnAction(event -> fxBoxCaja.getItems().setAll(fxBoxSede.getSelectionModel().getSelectedItem().getCajas()));
        fxBoxCaja.getItems().addAll(DataStore.getSessionStore().getCajas().getAllCache());
        fxBoxCierreZUsuarioApertura.getItems().addAll(DataStore.getSessionStore().getUsuarios().getAllCache());
        fxBoxCierreZUsuarioCierre.setItems(fxBoxCierreZUsuarioApertura.getItems());

        fxColumnCierreZId.setSortType(SortType.DESCENDING);
        fxColumnCierreZId.setCellValueFactory(new PropertyValueFactory<>("id"));
        fxColumnCierreZCaja.setCellValueFactory(new PropertyValueFactory<>("caja"));
        fxColumnCierreZApertura.setCellValueFactory(new PropertyValueFactory<>("apertura"));
        fxColumnCierreZUsuarioApertura.setCellValueFactory(new PropertyValueFactory<>("usuarioApertura"));
        fxColumnCierreZCierre.setCellValueFactory(new PropertyValueFactory<>("cierre"));
        fxColumnCierreZUsuarioCierre.setCellValueFactory(new PropertyValueFactory<>("usuarioCierre"));
        fxColumnCierreZTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        fxColumnCierreZAbrir.setCellValueFactory(new PropertyValueFactory<>(""));
        fxColumnCierreZAbrir.setCellFactory(getCierreZAbrirCallback());

        fxTableCierreZs.setItems(listedCierreZs);
        fxTableCierreZs.getSortOrder().add(fxColumnCierreZId);

        fxTableColumnVentaId.setSortType(SortType.DESCENDING);
        fxTableColumnVentaId.setCellValueFactory(new PropertyValueFactory<Venta, Integer>("id"));
        fxTableColumnVentaUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        fxTableColumnVentaCaja.setCellValueFactory(new PropertyValueFactory<>("caja"));
        fxTableColumnVentaSocio.setCellValueFactory(new PropertyValueFactory<>("socio"));
        fxTableColumnVentaFecha.setCellValueFactory(new PropertyValueFactory<>("fechahora"));
        fxTableColumnVentaTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        fxTableColumnVentaAbrir.setCellValueFactory(new PropertyValueFactory<>(""));
        fxTableColumnVentaAbrir.setCellFactory(getVentaAbrirCallback());

        fxTableVentas.setItems(listedVentas);
        fxTableVentas.getSortOrder().add(fxTableColumnVentaId);
    }

    public CajaControl(Caja caja) {
        setCaja(caja);
        fxPaneVentas.setVisible(false);
        if (caja.getCierreZs().size() > 0) {
            setCierreZ(caja.getLastCierreZ());
        } else
            fxBtnCierreZOpen.setDisable(false);
    }

    public CajaControl(CierreZ cierreZ) {
        setCaja(cierreZ.getCaja());
        setCierreZ(cierreZ);
        abrirCierreZ(cierreZ);
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
        fxBoxSede.getSelectionModel().select(caja.getSede());
        fxBoxCaja.getSelectionModel().select(caja);
        listedCierreZs.setAll(caja.getCierreZs());
        fxTableCierreZs.sort();
    }

    private void setCierreZ(CierreZ cierreZ) {
        this.cierreZ = cierreZ;
        fxDateCierreZApertura.setLocalDateTime(cierreZ.getApertura());
        fxBoxCierreZUsuarioApertura.getSelectionModel().select(cierreZ.getUsuarioApertura());

        if (cierreZ.getCierre() == null) {
            fxDateCierreZCierre.setText("");
            fxLabelCierreZStatus.setText("Abierta");
            fxBtnCierreZClose.setDisable(false);
            fxBtnCierreZOpen.setDisable(true);
        } else {
            fxDateCierreZCierre.setLocalDateTime(cierreZ.getCierre());
            fxBoxCierreZUsuarioCierre.getSelectionModel().select(cierreZ.getUsuarioCierre());
            fxLabelCierreZStatus.setText("Cerrada");
            fxBtnCierreZClose.setDisable(true);
            fxBtnCierreZOpen.setDisable(false);
            fxTableCierreZs.refresh();
        }
    }

    @FXML
    public void fxBtnOpenAction(ActionEvent actionEvent) {
        if (cierreZ == null || cierreZ.getUsuarioCierre() != null && cierreZ.getCierre() != null) {
            cierreZ = new CierreZ();
            cierreZ.setCaja(DataStore.getSessionStore().getCaja());
            cierreZ.setApertura(LocalDateTime.now());
            cierreZ.setUsuarioApertura(DataStore.getSessionStore().getUsuario());
            if (cierreZ.insertIntoDB() == 1) {
                setCierreZ(cierreZ);
                listedCierreZs.add(cierreZ);
                fxTableCierreZs.sort();
                listedVentas.clear();
                return;
            }
        }
        cierreZ = null;
        FxDialogs.showWarning(null, "Error abriendo Caja");
    }

    @FXML
    public void fxBtnCloseAction(ActionEvent actionEvent) {
        if (cierreZ != null && cierreZ.getUsuarioCierre() == null && cierreZ.getCierre() == null) {
            try {
                cierreZ.setBackup();
                cierreZ.setCierre(LocalDateTime.now());
                cierreZ.setUsuarioCierre(DataStore.getSessionStore().getUsuario());
                cierreZ.updateOnDb();
            } catch (CloneNotSupportedException e) {
                Flogger.atWarning().withCause(e).log("Clone Error");
            }
            setCierreZ(cierreZ);
        }
    }

    @FXML
    public void fxBtnDetailAction(ActionEvent actionEvent) {
        abrirCierreZ(cierreZ);
    }

    @FXML
    public void fxBtnVentaVolverAction(ActionEvent actionEvent) {
        fxTableCierreZs.setVisible(true);
        fxPaneVentas.setVisible(false);
    }

    @FXML
    public void fxBtnNuevaVentaAction(ActionEvent actionEvent) {
        VentaControl ventaControl = new VentaControl();
        MainPaneControl.setCenter(ventaControl);
    }


    private void abrirCierreZ(CierreZ cierreZ) {
        listedVentas.setAll(cierreZ.getVentas());
        fxFieldVentaTotal.setText(Integer.toString(listedVentas.stream().mapToInt(Venta::getTotal).sum()));

        fxTableCierreZs.setVisible(false);
        fxPaneVentas.setVisible(true);
    }

    private Callback<TableColumn<CierreZ, String>, TableCell<CierreZ, String>> getCierreZAbrirCallback() {
        return new Callback<TableColumn<CierreZ, String>, TableCell<CierreZ, String>>() {
            @Override
            public TableCell call(final TableColumn<CierreZ, String> param) {
                return new TableCell<CierreZ, String>() {
                    final Button btn = new Button("Abrir");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                CierreZ cierreZ = getTableView().getItems().get(getIndex());
                                System.out.println(cierreZ.fullToString());
                                abrirCierreZ(cierreZ);
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
            }
        };
    }

    private Callback<TableColumn<Venta, String>, TableCell<Venta, String>> getVentaAbrirCallback() {
        return new Callback<TableColumn<Venta, String>, TableCell<Venta, String>>() {
            @Override
            public TableCell<Venta, String> call(final TableColumn<Venta, String> param) {
                return new TableCell<Venta, String>() {
                    final Button btn = new Button("Abrir");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                Venta venta = getTableView().getItems().get(getIndex());
                                System.out.println(venta.fullToString());
                                VentaControl ventaControl = new VentaControl(venta);
                                MainPaneControl.setCenter(ventaControl);
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
