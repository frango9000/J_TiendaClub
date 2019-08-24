package tiendaclub.control.table;

import java.io.IOException;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import tiendaclub.data.framework.dao.core.IndexIdActiveDao;
import tiendaclub.misc.Flogger;
import tiendaclub.model.models.core.Activable;
import tiendaclub.view.FxDialogs;

public abstract class ActiveTableControl<T extends Activable> extends TableControl<T> {

    protected boolean showInactive = false;

    @Override
    void initialize() {
        fxColumnId.setCellValueFactory(new PropertyValueFactory<T, Integer>("id"));
        fxColumnIsActive.setCellValueFactory(f -> f.getValue().activeProperty());
        fxColumnIsActive.setCellFactory(tc -> new CheckBoxTableCell<>());
    }

    @FXML
    protected void fxBtnShowHideAction(ActionEvent actionEvent) {
        showInactive = !showInactive;
        if (showInactive) {
            listedObjects.addAll(getDataOrigin().getIndexActive().getActiveCache(false));
        } else {
            listedObjects.removeAll(getDataOrigin().getIndexActive().getActiveCache(false));
        }
    }

    @FXML
    protected void fxBtnDisableAction(ActionEvent actionEvent) {
        T selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            boolean isActive = selected.isActivo();
            if (FxDialogs.showConfirmBoolean("Cuidado",
                                             "Deseas " + (isActive ? "des" : "") + "activar el id " + selected.getId() +
                                             " ?")) {
                try {
                    selected.setBackup();
                } catch (CloneNotSupportedException e) {
                    Flogger.atSevere().withCause(e).log("Clone Fail: " + selected.toString());
                }
                selected.toggleActivo();
                boolean success = selected.updateOnDb() == 1;
                FxDialogs.showInfo("",
                                   "Usuario " + selected.getId() + (success ? " " : " NO ") + (isActive ? "des" : "")
                                   + "activado");
                if (!success) {
                    selected.toggleActivo();
                } else if (!showInactive && !selected.isActivo()) {
                    listedObjects.remove(selected);
                }
            }
        }
    }

    @Override
    protected abstract IndexIdActiveDao<T> getDataOrigin();

    @Override
    protected void addContent(boolean clean) {
        Set<T> list = null;
        if (showInactive) {
            list = getDataOrigin().getIndexId().getCacheValues();
            fxBtnShowHide.setText("Todos");
        } else {
            list = getDataOrigin().getIndexActive().getActiveCache(true);
            fxBtnShowHide.setText("Activos");
        }

        if (clean) {
            listedObjects.clear();
            listedObjects.addAll(list);
        } else {
            list.forEach(e -> {
                if (!listedObjects.contains(e)) {
                    listedObjects.add(e);
                }
            });
        }
    }

    @FXML
    @Override
    protected void fxBtnEditAction(ActionEvent actionEvent) throws IOException {
        super.fxBtnEditAction(actionEvent);
        T selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null && !showInactive && !selected.isActivo()) {
            listedObjects.remove(selected);
        }
    }
}
