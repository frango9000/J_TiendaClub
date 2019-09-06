package app.control;

import app.data.DataStore;
import app.misc.FxDialogs;
import app.model.Caja;
import app.model.Sede;
import app.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

//import org.controlsfx.control.textfield.CustomPasswordField;
//import org.controlsfx.control.textfield.CustomTextField;

public class LoginPaneControl extends BorderPane {

    @FXML
    public ComboBox<Sede> fxBoxSedes;
    @FXML
    public ComboBox<Caja> fxBoxCajas;
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

        fxBoxSedes.getItems().addAll(DataStore.getSessionStore().getSedes().getAllCache());
        fxBoxSedes.setOnAction(event -> fxBoxCajas.getItems().setAll(fxBoxSedes.getSelectionModel().getSelectedItem().getCajas()));
        fxBoxSedes.getSelectionModel().select(1);

        if (fxBoxSedes.getSelectionModel().getSelectedItem() != null) {
            fxBoxCajas.getItems().addAll(fxBoxSedes.getSelectionModel().getSelectedItem().getCajas());
            fxBoxCajas.getSelectionModel().select(1);
        }

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
        Usuario user = DataStore.getSessionStore().getUsuarios().getUsernameIndex().getValue(usernameTextField.getText().trim());
        if (user == null) {
            alertMsg.setStyle("-fx-text-fill: red");
            alertMsg.setText("User not found");
        } else if (!user.getPass().equals(passwordTextField.getText().trim())) {
            alertMsg.setStyle("-fx-text-fill: red");
            alertMsg.setText("Access Denied");
        } else if (!user.isActive()) {
            alertMsg.setStyle("-fx-text-fill: red");
            alertMsg.setText("Account Locked");
        } else if (user.getAcceso().getId() > 2 && (fxBoxCajas.getSelectionModel().getSelectedItem() == null ||
                                                    fxBoxSedes.getSelectionModel().getSelectedItem() == null)) {
            FxDialogs.showError(null, "Unset Values");
        } else {
            DataStore.getSessionStore().setSede(fxBoxSedes.getSelectionModel().getSelectedItem());
            DataStore.getSessionStore().setCaja(fxBoxCajas.getSelectionModel().getSelectedItem());
            DataStore.getSessionStore().setUsuario(user);
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        }
    }
}
