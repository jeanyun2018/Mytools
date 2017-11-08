package com.helper.cmd;

import java.util.HashMap;

import com.helper.inter.ICallBackCmd;
import com.helper.inter.IToCallListener;

public class CmdListenerController implements IToCallListener
{
		private CmdListenerController()
		{
			
		}
		private static class Controller{
				private  static CmdListenerController con = new CmdListenerController();
		}
		private HashMap<String, CmdListener> map  =new HashMap<String, CmdListener>();
		private ICallBackCmd cmdbacker = null;
		
		public ICallBackCmd getCmdbacker() {
			return cmdbacker;
		}
		public void setCmdbacker(ICallBackCmd cmdbacker) {
			this.cmdbacker = cmdbacker;
		}
		public static CmdListenerController getCmdListenerController()
		{
			return Controller.con;
		}
		public void putListener(CmdListener listener)
		{
			map.put(listener.getListenerName(), listener);
		}
		public HashMap<String, CmdListener> getMap()
		{
			return map;
		}
		public int getListenerSize()
		{
			return map.size();
		}
		private CmdListener getListener(String listenerName)
		{
			return map.get(listenerName);
		}
		public void executeCmd(String cmd)
		{
			//%listenerName% args
			//String cmd = new String("%name% 1 hhh iyhu guguighuihuihuihuihuihui http://fanyi.baidu.com/?aldtype=85&keyfrom=alading#en/zh/execute");
			String name  = cmd.substring(1,cmd.indexOf(" ")-1);
			CmdListener listener = getListener(name);
			
			if(listener!=null)
			{
				cmd = cmd.substring(cmd.indexOf(" ")+1);
				listener.runCmd(cmd.split(" "));
			}else 
			{
				if(cmdbacker!=null)
				{
					cmdbacker.cmdBacker("²å¼þÎ´ÕÒµ½!");
				}
			}
		}
		@Override
		public int callListener(String name, String[] args) {
			// TODO Auto-generated method stub
			CmdListener listener = getListener(name);
			if(listener==null)
			return -1;
			else 
			{
				listener.runCmd(args);
				return 0;
			}
		}
}
