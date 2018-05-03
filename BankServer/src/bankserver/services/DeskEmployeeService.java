/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankserver.services;

import static bankserver.services.Service.connection;
import static bankserver.services.Service.statement;
import com.digitalpersona.onetouch.DPFPFingerIndex;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPTemplate;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author jmwantisi
 */
public class DeskEmployeeService extends Service {
    private static PreparedStatement statement2 = null;
    private static int generateAccountNumber;
    private static int generateCardNumber;
    private static int generatePIN;
    private static ArrayList<Object> customerDetails;
    private static boolean runAccountGenerate = true;
    private static EnumMap<DPFPFingerIndex, DPFPTemplate> templates = null;
    private static int accountNumberGenerated;
    private static String firstNameGenerated;
    private static String lastNameGenerated;
    private static int atmPinGenerated;
    private static String phoneNumberGenerated;
    private static double balanceGenerated;
    
    private static byte[] data = null;
    private static Object index = null;
   
    
    public DeskEmployeeService() {
        super();
        currentSession = new ArrayList<>();
        customerDetails = new ArrayList();
         this.templates = new EnumMap<DPFPFingerIndex, DPFPTemplate>(DPFPFingerIndex.class);
        
        
    }
    

    @Override
    public void validateLoginDetails() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void newSessionValue(Object sessionValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Object> currentSession() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Integer generatedAccountNumber(){
        //code to generate an account and check if it was generate already
        return generateAccountNumber;
    
    }
    
    public Integer generateCardNumber(int account){
        //code to generate an card no and check if it was not generate already
        generateAccountNumber = account;
        return generateCardNumber;
    }
    
    public boolean accountGenerated(){
        return accountNotAvailable;
    }
    
    public Integer generateATMPIN(){
        return generatePIN;
    }
    
    public void enrolFingerprints(){}
    
    public void addNewCustomer(byte[] fingerprint, String firstName, String Surname, String dob, String address, String email, String phone, String accountType, double firstDeposit){
        //add the personal details along with account no, card number, and pin to database
    }
    
    public ArrayList<Object> getNewCustomer(int accountNumber){
        
        //run query to retrive details on customer 
        return customerDetails;
    }
    
    public void runGenerateAccount(){
        runAccountGenerate = true;
        //remember this will happen in the server
        // six digit code
       while(runAccountGenerate){
          Integer value = (int)(Math.random() * 100000000); //up to 9999 9999
          if (value > 9999999){
              runAccountGenerate = false;
              
              
              checkAlreadyExistAccountNumber(value);
              
              
          }
        }
    }
    
    private static boolean runPinGeneration = false;
     public void runGenerateCustomerATMPin(int accountNumber){
        runPinGeneration = true;
        //remember this will happen in the server
        // six digit code
       while(runPinGeneration){
          Integer value = (int)(Math.random() * 10000);
          if (value > 999){
              runPinGeneration = false;
              
              System.out.println("pin generated " + value + " for acc: " + accountNumber);
              addPinToCustomerATM(accountNumber, value);
              
              
          }
        }
    }
     
      public void addPinToCustomerATM(int accountNumber, int pin) {
        
        
        try {
             
            Class.forName(DATABASE_DRIVER);

           
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

            statement = connection.createStatement();
            
            
            statement.executeUpdate("INSERT INTO customeratm"
                        + "(accountNumber, atmPin, valid, numberOfFailedTrials)"
                        + "VALUES"
                         + "(" + accountNumber + ","+ pin +", 1, 0)");
            
                         getNewAccountDetails(accountNumber);
        
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
      
      
      public static Integer getACcountNumberGenerated(){
      
          return accountNumberGenerated;
      }
      
      public static String getFirstNameGenerated(){
      
          return firstNameGenerated;
      }
      
      public static String getLastNameGenerated(){
      
          return lastNameGenerated;
      }
      
      public static Integer getPinGenerated(){
          return atmPinGenerated;
      
      }
      
      public static String getPhoneNumberGenerated(){
          return phoneNumberGenerated;
      }
      
      
      public static double getBalanceGenerated(){
      
          return balanceGenerated;
      }
      
      public void getFingerPrintDetails(){
      
      
      }
      
      //update profile
      public void updateCustomerBalance(int accountNumber, String firstName, String lastName, String dob, String phone, String email, String address){
    
        try {
            Class.forName(DATABASE_DRIVER);

            System.out.println("Establishing connection");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
   
            
            System.out.println("Creating statement inside game class");
            statement = connection.createStatement();
            
            
            statement.executeUpdate("UPDATE CUSTOMER SET firstName = '"+firstName+"', lastName = '"+lastName+"', dob = '"+dob+"', phone = '"+phone+"', email = '"+email+"', address = '"+address+"' where accountNumber = "+accountNumber+""); 
             
            
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
      
      
      
       public void getAllFingerPrints(){
        
        try {
            Class.forName(DATABASE_DRIVER);

            //System.out.println("Establishing connection");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            //System.out.println("URL:  " + URL );

           // Statement s = null;
            
            
            statement = connection.createStatement();
            
            ResultSet rs = statement.executeQuery("SELECT cfb.fingerprintTemplate FROM customer AS c, customeratm AS at, customerfingerprintbinarys cfb  where c.accountNumber = at.accountNumber AND c.accountNumber "); 
            
           // System.out.println("Query Exe" + rs.toString());

            while (rs.next()) {
               accountNumberGenerated  = rs.getInt("accountNumber");
                firstNameGenerated = rs.getString("firstName");
                lastNameGenerated = rs.getString("lastName");
                atmPinGenerated = rs.getInt("atmPin");
               phoneNumberGenerated = rs.getString("phone");
               balanceGenerated = rs.getDouble("balance");
                
                
                //put them in a variables for return
              
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
       private static ArrayList<Object> accountNumber = new ArrayList<>();
       private static ArrayList<Object> firstNames = new ArrayList<>();
       private static ArrayList<Object> lastNames = new ArrayList<>();
       private static ArrayList<Object> phones = new ArrayList<>();
       private static ArrayList<Object> balances = new ArrayList<>();
       private static ArrayList<Object> emails = new ArrayList<>();
       private static ArrayList<Object> addresses = new ArrayList<>();
       
       public static ArrayList<Object> getCustomerAccounts(){
           return accountNumber;
       
       }
       
       public static ArrayList<Object> getCustomerFirstNames(){
           return firstNames;
       
       }
       
       public static ArrayList<Object> getCustomerLastNames(){
           return lastNames;
       
       }
       
       public static ArrayList<Object> getCustomerPhones(){
           return phones;
       
       }
       
       public static ArrayList<Object> getCustomerBalances(){
           return balances;
       
       }
       
       public static ArrayList<Object> getCustomerEmaails(){
           return emails;
       
       }
       
       public static ArrayList<Object> getCustomerAddresses(){
           return addresses;
       
       }
       
       private static String firstName, lastName, dob, phone, email, address;
       private static int accountNo;
       
       
       public static Integer getCustomerEditAccount(){
           return accountNo;
       
       }
       
            //edit
          public static String getCustomerEditFirstName(){
              return firstName;
          
          }
          
          public static String getCustomerEditLastName(){
              return lastName;
          }
          
          public static String getCustomerEditDOB(){
              return dob;
          }
          
          public static String getCustomerEditPhoneNumber(){
              return phone;
          }
          
          public static String getCustomerEditEmail(){
              return email;
          }
          
          public static String getCustomerEditAddress(){
              return address;
          }
          
          public void loadForEdit(int accountNumber){
            
        
        try {
            Class.forName(DATABASE_DRIVER);

            //System.out.println("Establishing connection");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            //System.out.println("URL:  " + URL );

           // Statement s = null;
            
            
            statement = connection.createStatement();
            
            ResultSet rs = statement.executeQuery("SELECT * FROM customer WHERE accountNumber = " + accountNumber+""); 
            
           // System.out.println("Query Exe" + rs.toString());

            while (rs.next()) {
                
                accountNo = rs.getInt("accountNumber");
                
                
                firstName = rs.getString("firstName");
               
                
                lastName = rs.getString("lastName");
                
                dob = rs.getString("dob");
                
                phone = rs.getString("phone");
              
                
                email = rs.getString("email");
                
                
                address = rs.getString("address");
                
              
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
       
       
       
        public void loadAllCustomerDetails(){
            accountNumber.clear();
            firstNames.clear();
            lastNames.clear();
            
            phones.clear();
            balances.clear();
            emails.clear();
            addresses.clear();
        
        try {
            Class.forName(DATABASE_DRIVER);

            //System.out.println("Establishing connection");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            //System.out.println("URL:  " + URL );

           // Statement s = null;
            
            
            statement = connection.createStatement();
            
            ResultSet rs = statement.executeQuery("SELECT * FROM customer"); 
            
           // System.out.println("Query Exe" + rs.toString());

            while (rs.next()) {
                int account = rs.getInt("accountNumber");
                accountNumber.add(account);
                
                String firstName = rs.getString("firstName");
                firstNames.add(firstName);
                
                String lastName = rs.getString("lastName");
                lastNames.add(lastName);
                
                String phone = rs.getString("phone");
                phones.add(phone);
                
                double balance = rs.getDouble("balance");
                balances.add(balance);
                
                String email = rs.getString("email");
                emails.add(email);
                
                String address = rs.getString("address");
                addresses.add(address);
                
                //put them in a variables for return
              
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
      
        private static ArrayList<Object> accountNumbrt = new ArrayList<>();
        private static ArrayList<Object> transactionName = new ArrayList<>();
        private static ArrayList<Object> date = new ArrayList<>();
        private static ArrayList<Object> time = new ArrayList<>();
        
        public static ArrayList<Object> getAccountTransACtionAccount(){
            return accountNumbrt;
        
        }
        
        public static ArrayList<Object> getTransactionName(){
            return transactionName;
        
        }
        
        public static ArrayList<Object> getTranactionDate(){
            return date;
        
        }
        
        public static ArrayList<Object> getTransactionTime(){
            return time;
        
        }
        public void transactionLogs(){
            accountNumbrt.clear();
            transactionName.clear();
            date.clear();
            time.clear();
        
        try {
            Class.forName(DATABASE_DRIVER);

            //System.out.println("Establishing connection");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            //System.out.println("URL:  " + URL );

           // Statement s = null;
            
            
            statement = connection.createStatement();
            
            ResultSet rs = statement.executeQuery("SELECT * FROM transactions"); 
            
           // System.out.println("Query Exe" + rs.toString());

            while (rs.next()) {
               
                Object acc = rs.getObject("accountNumber");
                accountNumbrt.add(acc);
                Object tra = rs.getObject("transactionName");
                transactionName.add(tra);
                Object d = rs.getObject("transactionDate");
                date.add(d);
                Object t = rs.getObject("transactionTime");
               time.add(t);
                
                
                //put them in a variables for return
              
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
      
      
      
      public void getNewAccountDetails(int account){
        
        try {
            Class.forName(DATABASE_DRIVER);

            //System.out.println("Establishing connection");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            //System.out.println("URL:  " + URL );

           // Statement s = null;
            
            
            statement = connection.createStatement();
            
            ResultSet rs = statement.executeQuery("SELECT * FROM customer AS c, customeratm AS at  where c.accountNumber = at.accountNumber AND c.accountNumber =  "+account+""); 
            
           // System.out.println("Query Exe" + rs.toString());

            while (rs.next()) {
               accountNumberGenerated  = rs.getInt("accountNumber");
                firstNameGenerated = rs.getString("firstName");
                lastNameGenerated = rs.getString("lastName");
                atmPinGenerated = rs.getInt("atmPin");
               phoneNumberGenerated = rs.getString("phone");
               balanceGenerated = rs.getDouble("balance");
                
                
                //put them in a variables for return
              
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
      
    
    
    
    private static boolean accountNotAvailable = false;
    
    public void checkAlreadyExistAccountNumber(int account){
        accountNotAvailable = false;
        try {
            Class.forName(DATABASE_DRIVER);

            //System.out.println("Establishing connection");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            //System.out.println("URL:  " + URL );

           // Statement s = null;
            
            
            statement = connection.createStatement();
            
            ResultSet rs = statement.executeQuery("SELECT * FROM customer where accountNumber =  "+account+""); 
            
           // System.out.println("Query Exe" + rs.toString());

            while (rs.next()) {
                
                
                 accountNotAvailable = false;
                 runGenerateAccount();
                 return;
              
            }
            
            if (!rs.next()){
                accountNotAvailable = true;
                generateCardNumber(account);
                System.out.println("Account generated: " + account);
                //store account generate
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
    
    public void enrolCustomerDetails(int accountNumber, String firstName, String lastName, String dob, String email, String address, String phone, double firtDeposit) {
       
        try {
             
            Class.forName(DATABASE_DRIVER);

           
           connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

            statement = connection.createStatement();
            
            
            statement.executeUpdate("INSERT INTO customer"
                        + "(accountNumber, firstName, middleName, lastName, dob, email, phone, address, accountType, balance)"
                        + "VALUES"
                        + " ( "+accountNumber+ ", '" + firstName + "', NULL , '"+ lastName +"', '"+ dob +"' ,'"+ email +"', '"+ phone +"', '"+ address +"', NULL ,"+ firtDeposit + ")");
            
            //also generate pin and insert into customerATM table and then promt for fingerprints
            runGenerateCustomerATMPin(accountNumber);
        
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
    
    
    
    
    
        public static EnumMap<DPFPFingerIndex, DPFPTemplate> getTemplates() {
        return templates;
    }

    public void enrollToDatabase(int accountNumber, String index, byte[] templateBinary) {

        try {

            Class.forName(DATABASE_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            statement2 = connection.prepareStatement("INSERT INTO customerfingerprintbinarys"
                    + "(accountNumber, fingerprintTemplate, fingerIndex)"
                    + "VALUES"
                    + "(?,?,?)");
            statement2.setInt(1, accountNumber);
            statement2.setBytes(2, templateBinary);
            statement2.setString(3, index);
            statement2.executeUpdate();
            statement2.close();
            connection.close();

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, "Connection to the Database Failed! \nMake sure your database is running");
            //se.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (statement2 != null) {
                    statement2.close();
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
    
    public void deleteFingerprint(int accountNumber, String index) {

        try {

            Class.forName(DATABASE_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            statement2 = connection.prepareStatement("DELETE FROM customerfingerprintbinarys where fingerIndex = '"+index+ "'");
            statement2.executeUpdate();

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, "Connection to the Database Failed! \nMake sure your database is running");
            //se.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (statement2 != null) {
                    statement2.close();
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
    
    public HashMap<byte[], Integer> getCustomerDetails(){
        return customers;
    }
    
    private static HashMap<byte[], Integer> customers;
    
    
    private static ArrayList<byte[]> fingerprints;
    
    public ArrayList<byte[]> getBinaryFingerprints(){
        return fingerprints;
    }
    
    public static String customer;
    
    public static String getCustomerName(){
        return customer;
    }
    
    public ArrayList<byte[]> loadAllFingerprints(int accountNumber) {
        customers = new HashMap<>();
        fingerprints = new ArrayList<>();
        customers.clear();
        fingerprints.clear();
        customer = "";
        try {
            Class.forName(DATABASE_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            statement2 = connection.prepareStatement("SELECT c.firstName, c.lastName, c.accountNumber, cfb.fingerprintTemplate, cfb.fingerIndex FROM customer AS c, customerfingerprintbinarys AS cfb where c.accountNumber = cfb.accountNumber AND c.accountNumber = "+ accountNumber +"");

            ResultSet rs = statement2.executeQuery();

            while (rs.next()) {

                 byte[] data = rs.getBytes("fingerprintTemplate");
                 fingerprints.add(data);
                 int account = rs.getInt("accountNumber");
                 customer = rs.getString("firstName") + " "+ rs.getString("lastName");

                customers.put(data, account);
                
                

             
            }

            rs.close();
            statement2.close();
            connection.close();

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, "Connection to the Database Failed! \nMake sure your database is running");
            //se.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (statement2 != null) {
                    statement2.close();
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
        return fingerprints;
    }

    public void loadFromDatabase(int accountNumber) {
        try {
            Class.forName(DATABASE_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            statement2 = connection.prepareStatement("SELECT cfb.fingerprintTemplate, cfb.fingerIndex FROM customer AS c, customerfingerprintbinarys AS cfb where c.accountNumber = cfb.accountNumber AND "
                    + " c.accountNumber  =  " + accountNumber + "");

            ResultSet rs = statement2.executeQuery();

            while (rs.next()) {

                data = rs.getBytes("fingerprintTemplate");
                index = rs.getString("fingerIndex");
                
                DPFPTemplate template = DPFPGlobal.getTemplateFactory().createTemplate();
        
                template.deserialize(data);

                //1
                if ("LEFT_THUMB".equals(index)) {
                    System.out.println("Left Thumb inserted");
                    templates.put(DPFPFingerIndex.LEFT_THUMB, template);
                }

                //2
                if ("LEFT_INDEX".equals(index)) {
                    System.out.println("left index inserted");
                    templates.put(DPFPFingerIndex.LEFT_INDEX, template);
                }

                //3
                if ("LEFT_MIDDLE".equals(index)) {
                    System.out.println("left middle inserted");
                    templates.put(DPFPFingerIndex.LEFT_MIDDLE, template);
                }

                //4
                if ("LEFT_PINKY".equals(index)) {
                    System.out.println("left pinky inserted");
                    templates.put(DPFPFingerIndex.LEFT_PINKY, template);
                }
                //4
                if ("LEFT_RING".equals(index)) {
                    System.out.println("left ring inserted");
                    templates.put(DPFPFingerIndex.LEFT_RING, template);
                }

                if ("RIGHT_INDEX".equals(index)) {
                    System.out.println("right index inserted");
                    templates.put(DPFPFingerIndex.RIGHT_INDEX, template);
                }

                if ("RIGHT_MIDDLE".equals(index)) {
                    System.out.println("right middle inserted");
                    templates.put(DPFPFingerIndex.RIGHT_MIDDLE, template);
                }

                if ("RIGHT_PINKY".equals(index)) {
                    System.out.println("Right pinky inserted");
                    templates.put(DPFPFingerIndex.RIGHT_PINKY, template);
                }

                if ("RIGHT_RING".equals(index)) {
                    System.out.println("right ring inserted");
                    templates.put(DPFPFingerIndex.RIGHT_RING, template);
                }

                if ("RIGHT_THUMB".equals(index)) {
                    System.out.println("right thumb inserted");
                    templates.put(DPFPFingerIndex.RIGHT_THUMB, template);
                }

                System.out.println("Finter print from database: " + template);

            }

            rs.close();
            statement2.close();
            connection.close();

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, "Connection to the Database Failed! \nMake sure your database is running");
            //se.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (statement2 != null) {
                    statement2.close();
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
    
    
   
}
