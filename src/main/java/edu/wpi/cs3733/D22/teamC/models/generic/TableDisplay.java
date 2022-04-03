package edu.wpi.cs3733.D22.teamC.models.generic;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.util.Callback;

/**
 * A wrapper for creating and managing TableDisplays.
 * @param <T> The type of object which the TableDisplay wrapper works on.
 */
public abstract class TableDisplay<T extends Object> {
    /**
     * An entry in the TableDisplay, created over an object of type T.
     */
    public abstract class TableDisplayEntry extends RecursiveTreeObject<TableDisplayEntry> {
        protected T object;

        public TableDisplayEntry(T object) {
            this.object = object;
        };
    }

    // Variables
    ObservableList<TableDisplayEntry> entries = FXCollections.observableArrayList();

    /**
     * Configure Table Display to JFXTreeTableView.
     * @param table The JFXTreeTableView.
     */
    public TableDisplay(JFXTreeTableView table) {
        TreeItem<TableDisplayEntry> root = new RecursiveTreeItem<TableDisplayEntry>(entries, RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);
        table.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
        setColumns(table);
    }

    //region Abstraction
        /**
         * Add object as a row entry. Essentially, call the constructor for the corresponding TableEntry object.
         * @param object The object of type T to be added.
         */
        public abstract void addObject(T object);

        /**
         * Set the columns of the JFXTreeTableView table. Override behavior to define columns for the table.
         * @param table The JFXTreeTableView.
         */
        protected abstract void setColumns(JFXTreeTableView table);
    //#endregion

    /**
     * Add a column to the JFXTreeTableView.
     * @param table The JFXTreeTableView.
     * @param header Header of the column.
     * @param maxWidth Max width of the column
     * @param callback Callback function foreach TableDisplayEntry to ObservableObject
     * @param <U> TableDisplayEntry the table acts on.
     * @param <V> Value ObservableValue acts on.
     */
    protected final <U extends TableDisplayEntry, V> void addColumn(JFXTreeTableView table, String header,
                                                              double maxWidth, Callback<U, ObservableValue<V>> callback) {
        JFXTreeTableColumn<U, V> col = new JFXTreeTableColumn<>(header);
        col.setMaxWidth(maxWidth);
        col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<U, V>, ObservableValue<V>>() {
            @Override
            public ObservableValue<V> call(TreeTableColumn.CellDataFeatures<U, V> param) {
                return callback.call(param.getValue().getValue());
            }
        });
        table.getColumns().add(col);
    }

    protected final <U extends TableDisplayEntry> void addEntry(U entry) {
        entries.add(entry);
    }
}
