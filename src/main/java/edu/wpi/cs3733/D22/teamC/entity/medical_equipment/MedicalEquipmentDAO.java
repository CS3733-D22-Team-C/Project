package edu.wpi.cs3733.D22.teamC.entity.medical_equipment;

import edu.wpi.cs3733.D22.teamC.HibernateManager;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;

import java.util.List;

public class MedicalEquipmentDAO extends DAO<MedicalEquipment> {

    @Override
    protected Class<MedicalEquipment> classType() {
        return MedicalEquipment.class;
    }

    public int insert(MedicalEquipment equipment) {
        return HibernateManager.insertObj(equipment);
    }

    public boolean update(MedicalEquipment equipment) {
        return HibernateManager.updateObj(equipment);
    }

    public boolean delete(MedicalEquipment equipment) {
        return HibernateManager.deleteObj(equipment);
    }

    public MedicalEquipment getByID(Integer id) {
        return HibernateManager.getObjByID(id, classType());
    }

    public List<MedicalEquipment> getAll() {
        return HibernateManager.filterQuery("from " + classType().getName());
    }

    public List<MedicalEquipment> getAllFromLocation() {
        return HibernateManager.filterQuery("from ")
    }

    public boolean deleteAllFromTable() {
        return HibernateManager.deleteAllFromTable(classType());
    }
}
