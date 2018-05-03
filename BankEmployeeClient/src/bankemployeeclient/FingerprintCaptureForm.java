package bankemployeeclient;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.capture.*;
import com.digitalpersona.onetouch.capture.event.*;
import com.digitalpersona.onetouch.processing.*;

public class FingerprintCaptureForm
        extends JPanel {

    private DPFPCapture capturer = DPFPGlobal.getCaptureFactory().createCapture();
    private JLabel picture = new JLabel();
    
    public FingerprintCaptureForm() {
        
        JLabel titleVerificationTest = new JLabel("Verification Test");
        titleVerificationTest.setBounds(400, 50, 600, 40);
        titleVerificationTest.setFont(new Font("Arial", Font.BOLD, 50));
        this.add(titleVerificationTest);
        
        

        //setTitle("Fingerprint Enrollment");
        setLayout(null);
        //setBackground(Color.black);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        picture.setPreferredSize(new Dimension(300, 300));
        picture.setBorder(BorderFactory.createLoweredBevelBorder());
        
        JButton quit = new JButton("Close");
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        
        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(Color.getColor("control"));
        //center.setSize(200, 200);
        center.setBackground(Color.gray);
        //center.add(right, BorderLayout.CENTER);
        center.setBounds(450, 200, 300, 300);
        center.add(picture, BorderLayout.LINE_START);
        //center.add(status, BorderLayout.PAGE_END);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        bottom.setBackground(Color.getColor("control"));
        //bottom.add(quit);

        setLayout(new BorderLayout());
        add(center, BorderLayout.CENTER);
        //add(bottom, BorderLayout.PAGE_END);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                init();
                start();
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                stop();
            }

        });
        //this.setVisible(true);
        //pack();
        //setLocationRelativeTo(null);

    }

    protected void init() {
        capturer.addDataListener(new DPFPDataAdapter() {
            @Override
            public void dataAcquired(final DPFPDataEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        //data captured
                        process(e.getSample());
                    }
                });
            }
        });
        capturer.addReaderStatusListener(new DPFPReaderStatusAdapter() {
            @Override
            public void readerConnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        //scanner cpnnected
                    }
                });
            }

            @Override
            public void readerDisconnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        //fingerprint disconnected
                    }
                });
            }
        });
        capturer.addSensorListener(new DPFPSensorAdapter() {
            @Override
            public void fingerTouched(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        //
                    }
                });
            }

            @Override
            public void fingerGone(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        //fingerprint removed
                    }
                });
            }
        });
        capturer.addImageQualityListener(new DPFPImageQualityAdapter() {
            @Override
            public void onImageQuality(final DPFPImageQualityEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        if (e.getFeedback().equals(DPFPCaptureFeedback.CAPTURE_FEEDBACK_GOOD)) {
                            //go quality
                        } else {
                            //bad quality
                        }
                    }
                });
            }
        });
    }

    protected void process(DPFPSample sample) {
        // Draw fingerprint sample image.
        drawPicture(convertSampleToBitmap(sample));
    }

    protected void start() {
        capturer.startCapture();
        //permits user to start using the deveice
    }

    protected void stop() {
        capturer.stopCapture();
    }

    public void drawPicture(Image image) {
        picture.setIcon(new ImageIcon(
                image.getScaledInstance(picture.getWidth(), picture.getHeight(), Image.SCALE_DEFAULT)));
    }

    protected Image convertSampleToBitmap(DPFPSample sample) {
        return DPFPGlobal.getSampleConversionFactory().createImage(sample);
    }

    protected DPFPFeatureSet extractFeatures(DPFPSample sample, DPFPDataPurpose purpose) {
        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
        try {
            return extractor.createFeatureSet(sample, purpose);
        } catch (DPFPImageQualityException e) {
            return null;
        }
    }

}
