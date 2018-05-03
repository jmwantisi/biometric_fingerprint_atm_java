package bankemployeeclient;



import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.ui.swing.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;

/**
 * Enrollment control test
 */
public class EnrollmentDialog
        extends JDialog {

    private static EnumMap<DPFPFingerIndex, DPFPTemplate> templates;
    private static int accountNumber;
    private NetworkToServer network;
    

    public EnrollmentDialog(EnumMap<DPFPFingerIndex, DPFPTemplate> templates, int accountNumber, NetworkToServer networkToServer) {
        this.templates = templates;
        this.templates.clear();
        this.accountNumber = accountNumber;
        this.network = networkToServer;

        setTitle("Fingerprint Enrollment");

        DPFPEnrollmentControl enrollmentControl = new DPFPEnrollmentControl();

        EnumSet<DPFPFingerIndex> fingers = EnumSet.noneOf(DPFPFingerIndex.class);
        fingers.addAll(templates.keySet());
        enrollmentControl.setEnrolledFingers(fingers);
       
        enrollmentControl.addEnrollmentListener(new DPFPEnrollmentListener() {

            public void fingerDeleted(DPFPEnrollmentEvent e) throws DPFPEnrollmentVetoException {
                System.out.println("removed");
                //add remove query here
                EnrollmentDialog.this.templates.remove(e.getFingerIndex());

                
            }

            public void fingerEnrolled(DPFPEnrollmentEvent e) throws DPFPEnrollmentVetoException {
                System.out.println("added to database");
                EnrollmentDialog.this.templates.put(e.getFingerIndex(), e.getTemplate());

               
                network.sendFingerprintEnrolRquest(52, accountNumber, e.getTemplate().serialize(), e.getFingerIndex().toString());

            }
        });

        getContentPane().setLayout(new BorderLayout());

        JButton closeButton = new JButton("Finish");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                if(templates.size() == 2){
                    setVisible(false);
                    network.generateAccount(accountNumber);
                     System.out.println("acc generated at enro: " + accountNumber);
                }else{
                
                    JOptionPane.showMessageDialog(null, "Please Enrol at least 2 Fingerprints");
                
                }
                
               
                
                
            }
        });

        JPanel bottom = new JPanel();
        bottom.add(closeButton);
        add(enrollmentControl, BorderLayout.CENTER);
        add(bottom, BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }
}
