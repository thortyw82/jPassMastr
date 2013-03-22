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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 *
 * @author thorty
 */
public class StartDialog extends JDialog implements ActionListener {

    JLabel l_choosedb, l_pass, L_remotes;
    JPasswordField t_pass;
    JComboBox cb_choosedb, cb_chooseremotes;
    JButton b_open, b_newdb;
    JPanel buttonpanel;
    JTextField nachricht;
    
    
    /**
     *
     */
    public StartDialog() {
        initStartDialog();
    }
    
    /**
     *
     * @param c
     * @param modal
     */
    public StartDialog(Frame c, boolean modal) {
        super(c, modal);
        initStartDialog();
    }    
    
    private void initStartDialog() {
        //Create Panel with GridbagLayout 
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new LineBorder(Color.GRAY));       
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        
        String[] localdbs = MainController.getDatabases();
        
            l_choosedb = new JLabel(MainController.bundle.getString("StartDialog.choosedb"));
            cs.gridx = 0;
            cs.gridy = 0;
            cs.gridwidth = 1;
            panel.add(l_choosedb, cs);  

            cb_choosedb = new JComboBox(MainController.getDatabases());        
            //cb_choosedb.setSelectedIndex(MainController.getDatabases().length);
            cb_choosedb.setEnabled(false);
            cb_choosedb.addActionListener(this);
            cs.gridx = 1;
            cs.gridy = 0;
            cs.gridwidth = 15;
            panel.add(cb_choosedb, cs);
            if (localdbs != null && localdbs.length > 0){
                cb_choosedb.setEnabled(true);
                cb_choosedb.setSelectedItem(" ");
            }
        
        String[] remotes = MainController.getDropBoxDatabases();
        
            L_remotes = new JLabel(MainController.bundle.getString("StartDialog.chooseRemote"));
            cs.gridx = 0;
            cs.gridy = 1;
            cs.gridwidth = 1;
            panel.add(L_remotes, cs);  
        
            if (MainController.configdata.isSynced() && remotes != null){
              cb_chooseremotes = new JComboBox(remotes);                
              cb_chooseremotes.setEnabled(true);
              cb_chooseremotes.setSelectedItem(" ");
            } else {
              Vector ve = new Vector();  
              ve.add(" ");
              cb_chooseremotes = new JComboBox(ve);
              cb_chooseremotes.setEnabled(false);
            }        
            //cb_choosedb.setSelectedIndex(MainController.getDatabases().length);
            
            cb_choosedb.addActionListener(this);
            cs.gridx = 1;
            cs.gridy = 1;
            cs.gridwidth = 15;
            panel.add(cb_chooseremotes, cs);        

            
        
        
        
        l_pass = new JLabel(MainController.bundle.getString("password"));
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth =1;
        panel.add(l_pass, cs);        
        
        t_pass = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 3;
        panel.add(t_pass, cs);
        
        
        nachricht = new JTextField(30);
        cs.gridx =0;
        cs.gridy = 3;
        cs.gridwidth = 4;
        cs.gridheight =5;
        //nachricht.setBackground(Color.lightGray);
        nachricht.setOpaque(false);
        nachricht.setBorder(BorderFactory.createEmptyBorder());
        panel.add(nachricht, cs);
        //Buttonpanel and Buttons
        buttonpanel = new JPanel();
        b_open = new JButton(MainController.bundle.getString("StartDialog.open"));
        b_newdb = new JButton(MainController.bundle.getString("new"));
        b_open.addActionListener(this);        
        b_newdb.addActionListener(this);
        buttonpanel.add(b_open);
        buttonpanel.add(b_newdb);  
        
        this.getRootPane().setDefaultButton(b_open);
        
        
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonpanel, BorderLayout.PAGE_END);        
        
        //properties of the JDialog                
        pack();
        setResizable(false);  
    }   
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b_open ){
            if (t_pass.getPassword().length != 0 && (!cb_choosedb.getSelectedItem().equals(" ") || !cb_chooseremotes.getSelectedItem().equals(" ") ) ) {
                try {
                    MainController.getEntryController().setPass(t_pass.getPassword());
                    if (!cb_choosedb.getSelectedItem().equals(" ")){
                        MainController.getEntryController().setName((String)cb_choosedb.getSelectedItem());
                        MainController.getEntryController().loadDBFile(t_pass.getPassword());
                    }
                    if (!cb_chooseremotes.getSelectedItem().equals(" ")){
                        MainController.getEntryController().setName((String)cb_chooseremotes.getSelectedItem());
                        MainController.getEntryController().downloadDBfromDropBox(t_pass.getPassword());
                    }
                    
                    
                    ((JPassMain)MainController.mainframe).getStatuslabel().setText(MainController.bundle.getString("Status.open"));
                    dispose();
                } catch (JPMException ex) {
                   //show the exception as a popup
//                    JOptionPane pane = new JOptionPane(ex.getUsermessage(), JOptionPane.ERROR_MESSAGE);
//                    JDialog dialog = pane.createDialog(this.getParent(), "Warning");
//                    dialog.setVisible(true);
                  //show as a text in the text area
                 nachricht.setText(ex.getUsermessage());
                 nachricht.setForeground(Color.red); 
                }
            } else {
                 nachricht.setText(MainController.bundle.getString("StartDialog.message"));
                 nachricht.setForeground(Color.red);          
            }
        }
        if (e.getSource() == b_newdb){
            dispose();
            MainController.getEntryController().newDB();
        }
    
}

}