/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbattery.core;

import java.util.Date;

/**
 * This interfaces define the API of Battery query information
 * 
 * @author FrederickSalazar
 */

public interface BatteryInterface {
    
    /**
     * Ths method query data of battery
     */
    
    public void queryBatteryData();
    
    
    /**
     * Return the vendor of battery
     * @return String vendor
     */
    
    public String getVendor();
    
    
    /**
     * Return the last date of query data of Battery
     * @return Date 
     */
    
    public Date getUpdateData();
    
    
    /**
     * This method query if Battery is present in the laptop
     * @return boolean
     */
    
    public boolean isPresent();
    
    
    /**
     * This method query if the battery is rechargable
     * @return boolean
     */
    
    public boolean isRechargable();
    
    
    /**
     * This method return the percentage of charge of  the Battery
     * @return int
     */
    
    public int getPercetage();
    
    
    /**
     * This methd return the capacity of Battery
     * @return int capacity
     */
    public int getCapacity();
    
    
    /**
     * This method return the state description  of the Battery
     * @return String state
     */
    public String getState();
    
    
    /**
     * This method return time to empty
     * @return float
     */
    
    public float timeToEmpty();
    
    /**
     * This method return time to full
     * @return float
     */
    
    public float timeToFull();
    
    
    /**
     * This method check battery health 
     */
    
    public void check_battery_health();
}
