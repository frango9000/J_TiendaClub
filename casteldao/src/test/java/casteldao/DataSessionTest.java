package casteldao;

import static com.google.common.truth.Truth.assertThat;

import casteldao.testing.TestSession;
import java.sql.SQLException;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataSessionTest {

    @BeforeClass
    public static void setUp() throws Exception {
        if (TestSession.getSession().listTables().size() == 0)
            TestSession.getSession().init();

    }

    @After
    public void tearDown() throws Exception {
        TestSession.getSession().close();
    }

    @Test
    public void connect() {
        assertThat(TestSession.getSession().connect()).isTrue();
    }

    @Test
    public void connectTry() {
        assertThat(TestSession.getSession().connectTry()).isTrue();
    }

    @Test
    public void isLinkValid() {
        assertThat(TestSession.getSession().isLinkValid()).isTrue();
    }

    @Test
    public void isSessionValid() {
        assertThat(TestSession.getSession().isSessionValid()).isTrue();
    }

    @Test
    public void whenCloseWithoutConnectShouldntThrow() {
        TestSession.getSession().close();
        try {
            assertThat(TestSession.getSession().conn.isClosed()).isTrue();
        } catch (SQLException ignored) {
        }
    }

    @Test
    public void whenConnectAndCloseShouldGetClosed() throws SQLException {
        TestSession.getSession().connect();
        TestSession.getSession().close();
        assertThat(TestSession.getSession().conn.isClosed()).isTrue();
    }

    @Test
    public void whenSetAutocloseShouldManageClosing() throws SQLException {
        TestSession.getSession().setAutoclose(false);
        TestSession.getSession().connect();
        TestSession.getSession().close();
        assertThat(TestSession.getSession().conn.isClosed()).isFalse();
        TestSession.getSession().setAutoclose(true);
        assertThat(TestSession.getSession().conn.isClosed()).isTrue();
    }

    @Test
    public void listTables() {
        assertThat(TestSession.getSession().listTables()).isNotEmpty();
    }

    @Test
    public void numOfTables() {
        assertThat(TestSession.getSession().listTables().size()).isEqualTo(2);
    }

    @Test
    public void createTables() {
//        TestSession.getSession().dropTables(TestSession.getSession().listTables());
//        assertThat(TestSession.getSession().createTables()).isEqualTo(2);
    }

    @Test
    public void insertData() {
//        TestSession.getSession().dropTables(TestSession.getSession().listTables());
//        TestSession.getSession().createTables();
//        assertThat(TestSession.getSession().insertData()).isEqualTo(2);
    }
}