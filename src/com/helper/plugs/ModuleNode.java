package com.helper.plugs;

import javax.swing.JComponent;
/**
 *	ģ��ڵ㣬һ��ģ����ж���ڵ㣬ÿ���ڵ���һ���ڵ�����JPanel�ؼ���ɡ�
 * @author jx
 *
 */
public class ModuleNode 
{
		private String nodeName;
		private JComponent panel;
		private boolean isFirstLoad = false;
		private boolean isExistence = false;
		private int indexId = -1;
		

		public int getIndexId() {
			return indexId;
		}

		public void setIndexId(int indexId) {
			this.indexId = indexId;
		}

		public boolean isFirstLoad() {
			return isFirstLoad;
		}

		public void setFirstLoad(boolean isFirstLoad) {
			this.isFirstLoad = isFirstLoad;
		}

		public boolean isExistence() {
			return isExistence;
		}

		public void setExistence(boolean isExistence) {
			this.isExistence = isExistence;
		}

		public void whenAppPanel()
		{
			
		}
		
		public void setPanel(JComponent panel) {
			this.panel = panel;
		}

		public ModuleNode(String nodeName,JComponent panel)
		{
			this.nodeName = nodeName;
			this.panel = panel;
		}
		public JComponent getPanel() {
			whenAppPanel();
			return panel;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return nodeName;
		}
		
		
}
