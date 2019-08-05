package tiendaclub.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import tiendaclub.MainFX;
import tiendaclub.data.DataStore;
import tiendaclub.view.MainStage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginPaneControl implements Initializable {
    @FXML
    private CustomTextField usernameTextField;
    @FXML
    private CustomPasswordField passwordTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Label alertMsg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginButton.setDisable(true);
        usernameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() || passwordTextField.getText().trim().isEmpty());
        });
        passwordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() || usernameTextField.getText().trim().isEmpty());
        });

    }


    @FXML
    private void loginOnAct(ActionEvent actionEvent) {
        MainStage.setUser(DataStore.getUsuarios().query("username", usernameTextField.getText().trim(), "pass", passwordTextField.getText().trim()));
        System.out.println(MainStage.getUser());
        if (MainStage.getUser() == null) {
            actionEvent.consume();
            alertMsg.setStyle("-fx-text-fill: red");
            alertMsg.setText("Acces Denied");

        } else MainFX.getLoginStage().close();

    }
}
