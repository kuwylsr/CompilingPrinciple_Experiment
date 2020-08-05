package GUI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import java.awt.Component;
import java.awt.EventQueue;
import javax.swing.Box;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class finalGUI extends JFrame {

	private JPanel contentPane;
    private JFileChooser chooser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;

					org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();

					UIManager.put("RootPane.setupButtonVisible", false);

				} catch (Exception e) {

					// TODO Auto-generated catch block

					e.printStackTrace();

				}
				try {
					finalGUI frame = new finalGUI();
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public finalGUI() {
		
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 20, 1362, 1000);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(60, 179, 113));
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);	
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setBounds(14, 316, 1316, 12);
		contentPane.add(horizontalStrut);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setBounds(14, 651, 1316, 12);
		contentPane.add(horizontalStrut_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 56, 290, 244);
		contentPane.add(scrollPane);
		
		JTextArea InputLAtextArea = new JTextArea();
		InputLAtextArea.setEditable(false);
		InputLAtextArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		scrollPane.setViewportView(InputLAtextArea);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(376, 56, 309, 244);
		contentPane.add(scrollPane_1);
		
		JTextArea tokenLAtextArea_1 = new JTextArea();
		tokenLAtextArea_1.setEditable(false);
		tokenLAtextArea_1.setFont(new Font("Monospaced", Font.PLAIN, 15));
		scrollPane_1.setViewportView(tokenLAtextArea_1);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(765, 56, 233, 247);
		contentPane.add(scrollPane_2);
		
		JTextArea errorLAtextArea_2 = new JTextArea();
		errorLAtextArea_2.setEditable(false);
		errorLAtextArea_2.setFont(new Font("Monospaced", Font.PLAIN, 15));
		scrollPane_2.setViewportView(errorLAtextArea_2);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(14, 376, 290, 262);
		contentPane.add(scrollPane_3);
		
		JTextArea grammerSAtextArea_3 = new JTextArea();
		grammerSAtextArea_3.setEditable(false);
		grammerSAtextArea_3.setFont(new Font("Monospaced", Font.PLAIN, 15));
		scrollPane_3.setViewportView(grammerSAtextArea_3);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(376, 376, 363, 262);
		contentPane.add(scrollPane_4);
		
		JTextArea SATableArea_4 = new JTextArea();
		SATableArea_4.setEditable(false);
		SATableArea_4.setFont(new Font("Monospaced", Font.PLAIN, 15));
		scrollPane_4.setViewportView(SATableArea_4);
		
		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(1089, 376, 207, 262);
		contentPane.add(scrollPane_5);
		
		JTextArea errorSAtextArea_5 = new JTextArea();
		errorSAtextArea_5.setEditable(false);
		errorSAtextArea_5.setFont(new Font("Monospaced", Font.PLAIN, 15));
		scrollPane_5.setViewportView(errorSAtextArea_5);
		
		
		
		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setBounds(14, 708, 290, 232);
		contentPane.add(scrollPane_6);
		
		JTextArea threeArea_6 = new JTextArea();
		threeArea_6.setFont(new Font("Monospaced", Font.PLAIN, 15));
		scrollPane_6.setViewportView(threeArea_6);
		
		JScrollPane scrollPane_7 = new JScrollPane();
		scrollPane_7.setBounds(376, 708, 363, 232);
		contentPane.add(scrollPane_7);
		
		JTextArea fourAddressArea_7 = new JTextArea();
		fourAddressArea_7.setFont(new Font("Monospaced", Font.PLAIN, 15));
		scrollPane_7.setViewportView(fourAddressArea_7);
		
		JScrollPane scrollPane_8 = new JScrollPane();
		scrollPane_8.setBounds(1083, 708, 213, 232);
		contentPane.add(scrollPane_8);
		
		JTextArea errorSematictextArea_8 = new JTextArea();
		scrollPane_8.setViewportView(errorSematictextArea_8);
		
		JScrollPane scrollPane_9 = new JScrollPane();
		scrollPane_9.setBounds(1051, 56, 233, 247);
		contentPane.add(scrollPane_9);
		
		JTextArea IDNtextArea = new JTextArea();
		IDNtextArea.setEditable(false);
		IDNtextArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		scrollPane_9.setViewportView(IDNtextArea);
		JButton LAButton = new JButton("词法分析");
		LAButton.setFont(new Font("宋体", Font.BOLD, 15));
		LAButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text = InputLAtextArea.getText();
				Map<String,String> LAResult = new Analysis().analysis(text);
				tokenLAtextArea_1.setText(LAResult.get("tokens"));
				errorLAtextArea_2.setText(LAResult.get("error"));
				IDNtextArea.setText(LAResult.get("IDN"));
			}
		});
		LAButton.setBounds(14, 13, 134, 33);
		contentPane.add(LAButton);
		
		chooser = new JFileChooser();
	    chooser.setCurrentDirectory(new File("."));
		JButton button = new JButton("输入源程序");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = chooser.showOpenDialog(null);
                if(result == JFileChooser.APPROVE_OPTION){
                    String name = chooser.getSelectedFile().getPath();
                    try {
						InputLAtextArea.setText(ReadFile(name));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
			}
		});
		button.setBounds(160, 19, 113, 27);
		contentPane.add(button);
		
		JScrollPane scrollPane_10 = new JScrollPane();
		scrollPane_10.setBounds(791, 376, 243, 262);
		contentPane.add(scrollPane_10);
		
		JTextArea productiontextArea = new JTextArea();
		productiontextArea.setEditable(false);
		productiontextArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		scrollPane_10.setViewportView(productiontextArea);
		JButton SAbutton = new JButton("语法分析");
		SAbutton.setFont(new Font("宋体", Font.BOLD, 15));
		SAbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					
					if(!errorLAtextArea_2.getText().equals("")) {
						LoginWarning dialog = new LoginWarning("词法分析出错！");
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setLocation(500, 500);
						dialog.setVisible(true);
					}else {
						ParseChart c = new ParseChart("src/GUI/semanticGrammer.txt");
						String output = tokenLAtextArea_1.getText();
						//Parser p = new Parser(c,output);
						SATableArea_4.setText(c.printParseChart());
						//Map<String,String> SAResult = p.parse();
						//productiontextArea.setText(SAResult.get("reduceProductions"));
						//errorSAtextArea_5.setText(SAResult.get("error"));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		SAbutton.setBounds(14, 330, 134, 33);
		contentPane.add(SAbutton);
		
		JButton button_2 = new JButton("输入文法");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = chooser.showOpenDialog(null);
                if(result == JFileChooser.APPROVE_OPTION){
                    String name = chooser.getSelectedFile().getPath();
                    try {
						grammerSAtextArea_3.setText(ReadFile(name));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
			}
		});
		button_2.setBounds(160, 336, 113, 27);
		contentPane.add(button_2);
		
		JLabel lblToken = new JLabel("token序列");
		lblToken.setFont(new Font("宋体", Font.BOLD, 17));
		lblToken.setBounds(492, 25, 97, 18);
		contentPane.add(lblToken);
		
		JLabel label_1 = new JLabel("错误词法信息");
		label_1.setFont(new Font("宋体", Font.BOLD, 17));
		label_1.setBounds(816, 25, 134, 18);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("符号表");
		label_2.setFont(new Font("宋体", Font.BOLD, 17));
		label_2.setBounds(1115, 28, 97, 18);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("语法分析表");
		label_3.setFont(new Font("宋体", Font.BOLD, 17));
		label_3.setBounds(492, 345, 97, 18);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("产生式");
		label_4.setFont(new Font("宋体", Font.BOLD, 17));
		label_4.setBounds(870, 345, 97, 18);
		contentPane.add(label_4);
		
		JLabel label_5 = new JLabel("语法分析错误");
		label_5.setFont(new Font("宋体", Font.BOLD, 17));
		label_5.setBounds(1115, 345, 119, 18);
		contentPane.add(label_5);
		
		JButton button_3 = new JButton("放大");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String table = SATableArea_4.getText();
				SATable frame = new SATable(table);
				frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		button_3.setBounds(616, 339, 69, 33);
		contentPane.add(button_3);
		
		JButton button_4 = new JButton("放大");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String production = productiontextArea.getText();
				SATable frame = new SATable(production);
				frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		button_4.setBounds(947, 341, 69, 33);
		contentPane.add(button_4);
		
		JButton button_5 = new JButton("放大");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String error = errorSAtextArea_5.getText();
				SATable frame = new SATable(error);
				frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		button_5.setBounds(1229, 341, 69, 33);
		contentPane.add(button_5);
		
		JButton button_6 = new JButton("放大");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String tokens = tokenLAtextArea_1.getText();
				SATable frame = new SATable(tokens);
				frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		button_6.setBounds(603, 16, 69, 33);
		contentPane.add(button_6);
		
		JButton button_7 = new JButton("放大");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String error = errorLAtextArea_2.getText();
				SATable frame = new SATable(error);
				frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		button_7.setBounds(929, 19, 69, 33);
		contentPane.add(button_7);
		
		JButton button_8 = new JButton("放大");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String idns = IDNtextArea.getText();
				SATable frame = new SATable(idns);
				frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		button_8.setBounds(1197, 19, 69, 33);
		contentPane.add(button_8);
		
		JLabel label = new JLabel("四元式");
		label.setFont(new Font("宋体", Font.BOLD, 17));
		label.setBounds(490, 677, 77, 18);
		contentPane.add(label);
		
		JButton button_10 = new JButton("放大");
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String four = fourAddressArea_7.getText();
				SATable frame = new SATable(four);
				frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		button_10.setBounds(581, 673, 69, 33);
		contentPane.add(button_10);
		
		JLabel label_6 = new JLabel("语义分析错误");
		label_6.setFont(new Font("宋体", Font.BOLD, 17));
		label_6.setBounds(1115, 677, 119, 18);
		contentPane.add(label_6);
		
		JButton button_11 = new JButton("放大");
		button_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String error = errorSematictextArea_8.getText();
				SATable frame = new SATable(error);
				frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		button_11.setBounds(1229, 673, 69, 33);
		contentPane.add(button_11);
		
		
		
		JScrollPane scrollPane_11 = new JScrollPane();
		scrollPane_11.setBounds(803, 708, 231, 232);
		contentPane.add(scrollPane_11);
		
		JTextArea symbolTableSematictextArea = new JTextArea();
		symbolTableSematictextArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		scrollPane_11.setViewportView(symbolTableSematictextArea);
		
		JLabel label_7 = new JLabel("符号表");
		label_7.setFont(new Font("宋体", Font.BOLD, 17));
		label_7.setBounds(853, 680, 97, 18);
		contentPane.add(label_7);
		
		JButton button_12 = new JButton("放大");
		button_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String symbolTable = symbolTableSematictextArea.getText();
				SATable frame = new SATable(symbolTable);
				frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		button_12.setBounds(929, 670, 69, 33);
		contentPane.add(button_12);
		
		
		
		JLabel label_8 = new JLabel("三元组");
		label_8.setFont(new Font("宋体", Font.BOLD, 17));
		label_8.setBounds(160, 677, 77, 18);
		contentPane.add(label_8);
		
		JButton button_9 = new JButton("放大");
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String three = threeArea_6.getText();
				SATable frame = new SATable(three);
				frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		button_9.setBounds(234, 671, 69, 33);
		contentPane.add(button_9);
		JButton button_1 = new JButton("语义分析");
		button_1.setFont(new Font("宋体", Font.BOLD, 15));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String text;
				try {
					semanticMain.offset = 0;
					String tokens = tokenLAtextArea_1.getText();
					System.out.println(tokens);
					ArrayList<str_value> w = semanticMain.getW(tokens);
					ArrayList<ArrayList<String>> table = semanticMain.getChart();
					Map<Integer,String> hash_flag = semanticMain.getHash();
					HashMap<String,symbol> futable=new HashMap<String,symbol>();
					Map<String,String> content = semanticMain.semanticFunc(table, w, (HashMap<Integer, String>) hash_flag, grammer.WF_INIT, futable);
					if(!content.containsKey("e")) {
						threeArea_6.setText("三元组："+"\n"+content.get("3"));
						fourAddressArea_7.setText("四元式："+"\n"+content.get("4"));
						symbolTableSematictextArea.setText(content.get("t"));
					}else {
						errorSematictextArea_8.setText(content.get("e"));
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_1.setBounds(14, 663, 134, 33);
		contentPane.add(button_1);
		
	}
	
	public static String ReadFile(String filePath) throws IOException {
		FileReader r = new FileReader(filePath);
		BufferedReader br = new BufferedReader(r);
		String line = null;
		StringBuilder content = new StringBuilder();
		while((line = br.readLine()) != null) {
			content.append(line);
			content.append("\n");
		}
		br.close();
		r.close();
		return content.toString();
	}
}
