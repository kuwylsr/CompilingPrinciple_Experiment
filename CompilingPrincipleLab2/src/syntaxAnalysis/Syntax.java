package syntaxAnalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Syntax {
	
	private ArrayList<String> productions; //产生式集合
	private ArrayList<String> NonTerminals; //非终结符集合
	private ArrayList<String> Terminals; //终结符集合
	private ArrayList<String> CauseEpsilon; //能推导出epsilon的符号集合
	private Map<String,ArrayList<String>> FirstList; //非终结符以及对应的first集
	private static Syntax Instance = null;
	
	public Syntax(String filepath) throws IOException {
		this.productions = new ArrayList<>();
		this.NonTerminals = new ArrayList<>();
		this.Terminals = new ArrayList<>(); 
		this.CauseEpsilon = new ArrayList<>();
		this.FirstList = new HashMap<>();
		InitProduction(filepath);
		InitCauseEpsilon();
		InitFirstList();
//		System.out.println(productions);
//		System.out.println(NonTerminals);
//		System.out.println(Terminals);
//		System.out.println(CauseEpsilon);
//		System.out.println(FirstList);
	}
	
	public static Syntax Instance(String filePath) throws IOException {
		if(Instance == null) {
			Instance = new Syntax(filePath);
		}
		return Instance;
	}
	
	public static Syntax getInstance() {
		return Instance;
	}
	
	public ArrayList<String> getProductions(){
		ArrayList<String> list = new ArrayList<>();
		for(String c : productions) {
			list.add(c);
		}
		return list;
	}
	
	public ArrayList<String> getNonT(){
		ArrayList<String> list = new ArrayList<>();
		for(String c : NonTerminals) {
			list.add(c);
		}
		return list;
	}
	
	public ArrayList<String> getT(){
		ArrayList<String> list = new ArrayList<>();
		for(String c : Terminals) {
			list.add(c);
		}
		return list;
	}
	
	public ArrayList<String> getCauseEpsilon(){
		ArrayList<String> list = new ArrayList<>();
		for(String c : CauseEpsilon) {
			list.add(c);
		}
		return list;
	}
	
	public Map<String,ArrayList<String>> getFirst(){
		Map<String,ArrayList<String>> f = new HashMap<>();
		for(Map.Entry<String, ArrayList<String>> entry : this.FirstList.entrySet()) {
			f.put(entry.getKey(), entry.getValue());
		}
		return f;
	}
	
	public void InitProduction(String filepath) throws IOException {
		FileReader r = new FileReader(filepath);
		BufferedReader br = new BufferedReader(r);
		String line = null;
		while((line = br.readLine()) != null) {
			if(line.charAt(0) != '/') {
				addTerm(line);
			}
		}
		br.close();
		r.close();
	}
	
	public void addTerm(String line) {
		System.out.println(line);
		String[] stemp = line.split("→");
		String left = stemp[0].trim();
		String[] rights = stemp[1].split("\\|");
		for(String right : rights) {
			this.productions.add(left + "→" + right.trim());
		}
		String[] terms = line.split(" ");
		for(String t : terms) {
			
			if(Character.isUpperCase(t.charAt(0))) {
				if(!NonTerminals.contains(t)) {
					NonTerminals.add(t);
				}
			}else if(!t.equals("→") && !t.equals("|") && !Terminals.contains(t)) {
				Terminals.add(t);
			}
			
		}
	}
	
	public void InitCauseEpsilon() {
		int flag = 0;
		while(flag == 0) {
			flag = 1;
			for(String production : productions) {
				String[] stemp = production.split("→");
				String left = stemp[0].trim();
				String rights = stemp[1];
				if(rights.equals("ε") && !CauseEpsilon.contains(left)) { //能推导出epsilon的产生式
					CauseEpsilon.add(left);
					flag = 0;
				}else {
					String[] rightList = stemp[1].split(" ");
					int i = 0;
					for(String r : rightList) {
						//如果是非终结符
						if(NonTerminals.contains(r)) {
							if(CauseEpsilon.contains(r)) {
								i++;
								continue;
							}else {
								break;
							}
						}else {
							break;
						}
						
					}
					//如果该产生式右侧都是非终结符，并且所有非终结符都能推导出epsilon
					if(i == rightList.length && !CauseEpsilon.contains(left)){ 
						CauseEpsilon.add(left);
						flag = 0;
					}
				}
			}
		}
	}
	
	public void InitFirstList() {
		for(String c : NonTerminals) {
			FirstList.put(c, new ArrayList<String>());
		}
		//一直循环求解first集，直到每个符号的first集都不变为止
		int flag = 0;
		while(flag == 0) { 
			flag = 1;
			for(String production : productions) {
				String[] stemp = production.split("→");
				String left = stemp[0].trim();
				String[] rights = stemp[1].split(" ");
				for(String r : rights) {
					//如果为终结符，则直接加入left的first集中
					if(Terminals.contains(r)) {
						if(!FirstList.get(left).contains(r)) {
							FirstList.get(left).add(r);
							flag = 0; //fisrt集改变，继续循环
						}
						break;
					}else {
						//若为非终结符，则直接合并二者的first集
						for(String c : FirstList.get(r)) {
							if(!FirstList.get(left).contains(c)) {
								FirstList.get(left).add(c);
								flag = 0; //fisrt集改变，继续循环
							}
						}
						if(!CauseEpsilon.contains(r)) { //若能推导出空串，则继续向后读
							break;
						}
					}
				}
			}
		}
		
	}
	
}
