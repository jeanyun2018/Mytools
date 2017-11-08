package com.helper.util;


import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import com.helper.config.Config;
 /**
  * ����jar����������Դ
  * @author jx
  *
  */
public final class ExtClasspathLoader {
     
    private static Method addURL = initAddMethod();
 
    private static URLClassLoader classloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
 
    /** 
     * ��ʼ��addUrl ����.
     * @return �ɷ���addUrl������Method����
     */
    private static Method initAddMethod() {
        try {
            Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
            add.setAccessible(true);
            return add;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
 
 
    /** 
     * ����jar classpath��
     */
    public static void loadClasspath() {
        List<String> files = getJarFiles();
        if(files!=null)
        for (String f : files) {
            loadClasspath(f);
        }
 
        List<String> resFiles = getResFiles();
        if(resFiles!=null)
        for (String r : resFiles) {
            loadResourceDir(r);
        }
    }
 
    private static void loadClasspath(String filepath) {
        File file = new File(filepath);
        loopFiles(file);
    }
 
    private static void loadResourceDir(String filepath) {
        File file = new File(filepath);
        loopDirs(file);
    }
 
    /** *//**    
     * ѭ������Ŀ¼���ҳ����е���Դ·����
     * @param file ��ǰ�����ļ�
     */
    private static void loopDirs(File file) {
        // ��Դ�ļ�ֻ����·��
    	System.out.println(file.getAbsolutePath());
        if (file.isDirectory()) {
            addURL(file);
            File[] tmps = file.listFiles();
            for (File tmp : tmps) {
                loopDirs(tmp);
            }
        }
    }
 
    /** 
     * ѭ������Ŀ¼���ҳ����е�jar����
     * @param file ��ǰ�����ļ�
     */
    private static void loopFiles(File file) {
        if (file.isDirectory()) {
            File[] tmps = file.listFiles();
            for (File tmp : tmps) {
                loopFiles(tmp);
            }
        }
        else {
            if (file.getAbsolutePath().endsWith(".jar") || file.getAbsolutePath().endsWith(".zip")) {
                addURL(file);
            }
        }
    }

    /**
     * ͨ��filepath�����ļ���classpath��
     * @param filePath �ļ�·��
     * @return URL
     * @throws Exception �쳣
     */
    private static void addURL(File file) {
        try {
            addURL.invoke(classloader, new Object[] { file.toURI().toURL() });;
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
    }
 
 
 
    /***
     * �������ļ��еõ����õ���Ҫ���ص�classpath���·�����ϡ�
     * @return
     */
    private static List<String> getJarFiles() {
        // TODO ��properties�ļ��ж�ȡ������Ϣ  ����������� �����Լ�new һ��List<String> Ȼ��� jar��·���ӽ�ȥ Ȼ�󷵻�
      	List<String> res = 	new ArrayList<String>();
    	res.add(Config.WORK_TMP_FOLDER_PATH);
    	return res;
    }
 
    /**
     * �������ļ��еõ����õ���Ҫ����classpath�����Դ·������
     * @return
     */
    private static List<String> getResFiles() {
        //TODO ��properties�ļ��ж�ȡ������Ϣ��  ����������� �����Լ�new һ��List<String> Ȼ��� jar��·���ӽ�ȥ Ȼ�󷵻�  �� ���û����Դ·��Ϊ�վͿ�����
    	return null;
    }
 
    public static void main(String[] args) {
        ExtClasspathLoader.loadClasspath();
    }
}