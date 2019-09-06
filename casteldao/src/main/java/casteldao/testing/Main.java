package casteldao.testing;

public class Main {

    public static void main(String[] args) {
        TestSession.getSession().init();
        TestStore.initialQuery();
    }


}
