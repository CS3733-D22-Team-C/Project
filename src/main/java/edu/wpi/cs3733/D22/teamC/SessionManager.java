package edu.wpi.cs3733.D22.teamC;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/** Manage Hibernation session. */
public class SessionManager {
    private static boolean serverDatabase = true;
	private static SessionFactory sf = createSessionFactory(serverDatabase);

	private SessionManager() {}

	/**
	* Retrieves the session built from the factory. YOU MUST CLOSE THE SESSION AFTER USE!
	*
	* @return Hibernate Session object.
	*/
	public static Session getSession() {
        if (sf == null) {
            sf = createSessionFactory(serverDatabase); 
        }
		return sf.openSession();
	}
    
    private static SessionFactory createSessionFactory(boolean server) {
        if (server) {
            return new Configuration().configure("hibernate_server.cfg.xml").buildSessionFactory();
        } else {
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }
    }
    
    /**
     * If you're thinking of using this, don't.
     */
    public static void killSessionFactory() {
        try {
            sf.close();
            sf = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Switch the database system between embedded and client-server.
     * NOTE: THIS WILL NOT POPULATE THE NEW DATABASE DURING RUNTIME!
     * @param serverDatabase True for client-server database.
     */
	public static void switchDatabase(boolean serverDatabase) {
        SessionManager.serverDatabase = serverDatabase;
        killSessionFactory();
        createSessionFactory(serverDatabase);
    }
}
