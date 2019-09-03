package app.data.casteldao;

import static com.google.common.base.Preconditions.checkNotNull;

import app.data.casteldao.index.core.IIndex;
import app.data.casteldao.model.EntityFactory;
import app.data.casteldao.model.IEntity;
import app.misc.Flogger;
import app.misc.Globals;
import com.google.common.collect.Sets;
import java.io.Serializable;
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
import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.Nullable;

public class GenericDao<I extends Serializable, E extends IEntity<I>> implements Globals {

    public String idColName = IEntity.ID_COL_NAME;
    public String tableName;
    public String newObjectId = "0";


    protected ArrayList<IIndex<?, E, I>> indexes;

    protected Class<E> clazz; // V = Entity Class
    private E clone;


    public GenericDao(String tableName, ArrayList<IIndex<?, E, I>> indexes, Class<E> clazz) {
        this.tableName = tableName;
        this.indexes   = indexes;
        if (clazz != null) {
            this.clazz = clazz;
        } else
            findGenericClass();
        clone = (E) new EntityFactory<E, I>().getNewInstance();
    }

    public GenericDao(String tableName, ArrayList<IIndex<?, E, I>> indexes) {
        this(tableName, indexes, null);
    }

    public Optional<E> buildObject(ResultSet rs) {
        E e = (E) clone.clone();
        boolean buildt = e.setEntity(rs);
        if (VERBOSE_FACTORY)
            System.out.println(e.toString());
        return buildt ? Optional.of(e) : Optional.empty();
    }

    @SuppressWarnings("unchecked")
    public Class<E> findGenericClass() {
        if (clazz == null)
            clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
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

    public Function<String, @Nullable I> mapToId() {
        return s -> (I) Integer.valueOf(s);
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

    public String getNewObjectId() {
        return newObjectId;
    }

    public void setNewObjectId(String newObjectId) {
        this.newObjectId = newObjectId;
    }

    public ArrayList<IIndex<?, E, I>> getIndexes() {
        return indexes;
    }

    protected void index(E objectV) {
        indexes.forEach(e -> e.index(objectV));
    }

    protected void deindex(I key) {
        indexes.forEach(e -> e.deindex(key));
    }

    protected void deindex(E objectV) {
        indexes.forEach(e -> e.deindex(objectV));
    }

    protected void reindex(E objectV) {
        indexes.forEach(e -> e.reindex(objectV));
    }


    private Optional<E> query(String colName, Object unique, boolean indexOn) {
        Optional<E> objecT = Optional.empty();
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

    public Optional<E> query(I id) {
        return query(idColName, id.toString());
    }

    public Optional<E> query(String colName, Object unique) {
        return query(colName, unique, true);
    }

    public Set<E> querySome(String colName, Collection<? extends Object> search, boolean isIn) {
        HashSet<E> returnSet = Sets.newHashSetWithExpectedSize(search.size());
        if (search.size() > 0)
            if (SessionDB.getSessionDB().connect()) {
                StringBuilder sql = new StringBuilder(String.format("SELECT * FROM %s WHERE %s %s IN( ", tableName, colName, isIn ? "" : "NOT"));
                Iterator<? extends Object> iterator = search.iterator();
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
                        Optional<E> objecT = buildObject(rs);
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

    public Set<E> querySome(String colName, Collection<? extends Object> search) {
        return querySome(colName, search, true);
    }

    public Set<E> querySome(String colName, String search) {
        return querySome(colName, Collections.singletonList(search));
    }

    public Set<E> querySome(String colName, String search, Boolean isIn) {
        return querySome(colName, Collections.singletonList(search), isIn);
    }

    public Set<E> querySome(Set<I> ids) {
        return querySome(idColName, ids, true);
    }

    public Set<E> querySome(Set<I> ids, boolean isIn) {
        return querySome(idColName, ids, isIn);
    }


    public Set<I> getOnlyIds(String colName, Collection search, boolean isIn) {
        HashSet<I> returnSet = Sets.newHashSetWithExpectedSize(search.size());
        if (search.size() > 0)
            if (SessionDB.getSessionDB().connect()) {
                StringBuilder sql = new StringBuilder(String.format("SELECT * FROM %s WHERE %s %s IN( ", tableName, colName, isIn ? "" : "NOT"));
                Iterator iterator = search.iterator();
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
                        returnSet.add(mapToId().apply(rs.getString(1)));
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

    public Set<I> getAllIds() {
        return getOnlyIds(idColName, Collections.singletonList(getNewObjectId()), false);
    }


    public Set<E> querySomeBi(String colName, Collection search, boolean isIn, String colName2, Collection search2, boolean isIn2) {
        HashSet<E> returnSet = Sets.newHashSetWithExpectedSize(search.size());
        if (search.size() > 0)
            if (SessionDB.getSessionDB().connect()) {
                StringBuilder sql = new StringBuilder(String.format("SELECT * FROM %s WHERE %s %s IN( ", tableName, colName, isIn ? "" : "NOT"));
                Iterator iterator = search.iterator();
                while (iterator.hasNext()) {
                    sql.append("'").append(iterator.next().toString()).append("'");
                    if (iterator.hasNext()) {
                        sql.append(", ");
                    } else {
                        sql.append(" )");
                    }
                }
                sql.append(String.format("AND %s %s IN( ", colName2, isIn ? "" : "NOT"));
                Iterator iterator2 = search2.iterator();
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
                        Optional<E> objecT = buildObject(rs);
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


    public Set<E> queryAll() {
        Set<E> returnSet = null;
        if (SessionDB.getSessionDB().connect()) {
            String sql = String.format("SELECT * FROM %s", tableName);
            try (Statement ps = SessionDB.getSessionDB()
                                         .getConn()
                                         .createStatement(); ResultSet rs = ps.executeQuery(sql)) {
                returnSet = Sets.newHashSetWithExpectedSize(rs.getFetchSize());
                while (rs.next()) {
                    Optional<E> objecT = buildObject(rs);
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


    public Set<E> queryLike(String colName, String string, boolean like) {
        HashSet<E> returnSet = Sets.newHashSet();
        if (string != null && string.length() > 0)
            if (SessionDB.getSessionDB().connect()) {
                StringBuilder sql = new StringBuilder(
                    "SELECT * FROM " + tableName + " WHERE " + colName + (like ? " " : " NOT ") + "LIKE '" + string +
                    "'");
                try (Statement ps = SessionDB.getSessionDB().getConn().createStatement();
                     ResultSet rs = ps.executeQuery(sql.toString())) {
                    while (rs.next()) {
                        Optional<E> objecT = buildObject(rs);
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

    public Set<E> queryLike(String colName, String string) {
        return queryLike(colName, string, true);
    }

    public Set<E> queryGreaterLesser(String colName, String string, boolean greaterThan, boolean inclusive) {
        HashSet<E> returnSet = Sets.newHashSet();
        if (string != null && string.length() > 0)
            if (SessionDB.getSessionDB().connect()) {
                StringBuilder sql = new StringBuilder(
                    String.format("SELECT * FROM %s WHERE %s %s%s '%s'", tableName, colName, greaterThan ? ">" : "<", inclusive ? "=" : "", string));
                try (Statement ps = SessionDB.getSessionDB()
                                             .getConn()
                                             .createStatement(); ResultSet rs = ps.executeQuery(sql.toString())) {
                    while (rs.next()) {
                        Optional<E> objecT = buildObject(rs);
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

    public Set<E> queryGreaterThan(String colName, String string) {
        return queryGreaterLesser(colName, string, true, false);
    }

    public Set<E> queryGreaterOrEqualThan(String colName, String string) {
        return queryGreaterLesser(colName, string, true, true);
    }

    public Set<E> queryLesserThan(String colName, String string) {
        return queryGreaterLesser(colName, string, false, false);
    }

    public Set<E> queryLesserOrEqualThan(String colName, String string) {
        return queryGreaterLesser(colName, string, false, true);
    }

    public Set<E> queryBetween(String colName, String start, String end, boolean in) {
        HashSet<E> returnSet = Sets.newHashSet();
        if (start != null && start.length() > 0)
            if (SessionDB.getSessionDB().connect()) {
                StringBuilder sql = new StringBuilder(
                    "SELECT * FROM " + tableName + " WHERE " + colName + (in ? " " : " NOT ") + "BETWEEN '" + start
                    + "' AND '" + end + "'");
                try (Statement ps = SessionDB.getSessionDB().getConn()
                                             .createStatement(); ResultSet rs = ps.executeQuery(sql.toString())) {
                    while (rs.next()) {
                        Optional<E> objecT = buildObject(rs);
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

    public Set<E> queryBetween(String colName, String start, String end) {
        return queryBetween(colName, start, end, true);
    }

    public Set<String> queryOneColumn(String returnColName, String whereColName, Collection<? extends String> equalsCondition,
                                      boolean isIn) {
        Set<String> strings = Sets.newHashSet();
        if (SessionDB.getSessionDB().connect()) {
            StringBuilder sql = new StringBuilder(String.format("SELECT %s FROM %s WHERE %s %s IN( ", returnColName, tableName, whereColName, isIn ? "" : "NOT"));
            Iterator<? extends String> iterator = equalsCondition.iterator();
            while (iterator.hasNext()) {
                sql.append("'").append(iterator.next()).append("'");
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

    public Optional<E> queryBiUnique(String col1Name, String uni, String col2Name, String que) {
        Optional<E> objecT = Optional.empty();
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


    public int insert(E objectV) {
        checkNotNull(objectV);
        int rows = 0;
        if (objectV.getId().toString().equals(newObjectId)) {
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
                            objectV.setId(rs);
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

    public int insert(Iterable<E> objectsV) {
        int rows = 0;
        SessionDB.getSessionDB().setAutoclose(false);
        try {
            for (E v : objectsV) {
                rows += insert(v);
            }
        } finally {
            SessionDB.getSessionDB().setAutoclose(true);
        }
        return rows;
    }

    public int update(E objectV) {
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

    public int update(Set<E> objectsV) {
        int rows;
        SessionDB.getSessionDB().setAutoclose(false);
        try {
            rows = objectsV.stream().mapToInt(this::update).sum();
        } finally {
            SessionDB.getSessionDB().setAutoclose(true);
        }
        return rows;
    }

    public int refresh(E objectV) {
        checkNotNull(objectV);
        deindex(objectV);
        Optional<E> freshObject = query(idColName, objectV.getId(), false);
        if (freshObject.isPresent()) {
            if (!freshObject.get().equals(objectV))
                return objectV.restoreFrom(freshObject.get()) ? 1 : 0;
        }
        return 0;
    }

    public int refresh(Set<E> objectsV) {
        int rows = 0;
        SessionDB.getSessionDB().setAutoclose(false);
        try {
            for (E v : objectsV) {
                rows += refresh(v);
            }
        } finally {
            SessionDB.getSessionDB().setAutoclose(true);
        }
        return rows;
    }


    public int delete(E objecT) {
        checkNotNull(objecT);
        return deleteKey(mapToId().apply(objecT.getId().toString()));
    }

    public int deleteSome(Set<E> toDelete) {
        HashSet<I> idsToDelete = Sets.newHashSet();
        toDelete.forEach(e -> idsToDelete.add(e.getId()));
        return deleteSomeKeys(idsToDelete);
    }

    public int deleteKey(I key) {
        int rows = 0;
        if (SessionDB.getSessionDB().connect()) {
            String sql = String.format("DELETE FROM %s WHERE %s = '%s' ", tableName, idColName, key.toString());
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

    public int deleteSomeKeys(Set<I> keys) {
        int rows = 0;
        if (keys.size() > 0)
            if (SessionDB.getSessionDB().connect()) {
                StringBuilder sql = new StringBuilder("DELETE FROM " + tableName + " WHERE " + idColName + " IN( ");
                Iterator<?> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    sql.append(iterator.next().toString());
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
