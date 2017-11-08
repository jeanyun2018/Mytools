package com.helper.modules;



import java.util.ArrayList;
import java.util.List;

import com.helper.config.HotKeys;
import com.helper.frame.MainFrame;
import com.helper.inter.IHotKeyManager;
import com.helper.inter.IHotKeyMonitor;
import com.helper.util.JintellitypeManage;
/**
 * 快捷键注册类
 * 负责本程序所需的快捷键注册，以及插件模块的快捷键注册和结果回调
 * @author jx
 *
 */
public class RegistHotKey implements IHotKeyManager
{
	private MainFrame frame;
	private ShotModule shotModule = new ShotModule();
	private List<IHotKeyMonitor> hotKeyMonitor = new ArrayList<IHotKeyMonitor>();
	
	
	public RegistHotKey(MainFrame frame)
	{
		this.frame = frame;
		
	}
	JintellitypeManage man = new JintellitypeManage()
			{

				@Override
				public void callBack(int mark) {
					// TODO Auto-generated method stub
					switch (mark) {
					case HotKeys.HIDE_SHOW_MAIN_FRAME:
						hideAndShow();
						break;
					case HotKeys.START_SCREEN_SHOT:
						StartShot();
						break;
					default:
						if(hotKeyMonitor.size()>0)
							{
									for(IHotKeyMonitor monitor:hotKeyMonitor)
										monitor.hotKeyCallBack(mark);
							};
					}
				}
					
			};
	protected void StartShot() {
		// TODO Auto-generated method stub
		
		shotModule.startShot();
	}
	public void startHotKey()
	{
		man.startHotKey();
	}
	protected void hideAndShow() {
		// TODO Auto-generated method stub
		if(frame!=null)
		{
			if(frame.isVisible())
			{
				frame.setVisible(false);
			}else 
			{
				frame.setVisible(true);
			}

		}
	}
	@Override
	public void registHotKey(int keyId, int keyOne, int keyTwo) {
		// TODO Auto-generated method stub
		man.registHotKey(keyId, keyOne, keyTwo);
	}
	@Override
	public void unregisterHotKey(int keyId) {
		// TODO Auto-generated method stub
		man.unregisterHotKey(keyId);
	}
	@Override
	public void setHotKeyMonitor(IHotKeyMonitor monitor) {
		// TODO Auto-generated method stub
		hotKeyMonitor.add(monitor);
	}

	
	
}
