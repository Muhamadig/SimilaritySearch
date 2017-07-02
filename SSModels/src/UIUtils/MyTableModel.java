package UIUtils;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyTableModel(Object[] doc_columnNames, Object[][] data) {
		super(data, doc_columnNames);
	}

	public boolean isCellEditable(int row, int column) {
		return false;// false for every column

	}
}
