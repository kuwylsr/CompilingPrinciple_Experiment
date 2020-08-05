package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class SATable extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public SATable(String table) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, screenSize.width, screenSize.height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 1870, 1006);
		contentPane.add(scrollPane);
		
		JTextArea SATabletextArea = new JTextArea();
		SATabletextArea.setEditable(false);
		SATabletextArea.setFont(new Font("Monospaced", Font.BOLD, 17));
		scrollPane.setViewportView(SATabletextArea);
		
		SATabletextArea.setText(table);
//		//自动化页面大小--全屏
//		int fraWidth = this.getWidth();//frame的宽
//		int fraHeight = this.getHeight();//frame的高
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		int screenWidth = screenSize.width;
//		int screenHeight = screenSize.height;
//		this.setSize(screenWidth, screenHeight);
//		this.setLocation(0, 0);
//		float proportionW = screenWidth/fraWidth;
//		float proportionH = screenHeight/fraHeight;
//		
//		SATable.modifyComponentSize(this, proportionW,proportionH);
//		this.toFront();
	}
	
	/**
	 * frame中的控件自适应frame大小：改变大小位置和字体
	 * @param frame 要控制的窗体
	 * @param proportion 当前和原始的比例
	 */
	public static void modifyComponentSize(JFrame frame,float proportionW,float proportionH){
		
		try 
		{
			Component[] components = frame.getRootPane().getContentPane().getComponents();
			for(Component co:components)
			{
//				String a = co.getClass().getName();//获取类型名称
//				if(a.equals("javax.swing.JLabel"))
//				{
//				}
				float locX = co.getX() * proportionW;
				float locY = co.getY() * proportionH;
				float width = co.getWidth() * proportionW;
				float height = co.getHeight() * proportionH;
				co.setLocation((int)locX, (int)locY);
				co.setSize((int)width, (int)height);
				int size = (int)(co.getFont().getSize() * proportionH);
				Font font = new Font(co.getFont().getFontName(), co.getFont().getStyle(), size);
				co.setFont(font);
			}
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
		}
	}

}
