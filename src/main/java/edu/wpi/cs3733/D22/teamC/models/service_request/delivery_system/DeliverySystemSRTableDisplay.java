package edu.wpi.cs3733.D22.teamC.models.service_request.delivery_system;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system.DeliverySystemSR;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DeliverySystemSRTableDisplay extends ServiceRequestTableDisplay<DeliverySystemSR> {
    
    public class DeliverySystemSRTableEntry extends ServiceRequestTableEntry {
        // Properties
        public StringProperty deliveryType;
        public StringProperty patientName;
        
        public DeliverySystemSRTableEntry(DeliverySystemSR deliverySystemSR) {
            super(deliverySystemSR);
    
            PatientDAO patientDAO = new PatientDAO();
            
            this.deliveryType = new SimpleStringProperty(deliverySystemSR.getDeliveryType().toString());
            this.patientName = new SimpleStringProperty((deliverySystemSR.getPatient() != null)
                    ? deliverySystemSR.getPatient().toString() : "N/A");
        }
    }
    
    public DeliverySystemSRTableDisplay(JFXTreeTableView table) {
        super(table);
    }
    
    @Override
    public void addObject(DeliverySystemSR object) {
        addEntry(new DeliverySystemSRTableEntry(object));
    }
    
    @Override
    protected void setColumns(JFXTreeTableView table) {
        // Insert Columns for Table
        super.setPartialColumns(table);
        
        addColumn(
                table,
                "Delivery Type",
                1f * Integer.MAX_VALUE * 16.66,
                (DeliverySystemSRTableEntry entry) -> {return entry.deliveryType;}
        );
    
        addColumn(
                table,
                "Patient Name",
                1f * Integer.MAX_VALUE * 16.66,
                (DeliverySystemSRTableEntry entry) -> {return entry.patientName;}
        );
    }
}
