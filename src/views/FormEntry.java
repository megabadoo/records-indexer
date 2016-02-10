package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import shared.communication.*;
import java.util.*;

import javax.swing.*;
 
public class FormEntry extends JPanel implements ActionListener, BatchListener {

	private BatchState batchState;
	private JPanel titles;
	private JPanel textFields;
	private IndexingFrame indexingFrame;
	private int currentRecord, currentField;
	
	public FormEntry(IndexingFrame indexingFrame, BatchState bs) {
		
		int currentRecord=0;
		int currentField=0;
		this.indexingFrame = indexingFrame;
		this.batchState = bs;
		batchState.addToList(this);
		this.setLayout(new GridLayout(0,2));
		
		titles = new JPanel();
		titles.setLayout(new BoxLayout(titles, BoxLayout.PAGE_AXIS));
		
		textFields = new JPanel();
		textFields.setLayout(new BoxLayout(textFields, BoxLayout.PAGE_AXIS));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void add(Container parent, ArrayList<String> fieldTitles, int numRecords){
		
		
		//titles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		
		//textFields.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		fieldTitles.remove(0);
		for(int x=0; x<fieldTitles.size(); x++){
			int y = x;
			titles.add(new JLabel(fieldTitles.get(x)));
			titles.add(Box.createVerticalGlue());
			JTextField textField = new JTextField(10);
			 textField.addFocusListener(new FocusListener() {

		            @Override
		            public void focusGained(FocusEvent e) {
		            	
		            	if(batchState.getCurrentRow()!=batchState.getCurrentRow() || batchState.getCurrentField()!=y){
		            		batchState.setCurrentRow(currentRecord);
		            		batchState.setCurrentField(y);
		            		currentRecord = batchState.getCurrentRow();
		            		currentField = batchState.getCurrentField();
		            		batchState.notifyCellChange();
		            	}
		                indexingFrame.showFieldHelp(indexingFrame.getFieldHelp(currentField));
		            }

		            @Override
		            public void focusLost(FocusEvent e) {
		                //Your code here
		            }
		        });
			
			textField.setMaximumSize(new Dimension(100, 10));
			textFields.add(textField);
			textFields.add(Box.createVerticalGlue());
			//textFields.add(Box.createHorizontalGlue());
		}
		
		
		parent.add(titles);
		parent.add(textFields);
		textFields.getComponents()[0].requestFocus();
		
		
		revalidate();
	}
	
	public void clear(){
		
		titles.removeAll();
		textFields.removeAll();
		
	}

	@Override
	public void cellChanged() {
		int newRecord = batchState.getCurrentRow();
		int newField = batchState.getCurrentField();
		if(newRecord!=currentRecord || newField!=currentField){
			currentField=newField;
			currentRecord=newRecord;
			textFields.getComponents()[newField*2].requestFocus();
		}
		
		
	}

	public int getCurrentRecord() {
		return currentRecord;
	}

	public void setCurrentRecord(int currentRecord) {
		this.currentRecord = currentRecord;
		if(batchState.getCurrentRow()!=currentRecord){
			batchState.setCurrentRow(currentRecord);
			batchState.notifyCellChange();
		}
	}

	public int getCurrentField() {
		return currentField;
	}

	public void setCurrentField(int currentField) {
		this.currentField = currentField;
		
	}
	
	
	
	
}