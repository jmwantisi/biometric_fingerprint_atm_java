/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankserver.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 *
 * @author Bupe
 */
public class SendingSMS {

    public SendingSMS() throws InterruptedException {
       //sendViaUrl();
       // send();
    }
    
    
    public void sendViaUrl(String phonNumber, String token){
        
        System.out.println("method called");
        
                try {
                        String recipient = "+265991406216";
                        String message = " sent from inside server";
                        String username = "admin";
                        String password = "123456789";
                        String originator = "NATIONAL BANK";

                        String requestUrl  = "http://127.0.0.1:9501/api?action=sendmessage&" +
            "username=" + URLEncoder.encode(username, "UTF-8") +
            "&password=" + URLEncoder.encode(password, "UTF-8") +
            "&recipient=" + URLEncoder.encode(recipient, "UTF-8") +
            "&messagetype=SMS:TEXT" +
            "&messagedata=" + URLEncoder.encode(message, "UTF-8") +
            "&originator=" + URLEncoder.encode(originator, "UTF-8") +
            "&serviceprovider=GSMModem1" +
            "&responseformat=html";



                        URL url = new URL(requestUrl);
                        HttpURLConnection uc = (HttpURLConnection)url.openConnection();

                        System.out.println(uc.getResponseMessage());

                        uc.disconnect();

                } catch(Exception ex) {
                        System.out.println(ex.getMessage());

               
 }
    
    }
    public void send(String phonNumber, String token) throws InterruptedException{
        try {
           
            String host = "localhost";
            int port = 9500;
            String username = "admin";
            String password = "123456789";
            
            SMSClient smsClient = new SMSClient(host, port);
            smsClient.login(username, password);
            
            String message = "sen from inside server send method";
            
            
            if(smsClient.isLoggedIn()){
                
                smsClient.sendMessage(phonNumber, token);
                System.out.println("login successful");
                
            }
            
            
        } catch (IOException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        } catch(InterruptedException e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
    
}
