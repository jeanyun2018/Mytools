package com.helper.util;
/**
 * �ַ���������
 * @author jx
 *
 */
public class StringTools 
{
		public static boolean StringIsNotEmpty(String src)
		{
			if(src!=null&&(!src.equals("")))
			{
				return true;
			}else 
			 return false;
		}
}
