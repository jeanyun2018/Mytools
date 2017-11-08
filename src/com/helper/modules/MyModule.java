package com.helper.modules;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

import com.helper.plugs.ModuleNode;

/**
 * ���ģ����
 * ��Ӧһ��jar�ļ���Ҳ����һ�����
 * @author jx
 *
 */
public class MyModule 
{
		private DefaultMutableTreeNode noduleTop;
		
		public MyModule(String moduleName)
		{
			
			noduleTop = new DefaultMutableTreeNode(moduleName);
		}
		public void add(ModuleNode node)
		{
			if(noduleTop!=null)
			{
				noduleTop.add(new DefaultMutableTreeNode(node));
			}
		}
		
		public DefaultMutableTreeNode getNoduleTop() {
			return noduleTop;
		}
		public void adds(List<ModuleNode> nodes)
		{
			if(noduleTop!=null)
			{
				for(ModuleNode node:nodes)
				noduleTop.add(new DefaultMutableTreeNode(node));
			}
		}
		
}
