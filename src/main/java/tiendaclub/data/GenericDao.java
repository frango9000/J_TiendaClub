package tiendaclub.data;


import tiendaclub.model.models.abstracts.Identifiable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenericDao<T extends Identifiable> implements IGenericDao<T> {

    protected String ID_COL_NAME = "id";
    protected String TABLE_NAME;

    public GenericDao(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
    }

    protected static void printSql(String sql) {
        if (SQL_DEBUG) {
            System.out.println(sql);
        }
    }

    protected void index(T objectT) {
    }

    protected void deindex(int id) {
    }

    protected void deindex(T objectT) {
    }

    protected void reindex(T objectT) {
    }

    @Override
    public T query(int id) {
        T objecT = null;
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s WHERE %s = '%d'", TABLE_NAME, ID_COL_NAME, id);
            try (Statement ps = SessionDB.getConn().createStatement();
                 ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    objecT = (T) DataFactory.buildObject(rs);
                    index(objecT);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return objecT;
    }

    @Override
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
                Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return objecT;
    }

    @Override
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
                Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return objecT;
    }

    @Override
    public HashMap<Integer, T> query(ArrayList<Integer> ids) {
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
                Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, sql.toString(), ex);
            } finally {
                SessionDB.close();
            }
        }
        return returnMap;
    }

    @Override
    public HashMap<Integer, T> queryAll() {
        //table.clear();
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
                Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return returnMap;
    }


    @Override
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
                    Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, sql, ex);
                } finally {
                    SessionDB.close();
                }
            }
        }
        return rows;
    }

    @Override
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
                Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    @Override
    public int updateObject(T objectT) {
        int rows = 0;
        if (objectT.getId() > 0) {
            if (SessionDB.connect()) {
                String sql = String.format("SELECT * FROM %s WHERE %s = '%d'", TABLE_NAME, ID_COL_NAME, objectT.getId());
                try (Statement ps = SessionDB.getConn().createStatement();
                     ResultSet rs = ps.executeQuery(sql)) {
                    if (rs.next()) {
                        objectT.updateObject(rs);
                        rows++;
                        reindex(objectT);
                    }
                    printSql(sql);
                } catch (SQLException ex) {
                    Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, sql, ex);
                } finally {
                    SessionDB.close();
                }
            }
        }
        return rows;
    }

    @Override
    public int delete(T objecT) {
        return delete(objecT.getId());
    }

    @Override
    public int delete(int id) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = String.format("DELETE FROM %s WHERE %s = '%d' ", TABLE_NAME, ID_COL_NAME, id);
            try (Statement stmt = SessionDB.getConn().createStatement()) {
                rows = stmt.executeUpdate(sql);
                deindex(id);
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    @Override
    public int deleteSome(ArrayList<T> toDelete) {
        ArrayList<Integer> idsToDelete = new ArrayList<>();
        toDelete.forEach(e -> idsToDelete.add(e.getId()));
        return deleteSomeIds(idsToDelete);
    }

    @Override
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
                Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, sql.toString(), ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }


    public static String buildInsertString(String TABLE_NAME, ArrayList<String> COL_NAMES) {
        final StringBuilder sql = new StringBuilder(String.format("INSERT INTO %s VALUES(NULL, ", TABLE_NAME));
        int i = COL_NAMES.size();
        while (i > 0) {
            sql.append("? ");
            if (i > 1)
                sql.append(", ");
            i--;
        }
        return sql.append(")").toString();
    }

    public static String buildUpdateString(String TABLE_NAME, String ID_COL_NAME, ArrayList<String> COL_NAMES, int id) {
        final StringBuilder sql = new StringBuilder(String.format("UPDATE %s SET ", TABLE_NAME));
        Iterator<String> iterator = COL_NAMES.iterator();
        while (iterator.hasNext()) {
            sql.append(iterator.next()).append(" = ? ");
            if (iterator.hasNext())
                sql.append(", ");
        }
        sql.append(String.format("WHERE %s = '%d'", ID_COL_NAME, id));
        return sql.toString();
    }
}
