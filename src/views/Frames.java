package views;

import java.awt.EventQueue;


public class Frames {

	private static LoginDialog login;
	private static IndexingFrame frame;
	
	public Frames() {
		
	}
	
	public static void main(String[] args) {

		/*
			In Swing, all user interface operations must occur on the �UI thread�.
			All components should be created on the UI thread.
			All method calls on UI components should happen on the UI thread.
			EventQueue.invokeLater runs the specified code on the UI thread.
			The main method for Swing programs should call EventQueue.invokeLater to create the UI.
			The main thread exits immediately after calling EventQueue.invokeLater, 
				but the UI thread keeps the program running.
		 */
		EventQueue.invokeLater(new Runnable() {		
			public void run() {
				
				BatchState batchState = new BatchState();
				// Create the frame window object
				Frames main = new Frames();
				frame = new IndexingFrame(main, batchState);
				login = new LoginDialog(frame, "Login to Indexer", batchState);
				
				// Make the frame window visible
				frame.setVisible(false);
				login.setVisible(true);
				
			}
		});

	}
	
	public void validUser() {
		login.setVisible(false);
		frame.setVisible(true);
	}
	
	public void showLogin(BatchState batchState) {
		login = new LoginDialog(frame, "Login to Indexer", batchState);
		login.setVisible(true);
	}

}