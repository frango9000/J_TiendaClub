package tiendaclub.control.editor;

import javafx.scene.control.MenuButton;
import javafx.scene.layout.GridPane;
import tiendaclub.data.casteldao.daomodel.Persistible;

public abstract class GridControl<T extends Persistible> extends GridPane {

    protected MenuButton fxButtonMenu;

    public void setFxButtonMenu(MenuButton fxButtonMenu) {
        this.fxButtonMenu = fxButtonMenu;
    }

    public abstract void updateEditee(T editee);

    public abstract T buildNew();

    public abstract void setFields(T editee);

    public abstract boolean validFields();
}
