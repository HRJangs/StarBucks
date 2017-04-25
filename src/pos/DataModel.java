package pos;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class DataModel extends AbstractTableModel{
	Vector<Vector> data= new Vector<Vector>();
	Vector<String> columnName= new Vector<String>();
	
	
	public DataModel(Vector data, Vector columnName) {
		this.data = data;
		this.columnName = columnName;
	}
	
	public String getColumnName(int col) {
		return columnName.get(col);
	}
	public int getRowCount() {
		return data.size();
	}

	public int getColumnCount() {
		return columnName.size();
	}
	
	public Object getValueAt(int row, int col) {
		return data.get(row).get(col);
	}
	//각셀의 값을 반영하는 메서드
	public void setValueAt(Object Value, int row, int col) {
		Vector vec =data.get(row);
		vec.set(col, Value);
		this.fireTableCellUpdated(row, col);
	}
	public boolean isCellEditable(int row, int col) {
		if(col!=0){
			return true;
		}
		return false;
	}
		
		
}
