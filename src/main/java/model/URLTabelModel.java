package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class URLTabelModel implements TableModel {

	List<String> urlList = new ArrayList<>();

	public URLTabelModel(Map<String, Map<String, String>> targetMap) {
		for (Map.Entry<String, Map<String, String>> entry : targetMap.entrySet()) {

			urlList.add(entry.getKey());
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {

		switch (columnIndex) {
		case 0:
			return String.class;
		}
		return null;
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public String getColumnName(int columnIndex) {

		switch (columnIndex) {
		case 0:
			return "Site";
		}
		return null;
	}

	@Override
	public int getRowCount() {
		return urlList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		return urlList.get(rowIndex);

	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	}

}
