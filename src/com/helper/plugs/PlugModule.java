package com.helper.plugs;
/**
 * ��������࣬��ʵ�ָ�����ʵ�ֲ����
 * ��ʼ������ʱ��Ĳ��������moduleInit�����У���ʼ����ʱ���Զ��Զ��̵߳ķ�ʽִ��
 * @author jx
 *
 */
public abstract class PlugModule implements IPlugModule
{
		private ParameterBridge parameterBridge;
		
		public void setParameterBridge(ParameterBridge parameterBridge) {
			this.parameterBridge = parameterBridge;
		}
		public ParameterBridge getParameterBridge() {
			return parameterBridge;
		}
		public abstract void moduleInit(); 
		
}
