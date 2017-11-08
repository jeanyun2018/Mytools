package com.helper.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.helper.inter.ICallBackCmd;
/**
 * 		cmd命令执行类，继承自Thread，可多线程执行
 * 		 可配置ICallBackCmd 命令执行结果返回接口
 * @author jx
 *
 */
public class RunCmd extends Thread
{
		private String cmd;				//执行的命令
		private String context;			//命令执行的上下文
		private ICallBackCmd cmdListener = null;


		
		

		public void setCmdListener(ICallBackCmd cmdListener) {
			this.cmdListener = cmdListener;
		}
		public void toStart()
		{
		
			  boolean isWork = false;

			  
				try {
					  Process p;
					  File folder;
					  if(this.getContext()!=null)
					  {
						  folder = new File(this.getContext());
							if(folder.exists())
							{
								p = Runtime.getRuntime().exec(cmd,null,folder);
							}else 
							{
								p = Runtime.getRuntime().exec(cmd);
							}
					  }else 
					  {
						  p = Runtime.getRuntime().exec(cmd);
					  }
					
			
				    InputStream is = p.getInputStream();
				    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				    String line;

				    while((line = reader.readLine())!= null){
				    	isWork = true;
				    	if(cmdListener!=null)
				    	{
				    		cmdListener.cmdBacker(line);
				    	}

				    }
				    if(!isWork)
				    {
				    	is = p.getErrorStream();
				    	reader = new BufferedReader(new InputStreamReader(is));
					    while((line = reader.readLine())!= null){
					    	if(cmdListener!=null)
					    	{
					    		cmdListener.cmdBacker(line);
					    	}
							   
				    }
				    is.close();
				    reader.close();
				    p.destroy();
				    
				 
				    }else 
				    {
					    is.close();
					    reader.close();
					    p.destroy();
					
				    }
		
				}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		//"cmd /c calc"
		
		
		
		
		public RunCmd(String cmd)
		{
			this.cmd = cmd;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(this.cmd.startsWith("%"))
			{
				CmdListenerController con = CmdListenerController.getCmdListenerController();
				con.executeCmd(this.cmd);
			}
			else 
			toStart();
		}
		public RunCmd(String cmd,String context)
		{
			this.cmd = cmd;
			this.context = context;
		}



		public String getContext() {
			return context;
		}
		public void setContext(String context) {
			this.context = context;
		}
		public String getCmd() {
			return cmd;
		}

		public void setCmd(String cmd) {
			this.cmd = cmd;
		}
}
