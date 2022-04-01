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
public abstract class TableDisplay<T extends Object, V extends TableDisplay.TableDisplayEntry> {
    /**
     * An entry in the TableDisplay, created over an object of type T.
     */
    protected abstract class TableDisplayEntry extends RecursiveTreeObject<V> {
        public TableDisplayEntry(T entry) {};
    }

    // Variables
    ObservableList<V> entries = FXCollections.observableArrayList();

    public TableDisplay(JFXTreeTableView jfxTreeTableView) {
        // Table Setup
        TreeItem<V> root = new RecursiveTreeItem<V>(entries, RecursiveTreeObject::getChildren);
        jfxTreeTableView.setRoot(root);
        jfxTreeTableView.setShowRoot(false);
        jfxTreeTableView.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
    }

    protected <U> void addColumn(
            JFXTreeTableView table,
            String header,
            double maxWidth,
            Callback<V, ObservableValue<U>> callback
    ) {
        JFXTreeTableColumn<V, U> col = new JFXTreeTableColumn<>(header);
        col.setMaxWidth(maxWidth);
        col.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<V, U>, ObservableValue<U>>() {
            @Override
            public ObservableValue<U> call(TreeTableColumn.CellDataFeatures<V, U> param) {
                return callback.call(param.getValue().getValue());
            }
        });
        table.getColumns().add(col);
    }

    protected final void addEntry(V entry) {
        entries.add(entry);
    }

    public abstract void addObject(T object);
}
