package edu.wpi.cs3733.D22.teamC.entity.Employee;

import com.sun.prism.shader.Solid_Color_AlphaTest_Loader;
import edu.wpi.cs3733.D22.teamC.DBManager;
import org.sqlite.core.DB;

import javax.naming.spi.ResolveResult;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO Implimenting class for interfacing with the Employee DB table
 */
public class EmployeeDAOImpl implements EmployeeDAO{

    public List<Employee> getAllEmployees(){
       try{
           // Execute SELECT query
           Statement selectStatement = DBManager.getInstance().connection.createStatement();
           ResultSet resultSet = selectStatement.executeQuery("SELECT * FROM EMPLOYEE");

           // Returning the Employee Objects
           List<Employee> employees = new ArrayList<>();
           while (resultSet.next()) {
               Employee employee = createEmployee(resultSet);
               if (employee != null) employees.add(employee);
           }
           return employees;
       } catch (SQLException e){
           System.out.println("Query to LOCATION table failed.");
           e.printStackTrace();
       }
       return null;
    }

    @Override
    public Employee getEmployee(int employeeID){
        try{
            //Execute SELECT query
            PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                    "SELECT * FROM EMPLOYEE WHERE ID= ?"
            );
            statement.setInt(1, employeeID);
            ResultSet resultSet = statement.executeQuery();

            //return Employee object
            if(resultSet.next()) return createEmployee(resultSet);

        }catch(SQLException e){
            System.out.println("Query to get employee from EMPLOYEE table failed.");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int insertEmployee(Employee employee){
        try{
            // check if the entry of hte same employeeID already exits
            Employee employeeInDB = getEmployee(employee.getEmployeeID());
            if(employeeInDB == null){
                // execute INSERT statement
                PreparedStatement statement = (employee.getEmployeeID()==0)
                        ? DBManager.getInstance().connection.prepareStatement("INSERT INTO EMPLOYEE VALUES (DEFAULT,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)
                        : DBManager.getInstance().connection.prepareStatement("INSERT INTO EMPLOYEE VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

                int index = 1;
                if(employee.getEmployeeID() != 0){
                    statement.setString(index, String.valueOf(employee.getEmployeeID()));
                    index++;
                }
                statement.setString(index, employee.getFirstName()); index++;
                statement.setString(index, employee.getLastName()); index++;
                statement.setString(index, employee.getEmail()); index++;
                statement.setString(index, employee.getRole().toString()); index++;
                statement.setBoolean(index, employee.isAdmin());
                statement.execute();

                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()) return resultSet.getInt(1);

            }

        }catch(SQLException e){
            System.out.println("INSERT to EMPLOYEE table failed.");
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        try {
            //check if entry of the same employeeID exits
            Employee employeeInDB = getEmployee(employee.getEmployeeID());

            if(employeeInDB != null){
                PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                        "UPDATE EMPLOYEE SET FirstName=?, LastName=?, Email=?, Role=?, IsAdmin=? WHERE ID=?"
                );
                statement.setString(1,employee.getFirstName());
                statement.setString(2,employee.getLastName());
                statement.setString(3,employee.getEmail());
                statement.setString(4, employee.getRole().toString());
                statement.setBoolean(5,employee.isAdmin());
                statement.setInt(6, employee.getEmployeeID());
                statement.execute();

                return true;
            }
        }catch(SQLException E){
            System.out.println("Update to the EMPLOYEE table failed.");
            E.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteEmployee(Employee employee) {
        try{
            Employee employeeInDB = getEmployee(employee.getEmployeeID());
            if(employeeInDB != null){
                PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                        "DELETE FROM EMPLOYEE WHERE ID = ?"
                );
                statement.setInt(1,employee.getEmployeeID());
                statement.execute();

                return true;
            }

        }catch(SQLException e){
            System.out.println("DELETE from EMPLOYEE table failed.");
            e.printStackTrace();
        }
        return false;
    }


    private Employee createEmployee(ResultSet resultSet) {
        try{
            Employee employee = new Employee(resultSet.getInt("ID"));
            employee.setFirstName(typesafeTrim(resultSet.getString("FirstName")));
            employee.setLastName(typesafeTrim(resultSet.getString("LastName")));
            employee.setEmail(typesafeTrim(resultSet.getString("Email")));
            employee.setRole(Employee.Role.valueOf(typesafeTrim(resultSet.getString("Role"))));
            employee.setAdmin(resultSet.getBoolean("IsAdmin"));

            return employee;
        }
        catch(SQLException e){
            System.out.println("Creation of object from EMPLOYEE ResultSet failed.");
            e.printStackTrace();

            return null;
        }
    }
    /**
     * Trim str if not null.
     * @param str The String to trim.
     * @return The trimmed str.
     */
    private String typesafeTrim(String str) {
        if (str == null) return null;
        else return str.trim();
    }


}
