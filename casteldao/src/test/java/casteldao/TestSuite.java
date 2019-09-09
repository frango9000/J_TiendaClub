package casteldao;

import casteldao.testing.TestSession;
import casteldao.testing.TestStoreTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({DataSessionTest.class, TestStoreTest.class})
public class TestSuite {

    @BeforeClass
    public static void setUp() {
        System.out.println("setting up");
        TestSession.getSession().init();
    }

    @AfterClass
    public static void tearDown() {
        System.out.println("tearing down");
    }
}
