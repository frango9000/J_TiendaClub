package casteldao.testing;

import casteldao.datasource.DataSourceIdActive;
import casteldao.misc.Flogger;
import casteldao.model.ActivableEntity;
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

public class Author extends ActivableEntity {

    String name;
    LocalDate born;


    public Author() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBorn() {
        return born;
    }

    public void setBorn(LocalDate born) {
        this.born = born;
    }

    @Override
    public boolean setEntity(ResultSet resultSet) {
        try {
            setId(resultSet.getInt(1));
            setName(resultSet.getString(2));
            setBorn(DateUtils.toLocalDate(resultSet.getTimestamp(3)));
            setActive(resultSet.getBoolean(4));
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }

    @Override
    public boolean buildStatement(@NonNull PreparedStatement pst) throws SQLException {
        try {
            pst.setString(1, getName());
            pst.setTimestamp(2, DateUtils.toTimestamp(getBorn()));
            pst.setBoolean(3, isActive());
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }

    @Override
    public ArrayList<String> getColumnNames() {
        return new ArrayList<>(Arrays.asList("name", "active", "active"));
    }

    @Override
    public DataSourceIdActive<Author> getDataStore() {
        return TestStore.authors;
    }

    @Override
    public boolean restoreFrom(@NonNull IEntity objectV) {
        if (objectV.getClass().equals(getClass()) && getId() == objectV.getId() && !this.equals(objectV)) {
            Author newValues = (Author) objectV;
            setName(newValues.getName());
            setBorn(newValues.getBorn());
            setActive(newValues.isActive());
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
        Author author = (Author) o;
        return Objects.equals(getId(), author.getId()) &&
               Objects.equals(getName(), author.getName()) &&
               Objects.equals(getBorn(), author.getBorn());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("id", id)
                          .add("name", name)
                          .add("born", born)
                          .toString();
    }
}
