package tiendaclub.model.models.abstracts;

import java.time.LocalDateTime;

public abstract class AbstractCierreZ extends Persistible {
    protected int idCaja;
    protected LocalDateTime apertura;
    protected LocalDateTime cierre;

    public AbstractCierreZ(int id, int idCaja, LocalDateTime apertura) {
        super(id);
        this.idCaja = idCaja;
        this.apertura = apertura;
    }

    public AbstractCierreZ(int idCaja, LocalDateTime apertura) {
        super(0);
        this.idCaja = idCaja;
        this.apertura = apertura;
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }

    public LocalDateTime getApertura() {
        return apertura;
    }

    public void setApertura(LocalDateTime apertura) {
        this.apertura = apertura;
    }

    public LocalDateTime getCierre() {
        return cierre;
    }

    public void setCierre(LocalDateTime cierre) {
        this.cierre = cierre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractCierreZ cierreZ = (AbstractCierreZ) o;

        return id == cierreZ.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "CierreZ{" +
                "id=" + id +
                ", idCaja=" + idCaja +
                ", apertura=" + apertura +
                ", cierre=" + cierre +
                '}';
    }
}
