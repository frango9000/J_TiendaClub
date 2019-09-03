package app.data.casteldao.model;

import java.io.Serializable;

public interface IIdentifiable<I extends Serializable> {

    I getId();

    void setId(I id);

}
