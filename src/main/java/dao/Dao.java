package dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {

    <L> T get(L id) throws SQLException, IOException;

    List<T> getAll();

    <L> L save(T t) throws SQLException, IOException;

    void update(T t);

    void delete(T t);
}