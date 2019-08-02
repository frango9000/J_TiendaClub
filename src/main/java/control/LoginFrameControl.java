/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import gui.LoginFrame;
import model.DataStore;
import model.Globals;
import model.SessionDB;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author NarF
 */
public class LoginFrameControl extends LoginFrame {

    public LoginFrameControl() {


        jTextFieldUser.setText(SessionDB.getUser());
        jPasswordField1.setText(SessionDB.getPassword());

        jButtonEnter.addActionListener(e -> {
            //System.out.println(SessionDB.isValid());

            DataStore.getAccesos().queryAll();
            DataStore.getSedes().queryAll();

            System.out.println(DataStore.getCompras().get(1).toString());
        });

        jButtonConfig.addActionListener(e -> {
            String ip = JOptionPane.showInputDialog(new JFrame(), "Enter DB IP", "DB IP", JOptionPane.PLAIN_MESSAGE, null, null, SessionDB.getJdbcIP()).toString().trim();
            String port = JOptionPane.showInputDialog(new JFrame(), "Enter DB Port", "DB Port", JOptionPane.PLAIN_MESSAGE, null, null, SessionDB.getJdbcPort()).toString().trim();
            if (ip.length() > 0 && port.length() > 0) {
                SessionDB.setJdbcIP(ip);
                SessionDB.setJdbcPort(port);
            }
        });

        jButtonTest.addActionListener(e -> {
            SessionDB.setUser(jTextFieldUser.getText().trim());
            SessionDB.setPassword(new String(jPasswordField1.getPassword()).trim());
            if (SessionDB.isValid()) {
                jButtonTest.setBackground(Color.yellow);
                System.out.println(SessionDB.numOfDBs());
                if (SessionDB.numOfDBs() == 0) {
                    if (!SessionDB.isRoot())
                        JOptionPane.showMessageDialog(null, "No valid DBs and no access to create.", "DB Wizard", JOptionPane.PLAIN_MESSAGE, null);
                    else {
                        if (createDB()) {
                            JOptionPane.showMessageDialog(null, "DB creation Success", "DB Wizard", JOptionPane.PLAIN_MESSAGE, null);
                            System.out.println(SessionDB.createFullStructure());
                            if (SessionDB.isSchemaValid()) {
                                jButtonTest.setBackground(Color.green);
                                JOptionPane.showMessageDialog(null, "Structure creation Success", "DB Wizard", JOptionPane.PLAIN_MESSAGE, null);
                            } else {
                                jButtonTest.setBackground(Color.yellow);
                                JOptionPane.showMessageDialog(null, "Structure creation Fail", "DB Wizard", JOptionPane.PLAIN_MESSAGE, null);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "DB Creation Fail", "DB Wizard", JOptionPane.PLAIN_MESSAGE, null);
                            jButtonTest.setBackground(Color.red);
                        }
                    }
                } else {
                    SessionDB.setJdbcCatalog(pickDb());
                    if (SessionDB.isSchemaValid())
                        jButtonTest.setBackground(Color.green);
                }
            } else jButtonTest.setBackground(Color.red);
        });

        jButtonSaveCfg.addActionListener(e -> {
            PropsLoader.saveProps();
        });

    }

    public static String pickDb() {
        String[] dbslist = SessionDB.listDBs().toArray(new String[0]);
        return JOptionPane.showInputDialog(null, "Pick a DB", "Pick a DB", JOptionPane.PLAIN_MESSAGE, null, dbslist, null).toString();
    }

    public static boolean createDB() {
        ArrayList<String> list = SessionDB.listDBs();
        JOptionPane.showMessageDialog(null, "Creating new DB", "DB Wizard", JOptionPane.PLAIN_MESSAGE, null);
        String name = "";
        do {
            name = JOptionPane.showInputDialog(new JFrame(), "Enter DB name", "DB Name", JOptionPane.PLAIN_MESSAGE, null, null, "").toString().trim();
        } while (name.length() < 1 || name == null || list.contains("tdc_" + name));

        JOptionPane.showMessageDialog(null, "Creating DB name: " + name, "DB Wizard", JOptionPane.PLAIN_MESSAGE, null);
        SessionDB.createCatalog(name);
        if (SessionDB.hasCatalog(name)) {
            SessionDB.setJdbcCatalog(Globals.DB_PREFIX + name);
            return true;
        } else return false;
    }

}
