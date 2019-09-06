package app.model;

import app.data.DataStore;
import app.data.appdao.CompraDao;
import app.misc.DateUtils;
import app.misc.Flogger;
import casteldao.model.EntityInt;
import casteldao.model.IEntity;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import org.checkerframework.checker.nullness.qual.NonNull;

public class Compra extends EntityInt {

    public static final String TABLE_NAME = "compras";
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("idUsuario", "idSede", "idProveedor", "fechahora"));


    protected int idUsuario;
    protected int idSede;
    protected int idProveedor;
    protected LocalDateTime fechahora;
    private Usuario usuario;
    private Proveedor proveedor;
    private Sede sede;

    public Compra() {
        super(0);
    }

    public Compra(int id, int idUsuario, int idSede, int idProveedor, LocalDateTime fechahora) {
        super(id);
        setIdUsuario(idUsuario);
        setIdSede(idSede);
        setIdProveedor(idProveedor);
        setFechahora(fechahora);
    }

    public Compra(int idUsuario, int idSede, int idProveedor, LocalDateTime fechahora) {
        this(0, idUsuario, idSede, idProveedor, fechahora);
    }

    public Compra(Usuario usuario, Sede sede, Proveedor proveedor, LocalDateTime fechahora) {
        super(0);
        setUsuario(usuario);
        setSede(sede);
        setProveedor(proveedor);
        setFechahora(fechahora);
    }

    @Override
    public boolean setEntity(@NonNull ResultSet rs) {
        try {
            setId(rs.getInt(1));
            setIdUsuario(rs.getInt(2));
            setIdSede(rs.getInt(3));
            setIdProveedor(rs.getInt(4));
            setFechahora(DateUtils.toLocalDateTime(rs.getDate(5)));
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }

    @Override
    public boolean buildStatement(@NonNull PreparedStatement pst) {
        try {
            pst.setInt(1, getIdUsuario());
            pst.setInt(2, getIdSede());
            pst.setInt(3, getIdProveedor());
            pst.setTimestamp(4, DateUtils.toTimestamp(getFechahora()));
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }

    @Override
    public boolean restoreFrom(@NonNull IEntity objectV) {
        if (objectV.getClass().equals(getClass()) && getId() == objectV.getId() && !this.equals(objectV)) {
            Compra newValues = (Compra) objectV;
            setUsuario(newValues.getUsuario());
            setSede(newValues.getSede());
            setProveedor(newValues.getProveedor());
            setFechahora(newValues.getFechahora());
            return true;
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CompraDao getDataStore() {
        return DataStore.getSessionStore().getCompras();
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        setUsuario(DataStore.getSessionStore().getUsuarios().getById().getCacheValue(idUsuario));
    }

    public int getIdSede() {
        return idSede;
    }

    public void setIdSede(int idSede) {
        setSede(DataStore.getSessionStore().getSedes().getById().getCacheValue(idSede));
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        setProveedor(DataStore.getSessionStore().getProveedores().getById().getCacheValue(idProveedor));
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario   = usuario;
        this.idUsuario = getUsuario().getId();
    }

    public LocalDateTime getFechahora() {
        return fechahora;
    }

    public void setFechahora(LocalDateTime fechahora) {
        this.fechahora = fechahora;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor   = proveedor;
        this.idProveedor = getProveedor().getId();
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede   = sede;
        this.idSede = getSede().getId();
    }

    public Set<Comprado> getComprados() {
        return DataStore.getSessionStore().getComprados().getIndexCompra().getCacheKeyValues(this);
    }

    @Override
    public ArrayList<String> getColumnNames() {
        return COLUMN_NAMES;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Compra compra = (Compra) o;
        return getId().equals(compra.getId()) &&
               getIdUsuario() == compra.getIdUsuario() &&
               getIdSede() == compra.getIdSede() &&
               getIdProveedor() == compra.getIdProveedor() &&
               Objects.equal(getFechahora(), compra.getFechahora());
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("id", id)
                          .add("idUsuario", idUsuario)
                          .add("usuario", usuario.toString())
                          .add("idSede", idSede)
                          .add("sede", sede.toString())
                          .add("idProveedor", idProveedor)
                          .add("proveedor", proveedor.toString())
                          .add("fechahora", fechahora)
                          .toString();
    }
}
