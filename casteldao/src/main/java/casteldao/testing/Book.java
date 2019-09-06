package casteldao.testing;

import casteldao.datasource.DataSourceIdImpl;
import casteldao.misc.Flogger;
import casteldao.model.EntityInt;
import casteldao.model.IEntity;
import com.google.common.base.MoreObjects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;

public class Book extends EntityInt {

    String title;
    LocalDate published;
    Author author;

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public LocalDate getPublished() {
        return published;
    }

    public void setPublished(LocalDate published) {
        this.published = published;
    }

    @Override
    public boolean setEntity(ResultSet resultSet) {
        try {
            setId(resultSet.getInt(1));
            setTitle(resultSet.getString(2));
            setPublished(DateUtils.toLocalDate(resultSet.getTimestamp(3)));
            setAuthor(TestStore.authors.getById().getCacheValue(resultSet.getInt(4)));
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }

    @Override
    public boolean buildStatement(@NonNull PreparedStatement pst) throws SQLException {
        try {
            pst.setString(1, getTitle());
            pst.setTimestamp(2, DateUtils.toTimestamp(getPublished()));
            pst.setInt(3, author.getId());
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }

    @Override
    public ArrayList<String> getColumnNames() {
        return new ArrayList<>(Arrays.asList("title", "published", "idAuthor"));
    }

    @Override
    public DataSourceIdImpl<Book> getDataStore() {
        return TestStore.books;
    }

    @Override
    public boolean restoreFrom(@NonNull IEntity objectV) {
        if (objectV.getClass().equals(getClass()) && getId() == objectV.getId() && !this.equals(objectV)) {
            Book newValues = (Book) objectV;
            setTitle(newValues.getTitle());
            setAuthor(newValues.getAuthor());
            setPublished(newValues.getPublished());
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        Book book = (Book) o;
        return Objects.equals(getTitle(), book.getTitle()) &&
               Objects.equals(getPublished(), book.getPublished()) &&
               Objects.equals(getAuthor(), book.getAuthor());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("id", id)
                          .add("title", title)
                          .add("published", published)
                          .add("author", author)
                          .toString();
    }
}
