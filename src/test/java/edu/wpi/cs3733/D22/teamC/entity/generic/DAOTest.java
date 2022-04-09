package edu.wpi.cs3733.D22.teamC.entity.generic;

import edu.wpi.cs3733.D22.teamC.SessionManager;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySRDAO;
import edu.wpi.cs3733.D22.teamC.factory.Factory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class DAOTest<T>{
    protected Factory<T> factory;
    protected DAO<T> DAO;

    public DAOTest(Factory<T> factory, DAO<T> DAO) {
        this.factory = factory;
        this.DAO = DAO;
    }


    @BeforeEach
    void setUp() {
        SessionManager.getSession();
    }

    @AfterEach
    void tearDown() {
        // Kill Session Factory after every test. Note: this will drop the tables!
        SessionManager.killSessionFactory();
    }

    @Test
    void insertTest(){
        //create an object of the entity
        T obj = factory.create();

        // insert
        String id = DAO.insert(obj);

        //get the ID and check if it doesn't equal to -1
        assertNotNull(id);

        // Retrieve object by using the getByID
        T queryObj = DAO.getByID(id);

        // check if not null
        assertNotNull(queryObj);

        //check if they are equal
        assertEquals(obj, queryObj);
    }

    
    // TODO: Abstract ID to Entity superclass for overwriting test procedure
    //@Test
    void updateTest(){
        //create a new Factory
        T obj = factory.create();
        // insert
        String id = DAO.insert(obj);
        //get the ID and check if it doesn't equal to -1
        assertNotNull(id);
        // Retrieve object by using the getByID
        T queryObj = DAO.getByID(id);
        // check if not null
        assertNotNull(queryObj);

        assertEquals(obj, queryObj);
    }

    @Test
    void deleteTest(){
        T obj = factory.create();
        String id = DAO.insert(obj);
        assertNotNull(id);

        boolean success = DAO.delete(obj);
        assertTrue(success);
    }

    @Test
    void getByIDTest(){
        T obj = factory.create();
        String id = DAO.insert(obj);
        assertNotNull(id);

        T retrievedObj = DAO.getByID(id);
        assertEquals(obj, retrievedObj);

    }

    @Test
    void getAllTests(){
        int size = 10;

        for (int i=0; i<size; i++) {
            T obj = factory.create();
            String id = DAO.insert(obj);
            assertNotNull(id);
        }


        List<T> list = DAO.getAll();
        assertEquals(size, list.size());

    }
}
