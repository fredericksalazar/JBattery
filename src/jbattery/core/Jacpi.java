/*
 * Copyright (c) NiconSystemCO 2015
 * License: GPLv3
 *
 * Authors:
 * Frederick Adolfo Salazar Sanchez <fredefass01@gmail.com>
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as
 *   published by the Free Software Foundation; either version 3,
 *   or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details
 *
 *   You should have received a copy of the GNU General Public
 *   License along with this program; if not, write to the
 *   Free Software Foundation, Inc.,
 *   51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package jbattery.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import nicon.notify.core.Notification;

/**
 *
 * @author frederick
 */
public class Jacpi {

    private String OS;
    private String nivel;
    private String tmRem;
    private String batData;
    private String [] acData;
    private String[] out;

    private BufferedReader br;
    private BufferedReader chkBuf;
    private Process proc;
    private Process chkBat;
    
    private int indexOf;
    private int capBat;
    private ArrayList <String> chkLines;

    /**
    * Inicializa la interface de comunicacion entre la JVM y el kernel Linux
    * usando ACPI para obtener los datos de la bateria del dispositivo, estados
    * datos serán obtenidos, interpretados y devueltos por JACPI.
    * JBattery solo funcionará sobre OS Linux que tengan instalado el paquete
    * ACPI, para ello al inicializar JACPI este se encargará de validar estados
    * 2 requerimientos, en caso de no cumplirse alguno de los dos entonces la
    * app será cerrada.
    */

    public Jacpi(){
        System.out.println("initializing JACPI ...");
        verifyAcpi();
    }


    /**
     * Valida si el entorno de ejecucion cumple con los dos requerimientos
     * 1 que el OS Sea GNU/Linux 2) que este instalado el paquete ACPI, en caso
     * de que alguna de los 2 requerimientos no se cumplan se notifica al usuario
     */
    private void verifyAcpi(){
            System.out.println("Validating requeriments ... \ngetting properties ...");
            OS = System.getProperty("os.name");
            System.out.println("\nOperative System : "+OS);
                if(OS.equals("Linux")){
                    try{
                      proc = Runtime.getRuntime().exec("acpi");
                      proc.destroy();
                    }catch(IOException er){
                        Notification.show("JBattery ERROR",er.getMessage()+"JBattery exit now.",
                                Notification.NICON_DARK_THEME, Notification.ERROR_MESSAGE, true);
                    }
                }else{
                   Notification.show("JBattery ERROR", "JBattery is developed only for GNU/Linux OS,"
                           + " please verify and execute, JBattery exit now.", Notification.NICON_DARK_THEME,
                           Notification.ERROR_MESSAGE, true);
                }
    }

    /**
     * Obtiene los datos de la batería recibidos de ACPI
     * @return String bateryData
     */
    private String[] getStatusACPI(){
        try{
            proc = Runtime.getRuntime().exec("acpi");
            br = new BufferedReader (new InputStreamReader (proc.getInputStream()));
            batData = br.readLine();
            br.close();
            proc.destroy();
        }catch(IOException e){
            System.err.println(e);
        }
        return batData.split(",");
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
        nivel = nivel.replaceAll("%", "");
        return Integer.parseInt(nivel.replace(" ", ""));
    }


    /**
     * Retorna el tiempo restante para descarga la bateria.
     * @return nivel
     */
    public String getTimeRemaining(){
        if(acData != null){
            tmRem = acData[2].replace("%", "");
            tmRem = tmRem.replace(" ", "").substring(0, tmRem.length()-11);
        }
        return tmRem;
    }

    /**
     * Este metodo verificará el estado vital de la bateria del dispositivo
     * en caso que full capacity sea menor a last full charged entonces la
     * bateria esta muriendo.
     */
    public void checkBattery(){
        try{
            chkLines = new ArrayList();
            String line;
            System.out.println("\nCheking the battery status life ...");
            chkBat = Runtime.getRuntime().exec("acpi -i");
            chkBuf = new BufferedReader(new InputStreamReader(chkBat.getInputStream()));
                do{
                    line = chkBuf.readLine();
                        if(line != null){
                           chkLines.add(line);
                        }
                }while(line != null);

            if(chkLines.size()>1){
                try{
                   out = chkLines.get(1).split(",");
                   indexOf = out[1].indexOf("%");
                   capBat = Integer.parseInt(out[1].substring(indexOf-3, indexOf).replace(" ", ""));
                   System.out.println("The Battery capacity is: "+capBat);

                        if(capBat < 50){
                            Notification.show("JBattery Status Life",
                                            "The battery capacity of your Device is: "+capBat+"%\n"
                                            + "Your Battery is BAD",Notification.NICON_DARK_THEME,
                                            Notification.PLUGIN_ICON);
                        }
                        if(capBat>50 && capBat < 70){
                            Notification.show("JBattery Status Life",
                                              "The Batter capacity of your Device is: "+capBat+"%\n"
                                            + "your battery has started to deteriorate",Notification.NICON_DARK_THEME);
                        }
                        if(capBat > 95){
                           Notification.show("JBattery Status Life",
                                              "The Batter capacity of your Device is: "+capBat+"%\n"
                                            + "your battery is VERY GOOD",Notification.NICON_DARK_THEME);
                        }
                }catch(NumberFormatException nme){
                    System.err.println(nme);
                }
            }
            chkBuf.close();
        }catch(Exception e){
            System.err.println(e);
        }
    }

}
