/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankserver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author jmwantisi
 */
public class OffState extends JFrame {

    
    private static JButton startServerButton;
    private static JTextField ipAddressField;
    private static JTextField portField;
    private JLabel ipAddressFieldLabel;
    private JLabel portFieldLabel;
    
    
    public OffState() {
        this.setLocation(320, 250);
        this.setLayout(null);
        portField = new JTextField("");
        portFieldLabel = new JLabel("Server Port Number:");
        ipAddressFieldLabel = new JLabel("Server IP Address: ");
        ipAddressField = new JTextField("");
        startServerButton = new JButton("Start Server");
        
        
        
        
        ipAddressFieldLabel.setBounds(180, 70, 130, 30);
        ipAddressField.setBounds(300, 70, 200, 30);
        
        portFieldLabel.setBounds(180, 130, 130, 30);
        portField.setBounds(300, 130, 200, 30);
        
        startServerButton.setBounds(370, 170, 130, 30);
        
        this.add(portFieldLabel);
        this.add(portField);
        this.add(ipAddressField);
        this.add(startServerButton);
        this.add(ipAddressFieldLabel);
        setTitle("Bank Server App");
        setSize(700, 300);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setResizable(false);
        setVisible(true);
        buttons();
        
    }
    
    public void buttons(){
        startServerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //dispose();
            
        
                
                
            }

        });

    }
    
 
    
    public void startServer () throws IOException{
       
    
    }
    
    public JButton startButton(){
        return startServerButton;
    }
    
    
}
