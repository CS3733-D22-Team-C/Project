package edu.wpi.cs3733.D22.teamC.entity.generic;

import edu.wpi.cs3733.D22.teamC.HibernateManager;

import java.util.List;

/**
 * Abstract wrapper class for DAO operations with Hibernate
 * @param <T>
 */
public abstract class DAO <T> {
    
    /**
     * When implementing a generic DAO, override this method to return the Object.class of the type T object.
     */
    protected abstract Class<T> classType();
    
    public int insert(Object obj) {
        return HibernateManager.insertObj(obj);
    }
    
    public boolean update(Object obj) {
        return HibernateManager.updateObj(obj);
    }
    
    public boolean delete(Object obj) {
        return HibernateManager.deleteObj(obj);
    }
    
    public T getByID(Integer id) {
        return HibernateManager.getObjByID(id, classType());
    }
    
    public List<T> getAll() {
        return HibernateManager.filterQuery("from " + classType().getName());
    }
    
    public boolean deleteAllFromTable() {
        return HibernateManager.deleteAllFromTable(classType());
    }
}
