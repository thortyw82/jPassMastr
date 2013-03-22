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

import com.dropbox.client2.exception.DropboxException;
import de.jpm.model.ConfigData;
import de.jpm.model.DropBoxUtil;
import de.jpm.model.EntryDB;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author thorty.w
 */
public class MainController {
    
    private static MainController instance = null;
    
    public static EntryTableController entryTableController;
    public static EntryDBController entryController;
    public static ConfigData configdata; 
    
    
    public static EntryDB entrydb;
    public static JFrame mainframe;
    public static File path;
    public static File[] databases;
    public static DropBoxUtil boxutil;
    public static final  ResourceBundle bundle = ResourceBundle.getBundle( "jpm" );



        
    
    private MainController() {
        try {            
            path = new File (MainController.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile();
            configdata = ConfigData.getConfigData();
            boxutil = new DropBoxUtil();
        } catch (DropboxException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
           entrydb = new EntryDB();
           //check if props are there and load or not     
//           if (isConfigFileAvailable()){
//               loadProps();
//           } else {
//               props = new Properties();
//           }           
    }
    
    
    public static MainController getInstance(){
        if (instance == null){
            instance = new MainController();            
        }
        return instance;
    }

    public static EntryDBController getEntryController() {
        return entryController;
    }

    public static void setEntryController(EntryDBController entryController) {
        MainController.entryController = entryController;
    }

    public static EntryTableController getEntryTableController() {
        return entryTableController;
    }

    public static void setEntryTableController(EntryTableController entryTableController) {
        MainController.entryTableController = entryTableController;
    }
    
    public static void setMainFrame(JFrame frame){
        mainframe = frame;
        
    }
    
    public static String[] getDatabases(){
        databases =  path.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.isFile() && pathname.getName().endsWith(".jp")){
                    return true;
                }
                else {
                    return false;
                }
            }
            });
        String[] names = new String[databases.length+1];
        for (int i = 0; i < databases.length; i++){
            names[i] = databases[i].getName();            
        }
        names[names.length-1] = " ";
        return names;
        
    }

    public static String[] getDropBoxDatabases(){        
        if (configdata.isSynced()){
        
            return boxutil.ListFiles(configdata.props.getProperty("drop1"), configdata.props.getProperty("drop2"));
        }
        else{ return null;
        
        }

    }


//    //FTPProps
//    public static void saveProps() {
//        FileOutputStream fileOut = null;
//        ObjectOutputStream out = null;
//        try {       
//            fileOut = new FileOutputStream((path.getAbsolutePath()) +"/jpm.se");
//            out = new ObjectOutputStream(fileOut);
//            out.writeObject(props);
//            out.close();
//            fileOut.close();
//        } catch (IOException ex) {
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                fileOut.close();
//            } catch (IOException ex) {
//                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                out.close();
//            } catch (IOException ex) {
//                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }        
//    }
////FTP Props
//    public static void loadProps() {
//        FileInputStream fileIn = null;
//        ObjectInputStream in = null;
//        try {
//            fileIn = new FileInputStream((path.getAbsolutePath())+"/jpm.se");
//            in = new ObjectInputStream(fileIn);
//            props = (Properties) in.readObject();
//            in.close();
//            fileIn.close();
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                fileIn.close();
//            } catch (IOException ex) {
//                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                in.close();
//            } catch (IOException ex) {
//                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }    

//    public static void uploadDBFTP() {
//        entryController.uploadFile(props.getProperty("ftp_server"), props.getProperty("ftp_port"),props.getProperty("ftp_folder"), props.getProperty("ftp_user"), props.getProperty("ftp_pass"));         
//    }
//
//    public static void downloadDBFTP() throws JPMException  {
//        entryController.downloadFile(props.getProperty("ftp_server"), props.getProperty("ftp_port"),props.getProperty("ftp_folder"), props.getProperty("ftp_user"), props.getProperty("ftp_pass"));
//    }
//    
     //FTP Props
//    public static boolean isConfigFileAvailable(){
//        File f = new File( (path.getAbsolutePath()) +"/jpm.se" );
//        if (f.exists()){
//            return true;
//        } else {
//            return false;
//        }

//    }    
          
    
}
