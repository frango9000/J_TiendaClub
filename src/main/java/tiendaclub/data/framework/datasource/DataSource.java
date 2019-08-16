package tiendaclub.data.framework.datasource;

import com.google.common.collect.Sets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import tiendaclub.data.DataFactory;
import tiendaclub.data.SessionDB;
import tiendaclub.data.framework.index.model.AbstractIndex;
import tiendaclub.model.Globals;
import tiendaclub.model.models.abstracts.IPersistible;

public class DataSource<V extends IPersistible> implements Globals {

    protected String ID_COL_NAME = "id";
    protected String TABLE_NAME;

    protected ArrayList<AbstractIndex<?, V>> indexes;

    public DataSource(String TABLE_NAME, ArrayList<AbstractIndex<?, V>> indexes) {
        this.TABLE_NAME = TABLE_NAME;
        this.indexes = indexes;
    }

    protected static void printSql(String sql) {
        if (SQL_DEBUG) {
            System.out.println(sql);
        }
    }

    public void setID_COL_NAME(String ID_COL_NAME) {
        this.ID_COL_NAME = ID_COL_NAME;
    }

    public ArrayList<AbstractIndex<?, V>> getIndexes() {
        return indexes;
    }

    protected void index(V objectV) {
        indexes.forEach(e -> e.index(objectV));
    }

    protected void deindex(int key) {
        indexes.forEach(e -> e.deindex(key));
    }

    protected void deindex(V objectV) {
        indexes.forEach(e -> e.deindex(objectV));
    }

    protected void reindex(V objectV) {
        indexes.forEach(e -> e.reindex(objectV));
    }

    public V query(int id) {
        return query(ID_COL_NAME, Integer.toString(id));
    }

    public V query(String colName, Object unique) {
        V objecT = null;
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s WHERE %s = '%s'", TABLE_NAME, colName, unique.toString());
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    objecT = (V) DataFactory.buildObject(rs);
                    index(objecT);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(tiendaclub.data.framework.dao.PersistibleDao.class.getName())
                        .log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return objecT;
    }

    public V query(String col1Name, String uni, String col2Name, String que) {
        V objecT = null;
        if (SessionDB.connect()) {
            String sql = String
                    .format("SELECT * FROM %s WHERE %s = '%s' AND %s = '%s'", TABLE_NAME, col1Name, uni, col2Name, que);
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    objecT = (V) DataFactory.buildObject(rs);
                    index(objecT);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(tiendaclub.data.framework.dao.PersistibleDao.class.getName())
                        .log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return objecT;
    }

    public Set<V> querySome(Set<Integer> ids) {
        HashSet<V> returnSet = Sets.newHashSetWithExpectedSize(ids.size());
        if (SessionDB.connect() && ids.size() > 0) {
            StringBuilder sql = new StringBuilder("SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL_NAME + " IN( ");
            Iterator<Integer> iterator = ids.iterator();
            while (iterator.hasNext()) {
                sql.append(iterator.next());
                if (iterator.hasNext()) {
                    sql.append(", ");
                } else {
                    sql.append(" )");
                }
            }
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql.toString())) {
                while (rs.next()) {
                    V objecT = (V) DataFactory.buildObject(rs);
                    index(objecT);
                    returnSet.add(objecT);
                }
                printSql(sql.toString());
            } catch (SQLException ex) {
                Logger.getLogger(tiendaclub.data.framework.dao.PersistibleDao.class.getName())
                        .log(Level.SEVERE, sql.toString(), ex);
            } finally {
                SessionDB.close();
            }
        }
        return returnSet;
    }

    public Set<V> querySome(String colName, Collection search) {
        HashSet<V> returnSet = Sets.newHashSetWithExpectedSize(search.size());
        if (SessionDB.connect() && search.size() > 0) {
            StringBuilder sql = new StringBuilder("SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL_NAME + " IN( ");
            Iterator iterator = search.iterator();
            while (iterator.hasNext()) {
                sql.append(iterator.next().toString());
                if (iterator.hasNext()) {
                    sql.append(", ");
                } else {
                    sql.append(" )");
                }
            }
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql.toString())) {
                while (rs.next()) {
                    V objecT = (V) DataFactory.buildObject(rs);
                    index(objecT);
                    returnSet.add(objecT);
                }
                printSql(sql.toString());
            } catch (SQLException ex) {
                Logger.getLogger(tiendaclub.data.framework.dao.PersistibleDao.class.getName())
                        .log(Level.SEVERE, sql.toString(), ex);
            } finally {
                SessionDB.close();
            }
        }
        return returnSet;
    }

    public Set<V> querySome(String colName, Object search) {
        HashSet<V> returnSet = Sets.newHashSet();
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s WHERE %s = '%s'", TABLE_NAME, colName, search.toString());
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                while (rs.next()) {
                    V objecT = (V) DataFactory.buildObject(rs);
                    index(objecT);
                    returnSet.add(objecT);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(tiendaclub.data.framework.dao.PersistibleDao.class.getName())
                        .log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return returnSet;
    }

    public Set<V> queryAll() {
        Set<V> returnSet = null;
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s", TABLE_NAME);
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                returnSet = Sets.newHashSetWithExpectedSize(rs.getFetchSize());
                while (rs.next()) {
                    V objecT = (V) DataFactory.buildObject(rs);
                    index(objecT);
                    returnSet.add(objecT);
                }
                printSql(sql);
            } catch (SQLException ex) {
                returnSet = Set.of();
                Logger.getLogger(tiendaclub.data.framework.dao.PersistibleDao.class.getName())
                        .log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return returnSet;
    }

    public int insert(V objectV) {
        int rows = 0;
        if (objectV.getId() == 0) {
            if (SessionDB.connect()) {
                String sql = objectV.getInsertString();
                try (PreparedStatement pstmt = SessionDB.getConn()
                        .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    objectV.buildStatement(pstmt);
                    rows = pstmt.executeUpdate();

                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            objectV.setId(rs.getInt(1));
                            index(objectV);
                        }
                    }
                    printSql(sql);
                } catch (SQLException ex) {
                    Logger.getLogger(tiendaclub.data.framework.dao.PersistibleDao.class.getName())
                            .log(Level.SEVERE, sql, ex);
                } finally {
                    SessionDB.close();
                }
            }
        }
        return rows;
    }

    public int update(V objectV) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = objectV.getUpdateString();
            try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
                objectV.buildStatement(pstmt);
                rows = pstmt.executeUpdate();
                reindex(objectV);
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(tiendaclub.data.framework.dao.PersistibleDao.class.getName())
                        .log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    public int updateObject(V objectV) {
        int rows = 0;
        if (objectV.getId() > 0) {
            if (SessionDB.connect()) {
                String sql = String
                        .format("SELECT * FROM %s WHERE %s = '%d'", TABLE_NAME, ID_COL_NAME, objectV.getId());
                deindex(objectV);
                try (Statement ps = SessionDB.getConn().createStatement();
                        ResultSet rs = ps.executeQuery(sql)) {
                    if (rs.next()) {
                        objectV.updateObject(rs);
                        rows++;
                    }
                    index(objectV);
                    printSql(sql);
                } catch (SQLException ex) {
                    Logger.getLogger(tiendaclub.data.framework.dao.PersistibleDao.class.getName())
                            .log(Level.SEVERE, sql, ex);
                } finally {
                    SessionDB.close();
                }
            }
        }
        return rows;
    }

    public int delete(V objecT) {
        return delete(objecT.getId());
    }

    public int delete(Integer key) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = String.format("DELETE FROM %s WHERE %s = '%d' ", TABLE_NAME, ID_COL_NAME, key);
            try (Statement stmt = SessionDB.getConn().createStatement()) {
                rows = stmt.executeUpdate(sql);
                deindex(key);
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(tiendaclub.data.framework.dao.PersistibleDao.class.getName())
                        .log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    public int deleteSome(Set<V> toDelete) {
        HashSet<Integer> idsToDelete = Sets.newHashSet();
        toDelete.forEach(e -> idsToDelete.add(e.getId()));
        return deleteSomeKeys(idsToDelete);
    }

    public int deleteSomeKeys(Set<Integer> keys) {
        int rows = 0;
        if (SessionDB.connect() && keys.size() > 0) {
            StringBuilder sql = new StringBuilder("DELETE FROM " + TABLE_NAME + " WHERE " + ID_COL_NAME + " IN( ");
            Iterator<Integer> iterator = keys.iterator();
            while (iterator.hasNext()) {
                sql.append(iterator.next());
                if (iterator.hasNext()) {
                    sql.append(", ");
                } else {
                    sql.append(" )");
                }
            }
            try (Statement ps = SessionDB.getConn().createStatement()) {
                SessionDB.getConn().setAutoCommit(false);
                rows = ps.executeUpdate(sql.toString());
                if (rows == keys.size()) {
                    SessionDB.getConn().commit();
                    keys.forEach(e -> deindex(e));
                } else {
                    SessionDB.getConn().rollback();
                }
                SessionDB.getConn().setAutoCommit(true);
                printSql(sql.toString());
            } catch (SQLException ex) {
                Logger.getLogger(tiendaclub.data.framework.dao.PersistibleDao.class.getName())
                        .log(Level.SEVERE, sql.toString(), ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }
}
