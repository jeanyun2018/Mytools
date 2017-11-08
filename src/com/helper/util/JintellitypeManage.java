package com.helper.util;

import com.helper.config.HotKeys;
import com.helper.config.KeyboardKeys;
import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
/**
 * 全局快捷键注册类，重写callBack函数，获取快捷键ID，判断触发的快捷键
 * @author jx
 *
 */
public class JintellitypeManage 
{
	private JIntellitype jintellitype = JIntellitype.getInstance();
	
	

	public void callBack(int mark)
	{
		
	}
	
	private void registerHotKey()
	{
		/**
		 * 		注册系统级别快捷键
		 * 		不需要组合键，第二个参数为0
		 * 
		 */
		jintellitype.registerHotKey(HotKeys.START_SCREEN_SHOT, HotKeys.ZERO, KeyboardKeys.F3);
		jintellitype.registerHotKey(HotKeys.HIDE_SHOW_MAIN_FRAME, HotKeys.ZERO, KeyboardKeys.F4);
		

	}
	private void addHotKeyListener()
	{

		jintellitype.addHotKeyListener(new HotkeyListener() {
			
			@Override
			public void onHotKey(int markCode) {
	
				callBack(markCode);			
			}
		});  
	}
	public void unregisterHotKey()
	{
		jintellitype.unregisterHotKey(HotKeys.START_SCREEN_SHOT); 
		jintellitype.unregisterHotKey(HotKeys.HIDE_SHOW_MAIN_FRAME); 
	}
	public void startHotKey()
	{
		registerHotKey();
		addHotKeyListener();
	}
	public JintellitypeManage()
	{

	}

	public void registHotKey(int keyId, int keyOne, int keyTwo) {
		// TODO Auto-generated method stub
		jintellitype.registerHotKey(keyId, keyOne, keyTwo);
	}
	public void unregisterHotKey(int keyId)
	{
		jintellitype.unregisterHotKey(keyId); 
	}
}
