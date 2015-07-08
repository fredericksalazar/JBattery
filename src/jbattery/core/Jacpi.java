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
    private String [] acData;
    private int indexOf;

    public Jacpi(){
        System.out.println("initializing JACPI ...");
        verifyAcpi();
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
                      proc.destroy();
                    }catch(IOException er){
                        Notification.show("JBattery ERROR",er.getMessage()+"JBattery exit now.", Notification.ERROR_MESSAGE, true);
                    }
                }else{
                   Notification.show("JBattery ERROR", "JBattery is developed only for GNU/Linux OS, please verify and execute, JBattery exit now.", Notification.ERROR_MESSAGE, Notification.NICON_DARK_THEME, true);
                }
    }

    /**
     * Obtiene los datos de la batería recibidos de ACPI
     * @return String bateryData
     */
    private String[] getStatusACPI(){
        try{
            proc = Runtime.getRuntime().exec("acpi");
            in = proc.getInputStream();
            br = new BufferedReader (new InputStreamReader (in));
            bateryData = br.readLine();
            in.close();
            br.close();
            proc.destroy();
        }catch(IOException e){
            e.printStackTrace();
        }
        return bateryData.split(",");
    }



    /**
     * Se  encarga de interpretar los datos enviados por el Jacpi y se encarga
     * de convertir los valores en el porcentaje real de la bateria
     *
     * @return String nivel
     */
    public int getPercentBattery(){
        acData = getStatusACPI();
        nivel = acData[1];
        indexOf = nivel.indexOf('%');
        System.out.println("The percent of batery is: "+nivel.substring(indexOf-3, indexOf)+"%");
        nivel = nivel.substring(indexOf-3, indexOf);
        return Integer.parseInt(nivel);
    }
    
    /**
     * Retorna el tiempo restante para descarga la bateria.
     * @return nivel
     */
    public String getTimeRemaining(){
        if(acData != null){
          nivel = acData[2].substring(0, 9);
        }
        return nivel;
    }
    
}
