package tiendaclub.model;


import tiendaclub.data.DataStore;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenericDao<T extends IPersistible> implements IDao<T> {

    protected final String ID_COL_NAME = "id";
    protected final HashMap<Integer, T> table = new HashMap<>();
    protected String TABLE_NAME;

    public GenericDao(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
    }

    protected static void printSql(String sql) {
        if (SQL_DEBUG) {
            System.out.println(sql);
        }
    }

    @Override
    public T query(int id) {
        T t = null;
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s WHERE %s = '%d'", TABLE_NAME, ID_COL_NAME, id);
            try (Statement ps = SessionDB.getConn().createStatement();
                 ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    t = DataStore.buildObject(rs);
                    table.putIfAbsent(t.getId(), t);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return t;
    }

    @Override
    public T query(String colName, String unique) {
        T t = null;
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s WHERE %s = '%s'", TABLE_NAME, colName, unique);
            try (Statement ps = SessionDB.getConn().createStatement();
                 ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    t = DataStore.buildObject(rs);
                    table.putIfAbsent(t.getId(), t);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return t;
    }

    @Override
    public T query(String col1Name, String uni, String col2Name, String que) {
        T t = null;
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s WHERE %s = '%s' AND %s = '%s'", TABLE_NAME, col1Name, uni, col2Name, que);
            try (Statement ps = SessionDB.getConn().createStatement();
                 ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    t = DataStore.buildObject(rs);
                    table.putIfAbsent(t.getId(), t);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return t;
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
                    T t = DataStore.buildObject(rs);
                    table.putIfAbsent(t.getId(), t);
                    returnMap.put(t.getId(), t);
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
        table.clear();
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s", TABLE_NAME);
            try (Statement ps = SessionDB.getConn().createStatement();
                 ResultSet rs = ps.executeQuery(sql)) {
                while (rs.next()) {
                    T t = DataStore.buildObject(rs);
                    System.out.println(t.toString());
                    table.put(t.getId(), t);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return table;
    }

    @Override
    public T get(int id) {
        if (id > 0) {
            if (table.containsKey(id)) {
                return table.get(id);
            } else {
                return query(id);
            }
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<T> getList(ArrayList<Integer> ids) {
        ArrayList<T> list = new ArrayList<>();
        if (ids.size() > 0) {
            ArrayList<Integer> idsToQuery = new ArrayList<>();
            for (int id : ids) {
                if (!table.containsKey(id)) {
                    idsToQuery.add(id);
                }
            }
            if (ids.size() > 0) {
                query(idsToQuery);
            }
            for (int id : ids) {
                list.add(table.get(id));
            }
        }
        return list;
    }

    @Override
    public HashMap<Integer, T> get(ArrayList<Integer> ids) {
        HashMap<Integer, T> filteredHashMap = new HashMap<>();
        if (ids.size() > 0) {
            ArrayList<T> objs = getList(ids);
            for (T t : objs) {
                filteredHashMap.put(t.getId(), t);
            }
        }
        return filteredHashMap;
    }

    @Override
    public HashMap<Integer, T> getCache() {
        return table;
    }

    @Override
    public int insert(T objectT) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = objectT.insertString();
            try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                objectT.buildStatement(pstmt);
                rows = pstmt.executeUpdate();

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        objectT.setId(rs.getInt(1));
                        table.put(objectT.getId(), objectT);
                    }
                }
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
    public int update(T objectT) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = objectT.updateString();
            try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
                objectT.buildStatement(pstmt);
                rows = pstmt.executeUpdate();
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
                        table.put(objectT.getId(), objectT);
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
    public int delete(T t) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = String.format("DELETE FROM %s WHERE %s = '%d' ", TABLE_NAME, ID_COL_NAME, t.getId());
            try (Statement stmt = SessionDB.getConn().createStatement()) {
                rows = stmt.executeUpdate(sql);
                table.remove(t.getId());
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
    public int delete(int id) {
        return delete(table.get(id));
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
                rows = ps.executeUpdate(sql.toString());
                toDelete.forEach(table::remove);//remove from local data store
                printSql(sql.toString());
            } catch (SQLException ex) {
                Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, sql.toString(), ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }


}
