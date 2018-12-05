/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbattery.core.util;

/**
 * This class define the main methods that are used as tools in Jbattery
 * Project
 * 
 * @author frederick
 * 
 */

public class JBUtil {
    
    private final String nameApp;
    private final String version;
    private final String autor;
    private final String build;
    
    private String OS;
    
    public static JBUtil instance;
    
    private JBUtil(){
        this.autor = "Frederick Salazar";
        this.nameApp = "JBattery";
        this.version = "2.8";
        this.build = "20181204";
    }

    public String getVersion() {
        return version;
    }

    public String getAutor() {
        return autor;
    }

    public String getBuild() {
        return build;
    }

    public String getNameApp() {
        return nameApp;
    }    
    
    
    /**
     * This method return the name and version information of Jbatter project
     * @return 
     */
    public String getNameAppVersion(){
        return this.nameApp+" "+this.version;
    }
    
    /**
     * this method return the name, version and build information
     * of the Jbattery project
     * 
     * @return String 
     */
    
    public String getNameAppVersionBuild(){
        return this.nameApp+" Version "+this.version+" BUILD "+this.build;
    }
    
    /**
     * Return de the operative System 
     * @return 
     */
    public String getOS(){
        if(OS == null || "".equals(OS)){
            OS = System.getProperty("os.name");
            System.out.println("The Operative System is : "+OS);
        }
        return OS;
    }
    
    /**
     * This Method verify the operative system of execution, in this moment
     * only supported Linux OS
     * @return 
     */
    
    public boolean verifyOperativeSystem(){
        boolean isSupported = true;        
           if(!getOS().equals("Linux")){
               isSupported = false;
           } 
        
        return isSupported;        
    }
    
    /**
     * Create a instance of JBUtil object in the memory
     * @return 
     */
    public static JBUtil getInstance(){
        if(instance == null){
            instance = new JBUtil();
        }
        
        return instance;
    }
    
}
