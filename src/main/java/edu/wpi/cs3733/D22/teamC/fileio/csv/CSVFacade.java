package edu.wpi.cs3733.D22.teamC.fileio.csv;

import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySR;
import edu.wpi.cs3733.D22.teamC.fileio.csv.employee.EmployeeCSVReader;
import edu.wpi.cs3733.D22.teamC.fileio.csv.employee.EmployeeCSVWriter;
import edu.wpi.cs3733.D22.teamC.fileio.csv.floor.FloorCSVReader;
import edu.wpi.cs3733.D22.teamC.fileio.csv.floor.FloorCSVWriter;
import edu.wpi.cs3733.D22.teamC.fileio.csv.location.LocationCSVReader;
import edu.wpi.cs3733.D22.teamC.fileio.csv.location.LocationCSVWriter;
import edu.wpi.cs3733.D22.teamC.fileio.csv.medical_equipment.MedicalEquipmentCSVReader;
import edu.wpi.cs3733.D22.teamC.fileio.csv.medical_equipment.MedicalEquipmentCSVWriter;
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.facility_maintenance.FacilityMaintenanceSRCSVReader;
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.facility_maintenance.FacilityMaintenanceSRCSVWriter;
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.lab_system.LabSystemSRCSVReader;
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.lab_system.LabSystemSRCSVWriter;
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.medical_equipment.MedicalEquipmentSRCSVReader;
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.medical_equipment.MedicalEquipmentSRCSVWriter;
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.medicine_delivery.MedicineDeliverySRCSVReader;
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.medicine_delivery.MedicineDeliverySRCSVWriter;
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.sanitation.SanitationSRCSVReader;
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.sanitation.SanitationSRCSVWriter;
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.security.SecuritySRCSVReader;
import edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.security.SecuritySRCSVWriter;

import java.io.File;
import java.util.List;

public class CSVFacade {

    /**
     * A generic CSV reading function that reads a file of a certain classType
     * @param classType the type of class
     * @param fileName file name for the respective list
     * @param <T> generic
     * @return List of certain type of objects
     */
    public static <T> List<T> read(Class<T> classType, String fileName){
        CSVReader<T> csvReader = null;

        if (classType == Floor.class) {
            csvReader = (CSVReader<T>) new FloorCSVReader();
        }
        else if(classType == Location.class){
            csvReader = (CSVReader<T>) new LocationCSVReader();
        }
        else if(classType == Employee.class){
            csvReader = (CSVReader<T>) new EmployeeCSVReader();
        }
        else if(classType == MedicalEquipment.class){
            csvReader = (CSVReader<T>) new MedicalEquipmentCSVReader();
        }
        else if(classType == FacilityMaintenanceSR.class){
            csvReader = (CSVReader<T>) new FacilityMaintenanceSRCSVReader();
        }
        else if(classType == LabSystemSR.class){
            csvReader = (CSVReader<T>) new LabSystemSRCSVReader();
        }
        else if(classType == MedicalEquipmentSR.class){
            csvReader = (CSVReader<T>) new MedicalEquipmentSRCSVReader();
        }
        else if(classType == MedicineDeliverySR.class){
            csvReader = (CSVReader<T>) new MedicineDeliverySRCSVReader();
        }
        else if(classType == SanitationSR.class){
            csvReader = (CSVReader<T>) new SanitationSRCSVReader();
        }
        else if(classType == SecuritySR.class){
            csvReader = (CSVReader<T>) new SecuritySRCSVReader();
        }

        return csvReader.readFile(fileName);
    }

    /**
     * A generic CSV reading function that reads a file of a certain classType
     * @param classType the type of class
     * @param file file to be read
     * @param <T> generic
     * @return List of certain type of objects
     */
    public static <T> List<T> read(Class<T> classType, File file){
        CSVReader<T> csvReader = null;

        if (classType == Floor.class) {
            csvReader = (CSVReader<T>) new FloorCSVReader();
        }
        else if(classType == Location.class){
            csvReader = (CSVReader<T>) new LocationCSVReader();
        }
        else if(classType == Employee.class){
            csvReader = (CSVReader<T>) new EmployeeCSVReader();
        }
        else if(classType == MedicalEquipment.class){
            csvReader = (CSVReader<T>) new MedicalEquipmentCSVReader();
        }
        else if(classType == FacilityMaintenanceSR.class){
            csvReader = (CSVReader<T>) new FacilityMaintenanceSRCSVReader();
        }
        else if(classType == LabSystemSR.class){
            csvReader = (CSVReader<T>) new LabSystemSRCSVReader();
        }
        else if(classType == MedicalEquipmentSR.class){
            csvReader = (CSVReader<T>) new MedicalEquipmentSRCSVReader();
        }
        else if(classType == MedicineDeliverySR.class){
            csvReader = (CSVReader<T>) new MedicineDeliverySRCSVReader();
        }
        else if(classType == SanitationSR.class){
            csvReader = (CSVReader<T>) new SanitationSRCSVReader();
        }
        else if(classType == SecuritySR.class){
            csvReader = (CSVReader<T>) new SecuritySRCSVReader();
        }

        return csvReader.readFile(file);
    }

    /**
     * Writes the list of data to a file of a certain class
     * @param classType the type of class
     * @param fileName file name for the respective list
     * @param data list of objects
     * @param <T> generic
     * @return true if it wrote, false otherwise
     */

    public static <T> boolean write(Class<T> classType, String fileName, List<T> data){
        CSVWriter<T> csvWriter = null;

        if (classType == Floor.class) {
            csvWriter = (CSVWriter<T>) new FloorCSVWriter();
        }
        else if(classType == Location.class){
            csvWriter = (CSVWriter<T>) new LocationCSVWriter();
        }
        else if(classType == Employee.class){
            csvWriter = (CSVWriter<T>) new EmployeeCSVWriter();
        }
        else if(classType == MedicalEquipment.class){
            csvWriter = (CSVWriter<T>) new MedicalEquipmentCSVWriter();
        }
        else if(classType == FacilityMaintenanceSR.class){
            csvWriter = (CSVWriter<T>) new FacilityMaintenanceSRCSVWriter();
        }
        else if(classType == LabSystemSR.class){
            csvWriter = (CSVWriter<T>) new LabSystemSRCSVWriter();
        }
        else if(classType == MedicalEquipmentSR.class){
            csvWriter = (CSVWriter<T>) new MedicalEquipmentSRCSVWriter();
        }
        else if(classType == MedicineDeliverySR.class){
            csvWriter = (CSVWriter<T>) new MedicineDeliverySRCSVWriter();
        }
        else if(classType == SanitationSR.class){
            csvWriter = (CSVWriter<T>) new SanitationSRCSVWriter();
        }
        else if(classType == SecuritySR.class){
            csvWriter = (CSVWriter<T>) new SecuritySRCSVWriter();
        }
        return csvWriter.writeFile(fileName, data);

    }

    /**
     * Writes the list of data to a file of a certain class
     * @param classType the type of class
     * @param file file to be read
     * @param data list of objects
     * @param <T> generic
     * @return true if it wrote, false otherwise
     */
    public static <T> boolean write(Class<T> classType, File file, List<T> data){
        CSVWriter<T> csvWriter = null;

        if (classType == Floor.class) {
            csvWriter = (CSVWriter<T>) new FloorCSVWriter();
        }
        else if(classType == Location.class){
            csvWriter = (CSVWriter<T>) new LocationCSVWriter();
        }
        else if(classType == Employee.class){
            csvWriter = (CSVWriter<T>) new EmployeeCSVWriter();
        }
        else if(classType == MedicalEquipment.class){
            csvWriter = (CSVWriter<T>) new MedicalEquipmentCSVWriter();
        }
        else if(classType == FacilityMaintenanceSR.class){
            csvWriter = (CSVWriter<T>) new FacilityMaintenanceSRCSVWriter();
        }
        else if(classType == LabSystemSR.class){
            csvWriter = (CSVWriter<T>) new LabSystemSRCSVWriter();
        }
        else if(classType == MedicalEquipmentSR.class){
            csvWriter = (CSVWriter<T>) new MedicalEquipmentSRCSVWriter();
        }
        else if(classType == MedicineDeliverySR.class){
            csvWriter = (CSVWriter<T>) new MedicineDeliverySRCSVWriter();
        }
        else if(classType == SanitationSR.class){
            csvWriter = (CSVWriter<T>) new SanitationSRCSVWriter();
        }
        else if(classType == SecuritySR.class){
            csvWriter = (CSVWriter<T>) new SecuritySRCSVWriter();
        }
        return csvWriter.writeFile(file, data);
    }


}
