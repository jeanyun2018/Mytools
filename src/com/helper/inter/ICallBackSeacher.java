package com.helper.inter;

import java.util.List;

import com.helper.bean.FileInfo;
/**
 * ��ѯ����ص��ӿ�
 * @author jx
 *
 */
public interface ICallBackSeacher 
{
		public void fileInfoCallBack(List<FileInfo> fileInfos);
		public void createIndexFinsh();
}
