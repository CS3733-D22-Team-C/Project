package edu.wpi.cs3733.D22.teamC;

import org.hibernate.Session;

import java.util.List;

/**
 * Run Hibernate ORM Framework
 */
public class HibernateManager {
    
    private HibernateManager() {}
    
    /**
     * Save an object to the database.
     * @param obj Object to save.
     */
    public static void saveObj(Object obj) {
        Session session = SessionManager.getSession();
        try {
            session.beginTransaction();
            session.save(obj);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            e.printStackTrace();
        }
    }
    
    /**
     * Save a list of objects to the database.
     * @param objectList A list of objects to save.
     */
    public static <T> void saveObjectList(List<T> objectList) {
        for (Object obj : objectList) {
            saveObj(obj);
        }
    }
    
    /**
     * Updates an existing object in the database.
     * @param obj An existing object to update.
     */
    public static void updateObj(Object obj) {
        Session session = SessionManager.getSession();
        try {
            session.beginTransaction();
            session.update(obj);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            e.printStackTrace();
        }
    }
    
    /**
     * Deletes an existing object in the database.
     * @param obj An existing object to delete.
     */
    public static void deleteObj(Object obj) {
        Session session = SessionManager.getSession();
        try {
            session.beginTransaction();
            session.delete(obj);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            e.printStackTrace();
        }
    }
    
}
