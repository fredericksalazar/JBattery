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

    private Jacpi acpi;
    private Timer timer;
    private TimerTask acpiTask;
    private TimerTask valTask;
    private TimerTask memTask;
    private int val;
    private int acpiVal;
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

                acpiVal = acpi.getPercentBattery();

                //validamos que la bateria no este al 100% para evitar un ArrayIndexOfBoundsException pues al momento
                //de ejecutar el comando ACPI este no retornará un String[2] sino un String[1]

                if(acpiVal < 100){
                    System.out.println("\ngetting the batery status ...\nthe battery level is: " + acpiVal + "%\n"
                            + "time remaining is: " + acpi.getTimeRemaining());
                }else {
                    System.out.println("The Battery is full charged, please disconect the AC power conector...");
                }

                if(acpiVal < 10){
                     if(val == 0){
                         Notification.show(Notification.BAT_DOWN, "Battery status",
                             "URGENT The battery is down, please conect to adapter AC\n"
                             +"Battery Level is"+acpiVal+"%\ntime remaning: "+acpi.getTimeRemaining(),
                             Notification.NICON_DARK_THEME);
                         val=1;
                     }
                 }

                if(acpiVal == 50 ){
                    if(val == 0){
                        Notification.show(Notification.BAT_MED, "Battery status",
                                          "Your battery is downloading\n"
                                          + "Battery level is:"+acpiVal+"%\n"
                                          + "time remaining: "+acpi.getTimeRemaining(),
                                          Notification.NICON_DARK_THEME,false);
                        val =1;
                    }
                 }

                 if(acpiVal == 100){
                     if(val==0){
                        Notification.show(Notification.BAT_FULL,"Battery status",
                                "The Battery is full charged, please disconect\n"
                               +"Battery Level is: "+acpiVal+"%",Notification.NICON_DARK_THEME);
                        val = 1;
                     }
                 }
            }
        };
        timer.schedule(acpiTask, 10,30000);
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
            timer.schedule(memTask, 1,150000);
        }catch(Exception e){
            System.err.println(e);
        }
    }

}
