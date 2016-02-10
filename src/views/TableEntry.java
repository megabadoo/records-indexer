package views;

import javax.swing.DefaultCellEditor;

/*
 * TableDemo.java requires no other files.
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/** 
 * TableDemo is just like SimpleTableDemo, except that it
 * uses a custom TableModel.
 */
public class TableEntry extends JTable implements BatchListener {
    private boolean DEBUG = false;
    private BatchState batchState;
    private IndexingFrame parent;
    private DefaultTableModel model;
    private TableEntry tableEntry;

    public TableEntry(IndexingFrame parent, BatchState bs, DefaultTableModel model) {
    	
    	super(model);
    	this.tableEntry = this;
    	this.parent = parent;
    	this.model = model;
        this.batchState = bs;
        batchState.addToList(this);
        
        this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.setColumnSelectionAllowed(true);
        this.setRowSelectionAllowed(true);
		
        this.setGridColor(Color.BLACK);
        
    }
    
    private void removeColumn(int index, JTable myTable){
		int nRow= myTable.getRowCount();
		int nCol= myTable.getColumnCount()-1;
		Object[][] cells= new Object[nRow][nCol];
		String[] names= new String[nCol];

		for(int j=0; j<nCol; j++){
			if(j<index){
				names[j]= myTable.getColumnName(j);
				for(int i=0; i<nRow; i++){
					cells[i][j]= myTable.getValueAt(i, j);
				}
			}else{
				names[j]= myTable.getColumnName(j+1);
				for(int i=0; i<nRow; i++){
					cells[i][j]= myTable.getValueAt(i, j+1);
				}
			}
		}

		model= new DefaultTableModel(cells, names);
		myTable.setModel(model);       
	}

    public void clearTable(){
    	int columnCount = tableEntry.getColumnCount();
		for(int y=0; y<columnCount; y++){
			removeColumn(0,tableEntry);
		}
    }
    
    public void setTable(Object[][] tableValues, Object[] columnNames){	
		
		int columnCount = tableEntry.getColumnCount();
		for(int y=0; y<columnCount; y++){
			removeColumn(0,tableEntry);
		}
		
		for(int x=0; x<columnNames.length; x++){
			model.addColumn(columnNames[x], tableValues[x]);
			
			if(x!=0){
				TableColumn soprtColumn=tableEntry.getColumnModel().getColumn(x);
				soprtColumn.setCellEditor(new DefaultCellEditor (new JTextField()));
				tableEntry.setCellSelectionEnabled(true);
			}
		}	
		
		tableEntry.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
			
		       int row = tableEntry.getEditingRow();
		       int col = tableEntry.getEditingColumn();
		       
		       if(batchState.getCurrentRow()!=row || batchState.getCurrentField()!=col-1){
		       if (row >= 0 && col >= 1) {
		    	   	tableEntry.setColumnSelectionInterval(col, col);
					tableEntry.setRowSelectionInterval(row, row);
					
					if(batchState.getCurrentRow()!=row || batchState.getCurrentField()!=col-1){
	            		batchState.setCurrentRow(row);
	            		batchState.setCurrentField(col-1);
	            		batchState.notifyCellChange();
	            	}
					
		    	   parent.showFieldHelp(parent.getFieldHelp(col-1));
		        }
		        else if(row ==0 && col ==0){
		        
		        	tableEntry.setColumnSelectionInterval(col, col);
					tableEntry.setRowSelectionInterval(row, row);
		        	parent.showFieldHelp(parent.getFieldHelp(col));
		        }
		       }
		    }

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		 tableEntry.setCellSelectionEnabled(true);
		    ListSelectionModel cellSelectionModel = tableEntry.getSelectionModel();
		    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			tableEntry.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = tableEntry.rowAtPoint(evt.getPoint());
		        int col = tableEntry.columnAtPoint(evt.getPoint());
		        if (row >= 0 && col >= 1) {
		        	if(batchState.getCurrentRow()!=row || batchState.getCurrentField()!=col-1){
	            		batchState.setCurrentRow(row);
	            		batchState.setCurrentField(col-1);
	            		batchState.notifyCellChange();
	            	}
		        	parent.showFieldHelp(parent.getFieldHelp(col-1));
		        }
		        else if(col ==0){
		        	
		        	parent.showFieldHelp(parent.getFieldHelp(col));
		        }
		    }
		});
		    
//		        int row = tableEntry.getSelectedRow();
//		        int col = tableEntry.getSelectedColumn();
//		        if (col>0) {
//		        	if(batchState.getCurrentRow()!=row || batchState.getCurrentField()!=col-1){
//	            		batchState.setCurrentRow(row);
//	            		batchState.setCurrentField(col-1);
//	            		batchState.notifyCellChange();
//	            	}
//			           parent.showFieldHelp(parent.getFieldHelp(col-1));
//			           
//		        }else{
//		        	parent.showFieldHelp(parent.getFieldHelp(col));
//		        }
		        	// else if(col==0){
//			        	if(batchState.getCurrentRow()!=row || batchState.getCurrentField()!=col){
//		            		batchState.setCurrentRow(row);
//		            		batchState.setCurrentField(col);
//		            		batchState.notifyCellChange();
//		            	}
//			        	parent.showFieldHelp(parent.getFieldHelp(col));
//			        }
//		      }
//		    });
		
//		tableEntry.addMouseListener(new java.awt.event.MouseAdapter() {
//		    @Override
//		    public void mouseClicked(java.awt.event.MouseEvent evt) {
//		        int row = tableEntry.rowAtPoint(evt.getPoint());
//		        int col = tableEntry.columnAtPoint(evt.getPoint());
//		        if (row >= 1 && col >= 1) {
//		           fieldHelp.showUrl(helpUrls.get(col-1));
//		        }
//		        else if(row ==0 && col ==0){
//		        	fieldHelp.showUrl(helpUrls.get(0));
//		        }
//		    }
//		});
	}

	@Override
	public void cellChanged() {
		
		int newRecord = batchState.getCurrentRow();
		int newField = batchState.getCurrentField();
		int currentRecord = tableEntry.getSelectedRow();
		int currentField = tableEntry.getSelectedColumn();
		
		if(newRecord!=currentRecord || newField!=currentField){
			currentField=newField;
			currentRecord=newRecord;
			tableEntry.setColumnSelectionInterval(currentField+1, currentField+1);
			tableEntry.setRowSelectionInterval(currentRecord, currentRecord);
		}
		
		
	}
}