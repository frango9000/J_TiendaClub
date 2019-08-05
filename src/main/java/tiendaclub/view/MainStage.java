package tiendaclub.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tiendaclub.model.models.Caja;
import tiendaclub.model.models.Sede;
import tiendaclub.model.models.Usuario;

import java.io.IOException;

public class MainStage extends Stage {

    private static Caja caja;
    private static Sede sede;
    private static Usuario user;

    public MainStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainPane.fxml"));
            setTitle("Main Screen");


            setScene(new Scene(root, 600, 400));
            root.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Caja getCaja() {
        return caja;
    }

    public static void setCaja(Caja caja) {
        MainStage.caja = caja;
    }

    public static Sede getSede() {
        return sede;
    }

    public static void setSede(Sede sede) {
        MainStage.sede = sede;
    }

    public static Usuario getUser() {
        return user;
    }

    public static void setUser(Usuario user) {
        MainStage.user = user;
    }
}
