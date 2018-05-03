package bankemployeeclient;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.ui.swing.DPFPVerificationControl;
import com.digitalpersona.onetouch.verification.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Timer;
import javax.swing.JTextField;

//dispose the entire frame.. create a new one, and ask for PIN if conditions are true
public class FingerpintPanel extends FingerprintCaptureForm {

    private int farAchieved;
    private DPFPVerificationControl verificationControl;
    private static boolean matched = false;
    private static boolean verified = false;
    private static ArrayList<byte[]> binaryTemplatesData;
    private static ArrayList<DPFPTemplate> blobTemplates;
    private static FingerpirintCheck fingerprintCheck;
    JTextField messageTex = new JTextField("  Fingerprint Scanner Ready");
    TextArea showAccountDetails = new TextArea("  Place Fingerprint on Scanner");
    TextField details = new TextField("  Place Fingerprint on Scanner");
    private static String customer;
            
   

    FingerpintPanel(ArrayList<byte[]> data, String customer) {
        this.customer = customer;
        setLayout(null);
        this.setBounds(200, 100, 400, 100);

        this.binaryTemplatesData = new ArrayList();
        

        //binary fingerprint data from database
        this.binaryTemplatesData = data;
        this.blobTemplates = new ArrayList();
        this.fingerprintCheck = new FingerpirintCheck();
        this.messageTex.setBounds(470, 530, 260, 40);
        messageTex.setEditable(false);
        messageTex.setBackground(Color.white);
        messageTex.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(messageTex);
        
        this.details.setBounds(470, 110, 260, 40);
        details.setBackground(Color.white);
        details.setFont(new Font("Arial", Font.BOLD, 15));
        details.setEditable(false);
        
        this.add(details);
        
        
        

    }

    @Override
    protected void init() {
        super.init();

        
    }

    //an array of objected bytes
    public ArrayList<DPFPTemplate> getTemplates() {
        //an array of object bytes sent from the server
        for (byte[] data : binaryTemplatesData) {

            //create a template from a binary data recieved from bank server
            DPFPTemplate t = DPFPGlobal.getTemplateFactory().createTemplate();

            t.deserialize(data);

            blobTemplates.add(t);

        }

        return blobTemplates;
    }

    @Override
    protected void process(DPFPSample sample) {
        super.process(sample);
        messageTex.setText("    Invalid Account Number");
        details.setText("     Account Does Not Exist");
      
        DPFPFeatureSet features = extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

        final DPFPVerification verificator = DPFPGlobal.getVerificationFactory().createVerification();
        // e.setStopCapture(false);	// we want to continue capture until the dialog is closed
        int bestFAR = DPFPVerification.PROBABILITY_ONE;
        boolean hasMatch = false;

        for (DPFPTemplate template : FingerpintPanel.this.getTemplates()) {

            DPFPVerificationResult result = verificator.verify(features, template);

            bestFAR = Math.min(bestFAR, result.getFalseAcceptRate());

            if (result.isVerified()) {

                matched = true;
                messageTex.setForeground(Color.green);
                messageTex.setText("         Fingerprint Verified");
                fingerprintCheck.getMatch(matched);
                //get user account and pass it on the next panel
                
                details.setText(" "+customer + " Verified");

                break;

            } else if (!result.isVerified()) {
                
                details.setText(" "+customer + " Not Verified");
                messageTex.setForeground(Color.red);
                messageTex.setText("     Fingerprint Not Verified");
                matched = false;
                fingerprintCheck.getMatch(matched);
               // details.setText("");

            }

        }

    }

    public boolean getFingerprintVerification() {
        return matched;
    }

   

}
