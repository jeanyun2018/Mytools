package com.helper.modules;


import org.json.JSONArray;
import org.json.JSONObject;
import com.helper.config.Config;
import com.helper.inter.ICallBackOCR;
import com.helper.util.StringTools;
import com.youtu.Youtu;
/**
 * ͼƬʶ�������
 * @author jx
 *
 */
public class ToRunOCR  
{

		private String filePath;						//��Ҫʶ���ͼƬ·��
		private String fileUrl;						//��Ҫʶ���ͼƬURL
		private ICallBackOCR backOCR=null;		//�ص��ӿ�
		private Youtu youtu = new Youtu(Config.APP_ID, Config.SECRET_ID, Config.SECRET_KEY,Youtu.API_YOUTU_END_POINT,Config.USER_ID);
		
		
		
		public void setBackOCR(ICallBackOCR backOCR) {
			this.backOCR = backOCR;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}

		public void setFileUrl(String fileUrl) {
			this.fileUrl = fileUrl;
		}

		public ToRunOCR()
		{
			
		}
		
		public  void toRun()  throws Exception
		{
		
			JSONObject respose = null ;
			if(StringTools.StringIsNotEmpty(filePath))
			{
				respose= youtu.GeneralOcr(filePath);
			}else if(StringTools.StringIsNotEmpty(fileUrl))
			{
				respose = youtu.GeneralOcrUrl(fileUrl);
			}
			JSONArray res = respose.getJSONArray("items");
			StringBuffer	backText = new StringBuffer();
			for(int index=0;index<res.length();index++)
			{
				backText.append( res.getJSONObject(index).getString("itemstring")+"\n");
			}
			if(backOCR!=null)
				backOCR.callBack(backText.toString());
		
		}

	
		
}
