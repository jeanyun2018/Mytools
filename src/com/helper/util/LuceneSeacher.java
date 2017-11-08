package com.helper.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.helper.bean.FileInfo;
import com.helper.inter.ICallBackSeacher;
/**
 * 基于Lucene实现索引查询
 * @author jx
 *
 */
public class LuceneSeacher {
	private ICallBackSeacher backSercher;
	public static int SELECT_TITLE = 1;
	public static int SELECT_CONTENT = 2;
	
	public void setBackSercher(ICallBackSeacher backSercher) {
		this.backSercher = backSercher;
	}

	public static void main(String[] args) {

		LuceneSeacher seacher = new LuceneSeacher();
		seacher.createIndex("D:\\save", "D:\\tmp\\index");

	}

	/**
	 * 给一个目录下的所有文本文件创建索引
	 * 
	 * @param sourceDir
	 *            待索引的文件目录
	 * @param indexDir
	 *            索引文件存储目录
	 * @return
	 */
	public  void createIndex(String sourceDir, String indexDir) {
		List<File> fileList = getTheFileList(sourceDir,".txt");

		if (fileList.size() > 0) {
			// 遍历文件并分别创建索引
			for (File file : fileList) {
			//	System.out.println(file.getAbsolutePath());
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append(getFileContent(file));
				Analyzer analyzer = new StandardAnalyzer();
				try {
					File indexFile = new File(indexDir);
					if (!indexFile.exists()) {
						indexFile.mkdirs();
					}
					//存储到文件中
					Directory directory = FSDirectory.open(new File(indexDir).toPath());
					IndexWriterConfig config = new IndexWriterConfig(analyzer);
					IndexWriter indexWriter = new IndexWriter(directory, config);
					Document document = new Document();
					document.add(new TextField("fileName", file.getName(), Store.YES));
					document.add(new TextField("content", stringBuffer.toString(), Store.NO));
					document.add(new StringField("path", file.getPath(), Store.YES));
					indexWriter.addDocument(document);
					indexWriter.commit();

					indexWriter.close();
	
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if(backSercher!=null)
		{
			backSercher.createIndexFinsh();
		}
	}

	/**
	 * 在索引目录下检索关键字
	 * 
	 * @param indexDir
	 *            索引文件存储目录
	 * @param searchWord
	 *            搜索的关键字
	 */
	public void searchIndex(String indexDir, String searchWord,int flag) {
		Analyzer analyzer = new StandardAnalyzer();

		try {
			//从一个磁盘目录中检索
			Directory directory = FSDirectory.open(new File(indexDir).toPath());
			DirectoryReader directoryReader = DirectoryReader.open(directory);
			IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

			// 检索正文
			QueryParser queryParser = null;
			if(flag ==LuceneSeacher.SELECT_TITLE)
			{
				queryParser = new QueryParser("fileName", analyzer);
			}else if(flag==LuceneSeacher.SELECT_CONTENT)
			{
				queryParser = new QueryParser("content", analyzer);
			}

			Query query = queryParser.parse(searchWord);

			// 检索前1000个结果
		//	System.out.println("总共有 " + indexSearcher.count(query) + " 个结果");
			ScoreDoc[] hits = indexSearcher.search(query, 1000).scoreDocs;
			List<FileInfo> list = new ArrayList<FileInfo>();
		//	System.out.println("当前有 " + hits.length + " 个结果，分别如下：");
			for (int i = 0; i < hits.length; i++) {
				int docId = hits[i].doc;
				Document hitDoc = indexSearcher.doc(docId);
				list.add(new FileInfo(hitDoc.get("fileName"), hitDoc.get("path")));
			//	System.out.println("文件名： " + hitDoc.get("fileName") + "    路径： " + hitDoc.get("path"));
			}
			if(backSercher!=null)
			{
				backSercher.fileInfoCallBack(list);
			}
			directoryReader.close();
			directory.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * 获取一个目录下的所有文件
	 * 
	 * @param sourceDir
	 *            文件目录
	 * @return 所有文件的集合
	 */
	public static List<File>getTheFileList(String sourceDir,String suffix)
	{
		List<File> fileList = new ArrayList<File>();
		 LuceneSeacher.getFileList(fileList,new File(sourceDir),suffix);
		 return fileList;
	}
	private static void getFileList(List<File>fileList,File dir,String suffix) {
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			if (files.length > 0) {
				for (File tmpFile : files) {
					if(tmpFile.isDirectory())
					{
						LuceneSeacher.getFileList(fileList,tmpFile,suffix);
					}else 
						if(tmpFile.getName().endsWith(suffix))
								fileList.add(tmpFile);
				}
			}		
		}
	}
	public void deleteIndex(String folder)
	{
		File file  = new File(folder);
		if(file.isDirectory())
		for(File f:file.listFiles())
		{
			if(f.isFile())
			{
				f.delete();
			}
		}
	}
	/**
	 * 获取一个文本文件的所有内容
	 * 
	 * @param file
	 * @return
	 */
	private static StringBuffer getFileContent(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			StringBuffer stringBuffer = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				stringBuffer.append(line + "\n");
			}

			reader.close();
			return stringBuffer;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}