/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankserver;

import bankserver.network.ServerNetwork;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author jmwantisi
 */
public class BankServer {
  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
    
        b();
    }
    
    public static void b () throws IOException, ClassNotFoundException{
        
        String portNumber = JOptionPane.showInputDialog(null, "Enter Server Port Number", null, 3);
        
        if(portNumber.isEmpty()){
            JOptionPane.showMessageDialog(null, "Invalid Server Ports");
            b(); //recurive
        }else{
            int port = Integer.parseInt(portNumber.trim());
            new ServerNetwork().startServer(port);
        }
        
        
        
    }
    
    
}
