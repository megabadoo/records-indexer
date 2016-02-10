package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.RescaleOp;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import shared.communication.*;

import javax.swing.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import server.database.DatabaseException;
import java.util.*;
import java.util.List;
 
public class ButtonBar extends JPanel implements ActionListener {
	
	private BatchState batchState;

	public ButtonBar(IndexingFrame parent, BatchState bs) {
		
		this.batchState = bs;
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JButton zoomIn = new JButton("Zoom In");
		zoomIn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==zoomIn){
					parent.zoomIn();
				}
				
			}
			
		});
		
		JButton zoomOut = new JButton("Zoom Out");
		zoomOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==zoomOut){
					parent.zoomOut();
				}
			}
			
		});
		
		JButton invertImage = new JButton("Invert Image");
		invertImage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==invertImage){
					//parent.invertImage(batchState.getImageUrl());
					parent.invertImage();
				}
				
			}
			
		});
		
		JButton toggleHighlights = new JButton("Toggle Highlights");
		toggleHighlights.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==toggleHighlights){
					//parent.invertImage(batchState.getImageUrl());
					parent.toggleHighlights();
				}
			}
			
		});
		
		JButton save = new JButton("Save");
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				batchState.setZoomLevel(parent.getScale());
				batchState.setHighlightsVisible(parent.getToggleHighlights());
				batchState.setImageInverted(parent.getInverted());
				batchState.setDesktopLocation(parent.getLocation());
				batchState.setDesktopSize(parent.getSize());
				int split = parent.getVerticalDivider();
				batchState.setVerticalSplit(split);
				split = parent.getHorizontalDivider();
				batchState.setHorizontalSplit(split);
				
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
				
			}
			
		});
		
		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==submit){
					parent.clearTable();
					parent.clearImage();
					parent.clearFormEntry();
					parent.clearFieldHelp();
					
					String username = batchState.getUsername();
					String password = batchState.getPassword();
				
					SubmitBatch_Params params = null;
				
					try {
						params = new SubmitBatch_Params();
						params.setUser(username);
						params.setPassword(password);
						params.setBatch(batchState.getBatchId());
						List<List<String>> values = new ArrayList<List<String>>();
						batchState.setValues(values);
						params.setField_values(batchState.getValues());
					
					} catch (DatabaseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
				
					ClientFacade clientFacade = new ClientFacade();
					SubmitBatch_Result result = clientFacade.submitBatch(params);
					if(result.isValid()){
						
					}
				}
				
			}
			
		});
		
		this.add(zoomIn);
		this.add(zoomOut);
		this.add(invertImage);
		this.add(toggleHighlights);
		this.add(save);
		this.add(submit);
		this.add(Box.createHorizontalGlue());
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}
	
}