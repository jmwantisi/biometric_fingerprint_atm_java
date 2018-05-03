/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankserver.services;

import hu.ozeki.OzSMSMessage;
import hu.ozeki.OzSmsClient;
import java.io.IOException;

/**
 *
 * @author Bupe
 */
public class SMSClient extends OzSmsClient {

    public SMSClient(String host, int port) throws IOException, InterruptedException {
        super(host, port);
    }

    @Override
    public void doOnMessageReceived(OzSMSMessage osmsm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doOnMessageDeliveredToHandset(OzSMSMessage osmsm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doOnMessageDeliveredToNetwork(OzSMSMessage osmsm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doOnMessageAcceptedForDelivery(OzSMSMessage osmsm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doOnMessageDeliveryError(OzSMSMessage osmsm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doOnClientConnectionError(int i, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
