/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atminterface;

import java.awt.Color;
import java.io.*;
import java.net.*;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.Timer;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author jmwantisi
 */
//extends the security policies of a Server
public class NetworkToServer implements Remote {

    private final int CLIENT_TYPE = 1;
    private static String IPAddressToServer;
    private ObjectOutputStream streamsToServer; //outpur
    private ObjectInputStream streamsFromServer; //inputs
    private Socket connectionSocket;
    private String serverResponse;
    private AuthenticationProcess authentication;
    private DisplayPanel displayPanel;
    private final int FINGER_PRINT_VALIDATION_REQUEST = 10;
    private final int CARD_VALIDATION_REQUEST = 1;
    private final int PIN_VALIDATION_REQUEST = 12;
    private final int WITHDRAWAL_REQUEST = 13;
    private final int FUND_TRANSFER_REQUEST = 14;
    private final int ONE_TIME_ENTRY_STATE = 50;

    private static int cardNumber;

    public NetworkToServer() {
        super();
       
    }
    
    void connection(String ip){
        IPAddressToServer = ip;
    
    }

    public void init(DisplayPanel displayPanel, AuthenticationProcess authentication) {
        this.authentication = authentication;
        this.displayPanel = displayPanel;

    }

    //use token requested
    public void sendRequestToUtiliseToken(int acc, int token) {

        sendRequestToServer(CLIENT_TYPE);
        sendRequestToServer(51);
        sendRequestToServer(acc);
        sendRequestToServer(token);

    }

    public void sendCardLessRequest(int acc) {

        sendRequestToServer(CLIENT_TYPE);
        sendRequestToServer(42);
        sendRequestToServer(authentication.customerCardNumber(acc));
    }

    public void sendFingerprintValidation(boolean fingerprintValidation, int acc) {
        ///System.out.println("inside network server: " + fingerprintValidation);

        sendRequestToServer(CLIENT_TYPE);
        sendRequestToServer(FINGER_PRINT_VALIDATION_REQUEST);
        sendRequestToServer(fingerprintValidation);
        sendRequestToServer(acc);
    }

    //request to validate pin
    public void sendCardValidation(int card) {
        sendRequestToServer(CLIENT_TYPE);
        sendRequestToServer(CARD_VALIDATION_REQUEST);
        sendRequestToServer(authentication.customerCardNumber(card));
    }

    public void sendPinValiation(int pin, int cardNo) {
        cardNumber = cardNo;
        sendRequestToServer(CLIENT_TYPE);
        //request type
        sendRequestToServer(PIN_VALIDATION_REQUEST);
        sendRequestToServer(authentication.customerPIN(pin));
        sendRequestToServer(cardNo);

    }

    public void sendWithdrawalRequest(int account, double amount) {
        sendRequestToServer(CLIENT_TYPE);
        sendRequestToServer(WITHDRAWAL_REQUEST);
        sendRequestToServer(account);
        sendRequestToServer(amount);

    }

    public void sendOutSideBankTransferRequest(int account, int requestType, double amount, int recepient, String bankName) {
        sendRequestToServer(CLIENT_TYPE); //client type ATM
        sendRequestToServer(requestType); // request types: bill pay - escom, waterboard, masm, dstv
        sendRequestToServer(account);
        sendRequestToServer(amount);
        sendRequestToServer(recepient);
        sendRequestToServer(bankName);

    }

    public void sendFundTranferRequest(int account, double amount, int recepient) {
        sendRequestToServer(CLIENT_TYPE);
        sendRequestToServer(FUND_TRANSFER_REQUEST);
        sendRequestToServer(account);
        sendRequestToServer(amount);
        sendRequestToServer(recepient);

    }

    public void sendBillPayRequest(int account, int requestType, double amount, int recepient) {
        sendRequestToServer(CLIENT_TYPE); //client type ATM
        sendRequestToServer(requestType); // request types: bill pay - escom, waterboard, masm, dstv
        sendRequestToServer(account);
        sendRequestToServer(amount);
        sendRequestToServer(recepient);

    }

    public void sendCardlessTokenRequest(int account, int requestType, int token, String phoneNumber) {
        sendRequestToServer(CLIENT_TYPE); //client type ATM
        sendRequestToServer(requestType); // request types: bill pay - escom, waterboard, masm, dstv
        sendRequestToServer(account);
        sendRequestToServer(token);
        sendRequestToServer(phoneNumber);

    }

    public void sendRequestToUtiliseToken(int account, int token, int requestType) {
        sendRequestToServer(CLIENT_TYPE); //client type ATM
        sendRequestToServer(requestType); // request types: bill pay - escom, waterboard, masm, dstv
        sendRequestToServer(account);
        sendRequestToServer(token);

    }

    private static double customerBankBalance;
    private static ArrayList<String> statement;

    public void setAccountBalance(double balance) {
        customerBankBalance = balance;

    }

    public double getAccountBalance() {
        return customerBankBalance;
    }
    private static ArrayList dates;
    private static ArrayList times;

    private void setStatement(ArrayList<String> statement) {
        this.statement = new ArrayList<>();
        //this.statement.clear();
        this.statement = statement;

    }

    private void setDates(ArrayList<String> statement) {
        this.dates = new ArrayList<>();
        //this.statement.clear();
        this.dates = statement;

    }

    private void setTimes(ArrayList<String> statement) {
        this.times = new ArrayList<>();
        //this.statement.clear();
        this.times = statement;

    }

    public ArrayList<String> getStatement() {
        return statement;

    }

    public ArrayList<String> getStatementDates() {
        System.out.println("dates: " + dates.toString());
        return dates;

    }

    public ArrayList<String> getStatementTimes() {
        System.out.println("times: " + times.toString());
        return times;

    }

    public void sendBalanceRequest(int account, int requestType, boolean balanceRequest) {

        sendRequestToServer(CLIENT_TYPE); //client type ATM
        sendRequestToServer(requestType); // request types: bill pay - escom, waterboard, masm, dstv
        sendRequestToServer(account);
        sendRequestToServer(balanceRequest);

    }

    public void sendStatementRequest(int account, int requestType, boolean statementRequest) {

        sendRequestToServer(CLIENT_TYPE); //client type ATM
        sendRequestToServer(requestType); // request types: bill pay - escom, waterboard, masm, dstv
        sendRequestToServer(account);
        sendRequestToServer(statementRequest);

    }

    //second class responsible validating the user using there fingerprint
    public void checkCardAndPin(Object accountNumber, Object pin) {
        //request tyoe
        sendRequestToServer(CLIENT_TYPE);
        sendRequestToServer(1);
        sendRequestToServer(accountNumber);
        sendRequestToServer(pin);

    }

    public void startConnection() {
        try {

            connectToServer();
            setupConnectionStreams();
            serverResponse();

        } catch (EOFException eofException) {
            System.out.println("\n Client terminated connection");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    //connecting to the server
    private void connectToServer() throws IOException {

        System.out.println("Attempting connection... \n");
        connectionSocket = new Socket(InetAddress.getByName(IPAddressToServer), 6262);
        System.out.println("Connected to: " + connectionSocket.getInetAddress().getHostName());

    }

    private void setupConnectionStreams() throws IOException {

        streamsToServer = new ObjectOutputStream(connectionSocket.getOutputStream());
        streamsToServer.flush();
        streamsFromServer = new ObjectInputStream(connectionSocket.getInputStream());
        System.out.println("\n Streams ready");

    }

    //send requests to the server
    private void sendRequestToServer(Object request) {
        try {

            //sending message
            System.out.println("request send:  " + request);
            streamsToServer.writeObject(request);
            streamsToServer.flush();
            //showMessages("\nCLIENT -" + message);
        } catch (IOException ioException) {
            System.out.println("Error occurried while attempting to send a request");
        }
    }

    //chatting with server
    private void serverResponse() throws IOException {

        do {
            try {
                //recieved response from the server// after computations

                int responseType = (int) streamsFromServer.readObject();

                if (responseType == 1) {
                    serverResponse = (String) streamsFromServer.readObject();

                    showServerResponse(serverResponse);
                }

                //Valid or invalid card .. login validation response
                //also reply with after the token us valid
                if (responseType == 2) {
                    serverResponse = (String) streamsFromServer.readObject();
                    boolean cardVerified = (boolean) streamsFromServer.readObject();
                    System.out.println("card verified:  " + cardVerified);
                    //this should called when every is correct
                    //authentication.verificationResponseFromServer(cardVerified);
                    if (authentication.verificationResponseFromServer(cardVerified) == true) {

                        //customer has been verified 
                        authentication.verified();

                        ArrayList<byte[]> templateData = new ArrayList<>();
                        templateData = (ArrayList<byte[]>) streamsFromServer.readObject();

                        displayPanel.fingerprintPanel(templateData);
                        displayPanel.clearPinField();

                    }
                    // new FingerpintPanel(null).getFingerprintVerification();

                    //change state to allow user insert their card
                    showServerResponse(serverResponse);
                }

                if (responseType == 3) {

                    String response = (String) streamsFromServer.readObject();
                    boolean pinVerified = (boolean) streamsFromServer.readObject();
                    
                    //System.out.println("card verified:  " + pinVerified);
                    System.out.println("response: " + response);
                    //this should called when every is correct
                    //authentication.verificationResponseFromServer(cardVerified);
                    if (authentication.pinVerificationResponceFromServer(pinVerified) == true) {

                        //customer has been verified 
                        authentication.verified();

                        //show main menu, to allow user perform transaction
                        displayPanel.menuPanel(cardNumber);
                    }

                    showServerResponse(response);

                }

                if (responseType == 12) {
                    boolean accountVerified = false;
                    String response = (String) streamsFromServer.readObject();
                    accountVerified = (boolean) streamsFromServer.readObject();
                    System.out.println("account number valid:  " + accountVerified);
                    System.out.println("response: " + response);
                    //this should called when every is correct
                    //authentication.verificationResponseFromServer(cardVerified);

                    //change this code to something more appropiate for verification for just account to get token working..
                    if (authentication.accountVerificationForTokenRequest(accountVerified) == true) {

                        //customer has been verified 
                        //authentication.verified();
                        //show main menu, to allow user perform transaction
                        displayPanel.clearFields();
                        displayPanel.tokenEntry();
                        displayPanel.setState(ONE_TIME_ENTRY_STATE);
                    }

                    showServerResponse(response);

                }

                if (responseType == 4) {
                    serverResponse = (String) streamsFromServer.readObject();
                    showServerResponse(serverResponse);

                }

                if (responseType == 20) {
                    serverResponse = (String) streamsFromServer.readObject();
                    boolean withdrawalStatus = (boolean) streamsFromServer.readObject();
                    //System.out.println("withdrawal status: " + withdrawalStatus);
                    //showServerResponse(serverResponse);
                    //displayPanel.transferNoficationDisplay("      Pay MWK " + amount + " for WATERBOARD acc no. " + account + " ?");

                    displayPanel.transferNoficationDisplay("                 " + serverResponse);
                    int anotherTransfer = JOptionPane.showConfirmDialog(null, "Would like to Perform Another Transaction?", "ATM", JOptionPane.YES_NO_OPTION);

                    if (anotherTransfer == JOptionPane.YES_OPTION) {

                        if (cardNumber != 0) {
                            displayPanel.menuPanel(cardNumber);
                            displayPanel.setState(2);
                            displayPanel.performAnotherTransactionInWithdrawal();

                        }

                    } else {

                        //go to idle state
                        displayPanel.idlePanel();

                        displayPanel.setState(0);

                    }

                }

                if (responseType == 19) {
                    serverResponse = (String) streamsFromServer.readObject();
                    boolean withdrawalStatus = (boolean) streamsFromServer.readObject();
                    //System.out.println("withdrawal status: " + withdrawalStatus);
                    //showServerResponse(serverResponse);
                    //displayPanel.transferNoficationDisplay("      Pay MWK " + amount + " for WATERBOARD acc no. " + account + " ?");

                    displayPanel.transferNoficationDisplay("                 " + serverResponse);
                    int anotherTransfer = JOptionPane.showConfirmDialog(null, "Would like to Perform Another Transaction?", "ATM", JOptionPane.YES_NO_OPTION);

                    if (anotherTransfer == JOptionPane.YES_OPTION) {

                        if (cardNumber != 0) {
                            displayPanel.menuPanel(cardNumber);
                            displayPanel.setState(2);
                            displayPanel.performAnotherTransactionInWithdrawal();

                        }

                    } else {

                        //go to idle state
                        displayPanel.idlePanel();

                        displayPanel.setState(0);

                    }

                }

                if (responseType == 18) {
                    serverResponse = (String) streamsFromServer.readObject();
                    boolean withdrawalStatus = (boolean) streamsFromServer.readObject();
                    //System.out.println("withdrawal status: " + withdrawalStatus);
                    //showServerResponse(serverResponse);
                    //displayPanel.transferNoficationDisplay("      Pay MWK " + amount + " for WATERBOARD acc no. " + account + " ?");

                    displayPanel.transferNoficationDisplay("                 " + serverResponse);
                    int anotherTransfer = JOptionPane.showConfirmDialog(null, "Would like to Perform Another Transaction?", "ATM", JOptionPane.YES_NO_OPTION);

                    if (anotherTransfer == JOptionPane.YES_OPTION) {

                        if (cardNumber != 0) {
                            displayPanel.menuPanel(cardNumber);
                            displayPanel.setState(2);
                            displayPanel.performAnotherTransactionInWithdrawal();

                        }

                    } else {

                        //go to idle state
                        displayPanel.idlePanel();

                        displayPanel.setState(0);

                    }

                }

                if (responseType == 17) {
                    serverResponse = (String) streamsFromServer.readObject();
                    boolean withdrawalStatus = (boolean) streamsFromServer.readObject();
                    //System.out.println("withdrawal status: " + withdrawalStatus);
                    //showServerResponse(serverResponse);
                    //displayPanel.transferNoficationDisplay("      Pay MWK " + amount + " for WATERBOARD acc no. " + account + " ?");

                    displayPanel.transferNoficationDisplay("                 " + serverResponse);
                    int anotherTransfer = JOptionPane.showConfirmDialog(null, "Would like to Perform Another Transaction?", "ATM", JOptionPane.YES_NO_OPTION);

                    if (anotherTransfer == JOptionPane.YES_OPTION) {

                        if (cardNumber != 0) {
                            displayPanel.menuPanel(cardNumber);
                            displayPanel.setState(2);
                            displayPanel.performAnotherTransactionInWithdrawal();

                        }

                    } else {

                        //go to idle state
                        displayPanel.idlePanel();

                        displayPanel.setState(0);

                    }

                }

                //withdrawal
                if (responseType == 15) {
                    serverResponse = (String) streamsFromServer.readObject();
                    boolean withdrawalStatus = (boolean) streamsFromServer.readObject();
                    //System.out.println("withdrawal status: " + withdrawalStatus);
                    //showServerResponse(serverResponse);
                    //displayPanel.transferNoficationDisplay("      Pay MWK " + amount + " for WATERBOARD acc no. " + account + " ?");

                    displayPanel.amountNotification("                 " + serverResponse);
                    int anotherTransfer = JOptionPane.showConfirmDialog(null, "Would like to Perform Another Transaction?", "ATM", JOptionPane.YES_NO_OPTION);

                    if (anotherTransfer == JOptionPane.YES_OPTION) {

                        if (cardNumber != 0) {
                            displayPanel.menuPanel(cardNumber);
                            displayPanel.setState(2);
                            displayPanel.amountNotification("                Select Or Enter Amount");

                        }

                    } else {

                        //go to idle state
                        displayPanel.idlePanel();

                        displayPanel.setState(0);

                    }

                }

                //transfer
                if (responseType == 30) {
                    serverResponse = (String) streamsFromServer.readObject();
                    boolean withdrawalStatus = (boolean) streamsFromServer.readObject();
                    //System.out.println("withdrawal status: " + withdrawalStatus);
                    //showServerResponse(serverResponse);

                    displayPanel.performTransfer("" + serverResponse);
                    //displayPanel.amountNotification("                 " +serverResponse);
                    int anotherTransfer = JOptionPane.showConfirmDialog(null, "Would like to Perform Another Transaction?", "ATM", JOptionPane.YES_NO_OPTION);

                    if (anotherTransfer == JOptionPane.YES_OPTION) {

                        if (cardNumber != 0) {
                            
                            displayPanel.menuPanel(cardNumber);
                            displayPanel.setState(2);
                            //displayPanel.transferAmountReset();
                            displayPanel.clearAmountTransfer();
                            
                            //displayPanel.outsideBankAmountDisplay();
                             displayPanel.resetTransferBill();
                             
                             displayPanel.resetDstvBill();
                            displayPanel.resetEscomBill();
                            displayPanel.resetWaterBill();
                            displayPanel.resetMasmBill();
                            displayPanel.resetDstvBill();
                         

                        }

                    } else {

                        //go to idle state
                        displayPanel.idlePanel();

                        displayPanel.setState(0);

                    }

                }
                
                if (responseType == 5) {
                    serverResponse = (String) streamsFromServer.readObject();
                    boolean withdrawalStatus = (boolean) streamsFromServer.readObject();
                    //System.out.println("withdrawal status: " + withdrawalStatus);
                    //showServerResponse(serverResponse);

                    displayPanel.transferNoficationDisplay("" + serverResponse);
                    //displayPanel.amountNotification("                 " +serverResponse);
                    int anotherTransfer = JOptionPane.showConfirmDialog(null, "Would like to Perform Another Transaction?", "ATM", JOptionPane.YES_NO_OPTION);

                    if (anotherTransfer == JOptionPane.YES_OPTION) {

                        if (cardNumber != 0) {
                            
                            displayPanel.menuPanel(cardNumber);
                            displayPanel.setState(2);
                            //displayPanel.transferAmountReset();
                            displayPanel.clearAmountTransfer();
                            //displayPanel.amountDisplay();
                            
                            displayPanel.resetDstvBill();
                            displayPanel.resetEscomBill();
                            displayPanel.resetWaterBill();
                            displayPanel.resetMasmBill();
                            displayPanel.resetDstvBill();
                            //displayPanel.outsideBankAmountDisplay();
                            displayPanel.resetTransferBill();
                            
                            
                            
                            if (displayPanel.outsideTransferReset()) {
                                
                                
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.blue);
                            
                            

                        } else {
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.blue);
                        }
                            
                            
                            if (displayPanel.resetTransfer()) {
                                
                                
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.blue);
                            
                            

                        } else {
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.blue);
                        }
                            
                            
                            
                            
                            if (displayPanel.dstvReset() == true) {

                               displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.blue);
                              
                            

                        } else {
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.blue);
                        }
                            
                            if (displayPanel.escomReset()== true) {

                               
                                displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.blue);
                                
                           

                        } else {
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.blue);
                        }
                            
                            if (displayPanel.waterBoardReset()== true) {

                               
                               displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.blue);
                               
                            

                        } else {
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.blue);
                        }
                            
                            if (displayPanel.masmReset()== true) {

                              displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.blue);
                              
                            
                           

                        } else {
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.blue);
                        }

                        }

                    } else {

                        //go to idle state
                        displayPanel.idlePanel();

                        displayPanel.setState(0);

                    }

                }

                //give every panel it own response
                //Withdrawal
                if (responseType == 99) {
                    serverResponse = (String) streamsFromServer.readObject();
                    boolean withdrawalStatus = (boolean) streamsFromServer.readObject();
                    //System.out.println("withdrawal status: " + withdrawalStatus);
                    //showServerResponse(serverResponse);

                    System.out.println(" Response succefful bill payment " + withdrawalStatus);

                    if (withdrawalStatus == true) {
                        if (displayPanel.dstvReset()) {
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.blue);

                        } else {
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.blue);
                        }
                    } else if (withdrawalStatus == false) {
                        if (displayPanel.dstvReset()) {
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.red);

                        } else {
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.red);
                        }

                    }
                    
                    
                    
                    if (withdrawalStatus == true) {
                        if (displayPanel.escomReset()) {
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.blue);

                        } else {
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.blue);
                        }
                    } else if (withdrawalStatus == false) {
                        if (displayPanel.escomReset()) {
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.red);

                        } else {
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.red);
                        }

                    }
                    
                    if (withdrawalStatus == true) {
                        if (displayPanel.waterBoardReset()) {
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.blue);

                        } else {
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.blue);
                        }
                    } else if (withdrawalStatus == false) {
                        if (displayPanel.waterBoardReset()) {
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.red);

                        } else {
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.red);
                        }
                        
                        if (withdrawalStatus == true) {
                        if (displayPanel.dstvReset()) {
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.blue);

                        } else {
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.blue);
                        }
                    } else if (withdrawalStatus == false) {
                        if (displayPanel.masmReset()) {
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.red);

                        } else {
                            displayPanel.transferNoficationDisplay("" + serverResponse).setForeground(Color.red);
                        }

                    }

                    }

                    // displayPanel.amountNotification("                 " +serverResponse);
                    int anotherTransfer = JOptionPane.showConfirmDialog(null, "Would like to Perform Another Transaction?", "ATM", JOptionPane.YES_NO_OPTION);

                    if (anotherTransfer == JOptionPane.YES_OPTION) {

                        if (cardNumber != 0) {
                            displayPanel.menuPanel(cardNumber);
                            displayPanel.setState(2);
                            displayPanel.performAnotherTransactionInWithdrawal();
                            displayPanel.clearFundTransferField();

                            displayPanel.resetDstvBill();
                            displayPanel.resetEscomBill();
                            displayPanel.resetWaterBill();
                            displayPanel.resetMasmBill();
                            displayPanel.resetDstvBill();
                            displayPanel.resetTransferBill();

                            displayPanel.clearAmountTransfer();

                            if (displayPanel.dstvReset()) {
                                displayPanel.clearAferResetFundTransferField();

                            } else {
                                displayPanel.clearFundTransferField();
                            }

                            if (displayPanel.masmReset()) {
                                displayPanel.clearAferResetFundTransferField();

                            } else {
                                displayPanel.clearFundTransferField();
                            }

                            if (displayPanel.waterBoardReset()) {
                                displayPanel.clearAferResetFundTransferField();

                            } else {
                                displayPanel.clearFundTransferField();
                            }

                            if (displayPanel.escomReset()) {
                                displayPanel.clearAferResetFundTransferField();

                            } else {
                                displayPanel.clearFundTransferField();
                            }

                                            //displayPanel.amountDisplay();
                        }

                    } else {

                        //go to idle state
                        displayPanel.idlePanel();

                        displayPanel.setState(0);

                    }

                }

                //retrive customer balance
                if (responseType == 10) {

                    double balance = (double) streamsFromServer.readObject();
                    setAccountBalance(balance);

                }

                if (responseType == 11) {

                    ArrayList<String> statement = (ArrayList<String>) streamsFromServer.readObject();
                    ArrayList<String> dates = (ArrayList<String>) streamsFromServer.readObject();
                    ArrayList<String> times = (ArrayList<String>) streamsFromServer.readObject();
                    setStatement(statement);

                }

            } catch (ClassNotFoundException classNotFoundException) {
                System.out.println("Error object type");

            }
        } while (!serverResponse.equals("SERVER - END"));
    }

    private void showServerResponse(final String m) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //add
                JOptionPane.showMessageDialog(null, m);
            }
        });

    }

    //close streams and sockets
    private void closeConnection() {
        System.out.println("\n connection closing");

        try {
            streamsToServer.close();
            streamsFromServer.close();
            connectionSocket.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();

        }
    }

}
