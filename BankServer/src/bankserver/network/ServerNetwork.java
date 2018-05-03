/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankserver.network;

import bankserver.ServerDisplay;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 *
 * @author jmwantisi
 */

 
public class ServerNetwork {
    private ServerSocket serverSocket; // waits for connection from the clients
    private Socket connectionSocket; //sets up connection between computers
    //private static ServerProcessesUI ui;
    private ServiceNetworkSession service;
    private String CONNECTION_MADE = "Server - [ Started ] - ";
    private final ServerDisplay serverUI;
    private static Thread sessionThread;
    private static boolean serverConnected;

    public ServerNetwork() throws IOException {
        serverUI = new ServerDisplay();
        serverConnected = false;
        
    }
    
    public void button(){
    }
    
    public void startServer(int serverPort) throws IOException{
        
        //server port and limit number of people who can connect to the server
        serverSocket = new ServerSocket(serverPort, 50);
        
        //getting assigned IP address to the server
        InetAddress serverIP = InetAddress.getByName("NAT");  //computer name translated to IP address when connected
        serverUI.getIpTextArea().append(serverIP.getHostAddress());
        
        //getting set port number to the server
        int port = serverSocket.getLocalPort();
        String portString = Integer.toString(port);
        serverUI.getPort().append(portString);
        
        //system administrator logs start here
        serverUI.geTextArea().append(new Date() + " " + "servername natmw[jmdc28]: Server Online.");
        serverUI.geTextArea().append("\n\n" + new Date() + " " + "servername natmw[jmdc28]: Server listening on " + serverIP.getHostAddress() + " Port "+ serverPort + ".");
        serverUI.geTextArea().append("\n\n" + new Date() + " " + "servername natmw[jmdc28]: Server Ready For Connection.");
        //log starting from here
        
        while (true) {  
            connectionSocket = serverSocket.accept();
             
            serverConnected = true;
            
            service = new ServiceNetworkSession(connectionSocket, serverConnected);
            sessionThread = new Thread(service);
            sessionThread.start();
            
        }
    
    }
    
}
