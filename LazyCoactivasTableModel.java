/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * Dummy implementation of LazyDataModel that uses a list to mimic a real
 * datasource like a database.
 */
public class LazyCoactivasTableModel extends LazyDataModel<Coactivas> {

    private List<Coactivas> datasource;

    public LazyCoactivasTableModel(List<Coactivas> datasource) {
        this.datasource = datasource;
    }

    @Override
    public Coactivas getRowData(String rowKey) {
        for (Coactivas coac : datasource) {
            if (coac.getCoactivaId().equals(rowKey)) {
                return coac;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(Coactivas coac) {
        return coac.getCoactivaId();
    }

    @Override
    public List<Coactivas> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<Coactivas> data = new ArrayList<Coactivas>();

        //filter
        for (Coactivas coac : datasource) {
            boolean match = true;

            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(coac.getClass().getField(filterProperty).get(coac));

                        if (filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                        } else {
                            match = false;
                            break;
                        }
                    } catch (Exception e) {
                        match = false;
                    }
                }
            }

            if (match) {
                data.add(coac);
            }
        }

        //sort
        if (sortField != null) {
            Collections.sort(data, new LazySorterCoac(sortField, sortOrder));
        }

        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);

        //paginate
        if (dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        } else {
            return data;
        }
    }
}
