package Lexical_Analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import Lexical_Analysis.symbolTable.delimiter;
import Lexical_Analysis.symbolTable.keyWord;
import Lexical_Analysis.symbolTable.operator;

public class Main {

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
	
	public static void WriteFile(String filePath,String content) throws IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file,false);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();
		fw.close();
	}
	
	public static void main(String[] args) throws IOException {
		String text = ReadFile("src/Lexical_Analysis/program.txt")+" ";
		
		Map<String,String> result = new Analysis().analysis(text);
		WriteFile("src/Lexical_Analysis/output.txt", result.get("tokens"));
		//System.out.println(output);

	}

}
