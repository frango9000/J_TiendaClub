package app.model.abstractmodel;

import java.time.LocalDateTime;

public class AbstractCierreZ {
    private int id;
    private short idCaja;
    private LocalDateTime apertura;
    private LocalDateTime cierre;

    public AbstractCierreZ(int id, short idCaja, LocalDateTime apertura) {
        this.id = id;
        this.idCaja = idCaja;
        this.apertura = apertura;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public short getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(short idCaja) {
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