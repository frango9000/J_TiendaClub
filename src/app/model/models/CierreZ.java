package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractCierreZ;

import java.time.LocalDateTime;

public class CierreZ extends AbstractCierreZ implements IPersistible {
    private Caja caja;

    public CierreZ(int id, int idCaja, LocalDateTime apertura) {
        super(id, idCaja, apertura);
        updateCaja();
    }

    public CierreZ(int idCaja, LocalDateTime apertura) {
        super(idCaja, apertura);
        updateCaja();
    }

    @Override
    public void setIdCaja(int idCaja) {
        super.setIdCaja(idCaja);
        updateCaja();
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    private void updateCaja() {
        if (caja != null)
            caja.getCierresZs().remove(id);
        //TODO DAO
        //caja = DAO caja . get ( idCaja );
        caja.getCierresZs().put(id, this);
    }

    @Override
    public int updateOnDb() {
        return 0;
    }

    @Override
    public int refreshFromDb() {
        return 0;
    }
}
