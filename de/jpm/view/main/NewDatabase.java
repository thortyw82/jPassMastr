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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author thorty
 */
public class NewDatabase extends JDialog implements ActionListener  {

    JLabel l_pass, l_name;
    JPasswordField t_pass;
    JTextField t_name;
    JButton b_ok, b_cancel;
    JPanel buttonpanel;
    JTextField nachricht;

    /**
     *
     */
    public NewDatabase(){
        initGetDatabasePass();
    }

    /**
     *
     * @param aThis
     * @param b
     */
    public NewDatabase(Frame aThis, boolean b) {
        super(aThis, b);
        initGetDatabasePass();
    }
    
    


    private void initGetDatabasePass() {
        //Create Panel with GridbagLayout 
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new LineBorder(Color.GRAY));
       
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        
        l_name = new JLabel(MainController.bundle.getString("database"));
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(l_name, cs);        
        
        t_name = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 3;
        t_name.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        panel.add(t_name, cs);        
        
        l_pass = new JLabel(MainController.bundle.getString("password"));
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(l_pass, cs);
        
        t_pass = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 3;
        t_pass.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        panel.add(t_pass, cs);
        
        nachricht = new JTextField(" ");
        cs.gridx =0;
        cs.gridy = 2;
        cs.gridwidth = 4;
        //nachricht.setBackground(Color.lightGray);
        nachricht.setOpaque(false);
        nachricht.setBorder(BorderFactory.createEmptyBorder());
        panel.add(nachricht, cs);
        //Buttonpanel and Buttons
        buttonpanel = new JPanel();
        b_ok = new JButton("Ok");
        //b_cancel = new JButton("Cancel");                
        buttonpanel.add(b_ok);
        //buttonpanel.add(b_cancel);
        
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonpanel, BorderLayout.PAGE_END);
       
        this.getRootPane().setDefaultButton(b_ok);
       
        //ButtonAction Listner                
        //b_cancel.addActionListener(this);
        b_ok.addActionListener(this);
        
        
        //properties of the JDialog                
        pack();
        setResizable(false);        
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b_ok){
            if ( t_pass.getPassword().length != 0 && !t_name.getText().isEmpty() ){
                try {
                    MainController.getEntryController().setPass(t_pass.getPassword());
                    MainController.getEntryController().setName(t_name.getText());
                    dispose();
                } catch (JPMException ex) {
                    JOptionPane pane = new JOptionPane(ex.getUsermessage(), JOptionPane.ERROR_MESSAGE);
                    JDialog dialog = pane.createDialog(this.getParent(), "Warning");
                    dialog.setVisible(true);
                }
            
            } else {
                 nachricht.setText(MainController.bundle.getString("NewDatabase.message"));
                 nachricht.setForeground(Color.red);
            
            }
                
        } 
        
    }    
}
