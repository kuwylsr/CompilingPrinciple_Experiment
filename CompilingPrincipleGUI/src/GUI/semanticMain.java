package GUI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import Lexical_Analysis2.Analysis;



public class semanticMain {
	
	static int tempNum = 0;

	
	
	public static int offset=0;//ƫ�����������ð�

	public static int FinStri(String m, String []a)//ʵ�����ַ����������ҵ��ַ�����λ�ã��������򷵻�-1
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
	
	public static ArrayList<str_value> getW(String text){
		ArrayList<str_value> list = new ArrayList<>();
		String[] tokens = text.split("\n");
		for(String token : tokens) {
			str_value s = new str_value();
			String str = token.split("\t")[2].substring(1).toUpperCase();
			String value = token.split("\t")[3];
			String value1 = value.substring(0, value.length()-2);
			if(value1.equals("_")) {
				value1 = "";
			}
			s.str = str;
			s.value = value1;
			list.add(s);
		}
		str_value s = new str_value();
		s.str = "#";
		s.value = "";
		list.add(s);
		return list;
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
	
	public static ArrayList<ArrayList<String>> getChart() throws IOException{
		FileReader r = new FileReader("E:\\eclipse-workspace\\CompilingPrincipleLab3\\src\\mine\\chart.txt");
		ArrayList<ArrayList<String>> table = new ArrayList<>();
		BufferedReader br = new BufferedReader(r);
		String line = null;
		
		while((line = br.readLine()) != null) {
			ArrayList<String> l = new ArrayList<>();
			String[] s = line.split("\t");
			for(String temp : s) {
				l.add(temp);
			}
			table.add(l);
		}
		br.close();
		r.close();
		return table;
	}
	
	public static Map<Integer,String> getHash() throws IOException{
		FileReader r = new FileReader("E:\\eclipse-workspace\\CompilingPrincipleLab3\\src\\mine\\hash.txt");
		Map<Integer,String> hash = new HashMap<>();
		BufferedReader br = new BufferedReader(r);
		String line = null;
		
		while((line = br.readLine()) != null) {
			Integer key = Integer.parseInt(line.split(" ")[0]);
			String value = line.split(" ")[1];
			hash.put(key, value);
		}
		br.close();
		r.close();
		return hash;
	}
	
	public static void main(String args[])
	{
		String text;
		try {
			text = ReadFile("src/mine/example1.txt")+" ";
			Map<String,String> result = new Analysis().analysis(text);
			String tokens = (result.get("tokens"));
			ArrayList<str_value> w = getW(tokens);
			
			ArrayList<ArrayList<String>> table = getChart();
			
			Map<Integer,String> hash_flag = getHash();
			
			HashMap<String,symbol> futable=new HashMap<String,symbol>();
			semanticFunc(table, w, (HashMap<Integer, String>) hash_flag, grammer.WF_INIT, futable);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	// table ������
	// w tokens
	// hash_flag �洢�ķ��г��ֵķ��ż������
	// WEN_INIT �ķ�
	// futable ���ַ���
	// err ����
	public static Map<String,String> semanticFunc(ArrayList<ArrayList<String>> table,ArrayList<str_value> w,HashMap<Integer,String> hash_flag, String [][]WEN_INIT,HashMap<String,symbol> futable){//LR1�����㷨
		
		ArrayList<String> three_address = new ArrayList<String>();//����ַ��������ʾ--��ʵ
		ArrayList<FourAddress> FourAddress_address = new ArrayList<FourAddress>();
		Stack<Integer> stateStack = new Stack<Integer>(); //����ջ--��״̬
		stateStack.push(0);
		Stack<Node> symbolStack = new Stack<Node>();  //����ջ--���ַ���
		Node intinumber=new Node();
		String error = "";
		Map<String,String> content = new HashMap<>();
		intinumber.str="#";
		symbolStack.push(intinumber);
		
		int zhen=0;//��ʾ����w���ĸ����ݣ�������ָ��
		String []map=new String[hash_flag.size()];  //Ŀ���Ƕ�Ӧ��λ��̬�����е������꣬��Ϊ��ϣ����ֻ�ܸ���key��values,����λ��̬�����Ǹ���key�����л��ֵ�
		for(int i=299; i<hash_flag.size()+299; i++)
		{
			map[i-299]=hash_flag.get(i);
		}
		int M1_quad=0,M2_quad=0;
		HashMap<String, String> shuju=new HashMap<String, String>();
		while((!stateStack.empty())&&zhen<w.size()){
			int y=FinStri(w.get(zhen).str, map);//���ַ����������ҵ��ַ�����λ��
			if(y<0) {return null;}
			String value=table.get(stateStack.peek()).get(y);//�õ���λ�����е�x��y������
			if(value.charAt(0)=='S')
			{
				int si=Integer.parseInt(value.substring(1));
				stateStack.push(si);
				Node temp =new Node();
				temp.str=w.get(zhen).str;
				temp.value=w.get(zhen).value;
				if(temp.str.equals("ID")){
					if(futable.containsKey(temp.value)){
						temp.type=futable.get(temp.value).type;
					}
				}
				if(temp.str.equals("DO")){
					M2_quad=three_address.size();
				}
				symbolStack.push(temp);
				zhen++;
			}
			else if(value.charAt(0)=='r'){
				
				int si=Integer.parseInt(value.substring(1));
				int beita=WEN_INIT[si].length-2;
				Node []rightNodes=new Node[beita];
				for(int i=0; i<beita; i++)
				{
					stateStack.pop();
					rightNodes[beita-1-i]=symbolStack.pop();
				}

				Node temp =new Node();
				switch (si){
				//������䣺
					case 1://D->D;D
						break;
					case 10:
						//D->id:T {enter(id.name,T.type,offset);
						//         offset := offset + T.width}
						symbol s1 = new symbol();
						s1.type=rightNodes[2].type;
						s1.offset=offset;
						if(futable.containsKey(rightNodes[0].value)) {
							error = error + "���������ظ���"+"\n";
							content.put("e", error);
							System.out.println("���������ظ���");
							return content;
						}else {
							futable.put(rightNodes[0].value, s1);
						}
						offset+=rightNodes[2].width;
						
						break;
					case 6:
						//T->integer {T.type := integer; T.width := 4}
						temp.type=rightNodes[0].str;
						temp.width=4;
						break;
					case 7:
						//T->real {T.type = real; T.width := 8}
						temp.type=rightNodes[0].str;
						temp.width=8;	
						break;
					case 8:
						//T->array[INT]of T {T.type := array(num.val , T1.type);
						//                   T.width := num.val * T1.width}
						temp.type=rightNodes[0].str+"("+rightNodes[1].value+","+rightNodes[3].type+")";
						temp.width = Integer.parseInt(rightNodes[1].value)*rightNodes[3].width;
						break;
					//��ֵ���
					case 53://C->[INT]
						temp=rightNodes[1];
						break;
					case 12:
						// S �� Left := E
						if(rightNodes[0].offset==null){
							
							if(futable.containsKey(rightNodes[0].value)) {
								System.out.println(rightNodes[0].value);
								String threeAddress1 = rightNodes[0].value+":="+rightNodes[2].value;
								three_address.add(threeAddress1);
								FourAddress fourAddress1 = new FourAddress();
								fourAddress1.op=":=";
								fourAddress1.arg1=rightNodes[2].value;
								fourAddress1.result=rightNodes[0].value;
								FourAddress_address.add(fourAddress1);
							}else {
								error = error + "����δ���壡" +"\n";
								System.out.println("����δ���壡");
								content.put("e", error);
								return content;
							}
							
						}
						else
						{
							String threeAddress2 = rightNodes[0].value+"["+rightNodes[0].offset+"]"+":="+rightNodes[2].value;
							three_address.add(threeAddress2);
							FourAddress fourAddress2=new FourAddress();
							fourAddress2.op="[]=";
							fourAddress2.arg1=rightNodes[2].value;
							fourAddress2.arg2=rightNodes[0].offset;
							fourAddress2.result=rightNodes[0].value;
							FourAddress_address.add(fourAddress2);
						}
						
						break;
					case 13:
						//E �� E + E {E.addr = newtemp;
						//           emit(E.addr = E1.addr + E2.addr) }
						symbol s2 = new symbol();
						s2.offset=offset;
						//����ת����������
						if(rightNodes[0].type.equals(rightNodes[2].type) ){
							s2.type=rightNodes[0].type;
							if(s2.type.equals("INTEGER")){
								offset+=4;
							}
							else
								offset+=8;
						}
						else
						{
							if(rightNodes[0].type.equals("INTEGER")&&rightNodes[2].type.equals("REAL")){
								s2.type="REAL";
								offset+=8;
							}
							else if(rightNodes[2].type.equals("INTEGER")&&rightNodes[0].type.equals("REAL")){
								s2.type="REAL";
								offset+=4;
							}
							else
							{
								error = error + "�������ʹ���"+"\n";
								System.out.println("�������ʹ���");
								content.put("e", error);
								return content;
							}
						}
						//String newtemp="temp_t"+offset;
						String newtemp = "t"+tempNum;
						tempNum ++;
						temp.value=newtemp;
						temp.type=s2.type;
						futable.put(newtemp, s2);

						String threeAddress3 =newtemp+":=" +rightNodes[0].value+"+"+rightNodes[2].value;
						three_address.add(threeAddress3);
						FourAddress fourAddress3 = new FourAddress();
						fourAddress3.op="+";
						fourAddress3.arg1=rightNodes[2].value;
						fourAddress3.arg2=rightNodes[0].value;
						fourAddress3.result=newtemp;
						FourAddress_address.add(fourAddress3);
						break;
					case 14:
						//E �� E * E {E.addr = newtemp;
						//           emit(E.addr = E1.addr * E2.addr) }
						symbol s3 = new symbol();
						s3.offset=offset;
						//����ת����������
						if(rightNodes[0].type.equals(rightNodes[2].type) ){
							s3.type=rightNodes[0].type;
							if(s3.type.equals("INTEGER")){
								offset+=4;
							}
							else
								offset+=8;
						}
						else
						{
							if(rightNodes[0].type.equals("INTEGER")&&rightNodes[2].type.equals("REAL")){
								s3.type="REAL";
								offset+=8;
							}
							else if(rightNodes[2].type.equals("INTEGER")&&rightNodes[0].type.equals("REAL")){
								s3.type="REAL";
								offset+=4;
							}
							else
							{
								error = error + "�������ʹ���"+"\n";
								System.out.println("�������ʹ���");
								content.put("e", error);
								return content;
							}
						}
						//String newtemp1="temp_ti"+offset;
						String newtemp1 = "t" + tempNum;
						tempNum ++;
						temp.value=newtemp1;
						temp.type=s3.type;
						futable.put(newtemp1, s3);
						//futable_copy.add(newtemp1);
						String threeAddress4 =newtemp1+":=" +rightNodes[0].value+"*"+rightNodes[2].value;
						three_address.add(threeAddress4);
						FourAddress fourAddress4 = new FourAddress();
						fourAddress4.op="*";
						fourAddress4.arg1=rightNodes[2].value;
						fourAddress4.arg2=rightNodes[0].value;
						fourAddress4.result=newtemp1;
						FourAddress_address.add(fourAddress4);
						break;
					case 16:
						// E �� ( E ){E.addr := E1.addr}
						temp=rightNodes[1];
						break;
					case 17:
						//E �� Left
						if(rightNodes[0].offset==null){
							temp = rightNodes[0];
						}
						else{
							symbol s4 = new symbol();
							s4.offset=offset;
							//����ת����������
							
							s4.type=rightNodes[0].type;
							if(s4.type.equals("INTEGER")){
								offset+=4;
							}
							else
								offset+=8;
							//String newtemp17="temp_tim"+offset;
							String newtemp17 = "t" + tempNum;
							tempNum ++;
							temp.value=newtemp17;
							temp.type=s4.type;
							futable.put(newtemp17, s4);
							//futable_copy.add(newtemp17);
							String threeAddress5 =newtemp17+":=" +rightNodes[0].value+"["+rightNodes[0].offset+"]";
							three_address.add(threeAddress5);
							FourAddress fourAddress5 = new FourAddress();
							fourAddress5.op="=[]";
							fourAddress5.arg1=rightNodes[0].value;
							fourAddress5.arg2=rightNodes[0].offset;
							fourAddress5.result=newtemp17;
							FourAddress_address.add(fourAddress5);
						}
						break;
					case 18:
						// E -> INT
						symbol s5 = new symbol();
						s5.offset=offset;
						s5.type="INTEGER";
						offset+=4;
						String newtemp17="t"+tempNum;
						tempNum++;
						temp.value=newtemp17;
						temp.type=s5.type;
						futable.put(newtemp17, s5);
						
						String threeAddress6 =newtemp17+":=" +rightNodes[0].value;
						three_address.add(threeAddress6);
						FourAddress fourAddress6 = new FourAddress();
						fourAddress6.op=":=";
						fourAddress6.arg1=rightNodes[0].value;
						fourAddress6.result=newtemp17;
						FourAddress_address.add(fourAddress6);
						break;	
					case 19:
						// E �� real {T.type := real; 
						//           T.width := 8}
						symbol s6 = new symbol();
						s6.offset=offset;
						s6.type="REAL";
						offset+=8;
						String newtemp19="t"+tempNum;
						tempNum++;
						temp.value=newtemp19;
						temp.type=s6.type;
						futable.put(newtemp19, s6);
						//futable_copy.add(newtemp19);
						String threeAddress7 =newtemp19+":=" +rightNodes[0].value;
						three_address.add(threeAddress7);
						FourAddress fourAddress7 = new FourAddress();
						fourAddress7.op=":=";
						fourAddress7.arg1=rightNodes[0].value;
						//sidi17.arg2=flaglvxiya[0].value;
						fourAddress7.result=newtemp19;
						FourAddress_address.add(fourAddress7);
						break;
					case 21:
						//Left �� EList ]
						//{Left.addr = newtemp;
						// emit (Left.addr = Elist.array -c)
						// L.offset = newtemp;
						// emit(L.offset = w* EList.addr)}
						symbol s7 = new symbol();
						s7.offset=offset;
						s7.type="INTEGER";
						offset+=4;
						String newtemp21=new String();
						if(shuju.containsKey(rightNodes[0].type)){
							newtemp21=shuju.get(rightNodes[0].type);
							
						}
						else
						{
							newtemp21="t"+tempNum;
							tempNum++;
							shuju.put(rightNodes[0].type, newtemp21);
							futable.put(newtemp21, s7);
							//futable_copy.add(newtemp21);
						}
						temp.value=newtemp21;
						temp.type=s7.type;
						
						s7.type="INTEGER";
						offset+=4;
						newtemp21="t"+tempNum;
						tempNum++;
						temp.offset=newtemp21;
						futable.put(newtemp21, s7);
						//futable_copy.add(newtemp21);
						
						String threeAddress8 =temp.value+":=" + rightNodes[0].type;
						three_address.add(threeAddress8);
						threeAddress8 = temp.offset+":="+rightNodes[0].value;//W,��Ϊ��int ����w=4
						three_address.add(threeAddress8);
						
						FourAddress fourAddress8 = new FourAddress();
						fourAddress8.op=":=";
						fourAddress8.arg1=rightNodes[0].type;
						fourAddress8.result=temp.value;
						FourAddress_address.add(fourAddress8);
						fourAddress8.op=":=";
						fourAddress8.arg1=rightNodes[0].value;
						fourAddress8.result=temp.offset;
						FourAddress_address.add(fourAddress8);
						
						break;
					case 22:
						//Left �� id
						// {L.addr = id.addr
						//  L.offset = null}
						temp=rightNodes[0];
						temp.offset=null;
						break;
					case 24:
						//EList �� id [ E
						//{Elist.addr = E.addr;
						// Elist.ndim = 1:
						// Elist.array = id.addr}
						temp.type=rightNodes[0].value;
						temp.value=rightNodes[2].value;
						break;
					case 34:
						//B �� E < E
						//{B.truelist:=makelist(nextquad);
						// B.falselist:=makelist(nextquad+1);
						// gencode('if'E1.addr < E2.addr 'goto-');
						// gencode('goto-')}
						M1_quad=three_address.size();
						temp.truelist=new ArrayList<Integer>();
						temp.falselist=new ArrayList<Integer>();
						temp.truelist.add(three_address.size());
						temp.falselist.add(three_address.size()+1);
						String threeAddress9 ="if "+rightNodes[0].value+" < "+rightNodes[2].value+" goto ";
						three_address.add(threeAddress9);
						
						FourAddress fourAddress9 = new FourAddress();
						fourAddress9.op="<";
						fourAddress9.arg1=rightNodes[0].value;
						fourAddress9.arg2=rightNodes[2].value;
						FourAddress_address.add(fourAddress9);
						
						FourAddress fourAddress10 = new FourAddress();
						fourAddress10.result=new String();
						FourAddress_address.add(fourAddress10);
						
						threeAddress9 ="goto ";
						three_address.add(threeAddress9);
						break;
					case 43:
						//S �� while M1B do M2S1
						//{backpatch(B.truelist,M2.quad);
						// backpatch(S1.nextlist,M1.quad);
						// S.nextlist:=B.falselist;
						// gencode('goto'M1.quad)}
						for(int i=0;i<rightNodes[3].truelist.size();i++){
							String threeAddress11=three_address.get(rightNodes[3].truelist.get(i));
							three_address.set(rightNodes[3].truelist.get(i), threeAddress11+M1_quad);
							
							FourAddress fourAddress11=FourAddress_address.get(rightNodes[3].truelist.get(i));
							fourAddress11.result=""+(M1_quad);
							FourAddress_address.set(rightNodes[3].truelist.get(i), fourAddress11);
						}
						
						for(int i=0;i<rightNodes[1].truelist.size();i++){
							String threeAddress11=three_address.get(rightNodes[1].truelist.get(i));
							three_address.set(rightNodes[1].truelist.get(i), threeAddress11+M2_quad);
							
							FourAddress fourAddress11=FourAddress_address.get(rightNodes[1].truelist.get(i));
							fourAddress11.result=""+M2_quad;
							FourAddress_address.set(rightNodes[1].truelist.get(i), fourAddress11);
							
						}
						temp.truelist=rightNodes[1].falselist;
						String threeAddress12 ="goto "+M1_quad;
						three_address.add(threeAddress12);
						FourAddress fourAddress12=new FourAddress();
						fourAddress12.result=""+M1_quad;
						FourAddress_address.add(fourAddress12);
						
						for(int i=0;i<rightNodes[1].falselist.size();i++){
							String threeAddress13=three_address.get(rightNodes[1].falselist.get(i));
							three_address.set(rightNodes[1].falselist.get(i), threeAddress13+three_address.size());
							
							FourAddress fourAddress13=FourAddress_address.get(rightNodes[1].falselist.get(i));
							fourAddress13.result=""+three_address.size();
							FourAddress_address.set(rightNodes[1].falselist.get(i), fourAddress13);
						}
						break;
					case 41:
						//S -> if B then MS1
						//backpatch(B.truelist,M.quad);
						//S.nextlist = merge(B.falselist,S1.nextlist)
						for(int i=0;i<rightNodes[1].truelist.size();i++){
							String threeAddress11=three_address.get(rightNodes[1].truelist.get(i));
							three_address.set(rightNodes[1].truelist.get(i), threeAddress11+(M1_quad-1));
							
							FourAddress fourAddress11=FourAddress_address.get(rightNodes[1].truelist.get(i));
							fourAddress11.result=""+(M1_quad-1);
							FourAddress_address.set(rightNodes[1].truelist.get(i), fourAddress11);
						}
						
						for(int i=0;i<rightNodes[1].falselist.size();i++){
							String threeAddress13=three_address.get(rightNodes[1].falselist.get(i));
							three_address.set(rightNodes[1].falselist.get(i), threeAddress13+three_address.size());
							
							FourAddress fourAddress13=FourAddress_address.get(rightNodes[1].falselist.get(i));
							fourAddress13.result=""+three_address.size();
							FourAddress_address.set(rightNodes[1].falselist.get(i), fourAddress13);
						}
						break;
					case 44:
						//L �� L ; MS
						//{ backpatch(S1.nextlist,M.quad);
						//  S.nextlist = S2.nextlist }
						if(rightNodes[0].truelist==null){
							rightNodes[0].truelist=new ArrayList<Integer>();
						}
						for(int i=0;i<rightNodes[0].truelist.size();i++){
							String threeAddress14=three_address.get(rightNodes[0].truelist.get(i));
							three_address.set(rightNodes[0].truelist.get(i), threeAddress14+M2_quad);
							
							FourAddress fourAddress14 = FourAddress_address.get(rightNodes[0].truelist.get(i));
							fourAddress14.result=""+M2_quad;
							FourAddress_address.set(rightNodes[0].truelist.get(i), fourAddress14);
						}
						temp.truelist=rightNodes[0].truelist;
						break;
					case 52:
						temp.truelist=rightNodes[1].truelist;
						break;
					case 45:
						temp.truelist=rightNodes[0].truelist;
						break;
					default:
						break;
				}
				temp.str=WEN_INIT[si][0];
				symbolStack.push(temp);
				int a=FinStri(WEN_INIT[si][0], map);
				stateStack.push(Integer.parseInt(table.get(stateStack.peek()).get(a)));
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
				String content3 = "";
				String content4 = "";
				String symbolTable = "";
				for(int i=0;i<three_address.size();i++){
					content3 =content3+i+": " +three_address.get(i)+"\n";
					FourAddress skl=FourAddress_address.get(i);
					content4 = content4 +i+": " + skl.op+" , " +skl.arg1+" , "+skl.arg2+" , "+skl.result + "\n";
					System.out.println(skl.op+" , " +skl.arg1+" , "+skl.arg2+" , "+skl.result);
				}
				
				for(Map.Entry<String, symbol> map1 : futable.entrySet()) {
					symbolTable = symbolTable + map1.getKey() + "\t" + map1.getValue().type +"\t"+map1.getValue().offset+"\n";
					System.out.println(map1.getKey() + "\t" + map1.getValue().type +"\t"+map1.getValue().offset);
				}
				
				content.put("3", content3);
				content.put("4", content4);
				content.put("t", symbolTable);
				return content;
			}
			else
			{
				//System.out.println(w.get(zhen));
				System.out.println("--------------------ERROR----------------------");
				return null;
			}
		}
		return null;
		
	}	
	
}
