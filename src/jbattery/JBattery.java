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
import nicon.notify.core.Notification;

import jbattery.core.Init;

/**
 *
 * @author Frederick Adolfo Salazar Sanchez
 * @version 1.5
 */

public class JBattery {

    public static void main(String[] args) {

        //Inicializa JBattery app
        if(args[0].equals("-s")||args[0].equals("-S")){
            try {
                System.out.println("Starting JBattery 1.8 ...");
                UIManager.setLookAndFeel(new NimbusLookAndFeel());
                Init in = new Init();
                in.init();
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(JBattery.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Muestra una notificacion con la version de JBattery y NiconNotifyOSD
        if(args[0].equals("-v")||args[0].equals("-V")){
            Notification.show(Notification.DISK_ICON,"JBattery information:","JBattery version: 1.9\n" +
                    "NiconNotifyOSD Version: 1.9.5");
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
                    "-s - - - - - - - - - - start a JBattery app (UI)\n" +
                    "-v - - - - - - - - - - show the version app in a desktop notification\n" +
                    "-h - - - - - - - - - - show a help information of JBattery\n\n\n");
        }
    }

}
