package edu.wpi.cs3733.D22.teamC.controller.table;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;

public abstract class InsertTableViewController<T extends Object> {
    //References
    BaseTableViewController<T> parentController;
    protected boolean addMode;
    protected T currentRow;

    public void setup(BaseTableViewController parentController)
    {
        this.parentController = parentController;
        addMode = false;
    }

    public void getRowInfo(T row){
        addMode = false;
        fieldsEditable(true);
        this.currentRow = row;
    }

    void addClicked() {
        fieldsEditable(true);
        addMode = true;
        resetValues();
    }


    public abstract void deleteValue(T currentRow);
    public abstract TableDisplay<T> createTableDisplay(JFXTreeTableView table);
    public abstract void fieldsEditable(boolean edit);
    public abstract void resetValues();

}
