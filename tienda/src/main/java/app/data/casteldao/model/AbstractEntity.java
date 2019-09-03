package app.data.casteldao.model;

import app.misc.Flogger;
import com.google.common.base.MoreObjects;
import java.io.Serializable;
import java.util.Objects;

public abstract class AbstractEntity<I extends Serializable> implements IEntity<I>, Serializable, Cloneable {

    protected I id;

    protected AbstractEntity<I> backup;


    public AbstractEntity() {
    }

    public AbstractEntity(I id) {
        this.id = id;
    }

    @Override
    public I getId() {
        return id;
    }

    @Override
    public void setId(I id) {
        this.id = id;
    }


    @Override
    public void setBackup() throws CloneNotSupportedException {
        backup = clone();
    }

    @Override
    public AbstractEntity getBackup() {
        return backup;
    }

    @Override
    public void restoreFromBackup() {
        if (backup != null) {
            restoreFrom(backup);
        }
    }

    @Override
    public void commit() {
        this.backup = null;
    }

    @Override
    public AbstractEntity<I> clone() {
        try {
            return (AbstractEntity<I>) super.clone();
        } catch (CloneNotSupportedException e) {
            Flogger.atWarning().withCause(e).log();
            return null;//??
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AbstractEntity<?> that = (AbstractEntity<?>) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("id", id)
                          .toString();
    }
}
