package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import shared.communication.*;

import javax.swing.*;
 
public class FieldHelp extends JEditorPane implements ActionListener, BatchListener {

	private BatchState batchState;
	private FieldHelp fieldHelp;
	private JEditorPane editorPane;
	
	public FieldHelp(BatchState bs) {
		
		this.batchState = bs;
		batchState.addToList(this);
		editorPane = this;
		this.setEditable(false);
		this.setSize(30, 40);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cellChanged() {
		// TODO Auto-generated method stub
		
	}
	
	public void showUrl(String url){
		if(!url.equals("")){
		java.net.URL helpURL = null;
		
		try {
			helpURL = new URL(url);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (helpURL != null) {
			try {
				editorPane.setContentType("text/html");
				editorPane.setPage(helpURL);
			} catch (IOException e) {
				System.err.println("Attempted to read a bad URL: " + helpURL);
			}
		} else {
			System.err.println("Couldn't find file: TextSamplerDemoHelp.html");
		}
		}
	}
	
	public void clearUrl(){
		editorPane.setText("");
	}
	
	
	
	
}