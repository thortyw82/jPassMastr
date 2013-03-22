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


import de.jpm.controller.JPMException;
import de.jpm.controller.MainController;
import de.jpm.view.info.AboutjPassMaster;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author thorty.w
 */
public class JPassMenuBar extends JMenuBar  implements ActionListener {
    
    private JMenu file, options, info; 
    private JMenuItem f_new, f_open, f_exit, i_about, i_help, o_options, f_savedb, f_uploaddb ; 
    
    
    public JPassMenuBar(){    
        initMenuBar();
    }

    private void initMenuBar() {
        
        //design
         this.setBorder(BorderFactory.createEmptyBorder());
        
        //File Menu
        //file = new JMenu("Database");
        file = new JMenu(MainController.bundle.getString("JPassMenuBar.file"));
        file.setMnemonic(KeyEvent.VK_F);        
        //File Menu Items
        f_new = new JMenuItem(MainController.bundle.getString("JPassMenuBar.file.new"));        
        f_new.addActionListener(this);
        f_open = new JMenuItem(MainController.bundle.getString("JPassMenuBar.file.open"));
        f_open.addActionListener(this);
        f_exit = new JMenuItem(MainController.bundle.getString("JPassMenuBar.file.exit"));
        f_exit.addActionListener(this);
        f_savedb = new JMenuItem(MainController.bundle.getString("JPassMenuBar.file.save"));
        f_savedb.addActionListener(this);
        f_uploaddb = new JMenuItem(MainController.bundle.getString("JPassMenuBar.file.upload"));
        f_uploaddb.addActionListener(this);
//        f_downloaddb = new JMenuItem(MainController.bundle.getString("JPassMenuBar.file.download"));
//        f_downloaddb.addActionListener(this);        
        
        file.add(f_new);
        file.add(f_open);
        file.add(f_savedb);
        file.add(f_uploaddb);
//        file.add(f_downloaddb);
        file.add(f_exit);
        
        
        //Options Menu
        options = new JMenu(MainController.bundle.getString("JPassMenuBar.options"));        
        options.setMnemonic(KeyEvent.VK_O);
        //Items
        o_options = new JMenuItem(MainController.bundle.getString("JPassMenuBar.option.dbsync"));
        o_options.addActionListener(this);
        options.add(o_options);
        if (MainController.configdata.isSynced()){
            o_options.setIcon(new ImageIcon(getClass().getResource("/de/jpm/graphics/Haken.png")));
        }
        //MainController.bundle.getString("JPassMenuBar.option.language")
        
        //Info Meni
        info = new JMenu(MainController.bundle.getString("JPassMenuBar.info"));        
        info.setMnemonic(KeyEvent.VK_I);
        //Items
        i_about = new JMenuItem(MainController.bundle.getString("JPassMenuBar.info.about"));        
        i_about.addActionListener(this);
        info.add(i_about);
        
        
        //Add the Menus
        this.add(file);
        this.add(options);
        this.add(info);                       
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == f_new ){
            MainController.getEntryController().newDB();            
        }
        if (e.getSource() == f_uploaddb ){
            try {
                boolean result = MainController.getEntryController().uploadDBToDropBox();
                if (result){
                    ((JPassMain)MainController.mainframe).getStatuslabel().setText(MainController.bundle.getString("Status.upload"));
                }
            } catch (JPMException ex) {
                    JOptionPane pane = new JOptionPane(ex.getUsermessage(), JOptionPane.ERROR_MESSAGE);
                    JDialog dialog = pane.createDialog(this.getParent(), "Warning");
                    dialog.setVisible(true);
            }
           
        }  
//        if (e.getSource() == f_downloaddb ){
////            try {
////                boolean result = MainController.getEntryController().downloadDBfromDropBox();
////                if (result){
////                    ((JPassMain)MainController.mainframe).getStatuslabel().setText(MainController.bundle.getString("Status.download"));
////                }
////            } catch (JPMException ex) {
////                    JOptionPane pane = new JOptionPane(ex.getUsermessage(), JOptionPane.ERROR_MESSAGE);
////                    JDialog dialog = pane.createDialog(this.getParent(), "Warning");
////                    dialog.setVisible(true);
////            }
//           StartDialog start = new StartDialog(MainController.mainframe, true);       
//           start.setLocationRelativeTo(this);
//           start.setVisible(true);  
//            
           
//        }              
        if (e.getSource() == f_open ){
//            MainController.getEntryController().openDB();
           StartDialog start = new StartDialog(MainController.mainframe, true);       
           start.setLocationRelativeTo(this);
           start.setVisible(true);  
            ((JPassMain)MainController.mainframe).getStatuslabel().setText(MainController.bundle.getString("Status.open"));


        }
        if (e.getSource() == f_exit ){
            MainController.configdata.saveConfigFile();
            //exit jPM
             System.exit(1);
        }
        if (e.getSource() == i_about ){

            AboutjPassMaster about = new AboutjPassMaster();
            about.setLocationRelativeTo(this);
            about.setVisible(true);
            
        }
        if (e.getSource() == o_options ){
//            OptionDialog opd = new OptionDialog();
//            opd.setLocationRelativeTo(this);
//            opd.setVisible(true);
            
            if ( MainController.boxutil == null){
                //todo jdialog fehler
            } else  {
                    HashMap keys = MainController.boxutil.firstAuthorisation();
                    if (keys != null){
                        //paste info in EntryDB
                        MainController.configdata.props.setProperty("drop1", (String)keys.get("key"));
                        MainController.configdata.props.setProperty("drop2", (String)keys.get("secret"));
                        MainController.configdata.setSynced(true);
                        MainController.configdata.saveConfigFile();
                        JOptionPane pane = new JOptionPane(MainController.bundle.getString("Status.AuthOk"),JOptionPane.INFORMATION_MESSAGE);
                        JDialog dialog = pane.createDialog(this.getParent(), "INFO");
                        dialog.setVisible(true);                          
                    }
                    else{
                    JOptionPane pane = new JOptionPane(MainController.bundle.getString("Status.AuthNotOk"), JOptionPane.ERROR_MESSAGE);
                    JDialog dialog = pane.createDialog(this.getParent(), "Warning");
                    dialog.setVisible(true);    
                    }
            }
            
            
        }        
        if (e.getSource() == f_savedb ){
            try {
                MainController.getEntryController().saveDB();
                ((JPassMain)MainController.mainframe).getStatuslabel().setText(MainController.bundle.getString("Status.save"));
            } catch (JPMException ex) {
                    JOptionPane pane = new JOptionPane(ex.getUsermessage(), JOptionPane.ERROR_MESSAGE);
                    JDialog dialog = pane.createDialog(this.getParent(), "Warning");
                    dialog.setVisible(true);
            }

        }                

    }
    
    
    
    
    
    
    
    
}
