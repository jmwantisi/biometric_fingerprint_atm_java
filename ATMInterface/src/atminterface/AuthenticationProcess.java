/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atminterface;

import com.digitalpersona.onetouch.DPFPFingerIndex;
import com.digitalpersona.onetouch.DPFPTemplate;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.EnumMap;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

/**
 *
 * @author jmwantisi
 */
public class AuthenticationProcess {

    private static boolean customerVerified = false;
    private static boolean cardVerified = false;
    private static boolean pinVerified = false;
    private static boolean fingerprintVerified = false;
    
    private static ArrayList<Object> currentCustomerSession;
    private static int customerCardNumber;
    private static int customerPin;
    //fingerprints will be loaded once the ATM card has been verified
    private static EnumMap<DPFPFingerIndex, DPFPTemplate> templates = new EnumMap<DPFPFingerIndex, DPFPTemplate>(DPFPFingerIndex.class);
    private JTextField farAchieved;
    JCheckBox fingerMatched;
    

    public AuthenticationProcess() {
        this.currentCustomerSession = new ArrayList<>();
        //this.currentCustomerSession = null;
        
        //this.network.startConnection();

    }

    public Integer customerPIN(int pin) {
        return customerPin = pin;
    }

    public Integer customerCardNumber(int cardNo) {
        return customerCardNumber = cardNo;
    }

    public Integer getCardNumber() {
        return customerCardNumber;
    }

    public byte[] customerFingerprint(byte[] fingerprint) {
        return fingerprint;
    }
    
    
    private static boolean accountVerified = false;
    
    public Boolean accountVerified(){
    
        return accountVerified;
    }
    
    public boolean accountVerificationForTokenRequest(boolean verified) {
        accountVerified = verified;
        accountVerified = verified;
        
        
        return accountVerified;
    }
    
    public boolean verificationResponseFromServer(boolean verified) {
        customerVerified = verified;
        cardVerified = verified;
        System.out.println("Card verified: " + customerVerified);
        
        return customerVerified;
    }
    
    public boolean pinVerificationResponceFromServer(boolean verified) {
        customerVerified = verified;
        pinVerified = verified;
        System.out.println("Pin verified: " + pinVerified);
        
        return pinVerified;
    }
    
    public boolean pinVerified(){
        return pinVerified;
    }
    
    public boolean fingerPrintVerificationResponseFromServer(boolean verified){
        fingerprintVerified = verified;
        System.out.println("Fingerprint Verification Response: " + fingerprintVerified);
        //network.sendFingerprintValidation(fingerprintVerified);
        
        
        
        return fingerprintVerified;
    
    
    }

    public boolean verified() {
        initilizingSessionFields(getCardNumber());
        return customerVerified;
    }
    
    public boolean cardVerified(){
        return cardVerified;
    }
    
    public boolean fVeriried(){
        return fingerprintVerified;
    
    }
    
    
    public void endSession(){
        accountVerified = false;
        fingerprintVerified = false;
        customerVerified = false;
        cardVerified = false;
        fingerprintVerified = false;
        currentCustomerSession.removeAll(currentCustomerSession);
    }
    
    public void initilizingSessionFields(Object sessionField) {
        //if customer verified. do this

        //first first with index 0 should the account number
        if (currentCustomerSession.isEmpty() && customerVerified == true) {
            System.out.println("filed added: " + sessionField);
            currentCustomerSession.add(0, sessionField);
            System.out.println("fingerptint request");
            //send request to load fingerprint database

            //new FingerprintCaptureForm();
            //showFingerprintVerficationDialogue();
        } else if (currentCustomerSession.get(1) == null) {
            //add second element account number
            currentCustomerSession.add(1, sessionField);

        }
    }

    public ArrayList<Object> sessionStart() {
        return currentCustomerSession;
    }

}
