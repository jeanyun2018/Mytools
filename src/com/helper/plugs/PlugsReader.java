package com.helper.plugs;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

import com.helper.cmd.CmdListener;
import com.helper.cmd.CmdListenerController;
import com.helper.config.Config;
import com.helper.inter.IHotKeyManager;
import com.helper.inter.LoadPlugsBack;
import com.helper.util.LuceneSeacher;
/**
 * 插件模块读取类
 * @author jx
 *
 */
public class PlugsReader   
{
	private DefaultMutableTreeNode treeNode;
	private LoadPlugsBack plugsBacker ;
	private IHotKeyManager manager =null;
	private PlugModule module;
	private URLClassLoader loader  =(URLClassLoader) ClassLoader.getSystemClassLoader();
	private CmdListenerController cmdListenerController = CmdListenerController.getCmdListenerController();
	
	public CmdListenerController getCmdListenerController() {
		return cmdListenerController;
	}
	public void setManager(IHotKeyManager manager) {
		this.manager = manager;
	}
	private List<File> files  = LuceneSeacher.getTheFileList(Config.WORK_TMP_FOLDER_PATH+Config.PLUGS_PATH,".jar");
	public PlugsReader(DefaultMutableTreeNode treeNode)
	{
		this.treeNode = treeNode;
		
	}
	
	public void setPlugsBacker(LoadPlugsBack plugsBacker) {
		this.plugsBacker = plugsBacker;
	}


	private void addThePlug(IPlugModule module)
	{
			DefaultMutableTreeNode noduleTop = new DefaultMutableTreeNode(module.getModuleName());
			for(ModuleNode node :module.getModuleNodes(loader,manager))
			{
				if(plugsBacker!=null)
				{
					plugsBacker.addNode(node);
				}
				noduleTop.add(new DefaultMutableTreeNode(node));
			}
			treeNode.add(noduleTop);
	}
	public void getPlugs()
	{
		if(treeNode!=null)
		{
			for(File file:files)
			{
					try {
						URL url = file.toURI().toURL();
						URLClassLoader loader  = new URLClassLoader(new URL[]{url});
						
						Class<?> cls = loader.loadClass(file.getName().replace(".jar", ""));
						Object obj = cls.newInstance();
						if(obj instanceof PlugModule)
						{
							
							module = (PlugModule)obj;
							if(plugsBacker!=null)
							{
								plugsBacker.plugsBack(module);
							}
						
							ParameterBridge parameter = new ParameterBridge();
							parameter.setLoader(loader);
							parameter.setManager(manager);
							module.setParameterBridge(parameter);
							Thread thread = new Thread()
									{

										@Override
										public void run() {
											// TODO Auto-generated method stub
											module.moduleInit();
										}
											
									};
									thread.start();
							addThePlug(module);
						}
						else if(obj instanceof IPlugModule)
						{
							IPlugModule module = (IPlugModule)obj;
							if(plugsBacker!=null)
							{
								plugsBacker.plugsBack(module);
							}
							addThePlug(module);
						}else if(obj instanceof CmdListener)
						{
							CmdListener listener =	 (CmdListener)obj;
							cmdListenerController.putListener(listener);
							listener.setCallListener(cmdListenerController);
							listener.setLoader(loader);
								Thread thread = new Thread()
										{

											@Override
											public void run() {
												// TODO Auto-generated method stub
												listener.initListener();
											}
										};
								thread.start();
						}
						loader.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
			}

		}
		if(plugsBacker!=null)
		{
			plugsBacker.loadOver();
		}
	}


}
