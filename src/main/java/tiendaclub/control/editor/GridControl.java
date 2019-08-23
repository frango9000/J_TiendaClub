package tiendaclub.control.editor;

import javafx.scene.layout.GridPane;
import tiendaclub.model.models.core.Persistible;

public abstract class GridControl<T extends Persistible> extends GridPane {

    public abstract void updateEditee(T editee);

    public abstract T buildNew();

    public abstract void setFields(T editee);

    public abstract boolean validFields();
}
