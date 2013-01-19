package appletUI;

import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 * This class is for the Console User Interface. It calls into the core accessor which returns
 * 		an array list of strings as the result. The program then prints those
 * 		strings to the console. 
 * 
 * @author Zach DeStefano
 *
 */
public class AppletLauncher {
	
	/**
	 * main method for console execution
	 * @param args
	 */
	public static void main(String[] args){
		//Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Hello World");
        frame.add(label);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

	}
	

}
