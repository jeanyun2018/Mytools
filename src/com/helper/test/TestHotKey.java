package com.helper.test;

import com.helper.inter.IHotKeyManager;
import com.helper.inter.IHotKeyMonitor;

public class TestHotKey implements IHotKeyMonitor
{
		public TestHotKey(IHotKeyManager man)
		{
			man.registHotKey(100, 0, 113);
			man.setHotKeyMonitor(this);
		}

		@Override
		public void hotKeyCallBack(int ketId) {
			// TODO Auto-generated method stub
			if(ketId==100)
			{
				System.out.println(100);
			}
		}
}
