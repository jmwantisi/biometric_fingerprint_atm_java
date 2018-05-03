/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankserver.services;

import com.digitalpersona.onetouch.DPFPFingerIndex;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPTemplate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.EnumMap;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author jmwantisi
 */
public class ATMService extends Service {

    private static double balance = 0.00;
    private static double receiptientBalance = 0.00;
    private int accountNumber = 0;
    private String firstName;
    private String lastName;
    private int token;
    private boolean validCardNumber = false;
    private boolean validPinNumber = false;
    private boolean validFingerpint = false;
    private boolean pinCheck = false;
    private boolean cardCheck = false;
    private static int cardNumber;
    private static int pinNumber;
    private static int account;
    private static boolean sufficientFunds = false;

    private static Connection connection = null;
    private static ResultSet rs;
    private static PreparedStatement fstatement = null;
    
    private static EnumMap<DPFPFingerIndex, DPFPTemplate> templates = null;

    private static byte[] data = null;
    private static Object index = null;
    private static ArrayList<byte[]> binaryFingerprintData = null;
    
    

    public ATMService() {
        super();
        this.currentSession = new ArrayList();
        this.templates = new EnumMap<DPFPFingerIndex, DPFPTemplate>(DPFPFingerIndex.class);
        this.binaryFingerprintData = new ArrayList<>();
    }

    @Override
    public void validateLoginDetails() {

    }

    //this before every transaction
    @Override
    public void newSessionValue(Object sessionValue) {
        //userID and index 0, etc
        if (currentSession().isEmpty()) {
            //no session started create a new session
            currentSession().add(0, sessionValue); //first value is cardNumber
        } else {
            currentSession().add(sessionValue);
        }
    }

    @Override
    public ArrayList<Object> currentSession() {
        return currentSession;
    }

    /*
    public boolean verifyCard(int cardNo){
        //runquery to check cardValidity.. return true or false
        
        return validCardNumber;
    } */
    public boolean verifyPIN(int pin) {
        //call verifycardmethod here and run query

        return validPinNumber;
    }

    public boolean verifyFingerprint(byte[] finger) {
        //call verify card and verify pin here here and run query to find fingerprint match
        //if all three check out the user has been fully authenticated

        //and call session start
        return validFingerpint;

    }

    //return customers balance
    public double getBalance() {
        //run query and return the available balance

        return balance;
    }
    
    public double getRecipientBalance(){
    
        return receiptientBalance;
    }
    
    public static boolean getSufficientFundReport(){
        return sufficientFunds;
    }

    public void withdrawalCash(int accountNumber, double amount) {
        //run query
        if (amount <= getBalance()) {
            //make report to the client
            
            sufficientFunds = true;
            //update account
            double newBalance = getBalance() - amount;
            updateCustomerBalance(accountNumber, newBalance);

            //suffient balance .. make transfer by query by update send balance by amount, and recipeint account number
        } else if (amount > getBalance()) {
            //Insuffient balance
            sufficientFunds = false;
        } 

    }

    public void transferFunds(int accountNumber, double amount, int recipientntAccountNumber) {
        
        // check if the receipt account exists
        if (accountNumber != 1 && amount < getBalance() && checkRecepientBalance(recipientntAccountNumber) == true && verifyRecipient(recipientntAccountNumber) == true) {
            sufficientFunds = true;
            //exists = true;
            
            //debit sender account
            
            System.out.println("BALANCE: " + balance);
            double newBalance = balance - amount;
            updateCustomerBalance(accountNumber, newBalance);
            
            System.out.println("sender account: " + accountNumber + " balance: " + " " + getBalance() + " new balance: " + newBalance + " amount sending: " + amount);
            
            //credit recepient account
            
            //add current balance to the amount sent
            //run to check receipient balance, then send
            checkRecepientBalance(recipientntAccountNumber);
            double newBalanceReceipient = receiptientBalance + amount;
            updateCustomerBalance(recipientntAccountNumber, newBalanceReceipient);
            
            
            
            //suffient balance .. make transfer by query by update send balance by amount, and recipeint account number
        } else if (amount > getBalance()) {
            //Insuffient balance
            sufficientFunds = false;
        }else if( verifyRecipient(recipientntAccountNumber) == false){
            System.out.println("account: " + recipientntAccountNumber + " does not exist");
            exists = false;
        }
        
        
        if(verifyRecipient(recipientntAccountNumber) == true && accountNumber==1 ){
             checkRecepientBalance(recipientntAccountNumber);
            double newBalanceReceipient = receiptientBalance + amount;
            
            System.out.println("new total " + newBalanceReceipient + " Receipient Balance " + receiptientBalance + " amount sent" + amount);
            updateCustomerBalance(recipientntAccountNumber, newBalanceReceipient);
            
          //s  exists = true;
        
        } 
        

    }
    
     public void transferFundsOutSide(int accountNumber, double amount, int recipientntAccountNumber, String bankName) {
        
        // check if the receipt account exists
        if (amount < getBalance()) {
            sufficientFunds = true;
            
            //debit sender account
            
            System.out.println("BALANCE: " + balance);
            double newBalance = balance - amount;
            updateCustomerBalance(accountNumber, newBalance);
            
            System.out.println("sender account: " + accountNumber + " balance: " + " " + getBalance() + " new balance: " + newBalance + " amount sending: " + amount);
            
            sendTransferForVettings(accountNumber, amount, bankName, recipientntAccountNumber);
            
            
            
            //suffient balance .. make transfer by query by update send balance by amount, and recipeint account number
        } else if (amount > getBalance()) {
            //Insuffient balance
            sufficientFunds = false;
        }
        

    }
    
    private static boolean exists = false;
    private static ArrayList<String> name = new ArrayList<>();
    
    //confirm name before sending
    private static ArrayList<String> transferingTo(){
        return name;
    }
    public boolean checkAccountRecipientExistance(){
        return exists;
    
    }
    
    
    public void billFunds(int accountNumber, double amount, int recipientntAccountNumber, int billRefNo) {
        
        // check if the receipt account exists
        if (amount < getBalance() && checkRecepientBalance(recipientntAccountNumber) == true) {
            sufficientFunds = true;
            
            //debit sender account
            
            System.out.println("BALANCE: " + balance);
            double newBalance = balance - amount;
            updateCustomerBalance(accountNumber, newBalance);
            
            System.out.println("sender account: " + accountNumber + " balance: " + " " + getBalance() + " new balance: " + newBalance + " amount sending: " + amount);
            
            //credit recepient account
            
            //add current balance to the amount sent
            //run to check receipient balance, then send
            
            checkRecepientBalance(recipientntAccountNumber);
            double newBalanceReceipient = receiptientBalance + amount;
            updateCustomerBalance(recipientntAccountNumber, newBalanceReceipient);
            
            
            
            //suffient balance .. make transfer by query by update send balance by amount, and recipeint account number
        } else if (amount > getBalance()) {
            //Insuffient balance
            sufficientFunds = false;
        }
        

    }

    public void billPayment(int accountNumber, double amount, int recipientntAccountNumber) {
        if (amount < getBalance()) {
            sufficientFunds = true;
            double newBalance = getBalance() - amount;
            updateCustomerBalance(accountNumber, newBalance);
            //suffient balance .. make transfer by query by update send balance by amount, and recipeint account number
        } else if (amount > getBalance()) {
            //Insuffient balance
            sufficientFunds = false;
        } 

    }

    public Integer generateOneTimeATMToken(int accountNumber) {
        //send SMS from here using Ozeki
        return token;
    }

    //end session
    public boolean getPinValidity() {
        return pinCheck;
    }

    public boolean getCardValidity() {
        return cardCheck;
    }

    //maximum 3 in period of 24 hours
    public void cardPinFailedAttempts(int accountNumber) {

    }

    public boolean cardVerified() {
        return validCardNumber;
    }
    
    public boolean pinVerified(){
        return validPinNumber;
    }
    
    
    private static double accountBalance;
    public double returnCustomerABalance (int account) {
        
        
        try {
            Class.forName(DATABASE_DRIVER);

            System.out.println("Establishing connection");
            
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            
            //Statement s = null;
            System.out.println("customer account at return: " + account);

            statement = connection.createStatement();

            //System.out.println("Card Numner at selection query:  " + cardNumber + "Pin number entered: " + pin);
            ResultSet rs = statement.executeQuery("SELECT * FROM customer AS c, customerATM AS ca where c.accountNumber = ca.accountNumber AND c.accountNumber =  " + account +"");

            while (rs.next()) {
                
                
                
                accountBalance = rs.getDouble("balance");
                System.out.println("customer balance at return: " + accountBalance);
                
                //this.accountNumber = rs.getInt("accountNumber");
                
                firstName = rs.getString("firstName");
                lastName = rs.getString("lastName");
                
                

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
        
        return accountBalance;

    }
    
    
    public boolean checkRecepientBalance(int recipientntAccountNumber) {
        validCardNumber = false;
        
        try {
            Class.forName(DATABASE_DRIVER);

            System.out.println("Establishing connection");
            
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            
            //Statement s = null;

            statement = connection.createStatement();

            //System.out.println("Card Numner at selection query:  " + cardNumber + "Pin number entered: " + pin);
            ResultSet rs = statement.executeQuery("SELECT * FROM customer AS c, customerATM AS ca where c.accountNumber = ca.accountNumber AND c.accountNumber =  " + recipientntAccountNumber +"");

            while (rs.next()) {
                
                
                
                receiptientBalance = rs.getDouble("balance");
                validCardNumber = true;
                //this.accountNumber = rs.getInt("accountNumber");
                
                firstName = rs.getString("firstName");
                lastName = rs.getString("lastName");
                
                

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
        
        return validCardNumber;

    }
    
    
    
    public void sendTransferForVettings(int sendingAcc, double amount, String bankName, int recipientAcc){
    
       try {
             
            Class.forName(DATABASE_DRIVER);

           
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

            statement = connection.createStatement();
            
            
            statement.executeUpdate("INSERT INTO  outsidebanktransfer"
                        + "(bankName, receipientAccNo, transferAmount,  accountNumber)"
                        + "VALUES"
                         + "('" + bankName + "'," + recipientAcc + "," + amount + ","+ sendingAcc +")");
        
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
    
    
    public void updateCustomerBalance(int accountNumber, double newBalance){
    
        try {
            Class.forName(DATABASE_DRIVER);

            System.out.println("Establishing connection");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
   
            
            System.out.println("Creating statement inside game class");
            statement = connection.createStatement();
            
            
            statement.executeUpdate("UPDATE CUSTOMER SET balance = "+newBalance+" where accountNumber = "+accountNumber+""); 
             
            
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
    
    public void logInfo(String log, int entityID) {
        System.out.println("log name: " + log + "entity ID: " + entityID);
        try {
             
            Class.forName(DATABASE_DRIVER);

           
           connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

            statement = connection.createStatement();
            
            
            statement.executeUpdate("INSERT INTO logs"
                        + "(logName, logDate, transactionTime, logEntityID)"
                        + "VALUES"
                        + " ('" + log + "', NOW() , NOW() , "+ entityID +")");
        
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

    public void logTransaction(String log, int accountNumber) {
        
        System.out.println("log name: " + log + "entity ID: " + accountNumber);
        try {
             
            Class.forName(DATABASE_DRIVER);

           
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

            statement = connection.createStatement();
            
            
            statement.executeUpdate("INSERT INTO transactions"
                        + "(transactionName, transactionDate, transactionTime, accountNumber)"
                        + "VALUES"
                         + "('" + log + "', NOW() , NOW() ,"+ accountNumber +")");
        
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
    
    //get account statement
    private static ArrayList<String> statementDetails;
    private static ArrayList<String> statementDate;
    private static ArrayList<String> statementTime;
    
    
    public ArrayList<String> getStatementTime(){
        return statementTime;
    }
    
    public ArrayList<String> getStatementDate(){
        return statementDate;
    }
     public ArrayList<String> getAccountStatement(int accountNumber) {
        statementDetails = new ArrayList<>();
        statementDate = new ArrayList<>();
        statementTime = new ArrayList<>();
        
        statementDetails.clear();
        statementDate.clear();
        statementTime.clear();
        
        try {
            Class.forName(DATABASE_DRIVER);

            System.out.println("Establishing connection");
            
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            
            //Statement s = null;

            statement = connection.createStatement();

            //System.out.println("Card Numner at selection query:  " + cardNumber + "Pin number entered: " + pin);
            ResultSet rs = statement.executeQuery("SELECT * FROM transactions where accountNumber =  " + accountNumber +" ORDER BY transactionID desc LIMIT 10");

            while (rs.next()) {
                
                String transaction = rs.getString("transactionName");
                String date = rs.getString("transactionDate");
                String time = rs.getString("transactionTime");
                statementDetails.add(transaction);
                statementDate.add(date);
                statementTime.add(time);
                
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
        return statementDetails;
    }
     
     
     public void transferOutSide(int accountNumber, double amount, int recipientntAccountNumber, String bankName) {
        
        sufficientFunds = false;
        this.account = account;

        try {
            Class.forName(DATABASE_DRIVER);

            System.out.println("Establishing connection");
            
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            
            //Statement s = null;

            statement = connection.createStatement();

            //System.out.println("Card Numner at selection query:  " + cardNumber + "Pin number entered: " + pin);
            ResultSet rs = statement.executeQuery("SELECT * FROM customer AS c, customerATM AS ca where c.accountNumber = ca.accountNumber AND c.accountNumber =  " + cardNumber +"");

            while (rs.next()) {
                
                
                
                balance = rs.getDouble("balance");
                this.accountNumber = rs.getInt("accountNumber");
                
                firstName = rs.getString("firstName");
                lastName = rs.getString("lastName");
                
                
                System.out.println("sender account: " + accountNumber + " balance: " + balance);
                transferFundsOutSide(this.accountNumber, amount, recipientntAccountNumber, bankName);

            }

            System.out.println("Account Number:  " + accountNumber);

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
    
    //make bill payment
    
    public void transfer(int accountNumber, double amount, int recipientntAccountNumber) {
        
        sufficientFunds = false;
        this.account = account;

        try {
            Class.forName(DATABASE_DRIVER);

            System.out.println("Establishing connection");
            
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            
            //Statement s = null;

            statement = connection.createStatement();

            //System.out.println("Card Numner at selection query:  " + cardNumber + "Pin number entered: " + pin);
            ResultSet rs = statement.executeQuery("SELECT * FROM customer AS c, customerATM AS ca where c.accountNumber = ca.accountNumber AND c.accountNumber =  " + cardNumber +"");

            while (rs.next()) {
                
                
                
                balance = rs.getDouble("balance");
                this.accountNumber = rs.getInt("accountNumber");
                
                firstName = rs.getString("firstName");
                lastName = rs.getString("lastName");
                
                
                System.out.println("sender account: " + accountNumber + " balance: " + balance);
                transferFunds(this.accountNumber, amount, recipientntAccountNumber);

            }
            
            if(!rs.next()){
                transferFunds(1, amount, recipientntAccountNumber);

            
            }

            System.out.println("Account Number:  " + accountNumber);

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
    
    public void paybill(int accountNumber, double amount, int recipientntAccountNumber, int billRefNumber) {
        
        sufficientFunds = false;
        this.account = account;

        try {
            Class.forName(DATABASE_DRIVER);

            System.out.println("Establishing connection");
            
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            
            //Statement s = null;

            statement = connection.createStatement();

            //System.out.println("Card Numner at selection query:  " + cardNumber + "Pin number entered: " + pin);
            ResultSet rs = statement.executeQuery("SELECT * FROM customer AS c, customerATM AS ca where c.accountNumber = ca.accountNumber AND c.accountNumber =  " + cardNumber +"");

            while (rs.next()) {
                
                
                
                balance = rs.getDouble("balance");
                this.accountNumber = rs.getInt("accountNumber");
                
                firstName = rs.getString("firstName");
                lastName = rs.getString("lastName");
                
                
                
                billFunds(accountNumber, amount, recipientntAccountNumber, billRefNumber);

            }

            System.out.println("Account Number:  " + accountNumber);

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
   
    
    
    //withdrawal
    public void makeWithdrawal(int account, double amount) {
        
        sufficientFunds = false;
        this.account = account;

        try {
            Class.forName(DATABASE_DRIVER);

            System.out.println("Establishing connection");
            
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            
            //Statement s = null;

            statement = connection.createStatement();

            //System.out.println("Card Numner at selection query:  " + cardNumber + "Pin number entered: " + pin);
            ResultSet rs = statement.executeQuery("SELECT * FROM customer AS c, customerATM AS ca where c.accountNumber = ca.accountNumber AND c.accountNumber =  " + cardNumber +"");

            while (rs.next()) {
                
                balance = rs.getDouble("balance");
                accountNumber = rs.getInt("accountNumber");
                System.out.println("Account Number:  " + accountNumber);
                firstName = rs.getString("firstName");
                lastName = rs.getString("lastName");
                
                
                
                withdrawalCash(accountNumber, amount);

            }

            System.out.println("Account Number:  " + accountNumber);

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
    
    public static String fullName;
    public static String getFullName(){
        return fullName;
    
    }
    
    public boolean verifyPin(int pin, int cardNo) {
        System.out.println("Pin No Inside ATM service:  " + pin);

        validPinNumber = false;
        cardNumber = cardNo;
        pinNumber = pin;
        

        try {
            Class.forName(DATABASE_DRIVER);

            System.out.println("Establishing connection");
            
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            
            //Statement s = null;

            statement = connection.createStatement();

            System.out.println("Card Numner at selection query:  " + cardNumber + "Pin number entered: " + pin);
            ResultSet rs = statement.executeQuery("SELECT * FROM customer AS c, customerATM AS ca where c.accountNumber = ca.accountNumber AND c.accountNumber =  " + cardNumber + " AND ca.atmPin = "+pinNumber+"");

            while (rs.next()) {
                this.pinCheck = true;
                balance = rs.getDouble("balance");
                accountNumber = rs.getInt("accountNumber");
                System.out.println("Account Number:  " + accountNumber);
                firstName = rs.getString("firstName");
                lastName = rs.getString("lastName");
                validPinNumber = true;
                
                fullName = firstName + " " + lastName;
                        
                System.out.println("pin valid for the card: " + accountNumber);

            }

            System.out.println("Account Number:  " + accountNumber);

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

        return validPinNumber;

    }
    
    private static int receipientAccount;
    
    public boolean verifyRecipient(int acountNumber) {
        
        name = null;
        exists = false;
        receipientAccount = 0;

        try {
            Class.forName(DATABASE_DRIVER);

            System.out.println("Establishing connection");
            
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            
            //Statement s = null;

            statement = connection.createStatement();

            System.out.println("Account number at selection query:  " + acountNumber);
            ResultSet rs = statement.executeQuery("SELECT * FROM customer AS c, customerATM AS ca where c.accountNumber = ca.accountNumber AND c.accountNumber =  " + acountNumber + "");

            while (rs.next()) {
                
                receipientAccount = rs.getInt("accountNumber");
                
                String f_name = rs.getString("firstName");
                String l_name = rs.getString("lastName");
                exists = true;
                name.add(f_name);
                name.add(l_name);
                

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

        return exists;

    }

    public boolean verifyCard(int acountNumber) {
        System.out.println("card No Inside ATM service:  " + acountNumber);

        validCardNumber = false;
        accountNumber = 0;

        try {
            Class.forName(DATABASE_DRIVER);

            System.out.println("Establishing connection");
            
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            
            //Statement s = null;

            statement = connection.createStatement();

            System.out.println("Account number at selection query:  " + acountNumber);
            ResultSet rs = statement.executeQuery("SELECT * FROM customer AS c, customerATM AS ca where c.accountNumber = ca.accountNumber AND c.accountNumber =  " + acountNumber + "");

            while (rs.next()) {
                this.pinCheck = true;
                balance = rs.getDouble("balance");
                accountNumber = rs.getInt("accountNumber");
                System.out.println("Account Number:  " + accountNumber);
                firstName = rs.getString("firstName");
                lastName = rs.getString("lastName");
                phoneNumber = rs.getString("phone");
                validCardNumber = true;
                

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

        return validCardNumber;

    }
    
    private static boolean verifyAccountTokenRequest = false;
    private static int accountNumberTokenRequest;
    
    private static String phoneNumber;
    
    public static String getPhoneNumberOnTokenRequest(){
        return phoneNumber;
    }
    
    public boolean accountForTokenValid(){
    
        return verifyAccountTokenRequest;
    }
    
    public boolean verifyAccountNumberTokenRequest(int acountNumber) {
        System.out.println("card No Inside ATM service:  " + acountNumber);

        verifyAccountTokenRequest = false;
        accountNumberTokenRequest = 0;

        try {
            Class.forName(DATABASE_DRIVER);

            System.out.println("Establishing connection");
            
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            
            //Statement s = null;

            statement = connection.createStatement();

            System.out.println("Account number at selection query:  " + acountNumber);
            ResultSet rs = statement.executeQuery("SELECT * FROM customer AS c, customerATM AS ca where c.accountNumber = ca.accountNumber AND c.accountNumber =  " + acountNumber + "");

            while (rs.next()) {
               
                balance = rs.getDouble("balance");
                accountNumber = rs.getInt("accountNumber");
                System.out.println("Account Number:  " + accountNumber);
                firstName = rs.getString("firstName");
                lastName = rs.getString("lastName");
                phoneNumber = rs.getString("phone");
                verifyAccountTokenRequest = true;
                

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

        return verifyAccountTokenRequest;

    }
    
    
    

    public void loadFromDatabase(int accountNumber) {
        try {
            Class.forName(DATABASE_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            fstatement = connection.prepareStatement("SELECT cfb.fingerprintTemplate, cfb.fingerIndex FROM customer AS c, customerfingerprintbinarys AS cfb where c.accountNumber = cfb.accountNumber AND "
                    + " c.accountNumber  =  " + accountNumber + "");

            rs = fstatement.executeQuery();

            while (rs.next()) {

                data = rs.getBytes("fingerprintTemplate");
                binaryFingerprintData.add(data);
                

            }

            rs.close();
            fstatement.close();
            connection.close();

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, "Connection to the Database Failed! \nMake sure your database is running");
            //se.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (fstatement != null) {
                    fstatement.close();
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

   

    public static ArrayList<byte[]> getTemplateBinary() {
        return binaryFingerprintData;
    }
    
     public static EnumMap<DPFPFingerIndex, DPFPTemplate> getTemplates() {
        return templates;
    }

}
