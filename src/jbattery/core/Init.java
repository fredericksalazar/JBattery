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
 *
 * @author frederick
 */
public class Init {
    
    private Jacpi acpi;
    private Timer timer;
    private TimerTask acpiTask;
    private TimerTask valTask;
    private TimerTask memTask;
    private int val;
    private String acpiVal;    
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
                System.out.println("getting de batery status ...");                
                if(acpiVal.contains("50")){
                    if(val == 0){
                        Notification.show(Notification.BAT_MED, "Battery status", 
                                          "Your battery is downloading,level is:"+acpiVal+"%",
                                          Notification.NICON_DARK_THEME,false);
                        val =1;
                    }
                 }
                
                 if(acpiVal.contains("40")){
                     if(val == 0){
                         Notification.show(Notification.BAT_DOWN, "Battery status", 
                             "URGENT The battery is down, please conect to adapter AC level: "+acpiVal+"%",
                             Notification.NICON_DARK_THEME);
                         val=1;
                     }
                 }
                 
                 if(acpiVal.contains("34")){
                     if(val==0){
                        Notification.show(Notification.BAT_FULL,"Battery status", 
                                "The Battery is full charged, please disconect, "
                               + "level:"+acpiVal+"%",Notification.NICON_DARK_THEME);
                        val = 1;
                     }
                 }
            }            
        }; 
        timer.schedule(acpiTask, 10,30000);
    }
    
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
