package views;

import java.awt.*;

import java.io.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import shared.communication.*;

import javax.swing.*;
 
public class LoginDialog extends JDialog implements ActionListener {
	
	private BatchState batchState;
	LoginDialog loginDialog;
	
	public LoginDialog (IndexingFrame parent, String title, BatchState bs) {	
		
		// Set the window's title
		//this.setTitle("Login to Indexer");
		super(parent, title, true);
		
		this.loginDialog = this;
		this.batchState = bs;
		// Specify what should happen when the user clicks the window's close icon
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		Container contentPane = this.getContentPane();
		
		JLabel usernameLabel = new JLabel("Username: ");
		JTextField usernameField = new JTextField(20);
		
		JPanel usernamePane = new JPanel();
		usernamePane.setLayout(new BoxLayout(usernamePane, BoxLayout.LINE_AXIS));
		usernamePane.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		usernamePane.add(usernameLabel);
		usernamePane.add(Box.createRigidArea(new Dimension(10, 0)));
		usernamePane.add(usernameField);
		
		JLabel passwordLabel = new JLabel("Password: ");
		JPasswordField passwordField = new JPasswordField(20);
		
		JPanel passwordPane = new JPanel();
		passwordPane.setLayout(new BoxLayout(passwordPane, BoxLayout.LINE_AXIS));
		passwordPane.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		passwordPane.add(passwordLabel);
		passwordPane.add(Box.createRigidArea(new Dimension(10, 0)));
		passwordPane.add(passwordField);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==loginButton){
					String username = usernameField.getText();
					String password = passwordField.getText();
					
					ClientFacade clientFacade = new ClientFacade();
					ValidateUser_Result result = clientFacade.validateUser(username, password);
					if(result.isValid()){
						String str = "Welcome, " + result.getUser_first_name() + " " + result.getUser_last_name() + ".\nYou have indexed " + result.getNum_records() + " records.";
						JOptionPane.showMessageDialog(contentPane, str, "Welcome to Indexing", JOptionPane.INFORMATION_MESSAGE);
						Frames main = new Frames();
						main.validUser();
						try {
							
							XStream xStream = new XStream(new DomDriver());
						
							InputStream inFile;	
							inFile = new BufferedInputStream(new FileInputStream("xstream.xml"));
							BatchState bs = (BatchState)xStream.fromXML(inFile);
							batchState.setUsername(bs.getUsername());
							batchState.setPassword(bs.getPassword());
							batchState.setZoomLevel(bs.getZoomLevel());
							batchState.setHighlightsVisible(bs.isHighlightsVisible());
							batchState.setImageInverted(bs.isImageInverted());
							batchState.setDesktopLocation(bs.getDesktopLocation());
							batchState.setDesktopSize(bs.getDesktopSize());
							batchState.setVerticalSplit(bs.getVerticalSplit());
							batchState.setHorizontalSplit(bs.getHorizontalSplit());
							batchState.setImageUrl(bs.getImageUrl());
							
							
							inFile.close();
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						if(batchState.getUsername()==null){
							
							batchState.setUsername(username);
							batchState.setPassword(password);
							
						} 
						if(batchState.getZoomLevel()!=0){
							
							batchState.setUsername(username);
							String check = batchState.getUsername();
							check = check;
							batchState.setPassword(password);
							parent.setScale(batchState.getZoomLevel());
							parent.setToggleHighlights(batchState.isHighlightsVisible());
							parent.setInverted(batchState.isImageInverted());
							parent.setLocation(batchState.getDesktopLocation());
							parent.setSize(batchState.getDesktopSize());
							int split = batchState.getVerticalSplit();
							parent.setVerticalDivider(split);
							int splitHorizontal = batchState.getHorizontalSplit();
							parent.setHorizontalDivider(splitHorizontal);
							parent.revalidate();
							String url = batchState.getImageUrl();
							if(url!=null)
								parent.setImage(url);		
							
						}
					}
					else {
						JOptionPane.showMessageDialog(contentPane, "Invalid username and/or password", "Login Failed", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				
			}
			
		});
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(e -> System.exit(0));
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(loginButton);
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(exitButton);
		buttonPane.add(Box.createHorizontalGlue());
		
		
		
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		contentPane.add(usernamePane);
		contentPane.add(passwordPane);
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