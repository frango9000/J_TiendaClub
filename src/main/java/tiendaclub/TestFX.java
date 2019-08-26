package tiendaclub;

import java.time.LocalDateTime;
import javafx.application.Application;
import javafx.stage.Stage;
import tiendaclub.control.PropsLoader;
import tiendaclub.data.DataStore;

public class TestFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        PropsLoader.loadProps();
//
//        DataStore.firstQuery();

//        DataStore.getSocios().getIndexFechaIn().getCacheKeys().forEach(System.out::println);
        System.out.println("\n");
        DataStore.getSocios()
                 .getIndexFechaIn()
                 .getKeyLesserThanValues(LocalDateTime.parse("2019-08-24T03:02:39"), true)
                 .forEach(System.out::println);

    }
}
