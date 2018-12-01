/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbattery.core.util;

/**
 *
 * @author frederick
 */
public class JBUtil {
    
    private final String nameApp;
    private final String version;
    private final String autor;
    private final String build;
    
    public static JBUtil instance;
    
    private JBUtil(){
        this.autor = "Frederick Salazar";
        this.nameApp = "JBattery";
        this.version = "2.2";
        this.build = "20181130";
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
    
    public String getNameAppVersionBuild(){
        return this.nameApp+" Version "+this.version+" BUILD "+this.build;
    }
    public static JBUtil getInstance(){
        if(instance == null){
            instance = new JBUtil();
        }
        
        return instance;
    }
    
}
