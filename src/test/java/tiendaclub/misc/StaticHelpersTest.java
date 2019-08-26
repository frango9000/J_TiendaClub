package tiendaclub.misc;

import static com.google.common.truth.Truth.assertThat;

import javafx.scene.control.TextField;
import org.junit.Test;
import tiendaclub.MainFX;

public class StaticHelpersTest {


    @Test
    public void isInteger_Test() {
        assertThat(StaticHelpers.isInteger("1")).isTrue();
    }

    @Test
    public void isNotInteger_Test() {
        assertThat(StaticHelpers.isInteger("L")).isFalse();
    }

    @Test
    public void byteSizeFormatter() {
        assertThat(StaticHelpers.byteSizeFormatter(Integer.MAX_VALUE)).isEqualTo("2.147.483.647");
        assertThat(StaticHelpers.byteSizeFormatter(Long.MAX_VALUE)).isEqualTo("9.223.372.036.854.775.807");
    }

    @Test
    public void textInputEmptyToNull_Test() {
        MainFX.initializeToolkit();
        TextField lbl = new TextField();
        assertThat(StaticHelpers.textInputEmptyToNull(lbl)).isNull();
        lbl.setText("");
        assertThat(StaticHelpers.textInputEmptyToNull(lbl)).isNull();
        lbl.setText(" ");
        assertThat(StaticHelpers.textInputEmptyToNull(lbl)).isNull();
        lbl.setText("  ");
        assertThat(StaticHelpers.textInputEmptyToNull(lbl)).isNull();
        lbl.setText("1");
        assertThat(StaticHelpers.textInputEmptyToNull(lbl)).isNotNull();
    }
}