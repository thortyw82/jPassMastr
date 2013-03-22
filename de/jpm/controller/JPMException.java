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

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.crypto.CryptoException;

/**
 *
 * @author thorty
 */
public class JPMException extends Exception {

    String messag;
    String usermessage;
          
    
    public JPMException(String message) {
        usermessage = message;
    }
    
    public JPMException(String message, Exception ex) {
        if (ex instanceof CryptoException){            
            usermessage = MainController.bundle.getString("JPMException.usermessage");
        } else {
            usermessage = message;
        }
         Logger.getLogger(JPMException.class.getName()).log(Level.SEVERE, null, ex);
         ex.printStackTrace();
         System.out.println(ex.getMessage());
    }

    public String getUsermessage() {
        return usermessage;
    }
    
    
    
    
    
}
