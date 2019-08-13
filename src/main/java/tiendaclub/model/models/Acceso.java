package tiendaclub.model.models;

import tiendaclub.model.models.abstracts.AbstractAcceso;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Acceso extends AbstractAcceso {
    public static final String TABLE_NAME = "accesos";
    private static final ArrayList<String> COL_NAMES = new ArrayList<>(Collections.singletonList("nivel"));

    private HashMap<Integer, Usuario> usuarios = new HashMap<>();

    public Acceso(int id, String nivel) {
        super(id, nivel);
    }

    public Acceso(String nivel) {
        this(0, nivel);
    }

    public Acceso(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getString(2));
    }

    @Override
    public void buildStatement(PreparedStatement pst) throws SQLException {
        pst.setString(1, getNivel());
    }

    @Override
    public void updateObject(ResultSet rs) throws SQLException {
        //setId(rs.getInt(1));
        setNivel(rs.getString(2));
    }

    public HashMap<Integer, Usuario> getUsuarios() {
        return usuarios;
    }

    @Override
    public String toString() {
        return getId() + " " + getNivel();
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public ArrayList<String> getColNames() {
        return COL_NAMES;
    }
}
