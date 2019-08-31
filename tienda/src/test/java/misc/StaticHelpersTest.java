package misc;

import app.misc.StaticHelpers;
import com.google.common.truth.Truth;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import org.junit.Test;

public class StaticHelpersTest {


    @Test
    public void isInteger_Test() {
        Truth.assertThat(StaticHelpers.isInteger("1")).isTrue();
    }

    @Test
    public void isNotInteger_Test() {
        Truth.assertThat(StaticHelpers.isInteger("L")).isFalse();
    }

    @Test
    public void byteSizeFormatter() {
        Truth.assertThat(StaticHelpers.byteSizeFormatter(Integer.MAX_VALUE)).isEqualTo("2.147.483.647");
        Truth.assertThat(StaticHelpers.byteSizeFormatter(Long.MAX_VALUE)).isEqualTo("9.223.372.036.854.775.807");
    }

    @Test
    public void textInputEmptyToNull_Test() {
        Platform.startup(() -> {
        });
        TextField lbl = new TextField();
        Truth.assertThat(StaticHelpers.textInputEmptyToNull(lbl)).isNull();
        lbl.setText("");
        Truth.assertThat(StaticHelpers.textInputEmptyToNull(lbl)).isNull();
        lbl.setText(" ");
        Truth.assertThat(StaticHelpers.textInputEmptyToNull(lbl)).isNull();
        lbl.setText("  ");
        Truth.assertThat(StaticHelpers.textInputEmptyToNull(lbl)).isNull();
        lbl.setText("1");
        Truth.assertThat(StaticHelpers.textInputEmptyToNull(lbl)).isNotNull();
    }
}