package edu.wpi.cs3733.D22.teamC;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Manage Hibernation session.
 */
public class SessionManager {
    private static final SessionFactory sf = new Configuration().configure().buildSessionFactory();
    
    private SessionManager() {}
    
    public static Session getSession() {
        return sf.openSession();
    }
    
    //TODO: add switch for embedded and server databases
}
