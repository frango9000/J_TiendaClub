package app.control.main;

import app.data.DataStore;
import app.misc.Flogger;
import app.model.Caja;
import app.model.CierreZ;
import app.model.Sede;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class CajaControl extends BorderPane {

    private final ObservableList<CierreZ> listedObjects = FXCollections.observableArrayList();
    @FXML
    public ComboBox<Caja> fxBoxCaja;
    @FXML
    public ComboBox<Sede> fxBoxSede;
    @FXML
    public Label fxLabelStatus;
    @FXML
    public TextField fxFieldCierre;
    @FXML
    public TextField fxFieldApertura;
    @FXML
    public TableView<CierreZ> fxTableCierres;
    @FXML
    public TableColumn<CierreZ, Integer> fxColumnId;
    @FXML
    public TableColumn<CierreZ, Caja> fxColumnCaja;
    @FXML
    public TableColumn<CierreZ, LocalDateTime> fxColumnApertura;
    @FXML
    public TableColumn<CierreZ, LocalDateTime> fxColumnCierre;
    @FXML
    public Button fxBtnOpen;
    @FXML
    public Button fxBtnClose;
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

        fxBoxSede.getItems().addAll(DataStore.getSessionStore().getSedes().getAllCache());
        fxBoxCaja.getItems().addAll(DataStore.getSessionStore().getCajas().getAllCache());

        fxColumnId.setCellValueFactory(new PropertyValueFactory<CierreZ, Integer>("id"));
        fxColumnCaja.setCellValueFactory(new PropertyValueFactory<CierreZ, Caja>("caja"));
        fxColumnApertura.setCellValueFactory(new PropertyValueFactory<CierreZ, LocalDateTime>("apertura"));
        fxColumnCierre.setCellValueFactory(new PropertyValueFactory<CierreZ, LocalDateTime>("cierre"));
        fxTableCierres.setItems(listedObjects);


    }

    public CajaControl(Caja caja) {
        this.caja = caja;
        setFields(caja);
    }

    @FXML
    public void initialize() {
    }

    public void setFields(Caja caja) {
        fxBoxSede.getSelectionModel().select(caja.getSede());
        fxBoxCaja.getSelectionModel().select(caja);
        fxTableCierres.getItems().setAll(caja.getCierreZs());

        try {
            cierreZ = caja.getTreeCierreZs().first();
            fxFieldApertura.setText(cierreZ.getApertura().toString());

            if (cierreZ.getCierre() == null) {
                fxBtnClose.setDisable(false);
                fxLabelStatus.setText("Abierta");

            } else {
                fxFieldCierre.setText(cierreZ.getCierre().toString());
                fxBtnClose.setDisable(true);
                fxLabelStatus.setText("Cerrada");
            }
        } catch (NoSuchElementException e) {
            Flogger.atWarning().withCause(e).log();
        }


    }

    @FXML
    public void fxBtnOpenAction(ActionEvent actionEvent) {
    }

    @FXML
    public void fxBtnCloseAction(ActionEvent actionEvent) {
    }

    @FXML
    public void fxBtnDetailAction(ActionEvent actionEvent) {
    }
}
