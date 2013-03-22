/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jpm.view.table;

import de.jpm.controller.MainController;
import de.jpm.model.Entry;
import java.util.Vector;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author thorty.w
 */
public class JPassTableModel extends AbstractTableModel implements TableModel {

    private Vector entrys = new Vector();
    private Vector listners = new Vector();
    
    //The Method to add Entrys to the table
    /**
     *
     * @param entry
     */
    public void AddEntry(Entry entry){
        int index = entrys.size();
        entrys.add(entry);    

        // Zuerst ein Event, "neue Row an der Stelle index" herstellen
        TableModelEvent e = new TableModelEvent( this, index, index, 
                TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT );

        
        
        // Nun das Event verschicken
        for( int i = 0, n = listners.size(); i<n; i++ ){
            ((TableModelListener)listners.get( i )).tableChanged( e );
        }
    }
    

    

    
    //return the rows to draw
    @Override
    public int getRowCount() {
        return entrys.size();
    }

    //return the colums to draw
    @Override
    public int getColumnCount() {
        return 4;
    }

    //return the columname to write 
    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex){
            case 0: return MainController.bundle.getString("JPassTableModel.colum0");
            case 1: return MainController.bundle.getString("JPassTableModel.colum1");
            case 2: return MainController.bundle.getString("JPassTableModel.colum2");
            case 3: return MainController.bundle.getString("JPassTableModel.colum3");
            default: return null;
        }       
    }

    //What a object is to drwa in the colum
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 0: return String.class;
            case 1: return String.class;
            case 2: return String.class;
            case 3: return String.class;
            default: return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    //return the explizit value to write
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Entry entry = (Entry) entrys.get(rowIndex);
        switch (columnIndex){
            case 0: return entry.getName();
            case 1: return entry.getUser();
            //case 2: return entry.getPass();
            case 2: return "**********";
            case 3: return entry.getNotes();
            default: return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listners.remove(l);
    }

    /**
     *
     * @param row
     */
    public void removeEntry(int row) {
        entrys.remove(row);
    }

    /**
     *
     */
    public void removeEntrys() {
        entrys.removeAllElements();
        
    }


    
    
    
    
    
    
}
