package com.helper.test;

import com.crawler.beans.DownLoadConfig;
import com.crawler.control.DownLoadImage;
import com.crawler.util.IfileCallFinish;

public class TestImg implements IfileCallFinish
{
			public static void main(String[] args) {
				TestImg img  = new TestImg();
				DownLoadConfig config = new DownLoadConfig();
				config.setCatalogPath("https://www.meitulu.com/item/7712.html");
				config.setFolderPath("D:\\tmp\\");
				config.setLinksSelecter("#pages>a");
				config.setImgSelecter("div.content>center>img");
				config.setIsFinish(img);
				DownLoadImage downImg =  new DownLoadImage();
				downImg.setConfig(config);
				downImg.beginWork();
			}

			@Override
			public void fileIsFinish(boolean isOk, String fileInfo) {
				// TODO Auto-generated method stub
				if(isOk)
				{
					System.out.println(fileInfo+"\t"+"下载成功");
				}else 
				{
					System.out.println(fileInfo+"\t"+"下载失败");
				}
			}
}
