package edu.wpi.cs3733.D22.teamC.entity.employee;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.HibernateManager;
import edu.wpi.cs3733.D22.teamC.SessionManager;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Arrays;
import java.util.List;

public class EmployeeDAO extends DAO<Employee> {

    @Override
    protected Class<Employee> classType() {
        return Employee.class;
    }

    public Employee getEmployeeByUsername(String username) {
        List<Employee> employee = HibernateManager.filterQuery("select u from " + classType().getName() + " u where u.username = '" + username + "'");
        if (employee.size() == 1) {
            return employee.get(0);
        }
        else {
            return null;
        }
    }
    
    @Override
    public List<Employee> getAll() {
        return null;
    }
    
    /**
     * Override table delete to preserve backdoor accounts.
     * @return True if successful.
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean deleteAllFromTable() {
        Session session = SessionManager.getSession();
        try {
            session.beginTransaction();
            Query<Employee> query = session.createQuery("delete from " + classType().getName() + 
                    " e where e.username not like 'admin' and e.username not like 'staff'");
            query.executeUpdate();
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            e.printStackTrace();
            return false;
        }
    }
}
