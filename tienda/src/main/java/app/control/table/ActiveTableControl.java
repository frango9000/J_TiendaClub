package app.control.table;

import app.misc.Flogger;
import app.misc.FxDialogs;
import casteldao.datasource.DataSourceIdActive;
import casteldao.model.ActivableEntity;
import java.io.IOException;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

public abstract class ActiveTableControl<E extends ActivableEntity> extends TableControl<E> {

    protected boolean showInactive = false;

    @Override
    void initialize() {
        fxColumnId.setCellValueFactory(new PropertyValueFactory<E, Integer>("id"));
        fxColumnIsActive.setCellValueFactory(new PropertyValueFactory<>("active"));
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
        E selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            boolean isActive = selected.isActive();
            if (FxDialogs.showConfirmBoolean("Cuidado",
                                             "Deseas " + (isActive ? "des" : "") + "activar el id " + selected.getId() +
                                             " ?")) {
                try {
                    selected.setBackup();
                } catch (CloneNotSupportedException e) {
                    Flogger.atSevere().withCause(e).log("Clone Fail: " + selected.toString());
                }
                selected.toggleActive();
                boolean success = selected.updateOnDb() == 1;
                FxDialogs.showInfo("",
                                   "Usuario " + selected.getId() + (success ? " " : " NO ") + (isActive ? "des" : "")
                                   + "activado");
                if (!success) {
                    selected.toggleActive();
                } else if (!showInactive && !selected.isActive()) {
                    listedObjects.remove(selected);
                }
            }
        }
    }

    @Override
    protected abstract DataSourceIdActive<E> getDataOrigin();

    @Override
    protected void addContent(boolean clean) {
        Set<E> list = null;
        if (showInactive) {
            list = getDataOrigin().getById().getCacheValues();
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
        E selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null && !showInactive && !selected.isActive()) {
            listedObjects.remove(selected);
        }
    }
}
