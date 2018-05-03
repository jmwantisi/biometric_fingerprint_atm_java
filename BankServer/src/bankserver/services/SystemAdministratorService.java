/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankserver.services;

import java.util.ArrayList;

/**
 *
 * @author jmwantisi
 */
public class SystemAdministratorService extends Service {
    private ArrayList<Object> systemLogs;
    public SystemAdministratorService() {
        super();
        currentSession = new ArrayList<>();
        systemLogs = new ArrayList<>();
    }
    
    
    
    @Override
    protected void validateLoginDetails() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void newSessionValue(Object sessionValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected ArrayList<Object> currentSession() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     public ArrayList<Object> getSystemLogs(int search){
        //pass in integer value to determine the filter required for the logs from query
        
        return systemLogs;
    }
    
    
    
}
