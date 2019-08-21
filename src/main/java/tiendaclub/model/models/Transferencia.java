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
import tiendaclub.model.models.core.IPersistible;
import tiendaclub.model.models.core.Persistible;
import tiendaclub.model.utils.DateUtils;

public class Transferencia extends Persistible {

    public static final String TABLE_NAME = "transferencias";
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("idUsuario", "idSedeOrigen", "idSedeDestino", "idProducto", "cantidad", "fechahora"));


    protected int idUsuario;
    protected int idSedeOrigen;
    protected int idSedeDestino;
    protected int idProducto;
    protected int cantidad;
    protected LocalDateTime fechahora;
    private Usuario usuario;
    private Sede sedeOrigen;
    private Sede sedeDestino;
    private Producto producto;

    {
        this.tableName = TABLE_NAME;
        this.columnNames = COLUMN_NAMES;
    }

    public Transferencia(int id, int idUsuario, int idSedeOrigen, int idSedeDestino, int idProducto, int cantidad, LocalDateTime fechahora) {
        super(id);
        setCantidad(cantidad);
        setFechahora(fechahora);
        setIdUsuario(idUsuario);
        setIdSedeOrigen(idSedeOrigen);
        setIdSedeDestino(idSedeDestino);
        setIdProducto(idProducto);
    }

    public Transferencia(int idUsuario, int idSedeOrigen, int idSedeDestino, int idProducto, int cantidad, LocalDateTime fechahora) {
        this(0, idUsuario, idSedeOrigen, idSedeDestino, idProducto, cantidad, fechahora);
    }

    public Transferencia(Usuario usuario, Sede sedeOrigen, Sede sedeDestino, Producto producto, int cantidad, LocalDateTime fechahora) {
        super(0);
        setUsuario(usuario);
        setSedeOrigen(sedeOrigen);
        setSedeDestino(sedeDestino);
        setProducto(producto);
        setCantidad(cantidad);
        setFechahora(fechahora);
    }

    public Transferencia(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), DateUtils.toLocalDateTime(rs.getDate(7)));
    }

    @Override
    public void buildStatement(@NonNull PreparedStatement pst) throws SQLException {
        pst.setInt(1, getIdUsuario());
        pst.setInt(2, getIdSedeOrigen());
        pst.setInt(3, getIdSedeDestino());
        pst.setInt(4, getIdProducto());
        pst.setInt(5, getCantidad());
        pst.setTimestamp(6, DateUtils.toTimestamp(getFechahora()));
    }

    @Override
    public <V extends IPersistible> boolean restoreFrom(@NonNull V objectV) {
        if (getId() == objectV.getId() && !this.equals(objectV)) {
            Transferencia newValues = (Transferencia) objectV;
            setUsuario(newValues.getUsuario());
            setSedeOrigen(newValues.getSedeOrigen());
            setSedeDestino(newValues.getSedeDestino());
            setProducto(newValues.getProducto());
            setCantidad(newValues.getCantidad());
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

    public int getIdSedeOrigen() {
        return idSedeOrigen;
    }

    public void setIdSedeOrigen(int idSedeOrigen) {
        this.idSedeOrigen = idSedeOrigen;
        updateSedeOrigen();
    }

    public int getIdSedeDestino() {
        return idSedeDestino;
    }

    public void setIdSedeDestino(int idSedeDestino) {
        this.idSedeDestino = idSedeDestino;
        updateSedeDestino();
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
        updateProducto();
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        this.idUsuario = getUsuario().getId();
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    private void updateUsuario() {
        setUsuario(DataStore.getUsuarios().getIndexId().getCacheValue(getIdUsuario()));
    }

    public Sede getSedeOrigen() {
        return sedeOrigen;
    }

    public void setSedeOrigen(Sede sedeOrigen) {
        this.sedeOrigen = sedeOrigen;
        this.idSedeOrigen = getSedeOrigen().getId();
    }

    public LocalDateTime getFechahora() {
        return fechahora;
    }

    public void setFechahora(LocalDateTime fechahora) {
        this.fechahora = fechahora;
    }

    private void updateSedeOrigen() {
        setSedeOrigen(DataStore.getSedes().getIndexId().getCacheValue(getIdSedeOrigen()));
    }

    public Sede getSedeDestino() {
        return sedeDestino;
    }

    public void setSedeDestino(Sede sedeDestino) {
        this.sedeDestino = sedeDestino;
        this.idSedeDestino = getSedeDestino().getId();
    }

    private void updateSedeDestino() {
        setSedeDestino(DataStore.getSedes().getIndexId().getCacheValue(getIdSedeDestino()));
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        this.idProducto = getProducto().getId();
    }

    private void updateProducto() {
        setProducto(DataStore.getProductos().getIndexId().getCacheValue(getIdProducto()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transferencia that = (Transferencia) o;
        return getId() == that.getId() && getIdUsuario() == that.getIdUsuario()
                && getIdSedeOrigen() == that.getIdSedeOrigen() && getIdSedeDestino() == that.getIdSedeDestino()
                && getIdProducto() == that.getIdProducto() && getCantidad() == that.getCantidad()
                && Objects.equal(getFechahora(), that.getFechahora());
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("idUsuario", idUsuario).add("usuario", usuario.toString()).add("idSedeOrigen", idSedeOrigen).add("sedeOrigen", sedeOrigen.toString()).add("idSedeDestino", idSedeDestino).add("sedeDestino", sedeDestino.toString()).add("idProducto", idProducto).add("producto", producto.toString()).add("cantidad", cantidad).add("fechahora", fechahora).toString();
    }
}
