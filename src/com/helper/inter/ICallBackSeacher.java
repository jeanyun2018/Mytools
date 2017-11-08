package com.helper.inter;

import java.util.List;

import com.helper.bean.FileInfo;
/**
 * 查询结果回调接口
 * @author jx
 *
 */
public interface ICallBackSeacher 
{
		public void fileInfoCallBack(List<FileInfo> fileInfos);
		public void createIndexFinsh();
}
