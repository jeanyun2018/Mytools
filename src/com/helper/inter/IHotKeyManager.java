package com.helper.inter;
/**
 * 系统快捷键注册接口，给插件模块使用
 * 设置快捷键监听方法
 * 注册快捷键方法
 * 删除快捷键方法
 * @author jx
 *
 */
public interface IHotKeyManager 
{
		public void setHotKeyMonitor(IHotKeyMonitor  monitor); 
		public void registHotKey(int keyId,int keyOne,int keyTwo);
		public void unregisterHotKey(int keyId);
}
