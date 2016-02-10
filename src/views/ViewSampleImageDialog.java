package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import shared.communication.*;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
 
public class ViewSampleImageDialog extends JDialog implements ActionListener {
	
	private BatchState batchState;
	
	public ViewSampleImageDialog (IndexingFrame parent, String title, BatchState bs, int projectId) {
		// Set the window's title
		super(parent, title, true);
		
		this.batchState = bs;
		
		// Specify what should happen when the user clicks the window's close icon
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		Container contentPane = this.getContentPane();
		
		ClientFacade clientFacade = new ClientFacade();
		String username = batchState.getUsername();
		String password = batchState.getPassword();
		GetSampleImage_Result result = clientFacade.getSampleImage(username, password, projectId);
		
		Image batch = null;
		try {
			batch = ImageIO.read(new URL(result.getUrl_header() + "/" + result.getImage_url()));
			batch = batch.getScaledInstance(batch.getWidth(null) / 2, batch.getHeight(null) / 2, Image.SCALE_DEFAULT);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JLabel picLabel = new JLabel(new ImageIcon(batch));
		picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(e -> this.dispose());
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(closeButton);
		buttonPane.add(Box.createHorizontalGlue());
		
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		contentPane.add(picLabel);
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