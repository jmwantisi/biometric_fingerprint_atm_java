/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankserver;

import java.awt.*;
import javax.swing.*;


/**
 *
 * @author jmwantisi
 */
public class ServerDisplay extends JFrame {

    private static JTextArea serverStarted;
    private static JTextArea serverIPAddress;
    private static JTextArea port;
    private static JTextField ipAddressField;
    private static JButton startServerButton;
    private JScrollPane serverScrollPane;
    
    
    public ServerDisplay(){
        
        
        
        serverIPAddress = new JTextArea("Server IP Address: ");
        serverIPAddress.setForeground(Color.white);
        serverIPAddress.setBackground(Color.black);
        serverIPAddress.setEditable(false);
        
        port = new JTextArea("Server Port: ");
        port.setForeground(Color.white);
        port.setBackground(Color.black);
        port.setEditable(false);
        
        serverStarted = new JTextArea();
        serverStarted.setEditable(false);
        serverStarted.setForeground(Color.white);
        serverStarted.setBackground(Color.black);
        //serverStarted.setSize(200,100);
        
        ipAddressField = new JTextField();
        startServerButton = new JButton("Start Server");
        
        
        
        setLayout(null);
        serverScrollPane = new JScrollPane(serverStarted);
        add(serverScrollPane, null);
        serverScrollPane.setBounds(10, 10, 900, 650);
        serverIPAddress.setBounds(960, 10, 210, 40);
        port.setBounds(960, 60, 210, 40);
        this.add(ipAddressField);
        this.add(serverIPAddress);
        this.add(port);
        setTitle("Bank Server App");
        this.setLocation(80, 30);
        setSize(1200, 700);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //serverStarted.append("Server Off");
        setResizable(false);
        setVisible(true);
       
        
    }
    
    
    
    public JButton startButton(){
        return startServerButton;
    }
    
    public static JTextArea geTextArea(){
       serverStarted.setFont(new Font("Arial", Font.BOLD, 11));
        return serverStarted;
    }
    
    public static JTextArea getIpTextArea(){
        serverIPAddress.setFont(new Font("Arial", Font.BOLD, 11));
        return serverIPAddress;
    }
    
    public static JTextArea getPort(){
       port.setFont(new Font("Arial", Font.BOLD, 11));
        return port;
    }
    
}
