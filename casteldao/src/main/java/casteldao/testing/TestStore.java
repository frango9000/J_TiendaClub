package casteldao.testing;

import casteldao.datasource.DataSourceIdActive;
import casteldao.datasource.DataSourceIdImpl;

public class TestStore {

    public static DataSourceIdImpl<Book> books = new DataSourceIdImpl<>(TestSession.getSession(), "book", Book.class);
    public static DataSourceIdActive<Author> authors = new DataSourceIdActive<>(TestSession.getSession(), "author", Author.class);


    public static void initialQuery() {
        books.getDao().queryAll();
        authors.getDao().queryAll();
    }
}
