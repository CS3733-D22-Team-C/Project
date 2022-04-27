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
        SERVER,
        CLOUD
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
        SessionManager.serverDatabase = DBMode.EMBEDDED_TEST;
        killSessionFactory();
        sf = createSessionFactory(serverDatabase);
    }

    private static SessionFactory createSessionFactory(DBMode mode) {
        if (mode == DBMode.SERVER) {
            return new Configuration().configure("hibernate_server.cfg.xml").buildSessionFactory();
        } else if (mode == DBMode.EMBEDDED) {
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } else if (mode == DBMode.CLOUD) {
            return new Configuration().configure("hibernate_cloud.cfg.xml").buildSessionFactory();
        } else {
            return new Configuration().configure("hibernate_test.cfg.xml").buildSessionFactory();
        }
    }
    
    /**
     * If you're thinking of using this, don't.
     */
    public static void killSessionFactory() {
        try {
            if (sf != null) {
                sf.close();
            }
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
        SessionManager.serverDatabase = DBMode.EMBEDDED;    // Default to Embedded
        if (mode == DBMode.SERVER) SessionManager.serverDatabase = DBMode.SERVER;
        if (mode == DBMode.CLOUD) SessionManager.serverDatabase = DBMode.CLOUD;
        
        killSessionFactory();
        sf = createSessionFactory(serverDatabase);
        
        addStaticAccounts();
    }
    
    // Backdoor... don't judge me.
    private static void addStaticAccounts() {
        EmployeeDAO dao = new EmployeeDAO();
        String[] accounts = new String[] {"admin", "staff"};
        Arrays.stream(accounts).forEach(accountName -> {
            if (dao.getEmployeeByUsername(accountName) == null) {
                Employee employee = new Employee();
                if (accountName.equals("admin")) {
                    employee.setAdmin(true);
                    employee.setID("78091196-5f80-4d1e-9f46-8aaa644fea97");
                } else {
                    employee.setID("729fd5b6-5295-4c24-b801-423be19f04a6");
                }
                employee.setUsername(accountName);
                employee.setPassword(accountName);
                employee.setRole(Employee.Role.Doctor);
                employee.setFirstName(accountName.toUpperCase());
                employee.setLastName(accountName.toUpperCase());
                employee.setPhone("N/A");
                dao.insert(employee);
            }
        });
    }
}
