package app.control.editor;

import app.data.DataStore;
import app.misc.Flogger;
import app.misc.FxDialogs;
import app.model.Caja;
import app.model.Categoria;
import app.model.Producto;
import app.model.Sede;
import app.model.Socio;
import app.model.Usuario;
import app.model.Vendido;
import app.model.Venta;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import jfxtras.scene.control.LocalDateTimeTextField;

public class VentaControl extends BorderPane {

    protected final ObservableList<Producto> listedProductos = FXCollections.observableArrayList();
    protected final ObservableList<Vendido> listedVendidos = FXCollections.observableArrayList();

    @FXML
    public ComboBox<Socio> fxBoxSocios;
    @FXML
    public ComboBox<Sede> fxBoxSedes;
    @FXML
    public ComboBox<Caja> fxBoxCajas;
    @FXML
    public ComboBox<Usuario> fxBoxUsuarios;
    @FXML
    public ComboBox<Categoria> fxBoxCategorias;

    @FXML
    private TableView<Vendido> fxTableVendidos;
    @FXML
    private TableColumn<Vendido, Integer> fxColVendidoId;
    @FXML
    private TableColumn<Vendido, Producto> fxColVendidoNombre;
    @FXML
    private TableColumn<Vendido, Integer> fxColVendidoCantidad;
    @FXML
    private TableColumn<Vendido, Integer> fxColVendidoPrecio;
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

    private Venta venta;

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
        fxTableVendidos.setItems(listedVendidos);
        fxTableProductos.setItems(listedProductos);
        fxColVendidoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        fxColVendidoNombre.setCellValueFactory(new PropertyValueFactory<>("producto"));
        fxColVendidoCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        fxColVendidoPrecio.setCellValueFactory(new PropertyValueFactory<>("precioUnidad"));

        fxColProductoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        fxColProductoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        fxColProductoCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        fxColProductoPrecioU.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        listedProductos.addAll(DataStore.getSessionStore().getProductos().getById().getCacheValues());

        fxBoxUsuarios.getItems().setAll(DataStore.getSessionStore().getUsuarios().getAllCache());
        fxBoxUsuarios.getSelectionModel().select(DataStore.getSessionStore().getUsuario());

        fxBoxSedes.getItems().addAll(DataStore.getSessionStore().getSedes().getAllCache());
        fxBoxSedes.setOnAction(event -> fxBoxCajas.getItems().setAll(fxBoxSedes.getSelectionModel().getSelectedItem().getCajas()));
        fxBoxSedes.getSelectionModel().select(DataStore.getSessionStore().getSede());

        fxBoxCajas.getItems().setAll(fxBoxSedes.getSelectionModel().getSelectedItem().getCajas());
        fxBoxCajas.getSelectionModel().select(DataStore.getSessionStore().getCaja());

        fxBoxCategorias.getItems().addAll(DataStore.getSessionStore().getCategorias().getAllCache());
        fxBoxCategorias.setOnAction(event -> listedProductos.setAll(fxBoxCategorias.getSelectionModel().getSelectedItem().getProductos()));

        fxBoxSocios.getItems().addAll(DataStore.getSessionStore().getSocios().getAllCache());
    }

    public VentaControl() {
        this(null);
    }

    public VentaControl(Venta venta) {
        if (venta != null) {
            this.venta = venta;
            setFields();
        } else {

        }
    }

    void setFields() {
        if (venta.getId() > 0)
            fxFieldId.setText(Integer.toString(venta.getId()));
        fxBoxUsuarios.getSelectionModel().select(venta.getUsuario());
        fxBoxSedes.getSelectionModel().select(venta.getCaja().getSede());
        fxBoxCajas.getSelectionModel().select(venta.getCaja());
        fxBoxSocios.getSelectionModel().select(venta.getSocio());
        fxFieldDate.setLocalDateTime(venta.getFechahora());
        listedVendidos.setAll(venta.getVendidos());
        IEntity.backupAll(listedVendidos);
        updateFxPrice();
    }

    public void setSocio(Socio socio) {
        if (fxBoxSocios.getItems().contains(socio))
            fxBoxSocios.getSelectionModel().select(socio);
    }

    @FXML
    void fxBtnAdd(ActionEvent event) {
        Producto selected = fxTableProductos.getSelectionModel().getSelectedItem();
        if (selected != null) {
            boolean exists = false;
            for (Vendido vendido : listedVendidos) {
                if (vendido.getProducto().getId().equals(selected.getId())) {
                    vendido.setCantidad(vendido.getCantidad() + 1);
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                Vendido vendido = new Vendido(selected, 1, selected.getPrecioVenta());
                if (venta != null)
                    vendido.setVenta(venta);
                listedVendidos.add(vendido);
            }
            fxTableVendidos.refresh();
            updateFxPrice();
        }
    }

    private void updateFxPrice() {
        fxFieldTotal.setText(listedVendidos.stream().mapToInt(value -> value.getCantidad() * value.getPrecioUnidad()).sum() + "");
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
            listedVendidos.clear();
        updateFxPrice();
    }

    @FXML
    void fxBtnVoid(ActionEvent event) {
        if (venta != null) {
            if (FxDialogs.showConfirmBoolean("Cuidado", "Deseas eliminar la orden " + venta.getId() + " ?")) {
                DataStore.getSessionStore().getVendidos().getDao().deleteSome(venta.getVendidos());
                boolean success = venta.deleteFromDb() == 1;
                FxDialogs.showInfo("", "Venta Id " + venta.getId() + (success ? " " : "NO ") + "eliminado");
                if (success) {
                    listedVendidos.clear();
                }
            }
        }

    }

    @FXML
    void fxBtnRemove(ActionEvent event) {
        Vendido selected = fxTableVendidos.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (FxDialogs.showConfirmBoolean("Cuidado", "Deseas eliminar el Id " + selected.getId() + " ?")) {
                if (selected.getId() != 0) {
                    boolean success = selected.deleteFromDb() == 1;
                    FxDialogs.showInfo("", "Id " + selected.getId() + (success ? " " : "NO ") + "eliminado");
                    if (success) {
                        listedVendidos.remove(selected);
                    }
                } else
                    listedVendidos.remove(selected);
                updateFxPrice();
            }
        }
    }

    @FXML
    void fxBtnSave(ActionEvent event) {
        if (fxBoxSocios.getSelectionModel().getSelectedItem() == null ||
            fxBoxUsuarios.getSelectionModel().getSelectedItem() == null ||
            fxBoxCajas.getSelectionModel().getSelectedItem() == null ||
            fxBoxSedes.getSelectionModel().getSelectedItem() == null) {
            FxDialogs.showError(null, "Unset Values");
            return;
        }
        if (venta == null) {
            if (listedVendidos.size() > 0) {
                this.venta = new Venta();
                venta.setUsuario(DataStore.getSessionStore().getUsuario());
                venta.setCaja(DataStore.getSessionStore().getCaja());
                venta.setSocio(fxBoxSocios.getSelectionModel().getSelectedItem());
                venta.setFechahora(LocalDateTime.now());

                DataStore.getSessionStore().getVentas().getDao().insert(venta);
                if (venta.getId() > 0) {
                    fxFieldId.setText(Integer.toString(venta.getId()));
                    fxFieldDate.setLocalDateTime(venta.getFechahora());
                    listedVendidos.forEach(vendido -> vendido.setVenta(venta));
                    DataStore.getSessionStore().getVendidos().getDao().insert(listedVendidos);
                    FxDialogs.showInfo("Id: " + venta.getId(), "Venta guardada");
                } else {
                    venta = null;
                    Flogger.atWarning().log("Venta no guardada");
                }
            } else
                FxDialogs.showInfo(null, "Nada que vender");
        } else {
            int updated = 0;
            int expected = 0;
            try {
                venta.setBackup();
                venta.setUsuario(DataStore.getSessionStore().getUsuario());
                venta.setCaja(DataStore.getSessionStore().getCaja());
                venta.setSocio(fxBoxSocios.getSelectionModel().getSelectedItem());
                venta.setFechahora(fxFieldDate.getLocalDateTime());
                if (!venta.equals(venta.getBackup())) {
                    expected++;
                    updated += DataStore.getSessionStore().getVentas().getDao().update(venta);
                } else
                    venta.commit();
            } catch (CloneNotSupportedException e) {
                Flogger.atSevere().withCause(e).log("Clone Fail");
            }
            Set<Vendido> toupdate = Sets.newHashSet(Sets.intersection(venta.getVendidos(), Sets.newHashSet(listedVendidos)));
            updated += DataStore.getSessionStore().getVendidos().getDao().update(toupdate);

            Set<Vendido> toremove = Sets.difference(venta.getVendidos(), Sets.newHashSet(listedVendidos));
            updated += DataStore.getSessionStore().getVendidos().getDao().deleteSome(toremove);

            Set<Vendido> toadd = Sets.difference(Sets.newHashSet(listedVendidos), venta.getVendidos());
            toadd.forEach(vendido -> vendido.setVenta(venta));
            updated += DataStore.getSessionStore().getVendidos().getDao().insert(toadd);

            expected += toadd.size() + toremove.size() + toupdate.size();
            if (expected == 0)
                FxDialogs.showInfo("Id: " + venta.getId(), "Nada que modificar");
            else if (expected == updated)
                FxDialogs.showInfo("Id: " + venta.getId(), "Venta guardada correctamente");
            else
                FxDialogs.showInfo("Id: " + venta.getId(), "Venta guardada incompleta y/o con errores " + updated + "/" + expected);
        }
        fxTableVendidos.refresh();
        setFields();
    }
}
