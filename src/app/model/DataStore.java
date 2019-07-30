package app.model;

import app.model.models.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataStore {

    private static GenericDao<Acceso> accesos = new GenericDao<>(Acceso.TABLE_NAME);

    public static GenericDao<Acceso> getAccesos() {
        return accesos;
    }

    static <T extends IPersistible> T buildObject(ResultSet rs) throws SQLException {
        switch (rs.getMetaData().getTableName(1)) {
            case Acceso.TABLE_NAME:
                return (T) new Acceso(rs);
            case Sede.TABLE_NAME:
                return (T) new Sede(rs);
            case Caja.TABLE_NAME:
                return (T) new Caja(rs);
            case Categoria.TABLE_NAME:
                return (T) new Categoria(rs);
            case CierreZ.TABLE_NAME:
                return (T) new CierreZ(rs);
            case Compra.TABLE_NAME:
                return (T) new Compra(rs);
            case Comprado.TABLE_NAME:
                return (T) new Comprado(rs);
            case Producto.TABLE_NAME:
                return (T) new Producto(rs);
            case Proveedor.TABLE_NAME:
                return (T) new Proveedor(rs);
            case Socio.TABLE_NAME:
                return (T) new Socio(rs);
            case Transferencia.TABLE_NAME:
                return (T) new Transferencia(rs);
            case Usuario.TABLE_NAME:
                return (T) new Usuario(rs);
            case Vendido.TABLE_NAME:
                return (T) new Vendido(rs);
            case Venta.TABLE_NAME:
                return (T) new Venta(rs);
            default:
                throw new IllegalStateException("Unexpected value: " + rs.getMetaData().getTableName(1));
        }
    }


}
