package imageviewer_ex;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.io.File;

import javax.swing.*;

public class ImageViewer extends JFrame {
	
	JButton btn_prev = new JButton("이전");
	JButton btn_next = new JButton("다음");
	JTextArea text = new JTextArea();
	ImageIcon[] image = new ImageIcon[4];
	ImageIcon openImage = null;
	JLabel imageLabel;
	int cnt;
	
	File folder;
	File[] files;
	
	void setFolder(String folder_name)
	{
		folder = new File(folder_name);
		if(!folder.isDirectory()) return;
		files = folder.listFiles();
	}
	
	int getFileLength()
	{
		
		return files.length;
	}
	
	void imgOpen(int i)
	{
		openImage = new ImageIcon(folder.getName()+"/"+files[i].getName());
		//openImage.
	}
	
	void refresh()
	{
		imageLabel.setIcon(openImage);
		text.setText(files[cnt].getName());
	}
	
	ImageViewer()
	{
		setTitle("이미지 뷰어");
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		
		// 이미지 로딩
		
		folder = new File("images");
		System.out.println(folder.isDirectory());
		files = folder.listFiles();
		System.out.println(files.length);
		openImage = new ImageIcon("images/" + files[0].getName());
		Image obi = openImage.getImage();
		ImageObserver ob = openImage.getImageObserver();
		ob.imageUpdate(obi, ABORT, 0, 0, 300, 300);
		text.setText(files[0].getName());
		text.setFont(new Font("나눔고딕", 16, 20));
		text.setEditable(false);
		/*
		for(int i=0; i<image.length; i++)
		{
			image[i] = new ImageIcon("images/image" + (i+1) + ".png");
		}
		*/
		//image[0].
		
		
		cnt=0;
		//imageLabel = new JLabel(image[cnt]);
		imageLabel = new JLabel(openImage);
		
		btn_prev.addActionListener(new MyListener());
		btn_next.addActionListener(new MyListener());
		
		add(imageLabel, BorderLayout.CENTER);
		panel.add(btn_prev);
		panel.add(btn_next);
		panel.add(text);
		add(panel, BorderLayout.SOUTH);
		setSize(500, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// 2 리스너 등록
	}
	
	// 1 리스너 정의
	class MyListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == btn_prev)
			{
				//if(cnt==0) cnt = image.length-1;
				if(cnt==0) cnt = files.length-1;
				else cnt--;
				//imageLabel.setIcon(image[cnt]);
			}
			else if(e.getSource() == btn_next)
			{
				if(cnt == files.length-1) cnt = 0;
				else cnt++;
				//imageLabel.setIcon(image[cnt]);
			}
			openImage = new ImageIcon("images/" + files[cnt].getName());
			text.setText(files[cnt].getName());
			imageLabel.setIcon(openImage);
			
		}
		
	}
	
	public static void main(String[] args)
	{
		new ImageViewer();
	}
	
}
