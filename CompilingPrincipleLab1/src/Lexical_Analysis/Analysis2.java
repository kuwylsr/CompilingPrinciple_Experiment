package Lexical_Analysis;

import Lexical_Analysis.symbolTable.delimiter;
import Lexical_Analysis.symbolTable.keyWord;
import Lexical_Analysis.symbolTable.operator;

public class Analysis2{
	
	public String formLine(String name,String TypeString,String value) {
		return name + "\t" + "<" + "\t" + TypeString+ "\t" + "," + "\t" + value + "\t" + ">" + "\n";
	}
	
	public String analysis(String content) {
		symbolTable table = new symbolTable();
		StringBuilder tokens = new StringBuilder();
		StringBuilder sTemp = new StringBuilder();
		char ch;
		for(int i = 0;i<content.length();i++) {
				ch = content.charAt(i);
				if(Character.isWhitespace(ch)) continue;
				//判定是否为标识符或是关键字
				if(Character.isLetter(ch)||ch == '_') {
					sTemp.append(ch);
					if(++i < content.length()) ch = content.charAt(i);
					else {
						tokens.append(formLine(sTemp.toString(), "IDN", sTemp.toString()));
					}
					while(Character.isLetter(ch) || Character.isDigit(ch)) {//一直读直到既不是数字也不是字母
						sTemp.append(ch);
						if(++i < content.length()) ch = content.charAt(i);
						else i++;
					}
					i--;
					keyWord k = table.isKeyWord(sTemp.toString());
					if(k != null) { //为关键字
						tokens.append(formLine(k.getName(), k.getTypeString(), "_"));
					}else {//若不是关键字，则为标识符
						tokens.append(formLine(sTemp.toString(), "IDN", sTemp.toString()));
					}
					sTemp.delete(0, sTemp.length());
				}
				//判定是否为常数
				else if(Character.isDigit(ch)) {
					while(Character.isDigit(ch)) {
						sTemp.append(ch);
						if(++i < content.length()) ch = content.charAt(i);
					}
					if(ch == '.') { //可能为浮点数
						sTemp.append(ch);
						if(++i < content.length()) ch = content.charAt(i);
						else {
							tokens.append(formLine("NumError", "error", "_"));
							break;
						}
						if(Character.isDigit(ch)) {
							while(Character.isDigit(ch)) {
								sTemp.append(ch);
								if(++i < content.length()) ch = content.charAt(i);
								else break;
							}
							i--;
						}else {
							tokens.append(formLine("NumError", "error", "_"));
							break;
						}
					}
					if(ch == 'E') {
						sTemp.append(ch);
						if(++i < content.length()) ch = content.charAt(i);
						else {
							tokens.append(formLine("NumError", "error", "_"));
							break;
						}
						if(ch == '+'||ch == '-') {
							sTemp.append(ch);
							if(++i < content.length()) ch = content.charAt(i);
							else {
								tokens.append(formLine("NumError", "error", "_"));
								break;
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
							tokens.append(formLine("NumError", "error", "_"));
							break;
						}
					}
						
					tokens.append(formLine(sTemp.toString(), "CONST", sTemp.toString()));
				
					sTemp.delete(0, sTemp.length());
				}
				//判定是否为运算符
				else if(table.isOperator(String.valueOf(ch)) != null) { //判定为运算符
					sTemp.append(ch);
					operator o = table.isOperator(String.valueOf(ch));
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
									tokens.append(formLine("NotesError", "error", "_"));
									break;
								}
								sTemp.append(ch);
								if(ch == '*') {
									if(++i < content.length()) ch = content.charAt(i);
									else {
										tokens.append(formLine("NotesError", "error", "_"));
										break;
									}
									sTemp.append(ch);
									if(ch == '/') {
										tokens.append("Notes: "+sTemp+"\n");
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
						operator oo = table.isOperator(sTemp+String.valueOf(ch)); //看两个字符连接在一起是否为新的一个运算符
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
					delimiter d = table.isDelimiter(String.valueOf(ch));
					tokens.append(formLine(d.getName(), d.getTypeString(), "_"));
				}
			
		}
		
		
		return tokens.toString();
	}
}
