package syntaxAnalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import Lexical_Analysis.Analysis;

public class main {

	public static String ReadFile(String filePath) throws IOException {
		FileReader r = new FileReader(filePath);
		BufferedReader br = new BufferedReader(r);
		String line = null;
		StringBuilder content = new StringBuilder();
		while((line = br.readLine()) != null) {
			content.append(line);
			content.append("\n");
		}
		br.close();
		r.close();
		return content.toString();
	}
	
	public static void main(String[] args) throws IOException {
		ParseChart c = new ParseChart("src/syntaxAnalysis/productions.txt");
		//c.printAllSetOfItems();
		c.printParseChart();
//		String text = ReadFile("E:\\eclipse-workspace/CompilingPrincipleLab1/src/Lexical_Analysis/programForSyntax.txt")+" ";
//		Map<String,String> LAResult = new Analysis().analysis(text);
//		String output = LAResult.get("tokens");
//		System.out.println();
//		System.out.println(output);
//		Parser p = new Parser(c,output);
//		p.parse();
	}

}
