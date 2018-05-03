/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atminterface;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import sun.security.util.Password;
import com.seaglasslookandfeel.*;
import java.awt.Font;
import javafx.application.Platform;
import javafx.scene.control.TableView;

/**
 *
 * @author jmwantisi
 */
public class StateManager extends JFrame {

    private DisplayPanel displayPanel;
    private JButton enter, clear, cancel, n1, n2, n3, n4, n5, n6, n7, n8, n9, n0, dot, scanFinger;
    private static JButton m1, m2, m3, m4, m5, m6, m7, m8, insertCard, ejectCard;
    private JTextField cardNumber;
    private JTextField cashDispenser;

    private NetworkToServer atmClient;
    private AuthenticationProcess authenticationProcess;
    // private StateManager display;

    private static boolean fingerprintValidationCheck = false;

    private static int cardNumberEntered, pinNumberEntered;
    private final int IDLE_STATE = 0;
    private final int CARD_VERIFICATION_STATE = 1;
    private final int MAIN_MENU_STATE = 2;
    private final int FINGERPRINT_VERIFICATION_STATE = 3;
    private final int PIN_VERIFICATION_STATE = 4;
    private final int CASH_WITHDRAWAL_STATE = 5;
    private final int BALANCE_INQUIRY_STATE = 6;
    private final int FUND_TRANSFER_STATE = 7;
    private final int BILL_PAYMENT_STATE = 8;
    private final int REQUEST_STATEMENT_STATE = 9;
    private final int CONFIRM_WITHDRAWAL_STATE = 10;
    private final int CONFIRM_FUND_TRANFER_STATE = 11;
    private final int WITHIN_NBM_TRANSFER_STATE = 12;
    private final int OUTSIDE_NBM_TRANSFER_STATE = 13;
    private final int PERFOMING_TRANSFER_STATE = 14;
    private final int RECIEPIENT_ACCOUNT_STATE = 15;
    private final int TRANSFER_AMOUNT_STATE = 16;
    private final int ESCOM_BILL_STATE = 17;
    private final int WATERBOARD_BILL_STATAE = 18;
    private final int MASM_BILL_STATE = 19;
    private final int DSTV_BILL_STATE = 20;

    private static double amount;
    private static int account;
    private final int ONE_THOUSAND = 1000;
    private final int TWO_THOUNDAND = 2000;
    private final int FIVE_THOUSAND = 5000;
    private final int TEN_THOUSAND = 10000;
    private final int FIFTY_THOUSAND = 50000;
    private final int HUNDRED_THOUSAND = 100000;
    private final int PERFOM_DSTV_BILL_PAY = 21;
    private final int PERFORM_ESCOM_BILL_PAY = 22;
    private final int PERFORM_WATERBOARD_BILL_PAY = 23;
    private final int PERFORM_MASM_BILL_PAY = 24;
    private final int CONFIRMATION_STATE_DSTV = 25;
    private final int CONFIRMATION_STATE_ESCOM = 26;
    private final int CONFIRMATION_STATE_WATERBOARD = 27;
    private final int CONFIRMATION_STATE_MASM = 28;
    private final int BANK_STATEMENT_STATE = 29;
    private final int PAY_ESCOM = 30;
    private final int PAY_WATERBOARD = 31;
    private final int PAY_MASM = 32;
    private final int PAY_DSTV = 33;
    private final int BALANCE_REQUEST = 34;
    private final int STATEMENT_REQUEST = 35;
    private final int OUTSIDE_BANKT_TRANSFER = 36;
    private final int PERFORM_OUTSIDE_BANK_TRANSFER = 37;
    private final int CONFIRM_OUTSIDE_BANK_TRANSFER = 38;
    private final int FUND_TRANSFER_OUTSIDE = 41;
    private final int OUTSIDE_BANK_NAME_STATE = 40;
    private final int CARDLESS_REQUEST = 42;
    private final int CARDlLESS_TOKEN_USAGE_REQUEST = 43;
    private final int TOKEN_REQUEST_STATE = 44;
    private final int ONE_TIME_ENTRY_STATE = 50;
    private static double balance;
    private static ArrayList<String> statement = new ArrayList<>();
    private static ArrayList<String> dates = new ArrayList<>();
    private static ArrayList<String> times = new ArrayList<>();
    private static String outsideBankName;

    public StateManager() {

        this.authenticationProcess = new AuthenticationProcess();
        this.displayPanel = new DisplayPanel(null);
        displayPanel.getState();

        //this.display = new StateManager();
        this.setTitle("ATM");
        this.setSize(1100, 760);
        this.setResizable(false);
        this.setLocation(130, 2);
        this.setLayout(null);
        //this.add(new DisplayPanel());
        this.displayPanel.setBounds(240, 5, 500, 400);

        this.cardNumber = new JTextField();
        this.cardNumber.setBounds(830, 430, 175, 30);
        this.add(cardNumber);

        this.insertCard = new JButton("Insert");
        this.insertCard.setBounds(1010, 435, 70, 20);
        this.add(insertCard);

        // ejectCard = new JButton("Eject");
        this.cashDispenser = new JTextField();
        this.cashDispenser.setText("                                                                   Cash Dispenser");
        this.cashDispenser.setEditable(false);
        this.cashDispenser.setBackground(Color.gray);
        this.cashDispenser.setBounds(240, 420, 500, 50);
        this.add(cashDispenser);

        this.ejectCard = new JButton("Eject");
        this.ejectCard.setBounds(1010, 435, 70, 20);

        //menu buttons
        this.m1 = new JButton("");
        this.m1.setBounds(175, 65, 40, 40);
        //this.m1.setEnabled(false);
        //this.add(m1);

        this.m2 = new JButton("");
        this.m2.setBounds(175, 155, 40, 40);
        //this.m2.setEnabled(false);
        this.add(m2);

        this.m3 = new JButton("");
        this.m3.setBounds(175, 255, 40, 40);
        //this.m3.setEnabled(false);
        this.add(m3);

        this.m4 = new JButton("");
        this.m4.setBounds(175, 355, 40, 40);
        //this.m4.setEnabled(false);
        this.add(m4);

        this.m5 = new JButton("");
        this.m5.setBounds(770, 60, 40, 40);
        //this.m5.setEnabled(false);
        //this.add(m5);

        this.m6 = new JButton("");
        this.m6.setBounds(770, 155, 40, 40);
        //this.m6.setEnabled(false);
        this.add(m6);

        this.m7 = new JButton("");
        this.m7.setBounds(770, 255, 40, 40);
        //this.m7.setEnabled(false);
        this.add(m7);

        this.m8 = new JButton("");
        this.m8.setBounds(770, 355, 40, 40);
        //this.m8.setEnabled(false);
        this.add(m8);

        //keypad 
        this.n1 = new JButton("1");
        this.n1.setBounds(255, 480, 100, 50);
        //this.n1.setEnabled(false);
        this.add(n1);

        this.n2 = new JButton("2");
        this.n2.setBounds(365, 480, 100, 50);
        //this.n2.setEnabled(false);
        this.add(n2);

        this.n3 = new JButton("3");
        this.n3.setBounds(475, 480, 100, 50);
        //this.n3.setEnabled(false);
        this.add(n3);

        this.n4 = new JButton("4");
        this.n4.setBounds(255, 540, 100, 50);
        //this.n4.setEnabled(false);
        this.add(n4);

        this.n5 = new JButton("5");
        this.n5.setBounds(365, 540, 100, 50);
        //this.n5.setEnabled(false);
        this.add(n5);

        this.n6 = new JButton("6");
        this.n6.setBounds(475, 540, 100, 50);
        //this.n6.setEnabled(false);
        this.add(n6);

        this.n7 = new JButton("7");
        this.n7.setBounds(255, 600, 100, 50);
        //this.n7.setEnabled(false);
        this.add(n7);

        this.n8 = new JButton("8");
        this.n8.setBounds(365, 600, 100, 50);
        //this.n8.setEnabled(false);
        this.add(n8);

        this.n9 = new JButton("9");
        this.n9.setBounds(475, 600, 100, 50);
        //this.n9.setEnabled(false);
        this.add(n9);

        this.n0 = new JButton("0");
        this.n0.setBounds(365, 660, 100, 50);
        //this.n0.setEnabled(false);
        this.add(n0);

        this.dot = new JButton(".");
        this.dot.setBounds(475, 660, 100, 50);
        //this.dot.setEnabled(false);
        this.add(dot);

        this.scanFinger = new JButton("Confirm");
        this.scanFinger.setBounds(590, 480, 130, 50);
        //this.scanFinger.setBackground(Color.red);
        //this.scanFinger.setEnabled(true);
        //this.add(scanFinger);

        this.cancel = new JButton("Cancel");
        this.cancel.setBounds(590, 480, 130, 50);
        this.cancel.setBackground(Color.red);
        this.add(cancel);

        this.clear = new JButton("Clear");
        this.clear.setBounds(590, 540, 130, 50);
        this.clear.setBackground(Color.yellow);
        this.add(clear);

        this.enter = new JButton("Enter");
        this.enter.setBounds(590, 600, 130, 50);
        this.enter.setBackground(Color.green);
        this.add(enter);
        setKeyPad();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(displayPanel);
        guiButtons();
        this.setVisible(true);

       
        JLabel ipInstructionLabel = new JLabel("Enter IP Address to Connect to Server");
        ipInstructionLabel.setBounds(800, 20, 300, 30);
        ipInstructionLabel.setForeground(Color.blue);
        ipInstructionLabel.setFont(new Font("Arial" , Font.BOLD, 15));
        this.add(ipInstructionLabel);
        JTextField ipAddress = new JTextField();
        ipAddress.setBounds(810, 60, 250, 30);
        this.add(ipAddress);
        
        JButton connect = new JButton("Connect");
        connect.setBounds(860, 100, 150, 30);
        this.add(connect);
        
        
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //connect.setEnabled(false);
                
                 new Thread(() -> {
                     
                     atmClient = new NetworkToServer();
                    atmClient.init(displayPanel, authenticationProcess);
                    atmClient.connection(""+ipAddress.getText().trim());
                            
                    atmClient.startConnection();
        

                        // network.startConnection();
                        Platform.runLater(() -> {
                             
        
                            
                        });

                    }).start();
                  
            }
        });
        
      

    }

    public void setKeyPad() {
        n1.setEnabled(true);
        n2.setEnabled(true);
        n3.setEnabled(true);
        n4.setEnabled(true);
        n5.setEnabled(true);
        n6.setEnabled(true);
        n7.setEnabled(true);
        n8.setEnabled(true);
        n9.setEnabled(true);
        n0.setEnabled(true);
        dot.setEnabled(true);
    }

    //eject card
    public void ejectCard() {
        remove(insertCard);
        this.revalidate();
        this.repaint();
        add(ejectCard);

        ejectCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //after verification
                displayPanel.idlePanel();
                add(insertCard);
                remove(ejectCard);
                cardNumber.setText("");
                cardNumber.setEditable(true);
                fingerprintValidationCheck = false;
                displayPanel.setState(IDLE_STATE);
                authenticationProcess.endSession();
                
                cardNumber.setText("");
                cardNumberEntered = 0;
                account = 0; 
                //pin =0;
                pinNumberEntered = 0;
                cardNumber.setEditable(true);
                insertCard.setEnabled(true);
                fingerprintValidationCheck = false;
                displayPanel.setState(IDLE_STATE);
                authenticationProcess.endSession();

            }
        });

    }

    public void guiButtons() {

        m2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String one = "1000";
                displayPanel.withdrawalAmount().setText(one);
                amount = Double.parseDouble(one.trim());

                switch (displayPanel.getState()) {
                    //clear to use on this state
                    case MAIN_MENU_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.resetEntryField();
                        displayPanel.clearWithdrawal();
                        displayPanel.setState(CASH_WITHDRAWAL_STATE);
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.cashWithdrawalPanel();

                        break;

                    // call main menu state here
                    case CASH_WITHDRAWAL_STATE:
                        //maintain state
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.setState(CASH_WITHDRAWAL_STATE);

                        break;

                    case FUND_TRANSFER_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        //use this alos fot, fund bill paying and stuff
                        displayPanel.clearFundTransferField();
                        displayPanel.performTransfer("   Enter Account to transfer to");
                        System.out.println("Within nat selected");
                        displayPanel.setState(PERFOMING_TRANSFER_STATE);

                        break;

                    case BILL_PAYMENT_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.clearFundTransferField();
                        displayPanel.performTransfer("   Enter ESCOM Bill Reference Number");
                        System.out.println("Escom transfer state");
                        displayPanel.setState(ESCOM_BILL_STATE);

                        break;

                    case ESCOM_BILL_STATE:

                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        break;

                }

            }
        }
        );

        m3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String one = "2000";
                displayPanel.withdrawalAmount().setText(one);
                amount = Double.parseDouble(one.trim());

                System.out.println("fund transfer button");
                switch (displayPanel.getState()) {
                    //clear to use on this state
                    case MAIN_MENU_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.setState(FUND_TRANSFER_STATE);
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.fundTransferSelectionPanel();

                        break;

                    case BILL_PAYMENT_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.clearFundTransferField();
                        
                        displayPanel.performTransfer("   Enter MASM Subscription Reference Number");
                        System.out.println("MASN transfer state");
                        displayPanel.setState(MASM_BILL_STATE);

                        break;

                    case MASM_BILL_STATE:

                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        break;

                }

            }
        }
        );

        m4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String one = "5000";
                displayPanel.withdrawalAmount().setText(one);
                amount = Double.parseDouble(one.trim());

                System.out.println("statement request button");
                switch (displayPanel.getState()) {
                    //clear to use on this state
                    case MAIN_MENU_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        //statement request
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, true);

                        statement = atmClient.getStatement();
                        //statement = atmClient.getStatement();
                        //statement = atmClient.getStatement();

                        displayPanel.setState(REQUEST_STATEMENT_STATE);
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.showBankStatement(statement);

                        break;

                    // call main menu state here
                }

            }
        }
        );

        m6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String one = "10000";
                displayPanel.withdrawalAmount().setText(one);
                amount = Double.parseDouble(one.trim());

                System.out.println("Balance button");
                switch (displayPanel.getState()) {
                    //clear to use on this state
                    case MAIN_MENU_STATE:
                        displayPanel.setState(BALANCE_INQUIRY_STATE);

                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, true);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        //request balance from server cardNumberEntered

                        System.out.println("Balance: " + balance);

                        displayPanel.balanceDisplay(balance);
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.balancePanel();

                        break;

                    // call main menu state here
                    case BALANCE_INQUIRY_STATE:
                        //maintain state
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.setState(BALANCE_INQUIRY_STATE);
                        System.out.println("balance state");

                        break;

                    case FUND_TRANSFER_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        System.out.println("outside bank selected");
                        //there was perfoming transfer state
                        displayPanel.setState(OUTSIDE_BANK_NAME_STATE);
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.outsideBankMenu();
                        break;

                    case BILL_PAYMENT_STATE:

                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.clearFundTransferField();
                        displayPanel.performTransfer("Enter WATERBOARD Bill Reference number");
                        System.out.println("WATERBOARD transfer state");
                        displayPanel.setState(MASM_BILL_STATE);

                        break;

                    case MASM_BILL_STATE:

                        break;

                }

            }
        }
        );

        m7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String one = "50000";
                displayPanel.withdrawalAmount().setText(one);
                amount = Double.parseDouble(one.trim());

                System.out.println("Bill button");
                switch (displayPanel.getState()) {
                    //clear to use on this state
                    case MAIN_MENU_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.setState(BILL_PAYMENT_STATE);
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.billPaymentPanel();

                        break;

                    // call main menu state here
                    case BILL_PAYMENT_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.setState(DSTV_BILL_STATE);
                        displayPanel.clearFundTransferField();
                        displayPanel.performTransfer("   Enter DSTV Subscription Reference number");
                        System.out.println("DSTV transfer state");

                        break;

                    case DSTV_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        ;
                        //server response here
                        balance = atmClient.getAccountBalance();
                        
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        break;

                }

            }
        }
        );

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel.idlePanel();
                add(insertCard);
                remove(ejectCard);
                cardNumber.setText("");
                cardNumberEntered = 0;
                account = 0; 
                //pin =0;
                pinNumberEntered = 0;
                cardNumber.setEditable(true);
                insertCard.setEnabled(true);
                fingerprintValidationCheck = false;
                displayPanel.setState(IDLE_STATE);
                authenticationProcess.endSession();

            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (displayPanel.getState()) {
                    
                    case TOKEN_REQUEST_STATE:
                        
                        displayPanel.clearFields();
                       break;
                    
                    case ONE_TIME_ENTRY_STATE:
                        
                        
                       displayPanel.clearFields();
                       break;
                    
                    //clear to use on this state
                    case PIN_VERIFICATION_STATE:
                        //displayPanel.resetTransferBill();
                        displayPanel.clearOnPinPressed();
                        break;

                    case CASH_WITHDRAWAL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        //displayPanel.resetTransferBill();
                        displayPanel.clearWithdrawal();
                        break;

                    //clear transaction to cancel
                    case CONFIRM_WITHDRAWAL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.clearWithdrawal();
                        break;

                    case CONFIRM_FUND_TRANFER_STATE:

                        //clear transfer process
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.performTransfer("   Enter Account to transfer to");
                        System.out.println("clear fund transfer");
                       // displayPanel.setState(PERFOMING_TRANSFER_STATE);

                        displayPanel.clearFundTransferField();
                        //displayPanel.resetTransferBill();
                        displayPanel.resetEntryField();
                       // displayPanel.resetTransferBill();
                        displayPanel.beforeResetFundTransfer();
                       // 
                       if(displayPanel.resetTransfer()){
                                   displayPanel.afterResetFundTransfer();
                               
                               }else{
                                   displayPanel.beforeResetFundTransfer();
                               
                               
                               }

                        break;

                    case PERFOMING_TRANSFER_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        //clear transfer process

                        displayPanel.performTransfer("   Enter Account to transfer to");
                        System.out.println("clear fund transfer");
                        displayPanel.setState(PERFOMING_TRANSFER_STATE);

                        displayPanel.clearFundTransferField();
                        //displayPanel.resetTransferBill();
                        displayPanel.resetEntryField();
                        //displayPanel.resetTransferBill();
                        
                        displayPanel.clearFundTransferField();
                       // displayPanel.resetTransferBill();
                        displayPanel.resetEntryField();
                       // displayPanel.resetTransferBill();
                        displayPanel.beforeResetFundTransfer();
                        
                        if(displayPanel.resetTransfer()){
                                   displayPanel.afterResetFundTransfer();
                               
                               }else{
                                   displayPanel.beforeResetFundTransfer();
                               
                               
                               }

                        break;

                    //every step of transfer
                    case CONFIRMATION_STATE_DSTV:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.performTransfer("   Enter DSTV account to bill to");
                        System.out.println("clear dstv bill pay");
                        displayPanel.setState(DSTV_BILL_STATE);

                        displayPanel.clearFundTransferField();
                        displayPanel.resetDstvBill();
                        displayPanel.resetEntryField();
                        displayPanel.resetDstvBill();
                        break;

                    case DSTV_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.performTransfer("   Enter DSTV account to bill to");
                        System.out.println("clear dstv bill pay");
                        //need to check if reset has been created or not
                        if (displayPanel.dstvReset()) {
                            displayPanel.clearAferResetFundTransferField();

                        } else {
                            displayPanel.clearFundTransferField();
                        }

                        break;
                    //clear amount field
                    case PERFOM_DSTV_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.performTransfer("   Enter amount to pay");
                        System.out.println("clear dstv bill pay");
                        //need to check if reset has been created or not

                        displayPanel.clearAmountTransfer();

                        break;

                    case CONFIRMATION_STATE_ESCOM:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.performTransfer("   Enter ESCOM account to bill to");
                        System.out.println("claer escom bill pay");
                        displayPanel.setState(ESCOM_BILL_STATE);

                        displayPanel.clearFundTransferField();
                        displayPanel.resetEscomBill();
                        displayPanel.resetEntryField();
                        displayPanel.resetEscomBill();
                        break;

                    case ESCOM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.performTransfer("   Enter ESCOM account to bill to");
                        System.out.println("claer escom bill pay");

                        if (displayPanel.escomReset()) {
                            displayPanel.clearAferResetFundTransferField();

                        } else {
                            displayPanel.clearFundTransferField();
                        }

                        break;

                    case PERFORM_ESCOM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.performTransfer("   Enter amount to pay");
                        System.out.println("clear dstv bill pay");
                        //need to check if reset has been created or not

                        displayPanel.clearAmountTransfer();

                        break;

                    case CONFIRMATION_STATE_MASM:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.performTransfer("   Enter MASM account to bill to");
                        System.out.println("claer masm bill pay");
                        displayPanel.setState(MASM_BILL_STATE);

                        displayPanel.clearFundTransferField();
                        displayPanel.resetMasmBill();
                        displayPanel.resetEntryField();
                        displayPanel.resetMasmBill();
                        break;

                    case MASM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.performTransfer("   Enter MASM account to bill to");
                        System.out.println("claer masm bill pay");

                        if (displayPanel.masmReset()) {
                            displayPanel.clearAferResetFundTransferField();

                        } else {
                            displayPanel.clearFundTransferField();
                        }

                        break;

                    case PERFORM_MASM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.performTransfer("   Enter amount to pay");
                        System.out.println("clear dstv bill pay");
                        //need to check if reset has been created or not

                        displayPanel.clearAmountTransfer();

                        break;

                    case CONFIRMATION_STATE_WATERBOARD:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.performTransfer("   Enter WATERBOARD account to bill to");
                        System.out.println("claer waterboard bill pay");
                        displayPanel.setState(WATERBOARD_BILL_STATAE);

                        displayPanel.clearFundTransferField();
                        displayPanel.resetWaterBill();
                        displayPanel.resetEntryField();
                        displayPanel.resetWaterBill();
                        break;

                    case WATERBOARD_BILL_STATAE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.performTransfer("   Enter WATERBOARD account to bill to");

                        if (displayPanel.waterBoardReset()) {
                            displayPanel.clearAferResetFundTransferField();

                        } else {
                            displayPanel.clearFundTransferField();
                        }

                        break;

                    case PERFORM_WATERBOARD_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        displayPanel.performTransfer("   Enter amount to pay");
                        System.out.println("clear dstv bill pay");
                        //need to check if reset has been created or not

                        displayPanel.clearAmountTransfer();

                        break;

                    case OUTSIDE_BANK_NAME_STATE:
                        //state
                        displayPanel.clearOutBankField();
                        displayPanel.outsideBankTransferAmountClear();
                        
                       

                        break;

                    case OUTSIDE_BANKT_TRANSFER:
                        //transfer
                        displayPanel.clearOutsideBankAccountField();
                        displayPanel.outsideBankTransferAmountClear();
                        
                        if(displayPanel.outsideTransferReset()){
                                   displayPanel.resetAfterOutsideTransfer();
                               
                               }else{
                                   displayPanel.outSideTransferReset();
                               
                               
                               }

                        break;

                    case PERFORM_OUTSIDE_BANK_TRANSFER:

                        displayPanel.clearOutSideBankAmountField();

                        break;

                }

            }
        }
        );

        n1.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {

                switch (displayPanel.getState()) {
                    //clear to use on this state
                    
                    case ONE_TIME_ENTRY_STATE:
                        displayPanel.onePressed();
                        break;
                        
                    
                    case PIN_VERIFICATION_STATE:
                        displayPanel.onePressed();
                        break;

                    case TOKEN_REQUEST_STATE:
                        displayPanel.onePressed();
                        break;

                    case CASH_WITHDRAWAL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.onePressed();
                        break;

                    case PERFOMING_TRANSFER_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.onePressed();
                        break;

                    case TRANSFER_AMOUNT_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.onePressed();
                        break;

                    case ESCOM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.onePressed();
                        break;

                    case MASM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.onePressed();
                        break;

                    case WATERBOARD_BILL_STATAE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.onePressed();
                        break;

                    case DSTV_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.onePressed();
                        break;
                    //here     
                    case PERFORM_WATERBOARD_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.onePressed();
                        break;

                    case PERFOM_DSTV_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.onePressed();
                        break;

                    case PERFORM_ESCOM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.onePressed();
                        break;

                    case PERFORM_MASM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.onePressed();
                        break;

                    case OUTSIDE_BANKT_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.onePressed();
                        break;

                    case OUTSIDE_BANK_NAME_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.onePressed();
                        break;

                    case PERFORM_OUTSIDE_BANK_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.onePressed();
                        break;

                }

            }
        }
        );

        n2.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                switch (displayPanel.getState()) {
                    //clear to use on this state
                    
                    case ONE_TIME_ENTRY_STATE:
                        displayPanel.twoPressed();
                        break;
                    
                    case TOKEN_REQUEST_STATE:
                        displayPanel.twoPressed();
                        break;

                    case PIN_VERIFICATION_STATE:
                        displayPanel.twoPressed();
                        break;
                    case CASH_WITHDRAWAL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.twoPressed();
                        break;
                    case PERFOMING_TRANSFER_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.twoPressed();
                        break;

                    case TRANSFER_AMOUNT_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.twoPressed();
                        break;

                    case ESCOM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.twoPressed();
                        break;

                    case MASM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.twoPressed();
                        break;

                    case WATERBOARD_BILL_STATAE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.twoPressed();
                        break;

                    case DSTV_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.twoPressed();
                        break;

                    //here     
                    case PERFORM_WATERBOARD_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.twoPressed();
                        break;

                    case PERFOM_DSTV_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.twoPressed();
                        break;

                    case PERFORM_ESCOM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.twoPressed();
                        break;

                    case PERFORM_MASM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.twoPressed();
                        break;

                    case OUTSIDE_BANKT_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.twoPressed();
                        break;

                    case OUTSIDE_BANK_NAME_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.twoPressed();
                        break;

                    case PERFORM_OUTSIDE_BANK_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.twoPressed();
                        break;

                }

            }
        }
        );

        n3.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {

                switch (displayPanel.getState()) {
                    //clear to use on this state
                    
                    case ONE_TIME_ENTRY_STATE:
                        displayPanel.threePressed();
                        break;
                    
                    case TOKEN_REQUEST_STATE:
                        displayPanel.threePressed();
                        break;

                    case PIN_VERIFICATION_STATE:
                        displayPanel.threePressed();
                        break;
                    case CASH_WITHDRAWAL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.threePressed();
                        break;
                    case PERFOMING_TRANSFER_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.threePressed();
                        break;

                    case TRANSFER_AMOUNT_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.threePressed();
                        break;

                    case ESCOM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.threePressed();
                        break;

                    case MASM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.threePressed();
                        break;

                    case WATERBOARD_BILL_STATAE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.threePressed();
                        break;

                    case DSTV_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.threePressed();
                        break;

                    //here     
                    case PERFORM_WATERBOARD_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.threePressed();
                        break;

                    case PERFOM_DSTV_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.threePressed();
                        break;

                    case PERFORM_ESCOM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.threePressed();
                        break;

                    case PERFORM_MASM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.threePressed();
                        break;

                    case OUTSIDE_BANKT_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.threePressed();
                        break;

                    case OUTSIDE_BANK_NAME_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.threePressed();
                        break;

                    case PERFORM_OUTSIDE_BANK_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.threePressed();
                        break;

                }

            }
        }
        );

        n4.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                switch (displayPanel.getState()) {
                    //clear to use on this state

                    
                    case ONE_TIME_ENTRY_STATE:
                        displayPanel.fourPressed();
                        break;
                    
                    case TOKEN_REQUEST_STATE:
                        displayPanel.fourPressed();
                        break;

                    case PIN_VERIFICATION_STATE:
                        displayPanel.fourPressed();
                        break;
                    case CASH_WITHDRAWAL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.fourPressed();
                        break;
                    case PERFOMING_TRANSFER_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.fourPressed();
                        break;

                    case TRANSFER_AMOUNT_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.fourPressed();
                        break;

                    case ESCOM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.fourPressed();
                        break;

                    case MASM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.fourPressed();
                        break;

                    case WATERBOARD_BILL_STATAE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.fourPressed();
                        break;

                    case DSTV_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.fourPressed();
                        break;

                    //here     
                    case PERFORM_WATERBOARD_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.fourPressed();
                        break;

                    case PERFOM_DSTV_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.fourPressed();
                        break;

                    case PERFORM_ESCOM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.fourPressed();
                        break;

                    case PERFORM_MASM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.fourPressed();
                        break;

                    case OUTSIDE_BANKT_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.fourPressed();
                        break;

                    case OUTSIDE_BANK_NAME_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.fourPressed();
                        break;

                    case PERFORM_OUTSIDE_BANK_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.fourPressed();
                        break;

                }

            }
        }
        );

        n5.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                switch (displayPanel.getState()) {
                    //clear to use on this state

                    case ONE_TIME_ENTRY_STATE:
                        displayPanel.fivePressed();
                        break;
                    
                    case TOKEN_REQUEST_STATE:
                        displayPanel.fivePressed();
                        break;

                    case PIN_VERIFICATION_STATE:
                        displayPanel.fivePressed();
                        break;
                    case CASH_WITHDRAWAL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.fivePressed();
                        break;
                    case PERFOMING_TRANSFER_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.fivePressed();
                        break;

                    case TRANSFER_AMOUNT_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.fivePressed();
                        break;

                    case ESCOM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.fivePressed();
                        break;

                    case MASM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.fivePressed();
                        break;

                    case WATERBOARD_BILL_STATAE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.fivePressed();
                        break;

                    case DSTV_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.fivePressed();
                        break;

                    //here     
                    case PERFORM_WATERBOARD_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.fivePressed();
                        break;

                    case PERFOM_DSTV_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.fivePressed();
                        break;

                    case PERFORM_ESCOM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.fivePressed();
                        break;

                    case PERFORM_MASM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.fivePressed();
                        break;

                    case OUTSIDE_BANKT_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.fivePressed();
                        break;

                    case OUTSIDE_BANK_NAME_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.fivePressed();
                        break;

                    case PERFORM_OUTSIDE_BANK_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.fivePressed();
                        break;

                }

            }
        }
        );

        n6.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                switch (displayPanel.getState()) {
                    //clear to use on this state

                    case ONE_TIME_ENTRY_STATE:
                        displayPanel.sixPressed();
                        break;
                    
                    case TOKEN_REQUEST_STATE:
                        displayPanel.sixPressed();
                        break;

                    case PIN_VERIFICATION_STATE:
                        displayPanel.sixPressed();
                        break;
                    case CASH_WITHDRAWAL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.sixPressed();
                        break;
                    case PERFOMING_TRANSFER_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.sixPressed();
                        break;

                    case TRANSFER_AMOUNT_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.sixPressed();
                        break;

                    case ESCOM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.sixPressed();
                        break;

                    case MASM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.sixPressed();
                        break;

                    case WATERBOARD_BILL_STATAE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.sixPressed();
                        break;

                    case DSTV_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.sixPressed();
                        break;

                    //here     
                    case PERFORM_WATERBOARD_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.sixPressed();
                        break;

                    case PERFOM_DSTV_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.sixPressed();
                        break;

                    case PERFORM_ESCOM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.sixPressed();
                        break;

                    case PERFORM_MASM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.sixPressed();
                        break;

                    case OUTSIDE_BANKT_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.sixPressed();
                        break;

                    case OUTSIDE_BANK_NAME_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.sixPressed();
                        break;

                    case PERFORM_OUTSIDE_BANK_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.sixPressed();
                        break;

                }

            }
        }
        );

        n7.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                switch (displayPanel.getState()) {
                    //clear to use on this state

                    case ONE_TIME_ENTRY_STATE:
                        displayPanel.sevenPressed();
                        break;
                    
                    case TOKEN_REQUEST_STATE:
                        displayPanel.sevenPressed();
                        break;

                    case PIN_VERIFICATION_STATE:
                        displayPanel.sevenPressed();
                        break;
                    case CASH_WITHDRAWAL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.sevenPressed();
                        break;
                    case PERFOMING_TRANSFER_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.sevenPressed();
                        break;

                    case TRANSFER_AMOUNT_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.sevenPressed();
                        break;

                    case ESCOM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.sevenPressed();
                        break;

                    case MASM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.sevenPressed();
                        break;

                    case WATERBOARD_BILL_STATAE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.sevenPressed();
                        break;

                    case DSTV_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.sevenPressed();
                        break;

                    //here     
                    case PERFORM_WATERBOARD_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.sevenPressed();
                        break;

                    case PERFOM_DSTV_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.sevenPressed();
                        break;

                    case PERFORM_ESCOM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.sevenPressed();
                        break;

                    case PERFORM_MASM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.sevenPressed();
                        break;

                    case OUTSIDE_BANKT_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.sevenPressed();
                        break;

                    case OUTSIDE_BANK_NAME_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.sevenPressed();
                        break;

                    case PERFORM_OUTSIDE_BANK_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.sevenPressed();
                        break;

                }

            }
        }
        );

        n8.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                switch (displayPanel.getState()) {
                    //clear to use on this stat
                    
                    case ONE_TIME_ENTRY_STATE:
                        displayPanel.eightPressed();
                        break;
                    
                    case TOKEN_REQUEST_STATE:
                        displayPanel.eightPressed();
                        break;

                    case PIN_VERIFICATION_STATE:
                        displayPanel.eightPressed();
                        break;
                    case CASH_WITHDRAWAL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.eightPressed();
                        break;
                    case PERFOMING_TRANSFER_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.eightPressed();
                        break;

                    case TRANSFER_AMOUNT_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.eightPressed();
                        break;

                    case ESCOM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.eightPressed();
                        break;

                    case MASM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.eightPressed();
                        break;

                    case WATERBOARD_BILL_STATAE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.eightPressed();
                        break;

                    case DSTV_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.eightPressed();
                        break;

                    //here     
                    case PERFORM_WATERBOARD_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.eightPressed();
                        break;

                    case PERFOM_DSTV_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.eightPressed();
                        break;

                    case PERFORM_ESCOM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.eightPressed();
                        break;

                    case PERFORM_MASM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.eightPressed();
                        break;

                    case OUTSIDE_BANKT_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.eightPressed();
                        break;

                    case PERFORM_OUTSIDE_BANK_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.eightPressed();
                        break;

                }

            }
        }
        );

        n9.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                switch (displayPanel.getState()) {
                    //clear to use on this state

                    
                    case ONE_TIME_ENTRY_STATE:
                        displayPanel.ninePressed();
                        break;
                    
                    case TOKEN_REQUEST_STATE:
                        displayPanel.ninePressed();
                        break;

                    case PIN_VERIFICATION_STATE:
                        displayPanel.ninePressed();
                        break;
                    case CASH_WITHDRAWAL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.ninePressed();
                        break;
                    case PERFOMING_TRANSFER_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.ninePressed();
                        break;

                    case TRANSFER_AMOUNT_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.ninePressed();
                        break;

                    case ESCOM_BILL_STATE:

                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.ninePressed();
                        break;

                    case MASM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);//server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.ninePressed();
                        break;

                    case WATERBOARD_BILL_STATAE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.ninePressed();
                        break;

                    case DSTV_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.ninePressed();
                        break;

                    //here     
                    case PERFORM_WATERBOARD_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.ninePressed();
                        break;

                    case PERFOM_DSTV_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.ninePressed();
                        break;

                    case PERFORM_ESCOM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.ninePressed();
                        break;

                    case PERFORM_MASM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.ninePressed();
                        break;

                    case OUTSIDE_BANKT_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.ninePressed();
                        break;

                    case PERFORM_OUTSIDE_BANK_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.ninePressed();
                        break;

                }

            }
        }
        );

        n0.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                switch (displayPanel.getState()) {
                    //clear to use on this state

                    
                    case ONE_TIME_ENTRY_STATE:
                        displayPanel.zeroPressed();
                        break;
                    
                    case TOKEN_REQUEST_STATE:
                        displayPanel.zeroPressed();
                        break;

                    case PIN_VERIFICATION_STATE:
                        displayPanel.zeroPressed();
                        break;
                    case CASH_WITHDRAWAL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.zeroPressed();
                        break;
                    case PERFOMING_TRANSFER_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.zeroPressed();
                        break;

                    case TRANSFER_AMOUNT_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.zeroPressed();
                        break;

                    case ESCOM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.zeroPressed();
                        break;

                    case MASM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.zeroPressed();
                        break;

                    case WATERBOARD_BILL_STATAE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.zeroPressed();
                        break;

                    case DSTV_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.zeroPressed();
                        break;

                    //here     
                    case PERFORM_WATERBOARD_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.zeroPressed();
                        break;

                    case PERFOM_DSTV_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.zeroPressed();
                        break;

                    case PERFORM_ESCOM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.zeroPressed();
                        break;

                    case PERFORM_MASM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.zeroPressed();
                        break;

                    case OUTSIDE_BANKT_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.zeroPressed();
                        break;

                    case PERFORM_OUTSIDE_BANK_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.zeroPressed();
                        break;

                }

            }
        }
        );

        dot.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                switch (displayPanel.getState()) {
                    //clear to use on this state
                    case PIN_VERIFICATION_STATE:
                        displayPanel.dotPressed();
                        break;
                    case CASH_WITHDRAWAL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.dotPressed();
                        break;
                    case PERFOMING_TRANSFER_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.dotPressed();
                        break;

                    case TRANSFER_AMOUNT_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.dotPressed();
                        break;

                    case ESCOM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.dotPressed();
                        break;

                    case MASM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.dotPressed();
                        break;

                    case WATERBOARD_BILL_STATAE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.dotPressed();
                        break;

                    case DSTV_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.dotPressed();
                        break;

                    //here     
                    case PERFORM_WATERBOARD_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.dotPressed();
                        break;

                    case PERFOM_DSTV_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.dotPressed();
                        break;

                    case PERFORM_ESCOM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.dotPressed();
                        break;

                    case PERFORM_MASM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.dotPressed();
                        break;

                    case OUTSIDE_BANKT_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.dotPressed();
                        break;

                    case PERFORM_OUTSIDE_BANK_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        displayPanel.dotPressed();
                        break;

                }

            }
        }
        );

        scanFinger.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {

                displayPanel.scannerPressed();

            }
        }
        );

        enter.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {

                if (authenticationProcess.cardVerified() == true && fingerprintValidationCheck == false) {
                    displayPanel.setState(PIN_VERIFICATION_STATE);
                    fingerprintValidationCheck = true;
                }

                switch (displayPanel.getState()) {
                    
                    case ONE_TIME_ENTRY_STATE:
                        
                        atmClient.sendBalanceRequest(account, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(account, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                          
                            if (!displayPanel.toUtiliseToken().getText().trim().isEmpty() && !displayPanel.toUtiliseToken().getText().trim().contains(".")) {

                                //send request to the server for token
                                //state waiting to enter card token after being received via sms
                                
                               int token = Integer.parseInt(displayPanel.toUtiliseToken().getText().trim());
                                
                               atmClient.sendRequestToUtiliseToken(account, token);
                                
                                
                                System.out.println("request sent for account number: " + token + " for account number number: " + account);

                                //transfer alternative after reset
                            } else {

                                 JOptionPane.showMessageDialog(null, " Please Enter Valid Token Number");


                            }
                        
                        
                        return;

                    case TOKEN_REQUEST_STATE:
                        System.out.println("token request state");

                        
                        
                            
                            if (!displayPanel.tokenRequestAccount().getText().trim().isEmpty() && !displayPanel.tokenRequestAccount().getText().trim().contains(".")) {

                                //send request to the server for token
                                //state waiting to enter card token after being received via sms
                                
                                account = Integer.parseInt(displayPanel.tokenRequestAccount().getText().trim());
                               cardNumberEntered = account;
                                atmClient.sendCardLessRequest(account);
                                
                                atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                                
                                
                                
                                //create own check for this
                                if (authenticationProcess.accountVerified() == true) {
                                    
                                    
                      
                                }


                                //displayPanel.setState(TRANSFER_AMOUNT_STATE);
                                //ch

                                System.out.println("request sent for account number: " + account);

                                //transfer alternative after reset
                            } else {

                                 JOptionPane.showMessageDialog(null, " Please Enter Valid Account Number");


                            }
                        

                        break;

                    case FINGERPRINT_VERIFICATION_STATE:

                        

                            //displayPanel.setState(PIN_VERIFICATION_STATE);

                            

                        

                        break;

                    case PIN_VERIFICATION_STATE:
                        System.out.println("Enter being used at Pin States");
                        pinState();

                        if (authenticationProcess.pinVerified() == true) {
                            System.out.println("Pin verified here here");
                            displayPanel.setState(MAIN_MENU_STATE);
                            displayPanel.accIcon(cardNumberEntered);
                            displayPanel.menuPanel(cardNumberEntered);

                        }

                        break;

                    case MAIN_MENU_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        System.out.println("Enter being used as menu state");
                        break;

                    case CASH_WITHDRAWAL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        System.out.println("cash withdrawal state");

                        displayPanel.withdrawalAmount().setText(displayPanel.withdrawalAmount().getText());
                        amount = Double.parseDouble(displayPanel.withdrawalAmount().getText().trim());

                        if (!displayPanel.withdrawalAmount().getText().trim().isEmpty() && !displayPanel.withdrawalAmount().getText().trim().contains(".")) {
                            //displayPanel.amountNotification(" Would like to withdrawal " + "MWK " + amount + " ?");
                            
                            
                            int ans = JOptionPane.showConfirmDialog(null, "      Would like to perform a withrawal of MWK " + amount + " ?", "ATM", JOptionPane.YES_NO_OPTION);

                            if (ans == JOptionPane.YES_OPTION) {
                               //displayPanel.setState(CONFIRM_WITHDRAWAL_STATE);
                                
                                atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                                //server response here
                                balance = atmClient.getAccountBalance();

                                atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                                statement = atmClient.getStatement();

                                System.out.println("perform transaction now");
                                withdrawalState();

                                //displayPanel.performAnotherTransactionInWithdrawal();
                                
                            }else{
                                displayPanel.setState(IDLE_STATE);
                                displayPanel.idlePanel();
                            
                            }
                            
                            //System.out.println("Would like to withdrawal " + amount);
                            

                        } else {

                             JOptionPane.showMessageDialog(null, " Please Enter Valid Amount");

                        }

                        break;

                    case CONFIRM_WITHDRAWAL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        System.out.println("perform transaction now");
                        withdrawalState();
                        
                        displayPanel.performAnotherTransactionInWithdrawal();
                        break;

                    //fund tranfer from here
                    case PERFOMING_TRANSFER_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        if (!displayPanel.fundTransferDisplay().getText().trim().isEmpty() && !displayPanel.fundTransferDisplay().getText().trim().contains(".")) {
                            displayPanel.setState(TRANSFER_AMOUNT_STATE);

                            displayPanel.fundTransferDisplay().setText(displayPanel.fundTransferDisplay().getText());
                            account = Integer.parseInt(displayPanel.fundTransferDisplay().getText().trim());

                            System.out.println("Account Number to transfer to: " + account);

                            displayPanel.amountDisplay();

                            //transfer alternative after reset
                        } else {
                            if (!displayPanel.fundTransferDisplayTwo().getText().trim().isEmpty() && !displayPanel.fundTransferDisplayTwo().getText().trim().contains(".")) {

                                displayPanel.setState(TRANSFER_AMOUNT_STATE);

                                displayPanel.fundTransferDisplayTwo().setText(displayPanel.fundTransferDisplayTwo().getText());
                                account = Integer.parseInt(displayPanel.fundTransferDisplayTwo().getText().trim());

                                System.out.println("Account Number to transfer to: " + account);

                                displayPanel.amountDisplay();

                                System.out.println("entering account number after reset");

                            } else {

                                JOptionPane.showMessageDialog(null, " Please Enter Valid Account Number");


                            }
                        }

                        break;

                    case TRANSFER_AMOUNT_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        double currentAmount;
                        if (!displayPanel.transferAmount().getText().trim().isEmpty()) {
                            
                            displayPanel.transferAmount().setText(displayPanel.transferAmount().getText());
                            amount = Integer.parseInt(displayPanel.transferAmount().getText().trim());

                            int ans = JOptionPane.showConfirmDialog(null, "      Transfer MWK " + amount + " to " + account + " ?", "ATM", JOptionPane.YES_NO_OPTION);

                            if (ans == JOptionPane.YES_OPTION) {
                                 atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                                 //server response here
                                 balance = atmClient.getAccountBalance();

                                 atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                                 statement = atmClient.getStatement();

                                 fundTransferState();
                        
                        
                            }else{
                                displayPanel.setState(IDLE_STATE);
                                displayPanel.idlePanel();
                            
                            }

                            //condition to listen to this button
                            
                        } else {

                             JOptionPane.showMessageDialog(null, " Please Enter Valid Amount");


                        }

                        break;

                    case CONFIRM_FUND_TRANFER_STATE:

                        
                        break;

                    case WATERBOARD_BILL_STATAE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        if (!displayPanel.fundTransferDisplay().getText().trim().isEmpty() && !displayPanel.fundTransferDisplay().getText().trim().contains(".")) {
                            displayPanel.setState(PERFORM_WATERBOARD_BILL_PAY);

                            displayPanel.fundTransferDisplay().setText(displayPanel.fundTransferDisplay().getText());
                            account = Integer.parseInt(displayPanel.fundTransferDisplay().getText().trim());

                            System.out.println("water bill account: " + account);
                            displayPanel.amountDisplay();

                            //change state to confirmation state
                        } else {
                            if (!displayPanel.fundTransferDisplayTwo().getText().trim().isEmpty() && !displayPanel.fundTransferDisplayTwo().getText().trim().contains(".")) {

                                displayPanel.setState(PERFORM_WATERBOARD_BILL_PAY);

                                displayPanel.fundTransferDisplayTwo().setText(displayPanel.fundTransferDisplayTwo().getText());
                                account = Integer.parseInt(displayPanel.fundTransferDisplayTwo().getText().trim());

                                System.out.println("water bill account: " + account);

                                displayPanel.amountDisplay();

                                System.out.println("entering account number after reset");

                            } else {

                                 JOptionPane.showMessageDialog(null, " Please Enter Valid Refernce Number");


                            }
                        }
                        break;

                    case PERFORM_WATERBOARD_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        if (!displayPanel.transferAmount().getText().trim().isEmpty()) {

                            displayPanel.transferAmount().setText(displayPanel.transferAmount().getText());
                            amount = Integer.parseInt(displayPanel.transferAmount().getText().trim());

                           
                            
                            int ans = JOptionPane.showConfirmDialog(null, "      Pay Water Bill MWK " + amount + " for bill reference " + account + " ?", "ATM", JOptionPane.YES_NO_OPTION);

                            if (ans == JOptionPane.YES_OPTION) {
                                 atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                                //server response here
                                balance = atmClient.getAccountBalance();

                                atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                                statement = atmClient.getStatement();

                                billPay(PAY_WATERBOARD);
                        
                        
                            }else{
                                displayPanel.setState(IDLE_STATE);
                                displayPanel.idlePanel();
                            
                            }

                            

                        } else {

                             JOptionPane.showMessageDialog(null, " Please Enter Valid Amount");

                        }

                        break;

                    case CONFIRMATION_STATE_WATERBOARD:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        billPay(PAY_WATERBOARD);

                        break;

                    case MASM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        if (!displayPanel.fundTransferDisplay().getText().trim().isEmpty() && !displayPanel.fundTransferDisplay().getText().trim().contains(".")) {
                            displayPanel.setState(PERFORM_MASM_BILL_PAY);
                            System.out.println("entering amount at masm bill state");
                            displayPanel.fundTransferDisplay().setText(displayPanel.fundTransferDisplay().getText());
                            account = Integer.parseInt(displayPanel.fundTransferDisplay().getText().trim());

                            System.out.println("masm bill account: " + account);

                            displayPanel.amountDisplay();

                            //change state to confirmation state
                        } else {
                            if (!displayPanel.fundTransferDisplayTwo().getText().trim().isEmpty() && !displayPanel.fundTransferDisplayTwo().getText().trim().contains(".")) {

                                displayPanel.setState(PERFORM_MASM_BILL_PAY);

                                displayPanel.fundTransferDisplayTwo().setText(displayPanel.fundTransferDisplayTwo().getText());
                                account = Integer.parseInt(displayPanel.fundTransferDisplayTwo().getText().trim());

                                System.out.println("masm bill account: " + account);

                                displayPanel.amountDisplay();

                                System.out.println("entering account number after reset");

                            } else {

                                JOptionPane.showMessageDialog(null, " Please Enter Valid Referene Number");


                            }
                        }
                        break;

                    case PERFORM_MASM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        if (!displayPanel.transferAmount().getText().trim().isEmpty()) {

                            displayPanel.transferAmount().setText(displayPanel.transferAmount().getText());
                            amount = Integer.parseInt(displayPanel.transferAmount().getText().trim());

                            System.out.println("masm Amount to transfer to: " + amount);

                           // displayPanel.transferNoficationDisplay("      Pay MWK " + amount + " for MASM acc no. " + account + " ?");
                            //condition to listen to this button
                            
                            int ans = JOptionPane.showConfirmDialog(null, "      Pay MASM Subscription MWK " + amount + " for bill reference " + account + " ?", "ATM", JOptionPane.YES_NO_OPTION);

                            if (ans == JOptionPane.YES_OPTION) {
                                 atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                                //server response here
                                balance = atmClient.getAccountBalance();

                                atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                                statement = atmClient.getStatement();

                                billPay(PAY_MASM);
                        
                        
                            }else{
                                displayPanel.setState(IDLE_STATE);
                                displayPanel.idlePanel();
                            
                            }
                            
                            

                        } else {

                             JOptionPane.showMessageDialog(null, " Please Enter Valid Amount");


                        }

                        break;

                    case CONFIRMATION_STATE_MASM:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        billPay(PAY_MASM);

                        break;

                    case ESCOM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        if (!displayPanel.fundTransferDisplay().getText().trim().isEmpty() && !displayPanel.fundTransferDisplay().getText().trim().contains(".")) {
                            displayPanel.setState(PERFORM_ESCOM_BILL_PAY);
                            displayPanel.fundTransferDisplay().setText(displayPanel.fundTransferDisplay().getText());
                            account = Integer.parseInt(displayPanel.fundTransferDisplay().getText().trim());

                            System.out.println("escom bill account: " + account);
                            displayPanel.amountDisplay();

                            //change state to confirmation state
                        } else {
                            if (!displayPanel.fundTransferDisplayTwo().getText().trim().isEmpty() && !displayPanel.fundTransferDisplayTwo().getText().trim().contains(".")) {

                                displayPanel.setState(PERFORM_ESCOM_BILL_PAY);

                                displayPanel.fundTransferDisplayTwo().setText(displayPanel.fundTransferDisplayTwo().getText());
                                account = Integer.parseInt(displayPanel.fundTransferDisplayTwo().getText().trim());

                                System.out.println("escom water bill account: " + account);

                                displayPanel.amountDisplay();

                                System.out.println("entering account number after reset");

                            } else {

                                 JOptionPane.showMessageDialog(null, " Please Enter Valid Reference Number");


                            }
                        }
                        break;

                    case PERFORM_ESCOM_BILL_PAY:

                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        if (!displayPanel.transferAmount().getText().trim().isEmpty()) {
                            

                            displayPanel.transferAmount().setText(displayPanel.transferAmount().getText());
                            amount = Integer.parseInt(displayPanel.transferAmount().getText().trim());

                            //condition to listen to this button
                            
                            
                            int ans = JOptionPane.showConfirmDialog(null, "      Pay ESCOM Subscription MWK " + amount + " for bill reference " + account + " ?", "ATM", JOptionPane.YES_NO_OPTION);

                            if (ans == JOptionPane.YES_OPTION) {
                                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                               //server response here
                               balance = atmClient.getAccountBalance();

                               atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                               statement = atmClient.getStatement();

                               billPay(PAY_ESCOM);
                        
                        
                            }else{
                                
                                displayPanel.setState(IDLE_STATE);
                                displayPanel.idlePanel();
                            }
                            
                            
                            
                            

                        } else {

                            JOptionPane.showMessageDialog(null, " Please Enter Valid Amount");

                        }

                        break;

                    case CONFIRMATION_STATE_ESCOM:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        billPay(PAY_ESCOM);

                        break;

                    case DSTV_BILL_STATE:

                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        if (!displayPanel.fundTransferDisplay().getText().trim().isEmpty() && !displayPanel.fundTransferDisplay().getText().trim().contains(".")) {
                            displayPanel.setState(PERFOM_DSTV_BILL_PAY);

                            displayPanel.fundTransferDisplay().setText(displayPanel.fundTransferDisplay().getText());
                            account = Integer.parseInt(displayPanel.fundTransferDisplay().getText().trim());

                            System.out.println("dstv bill account: " + account);
                            displayPanel.amountDisplay();

                            //change state to confirmation state
                        } else {
                            if (!displayPanel.fundTransferDisplayTwo().getText().trim().isEmpty() && !displayPanel.fundTransferDisplayTwo().getText().trim().contains(".")) {

                                displayPanel.setState(PERFOM_DSTV_BILL_PAY);

                                displayPanel.fundTransferDisplayTwo().setText(displayPanel.fundTransferDisplayTwo().getText());
                                account = Integer.parseInt(displayPanel.fundTransferDisplayTwo().getText().trim());

                                System.out.println("dstv bill account: " + account);

                                displayPanel.amountDisplay();

                            } else {

                                JOptionPane.showMessageDialog(null, " Please Enter Valid Reference Number");

                            }
                        }
                        break;

                    case PERFOM_DSTV_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        if (!displayPanel.transferAmount().getText().trim().isEmpty()) {

                            displayPanel.transferAmount().setText(displayPanel.transferAmount().getText());
                            amount = Integer.parseInt(displayPanel.transferAmount().getText().trim());

                            System.out.println("dstv bill Amount to transfer to: " + amount);

                            
                            
                            int ans = JOptionPane.showConfirmDialog(null, "      Pay DSTV Subscription MWK " + amount + " for bill reference " + account + " ?", "ATM", JOptionPane.YES_NO_OPTION);

                            if (ans == JOptionPane.YES_OPTION) {
                                atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                                balance = atmClient.getAccountBalance();

                                atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                                statement = atmClient.getStatement();

                                billPay(PAY_DSTV);
                        
                        
                            }else{
                                
                            
                            }
                            
                            

                        } else {

                            JOptionPane.showMessageDialog(null, " Please Enter Valid Amount");

                        }
                        //confirmation message here

                        break;

                    case CONFIRMATION_STATE_DSTV:

                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        billPay(PAY_DSTV);
                        break;

                    case OUTSIDE_BANK_NAME_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        if (!displayPanel.getOutBankName().getText().trim().isEmpty() && !displayPanel.getOutBankName().getText().trim().contains(".")) {
                            displayPanel.setState(OUTSIDE_BANKT_TRANSFER);

                            displayPanel.getOutBankName().setText(displayPanel.getOutBankName().getText());
                            outsideBankName = displayPanel.getOutBankName().getText().trim();

                            displayPanel.outsideBankAccountEntryMenu("Enter " + outsideBankName + "'s Receipient Account Number");

                        } else {
                            JOptionPane.showMessageDialog(null, " Please Valid Account Number");

                        }

                        break;

                    case OUTSIDE_BANKT_TRANSFER:

                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        if (!displayPanel.getOutSideBankAccount().getText().trim().isEmpty() && !displayPanel.getOutSideBankAccount().getText().trim().contains(".")) {
                            displayPanel.setState(PERFORM_OUTSIDE_BANK_TRANSFER);

                            displayPanel.getOutSideBankAccount().setText(displayPanel.getOutSideBankAccount().getText());
                            account = Integer.parseInt(displayPanel.getOutSideBankAccount().getText().trim());

                            displayPanel.outsideBankAccountEntryMenu("Enter amount to transfer");

                            displayPanel.outsideBankAmountDisplay();

                        } else {
                             JOptionPane.showMessageDialog(null, " Please Select Valid Bank");
                        }

                        break;

                    case PERFORM_OUTSIDE_BANK_TRANSFER:

                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        if (!displayPanel.getOutSideBankTransferAmout().getText().trim().isEmpty()) {

                            displayPanel.getOutSideBankTransferAmout().setText(displayPanel.getOutSideBankTransferAmout().getText());
                            amount = Integer.parseInt(displayPanel.getOutSideBankTransferAmout().getText().trim());

                             //correct for this
                            
                            int ans = JOptionPane.showConfirmDialog(null, "      Transfer MWK " + amount + " to " + account + " ?", "ATM", JOptionPane.YES_NO_OPTION);

                            if (ans == JOptionPane.YES_OPTION) {
                                atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                                //server response here
                                balance = atmClient.getAccountBalance();

                                atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                                statement = atmClient.getStatement();
                                outsideBankTrabsfer(FUND_TRANSFER_OUTSIDE, outsideBankName);
                                
                                        //state
                               displayPanel.clearOutBankField();
                               //transfer
                               displayPanel.clearOutsideBankAccountField();
                               displayPanel.outsideBankTransferAmountClear();
                               displayPanel.resetAfterOutsideTransfer();
                               displayPanel.outSideTransferReset();
                               
                               

                        
                            }else{
                                
                            
                            }
                            
                            
                           

                        }else{
                           JOptionPane.showMessageDialog(null, " Please Enter Valid Amount"); 
                        
                        }

                        break;

                    case CONFIRM_OUTSIDE_BANK_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();
                        outsideBankTrabsfer(FUND_TRANSFER_OUTSIDE, outsideBankName);
                        //method to send request to the server
                        break;

                }

            }
        }
        );

        m8.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                String one = "50000";
                
                displayPanel.withdrawalAmount().setText(one);
                amount = Double.parseDouble(one.trim());
                switch (displayPanel.getState()) {
                    //clear to use on this state
                    case IDLE_STATE:
                        displayPanel.resetEntryField();
                        displayPanel.cardLessPressed();
                        displayPanel.setState(TOKEN_REQUEST_STATE);
                        insertCard.setEnabled(false);
                        cardNumber.setEditable(false);

                        break;

                    case BALANCE_INQUIRY_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.resetEntryField();
                        displayPanel.setState(MAIN_MENU_STATE);
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.menuPanel(cardNumberEntered);
                        break;

                    case WITHIN_NBM_TRANSFER_STATE:

                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.resetEntryField();
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.menuPanel(cardNumberEntered);
                        displayPanel.setState(MAIN_MENU_STATE);
                        break;

                    case CASH_WITHDRAWAL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.resetEntryField();
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.menuPanel(cardNumberEntered);
                        displayPanel.setState(MAIN_MENU_STATE);
                        break;

                    case FUND_TRANSFER_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.resetEntryField();
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.menuPanel(cardNumberEntered);
                        displayPanel.setState(MAIN_MENU_STATE);
                        break;

                    case PERFOMING_TRANSFER_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.resetEntryField();
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.menuPanel(cardNumberEntered);
                        displayPanel.setState(MAIN_MENU_STATE);
                        break;

                    case BILL_PAYMENT_STATE:

                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.resetEntryField();
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.menuPanel(cardNumberEntered);
                        displayPanel.setState(MAIN_MENU_STATE);
                        break;

                    case ESCOM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.resetEntryField();
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.menuPanel(cardNumberEntered);
                        displayPanel.setState(MAIN_MENU_STATE);
                        break;

                    case MASM_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.resetEntryField();
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.menuPanel(cardNumberEntered);
                        displayPanel.setState(MAIN_MENU_STATE);
                        break;

                    case WATERBOARD_BILL_STATAE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.resetEntryField();
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.menuPanel(cardNumberEntered);
                        displayPanel.setState(MAIN_MENU_STATE);
                        break;

                    case DSTV_BILL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.resetEntryField();
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.menuPanel(cardNumberEntered);
                        displayPanel.setState(MAIN_MENU_STATE);
                        break;

                    case PERFOM_DSTV_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.resetEntryField();
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.menuPanel(cardNumberEntered);
                        displayPanel.setState(MAIN_MENU_STATE);
                        break;

                    case PERFORM_ESCOM_BILL_PAY:

                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.resetEntryField();
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.menuPanel(cardNumberEntered);
                        displayPanel.setState(MAIN_MENU_STATE);
                        break;

                    case PERFORM_MASM_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.resetEntryField();
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.menuPanel(cardNumberEntered);
                        displayPanel.setState(MAIN_MENU_STATE);
                        break;

                    case PERFORM_WATERBOARD_BILL_PAY:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();

                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.resetEntryField();
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.menuPanel(cardNumberEntered);
                        displayPanel.setState(MAIN_MENU_STATE);
                        break;

                    case REQUEST_STATEMENT_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.resetEntryField();
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.menuPanel(cardNumberEntered);
                        displayPanel.setState(MAIN_MENU_STATE);
                        break;

                    case CONFIRM_FUND_TRANFER_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.resetEntryField();
                        
                        
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.menuPanel(cardNumberEntered);

                        displayPanel.setState(MAIN_MENU_STATE);
                        break;

                    case CONFIRM_WITHDRAWAL_STATE:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.resetEntryField();
                        
                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.menuPanel(cardNumberEntered);

                        displayPanel.setState(MAIN_MENU_STATE);
                        break;

                    case OUTSIDE_BANKT_TRANSFER:
                        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);
                        //server response here
                        balance = atmClient.getAccountBalance();
                        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

                        statement = atmClient.getStatement();

                        displayPanel.clearOutBankField();

                        displayPanel.accIcon(cardNumberEntered);
                        displayPanel.menuPanel(cardNumberEntered);

                        displayPanel.setState(MAIN_MENU_STATE);
                        break;

                }

                //pass input from here customer Card Number + pin
            }
        }
        );

        insertCard.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                switch (displayPanel.getState()) {
                    case IDLE_STATE:
                        ejectCard();
                        displayPanel.setState(CARD_VERIFICATION_STATE);
                        cardState();

                        break;

                }
            }
        }
        );

    }

    public void fundTransferState() {
        //user account, selected amount, receipient
        atmClient.sendFundTranferRequest(cardNumberEntered, amount, account);
    }

    public void billPay(int billType) {
        atmClient.sendBillPayRequest(cardNumberEntered, billType, amount, account);
    }

    public void outsideBankTrabsfer(int outsideBankTransfer, String bankName) {

        //change to a suitable method
        atmClient.sendOutSideBankTransferRequest(cardNumberEntered, outsideBankTransfer, amount, account, outsideBankName);
    }

    public void withdrawalState() {
        
        
        
            atmClient.sendWithdrawalRequest(cardNumberEntered, amount);
        
        
        
        

    }

    public void pinState() {
        
        if(!displayPanel.getPasswordEntered().getText().isEmpty()){
        
            displayPanel.getPasswordEntered().setText(displayPanel.getPasswordEntered().getText());
        pinNumberEntered = Integer.parseInt(displayPanel.getPasswordEntered().getText().trim());
        
        if(cardNumberEntered == 0){
            
            atmClient.sendPinValiation(authenticationProcess.customerPIN(pinNumberEntered), account);

        }else 
            if(cardNumberEntered != 0) {
                
                atmClient.sendPinValiation(authenticationProcess.customerPIN(pinNumberEntered), cardNumberEntered);

            
            }
        }else{
            JOptionPane.showMessageDialog(null, "Please Enter Valid Pin");
        
        }
        
        
        
        
    }

    public void cardState(){
        
        if(!cardNumber.getText().isEmpty()){
            cardNumber.setText(cardNumber.getText());
        cardNumberEntered = Integer.parseInt(cardNumber.getText().trim());

        atmClient.sendCardValidation(authenticationProcess.customerCardNumber(cardNumberEntered));
        atmClient.sendBalanceRequest(cardNumberEntered, BALANCE_REQUEST, false);

        balance = atmClient.getAccountBalance();
        atmClient.sendStatementRequest(cardNumberEntered, STATEMENT_REQUEST, false);

        statement = atmClient.getStatement();
        cardNumber.setEditable(false);
        
        }else{
            JOptionPane.showMessageDialog(null, " Please Insert Virtual ATM Card");
        
        }
        

    }

}
