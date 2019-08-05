package tiendaclub.model.models;

import tiendaclub.data.DataStore;
import tiendaclub.model.IPersistible;
import tiendaclub.model.models.abstracts.AbstractAcceso;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Acceso extends AbstractAcceso implements IPersistible {
    public static final String TABLE_NAME = "accesos";
    private static final ArrayList<String> COL_NAMES = new ArrayList<>(Collections.singletonList("nivel"));

    private HashMap<Integer, Usuario> usuarios = new HashMap<>();

    public Acceso(int id, String nivel) {
        super(id, nivel);
    }

    public Acceso(String nivel) {
        super(nivel);
    }

    public Acceso(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getString(2));
    }

    @Override
    public void buildStatement(PreparedStatement pst) throws SQLException {
        pst.setString(1, nivel);
    }

    @Override
    public void updateObject(ResultSet rs) throws SQLException {
        //setId(rs.getInt(1));
        setNivel(rs.getString(2));
    }

    @Override
    public String insertString() {
        return IPersistible.buildInsertString(TABLE_NAME, COL_NAMES);
    }

    @Override
    public String updateString() {
        return IPersistible.buildUpdateString(TABLE_NAME, ID_COL_NAME, COL_NAMES, getId());
    }

    public HashMap<Integer, Usuario> getUsuarios() {
        return usuarios;
    }

    @Override
    public int updateOnDb() {
        return DataStore.getAccesos().update(this);
    }

    @Override
    public int refreshFromDb() {
        return DataStore.getAccesos().updateObject(this);
    }

    @Override
    public String toString() {
        return id + " " + nivel;
    }
}
