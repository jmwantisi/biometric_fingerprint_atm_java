/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankemployeeclient;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Bupe
 */
public class Display extends JFrame {
    
    private JTabbedPane tabs;
    private JPanel manageEmployees, addNewCustomer, fingerprintVerification, verificationCard, depositToAccount, viewSysteLogs;
    private NetworkToServer network ;
    private JTable customerList, logList;
    private DefaultTableModel customerListTableModel, logListModel;

    public Display() {
        
        network  = new NetworkToServer();
        
        this.setSize(1200, 700);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.tabs = new JTabbedPane();
        
        this.add(tabs, BorderLayout.CENTER);
        this.tabs.add(manageEmployees(), "Mananage Enrolled Customers");
        this.tabs.setToolTipText("Mananage Enrolled Customers");
        
        this.tabs.add(addCustomer(), "Enrol New Customer");
        this.tabs.setToolTipText("Enrol New Customer");
        
        this.tabs.add(verifyFingerPrint(), "Fingerprint Verification Test");
        this.tabs.setToolTipText("Fingerprint Verification Test");
        
        this.tabs.add(depost(), "Deposit");
        this.tabs.setToolTipText("Deposit");
        
        
        this.tabs.add(systemLogs(), "Transaction Logs");
        this.tabs.setToolTipText("Transaction Logs");
        
        
        
        this.setVisible(true);
        
        
        //this should be started on sign in dialogue
       
       
        network.startConnection();
       // populateTables();
       
       //requestCustomerDetails();
       ///network.requestAccountDetails(70, Display.this);
                
                
        
        
        
    }
    
    public JScrollPane customerListTable(){
        
        
         JTable customerList;
         DefaultTableModel customerListTableModel;
        customerListTableModel = new DefaultTableModel();
        
       
        
        customerListTableModel.addColumn(" Account Number ");
        customerListTableModel.addColumn(" First Name ");
        customerListTableModel.addColumn(" Last Name ");
        customerListTableModel.addColumn(" Phone Number ");
        customerListTableModel.addColumn(" Balance ");
        customerListTableModel.addColumn(" Email ");
        customerListTableModel.addColumn(" Address ");
        
        customerList = new JTable(customerListTableModel);
        
        JScrollPane scrollPane = new JScrollPane(customerList);
        
        return scrollPane;
    
    
    }
    
  //  JTable customerList;
    

//     DefaultTableModel customerListTableModel;
    
    public JPanel depost(){
        
        depositToAccount = new JPanel();
        depositToAccount.setLayout(null);
        
        JLabel label = new JLabel ("NBM Customer Management");
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setBounds(10, 10, 500, 30);
        depositToAccount.add(label);
    
        
        
        JButton deposit = new JButton("Deposit");
        deposit.setBounds(630, 320, 150, 30);
        
        JButton clear = new JButton("Clear Fields");
        clear.setBounds(450, 320, 150, 30);
        
        JLabel firstNameLabel = new JLabel("Amount MWK");
        firstNameLabel.setFont(new Font("Arial", Font.BOLD, 18));  //1
        firstNameLabel.setBounds(280, 140, 150, 100);
        depositToAccount.add(firstNameLabel);
        
        JTextField firstNameTextField = new JTextField("");
        firstNameTextField.setBounds(430, 175, 370, 30);
        firstNameTextField.setFont(new Font("Arial", Font.BOLD, 20)); 
        depositToAccount.add(firstNameTextField);
        
        JLabel lastNameLabel = new JLabel("Account Number");
        lastNameLabel.setFont(new Font("Arial", Font.BOLD, 18)); // 2
        lastNameLabel.setBounds(250, 190, 150, 100);
        depositToAccount.add(lastNameLabel);
        
        JTextField lastNameTextField = new JTextField("");
        lastNameTextField.setBounds(430, 225, 370, 30);
        lastNameTextField.setFont(new Font("Arial", Font.BOLD, 20));
        depositToAccount.add(lastNameTextField);
        
        
         JLabel dob = new JLabel("Deposited By");
        dob.setFont(new Font("Arial", Font.BOLD, 18)); //3
        dob.setBounds(280, 240, 150, 100);
        depositToAccount.add(dob);
        
        JTextField dobField = new JTextField("");
        dobField.setBounds(430, 275, 370, 30);
        dobField.setFont(new Font("Arial", Font.BOLD, 20));
        depositToAccount.add(dobField);
        
        depositToAccount.add(deposit);
        depositToAccount.add(clear);
        
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                firstNameTextField.setText("");
                lastNameTextField.setText("");
                dobField.setText("");
             
                
                //requestCustomerDetails();
                
                
            }
        });
        
         deposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                    if(!lastNameTextField.getText().isEmpty() && !dob.getText().isEmpty() && !firstNameTextField.getText().isEmpty()){
                        network.requestDeposit(200, Display.this, Integer.parseInt(lastNameTextField.getText().trim()), dob.getText(), Double.parseDouble(firstNameTextField.getText() )); //request 200 for logs
                   
                    
                    }else{
                        JOptionPane.showMessageDialog(null, "Please Enter All Required Fields");
                    
                    }
                
                   
                
                
            }
        });
        
        
        return depositToAccount;
    }
    
    
    private static int accoutLoged;
    public JPanel systemLogs(){
        
        JButton load = new JButton("Load Transaction Logs");
        load.setBounds(530, 10, 200, 30);
        
        JButton edit = new JButton("View Log Details");
        edit.setBounds(540, 580, 150, 30);
        
       // JButton delete = new JButton("Delete Record");
       // delete.setBounds(600, 580, 150, 30);
        
        
        
        logListModel = new DefaultTableModel();
        
       
        
        logListModel.addColumn(" Transation ");
        logListModel.addColumn(" Transaction Name ");
        logListModel.addColumn(" Date ");
        logListModel.addColumn(" Time ");
        
        
        logList = new JTable(logListModel);
        
        JScrollPane scrollPane = new JScrollPane(logList);
        
        viewSysteLogs = new JPanel();
        
        
    
        
        viewSysteLogs.setSize(1200, 900);
        viewSysteLogs.setLayout(null);
        scrollPane.setBounds(40, 50, 1100, 500);
        
        //viewSysteLogs.add(edit);
        //viewSysteLogs.add(delete);
        viewSysteLogs.add(load);
        
        viewSysteLogs.add(scrollPane);
        
        JLabel label = new JLabel ("NBM Customer Management");
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setBounds(10, 10, 500, 30);
        viewSysteLogs.add(label);
        
          load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //customerListTableModel =null;
                
               logListModel.setRowCount(0);
                logListModel.fireTableDataChanged();
                
                   network.requestTransActionLogs(3000, Display.this); //request 200 for logs
                   logListModel.fireTableDataChanged();
                
                
                
            }
        });
          
          this.logList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {

                accoutLoged = Integer.parseInt(logList.getValueAt(logList.getSelectedRow(), 0).toString());

                System.out.println( accoutLoged);
                //validate();
            }
        });
          
          
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                network.requestLogView(accoutLoged, 300);
                //send request to server to edit an account
                
                
                
                
            }
        });
        
        
        
        
     
        
        //load tables
        
       // manageEmployees.setBackground(Color.red);
        
        return viewSysteLogs;
    
    }
    
    private Iterator<Object> tr, nm, dt, tm;
    //response back
    public void populateTables( ArrayList<Object> account, ArrayList<Object> name, ArrayList<Object> date, ArrayList<Object> time){
        
        for (tr = account.iterator(); tr.hasNext();) {
            for (nm = name.iterator(); nm.hasNext();) {
            for (dt = date.iterator(); dt.hasNext();) {

                for (tm = time.iterator(); tm.hasNext();) {

                   
                                    Object acc = tr.next();
                                    Object nmm = nm.next();
                                    Object d = dt.next();
                                    Object t = tm.next();
                                    
                                    
                                    
                                    this.logListModel.addRow(new Object[]{acc,nmm, d, t});

                }

            }}

        }
        
        
        logList.revalidate();
        logList.repaint();
    
    
    
    }
    
    static int accountNumberSelected;
    public JPanel manageEmployees(){
        
        JButton load = new JButton("Load Records");
        load.setBounds(500, 10, 150, 30);
        
        JButton edit = new JButton("Edit Record");
        edit.setBounds(550, 580, 150, 30);
        
        JButton delete = new JButton("Delete Record");
        delete.setBounds(600, 580, 150, 30);
        
        
        
        customerListTableModel = new DefaultTableModel();
        
       
        
        customerListTableModel.addColumn(" Account Number ");
        customerListTableModel.addColumn(" First Name ");
        customerListTableModel.addColumn(" Last Name ");
        customerListTableModel.addColumn(" Phone Number ");
        customerListTableModel.addColumn(" Balance ");
        customerListTableModel.addColumn(" Email ");
        customerListTableModel.addColumn(" Address ");
        
        customerList = new JTable(customerListTableModel);
        
        JScrollPane scrollPane = new JScrollPane(customerList);
        
        manageEmployees = new JPanel();
        
        manageEmployees.setSize(1200, 900);
        manageEmployees.setLayout(null);
        scrollPane.setBounds(140, 50, 900, 500);
        
        manageEmployees.add(edit);
        //manageEmployees.add(delete);
        manageEmployees.add(load);
        
        JLabel label = new JLabel ("NBM Customer Management");
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setBounds(10, 10, 500, 30);
        manageEmployees.add(label);
        
        manageEmployees.add(scrollPane);
        
          load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //customerListTableModel =null;
                
               customerListTableModel.setRowCount(0);
                customerListTableModel.fireTableDataChanged();
                   network.requestAccountDetails(70, Display.this);
                   customerListTableModel.fireTableDataChanged();
                
                
                
            }
        });
          
          this.customerList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {

                accountNumberSelected = Integer.parseInt(customerList.getValueAt(customerList.getSelectedRow(), 0).toString());

                System.out.println( accountNumberSelected);
                //validate();
            }
        });
          
          
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                network.requestEditRecord(accountNumberSelected, 72);
                //send request to server to edit an account
                
                
                
                
            }
        });
        
        
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                
                
                
            }
        });
        
     
        
        //load tables
        
       // manageEmployees.setBackground(Color.red);
        
        return manageEmployees;
    
    }
    
    //request
    public void requestCustomerDetails(){
    
        network.requestAccountDetails(70, this);
    }
    
    private Iterator<Object> a, b, c, d, e, f, g;
    
    //response back
    public void populateTables( ArrayList<Object> account, ArrayList<Object> firstName, ArrayList<Object> lastName, ArrayList<Object> phone, ArrayList<Object> balance, ArrayList<Object> email, ArrayList<Object> Address){
        
        for (a = account.iterator(); a.hasNext();) {

            for (b = firstName.iterator(); b.hasNext();) {

                for (c = lastName.iterator(); c.hasNext();) {

                    for (d = phone.iterator(); d.hasNext();) {

                        for (e = balance.iterator(); e.hasNext();) {

                            for (f = email.iterator(); f.hasNext();) {

                                for (g = Address.iterator(); g.hasNext();) {

                                    Object acc = a.next();
                                    Object fName = b.next();
                                    Object lName = c.next();
                                    Object pNo = d.next();
                                    Object accB = e.next();
                                    Object eAddress = f.next();
                                    Object pAddree = g.next();
                                    
                                    
                                    this.customerListTableModel.addRow(new Object[]{acc, fName, lName, pNo, accB, eAddress, pAddree});

                                }

                            }

                        }

                    }

                }

            }

        }
        
        
        customerList.revalidate();
        customerList.repaint();
    
    
    
    }
    
   
    
    private static ArrayList<byte[]> fingerprints = new ArrayList<>();
    
    
    //display user account details here
    
    public void verificationCard(ArrayList<byte[]> blobs, String customer){
        fingerprints = blobs;
        FingerpintPanel test = new FingerpintPanel(fingerprints, customer);
        JButton back = new JButton("Back");
        back.setBounds(20, 10, 150, 30);
        test.add(back);

        //verificationPage.add(back);
        
         
                
               if(blobs.isEmpty()){
                   JOptionPane.showMessageDialog(null, "Account Does Not Exist");
               
               }else{
                    fingerprintVerification.add(test, "v_page");
                    verificationCards.show(fingerprintVerification, "v_page");
               
               }
        
        
        
        
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //requestCustomerDetails();
                
               
               accountField.setText("");
               verificationCards.show(fingerprintVerification, "v_home");
                
                
                
            }
        });

    
    }
    TextField accountField = new TextField();
    JPanel verificationPage = new JPanel();
    CardLayout verificationCards = new CardLayout();
    public JPanel verifyFingerPrint(){
        
        JPanel verificationHome = new JPanel();
       // verificationHome.setBackground(Color.red);
        verificationHome.setLayout(null);
        
        JButton startVerification = new JButton("Start Verification");
        //startVerification.setFont(new Font("Arial", Font.BOLD, 25));
        startVerification.setBounds(530, 350, 150, 30);
        verificationHome.add(startVerification);
        
        
        JLabel pleaseEnterAccountNumber = new JLabel("Please Enter Account Number For Fingerprint Verification");
        pleaseEnterAccountNumber.setFont(new Font("Arial", Font.BOLD, 30));  //1
        pleaseEnterAccountNumber.setBounds(200, 150, 1000, 100);
        verificationHome.add(pleaseEnterAccountNumber);
        
        
        
        accountField.setBounds(450, 270, 300, 50);
        accountField.setFont(new Font("Arial", Font.BOLD, 20));
        verificationHome.add(accountField);
        
        
        
        
        
        
        
        //verificationPage.setBackground(Color.yellow);
        verificationPage.setLayout(null);
        
        JLabel label = new JLabel ("NBM Customer Management");
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setBounds(10, 10, 500, 30);
        verificationPage.add(label);
        
        
        JButton back = new JButton("Back");
        back.setBounds(20, 10, 150, 30);
        
        
        verificationPage.add(back);
        
        fingerprintVerification = new JPanel(verificationCards);
        verificationCard = new JPanel();
        verificationCard.setLayout(null);
        verificationCard.add(label);
        
        fingerprintVerification.setSize(1200, 900);
        fingerprintVerification.add(verificationHome, "v_home");
        
        //verificationCards.show(verificationCard, "v_page");
        
        //request fingerprints here
        
           // network.requestFingerprints(60);
            
            //get response here
            
        
        
          startVerification.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                ;
                //requestCustomerDetails();
                //account number
                
                if(!accountField.getText().isEmpty()){
                     network.requestFingerprints(60, Display.this, Integer.parseInt(accountField.getText().trim()));
                
                }else{
                
                    JOptionPane.showMessageDialog(null, "Please Enter Valid Account Number");
                }
                
               
                
                
            }
        });
        
       back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                 //request type, first name, last name, dob, email, pone, address, first deposit
               
                // requestCustomerDetails();
                verificationCards.show(fingerprintVerification, "v_home");
                
                
            }
        });   
          
          
        //fingerprintVerification.setBackground(Color.yellow);
        
        return fingerprintVerification;
    
    }
    
    
    public JPanel addCustomer(){
        
        addNewCustomer = new JPanel();
        addNewCustomer.setSize(1200, 900);
        //addNewCustomer.setBackground(Color.blue);
        addNewCustomer.setLayout(null);
        
         JLabel label = new JLabel ("NBM Customer Management");
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setBounds(10, 10, 500, 30);
        addNewCustomer.add(label);
        
        
        JLabel firstNameLabel = new JLabel("First Name");
        firstNameLabel.setFont(new Font("Arial", Font.BOLD, 18));  //1
        firstNameLabel.setBounds(300, 40, 100, 100);
        addNewCustomer.add(firstNameLabel);
        
        JTextField firstNameTextField = new JTextField("");
        firstNameTextField.setBounds(430, 75, 370, 30);
        firstNameTextField.setFont(new Font("Arial", Font.BOLD, 20)); 
        addNewCustomer.add(firstNameTextField);
        
        
        JLabel lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setFont(new Font("Arial", Font.BOLD, 18)); // 2
        lastNameLabel.setBounds(300, 90, 100, 100);
        addNewCustomer.add(lastNameLabel);
        
        JTextField lastNameTextField = new JTextField("");
        lastNameTextField.setBounds(430, 125, 370, 30);
        lastNameTextField.setFont(new Font("Arial", Font.BOLD, 20));
        addNewCustomer.add(lastNameTextField);
        
        
        JLabel dob = new JLabel("Date Of Birth");
        dob.setFont(new Font("Arial", Font.BOLD, 18)); //3
        dob.setBounds(280, 140, 150, 100);
        addNewCustomer.add(dob);
        
        JTextField dobField = new JTextField("");
        dobField.setBounds(430, 175, 370, 30);
        dobField.setFont(new Font("Arial", Font.BOLD, 20));
        addNewCustomer.add(dobField);
        
        JLabel phone = new JLabel("Phone Number");
        phone.setFont(new Font("Arial", Font.BOLD, 18)); //4
        phone.setBounds(265, 190, 150, 100);
        addNewCustomer.add(phone);
        
        JTextField phoneNumberField = new JTextField("");
        phoneNumberField.setBounds(430, 225, 370, 30);
        phoneNumberField.setFont(new Font("Arial", Font.BOLD, 20));
        addNewCustomer.add(phoneNumberField);
        
        JLabel email = new JLabel("Email Address");
        email.setFont(new Font("Arial", Font.BOLD, 18)); //5
        email.setBounds(270, 240, 150, 100);
        addNewCustomer.add(email);
        
        JTextField emailField = new JTextField("");
        emailField.setBounds(430, 275, 370, 30);
        emailField.setFont(new Font("Arial", Font.BOLD, 20));
        addNewCustomer.add(emailField);
        
        
        JLabel address = new JLabel("Physical Address");
        address.setFont(new Font("Arial", Font.BOLD, 18)); //6
        address.setBounds(250, 290, 200, 100);
        addNewCustomer.add(address);
        
        JTextField addressField = new JTextField("");
        addressField.setBounds(430, 325, 370, 30);
        addressField.setFont(new Font("Arial", Font.BOLD, 20));
        addNewCustomer.add(addressField);
        
        JLabel firstDeposit = new JLabel("First Deposit(MWK)");
        firstDeposit.setFont(new Font("Arial", Font.BOLD, 18)); // 7
        firstDeposit.setBounds(235, 340, 200, 100);
        addNewCustomer.add(firstDeposit);
        
        JTextField firstDepostField = new JTextField("");
        firstDepostField.setBounds(430, 375, 370, 30);
        firstDepostField.setFont(new Font("Arial", Font.BOLD, 20));
        addNewCustomer.add(firstDepostField);
        
        JButton continueBtn = new JButton("Continue");
        continueBtn.setBounds(700, 500, 150, 30);
        //continueBtn.setFont(new Font("Arial", Font.BOLD, 20));
        addNewCustomer.add(continueBtn);
        
        JButton logout = new JButton("Logout");
        logout.setBounds(1020, 20, 150, 30);
        //logout.setFont(new Font("Arial", Font.BOLD, 20));
        addNewCustomer.add(logout);
        
        
        JButton clear = new JButton("Clear Fields");
        clear.setBounds(400, 500, 150, 30);
       // clear.setFont(new Font("Arial", Font.BOLD, 20));
        addNewCustomer.add(clear);
        
        
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                firstNameTextField.setText("");
                lastNameTextField.setText("");
                dobField.setText("");
                phoneNumberField.setText("");
                emailField.setText("");
                addressField.setText("");
                firstDepostField.setText("");
                
                //requestCustomerDetails();
                
                
            }
        });
        
         continueBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                 //request type, first name, last name, dob, email, pone, address, first deposit
               
                 //requestCustomerDetails();
                //confirmation message here
                
                
                
                
                
                
                
                
                
                if(!firstDepostField.getText().isEmpty()&& !addressField.getText().isEmpty() && !emailField.getText().isEmpty() && !phoneNumberField.getText().isEmpty()&&!firstNameTextField.getText().isEmpty() && !lastNameTextField.getText().isEmpty() && !dobField.getText().isEmpty()){
                    network.enrolNewCustomerDetails(51, firstNameTextField.getText().trim(), lastNameTextField.getText().trim(), ""+dobField.getText().trim(), ""+ emailField.getText().trim(), ""+phoneNumberField.getText().trim(), ""+addressField.getText().trim(), Double.parseDouble(firstDepostField.getText().trim()));
                System.out.println("send to server");
                
                
                }else{
                    JOptionPane.showMessageDialog(null, "Please Fill All Required Fields");
                }
                
                
                
                ///after confirming clear
                
                
            }
        });
        
        
        //emal, address
        
        return addNewCustomer;
    
    }
    
    
    
    
}
