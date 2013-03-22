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

/*
 * NOT used at the moment
 */
//package de.jpm.model;
//
//
//import java.io.*;
//import org.apache.commons.net.ftp.FTP;
//import org.apache.commons.net.ftp.FTPClient;
//
///**k
// * FTP-Utility, basierend auf Apache FTPClient:
// * {@link http://commons.apache.org/net/apidocs/org/apache/commons/net/ftp/FTPClient.html }
//*/
//public class FTPUploadDownloadUtil
//{
//   /**
//    * FTP-Dateienliste.
//    * @return String-Array der Dateinamen auf dem FTP-Server
//    */
//   public static String[] list( String host, int port, String usr, String pwd ) throws IOException
//   {
//      FTPClient ftpClient = new FTPClient();
//      String[]  filenameList;
//
//      try {
//         ftpClient.connect( host, port );
//         ftpClient.login( usr, pwd );
//         filenameList = ftpClient.listNames();
//         ftpClient.logout();
//      } finally {
//         ftpClient.disconnect();
//      }
//
//      return filenameList;
//   }
//
//   /**
//    * FTP-Client-Download.
//    * @return true falls ok
//    */
//   public static boolean download( String localResultFile, String remoteSourceFile,
//         String host, int port, String usr, String pwd, boolean showMessages ) throws IOException
//   {
//      FTPClient        ftpClient = new FTPClient();
//      FileOutputStream fos = null;
//      boolean          resultOk = true;
//
//      try {
//         ftpClient.connect( host, port );
//         ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//         if( showMessages ) { System.out.println( ftpClient.getReplyString() ); }
//         resultOk &= ftpClient.login( usr, pwd );
//         if( showMessages ) { System.out.println( ftpClient.getReplyString() ); }
//         fos = new FileOutputStream( localResultFile );
//         resultOk &= ftpClient.retrieveFile( remoteSourceFile, fos );
//         if( showMessages ) { System.out.println( ftpClient.getReplyString() ); }
//         resultOk &= ftpClient.logout();
//         if( showMessages ) { System.out.println( ftpClient.getReplyString() ); }
//      } finally {
//         try { if( fos != null ) { fos.close(); } } catch( IOException e ) {/* nothing to do */}
//         ftpClient.disconnect();
//      }
//
//      return resultOk;
//   }
//
//   /**
//    * FTP-Client-Upload.
//    * @return true falls ok
//    */
//   public static boolean upload( String localSourceFile, String remoteResultFile,
//         String host, int port, String usr, String pwd, boolean showMessages ) throws IOException
//   {
//      FTPClient       ftpClient = new FTPClient();
//      FileInputStream fis = null;
//      boolean         resultOk = true;
//
//      try {         
//         ftpClient.connect( host, port );
//                  
//         ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//         if( showMessages ) { System.out.println( ftpClient.getReplyString() ); }
//         resultOk &= ftpClient.login( usr, pwd );
//         if( showMessages ) { System.out.println( ftpClient.getReplyString() ); }
//         fis = new FileInputStream( localSourceFile );        
//         resultOk &= ftpClient.storeFile( remoteResultFile, fis );         
//         if( showMessages ) { System.out.println( ftpClient.getReplyString() ); }
//         resultOk &= ftpClient.logout();
//         if( showMessages ) { System.out.println( ftpClient.getReplyString() ); }
//      } finally {
//         try { if( fis != null ) { fis.close(); } } catch( IOException e ) {/* nothing to do */}
//         ftpClient.disconnect();
//      }
//
//      return resultOk;
//   }
//}