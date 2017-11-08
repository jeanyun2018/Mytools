package com.helper.plugs;

import java.net.URLClassLoader;

import com.helper.inter.IHotKeyManager;
/**
 * 插件与程序之间的链接桥
 * 用于参数之间的传递
 * @author jx
 *
 */
public class ParameterBridge 
{
		private URLClassLoader loader;
		private IHotKeyManager manager;
		
		
		
		public void setLoader(URLClassLoader loader) {
			this.loader = loader;
		}
		public void setManager(IHotKeyManager manager) {
			this.manager = manager;
		}
		public URLClassLoader getLoader() {
			return loader;
		}
		public IHotKeyManager getManager() {
			return manager;
		}
		
		
		
}
