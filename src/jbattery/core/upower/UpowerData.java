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

package jbattery.core.upower;

/**
 *
 * @author frederick
 */
public class UpowerData {
    
    private String nativePath;
    private String vendor;
    private String model;
    private String serial;
    private String powerSupply;
    private String updated;
    private boolean hasHistory;
    private boolean hasStatistics;
    
    private boolean isPressent;
    private boolean isRechargeable;
    private String state;
    private String warninglevel;
    private String energy;
    private String energyEmpty;
    private String energyFull;
    private String energyFullDesign;
    private String energyRate;
    private String voltage;
    private int timeToFull;
    private int timeToEmpty;
    private int percentage;
    private int capacity;
    private String technology;
    
    public UpowerData(){
        
    }
    
    public UpowerData(String path,String vendor,String model,String serial,
                      String powerSupply,String updated, boolean hasHistory,
                      boolean hasStatistics){
        
        this.nativePath = path;
        this.vendor = vendor;
        this.model = model;
        this.serial = serial;
        this.powerSupply = powerSupply;
        this.updated = updated;
        this.hasHistory = hasHistory;
        this.hasStatistics = hasStatistics;
        
    }

    public String getNativePath() {
        return nativePath;
    }

    public void setNativePath(String nativePath) {
        this.nativePath = nativePath;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getPowerSupply() {
        return powerSupply;
    }

    public void setPowerSupply(String powerSupply) {
        this.powerSupply = powerSupply;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public boolean isHasHistory() {
        return hasHistory;
    }

    public void setHasHistory(boolean hasHistory) {
        this.hasHistory = hasHistory;
    }

    public boolean isHasStatistics() {
        return hasStatistics;
    }

    public void setHasStatistics(boolean hasStatistics) {
        this.hasStatistics = hasStatistics;
    }

    public boolean isIsPressent() {
        return isPressent;
    }

    public void setIsPressent(boolean isPressent) {
        this.isPressent = isPressent;
    }

    public boolean isIsRechargeable() {
        return isRechargeable;
    }

    public void setIsRechargeable(boolean isRechargeable) {
        this.isRechargeable = isRechargeable;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWarninglevel() {
        return warninglevel;
    }

    public void setWarninglevel(String warninglevel) {
        this.warninglevel = warninglevel;
    }

    public String getEnergy() {
        return energy;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
    }

    public String getEnergyEmpty() {
        return energyEmpty;
    }

    public void setEnergyEmpty(String energyEmpty) {
        this.energyEmpty = energyEmpty;
    }

    public String getEnergyFull() {
        return energyFull;
    }

    public void setEnergyFull(String energyFull) {
        this.energyFull = energyFull;
    }

    public String getEnergyFullDesign() {
        return energyFullDesign;
    }

    public void setEnergyFullDesign(String energyFullDesign) {
        this.energyFullDesign = energyFullDesign;
    }

    public String getEnergyRate() {
        return energyRate;
    }

    public void setEnergyRate(String energyRate) {
        this.energyRate = energyRate;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public int getTimeToFull() {
        return timeToFull;
    }

    public void setTimeToFull(int timeToFull) {
        this.timeToFull = timeToFull;
    }

    public int getTimeToEmpty() {
        return timeToEmpty;
    }

    public void setTimeToEmpty(int timeToEmpty) {
        this.timeToEmpty = timeToEmpty;
    }
    
    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    @Override
    public String toString() {
        return "UpowerData{" + "nativePath=" + nativePath + ", vendor=" + vendor + ", model=" + model + ", serial=" + serial + ", powerSupply=" + powerSupply + ", updated=" + updated + ", hasHistory=" + hasHistory + ", hasStatistics=" + hasStatistics + ", isPressent=" + isPressent + ", isRechargeable=" + isRechargeable + ", state=" + state + ", warninglevel=" + warninglevel + ", energy=" + energy + ", energyEmpty=" + energyEmpty + ", energyFull=" + energyFull + ", energyFullDesign=" + energyFullDesign + ", energyRate=" + energyRate + ", voltage=" + voltage + ", timeToFull=" + timeToFull + ", timeToEmpty=" + timeToEmpty + ", percentage=" + percentage + ", capacity=" + capacity + ", technology=" + technology + '}';
    }
}
