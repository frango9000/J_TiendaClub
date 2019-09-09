package casteldao.testing;

import casteldao.datasource.DataSourceIdActive;
import casteldao.datasource.DataSourceIdImpl;
import java.time.LocalDate;

public class TestStore {

    public static DataSourceIdImpl<Book> books = new DataSourceIdImpl<>(TestSession.getSession(), "book", Book.class);
    public static DataSourceIdActive<Author> authors = new DataSourceIdActive<>(TestSession.getSession(), "author", Author.class);


    public static void initialQuery() {
        books.getDao().queryAll();
        authors.getDao().queryAll();
    }

    public void insertMockData() throws CloneNotSupportedException {
        for (int i = 1; i < 51; i++) {
            Author author = new Author();
            author.setName("Author " + i);
            author.setBorn(LocalDate.ofEpochDay(i * Integer.MAX_VALUE / 10));
            author.setBackup();
            author.insertIntoDB();
            for (int j = 1; j < 31; j++) {
                Book book = new Book();
                book.setTitle(i + " Book " + j);
                book.setPublished(LocalDate.ofEpochDay(i * Integer.MAX_VALUE));
                book.setAuthor(author);
                book.setBackup();
                book.insertIntoDB();
            }
        }
    }
}
