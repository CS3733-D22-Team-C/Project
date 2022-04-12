package edu.wpi.cs3733.D22.teamC.entity.medical_equipment;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAOTest;
import edu.wpi.cs3733.D22.teamC.factory.Factory;
import edu.wpi.cs3733.D22.teamC.factory.medical_equipment.MedicalEquipmentFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class MedicalEquipmentDAOTest extends DAOTest<MedicalEquipment> {
    public MedicalEquipmentDAOTest() {
        super(new MedicalEquipmentFactory(), new MedicalEquipmentDAO());
    }

    @Test
    void testGetByLocation() {
        MedicalEquipment me1 = factory.create();
        MedicalEquipment me2 = factory.create();

        me2.setLocationID(me1.getLocationID());
        String locationID = me2.getLocationID();

        String id1 = dao.insert(me1);
        String id2 = dao.insert(me2);

        assertNotNull(id1);
        assertNotNull(id2);

        List<MedicalEquipment> meList = ((MedicalEquipmentDAO) dao).getEquipmentByLocation(locationID);

        assertEquals(2, meList.size());
    }

    @Test
    void testGetByFloor(){

    }
}
