package app.data.casteldao.model;


import app.misc.Flogger;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class EntityInt extends AbstractEntity<Integer> {

    public EntityInt() {
    }

    public EntityInt(Integer id) {
        super(Math.max(id, 0));
    }

    @Override
    public void setId(ResultSet resultSet) {
        try {
            setId(resultSet.getInt(1));
        } catch (SQLException e) {
            Flogger.atSevere().withCause(e).log();
        }
    }

}
