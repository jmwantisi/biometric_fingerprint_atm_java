/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atminterface;

import com.digitalpersona.onetouch.DPFPFingerIndex;
import com.digitalpersona.onetouch.DPFPTemplate;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.EnumMap;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javax.swing.*;
import com.seaglasslookandfeel.*;

/**
 *
 * @author jmwantisi
 */
//combine this class with fingeprint 
public class DisplayPanel extends JPanel {

    private CardLayout displayPanel;
    private JPanel atmInsertionPanel, performAnotherTransaction;
    private JPanel atmPinEntryPanel;
    private JPanel userMenuPanel;
    private JPanel fingerprintPanel, oneTimeTokenEntryPanel, cashWithdrawalPanel, performFundPanel, performfundTransferOutsideBank, viewAccountBalance, miniStatementPanel, outsideBankTransfer;
    private JTextArea fingerprintDisplayPanel, statementPrintArea, outBankSelectionList;
    private JPanel accountNumberRequestTokkenPanel;
    private JTextField cardNumber, oneTimeTokenEntryField; 
    private static JTextField pinNumber;
    private JTextArea text;
    private JLabel insertCard;
    private JPasswordField pinDisplay;
    private static JTextField transferFundDisplay, tokenRequestAreaField, tansferDisplayAfterReset, amountDisplay, outsideBankNotificationField, outsideBankTransferDisplay, outsideBankamountDisplay, outSideSelection;
    private static JTextField loginIcon;
    private static JTextArea pass = new JTextArea();
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
    private final int PERFOMING_TRANSFER_STATE = 14;
    private final int RECIEPIENT_ACCOUNT_STATE = 15;
    private final int TRANSFER_AMOUNT_STATE = 16;
    private final int TOKEN_REQUEST_STATE = 44;
    private final int ONE_TIME_ENTRY_STATE = 50;
    private static int state;
    private static ArrayList<byte[]> templateBinaryData;
    private MainMenu mainMenu;
    
    
    private JTextField nofiationField, transferNoficationField, accountBalanceDisplay;
    private JTextField amountField = new JTextField();
    private CashWindrawal cashWithdrawal;
    private FundTransferPanel fundTransferSelectionPanel;
    private BillPayment billPayment;
    private final int ESCOM_BILL_STATE = 17;
    private final int WATERBOARD_BILL_STATAE = 18;
    private final int MASM_BILL_STATE = 19;
    private final int DSTV_BILL_STATE = 20;
    private final int PERFOM_DSTV_BILL_PAY = 21;
    private final int PERFORM_ESCOM_BILL_PAY = 22;
    private final int PERFORM_WATERBOARD_BILL_PAY = 23;
    private final int PERFORM_MASM_BILL_PAY = 24;
    private final int OUTSIDE_BANKT_TRANSFER = 36;
    private final int PERFORM_OUTSIDE_BANK_TRANSFER = 37;
    private final int OUTSIDE_BANK_NAME_STATE = 40;
    private static boolean transferReset, escomRest, masmReset, dstvReset, waterBoardReset = false;
    

    public DisplayPanel(ArrayList<byte[]> data) {
        //new MainMenu();
        //cashWithdrawal = new CashWindrawal();
        
        this.performFundPanel = new JPanel();
        this.loginIcon = new JTextField();
        this.loginIcon.setForeground(Color.blue);
        this.loginIcon.setBackground(Color.black);
        this.loginIcon.setEditable(false);

        this.loginIcon.setBounds(280, 10, 190, 30);

        this.templateBinaryData = new ArrayList<>();
        this.templateBinaryData = null;
        this.displayPanel = new CardLayout();
        this.setLayout(displayPanel);
        this.setSize(200, 300);
        this.setBackground(Color.red);

        this.fingerprintDisplayPanel = new JTextArea("Place You Fingerprint On Scanner");

        this.insertCard = new JLabel("\nInsert Virtual ATM Card or Select Cardless to begin");
        insertCard.setFont(new Font("Arial Black", Font.BOLD, 15));
        this.insertCard.setForeground(Color.white);
        this.insertCard.setBackground(Color.black);
        this.insertCard.setBounds(10, 150, 500, 100);

        JLabel cardless = new JLabel("Cardless");
        cardless.setFont(new Font("Arial Black", Font.BOLD, 20));
        cardless.setForeground(Color.white);
        cardless.setBackground(Color.black);
        
        cardless.setBounds(380, 315, 300, 100);

        this.atmInsertionPanel = new JPanel();
        this.atmInsertionPanel.add(loginIcon);
        this.atmInsertionPanel.setBackground(Color.black);
        this.atmInsertionPanel.add(cardless);
        this.atmInsertionPanel.add(insertCard);
        this.atmInsertionPanel.setLayout(null);
        
        //cash withdrawal panel
       
        nofiationField = new JTextField("                Select Or Enter Amount");
        nofiationField.setFont(new Font("Arial Black", Font.BOLD, 18));
        nofiationField.setForeground(Color.blue);
        nofiationField.setBackground(Color.gray);
        nofiationField.setEditable(false);
        nofiationField.setBounds(10, 10, 479, 70);
        
        
        amountField = new JTextField("0.00");
        amountField.setFont(new Font("Arial Black", Font.BOLD, 18));
        amountField.setForeground(Color.blue);
        amountField.setBackground(Color.gray);
        amountField.setEditable(false);
        amountField.setBounds(160, 90, 200, 50);
        
        //this.cashWithdrawalPanel = new JPanel();
        

        JLabel pin = new JLabel("\nEnter Your Pin");
        pin.setFont(new Font("Arial", Font.BOLD, 18));
        pin.setForeground(Color.blue);
        pin.setBackground(Color.black);
        pin.setBounds(190, 150, 300, 100);

        this.pinDisplay = new JPasswordField("");
        this.pinDisplay.setForeground(Color.blue);
        this.pinDisplay.setBackground(Color.gray);
        this.pinDisplay.setEditable(false);
        this.pinDisplay.setBounds(160, 240, 200, 50);

        

        this.atmPinEntryPanel = new JPanel();
        this.atmPinEntryPanel.setBackground(Color.black);
        this.atmPinEntryPanel.add(pin);
        this.atmInsertionPanel.add(loginIcon);
        this.atmPinEntryPanel.add(pinDisplay);
        this.atmPinEntryPanel.setLayout(null);
        
        //fund transfer panel
        this.transferFundDisplay = new JTextField();
        this.transferFundDisplay.setFont(new Font("Arial Black", Font.BOLD, 12));
        this.transferFundDisplay.setForeground(Color.blue);
        this.transferFundDisplay.setBackground(Color.gray);
        this.transferFundDisplay.setEditable(false);
        this.transferFundDisplay.setBounds(160, 280, 200, 50);
        
        //this should appear once the account has been entered
       
        
        transferNoficationField = new JTextField("       Enter recepient account");
        transferNoficationField.setFont(new Font("Arial Black", Font.BOLD, 20));
        transferNoficationField.setForeground(Color.blue);
        transferNoficationField.setBackground(Color.gray);
        transferNoficationField.setEditable(false);
        transferNoficationField.setBounds(10, 150, 479, 100);
        
        JLabel mainMenuLabel2 = new JLabel("Back Main Menu");
        mainMenuLabel2.setFont(new Font("Arial Black", Font.BOLD, 15));
        mainMenuLabel2.setForeground(Color.white);
        mainMenuLabel2.setBackground(Color.gray);
        mainMenuLabel2.setBounds(340, 310, 479, 120);
        
        
        
        this.performFundPanel.setBackground(Color.black);
        this.performFundPanel.add(loginIcon);
        this.performFundPanel.add(transferFundDisplay);
        this.performFundPanel.add(transferNoficationField);
        this.performFundPanel.add(mainMenuLabel2);
        this.performFundPanel.setLayout(null);
        
        
        
        //outside bank
        
        JLabel outsideBankLabel2 = new JLabel("OUTSIDE BANK TRANSFER");
        outsideBankLabel2.setFont(new Font("Arial Black", Font.BOLD, 15));
        outsideBankLabel2.setForeground(Color.blue);
        outsideBankLabel2.setBackground(Color.gray);
        outsideBankLabel2.setBounds(130, 5, 350, 50);
        
        this.outsideBankTransferDisplay = new JTextField();
        this.outsideBankTransferDisplay.setFont(new Font("Arial Black", Font.BOLD, 15));
        this.outsideBankTransferDisplay.setForeground(Color.blue);
        this.outsideBankTransferDisplay.setBackground(Color.gray);
        this.outsideBankTransferDisplay.setEditable(false);
        this.outsideBankTransferDisplay.setBounds(160, 280, 200, 50);
        
        outsideBankNotificationField = new JTextField("       Enter recepient account");
        outsideBankNotificationField.setFont(new Font("Arial Black", Font.BOLD, 20));
        outsideBankNotificationField.setForeground(Color.blue);
        outsideBankNotificationField.setBackground(Color.gray);
        outsideBankNotificationField.setEditable(false);
        outsideBankNotificationField.setBounds(10, 150, 479, 100);
        
        this.performfundTransferOutsideBank = new JPanel();
        this.performfundTransferOutsideBank.setBackground(Color.black);
        this.performfundTransferOutsideBank.add(outsideBankTransferDisplay);
        this.performfundTransferOutsideBank.add(outsideBankNotificationField);
        this.performfundTransferOutsideBank.add(mainMenuLabel2);
        this.performfundTransferOutsideBank.add(outsideBankLabel2);
        this.performfundTransferOutsideBank.setLayout(null);
        
        
        
        //outside bank fund transfer bank selecton list
        
         
        outBankSelectionList = new JTextArea();
        
        outBankSelectionList.setEnabled(false);
        outBankSelectionList.setForeground(Color.blue);
        outBankSelectionList.setBackground(Color.gray);
        outBankSelectionList.setFont(new Font("Arial Black", Font.BOLD, 15));
        outBankSelectionList.setBounds(10, 100, 480, 180);
        
        JLabel outsideBankLabel = new JLabel("OUTSIDE BANK TRANSFER");
        outsideBankLabel.setFont(new Font("Arial Black", Font.BOLD, 15));
        outsideBankLabel.setForeground(Color.blue);
        outsideBankLabel.setBackground(Color.gray);
        outsideBankLabel.setBounds(130, 5, 350, 50);
        
        String instruction = "\n ENTER A SELECTION NUMBER TO SELECT A BANK FOR TRANSFER";
        //JLab
        
        String bankList = "\n\n\t\t 1. Standard Bank \n\t\t 2. NBS \n\t\t 3. FMB \n\t\t 4. FDH \n\t\t 5. ECCO Bank \n\t\t 6. OIBM \n\t\t 7. NED Bank ";
        
        outBankSelectionList.append(instruction);
        
        outBankSelectionList.append(bankList);
        outBankSelectionList.setFont(new Font("Arial Black", Font.BOLD, 11));
        
        
                
        outSideSelection = new JTextField("");
        outSideSelection.setFont(new Font("Arial Black", Font.BOLD, 15));
        outSideSelection.setForeground(Color.blue);
        outSideSelection.setBackground(Color.gray);
        outSideSelection.setEditable(false);
        outSideSelection.setBounds(230, 290, 70, 50);
        
        JLabel bankSelectedLabel = new JLabel("Selection");
        bankSelectedLabel.setFont(new Font("Arial Black", Font.BOLD, 15));
        bankSelectedLabel.setForeground(Color.blue);
        bankSelectedLabel.setBackground(Color.gray);
        bankSelectedLabel.setBounds(130, 290, 100, 50);
        
        this.outsideBankTransfer = new JPanel();
        this.outsideBankTransfer.add(loginIcon);
        this.outsideBankTransfer.setBackground(Color.black);
        this.outsideBankTransfer.add(outBankSelectionList);
        this.outsideBankTransfer.add(outSideSelection);
        this.outsideBankTransfer.add(mainMenuLabel2);
        this.outsideBankTransfer.add(outsideBankLabel);
        this.outsideBankTransfer.add(bankSelectedLabel);
        this.outsideBankTransfer.setLayout(null);
        
        
        
        
        accountBalanceDisplay = new JTextField("");
        accountBalanceDisplay.setFont(new Font("Arial Black", Font.BOLD, 25));
        accountBalanceDisplay.setForeground(Color.blue);
        accountBalanceDisplay.setBackground(Color.gray);
        accountBalanceDisplay.setEditable(false);
        accountBalanceDisplay.setBounds(140, 170, 300, 100);
        
        JLabel mwk = new JLabel("MWK");
        mwk.setFont(new Font("Arial Black", Font.BOLD, 25));
        mwk.setForeground(Color.blue);
        mwk.setBackground(Color.gray);
        mwk.setBounds(50, 170, 479, 100);
        
        JLabel accountBalance = new JLabel("  Account Balance");
        accountBalance.setFont(new Font("Arial Black", Font.BOLD, 40));
        accountBalance.setForeground(Color.blue);
        accountBalance.setBackground(Color.gray);
        accountBalance.setBounds(20, 45, 479, 100);
        
        JLabel mainMenuLabel = new JLabel("Back Main Menu");
        mainMenuLabel.setFont(new Font("Arial Black", Font.BOLD, 15));
        mainMenuLabel.setForeground(Color.blue);
        mainMenuLabel.setBackground(Color.gray);
        mainMenuLabel.setBounds(340, 310, 479, 120);
     
        
        this.viewAccountBalance = new JPanel();
        this.viewAccountBalance.add(loginIcon);
         this.viewAccountBalance.setLayout(null);
        this.viewAccountBalance.setBackground(Color.black);
        this.viewAccountBalance.add(accountBalanceDisplay);
        this.viewAccountBalance.add(mainMenuLabel);
        this.viewAccountBalance.add(mwk);
        this.viewAccountBalance.add(accountBalance);
        
       
        
        JLabel miniStatementLabel = new JLabel("ACCOUNT MINI STATEMENT");
        miniStatementLabel.setFont(new Font("Arial Black", Font.BOLD, 15));
        miniStatementLabel.setForeground(Color.blue);
        miniStatementLabel.setBackground(Color.gray);
        
        miniStatementLabel.setBounds(120, 20, 479, 100);
        
        statementPrintArea = new JTextArea();
        
        statementPrintArea.setEnabled(false);
        statementPrintArea.setForeground(Color.blue);
        statementPrintArea.setBackground(Color.gray);
        statementPrintArea.setBounds(10, 100, 480, 250);
        
        this.miniStatementPanel = new JPanel();
         this.miniStatementPanel.add(loginIcon);
        this.miniStatementPanel.setBackground(Color.black);
        this.miniStatementPanel.add(statementPrintArea);
        this.miniStatementPanel.add(mainMenuLabel);
        this.miniStatementPanel.add(miniStatementLabel);
        this.miniStatementPanel.setLayout(null);
        

        JLabel fingerprint = new JLabel("Place Fingerprint on the Scanner");
        fingerprint.setForeground(Color.blue);
       // fingerprint.setBackground(Color.black);
        fingerprint.setBounds(50, 10, 100, 50);
        
        
        //fingerprint panel
        this.fingerprintPanel = new JPanel();
        this.fingerprintPanel.add(loginIcon);
        this.fingerprintPanel.setLayout(null);
        this.fingerprintPanel.setBackground(Color.black);
        this.fingerprintDisplayPanel.setBounds(210, 150, 300, 100);
        this.fingerprintPanel.add(fingerprintDisplayPanel);
        this.fingerprintPanel.add(fingerprint);
        this.fingerprintPanel.add(new FingerpintPanel(data, this));
        
        //this.atmPinEntryPanel.add(pinDisplay);

        
        
        //token useage panel
        
         JLabel help = new JLabel(" One Time Token Lets you use ATM services with no ATM card ");
        help.setFont(new Font("Arial Black", Font.BOLD, 12));
        help.setForeground(Color.blue);
        help.setBackground(Color.black);
        help.setBounds(95, 130, 500, 50);
        
        JLabel help2 = new JLabel(" token number recieved via SMS");
        help2.setFont(new Font("Arial Black", Font.BOLD, 15));
        help2.setForeground(Color.blue);
        help2.setBackground(Color.black);
        help2.setBounds(95, 160, 500, 50);
        
        JLabel tokenEntryMessage1 = new JLabel(" Please Enter 6 digit ");
        tokenEntryMessage1.setFont(new Font("Arial Black", Font.BOLD, 15));
        tokenEntryMessage1.setForeground(Color.blue);
        tokenEntryMessage1.setBackground(Color.black);
        tokenEntryMessage1.setBounds(95, 130, 500, 50);
        
        JLabel tokenEntryMessage2 = new JLabel(" token number recieved via SMS");
        tokenEntryMessage2.setFont(new Font("Arial Black", Font.BOLD, 15));
        tokenEntryMessage2.setForeground(Color.blue);
        tokenEntryMessage2.setBackground(Color.black);
        tokenEntryMessage2.setBounds(95, 160, 500, 50);
        
        
        oneTimeTokenEntryField = new JTextField("");
        oneTimeTokenEntryField.setFont(new Font("Arial Black", Font.BOLD, 25));
        oneTimeTokenEntryField.setForeground(Color.blue);
        oneTimeTokenEntryField.setBackground(Color.gray);
        oneTimeTokenEntryField.setEditable(false);
        oneTimeTokenEntryField.setBounds(105, 220, 300, 100);
        
        
        oneTimeTokenEntryPanel = new JPanel();
        this.oneTimeTokenEntryPanel.add(loginIcon);
        oneTimeTokenEntryPanel.add(tokenEntryMessage1);
        oneTimeTokenEntryPanel.add(tokenEntryMessage2);
        oneTimeTokenEntryPanel.add(oneTimeTokenEntryField);
        this.oneTimeTokenEntryPanel.setBackground(Color.black);
        this.oneTimeTokenEntryPanel.setLayout(null);
        
        //Token request Panel
        
        JLabel requestMessageLine1 = new JLabel(" Enter Account Number");
        requestMessageLine1.setFont(new Font("Arial Black", Font.BOLD, 15));
        requestMessageLine1.setForeground(Color.blue);
        requestMessageLine1.setBackground(Color.black);
        requestMessageLine1.setBounds(95, 130, 500, 50);
        
        JLabel requestMessageLine2 = new JLabel(" to request ATM Cardless Token");
        requestMessageLine2.setFont(new Font("Arial Black", Font.BOLD, 15));
        requestMessageLine2.setForeground(Color.blue);
        requestMessageLine2.setBackground(Color.black);
        requestMessageLine2.setBounds(95, 160, 500, 50);
        
        
        tokenRequestAreaField = new JTextField();
        tokenRequestAreaField.setFont(new Font("Arial Black", Font.BOLD, 25));
        tokenRequestAreaField.setForeground(Color.blue);
        tokenRequestAreaField.setBackground(Color.gray);
        tokenRequestAreaField.setEditable(false);
        tokenRequestAreaField.setBounds(105, 220, 300, 100);
        
        
        accountNumberRequestTokkenPanel = new JPanel();
        this.accountNumberRequestTokkenPanel.add(loginIcon);
        accountNumberRequestTokkenPanel.add(requestMessageLine1);
        accountNumberRequestTokkenPanel.add(requestMessageLine2);
        accountNumberRequestTokkenPanel.add(tokenRequestAreaField);
        this.accountNumberRequestTokkenPanel.setBackground(Color.black);
        this.accountNumberRequestTokkenPanel.setLayout(null);
        
        
        
        JLabel performMessage = new JLabel(" Would like to Perform Transaction?");
        performMessage.setFont(new Font("Arial", Font.BOLD, 20));
        performMessage.setForeground(Color.blue);
        performMessage.setBounds(70, 120, 500, 150);
        
        /*
        JLabel performMessage2 = new JLabel(" ");
        performMessage2.setFont(new Font("Arial", Font.BOLD, 20));
        performMessage2.setForeground(Color.blue);
        performMessage2.setBounds(200, 100, 100, 300);
        
        */
        performAnotherTransaction = new JPanel();
        this.performAnotherTransaction.add(loginIcon);
        performAnotherTransaction.add(performMessage);
       // performAnotherTransaction.add(performMessage2);
        this.performAnotherTransaction.setBackground(Color.black);
        this.performAnotherTransaction.setLayout(null);
        
        

        this.cardNumber = new JTextField();
        this.pinNumber = new JTextField();

        mainMenu = new MainMenu();
        cashWithdrawal = new CashWindrawal();
        fundTransferSelectionPanel = new FundTransferPanel();
        billPayment = new BillPayment();
        
        this.add(atmInsertionPanel, "IDLE PANEL");
        this.add(atmPinEntryPanel, "PIN PANEL");
        this.add(mainMenu, "USER MENU");
        this.add(accountNumberRequestTokkenPanel, "TOKKEN REQUEST PANEL");
        this.add(cashWithdrawal, "CASH WITHDRAWAL PANEL");
        this.add(fundTransferSelectionPanel, "FUND TRANSFER PANEL");
        this.add(performFundPanel, "PERFORM FUND TRANSFER");
        this.add(viewAccountBalance, "ACCOUNT BALANCE");
        this.add(billPayment, "BILL PAYMENT");
        this.add(miniStatementPanel, "MINI STATEMENT");
        this.add(outsideBankTransfer, "OUTSIDE BANK TRANSFER");
        this.add(performfundTransferOutsideBank, "PERFORM FUND TRANSFER OUTSIDE BANK");
        this.add(oneTimeTokenEntryPanel, "TOKEN ENTRY");
        this.add(performAnotherTransaction, "PERFORM ANOTHER TRANSACTION");
        
       
        //pinPanel();
        idlePanel();

    }
    
    public void tokenEntry() {
        //Enter pin number to request one time tokken
        pass.setText("");
        oneTimeTokenEntryField.setText("");
        
        displayPanel.show(this, "TOKEN ENTRY");
    }
    
    public void cardLessPressed() {
        //Enter pin number to request one time tokken
        pass.setText("");
        tokenRequestAreaField.setText("");
        
        displayPanel.show(this, "TOKKEN REQUEST PANEL");
    }
    
    public void outsideBankMenu(){
        outsideBankTransfer.add(loginIcon);
        displayPanel.show(this, "OUTSIDE BANK TRANSFER");
    
    }
    
    public void outsideBankAccountEntryMenu(String bank){
        pass.setText("");
        outsideBankNotificationField.setText(bank);
        
        performfundTransferOutsideBank.add(loginIcon);
        displayPanel.show(this, "PERFORM FUND TRANSFER OUTSIDE BANK");
        
    
    }
    
    public void menuPanel(int acc) {

        loginIcon.setText("Account: " + acc);
        mainMenu.add(loginIcon);
        state = MAIN_MENU_STATE;
        displayPanel.show(this, "USER MENU");
        //g2d.draw(line);

    }
    
    public void pinPanel() {
        //populate then call the card
        displayPanel.show(this, "PIN PANEL");
    }
    
    public void balancePanel(){
        
        JLabel mainMenuLabel = new JLabel("Back Main Menu");
        mainMenuLabel.setFont(new Font("Arial Black", Font.BOLD, 15));
        mainMenuLabel.setForeground(Color.white);
        mainMenuLabel.setBackground(Color.gray);
        mainMenuLabel.setBounds(340, 310, 479, 120);
        viewAccountBalance.add(mainMenuLabel);
        viewAccountBalance.add(loginIcon);
        displayPanel.show(this, "ACCOUNT BALANCE");
    }
    
    public void billPaymentPanel(){
        billPayment.add(loginIcon);
        displayPanel.show(this, "BILL PAYMENT");
    
    }
    
    public void cashWithdrawalPanel(){
        cashWithdrawal.add(loginIcon);
        displayPanel.show(this, "CASH WITHDRAWAL PANEL");
        
    }
    
    public void fundTransferSelectionPanel(){
        fundTransferSelectionPanel.add(loginIcon);
        displayPanel.show(this, "FUND TRANSFER PANEL");
        
    }
    
    public void showBankStatement(ArrayList<String> transactions){
        
        statementPrintArea.setText("");
         //example
        //copy from an actual bank statement
        //String[] sampleStatement = {"creadi 5000", "debit 112312.21", "eft james 49923"};
        statementPrintArea.setFont(new Font("Arial Black", Font.BOLD, 15));
        statementPrintArea.append("\n DATE/TIME                                  TRANSACTION                CREDIT/DEBIT \n");
        for(String tran: transactions){
            statementPrintArea.setFont(new Font("Arial Black", Font.BOLD, 10));
            statementPrintArea.append("\n "+tran);
        }
        displayPanel.show(this, "MINI STATEMENT");
        
    
    }
    
    
    
    public void performTransfer(String notification){
        
        this.transferNoficationField.setText(notification);
        
        displayPanel.show(this, "PERFORM FUND TRANSFER");
        
    }

    public void fingerprintPanel(ArrayList<byte[]> data) {
        System.out.println("panel called");
        templateBinaryData = data;

        this.add(new FingerpintPanel(templateBinaryData, this), "FINGERPRINT PANEL");
        displayPanel.show(this, "FINGERPRINT PANEL");

    }
    
    public void resetAllField(){
        //outsideBankamountDisplay, outSideSelection, amountDisplay, oneTimeTokenEntryField, tokenRequestAreaField 
        //pinDisplay, amountField, transferReset, tansferDisplayAfterReset, transferFundDisplay
                
                
    }
    
    
    public void outsideBankTransferAmountClear(){
    
        outsideBankTransferDisplay.setText("");
    
    }
     
    public void afterTrasferReset(){
        tansferDisplayAfterReset.setText("");
        transferFundDisplay.setText("");
    
    }
    
    
    //outside transfer reset
    public void transferAmountReset(){
        outSideSelection.setText("");
        amountField.setText("");
        outsideBankamountDisplay.setText("");
        amountDisplay.setText("");
    
    }
    
    //clear text
    public void clearTrasferField(){
        transferNoficationField.setText("");
    
    }
    
    public void clearWithdrawalNofication(){
        nofiationField.setText("");
        
    
    }
    
    public JTextField setTransferNoticationField(String notification){
    
    
        nofiationField.setText(notification);
        return nofiationField;
    }
    
    
    //call for succesful or failed withdrawal
    public void performAnotherTransactionInWithdrawal(){
        
        //displayPanel.show(this, "PERFORM ANOTHER TRANSACTION");
        
        nofiationField.setText("                 Select Or Enter Amount");
        amountField.setText("");
        
    }
    
    public void clearPinField(){
        pass.setText("");
        pinDisplay.setText("");
    
    }
    
    //this function should be responsible for clearing all fields
    public void clearFields(){
        
        pass.setText("");
        pinDisplay.setText("");
        pinNumber.setText("");
        tokenRequestAreaField.setText("");
        oneTimeTokenEntryField.setText("");
    
    }
    
    
    
    public JTextField toUtiliseToken(){
    
        return oneTimeTokenEntryField;
    
    }
    
    public JTextField tokenRequestAccount(){
    
        return tokenRequestAreaField;
    }
    
    
    
    public JTextField getOutSideBankAccount(){
    
        return outsideBankTransferDisplay;
    }
    
    public JTextField getOutSideBankTransferAmout(){
    
        return outsideBankamountDisplay;
    }
    
    public JTextField getOutBankName(){
        return outSideSelection;
    }
    
    
    public void clearOutBankField(){
        pass.setText("");
        outSideSelection.setText("");
    
    }
    
     public void accIcon(int acc) {
        loginIcon.setText("Account: " + acc);
    }

    
    
    
    public class BillPayment extends JPanel {

        public BillPayment() {
            this.setBackground(Color.black);
            //this.setSize(200, 300);
            //userMenuPanel = new JPanel();
            JLabel one = new JLabel("ESCOM");
            JLabel two = new JLabel("WATERBOARD");
            JLabel five = new JLabel("MASM");
            JLabel ten = new JLabel("DSTV");
            
            this.add(loginIcon);
            this.setBackground(Color.black);
            
            
            one.setForeground(Color.white);
            one.setBounds(90, 155, 250, 30);
            one.setFont(new Font("Arial Black", Font.BOLD, 15));
            
            two.setForeground(Color.white);
            two.setBounds(310, 155, 200, 30);
            two.setFont(new Font("Arial Black", Font.BOLD, 15));
            
            
            five.setForeground(Color.white);
            five.setBounds(95, 255, 200, 30);
            five.setFont(new Font("Arial Black", Font.BOLD, 15));
            
            ten.setForeground(Color.white);
            ten.setBounds(350, 255, 200, 30);
            ten.setFont(new Font("Arial Black", Font.BOLD, 15));
           
            JLabel mainMenuLabel = new JLabel("Back Main Menu");
            mainMenuLabel.setFont(new Font("Arial Black", Font.BOLD, 15));
            mainMenuLabel.setForeground(Color.white);
            mainMenuLabel.setBackground(Color.gray);
            mainMenuLabel.setBounds(340, 310, 479, 120);
            
            this.add(one);
            this.add(two);
            this.add(five);
            this.add(ten);
            
            this.add(mainMenuLabel);
            
            loginIcon.setForeground(Color.white);
            loginIcon.setFont(new Font("Arial Black", Font.BOLD, 12));
            this.add(loginIcon);
            this.setLayout(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            g.setColor(Color.blue);
            
           // g.drawLine(0, 60, 1000, 60);
            //g.drawLine(0, 100, 1000, 100);
            
            g.drawLine(0, 150, 1000, 150);
            g.drawLine(0, 190, 1000, 190);
            
            g.drawLine(0, 250, 1000, 250);
            g.drawLine(0, 290, 1000, 290);
            
            g.drawLine(0, 350, 1000, 350);
            g.drawLine(0, 390, 1000, 390);
            
            g.drawLine(250, 150, 250, 390);
            g.setFont(new Font("Arial Black", Font.BOLD, 20));
            g.setPaintMode();
            
        }

    }
    
    public class FundTransferPanel extends JPanel {

        public FundTransferPanel() {
            this.setBackground(Color.black);
            //this.setSize(200, 300);
            //userMenuPanel = new JPanel();
            JLabel within = new JLabel("Within NBM");
            JLabel outside = new JLabel("Outside NBM");
            
            this.add(loginIcon);
            this.setBackground(Color.black);
            
            
            within.setForeground(Color.white);
            within.setBounds(10, 155, 250, 30);
            within.setFont(new Font("Arial Black", Font.BOLD, 15));
            
            outside.setForeground(Color.white);
            outside.setBounds(370, 155, 200, 30);
            outside.setFont(new Font("Arial Black", Font.BOLD, 15));
            
            JLabel mainMenuLabel = new JLabel("Back Main Menu");
            mainMenuLabel.setFont(new Font("Arial Black", Font.BOLD, 15));
            mainMenuLabel.setForeground(Color.white);
            mainMenuLabel.setBackground(Color.gray);
            mainMenuLabel.setBounds(340, 310, 479, 120);
            
            this.add(mainMenuLabel);
            this.add(within);
            this.add(outside);
           
            
            loginIcon.setForeground(Color.white);
            loginIcon.setFont(new Font("Arial Black", Font.BOLD, 12));
            this.add(loginIcon);
            this.setLayout(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            g.setColor(Color.blue);
            
           // g.drawLine(0, 60, 1000, 60);
            //g.drawLine(0, 100, 1000, 100);
            
            g.drawLine(0, 150, 1000, 150);
            g.drawLine(0, 190, 1000, 190);
            
            g.drawLine(0, 250, 1000, 250);
            g.drawLine(0, 290, 1000, 290);
            
            g.drawLine(0, 350, 1000, 350);
            g.drawLine(0, 390, 1000, 390);
            
            g.drawLine(250, 150, 250, 390);
            g.setFont(new Font("Arial Black", Font.BOLD, 20));
            g.setPaintMode();
            
        }

    }
    
    
    
    public class CashWindrawal extends JPanel {

        public CashWindrawal() {
            this.setBackground(Color.black);
            //this.setSize(200, 300);
            //userMenuPanel = new JPanel();
            JLabel one = new JLabel("MWK 1,000.00");
            JLabel two = new JLabel("MWK 10,000.00");
            JLabel five = new JLabel("MKW 2,000.00");
            JLabel ten = new JLabel("MWK 50,000.00");
            JLabel twenty = new JLabel("MWK 5,000.00");
            JLabel fifty = new JLabel("MWK 50,000.00");
            
            this.setBackground(Color.black);
            this.add(nofiationField);
            this.add(amountField);
            this.add(loginIcon);
            one.setForeground(Color.white);
            one.setBounds(10, 155, 250, 30);
            one.setFont(new Font("Arial Black", Font.BOLD, 15));
            
            two.setForeground(Color.white);
            two.setBounds(350, 155, 200, 30);
            two.setFont(new Font("Arial Black", Font.BOLD, 15));
            
            
            five.setForeground(Color.white);
            five.setBounds(10, 255, 200, 30);
            five.setFont(new Font("Arial Black", Font.BOLD, 15));
            
            ten.setForeground(Color.white);
            ten.setBounds(350, 255, 200, 30);
            ten.setFont(new Font("Arial Black", Font.BOLD, 15));
            
            twenty.setForeground(Color.white);
            twenty.setBounds(10, 355, 200, 30);
            twenty.setFont(new Font("Arial Black", Font.BOLD, 15));
            
            fifty.setForeground(Color.white);
            fifty.setBounds(350, 355, 200, 30);
            fifty.setFont(new Font("Arial Black", Font.BOLD, 15));
            
            JLabel mainMenuLabel = new JLabel("Back Main Menu");
            mainMenuLabel.setFont(new Font("Arial Black", Font.BOLD, 15));
            mainMenuLabel.setForeground(Color.white);
            mainMenuLabel.setBackground(Color.gray);
            mainMenuLabel.setBounds(340, 310, 479, 120);
            
            this.add(one);
            this.add(two);
            this.add(five);
            this.add(ten);
            this.add(twenty);
            this.add(mainMenuLabel);
            
            loginIcon.setForeground(Color.white);
            loginIcon.setFont(new Font("Arial Black", Font.BOLD, 12));
            this.add(loginIcon);
            this.setLayout(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            g.setColor(Color.blue);
            
           // g.drawLine(0, 60, 1000, 60);
            //g.drawLine(0, 100, 1000, 100);
            
            g.drawLine(0, 150, 1000, 150);
            g.drawLine(0, 190, 1000, 190);
            
            g.drawLine(0, 250, 1000, 250);
            g.drawLine(0, 290, 1000, 290);
            
            g.drawLine(0, 350, 1000, 350);
            g.drawLine(0, 390, 1000, 390);
            
            g.drawLine(250, 150, 250, 390);
            g.setFont(new Font("Arial Black", Font.BOLD, 20));
            g.setPaintMode();
            
        }

    }
    

    public class MainMenu extends JPanel {

        public MainMenu() {
            this.setBackground(Color.black);
            //this.setSize(200, 300);
            //userMenuPanel = new JPanel();
            this.add(loginIcon);
            
            JLabel withdrawalLabel = new JLabel("Virtual Cash Withdrawal");
            JLabel balanceInqueryLabel = new JLabel("Balance Inquery");
            JLabel fundTransferLabel = new JLabel("Fund Transfer");
            JLabel billPayment = new JLabel("Bill Payment");
            JLabel requestBankStatement = new JLabel("Request Statement");
            
            withdrawalLabel.setForeground(Color.white);
            withdrawalLabel.setBounds(15, 155, 250, 30);
            withdrawalLabel.setFont(new Font("Arial Black", Font.BOLD, 15));
            
            balanceInqueryLabel.setForeground(Color.white);
            balanceInqueryLabel.setBounds(295, 155, 200, 30);
            balanceInqueryLabel.setFont(new Font("Arial Black", Font.BOLD, 15));
            
            
            fundTransferLabel.setForeground(Color.white);
            fundTransferLabel.setBounds(60, 255, 200, 30);
            fundTransferLabel.setFont(new Font("Arial Black", Font.BOLD, 15));
            
            billPayment.setForeground(Color.white);
            billPayment.setBounds(315, 255, 200, 30);
            billPayment.setFont(new Font("Arial Black", Font.BOLD, 15));
            
            requestBankStatement.setForeground(Color.white);
            requestBankStatement.setBounds(35, 355, 200, 30);
            requestBankStatement.setFont(new Font("Arial Black", Font.BOLD, 15));
            
            this.add(withdrawalLabel);
            this.add(balanceInqueryLabel);
            this.add(billPayment);
            this.add(fundTransferLabel);
            this.add(requestBankStatement);
            
            loginIcon.setForeground(Color.white);
            loginIcon.setFont(new Font("Arial Black", Font.BOLD, 12));
            this.add(loginIcon);
            this.setLayout(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            g.setColor(Color.blue);
            
           // g.drawLine(0, 60, 1000, 60);
            //g.drawLine(0, 100, 1000, 100);
            
            g.drawLine(0, 150, 1000, 150);
            g.drawLine(0, 190, 1000, 190);
            
            g.drawLine(0, 250, 1000, 250);
            g.drawLine(0, 290, 1000, 290);
            
            g.drawLine(0, 350, 1000, 350);
            g.drawLine(0, 390, 1000, 390);
            
            g.drawLine(250, 150, 250, 390);
            g.setFont(new Font("Arial Black", Font.BOLD, 20));
            g.setPaintMode();
            
        }

    }

   
    public static void setPinNumber(String pin) {
        pinNumber.setText(pin);
    }

    public String getPinNumber() {
        return pass.getText();
    }

    public void idlePanel() {
        state = IDLE_STATE; //idle state
        pass.setText("");
        pinDisplay.setText("");
        displayPanel.show(this, "IDLE PANEL");

    }
    
    public JTextField responseFromServerSuccefulBill(String notification){
        transferNoficationField.setText(notification);
        
        return transferNoficationField;
    }
    
    public JTextField responseFromServerUnSuccefulBill(String notification){
        transferNoficationField.setText(notification);
        
        return transferNoficationField;
    }
    
    public JTextField transferNoficationDisplay(String notification){
        transferNoficationField.setText(notification);
        return transferNoficationField;
    }
    
    public JTextField outsideBankNoficationDisplay(String notification){
        outsideBankNotificationField.setText(notification);
        return outsideBankNotificationField;
    }
    
    public void resetEntryField(){
        pass.setText("");
    }
    
    public void clearAferResetFundTransferField(){
        pass.setText("");
        tansferDisplayAfterReset.setText("");
        
    }
    
    public void clearFundTransferField() {

        pass.setText("");
        transferFundDisplay.setText("");

    }
    
    public void clearOutsideBankAccountField() {

        pass.setText("");
        outsideBankTransferDisplay.setText("");

    }
    
    public void clearOutSideBankAmountField(){
        pass.setText("");
        outsideBankamountDisplay.setText("");
        
    }
    
    public void resetTransferBill(){
        transferReset = true;
        System.out.println("reset method called");
        this.amountDisplay.setText("");
        this.performFundPanel.remove(amountDisplay);
        this.performFundPanel.repaint();
        this.performFundPanel.revalidate();
        
        pass.setText("");
        
        this.tansferDisplayAfterReset = new JTextField("");
        this.tansferDisplayAfterReset.setFont(new Font("Arial Black", Font.BOLD, 15));
        this.tansferDisplayAfterReset.setForeground(Color.gray);
        //this.tansferDisplayAfterReset.setBackground(Color.yellow);
        this.tansferDisplayAfterReset.setEditable(false);
        
        this.tansferDisplayAfterReset.setBounds(160, 280, 200, 50);
        
        this.performFundPanel.add(tansferDisplayAfterReset);
        //this.add(performFundPanel, "PERFORM FUND TRANSFER");
    
    }
    
     public void resetEscomBill(){
        escomRest = true;
        System.out.println("reset escom called");
        this.amountDisplay.setText("");
        this.performFundPanel.remove(amountDisplay);
        this.performFundPanel.repaint();
        this.performFundPanel.revalidate();
        
        pass.setText("");
        
        this.tansferDisplayAfterReset = new JTextField("");
        this.tansferDisplayAfterReset.setFont(new Font("Arial Black", Font.BOLD, 15));
        this.tansferDisplayAfterReset.setForeground(Color.gray);
        this.tansferDisplayAfterReset.setBackground(Color.gray);
        this.tansferDisplayAfterReset.setEditable(false);
        
        this.tansferDisplayAfterReset.setBounds(160, 280, 200, 50);
        
        this.performFundPanel.add(tansferDisplayAfterReset);
        //this.add(performFundPanel, "PERFORM FUND TRANSFER");
    
    }
     
      public void resetWaterBill(){
        waterBoardReset = true;
        System.out.println("reset waterboard called");
        this.amountDisplay.setText("");
        this.performFundPanel.remove(amountDisplay);
        this.performFundPanel.repaint();
        this.performFundPanel.revalidate();
        
        pass.setText("");
        
        this.tansferDisplayAfterReset = new JTextField("");
        this.tansferDisplayAfterReset.setFont(new Font("Arial Black", Font.BOLD, 15));
        this.tansferDisplayAfterReset.setForeground(Color.gray);
        this.tansferDisplayAfterReset.setBackground(Color.gray);
        this.tansferDisplayAfterReset.setEditable(false);
        
        this.tansferDisplayAfterReset.setBounds(160, 280, 200, 50);
        
        this.performFundPanel.add(tansferDisplayAfterReset);
        //this.add(performFundPanel, "PERFORM FUND TRANSFER");
    
    }
      
       public void resetMasmBill(){
        masmReset = true;
        System.out.println("reset masm called");
        this.amountDisplay.setText("");
        this.performFundPanel.remove(amountDisplay);
        this.performFundPanel.repaint();
        this.performFundPanel.revalidate();
        
        pass.setText("");
        
        this.tansferDisplayAfterReset = new JTextField("");
        this.tansferDisplayAfterReset.setFont(new Font("Arial Black", Font.BOLD, 15));
        this.tansferDisplayAfterReset.setForeground(Color.gray);
        this.tansferDisplayAfterReset.setBackground(Color.gray);
        this.tansferDisplayAfterReset.setEditable(false);
        
        this.tansferDisplayAfterReset.setBounds(160, 280, 200, 50);
        
        this.performFundPanel.add(tansferDisplayAfterReset);
        //this.add(performFundPanel, "PERFORM FUND TRANSFER");
    
    }
       
    public void showMessageBeforeReset(String message){
    
        amountDisplay.setText("");
    }
    
      public void showMessageAfterReset(String message){
          tansferDisplayAfterReset.setText("");
        
    }
      
       
     public void resetDstvBill(){
        dstvReset = true;
        System.out.println("reset dstv called");
        this.amountDisplay.setText("");
        this.performFundPanel.remove(amountDisplay);
        this.performFundPanel.repaint();
        this.performFundPanel.revalidate();
        
        pass.setText("");
        
        this.tansferDisplayAfterReset = new JTextField("");
        this.tansferDisplayAfterReset.setFont(new Font("Arial Black", Font.BOLD, 15));
        this.tansferDisplayAfterReset.setForeground(Color.gray);
       // this.tansferDisplayAfterReset.setBackground(Color.yellow);
        this.tansferDisplayAfterReset.setEditable(false);
        
        this.tansferDisplayAfterReset.setBounds(160, 280, 200, 50);
        
        this.performFundPanel.add(tansferDisplayAfterReset);
        //this.add(performFundPanel, "PERFORM FUND TRANSFER");
    
    }
    
     private static boolean outsideTransfer = false;
     
     public boolean outsideTransferReset(){
         return outsideTransfer;
     
     }
     
     public void resetAfterOutsideTransfer(){
         outsideBankamountDisplay.setText("");
     }
     
     public void outSideTransferReset(){
     
         outsideBankTransferDisplay.setText("");
     }
     
     
     
     
     public void outsideBankAmountDisplay(){
         outsideTransfer = true;
         this.performfundTransferOutsideBank.remove(outsideBankTransferDisplay);
            this.performfundTransferOutsideBank.repaint();
            this.performfundTransferOutsideBank.revalidate();
            pass.setText("");
            
           // this.transferNoficationField.setText("       Enter Amount for Transfer to" + recipient);
            
            this.outsideBankamountDisplay = new JTextField("");
        this.outsideBankamountDisplay.setFont(new Font("Arial Black", Font.BOLD, 15));
        this.outsideBankamountDisplay.setForeground(Color.blue);
        this.outsideBankamountDisplay.setBackground(Color.gray);
        this.outsideBankamountDisplay.setEditable(false);
        this.outsideBankamountDisplay.setBounds(160, 280, 200, 50);
        this.performfundTransferOutsideBank.add(outsideBankamountDisplay);
     
     }
    //after entering the receiptient account
    
    //create these for each transfer// bill pay
     
     
     
     
     
     public void beforeResetFundTransfer(){
         transferFundDisplay.setText("");
     
     }
     
     
     
     public void afterResetFundTransfer(){
         tansferDisplayAfterReset.setText("");
     
     }
     
     
     
    public void amountDisplay(){
        
        
        if(transferReset){
            this.performFundPanel.remove(tansferDisplayAfterReset);
            this.performFundPanel.repaint();
            this.performFundPanel.revalidate();
        }else
        if(masmReset){
            this.performFundPanel.remove(tansferDisplayAfterReset);
            this.performFundPanel.repaint();
            this.performFundPanel.revalidate();
        }else
        if(dstvReset){
            this.performFundPanel.remove(tansferDisplayAfterReset);
            this.performFundPanel.repaint();
            this.performFundPanel.revalidate();
        }
        else
        if(escomRest){
            this.performFundPanel.remove(tansferDisplayAfterReset);
            this.performFundPanel.repaint();
            this.performFundPanel.revalidate();
        }
        else
        if(waterBoardReset){
            this.performFundPanel.remove(tansferDisplayAfterReset);
            this.performFundPanel.repaint();
            this.performFundPanel.revalidate();
        }else{
            this.performFundPanel.remove(transferFundDisplay);
            this.performFundPanel.repaint();
            this.performFundPanel.revalidate();
        
        }
        
        
        
        
        pass.setText("");
        
        switch(getState()){
            case TRANSFER_AMOUNT_STATE:
                this.transferNoficationField.setText("       Enter Amount for Transfer to");
            break;
            //1
            case PERFOM_DSTV_BILL_PAY:
                this.transferNoficationField.setText("       Enter Amount for DSTV subcription");
            break;
            
            case PERFORM_ESCOM_BILL_PAY:
                this.transferNoficationField.setText("       Enter Amount to pay for ESCOM bill");
            break;
            
            case PERFORM_MASM_BILL_PAY:
                this.transferNoficationField.setText("       Enter Amount for MASM subcription");
            break;
            
            case PERFORM_WATERBOARD_BILL_PAY:
                this.transferNoficationField.setText("       Enter Amount to pay for WATERBOARD bill");
            break;
            
            
                
        }
        
        this.amountDisplay = new JTextField("");
        this.amountDisplay.setFont(new Font("Arial Black", Font.BOLD, 15));
        this.amountDisplay.setForeground(Color.blue);
        this.amountDisplay.setBackground(Color.gray);
        this.amountDisplay.setEditable(false);
        this.amountDisplay.setBounds(160, 280, 200, 50);
        this.performFundPanel.add(amountDisplay);
    
    }
    
    
    public boolean escomReset(){
        return escomRest;
    }
    
    
    public boolean masmReset(){
        return masmReset;
    }
    
    public boolean waterBoardReset(){
        return waterBoardReset;
    }
    
    public boolean dstvReset(){
        return dstvReset;
    }
    
    
    /////
    public JTextField fundTransferDisplay(){
        return transferFundDisplay;
    
    }
    
    public JTextField fundTransferDisplayTwo(){
        System.out.println("account number test: " + tansferDisplayAfterReset);
        return tansferDisplayAfterReset;
    }
    
    public JTextField transferAmount(){
        return amountDisplay ;
    }
    
    public JTextField withdrawalAmount(){
        return amountField;
    }
    
    public JPasswordField getPasswordEntered() {
        return pinDisplay;
    }

    public void atmCardInserted() {
        //fingerprint state, active enter and cancel
        state = CARD_VERIFICATION_STATE;
    }

    public static void setState(int newState) {
        state = newState;
    }

    public static Integer getState() {
        return state;
    }

    public void scannerPressed() {

    }

    public void onePressed() {
        
        System.out.println("current state when number is pressed: " + getState());
        switch(getState()){
            
            case ONE_TIME_ENTRY_STATE:
                pass.append("1");
                oneTimeTokenEntryField.setText(pass.getText());
                
                break;
            
            
            case TOKEN_REQUEST_STATE:
                
                pass.append("1");
                tokenRequestAreaField.setText(pass.getText());
                break;
                
            
            case PIN_VERIFICATION_STATE:
                
                pass.append("1");
                pinDisplay.setText(pass.getText());
                break;
                
            case CASH_WITHDRAWAL_STATE:
                pass.append("1");
                amountField.setText(pass.getText());
                break;
            
            // from perfom transfer
            case PERFOMING_TRANSFER_STATE:
                
                
                if (transferReset){
                   
                    pass.append("1");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("1");
                    transferFundDisplay.setText(pass.getText());
                
                }
                
                break;
                
            case TRANSFER_AMOUNT_STATE:
                
                pass.append("1");
                amountDisplay.setText(pass.getText());
                
                break;
                
                //dstv bill pay state
            case DSTV_BILL_STATE:
                
                if (dstvReset){
                   
                    pass.append("1");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("1");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            
            case PERFOM_DSTV_BILL_PAY:
                pass.append("1"); 
                
                amountDisplay.setText(pass.getText());
                break;
                
                
            //escom bill pay state
             case ESCOM_BILL_STATE:
                if (escomRest){
                   
                    pass.append("1");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("1");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            
            case PERFORM_ESCOM_BILL_PAY:
                pass.append("1");
                amountDisplay.setText(pass.getText());
                break;
                
            
             case MASM_BILL_STATE:
                if (masmReset){
                   
                    pass.append("1");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("1");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            //accout entry state
            case PERFORM_MASM_BILL_PAY:
                pass.append("1");
                amountDisplay.setText(pass.getText());
                break;
                
             case WATERBOARD_BILL_STATAE:
                if (waterBoardReset){
                    System.out.println("one pressed after waterboard reset");
                    pass.append("1");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("1");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            //accout entry state
            case PERFORM_WATERBOARD_BILL_PAY:
                pass.append("1");
                amountDisplay.setText(pass.getText());
                break;
                
            case OUTSIDE_BANK_NAME_STATE:
                pass.setText("");
                outSideSelection.setText("");
                pass.append("STDB");
                outSideSelection.setText(pass.getText());
                break;
           
            case PERFORM_OUTSIDE_BANK_TRANSFER:
                pass.append("1");
                outsideBankamountDisplay.setText(pass.getText());
                break;
                
            case OUTSIDE_BANKT_TRANSFER: 
                 pass.append("1");
                outsideBankTransferDisplay.setText(pass.getText());
                break;
                
            
                
            
                
                
                
        }
       
    }

    public void twoPressed() {

        switch(getState()){
            
            case ONE_TIME_ENTRY_STATE:
                pass.append("2");
                oneTimeTokenEntryField.setText(pass.getText());
                
                break;
            
            case TOKEN_REQUEST_STATE:
                
                pass.append("2");
                tokenRequestAreaField.setText(pass.getText());
                break;
            
            case PIN_VERIFICATION_STATE:
                
                pass.append("2");
                pinDisplay.setText(pass.getText());
                break;
                
            case CASH_WITHDRAWAL_STATE:
                pass.append("2");
                amountField.setText(pass.getText());
                break;
                
            case PERFOMING_TRANSFER_STATE:
                if (transferReset){
                   
                    pass.append("2");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("2");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
            
            
            case TRANSFER_AMOUNT_STATE:
                pass.append("2");
                amountDisplay.setText(pass.getText());
                break;
            
            case DSTV_BILL_STATE:
                
                if (dstvReset){
                   
                    pass.append("2");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("2");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            
            case PERFOM_DSTV_BILL_PAY:
                pass.append("2");
                amountDisplay.setText(pass.getText());
                break;
                
                
            //escom bill pay state
             case ESCOM_BILL_STATE:
                if (escomRest){
                   
                    pass.append("2");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("2");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            
            case PERFORM_ESCOM_BILL_PAY:
                pass.append("2");
                amountDisplay.setText(pass.getText());
                break;
                
            
             case MASM_BILL_STATE:
                if (masmReset){
                   
                    pass.append("2");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("2");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            //accout entry state
            case PERFORM_MASM_BILL_PAY:
                pass.append("2");
                amountDisplay.setText(pass.getText());
                break;
                
             case WATERBOARD_BILL_STATAE:
                if (waterBoardReset){
                   
                    pass.append("2");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("2");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            //accout entry state
            case PERFORM_WATERBOARD_BILL_PAY:
                pass.append("2");
                amountDisplay.setText(pass.getText());
                break;
                
            case OUTSIDE_BANK_NAME_STATE:
                pass.setText("");
                outSideSelection.setText("");
                pass.append("NBS");
                outSideSelection.setText(pass.getText());
                break;
                
            case PERFORM_OUTSIDE_BANK_TRANSFER:
                pass.append("2");
                outsideBankamountDisplay.setText(pass.getText());
                break;
                
            case OUTSIDE_BANKT_TRANSFER: 
                 pass.append("2");
                outsideBankTransferDisplay.setText(pass.getText());
                break;
                
                
        }

    }

    public void threePressed() {

        switch(getState()){
            
            
            case ONE_TIME_ENTRY_STATE:
                pass.append("3");
                oneTimeTokenEntryField.setText(pass.getText());
                
                break;
            
            case TOKEN_REQUEST_STATE:
                
                pass.append("3");
                tokenRequestAreaField.setText(pass.getText());
                break;
            
            case PIN_VERIFICATION_STATE:
                
                pass.append("3");
                pinDisplay.setText(pass.getText());
                break;
                
            case CASH_WITHDRAWAL_STATE:
                pass.append("3");
                amountField.setText(pass.getText());
                break;
                
            case PERFOMING_TRANSFER_STATE:
                if (transferReset){
                   
                    pass.append("3");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("3");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            case TRANSFER_AMOUNT_STATE:
                pass.append("3");
                amountDisplay.setText(pass.getText());
                break;
        
            //from here
           case DSTV_BILL_STATE:
                
                if (dstvReset){
                   
                    pass.append("3");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("3");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            
            case PERFOM_DSTV_BILL_PAY:
                pass.append("3");
                amountDisplay.setText(pass.getText());
                break;
                
                
            //escom bill pay state
             case ESCOM_BILL_STATE:
                 
                if (escomRest){
                   
                    pass.append("3");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("3");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            
            case PERFORM_ESCOM_BILL_PAY:
                pass.append("3");
                amountDisplay.setText(pass.getText());
                break;
                
            
             case MASM_BILL_STATE:
                if (masmReset){
                   
                    pass.append("3");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("3");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            //accout entry state
            case PERFORM_MASM_BILL_PAY:
                pass.append("3");
                amountDisplay.setText(pass.getText());
                break;
                
             case WATERBOARD_BILL_STATAE:
                if (waterBoardReset){
                   
                    pass.append("3");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("3");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            //accout entry state
            case PERFORM_WATERBOARD_BILL_PAY:
                pass.append("3");
                amountDisplay.setText(pass.getText());
                break;
                
            case OUTSIDE_BANK_NAME_STATE:
                pass.setText("");
                outSideSelection.setText("");
                pass.append("FMB");
                outSideSelection.setText(pass.getText());
                break;
                
            case PERFORM_OUTSIDE_BANK_TRANSFER:
                pass.append("3");
                outsideBankamountDisplay.setText(pass.getText());
                break;
                
            case OUTSIDE_BANKT_TRANSFER: 
                 pass.append("3");
                outsideBankTransferDisplay.setText(pass.getText());
                break;
                
                       
                
        }

    }

    public void fourPressed() {

        switch(getState()){
            
            case ONE_TIME_ENTRY_STATE:
                pass.append("4");
                oneTimeTokenEntryField.setText(pass.getText());
                
                break;
            
            case TOKEN_REQUEST_STATE:
                
                pass.append("4");
                tokenRequestAreaField.setText(pass.getText());
                break;
            
            case PIN_VERIFICATION_STATE:
                
                pass.append("4");
                pinDisplay.setText(pass.getText());
                break;
                
            case CASH_WITHDRAWAL_STATE:
                pass.append("4");
                amountField.setText(pass.getText());
                break;
                
            case PERFOMING_TRANSFER_STATE:
                if (transferReset){
                   
                    pass.append("4");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("4");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            case TRANSFER_AMOUNT_STATE:
                pass.append("4");
                amountDisplay.setText(pass.getText());
                break;
                
                
            // from here   
            case DSTV_BILL_STATE:
                
                if (dstvReset){
                   
                    pass.append("4");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("4");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            
            case PERFOM_DSTV_BILL_PAY:
                pass.append("4");
                amountDisplay.setText(pass.getText());
                break;
                
                
            //escom bill pay state
             case ESCOM_BILL_STATE:
                if (escomRest){
                   
                    pass.append("4");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("4");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            
            case PERFORM_ESCOM_BILL_PAY:
                pass.append("4");
                amountDisplay.setText(pass.getText());
                break;
                
            
             case MASM_BILL_STATE:
                if (masmReset){
                   
                    pass.append("4");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("4");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            //accout entry state
            case PERFORM_MASM_BILL_PAY:
                pass.append("4");
                amountDisplay.setText(pass.getText());
                break;
                
             case WATERBOARD_BILL_STATAE:
                if (waterBoardReset){
                   
                    pass.append("4");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("4");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            //accout entry state
            case PERFORM_WATERBOARD_BILL_PAY:
                pass.append("4");
                amountDisplay.setText(pass.getText());
                break;
                
            case OUTSIDE_BANK_NAME_STATE:
                pass.setText("");
                outSideSelection.setText("");
                pass.append("FDH");
                outSideSelection.setText(pass.getText());
                break;
                
            case PERFORM_OUTSIDE_BANK_TRANSFER:
                pass.append("4");
                outsideBankamountDisplay.setText(pass.getText());
                break;
                
            case OUTSIDE_BANKT_TRANSFER: 
                 pass.append("4");
                outsideBankTransferDisplay.setText(pass.getText());
                break;
        }

    }

    public void fivePressed() {

        switch(getState()){
            
            case ONE_TIME_ENTRY_STATE:
                pass.append("5");
                oneTimeTokenEntryField.setText(pass.getText());
                
                break;
            
            case TOKEN_REQUEST_STATE:
                
                pass.append("5");
                tokenRequestAreaField.setText(pass.getText());
                break;
            
            case PIN_VERIFICATION_STATE:
                
                pass.append("5");
                pinDisplay.setText(pass.getText());
                break;
                
            case CASH_WITHDRAWAL_STATE:
                pass.append("5");
                amountField.setText(pass.getText());
                break;
                
            case PERFOMING_TRANSFER_STATE:
                if (transferReset){
                   
                    pass.append("5");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("5");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            case TRANSFER_AMOUNT_STATE:
                pass.append("5");
                amountDisplay.setText(pass.getText());
                break;
            
            //from here
            case DSTV_BILL_STATE:
                
                if (dstvReset){
                   
                    pass.append("5");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("5");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            
            case PERFOM_DSTV_BILL_PAY:
                pass.append("5");
                amountDisplay.setText(pass.getText());
                break;
                
                
            //escom bill pay state
             case ESCOM_BILL_STATE:
                if (escomRest){
                   
                    pass.append("5");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("5");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            
            case PERFORM_ESCOM_BILL_PAY:
                pass.append("5");
                amountDisplay.setText(pass.getText());
                break;
                
            
             case MASM_BILL_STATE:
                if (masmReset){
                   
                    pass.append("5");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("5");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            //accout entry state
            case PERFORM_MASM_BILL_PAY:
                pass.append("5");
                amountDisplay.setText(pass.getText());
                break;
                
             case WATERBOARD_BILL_STATAE:
                if (waterBoardReset){
                   
                    pass.append("5");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("5");
                    transferFundDisplay.setText(pass.getText());
                
                };
                break;
                
            //accout entry state
            case PERFORM_WATERBOARD_BILL_PAY:
                pass.append("5");
                amountDisplay.setText(pass.getText());
                break;
                
            case OUTSIDE_BANK_NAME_STATE:
                pass.setText("");
                outSideSelection.setText("");
                pass.append("ECCOB");
                outSideSelection.setText(pass.getText());
                break;
                
            case PERFORM_OUTSIDE_BANK_TRANSFER:
                pass.append("5");
                outsideBankamountDisplay.setText(pass.getText());
                break;
                
            case OUTSIDE_BANKT_TRANSFER: 
                 pass.append("5");
                outsideBankTransferDisplay.setText(pass.getText());
                break;
        }

    }

    public void sixPressed() {

        switch(getState()){
            
            case ONE_TIME_ENTRY_STATE:
                pass.append("6");
                oneTimeTokenEntryField.setText(pass.getText());
                
                break;
            
            case TOKEN_REQUEST_STATE:
                
                pass.append("6");
                tokenRequestAreaField.setText(pass.getText());
                break;
            
            case PIN_VERIFICATION_STATE:
                
                pass.append("6");
                pinDisplay.setText(pass.getText());
                break;
                
            case CASH_WITHDRAWAL_STATE:
                pass.append("6");
                amountField.setText(pass.getText());
                break;
                
            case PERFOMING_TRANSFER_STATE:
                if (transferReset){
                   
                    pass.append("6");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("6");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            case TRANSFER_AMOUNT_STATE:
                pass.append("6");
                amountDisplay.setText(pass.getText());
                break;
            
            //from here
            case DSTV_BILL_STATE:
                if (dstvReset){
                   
                    pass.append("6");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("6");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            
            case PERFOM_DSTV_BILL_PAY:
                pass.append("6");
                amountDisplay.setText(pass.getText());
                break;
                
                
            //escom bill pay state
             case ESCOM_BILL_STATE:
                if (escomRest){
                   
                    pass.append("6");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("6");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            
            case PERFORM_ESCOM_BILL_PAY:
                pass.append("6");
                amountDisplay.setText(pass.getText());
                break;
                
            
             case MASM_BILL_STATE:
                if (masmReset){
                   
                    pass.append("6");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("6");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            //accout entry state
            case PERFORM_MASM_BILL_PAY:
                pass.append("6");
                amountDisplay.setText(pass.getText());
                break;
                
             case WATERBOARD_BILL_STATAE:
                if (waterBoardReset){
                   
                    pass.append("6");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("6");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            //accout entry state
            case PERFORM_WATERBOARD_BILL_PAY:
                pass.append("6");
                amountDisplay.setText(pass.getText());
                break;
                
            case OUTSIDE_BANK_NAME_STATE:
                pass.setText("");
                outSideSelection.setText("");
                pass.append("OIBM");
                outSideSelection.setText(pass.getText());
                break;
                
            case PERFORM_OUTSIDE_BANK_TRANSFER:
                pass.append("6");
                outsideBankamountDisplay.setText(pass.getText());
                break;
                
            case OUTSIDE_BANKT_TRANSFER: 
                 pass.append("6");
                outsideBankTransferDisplay.setText(pass.getText());
                break;
                
        }

    }
    
    public boolean resetTransfer(){
    
        return transferReset;
    }
    
    public void sevenPressed() {

        switch(getState()){
            
           
            
            case ONE_TIME_ENTRY_STATE:
                pass.append("7");
                oneTimeTokenEntryField.setText(pass.getText());
                
                break;
            
            case TOKEN_REQUEST_STATE:
                
                pass.append("7");
                tokenRequestAreaField.setText(pass.getText());
                break;
            
            case PIN_VERIFICATION_STATE:
                
                pass.append("7");
                pinDisplay.setText(pass.getText());
                break;
                
                
            case CASH_WITHDRAWAL_STATE:
                pass.append("7");
                amountField.setText(pass.getText());
                break;
                
            case PERFOMING_TRANSFER_STATE:
                if (transferReset){
                   
                    pass.append("7");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("7");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            case TRANSFER_AMOUNT_STATE:
                pass.append("7");
                amountDisplay.setText(pass.getText());
                break;
            
            //from here
            case DSTV_BILL_STATE:
               
                if (dstvReset){
                   
                    pass.append("7");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("7");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            
            case PERFOM_DSTV_BILL_PAY:
                pass.append("7");
                amountDisplay.setText(pass.getText());
                break;
                
                
            //escom bill pay state
             case ESCOM_BILL_STATE:
                if (escomRest){
                   
                    pass.append("7");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("7");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            
            case PERFORM_ESCOM_BILL_PAY:
                pass.append("7");
                amountDisplay.setText(pass.getText());
                break;
                
            
             case MASM_BILL_STATE:
                if (masmReset){
                   
                    pass.append("7");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("7");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            //accout entry state
            case PERFORM_MASM_BILL_PAY:
                pass.append("7");
                amountDisplay.setText(pass.getText());
                break;
                
             case WATERBOARD_BILL_STATAE:
                if (waterBoardReset){
                   
                    pass.append("7");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("7");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            //accout entry state
            case PERFORM_WATERBOARD_BILL_PAY:
                pass.append("7");
                amountDisplay.setText(pass.getText());
                break;
                
                
                 
            case OUTSIDE_BANK_NAME_STATE:
                pass.setText("");
                outSideSelection.setText("");
                pass.append("NEDB");
                outSideSelection.setText(pass.getText());
                break;
                
            case PERFORM_OUTSIDE_BANK_TRANSFER:
                pass.append("7");
                outsideBankamountDisplay.setText(pass.getText());
                break;
                
            case OUTSIDE_BANKT_TRANSFER: 
                 pass.append("7");
                outsideBankTransferDisplay.setText(pass.getText());
                break;
                
        }

    }

    public void eightPressed() {

        switch(getState()){
            
            case ONE_TIME_ENTRY_STATE:
                pass.append("8");
                oneTimeTokenEntryField.setText(pass.getText());
                
                break;
            
            case TOKEN_REQUEST_STATE:
                
                pass.append("8");
                tokenRequestAreaField.setText(pass.getText());
                break;
            
            
            case PIN_VERIFICATION_STATE:
                
                pass.append("8");
                pinDisplay.setText(pass.getText());
                break;
                
            case CASH_WITHDRAWAL_STATE:
                pass.append("8");
                amountField.setText(pass.getText());
                break;
                
            case PERFOMING_TRANSFER_STATE:
                if (transferReset){
                   
                    pass.append("8");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("8");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            case TRANSFER_AMOUNT_STATE:
                pass.append("8");
                amountDisplay.setText(pass.getText());
                break;
            
            // from here
            case DSTV_BILL_STATE:
                
                if (dstvReset){
                   
                    pass.append("8");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("8");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            
            case PERFOM_DSTV_BILL_PAY:
                pass.append("8");
                amountDisplay.setText(pass.getText());
                break;
                
                
            //escom bill pay state
             case ESCOM_BILL_STATE:
                if (escomRest){
                   
                    pass.append("8");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("8");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            
            case PERFORM_ESCOM_BILL_PAY:
                pass.append("8");
                amountDisplay.setText(pass.getText());
                break;
                
            
             case MASM_BILL_STATE:
                if (masmReset){
                   
                    pass.append("8");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("8");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            //accout entry state
            case PERFORM_MASM_BILL_PAY:
                pass.append("8");
                amountDisplay.setText(pass.getText());
                break;
                
             case WATERBOARD_BILL_STATAE:
                if (waterBoardReset){
                   
                    pass.append("8");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("8");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            //accout entry state
            case PERFORM_WATERBOARD_BILL_PAY:
                pass.append("8");
                amountDisplay.setText(pass.getText());
                break;
                
            case OUTSIDE_BANK_NAME_STATE:
                
                pass.append("8");
                outSideSelection.setText(pass.getText());
                break;
                
            case PERFORM_OUTSIDE_BANK_TRANSFER:
                pass.append("8");
                outsideBankamountDisplay.setText(pass.getText());
                break;
                
            case OUTSIDE_BANKT_TRANSFER: 
                 pass.append("8");
                outsideBankTransferDisplay.setText(pass.getText());
                break;
                
        }

    }

    public void ninePressed() {

        switch(getState()){
            
            case ONE_TIME_ENTRY_STATE:
                pass.append("9");
                oneTimeTokenEntryField.setText(pass.getText());
                
                break;
            
            case TOKEN_REQUEST_STATE:
                
                pass.append("9");
                tokenRequestAreaField.setText(pass.getText());
                break;
                
            case PIN_VERIFICATION_STATE:
                
                pass.append("9");
                pinDisplay.setText(pass.getText());
                break;
                
            case CASH_WITHDRAWAL_STATE:
                pass.append("9");
                amountField.setText(pass.getText());
                break;
                
            case PERFOMING_TRANSFER_STATE:
                if (transferReset){
                   
                    pass.append("9");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("9");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            case TRANSFER_AMOUNT_STATE:
                pass.append("9");
                amountDisplay.setText(pass.getText());
                break;
                
            //from here
            case DSTV_BILL_STATE:
                
                if (dstvReset){
                   
                    pass.append("9");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("9");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            
            case PERFOM_DSTV_BILL_PAY:
                pass.append("9");
                amountDisplay.setText(pass.getText());
                break;
                
                
            //escom bill pay state
             case ESCOM_BILL_STATE:
                if (escomRest){
                   
                    pass.append("9");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("9");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            
            case PERFORM_ESCOM_BILL_PAY:
                pass.append("9");
                amountDisplay.setText(pass.getText());
                break;
                
            
             case MASM_BILL_STATE:
                if (masmReset){
                   
                    pass.append("9");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("9");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            //accout entry state
            case PERFORM_MASM_BILL_PAY:
                pass.append("9");
                amountDisplay.setText(pass.getText());
                break;
                
             case WATERBOARD_BILL_STATAE:
                if (waterBoardReset){
                   
                    pass.append("9");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("9");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            //accout entry state
            case PERFORM_WATERBOARD_BILL_PAY:
                pass.append("9");
                amountDisplay.setText(pass.getText());
                break;
                
            case OUTSIDE_BANK_NAME_STATE:
                
                pass.append("9");
                outSideSelection.setText(pass.getText());
                break;
                
            case PERFORM_OUTSIDE_BANK_TRANSFER:
                pass.append("9");
                outsideBankamountDisplay.setText(pass.getText());
                break;
                
            case OUTSIDE_BANKT_TRANSFER: 
                 pass.append("9");
                outsideBankTransferDisplay.setText(pass.getText());
                break;
                
        }

    }

    public void zeroPressed() {

        switch(getState()){
            
            case ONE_TIME_ENTRY_STATE:
                pass.append("0");
                oneTimeTokenEntryField.setText(pass.getText());
                
                break;
            
            case TOKEN_REQUEST_STATE:
                
                pass.append("0");
                tokenRequestAreaField.setText(pass.getText());
                break;
            
            case PIN_VERIFICATION_STATE:
                
                pass.append("0");
                pinDisplay.setText(pass.getText());
                break;
                
            case CASH_WITHDRAWAL_STATE:
                pass.append("0");
                amountField.setText(pass.getText());
                break;
                
            case PERFOMING_TRANSFER_STATE:
                if (transferReset){
                   
                    pass.append("0");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("0");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            case TRANSFER_AMOUNT_STATE:
                pass.append("0");
                amountDisplay.setText(pass.getText());
                break;
            
            //from here
           case DSTV_BILL_STATE:
                if (dstvReset){
                   
                    pass.append("0");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("0");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            
            case PERFOM_DSTV_BILL_PAY:
                pass.append("0");
                amountDisplay.setText(pass.getText());
                break;
                
                
            //escom bill pay state
             case ESCOM_BILL_STATE:
                if (escomRest){
                   
                    pass.append("0");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("0");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            
            case PERFORM_ESCOM_BILL_PAY:
                pass.append("0");
                amountDisplay.setText(pass.getText());
                break;
                
            
             case MASM_BILL_STATE:
                if (masmReset){
                   
                    pass.append("0");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("0");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            //accout entry state
            case PERFORM_MASM_BILL_PAY:
                pass.append("0");
                amountDisplay.setText(pass.getText());
                break;
                
             case WATERBOARD_BILL_STATAE:
                if (waterBoardReset){
                   
                    pass.append("0");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append("0");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            //accout entry state
            case PERFORM_WATERBOARD_BILL_PAY:
                pass.append("0");
                amountDisplay.setText(pass.getText());
                break;
                
            case OUTSIDE_BANK_NAME_STATE:
                
                pass.append("0");
                outSideSelection.setText(pass.getText());
                break;
                
            case PERFORM_OUTSIDE_BANK_TRANSFER:
                pass.append("0");
                outsideBankamountDisplay.setText(pass.getText());
                break;
                
            case OUTSIDE_BANKT_TRANSFER: 
                 pass.append("0");
                outsideBankTransferDisplay.setText(pass.getText());
                break;
                
            
                
        }

    }

    public void dotPressed() {

        switch(getState()){
            case PIN_VERIFICATION_STATE:
                
                pass.append(".");
                pinDisplay.setText(pass.getText());
                break;
                
            case CASH_WITHDRAWAL_STATE:
                pass.append(".");
                amountField.setText(pass.getText());
                break;
                
            case PERFOMING_TRANSFER_STATE:
                if (transferReset){
                   
                    pass.append(".");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append(".");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            case TRANSFER_AMOUNT_STATE:
                pass.append(".");
                amountDisplay.setText(pass.getText());
                break;
            
            //from here
            case DSTV_BILL_STATE:
                
                if (transferReset){
                   
                    pass.append(".");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append(".");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            
            case PERFOM_DSTV_BILL_PAY:
                pass.append(".");
                amountDisplay.setText(pass.getText());
                break;
                
                
            //escom bill pay state
             case ESCOM_BILL_STATE:
               if (escomRest){
                   
                    pass.append(".");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append(".");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            
            case PERFORM_ESCOM_BILL_PAY:
                pass.append(".");
                amountDisplay.setText(pass.getText());
                break;
                
            
             case MASM_BILL_STATE:
                if (masmReset){
                   
                    pass.append(".");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append(".");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            //accout entry state
            case PERFORM_MASM_BILL_PAY:
                pass.append(".");
                amountDisplay.setText(pass.getText());
                break;
                
             case WATERBOARD_BILL_STATAE:
                if (waterBoardReset){
                   
                    pass.append(".");
                    tansferDisplayAfterReset.setText(pass.getText());
                }else{
                    pass.append(".");
                    transferFundDisplay.setText(pass.getText());
                
                }
                break;
                
            //accout entry state
            case PERFORM_WATERBOARD_BILL_PAY:
                pass.append(".");
                amountDisplay.setText(pass.getText());
                break;
                
            case PERFORM_OUTSIDE_BANK_TRANSFER:
                pass.append(".");
                outsideBankamountDisplay.setText(pass.getText());
                break;
                
        }

    }

    public void cancelPressed() {
        //cancel and reset everything here// session etc
        pass.setText("");
        pinDisplay.setText("");
        idlePanel();

    }
    
    public void amountNotification(String notification) {
        //cancel and reset everything here// session etc
        pass.setText("");
        nofiationField.setText(notification);
        

    }

    public void clearOnPinPressed() {

        //pass.append("2");
        pass.setText("");
        
        
        pinDisplay.setText("");

    }

    
    
    
    //clear amount to transfer
    public void clearAmountTransfer(){
        pass.setText("");
        amountDisplay.setText("");
        
    
    }
    
    //cash withdrawal
    public void clearWithdrawal() {

        //pass.append("2");
        
        //nofiationField.setText("            Select or Enter Amount");
        pass.setText("");
        amountField.setText("");

    }
    
   
    public JTextField balanceDisplay(double balance){
        accountBalanceDisplay.setText(""+balance);
        return accountBalanceDisplay;
    
    }
            
    

    public void enterPressed() {
        System.out.println("Pin Entered: " + pass.getText());
    }

    

    //cardLayout.show(cards, "Profile Edit Card"); inside an action listener
}
