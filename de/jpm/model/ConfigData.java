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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thorty.w
 */
public class ConfigData implements Serializable {
 
    /**
     *
     */
    public Properties props = new Properties();
    private boolean synced = false;    
    private static File configfile;    
    private static final String name = "conf.jpm";
    private static String filepath;
    //private String configfilePath;
    
    private ConfigData(){
        
    }
    
    /**
     *
     * @return
     */
    public static ConfigData getConfigData(){        
        try {
            
            File f = new File( ConfigData.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile();            
            
            String os = System.getProperty("os.name").toLowerCase();
 
	    if (os.indexOf( "win" ) >= 0) {          
                filepath = f.getAbsolutePath() +"\\"+name;         
            } else {
                filepath = f.getAbsolutePath() +"/"+name;     
            }
            
            
                           
            configfile = new File(filepath) ;
            if (configfile.exists()) {
                return loadConfigData();
            }
            else {
                return new ConfigData();
            }
            
        } catch (URISyntaxException ex) {            
            return null;
        }
    }
        


    /**
     *
     * @return
     */
    public boolean isSynced() {
        return synced;
    }

    /**
     *
     * @param synced
     */
    public void setSynced(boolean synced) {
        this.synced = synced;
    }

    /**
     *
     * @return
     */
    public File getConfigfile() {
        return configfile;
    }

    /**
     *
     * @param configfile
     */
    public void setConfigfile(File configfile) {
        this.configfile = configfile;
    }
    
    /**
     *
     */
    public void saveConfigFile() {
        saveConfigData(this);
    }    

  private static void saveConfigData(ConfigData cd){
        ObjectOutputStream o = null;
        try {
//            File f = new File(filepath);
//            if (!f.exists()){
//                f.createNewFile();
//            }
                
            FileOutputStream file = new FileOutputStream( configfile );
            o = new ObjectOutputStream( file );
            o.writeObject(cd);
            o.flush();
            o.close();
        } catch (IOException ex) {
            Logger.getLogger(ConfigData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                o.close();
            } catch (IOException ex) {
                Logger.getLogger(ConfigData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
    }
    
    private static ConfigData loadConfigData(){
        try {
            // Von der Festplatte laden
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath));
            ConfigData conf = (ConfigData) ois.readObject();
            ois.close();
            return conf;
        } catch (IOException ex) {
            Logger.getLogger(ConfigData.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConfigData.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
}
}
