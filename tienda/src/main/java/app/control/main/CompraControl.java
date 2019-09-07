package app.control.main;

import app.data.DataStore;
import app.misc.Flogger;
import app.misc.FxDialogs;
import app.model.Categoria;
import app.model.Compra;
import app.model.Comprado;
import app.model.Producto;
import app.model.Proveedor;
import app.model.Sede;
import app.model.Usuario;
import casteldao.model.IEntity;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import jfxtras.scene.control.LocalDateTimeTextField;

public class CompraControl extends BorderPane {

    protected final ObservableList<Producto> listedProductos = FXCollections.observableArrayList();
    protected final ObservableList<Comprado> listedComprados = FXCollections.observableArrayList();

    @FXML
    public ComboBox<Proveedor> fxBoxExchangee;
    @FXML
    public ComboBox<Sede> fxBoxSedes;
    @FXML
    public ComboBox fxBoxCajas;
    @FXML
    public ComboBox<Usuario> fxBoxUsuarios;
    @FXML
    public ComboBox<Categoria> fxBoxCategorias;
    @FXML
    public Label fxLabelExchangee;
    @FXML
    public Label fxLabelType;
    @FXML
    public Label fxLabelCaja;

    @FXML
    private TableView<Comprado> fxTableVendidos;
    @FXML
    private TableColumn<Comprado, Integer> fxColServidoId;
    @FXML
    private TableColumn<Comprado, Producto> fxColServidoNombre;
    @FXML
    private TableColumn<Comprado, Integer> fxColServidoCantidad;
    @FXML
    private TableColumn<Comprado, Integer> fxColServidoPrecio;
    @FXML
    private TableView<Producto> fxTableProductos;
    @FXML
    private TableColumn<Producto, Integer> fxColProductoId;
    @FXML
    private TableColumn<Producto, String> fxColProductoNombre;
    @FXML
    private TableColumn<Producto, Categoria> fxColProductoCategoria;
    @FXML
    private TableColumn<Producto, Integer> fxColProductoPrecioU;
    @FXML
    private TextField fxFieldId;
    @FXML
    private LocalDateTimeTextField fxFieldDate;
    @FXML
    private TextField fxFieldTotal;

    private Compra compra;

    {
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/VentasPane.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (final IOException e) {
            Flogger.atSevere().withCause(e).log();
        }
        if (DataStore.getSessionStore().getUsuario().getAcceso().getId() <= 2) {
            fxBoxSedes.setDisable(false);
            fxBoxCajas.setDisable(false);
            fxBoxUsuarios.setDisable(false);
        }
        fxLabelExchangee.setText("Proveedor");
        fxBoxExchangee.getItems().addAll(DataStore.getSessionStore().getProveedores().getAllCache());

        fxBoxSedes.getItems().addAll(DataStore.getSessionStore().getSedes().getAllCache());
        fxBoxSedes.getSelectionModel().select(DataStore.getSessionStore().getSede());

        fxBoxCajas.setVisible(false);
        fxLabelCaja.setVisible(false);

        fxBoxUsuarios.getItems().setAll(DataStore.getSessionStore().getUsuarios().getAllCache());
        fxBoxUsuarios.getSelectionModel().select(DataStore.getSessionStore().getUsuario());

        fxTableVendidos.setItems(listedComprados);
        fxTableProductos.setItems(listedProductos);
        fxColServidoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        fxColServidoNombre.setCellValueFactory(new PropertyValueFactory<>("producto"));
        fxColServidoCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        fxColServidoPrecio.setCellValueFactory(new PropertyValueFactory<>("precioUnidad"));

        fxColProductoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        fxColProductoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        fxColProductoCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        fxColProductoPrecioU.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        listedProductos.addAll(DataStore.getSessionStore().getProductos().getById().getCacheValues());

        fxBoxCategorias.getItems().addAll(DataStore.getSessionStore().getCategorias().getAllCache());
        fxBoxCategorias.setOnAction(event -> listedProductos.setAll(fxBoxCategorias.getSelectionModel().getSelectedItem().getProductos()));
    }

    public CompraControl() {
        this(null);
    }

    public CompraControl(Compra compra) {
        if (compra != null) {
            this.compra = compra;
            setFields();
        } else {

        }
    }

    void setFields() {
        if (compra.getId() > 0)
            fxFieldId.setText(Integer.toString(compra.getId()));
        fxBoxUsuarios.getSelectionModel().select(compra.getUsuario());
        fxBoxSedes.getSelectionModel().select(compra.getSede());
        fxBoxExchangee.getSelectionModel().select(compra.getProveedor());
        fxFieldDate.setLocalDateTime(compra.getFechahora());
        listedComprados.setAll(compra.getComprados());
        IEntity.backupAll(listedComprados);
        updateFxPrice();
    }

    @FXML
    void fxBtnAdd(ActionEvent event) {
        Producto selected = fxTableProductos.getSelectionModel().getSelectedItem();
        if (selected != null) {
            boolean exists = false;
            for (Comprado comprado : listedComprados) {
                if (comprado.getProducto().getId().equals(selected.getId())) {
                    comprado.setCantidad(comprado.getCantidad() + 1);
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                Comprado comprado = new Comprado(selected, 1, selected.getPrecioVenta());
                if (compra != null)
                    comprado.setCompra(compra);
                listedComprados.add(comprado);
            }
            fxTableVendidos.refresh();
            updateFxPrice();
        }
    }

    private void updateFxPrice() {
        fxFieldTotal.setText(listedComprados.stream().mapToInt(value -> value.getCantidad() * value.getPrecioUnidad()).sum() + "");
    }

    @FXML
    void fxBtnDiscard(ActionEvent event) {
        this.setVisible(false);
    }

    @FXML
    void fxBtnEdit(ActionEvent event) {

    }

    @FXML
    void fxBtnRemoveAll(ActionEvent event) {
        if (FxDialogs.showConfirmBoolean("Cuidado", "Deseas eliminar todos los productos de la orden ?"))
            listedComprados.clear();
        updateFxPrice();
    }

    @FXML
    void fxBtnVoid(ActionEvent event) {
        if (compra != null) {
            if (FxDialogs.showConfirmBoolean("Cuidado", "Deseas eliminar la orden " + compra.getId() + " ?")) {
                DataStore.getSessionStore().getComprados().getDao().deleteSome(compra.getComprados());
                boolean success = compra.deleteFromDb() == 1;
                FxDialogs.showInfo("", "Compra Id " + compra.getId() + (success ? " " : "NO ") + "eliminado");
                if (success) {
                    listedComprados.clear();
                }
            }
        }

    }

    @FXML
    void fxBtnRemove(ActionEvent event) {
        Comprado selected = fxTableVendidos.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (FxDialogs.showConfirmBoolean("Cuidado", "Deseas eliminar el Id " + selected.getId() + " ?")) {
                if (selected.getId() != 0) {
                    boolean success = selected.deleteFromDb() == 1;
                    FxDialogs.showInfo("", "Id " + selected.getId() + (success ? " " : "NO ") + "eliminado");
                    if (success) {
                        listedComprados.remove(selected);
                    }
                } else
                    listedComprados.remove(selected);
                updateFxPrice();
            }
        }
    }

    @FXML
    void fxBtnSave(ActionEvent event) {
        if (fxBoxExchangee.getSelectionModel().getSelectedItem() == null ||
            fxBoxUsuarios.getSelectionModel().getSelectedItem() == null ||
            fxBoxSedes.getSelectionModel().getSelectedItem() == null) {
            FxDialogs.showError(null, "Unset Values");
            return;
        }
        if (compra == null) {
            if (listedComprados.size() > 0) {
                this.compra = new Compra();
                compra.setUsuario(fxBoxUsuarios.getSelectionModel().getSelectedItem());
                compra.setSede(fxBoxSedes.getSelectionModel().getSelectedItem());
                compra.setProveedor(fxBoxExchangee.getSelectionModel().getSelectedItem());
                compra.setFechahora(LocalDateTime.now());

                DataStore.getSessionStore().getCompras().getDao().insert(compra);
                if (compra.getId() > 0) {
                    fxFieldId.setText(Integer.toString(compra.getId()));
                    fxFieldDate.setLocalDateTime(compra.getFechahora());
                    listedComprados.forEach(vendido -> vendido.setCompra(compra));
                    DataStore.getSessionStore().getComprados().getDao().insert(listedComprados);
                    FxDialogs.showInfo("Id: " + compra.getId(), "Compra guardada");
                } else {
                    compra = null;
                    Flogger.atWarning().log("Compra no guardada");
                }
            } else
                FxDialogs.showInfo(null, "Nada que vender");
        } else {
            int updated = 0;
            int expected = 0;
            try {
                compra.setBackup();
                compra.setUsuario(fxBoxUsuarios.getSelectionModel().getSelectedItem());
                compra.setSede(fxBoxSedes.getSelectionModel().getSelectedItem());
                compra.setProveedor(fxBoxExchangee.getSelectionModel().getSelectedItem());
                compra.setFechahora(fxFieldDate.getLocalDateTime());
                if (!compra.equals(compra.getBackup())) {
                    expected++;
                    updated += DataStore.getSessionStore().getCompras().getDao().update(compra);
                } else
                    compra.commit();
            } catch (CloneNotSupportedException e) {
                Flogger.atSevere().withCause(e).log("Clone Fail");
            }
            Set<Comprado> toupdate = Sets.newHashSet(Sets.intersection(compra.getComprados(), Sets.newHashSet(listedComprados)));
            updated += DataStore.getSessionStore().getComprados().getDao().update(toupdate);

            Set<Comprado> toremove = Sets.difference(compra.getComprados(), Sets.newHashSet(listedComprados));
            updated += DataStore.getSessionStore().getComprados().getDao().deleteSome(toremove);

            Set<Comprado> toadd = Sets.difference(Sets.newHashSet(listedComprados), compra.getComprados());
            toadd.forEach(vendido -> vendido.setCompra(compra));
            updated += DataStore.getSessionStore().getComprados().getDao().insert(toadd);

            expected += toadd.size() + toremove.size() + toupdate.size();
            if (expected == 0)
                FxDialogs.showInfo("Id: " + compra.getId(), "Nada que modificar");
            else if (expected == updated)
                FxDialogs.showInfo("Id: " + compra.getId(), "Compra guardada correctamente");
            else
                FxDialogs.showInfo("Id: " + compra.getId(), "Compra guardada incompleta y/o con errores " + updated + "/" + expected);
        }
        fxTableVendidos.refresh();
        setFields();
    }
}
