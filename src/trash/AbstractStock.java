package trash;

public abstract class AbstractStock {
    protected byte idSede;
    protected short idProducto;
    protected int cantidad;

    public AbstractStock(byte idSede, short idProducto, int cantidad) {
        this.idSede = idSede;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public byte getIdSede() {
        return idSede;
    }

    public void setIdSede(byte idSede) {
        this.idSede = idSede;
    }

    public short getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(short idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractStock that = (AbstractStock) o;

        if (idSede != that.idSede) return false;
        return idProducto == that.idProducto;
    }

    @Override
    public int hashCode() {
        int result = idSede;
        result = 31 * result + (int) idProducto;
        return result;
    }

    @Override
    public String toString() {
        return "AbstractStock{" +
                "idSede=" + idSede +
                ", idProducto=" + idProducto +
                ", cantidad=" + cantidad +
                '}';
    }
}
