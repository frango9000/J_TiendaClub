package tiendaclub.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import tiendaclub.data.DataStore;

public class LoginPaneControl extends BorderPane {
    @FXML
    private CustomTextField usernameTextField;
    @FXML
    private CustomPasswordField passwordTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Label alertMsg;

    @FXML
    public void initialize() {
        loginButton.setDisable(true);
        usernameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() || passwordTextField.getText().trim().isEmpty());
        });
        passwordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() || usernameTextField.getText().trim().isEmpty());
        });
        usernameTextField.setOnAction(this::loginOnAct);
        passwordTextField.setOnAction(this::loginOnAct);
        usernameTextField.setText("admin");
        passwordTextField.setText("admin");
    }

    @FXML
    private void loginOnAct(ActionEvent actionEvent) {
        DataStore.setUser(DataStore.getUsuarios().query("username", usernameTextField.getText().trim(), "pass", passwordTextField.getText().trim()));
        System.out.println(DataStore.getUser());
        if (DataStore.getUser() == null) {
            alertMsg.setStyle("-fx-text-fill: red");
            alertMsg.setText("Access Denied");
        } else if (!DataStore.getUser().isActivo()) {
            alertMsg.setStyle("-fx-text-fill: red");
            alertMsg.setText("Account Locked");
        } else ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }
}
