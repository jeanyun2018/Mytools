package com.helper.inter;

import com.helper.plugs.IPlugModule;
import com.helper.plugs.ModuleNode;

/**
 * 插件导入回调用接口
 * @author jx
 *
 */
public interface LoadPlugsBack 
{
		public void addNode(ModuleNode node);
		public void plugsBack(IPlugModule module);
		public void loadOver();
}
