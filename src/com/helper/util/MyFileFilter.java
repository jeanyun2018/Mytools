package com.helper.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;
/**
 * 通用文件过滤器类
 * @author jx
 *
 */
public class MyFileFilter extends FileFilter {
	private String suffix;

	public MyFileFilter(String suffix) {
		this.suffix = suffix;
	}

	@Override
	public boolean accept(File f) {
		// TODO Auto-generated method stub
		if (f.isDirectory())
			return true;
		
		return f.getName().endsWith(suffix);
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return suffix;
	}

}
