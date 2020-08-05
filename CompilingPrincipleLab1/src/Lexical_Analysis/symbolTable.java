package Lexical_Analysis;

import java.util.ArrayList;

public class symbolTable {
	
	//关键字
	public final String[] keywordList = { "abstract", "boolean", "break", "byte",
			"case", "catch", "char", "class", "continue", "default", "do",
			"double", "else", "extends", "final", "finally", "float", "for",
			"if", "implements", "import", "instanceof", "int", "interface",
			"long", "native", "new", "package", "private", "protected",
			"public", "return", "short", "static", "super", "switch",
			"synchronized", "this", "throw", "throws", "transient", "try",
			"void", "volatile", "while","strictfp","enum","goto","const","assert" };
	
	//运算符
	public final String[] operatorList = { "+", "-", "*", "/", "%", ">", "<",
	"!", "|", "&", "<=", ">=", "==", "!=", "&&", "||", "++", "--", "+=", "-=", "%=", "*=", "/=" };
	
	//分界符
	public final String[] delimiterList = { ",", ";", "{", "}", "(", ")", "[", "]", "_",
			":", ".", "\"", "\'", "="};	
	
	//各种符号的对象集合
	private int index = 1;//id
	private final keyWord[] Keywords = new keyWord[keywordList.length];
	private final operator[] Operators = new operator[operatorList.length];
	private final delimiter[] Delimiters = new delimiter[delimiterList.length];
	private final ArrayList<Identifier> Identifiers = new ArrayList<>();	
	
	public symbolTable() {
		for(int i = 0; i< keywordList.length;i++) {
			keyWord k = new keyWord(keywordList[i],String.valueOf(index));
			this.Keywords[i] = k;
			index++;
		}
		for(int i = 0; i< operatorList.length;i++) {
			operator o = new operator(operatorList[i], String.valueOf(index));
			this.Operators[i] = o;
			index++;
		}
		for(int i = 0;i < delimiterList.length;i++) {
			delimiter d = new delimiter(delimiterList[i], String.valueOf(index));
			this.Delimiters[i] = d;
			index++;
		}
	}
	public int getIndex() {
		return this.index;
	}
	
	public ArrayList<Identifier> getIdentifiers(){
		return this.Identifiers;
	}
	
	public keyWord isKeyWord(String temp) {
		for(keyWord k : Keywords) {
			if(k.getName().equals(temp)) {
				return k;
			}
		}
		return null;
	}
	public operator isOperator(String temp) {
		for(operator o : Operators) {
			if(o.getName().equals(temp)) {
				return o;
			}
		}
		return null;
	}
	public delimiter isDelimiter(String temp) {
		for(delimiter d : Delimiters) {
			if(d.getName().equals(temp)) {
				return d;
			}
		}
		return null;
	}
	
	public String identifiersToString(ArrayList<Identifier> Identifiers) {
		String content = "";
		int num = 0;
		for(Identifier identifier : Identifiers) {
			num ++;
			content = content + String.valueOf(num)+"\t"+identifier.getName()+"\n";
		}
		return content;
	}
	
	
	public class keyWord {
		
		private String name;
		private String TypeNum;
		private String TypeString;
		
		public keyWord(String name,String num) {
			this.name = name;
			this.TypeString = name.toUpperCase();
			this.TypeNum = num;
		}
		public String getName() {
			return this.name;
		}
		public String getTypeString() {
			return this.TypeString;
		}
		public String getTypeNum() {
			return this.TypeNum;
		}
		
	}
	
	public class operator {

		private String name;
		private String TypeNum;
		private String TypeString;
		
		public operator(String name,String num) {
			this.name = name;
			//算数运算符
			if(name.equals( "+")) {
				this.TypeString = "PLUS";
			}else if (name.equals("-")) {
				this.TypeString = "MIN";
			}else if (name.equals("*")) {
				this.TypeString = "MUL";
			}else if (name.equals("/")) {
				this.TypeString = "DIV";
			}else if (name.equals("%")) {
				this.TypeString = "REMA";
			}else if (name.equals("%=")) {
				this.TypeString = "REEQ";
			}else if (name.equals("--")) {
				this.TypeString = "MM";
			}else if (name.equals("++")) {
				this.TypeString = "PP";
			}else if (name.equals("+=")) {
				this.TypeString = "PEQ";
			}else if (name.equals("-=")) {
				this.TypeString = "MIEQ";
			}else if (name.equals("*=")) {
				this.TypeString = "MUEQ";
			}else if (name.equals("/=")) {
				this.TypeString = "DEQ";
			}
			
			//关系运算符
			else if (name.equals(">")) {
				this.TypeString = "GT";
			}else if (name.equals("<")) {
				this.TypeString = "LT";
			}else if (name.equals("<=")) {
				this.TypeString = "LTE";
			}else if (name.equals(">=")) {
				this.TypeString = "GTE";
			}else if (name.equals("==")) {
				this.TypeString = "EQ";
			}else if (name.equals("!=")) {
				this.TypeString = "NEQ";
			}
			//逻辑运算符
			else if (name.equals("!")) {
				this.TypeString = "NOT";
			}else if (name.equals("&&")) {
				this.TypeString = "LAND";
			}else if (name.equals("||")) {
				this.TypeString = "LOR";
			}
			this.TypeNum = num;
		}
		public String getName() {
			return this.name;
		}
		public String getTypeString() {
			return this.TypeString;
		}
		public String getTypeNum() {
			return this.TypeNum;
		}
	}
	
	public class delimiter {
		
		private String name;
		private String TypeNum;
		private String TypeString;
		
		public delimiter(String name,String num) {
			this.name = name;
			//界符
			if(name.equals( ",")) {
				this.TypeString = "COM";
			}else if (name.equals(";")) {
				this.TypeString = "SEMI";
			}else if (name.equals("{")) {
				this.TypeString = "LP";
			}else if (name.equals("}")) {
				this.TypeString = "RP";
			}else if (name.equals("(")) {
				this.TypeString = "SLP";
			}else if (name.equals(")")) {
				this.TypeString = "SRP";
			}else if (name.equals("[")) {
				this.TypeString = "MLP";
			}else if (name.equals("]")) {
				this.TypeString = "MRP";
			}else if (name.equals("_")) {
				this.TypeString = "UL";
			}else if (name.equals(":")) {
				this.TypeString = "COL";
			}else if (name.equals(".")) {
				this.TypeString = "FS";
			}else if (name.equals("\"")) {
				this.TypeString = "DQ";
			}else if (name.equals("=")) { //赋值
				this.TypeString = "ASS";
			}
			this.TypeNum = num;
		}
		
		public String getName() {
			return this.name;
		}
		public String getTypeString() {
			return this.TypeString;
		}
		public String getTypeNum() {
			return this.TypeNum;
		}
	}
	
}
