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
package de.jpm.controller;

import de.jpm.model.Entry;
import de.jpm.view.table.JPassTableModel;
import java.util.List;
import javax.swing.JTable;


/**
 * Controls Entrytable (JTable) and EntryTableModel
 * @author thorty.w
 */
public class EntryTableController {
    
    
    private JPassTableModel tablemodel;
    private JTable jtable;
   
    /*
     * @param entrydb the one and only entrydb
     * @param tablemodel the used tablemodel to display all entrys
     */
    /**
     *
     * @param tablemodel
     * @param jtable
     */
    public EntryTableController(JPassTableModel tablemodel, JTable jtable){
        //this.entrydb =  entrydb;
        this.tablemodel = tablemodel;
        this.jtable = jtable;
    }
    
    
    //reads the complete EntryDB 
    /**
     *
     */
    public void readEntryDB(){        
        List entrys = MainController.entrydb.getEntrys();        
        for (Object entry : entrys){
             tablemodel.AddEntry((Entry)entry);             
        }
         
    }
    
    //reads the complete EntryDB 
    /**
     *
     */
    public void readEntryDBAgain(){                        
        List entrys = MainController.entrydb.getEntrys();        
        tablemodel.removeEntrys();
        tablemodel.fireTableDataChanged();
        for (Object entry : entrys){
             tablemodel.AddEntry((Entry)entry);             
        }
        tablemodel.fireTableDataChanged();
        tablemodel.fireTableStructureChanged();
    }
    
    
    //post the last Entry to the tablemodel
    /**
     *
     */
    public void addTableEntry(){
        Entry entr = MainController.entrydb.getEntrys().get(MainController.entrydb.getEntrys().size() -1); 
        tablemodel.AddEntry(entr);
        if (!jtable.isVisible()){
            jtable.setVisible(true);
        }
        
    }
    
    //Updates the Entry when changes are happend
    /**
     *
     */
    public void updateTable(){        
        jtable.repaint();
        tablemodel.fireTableDataChanged();
    }

    /**
     *
     * @param row
     */
    public void removeEntry(int row) {
        tablemodel.removeEntry(row);     
        tablemodel.fireTableDataChanged();
        jtable.repaint();
        
    }
    
    /**
     *
     */
    public void removeAll(){
        tablemodel.removeEntrys();
        jtable.removeAll();
        jtable.setVisible(false);
        tablemodel.fireTableDataChanged();
       
    }
    
   
    
}
