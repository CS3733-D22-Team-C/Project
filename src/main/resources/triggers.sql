-- Reverse cascade deletion --
-- MedicalEquipmentSR 
CREATE TRIGGER reverse_cascade_MESR AFTER DELETE ON MEDICAL_EQUIPMENT_SR REFERENCING OLD AS OLD FOR EACH ROW DELETE FROM SERVICE_REQUEST WHERE ID = OLD.ID;

-- DeliverySystemSR 
CREATE TRIGGER reverse_cascade_DSSR AFTER DELETE ON DELIVERY_SYSTEM_SR REFERENCING OLD AS OLD FOR EACH ROW DELETE FROM SERVICE_REQUEST WHERE ID = OLD.ID;

-- LabSystemSR 
CREATE TRIGGER reverse_cascade_LSSR AFTER DELETE ON LAB_SYSTEM_SR REFERENCING OLD AS OLD FOR EACH ROW DELETE FROM SERVICE_REQUEST WHERE ID = OLD.ID;

-- MedicineDeliverySR 
CREATE TRIGGER reverse_cascade_MDSR AFTER DELETE ON MEDICINE_DELIVERY_SR REFERENCING OLD AS OLD FOR EACH ROW DELETE FROM SERVICE_REQUEST WHERE ID = OLD.ID;

-- PatientTransportSR 
CREATE TRIGGER reverse_cascade_PTSR AFTER DELETE ON PATIENT_TRANSPORT_SR REFERENCING OLD AS OLD FOR EACH ROW DELETE FROM SERVICE_REQUEST WHERE ID = OLD.ID;

-- TranslatorSR 
CREATE TRIGGER reverse_cascade_TSR AFTER DELETE ON TRANSLATOR_SR REFERENCING OLD AS OLD FOR EACH ROW DELETE FROM SERVICE_REQUEST WHERE ID = OLD.ID;