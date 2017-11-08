package com.helper.cmd;

import java.net.URLClassLoader;

import com.helper.inter.ICallBackCmd;
import com.helper.inter.IToCallListener;

public abstract class CmdListener 
{
		private ICallBackCmd cmdbacker = null;
		private IToCallListener callListener = null;
		private URLClassLoader loader;
		
		
		public URLClassLoader getLoader() {
			return loader;
		}
		public void setLoader(URLClassLoader loader) {
			this.loader = loader;
		}
		public IToCallListener getCallListener() {
			return callListener;
		}
		public void setCallListener(IToCallListener callListener) {
			this.callListener = callListener;
		}
		public ICallBackCmd getCmdbacker() {
			return cmdbacker;
		}
		public void setCmdbacker(ICallBackCmd cmdbacker) {
			this.cmdbacker = cmdbacker;
		}
		public abstract String getListenerName();
		public abstract void initListener();
		public abstract void windowClose();
		
		public abstract void runCmd(String[] args);
		
		
}
