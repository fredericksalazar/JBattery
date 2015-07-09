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
import jbattery.core.Init;

/**
 *
 * @author Frederick Adolfo Salazar Sanchez
 * @version 1.5
 */

public class JBattery {

    public static void main(String[] args) {
        try {
            System.out.println("Starting JBattery 1.8 ...");
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
            Init in = new Init();
            in.init();
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(JBattery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
