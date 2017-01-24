package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class TableTextModel implements TableModel {

	Map<String, String> currentMap;
	List<String> keyList = new ArrayList<>();

	public TableTextModel(Map<String, String> targetMap) {
		currentMap = targetMap;

		for (String string : targetMap.keySet()) {
			keyList.add(string);
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
		case 1:
			return String.class;
		}
		return null;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "Text";
		case 1:
			return "%";
		}
		return null;
	}

	@Override
	public int getRowCount() {
		return currentMap.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String key = keyList.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return key;
		case 1:
			return currentMap.get(key);
		}
		return null;
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
