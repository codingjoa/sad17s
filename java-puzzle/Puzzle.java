
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Puzzle extends JFrame{

	private InputBox inputbox = new InputBox();
	private OutputBox outputbox = new OutputBox();
	private OutputBox countbox = new OutputBox();
	private OutputBox helpbox = new OutputBox(new Font("나눔고딕", Font.PLAIN, 18));
	private InputList table = new InputList();
	private Button inputbutton = new Button("입력", new InputAction());
	private Button resetbutton = new Button("리셋", new ResetAction());
	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JPanel panel3 = new JPanel();
	private JPanel panel4 = new JPanel();
	private JPanel panel5 = new JPanel();
	private JPanel panel6 = new JPanel();
	private PuzzleLogic logic = new PuzzleLogic();
	private boolean end = false;
	private boolean debug = false;

	Puzzle()
	{
		helpbox.setText("게임방법 : 컴퓨터가가 생각하는\n0부터 1000 사이의 숫자를 맞춰보세요!\n제한횟수는 단 10회!");
		panel1.add(inputbox);
		panel2.add(outputbox);
		panel3.add(countbox);
		//panel4.add(helpbox);
		panel5.add(table);
		panel6.add(inputbutton);
		panel6.add(resetbutton);
		this.add(panel1);
		this.add(panel2);
		this.add(panel3);
		this.add(panel4);
		this.add(panel5);
		this.add(panel6);
		this.setLayout(new FlowLayout());
		this.setSize(300, 500);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}


	private class InputBox extends JTextField
	{
		InputBox()
		{
			this.setText("");
			this.setFont(new Font("나눔고딕", Font.PLAIN, 24));
			this.setColumns(10);
			this.addKeyListener(new InputAction());
		}
	}

	private class OutputBox extends JLabel
	{
		OutputBox()
		{
			this.setFont(new Font("나눔고딕", Font.BOLD, 24));
		}

		OutputBox(Font f)
		{
			this.setFont(f);
		}

	}

	private class InputList extends JTable
	{
		private Table table;
		InputList()
		{
			table = new Table();
			this.setModel(table);
			this.setEnabled(false);
		}
		public void reset()
		{
			for(int i=0; i<10; i++)
			{
				table.setValueAt("", i, 0);
				table.setValueAt("", i, 1);
				table.setValueAt("", i, 2);
			}
		}

		private class Table extends DefaultTableModel
		{
			Table()
			{
				this.setColumnCount(3);
				this.setRowCount(10);
				//this.setValueAt("a", 0, 0);
			}
		}
	}

	private class Button extends JButton
	{
		Button(String text, ActionListener action)
		{
			this.setText(text);
			this.addActionListener(action);
		}
	}

	private class PuzzleLogic
	{
		private int value = 0; // 정답을 담는 변수
		private int count = 0; // 정답 제출 횟수

		PuzzleLogic()
		{
			reset();
		}

		public void submit(int value)
		{
			if(this.value == value)
			{
				// 정답입니다 :D
				outputbox.setText("정답입니다 :D");
				countbox.setText(count+1 + "회 만에 성공!");
				table.setValueAt((count+1)+"회", count, 0);
				table.setValueAt(value, count, 1);
				table.setValueAt("성공", count, 2);
				end = true;
			}
			else
			{
				if(value > 1000)
				{
					outputbox.setText("0~1000 사이로 입력하세요.");
				}
				else if(count < 10)
				{
					// 다시시도 :D
					if(this.value > value)
					{
						outputbox.setText("값이 답보다 작습니다."); // 입력값보다 답이 커요
						table.setValueAt((count+1)+"회", count, 0);
						table.setValueAt(value, count, 1);
						table.setValueAt("작음", count, 2);
					}
					else if(this.value < value)
					{
						outputbox.setText("값이 답보다 큽니다."); // 입력값보다 답이 작아요
						table.setValueAt((count+1)+"회", count, 0);
						table.setValueAt(value, count, 1);
						table.setValueAt("큼", count, 2);
					}
					count++;
					countbox.setText(count + "회 /" + "10회");
					if(count >= 10)
					{
						// 오답입니다 :(
						outputbox.setText("모두 오답이에요...");
						end = true;
					}
				}
			}


		}

		public void reset()
		{
			Random r = new Random();
			value = r.nextInt(1000);
			count = 0;
			outputbox.setText("즐거운 숫자 맞추기!");
			countbox.setText(count + "회 /" + "10회");
			end = false;
			if(debug) System.out.println("정답: " + value);
		}

	}

	private class InputAction implements ActionListener, KeyListener
	{

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				action();
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			action();
		}

		private void action()
		{
			if(end) return;
			try
			{
				String str = inputbox.getText();
				int num = Integer.parseInt(str);
				logic.submit(num);
			}
			catch(NumberFormatException error)
			{
				error.printStackTrace();
				outputbox.setText("숫자만 입력해주세요.");
			}

		}

	}

	private class ResetAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			logic.reset();
			table.reset();
		}

	}

	public static void main(String[] args)
	{
		new Puzzle();
	}
}
