package app.data.casteldao;

import static com.google.common.base.Preconditions.checkNotNull;

import app.data.casteldao.daomodel.IPersistible;
import app.data.casteldao.index.core.IIndex;
import app.misc.Flogger;
import app.misc.Globals;
import com.google.common.collect.Sets;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class DataSource<V extends IPersistible> implements Globals {

    public String idColName = "id";
    public String tableName;


    protected ArrayList<IIndex<?, V>> indexes;

    protected Class<V> clazz; // V = Entity Class

    public DataSource(String tableName, ArrayList<IIndex<?, V>> indexes, Class<V> clazz) {
        this.tableName = tableName;
        this.indexes   = indexes;
        if (clazz != null) {
            this.clazz = clazz;
        } else
            findGenericClass();
    }

    public DataSource(String tableName, ArrayList<IIndex<?, V>> indexes) {
        this(tableName, indexes, null);
    }

    public Optional<V> buildObject(ResultSet rs) {
        try {
            return Optional.of(clazz.getConstructor(ResultSet.class).newInstance(rs));
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            Flogger.atSevere().withCause(e).log();
        }
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    public Class<V> findGenericClass() {
        if (clazz == null)
            clazz = (Class<V>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return clazz;
    }

    protected static void printSql(String sql) {
        if (SQL_DEBUG) {
            Flogger.atInfo().log(sql);
        }
    }

    public static String getInsertString(int numOfColumns) {
        final StringBuilder sql = new StringBuilder().append(" ( NULL, ");
        while (numOfColumns > 0) {
            sql.append("? ");
            if (numOfColumns > 1) {
                sql.append(", ");
            }
            numOfColumns--;
        }
        return sql.append(") ").toString();
    }

    public String getUpdateString(Iterator<String> columns) {
        final StringBuilder sql = new StringBuilder(" ");
        while (columns.hasNext()) {
            sql.append(columns.next()).append(" = ? ");
            if (columns.hasNext()) {
                sql.append(", ");
            }
        }
        return sql.toString();
    }

    public String getIdColName() {
        return idColName;
    }

    public void setIdColName(String idColName) {
        this.idColName = idColName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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


    private Optional<V> query(String colName, Object unique, boolean indexOn) {
        Optional<V> objecT = Optional.empty();
        if (SessionDB.getSessionDB().connect()) {
            String sql = String.format("SELECT * FROM %s WHERE %s = '%s'", tableName, colName, unique.toString());
            try (Statement ps = SessionDB.getSessionDB()
                                         .getConn()
                                         .createStatement(); ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    objecT = buildObject(rs);
                    if (indexOn) {
                        objecT.ifPresent(this::index);
                    }
                }
                printSql(sql);
            } catch (SQLException ex) {
                Flogger.atSevere().withCause(ex).log(sql);
            } finally {
                SessionDB.getSessionDB().close();
            }
        }
        return objecT;
    }

    public Optional<V> query(int id) {
        return query(idColName, Integer.toString(id));
    }

    public Optional<V> query(String colName, Object unique) {
        return query(colName, unique, true);
    }

    public Set<V> querySome(String colName, Collection search, boolean isIn) {
        HashSet<V> returnSet = Sets.newHashSetWithExpectedSize(search.size());
        if (search.size() > 0)
            if (SessionDB.getSessionDB().connect()) {
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
                try (Statement ps = SessionDB.getSessionDB().getConn()
                                             .createStatement(); ResultSet rs = ps.executeQuery(sql.toString())) {
                    while (rs.next()) {
                        Optional<V> objecT = buildObject(rs);
                        if (objecT.isPresent()) {
                            index(objecT.get());
                            returnSet.add(objecT.get());
                        }
                    }
                    printSql(sql.toString());
                } catch (SQLException ex) {
                    Flogger.atSevere().withCause(ex).log(sql.toString());
                } finally {
                    SessionDB.getSessionDB().close();
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
        if (SessionDB.getSessionDB().connect()) {
            String sql = String.format("SELECT * FROM %s", tableName);
            try (Statement ps = SessionDB.getSessionDB()
                                         .getConn()
                                         .createStatement(); ResultSet rs = ps.executeQuery(sql)) {
                returnSet = Sets.newHashSetWithExpectedSize(rs.getFetchSize());
                while (rs.next()) {
                    Optional<V> objecT = buildObject(rs);
                    if (objecT.isPresent()) {
                        index(objecT.get());
                        returnSet.add(objecT.get());
                    }
                }
                printSql(sql);
            } catch (SQLException ex) {
                returnSet = Set.of();
                Flogger.atSevere().withCause(ex).log(sql);
            } finally {
                SessionDB.getSessionDB().close();
            }
        }
        return returnSet;
    }


    public Set<V> queryLike(String colName, String string, boolean like) {
        HashSet<V> returnSet = Sets.newHashSet();
        if (string != null && string.length() > 0)
            if (SessionDB.getSessionDB().connect()) {
                StringBuilder sql = new StringBuilder(
                    "SELECT * FROM " + tableName + " WHERE " + colName + (like ? " " : " NOT ") + "LIKE '" + string +
                    "'");
                try (Statement ps = SessionDB.getSessionDB().getConn().createStatement();
                     ResultSet rs = ps.executeQuery(sql.toString())) {
                    while (rs.next()) {
                        Optional<V> objecT = buildObject(rs);
                        if (objecT.isPresent()) {
                            index(objecT.get());
                            returnSet.add(objecT.get());
                        }
                    }
                    printSql(sql.toString());
                } catch (SQLException ex) {
                    Flogger.atSevere().withCause(ex).log(sql.toString());
                } finally {
                    SessionDB.getSessionDB().close();
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
            if (SessionDB.getSessionDB().connect()) {
                StringBuilder sql = new StringBuilder(
                    String.format("SELECT * FROM %s WHERE %s %s%s '%s'", tableName, colName, greaterThan ? ">" : "<", inclusive ? "=" : "", string));
                try (Statement ps = SessionDB.getSessionDB()
                                             .getConn()
                                             .createStatement(); ResultSet rs = ps.executeQuery(sql.toString())) {
                    while (rs.next()) {
                        Optional<V> objecT = buildObject(rs);
                        if (objecT.isPresent()) {
                            index(objecT.get());
                            returnSet.add(objecT.get());
                        }
                    }
                    printSql(sql.toString());
                } catch (SQLException ex) {
                    Flogger.atSevere().withCause(ex).log(sql.toString());
                } finally {
                    SessionDB.getSessionDB().close();
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
            if (SessionDB.getSessionDB().connect()) {
                StringBuilder sql = new StringBuilder(
                    "SELECT * FROM " + tableName + " WHERE " + colName + (in ? " " : " NOT ") + "BETWEEN '" + start
                    + "' AND '" + end + "'");
                try (Statement ps = SessionDB.getSessionDB().getConn()
                                             .createStatement(); ResultSet rs = ps.executeQuery(sql.toString())) {
                    while (rs.next()) {
                        Optional<V> objecT = buildObject(rs);
                        if (objecT.isPresent()) {
                            index(objecT.get());
                            returnSet.add(objecT.get());
                        }
                    }
                    printSql(sql.toString());
                } catch (SQLException ex) {
                    Flogger.atSevere().withCause(ex).log(sql.toString());
                } finally {
                    SessionDB.getSessionDB().close();
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
        if (SessionDB.getSessionDB().connect()) {
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
            try (Statement ps = SessionDB.getSessionDB()
                                         .getConn()
                                         .createStatement(); ResultSet rs = ps.executeQuery(sql.toString())) {
                while (rs.next()) {
                    strings.add(rs.getString(1));
                }
                printSql(sql.toString());
            } catch (SQLException ex) {
                Flogger.atSevere().withCause(ex).log(sql.toString());
            } finally {
                SessionDB.getSessionDB().close();
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

    public Optional<V> queryBiUnique(String col1Name, String uni, String col2Name, String que) {
        Optional<V> objecT = Optional.empty();
        if (SessionDB.getSessionDB().connect()) {
            String sql = String.format("SELECT * FROM %s WHERE %s = '%s' AND %s = '%s'", tableName, col1Name, uni, col2Name, que);
            try (Statement ps = SessionDB.getSessionDB()
                                         .getConn()
                                         .createStatement(); ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    objecT = buildObject(rs);
                    if (objecT.isPresent()) {
                        index(objecT.get());
                        return objecT;
                    }
                }
                printSql(sql);
            } catch (SQLException ex) {
                Flogger.atSevere().withCause(ex).log(sql);
            } finally {
                SessionDB.getSessionDB().close();
            }
        }
        return objecT;
    }


    public int insert(V objectV) {
        checkNotNull(objectV);
        int rows = 0;
        if (objectV.getId() == 0) {
            if (SessionDB.getSessionDB().connect()) {
                String sql = String.format("INSERT INTO %s VALUES %s", tableName, getInsertString(objectV.getColumnNames()
                                                                                                         .size()));
                try (PreparedStatement pstmt = SessionDB.getSessionDB()
                                                        .getConn()
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
                    Flogger.atSevere().withCause(ex).log(sql);
                } finally {
                    SessionDB.getSessionDB().close();
                }
            }
        }
        return rows;
    }

    public int insert(Iterable<V> objectsV) {
        int rows = 0;
        SessionDB.getSessionDB().setAutoclose(false);
        try {
            for (V v : objectsV) {
                rows += insert(v);
            }
        } finally {
            SessionDB.getSessionDB().setAutoclose(true);
        }
        return rows;
    }

    public int update(V objectV) {
        checkNotNull(objectV);
        int rows = 0;
        if (!Globals.SAFE_UPDATE || objectV.getBackup() != null) {
            if (SessionDB.getSessionDB().connect()) {
                String sql = String.format("UPDATE %s SET %s WHERE %s = '%d'", tableName, getUpdateString(objectV.getColumnNames()
                                                                                                                 .iterator()), idColName, objectV
                                               .getId());
                try (PreparedStatement pstmt = SessionDB.getSessionDB().getConn().prepareStatement(sql)) {
                    objectV.buildStatement(pstmt);
                    rows = pstmt.executeUpdate();
                    if (rows > 0) {
                        reindex(objectV);
                    }
                    printSql(sql);
                } catch (SQLException ex) {
                    Flogger.atSevere().withCause(ex).log(sql);
                    Flogger.atSevere().log("Reverting object to backup");
                    objectV.restoreFromBackup();
                } finally {
                    SessionDB.getSessionDB().close();
                    objectV.commit();
                }
            }
        } else
            Flogger.atInfo().log("no object backup and Safe Update on");
        return rows;
    }

    public int update(Set<V> objectsV) {
        int rows;
        SessionDB.getSessionDB().setAutoclose(false);
        try {
            rows = objectsV.stream().mapToInt(this::update).sum();
        } finally {
            SessionDB.getSessionDB().setAutoclose(true);
        }
        return rows;
    }

    public int refresh(V objectV) {
        checkNotNull(objectV);
        deindex(objectV);
        Optional<V> freshObject = query(idColName, objectV.getId(), false);
        if (freshObject.isPresent()) {
            if (!freshObject.get().equals(objectV))
                return objectV.restoreFrom(freshObject.get()) ? 1 : 0;
        }
        return 0;
    }

    public int refresh(Set<V> objectsV) {
        int rows = 0;
        SessionDB.getSessionDB().setAutoclose(false);
        try {
            for (V v : objectsV) {
                rows += refresh(v);
            }
        } finally {
            SessionDB.getSessionDB().setAutoclose(true);
        }
        return rows;
    }


    public int delete(V objecT) {
        checkNotNull(objecT);
        return delete(objecT.getId());
    }

    public int delete(Integer key) {
        int rows = 0;
        if (SessionDB.getSessionDB().connect()) {
            String sql = String.format("DELETE FROM %s WHERE %s = '%d' ", tableName, idColName, key);
            try (Statement stmt = SessionDB.getSessionDB().getConn().createStatement()) {
                rows = stmt.executeUpdate(sql);
                deindex(key);
                printSql(sql);
            } catch (SQLException ex) {
                Flogger.atSevere().withCause(ex).log(sql);
            } finally {
                SessionDB.getSessionDB().close();
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
            if (SessionDB.getSessionDB().connect()) {
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
                try (Statement ps = SessionDB.getSessionDB().getConn().createStatement()) {
                    SessionDB.getSessionDB().getConn().setAutoCommit(false);
                    rows = ps.executeUpdate(sql.toString());
                    if (rows == keys.size()) {
                        SessionDB.getSessionDB().getConn().commit();
                        keys.forEach(e -> deindex(e));
                    } else {
                        SessionDB.getSessionDB().getConn().rollback();
                    }
                    SessionDB.getSessionDB().getConn().setAutoCommit(true);
                    printSql(sql.toString());
                } catch (SQLException ex) {
                    Flogger.atSevere().withCause(ex).log(sql.toString());
                } finally {
                    SessionDB.getSessionDB().close();
                }
            }
        return rows;
    }
}
