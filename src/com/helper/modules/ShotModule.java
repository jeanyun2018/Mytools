package com.helper.modules;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPopupMenu;

import com.helper.config.Config;
import com.helper.frame.ShowOCR;
import com.helper.inter.ICallBackJPopupMenu;
import com.helper.inter.ICallBackOCR;
import com.helper.util.ClipboardTool;
/**
 * 截图模块的右键响应
 * @author jx
 *
 */
public class ShotModule implements ICallBackOCR,ICallBackJPopupMenu
{
	private JPopupMenu jPopup = new JPopupMenu();
	private  JButton btn_copy  = new JButton("复制");
	private  JButton btn_save  = new JButton("保存");
	private  JButton btn_git  = new JButton("提取");
	private  JButton btn_exit  = new JButton("退出");
	private ScreenShot shot;
	private ToRunOCR orc = new ToRunOCR();
	public ShotModule()
	{
		init();
	}
	public void startShot() {
		// TODO Auto-generated method stub

				shot.startShot();
	}
	
	private void writeImgNeedBack(String filePath,BufferedImage img)
	{
		writeImg(filePath,img);
		orc.setBackOCR(this);
		orc.setFilePath(filePath);
		Thread thread = new Thread()
				{
			  @Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							orc.toRun();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
						
				};
				thread.start();
		
	}
	private void writeImg(String filePath,BufferedImage img)
	{
		File f = new File(filePath);
		try {
			ImageIO.write(img, "png", f);
			img.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void init() {
		// TODO Auto-generated method stub
		jPopup.add(btn_copy);
		jPopup.add(btn_save);
		jPopup.add(btn_git);
		jPopup.add(btn_exit);
		shot = new ScreenShot(jPopup);
		shot.setCallBackJPopupMenu(this);
	}
	@Override
	public void callBack(String backText) {
		// TODO Auto-generated method stub

		ShowOCR show = new ShowOCR(backText);
		show.setVisible(true);
	}
	@Override
	public void CallBackJPopupMenu(Object source) {
		// TODO Auto-generated method stub
		if(source==btn_copy)
		{
			/**
			 *		点击复制按钮 
			 */
			if(shot.getSelectImg()!=null)
			{				
				ClipboardTool.setClipboardImage(shot.getSelectImg());		
				shot.getJWindow().dispose();
			}
		}else if(source==btn_save)
		{
			/**
			 * 		点击保存按钮
			 */
			shot.getJWindow().setVisible(false);
			JFileChooser chooser = 	new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = 	chooser.showSaveDialog(null);
			if(result== JFileChooser.APPROVE_OPTION)
			{
				SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");	
				writeImg(chooser.getSelectedFile().getAbsolutePath()+"\\"+df.format(new Date())+".png",shot.getSelectImg());
			}
			shot.getJWindow().dispose();
		}else if(source==btn_git)
		{
			/**
			 * 		点击提取按钮
			 */
			System.out.println("调用提取按钮");
			File file  = new File(Config.WORK_TMP_FOLDER_PATH);
			if(!file.exists())
			{
				file.mkdir();
			}
			writeImgNeedBack(Config.WORK_TMP_FOLDER_PATH+Config.WORK_TMP_FILE_NAME,
					shot.getSelectImg());
			shot.getJWindow().dispose();
		}else if(source==btn_exit)
		{
			/**
			 * 		点击退出按钮
			 */
			shot.getJWindow().dispose();
		}
	
				
	}


}
