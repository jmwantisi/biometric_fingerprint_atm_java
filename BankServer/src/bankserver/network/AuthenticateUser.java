/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankserver.network;

import bankserver.services.SystemAdministratorService;

/**
 *
 * @author jmwantisi
 */
public class AuthenticateUser {
    
    private static int clientType;
    private final int ATM_CLIENT = 1;
    private final int DESK_EMPLOYEE_CLIENT = 2;
    private final int SYSTEM_ADMINISTRATOR_CLIENT = 3;
    private static boolean customerAuthenticated = false;
    private static boolean deskEmployeeAuthenticated = false;
    private static boolean systemAdministratorAuthenticated = false;
   
    
    public AuthenticateUser() {
        
    }
    public void authoriseUser (int client){
        
        clientType = client;
        
        switch(clientType){
            case SYSTEM_ADMINISTRATOR_CLIENT:
                
                
                if(customerAuthenticated == true){
                    SystemAdministratorService service = new SystemAdministratorService();
                    //login and start using service
                    //get the session fields from the query.. acount number etc
                }
                
                //start using service as person login in
                
                break;
            
            case DESK_EMPLOYEE_CLIENT: 
                
                break;
            
            case ATM_CLIENT:
                
                break;
            
        }
    
    }
     
    public boolean authenticateCustomer(byte[] fingerprint, int cardNumber, int pin){
        
        //run check query
        return customerAuthenticated;
    }
     
    public boolean authenticateEmployee(String username, String password){
        
        //run check query
        return deskEmployeeAuthenticated;
    }
    
    //call this first
    public boolean authenticateSystemAdministrator(String username, String password){
        
        //run check query
        return systemAdministratorAuthenticated;
    }
    
    
}
