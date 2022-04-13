package edu.wpi.cs3733.D22.teamC;

import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;

/** Manage Hibernation session. */
public class SessionManager {
    private static DBMode serverDatabase = DBMode.EMBEDDED;
	private static SessionFactory sf = createSessionFactory(serverDatabase);

	private SessionManager() {}
    
    public enum DBMode {
        EMBEDDED,
        EMBEDDED_TEST,
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

    public static DBMode getServerDatabase() {
        return serverDatabase;
    }

    public static void setTestDatabase() {
        serverDatabase = DBMode.EMBEDDED_TEST;
    }

    private static SessionFactory createSessionFactory(DBMode mode) {
        if (mode == DBMode.SERVER) {
            return new Configuration().configure("hibernate_server.cfg.xml").buildSessionFactory();
        } else if (mode == DBMode.EMBEDDED){
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } else {
            Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
            cfg.setProperty("hibernate.hbm2ddl.auto", "create-drop");
            return cfg.buildSessionFactory();
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

        addStaticAccounts();
    }

    private static void addStaticAccounts() {
        EmployeeDAO dao = new EmployeeDAO();
        String[] accounts = new String[] {"admin", "staff"};
        Arrays.stream(accounts).forEach(accountName -> {
            if (dao.getEmployeeByUsername(accountName) == null) {
                Employee employee = new Employee();
                employee.setRole(Employee.Role.Doctor);
                if (accountName.equals("admin")) employee.setAdmin(true);
                employee.setUsername(accountName);
                employee.setPassword(accountName);
                dao.insert(employee);
            }
        });
    }
}
