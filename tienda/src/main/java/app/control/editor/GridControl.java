package app.control.editor;

import app.data.casteldao.model.AbstractPersistible;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.GridPane;

public abstract class GridControl<T extends AbstractPersistible> extends GridPane {

    protected MenuButton fxButtonMenu;

    public void setFxButtonMenu(MenuButton fxButtonMenu) {
        this.fxButtonMenu = fxButtonMenu;
    }

    public abstract void updateEditee(T editee);

    public abstract T buildNew();

    public abstract void setFields(T editee);

    public abstract boolean validFields();
}
