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

import java.io.OutputStream;
import java.io.Serializable;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SealedObject;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author thorty
 */
public class Cryptograph {
    
    /**
     *
     * @param o
     * @param out
     * @param pass
     * @return
     * @throws Exception
     */
    public static SealedObject encode( Serializable o, OutputStream out, String pass ) throws Exception 
     { 
        Cipher c = Cipher.getInstance( "DES" ); 
        Key k = new SecretKeySpec( pass.getBytes(), "DES" );
        //Key k = generateKeyFromText(pass);
        c.init( Cipher.ENCRYPT_MODE, k ); 

        OutputStream cos = new CipherOutputStream( out, c ); 
        SealedObject so = new SealedObject(o,c); 
        return so;

     } 
 
    /**
     *
     * @param o
     * @param pass
     * @return
     * @throws Exception
     */
    public static Object decode( Serializable o, String pass ) throws Exception 
  { 
    Cipher c = Cipher.getInstance( "DES" );   
    Key k = new SecretKeySpec( pass.getBytes(), "DES" );
    //Key k = generateKeyFromText(pass);
    
    c.init( Cipher.DECRYPT_MODE, k ); 
    
    SealedObject so = new SealedObject(o, c);
    //ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
    //CipherInputStream cis = new CipherInputStream( is, c ); 
    
//    for (int b; (b = cis.read()) != -1;){
//       bos.write(b);
//    }
//    cis.close(); 
//    return bos.toByteArray(); 
    
    Object original = so.getObject(k);
    return original;
    
  } 
    
    
}
