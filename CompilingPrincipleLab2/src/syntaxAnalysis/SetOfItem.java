package syntaxAnalysis;

import java.util.ArrayList;

public class SetOfItem {
	
	public static int count = 0; //设置状态集的标号计数
	private boolean joinAllSetOfItem; //定义是否加入了所有的项目集的集合
	private int state; //项目集的状态标号
	 
	private ArrayList<Item> items;
	
	public SetOfItem(ArrayList<Item> items) {
		this.items = items;
		this.joinAllSetOfItem = false;
	}
	
	public void calState() {
		this.state = count++;
	}
	
	public int getState() {
		return this.state;
	}
	
	public boolean getIfJoin() {
		return joinAllSetOfItem;
	}
	public void setIfJoin(boolean flag) {
		this.joinAllSetOfItem = flag;
	}
	
	public ArrayList<Item> getItems(){
		return this.items;
	}
	
	public boolean contains(ArrayList<Item> items , Item item) {
		for(Item i : items) {
			if(item.equals(i)) {
				return true;
			}
		}
		return false;
	}
	
	//求当前项目集的项目集闭包
	public void closure() {
		Syntax syntax = Syntax.getInstance();
		for(int i = 0 ;i < items.size() ;i++) {
			Item item = items.get(i);
			//System.out.println(item.getProduction());
			
			if(item.getRights().length == item.getPLocation()) { //如果项目为带归约项目，则跳过
				continue;
			}
			if(syntax.getNonT().contains(item.getRights()[item.getPLocation()])) { //若项目集point后的位置为非终结符
//				System.out.println(i+"================");
				for(String s :syntax.getProductions()) { //找出目标非终结符的所有产生式
					String left = s.split("→")[0];
					if(left.equals(item.getRights()[item.getPLocation()])) { 
						ArrayList<String> expectations = item.SupportExpectations();
						Item itemTemp = new Item(s, 0, expectations);
						
						if(!contains(items, itemTemp)) {
							items.add(itemTemp);
//							System.out.println(itemTemp.getProduction());
						}
					}
				}
			}
		}
	}
	
	//求项目集的转移函数goto（和goto表无关）
	public SetOfItem GoTo(String s) {
		ArrayList<Item> itemsGoTo = new ArrayList<>();		
		for(Item item : this.items) {
			if(item.getRights().length == item.getPLocation()) { //若为带规约的产生式，直接跳过
				continue;
			}
			
			if(item.getRights()[item.getPLocation()].equals(s)) { //若产生式point后的符号为输入符号s，则进行状态转移
//				System.out.println(s);
//				System.out.println(item.getProduction() + "||" + item.getRights()[item.getPLocation()]);
				Item itemTemp = new Item(item.getProduction(), item.getPLocation()+1, item.getExpectations());
				itemsGoTo.add(itemTemp);
			}
		}
		SetOfItem setOfItemGoTo = new SetOfItem(itemsGoTo);
		setOfItemGoTo.closure();
		int i = 0;
		if(itemsGoTo.size()!=0) {
			
			for(;i<ParseChart.AllSetOfItem.size();i++) {
				//如果goto转到的项目集已经在总状态集里面
				if(setOfItemGoTo.equals(ParseChart.AllSetOfItem.get(i))) {
					//System.out.println(s+"||"+"aaa" + "||"+i);
					setOfItemGoTo = ParseChart.AllSetOfItem.get(i);
					break;
				}
			}
			//如果goto转到的项目集不在总状态里面
			if(i == ParseChart.AllSetOfItem.size()) {
				setOfItemGoTo.calState();
			}
		}else {
			setOfItemGoTo = null;
		}
		return setOfItemGoTo;
	}
	
	//判断两个项目集是否完全相同
	public boolean equals(SetOfItem setOfItem) {
		
		for(Item item1 : this.items) {
			int flag = 0;
			for(Item item2 : setOfItem.getItems()) {
				if(item1.equals(item2)) {
					flag = 1;
					break;
				}
			}
			if(flag == 0) {
				return false;
			}
		}
		if(items.size() == setOfItem.getItems().size()) {
			return true;
		}else {
			return false;
		}
		
		
	}
	
	//求项目遇到输入符号s时，是否可以规约;若找到，返回对应集合位置的下标，否则返回-1
	public int reduce(String s) {
		Syntax syntax = Syntax.getInstance();
		for(Item item : items) {
			if(item.getPLocation() == item.getRights().length) {//若为待规约的产生式
				if(item.getExpectations().contains(s)) { //判断展望符集合中是否包含待读入符号
					String productionTemp = item.getProduction();
					ArrayList<String> productions = syntax.getProductions();
					for(int i =0 ;i<productions.size();i++) {
						if(productionTemp.equals(productions.get(i))) {
							return i; //返回产生式在集合中的序号
						}
					}
				}
			}
		}
		return -1;
	}
}
