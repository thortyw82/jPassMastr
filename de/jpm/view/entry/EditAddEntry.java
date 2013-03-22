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
package de.jpm.view.entry;

import de.jpm.controller.EntryTableController;
import de.jpm.controller.MainController;
import de.jpm.model.Entry;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author thorty.w
 */
public class EditAddEntry extends JDialog implements ActionListener {

    //BusinessObjects
    //EntryDB entrydb = null;
    EntryTableController tablecontrol = null;
    boolean edit = false;
    int entryToEdit = 0;
    
    
    JLabel l_name, l_user, l_pass, l_notes;
    JTextField t_name, t_user;
    JTextArea t_notes;
    JButton b_ok, b_cancel, b_unmaskPass;
    JPanel buttonpanel;
    JPasswordField t_pass;
    
//    public EditAddEntry(){
//        initEditAdd();
//    }

    /**
     *
     * @param etc
     */
    public EditAddEntry(EntryTableController etc) {
        //this.entrydb = entrydb;
        this.tablecontrol = etc;
        initEditAdd();
    }

    /**
     *
     * @param etc
     * @param row
     */
    public EditAddEntry(EntryTableController etc, int row) {
        this.entryToEdit = row;        
       // this.entrydb = entrydb;
        this.tablecontrol = etc;
        this.edit = true;
        initEditAdd(row);
    }    
    
private void initEditAdd(int row) {
        
        //Create Panel with GridbagLayout 
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new LineBorder(Color.GRAY));
       
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        
        Entry entry = MainController.entrydb.getEntrys().get(row);
         
        l_name = new JLabel(MainController.bundle.getString("EditAddEndtry.name"));
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(l_name, cs);
        
        
        t_name = new JTextField(entry.getName(), 20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 3;
        t_name.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        panel.add(t_name, cs);
                
        l_user = new JLabel(MainController.bundle.getString("EditAddEndtry.user"));
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(l_user, cs);
        
        t_user = new JTextField(entry.getUser(), 20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 3;   
        t_user.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        panel.add(t_user, cs);
        
        l_pass = new JLabel(MainController.bundle.getString("EditAddEndtry.pass"));
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(l_pass, cs);
        
        t_pass = new JPasswordField(entry.getPass(), 20);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 3;
        t_pass.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        panel.add(t_pass, cs);
        
        b_unmaskPass = new JButton(MainController.bundle.getString("EditAddEndtry.button.show"));
        cs.gridx = 1;
        cs.gridy = 3;
        cs.gridwidth =1;
        panel.add(b_unmaskPass, cs);
        
        l_notes = new JLabel(MainController.bundle.getString("EditAddEndtry.notes"));
        cs.gridx = 0;
        cs.gridy = 4;
        cs.gridwidth = 1;
        panel.add(l_notes, cs);
        
        t_notes = new JTextArea(entry.getNotes(), 5,10);
        cs.gridx = 1;
        cs.gridy = 4;
        cs.gridwidth = 5;
        cs.gridheight = 10;
        t_notes.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        panel.add(t_notes, cs);
        
       
        
        //Buttonpanel and Buttons
        buttonpanel = new JPanel();
        b_ok = new JButton("Ok");
        b_cancel = new JButton(MainController.bundle.getString("cancel"));                
        buttonpanel.add(b_ok);
        buttonpanel.add(b_cancel);
        
        this.getRootPane().setDefaultButton(b_ok);
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonpanel, BorderLayout.PAGE_END);
       
       
        //ButtonAction Listner                
        b_unmaskPass.addActionListener(this);
        b_cancel.addActionListener(this);
        b_ok.addActionListener(this);
        
        
        //properties of the JDialog                
        pack();
        setResizable(false);

                
    }    
    
    
    private void initEditAdd() {
        
        //Create Panel with GridbagLayout 
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new LineBorder(Color.GRAY));
       
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        
        
         
        l_name = new JLabel(MainController.bundle.getString("EditAddEndtry.name"));
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(l_name, cs);
        
        
        t_name = new JTextField("", 20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 3;
        t_name.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        panel.add(t_name, cs);
                
       l_user = new JLabel(MainController.bundle.getString("EditAddEndtry.user"));
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(l_user, cs);
        
        t_user = new JTextField("", 20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 3;   
        t_user.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        panel.add(t_user, cs);
        
        l_pass = new JLabel(MainController.bundle.getString("EditAddEndtry.pass"));
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(l_pass, cs);
        
        t_pass = new JPasswordField("", 20);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 3;
        t_pass.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        panel.add(t_pass, cs);
        
       b_unmaskPass = new JButton(MainController.bundle.getString("EditAddEndtry.button.show"));
        cs.gridx = 1;
        cs.gridy = 3;
        cs.gridwidth =1;
        panel.add(b_unmaskPass, cs);
        
        l_notes = new JLabel(MainController.bundle.getString("EditAddEndtry.notes"));
        cs.gridx = 0;
        cs.gridy = 4;
        cs.gridwidth = 1;
        panel.add(l_notes, cs);
        
        t_notes = new JTextArea(5,10);
        cs.gridx = 1;
        cs.gridy = 4;
        cs.gridwidth = 5;
        cs.gridheight = 10;
        t_notes.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        panel.add(t_notes, cs);
        
       
        
        //Buttonpanel and Buttons
        buttonpanel = new JPanel();
        b_ok = new JButton("Ok");
        b_cancel = new JButton(MainController.bundle.getString("cancel"));                
        buttonpanel.add(b_ok);
        buttonpanel.add(b_cancel);
        
        this.getRootPane().setDefaultButton(b_ok);
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonpanel, BorderLayout.PAGE_END);
        
       
        //ButtonAction Listner                
        b_unmaskPass.addActionListener(this);
        b_cancel.addActionListener(this);
        b_ok.addActionListener(this);
        
        
        //properties of the JDialog                
        pack();
        setResizable(false);
           
    }
   
   @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b_unmaskPass){
            t_pass.setEchoChar((char)0);                         
        }
        if (e.getSource() == b_ok){
           if (!edit){
           Entry newEntry = new Entry(t_name.getText(),  t_user.getText(),new String(t_pass.getPassword()), t_notes.getText()); 
           MainController.entrydb.setEntry(newEntry);        
           tablecontrol.addTableEntry();
           dispose();
           }
           else{
           Entry newEntry = new Entry(t_name.getText(),  t_user.getText(),new String(t_pass.getPassword()), t_notes.getText()); 
           MainController.entrydb.updateEntry(newEntry, entryToEdit);     
           tablecontrol.updateTable();           
           dispose();
           }
    
        }
        if (e.getSource() == b_cancel){
           dispose();                         
        }        
    }


    
}
