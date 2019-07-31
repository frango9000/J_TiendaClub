/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.control;

import app.gui.LoginFrame;
import app.model.DataStore;
import app.model.SessionDB;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author NarF
 */
public class LoginControl extends LoginFrame{

    public LoginControl() {

        
        jTextFieldUser.setText(SessionDB.getUser());
        jPasswordField1.setText(SessionDB.getPassword());
        
        jButton1.addActionListener(e -> {
            //System.out.println(SessionDB.isValid());
            
            DataStore.getAccesos().queryAll();
            DataStore.getSedes().queryAll();

            System.out.println(DataStore.getCompras().get(1).toString());
        });
        
        jButton2.addActionListener(e -> {
            String ip = JOptionPane.showInputDialog(new JFrame(), "Enter DB IP", SessionDB.getJdbcIP()).trim();
            String port = JOptionPane.showInputDialog(new JFrame(), "Enter DB Port",  SessionDB.getJdbcPort()).trim();
            if(ip.length() > 0 && port.length() > 0){
                SessionDB.setJdbcIP(ip);
                SessionDB.setJdbcPort(port);
            }
        });
        
        jButton3.addActionListener(e -> {
            SessionDB.setUser(jTextFieldUser.getText().trim());
            SessionDB.setPassword(new String(jPasswordField1.getPassword()).trim());
            if(SessionDB.isValid())
                jButton3.setBackground(Color.green);
            else jButton3.setBackground(Color.red);
            
        });
        
        jButton4.addActionListener(e -> {
            PropsLoader.saveProps();
        });
        
    }
  
}
