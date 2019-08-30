package tiendaclub.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import tiendaclub.data.casteldao.daomodel.IPersistible;
import tiendaclub.misc.Globals;
import tiendaclub.model.Acceso;
import tiendaclub.model.Caja;
import tiendaclub.model.Categoria;
import tiendaclub.model.CierreZ;
import tiendaclub.model.Compra;
import tiendaclub.model.Comprado;
import tiendaclub.model.Producto;
import tiendaclub.model.Proveedor;
import tiendaclub.model.Sede;
import tiendaclub.model.Socio;
import tiendaclub.model.Transferencia;
import tiendaclub.model.Usuario;
import tiendaclub.model.Vendido;
import tiendaclub.model.Venta;

public class DataFactory implements Globals {

    public static <T extends IPersistible> T buildObject(ResultSet rs, Class<T> clazz) throws SQLException {
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
        return clazz.cast(iPersistible);
    }
}