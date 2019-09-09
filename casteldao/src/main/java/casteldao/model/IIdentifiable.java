package casteldao.model;

import com.google.common.base.MoreObjects;
import java.io.Serializable;

public interface IIdentifiable<I extends Serializable> {

    I getId();

    void setId(I id);

    default String fullToString() {
        return MoreObjects.toStringHelper(this)
                          .add("id", getId())
                          .toString();
    }
}
