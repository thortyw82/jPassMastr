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
 * MainClass from the App
 * 
 */

import de.jpm.view.main.JPassMain;
import java.security.Security;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;




/**
 *
 * @author thorty.w
 */
public class JPassMaster {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
       //new JPassMain();
        
        //Use the System look and feel 
        //ToDo Exceptions abfangen!
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
       
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JPassMain jp = new JPassMain();
                jp.setVisible(true);
            }
        });       

        
        //SecurityStuff
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
       
    }
}
