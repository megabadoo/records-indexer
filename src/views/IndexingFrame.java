package views;
 
import java.awt.*;
import java.io.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import shared.communication.ValidateUser_Result;
 
@SuppressWarnings("serial")
public class IndexingFrame extends JFrame {
	
	
	private BatchState batchState;
	private ImageComponent imageComponent;
	private Frames parent;
	//private JTable tableEntry;
	private TableEntry tableEntry;
	private DefaultTableModel model;
	private FormEntry formEntry;
	private DefaultListModel<String> listModel;
	private JList list;
	private DrawingComponent component;
	private JSlider slider;
	private JPanel componentPanel;
	private ArrayList<String> helpUrls;
	private FieldHelp fieldHelp;
	private JSplitPane verticalSplitPane, horizontalSplitPane;
	private IndexingFrame indexingFrame;
	
	
public IndexingFrame(Frames parent, BatchState bs) {
		
		this.parent = parent;
		this.batchState = bs;
		this.indexingFrame = this;
		
		// Set the window's title
		this.setTitle("Indexing");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		//Create menu bar
		this.createMenuBar(this);
		
		this.setPreferredSize(new Dimension(2000, 1000));
        	
		// Set the location of the window on the desktop
		this.setLocation(100, 100);

		// Size the window according to the preferred sizes and layout of its subcomponents
		this.pack();
		
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
		ButtonBar buttonBar = new ButtonBar(this, batchState);
		contentPane.add(buttonBar);
		
		JTabbedPane leftTabbedPane = new JTabbedPane();
		formEntry = new FormEntry(this, batchState);
		//TableEntry table = new TableEntry();
		model = new DefaultTableModel() {
			@Override
			   public boolean isCellEditable(int row, int column) {
			       
			       return column != 0;
			   }
		};
		
//		tableEntry = new JTable(model);
//		tableEntry.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//		tableEntry.setColumnSelectionAllowed(true);
//		tableEntry.setRowSelectionAllowed(true);
//		
//		tableEntry.setGridColor(Color.BLACK);
		
		tableEntry = new TableEntry(this, batchState, model);
		
		
		
		
		
		JScrollPane scrollpane = new JScrollPane(tableEntry);
		leftTabbedPane.add("Table Entry", scrollpane);
		
		listModel = new DefaultListModel<String>();
		list = new JList(listModel);
		JScrollPane listScroll = new JScrollPane(list);
		
		JScrollPane formScroll = new JScrollPane(formEntry);
		
		JPanel formSplit = new JPanel();
		formSplit.setLayout(new BoxLayout(formSplit, BoxLayout.LINE_AXIS));
		formSplit.add(listScroll);
		formSplit.add(formScroll);
	
		leftTabbedPane.add("Form Entry", formSplit);
		
		JTabbedPane rightTabbedPane = new JTabbedPane();
		fieldHelp = new FieldHelp(batchState);
		JScrollPane helpScroll = new JScrollPane(fieldHelp);
		helpScroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		ImageNavigation imageNavigation = new ImageNavigation();
		rightTabbedPane.add("Field Help", helpScroll);
		rightTabbedPane.add("Image Navigation", imageNavigation);
		
		horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftTabbedPane, rightTabbedPane);
		horizontalSplitPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		horizontalSplitPane.setDividerLocation(this.getWidth()/2);
		horizontalSplitPane.setOneTouchExpandable(true);
		horizontalSplitPane.setResizeWeight(0.5);
		
		this.imageComponent = new ImageComponent();
		
		
		componentPanel = new JPanel();
		//componentPanel.setBackground(Color.gray);
		//componentPanel.setPreferredSize(new Dimension(400,400));
		component = new DrawingComponent(this, batchState);
		componentPanel.add(component, BorderLayout.CENTER);
		
//		slider = new JSlider(1, 100, 20);
//		slider.addChangeListener(sliderChangeListener);
//		componentPanel.add(slider, BorderLayout.SOUTH);
		
		verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, component, horizontalSplitPane);
		verticalSplitPane.setDividerLocation(this.getHeight()/2);
		verticalSplitPane.setOneTouchExpandable(true);
		verticalSplitPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		verticalSplitPane.setResizeWeight(0.5);
		
		contentPane.add(verticalSplitPane);
		
		this.pack();
	}


//	private void removeColumn(int index, JTable myTable){
//		int nRow= myTable.getRowCount();
//		int nCol= myTable.getColumnCount()-1;
//		Object[][] cells= new Object[nRow][nCol];
//		String[] names= new String[nCol];
//
//		for(int j=0; j<nCol; j++){
//			if(j<index){
//				names[j]= myTable.getColumnName(j);
//				for(int i=0; i<nRow; i++){
//					cells[i][j]= myTable.getValueAt(i, j);
//				}
//			}else{
//				names[j]= myTable.getColumnName(j+1);
//				for(int i=0; i<nRow; i++){
//					cells[i][j]= myTable.getValueAt(i, j+1);
//				}
//			}
//		}
//
//		model= new DefaultTableModel(cells, names);
//		myTable.setModel(model);       
//	}

	public void setFieldHelp(ArrayList<String> helpUrls){
		this.helpUrls = helpUrls;
	}
	
	public String getFieldHelp(int col){
		return helpUrls.get(col);
	}
	
	public void clearFieldHelp(){
		fieldHelp.clearUrl();
	}
	
	public void showFieldHelp(String url){
		fieldHelp.showUrl(url);
	}
	
	public void clearTable() {
		tableEntry.clearTable();
		
//		int columnCount = tableEntry.getColumnCount();
//		for(int y=0; y<columnCount; y++){
//			removeColumn(0,tableEntry);
//		}
	}
	
	public void setTable(Object[][] tableValues, Object[] columnNames){	

		tableEntry.setTable(tableValues, columnNames);
		
//		int columnCount = tableEntry.getColumnCount();
//		for(int y=0; y<columnCount; y++){
//			removeColumn(0,tableEntry);
//		}
//		
//		for(int x=0; x<columnNames.length; x++){
//			model.addColumn(columnNames[x], tableValues[x]);
//			
//			if(x!=0){
//				TableColumn soprtColumn=tableEntry.getColumnModel().getColumn(x);
//				soprtColumn.setCellEditor(new DefaultCellEditor (new JTextField()));
//				tableEntry.setCellSelectionEnabled(true);
//			}
//		}	
//		
////		tableEntry.addFocusListener(new FocusListener() {
////			public void focusGained(FocusEvent e) {
////		       int row = tableEntry.getEditingRow();
////		       int col = tableEntry.getEditingColumn();
////		       
////		       if (row >= 1 && col >= 1) {
////		           fieldHelp.showUrl(helpUrls.get(col-1));
////		        }
////		        else if(row ==0 && col ==0){
////		        	fieldHelp.showUrl(helpUrls.get(0));
////		        }
////		    }
////
////			@Override
////			public void focusLost(FocusEvent e) {
////				// TODO Auto-generated method stub
////				
////			}
////		});
//		
//		
//		 tableEntry.setCellSelectionEnabled(true);
//		    ListSelectionModel cellSelectionModel = tableEntry.getSelectionModel();
//		    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//
//		    cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
//		      public void valueChanged(ListSelectionEvent e) {
//		      
//		    
//		        int row = tableEntry.getSelectedRow();
//		        int col = tableEntry.getSelectedColumn();
//		        if (row >= 1 && col >= 1) {
//			           fieldHelp.showUrl(helpUrls.get(col-1));
//			        }
//			        else if(row ==0 && col ==0){
//			        	fieldHelp.showUrl(helpUrls.get(0));
//			        }
//		      }
//		    });
//		
////		tableEntry.addMouseListener(new java.awt.event.MouseAdapter() {
////		    @Override
////		    public void mouseClicked(java.awt.event.MouseEvent evt) {
////		        int row = tableEntry.rowAtPoint(evt.getPoint());
////		        int col = tableEntry.columnAtPoint(evt.getPoint());
////		        if (row >= 1 && col >= 1) {
////		           fieldHelp.showUrl(helpUrls.get(col-1));
////		        }
////		        else if(row ==0 && col ==0){
////		        	fieldHelp.showUrl(helpUrls.get(0));
////		        }
////		    }
////		});
	}
	
	public void clearFormEntry(){
		formEntry.clear();	
		listModel.clear();
		revalidate();
	}
	
	public void setFormEntry(ArrayList<String> fieldTitles, int numRecords){
		
		//for(int x=0; x<fieldTitles.size(); x++){
			formEntry.add(formEntry, fieldTitles, numRecords);
		//}
		
			for(int x=0; x<numRecords; x++){
				listModel.addElement((x+1) + "");
			}
			
			ListSelectionModel listSelectionModel = list.getSelectionModel();
			listSelectionModel.addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
					if(formEntry.getCurrentRecord()!=list.getSelectedIndex())
						formEntry.setCurrentRecord(list.getSelectedIndex());
					
				}
				
			});
	}
	
	public void drawCell(int x, int y, int width, int height, int record, int field){
		component.drawCell(x, y, width, height, record, field);
	}
	
	public void toggleHighlights(){
		component.toggleHighlights();
	}
	

	public void clearImage(){
		component.clearImage();
	}
	
	public void setImage(String url){
		
		component.setImage(url);
		//componentPanel.add(component, BorderLayout.CENTER);
		revalidate();
		
	}
	
	public void invertImage(String url){
		imageComponent.invertImage(url, imageComponent);
	}

	public void invertImage() {
		component.invertImage();
	}
	
	public void createMenuBar(IndexingFrame frame){
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		
		JMenuItem downloadBatchMenuItem = new JMenuItem("Download Batch");
		downloadBatchMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==downloadBatchMenuItem){
					
					DownloadBatchDialog downloadBatchDialog = new DownloadBatchDialog(frame, "Download Batch", batchState);
					downloadBatchDialog.setVisible(true);
		
				}
				
			}
			
		});
		
		JMenuItem logoutMenuItem = new JMenuItem("Logout");
		logoutMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==logoutMenuItem){
					
					batchState.setZoomLevel(component.getScale());
					batchState.setHighlightsVisible(component.getToggleHighlights());
					batchState.setImageInverted(component.getInverted());
					batchState.setDesktopLocation(indexingFrame.getLocation());
					batchState.setDesktopSize(indexingFrame.getSize());
					batchState.setVerticalSplit(indexingFrame.getVerticalDivider());
					batchState.setHorizontalSplit(indexingFrame.getHorizontalDivider());
					
					try {
						XStream xStream = new XStream(new DomDriver());
					
						OutputStream outFile;
						
						outFile = new BufferedOutputStream(new FileOutputStream("xstream.xml"));
						xStream.toXML(batchState, outFile);
						outFile.close();
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					frame.setVisible(false);
					parent.showLogin(batchState);
		
				}
				
			}
			
		});
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==logoutMenuItem){
					
					batchState.setZoomLevel(component.getScale());
					batchState.setHighlightsVisible(component.getToggleHighlights());
					batchState.setImageInverted(component.getInverted());
					batchState.setDesktopLocation(indexingFrame.getLocation());
					batchState.setDesktopSize(indexingFrame.getSize());
					batchState.setVerticalSplit(verticalSplitPane.getDividerLocation());
					batchState.setHorizontalSplit(horizontalSplitPane.getDividerLocation());
					
					try {
						XStream xStream = new XStream(new DomDriver());
					
						OutputStream outFile;
						
						outFile = new BufferedOutputStream(new FileOutputStream("xstream.xml"));
						xStream.toXML(batchState, outFile);
						outFile.close();
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					System.exit(0);
				}
				
			}
			
		});
		menu.add(downloadBatchMenuItem);
		menu.add(logoutMenuItem);
		menu.add(exitMenuItem);
		frame.setJMenuBar(menuBar);
	}
	
	public double getScale(){
		return component.getScale();
	}
	
	public void setScale(double newScale){
		component.setScale(newScale);
	}
	
	public boolean getToggleHighlights(){
		return component.getToggleHighlights();
	}
	
	public void setToggleHighlights(boolean highlights){
		component.setToggleHighlights(highlights);
	}
	
	public boolean getInverted(){
		return component.getInverted();
	}
	
	public void setInverted(boolean inverted){
		component.setInverted(inverted);
	}
	
	public int getVerticalDivider(){
		int location = verticalSplitPane.getSize().height - verticalSplitPane.getInsets().bottom - verticalSplitPane.getDividerSize() - verticalSplitPane.getBottomComponent().getHeight();
		double height = indexingFrame.getHeight();
		double proportionalLocation = location/height;
//		verticalSplitPane.setDividerLocation(proportionalLocation);
//		double check = verticalSplitPane.getDiv
		return location;
	}
	
	public void setVerticalDivider(int divider){
		verticalSplitPane.setDividerLocation(divider);
	}
	
	public int getHorizontalDivider(){
		int location = horizontalSplitPane.getSize().width - horizontalSplitPane.getInsets().left - horizontalSplitPane.getDividerSize() - horizontalSplitPane.getRightComponent().getWidth();
		double width = indexingFrame.getWidth();
		double proportionalLocation = location/width;
		//horizontalSplitPane.setDividerLocation(proportionalLocation);
		return location;
	}
	
	public void setHorizontalDivider(int divider){
		horizontalSplitPane.setDividerLocation(divider);
	}
	
	private ChangeListener sliderChangeListener = new ChangeListener() {
		
		@Override
		public void stateChanged(ChangeEvent e) {
			component.setScale(slider.getValue() * 0.05);
		}
	};
	
	public void zoomIn() {
		component.setScale(component.getScale()*1.05);
	}
	
	public void zoomOut() {
		component.setScale(component.getScale()*.95);
	}
	
}