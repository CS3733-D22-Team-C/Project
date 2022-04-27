package edu.wpi.cs3733.D22.teamC.entity.generic;

import edu.wpi.cs3733.D22.teamC.SessionManager;
import edu.wpi.cs3733.D22.teamC.factory.Factory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public abstract class DAOTest<T extends IDEntity>{
    protected Factory<T> factory;
    protected DAO<T> dao;

    public DAOTest(Factory<T> factory, DAO<T> dao) {
        this.factory = factory;
        this.dao = dao;
    }


    @BeforeEach
    void setUp() {
        SessionManager.setTestDatabase();
        SessionManager.getSession();
    }

    @AfterEach
    void tearDown() {
        // Kill Session Factory after every test. Note: this will drop the tables!
        dao.deleteAllFromTable();
        SessionManager.killSessionFactory();
    }

    @Test
    void insertTest(){
        //create an object of the entity
        T obj = factory.create();

        // insert
        String id = dao.insert(obj);

        //get the ID and check if it doesn't equal to -1
        assertNotNull(id);

        // Retrieve object by using the getByID
        T queryObj = dao.getByID(id);

        // check if not null
        assertNotNull(queryObj);

        //check if they are equal
        assertEquals(obj, queryObj);
    }
    
    @Test
    void updateTest(){
        // Create a new object
        T obj = factory.create();
        
        // Insert into DB, and check that its ID is not null
        String id = dao.insert(obj);
        assertNotNull(id);
        
        // Retrieve object by using `getByID()` and check that it is not null
        T queryObj = dao.getByID(id);
        assertNotNull(queryObj);
        assertEquals(obj, queryObj);
        
        // Create a new object with new different attributes, but set its ID to the first object
        T newObj = factory.create();
        newObj.setID(queryObj.getID());
        
        // See if we can update the database with the new object with the same ID
        boolean success = dao.update(newObj);
        assertTrue(success);
        
        // Check that update is reflected in the DB
        queryObj = dao.getByID(id);
        assertNotNull(queryObj);
        assertEquals(newObj, queryObj);
    }

    @Test
    protected void deleteTest(){
        T obj = factory.create();
        String id = dao.insert(obj);
        assertNotNull(id);

        boolean success = dao.delete(obj);
        assertTrue(success);
    }

    @Test
    void getByIDTest(){
        T obj = factory.create();
        String id = dao.insert(obj);
        assertNotNull(id);

        T retrievedObj = dao.getByID(id);
        assertEquals(obj, retrievedObj);

    }

    @Test
    void getAllTests(){
        int size = 10;

        for (int i=0; i<size; i++) {
            T obj = factory.create();
          
            String id = dao.insert(obj);
            assertNotNull(id);
        }

        List<T> list = dao.getAll();
        assertEquals(size, list.size());

    }
}
