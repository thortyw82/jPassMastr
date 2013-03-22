/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jpm.view.table;

import de.jpm.controller.MainController;
import de.jpm.model.OpenURLUtil;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.event.MouseInputListener;
import javax.swing.table.TableModel;

/**
 *
 * @author thorty.w
 */
public class EntryTable extends JTable implements MouseInputListener {
    
    /**
     *
     * @param tm
     */
    public EntryTable (TableModel tm){
        super(tm);
        initEntryTable();
    }

    private void initEntryTable() {
        this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));                
        this.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {      
        if (e.getClickCount() == 2){        
          System.out.println(this.getValueAt(this.getSelectedRow(), this.getSelectedColumn()));      
          String uri_str = (String) this.getValueAt(this.getSelectedRow(), this.getSelectedColumn());                                                      
          
          URI uri = URI.create(uri_str);
          
                OpenURLUtil.openURL(uri_str);
           
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }
    
    
    
    
}
