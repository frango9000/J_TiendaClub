package tiendaclub.control.table;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import tiendaclub.data.framework.dao.ActivableDao;
import tiendaclub.model.models.Usuario;
import tiendaclub.model.models.abstracts.Activable;
import tiendaclub.view.FxDialogs;

import java.util.Collection;

public abstract class ActiveTableControl<T extends Activable> extends TableControl<T> {

    protected boolean showInactive = false;

    protected ActivableDao<T> dataOrigin;
    @FXML
    protected Button fxBtnShowHide;
    @FXML
    protected TableColumn<Usuario, Boolean> fxColumnIsActive;

    @FXML
    protected void fxBtnShowHideAction(ActionEvent actionEvent) {
        showInactive = !showInactive;
        addContent(true);
    }

    @FXML
    protected void fxBtnDisableAction(ActionEvent actionEvent) {
        T selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            boolean isActive = selected.isActivo();
            if (FxDialogs.showConfirmBoolean("Cuidado", "Deseas " + (isActive ? "des" : "") + "activar el id " + selected.getId() + " ?")) {
                selected.toggleActivo();
                boolean success = selected.updateOnDb() == 1;
                FxDialogs.showInfo("", "Usuario " + selected.getId() + (success ? " " : "NO ") + (isActive ? "des" : "") + "activado");
                if (!success)
                    selected.toggleActivo();
                else if (!showInactive && !selected.isActivo())
                    listedObjects.remove(selected);
            }
        }
    }

    @Override
    protected void addContent(boolean clean) {
        if (clean)
            listedObjects.clear();


        Collection<T> list = null;
        if (showInactive) {
            list = dataOrigin.getCache().values();
            fxBtnShowHide.setText("Todos");
        } else {
            list = dataOrigin.getActive(true);
            fxBtnShowHide.setText("Activos");
        }

        list.forEach(e -> {
            if (!listedObjects.contains(e))
                listedObjects.add(e);
        });
    }

}
