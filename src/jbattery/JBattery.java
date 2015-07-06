/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbattery;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import jbattery.core.Init;

/**
 *
 * @author frederick
 */
public class JBattery {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println("Starting JBattery 1.0 ...");
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
            Init in = new Init();
            in.init();
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(JBattery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
