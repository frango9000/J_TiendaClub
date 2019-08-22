package tiendaclub;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tiendaclub.control.PropsLoader;
import tiendaclub.control.table.UsuariosTableControl;
import tiendaclub.data.DataStore;

public class TestFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        PropsLoader.loadProps();

        DataStore.firstQuery();

        //        GenericActivableTablePane<Caja> genericTablePane =  new GenericActivableTablePane<>();
        UsuariosTableControl pane = new UsuariosTableControl();
        primaryStage.setScene(new Scene(pane));
        primaryStage.show();

    }
}
