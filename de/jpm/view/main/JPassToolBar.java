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
import de.jpm.controller.EntryTableController;
import de.jpm.controller.MainController;
import de.jpm.view.entry.EditAddEntry;
import java.awt.Dialog.ModalityType;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

/**
 *
 * @author thorty.w
 */
public class JPassToolBar extends JToolBar implements ActionListener {

    //Buttons on toolbar
    //ToDo: Miss ##syncDB##
    JButton b_newEntry, b_editEntry,b_deleteEntry, b_copyUser, b_copyPass, b_opendb, b_savedb;
    ImageIcon i_newEntry, i_editEntry, i_copyUser, i_copyPass, i_deleteEntry, i_opendb, i_savedb;
    
    //EntryDB entrydb = null;
    private EntryTableController etc =null;
    JTable entrytable;
    
    
    JPassToolBar( EntryTableController etc) {        
        this.etc = etc;
        initToolBar();
    }

    JPassToolBar(EntryTableController etc, JTable entrytable) {        
        this.etc = etc;
        this.entrytable = entrytable;
        initToolBar();
    }    
    
    private void initToolBar() {
       
        //Toolbar        
        this.setFloatable(false); //nicht verschiebbar
        //this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        
        //Initial icons
        i_newEntry = new ImageIcon(getClass().getResource("/de/jpm/graphics/Create.png"));
        i_editEntry = new ImageIcon(getClass().getResource("/de/jpm/graphics/Edit.png"));
        i_copyUser = new ImageIcon(getClass().getResource("/de/jpm/graphics/User.png"));
        i_copyPass = new ImageIcon(getClass().getResource("/de/jpm/graphics/Properties.png"));
        i_deleteEntry = new ImageIcon(getClass().getResource("/de/jpm/graphics/Delete.png"));
        i_opendb = new ImageIcon(getClass().getResource("/de/jpm/graphics/open.png"));
        i_savedb = new ImageIcon(getClass().getResource("/de/jpm/graphics/save.png"));
        
        //Initial Buttons
        b_newEntry = new JButton( i_newEntry);
        b_newEntry.setToolTipText(MainController.bundle.getString("JPassToolBar.newentry"));
        b_newEntry.addActionListener(this);
        b_editEntry = new JButton( i_editEntry);
        b_editEntry.setToolTipText(MainController.bundle.getString("JPassToolBar.editentry"));
        b_editEntry.addActionListener(this);
        b_deleteEntry = new JButton( i_deleteEntry);
        b_deleteEntry.setToolTipText(MainController.bundle.getString("JPassToolBar.deleteentry"));
        b_deleteEntry.addActionListener(this);
        b_copyUser = new JButton( i_copyUser);
        b_copyUser.setToolTipText(MainController.bundle.getString("JPassToolBar.copyuser"));
        b_copyUser.addActionListener(this);
        b_copyPass = new JButton( i_copyPass);
        b_copyPass.setToolTipText(MainController.bundle.getString("JPassToolBar.copypass"));
        b_copyPass.addActionListener(this);
        b_opendb = new JButton(i_opendb);
        b_opendb.setToolTipText(MainController.bundle.getString("JPassToolBar.open"));
        b_opendb.addActionListener(this);
        b_savedb = new JButton(i_savedb);
        b_savedb.setToolTipText(MainController.bundle.getString("JPassToolBar.save"));
        b_savedb.addActionListener(this);
        
        
        this.add(b_opendb);
        this.add(b_savedb);        
        this.add(b_newEntry);
        this.add(b_editEntry);
        this.add(b_deleteEntry);
        this.add(b_copyUser);
        this.add(b_copyPass);
        
        
        
        
    }    
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b_newEntry){
           
            EditAddEntry editAddEntry = new EditAddEntry( etc);
           
            editAddEntry.setVisible(true);
            editAddEntry.setLocationRelativeTo(this);
            //set Dialog as Modal
            editAddEntry.setModalityType(ModalityType.APPLICATION_MODAL);
        }
        if (e.getSource() == b_editEntry){
            int row = entrytable.getSelectedRow();
            EditAddEntry editAddEntry = new EditAddEntry(etc, row);            
            editAddEntry.setVisible(true);
            editAddEntry.setLocationRelativeTo(this);
            //set Dialog as Modal
            editAddEntry.setModalityType(ModalityType.APPLICATION_MODAL);            
        }        
        if (e.getSource() == b_deleteEntry){
            int row = entrytable.getSelectedRow();
            //deletw from entrydb
            MainController.entrydb.deleteEntry(row);
            //delete from tablemodel and refresh the view
            etc.removeEntry(row);
            
        }                
        if (e.getSource() == b_copyPass){
            int row = entrytable.getSelectedRow();
            String pass = MainController.entrydb.getEntrys().get(row).getPass();
            
            StringSelection stringSelection = new StringSelection( pass );
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, stringSelection);
            ((JPassMain)MainController.mainframe).getStatuslabel().setText(MainController.bundle.getString("Status.ClipboardPass"));
        }                        
        if (e.getSource() == b_copyUser){
            int row = entrytable.getSelectedRow();
            String pass = MainController.entrydb.getEntrys().get(row).getUser();
            
            StringSelection stringSelection = new StringSelection( pass );
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, stringSelection);
            ((JPassMain)MainController.mainframe).getStatuslabel().setText(MainController.bundle.getString("Status.ClipboardUser"));
        }                           
        if (e.getSource() == b_opendb){
           //MainController.getEntryController().openDB();    
           StartDialog start = new StartDialog(MainController.mainframe, true);       
           start.setLocationRelativeTo(this);
           start.setVisible(true);              
        }                                   
        if (e.getSource() == b_savedb){
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
