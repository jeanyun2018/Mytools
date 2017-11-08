package com.helper.test;

import com.crawler.beans.DownLoadConfig;
import com.crawler.control.DownloadDocs;
import com.crawler.util.IfileCallFinish;

public class TestFile implements IfileCallFinish
{
	
	public static void main(String[] args) {
		TestFile test = new TestFile();
		DownLoadConfig config = new DownLoadConfig();
		config.setCatalogPath("http://www.x23us.com/html/29/29402/");
		config.setFolderPath("D:\\tmp\\");
		config.setLinksSelecter("table[id=at] td>a");
		config.setTitleSelecter("div.bdsub>dl>dd");
		config.setDataSelecter("#contents");
		config.setIsFinish(test);
		DownloadDocs down = new DownloadDocs();
		down.setConfig(config);
		down.beginWork();
	}
	
	
	@Override
	public void fileIsFinish(boolean isOk, String fileInfo) {
		// TODO Auto-generated method stub
		if(isOk)
		{
			System.out.println(fileInfo+"下载成功");
		}else 
		{
			System.out.println(fileInfo+"下载失败");
		}
	}
		
}
