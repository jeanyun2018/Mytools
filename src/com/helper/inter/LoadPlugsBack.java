package com.helper.inter;

import com.helper.plugs.IPlugModule;
import com.helper.plugs.ModuleNode;

/**
 * �������ص��ýӿ�
 * @author jx
 *
 */
public interface LoadPlugsBack 
{
		public void addNode(ModuleNode node);
		public void plugsBack(IPlugModule module);
		public void loadOver();
}
