package GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import Lexical_Analysis.Analysis;

public class Parser {

	private Stack<String> stateStack;
	private ParseChart chart;
	private String input;
	private ArrayList<String> productions;
	static int index = 0;
	
	public Parser(ParseChart chart,String input) {
		this.stateStack = new Stack<>();
		this.stateStack.push("0");
		this.chart = chart;
		this.input = (input + "#");
		this.productions = chart.getSyntax().getProductions();
	}
	
	public Map<String,String> parse() {
		
		Map<String,String> chartEvent = chart.getChart();
		String[] content = getNextToken().split("\t");
		String row = content[0];
		String token = content[1];
		String message = "";
		String reduceP = "";
		while(true) {
			//System.out.println(stateStack);
			String state = stateStack.peek();
			//System.out.println(state + "~" + token);
			String action = chartEvent.get(state + "~" + token);
			//System.out.println(action);
			if(action == null) {
				System.out.println(token);
				message = message + "Error At Line["+ row +"]:";
				if(token.equals("id")||token.equals("(")) {
					message = message + "[缺少一个运算符！]"+"\t"+"处理方式：[使用默认运算符填充]"+"\n";
					token = "+";
					action = chartEvent.get(state + "~" + token);
					index--;
				}else if(token.equals(")")) {
					message = message + "[不匹配的右括号！]"+"\t"+"处理方式：[忽略右括号]"+"\n";
					content = getNextToken().split("\t");
				    row = content[0];
					token = content[1];
					action = chartEvent.get(state + "~" + token);
				}else if(token.equals("+")||token.equals("*")||token.equals("/")||token.equals(";")) {
					message = message + "[缺少运算分量！]"+"\t"+"处理方式：[使用默认运算分量填充]"+"\n";
					token = "digit";
					action = chartEvent.get(state + "~" + token);
					index--;
				}else if(token.equals("#")) {
					message = message + "[左括号不匹配！]"+"\t"+"处理方式：[添加右括号]"+"\n";
					token = ")";
					action = chartEvent.get(state + "~" + token);
					index--;
				}else {
					break;
				}
			}
			if(action.charAt(0) == 's') {
				stateStack.push(action.substring(1));
				content = getNextToken().split("\t");
//				System.out.println(content.length);
//				System.out.println(content[0]);
			    row = content[0];
				token = content[1];
			
			}else if(action.charAt(0) == 'r') {//进行规约
				String production = productions.get(Integer.parseInt(action.substring(1)));
				reduceP = reduceP + production + "\n";
				int bateSize = production.split("→")[1].split(" ").length; //规约串的长度
				for(int i =0;i< bateSize ;i++) { //从栈中弹出bateSize个符号
					stateStack.pop();
				}
				if(action.equals("r0")) {
					break;
				}
				String top = stateStack.peek();
				String A = production.split("→")[0];
				//System.out.println(top + "~" + A);
				String a = stateStack.push((chartEvent.get(top + "~" + A)));
				//System.out.println(a);
			}
			
		}
		Map<String,String> SAResult = new HashMap<>();
		SAResult.put("reduceProductions", reduceP);
		SAResult.put("error", message);
		System.out.println(reduceP);
		System.out.println();
		System.err.println(message);
		
		return SAResult;
	}
	
	public String getNextToken() {
		String[] tokens = input.split("\n");
		String difference = "";
		String row = "";
		if(tokens[index].equals("#")) {
			difference = "#";
			int i = index - 1;
			System.out.println(tokens.length);
			System.out.println(i);
			row = tokens[i].split("\t")[0].split(":")[1];
		}else {
			difference = tokens[index].split("\t")[2].split("<")[1];
			row = tokens[index].split("\t")[0].split(":")[1];
		}	
		index++;
		return row+"\t"+difference;
	}
}
