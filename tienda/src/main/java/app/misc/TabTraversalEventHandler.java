package app.misc;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Handles tab/shift-tab keystrokes to navigate to other fields, ctrl-tab to insert a tab character in the text area.
 * <p>
 * https://stackoverflow.com/questions/12860478/tab-key-navigation-in-javafx-textarea
 * <p>
 * Dec 5 '17 at 4:11 MarcG
 * <p>
 * Oct 11 '17 at 10:24 Johan De Schutter
 */
public class TabTraversalEventHandler implements EventHandler<KeyEvent> {

    @Override
    public void handle(KeyEvent event) {
        KeyCode code = event.getCode();

        if (code == KeyCode.TAB && !event.isShiftDown() && !event.isControlDown()) {
            event.consume();
            Node node = (Node) event.getSource();
            KeyEvent newEvent
                = new KeyEvent(event.getSource(),
                               event.getTarget(), event.getEventType(),
                               event.getCharacter(), event.getText(),
                               event.getCode(), event.isShiftDown(),
                               true, event.isAltDown(),
                               event.isMetaDown());

            node.fireEvent(newEvent);
        }
    }
}
