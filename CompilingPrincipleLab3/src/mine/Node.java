package mine;

import java.util.ArrayList;

public class Node {
	String str; // 变量名称
	String value; // 变量的值
	String type; // 变量的类型
	int width; // 变量所占字节数
	String offset; // 变量在地址空间中的偏移量
	ArrayList<Integer> truelist; // 指向包含跳转指令的列表（B为真）
	ArrayList<Integer> falselist; // 指向包含跳转指令的列表（B为假）
}
