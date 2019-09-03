package app.control;

import app.data.DataStore;
import app.data.SessionStore;
import app.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

//import org.controlsfx.control.textfield.CustomPasswordField;
//import org.controlsfx.control.textfield.CustomTextField;

public class LoginPaneControl extends BorderPane {

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
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
        Usuario user = DataStore.getUsuarios().getUsernameIndex().getValue(usernameTextField.getText().trim());
        if (user == null) {
            alertMsg.setStyle("-fx-text-fill: red");
            alertMsg.setText("User not found");
        } else if (!user.getPass().equals(passwordTextField.getText().trim())) {
            alertMsg.setStyle("-fx-text-fill: red");
            alertMsg.setText("Access Denied");
        } else if (!user.isActive()) {
            alertMsg.setStyle("-fx-text-fill: red");
            alertMsg.setText("Account Locked");
        } else {
            SessionStore.setUsuario(user);
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        }
    }
}
