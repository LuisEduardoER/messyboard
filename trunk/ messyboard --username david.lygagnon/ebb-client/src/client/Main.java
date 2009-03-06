package client;

import javax.swing.JFrame;

public class Main {
	
	/**
	 * @param args	registry name
	 */
	public static void main(String[] args) {
		MainGUI gui;		//GUI
		EbbClient client;	//Client application
		
		/* Verify that client runs with 1 parameter */
		if (args.length != 1) {
            System.out.println("Usage: java " + Main.class + " <registry name>");
            return;
        }
		
		client = new EbbClient(args[0]);
		gui = new MainGUI(client);
		gui.setVisible(true);
		
		
	}
}
