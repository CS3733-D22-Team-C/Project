package edu.wpi.cs3733.D22.teamC;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/** Manage Hibernation session. */
public class SessionManager {
	private static SessionFactory sf = createSessionFactory();

	private SessionManager() {}

	/**
	* Retrieves the session built from the factory. YOU MUST CLOSE THE SESSION AFTER USE!
	*
	* @return Hibernate Session object.
	*/
	public static Session getSession() {
        if (sf == null) {
            sf = createSessionFactory(); 
        }
		return sf.openSession();
	}
    
    private static SessionFactory createSessionFactory() {
        return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
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

	// TODO: add switch for embedded and server databases
}
