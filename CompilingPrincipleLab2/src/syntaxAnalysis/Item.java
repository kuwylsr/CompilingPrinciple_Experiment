package syntaxAnalysis;

import java.util.ArrayList;

public class Item {

	private String production;
	private String left; //产生式左部
	private String[] rights; //产生式右部
	private int pointLocation; //point的位置为[0,len]，标识对应数组元素的前面
	private ArrayList<String> expectations; //展望符集合
	
	public Item(String production, int pointLocation, ArrayList<String> expectations) {
		this.production = production;
		this.pointLocation = pointLocation;
		this.expectations = new ArrayList<>();
		for(String s : expectations) {
			this.expectations.add(s);
		}
		String[] stemp = production.split("→");
		left = stemp[0].trim();
		rights = stemp[1].split(" ");
	}
	
	public String getProduction() {
		return this.production;
	}
	
	public int getPLocation() {
		return this.pointLocation;
	}
	
	public ArrayList<String> getExpectations(){
		ArrayList<String> list = new ArrayList<>();
		for(String s : expectations) {
			list.add(s);
		}
		return list;
	}
	
	public String getLeft() {
		return this.left;
	}
	
	public String[] getRights() {
		return this.rights;
	}
	
	//求由该项目提供的展望符集合
	public ArrayList<String> SupportExpectations(){
		Syntax syntax = Syntax.getInstance();
		String[] stemp = production.split("→");
		String[] rights = stemp[1].split(" ");
		//String beta = rights.substring(pointLocation+1);
		ArrayList<String> SupportExps = new ArrayList<>();
		for(int i = pointLocation+1 ;i< rights.length; i++) {
			if(syntax.getT().contains(rights[i])) { //若point+1后的字符为终结符
				if(!SupportExps.contains(rights[i])) {
					SupportExps.add(rights[i]);
				}
				return SupportExps;
			}else { //若point后的字符为非终结符
				for(String s : syntax.getFirst().get(rights[i])) {
					if(!SupportExps.contains(s)) {
						SupportExps.add(s);
					}
				}
				if(!syntax.getCauseEpsilon().contains(rights[i])) {
					return SupportExps;
				}
			}	
		}
		//若point后都是终结符且终结符均可推导出Epsilon
		for(String s : this.expectations) {
			if(!SupportExps.contains(s)) {
				SupportExps.add(s);
			}
		}
		return SupportExps;
	}
	
	//判断两个项目是否相等
	public boolean equals(Object o) {
		Item item = (Item)o;
		if(this.getProduction().equals(item.getProduction())&&(this.getPLocation() == item.getPLocation())) {
			for(String e1 : this.expectations) {
				int flag = 0;
				for(String e2 : item.expectations) {
					if(e1.equals(e2)) {
						flag = 1;
						break;
					}
				}
				if(flag == 0) {
					return false;
				}
			}
			if(this.expectations.size() == item.expectations.size()) {
				return true;
			}else {
				return false;
			}	
		}else {
			return false;
		}
	}
}
