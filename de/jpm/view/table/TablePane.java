/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jpm.view.table;

import de.jpm.controller.EntryTableController;
import javax.swing.JScrollPane;
import javax.swing.JTable;
/**
 *
 * @author thorty.w
 */
public class TablePane extends JScrollPane {

    
    JPassTableModel tablemodel;
    JTable entrytable;    

    
    /**
     *
     * @param tablemodel
     * @param entrytable
     * @param tablecontroller
     */
    public TablePane (JPassTableModel tablemodel, JTable entrytable, EntryTableController tablecontroller){
       super(entrytable);
       this.tablemodel = tablemodel;
       this.entrytable = entrytable;            
       this.setVisible(true);  
    }


    private void initTablePane() {
       
       
       
    }

    
}
