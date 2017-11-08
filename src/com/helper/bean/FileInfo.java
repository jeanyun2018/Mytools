package com.helper.bean;
/**
 * 用于存储文件的基本信息
 * @author jx
 *
 */
public class FileInfo 
{
		private String fileName;
		private String path;
		
		public  FileInfo(String fileName,String path)
		{
			this.fileName = fileName;
			this.path = path;
		}
		
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		
		
}
