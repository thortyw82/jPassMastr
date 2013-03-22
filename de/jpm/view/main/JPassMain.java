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

import de.jpm.controller.EntryDBController;

import de.jpm.view.table.JPassTableModel;
import de.jpm.view.table.TablePane;
import de.jpm.controller.EntryTableController;
import de.jpm.controller.MainController;
import de.jpm.model.EntryDB;
import de.jpm.view.table.EntryTable;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 *
 * @author thorty.w
 * 
 * 
 */
public class JPassMain extends JFrame {

    //
    MainController maincontroller = MainController.getInstance();
    
    //initiate here to give it to the toolbar and other things like dialogs and so on.    
    JPassTableModel tablemodel;
    EntryTable entrytable;
    EntryTableController tablecontroller;        
    TablePane tableview;    
    
    JPassMenuBar menubar;
    JPassToolBar toolbar;    
    
    StatusLabel statuslabel;
    JPanel southPanel;
        
    
    public  JPassMain() {
        initMain();
    }

    
    private void initMain()  {
        
        //initiate the entryDB
        //ToDo just initiate the controller how wants to get the db
        EntryDB entrydb = MainController.entrydb;

        
        menubar = new JPassMenuBar();
        this.setJMenuBar(menubar);

        //EntryController
        EntryDBController entrycontroller = new EntryDBController();
        
        
        //create tht jtable with an own JTableModel 
        tablemodel = new JPassTableModel();
        entrytable = new EntryTable(tablemodel);
                
        //give the entrydb, the Model and the jtable to the controller to draw the table                
        tablecontroller = new EntryTableController(tablemodel, entrytable);
        tablecontroller.readEntryDB(); //read the complete DB
                
        
        //Initiate the MainController
        maincontroller.setEntryTableController(tablecontroller);
        maincontroller.setEntryController(entrycontroller);
        maincontroller.setMainFrame(this);                    
        
        //create own jScrollPane(TablePane) and add this to the view        
        tableview = new TablePane(tablemodel, entrytable, tablecontroller);        
        this.add(tableview);
        
        toolbar = new JPassToolBar(tablecontroller, entrytable);
        this.add(toolbar, BorderLayout.NORTH);
        
        statuslabel = new StatusLabel();                
        this.add(statuslabel, BorderLayout.SOUTH);
        
        
        this.setVisible(true);
        this.setTitle("jPassMaster");
        this.setSize(600,400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); 
        
        startDialog();
        
    }

    private void startDialog() {        
        
        if (MainController.getDatabases().length > 1 || MainController.getDropBoxDatabases() != null ){           
           StartDialog start = new StartDialog(this, true);       
           start.setLocationRelativeTo(this);
           start.setVisible(true);  
        } else {
           MainController.entrydb = new EntryDB();
           MainController.entryTableController.removeAll();
           NewDatabase newdb = new NewDatabase(this, true);                   
           newdb.setLocationRelativeTo(this);
           newdb.setVisible(true);              
        }
    }

    public StatusLabel getStatuslabel() {
        return statuslabel;
    }

    
    
    
}
