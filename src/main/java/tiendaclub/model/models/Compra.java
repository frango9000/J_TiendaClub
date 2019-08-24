package tiendaclub.model.models;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.NonNull;
import tiendaclub.data.DataStore;
import tiendaclub.misc.DateUtils;
import tiendaclub.model.models.core.IPersistible;
import tiendaclub.model.models.core.Persistible;

public class Compra extends Persistible {

    public static final String TABLE_NAME = "compras";
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("idUsuario", "idSede", "idProveedor", "fechahora"));


    protected int idUsuario;
    protected int idSede;
    protected int idProveedor;
    protected LocalDateTime fechahora;
    private Usuario usuario;
    private Proveedor proveedor;
    private Sede sede;

    {
        this.tableName   = TABLE_NAME;
        this.columnNames = COLUMN_NAMES;
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

    public Compra(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), DateUtils.toLocalDateTime(rs.getDate(5)));
    }

    @Override
    public void buildStatement(@NonNull PreparedStatement pst) throws SQLException {
        pst.setInt(1, getIdUsuario());
        pst.setInt(2, getIdSede());
        pst.setInt(3, getIdProveedor());
        pst.setTimestamp(4, DateUtils.toTimestamp(getFechahora()));
    }

    @Override
    public <V extends IPersistible> boolean restoreFrom(@NonNull V objectV) {
        if (getId() == objectV.getId() && !this.equals(objectV)) {
            Compra newValues = (Compra) objectV;
            setUsuario(newValues.getUsuario());
            setSede(newValues.getSede());
            setProveedor(newValues.getProveedor());
            setFechahora(newValues.getFechahora());
            return true;
        }
        return false;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
        updateUsuario();
    }

    public int getIdSede() {
        return idSede;
    }

    public void setIdSede(int idSede) {
        this.idSede = idSede;
        updateSede();
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
        updateProveedor();
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

    private void updateUsuario() {
        setUsuario(DataStore.getUsuarios().getIndexId().getCacheValue(getIdUsuario()));
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor   = proveedor;
        this.idProveedor = getProveedor().getId();
    }

    private void updateProveedor() {
        setProveedor(DataStore.getProveedores().getIndexId().getCacheValue(getIdProveedor()));
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede   = sede;
        this.idSede = getSede().getId();
    }

    private void updateSede() {
        setSede(DataStore.getSedes().getIndexId().getCacheValue(getIdSede()));
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
        return getId() == compra.getId() && getIdUsuario() == compra.getIdUsuario() && getIdSede() == compra.getIdSede()
               && getIdProveedor() == compra.getIdProveedor() && Objects.equal(getFechahora(), compra.getFechahora());
    }

    @Override
    public int hashCode() {
        return getId();
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
