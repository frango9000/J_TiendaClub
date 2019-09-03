package app.model;

import app.data.DataStore;
import app.data.appdao.TransferenciaDao;
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
import org.checkerframework.checker.nullness.qual.NonNull;

public class Transferencia extends EntityInt {

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

    public Transferencia() {
        super(0);
    }

    public Transferencia(Usuario usuario, Sede sedeOrigen, Sede sedeDestino, Producto producto, int cantidad,
                         LocalDateTime fechahora) {
        super(0);
        setUsuario(usuario);
        setSedeOrigen(sedeOrigen);
        setSedeDestino(sedeDestino);
        setProducto(producto);
        setCantidad(cantidad);
        setFechahora(fechahora);
    }

    @Override
    public boolean setEntity(@NonNull ResultSet rs) {
        try {
            setId(rs.getInt(1));
            setIdUsuario(rs.getInt(2));
            setIdSedeOrigen(rs.getInt(3));
            setIdSedeDestino(rs.getInt(4));
            setIdProducto(rs.getInt(5));
            setCantidad(rs.getInt(6));
            setFechahora(DateUtils.toLocalDateTime(rs.getDate(7)));
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }
    @Override
    public ArrayList<String> getColumnNames() {
        return COLUMN_NAMES;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TransferenciaDao getDataStore() {
        return DataStore.getTransferencias();
    }

    @Override
    public boolean buildStatement(@NonNull PreparedStatement pst) {
        try {
            pst.setInt(1, getIdUsuario());
            pst.setInt(2, getIdSedeOrigen());
            pst.setInt(3, getIdSedeDestino());
            pst.setInt(4, getIdProducto());
            pst.setInt(5, getCantidad());
            pst.setTimestamp(6, DateUtils.toTimestamp(getFechahora()));
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }
    @Override
    public boolean restoreFrom(@NonNull IEntity objectV) {
        if (objectV.getClass().equals(getClass()) && getId() == objectV.getId() && !this.equals(objectV)) {
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
        setUsuario(DataStore.getUsuarios().getById().getCacheValue(idUsuario));
    }

    public int getIdSedeOrigen() {
        return idSedeOrigen;
    }

    public void setIdSedeOrigen(int idSedeOrigen) {
        setSedeOrigen(DataStore.getSedes().getById().getCacheValue(idSedeOrigen));
    }

    public int getIdSedeDestino() {
        return idSedeDestino;
    }

    public void setIdSedeDestino(int idSedeDestino) {
        setSedeDestino(DataStore.getSedes().getById().getCacheValue(idSedeDestino));
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        setProducto(DataStore.getProductos().getById().getCacheValue(idProducto));
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario   = usuario;
        this.idUsuario = getUsuario().getId();
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Sede getSedeOrigen() {
        return sedeOrigen;
    }

    public void setSedeOrigen(Sede sedeOrigen) {
        this.sedeOrigen   = sedeOrigen;
        this.idSedeOrigen = getSedeOrigen().getId();
    }

    public LocalDateTime getFechahora() {
        return fechahora;
    }

    public void setFechahora(LocalDateTime fechahora) {
        this.fechahora = fechahora;
    }


    public Sede getSedeDestino() {
        return sedeDestino;
    }

    public void setSedeDestino(Sede sedeDestino) {
        this.sedeDestino   = sedeDestino;
        this.idSedeDestino = getSedeDestino().getId();
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto   = producto;
        this.idProducto = getProducto().getId();
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
        return getId().equals(that.getId())
               && getIdUsuario() == that.getIdUsuario()
               && getIdSedeOrigen() == that.getIdSedeOrigen()
               && getIdSedeDestino() == that.getIdSedeDestino()
               && getIdProducto() == that.getIdProducto()
               && getCantidad() == that.getCantidad()
               && Objects.equal(getFechahora(), that.getFechahora());
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("id", id)
                          .add("idUsuario", idUsuario)
                          .add("usuario", usuario.toString())
                          .add("idSedeOrigen", idSedeOrigen)
                          .add("sedeOrigen", sedeOrigen.toString())
                          .add("idSedeDestino", idSedeDestino)
                          .add("sedeDestino", sedeDestino.toString())
                          .add("idProducto", idProducto)
                          .add("producto", producto.toString())
                          .add("cantidad", cantidad)
                          .add("fechahora", fechahora)
                          .toString();
    }
}
