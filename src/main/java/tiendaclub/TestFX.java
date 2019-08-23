package tiendaclub;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tiendaclub.control.PropsLoader;
import tiendaclub.control.editor.EditorControl;
import tiendaclub.control.editor.UsuarioEditorControl;
import tiendaclub.data.DataStore;
import tiendaclub.model.models.Usuario;

public class TestFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        PropsLoader.loadProps();

        DataStore.firstQuery();

        EditorControl<Usuario> pane = UsuarioEditorControl.getPane();
        pane.setEditee(DataStore.getUsuarios().getIndexId().getValue(4));
        primaryStage.setScene(new Scene(pane));
        primaryStage.show();

    }
}
