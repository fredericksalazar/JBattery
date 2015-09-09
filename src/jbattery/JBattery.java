
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

package jbattery;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import jbattery.core.Jacpi;
import nicon.notify.core.Notification;

import jbattery.core.Init;

/**
 * JBattery es una herramienta simple para sistemas GNU/Linux que permite contro
 * lar los eventos de energía en la gestion de las baterías, JBattery tiene como
 * objetivo principal informar al usuario de los diferentes eventos que ocurren
 * al usar la batería e informa de los diferentes niveles de carga para ayudar
 * a gestionar de una forma más eficiente y extender la vida útil de la batería
 * de una laptop, JBattery usa la interfaz ACPI para obtener la informacion de la
 * bateria e implemetna Jacpi para analizar los datos
 * 
 * @author Frederick Adolfo Salazar Sanchez
 * @version 1.5
 */

public class JBattery {
    
    /**
     * Metodo principal de entrada que recibe una lista de parámetros que deter
     * minan que funciones ejecutar en este caso las opciones permitidas son:<br>
     * -s -S para iniciar JBattery en MONITOR_MODE<br>
     * -c -C para iniciar JBattery en CHK_STATUS_BAT y analizar la salud de la bateria<br>
     * -v -V para mostrar información de la versión y desarrollo
     * -h -H para mostrar información de ayuda via terminal
     * 
     * @param args 
     */
    public static void main(String[] args) {
        
        try {
            System.out.println("Starting JBattery 1.9 ...");
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
            
            
            /*
                Inicia JBattery en MONITOR_MODE que permite validar el nivel de
                carga de la batería e informar al usuario de los diferentes niveles
                de carga de la misma
            */
            
            if(args.length==0||args[0].equals("-s")||args[0].equals("-S")){
                Init MONITOR_MODE = new Init();
                MONITOR_MODE.init();
            }
            
            
            /*
                Inicia JBattery en CHEK_MODE y activa el módulo de chequeo para
                conocer el estado vital de la bateria donde reconoce tres modos
                bad, medium good
            */
            
            if(args[0].equals("-c")||args[0].equals("-C")) {
                try {
                    System.out.println("\nStarting a JBattery CHEKING_MODE ...\n");
                    Jacpi CHK_STATUS_BAT = new Jacpi();
                    CHK_STATUS_BAT.checkBattery();
                } catch (Exception e) {
                    System.err.println("JBatter: Error detail: "+e);
                }
            }
            
            //Muestra una notificacion con la version de JBattery y NiconNotifyOSD
            if(args[0].equals("-v")||args[0].equals("-V")){
                Notification.show("JBattery", "JBattery version: 1.9.5\n"
                        + "NiconNotifyOSD version: 1.9.8\n"
                        + "Developed by: NiconSystemCO 2015",
                        Notification.NICON_DARK_THEME, Notification.NICON_SYSTEM);
            }
            
            //Muestra por consola un manual de ayuda de JBattery
            if(args[0].equals("-h")){
                System.out.println("\n\n////////////////////////////////////////////////////////////////////////////////////\n\n" +
                        "Welcome to JBattery Manual\n\n" +
                        "JBattery is a java app for GNU/Linux that allows monitoring the different \n" +
                        "events battery of a device, report the different energy levels and evaluates\n" +
                        "the state of health of the battery of your device.\n" +
                        "\n" +
                        "Below is the list of parameters to use: \n\n" +
                        "-s -S - - - - - - - - - - start a JBattery app (UI)\n" +
                        "-c -C - - - - - - - - - - start CHEKING_MODE for evaluate the battery life\n"+
                        "-v -V - - - - - - - - - - show the version app in a desktop notification\n" +
                        "-h -H - - - - - - - - - - show a help information of JBattery\n\n"
                        + "JBattery is developed by: NiconSystemCO\n"
                        + "JBattery mainteined by: Frederick Salazar Sanchez <fredefass01@gmail.com>"
                        + "\n\n////////////////////////////////////////////////////////////////////////////////////\n\n");
            }
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(JBattery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
