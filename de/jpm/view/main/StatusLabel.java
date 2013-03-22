/*
 * jPassMaster
 * 
 * A free tool to Manage your Accounts.
 * And Sync them with your dropbox account. 
 * The Software is made in hope to be usefull to you as it is for me.
 * 
 * This class is a part of jPass Master. 
 * EMail: jpassmaster@thorstenweiskopf.de
 * 
 * 
 *   Copyright (C) 2012  Thortsten Weiskopf
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package de.jpm.view.main;

import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 *
 * @author thorty.w
 */
public class StatusLabel extends JLabel {

    /**
     *
     */
    public StatusLabel() {
        init();
    }

    private void init() {
        //this.setBackground(Color.BLACK);
        //this.setOpaque(true);
        //this.setBorder(BorderFactory.createEtchedBorder(Color.white, Color.black));
        this.setBorder(BorderFactory.createEmptyBorder());
        //this.setForeground(Color.white);
        this.setFont(new Font("Arial", Font.BOLD, 12));
        this.setText("Status");        
        this.setVisible(true);
    }
}