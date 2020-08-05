package Lexical_Analysis2;

public class Identifier {
	
	private String name;
	private String TypeNum;
	private String TypeString;
	
	public Identifier(String name,String num) {
		this.name = name;
		this.TypeString = "IDN";
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
