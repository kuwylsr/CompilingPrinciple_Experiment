package GUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ParseChart {

	private Syntax syntax; //
	private Map<String,String> chartEvent; //存储ACTION和GOTO的动作
	private ArrayList<String> allSymbols; //所有的符号集合
	public static ArrayList<SetOfItem> AllSetOfItem; //所有项目集的集合
	
	public ParseChart(String filePath) throws IOException {
		this.syntax = Syntax.Instance(filePath);
		this.chartEvent = new HashMap<>();
		ParseChart.AllSetOfItem = new ArrayList<>();
		allSymbols = new ArrayList<>(); 
		allSymbols.addAll(syntax.getT());
		allSymbols.addAll(syntax.getNonT());
		allSymbols.add("#");
		InitSetOfItem();
	}
	
	public Map<String,String> getChart(){
		return this.chartEvent;
	}
	
	public Syntax getSyntax() {
		return this.syntax;
	}
	
	
	//初始化第一个项目集
	public void InitSetOfItem() {
		ArrayList<Item> items = new ArrayList<>();
		String initProduction = syntax.getProductions().get(0);
		ArrayList<String> InitExpectations = new ArrayList<>();
		InitExpectations.add("#");
		items.add(new Item(initProduction, 0, InitExpectations));
		SetOfItem initSet = new SetOfItem(items);
		initSet.calState();
		AllSetOfItem.add(initSet);
		initSet.setIfJoin(true);
		initSet.closure();
		BuildParseChart(initSet);	
	}
	
	//
	public void BuildParseChart(SetOfItem initSet) {
		ArrayList<SetOfItem> nonJumpSetOfItem = new ArrayList<>();
		nonJumpSetOfItem.add(initSet);
		for(int i = 0; i< nonJumpSetOfItem.size() ;i++) {
			SetOfItem setTemp = nonJumpSetOfItem.get(i);

			//setTemp.closure();
			for(String s : allSymbols) {
				int index = setTemp.reduce(s); //判断是否能进行归约
				if(index == -1) {
					SetOfItem nextSet = setTemp.GoTo(s); //判断是否能进行移入
					
					if(nextSet != null) {
						if(!nextSet.getIfJoin()) {
							nonJumpSetOfItem.add(nextSet);
							AllSetOfItem.add(nextSet);
							nextSet.setIfJoin(true);
						}
						if(syntax.getT().contains(s)) {
							chartEvent.put(setTemp.getState() +"~"+ s, "s" + nextSet.getState());
						}else {
							chartEvent.put(setTemp.getState() +"~"+s, String.valueOf(nextSet.getState()));
						}
					}
				}else {
					chartEvent.put(setTemp.getState() +"~"+ s, "r"+index);
				}
			}
		}
	}
	
	public void printAllSetOfItems() {
		for(SetOfItem set : AllSetOfItem) {
			System.out.println("-------状态 " + set.getState() +" Begin--------");
			for(Item item : set.getItems()) {
				System.out.println(item.getProduction() + "||" + item.getPLocation() + "||" + item.getExpectations());
			}
			System.out.println("-------状态 " + set.getState() +" End--------");
			
		}
	}
	
	public String printParseChart() {
		
		String content = "";
		String stitle = "";
		ArrayList<String> title = new ArrayList<>();
		stitle = stitle + "状态" + "\t";
		Collections.sort(this.syntax.getT());
		Collections.sort(this.syntax.getNonT());
		for(String symbol : this.syntax.getT()) {
			title.add(symbol);
			stitle = stitle + symbol +"\t";
		}
		stitle = stitle + "#" + "\t";
		title.add("#");
		for(String symbol : this.syntax.getNonT()) {
			title.add(symbol);
			stitle = stitle+symbol +"\t";
		}
		//System.out.println(stitle);
        content = content + stitle + "\n";
        for(int i  =0 ;i<AllSetOfItem.size();i++) {
        	content = content + String.valueOf(i)+"\t";
        	for(String t : title) {
        		int flag = 0;
        		if(chartEvent.containsKey(i+"~"+t)) {
        			String action = chartEvent.get(i+"~"+t);
        			if(action.equals("r0")) {
        				content = content + "rcc" + "\t";
        			}else {
        				content = content + action + "\t" ;
        			}
        		}else {
        			content = content + " " + "\t" ;
        		}
        	}
        	content = content + "\n";
        }	
        System.out.println(content);
        return content;
	}
}
