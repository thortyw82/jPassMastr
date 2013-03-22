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
package de.jpm.model;


import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thorty.w
 */
public class EntryDB implements Serializable {
    
    private List<Entry> entrys;
    private static EntryDB instance = null;    
    private File file = new File("dummy");
    
    
    /**
     *
     */
    public EntryDB(){
        //Todo Read the DBFile
        entrys = new ArrayList<Entry>();       
//        entrys.add( new Entry("www.gmx.de", "Uschi", "bla", "E-Mail Account") );
//        entrys.add( new Entry("www.t-online.de", "Uschi", "bla", "E-Mail Account") );
//        entrys.add( new Entry("twitter", "cyber", "fooo", "Twitter thorty") );
        
    }
    
    

//    public static EntryDB getInstance() {
//        if (instance == null) {
//            instance = new EntryDB();
//        }
//        return instance;
//    }
    
    
    
    
    /**
     *
     * @return
     */
    public List<Entry> getEntrys() {
        return entrys;
    }
    
    /**
     *
     * @param entry
     */
    public void setEntry (Entry entry){
        entrys.add(entry);
    }
    
    /**
     *
     * @param entry
     * @param index
     */
    public void updateEntry(Entry entry, int index){
        Entry myEntry = entrys.get(index);
        myEntry.setName(entry.getName());
        myEntry.setNotes(entry.getNotes());        
        myEntry.setPass(entry.getPass());
        myEntry.setUser(entry.getUser());
    }

    /**
     *
     * @param row
     */
    public void deleteEntry(int row) {
        entrys.remove(row);
    }



    /**
     *
     * @param file
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     *
     * @return
     */
    public File getFile() {
        return file;
    }


    
    
    
    
    
}
