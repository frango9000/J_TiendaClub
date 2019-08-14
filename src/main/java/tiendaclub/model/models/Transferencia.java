package tiendaclub.model.models;

import tiendaclub.data.DataStore;
import tiendaclub.model.models.abstracts.AbstractTransferencia;
import tiendaclub.model.utils.DateUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Transferencia extends AbstractTransferencia {
    public static final String TABLE_NAME = "transferencias";
    private static final ArrayList<String> COL_NAMES = new ArrayList<>(Arrays.asList("idUsuario", "idSedeOrigen", "idSedeDestino", "idProducto", "cantidad", "fechahora"));

    private Usuario usuario;
    private Sede sedeOrigen;
    private Sede sedeDestino;
    private Producto producto;

    public Transferencia(int id, int idUsuario, int idSedeOrigen, int idSedeDestino, int idProducto, int cantidad, LocalDateTime fechahora) {
        super(id, idUsuario, idSedeOrigen, idSedeDestino, idProducto, cantidad, fechahora);
        updateUsuario();
        updateSedeOrigen();
        updateSedeDestino();
        updateProducto();
    }

    public Transferencia(int idUsuario, int idSedeOrigen, int idSedeDestino, int idProducto, int cantidad) {
        super(idUsuario, idSedeOrigen, idSedeDestino, idProducto, cantidad);
        updateUsuario();
        updateSedeOrigen();
        updateSedeDestino();
        updateProducto();
    }

    public Transferencia(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), DateUtils.toLocalDateTime(rs.getDate(7)));
    }

    @Override
    public void buildStatement(PreparedStatement pst) throws SQLException {
        pst.setInt(1, getIdUsuario());
        pst.setInt(2, getIdSedeOrigen());
        pst.setInt(3, getIdSedeDestino());
        pst.setInt(4, getIdProducto());
        pst.setInt(5, getCantidad());
        pst.setTimestamp(6, DateUtils.toTimestamp(getFechahora()));
    }

    @Override
    public void updateObject(ResultSet rs) throws SQLException {
        //setId(rs.getInt(1));
        setIdUsuario(rs.getInt(2));
        setIdSedeOrigen(rs.getInt(3));
        setIdSedeDestino(rs.getInt(4));
        setIdProducto(rs.getInt(5));
        setCantidad(rs.getInt(6));
        setFechahora(DateUtils.toLocalDateTime(rs.getTimestamp(7)));
    }

    @Override
    public void setIdUsuario(int idUsuario) {
        super.setIdUsuario(idUsuario);
        updateUsuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario2) {
        if (usuario != null)
            usuario.getTransferencias().remove(getId());
        this.usuario = usuario2;
        if (usuario != null)
            usuario.getTransferencias().put(getId(), this);
    }

    private void updateUsuario() {
        setUsuario(DataStore.getUsuarios().getIdIndex().getMap(getIdUsuario()));
    }

    @Override
    public void setIdSedeOrigen(int idSedeOrigen) {
        super.setIdSedeOrigen(idSedeOrigen);
        updateSedeOrigen();
    }

    public Sede getSedeOrigen() {
        return sedeOrigen;
    }

    public void setSedeOrigen(Sede sedeOrigen2) {
        if (sedeOrigen != null)
            sedeOrigen.getTransferIn().remove(getId());
        this.sedeOrigen = sedeOrigen2;
        if (sedeOrigen != null)
            sedeOrigen.getTransferIn().put(getId(), this);
    }

    private void updateSedeOrigen() {
        setSedeOrigen(DataStore.getSedes().getIdIndex().getMap(getIdSedeOrigen()));
    }


    @Override
    public void setIdSedeDestino(int idSedeDestino) {
        super.setIdSedeDestino(idSedeDestino);
        updateSedeDestino();
    }

    public Sede getSedeDestino() {
        return sedeDestino;
    }

    public void setSedeDestino(Sede sedeDestino2) {
        if (sedeDestino != null)
            sedeDestino.getTransferIn().remove(getId());
        this.sedeDestino = sedeDestino2;
        if (sedeDestino != null)
            sedeDestino.getTransferIn().put(getId(), this);
    }

    private void updateSedeDestino() {
        setSedeDestino(DataStore.getSedes().getIdIndex().getMap(getIdSedeDestino()));
    }


    @Override
    public void setIdProducto(int idProducto) {
        super.setIdProducto(idProducto);
        updateProducto();
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto2) {
        //if(producto!=null)
        //  producto.getComprados().remove(getId()); //No Use
        this.producto = producto2;
        //if(producto!=null)
        //  producto.getComprados().put(getId(), this); //No Use
    }

    private void updateProducto() {
        setProducto(DataStore.getProductos().getIdIndex().getMap(getIdProducto()));
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
