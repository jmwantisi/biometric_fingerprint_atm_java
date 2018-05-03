/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankserver.services;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;


/**
 *
 * @author jmwantisi
 */
public class TokenGenerator extends Service{
 
    private boolean run = true;
    private static int counter = 0;
    
    private static Integer customerRequestingOneTimeTokken;
    private static Integer customerUsingOneTimeTokken;
    private static Integer checkingTokkenCustomerAccount;
    private SendingSMS sms;
    
    
    public TokenGenerator() {
        
       
        
        toDaysDate();
      
    }
    static boolean runThis = true;
   

    
   //call this when a user has request an ATM token
    public void generateToken(Integer customerAccountNumber, String phone){
        //remember this will happen in the server
        // six digit code
        tokenValidated = false;
        runThis = true;
       while(runThis){
          Integer value = (int)(Math.random() * 1000000); 
          if (value > 99999){
              runThis = false;
              
              
              checkForAlreadyGeneratedTokken(value,customerAccountNumber, phone );
              
              
          }
        }
    }
    
   
    
    
    public static String toDaysDate;
    public static String getToDaysDate(){
        return toDaysDate;
    }
    
    //TAKE OUT THIS PERFOMING AS NESTED/SUB QUERY
    public void toDaysDate(){
        try {
            Class.forName(DATABASE_DRIVER);

            //System.out.println("Establishing connection");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
           // System.out.println("URL:  " + URL );

           // Statement s = null;
            
            //System.out.println("Time Difference:  " + time);
            statement = connection.createStatement();
            
            ResultSet rs = statement.executeQuery("SELECT current_date AS dateToDay"); 
            
            
           // System.out.println("Query Exe" + rs.toString());

            while (rs.next()) {
                
                toDaysDate = rs.getString("dateToDay");
                
                
                //validate(toDaysDate);
                
                
                //System.out.println("The date To day:  " + toDaysDate);
                
                 return;
              
            }
            
            if (!rs.next()){
                tokenValidated = false;
                 System.out.println("Tokken No valid");
            }

            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, "Connection to the Database Failed! \nMake sure your database is running");
            //se.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException se2) {
                
                 JOptionPane.showMessageDialog(null, " Error in hereConnection to the Database Failed! \nMake sure your database is running");
            
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        
        }
    
    }
    
    //KEEP THIS TO UPDATE TOKEN AS USED
    public void tokkenUsed(Integer getReturnToken, Integer customerAccountNumber){
        try {
            Class.forName(DATABASE_DRIVER);

            //System.out.println("Establishing connection");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            //System.out.println("URL:  " + URL );

            Statement s = null;
            
            //System.out.println("Creating statement inside game class");
            statement = connection.createStatement();
            
            //System.out.println("Tokken Used:  " + getReturnToken);
            
            //ResultSet rs = s.executeQuery("SELECT * FROM GAMES");
            
            
            
            statement.executeUpdate("UPDATE atmcardtoken SET status = 'used' where tokken = "+ getReturnToken+ " AND accountNumber = "+ customerAccountNumber +""); 
             
            
            statement.close();
            connection.close();

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, "Connection to the Database Failed! \nMake sure your database is running");
            //se.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException se2) {
                
                 JOptionPane.showMessageDialog(null, " Error in hereConnection to the Database Failed! \nMake sure your database is running");
            
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        
        }
    
    }
    
    public Integer getTokkenGenerated(){
        return tokkenGernated;
                
    }
    
    private static boolean tokenValidate = false;
    private static boolean tokenValidated = false;
    
    public boolean tokenNotExprired(){
        return tokenValidate;
    }
    
    public boolean tokenValid(){
        return tokenValidated;
    }
    
    //VALIDATE IF TOKEN IS MADE FOR THIS ACCOUNT OR HAS NOT EXPIRED
    public void validate(Integer timeInSec){
        tokenValidate = false;
        
        System.out.println("found account:  " + getAccountCheck() );
        //System.out.println("Requested by:  " + customerRequestingOneTimeTokken + "Being used by:  " + customerUsingOneTimeTokken);
        
        //check the current date again the date it was generated.. 15 minutes and still valid
        
        
        System.out.println("Before condition" + timeInSec + currentStatus + dateStamp + getToDaysDate());
        if(timeInSec < 900 && "unused".equals(currentStatus) && dateStamp.equals(getToDaysDate())){
            System.out.println("Token:  "+getReturnToken()+" is VALID:  ");
            tokenValidate = true;
            tokenValidated = true;
            tokkenUsed(getReturnToken(), getAccountCheck() );
            
        }else{
            tokenValidate = false;
            tokenValidated = false;
            System.out.println("Tokken:  " + getReturnToken() + "  Expired");
        }
        
    }
    
    //DELETE THIS DO THIS INSIDE AS SUB QUERY
    public void convertingTimeToSeconds(String time){
        try {
            Class.forName(DATABASE_DRIVER);

            //System.out.println("Establishing connection");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            statement = connection.createStatement();
            
            ResultSet rs = statement.executeQuery("SELECT TIME_TO_SEC('"+time+"') AS timeInSec"); 
            
            
           // System.out.println("Query Exe" + rs.toString());

            while (rs.next()) {
                
                Integer timeInSec = rs.getInt("timeInSec");
                
                
                validate(timeInSec);
                
                
                //System.out.println("Time In Seconds:  " + timeInSec);
                
                 return;
              
            }
         
            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, "Connection to the Database Failed! \nMake sure your database is running");
            //se.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException se2) {
                
                 JOptionPane.showMessageDialog(null, " Error in hereConnection to the Database Failed! \nMake sure your database is running");
            
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        
        }
    
    }
    
    //PART OF SUB QUERY
    public void validateTimeStamp(String timeStamp){
        try {
            Class.forName(DATABASE_DRIVER);

            //System.out.println("Establishing connection");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            //System.out.println("URL:  " + URL );

           // Statement s = null;
            
           // System.out.println("TimeStamp:  " + timeStamp);
            statement = connection.createStatement();
            
            ResultSet rs = statement.executeQuery("SELECT SUBTIME(current_time, '"+timeStamp+"') AS timeD"); 
            
            
           // System.out.println("Query Exe" + rs.toString());

            while (rs.next()) {
                
                String timeDifference = rs.getString("timeD");
                
                convertingTimeToSeconds(timeDifference);
                
                //System.out.println("Time difference:  " + timeDifference);
                
                 return;
              
            }
           
            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, "Connection to the Database Failed! \nMake sure your database is running");
            //se.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException se2) {
                
                 JOptionPane.showMessageDialog(null, " Error in hereConnection to the Database Failed! \nMake sure your database is running");
            
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        
        }
    
    }
    
    
    
    private static Integer tokkenGernated;
    private static String currentStatus;
    private static String dateStamp;
    private static String timeStamp;
    
    public static Integer getReturnToken(){
        return theTokken;
    }
    
    public static String getReturnCurrentStatuc(){
    
        return currentStatus;
    }
    
    public static String getReturnDateStamp(){
        return dateStamp;
    }
    
    public static String getReturnTimeStamp(){
        return timeStamp;
    
    }
    
    public static Integer getAccountCheck(){
        return checkingTokkenCustomerAccount;
    }
    public void checkingTokkenCustomerAccount(Integer token, Integer customerAccountNumber){
        //the exact tokken + account number
        try {
            Class.forName(DATABASE_DRIVER);

           // System.out.println("Establishing connection");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_URL, DATABASE_PASSWORD);
            //System.out.println("URL:  " + URL );

           // Statement s = null;
            
            
            statement = connection.createStatement();
            
            //select account number as a foreign key 
            ResultSet rs = statement.executeQuery("SELECT * FROM ATMCARDTOKEN where tokken =  "+token+" AND accountNumber ="+customerAccountNumber+""); 
            
           // System.out.println("Query Exe" + rs.toString());

            while (rs.next()) {
                
                //System.out.println("Duplicate:  " + rs.getInt("tokken"));
                theTokken = rs.getInt("tokken");
                currentStatus = rs.getString("Status");
                timeStamp = rs.getString("TimeStamp");
                dateStamp = rs.getString("DateStamp");
                checkingTokkenCustomerAccount = rs.getInt("accountNumber");
                //validate();
               // System.out.println("Passed");
               //validateTimeStamp(timeStamp);
                 return;
              
            }
            
            if (!rs.next()){
               tokenValidated = false;

                //if no results tokken is not valid
                 System.out.println("Tokken No valid for Account Number");
            }

            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, "Connection to the Database Failed! \nMake sure your database is running");
            //se.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException se2) {
                
                 JOptionPane.showMessageDialog(null, " Error in hereConnection to the Database Failed! \nMake sure your database is running");
            
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        
        }
    
    }
    
    private static Integer theTokken;
    
    
    //this will be called when we want tooooo use the tokken
    public void checkingForTokkenValidity(Integer customerAccountNumber, Integer token){
        //the exact tokken + account number
        tokenValidated = false;
        try {
            Class.forName(DATABASE_DRIVER);

           // System.out.println("Establishing connection");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            //System.out.println("URL:  " + URL );

           // Statement s = null;
            
            
            statement = connection.createStatement();
            
            //select account number as a foreign key 
            //the the exact customer plus the tokken assigned to them
             ResultSet rs = statement.executeQuery("SELECT * FROM ATMCARDTOKEN where tokken =  "+token+" AND accountNumber ="+customerAccountNumber+""); 
            
           // System.out.println("Query Exe" + rs.toString());

            while (rs.next()) {
                
                //System.out.println("Duplicate:  " + rs.getInt("tokken"));
                theTokken = rs.getInt("tokken");
                currentStatus = rs.getString("Status");
                timeStamp = rs.getString("TimeStamp");
                dateStamp = rs.getString("DateStamp");
                checkingTokkenCustomerAccount = rs.getInt("accountNumber");
                tokenValidated = true;
               // System.out.println("Passed");
               validateTimeStamp(timeStamp);
                 return;
              
            }
            
            if (!rs.next()){
                //if no results tokken is not valid
                tokenValidated = false;
                 System.out.println("Tokken Not valid!!");
            }

            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, "Connection to the Database Failed! \nMake sure your database is running");
            //se.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException se2) {
                
                 JOptionPane.showMessageDialog(null, " Error in hereConnection to the Database Failed! \nMake sure your database is running");
            
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        
        }
    
    }
    
    String smsFirstName,smsLastName, smsPhoneNumber;
    
    int smsToken, smsAccountNumber;
    public void sendSMStoClient(Integer customerAccountNumber, Integer token){
        
        try {
            Class.forName(DATABASE_DRIVER);

           // System.out.println("Establishing connection");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            //System.out.println("URL:  " + URL );

           // Statement s = null;
            
            
            statement = connection.createStatement();
            
            //select account number as a foreign key 
            //the the exact customer plus the tokken assigned to them
             ResultSet rs = statement.executeQuery("SELECT * FROM ATMCARDTOKEN AS atc, customer AS c where atc.tokken =  "+token+" AND c.accountNumber ="+customerAccountNumber+""); 
            
           // System.out.println("Query Exe" + rs.toString());

            while (rs.next()) {
                
                //System.out.println("Duplicate:  " + rs.getInt("tokken"));
                smsToken = rs.getInt("tokken");
                smsPhoneNumber = rs.getString("phone");
                smsFirstName = rs.getString("firstName");
                smsLastName = rs.getString("lastName");
                smsAccountNumber = rs.getInt("accountNumber");
                System.out.println("sending");
                
                
                
                 //return;
              
            }
            
            sms = new SendingSMS();
            
            sms.send(smsPhoneNumber, " Hello "+ smsFirstName + " " + smsLastName +", Your Onetime Cardless ATM token "+smsToken + ", Validity [ should be used in less than 15 minutes and only valid to account number "+smsAccountNumber+" ]");
           // sms.sendViaUrl(smsPhoneNumber, " Hello "+ smsFirstName + " " + smsLastName +", Your Onetime Cardless ATM token "+smsToken + ", Validity [ should be used in less than 15 minutes and only valid to account number "+smsAccountNumber+" ]");
                
            
            
            //sms.send();
            //sms.sendViaUrl();
            //  new SendingSMS().sendViaUrl(receiptPhoneNumber, " Hello "+ firstName + " " + lastName +", Your Onetime Cardless ATM token "+tokenSent + ", Validity [ should be used in less than 15 minutes and only valid to account number "+accountNumber+" ]");
                

            
            
            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, "Connection to the Database Failed! \nMake sure your database is running");
            //se.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException se2) {
                
                 JOptionPane.showMessageDialog(null, " Error in hereConnection to the Database Failed! \nMake sure your database is running");
            
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        
        }
    
    }
    
    
    
    public void storeTokkenUsed(Integer tokken, Integer customerAccountNumber){
        
            try {

                Class.forName(DATABASE_DRIVER);

                //System.out.println("Establishing connection to store Tokken");
                connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

                //System.out.println("Creating statement");
                statement = connection.createStatement();
                
                //make this a view
                this.statement.executeUpdate("INSERT INTO ATMCARDTOKEN"
                        + "(tokken, status, TimeStamp, DateStamp, accountNumber)"
                        + "VALUES"
                        + "(" + tokken + ", 'unused' " + ", NOW()"+ ", NOW() ," + customerAccountNumber+ ")");
                
                sendSMStoClient(customerAccountNumber, tokken);
                
               
                statement.close();
                connection.close();
                

            } catch (SQLException se) {
                JOptionPane.showMessageDialog(null, "Connection to the Database Failed! \nMake sure your database is running");
                //se.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                try {
                    if (statement != null) {
                        statement.close();
                    }
                } catch (SQLException se2) {

                }

                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }

      }
    
    
    public void checkForAlreadyGeneratedTokken(Integer token, Integer customerAccountNumber, String phoneNumberr){
        try {
            Class.forName(DATABASE_DRIVER);

            //System.out.println("Establishing connection");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            //System.out.println("URL:  " + URL );

           // Statement s = null;
            
            
            statement = connection.createStatement();
            
            ResultSet rs = statement.executeQuery("SELECT * FROM ATMCARDTOKEN AS act, customer AS c where c.accountNumber = act.accountNumber AND act.tokken =  "+token+""); 
            
           // System.out.println("Query Exe" + rs.toString());

            while (rs.next()) {
                
                // System.out.println("Duplicate:  " + rs.getInt("tokken"));
                 //keep generating until the value is different in the database
                 runThis = true;
                 generateToken(customerAccountNumber, phoneNumberr);
                 return;
              
            }
            
            if (!rs.next()){
                //send message to the client
                //sms = new SendingSMS();
                //sms.send(phoneNumberr, ""+token);
                
                runThis = false;
                tokkenGernated = token;
                
                System.out.println("token generated: " + token);
                
                storeTokkenUsed(getTokkenGenerated(), customerAccountNumber);
                
               // System.out.println("No duplicate... Proceed to giving the tokken:  " + getTokkenGenerated());
            }

            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, "Connection to the Database Failed! \nMake sure your database is running");
            //se.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException se2) {
                
                 JOptionPane.showMessageDialog(null, " Error in hereConnection to the Database Failed! \nMake sure your database is running");
            
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        
        }
    
    }

    @Override
    protected void validateLoginDetails() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void newSessionValue(Object sessionValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected ArrayList<Object> currentSession() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

}
