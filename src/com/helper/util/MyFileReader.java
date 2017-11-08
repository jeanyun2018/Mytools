package com.helper.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;
/**
 * �ļ���ȡ��
 * @author jx
 *
 */
public class MyFileReader 
{
    public static List<String> readFileByLines(File file){
    	List<String>lines  = new ArrayList<String>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//����һ��BufferedReader������ȡ�ļ�
            String s = null;
            while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
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
