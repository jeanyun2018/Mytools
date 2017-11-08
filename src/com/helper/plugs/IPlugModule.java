package com.helper.plugs;

import java.net.URLClassLoader;
import java.util.List;

import com.helper.inter.IHotKeyManager;
/**
 * �Զ������ӿ�
 * �Զ�����ʵ�ָýӿڣ���ͨ��PlugModule��������ʵ�ֲ�����Ƽ������ࡣ
 * @author jx
 *
 */
public interface IPlugModule 
{
	public  String getModuleName();
	public  List<ModuleNode> getModuleNodes(URLClassLoader loader,IHotKeyManager manager);
	public void onWindowClose();
}
