package tiendaclub.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import tiendaclub.model.models.Acceso;
import tiendaclub.model.models.Caja;
import tiendaclub.model.models.Categoria;
import tiendaclub.model.models.CierreZ;
import tiendaclub.model.models.Compra;
import tiendaclub.model.models.Comprado;
import tiendaclub.model.models.Producto;
import tiendaclub.model.models.Proveedor;
import tiendaclub.model.models.Sede;
import tiendaclub.model.models.Socio;
import tiendaclub.model.models.Transferencia;
import tiendaclub.model.models.Usuario;
import tiendaclub.model.models.Vendido;
import tiendaclub.model.models.Venta;
import tiendaclub.model.models.abstracts.Identifiable;

public class DataFactory {

    public static Identifiable buildObject(ResultSet rs) throws SQLException {
        String originTable = rs.getMetaData().getTableName(1);

        switch (originTable) {
            case Acceso.TABLE_NAME:
                return new Acceso(rs);
            case Sede.TABLE_NAME:
                return new Sede(rs);
            case Caja.TABLE_NAME:
                return new Caja(rs);
            case Categoria.TABLE_NAME:
                return new Categoria(rs);
            case CierreZ.TABLE_NAME:
                return new CierreZ(rs);
            case Compra.TABLE_NAME:
                return new Compra(rs);
            case Comprado.TABLE_NAME:
                return new Comprado(rs);
            case Producto.TABLE_NAME:
                return new Producto(rs);
            case Proveedor.TABLE_NAME:
                return new Proveedor(rs);
            case Socio.TABLE_NAME:
                return new Socio(rs);
            case Transferencia.TABLE_NAME:
                return new Transferencia(rs);
            case Usuario.TABLE_NAME:
                return new Usuario(rs);
            case Vendido.TABLE_NAME:
                return new Vendido(rs);
            case Venta.TABLE_NAME:
                return new Venta(rs);
            default:
                throw new IllegalStateException("Unexpected value: " + rs.getMetaData().getTableName(1));
        }
    }
}