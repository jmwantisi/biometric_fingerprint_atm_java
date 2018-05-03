/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankserver.network;

import bankserver.ServerDisplay;
import bankserver.services.ATMService;
import bankserver.services.DeskEmployeeService;
import bankserver.services.TokenGenerator;
import java.io.IOException;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.SwingUtilities;

/**
 *
 * @author jmwantisi
 */
public class ServiceNetworkSession implements Runnable {

    private ObjectOutputStream streamsToClient;
    private ObjectInputStream streamsFromClient;
    private Socket connectionSocket; //sets up connection between computers
    private ServerDisplay serverProcessesui;
    private static boolean serverRunning;
    private ATMService atmSevice;
    private int CLIENT_CONNECTION = 1;
    private static InetAddress serverIP;
    private TokenGenerator tokenGenerator;

    //type of clients
    private final int ATM_CLIENT = 1;
    private final int DESK_EMPLOYEE_CLIENT = 2;
    private final int SYSTEM_ADMINISTRATOR_CLIENT = 3;

    //type of ATM operations/transactions
    private final int VALIDATE_CARD = 1;
    private final int VALIDATE_FINGERPRINT = 10;
    private final int PIN_VALIDATION_REQUEST = 12;
    private final int WITHDRAWAL_REQUEST = 13;
    private final int FUND_TRANSFER_REQUEST = 14;

    private final int PAY_ESCOM = 30;
    private final int PAY_WATERBOARD = 31;
    private final int PAY_MASM = 32;
    private final int PAY_DSTV = 33;
    private final int BALANCE_REQUEST = 34;
    private final int REQUEST_STATEMENT = 35;
    private final int FUND_TRANSFER_OUTSIDE = 41;
    private final int CARDLESS_REQUEST = 42;
    private final int CARDlLESS_TOKEN_USAGE_REQUEST = 43;
    private final int ENROL_NEW_CUSTOMER_DETAILS = 51;
    private final int FINGER_PRINT_ENROL = 52;

    //request from 51
    private int INVALID_CARD = 2;

    //plus the service you would like to run
    public ServiceNetworkSession(Socket s, boolean running) throws UnknownHostException {
        this.atmSevice = new ATMService();
        serverIP = InetAddress.getByName("NAT");

        connectionSocket = s;

        serverRunning = running;
        if (serverRunning == true) {
            //do nothing
        } else {
            serverRunning = true;
            serverProcessesui = new ServerDisplay();
        }

        responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "connected");

    }

    //set up and run the server
    public void startConnection() {
        try {
            // 100(backLog) aka queue lenght.. limits number of people connected to the server
            //port 6789

            while (true) {
                try {

                    responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "session opened for user");
                    setupConnectionStreams();
                    serverResponse();
                } catch (EOFException eofException) {
                    responseFromClient("\n Server ended the connection");
                } finally {
                    closeConnection();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //pathways for connection - get stream to send and receive data
    public void setupConnectionStreams() throws IOException {
        streamsToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
        streamsToClient.flush(); // removes remaining data from a previous connection
        streamsFromClient = new ObjectInputStream(connectionSocket.getInputStream());
    }

    //these two methods should constructed in the ATMService class
    public void logInfo(String log, int entityID) {
    }

    public void logTransaction(String log, int accountNumber) {
    }
    private static int cardNo;

    public void serverResponse() throws IOException {
        messageType(CLIENT_CONNECTION);
        String message = "Connected to Server";

        sendRequestToClient(message);
        //server logs, and transactions go here

        do {
            //have conversation
            try {
                //First tell the server what type of client you are// then the request you want from the server
                int clientType = (int) streamsFromClient.readObject(); //first called to determine which of the request to be made

                //services access by ATM clients only
                switch (clientType) {

                    case DESK_EMPLOYEE_CLIENT:

                        System.out.println("desk client service reach");

                        int requestTypeOnDEC = (int) streamsFromClient.readObject();

                        System.out.println("request type: " + requestTypeOnDEC);
                        
                                
                                        
                                        if (requestTypeOnDEC == 3000) {
                            
                                             DeskEmployeeService sendFingerprints = new DeskEmployeeService();
                                            sendFingerprints.transactionLogs();
                                             messageType(444);
                                             
                                             sendRequestToClient(sendFingerprints.getAccountTransACtionAccount());
                                             sendRequestToClient(sendFingerprints.getTransactionName());
                                             sendRequestToClient(sendFingerprints.getTranactionDate());
                                             sendRequestToClient(sendFingerprints.getTransactionTime());
                                             
                                    
                            

                        }
                                
                                if (requestTypeOnDEC == 200) {
                            
                            System.out.println("edit customer details");
                            int accountNumber = (int) streamsFromClient.readObject();
                            
                            String name = (String) streamsFromClient.readObject();
                            double amount = (double) streamsFromClient.readObject();
                            
                                    employeeMakeDeposit(accountNumber, name, amount);
                            

                        }
                                
                         
                        if (requestTypeOnDEC == 90) {
                            
                            System.out.println("edit customer details");
                            int accountNumber = (int) streamsFromClient.readObject();
                            
                            String firstName = (String) streamsFromClient.readObject();
                            String lastName = (String) streamsFromClient.readObject();
                            String dob = (String) streamsFromClient.readObject();
                            String phone = (String) streamsFromClient.readObject();
                            String email = (String) streamsFromClient.readObject();
                            String address = (String) streamsFromClient.readObject();
                            
                            //update successful
                           
                            
                            DeskEmployeeService sendFingerprints = new DeskEmployeeService();
                            
                            sendFingerprints.updateCustomerBalance(accountNumber, firstName, lastName, dob, phone, email, address);
                            
                            messageType(20);
                            sendRequestToClient("Account Details Updated");
                            
                            

                        }
                        
                        
                        
                        if (requestTypeOnDEC == 72) {
                            
                            System.out.println("edit customer details");
                            int accountNumber = (int) streamsFromClient.readObject();
                            
                            System.out.println("account recieved " + accountNumber);
                            messageType(19);
                            
                            DeskEmployeeService sendFingerprints = new DeskEmployeeService();
                            
                           sendFingerprints.loadForEdit(accountNumber);
                            System.out.println("edit: " + sendFingerprints.getCustomerEditAccount() );
                            //send customer details
                            sendRequestToClient(sendFingerprints.getCustomerEditAccount());
                            sendRequestToClient(sendFingerprints.getCustomerEditFirstName());
                            sendRequestToClient(sendFingerprints.getCustomerEditLastName());
                            sendRequestToClient(sendFingerprints.getCustomerEditDOB());
                            sendRequestToClient(sendFingerprints.getCustomerEditPhoneNumber());
                            sendRequestToClient(sendFingerprints.getCustomerEditEmail());
                            sendRequestToClient(sendFingerprints.getCustomerEditAddress());
                            

                        }
                        
                        
                        //request all customer details
                        if (requestTypeOnDEC == 70) {
                            
                            System.out.println("customer details requests");
                            
                            messageType(18);
                            
                            DeskEmployeeService sendFingerprints = new DeskEmployeeService();
                            
                           sendFingerprints.loadAllCustomerDetails();
                            //send customer details
                            sendRequestToClient(sendFingerprints.getCustomerAccounts());
                            sendRequestToClient(sendFingerprints.getCustomerFirstNames());
                            sendRequestToClient(sendFingerprints.getCustomerLastNames());
                            sendRequestToClient(sendFingerprints.getCustomerPhones());
                            sendRequestToClient(sendFingerprints.getCustomerBalances());
                            sendRequestToClient(sendFingerprints.getCustomerEmaails());
                            sendRequestToClient(sendFingerprints.getCustomerAddresses());
                            

                        }
                        
                        if (requestTypeOnDEC == 60) {
                            
                            int accountNumber = (int) streamsFromClient.readObject();
                            
                            messageType(14);
                            
                            DeskEmployeeService sendFingerprints = new DeskEmployeeService();
                            //sendFingerprints.loadAllFingerprints();
                           // sendRequestToClient(sendFingerprints.getCustomerDetails());
                           
                            sendRequestToClient(sendFingerprints.loadAllFingerprints(accountNumber));
                            sendRequestToClient(sendFingerprints.getCustomerName());
                            

                        }

                        if (requestTypeOnDEC == ENROL_NEW_CUSTOMER_DETAILS) {

                            System.out.println("desk client service reach request type reach");

                            String firstName = (String) streamsFromClient.readObject();
                            System.out.println("1");
                            String lastName = (String) streamsFromClient.readObject();
                            System.out.println("2");
                            String dob = (String) streamsFromClient.readObject();
                            System.out.println("3");
                            String email = (String) streamsFromClient.readObject();
                            System.out.println("4");
                            String address = (String) streamsFromClient.readObject();
                            System.out.println("5");
                            String phone = (String) streamsFromClient.readObject();
                            System.out.println("6");
                            double firstDeposit = (double) streamsFromClient.readObject();
                            System.out.println("7");

                            DeskEmployeeService enrol = new DeskEmployeeService();
                            enrol.runGenerateAccount();

                            if (enrol.accountGenerated()) {
                                // this will invoke fingerprint enrolment on the client side
                                //sendRequestToClient(enrol.generatedAccountNumber());

                                enrol.enrolCustomerDetails(enrol.generatedAccountNumber(), firstName, lastName, dob, email, address, phone, firstDeposit);
                                messageType(12);
                                sendRequestToClient(enrol.generatedAccountNumber());

                                // enrol.runGenerateCustomerATMPin(enrol.generatedAccountNumber());
                            }

                            //call database here
                        }

                        if (requestTypeOnDEC == 80) {

                            int accountNumber = (int) streamsFromClient.readObject();

                            DeskEmployeeService enrol = new DeskEmployeeService();
                            enrol.getNewAccountDetails(accountNumber);
                            
                            messageType(13);
                            sendRequestToClient(enrol.getACcountNumberGenerated());
                            sendRequestToClient(enrol.getFirstNameGenerated());
                            sendRequestToClient(enrol.getLastNameGenerated());
                            sendRequestToClient(enrol.getPinGenerated());
                            sendRequestToClient(enrol.getPhoneNumberGenerated());
                            sendRequestToClient(enrol.getBalanceGenerated());

                        }

                        if (requestTypeOnDEC == FINGER_PRINT_ENROL) {

                            int accountNumber = (int) streamsFromClient.readObject();
                            byte[] fingerprint = (byte[]) streamsFromClient.readObject();
                            String index = (String) streamsFromClient.readObject();

                            System.out.println("account numbwe: " + accountNumber + " fingerprint" + fingerprint + " index: " + index);

                            DeskEmployeeService enrol = new DeskEmployeeService();
                            enrol.enrollToDatabase(accountNumber, index, fingerprint);

                            //call database here
                        }

                        break;

                    case ATM_CLIENT:

                        //the services will be linked to this page
                        int requestType = (int) streamsFromClient.readObject();
                        
                        //request token
                        if (requestType == 100) {
                            int cardNumber = (int) streamsFromClient.readObject();

                            cardNo = cardNumber;
                            cardCheck(cardNumber);
                            atmSevice.logInfo("ATM Card Validation attempt on " + connectionSocket.getInetAddress().getHostAddress(), cardNumber);
                            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "user atm card inserted"); //validate card first

                        }
                        
                        
                        if (requestType == 51) {
                            int cardNumber = (int) streamsFromClient.readObject();
                            int tokken = (int) streamsFromClient.readObject();

                            cardNo = cardNumber;
                            
                            useToken(cardNumber, tokken);
                            
                            atmSevice.logInfo("ATM Card Validation attempt on " + connectionSocket.getInetAddress().getHostAddress(), cardNumber);
                            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "user atm card inserted"); //validate card first

                        }

                        //tell server to verify card and pin
                        if (requestType == VALIDATE_CARD) {
                            int cardNumber = (int) streamsFromClient.readObject();

                            cardNo = cardNumber;
                            cardCheck(cardNumber);
                            atmSevice.logInfo("ATM Card Validation attempt on " + connectionSocket.getInetAddress().getHostAddress(), cardNumber);
                            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "user atm card inserted"); //validate card first

                        }

                        if (requestType == VALIDATE_FINGERPRINT) {
                            boolean fValid = (boolean) streamsFromClient.readObject();

                            atmSevice.logInfo("Fingerprint verification attempt on " + connectionSocket.getInetAddress().getHostAddress(), cardNo);
                            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "user fingerprints" + fValid); //validate card first

                        }

                        if (requestType == PIN_VALIDATION_REQUEST) {
                            //verified customer card number will sent along with fingerprint

                            int pin = (int) streamsFromClient.readObject();
                            int cardNumber = (int) streamsFromClient.readObject();
                            pinCheck(pin, cardNumber);
                            atmSevice.logInfo("PIN verification attempt on " + connectionSocket.getInetAddress().getHostAddress(), cardNumber);
                            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "user entered pin"); //validate card first

                        }

                        //withdrawal reques
                        if (requestType == WITHDRAWAL_REQUEST) {
                            //verified customer card number will sent along with fingerprint
                            int cardNumber = (int) streamsFromClient.readObject();
                            double amount = (double) streamsFromClient.readObject();

                            makeWithdrawal(cardNumber, amount);

                            atmSevice.logInfo("withdrawal attempt on " + connectionSocket.getInetAddress().getHostAddress(), cardNumber);
                            //logTransaction("Debit", cardNumber);

                            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "user requested withdrawal"); //validate card first

                        }

                        //transfer
                        if (requestType == FUND_TRANSFER_REQUEST) {
                            //verified customer card number will sent along with fingerprint
                            int cardNumber = (int) streamsFromClient.readObject();
                            double amount = (double) streamsFromClient.readObject();
                            int receipint = (int) streamsFromClient.readObject();

                            makeTranfer(cardNumber, amount, receipint);
                            atmSevice.logInfo("fund transfer attempt on " + connectionSocket.getInetAddress().getHostAddress(), cardNumber);

                            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "user requested fund transfer"); //validate card first

                        }

                        if (requestType == PAY_DSTV) {
                            //verified customer card number will sent along with fingerprint
                            int cardNumber = (int) streamsFromClient.readObject();
                            double amount = (double) streamsFromClient.readObject();
                            int receipint = (int) streamsFromClient.readObject();

                            //change this to make dstv billPay
                            makeDstvPayment(cardNumber, amount, receipint);
                            atmSevice.logInfo("dstv sub payment attempt on " + connectionSocket.getInetAddress().getHostAddress(), cardNumber);
                            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "user requested fund transfer"); //validate card first

                        }

                        if (requestType == PAY_ESCOM) {
                            //verified customer card number will sent along with fingerprint
                            int cardNumber = (int) streamsFromClient.readObject();
                            double amount = (double) streamsFromClient.readObject();
                            int receipint = (int) streamsFromClient.readObject();

                            //change this to escom bill pay
                            makeEscomPayment(cardNumber, amount, receipint);
                            atmSevice.logInfo("escom bill payment attempt on " + connectionSocket.getInetAddress().getHostAddress(), cardNumber);
                            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "user requested fund transfer"); //validate card first

                        }

                        if (requestType == PAY_MASM) {
                            //verified customer card number will sent along with fingerprint
                            int cardNumber = (int) streamsFromClient.readObject();
                            double amount = (double) streamsFromClient.readObject();
                            int receipint = (int) streamsFromClient.readObject();

                            //change this to make masm billPay
                            makeMasmPayment(cardNumber, amount, receipint);
                            atmSevice.logInfo("masms sub payment attempt " + connectionSocket.getInetAddress().getHostAddress(), cardNumber);
                            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "user requested fund transfer"); //validate card first

                        }

                        if (requestType == PAY_WATERBOARD) {
                            //verified customer card number will sent along with fingerprint
                            int cardNumber = (int) streamsFromClient.readObject();
                            double amount = (double) streamsFromClient.readObject();
                            int receipint = (int) streamsFromClient.readObject();

                            //change this to make waterboard billPay
                            makeWaterBoardPayment(cardNumber, amount, receipint);
                            atmSevice.logInfo("waterboard bill payment attempt on " + connectionSocket.getInetAddress().getHostAddress(), cardNumber);
                            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "user requested fund transfer"); //validate card first

                        }

                        if (requestType == BALANCE_REQUEST) {
                            //verified customer card number will sent along with fingerprint
                            int cardNumber = (int) streamsFromClient.readObject();
                            boolean balanceRequest = (boolean) streamsFromClient.readObject();
                            getBalance(cardNumber);

                            if (balanceRequest) {
                                atmSevice.logInfo("balance equired on " + connectionSocket.getInetAddress().getHostAddress(), cardNumber);
                                responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "user requested balance"); //validate card first

                            }

                        }

                        if (requestType == REQUEST_STATEMENT) {
                            //verified customer card number will sent along with fingerprint
                            int cardNumber = (int) streamsFromClient.readObject();
                            boolean statementRequest = (boolean) streamsFromClient.readObject();

                            getStatement(cardNumber);

                            if (statementRequest) {
                                atmSevice.logInfo("statement requested on " + connectionSocket.getInetAddress().getHostAddress(), cardNumber);
                                responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "user requested bank statement"); //validate card first

                            }

                        }

                        if (requestType == FUND_TRANSFER_OUTSIDE) {
                            //verified customer card number will sent along with fingerprint
                            int cardNumber = (int) streamsFromClient.readObject();
                            double amount = (double) streamsFromClient.readObject();
                            int receipint = (int) streamsFromClient.readObject();
                            String bankName = (String) streamsFromClient.readObject();

                            //change this to make waterboard billPay
                            makeOutsideTranfer(cardNumber, amount, receipint, bankName);
                            atmSevice.logInfo("outside bank transfer attempt on " + connectionSocket.getInetAddress().getHostAddress(), cardNumber);
                            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "user requested outside fund transfer"); //validate card first

                        }
                        
                        //cardless requiesst from server

                        if (requestType == CARDLESS_REQUEST) {
                            
                            System.out.println("cardless reached");
                            //verified customer card number will sent along with fingerprint
                            int cardNumber = (int) streamsFromClient.readObject();
                            

                            generateToken(cardNumber);
                            atmSevice.logInfo("token request attempt on " + connectionSocket.getInetAddress().getHostAddress(), cardNumber);
                            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "user requested cardless atm token"); //validate card first

                        }

                        if (requestType == CARDlLESS_TOKEN_USAGE_REQUEST) {
                            //verified customer card number will sent along with fingerprint
                            int cardNumber = (int) streamsFromClient.readObject();
                            int token = (int) streamsFromClient.readObject();

                            useToken(cardNumber, token);
                            atmSevice.logInfo("token usage attempt on " + connectionSocket.getInetAddress().getHostAddress(), cardNumber);
                            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "user requested to use a cardless atm token"); //validate card first

                        }

                        break;
                }

            } catch (ClassNotFoundException classNotFoundException) {
                responseFromClient("Exception: " + classNotFoundException);

            }

        } while (!message.equals("CLIENT - END"));
    }

    public void generateToken(int cardNumber) {
       atmSevice.verifyAccountNumberTokenRequest(cardNumber);

        if (atmSevice.accountForTokenValid()) {

            tokenGenerator = new TokenGenerator();

            tokenGenerator.generateToken(cardNumber, atmSevice.getPhoneNumberOnTokenRequest());
            
            System.out.println("card number: " + cardNumber);
            messageType(12);
            sendRequestToClient("Token Request successful \n");
            sendRequestToClient(true);
            atmSevice.logInfo("cardless atm token sent to user phone number on " + connectionSocket.getInetAddress().getHostAddress(), cardNumber);

            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "cardless atm token validation successful");

        } else if (atmSevice.accountForTokenValid() == false) {
            messageType(12);
            sendRequestToClient("Token request unsuccessful, invalid account\n");
            sendRequestToClient(false);
            atmSevice.logInfo("Token request unsuccessful, invalid account number " + connectionSocket.getInetAddress().getHostAddress(), cardNumber);

            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "cardless atm token validation unsuccessful - casuse invalid token");

        }

    }
    
    public void employeeMakeDeposit(int accountNumber, String name, double amount) {
        ///makeWithdrawal(account, amount);

       atmSevice.transfer(1, amount, accountNumber);

        if (atmSevice.checkAccountRecipientExistance() == false) {

            messageType(5);
            sendRequestToClient("Account Does not exist\n");
            sendRequestToClient(false);
           responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "withdrawal failed");

        } else {
            //allow user to enter their pin
            messageType(5);
            sendRequestToClient("Deposit Successful\n");
            sendRequestToClient(true);
            atmSevice.logTransaction(new Date() + "   Bank Transfer by " + name + "   MWK +" + amount, accountNumber);
            atmSevice.logInfo("fund transfer unsuccessful on " + connectionSocket.getInetAddress().getHostAddress() + " to " + accountNumber + " amount: " + amount + " - cause: insuffiecient balance", accountNumber);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "withdrawal failed");

        }

    }


    public void useToken(int cardNumber, int token) {
        tokenGenerator = new TokenGenerator();
        tokenGenerator.checkingForTokkenValidity(cardNumber, token);

        if (tokenGenerator.tokenNotExprired() && tokenGenerator.tokenValid()) {
            messageType(2);
           
            sendRequestToClient("Token Valid \n");
            sendRequestToClient(true);

            //load fingerprints from database
            atmSevice.loadFromDatabase(cardNumber);

            //send finerprints to client atmz
            sendRequestToClient(atmSevice.getTemplateBinary());
            
            atmSevice.logInfo("cardless atm token validation successful on " + connectionSocket.getInetAddress().getHostAddress() + " token no: " + token, cardNumber);

            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "cardless atm token validation successful");

        } else if (tokenGenerator.tokenNotExprired() == false && tokenGenerator.tokenValid() == false) {
            messageType(2);
            sendRequestToClient("Token Not Valid \n");
            sendRequestToClient(false);
            atmSevice.logInfo("cardless atm token validation unsuccessful on " + connectionSocket.getInetAddress().getHostAddress() + " token no: " + token, cardNumber);

            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "Token request unsuccessful - casuse invalid account number");

        }  else {
            messageType(2);
            sendRequestToClient("Token Not Valid \n");
            sendRequestToClient(false);
            atmSevice.logInfo("cardless atm token validation unsuccessful on " + connectionSocket.getInetAddress().getHostAddress() + " token no: " + token, cardNumber);

            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "Token request unsuccessful - casuse invalid account number");

        
        
        }

    }

    public void makeOutsideTranfer(int accountNumber, double amount, int recipientntAccountNumber, String bankName) {
        ///makeWithdrawal(account, amount);

        atmSevice.transferOutSide(accountNumber, amount, recipientntAccountNumber, bankName);

        if (atmSevice.getSufficientFundReport() == true) {

            messageType(30);
            sendRequestToClient("Transfer Successful \n");
            sendRequestToClient(true);
            atmSevice.logInfo("outside bank fund transfer successful on " + connectionSocket.getInetAddress().getHostAddress() + " to " + recipientntAccountNumber + " amount: " + amount, accountNumber);

            atmSevice.logTransaction(new Date() + "   OUTSIDE BANK(" + bankName + ") TRANSFER: ACCN " + recipientntAccountNumber + "   MWK -" + amount, accountNumber);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "outside bank fund transfer successful");

        } else {
            //allow user to enter their pin
            messageType(30);
            sendRequestToClient("Insuffient funds\n");
            sendRequestToClient(true);
            atmSevice.logInfo("outside babk fund transfer unsuccessful on " + connectionSocket.getInetAddress().getHostAddress() + " to " + recipientntAccountNumber + " amount: " + amount + " - cause: insuffiecient balance", accountNumber);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "withdrawal failed");

        }

    }

    public void makeEscomPayment(int accountNumber, double amount, int referenceNumber) {

        System.out.println("escom bill paying");
        //Escom account Number 100888888

        //change this to run a query for escom payment
        //sending account number, amount, bill reference number, plus escom account numbers
        atmSevice.transfer(accountNumber, amount, 100444444);

        if (atmSevice.getSufficientFundReport() == true) {

            messageType(5);
            sendRequestToClient("ESCOM bill payment Successful \n");
            sendRequestToClient(true);
            atmSevice.logInfo("escom bill pay successful on " + connectionSocket.getInetAddress().getHostAddress() + " amount: " + amount, accountNumber);
            atmSevice.logTransaction(new Date() + "   Bill pay for Ref No " + accountNumber + "   MWK +" + amount, 100444444);

            atmSevice.logTransaction(new Date() + "   Bill: ESCO - Ref No " + referenceNumber + "   MWK -" + amount, accountNumber);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "withdrawal successful");

        } else if (atmSevice.checkAccountRecipientExistance() == false) {

            messageType(5);
            sendRequestToClient("Account Does not exist\n");
            sendRequestToClient(true);
            atmSevice.logInfo("fund transfer unsuccessful on " + connectionSocket.getInetAddress().getHostAddress() + " to " + referenceNumber + " amount: " + amount + " - cause: account number does not exist", accountNumber);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "withdrawal failed");

        } else {
            //allow user to enter their pin
            messageType(5);
            sendRequestToClient("Insuffient funds\n");
            sendRequestToClient(true);
            atmSevice.logInfo("escom bill pay unsuccessful on " + connectionSocket.getInetAddress().getHostAddress() + " amount: " + amount + " - cause: insuffiecient balance", accountNumber);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "withdrawal failed");

        }

    }

    public void makeWaterBoardPayment(int accountNumber, double amount, int referenceNumber) {

        //WaterBoard Account Number 1001111111
        //change this to run a query for water payment
        atmSevice.transfer(accountNumber, amount, 1001111111);

        if (atmSevice.getSufficientFundReport() == true) {

            messageType(5);
            sendRequestToClient("WATERBOARD bill payment Successful \n");
            sendRequestToClient(true);

            atmSevice.logInfo("waterboard bill pay successful on " + connectionSocket.getInetAddress().getHostAddress() + " amount: " + amount, accountNumber);
            atmSevice.logTransaction(new Date() + "   Bill pay for Ref No " + accountNumber + "   MWK +" + amount, 1001111111);

            atmSevice.logTransaction(new Date() + "   Bill: WBRD - Ref No " + referenceNumber + "   MWK -" + amount, accountNumber);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "withdrawal successful");

        } else if (atmSevice.checkAccountRecipientExistance() == false) {

            messageType(5);
            sendRequestToClient("Account Does not exist\n");
            sendRequestToClient(true);
            atmSevice.logInfo("fund transfer unsuccessful on " + connectionSocket.getInetAddress().getHostAddress() + " to " + referenceNumber + " amount: " + amount + " - cause: account number does not exist", accountNumber);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "withdrawal failed");

        } else {
            //allow user to enter their pin
            messageType(5);
            sendRequestToClient("Insuffient funds\n");
            sendRequestToClient(true);
            atmSevice.logInfo("waterboard bill pay unsuccessful on " + connectionSocket.getInetAddress().getHostAddress() + " amount: " + amount + " - cause: insuffiecient balance", accountNumber);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "withdrawal failed");

        }

    }

    public void getBalance(int accountNumber) {

        double balance = atmSevice.returnCustomerABalance(accountNumber);

        messageType(10);

        sendRequestToClient(balance);

    }

    public void getStatement(int accountNumber) {

        ArrayList<String> statemeent = atmSevice.getAccountStatement(accountNumber);
        ArrayList<String> statementDates = atmSevice.getStatementDate();
        ArrayList<String> statementTimes = atmSevice.getStatementTime();

        messageType(11);

        sendRequestToClient(statemeent);
        sendRequestToClient(statementDates);
        sendRequestToClient(statementDates);

    }

    public void makeMasmPayment(int accountNumber, double amount, int referenceNumber) {

        //masm bank account 100222222
        //change this to run a query for masm payment
        atmSevice.transfer(accountNumber, amount, 100222222);

        if (atmSevice.getSufficientFundReport() == true) {

            messageType(5);
            sendRequestToClient("MASM substription payment Successful \n");
            sendRequestToClient(true);
            atmSevice.logInfo("masm bill pay successful on " + connectionSocket.getInetAddress().getHostAddress() + " amount: " + amount, accountNumber);
            atmSevice.logTransaction(new Date() + "   Bill pay for Ref No " + accountNumber + "   MWK +" + amount, 100222222);
            atmSevice.logTransaction(new Date() + "   Bill: MASM - Ref No " + referenceNumber + "   MWK -" + amount, accountNumber);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "withdrawal successful");

        } else if (atmSevice.checkAccountRecipientExistance() == false) {

            messageType(5);
            sendRequestToClient("Account Does not exist\n");
            sendRequestToClient(true);
            atmSevice.logInfo("fund transfer unsuccessful on " + connectionSocket.getInetAddress().getHostAddress() + " to " + referenceNumber + " amount: " + amount + " - cause: account number does not exist", accountNumber);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "withdrawal failed");

        } else {
            //allow user to enter their pin
            messageType(5);
            sendRequestToClient("Insuffient funds\n");
            sendRequestToClient(true);
            atmSevice.logInfo("masm bill pay unsuccessful on " + connectionSocket.getInetAddress().getHostAddress() + " amount: " + amount + " - cause: insuffiecient balance", accountNumber);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "withdrawal failed");

        }

    }

    public void makeDstvPayment(int accountNumber, double amount, int referenceNumber) {
        //100333333
        //change this to run a query for dstv payment
        atmSevice.transfer(accountNumber, amount, 100333333);

        if (atmSevice.getSufficientFundReport() == true) {

            System.out.println("funds available for transfer");
            messageType(5);
            sendRequestToClient("DSTV bill payment Successful \n");
            sendRequestToClient(true);
           atmSevice.logInfo("dstv bill pay successful on " + connectionSocket.getInetAddress().getHostAddress() + " amount: " + amount, accountNumber);
           atmSevice.logTransaction(new Date() + "   Bill pay for Ref No " + accountNumber + "   MWK +" + amount, 100333333);
           atmSevice.logTransaction(new Date() + "   Bill: DSTV - Ref No " + referenceNumber + "   MWK -" + amount, accountNumber);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "withdrawal successful");

        } else if (atmSevice.checkAccountRecipientExistance() == false) {

            messageType(5);
            sendRequestToClient("Account Does not exist\n");
            sendRequestToClient(true);
            atmSevice.logInfo("fund transfer unsuccessful on " + connectionSocket.getInetAddress().getHostAddress() + " to " + referenceNumber + " amount: " + amount + " - cause: account number does not exist", accountNumber);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "withdrawal failed");

        } else {
            //allow user to enter their pin
            messageType(5);
            sendRequestToClient("Insuffient funds\n");
            sendRequestToClient(true);
            atmSevice.logTransaction("dstv bill pay " + " amount " + amount, accountNumber);
            atmSevice.logInfo("dstv bill pay unsuccessful on " + connectionSocket.getInetAddress().getHostAddress() + " amount: " + amount + " - cause: insuffiecient balance", accountNumber);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "withdrawal failed");

        }

    }

    //sending account , amount, reciver account
    public void makeTranfer(int accountNumber, double amount, int recipientntAccountNumber) {
        ///makeWithdrawal(account, amount);

        atmSevice.transfer(accountNumber, amount, recipientntAccountNumber);

        if (atmSevice.getSufficientFundReport() == true && atmSevice.checkAccountRecipientExistance() == true && accountNumber != recipientntAccountNumber) {

            System.out.println("funds available for transfer");
            messageType(30);
            sendRequestToClient("Transfer Successful \n");
            sendRequestToClient(true);
            atmSevice.logInfo("fund transfer successful on " + connectionSocket.getInetAddress().getHostAddress() + " to " + recipientntAccountNumber + " amount: " + amount, accountNumber);
            atmSevice.logTransaction(new Date() + "   BANK TRANSFER to ACCN " + recipientntAccountNumber + "   MWK -" + amount, accountNumber);
            atmSevice.logTransaction(new Date() + "   BANK TRANSFER from ACCN " + accountNumber + "   MWK +" + amount, recipientntAccountNumber);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "withdrawal successful");

        } else if (atmSevice.checkAccountRecipientExistance() == false) {

            messageType(30);
            sendRequestToClient("Account Does not exist\n");
            sendRequestToClient(true);
            atmSevice.logInfo("fund transfer unsuccessful on " + connectionSocket.getInetAddress().getHostAddress() + " to " + recipientntAccountNumber + " amount: " + amount + " - cause: account number does not exist", accountNumber);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "withdrawal failed");

        } else if (accountNumber == recipientntAccountNumber) {
            messageType(30);
            sendRequestToClient("You can not make a transfer to yourself");
            sendRequestToClient(true);
            atmSevice.logInfo("fund transfer unsuccessful on " + connectionSocket.getInetAddress().getHostAddress() + " to " + recipientntAccountNumber + " amount: " + amount + " - cause: recipient account same as sender account", accountNumber);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "withdrawal failed");

        } else {
            //allow user to enter their pin
            messageType(30);
            sendRequestToClient("Insuffient funds\n");
            sendRequestToClient(true);
            atmSevice.logInfo("fund transfer unsuccessful on " + connectionSocket.getInetAddress().getHostAddress() + " to " + recipientntAccountNumber + " amount: " + amount + " - cause: insuffiecient balance", accountNumber);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "withdrawal failed");

        }

    }

    //Withdrawal
    public void makeWithdrawal(int account, double amount) {
        ///makeWithdrawal(account, amount);
        atmSevice.makeWithdrawal(account, amount);
        if (atmSevice.getSufficientFundReport() == true) {
            messageType(15);
            sendRequestToClient("Withdrawl successful \n");
            sendRequestToClient(true);
            atmSevice.logInfo("withdrawal successful on " + connectionSocket.getInetAddress().getHostAddress() + " amount: " + amount, account);
            atmSevice.logTransaction(new Date() + "   Virtual ATM Withdra      " + "   MWK - " + amount, account);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "withdrawal successful");

        } else if (atmSevice.getSufficientFundReport() == false) {
            //allow user to enter their pin
            messageType(15);
            sendRequestToClient("Insuffient funds\n");
            sendRequestToClient(true);
            atmSevice.logInfo("withdrawal unsuccessful on " + connectionSocket.getInetAddress().getHostAddress() + " to " + " amount: " + amount + " - cause: insuffiecient balance", account);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "withdrawal failed");

        }

    }

    public void cardCheck(int accountNo) throws IOException {

        //your problem is here
        if (atmSevice.verifyCard(accountNo) == false) {
            messageType(2);
            sendRequestToClient("Invalid ATM Card\n");
            sendRequestToClient(false);
            atmSevice.logInfo("ATM verification failed " + connectionSocket.getInetAddress().getHostAddress() + " - cause: Invalid ATM Card Inserted", accountNo);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "user atm card invalid");

        } else if (atmSevice.verifyCard(accountNo) == true) {
            //allow user to enter their pin
            messageType(2);
            sendRequestToClient("ATM valid, Please Scan Scan Your Fingerprint\n");
            sendRequestToClient(true);

            //load fingerprints from database
            atmSevice.loadFromDatabase(accountNo);

            //send finerprints to client atmz
            sendRequestToClient(atmSevice.getTemplateBinary());
            atmSevice.logInfo("ATM card verification successful on " + connectionSocket.getInetAddress().getHostAddress(), accountNo);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "user atm card valid");

        }

    }

    public void pinCheck(int pin, int cardNo) throws IOException {

        //your problem is here
        if (atmSevice.verifyPin(pin, cardNo) == false) {
            messageType(3);
            sendRequestToClient("Invalid Pin Entered\n");
            sendRequestToClient(false);
            atmSevice.logInfo("PIN code verification failed " + connectionSocket.getInetAddress().getHostAddress() + " - cause: Invalid PIN code entered", cardNo);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "user pin entered invalid");

        } else if (atmSevice.verifyPin(pin, cardNo) == true) {
            //allow user to enter their pin
            System.out.println("pin check: " + atmSevice.verifyPin(pin, cardNo));
            messageType(3);
            sendRequestToClient("Pin valid\n");
            sendRequestToClient(true);
            atmSevice.logInfo("PIN code verification successful on " + connectionSocket.getInetAddress().getHostAddress(), cardNo);
            responseFromClient("\n\n" + new Date() + " " + "media atmclient: ATMREQUEST on eth0 to" + " " + serverIP + " " + connectionSocket.getPort() + " " + "atmclient" + " [ " + connectionSocket.getInetAddress().getHostAddress() + " ] " + "user pin valid");

        }

    }

    //close streams and sockets after your are done chatting
    public void closeConnection() throws IOException {
        sendRequestToClient("Server Closing Connection...");

        try {

            streamsToClient.close();
            streamsFromClient.close();
            connectionSocket.close();
        } catch (IOException ioException) {
            ioException.getStackTrace();
        }
    }

    public void messageType(int type) {
        try {

            //After computation the server will reply with an appropiate response..
            streamsToClient.writeObject(type);
            streamsToClient.flush();

        } catch (IOException ioException) {
            serverProcessesui.geTextArea().append("Excepetion" + ioException);
        }
    }

    public void sendValidationResponse(boolean card, boolean pin) {
        try {

            //After computation the server will reply with an appropiate response..
            streamsToClient.writeObject(card);
            streamsToClient.writeObject(pin);
            streamsToClient.flush();

        } catch (IOException ioException) {
            serverProcessesui.geTextArea().append("Excepetion" + ioException);
        }
    }

    //public void mm(){}
    public void sendRequestToClient(Object message) {
        try {

            //After computation the server will reply with an appropiate response..
            streamsToClient.writeObject(message);
            streamsToClient.flush();

        } catch (IOException ioException) {
            serverProcessesui.geTextArea().append("Excepetion" + ioException);
        }
    }

    //show what the ATM user is doing in realtime
    public void responseFromClient(final String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                serverProcessesui.geTextArea().append("\n" + message);

            }
        });

        //private void 
    }

    @Override
    public void run() {
        startConnection();
    }

}
