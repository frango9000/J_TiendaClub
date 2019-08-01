package app;

import app.control.LoginControl;
import app.control.PropsLoader;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author NarF
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        PropsLoader.setProps();

        //InitialConfig.init();
        //SessionDB.crearTablas();
        //SessionDB.isValid();


        LoginControl lc = new LoginControl();
        lc.setVisible(true);


    }

}
