package edu.wpi.cs3733.D22.teamC;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

/** Run Hibernate ORM Framework */
public class HibernateManager {

	private HibernateManager() {}

	/**
	* Save an object to the database (insert).
	*
	* @param obj Object to save.
	*/
	public static int insertObj(Object obj) {
		Session session = SessionManager.getSession();
		try {
			session.beginTransaction();
			int generatedID = (Integer) session.save(obj);
			session.getTransaction().commit();
			session.close();
			return generatedID;
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();
			return -1;
		}
	}

	/**
	* Save a list of objects to the database.
	*
	* @param objectList A list of objects to save.
	*/
	public static <T> void insertObjs(List<T> objectList) {
		for (Object obj : objectList) {
			insertObj(obj);
		}
	}

	/**
	 * Updates an existing object in the database.
	 * @param obj An existing object to update.
     * @return True if successful, false if not.
	*/
	public static boolean updateObj(Object obj) {
		Session session = SessionManager.getSession();
		try {
			session.beginTransaction();
			session.update(obj);
			session.getTransaction().commit();
			session.close();
            return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();
		}
        return false;
	}

	/**
	 * Deletes an existing object in the database.
	 * @param obj An existing object to delete.
     * @return True if successful, false if not.   
	*/
	public static boolean deleteObj(Object obj) {
		Session session = SessionManager.getSession();
		try {
			session.beginTransaction();
			session.delete(obj);
			session.getTransaction().commit();
			session.close();
            return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();
		}
        return false;
	}

	/**
	* Get a specific entry in the database of a given Class type.
	*
	* @param id The ID of the entry in the database
	* @param objType A class type in the DB, i.e. Location
	* @return The entry in the database as an object of the given type.
	*/
	public static <T> T getObjByID(Integer id, Class<T> objType) {
		Session session = SessionManager.getSession();
		try {
			session.beginTransaction();
			return objType.cast(session.get(objType, id));
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();
			return null;
		}
	}
    
    /**
     * Run a filtered query on the database.
     * @param sqlQuery SQL query
     *                 
     * @return A list of objects from the database.
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> filterQuery(String sqlQuery) {
        Session session = SessionManager.getSession();
        try {
            session.beginTransaction();
            Query<T> query = session.createQuery(sqlQuery);
            List<T> objs = query.list();
            session.getTransaction().commit();
            session.close();
            return objs;
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            e.printStackTrace();
            return null;
        }
    }

	
}
