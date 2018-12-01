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

import java.util.Timer;
import java.util.TimerTask;
import nicon.notify.core.Notification;

/**
 * Inicializa la aplicacion, se encarga de validar los diferentes estados e
 * información que arroja la batería
 *
 * @author frederick
 * @version 2.0
 */

public class Init {

    private final Jacpi acpi;
    private final Timer timer;
    private TimerTask acpiTask;
    private TimerTask valTask;
    private TimerTask memTask;
    private int val;
    private int bat_Level;
    Runtime run;

    public Init(){
        System.out.println("initializing init ...\n");
        acpi = new Jacpi();
        timer = new Timer();
        validator();
        clearMemory();
    }

    public void init(){
        System.out.println("setting init interface\n");
        acpi.checkBattery();
        acpiTask = new TimerTask(){
            @Override
            public void run() {
                bat_Level = acpi.getPercentBattery();
                printLine();

                //Si bat_Level es menor a 10% indica que la bateria esta en un punto crítico y que debe conectarse a una
                //fuente de energía.
                if(bat_Level < 10){
                     if(val == 0){
                         Notification.show("URGENT Battery Status","Battery is DOWN, please conect to adapter AC"
                                 + "Battery Level is"+ bat_Level +"%\ntime remaning:"+acpi.getTimeRemaining(),
                                 Notification.NICON_DARK_THEME, Notification.BAT_DOWN,true);
                         val=1;
                     }
                }

                //Si bat_Level esta en 50% indica que la batería ha llegado a la mitady que pronto iniciará su proceso
                //de descarga
                if(bat_Level == 50 ){
                    if(val == 0){
                        Notification.show("Battery status","Your battery is downloading\n"
                                          + "Battery level is:"+ bat_Level +"%\n"
                                          + "time remaining: "+acpi.getTimeRemaining(),
                                          Notification.NICON_DARK_THEME,Notification.BAT_MED,true);
                        val =1;
                    }
                }

                //Si bat_Level llega al 100& se informa que la batería se ha recargado exitosamente y que debe ser
                //desconectada para evitar daños en la misma
                if(bat_Level == 100){
                     if(val==0){
                        Notification.show("Battery status","The Battery is full charged, please disconect\n"
                               +"Battery Level is: "+ bat_Level +"%",Notification.NICON_DARK_THEME,
                               Notification.BAT_FULL,true);
                        val = 1;
                     }
                }
            }
        };
        timer.schedule(acpiTask, 10, 30000);
    }

    /**
     * Este metodo se encarga de imprimir en consola los valores de batteryLeve y timeRemaining
     */
    private void printLine(){
        if(bat_Level < 100){
            System.out.println("\ngetting the batery status ...\nthe battery level is: " + bat_Level + "%\n"
                    + "time remaining is: " + acpi.getTimeRemaining());
        }else {
            System.out.println("The Battery is full charged, please disconect the AC power conector...");
        }
    }

    /**
    Este metodo se encarga de validar la ejecucion de JACPI.
    */
    private void validator(){
        valTask = new TimerTask(){
            @Override
            public void run() {
                val = 0;
            }
        };
        timer.schedule(valTask, 1,600000);
    }

    /**
     * Este metodo se encarga de hacer una dump de memoria haciendo una invocacion al garbage collector de la JVM
     * es llamado cada 15 minutos. (Se espera aumentar mas el tiempo para hacer la llamada)
     */
    private void clearMemory(){
        try{
            memTask = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Starting a Garbage Collector JVM...");
                    System.runFinalization();
                    System.gc();
                    System.out.println("Garbage Collector finalized ...");
                }
            };
            timer.schedule(memTask, 1,300000);
        }catch(Exception e){
            System.err.println(e);
        }
    }

}
