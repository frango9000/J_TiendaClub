package casteldao.model;

import casteldao.datasource.DataSource;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.checkerframework.checker.nullness.qual.NonNull;

public class EntityFactory<T extends IEntity<I>, I extends Serializable> {


    public AbstractEntity<I> getNewInstance() {
        return new AbstractEntity<>() {
            @Override
            public void setId(ResultSet resultSet) {

            }

            @Override
            public boolean setEntity(ResultSet resultSet) {
                return false;
            }

            @Override
            public boolean buildStatement(@NonNull PreparedStatement pst) throws SQLException {
                return false;
            }

            @Override
            public ArrayList<String> getColumnNames() {
                return null;
            }

            @Override
            public <E extends IEntity<I>> DataSource<E, I> getDataStore() {
                return null;
            }

            @Override
            public boolean restoreFrom(@NonNull IEntity objectV) {
                return false;
            }
        };
    }


}



