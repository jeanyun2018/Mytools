package com.helper.plugs;

import java.net.URLClassLoader;
import java.util.List;

import com.helper.inter.IHotKeyManager;
/**
 * 自定义插件接口
 * 自定义需实现该接口，或通过PlugModule抽象类来实现插件。推荐抽象类。
 * @author jx
 *
 */
public interface IPlugModule 
{
	public  String getModuleName();
	public  List<ModuleNode> getModuleNodes(URLClassLoader loader,IHotKeyManager manager);
	public void onWindowClose();
}
