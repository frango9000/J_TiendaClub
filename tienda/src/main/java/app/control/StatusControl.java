package app.control;

import app.misc.FXMLStage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class StatusControl extends BorderPane {

    public static Pane loadFXML() {
        return FXMLStage.getPane("/fxml/StatusPane.fxml");
    }

}
