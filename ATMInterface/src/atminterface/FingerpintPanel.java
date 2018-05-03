package atminterface;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.ui.swing.DPFPVerificationControl;
import com.digitalpersona.onetouch.verification.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Timer;
import javax.swing.JOptionPane;

//dispose the entire frame.. create a new one, and ask for PIN if conditions are true
public class FingerpintPanel extends FingerprintCaptureForm {

    private int farAchieved;
    private DPFPVerificationControl verificationControl;
    private static boolean matched = false;
    private static boolean verified = false;
    private static ArrayList<byte[]> binaryTemplatesData;
    private static ArrayList<DPFPTemplate> blobTemplates;
    private AuthenticationProcess authenticationProcess;
    private DisplayPanel display;
     private final int PIN_VERIFICATION_STATE = 4;

    FingerpintPanel(ArrayList<byte[]> data, DisplayPanel panel) {
        setLayout(null);
        this.setBounds(200, 100, 100, 100);

        this.binaryTemplatesData = new ArrayList();
        this.display = panel;

        //binary fingerprint data from database
        this.binaryTemplatesData = data;
        this.blobTemplates = new ArrayList();
        this.authenticationProcess = new AuthenticationProcess();

    }

    @Override
    protected void init() {
        super.init();

        updateStatus(0);
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
              
                authenticationProcess.fingerPrintVerificationResponseFromServer(matched);
                
                display.pinPanel();
                display.setState(PIN_VERIFICATION_STATE);
                //get user account and pass it on the next panel
                JOptionPane.showMessageDialog(null, "Fingerprint Verified");
                break;

            } else if (!result.isVerified()) {

                matched = false;
                authenticationProcess.fingerPrintVerificationResponseFromServer(matched);
                

            }

        }
        
        if(matched == false){
            JOptionPane.showMessageDialog(null, "Fingerprint Not Verified for this Account");
        }
        

    }

    public boolean getFingerprintVerification() {
        return matched;
    }

    private void updateStatus(int FAR) {
        // Show "False accept rate" value
        
    }

}
