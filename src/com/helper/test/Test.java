package com.helper.test;

public class Test {
	public static void main(String[] args) {
		String cmd = new String("%name% 1 hhh iyhu guguighuihuihuihuihuihui http://fanyi.baidu.com/?aldtype=85&keyfrom=alading#en/zh/execute");
		cmd.startsWith("%");
		System.out.println(cmd.substring(1,cmd.indexOf(" ")-1));
		cmd = cmd.substring(cmd.indexOf(" ")+1);
		for(String c:cmd.split(" "))
		{
			System.out.println(c);
		}
	}
}
