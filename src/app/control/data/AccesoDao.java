package app.control.data;

import app.model.AbstractDao;
import app.model.SessionDB;
import app.model.models.Acceso;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class AccesoDao extends AbstractDao<Acceso> {
    /**
     * Singleton lazy initialization
     */
    private static AccesoDao daoInstance;

    private AccesoDao() {
        TABLE_NAME = "accesos";
    }

    public static synchronized AccesoDao getD() {
        if (daoInstance == null) {
            daoInstance = new AccesoDao();
        }
        return daoInstance;
    }

    @Override
    public Acceso query(int id) {
        Acceso acceso = null;
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s WHERE %s = '%d'", TABLE_NAME, ID_COL_NAME, id);
            try (Statement ps = SessionDB.getConn().createStatement();
                 ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    acceso = new Acceso(rs.getInt(ID_COL_NAME), rs.getString("nivel"));
                    table.putIfAbsent(acceso.getId(), acceso);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(AccesoDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return acceso;
    }

    @Override
    public HashMap<Integer, Acceso> query(ArrayList<Integer> ids) {
        HashMap<Integer, Acceso> returnMap = new HashMap<>();
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
                    Acceso acceso = new Acceso(rs.getInt(ID_COL_NAME), rs.getString("nivel"));
                    table.putIfAbsent(acceso.getId(), acceso);
                    returnMap.put(acceso.getId(), acceso);
                }
                printSql(sql.toString());
            } catch (SQLException ex) {
                Logger.getLogger(AccesoDao.class.getName()).log(Level.SEVERE, sql.toString(), ex);
            } finally {
                SessionDB.close();
            }
        }
        return returnMap;
    }

    @Override
    public HashMap<Integer, Acceso> queryAll() {
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s", TABLE_NAME);
            try (Statement ps = SessionDB.getConn().createStatement();
                 ResultSet rs = ps.executeQuery(sql)) {
                while (rs.next()) {
                    Acceso acceso = new Acceso(rs.getInt(ID_COL_NAME), rs.getString("nivel"));
                    table.put(acceso.getId(), acceso);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(AccesoDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return table;
    }

    @Override
    public int insert(Acceso acceso) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = String.format("INSERT INTO %s VALUES(NULL, ?)", TABLE_NAME);
            try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
                pstmt.setString(1, acceso.getNivel());
                rows = pstmt.executeUpdate();

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        acceso.setId(rs.getInt(1));
                        table.put(acceso.getId(), acceso);
                    }
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(AccesoDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    @Override
    public int update(Acceso acceso) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = String.format("UPDATE %a SET nivel = ? WHERE %s = ?", TABLE_NAME, ID_COL_NAME);
            try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
                pstmt.setString(1, acceso.getNivel());
                pstmt.setInt(2, acceso.getId());
                rows = pstmt.executeUpdate();
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(AccesoDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    @Override
    public int updateDao(Acceso acceso) {
        int rows = 0;
        if (acceso.getId() > 0) {
            if (SessionDB.connect()) {
                String sql = String.format("SELECT * FROM %s WHERE %s = '%d'", TABLE_NAME, ID_COL_NAME, acceso.getId());
                try (Statement ps = SessionDB.getConn().createStatement();
                     ResultSet rs = ps.executeQuery(sql)) {
                    if (rs.next()) {
                        acceso.setNivel(rs.getString(2));
                        rows++;
                        table.put(acceso.getId(), acceso);
                    }
                    printSql(sql);
                } catch (SQLException ex) {
                    Logger.getLogger(AccesoDao.class.getName()).log(Level.SEVERE, sql, ex);
                } finally {
                    SessionDB.close();
                }
            }
        }
        return rows;
    }
}
