package edu.wpi.cs3733.D22.teamC.entity.floor;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAOTest;
import edu.wpi.cs3733.D22.teamC.factory.Factory;
import edu.wpi.cs3733.D22.teamC.factory.floor.FloorFactory;

public class FloorDAOTest extends DAOTest<Floor> {

    public FloorDAOTest() {
        super(new FloorFactory(), new FloorDAO());
    }
}
