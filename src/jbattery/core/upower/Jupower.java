/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbattery.core.upower;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import jbattery.core.BatteryInterface;
import jbattery.core.util.JBUtil;
import nicon.notify.core.Notification;

/**
 *
 * @author frederick
 */
public class Jupower implements BatteryInterface{
    
    private Process exec;
    private BufferedReader br;
    private String upowerData;
    private String[] fields;
    private UpowerData UpowerData;
    
    public Jupower(){
        
    }

    /**
     * This method query the upower data and format de information to be 
     * tokenizer and parse to object
     */
    
    @Override
    public void queryBatteryData() {
        String line;
        String n_line;
        
        try{
           exec = Runtime.getRuntime().exec("upower -i /org/freedesktop/UPower/devices/battery_BAT0");
           br = new BufferedReader(new InputStreamReader(exec.getInputStream()));
           
            if(br != null){
                while((line = br.readLine()) != null){
                    n_line = line.replace(" ", "").concat(";");
                    upowerData += n_line;
                    
                    if(n_line.contains("icon-name:")) break;
                }
            }
                       
            br.close();
            exec.destroy();
            
            parseUpowerObject();
          
        }catch(IOException e){
            System.err.println("ERROR in JUpower.queryBatteryData:\n"+e);
        }
        finally{
            br = null;
            exec = null;
            line = null;
            n_line = null;
        }
    }
    
    
    /**
     * This method is used to transform the string data of Battery in 
     * a object UpowerData 
     */
    private void parseUpowerObject(){
                
        boolean hasHistory = false;
        boolean hasStatistics = false;
        boolean present = false;
        boolean rechargable = false;
        int percentage = -1;
        int capacity = -1;
        int timeToEmpty = -1;
        int timeToFull = -1;
        int lengthList = -1;
        String technology = "";
                       
        try{
           if(upowerData != null){
               
               if(fields == null){
                    fields = upowerData.split(";");
               }
               
               if(fields.length > 0){
                   
                   lengthList = fields.length;
                   
                   /*
                   create the UpowerData Object to parse the information
                   */
                   if(UpowerData == null){
                       
                       if(fields[6].split(":")[1].equals("yes")) hasHistory = true;
                       if(fields[7].split(":")[1].equals("yes")) hasStatistics = true;
                       
                       UpowerData = new UpowerData(fields[0].split(":")[1],
                                                   fields[1].split(":")[1],
                                                   fields[2].split(":")[1],
                                                   fields[3].split(":")[1],
                                                   fields[4].split(":")[1],
                                                   fields[5].split(":")[1],
                                                   hasHistory,
                                                   hasStatistics);
                        parseUpowerObject();
                   }else{
                       
                       System.out.println(fields[9]);
                       
                       if(fields[9].split(":")[1].equals("yes")) present = true;
                       if(fields[10].split(":")[1].equals("yes")) rechargable = true;
                                                                                           
                       UpowerData.setIsPressent(present);
                       UpowerData.setIsRechargeable(rechargable);
                       UpowerData.setState(fields[11].split(":")[1]);
                       UpowerData.setWarninglevel(fields[12].split(":")[1]);
                       UpowerData.setEnergy(fields[13].split(":")[1]);
                       UpowerData.setEnergyEmpty(fields[14].split(":")[1]);
                       UpowerData.setEnergyFull(fields[15].split(":")[1]);
                       UpowerData.setEnergyFullDesign(fields[16].split(":")[1]);
                       UpowerData.setEnergyRate(fields[17].split(":")[1]);
                       UpowerData.setVoltage(fields[18].split(":")[1]);
                       
                       if(fields.length == 24){
                            if(fields[11].split(":")[1].equals("discharging")){
                                timeToEmpty = (int) Float.parseFloat(fields[19].split(":")[1].replace("minutes", "").replace(",", "."));
                            }else{
                                timeToFull = (int) Float.parseFloat(fields[19].split(":")[1].replace("minutes", "").replace(",", "."));
                            }
                            percentage = Integer.parseInt(fields[20].split(":")[1].replace("%", ""));
                            capacity = Integer.parseInt(fields[21].split(":")[1].replace("%", ""));
                            technology = fields[22].split(":")[1];
                       }else{
                            percentage = Integer.parseInt(fields[19].split(":")[1].replace("%", ""));
                            capacity = Integer.parseInt(fields[20].split(":")[1].replace("%", ""));
                            technology = fields[21].split(":")[1];
                       }                        
                       
                       UpowerData.setPercentage(percentage);
                       UpowerData.setCapacity(capacity);
                       UpowerData.setTimeToEmpty(timeToEmpty);
                       UpowerData.setTimeToFull(timeToFull);
                       UpowerData.setTechnology(technology);
                       
                       System.out.println(UpowerData.toString());                       
                   }
               }
           } 
        }catch(NumberFormatException e){
            System.err.println("ERROR has ocurred in Jupower.parseUpowerObject");           
        }finally{
            upowerData = null;
            fields = null;
        }
    }

    @Override
    public String getVendor() {
        return UpowerData.getVendor();
    }

    @Override
    public Date getUpdateData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isPresent() {
        return UpowerData.isIsPressent();
    }

    @Override
    public boolean isRechargable() {
        return UpowerData.isIsRechargeable();
    }

    @Override
    public int getPercetage() {
        return UpowerData.getPercentage();
    }

    @Override
    public int getCapacity() {
        return UpowerData.getCapacity();
    }

    @Override
    public String getState() {
        return UpowerData.getState();
    }

    @Override
    public float timeToEmpty() {
        return UpowerData.getTimeToEmpty();
    }

    @Override
    public float timeToFull() {
        return UpowerData.getTimeToFull();
    }
    
    /**
     * This method is used to check the health of battery and show information
     * about the percentage of life od the device, in case the battery capacity is less of
     * 50% then show the notification, if  the capacity is  more 50 and less 70 
     * then show a warning notification, if the capacity is more that 90% then 
     * show a ok notificaction
     * 
     */
    
    @Override
    public void check_battery_health() {
        try{
            if(UpowerData != null){
                if(UpowerData.getCapacity() < 50){                    
                    Notification.showConfirm("JBatery CHECK_MODE "+JBUtil.getInstance().getVersion(),
                                             "IMPORTANT the capacity of your battery is very slow and must be changed\n"
                                           + "capacity is : "+UpowerData.getCapacity()+" %", 
                                             Notification.NICON_DARK_THEME,
                                             Notification.ERROR_MESSAGE,60);
                }
                if(UpowerData.getCapacity()>50 && UpowerData.getCapacity() < 70){
                    Notification.showConfirm("JBatery CHECK_MODE "+JBUtil.getInstance().getVersion(),
                                             "IMPORTANT the capacity of your battery is "+UpowerData.getCapacity()+"% \n"
                                           + "your battery has started to deteriorate",
                                             Notification.NICON_DARK_THEME,
                                             Notification.WARNING_MESSAGE,60);
                }
                if(UpowerData.getCapacity() > 70){
                   Notification.showConfirm("JBatery CHECK_MODE "+JBUtil.getInstance().getVersion(),
                                            "the capacity of your battery is : "+UpowerData.getCapacity()+"%\n"
                                          + "your battery is VERY GOOD of healt !!",
                                            Notification.NICON_DARK_THEME,
                                            Notification.OK_MESSAGE,60);
                }
            }else{
                queryBatteryData();
                check_battery_health();
            }
        }catch(Exception e){
            System.err.println("A ERROR Has ocurred  in Jupower.check_battery_health()\n"+e);
        }
    }
    
}
