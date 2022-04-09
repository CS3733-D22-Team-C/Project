package edu.wpi.cs3733.D22.teamC.entity.location;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAOTest;
import edu.wpi.cs3733.D22.teamC.factory.Factory;
import edu.wpi.cs3733.D22.teamC.factory.location.LocationFactory;

public class LocationDAOTest extends DAOTest<Location> {
    public LocationDAOTest() {
        super(new LocationFactory(), new LocationDAO());
    }
}
