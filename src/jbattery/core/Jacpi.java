/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbattery.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import nicon.notify.core.Notification;

/**
 *
 * @author frederick
 */
public class Jacpi {
    
    private String nivel;
    private InputStream in;
    private BufferedReader br;
    private String bateryData;
    private Process proc;
    private int indexOf;
        
    public Jacpi(){
        System.out.println("initializing JACPI ...");
        verifyAcpi();
    }
    
    /**
     * Obtiene los datos de la batería recibidos de ACPI
     * @return String bateryData
     */
    private String getStatusACPI(){
        try{
            in = proc.getInputStream();
            br = new BufferedReader (new InputStreamReader (in));
            bateryData = br.readLine(); 
            in.close();
            br.close();
            proc.destroy();
        }catch(Exception e){
            System.err.println(e.fillInStackTrace());
        }
        return bateryData;
    }
    
    
    /**
     * Se encargará de validar si la app se ejecuta sobre un OS Linux y ademas
     * de si ACPI esta instalado en el sistema.
     */
    private void verifyAcpi(){
            System.out.println("Getting properties ...");            
            String os = System.getProperty("os.name");
            System.out.println("Operative System : "+os);            
                if(os.equals("Linux")){
                    try{
                      proc = Runtime.getRuntime().exec("acpi");  
                    }catch(IOException er){
                        Notification.show("JBattery ERROR",er.getMessage()+" JBattery exit now.", Notification.ERROR_MESSAGE, true);
                    }
                }else{
                   Notification.show("JBattery ERROR", "JBattery is developed only for GNU/Linux OS, please verify and execute, JBattery exit now.", Notification.ERROR_MESSAGE, Notification.NICON_DARK_THEME, true);
                }
    }
    
    /**
     * Se  encarga de interpretar los datos enviados por el Jacpi y se encarga
     * de convertir los valores en el porcentaje real de la bateria
     * 
     * @return String nivel 
     */
    public String getPercentBattery(){
        nivel = getStatusACPI();
        indexOf = nivel.indexOf('%');
        System.out.println("The percent of batery is: "+nivel.substring(indexOf-3, indexOf)+"%");
        nivel = nivel.substring(indexOf-3, indexOf);
        return nivel;
    }
    
}
