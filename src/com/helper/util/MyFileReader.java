package com.helper.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;
/**
 * 文件读取类
 * @author jx
 *
 */
public class MyFileReader 
{
    public static List<String> readFileByLines(File file){
    	List<String>lines  = new ArrayList<String>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                if(StringTools.StringIsNotEmpty(s))
                {
                	lines.add(s);
                }
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return lines;
    }
		public static List<String> readFileByLines(String fileName)
		{
			File file  = new File(fileName);
			return MyFileReader.readFileByLines(file);
		}
}
