/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankserver.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author jmwantisi
 */
public abstract class Service { //interace
    
    protected final String DATABASE_DRIVER;
    protected final String DATABASE_URL;
    protected final String DATABASE_USERNAME;
    protected final String DATABASE_PASSWORD;
    
    protected static Connection connection;
    protected static ResultSet resultSet;
    protected static Statement statement;
    
    protected static ArrayList<Object> currentSession;
    protected static String logEntityAction;
    
    
    public Service() {
        this.DATABASE_DRIVER = "com.mysql.jdbc.Driver";
        this.DATABASE_URL = "jdbc:mysql://localhost:3306/nationalbankdatabase";
        this.DATABASE_USERNAME = "root";
        this.DATABASE_PASSWORD = "";
        this.statement = null;
        this.connection = null;
        
        this.logEntityAction = null;
        
    }
    
    protected abstract void validateLoginDetails();
    
    protected abstract void newSessionValue(Object sessionValue);
    
    protected abstract ArrayList<Object> currentSession();
    
    
}
