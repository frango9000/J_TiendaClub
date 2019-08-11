package tiendaclub.control.table;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import tiendaclub.control.editor.UsuarioEditorPaneControl;
import tiendaclub.data.DataStore;
import tiendaclub.model.models.Acceso;
import tiendaclub.model.models.Usuario;
import tiendaclub.view.FXMLStage;

import java.io.IOException;

public class UsuariosPaneControl extends ActiveTableControl<Usuario> {

    @FXML
    private TableColumn<Usuario, String> fxColumnUser;
    @FXML
    private TableColumn<Usuario, String> fxColumnName;
    @FXML
    private TableColumn<Usuario, Acceso> fxColumnLevel;

    public static Pane getPane() {
        return FXMLStage.getPane("/fxml/tables/UsuariosPane.fxml");
    }

    @FXML
    public void initialize() {
        dataOrigin = DataStore.getUsuarios();

        fxColumnId.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("id"));
        fxColumnUser.setCellValueFactory(new PropertyValueFactory<Usuario, String>("username"));
        fxColumnName.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nombre"));
        fxColumnLevel.setCellValueFactory(new PropertyValueFactory<Usuario, Acceso>("acceso"));
        fxColumnIsActive.setCellValueFactory(f -> f.getValue().activeProperty());
        fxColumnIsActive.setCellFactory(tc -> new CheckBoxTableCell<>());
        fxTable.setItems(listedObjects);

        addContent();
    }

    @FXML
    protected void fxBtnAddAction(ActionEvent actionEvent) throws IOException {
        Pane pane = UsuarioEditorPaneControl.loadFXML();
        FXMLStage stage = new FXMLStage(pane, "Usuario");
        stage.showAndWait();
        fxTable.refresh();
        addContent();
    }

    @FXML
    protected void fxBtnEditAction(ActionEvent actionEvent) throws IOException {
        Usuario selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Pane pane = UsuarioEditorPaneControl.loadFXML();
            UsuarioEditorPaneControl control = UsuarioEditorPaneControl.getController();
            control.setUsuario(selected);
            FXMLStage stage = new FXMLStage(pane, "Usuario");
            stage.showAndWait();
            if (!showInactive && !selected.isActivo())
                listedObjects.remove(selected);
            fxTable.refresh();
        }
    }
}
