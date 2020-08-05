package mine;

import javax.swing.*; 
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


import java.util.*;
import java.awt.*; 
import java.awt.event.*; 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class testMain extends Frame{
	static final int CHACHE_LENGTH=100;//final���峣��������ֵ������ʱ���ܱ��ı�
	static int offset=0;//ƫ�����������ð�
	JFrame mainFrame;
	JTextArea input,output,code_run;
	JList lineNumberColumn;
	JScrollPane input_Pane, output_Pane, table_Pane;
	//JButton getIt;
	JTable table,out;
	//JPanel getin;
	JLabel wenben,shuchu;
	DefaultTableModel tableModel,errorModel;
	Cff jisuan = new Cff();
	private JMenuBar bar;
	private JMenuItem openItem,saveItem,runItem;//,closeItem;
	private JMenu fileMenu,runMenu;
	private FileDialog openDia,saveDia;
	private File file;
	JToolBar toolbar;
	JSeparator sepv;
	//��-2016-5-24
	
	public testMain()
	{
		
		mainFrame = new JFrame("������");
		GridBagLayout layout = new GridBagLayout();
		Container contentPane = mainFrame.getContentPane();  
        contentPane.setLayout(layout); 
        wenben =new JLabel("�ı��༭��");
        shuchu =new JLabel("���ɵĻ�����");
        //Integer []liine={1};
        lineNumberColumn = new JList();
        final DefaultListModel listModel = new DefaultListModel();
        
        lineNumberColumn.setModel(listModel );
        listModel.addElement("1"+" ");
        //�����˵���
		bar = new JMenuBar();
		bar.setForeground(Color.gray);
		toolbar = new JToolBar();
		//�����˵��Ͳ˵���Ŀ
		fileMenu = new JMenu("File");
		fileMenu.setFont(new Font("΢���ź�",Font.BOLD,12));
		runMenu = new JMenu("Run");
		runMenu.setFont(new Font("΢���ź�",Font.BOLD,12));
		
		runItem=new JMenuItem("Run");
		runItem.setFont(new Font("΢���ź�",Font.BOLD,12));
		//ImageIcon logoing=new ImageIcon("go-next-view.png");   //���ﶨ��һ��IconͼƬ
		
		runItem.setIcon(new ImageIcon("go-next-view.png"));
		runItem.setPreferredSize(new Dimension(200,18));//���ó�ʼ���
		runItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));//���п�ݼ�Ϊctrl+r
		
		openItem = new JMenuItem("Open");
		openItem.setPreferredSize(new Dimension(200,18));		
		openItem.setFont(new Font("΢���ź�",Font.BOLD,12));
		openItem.setIcon(new ImageIcon("document-open.png"));
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));//���п�ݼ�Ϊctrl+o
		
		saveItem = new JMenuItem("Save");
		saveItem.setFont(new Font("΢���ź�",Font.BOLD,12));
		saveItem.setPreferredSize(new Dimension(200,18));
		saveItem.setIcon(new ImageIcon("document-save-as.png"));
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));//���п�ݼ�Ϊctrl+s
		//closeItem = new MenuItem("�˳�");
		
		//Ϊ�˵���Ӳ˵���Ŀ
		fileMenu.add(openItem);
		//fileMenu.addSeparator();//���÷ָ��
		fileMenu.add(saveItem);
		//fileMenu.add(closeItem);
		runMenu.add(runItem);
		//���˵���ӵ��˵�����
		bar.add(fileMenu);
		bar.add(runMenu);
		//Ϊ�������ò˵���
		mainFrame.setJMenuBar(bar);
		
		//�������򿪡��͡����桱�Ի���
		openDia = new FileDialog(mainFrame,"open",FileDialog.LOAD);
		saveDia = new FileDialog(mainFrame,"save",FileDialog.SAVE);
		
        //�������
        JTable table = new JTable(){public boolean isCellEditable(int rowIndex, int mColIndex) {
            return false;
        }
        //table.setForeground(Color.RED);
        
      
        public String getToolTipText(MouseEvent e) {  
            int row=this.rowAtPoint(e.getPoint());  
            int col=this.columnAtPoint(e.getPoint());  
            String tiptextString=null;  
            if(row>-1 && col>-1){  
                Object value=this.getValueAt(row, col);  
                if(null!=value && !"".equals(value))  
                    tiptextString=value.toString();//������ʾ��Ԫ������  
            }  
            return tiptextString;  
        } 
        };
        table.setForeground(Color.RED);
        table.setPreferredScrollableViewportSize(new Dimension(850, 100));
        errorModel = (DefaultTableModel) table.getModel();
        errorModel.addColumn("��������");
        errorModel.addColumn("��������");
        errorModel.addColumn("��������");
        
        //errorModel.
        //���������ʾ
        DefaultTableCellRenderer   rr   =   new   DefaultTableCellRenderer();   //���������ʾ
		rr.setHorizontalAlignment(JLabel.CENTER);   
		//r.setBackground(Color.GRAY);
		table.setDefaultRenderer(Object.class,  rr);
      //���ÿ��
		TableColumn err = table.getColumnModel().getColumn(0);//ȡ��һ��
		err.setPreferredWidth(200);
		err = table.getColumnModel().getColumn(1);
		err.setPreferredWidth(550);
		err = table.getColumnModel().getColumn(2);
		err.setPreferredWidth(100);
        
        
        //������
        JTable output = new JTable(){public boolean isCellEditable(int rowIndex, int mColIndex) {
            return false;
        }
        
        public String getToolTipText(MouseEvent e) {  
            int row=this.rowAtPoint(e.getPoint());  
            int col=this.columnAtPoint(e.getPoint());  
            String tiptextString=null;  
            if(row>-1 && col>-1){  
                Object value=this.getValueAt(row, col);  
                if(null!=value && !"".equals(value))  
                    tiptextString=value.toString();//������ʾ��Ԫ������  
            }  
            return tiptextString;  
        }  
        };
        output.setPreferredScrollableViewportSize(new Dimension(350, 350));
        tableModel = (DefaultTableModel) output.getModel();
        tableModel.addColumn("����");
		tableModel.addColumn("�к�");
		DefaultTableCellRenderer   r   =   new   DefaultTableCellRenderer();   //���������ʾ
		r.setHorizontalAlignment(JLabel.CENTER);   
		//r.setBackground(Color.GRAY);
		output.setDefaultRenderer(Object.class,  r);
		//���ÿ��
		TableColumn column = output.getColumnModel().getColumn(0);//ȡ��һ��
		column.setPreferredWidth(250);
		column = output.getColumnModel().getColumn(1);
		column.setPreferredWidth(100);
        //////////////////////////////////////////////////////////////////
        input =new JTextArea(20,44);
        code_run=new JTextArea(20,30);
        //input.setPreferredSize(new Dimension(350,370));
		//output =new JTextArea(20,30);
		//input.setLineWrap(true);// �����Զ����й���  
        input.setWrapStyleWord(true);// ������в����ֹ���  
        lineNumberColumn.setFocusable( false );
        code_run.setWrapStyleWord(true);// ������в����ֹ���  
        lineNumberColumn.setFocusable( false );
        
        input.getDocument( ).addDocumentListener( new DocumentListener() {
            public void insertUpdate( DocumentEvent e ) {
                //lineNumberColumn.( "" );
            	listModel.removeAllElements();
                for ( int i = 1; i <= input.getLineCount( ); i++ )
                	listModel.addElement(i+" ");
                	//lineNumberColumn.append( " " + i + " \n" );
            }
            public void  removeUpdate( DocumentEvent e ) { insertUpdate( e ); }
            public void changedUpdate( DocumentEvent e ) { insertUpdate( e );            }
        } );
        //listModel.getSize();
        //getIt = new JButton("�ʷ�����");
        //getIt.addActionListener(jisuan);
        runItem.addActionListener(jisuan);
		//getin = new JPanel();
		//getin.add(getIt);
        //input.setFont(new Font("Courier New",Font.BOLD,18));
        //lineNumberColumn.setFixedCellHeight();
        lineNumberColumn.setFixedCellHeight(18);//����List��ÿһ�еĸ߶�
        
        input_Pane = new JScrollPane(input); 
        output_Pane = new JScrollPane(code_run);
       // output_Pane = new JScrollPane(output);
        table_Pane = new JScrollPane(table);
        //input.setRowHeaderView(new JList());
        contentPane.add(wenben);
        contentPane.add(shuchu);
		contentPane.add(input_Pane);
		//contentPane.add(getin);
		contentPane.add(output_Pane);
		contentPane.add(table_Pane);
		input_Pane.setRowHeaderView( lineNumberColumn );
		 lineNumberColumn.setBackground(Color.getColor("input_Pane"));
		 //input_Pane.setBorder(null);
        GridBagConstraints s= new GridBagConstraints();//����һ��GridBagConstraints��������������ӽ����������ʾλ��
        s.gridx=0;
        s.gridy=1;
        s.anchor= GridBagConstraints.SOUTH;
        
        s.insets = new Insets(5,10,5,10);
        //���������--start
        layout.setConstraints(input_Pane, s);
        /*
        s.gridx=1;
        s.gridy=1;
        s.anchor= GridBagConstraints.CENTER;
        layout.setConstraints(getin, s);
        */
        s.gridx=2;
        s.gridy=1;
        s.anchor= GridBagConstraints.NORTHEAST;        
        layout.setConstraints(output_Pane, s);
        
        s.gridx=0;
        s.gridy=0;
        s.anchor= GridBagConstraints.WEST;        
        layout.setConstraints(wenben, s);
        
        s.gridx=2;
        s.gridy=0;
        s.anchor= GridBagConstraints.WEST;        
        layout.setConstraints(shuchu, s);
        
        s.gridx=0;
        s.gridy=2;
        s.anchor= GridBagConstraints.NORTHEAST;
        s.gridwidth=3;
        //s.fill = GridBagConstraints.BOTH;
        layout.setConstraints(table_Pane, s);
        
        //���������--end
		mainFrame.setSize(960,630);//���ô�С
		mainFrame.setResizable(false);//���ô�С���ɱ�
		mainFrame.setLocation(40, 40); //���ó�������Ļ��λ��
		mainFrame.setVisible(true); 
		
		myEvent();
		
		mainFrame.addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent e) {  
                System.exit(0);  
            }  
        });  
	}
	public void getArea(){
		String s=input.getText();
		String m=s.trim();
		output.setText(m);
	}
	
	//���������s��Ҫ��ȡ�µ�����ʱ�������ж�
	//�������������
	public void chache_read(int length, String []s, int ch_index){
		if(ch_index%CHACHE_LENGTH==0)
		{
			int m=ch_index/CHACHE_LENGTH;
			if((m+2)*CHACHE_LENGTH<length)
				s[(m+1)%2]=input.getText().substring((m+1)*CHACHE_LENGTH,(m+2)*CHACHE_LENGTH).toLowerCase();
			else if((m+2)*CHACHE_LENGTH>=length&&(m+1)*CHACHE_LENGTH<length)
			{
				s[(m+1)%2]=input.getText().substring((m+1)*CHACHE_LENGTH,length).toLowerCase();
			}
		}
		
	}
	private void myEvent(){
		//Ϊ����˵���Ŀ�����Լ��¼�����
//���ļ����´����ģ������Ի��򣬷���ֱ�ӱ���
		//�����Ϊ��һ�������Ի���
		saveItem.addActionListener(new ActionListener(){
				
			public void actionPerformed(ActionEvent e){
				//�ж��ļ��Ƿ��Ѿ�����
				if(file==null){
					saveDia.setVisible(true);
					
					//��ȡ�ļ���·��������
					String dirPath = saveDia.getDirectory();
					String fileName = saveDia.getFile();

					//ȡ��ʱ������׳���жϣ���ֹ��ָ���쳣
					if(dirPath==null || fileName==null)
						return ;

					file = new File(dirPath,fileName);
				}

				try{
					//����һ��д�뻺����
					BufferedWriter bufw  = new BufferedWriter(new FileWriter(file));
					//��ȡ�ı����е�����
					String text = input.getText();
					bufw.write(text);
					//bufw.flush();
					bufw.close();
				}
				catch (IOException ex){
					
					throw new RuntimeException();
				}	
			}
		});
	
		//Ϊ���򿪡��˵���Ŀ��Ӽ������ʹ����¼�
		openItem.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){
				//��ʾ�Ի���
				errorModel.setRowCount(0);
				openDia.setVisible(true);
				//��ȡ�ļ�·��������
				String dirPath = openDia.getDirectory();
				String fileName = openDia.getFile();
//				System.out.println(dirPath+"..."+fileName);

				//�����ȡ����ʱ������׳���жϣ���ֹ��ָ���쳣
				if(dirPath==null || fileName==null)
					return ;
				//����ϴδ򿪵�����
				input.setText("");
				//�½�һ���ļ�����
				file = new File(dirPath,fileName);

				//���ı����е�����д���������ļ���
				try{
					BufferedReader bufr = new BufferedReader(new FileReader(file));
					String line = null;
					input.setForeground(Color.BLACK);
					while((line=bufr.readLine())!=null){
						input.append(line+"\n");
					}
					bufr.close();
				}
				catch (IOException ex){
					//input.append("��ȡʧ�ܣ�������������");
					//input.setForeground(Color.RED);
					errorModel.setRowCount(0);
					Object[] e1={"��ȡ�ļ�","��ȡ�ļ�ʧ��",0};
					
					errorModel.addRow(e1);
				
					//throw new RuntimeException("��ȡʧ��");
				}
			}
		});
		
		/*
		//Ϊ�ر���Ŀ��Ӽ�����������
		closeItem.addActionListener(new ActionListener(){	
			//���ڹرմ���
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		 */
		//�������������
		mainFrame.addWindowListener(new WindowAdapter(){
			//�رմ���
			public void windowClosing(WindowEvent e){
				System.exit(0);	
			}
		});
	}
	
	public void chache_read_init(int length, String []s){
		if(CHACHE_LENGTH>=length)
			s[0]=input.getText().substring(0,length).toLowerCase();
		else
		{
			s[0]=input.getText().substring(0,CHACHE_LENGTH).toLowerCase();
			if(length<=2*CHACHE_LENGTH){
				s[1] =input.getText().substring(CHACHE_LENGTH,length).toLowerCase();
			}
			else
				s[1] =input.getText().substring(CHACHE_LENGTH,2*CHACHE_LENGTH).toLowerCase();
		}
	}
	public class END{  //����Ƿ��Ѿ�ȫ�����ļ����뵽������
		boolean value;
	}
	public class INT{  //һ������������������ı��
		int value;
	}
	class EVA{
		String str;
		String value;//ֵ���߱�����
		String type;//0--int 1--real 2-string 3_number_T1-array
		int width;
		//String addr;==value
		String offset;
		ArrayList<Integer> truelist;
		ArrayList<Integer> falselist;
	}
	class FOUR{
		String op;
		String arg1;
		String arg2;
		String result;
	}
	class str_value{
		String str;
		String value;
	}
	public char getch(char ch, String []s, int ch_index){
		int m=ch_index/CHACHE_LENGTH;
		ch=s[m%2].charAt(ch_index%CHACHE_LENGTH);
		//System.out.println(ch);
		return ch;
	}
	//ciΪ��������������ı�ǣ�cfΪ�����ĸ��������ı�ǣ�endΪ�Ƿ��Ѿ�ȫ�����ļ����뵽�������ı��
	public ArrayList<str_value>  token_scan(HashMap<String,Integer> hash,DefaultTableModel tableModel,DefaultTableModel err){
		INIT(hash); //��ʼ����ϣ��
		//������ʹ��������
		code_run.setText("");
		tableModel.setRowCount(0);
		err.setRowCount(0);
		int length=input.getText().length();
		String []s=new String[2];
		//�տ�ʼ��ȡ��ʱ��
		chache_read_init(length, s);
		
		ArrayList<str_value> str_w=new ArrayList<str_value>();//��Ϊ�﷨�������������ַ�������
		
		int line=1;//�������
		char ch=0;//�������ַ�
		int ch_index=0; //������λ��
		boolean flag=true;//��ǻ�������
		int xiaokuohao=0,zhongkuohao=0;//)��]�ļ��
		
		while(ch_index<length||flag==false)
		{
			String token="";
			if(flag){
				ch=getch(ch,s,ch_index);
				ch_index++;chache_read(length, s, ch_index);
			}
			flag=true;
			while((ch==' ' || ch=='\n')&&ch_index<length) //�������ַ�
			{
				if(ch=='\n') {line=line+1;}
				ch=getch(ch,s,ch_index);
				ch_index++;chache_read(length, s, ch_index);chache_read(length, s, ch_index);
				
			}
			//System.out.println("119��:"+line);
			if(Character.isLetter(ch))
			{
				token+=ch;
				if(ch_index<length){
					ch=getch(ch,s,ch_index);
					ch_index++;chache_read(length, s, ch_index);
					while((Character.isLetter(ch) || Character.isDigit(ch))&&ch_index<length){
						token+=ch;
						ch=getch(ch,s,ch_index);
						ch_index++;chache_read(length, s, ch_index);
					}
					if(ch_index==length&&(Character.isLetter(ch) || Character.isDigit(ch)))
					{
						token+=ch;
						if(hash.containsKey(token))
						{
							//out+="("+token.toUpperCase()+",)"+"\n";
							Object[] o={"("+token.toUpperCase()+",)",line};
							tableModel.addRow(o);
							str_value ao = new str_value();
							ao.str=token.toUpperCase();
							ao.value="";
							str_w.add(ao);
						}
						else
						{
							//��Ҫ������ű�����������
							
							//out+="(ID��"+token+")"+"\n";
							Object[] o={"(ID��"+token+")",line};
							str_value ao = new str_value();
							ao.str="ID";
							ao.value=token;
							str_w.add(ao);
							tableModel.addRow(o);
						}
						break;
					}
				}
				
				if(hash.containsKey(token))
				{
					//out+="("+token.toUpperCase()+",)"+"\n";
					Object[] o={"("+token.toUpperCase()+",)",line};
					
					str_value ao  = new str_value();
					ao.str=token.toUpperCase();
					ao.value="";
					str_w.add(ao);
					tableModel.addRow(o);
				}
				else
				{
					//��Ҫ������ű�����������
					Object[] o={"(ID��"+token+")",line};
					tableModel.addRow(o);
					str_value ao  = new str_value();
					ao.str="ID";
					ao.value=token;
					str_w.add(ao);
					//out+="(ID��"+token+")"+"\n";
				}
			}
			if(Character.isDigit(ch)){
				token+=ch;
				if(ch_index<length){
					ch=getch(ch,s,ch_index);
					ch_index++;chache_read(length, s, ch_index);
					while(Character.isDigit(ch)&&ch_index<length){   
						token+=ch;
						ch=getch(ch,s,ch_index);
						ch_index++;chache_read(length, s, ch_index);
					}
					if(ch_index==length&&Character.isDigit(ch)){
						token+=ch;
						//out+="(INT��"+token+")"+"\n";
						Object[] o={"(INT��"+token+")",line};
						tableModel.addRow(o);
						
						str_value ao  = new str_value();
						ao.str="INT";
						ao.value=token;
						str_w.add(ao);
						break;
					}
					if(!(Character.isDigit(ch)||ch=='.')) flag=false;
				}
				if(ch=='.')
				{
					token+=ch;
					if(ch_index<length){
					ch=getch(ch,s,ch_index);
					ch_index++;chache_read(length, s, ch_index);
					while(Character.isDigit(ch)&&ch_index<length){   
						token+=ch;
						ch=getch(ch,s,ch_index);
						ch_index++;chache_read(length, s, ch_index);
					}}
					if(ch_index==length&&Character.isDigit(ch)){
						token+=ch;
						//out+="(REAL��"+token+")"+"\n";
						Object[] o={"(REALL��"+token+")",line};
						tableModel.addRow(o);
						str_value ao  = new str_value();
						ao.str="REALL";
						ao.value=token;
						str_w.add(ao);
						break;
					}
					if(!Character.isDigit(ch)) flag=false;
					//out+="(REAL��"+token+")"+"\n";
					Object[] o={"(REALL��"+token+")",line};
					tableModel.addRow(o);
					str_value ao  = new str_value();
					ao.str="REALL";
					ao.value=token;
					str_w.add(ao);
				}
				else
				{
					//out+="(INT��"+token+")"+"\n";
					Object[] o={"(INT��"+token+")",line};
					tableModel.addRow(o);
					str_value ao  = new str_value();
					ao.str="INT";
					ao.value=token;
					str_w.add(ao);
				}
				//flag=false;
			}
			else{
				switch(ch){
				case '*':
					ch=getch(ch,s,ch_index);
					ch_index++;chache_read(length, s, ch_index);
					if(ch=='*'){
						token+=ch;
						//out+="(EXP,)"+"\n";
						Object[] o={"(EXP,)",line};
						tableModel.addRow(o);
						str_value ao  = new str_value();
						ao.str="**";
						ao.value="";
						str_w.add(ao);

					}
					else{
						flag=false;
						//out+="(MULTI,)"+"\n";
						Object[] o={"(MULTI,)",line};
						tableModel.addRow(o);
						str_value ao  = new str_value();
						ao.str="*";
						ao.value="";
						str_w.add(ao);
					}
					break;
				case ':':
					ch=getch(ch,s,ch_index);
					ch_index++;chache_read(length, s, ch_index);
					if(ch=='='){
						token+=ch;
						//out+="(ASSIGN,)"+"\n";
						Object[] o={"(ASSIGN,)",line};
						tableModel.addRow(o);
						str_value ao  = new str_value();
						ao.str=":=";
						ao.value="";
						str_w.add(ao);
					}
					else{
						flag=false;
						Object[] o={"(COLON,)",line};
						tableModel.addRow(o);
						
						str_value ao  = new str_value();
						ao.str=":";
						ao.value="";
						str_w.add(ao);
						//out+="(COLON,)"+"\n";
					}
					break;
				case '<':
					if(ch_index<length)
					{
						ch=getch(ch,s,ch_index);
						ch_index++;chache_read(length, s, ch_index);
						
						if(ch=='='){
							token+=ch;
							//out+="(LE,)"+"\n";
							Object[] o={"(LE,)",line};
							tableModel.addRow(o);
							str_value ao  = new str_value();
							ao.str="<=";
							ao.value="";
							str_w.add(ao);
							
						}
						else if(ch=='>'){
							token+=ch;
							//out+="(NE,)"+"\n";
							Object[] o={"(NE,)",line};
							tableModel.addRow(o);
							str_value ao  = new str_value();
							ao.str="<>";
							ao.value="";
							str_w.add(ao);
							//w.add("<>");
						}
						else{
							flag=false;
							//out+="(LT,)"+"\n";
							Object[] o={"(LT,)",line};
							tableModel.addRow(o);
							str_value ao  = new str_value();
							ao.str="<";
							ao.value="";
							str_w.add(ao);
							//w.add("<");
						}
					}
					else
					{
						Object[] o={"(LT,)",line};
						tableModel.addRow(o);
						//w.add("<");
						str_value ao  = new str_value();
						ao.str="<";
						ao.value="";
						str_w.add(ao);
					}
					
					
					break;
				case '=':
					//out+="(EQ,)"+"\n";
					Object[] o={"(EQ,)",line};
					tableModel.addRow(o);
					str_value ao  = new str_value();
					ao.str="=";
					ao.value="";
					str_w.add(ao);
					//w.add("=");
					break;
				case '>':
					
					if(ch_index<length)
					{
						ch=getch(ch,s,ch_index);
						ch_index++;chache_read(length, s, ch_index);
						
						if(ch=='='){
								token+=ch;
								Object[] o1={"(GE,)",line};
								tableModel.addRow(o1);
								str_value aoa  = new str_value();
								aoa.str=">=";
								aoa.value="";
								str_w.add(aoa);
								//w.add(">=");
								//out+="(GE,)"+"\n";
							//out+="(RANGE,)"+"\n";
						}
						else{
							flag=false;
							Object[] o1={"(GT,)",line};
							tableModel.addRow(o1);
							str_value aob  = new str_value();
							aob.str=">";
							aob.value="";
							str_w.add(aob);
							//w.add(">");
							//out+="(GT,)"+"\n";
						}
					}
					else
					{
						Object[] o1={"(GT,)",line};
						tableModel.addRow(o1);
						str_value aoc  = new str_value();
						aoc.str=">";
						aoc.value="";
						str_w.add(aoc);
						//w.add(">");
					}
						
					break;
				case '+': 
					//out+="(PLUS,)"+"\n";
					Object[] o1={"(PLUS,)",line};
					tableModel.addRow(o1);
					str_value aoe = new str_value();
					aoe.str="+";
					aoe.value="";
					str_w.add(aoe);
					break;
				case '-': 
					//out+="(MINUS,)"+"\n";
					Object[] o2={"(MINUS,)",line};
					tableModel.addRow(o2);
					str_value aof = new str_value();
					aof.str="-";
					aof.value="";
					str_w.add(aof);
					//w.add("-");
					break;
				case '/': 
					//out+="(RDIV,)"+"\n";
					Object[] o3={"(RDIV,)",line};
					tableModel.addRow(o3);
					str_value aog  = new str_value();
					aog.str="/";
					aog.value="";
					str_w.add(aog);
					//w.add("/");
					break;
				case ',': 
					//out+="(COMMA,)"+"\n";
					Object[] o4={"(COMMA,)",line};
					tableModel.addRow(o4);
					str_value aoh  = new str_value();
					aoh.str=",";
					aoh.value="";
					str_w.add(aoh);
					//w.add(",");
					break;
				case ';': 
					//out+="(SEMIC,)"+"\n";
					Object[] o5={"(SEMIC,)",line};
					tableModel.addRow(o5);
					str_value aoi  = new str_value();
					aoi.str=";";
					aoi.value="";
					str_w.add(aoi);
					//w.add(";");
					break;
				case '(': 
					if(ch_index<length)
						ch=getch(ch,s,ch_index);
					
					//ch_index++;chache_read(length, s, ch_index);
					if(ch=='*'){
						if(ch_index+1<length&&ch_index+2<length)
						{
							char ch1=getch(ch,s,ch_index+1);
							char ch2=getch(ch,s,ch_index+2);
							while((ch_index<length-2)&&!(ch1=='*'&&ch2==')')){
								ch1=getch(ch,s,ch_index+1);
								ch2=getch(ch,s,ch_index+2);
								ch_index++;chache_read(length, s, ch_index);
								if(ch_index<length){
								if(getch(ch,s,ch_index)=='\n') line++;}
							}
							ch_index++;chache_read(length, s, ch_index);
							ch_index++;chache_read(length, s, ch_index);
							ch_index++;chache_read(length, s, ch_index);
						//System.out.println(ch_index);
						}
					}
					else{
						if(LR_BRAC(ch_index,s,length,line)==true)
						{
							//out+="(LR_BRAC,)"+"\n";
							xiaokuohao++;
							Object[] o6={"(LR_BRAC,)",line};
							tableModel.addRow(o6);
							//w.add("(");
							str_value aoj  = new str_value();
							aoj.str="(";
							aoj.value="";
							str_w.add(aoj);
						}
							
						else{
							//error+="����Դ���--'('��������"+line+"��\n";
							Object[] e={"�ʷ�����","'('����Դ���",line};
							err.addRow(e);
						}
					}
					break;
						
				case ')': 
					if(xiaokuohao>0)
					{
						xiaokuohao--;
						Object[] o6={"(RR_BRAC,)",line};
						tableModel.addRow(o6);
						str_value aoj  = new str_value();
						aoj.str=")";
						aoj.value="";
						str_w.add(aoj);
					}
					else
					{
						Object[] e={"�ʷ�����","')'����Դ���",line};
						err.addRow(e);
					}
					break;
				case '��': 
					Object[] o7={"(P_MARK,)",line};
					tableModel.addRow(o7);
					str_value aoj  = new str_value();
					aoj.str="��";
					aoj.value="";
					str_w.add(aoj);
					//out+="(P_MARK,)"+"\n";
					break;
				case '.': 
					if(ch_index<length)
					{
						ch=getch(ch,s,ch_index);
						ch_index++;chache_read(length, s, ch_index);
						
						if(ch=='.'){
							token+=ch;
							Object[] o6={"(RANGE,)",line};
							tableModel.addRow(o6);
							str_value aok  = new str_value();
							aok.str="..";
							aok.value="";
							str_w.add(aok);
							//out+="(RANGE,)"+"\n";
						}
						else{
							flag=false;
							Object[] o6={"(F_STOP,)",line};
							tableModel.addRow(o6);
							str_value aok  = new str_value();
							aok.str=".";
							aok.value="";
							str_w.add(aok);
							//out+="(F_STOP,)"+"\n";
						}
					}
					else
					{
						Object[] o6={"(F_STOP,)",line};
						tableModel.addRow(o6);
						str_value aok  = new str_value();
						aok.str=".";
						aok.value="";
						str_w.add(aok);
					}
					
					break;
				case '^': 
					//out+="(CAP,)"+"\n";
					Object[] o6={"(CAP,)",line};
					tableModel.addRow(o6);
					str_value aok  = new str_value();
					aok.str="^";
					aok.value="";
					str_w.add(aok);
					break;
				case '[': 
					if(LS_BRAC(ch_index,s,length,line)==true)
					{
						//out+="(LS_BRAC,)"+"\n";
						zhongkuohao++;
						Object[] o8={"(LS_BRAC,)",line};
						str_value aok1  = new str_value();
						aok1.str="[";
						aok1.value="";
						str_w.add(aok1);
						tableModel.addRow(o8);
					}
						
					else{
						Object[] e11={"�ʷ�����","'['����Դ���",line};
						err.addRow(e11);
					}
					break;
				case ']': 
					if(zhongkuohao>0)
					{
						zhongkuohao--;
						Object[] o8={"(RS_BRAC,)",line};
						str_value aok2  = new str_value();
						aok2.str="]";
						aok2.value="";
						str_w.add(aok2);
						tableModel.addRow(o8);
					}
					else
					{
						Object[] e1={"�ʷ�����","']'����Դ���",line};
						err.addRow(e1);
					}
					break;
				case '\'': 
					if(Q_MARK(ch_index,s,length,line)==true)
					{
						//out+="(RS_BRAC,)"+"\n";
						Object[] o8={"(Q_MARK,)",line};
						tableModel.addRow(o8);
						str_value aokk  = new str_value();
						aokk.str="'";
						aokk.value="";
						str_w.add(aokk);
						ch=getch(ch,s,ch_index);
						ch_index++;chache_read(length, s, ch_index);
						
						while(ch!='\'')
						{
							token+=ch;
							ch=getch(ch,s,ch_index);
							ch_index++;chache_read(length, s, ch_index);
						}
						//System.out.println(flag);
						Object[] o11={"("+"STRING"+","+token+")",line};
						tableModel.addRow(o11);
						//tableModel.addRow(o8);
						//w.add("STRING");
						str_value aokl  = new str_value();
						aokl.str="STRING";
						aokl.value=token;
						str_w.add(aokl);
						//out+="(RS_BRAC,)"+"\n";
						//System.out.println(ch_index);
					}
					else{
						
						Object[] e4={"�ʷ�����","'����'����Դ���",line};
						err.addRow(e4);
					}
					break;
				case ' ':
					break;
				case '\n':
					line++;
					break;
				case '}':
					Object[] e3={"�ʷ�����","'}'����Դ���",line};
					err.addRow(e3);
					break;
				case '{':  //ȥ��ע��
					if(ch_index<length)
						ch=getch(ch,s,ch_index);
					
					//ch_index++;chache_read(length, s, ch_index);
					if(ch=='*'){
						//System.out.println(ch);
						if(ch_index+1<length&&ch_index+2<length)
						{
							char ch1=getch(ch,s,ch_index+1);
							char ch2=getch(ch,s,ch_index+2);
							while((ch_index<length-2)&&!(ch1=='*'&&ch2=='}')){
								ch1=getch(ch,s,ch_index+1);
								ch2=getch(ch,s,ch_index+2);
								ch_index++;chache_read(length, s, ch_index);
								if(ch_index<length){
								if(getch(ch,s,ch_index)=='\n') line++;}
							}
							ch_index++;chache_read(length, s, ch_index);
							ch_index++;chache_read(length, s, ch_index);
							ch_index++;chache_read(length, s, ch_index);
						//System.out.println(ch_index);
						}
					}
					else{
						if(Explain(ch_index,s,length)==true)
						{
							if(ch=='}'){ch_index++;chache_read(length, s, ch_index);}
							while(ch!='}')
							{		
								ch=getch(ch,s,ch_index);
								ch_index++;chache_read(length, s, ch_index);
							}
						}
						else
						{
							Object[] e9={"�ʷ�����","'{'����Դ���",line};
							err.addRow(e9);
						}
					}
					break;
					
					
				default:
					Object[] e0={"�ʷ�����","�Ƿ��ַ�",line};
					err.addRow(e0);
				}	
			}
		}
		//System.out.print(s.charAt(0));
		//output.setText(out);
		return str_w;
	}
	public boolean Explain(int mm,String []s,int length) //�ж�����{�ķ����--ע��
	{
		int j=mm;
		char find=0;
		
		for (int i=0;i<CHACHE_LENGTH/3;i++)
		{
			if(j+i>=length) return false;
			find=getch(find,s,i+j);
			if(find=='}')
			{
				return true;	
			}
			else if(find=='\n') 
			{
				return false;
			}
		}
		return false;
	}
	public boolean Q_MARK(int mm,String []s,int length,int line) //�ж�����'�ķ����
	{
		int j=mm;
		char find=0;
		
		for (int i=0;i<CHACHE_LENGTH/3;i++)
		{
			if(j+i>=length) return false;
			find=getch(find,s,i+j);
			if(find=='\'')
			{
				return true;	
			}
			else if(find=='\n') 
			{
				return false;
			}
		}
		return false;
	}
	public boolean LR_BRAC(int mm,String []s,int length,int line) //�ж����ţ��ķ����
	{
		int j=mm;
		char find=0;
		int num=0;
		for (int i=0;i<CHACHE_LENGTH/2;i++)
		{
			if(j+i>=length) return false;
			find=getch(find,s,i+j);
			if(find==')')
			{
				if (num==0)
				{
					//s[((i+j)/CHACHE_LENGTH)%2]=s[((i+j)/CHACHE_LENGTH)%2].substring(0, (i+j)%CHACHE_LENGTH)+" "+s[((i+j)/CHACHE_LENGTH)%2].substring((i+j)%CHACHE_LENGTH+1);
					//System.out.println(s[((i+j)/CHACHE_LENGTH)%2]);
					//Object[] o6={"(RR_BRAC,)",line};
					//tableModel.addRow(o6);
					return true;
				}
				else
					num--;
			}
			else if(find=='(') num++; 
			else if(find=='\n') 
			{
				return false;
			}
		}
		return false;
	}

	public boolean LS_BRAC(int mm,String []s,int length,int line) //�ж�����[�ķ����
	{
		int j=mm;
		char find=0;
		int num=0;
		for (int i=0;i<CHACHE_LENGTH/3;i++)
		{
			if(j+i>=length) return false;
			find=getch(find,s,i+j);
			if(find==']')
			{
				if (num==0)
				{
					//s[((i+j)/CHACHE_LENGTH)%2]=s[((i+j)/CHACHE_LENGTH)%2].substring(0, (i+j)%CHACHE_LENGTH)+' '+s[((i+j)/CHACHE_LENGTH)%2].substring((i+j)%CHACHE_LENGTH+1);
					//System.out.println(s[((i+j)/CHACHE_LENGTH)%2]);
					//Object[] o6={"(RS_BRAC,)",line};
					//tableModel.addRow(o6);
					return true;
				}
				else
					num--;
			}
			else if(find=='[') num++; 
			else if(find=='\n') 
			{
				return false;
			}
		}
		return false;
	}
	class F_enter{
		String type;
		int offset;
	}
	
	class Cff implements ActionListener	 //main
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource()==runItem){
				
				HashMap<String,Integer> hash = new HashMap<String,Integer>();
				HashMap<String,F_enter> table_of_mathematical_symbols=new HashMap<String,F_enter>();
				ArrayList<str_value> str_w=token_scan(hash, tableModel, errorModel);
				str_value a0 = new str_value();
				a0.str="#";
				a0.value="";
				str_w.add(a0);
				
				//asm_code="";
				try {
					Wenfa_init(str_w,table_of_mathematical_symbols,errorModel);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
	}
	class WENFA{//���Դ洢ÿ���ķ�
		String []wf;
		int dort;
		String next;
	}
	
	public boolean Equeals(WENFA w1, WENFA w2){//�ж������ķ��Ƿ���ͬ
		if(w1.dort!=w2.dort || w1.next.length()!=w2.next.length() || w1.wf.length!=w2.wf.length) 
		{
			return false;
		}
		else{
			int len=w1.wf.length;
			for(int i=0; i<len; i++)
			{
				if(!w1.wf[i].equals(w2.wf[i]))
					return false;
			}
			for(int j=0; j<w1.next.length(); j++){
				char[] chars = w1.next.toCharArray();
				Arrays.sort(chars);
				String sorted =new String(chars);
				char[] chars2 = w2.next.toCharArray();
				Arrays.sort(chars2);
				String sorted2 =new String(chars2);
				if(!sorted.equals(sorted2))
					return false;
			}
			return true;
		}
		
	}
	public void Wenfa_init(ArrayList<str_value> w,HashMap<String,F_enter> fu_table,DefaultTableModel err) throws IOException{//�﷨��ʼ��������
		String [][]WF_INIT={{"P", "PROGRAM", "ID", "D",";","S","#"},//0
				{"D", "D",";","D","#"},//1
				{"D","List",":","T","#"},//2
				{"D","PROCEDURE","ID","D",";","S","#"},//3
				{"List","List",",","ID","#"},//4
				{"List","ID","#"},//5
				{"T","INTEGER","#"},//6
				{"T","REAL","#"},//7
				{"T","ARRAY","C","OF","T","#"},//8
				{"T","RECORD","D","#"},//9
				
				{"D","ID",":","T","#"},//10
				
				{"T","RECORD","D","END","#"},//11
				
				
	              {"S","Left",":=","E","#"},//12
	              {"E","E","+","E","#"},//13
	              {"E","E","*","E","#"},//14
	              {"E","-","E","#"},//15
	              {"E","(","E",")","#"},//16
	              {"E","Left","#"},//17
	              {"E","INT","#"},//18
	              {"E","REALL","#"},//19
	              {"E","STRING","#"},//20
	              {"Left","Elist","]","#"},//21
	              {"Left","ID","#"},//22
	              
	              {"Elist","Elist",",","E","#"},//23
	              {"Elist","ID","[","E","#"},//24
	              
				  {"S","CALL","ID","(","Elist",")","#"},//25
				  {"Elist","Elist",",","E","#"},//26
				  {"Elist","E","#"},//27
				  {"S","RETURN","E","#"},//28
				  
	              {"B","B","OR","B","#"},//29
	              {"B","B","AND","B","#"},//30
	              {"B","NOT","B","#"},//31
	              {"B","(","B",")","#"},//32
	              {"B","E",">","E","#"},//33
	              {"B","E","<","E","#"},//34
	              {"B","E",">=","E","#"},//35
	              {"B","E","<=","E","#"},//36
	              {"B","E","<>","E","#"},//37
	              {"B","E","=","E","#"},//38
	              {"B","TRUE","#"},//39
	              {"B","FALSE","#"},//40

	              {"S","IF","B","THEN","S","#"},//41
	              {"S","IF","B","THEN","S","ELSE","S","#"},//42
	              {"S","WHILE","B","DO","S","#"},//43
	              
	              {"L","L",";","S","#"},//44
	              {"L","S","#"},//45
	              
	          	{"S","READ","(","List",")","#"},//46
	          	{"S","READLN","(","List",")","#"},//47
	          	{"S","WRITE","(","Elist",")","#"},//48
	          	{"S","WRITELN","(","Elist",")","#"},//49
	          	
	              {"S","FOR","ID",":=","E","TO","E","STEP","E","DO","S","#"},//50
	              
	              {"S","REPEAT","S","UNTIL","B","#"},//51
	              {"S","BEGIN", "L","END","#"},//52
	              {"C","[","INT","]","#"},//53
		};//����ԭʼ�ķ�
		int hash_number=300;//��300��ʼ�����VUT�ĸ���hash_number-300
		HashMap<Integer,String> hash_flag=new HashMap<Integer,String>();//�洢�﷨�г��ֵķ���
		hash_flag.put(299,"#");//������
		for(int WF_INIT_length=0;WF_INIT_length<WF_INIT.length;WF_INIT_length++){//���ķ��г��ֵķ��Ž��й�ϣ��洢
			for(int WF_INIT_EVERY=0; WF_INIT_EVERY<WF_INIT[WF_INIT_length].length;WF_INIT_EVERY++)
			{
				String li=WF_INIT[WF_INIT_length][WF_INIT_EVERY];
				
				if(!hash_flag.containsValue(li)){
					hash_flag.put(hash_number++,li);
				}
			}
		}
		ArrayList<ArrayList<WENFA>> GFZ = new ArrayList();//GFZ��LR��1������Ŀ���淶��
		//int []WF_dort=new int[10];//����
		ArrayList<WENFA> I0=new ArrayList<WENFA>();
		WENFA i0 = new WENFA();
		i0.wf=new String[]{"P", "PROGRAM", "ID", "D",";","S","#"};
		i0.dort=1;
		i0.next="";
		i0.next+='#';
		I0.add(i0);
		CLOSURE(I0,WF_INIT);
		GFZ.add(I0);
		//System.out.println("��Ŀ���淶��Ĵ�СΪ��"+GFZ.size());
		CLO_FULL(GFZ, hash_number, hash_flag,WF_INIT); //��Ŀ���淶��
		System.out.println("��Ŀ���淶��Ĵ�СΪ��"+GFZ.size());
		ArrayList<ArrayList<String>> table=LRtable(GFZ,hash_number, hash_flag,WF_INIT);//���������
		
		LR1_main(table,w,hash_flag, WF_INIT,fu_table);
	}
	public void Pri(ArrayList<WENFA> flag){                                          //��ӡ�հ�
		System.out.println("����--------�հ�--------����");
		for(int i=0; i<flag.size();i++) //�ѽ����ӡ������Ϊ�˼���Ƿ����
		{
			String mlll="";
			mlll+=((WENFA)flag.get(i)).wf[0]+"->";
			for(int kk=1;kk<((WENFA)flag.get(i)).wf.length-1;kk++)
			{
				mlll+=((WENFA)flag.get(i)).wf[kk]+' ';
			}
			System.out.println(mlll+','+((WENFA)flag.get(i)).next+' '+((WENFA)flag.get(i)).dort);
			
		}
	}
	
	public boolean Equa(ArrayList<WENFA> g1,ArrayList<WENFA> g2){//�ж������հ��Ƿ���ͬ
		if(g1.size()!=g2.size())
			return false;
		else{
			int number=g1.size();
			for(int i=0; i<number; i++)
			{
				boolean m=true;
				for(int j=0; j<number; j++)
				{
					if(Equeals(g1.get(i), g2.get(j))){
						m=false;
						break;
					}
				}
				if(m)
				{
					return false;
				}
			}
			return true;
		}
	}
	//����Ŀ���淶��
	public void CLO_FULL(ArrayList<ArrayList<WENFA>> GFZ, int hash_number, HashMap<Integer,String> hash_flag,String [][]WEN_INIT){//LR��1����Ŀ���淶��Ĺ���
		int qnr =0;
		int number=GFZ.size();
		//Pri(GFZ.get(0));
		while(qnr<number){
			ArrayList<WENFA> flag=GFZ.get(qnr);
			for(int n=300;n<hash_number;n++){
				ArrayList<WENFA> go=GO(flag,hash_flag.get(n),WEN_INIT);
				
				if(go.size()>0){
					boolean in=true;
					for(int i=0;i<number;i++){
						if(Equa(go,GFZ.get(i))){
							in=false;
							break;
						}
					}
					if(in) {
						GFZ.add(go);
						//Pri(go);
					}
				}
			}
			qnr++;
			number=GFZ.size();
			
		}
	}
	//ת�ƺ���
	public ArrayList<WENFA> GO(ArrayList<WENFA> al, String X,String [][]WEN_INIT) //ת�ƺ���
	{
		ArrayList<WENFA> J0=new ArrayList<WENFA>();//��Ӧ�㷨�е�j
		
		for(int i=0; i<al.size(); i++)
		{
			WENFA flag=al.get(i);
			//System.out.println(flag.wf[flag.dort]);
			
			if(flag.wf[flag.dort].equals(X)){
				WENFA j0=new WENFA();
				j0.wf=flag.wf;
				j0.dort=flag.dort+1;
				j0.next=flag.next;
				J0.add(j0);
			}
		}
		//System.out.println(J0.size());
		CLOSURE(J0, WEN_INIT);
		return J0;
	}
	//��հ�����
	public void CLOSURE(ArrayList<WENFA> al, String [][]WEN_INIT ){//LR(1)�ıհ�
		int changdu =0;
		int length=al.size();
		HashMap<String,String> kv = new HashMap<String,String>();//ʹ�ù�����Ŀ����Ϊ�˼����չ�������������A->B-C���֣�keyΪA��valuesΪ-
		while(changdu <length)
		{
			String []m=((WENFA)al.get(changdu)).wf;
			int index = ((WENFA)al.get(changdu)).dort;
			//System.out.println(""+changdu+"-----"+index+"----"+m.length);
			
			String B =m[index];
			
			if(!kv.containsKey(B)){
				for(int j=0; j<WEN_INIT.length;j++)
				{
					if(WEN_INIT[j][0].equals(B))
					{
						WENFA i0=new WENFA();
						i0.wf=WEN_INIT[j];
						i0.dort=1;
						i0.next="";
						if(index+1==((WENFA)al.get(changdu)).wf.length-1)
						{
							i0.next=((WENFA)al.get(changdu)).next;
							if(((WENFA)al.get(changdu)).next.indexOf(m[index+1])>=0);
							else
							{
								i0.next+=m[index+1];
							}
						}
						else
							i0.next+=m[index+1];
						al.add(i0);
						kv.put(B, i0.next);
					}
				}
			}
			else{
				String l_x_y=m[index+1];
				if(kv.get(B).indexOf(l_x_y)<0){
					kv.put(B, kv.get(B)+l_x_y);
					for(int ll=0; ll<al.size();ll++)
					{
						if(al.get(ll).wf[0]==B){
							WENFA ll0=al.get(ll);
							ll0.next=kv.get(B);
							al.set(ll, ll0);
						}
					}
				}
			}
			
			length=al.size();	
			changdu++;
		}
		/*
		for(int i=0; i<al.size();i++) //�ѽ����ӡ������Ϊ�˼���Ƿ����
		{
			String mlll="";
			for(int kk=0;kk<((WENFA)al.get(i)).wf.length;kk++)
			{
				mlll+=((WENFA)al.get(i)).wf[kk]+' ';
			}
			System.out.println(mlll+' '+((WENFA)al.get(i)).next+' '+((WENFA)al.get(i)).dort);
			
		}
		*/
	}
	//����Ŀ���淶�����ҵ��հ����ڵ�λ�ã������򷵻�-1
	public int FindInCA(ArrayList<ArrayList<WENFA>> GFZ,ArrayList<WENFA> al)
	{
		int length=GFZ.size();
		for(int i=0; i<length; i++)
		{
			if(Equa(al,GFZ.get(i))){
				return i;
			}
		}
		return -1;
	}
	public boolean JudgeSt(String []a,String []b)//�ж������ַ��������Ƿ���ͬ
	{
		if(a.length!=b.length)
			return false;
		else
		{
			for(int i=0; i<a.length; i++)
			{
				if(!a[i].equals(b[i]))
					return false;
			}
		}
		return true;
	}
	
	//���ع��ķ����ҵ�A->a���ڵ�λ��,�ҵ�����λ�ã����򷵻�-1
	public int FindG(String [][]WEN_INIT,WENFA a)
	{
		String []linshi=a.wf;
		for(int i=0; i<WEN_INIT.length; i++)
		{
			if(JudgeSt(linshi,WEN_INIT[i]))
				return i;
		}
		return -1;
	}
	//������Ĺ��죬GFZΪLR(1)����Ŀ���淶��--���а���action���goto��
	public ArrayList<ArrayList<String>> LRtable(ArrayList<ArrayList<WENFA>> GFZ,int hash_number, HashMap<Integer,String> hash_flag,String [][]WEN_INIT){ 
		ArrayList<ArrayList<String>> table=new ArrayList<ArrayList<String>>();//����һ����λ�����ʾ������
		int arr_number=GFZ.size();//arr_number��ʾ��Ŀ���淶��Ĵ�С
		String V[]=new String[WEN_INIT.length];
		//-----------------------------------------------------------------------------------------------------------
		//���ڿ϶�Ҫ���и��ĵĵط�
		for(int i=0; i<WEN_INIT.length; i++)
		{
			V[i]=WEN_INIT[i][0];
		}
		//String T="*=i";//�ֶ������ս��
		//-----------------------------------------------------------------------------------------------------------
		for(int i=0; i<arr_number;i++){
			ArrayList<WENFA> flag=GFZ.get(i);    //flag��ʱ��ʾGFZ�еĵ�i���հ�
			ArrayList<String> table_every=new ArrayList<String>();//һ�У���ʾ��i���հ�
			for(int m=299; m<hash_number; m++){//ȡÿ�����������ս��
				boolean tianchong=false;
				if(FinStri(hash_flag.get(m), V)<0&&!hash_flag.get(m).equals("#")){//���ս��,��Ӧ���ϵ�Ȧ1
					for(int j=0; j<flag.size(); j++){
						WENFA wf_flag=flag.get(j);
						//System.out.println("----woshiyyiigejihao----------------------------------------------");
						if(wf_flag.wf[wf_flag.dort].equals(hash_flag.get(m))){
							ArrayList<WENFA> ff=GO(flag,hash_flag.get(m),WEN_INIT);
							int linshi=FindInCA(GFZ,ff);
							if(linshi>=0){
								table_every.add("S"+linshi);
								tianchong=true;
								break;
							}
						}
						else if(wf_flag.wf[wf_flag.dort].equals("#")&&wf_flag.next.indexOf(hash_flag.get(m))>=0)//��ӦȦ3
						{
							int linshi=FindG(WEN_INIT,wf_flag);
							if(linshi>=0){
								table_every.add("r"+linshi);
								tianchong=true;
								break;
							}
						}
					}
				}	
				else if(FinStri(hash_flag.get(m), V)>=0)//�����ս������Ӧ���ϵ�Ȧ2
				{
					for(int j=0; j<flag.size(); j++){
						ArrayList<WENFA> ff=GO(flag,hash_flag.get(m),WEN_INIT);
						int linshi=FindInCA(GFZ,ff);
						if(linshi>=0){
							table_every.add(""+linshi);
							tianchong=true;
							break;
						}
					}
				}
				else if(hash_flag.get(m).equals("#"))
				{
					//System.out.println("woshiyigboaoa");
					for(int j=0; j<flag.size(); j++){
						WENFA wf_flag=flag.get(j);
						//System.out.println(wf_flag.wf[wf_flag.dort]);
						if(wf_flag.wf[wf_flag.dort].equals("#")&&wf_flag.next.indexOf("#")>=0)//��ӦȦ3
						{
							int linshi=FindG(WEN_INIT,wf_flag);
							if(linshi>0){
								table_every.add("r"+linshi);
								tianchong=true;
								break;
							}
							else if(linshi==0)
							{
								table_every.add("acc");
								tianchong=true;
								break;
							}
						}
					}
				}
				if(!tianchong)
					table_every.add("error");
			}
			table.add(table_every);
		}
		return table;
	}
	public int FinStri(String m, String []a)//ʵ�����ַ����������ҵ��ַ�����λ�ã��������򷵻�-1
	{
		int length=a.length;
		for(int i=0; i<length; i++){
			//System.out.println(a[i]);
			//System.out.println(m);
			if(a[i].equals(m)){
				return  i;
			}
		}
		return -1;
	}

	public void WriteFile(String filePath,String content) throws IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file,false);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();
		fw.close();
	}
	
	public void LR1_main(ArrayList<ArrayList<String>> table,ArrayList<str_value> w,HashMap<Integer,String> hash_flag, String [][]WEN_INIT,HashMap<String,F_enter> futable) throws IOException{//LR1�����㷨

		for(str_value v : w) {
			System.out.println(v.str + "||" +v.value);
		}
		
		System.out.println(hash_flag);
		
		for(Map.Entry<String, F_enter> map : futable.entrySet()) {
			System.out.println(map.getKey() + "||" + map.getValue().type +"__"+map.getValue().offset);
		}
		//System.out.println(content);
//		System.out.println("=============");
//		System.out.println(table);
//		System.out.println(w);
		//System.out.println(hash_flag);
//		System.out.println(WEN_INIT);
//		System.out.println(futable);
		
		
		ArrayList<String> three_address = new ArrayList<String>();//����ַ��������ʾ--��ʵ
		ArrayList<FOUR> four_address = new ArrayList<FOUR>();
		ArrayList<String> futable_copy =new ArrayList<String>();
		Stack<Integer> Sa1 = new Stack<Integer>(); //����ջ--��״̬
		Sa1.push(0);
		Stack<EVA> Sa2 = new Stack<EVA>();  //����ջ--���ַ���
		EVA intinumber=new EVA();
		intinumber.str="#";
		Sa2.push(intinumber);
		
		int zhen=0;//��ʾ����w���ĸ����ݣ�������ָ��
		String []map=new String[hash_flag.size()];  //Ŀ���Ƕ�Ӧ��λ��̬�����е������꣬��Ϊ��ϣ����ֻ�ܸ���key��values,����λ��̬�����Ǹ���key�����л��ֵ�
		for(int i=299; i<hash_flag.size()+299; i++)
		{
			map[i-299]=hash_flag.get(i);
			//System.out.println(hash_flag.get(i));
		}
		int M1_quad=0,M=0,fini=0;
		HashMap<String, String> shuju=new HashMap<String, String>();
		while((!Sa1.empty())&&zhen<w.size()){
			int y=FinStri(w.get(zhen).str, map);//���ַ����������ҵ��ַ�����λ��
			//System.out.println("::::::::::::::"+zhen+w.get(zhen).str);
			//System.out.println(w.get(zhen).str);
			if(y<0) {return;}
			String value=table.get(Sa1.peek()).get(y);//�õ���λ�����е�x��y������
			if(value.charAt(0)=='S')
			{
				int si=Integer.parseInt(value.substring(1));
				Sa1.push(si);
				EVA temp =new EVA();
				temp.str=w.get(zhen).str;
				temp.value=w.get(zhen).value;
				if(temp.str.equals("ID")){
					//System.out.println("guagua");
					//System.out.println(.size());
					if(futable.containsKey(temp.value)){
						//System.out.println(temp.type);
						temp.type=futable.get(temp.value).type;
					}
				}
				if(temp.str.equals("DO")){
					//System.out.println("guagua");
					//System.out.println(futable.size());
					M=three_address.size();
				}
				Sa2.push(temp);
				zhen++;
				//System.out.println("--->"+temp.str);
			}
			else if(value.charAt(0)=='r'){
				
				int si=Integer.parseInt(value.substring(1));
				int beita=WEN_INIT[si].length-2;
				EVA []flaglvxiya=new EVA[beita];
				for(int i=0; i<beita; i++)
				{
					Sa1.pop();
					flaglvxiya[beita-1-i]=Sa2.pop();
				}
				
				EVA temp =new EVA();
				//temp.str=w.get(zhen).str;
				//temp.value=w.get(zhen).value;
				switch (si){
				//������䣺
					case 1://D->D;D
						break;
					case 10://D->id:T
						F_enter mingming = new F_enter();
						mingming.type=flaglvxiya[2].type;
						mingming.offset=offset;
						futable.put(flaglvxiya[0].value, mingming);
						futable_copy.add(flaglvxiya[0].value);
						//temp.offset = offset;
						offset+=flaglvxiya[2].width;
						
						break;
					case 6://T->integer
						
						temp.type=flaglvxiya[0].str;
						temp.width=4;
						break;
					case 7://T->real
						temp.type=flaglvxiya[0].str;
						temp.width=8;
						
						break;
					case 8://T->array[INT]of T=====T->array C of T
						System.out.println("case 8--");
						temp.type=flaglvxiya[0].str+"("+flaglvxiya[1].value+","+flaglvxiya[3].type+")";
						temp.width = Integer.parseInt(flaglvxiya[1].value)*flaglvxiya[3].width;//ϣ����Ҫ����----------------------------------
						break;
					//��ֵ���
					case 53://C->[INT]
						System.out.println("case 8--");
						temp=flaglvxiya[1];
						break;
					case 12:
						//M=three_address.size();
						//��ӡ������ַ��
						if(flaglvxiya[0].offset==null){
							String sandizhi12 = flaglvxiya[0].value+":="+flaglvxiya[2].value;
							three_address.add(sandizhi12);
							FOUR sidi = new FOUR();
							sidi.op=":=";
							sidi.arg1=flaglvxiya[2].value;
							sidi.result=flaglvxiya[0].value;
							four_address.add(sidi);
						}
						else
						{
							String sandizhi12 = flaglvxiya[0].value+"["+flaglvxiya[0].offset+"]"+":="+flaglvxiya[2].value;
							three_address.add(sandizhi12);
							//��д--��Ϊû���õ�����
							FOUR fo=new FOUR();
							fo.op="[]=";
							fo.arg1=flaglvxiya[2].value;
							fo.arg2=flaglvxiya[0].offset;
							fo.result=flaglvxiya[0].value;
							four_address.add(fo);
						}
						
						break;
					case 13:
						F_enter ming13 = new F_enter();
						ming13.offset=offset;
						//����ת����������
						if(flaglvxiya[0].type.equals(flaglvxiya[2].type) ){
							ming13.type=flaglvxiya[0].type;
							if(ming13.type.equals("INTEGER")){
								offset+=4;
							}
							else
								offset+=8;
						}
						else
						{
							if(flaglvxiya[0].type.equals("INTEGER")&&flaglvxiya[2].type.equals("REAL")){
								ming13.type="REAL";
								offset+=8;
							}
							else if(flaglvxiya[2].type.equals("INTEGER")&&flaglvxiya[0].type.equals("REAL")){
								ming13.type="REAL";
								offset+=4;
							}
							else
							{
								Object[] e9={"�������","���ʹ���",0};
								System.out.println("��1784�г���");
								//err.addRow(e9);
								return ;
							}
						}
						String newtemp="temp_t"+offset;
						temp.value=newtemp;
						temp.type=ming13.type;
						futable.put(newtemp, ming13);
						futable_copy.add(newtemp);
						String sandizhi13 =newtemp+":=" +flaglvxiya[0].value+"+"+flaglvxiya[2].value;
						three_address.add(sandizhi13);
						FOUR sidi = new FOUR();
						sidi.op="+";
						sidi.arg1=flaglvxiya[2].value;
						sidi.arg2=flaglvxiya[0].value;
						sidi.result=newtemp;
						four_address.add(sidi);
						break;
					case 14:
						F_enter ming14 = new F_enter();
						ming14.offset=offset;
						//����ת����������
						if(flaglvxiya[0].type.equals(flaglvxiya[2].type) ){
							ming14.type=flaglvxiya[0].type;
							if(ming14.type.equals("INTEGER")){
								offset+=4;
							}
							else
								offset+=8;
						}
						else
						{
							if(flaglvxiya[0].type.equals("INTEGER")&&flaglvxiya[2].type.equals("REAL")){
								ming14.type="REAL";
								offset+=8;
							}
							else if(flaglvxiya[2].type.equals("INTEGER")&&flaglvxiya[0].type.equals("REAL")){
								ming14.type="REAL";
								offset+=4;
							}
							else
							{
								Object[] e9={"�������","���ʹ���",0};
								//err.addRow(e9);
								return ;
							}
						}
						String newtemp1="temp_ti"+offset;
						temp.value=newtemp1;
						temp.type=ming14.type;
						futable.put(newtemp1, ming14);
						futable_copy.add(newtemp1);
						String sandizhi14 =newtemp1+":=" +flaglvxiya[0].value+"*"+flaglvxiya[2].value;
						three_address.add(sandizhi14);
						FOUR sidi_ = new FOUR();
						sidi_.op="*";
						sidi_.arg1=flaglvxiya[2].value;
						sidi_.arg2=flaglvxiya[0].value;
						sidi_.result=newtemp1;
						four_address.add(sidi_);
						break;
					case 16:
						temp=flaglvxiya[1];
						break;
					case 17:
						if(flaglvxiya[0].offset==null){
							//temp.value = flaglvxiya[0].value;
							temp = flaglvxiya[0];
						}
						else{
							F_enter ming17 = new F_enter();
							ming17.offset=offset;
							//����ת����������
							
							ming17.type=flaglvxiya[0].type;
							if(ming17.type.equals("INTEGER")){
								offset+=4;
							}
							else
								offset+=8;
							String newtemp17="temp_tim"+offset;
							temp.value=newtemp17;
							temp.type=ming17.type;
							futable.put(newtemp17, ming17);
							futable_copy.add(newtemp17);
							String sandizhi17 =newtemp17+":=" +flaglvxiya[0].value+"["+flaglvxiya[0].offset+"]";
							three_address.add(sandizhi17);
							FOUR sidi17 = new FOUR();
							sidi17.op="=[]";
							sidi17.arg1=flaglvxiya[0].value;
							sidi17.arg2=flaglvxiya[0].offset;
							sidi17.result=newtemp17;
							four_address.add(sidi17);
						}
						break;
					case 18:
						F_enter ming17 = new F_enter();
						ming17.offset=offset;
						ming17.type="INTEGER";
						offset+=4;
						String newtemp17="temp_time"+offset;
						temp.value=newtemp17;
						temp.type=ming17.type;
						futable.put(newtemp17, ming17);
						futable_copy.add(newtemp17);
						String sandizhi17 =newtemp17+":=" +flaglvxiya[0].value;
						three_address.add(sandizhi17);
						FOUR sidi17 = new FOUR();
						sidi17.op=":=";
						sidi17.arg1=flaglvxiya[0].value;
						//sidi17.arg2=flaglvxiya[0].value;
						sidi17.result=newtemp17;
						four_address.add(sidi17);
						break;	
					case 19:
						F_enter ming19 = new F_enter();
						ming19.offset=offset;
						ming19.type="REAL";
						offset+=8;
						String newtemp19="temp_time_"+offset;
						temp.value=newtemp19;
						temp.type=ming19.type;
						futable.put(newtemp19, ming19);
						futable_copy.add(newtemp19);
						String sandizhi19 =newtemp19+":=" +flaglvxiya[0].value;
						three_address.add(sandizhi19);
						FOUR sidi19 = new FOUR();
						sidi19.op=":=";
						sidi19.arg1=flaglvxiya[0].value;
						//sidi17.arg2=flaglvxiya[0].value;
						sidi19.result=newtemp19;
						four_address.add(sidi19);
						break;
					case 21:
						F_enter ming21 = new F_enter();
						ming21.offset=offset;
						ming21.type="INTEGER";
						//if(ming12.)
						offset+=4;
						String newtemp21=new String();
						if(shuju.containsKey(flaglvxiya[0].type)){
							newtemp21=shuju.get(flaglvxiya[0].type);
							
						}
						else
						{
							newtemp21="temp_time_"+offset;
							shuju.put(flaglvxiya[0].type, newtemp21);
							futable.put(newtemp21, ming21);
							futable_copy.add(newtemp21);
						}
						temp.value=newtemp21;
						temp.type=ming21.type;
						
						ming21.type="INTEGER";
						offset+=4;
						newtemp21="temp_time_"+offset;
						temp.offset=newtemp21;
						futable.put(newtemp21, ming21);
						futable_copy.add(newtemp21);
						
						String sandizhi21 =temp.value+":=" + flaglvxiya[0].type;
						three_address.add(sandizhi21);
						sandizhi21 = temp.offset+":="+flaglvxiya[0].value;//W,��Ϊ��int ����w=4
						three_address.add(sandizhi21);
						
						FOUR sidi21 = new FOUR();
						sidi21.op=":=";
						sidi21.arg1=flaglvxiya[0].type;
						sidi21.result=temp.value;
						four_address.add(sidi21);
						sidi21.op=":=";
						sidi21.arg1=flaglvxiya[0].value;
						sidi21.result=temp.offset;
						four_address.add(sidi21);
						
						break;
					case 22:
						//temp.value=flaglvxiya[0].value;
						temp=flaglvxiya[0];
						temp.offset=null;
						break;
					case 24:
						temp.type=flaglvxiya[0].value;
						temp.value=flaglvxiya[2].value;
						break;
					case 34:
						M1_quad=three_address.size();
						temp.truelist=new ArrayList<Integer>();
						temp.falselist=new ArrayList<Integer>();
						temp.truelist.add(three_address.size());
						temp.falselist.add(three_address.size()+1);
						String sandizhi34 ="if "+flaglvxiya[0].value+" < "+flaglvxiya[2].value+" goto ";
						three_address.add(sandizhi34);
						
						FOUR sidi34 = new FOUR();
						sidi34.op="<";
						sidi34.arg1=flaglvxiya[0].value;
						sidi34.arg2=flaglvxiya[2].value;
						four_address.add(sidi34);
						
						FOUR sidi3 = new FOUR();
						sidi3.result=new String();
						four_address.add(sidi3);
						
						sandizhi34 ="goto ";
						three_address.add(sandizhi34);
						break;
					case 43:
						//M=three_address.size();
						for(int i=0;i<flaglvxiya[3].truelist.size();i++){
							String linshi=three_address.get(flaglvxiya[3].truelist.get(i));
							three_address.set(flaglvxiya[3].truelist.get(i), linshi+M1_quad);
							FOUR ji2016=four_address.get(flaglvxiya[3].truelist.get(i));
							ji2016.result=""+M1_quad;
							four_address.set(flaglvxiya[3].truelist.get(i), ji2016);
						}
						//flaglvxiya[3].truelist.add(three_address.size()-2);
						for(int i=0;i<flaglvxiya[1].truelist.size();i++){
							String linshi=three_address.get(flaglvxiya[1].truelist.get(i));
							three_address.set(flaglvxiya[1].truelist.get(i), linshi+M);
							
							FOUR ji2016=four_address.get(flaglvxiya[1].truelist.get(i));
							ji2016.result=""+M;
							four_address.set(flaglvxiya[1].truelist.get(i), ji2016);
							
						}
						temp.truelist=flaglvxiya[1].falselist;
						String sandizhi43 ="goto "+M1_quad;
						//M1_quad.remove(M1_quad.size()-1);
						three_address.add(sandizhi43);
						
						FOUR jin2016=new FOUR();
						jin2016.result=""+M1_quad;
						four_address.add(jin2016);
						fini=three_address.size();
						for(int i=0;i<flaglvxiya[1].falselist.size();i++){
							String linshi=three_address.get(flaglvxiya[1].falselist.get(i));
							three_address.set(flaglvxiya[1].falselist.get(i), linshi+three_address.size());
							
							FOUR ji2016=four_address.get(flaglvxiya[1].falselist.get(i));
							ji2016.result=""+three_address.size();
							four_address.set(flaglvxiya[1].falselist.get(i), ji2016);
						}
						break;
					case 44:
						if(flaglvxiya[0].truelist==null){
							flaglvxiya[0].truelist=new ArrayList<Integer>();
						}
						for(int i=0;i<flaglvxiya[0].truelist.size();i++){
							String linshi=three_address.get(flaglvxiya[0].truelist.get(i));
							three_address.set(flaglvxiya[0].truelist.get(i), linshi+M);
							
							FOUR ji2016=four_address.get(flaglvxiya[0].truelist.get(i));
							ji2016.result=""+M;
							four_address.set(flaglvxiya[0].truelist.get(i), ji2016);
						}
						//if(flaglvxiya[0].truelist==null){
							//flaglvxiya[0].truelist=new ArrayList<Integer>();
						//}
						//flaglvxiya[0].truelist.add(three_address.size()-1);
						temp.truelist=flaglvxiya[0].truelist;
						break;
					case 52://------------
						//M=three_address.size();
						temp.truelist=flaglvxiya[1].truelist;
						break;
					case 45:
						temp.truelist=flaglvxiya[0].truelist;
						break;
					default:
						break;
				}
				temp.str=WEN_INIT[si][0];
				Sa2.push(temp);
				int a=FinStri(WEN_INIT[si][0], map);//
				Sa1.push(Integer.parseInt(table.get(Sa1.peek()).get(a)));
				//��ӡ����ʽ
				
				String mlll=WEN_INIT[si][0]+"->";
				for(int kk=1;kk<WEN_INIT[si].length-1;kk++)
				{
					mlll+=WEN_INIT[si][kk]+' ';
				}
				
				System.out.println("��Լ��"+mlll);
			}
			else if(value.equals("acc"))
			{
				String mlll=WEN_INIT[0][0]+"->";
				for(int kk=1;kk<WEN_INIT[0].length-1;kk++)
				{
					mlll+=WEN_INIT[0][kk]+' ';
				}
				
				System.out.println("��Լ��"+mlll);
				System.out.println("����ַ�룺");
				
				for(int i=0;i<three_address.size();i++){
					//System.out.println(""+i+":"+three_address.get(i));
					FOUR skl=four_address.get(i);
					System.out.println(skl.op+" || " +skl.arg1+" || "+skl.arg2+" || "+skl.result);
				}
				return;
			}
			else
			{
				//System.out.println(w.get(zhen));
				System.out.println("--------------------ERROR----------------------");
				return;
			}
		}
		
	}
	
		
	public static void INIT(HashMap hash)
	{
		hash.put("and",1);
		hash.put("array",2);
		hash.put("begin",3);
		hash.put("case",4);
		hash.put("const",5);
		hash.put("div",6);
		hash.put("do",7);
		hash.put("downto",8);
		hash.put("else",9);
		hash.put("end",10);
		hash.put("file",11);
		hash.put("for",12);
		hash.put("function",13);
		hash.put("goto",14);
		hash.put("if",15);
		hash.put("in",16);
		hash.put("label",17);
		hash.put("mod",18);
		hash.put("nil",19);
		hash.put("not",20);
		hash.put("of",21);
		hash.put("or",22);
		hash.put("packed",23);
		hash.put("procedure",24);
		hash.put("program",25);
		hash.put("record",26);
		hash.put("repeat",27);
		hash.put("set",28);
		hash.put("then",29);
		hash.put("to",30);
		hash.put("type",31);
		hash.put("until",32);
		hash.put("var",33);
		hash.put("while",34);
		hash.put("with",35);
		hash.put("+",41);
		hash.put("-",42);
		hash.put("*",43);
		hash.put("/",44);
		hash.put("=",45);
		hash.put("<",46);
		hash.put(">",47);
		hash.put(">=",48);
		hash.put("<=",49);
		hash.put("<>",50);
		hash.put("(",51);
		hash.put(")",52);
		hash.put(",",53);
		hash.put("��",54);
		hash.put(".",55);
		hash.put("..",56);
		hash.put(":",57);
		hash.put(":=",58);
		hash.put(";",59);
		hash.put("^",60);
		hash.put("**",61);
		hash.put("[",62);
		hash.put("]",63);
		hash.put("'",64);
		hash.put("true", 65);
		hash.put("false", 66);
		hash.put("step", 67);
		hash.put("integer", 68);
		hash.put("real",74);
		hash.put("return", 69);
		hash.put("read", 70);
		hash.put("readln", 71);
		hash.put("write", 72);
		hash.put("writeln", 73);
		hash.put("begin", 74);
		hash.put("end", 75);
	}
	public static void main(String args[])
	{
		new testMain();		
	}
}

