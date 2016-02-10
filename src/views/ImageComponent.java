package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import shared.communication.*;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.swing.*;
 
public class ImageComponent extends JPanel implements ActionListener {
	
	private JLabel picLabel;
	
	public ImageComponent(){
		this.setBackground(Color.GRAY);
		picLabel = null;
	}
	
	public void setImage(String imageUrl, ImageComponent parent){
		
		Image batch = null;
		try {
			batch = ImageIO.read(new URL(imageUrl));
			batch = batch.getScaledInstance(batch.getWidth(null) / 2, batch.getHeight(null) / 2, Image.SCALE_DEFAULT);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		picLabel = new JLabel(new ImageIcon(batch));
		picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	
		picLabel.setVisible(true);
		parent.add(picLabel);
		revalidate();
	}
	
	public void invertImage(String imageUrl, ImageComponent parent){
		
		BufferedImage batch = null;
		try {
			batch = ImageIO.read(new URL(imageUrl));
			//batch = JAI.create("url", new URL(imageUrl)).getAsBufferedImage();
			//batch = batch.getScaledInstance(batch.getWidth(null) / 2, batch.getHeight(null) / 2, Image.SCALE_DEFAULT);
			RescaleOp op = new RescaleOp(-1.0f, 255f, null);
			batch = op.filter(batch, null);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
		e1.printStackTrace();
		}
		picLabel = new JLabel(new ImageIcon(batch));
		picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		picLabel.setVisible(true);
		//parent.add(picLabel);
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}