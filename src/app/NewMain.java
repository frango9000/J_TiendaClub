package app;

import app.model.SessionDB;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author NarF
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        SessionDB.setProps();
        SessionDB.crearTablas();
        SessionDB.isValid();
               
    }
    
}
