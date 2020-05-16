package sys.ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Pn_Editor extends JPanel {
	
	private boolean autorun = true;
	private final int DEC = 0;
	private final int BIN = 1;
	private final int HEX = 2;
	private int type = DEC;
	private InputBox input = new InputBox();
	private OutputBox output = new OutputBox();
	private OutputBox output_hex = new OutputBox();
	private OutputBox output_bin = new OutputBox();
	private Button button_process = new Button("실행", "진법 변환 수행합니다.", new BtnEvent());
	private Button button_end = new Button("종료", "프로그램을 종료합니다.", new Exit());
	private CheckButton check_auto = new CheckButton();
	private RadioButtonGroup check_type = new RadioButtonGroup();
	private JPanel line1 = new JPanel();
	private JPanel line2 = new JPanel();
	private JPanel line3 = new JPanel();
	private JPanel line4 = new JPanel();
	private JPanel line5 = new JPanel();
	
	Pn_Editor()
	{
		line1.add(input);
		line2.add(button_process);
		line2.add(button_end);
		line2.add(check_auto);
		line3.add(new JLabel("10진법"));
		line3.add(output);
		line4.add(new JLabel("16진법"));
		line4.add(output_hex);
		line5.add(new JLabel("2진법"));
		line5.add(output_bin);
		this.add(check_type);
		this.add(line1);
		this.add(line2);
		this.add(line3);
		this.add(line4);
		this.add(line5);
	}
	
	private class InputBox extends JTextField
	{
		InputBox()
		{
			setColumns(10);
			this.setFont(new Font("나눔고딕", Font.BOLD, 20));
			this.addKeyListener(new BtnEvent());
		}
	}
	
	private class OutputBox extends JTextField
	{
		OutputBox()
		{
			this.setEditable(false);
			this.setColumns(25);
			this.setFont(new Font("나눔고딕", 20, 18));
		}
	}
	
	private class Button extends JButton
	{
		Button(String text, String method, ActionListener action)
		{
			this.setText(text);
			this.setEnabled(true);
			this.addActionListener(action);
			this.setToolTipText(method);
		}
	}
	
	private class RadioButtonGroup extends JPanel
	{
		private JRadioButton[] btn = new JRadioButton[3];
		private Event ev = new Event();
		
		RadioButtonGroup()
		{
			btn[0] = new JRadioButton("10진");
			btn[0].setSelected(true);
			btn[0].addActionListener(ev);
			btn[1] = new JRadioButton("16진");
			btn[1].addActionListener(ev);
			btn[2] = new JRadioButton("2진");
			btn[2].addActionListener(ev);
			this.add(btn[0]);
			this.add(btn[1]);
			this.add(btn[2]);
		}
		
		private class Event implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource() == btn[0])
				{
					if(!btn[0].isSelected()) btn[0].setSelected(true);
					btn[1].setSelected(false);
					btn[2].setSelected(false);
					type = DEC;
				}
				if(e.getSource() == btn[1])
				{
					if(!btn[1].isSelected()) btn[1].setSelected(true);
					btn[0].setSelected(false);
					btn[2].setSelected(false);
					type = HEX;
				}
				if(e.getSource() == btn[2])
				{
					if(!btn[2].isSelected()) btn[2].setSelected(true);
					btn[0].setSelected(false);
					btn[1].setSelected(false);
					type = BIN;
				}
				if(autorun) Action();
			}
			
		}
	}
	
	private class CheckButton extends JCheckBox
	{
		CheckButton()
		{
			this.setText("자동변환");
			this.setSelected(true);
			this.addActionListener(new Event());
		}
		private class Event implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(autorun) autorun = false;
				else autorun = true;
			}
			
		}
	}
	
	private class BtnEvent implements ActionListener, KeyListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Action();
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			switch(e.getKeyCode())
			{
			case 48:
			case 49: if(type == BIN) break;
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57: if(type == DEC) break;
			case 65:
			case 66:
			case 67:
			case 68:
			case 69:
			case 70: if(type == HEX) break;
			default: return;
			}
			System.out.print(e.getKeyCode() + " ");
			if(e.getKeyCode() == 10) // enter
			{
				e.consume();
				Action();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			if(autorun)
			{
				Action();
			}
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private void Action()
	{
		try
		{
			if(input.getText() == "")
			{
				output.setText("0");
				output_bin.setText("0");
				output_hex.setText("0");
				return;
			}
			int input_value = 0;
			if(type == DEC)
				input_value = Integer.parseInt(input.getText());
			else if(type == BIN)
				input_value = sys.logic.DConvertor.bin(input.getText());
			else if(type == HEX)
				input_value = sys.logic.DConvertor.hex(input.getText()); 
			String hex = Integer.toHexString(input_value).toUpperCase();
			String bin = Integer.toBinaryString(input_value);
			String dem = Integer.toString(input_value);
			output.setText(dem);
			output_bin.setText(bin);
			output_hex.setText(hex);
		}
		catch(NumberFormatException error)
		{
			//error.printStackTrace();
		}
	}
	
	private class Exit implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);
		}
		
	}
	
}
