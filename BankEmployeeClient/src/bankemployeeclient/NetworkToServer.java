package bankemployeeclient;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.digitalpersona.onetouch.DPFPFingerIndex;
import com.digitalpersona.onetouch.DPFPTemplate;
import java.awt.Color;
import java.io.*;
import java.net.*;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.RootPaneUI;

/**
 *
 * @author jmwantisi
 */
//extends the security policies of a Server
public class NetworkToServer extends Thread implements Remote {

    private final int CLIENT_TYPE = 2;
    private String IPAddressToServer;
    private ObjectOutputStream streamsToServer; //outpur
    private ObjectInputStream streamsFromServer; //inputs
    private Socket connectionSocket;
    private String serverResponse;
    private final int FINGER_PRINT_VALIDATION_REQUEST = 10;
    private final int CARD_VALIDATION_REQUEST = 1;
    private final int PIN_VALIDATION_REQUEST = 12;
    private final int WITHDRAWAL_REQUEST = 13;
    private final int FUND_TRANSFER_REQUEST = 14;

    private static int cardNumber;

    private Display display;

    private Thread thread;

    private static EnumMap<DPFPFingerIndex, DPFPTemplate> templates = new EnumMap<DPFPFingerIndex, DPFPTemplate>(DPFPFingerIndex.class);
    private EnumMap<DPFPFingerIndex, JCheckBox> checkBoxes = new EnumMap<DPFPFingerIndex, JCheckBox>(DPFPFingerIndex.class);

    public NetworkToServer() {
        super();
        IPAddressToServer = "localhost";
        this.setDaemon(true);

    }

    public void init() {

    }
    
    public void requestSaveEditChanges(int requestType, int accountNumber, String firstName, String lastName, String dob, String phone, String email, String address){
        //firstName //lastName dob phone email address physical address
        sendRequestToServer(CLIENT_TYPE);
        sendRequestToServer(requestType);
        
        sendRequestToServer(accountNumber);
        
        sendRequestToServer(firstName);
        sendRequestToServer(lastName);
        sendRequestToServer(dob);
        sendRequestToServer(phone);
        sendRequestToServer(email);
        sendRequestToServer(address);
    
    }

    public void requestEditRecord(int accountNumber, int requestType) {
        sendRequestToServer(CLIENT_TYPE);

        sendRequestToServer(requestType);
        sendRequestToServer(accountNumber);

    }
    
     public void requestTransActionLogs(int requestType, Display display) {
        sendRequestToServer(CLIENT_TYPE);
        sendRequestToServer(3000);
        this.display = display;

    }
    
    public void requestLogView(int accountNumber, int requestType) {
        sendRequestToServer(CLIENT_TYPE);

        sendRequestToServer(requestType);
        sendRequestToServer(accountNumber);

    }
    
    
   
    
    
    
    public void requestDeposit(int requestType, Display display, int accountNumber, String name, double amount) {

        sendRequestToServer(CLIENT_TYPE);
        sendRequestToServer(requestType);
        sendRequestToServer(accountNumber);
        sendRequestToServer(name);
        sendRequestToServer(amount);
        this.display = display;
    }

    public void requestAccountDetails(int requestType, Display display) {
        sendRequestToServer(CLIENT_TYPE);
        sendRequestToServer(requestType);
        this.display = display;

    }

    public void requestFingerprints(int requestType, Display display, int accountNumber) {

        sendRequestToServer(CLIENT_TYPE);
        sendRequestToServer(requestType);
        sendRequestToServer(accountNumber);
        this.display = display;
    }

    public void enrolNewCustomerDetails(int requestType, String firstName, String lastName, String dob, String email, String phone, String address, double firsteposit) {

        sendRequestToServer(CLIENT_TYPE);
        sendRequestToServer(requestType);

        sendRequestToServer(firstName);
        sendRequestToServer(lastName);
        sendRequestToServer(dob);
        sendRequestToServer(email);
        sendRequestToServer(address);
        sendRequestToServer(phone);
        sendRequestToServer(firsteposit); //first deposit to the bank

    }

    public void generateAccount(int acountNumber) {
        sendRequestToServer(CLIENT_TYPE);
        sendRequestToServer(80);

        sendRequestToServer(acountNumber);

    }

    public void sendFingerprintEnrolRquest(int requestType, int accountNumber, byte[] binaryTemplate, String index) {
        sendRequestToServer(CLIENT_TYPE);
        sendRequestToServer(requestType);

        sendRequestToServer(accountNumber);
        sendRequestToServer(binaryTemplate);
        sendRequestToServer(index);

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
        if (thread == null) {
            thread = new Thread(this);
            thread.start();

        }

    }

    //send requests to the server
    private void sendRequestToServer(Object request) {
        try {

            //sending message
            System.out.println("request sent:  " + request);
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
                
                 if (responseType == 1000) {
                    serverResponse = (String) streamsFromServer.readObject();
                    showServerResponse(serverResponse);

                }
                
                
                if (responseType == 1) {
                    serverResponse = (String) streamsFromServer.readObject();
                    showServerResponse(serverResponse);

                }
                
                
                if (responseType == 20) {
                    serverResponse = (String) streamsFromServer.readObject();
                    showServerResponse(serverResponse);

                }

                if (responseType == 19) {

                    // fill up dialogue dialogue box to edit
                    //and send it back
                    Integer accountNumberr = (Integer) streamsFromServer.readObject();
                    String firstNamee = (String) streamsFromServer.readObject();
                    String lastNamee = (String) streamsFromServer.readObject();
                    String dobb = (String) streamsFromServer.readObject();
                    String phonee = (String) streamsFromServer.readObject();
                    String emaill = (String) streamsFromServer.readObject();
                    String addresss = (String) streamsFromServer.readObject();

                    //show show edit dialogue box
                    JFrame editFrame = new JFrame();

                    editFrame.setSize(800, 700);
                    //addNewCustomer.setBackground(Color.blue);
                    editFrame.setLayout(null);

                    JLabel firstNameLabel = new JLabel("First Name");
                    firstNameLabel.setFont(new Font("Arial", Font.BOLD, 18));  //300, 150
                    firstNameLabel.setBounds(120, 80, 100, 100); //150
                    editFrame.add(firstNameLabel);

                    JTextField firstNameTextField = new JTextField(firstNamee);
                    firstNameTextField.setBounds(250, 105, 370, 50);
                    firstNameTextField.setFont(new Font("Arial", Font.BOLD, 20)); //430, 175
                    editFrame.add(firstNameTextField);

                    JLabel lastNameLabel = new JLabel("Last Name");
                    lastNameLabel.setFont(new Font("Arial", Font.BOLD, 18)); // 2
                    lastNameLabel.setBounds(120, 160, 100, 100);
                    editFrame.add(lastNameLabel);

                    JTextField lastNameTextField = new JTextField(lastNamee);
                    lastNameTextField.setBounds(250, 185, 370, 50);
                    lastNameTextField.setFont(new Font("Arial", Font.BOLD, 20));
                    editFrame.add(lastNameTextField);

                    JLabel dob = new JLabel("Date Of Birth");
                    dob.setFont(new Font("Arial", Font.BOLD, 18)); //3
                    dob.setBounds(110, 240, 150, 100);
                    editFrame.add(dob);

                    JTextField dobField = new JTextField(dobb);
                    dobField.setBounds(250, 265, 370, 50);
                    dobField.setFont(new Font("Arial", Font.BOLD, 20));
                    editFrame.add(dobField);

                    JLabel phone = new JLabel("Phone Number");
                    phone.setFont(new Font("Arial", Font.BOLD, 18)); //4
                    phone.setBounds(100, 320, 150, 100);
                    editFrame.add(phone);

                    JTextField phoneNumberField = new JTextField(phonee);
                    phoneNumberField.setBounds(250, 345, 370, 50);
                    phoneNumberField.setFont(new Font("Arial", Font.BOLD, 20));
                    editFrame.add(phoneNumberField);

                    JLabel email = new JLabel("Email Address");
                    email.setFont(new Font("Arial", Font.BOLD, 18)); //5
                    email.setBounds(100, 400, 150, 100);
                    editFrame.add(email);

                    JTextField emailField = new JTextField(emaill);
                    emailField.setBounds(250, 425, 370, 50);
                    emailField.setFont(new Font("Arial", Font.BOLD, 20));
                    editFrame.add(emailField);

                    JLabel address = new JLabel("Physical Address");
                    address.setFont(new Font("Arial", Font.BOLD, 18)); //6
                    address.setBounds(80, 480, 200, 100);
                    editFrame.add(address);

                    JTextField addressField = new JTextField(addresss);
                    addressField.setBounds(250, 505, 370, 50);
                    addressField.setFont(new Font("Arial", Font.BOLD, 20));
                    editFrame.add(addressField);

                    JButton continueBtn = new JButton("Save Changes");
                    continueBtn.setBounds(320, 580, 150, 30);
                    continueBtn.setFont(new Font("Arial", Font.BOLD, 15));
                    editFrame.add(continueBtn);
                    
                    JButton clear = new JButton("Clear Fields");
                    clear.setBounds(250, 570, 170, 50);
                    clear.setFont(new Font("Arial", Font.BOLD, 15));
                    //editFrame.add(clear);

                    clear.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            firstNameTextField.setText("");
                            lastNameTextField.setText("");
                            dobField.setText("");
                            phoneNumberField.setText("");
                            emailField.setText("");
                            addressField.setText("");

                            //requestCustomerDetails();
                        }
                    });

                    continueBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            //request type, first name, last name, dob, email, pone, address, first deposit
                            //requestCustomerDetails();
                            //confirmation message here
                            
                            //firstName //lastName dob phone email address physical address
                            requestSaveEditChanges(90, accountNumberr, firstNameTextField.getText().trim(), lastNameTextField.getText().trim(), ""+dobField.getText().trim(), ""+ phoneNumberField.getText().trim(), ""+ emailField.getText().trim(), ""+addressField.getText().trim());
                            
                            //firstName //lastName dob phone email address physical address
                            //network.enrolNewCustomerDetails(51, firstNameTextField.getText().trim(), lastNameTextField.getText().trim(), ""+dobField.getText().trim(), ""+ emailField.getText().trim(), ""+phoneNumberField.getText().trim(), ""+addressField.getText().trim(), Double.parseDouble(firstDepostField.getText().trim()));
                            System.out.println("send to server");

                            ///after confirming clear
                        }
                    });
                    
                    editFrame.setVisible(true);

                }
                
                 if (responseType == 444) {
                    System.out.println("customer details recieved");
                    ArrayList<Object> accountNumbers = (ArrayList<Object>) streamsFromServer.readObject();
                    ArrayList<Object> transactionName = (ArrayList<Object>) streamsFromServer.readObject();
                    ArrayList<Object> date = (ArrayList<Object>) streamsFromServer.readObject();
                    ArrayList<Object> time = (ArrayList<Object>) streamsFromServer.readObject();
                   

                    display.populateTables(accountNumbers, transactionName, date, time);

                }

                if (responseType == 18) {
                    System.out.println("customer details recieved");
                    ArrayList<Object> accountNumbers = (ArrayList<Object>) streamsFromServer.readObject();
                    ArrayList<Object> firstNames = (ArrayList<Object>) streamsFromServer.readObject();
                    ArrayList<Object> lastNames = (ArrayList<Object>) streamsFromServer.readObject();
                    ArrayList<Object> phones = (ArrayList<Object>) streamsFromServer.readObject();
                    ArrayList<Object> balances = (ArrayList<Object>) streamsFromServer.readObject();
                    ArrayList<Object> emails = (ArrayList<Object>) streamsFromServer.readObject();
                    ArrayList<Object> addresses = (ArrayList<Object>) streamsFromServer.readObject();

                    display.populateTables(accountNumbers, firstNames, lastNames, phones, balances, emails, addresses);

                }

                if (responseType == 15) {
                    serverResponse = (String) streamsFromServer.readObject();
                    showServerResponse(serverResponse);

                }

                if (responseType == 4) {
                    serverResponse = (String) streamsFromServer.readObject();
                    showServerResponse(serverResponse);

                }

                if (responseType == 5) {
                    serverResponse = (String) streamsFromServer.readObject();
                    boolean withdrawalStatus = (boolean) streamsFromServer.readObject();
                    System.out.println("withdrawal status: " + withdrawalStatus);
                    showServerResponse(serverResponse);

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

                if (responseType == 12) {
                    int accountNumber;

                    accountNumber = (Integer) streamsFromServer.readObject();
                    new EnrollmentDialog(templates, accountNumber, this
                    ).setVisible(true);

                }

                if (responseType == 14) {

                    //HashMap<byte[], Integer> customerDetails  = (HashMap<byte[], Integer>) streamsFromServer.readObject();
                    ArrayList<byte[]> fingerprints = (ArrayList<byte[]>) streamsFromServer.readObject();
                    String customer = (String) streamsFromServer.readObject();

                    //send along user details display them if fingerprint has been verified
                    display.verificationCard(fingerprints, customer);

                }

                //account has been aussessful added, ask for fingerprint
                //after account details have added prompt fingerprint scan
                if (responseType == 13) {
                    System.out.println("response reached");
                    //when managing responsed with an array of templates

                    int accountNumber = (Integer) streamsFromServer.readObject();
                    String firstName = (String) streamsFromServer.readObject();
                    String lastName = (String) streamsFromServer.readObject();
                    int pin = (int) streamsFromServer.readObject();
                    String phone = (String) streamsFromServer.readObject();
                    double balance = (Double) streamsFromServer.readObject();

                    JFrame showGenereateResults = new JFrame();
                    showGenereateResults.setLayout(null);
                    showGenereateResults.setSize(600, 800);

                    //showGenereateResults.setDefaultCloseOperation(EXIT_ON_CLOSE);
                    JTextArea results = new JTextArea();
                    results.setBounds(45, 200, 500, 500);

                    showGenereateResults.add(results);

                    JLabel successful = new JLabel("Customer Successfully Enrolled");
                    successful.setFont(new Font("Arial", Font.BOLD, 30));
                    successful.setForeground(Color.green);
                    successful.setBounds(70, 110, 470, 50);
                    showGenereateResults.add(successful);

                    results.setFont(new Font("Arial", Font.BOLD, 20));
                    results.append(" " + new Date() + "\n\n");
                    results.append("                New Customer Account Details\n\n");
                    //results.setFont(new Font("Arial", Font.ROMAN_BASELINE, 20));
                    results.append("                Account Number               " + accountNumber + " \n");
                    results.append("                First Name                        " + firstName + " \n");
                    results.append("                Last Name                        " + lastName + " \n");

                    results.append("                Pin                                    " + pin + " \n");
                    results.append("                Phone Number                 " + phone + " \n");
                    results.append("                Account Balance(MWK)    " + balance + " \n");
                    results.append("                \n               Customer Fingerprints Enrolled ");
                    results.setEditable(false);
                    showGenereateResults.setResizable(false);

                    JButton close = new JButton("Close");
                    close.setFont(new Font("Arial", Font.BOLD, 14));

                    close.setBounds(250, 720, 100, 40);
                    showGenereateResults.add(close);

                    showGenereateResults.setVisible(true);

                    close.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            showGenereateResults.setVisible(false);

                        }
                    });

                }

            } catch (ClassNotFoundException classNotFoundException) {
                System.out.println("Error object type");

            }
        } while (!serverResponse.equals("SERVER - END"));
    }

    private void showServerResponse(final String m) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                //System.err.println("Responce from the server: " + m);
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
