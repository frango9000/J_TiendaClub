package tiendaclub.data.framework.datasource;

import tiendaclub.data.DataFactory;
import tiendaclub.data.SessionDB;
import tiendaclub.data.framework.index.AbstractIndex;
import tiendaclub.model.Globals;
import tiendaclub.model.models.abstracts.Persistible;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataSource<T extends Persistible> implements Globals {

    protected String ID_COL_NAME = "id";
    protected String TABLE_NAME;

    protected ArrayList<AbstractIndex<?, T>> indexes;

    public DataSource(String TABLE_NAME, ArrayList<AbstractIndex<?, T>> indexes) {
        this.TABLE_NAME = TABLE_NAME;
        this.indexes = indexes;
    }

    protected static void printSql(String sql) {
        if (SQL_DEBUG) {
            System.out.println(sql);
        }
    }

    public ArrayList<AbstractIndex<?, T>> getIndexes() {
        return indexes;
    }

    protected void index(T objectT) {
        indexes.forEach(e -> e.index(objectT));
    }

    protected void deindex(int id) {
        indexes.forEach(e -> e.deindex(id));
    }

    protected void deindex(T objectT) {
        indexes.forEach(e -> e.deindex(objectT));
    }

    protected void reindex(T objectT) {
        indexes.forEach(e -> e.reindex(objectT));
    }

    public T query(int id) {
        return query(ID_COL_NAME, Integer.toString(id));
    }

    public T query(String colName, String unique) {
        T objecT = null;
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s WHERE %s = '%s'", TABLE_NAME, colName, unique);
            try (Statement ps = SessionDB.getConn().createStatement();
                 ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    objecT = (T) DataFactory.buildObject(rs);
                    index(objecT);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(tiendaclub.data.framework.dao.PersistibleDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return objecT;
    }

    public T query(String col1Name, String uni, String col2Name, String que) {
        T objecT = null;
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s WHERE %s = '%s' AND %s = '%s'", TABLE_NAME, col1Name, uni, col2Name, que);
            try (Statement ps = SessionDB.getConn().createStatement();
                 ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    objecT = (T) DataFactory.buildObject(rs);
                    index(objecT);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(tiendaclub.data.framework.dao.PersistibleDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return objecT;
    }

    public HashMap<Integer, T> querySome(ArrayList<Integer> ids) {
        HashMap<Integer, T> returnMap = new HashMap<>();
        if (SessionDB.connect() && ids.size() > 0) {
            StringBuilder sql = new StringBuilder("SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL_NAME + " IN( ");
            Iterator<Integer> iterator = ids.iterator();
            while (iterator.hasNext()) {
                sql.append(iterator.next());
                if (iterator.hasNext())
                    sql.append(", ");
                else
                    sql.append(" )");
            }
            try (Statement ps = SessionDB.getConn().createStatement();
                 ResultSet rs = ps.executeQuery(sql.toString())) {
                while (rs.next()) {
                    T objecT = (T) DataFactory.buildObject(rs);
                    index(objecT);
                    returnMap.putIfAbsent(objecT.getId(), objecT);
                }
                printSql(sql.toString());
            } catch (SQLException ex) {
                Logger.getLogger(tiendaclub.data.framework.dao.PersistibleDao.class.getName()).log(Level.SEVERE, sql.toString(), ex);
            } finally {
                SessionDB.close();
            }
        }
        return returnMap;
    }

    public HashMap<Integer, T> querySome(String colName, String search) {
        HashMap<Integer, T> returnMap = new HashMap<>();
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s WHERE %s = '%s'", TABLE_NAME, colName, search);
            try (Statement ps = SessionDB.getConn().createStatement();
                 ResultSet rs = ps.executeQuery(sql)) {
                while (rs.next()) {
                    T objecT = (T) DataFactory.buildObject(rs);
                    index(objecT);
                    returnMap.putIfAbsent(objecT.getId(), objecT);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(tiendaclub.data.framework.dao.PersistibleDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return returnMap;
    }

    public HashMap<Integer, T> queryAll() {
        HashMap<Integer, T> returnMap = new HashMap<>();
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s", TABLE_NAME);
            try (Statement ps = SessionDB.getConn().createStatement();
                 ResultSet rs = ps.executeQuery(sql)) {
                while (rs.next()) {
                    T objecT = (T) DataFactory.buildObject(rs);
                    index(objecT);
                    returnMap.putIfAbsent(objecT.getId(), objecT);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(tiendaclub.data.framework.dao.PersistibleDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return returnMap;
    }

    public int insert(T objectT) {
        int rows = 0;
        if (objectT.getId() == 0) {
            if (SessionDB.connect()) {
                String sql = objectT.getInsertString();
                try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    objectT.buildStatement(pstmt);
                    rows = pstmt.executeUpdate();

                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            objectT.setId(rs.getInt(1));
                            index(objectT);
                        }
                    }
                    printSql(sql);
                } catch (SQLException ex) {
                    Logger.getLogger(tiendaclub.data.framework.dao.PersistibleDao.class.getName()).log(Level.SEVERE, sql, ex);
                } finally {
                    SessionDB.close();
                }
            }
        }
        return rows;
    }

    public int update(T objectT) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = objectT.getUpdateString();
            try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
                objectT.buildStatement(pstmt);
                rows = pstmt.executeUpdate();
                reindex(objectT);
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(tiendaclub.data.framework.dao.PersistibleDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    public int updateObject(T objectT) {
        int rows = 0;
        if (objectT.getId() > 0) {
            if (SessionDB.connect()) {
                String sql = String.format("SELECT * FROM %s WHERE %s = '%d'", TABLE_NAME, ID_COL_NAME, objectT.getId());
                deindex(objectT);
                try (Statement ps = SessionDB.getConn().createStatement();
                     ResultSet rs = ps.executeQuery(sql)) {
                    if (rs.next()) {
                        objectT.updateObject(rs);
                        rows++;
                    }
                    index(objectT);
                    printSql(sql);
                } catch (SQLException ex) {
                    Logger.getLogger(tiendaclub.data.framework.dao.PersistibleDao.class.getName()).log(Level.SEVERE, sql, ex);
                } finally {
                    SessionDB.close();
                }
            }
        }
        return rows;
    }

    public int delete(T objecT) {
        return delete(objecT.getId());
    }

    public int delete(int id) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = String.format("DELETE FROM %s WHERE %s = '%d' ", TABLE_NAME, ID_COL_NAME, id);
            try (Statement stmt = SessionDB.getConn().createStatement()) {
                rows = stmt.executeUpdate(sql);
                deindex(id);
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(tiendaclub.data.framework.dao.PersistibleDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    public int deleteSome(ArrayList<T> toDelete) {
        ArrayList<Integer> idsToDelete = new ArrayList<>();
        toDelete.forEach(e -> idsToDelete.add(e.getId()));
        return deleteSomeIds(idsToDelete);
    }

    public int deleteSomeIds(ArrayList<Integer> toDelete) {
        int rows = 0;
        if (SessionDB.connect() && toDelete.size() > 0) {
            StringBuilder sql = new StringBuilder("DELETE FROM " + TABLE_NAME + " WHERE " + ID_COL_NAME + " IN( ");
            Iterator<Integer> iterator = toDelete.iterator();
            while (iterator.hasNext()) {
                sql.append(iterator.next());
                if (iterator.hasNext())
                    sql.append(", ");
                else
                    sql.append(" )");
            }
            try (Statement ps = SessionDB.getConn().createStatement()) {
                SessionDB.getConn().setAutoCommit(false);
                rows = ps.executeUpdate(sql.toString());
                if (rows == toDelete.size()) {
                    SessionDB.getConn().commit();
                    toDelete.forEach(e -> deindex(e));
                } else SessionDB.getConn().rollback();
                SessionDB.getConn().setAutoCommit(true);
                printSql(sql.toString());
            } catch (SQLException ex) {
                Logger.getLogger(tiendaclub.data.framework.dao.PersistibleDao.class.getName()).log(Level.SEVERE, sql.toString(), ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }
}
