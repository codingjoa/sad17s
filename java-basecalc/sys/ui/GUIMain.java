package sys.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class GUIMain extends JFrame {
	
	GUIMain()
	{
		this.setTitle("Project");
		this.setSize(640, 220);
		this.setLocation(100, 100);
		BorderLayout layout_manager = new BorderLayout();
		this.setLayout(layout_manager);
		this.setResizable(false);
		
		//this.add(new Pn_MenuBar(), BorderLayout.NORTH);
		this.add(new Pn_Editor(), BorderLayout.CENTER);
		//this.add(new Pn_Tree(), BorderLayout.WEST);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		
	}
}
