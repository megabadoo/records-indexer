package views;

import java.awt.*;

import shared.model.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import shared.communication.*;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
 
public class DownloadBatchDialog extends JDialog implements ActionListener {
	
	private BatchState batchState;
	
	public DownloadBatchDialog (IndexingFrame parent, String title, BatchState bs) {
		// Set the window's title
		//this.setTitle("Login to Indexer");
		super(parent, title, true);
		
		this.batchState = bs;
		
		// Specify what should happen when the user clicks the window's close icon
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		Container contentPane = this.getContentPane();
		
		ClientFacade clientFacade = new ClientFacade();
		String username = batchState.getUsername();
		String password = batchState.getPassword();
		GetProjects_Result result = clientFacade.getProjects(username, password);
		List<String> projectNames = new ArrayList<String>();
		for(int x=0; x<result.getProjects().size(); x++){
			projectNames.add(result.getProjects().get(x).getTitle());
		}
		String [] projectList = projectNames.toArray(new String[projectNames.size()]);
		
		JLabel projectsLabel = new JLabel("Project: ");
		JComboBox projectDropDown = new JComboBox(projectList);
		JButton viewSampleButton = new JButton("View Sample");
		viewSampleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==viewSampleButton){
					String username = batchState.getUsername();
					String password = batchState.getPassword();
					int projectId = result.getProjects().get(projectDropDown.getSelectedIndex()).getId();
					ViewSampleImageDialog viewSampleImageDialog = new ViewSampleImageDialog(parent, "View Sample", batchState, projectId);
					viewSampleImageDialog.setVisible(true);
				}
				
			}
			
		});
		
		JPanel projectsPane = new JPanel();
		projectsPane.setLayout(new BoxLayout(projectsPane, BoxLayout.LINE_AXIS));
		projectsPane.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		projectsPane.add(projectsLabel);
		projectsPane.add(Box.createRigidArea(new Dimension(10, 0)));
		projectsPane.add(projectDropDown);
		projectsPane.add(Box.createRigidArea(new Dimension(10, 0)));
		projectsPane.add(viewSampleButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> this.dispose());
		
		JButton downloadButton = new JButton("Download");
		downloadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==downloadButton){
					dispose();
					String username = batchState.getUsername();
					String password = batchState.getPassword();
					int projectId = result.getProjects().get(projectDropDown.getSelectedIndex()).getId();
					
					ClientFacade clientFacade = new ClientFacade();
					DownloadBatch_Result result = clientFacade.downloadBatch(username, password, projectId);
					if(result.isValid()){
						
						batchState.setBatchId(result.getBatch_id());
						
						//set up image component
						String imageUrl = result.getUrl_header()  + "/" + result.getImage_url();
						batchState.setImageUrl(imageUrl);
						parent.setImage(imageUrl);
						
						
						//set up table entry
						ArrayList<String> columnNames = new ArrayList<String>();
						columnNames.add("Record Number");
						for(int x=0; x<result.getNum_fields(); x++){
							columnNames.add(result.getFields().get(x).getTitle());
						}
						
						ArrayList<String> rowValues = new ArrayList<String>();
						for(int y=0; y<result.getNum_records(); y++){
							rowValues.add("");
						}

						Object[][] tableValues = new Object[columnNames.size()][rowValues.size()];
						for(int x=0; x<columnNames.size(); x++){
							tableValues[x] = rowValues.toArray();
						}
						
						for(int x=0; x<result.getNum_records(); x++){
							tableValues[0][x] = (x+1) + "";
						}
						
						//set up field help
						ArrayList<String> helpUrls = new ArrayList<String>();
						for(Field f: result.getFields()){
							if(f.getHelphtml()==null)
								helpUrls.add("");
							else
								helpUrls.add(result.getUrl_header() + "/" + f.getHelphtml());
						}
						parent.setFieldHelp(helpUrls);
						
						parent.setTable(tableValues, (Object[]) columnNames.toArray());
						
						//set up form entry form
						parent.setFormEntry(columnNames, result.getNum_records());
						
						
						
						
						//draw cells on image
						for(int j=0; j<result.getNum_records(); j++){
							int height = result.getRecord_height();
							int y = result.getFirst_y_coord() + j*result.getRecord_height();
							for(int i=0; i<result.getFields().size(); i++){
								int x = result.getFields().get(i).getXcoord();
								
								int width = result.getFields().get(i).getWidth();
								
							
								parent.drawCell(x, y, width, height, j, i);
							}
						}
					}
					else {
						JOptionPane.showMessageDialog(contentPane, "Unable to download batch. \nSubmit current batch and try again.", "Error", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				
			}
			
		});

		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(cancelButton);
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(downloadButton);
		buttonPane.add(Box.createHorizontalGlue());
		
		
		
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		contentPane.add(projectsPane);
		contentPane.add(buttonPane);

		
		// Set the location of the window on the desktop
		this.setLocation(500, 300);

		// Size the window according to the preferred sizes and layout of its subcomponents
		this.pack();
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}