package edu.wpi.cs3733.D22.teamC;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/** Manage Hibernation session. */
public class SessionManager {
    private static DBMode serverDatabase = DBMode.EMBEDDED;
	private static SessionFactory sf = createSessionFactory(serverDatabase);

	private SessionManager() {}
    
    public enum DBMode {
        EMBEDDED,
        SERVER
    }

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
    
    private static SessionFactory createSessionFactory(DBMode mode) {
        if (mode == DBMode.SERVER) {
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
     * @param mode DBMode.EMBEDDED for client-server database.
     */
	public static void switchDatabase(DBMode mode) {
        SessionManager.serverDatabase = (mode == DBMode.EMBEDDED) ? DBMode.EMBEDDED : DBMode.SERVER;
        killSessionFactory();
        sf = createSessionFactory(serverDatabase);
    }
}
