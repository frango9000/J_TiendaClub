package app.model;

public interface IPersistible {

    int getId();

    int updateOnDb();

    int refreshFromDb();

}
