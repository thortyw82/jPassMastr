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

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.DropboxFileInfo;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.RequestTokenPair;
import com.dropbox.client2.session.Session;
import com.dropbox.client2.session.WebAuthSession;
import de.jpm.controller.MainController;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author thorty.w
 */
public class DropBoxUtil {
    
    private static DropboxAPI<WebAuthSession> mDBApi;
    private static final String APP_KEY = "Here must be the dropbox app key"; /* Here must be the dropbox app key */
    private static final String APP_SECRET = "Here must be the dropbox app key";     
    private static final Session.AccessType ACCESS_TYPE = Session.AccessType.APP_FOLDER;
    //private static HashMap<String,String> DBtokens = new HashMap<>();
    private static WebAuthSession session;
    
    public DropBoxUtil() throws DropboxException, MalformedURLException, IOException, URISyntaxException{
        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        session = new WebAuthSession(appKeys, ACCESS_TYPE);
                
        
    }
    
    
    public HashMap firstAuthorisation() {        
        try {
            WebAuthSession.WebAuthInfo authInfo = session.getAuthInfo();
            RequestTokenPair pair = authInfo.requestTokenPair;
            String url = authInfo.url;
     
            OpenURLUtil.openURL(url);
            JOptionPane.showMessageDialog(null, MainController.bundle.getString("DropBoxUtil.AuthDialog"));
            session.retrieveWebAccessToken(pair);
     
            AccessTokenPair tokens = session.getAccessTokenPair();
            HashMap map = new HashMap();
            map.put("key", tokens.key);
            map.put("secret", tokens.secret);
            return map;

        } catch (DropboxException ex) {
            Logger.getLogger(DropBoxUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
    }
    
    public String uploadFile(File file, String key, String secret) throws DropboxException, FileNotFoundException{
        AccessTokenPair access =  new AccessTokenPair(key, secret);
        
        mDBApi = new DropboxAPI(session);        
        mDBApi.getSession().setAccessTokenPair(access);  
        
        
        FileInputStream inputStream = null;   
        inputStream = new FileInputStream(file);
        //Entry newEntry = mDBApi.putFile("/"+file.getName(), inputStream, file.length(), null, null);
        Entry newEntry = mDBApi.putFileOverwrite("/"+file.getName(), inputStream, file.length() , null);
        return newEntry.rev;
        
    }
    
    public String downloadFile(File toStore, String key, String secret){

        AccessTokenPair access =  new AccessTokenPair(key, secret);        
        mDBApi = new DropboxAPI(session);        
        mDBApi.getSession().setAccessTokenPair(access);         
        
        FileOutputStream outputStream = null;
        try {
       // Get file.                 
            outputStream = new FileOutputStream(toStore);
            DropboxFileInfo info = mDBApi.getFile("/"+toStore.getName(), null, outputStream, null);
            return info.getMetadata().rev;               
        } catch (DropboxException e) {
            return null;
        } catch (FileNotFoundException e) {
            return null;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {}
            }
            
        }
    }
    
    public String[] ListFiles(String key, String secret){

        AccessTokenPair access =  new AccessTokenPair(key, secret);        
        mDBApi = new DropboxAPI(session);        
        mDBApi.getSession().setAccessTokenPair(access);         
        
        List<String> filenames = new Vector<String>();
        String[] filenames_a = null;        
        try {
       // Get files.                 
            List<DropboxAPI.Entry> entries = mDBApi.search("/",".jp",20,false);
                                        
            if (entries != null) {
                    filenames_a = new String[entries.size()+1];
                    Iterator<DropboxAPI.Entry> eit = entries.iterator();
                    int i=0;
                     
                    while (eit.hasNext()) {                            
                            DropboxAPI.Entry entry = eit.next();
                            String pathName = entry.path;
                            //System.out.println(pathName);
                            filenames.add(pathName);
                            filenames_a[i] = pathName;
                            i++;
                    }
                    filenames_a[filenames_a.length-1] = " ";
                   // System.out.println("END OF FILELIST");
                    return filenames_a;
            } else {
                    //System.out.println("entries was null");
                    return filenames_a;
            }            
            
        } catch (DropboxException e) {
            return filenames_a;
        }        
    }    
    
    
    
    
}
