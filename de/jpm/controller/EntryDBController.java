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
import de.jpm.model.DropBoxUtil;
import de.jpm.model.EncryptionService;
import de.jpm.model.EntryDB;
import de.jpm.view.main.OpenDatabase;
import de.jpm.view.main.NewDatabase;
import java.awt.Dialog;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import org.bouncycastle.crypto.CryptoException;

/**
 *
 * @author thorty.w
 */
public class EntryDBController {
    
    EncryptionService es;
    File dbfile;    
    String name;
    DropBoxUtil boxutil;

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     *
     */
    public EntryDBController() {
        
    }
    
    String getFileSeperator(){
           String os = System.getProperty("os.name").toLowerCase();
 
	    if (os.indexOf( "win" ) >= 0) {          
                return "\\";       
            } else {
                return "/";
            }
    }

     /**
     *
     */
    public void newDB(){
           MainController.entrydb = new EntryDB();
           MainController.entryTableController.removeAll();
           NewDatabase newdb = new NewDatabase(MainController.mainframe, true);                  
           newdb.setLocationRelativeTo(MainController.mainframe);           
           newdb.setVisible(true);   
     }

     /**
     *
     */
    public void openDB(){
                JFileChooser fileopen = new JFileChooser();
                fileopen.setCurrentDirectory(MainController.path);
                JPanel filepanel = new JPanel();
                int ret = fileopen.showDialog(MainController.mainframe.add(filepanel), MainController.bundle.getString("open"));

                if (ret == JFileChooser.APPROVE_OPTION) {
                    //storefile
                    dbfile = fileopen.getSelectedFile();
                    //get pass and open the db 
                   OpenDatabase getpass = new OpenDatabase("open");
                   getpass.setVisible(true);
                   getpass.setLocationRelativeTo(MainController.mainframe);
                   getpass.setModalityType(Dialog.ModalityType.MODELESS.APPLICATION_MODAL);     
                                       
                } else {
                     
                }
        } 
     
//     public void saveDB() throws JPMException{
//         if (!name.endsWith(".jp")){
//                    dbfile = new File(MainController.path.toString() +"\\"+ name +".jp");
//         }
//                    //saveDB(pass);
//                    saveDB();
//             
//     }
    
     /**
     *
     * @param pass
     * @throws JPMException
     */
    public void setPass(char[] pass) throws JPMException{
        try {
            es = new EncryptionService(pass);
            //this.pass = pass;
            //MainController.entrydb.setPass(pass);
        } catch (CryptoException ex) {
            throw new JPMException("crypto exception", ex);
        } catch (NoSuchAlgorithmException ex) {
            throw new JPMException("crypto exception", ex);
        }
     }
     
     
    /**
     *
     * @throws JPMException
     */
    public void saveDB() throws JPMException {
    try
    { 
      if (!name.endsWith(".jp")){
         dbfile = new File(MainController.path.toString() +getFileSeperator()+ name +".jp"); 
      } else {
          dbfile = new File(MainController.path.toString() +getFileSeperator()+ name);
      }
      
      
             
      FileOutputStream file = new FileOutputStream( dbfile );
      MainController.entrydb.setFile(dbfile);
      //Verschluesselung
      EntryDB mydb = MainController.entrydb;
      //mydb.setPass(pass); //ToDo noch zu verschlüsseln an dieser Stelle

      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutput out = new ObjectOutputStream(bos);   
      
      out.writeObject(mydb);
      byte[] yourBytes = bos.toByteArray();      
      //es = new EncryptionService(pass);
      byte[] bla = es.encrypt(yourBytes);
      file.write(es.getSalt());
      file.write(bla);        
      file.close();
    }
    catch ( IOException e ){
        throw new JPMException(MainController.bundle.getString("EntryDBController.couldnotsave"), e);
    }     
    catch ( Exception ex ){
        throw new JPMException(MainController.bundle.getString("EntryDBController.couldnotsave"),ex);
    }        
}


    /**
     *
     * @param pass
     * @throws JPMException
     */
    public void loadDBFile(char[] pass) throws JPMException {
        try {          
            //read all
            if (dbfile == null){
                dbfile = new File(MainController.path.toString() +getFileSeperator()+ name );
            }
            
            byte[] temp = readFile(dbfile); 
            //get salt
            byte[] salt = new byte[EncryptionService.SALT_LENGTH];
            System.arraycopy(temp, 0, salt, 0, EncryptionService.SALT_LENGTH);            
            //get Object
            int objectlegth = temp.length - EncryptionService.SALT_LENGTH;
            byte[] data = new byte[objectlegth];
            System.arraycopy(temp, EncryptionService.SALT_LENGTH, data, 0, objectlegth);
            //decrypt
            es = new EncryptionService(pass, salt);
            //es.setSalt(salt);
            byte[] bytes = es.decrypt(data);
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInput in = new ObjectInputStream(bis);
            EntryDB entrydb = (EntryDB) in.readObject();
            MainController.entrydb = entrydb;
            MainController.entryTableController.addTableEntry();           
            MainController.entryTableController.readEntryDBAgain();            
        } catch (CryptoException ex) {
           throw new JPMException(null, ex);
        } catch (ClassNotFoundException ex) {
            throw new JPMException(MainController.bundle.getString("EntryDBController.couldnotload"),ex);
        } catch (IOException ex) {
             throw new JPMException(MainController.bundle.getString("EntryDBController.couldnotload"),ex);
            
        }        
    }   
   
   /*
    *Helper Method to read the Binary Data
    */
    private byte[] readFile(File file) throws JPMException {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            // Create the byte array to hold the data
            byte[] bytes = new byte[(int) file.length()];
            // Read in the bytes
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length
                   && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }
            // Ensure all the bytes have been read in
            if (offset < bytes.length) {
                throw new JPMException(MainController.bundle.getString("EntryDBController.databaseempty"));
            }
            is.close();
            return bytes;
        } catch (IOException ex) {
            throw new JPMException(MainController.bundle.getString("EntryDBController.readerror"), ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
               
            }
        }
    }

    private void setFile(File file) {
       this.dbfile = file;
    }

    
//    public boolean uploadFileFTP(String server, String port, String folder, String user, String pass){
//        try {
//            boolean result =FTPUploadDownloadUtil.upload(dbfile.getAbsolutePath(), folder+dbfile.getName(), server, Integer.parseInt(port), user, pass, true);
//            return result;
//        } catch (IOException ex) {
//            Logger.getLogger(EntryDBController.class.getName()).log(Level.SEVERE, null, ex);
//            return false;
//        }
//    }    
//    public boolean downloadFileFTP(String server, String port, String folder, String user, String pass) throws JPMException{
//        try {
//            boolean result =FTPUploadDownloadUtil.download(dbfile.getAbsolutePath(), folder+dbfile.getName(), server, Integer.parseInt(port), user, pass, true);            
//            loadDBFile(this.pass);
//            return result;
//        } catch (IOException ex) {
//            Logger.getLogger(EntryDBController.class.getName()).log(Level.SEVERE, null, ex);
//            return false;
//        }        
        
        
    /**
     *
     * @return
     * @throws JPMException
     */
    public boolean uploadDBToDropBox() throws JPMException {
        try {
            saveDB();
            if (MainController.configdata.isSynced()){
                boxutil = new DropBoxUtil();
                boxutil.uploadFile(dbfile, (String)MainController.configdata.props.getProperty("drop1"), (String)MainController.configdata.props.getProperty("drop2"));            
                return true;
            } else {
                throw new JPMException(MainController.bundle.getString("EntryDBController.BoxAccessError"));
            }
            
            
        
        } catch (DropboxException ex) {
            Logger.getLogger(EntryDBController.class.getName()).log(Level.SEVERE, null, ex);
            throw new JPMException(MainController.bundle.getString("EntryDBController.Boxuploaderror"),ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(EntryDBController.class.getName()).log(Level.SEVERE, null, ex);
            throw new JPMException(MainController.bundle.getString("EntryDBController.Boxuploaderror"),ex);            
        } catch (IOException ex) {
            Logger.getLogger(EntryDBController.class.getName()).log(Level.SEVERE, null, ex);
            throw new JPMException(MainController.bundle.getString("EntryDBController.Boxuploaderror"),ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(EntryDBController.class.getName()).log(Level.SEVERE, null, ex);
            throw new JPMException(MainController.bundle.getString("EntryDBController.Boxuploaderror"),ex);
        }
    }

    /**
     *
     * @return
     * @throws JPMException
     */
    public boolean downloadDBfromDropBox() throws JPMException {
        try {
            if (MainController.configdata.isSynced()){
                
                if (dbfile == null){
                dbfile = new File(MainController.path.toString() +getFileSeperator()+ name );
                }
                boxutil = new DropBoxUtil();
                String result = boxutil.downloadFile(dbfile, (String)MainController.configdata.props.getProperty("drop1"), (String)MainController.configdata.props.getProperty("drop2"));
            if (result != null){
                 loadDBFile();
                 return true;        
            } else {
                throw new JPMException(MainController.bundle.getString("EntryDBController.Boxdownloaderror"));
            }            
        } else {
                throw new JPMException(MainController.bundle.getString("EntryDBController.BoxAccessError"));
            }            
        }        
        catch (DropboxException ex) {
            Logger.getLogger(EntryDBController.class.getName()).log(Level.SEVERE, null, ex);
            throw new JPMException(MainController.bundle.getString("EntryDBController.Boxdownloaderror"),ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(EntryDBController.class.getName()).log(Level.SEVERE, null, ex);
            throw new JPMException(MainController.bundle.getString("EntryDBController.Boxdownloaderror"),ex);            
        } catch (IOException ex) {
            Logger.getLogger(EntryDBController.class.getName()).log(Level.SEVERE, null, ex);
            throw new JPMException(MainController.bundle.getString("EntryDBController.Boxdownloaderror"),ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(EntryDBController.class.getName()).log(Level.SEVERE, null, ex);
            throw new JPMException(MainController.bundle.getString("EntryDBController.Boxdownloaderror"),ex);
        }
    }      
    
    /**
     *
     * @param pass
     * @return
     * @throws JPMException
     */
    public boolean downloadDBfromDropBox(char[] pass) throws JPMException {
        try {
            if (MainController.configdata.isSynced()){
                
                if (dbfile == null){
                dbfile = new File(MainController.path.toString() +getFileSeperator()+ name );
                }
                boxutil = new DropBoxUtil();
                String result = boxutil.downloadFile(dbfile, (String)MainController.configdata.props.getProperty("drop1"), (String)MainController.configdata.props.getProperty("drop2"));
            if (result != null){
                 loadDBFile(pass);
                 return true;        
            } else {
                throw new JPMException(MainController.bundle.getString("EntryDBController.Boxdownloaderror"));
            }            
        } else {
                throw new JPMException("No Access to yoir DropBox, pleas Auuth under Options");
            }            
        }        
        catch (DropboxException ex) {
            Logger.getLogger(EntryDBController.class.getName()).log(Level.SEVERE, null, ex);
            throw new JPMException(MainController.bundle.getString("EntryDBController.Boxdownloaderror"),ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(EntryDBController.class.getName()).log(Level.SEVERE, null, ex);
            throw new JPMException(MainController.bundle.getString("EntryDBController.Boxdownloaderror"),ex);            
        } catch (IOException ex) {
            Logger.getLogger(EntryDBController.class.getName()).log(Level.SEVERE, null, ex);
            throw new JPMException(MainController.bundle.getString("EntryDBController.Boxdownloaderror"),ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(EntryDBController.class.getName()).log(Level.SEVERE, null, ex);
            throw new JPMException(MainController.bundle.getString("EntryDBController.Boxdownloaderror"),ex);
        }
    }    
    

    private void loadDBFile() throws JPMException {
            try {          
            //read all
            if (dbfile == null){
                dbfile = new File(MainController.path.toString() +getFileSeperator()+ name );
            }
            
            byte[] temp = readFile(dbfile); 
            //get salt
            byte[] salt = new byte[EncryptionService.SALT_LENGTH];
            System.arraycopy(temp, 0, salt, 0, EncryptionService.SALT_LENGTH);            
            //get Object
            int objectlegth = temp.length - EncryptionService.SALT_LENGTH;
            byte[] data = new byte[objectlegth];
            System.arraycopy(temp, EncryptionService.SALT_LENGTH, data, 0, objectlegth);
            //decrypt
            
            byte[] bytes = es.decrypt(data);
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInput in = new ObjectInputStream(bis);
            EntryDB entrydb = (EntryDB) in.readObject();
            MainController.entrydb = entrydb;
            MainController.entryTableController.readEntryDBAgain();            
        } catch (CryptoException ex) {
           throw new JPMException(null, ex);
        } catch (ClassNotFoundException ex) {
            throw new JPMException(MainController.bundle.getString("EntryDBController.couldnotload"),ex);
        } catch (IOException ex) {
             throw new JPMException(MainController.bundle.getString("EntryDBController.couldnotload"),ex);
            
        }        
    }
        

    
    
}
