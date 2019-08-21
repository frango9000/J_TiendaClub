package tiendaclub.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import tiendaclub.misc.Globals;
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
import tiendaclub.model.models.core.IPersistible;

public class DataFactory implements Globals {

    public static IPersistible buildObject(ResultSet rs) throws SQLException {
        String originTable = rs.getMetaData().getTableName(1);
        IPersistible iPersistible = null;
        switch (originTable) {
            case Acceso.TABLE_NAME:
                iPersistible = new Acceso(rs);
                break;
            case Sede.TABLE_NAME:
                iPersistible = new Sede(rs);
                break;
            case Caja.TABLE_NAME:
                iPersistible = new Caja(rs);
                break;
            case Categoria.TABLE_NAME:
                iPersistible = new Categoria(rs);
                break;
            case CierreZ.TABLE_NAME:
                iPersistible = new CierreZ(rs);
                break;
            case Compra.TABLE_NAME:
                iPersistible = new Compra(rs);
                break;
            case Comprado.TABLE_NAME:
                iPersistible = new Comprado(rs);
                break;
            case Producto.TABLE_NAME:
                iPersistible = new Producto(rs);
                break;
            case Proveedor.TABLE_NAME:
                iPersistible = new Proveedor(rs);
                break;
            case Socio.TABLE_NAME:
                iPersistible = new Socio(rs);
                break;
            case Transferencia.TABLE_NAME:
                iPersistible = new Transferencia(rs);
                break;
            case Usuario.TABLE_NAME:
                iPersistible = new Usuario(rs);
                break;
            case Vendido.TABLE_NAME:
                iPersistible = new Vendido(rs);
                break;
            case Venta.TABLE_NAME:
                iPersistible = new Venta(rs);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + rs.getMetaData().getTableName(1));
        }
        if (VERBOSE_FACTORY) {
            System.out.println(iPersistible.toString());
        }
        return iPersistible;
    }
}