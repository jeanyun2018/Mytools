package com.helper.plugs;

import java.net.URLClassLoader;

import com.helper.inter.IHotKeyManager;
/**
 * ��������֮���������
 * ���ڲ���֮��Ĵ���
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
