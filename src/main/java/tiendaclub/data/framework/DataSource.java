package tiendaclub.data.framework;

import com.google.common.collect.Sets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import tiendaclub.data.framework.index.core.IIndex;
import tiendaclub.misc.Flogger;
import tiendaclub.misc.Globals;
import tiendaclub.model.models.core.IPersistible;

@SuppressWarnings("unchecked") //TODO verify casting
public class DataSource<V extends IPersistible> implements Globals {

    public String idColName = "id";
    public String tableName;

    protected ArrayList<IIndex<?, V>> indexes;

    public DataSource(String tableName, ArrayList<IIndex<?, V>> indexes) {
        this.tableName = tableName;
        this.indexes   = indexes;
    }

    protected static void printSql(String sql) {
        if (SQL_DEBUG) {
            Flogger.atInfo().log(sql);
        }
    }

    public String getIdColName() {
        return idColName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setIdColName(String idColName) {
        this.idColName = idColName;
    }

    public ArrayList<IIndex<?, V>> getIndexes() {
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


    private V query(String colName, Object unique, boolean indexOn) {
        V objecT = null;
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s WHERE %s = '%s'", tableName, colName, unique.toString());
            try (Statement ps = SessionDB.getConn().createStatement(); ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    objecT = (V) DataFactory.buildObject(rs);
                    if (indexOn) {
                        index(objecT);
                    }
                }
                printSql(sql);
            } catch (SQLException ex) {
                Flogger.atSevere().withCause(ex).log("\nSQL: ", sql);
            } finally {
                SessionDB.close();
            }
        }
        return objecT;
    }

    public V query(int id) {
        return query(idColName, Integer.toString(id));
    }

    public V query(String colName, Object unique) {
        return query(colName, unique, true);
    }

    public Set<V> querySome(String colName, Collection search, boolean isIn) {
        HashSet<V> returnSet = Sets.newHashSetWithExpectedSize(search.size());
        if (search.size() > 0)
            if (SessionDB.connect()) {
                StringBuilder sql = new StringBuilder(String.format("SELECT * FROM %s WHERE %s %s IN( ", tableName, isIn ? "" : "NOT", colName));
                Iterator iterator = search.iterator();
                while (iterator.hasNext()) {
                    sql.append("'").append(iterator.next().toString()).append("'");
                    if (iterator.hasNext()) {
                        sql.append(", ");
                    } else {
                        sql.append(" )");
                    }
                }
                try (Statement ps = SessionDB.getConn()
                                             .createStatement(); ResultSet rs = ps.executeQuery(sql.toString())) {
                    while (rs.next()) {
                        V objecT = (V) DataFactory.buildObject(rs);
                        index(objecT);
                        returnSet.add(objecT);
                    }
                    printSql(sql.toString());
                } catch (SQLException ex) {
                    Flogger.atSevere().withCause(ex).log("\nSQL: ", sql);
                } finally {
                    SessionDB.close();
                }
            }
        return returnSet;
    }

    public Set<V> querySome(String colName, Collection search) {
        return querySome(colName, search, true);
    }

    public Set<V> querySome(String colName, String search) {
        return querySome(colName, Collections.singletonList(search));
    }

    public Set<V> querySome(String colName, String search, Boolean isIn) {
        return querySome(colName, Collections.singletonList(search), isIn);
    }

    public Set<V> querySome(Set<Integer> ids) {
        return querySome(idColName, ids);
    }

    public Set<V> querySome(Set<Integer> ids, boolean isIn) {
        return querySome(idColName, ids, false);
    }

    public Set<V> queryAll() {
        Set<V> returnSet = null;
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s", tableName);
            try (Statement ps = SessionDB.getConn().createStatement(); ResultSet rs = ps.executeQuery(sql)) {
                returnSet = Sets.newHashSetWithExpectedSize(rs.getFetchSize());
                while (rs.next()) {
                    V objecT = (V) DataFactory.buildObject(rs);
                    index(objecT);
                    returnSet.add(objecT);
                }
                printSql(sql);
            } catch (SQLException ex) {
                returnSet = Set.of();
                Flogger.atSevere().withCause(ex).log("\nSQL: ", sql);
            } finally {
                SessionDB.close();
            }
        }
        return returnSet;
    }


    public Set<V> queryLike(String colName, String string, boolean like) {
        HashSet<V> returnSet = Sets.newHashSet();
        if (string != null && string.length() > 0)
            if (SessionDB.connect()) {
                StringBuilder sql = new StringBuilder(
                    "SELECT * FROM " + tableName + " WHERE " + colName + (like ? " " : " NOT ") + "LIKE '" + string
                    + "'");
                try (Statement ps = SessionDB.getConn()
                                             .createStatement(); ResultSet rs = ps.executeQuery(sql.toString())) {
                    while (rs.next()) {
                        V objecT = (V) DataFactory.buildObject(rs);
                        index(objecT);
                        returnSet.add(objecT);
                    }
                    printSql(sql.toString());
                } catch (SQLException ex) {
                    Flogger.atSevere().withCause(ex).log("\nSQL: ", sql);
                } finally {
                    SessionDB.close();
                }
            }
        return returnSet;
    }

    public Set<V> queryLike(String colName, String string) {
        return queryLike(colName, string, true);
    }

    public Set<V> queryGreaterLesser(String colName, String string, boolean greaterThan, boolean inclusive) {
        HashSet<V> returnSet = Sets.newHashSet();
        if (string != null && string.length() > 0)
            if (SessionDB.connect()) {
                StringBuilder sql = new StringBuilder(
                    String.format("SELECT * FROM %s WHERE %s %s%s '%s'", tableName, colName, greaterThan ? ">" : "<", inclusive ? "=" : "", string));
                try (Statement ps = SessionDB.getConn()
                                             .createStatement(); ResultSet rs = ps.executeQuery(sql.toString())) {
                    while (rs.next()) {
                        V objecT = (V) DataFactory.buildObject(rs);
                        index(objecT);
                        returnSet.add(objecT);
                    }
                    printSql(sql.toString());
                } catch (SQLException ex) {
                    Flogger.atSevere().withCause(ex).log("\nSQL: ", sql);
                } finally {
                    SessionDB.close();
                }
            }
        return returnSet;
    }

    public Set<V> queryGreaterThan(String colName, String string) {
        return queryGreaterLesser(colName, string, true, false);
    }

    public Set<V> queryGreaterOrEqualThan(String colName, String string) {
        return queryGreaterLesser(colName, string, true, true);
    }

    public Set<V> queryLesserThan(String colName, String string) {
        return queryGreaterLesser(colName, string, false, false);
    }

    public Set<V> queryLesserOrEqualThan(String colName, String string) {
        return queryGreaterLesser(colName, string, false, true);
    }

    public Set<V> queryBetween(String colName, String start, String end, boolean in) {
        HashSet<V> returnSet = Sets.newHashSet();
        if (start != null && start.length() > 0)
            if (SessionDB.connect()) {
                StringBuilder sql = new StringBuilder(
                    "SELECT * FROM " + tableName + " WHERE " + colName + (in ? " " : " NOT ") + "BETWEEN '" + start
                    + "' AND '" + end + "'");
                try (Statement ps = SessionDB.getConn()
                                             .createStatement(); ResultSet rs = ps.executeQuery(sql.toString())) {
                    while (rs.next()) {
                        V objecT = (V) DataFactory.buildObject(rs);
                        index(objecT);
                        returnSet.add(objecT);
                    }
                    printSql(sql.toString());
                } catch (SQLException ex) {
                    Flogger.atSevere().withCause(ex).log("\nSQL: ", sql);
                } finally {
                    SessionDB.close();
                }
            }
        return returnSet;
    }

    public Set<V> queryBetween(String colName, String start, String end) {
        return queryBetween(colName, start, end, true);
    }

    public Set<String> queryOneColumn(String returnColName, String whereColName, Collection equalsCondition,
                                      boolean isIn) {
        Set<String> strings = Sets.newHashSet();
        if (SessionDB.connect()) {
            StringBuilder sql = new StringBuilder(String.format("SELECT %s FROM %s WHERE %s %s IN( ", returnColName, tableName, whereColName, isIn ? "" : "NOT"));
            Iterator iterator = equalsCondition.iterator();
            while (iterator.hasNext()) {
                sql.append("'").append(iterator.next().toString()).append("'");
                if (iterator.hasNext()) {
                    sql.append(", ");
                } else {
                    sql.append(" )");
                }
            }
            try (Statement ps = SessionDB.getConn().createStatement(); ResultSet rs = ps.executeQuery(sql.toString())) {
                while (rs.next()) {
                    strings.add(rs.getString(1));
                }
                printSql(sql.toString());
            } catch (SQLException ex) {
                Flogger.atSevere().withCause(ex).log("\nSQL: ", sql);
            } finally {
                SessionDB.close();
            }
        }
        return strings;
    }

    public Set<String> queryOneColumn(String returnColName, String whereColName, String equalsCondition, boolean isIn) {
        return queryOneColumn(returnColName, whereColName, Collections.singletonList(equalsCondition), isIn);
    }

    public Set<String> queryOneColumn(String returnColName, String whereColName, String equalsCondition) {
        return queryOneColumn(returnColName, whereColName, equalsCondition, true);
    }

    public V queryBiUnique(String col1Name, String uni, String col2Name, String que) {
        V objecT = null;
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s WHERE %s = '%s' AND %s = '%s'", tableName, col1Name, uni, col2Name, que);
            try (Statement ps = SessionDB.getConn().createStatement(); ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    objecT = (V) DataFactory.buildObject(rs);
                    index(objecT);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Flogger.atSevere().withCause(ex).log("\nSQL: ", sql);
            } finally {
                SessionDB.close();
            }
        }
        return objecT;
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
                    Flogger.atSevere().withCause(ex).log("\nSQL: ", sql);
                } finally {
                    SessionDB.close();
                }
            }
        }
        return rows;
    }

    public int update(V objectV) {
        int rows = 0;
        if (!Globals.SAFE_UPDATE || objectV.getBackup() != null) {
            if (SessionDB.connect()) {
                String sql = objectV.getUpdateString();
                try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
                    objectV.buildStatement(pstmt);
                    rows = pstmt.executeUpdate();
                    if (rows > 0) {
                        reindex(objectV);
                    }
                    printSql(sql);
                } catch (SQLException ex) {
                    Flogger.atSevere().withCause(ex).log("\nSQL: ", sql);
                    Flogger.atSevere().log("Reverting object to backup");
                    objectV.restoreFromBackup();
                } finally {
                    SessionDB.close();
                    objectV.commit();
                }
            }
        } else
            Flogger.atInfo().log("no object backup and Safe Update on");
        return rows;
    }

    public boolean updateObject(V objectV) {
        deindex(objectV);
        V freshObject = query(idColName, objectV.getId(), false);
        boolean b = objectV.restoreFrom(freshObject);
        index(objectV);
        return b;
    }

    public int delete(V objecT) {
        return delete(objecT.getId());
    }

    public int delete(Integer key) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = String.format("DELETE FROM %s WHERE %s = '%d' ", tableName, idColName, key);
            try (Statement stmt = SessionDB.getConn().createStatement()) {
                rows = stmt.executeUpdate(sql);
                deindex(key);
                printSql(sql);
            } catch (SQLException ex) {
                Flogger.atSevere().withCause(ex).log("\nSQL: ", sql);
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
        if (keys.size() > 0)
            if (SessionDB.connect()) {
                StringBuilder sql = new StringBuilder("DELETE FROM " + tableName + " WHERE " + idColName + " IN( ");
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
                    Flogger.atSevere().withCause(ex).log("\nSQL: ", sql);
                } finally {
                    SessionDB.close();
                }
            }
        return rows;
    }
}
