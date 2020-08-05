package GUI;

import java.util.ArrayList;

public class symbolTable2 {
	
	//关键字
	public final String[] keywordList = { "abstract", "call", "boolean", "break", "byte",
			"case", "catch", "char", "class", "continue", "default", "do",
			"double", "else", "extends", "final", "finally", "float", "for",
			"if", "then", "implements", "import", "instanceof", "integer", "interface",
			"long", "native", "new", "package", "private", "protected",
			"public", "return", "short", "static", "super", "switch",
			"synchronized", "this", "throw", "throws", "transient", "try",
			"void", "volatile", "while","strictfp","enum","goto","const","assert"
			
			,"procedure", "real","array","of","record","end","begin","recall","string","or","and","not",
			"read","readln","write","writeln","to","step","repeat","until","program"};
	
	//运算符
	public final String[] operatorList = { ":","=", "+", "-", "*", "/", "%", ">", "<",
	"!", "|", "&", "<=", ">=", "==", "!=", "&&", "||", "++", "--", "+=", "-=", "%=", "*=", "/=",":=" };
	
	//分界符
	public final String[] delimiterList = { ",", ";", "{", "}", "(", ")", "[", "]", "_",
			":", ".", "\"", "\'"};	
	
	//各种符号的对象集合
	private int index = 1;//id
	private final keyWord[] Keywords = new keyWord[keywordList.length];
	private final operator[] Operators = new operator[operatorList.length];
	private final delimiter[] Delimiters = new delimiter[delimiterList.length];
	private final ArrayList<Identifier> Identifiers = new ArrayList<>();	
	
	public symbolTable2() {
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
			this.TypeString = name;
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
			this.TypeString = name;
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
			this.TypeString = name;
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

