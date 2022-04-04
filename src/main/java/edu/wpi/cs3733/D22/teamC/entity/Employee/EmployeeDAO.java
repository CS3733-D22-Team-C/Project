package edu.wpi.cs3733.D22.teamC.entity.Employee;


import java.util.List;

public interface EmployeeDAO {
    public List<Employee> getAllEmployees();
    public Employee getEmployee(int employeeID);

    public int insertEmployee(Employee employee);
    public boolean updateEmployee(Employee employee);
    public boolean deleteEmployee(Employee employee);
}
