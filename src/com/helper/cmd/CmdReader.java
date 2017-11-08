package com.helper.cmd;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.helper.inter.ICallBackCmd;
import com.helper.inter.IExcBack;
import com.helper.util.MyFileReader;
import com.helper.util.StringTools;
/**
 * 			可设置ICallBackCmd 命令执行结果回调接口
 * 					     IExcBack      命令解析结果回调接口
 * 
 * 			xml文件包含若干个cmd标签 每个cmd标签对应一条cmd命令，cmd命令可执行多次
 * 		    cmd标签属性  context   该命令的上下文    可选
 * 							      times 	     该命令执行的次数 可选
 * 								  delay       该命令执行的延迟 可选
 * 			cmd命令可有子标签  可选
 * 					res    标签包裹命令段，包含部分命令的内容，多个res与其他内容拼接为一条命令
 * 					
 * 					list    标签与times配合使用，每次调用list中的一条内容，与res配合占位
 * 							 有filePath属性，每次读取一行。逐行调用。每个cmd标签只能有一个list
 * 					int	 标签占位一个整数，可选add属性 多次调用时自增步长，步长可为负数。
 * 					float  标签占位一个浮点数，用法同上。
 * 							均有可选参数 是否有空格nbsp=true 默认为flase
 * 					子标签需与res标签配合使用，res标签包裹静态命令，其他标签包裹动态参数，拼接为完整命令
 * @author jx
 *
 */
public class CmdReader 
{
		private  Document doc; 
		private CmdThreadPool pool  = null;
		private ICallBackCmd cmdListener = null; 			//命令执行结果回调接口
		private IExcBack excBackListener = null;			//命令解析结果回调接口
		private String filePath;
		private int threadNum;
		
		public void setExcBackListener(IExcBack excBackListener) {
			this.excBackListener = excBackListener;
		}
		
		
		public String getFilePath() {
			return filePath;
		}


		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}


		public int getThreadNum() {
			return threadNum;
		}


		public void setThreadNum(int threadNum) {
			this.threadNum = threadNum;
		}


		public CmdThreadPool getPool() {
			return pool;
		}


		public CmdReader()
		{
			
		}
		public void Init()
		{
			try {
				doc= Jsoup.parse(new File(filePath),"utf-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
			pool = new CmdThreadPool(threadNum);
		}
		public CmdReader(String filePath,int threadNum)
		{
			try {
				doc= Jsoup.parse(new File(filePath),"utf-8");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pool = new CmdThreadPool(threadNum);
		}
		
		public void setCmdListener(ICallBackCmd cmdListener) {
			this.cmdListener = cmdListener;
		}
		public void shutdown()
		{
			pool.shutdown();
		}
		public void test()
		{
			Elements cmds =  doc.getElementsByTag("list");
			System.out.println(cmds.size());
	
		}
		private float tryGetAdd(Element e)
		{
			if(e.hasAttr("add")&&StringTools.StringIsNotEmpty(e.attr("add")))
			{
				try {
					return Float.parseFloat(e.attr("add"));
				} catch (Exception e2) {
					// TODO: handle exception
					return 0;
				}
			
			}
			return 0;
		}
		private int tryGetTimes(Element e)
		{
			if(e.hasAttr("times")&&StringTools.StringIsNotEmpty(e.attr("times")))
			{
				try {
					return Integer.parseInt(e.attr("times"))>1?Integer.parseInt(e.attr("times")):1;
				} catch (Exception e2) {
					// TODO: handle exception
					return 1;
				}
			}
			return 1;
		}
		private int tryGetDelay(Element e)
		{
			if(e.hasAttr("delay")&&StringTools.StringIsNotEmpty(e.attr("delay")))
			{
				try {
					return Integer.parseInt(e.attr("delay"))>0?Integer.parseInt(e.attr("delay")):0;
				} catch (Exception e2) {
					// TODO: handle exception
					return 0;
				}
			}
			return 0;
		}
		private boolean tryGetNbsp(Element e)
		{
			if(e.hasAttr("nbsp")&&StringTools.StringIsNotEmpty(e.attr("nbsp")))
			{
				try {
					return Boolean.parseBoolean(e.attr("nbsp"));
				} catch (Exception e2) {
					// TODO: handle exception
					return false;
				}
			}
			return false;
		}
		public void  reader()
		{
			Elements cmds =  doc.getElementsByTag("cmd");
			for(Element cmd  :cmds)
			{
				Elements res =  cmd.getAllElements();
				if(res.size()==1)
				{
					runTheCmd(res.get(0));
				}
				else 
				{
					int timesNow = 0;
					Element cmdHaveRes  =res.get(0);
					int times = tryGetTimes(cmdHaveRes);
					int delay = tryGetDelay(cmdHaveRes);
					List<String> list = null;
					Elements lists = cmdHaveRes.getElementsByTag("list");
					if(lists.size()!=0)
					{
						Elements items  = lists.get(0).getElementsByTag("item");
						if(items.size()!=0)
						{
							list =new ArrayList<String>();
							for(Element e:items)
							{
								if(StringTools.StringIsNotEmpty(e.text()))
								list.add(e.text());
							}
						}else if(lists.get(0).hasAttr("filePath")&&StringTools.StringIsNotEmpty(lists.get(0).attr("filePath")))
						{
							list = MyFileReader.readFileByLines(lists.get(0).attr("filePath"));
						}
					}
					String context = "";
					if(cmdHaveRes.hasAttr("context"))
					{
						context = cmdHaveRes.attr("context");
					}
					for(int conut=0;conut<times;conut++)
					{
						StringBuffer buffer = new StringBuffer("");
						timesNow ++;
						for(int index=1;index<res.size();index++)
						{
							Element e = res.get(index);
							switch (e.tagName()) {
							case "res":
								if(tryGetNbsp(e))
								{
									buffer.append(" "+e.text());
								}else 
								{
									buffer.append(e.text());
								}
						
								break; 
							case "int":
								int baseInt = Integer.parseInt(e.text());
								int add  = (int)tryGetAdd(e);
								if(tryGetNbsp(e))
								{
									buffer.append(" "+(baseInt+add*timesNow));
								}else 
								{
									buffer.append(baseInt+add*timesNow);
								}
								break; 	
							case "float":
								float baseFloat = Float.parseFloat(e.text());
								float addf  = tryGetAdd(e);
								if(tryGetNbsp(e))
								{
									buffer.append(" "+(baseFloat+addf*timesNow));
								}
								else 
								{
									buffer.append(baseFloat+addf*timesNow);
								}		
								break; 
							case "list":
								
								if(list!=null)
								{
									if(list.size()>=timesNow)
									{
										if(tryGetNbsp(e))
										{
											buffer.append(" "+list.get(timesNow-1));
										}	
										else 
										{
											buffer.append(list.get(timesNow-1));
										}
									}else 
									{
										if(tryGetNbsp(e))
										{
											buffer.append(" "+list.get(list.size()-1));
										}else 
										{
											buffer.append(list.get(list.size()-1));
										}
									}
								}
								break; 
							}
							
						}
						if(excBackListener!=null)
						{
							excBackListener.tryToexc(buffer.toString());
						}
						if(StringTools.StringIsNotEmpty(context))
						{
							RunCmd theCmd = new RunCmd(buffer.toString(), context);
							if(cmdListener!=null)
							{
								theCmd.setCmdListener(cmdListener);
							}
							pool.addCmd(theCmd);
						}else 
						{
							RunCmd theCmd = new RunCmd(buffer.toString());
							if(cmdListener!=null)
							{
								theCmd.setCmdListener(cmdListener);
							}
							pool.addCmd(theCmd);
						}
						try {
							Thread.sleep(delay);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				
					
				
		
						
			
				}

			}
		}
	
		
		private void runTheCmd(Element e) {
			// TODO Auto-generated method stub
			int times = tryGetTimes(e);
			String context = "";
			int delay = tryGetDelay(e);
			if(e.hasAttr("context"))
			{
				context = e.attr("context");
			}
			
			for(int index=0;index<times;index++)
			{
				if(excBackListener!=null)
				{
					excBackListener.tryToexc(e.text());
				}
				if(StringTools.StringIsNotEmpty(context))
				{
					RunCmd cmd = 	new RunCmd(e.text(),context);
					if(cmdListener!=null)
					{
						cmd.setCmdListener(cmdListener);
					}
					pool.addCmd(cmd);
				}else 
				{
					RunCmd cmd = 	new RunCmd(e.text());
					if(cmdListener!=null)
					{
						cmd.setCmdListener(cmdListener);
					}
					pool.addCmd(cmd);
				}
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}

		}
}
