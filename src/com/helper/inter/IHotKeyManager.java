package com.helper.inter;
/**
 * ϵͳ��ݼ�ע��ӿڣ������ģ��ʹ��
 * ���ÿ�ݼ���������
 * ע���ݼ�����
 * ɾ����ݼ�����
 * @author jx
 *
 */
public interface IHotKeyManager 
{
		public void setHotKeyMonitor(IHotKeyMonitor  monitor); 
		public void registHotKey(int keyId,int keyOne,int keyTwo);
		public void unregisterHotKey(int keyId);
}
