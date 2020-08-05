package Lexical_Analysis2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Analysis{
	
	static int row = 1;
	
	public String formLine(String name,String TypeString,String value) {
		return "row:"+row + "\t"+name + "\t" + "<" + TypeString+ "\t" + value + ">" + "\r\n";
	}
	public String illegalOutput(String s,String type) {
		return "row:"+row + "\t"+s+"\t"+type+"\n";
	}
	
	public Map<String,String> analysis(String content) {
		//symbolTable table = new symbolTable();
		symbolTable2 table = new symbolTable2();
		ArrayList<Identifier> Identifiers = table.getIdentifiers();
		StringBuilder tokens = new StringBuilder();
		StringBuilder illegal = new StringBuilder();
		StringBuilder sTemp = new StringBuilder();
		char ch;
		for(int i = 0;i<content.length();i++) {
				ch = content.charAt(i);
				if(Character.isWhitespace(ch)) {
					if(ch == '\n') {
						row ++;
					}
					continue;
				}
				//判定是否为标识符或是关键字
				if(Character.isLetter(ch)||ch == '_') {
					sTemp.append(ch);
					if(++i < content.length()) ch = content.charAt(i);
					else {
						//tokens.append(formLine(sTemp.toString(), "IDN", sTemp.toString()));
						tokens.append(formLine(sTemp.toString(), "ID", sTemp.toString()));
					}
					while(Character.isLetter(ch) || Character.isDigit(ch)) {//一直读直到既不是数字也不是字母
						sTemp.append(ch);
						if(++i < content.length()) ch = content.charAt(i);
						else break;
					}
					i--;
					Lexical_Analysis2.symbolTable2.keyWord k = table.isKeyWord(sTemp.toString());
					if(k != null) { //为关键字
						tokens.append(formLine(k.getName(), k.getTypeString(), "_"));
					}else {//若不是关键字，则为标识符
						int index = table.getIndex();
						Identifier d = new Identifier(sTemp.toString(), String.valueOf(index));
						Identifiers.add(d);
						//tokens.append(formLine(sTemp.toString(), "IDN", sTemp.toString()));
						tokens.append(formLine(sTemp.toString(), "ID", sTemp.toString()));
					}
					sTemp.delete(0, sTemp.length());
				}
				//判定是否为常数
				
				else if(Character.isDigit(ch)) {
					int flag = 0;
					while(Character.isDigit(ch)) {
						sTemp.append(ch);
						if(++i < content.length()) ch = content.charAt(i);
					}
					if(ch == '.') { //可能为浮点数
						flag = 1;
						sTemp.append(ch);
						if(++i < content.length()) ch = content.charAt(i);
						else {
							illegal.append(illegalOutput(sTemp.toString(),"NumError"));
							continue;
						}
						if(Character.isDigit(ch)) {
							while(Character.isDigit(ch)) {
								sTemp.append(ch);
								if(++i < content.length()) ch = content.charAt(i);
								else break;
							}
							i--;
						}else {
							illegal.append(illegalOutput(sTemp.toString(),"NumError"));
							sTemp.delete(0, sTemp.length());
							i--;
							continue;
						}
					}
					if(ch == 'E') {
						flag = 1;
						sTemp.append(ch);
						if(++i < content.length()) ch = content.charAt(i);
						else {
							illegal.append(illegalOutput(sTemp.toString(),"NumError"));
							continue;
						}
						if(ch == '+'||ch == '-') {
							sTemp.append(ch);
							if(++i < content.length()) ch = content.charAt(i);
							else {
								illegal.append(illegalOutput(sTemp.toString(),"NumError"));
								continue;
							}
						}
						if(Character.isDigit(ch)) {
							while(Character.isDigit(ch)) {
								sTemp.append(ch);
								if(++i < content.length()) ch = content.charAt(i);
								else break;
							}
							i--;
						}else {
							illegal.append(illegalOutput(sTemp.toString(),"NumError"));
							sTemp.delete(0, sTemp.length());
							i--;
							continue;
						}
					}
					if(flag == 0){
						i--;
					}
						
					//tokens.append(formLine(sTemp.toString(), "CONST", sTemp.toString()));
					tokens.append(formLine(sTemp.toString(), "INT", sTemp.toString()));
					sTemp.delete(0, sTemp.length());
				}
				//判定是否为运算符
				else if(table.isOperator(String.valueOf(ch)) != null) { //判定为运算符
					sTemp.append(ch);
					Lexical_Analysis2.symbolTable2.operator o = table.isOperator(String.valueOf(ch));
					if(o.getName().equals("/")) { //运算符除号可能也为注释的开始符号
						if(++i < content.length()) ch = content.charAt(i);
						else { //若没有之后的符号，则判定为除号
							tokens.append(formLine(o.getName(), o.getTypeString(), "_"));
							break;
						}
						//继续判定是否为注释
						if(ch == '*') {//只可能为注释，否则出错
							sTemp.append(ch);
							while(true) {
								if(++i < content.length()) ch = content.charAt(i);
								else {
									illegal.append(illegalOutput(sTemp.toString().trim(),"NotesError"));
									break;
								}
								sTemp.append(ch);
								if(ch == '*') {
									if(++i < content.length()) ch = content.charAt(i);
									else {
										illegal.append(illegalOutput(sTemp.toString(),"NotesError"));
										break;
									}
									sTemp.append(ch);
									if(ch == '/') {
										tokens.append("row:"+row + "\t"+"Notes: "+sTemp+"\n");
										break;
									}
								}
							}
						}else { //不可能为注释，为单独的除号
							tokens.append(formLine(o.getName(), o.getTypeString(), "_"));
							i--;
						}
						sTemp.delete(0, sTemp.length());
					}else { //若运算符不是除号
						if(++i < content.length()) ch = content.charAt(i);
						else {
							tokens.append(formLine(o.getName(), o.getTypeString(), "_"));
							break;
						}
						//System.out.println(sTemp+String.valueOf(ch));
						Lexical_Analysis2.symbolTable2.operator oo = table.isOperator(sTemp+String.valueOf(ch)); //看两个字符连接在一起是否为新的一个运算符
						if(oo != null) {
							tokens.append(formLine(oo.getName(), oo.getTypeString(), "_"));
						}else {//单独的运算符进行输出
							tokens.append(formLine(o.getName(), o.getTypeString(), "_"));
							i--;
						}
						sTemp.delete(0, sTemp.length());
					}
				}
				//判定是否为分解符
				else if(table.isDelimiter(String.valueOf(ch)) != null) {
					Lexical_Analysis2.symbolTable2.delimiter d = table.isDelimiter(String.valueOf(ch));
					tokens.append(formLine(d.getName(), d.getTypeString(), "_"));
				}else {
					illegal.append(illegalOutput("中文非法","IllegalError"));
					continue;
				}
		}
		Map<String,String> LAResult = new HashMap<>();
		LAResult.put("IDN", table.identifiersToString(Identifiers));
		LAResult.put("tokens", tokens.toString());
		LAResult.put("error", illegal.toString());
//		System.out.println("标识符表："+"\n"+table.identifiersToString(Identifiers));
//		System.out.println();
//		System.out.println("token序列："+"\n"+tokens.toString());
//		System.out.println();
//		System.out.println("非法识别："+"\n"+illegal.toString());
		return LAResult;
	}
}
