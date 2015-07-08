/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        acpiTask = new TimerTask(){
            @Override
            public void run() {
                acpiVal = acpi.getPercentBattery();
                System.out.println("getting de batery status ...\nthe battery level is: "+acpiVal
                +"\ntime reimaining: "+acpi.getTimeRemaining());                
                if(acpiVal ==50 ){
                    if(val == 0){
                        Notification.show(Notification.BAT_MED, "Battery status",
                                          "Your battery is downloading,level is:"
                                          +acpiVal+"\n time remaining: "+acpi.getTimeRemaining(),
                                          Notification.NICON_DARK_THEME,false);
                        val =1;
                    }
                 }

                 if(acpiVal < 10){
                     if(val == 0){
                         Notification.show(Notification.BAT_DOWN, "Battery status",
                             "URGENT The battery is down, please conect to adapter AC level: "
                             +acpiVal+"\ntime remaning: "+acpi.getTimeRemaining(),
                             Notification.NICON_DARK_THEME);
                         val=1;
                     }
                 }

                 if(acpiVal == 100){
                     if(val==0){
                        Notification.show(Notification.BAT_FULL,"Battery status",
                                "The Battery is full charged, please disconect, "
                               + "level:"+acpiVal+"\ntime remaining: "+acpi.getTimeRemaining()
                                ,Notification.NICON_DARK_THEME);
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
