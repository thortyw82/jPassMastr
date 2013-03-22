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
package de.jpm.view.info;

import de.jpm.controller.MainController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author thorty.w
 */
public class AboutjPassMaster extends JFrame {
    
    private Toolkit toolkit;
    
    public AboutjPassMaster(){
        
       setTitle("About");
       
       String text = MainController.bundle.getString("about");
       
       JPanel panel = new JPanel();
       panel.setLayout(new BorderLayout(10,10));
       
       
       JLabel label = new JLabel(text);
       label.setFont(new Font("Georgia", Font.PLAIN, 14));
       label.setForeground(new Color(50,50,25));
       
       panel.add(label, BorderLayout.CENTER);
       panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));       
       add(panel);
       pack();
       
      // setDefaultCloseOperation(EXIT_ON_CLOSE);
       
    }
    
    
}
