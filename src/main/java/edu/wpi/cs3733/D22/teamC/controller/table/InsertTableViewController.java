package edu.wpi.cs3733.D22.teamC.controller.table;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.generic.IDEntity;
import edu.wpi.cs3733.D22.teamC.models.builders.NotificationBuilder;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;

public abstract class InsertTableViewController<T extends IDEntity> {
    // References
    BaseTableViewController<T> parentController;

    //#region Setup
        public void setup(BaseTableViewController parentController) {
            this.parentController = parentController;
        }
    //#endregion

    //#region Abstraction
        public abstract T createObject();
        public abstract TableDisplay<T> createTableDisplay(JFXTreeTableView table);
        public abstract DAO<T> createDAO();
        public abstract String getObjectName();

    //#endregion

    //#region DB Operations
        public void addObject() {
            // Create Object
            T object = createObject();
            setValues(object);

            // DB Insertion
            DAO<T> dao = createDAO();
            dao.insert(object);

            // Add to Table
            parentController.tableDisplay.addObject(object);

            // Push Notification
            String notification = "New " + getObjectName() + " " + object.toString() + " has been created.";
            NotificationBuilder.createNotification(getObjectName() + " Created", notification);
        }

        public void deleteObject() {
            // Get Object
            T object = parentController.currentObj;

            // DB Removal
            DAO<T> dao = createDAO();
            dao.delete(object);

            // Remove from Table
            parentController.tableDisplay.removeObject(object);

            //Push Notification
            String notification = getObjectName() + " " + object.toString() + " has been deleted.";
            NotificationBuilder.createNotification(getObjectName() + " Deleted", notification);
        }

        public void updateObject() {
            // Get Object
            T object = parentController.currentObj;
            object = setValues(object);

            // DB Update
            DAO<T> dao = createDAO();
            dao.update(object);

            // Update on Table
            parentController.tableDisplay.updateObject(object);

            //Push Notification
            String notification = getObjectName() + " " + object.toString() + " has been updated.";
            NotificationBuilder.createNotification(getObjectName() + " Updated", notification);
        }
    //#endregion

    //#region Field Interaction
        /**
         * Set values of the given object based on insert fields.
         * @param object The object to have its values overwritten.
         * @return The modified object.
         */
        public abstract T setValues(T object);

        /**
         * Set values of insert fields from the given object.
         * @param object The object to set field values from.
         */
        public abstract void setFields(T object);
    //#endregion

}
