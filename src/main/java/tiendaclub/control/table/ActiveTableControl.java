package tiendaclub.control.table;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import tiendaclub.data.framework.dao.IdBoolIndexDao;
import tiendaclub.model.models.Usuario;
import tiendaclub.model.models.abstracts.Activable;
import tiendaclub.view.FxDialogs;

import java.io.IOException;
import java.util.Set;

public abstract class ActiveTableControl<T extends Activable> extends TableControl<T> {

    protected boolean showInactive = false;
    @FXML
    protected Button fxBtnShowHide;
    @FXML
    protected TableColumn<Usuario, Boolean> fxColumnIsActive;

    @FXML
    protected void fxBtnShowHideAction(ActionEvent actionEvent) {
        showInactive = !showInactive;
        if (showInactive) {
            getDataOrigin().getActiveIndex().getActiveCache(false).forEach(e -> {
                if (!listedObjects.contains(e))
                    listedObjects.add(e);
            });
        } else {
            listedObjects.removeAll(getDataOrigin().getActiveIndex().getActiveCache(false));
        }
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
    protected abstract IdBoolIndexDao<T> getDataOrigin();

    @Override
    protected void addContent(boolean clean) {
        Set<T> list = null;
        if (showInactive) {
            list = getDataOrigin().getIdIndex().getCacheValues();
            fxBtnShowHide.setText("Todos");
        } else {
            list = getDataOrigin().getActiveIndex().getActiveCache(true);
            fxBtnShowHide.setText("Activos");
        }

        if (clean) {
            listedObjects.clear();
            listedObjects.addAll(list);
        } else {
            list.forEach(e -> {
                if (!listedObjects.contains(e))
                    listedObjects.add(e);
            });
        }
    }

    @Override
    protected void fxBtnEditAction(ActionEvent actionEvent) throws IOException {
        super.fxBtnEditAction(actionEvent);
        T selected = fxTable.getSelectionModel().getSelectedItem();
        if (!showInactive && !selected.isActivo())
            listedObjects.remove(selected);
    }
}
