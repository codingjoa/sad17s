package sys.ui;

import javax.swing.JFrame;

public class MainUI extends JFrame{
	
	MainUI()
	{
		// ui셋업
		new GUIMain();
	}
	
	public static void main(String[] args)
	{
		new MainUI();
		//new sys.logic.DBinaryTree();
		
	}
}
