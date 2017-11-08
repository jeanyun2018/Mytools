package com.helper.frame;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.baidu.translate.demo.TransApi;
import com.crawler.beans.DownLoadConfig;
import com.crawler.control.DownLoadImage;
import com.crawler.control.DownloadDocs;
import com.crawler.control.LinksSelecterTest;
import com.crawler.util.IfileCallFinish;
import com.helper.bean.FileInfo;
import com.helper.cmd.CmdListener;
import com.helper.cmd.CmdListenerController;
import com.helper.cmd.CmdReader;
import com.helper.cmd.RunCmd;
import com.helper.config.Config;
import com.helper.inter.ICallBackCmd;
import com.helper.inter.ICallBackOCR;
import com.helper.inter.ICallBackSeacher;
import com.helper.inter.IExcBack;
import com.helper.inter.LoadPlugsBack;
import com.helper.modules.RegistHotKey;
import com.helper.modules.ToRunOCR;
import com.helper.plugs.IPlugModule;
import com.helper.plugs.ModuleNode;
import com.helper.plugs.PlugsReader;
import com.helper.util.ExtClasspathLoader;
import com.helper.util.LuceneSeacher;
import com.helper.util.MyFileFilter;
import com.helper.util.StringTools;

import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class MainFrame extends JFrame
		implements IfileCallFinish, ICallBackOCR, ICallBackCmd, IExcBack, ICallBackSeacher,LoadPlugsBack {

	private JPanel contentPane;
	private JTextArea textArea_need_show;
	private JTextArea textArea_to_trans;
	private JRadioButton radioButton_to_zh;
	private JRadioButton radioButton_to_en;
	private JPanel panel_2;
	private JPanel panel;
	private JPanel panel_1;
	private JTextField textField_text_url;
	private JTextField textField_text_folder;
	private JTextField textField_text_links;
	private JTextField textField_text_title;
	private JTextField textField_text_data;
	private JTextField textField_text_num;
	private JTextField textField_text_delay;
	private JTextField textField_text_deep;
	private DownloadDocs docs = null;
	private DownLoadImage imgs = null;
	private JTextArea textArea_text;
	private JTabbedPane tabbedPane;
	private JLabel lblurl_1;
	private JTextField textField_img_url;
	private JLabel label_9;
	private JTextField textField_img_folder;
	private JLabel label_10;
	private JTextField textField_img_links;
	private JLabel label_11;
	private JTextField textField_img_imgs;
	private JLabel label_12;
	private JTextField textField_img_num;
	private JLabel label_13;
	private JTextField textField_img_delay;
	private JLabel label_14;
	private JTextField textField_img_deep;
	private JScrollPane scrollPane_3;
	private JButton button_4;
	private JButton button_5;
	private JButton button_6;
	private JTextArea textArea_img;
	private JLabel label_15;
	private JTextField textField_img_fileName;
	private TransApi api = new TransApi(Config.BAI_DU_APP_ID, Config.BAI_DU_SECURITY_KEY);
	private JTextField textField_ext_filePath;
	private JTextArea textArea_ext;
	private ToRunOCR ocr = new ToRunOCR();
	private JPanel panel_4;
	private JPanel panel_5;
	private JTextField textField_test_filePath;
	private JTextField textField_test_cmd;
	private JScrollPane scrollPane_6;
	private JTextArea textArea_test_ext;
	private JTextArea textArea_test_cmd;
	private JLabel label_18;
	private JTextField textField_test_threadNum;
	private JButton button_13;
	private CmdReader cmdReader = new CmdReader();
	private JTextField textField_search_srcdir;
	private JTextField textField_search_indexdir;
	private JTextField textField_search_key;
	private JScrollPane scrollPane_7;
	private JButton button_17;
	private JTable table;
	private JButton button_18;
	private LuceneSeacher seacher = new LuceneSeacher();
	private JRadioButton radioButton_title;
	private JRadioButton radioButton_content;
	private JButton button_20;
	private JCheckBox checkBox_text_clean;
	private int cmdBackNum = 0;
	private DefaultMutableTreeNode baseNode;
	private JTree tree;
	private String editor="";
	private RegistHotKey hotKey;
	private List<IPlugModule> modules = new ArrayList<IPlugModule>();
	private List<ModuleNode> nodes = new ArrayList<ModuleNode>();


	public void setModules(List<IPlugModule> modules) {
		this.modules = modules;
	}

	public RegistHotKey getHotKey() {
		return hotKey;
	}

	public DefaultMutableTreeNode getBaseNode() {
		return baseNode;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public CmdReader getCmdReader() {
		return cmdReader;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/**
		 * 配置UI
		 */
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("RootPane.setupButtonVisible", false);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
					/**
					 * 加载插件 D盘tmp文件夹下jar文件夹，所有jar文件，文件命名为包名点类名。 该类继承自MyPane类
					 */
				    ExtClasspathLoader.loadClasspath();
					PlugsReader plugsReader=new PlugsReader(frame.getBaseNode());
					plugsReader.setManager(frame.getHotKey());
					plugsReader.setPlugsBacker(frame);
					plugsReader.getPlugs();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/icos/frame.png")));
		setTitle("\u7F16\u7A0B\u52A9\u624B");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1244, 685);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		panel = new JPanel();
		tabbedPane.addTab("翻译", null, panel, null);

		JScrollPane scrollPane = new JScrollPane();

		JButton button = new JButton("\u7FFB\u8BD1");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread thread = new Thread() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						trans();

					}

				};
				thread.start();
			}
		});

		JLabel label = new JLabel("\u7FFB\u8BD1\u5185\u5BB9");
		ButtonGroup bg = new ButtonGroup();
		radioButton_to_en = new JRadioButton("\u7FFB\u8BD1\u4E3A\u82F1\u8BED");
		radioButton_to_en.setSelected(true);
		radioButton_to_zh = new JRadioButton("\u7FFB\u8BD1\u4E3A\u6C49\u8BED");
		bg.add(radioButton_to_zh);
		bg.add(radioButton_to_en);

		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup().addGap(40).addComponent(label)
						.addPreferredGap(ComponentPlacement.RELATED, 463, Short.MAX_VALUE)
						.addComponent(radioButton_to_zh).addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(radioButton_to_en).addGap(18).addComponent(button).addGap(87))
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup().addGap(31)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane_1, Alignment.TRAILING)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 874, GroupLayout.PREFERRED_SIZE))
								.addContainerGap(38, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGap(21)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(button)
								.addComponent(label).addComponent(radioButton_to_en).addComponent(radioButton_to_zh))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE).addGap(18)
				.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(69, Short.MAX_VALUE)));

		textArea_to_trans = new JTextArea();
		textArea_to_trans.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		scrollPane_1.setViewportView(textArea_to_trans);

		textArea_need_show = new JTextArea();
		textArea_need_show.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		scrollPane.setViewportView(textArea_need_show);
		panel.setLayout(gl_panel);

		panel_1 = new JPanel();
		tabbedPane.addTab("文章", null, panel_1, null);

		JLabel lblurl = new JLabel("\u79CD\u5B50URL");

		textField_text_url = new JTextField();
		textField_text_url.setColumns(10);

		JLabel label_1 = new JLabel("\u4FDD\u5B58\u6587\u4EF6\u5939");

		textField_text_folder = new JTextField();
		textField_text_folder.setColumns(10);

		JLabel label_2 = new JLabel("\u94FE\u63A5\u9009\u62E9\u5668");

		textField_text_links = new JTextField();
		textField_text_links.setColumns(10);

		JLabel label_3 = new JLabel("\u6807\u9898\u9009\u62E9\u5668");

		textField_text_title = new JTextField();
		textField_text_title.setColumns(10);

		JLabel label_4 = new JLabel("\u6587\u672C\u9009\u62E9\u5668");

		textField_text_data = new JTextField();
		textField_text_data.setColumns(10);

		JLabel label_5 = new JLabel("\u8FDB\u7A0B\u6570");

		textField_text_num = new JTextField();
		textField_text_num.setColumns(10);

		textField_text_delay = new JTextField();
		textField_text_delay.setColumns(10);

		JLabel label_6 = new JLabel("\u5EF6\u8FDF");

		JLabel label_7 = new JLabel("\u6DF1\u5EA6");

		textField_text_deep = new JTextField();
		textField_text_deep.setColumns(10);

		JScrollPane scrollPane_2 = new JScrollPane();

		JButton button_1 = new JButton("\u5F00\u59CB\u6293\u53D6");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toDocs();
			}
		});

		JButton button_2 = new JButton("\u6E05\u7A7A\u8BB0\u5F55");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_text.setText("");
			}
		});

		JButton button_3 = new JButton("\u505C\u6B62\u6293\u53D6");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (docs != null) {
					docs.shoutDown();
				}
			}
		});

		JLabel label_8 = new JLabel("\u63A7\u5236\u53F0");

		JButton button_19 = new JButton("\u6D4B\u8BD5\u94FE\u63A5");
		button_19.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toLinksTest();
			}
		});
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1
				.createSequentialGroup()
				.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1.createSequentialGroup()
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_panel_1.createSequentialGroup().addGap(61).addComponent(lblurl)
												.addGap(18).addComponent(textField_text_url, GroupLayout.PREFERRED_SIZE,
														276, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel_1.createSequentialGroup().addGap(52).addGroup(gl_panel_1
												.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panel_1.createSequentialGroup().addComponent(label_4)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(textField_text_data, GroupLayout.DEFAULT_SIZE,
																276, Short.MAX_VALUE))
												.addGroup(gl_panel_1.createSequentialGroup().addComponent(label_2)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(textField_text_links)))))
								.addGroup(gl_panel_1.createSequentialGroup().addGap(68).addComponent(label_8)))
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup().addGap(42)
										.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
												.addComponent(label_1).addComponent(label_3))
								.addGap(18)
								.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
										.addComponent(textField_text_title).addComponent(textField_text_folder,
												GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)))
								.addGroup(gl_panel_1.createSequentialGroup().addGap(31).addComponent(label_5).addGap(18)
										.addComponent(textField_text_num, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(label_6)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(textField_text_delay, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGap(8)
										.addComponent(
												label_7)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(textField_text_deep,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(Alignment.TRAILING,
										gl_panel_1.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(button_19).addGap(18).addComponent(button_1).addGap(18)
												.addComponent(button_2).addGap(18).addComponent(button_3))))
						.addGroup(gl_panel_1.createSequentialGroup().addGap(60).addComponent(scrollPane_2,
								GroupLayout.PREFERRED_SIZE, 836, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(59, Short.MAX_VALUE)));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addGap(41)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE).addComponent(lblurl)
								.addComponent(textField_text_url, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_text_folder, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addComponent(label_1))
				.addGap(38)
				.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE).addComponent(label_2)
						.addComponent(textField_text_links, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(label_3).addComponent(textField_text_title, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(45)
				.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE).addComponent(label_4)
						.addComponent(textField_text_data, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(label_5)
						.addComponent(textField_text_num, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_text_delay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(label_6).addComponent(label_7).addComponent(textField_text_deep,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE).addComponent(button_3)
								.addComponent(button_2).addComponent(button_1).addComponent(button_19))
						.addComponent(label_8)).addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(95, Short.MAX_VALUE)));

		textArea_text = new JTextArea();
		textArea_text.setEditable(false);
		scrollPane_2.setViewportView(textArea_text);
		panel_1.setLayout(gl_panel_1);

		panel_2 = new JPanel();
		tabbedPane.addTab("图集", null, panel_2, null);

		lblurl_1 = new JLabel("\u79CD\u5B50URL");

		textField_img_url = new JTextField();
		textField_img_url.setColumns(10);

		label_9 = new JLabel("\u4FDD\u5B58\u6587\u4EF6\u5939");

		textField_img_folder = new JTextField();
		textField_img_folder.setColumns(10);

		label_10 = new JLabel("\u94FE\u63A5\u9009\u62E9\u5668");

		textField_img_links = new JTextField();
		textField_img_links.setColumns(10);

		label_11 = new JLabel("\u56FE\u7247\u9009\u62E9\u5668");

		textField_img_imgs = new JTextField();
		textField_img_imgs.setColumns(10);

		label_12 = new JLabel("\u8FDB\u7A0B\u6570");

		textField_img_num = new JTextField();
		textField_img_num.setColumns(10);

		label_13 = new JLabel("\u5EF6\u8FDF");

		textField_img_delay = new JTextField();
		textField_img_delay.setColumns(10);

		label_14 = new JLabel("\u6DF1\u5EA6");

		textField_img_deep = new JTextField();
		textField_img_deep.setColumns(10);

		scrollPane_3 = new JScrollPane();

		button_4 = new JButton("\u5F00\u59CB\u6293\u53D6");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toImgs();
			}
		});

		button_5 = new JButton("\u6E05\u7A7A\u8BB0\u5F55");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_img.setText("");
			}
		});

		button_6 = new JButton("\u505C\u6B62\u6293\u53D6");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (imgs != null) {
					imgs.shoutDown();
				}
			}
		});

		label_15 = new JLabel("\u6587\u4EF6\u540D");

		textField_img_fileName = new JTextField();
		textField_img_fileName.setColumns(10);

		button_20 = new JButton("\u6D4B\u8BD5\u94FE\u63A5");
		button_20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toImgLinksTest();
			}
		});
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup().addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
								.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel_2.createSequentialGroup().addGap(44).addComponent(lblurl_1)
												.addGap(18).addComponent(textField_img_url, GroupLayout.PREFERRED_SIZE,
														260, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel_2.createSequentialGroup().addGap(33).addComponent(label_10)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(textField_img_links,
												GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)))
						.addGap(58)
						.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addComponent(label_9)
								.addComponent(label_11))
						.addGap(26)
						.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
								.addComponent(textField_img_imgs)
								.addComponent(textField_img_folder, GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)))
						.addGroup(gl_panel_2.createSequentialGroup().addGap(52).addGroup(gl_panel_2
								.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 749, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel_2.createSequentialGroup()
										.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
												.addComponent(button_20)
												.addGroup(gl_panel_2.createSequentialGroup().addComponent(label_12)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(textField_img_num, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(label_13)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(textField_img_delay, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(18)
												.addComponent(label_14).addGap(18)
												.addComponent(textField_img_deep, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(18)
												.addComponent(button_4)))
										.addGap(18).addComponent(button_5).addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(button_6)))
								.addPreferredGap(ComponentPlacement.RELATED, 22, Short.MAX_VALUE)))
						.addGap(127))
				.addGroup(gl_panel_2.createSequentialGroup().addGap(110)
						.addComponent(label_15).addGap(38).addComponent(textField_img_fileName,
								GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(575, Short.MAX_VALUE)));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup().addGap(37)
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE).addComponent(lblurl_1)
								.addComponent(textField_img_url, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(label_9).addComponent(textField_img_folder, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(36)
				.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE).addComponent(label_10)
						.addComponent(textField_img_links, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(label_11).addComponent(textField_img_imgs, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(31)
				.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE).addComponent(label_12)
						.addComponent(textField_img_num, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(label_13)
						.addComponent(textField_img_delay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(label_14)
						.addComponent(textField_img_deep, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(button_4).addComponent(button_5).addComponent(button_6))
				.addPreferredGap(ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
				.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel_2.createSequentialGroup()
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_img_fileName, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(button_20))
						.addGap(10)).addGroup(gl_panel_2.createSequentialGroup().addComponent(label_15).addGap(13)))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE).addGap(116)));

		textArea_img = new JTextArea();
		textArea_img.setEditable(false);
		scrollPane_3.setViewportView(textArea_img);
		panel_2.setLayout(gl_panel_2);

		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("提取", null, panel_3, null);

		JLabel label_16 = new JLabel("\u9009\u62E9\u672C\u5730\u6587\u4EF6");

		textField_ext_filePath = new JTextField();
		textField_ext_filePath.setEnabled(false);
		textField_ext_filePath.setColumns(10);

		JButton button_7 = new JButton("\u9009\u62E9\u6587\u4EF6");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toChooseFile();
			}
		});

		JScrollPane scrollPane_4 = new JScrollPane();

		JButton button_8 = new JButton("\u5185\u5BB9\u63D0\u53D6");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toExtImg();
			}
		});
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup().addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_3.createSequentialGroup().addGap(160).addComponent(label_16).addGap(18)
								.addComponent(textField_ext_filePath, GroupLayout.PREFERRED_SIZE, 281,
										GroupLayout.PREFERRED_SIZE)
								.addGap(26).addComponent(button_7).addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(button_8))
						.addGroup(gl_panel_3.createSequentialGroup().addGap(119).addComponent(scrollPane_4,
								GroupLayout.PREFERRED_SIZE, 684, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(152, Short.MAX_VALUE)));
		gl_panel_3.setVerticalGroup(gl_panel_3.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_3
				.createSequentialGroup().addGap(85)
				.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE).addComponent(label_16)
						.addComponent(textField_ext_filePath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(button_7).addComponent(button_8))
				.addGap(18).addComponent(scrollPane_4, GroupLayout.PREFERRED_SIZE, 333, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(92, Short.MAX_VALUE)));

		textArea_ext = new JTextArea();
		scrollPane_4.setViewportView(textArea_ext);
		panel_3.setLayout(gl_panel_3);

		panel_4 = new JPanel();
		tabbedPane.addTab("引擎", null, panel_4, null);

		JLabel label_19 = new JLabel("\u641C\u7D22\u8D44\u6E90\u76EE\u5F55");

		textField_search_srcdir = new JTextField();
		textField_search_srcdir.setEnabled(false);
		textField_search_srcdir.setColumns(10);

		JButton button_14 = new JButton("\u9009\u62E9\u8D44\u6E90\u76EE\u5F55");
		button_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String folder = toChooseFolder();
				if (StringTools.StringIsNotEmpty(folder)) {
					textField_search_srcdir.setText(folder);
				}
			}
		});

		JLabel label_20 = new JLabel("\u7D22\u5F15\u6587\u4EF6\u76EE\u5F55");

		textField_search_indexdir = new JTextField();
		textField_search_indexdir.setEnabled(false);
		textField_search_indexdir.setColumns(10);

		JButton button_15 = new JButton("\u9009\u62E9\u7D22\u5F15\u76EE\u5F55");
		button_15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String folder = toChooseFolder();
				if (StringTools.StringIsNotEmpty(folder)) {
					textField_search_indexdir.setText(folder);
				}
			}
		});

		textField_search_key = new JTextField();
		textField_search_key.setColumns(10);

		JButton button_16 = new JButton("\u641C\u7D22");
		button_16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toseach();
			}
		});

		scrollPane_7 = new JScrollPane();

		button_17 = new JButton("\u6E05\u7A7A");
		button_17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel dfm = (DefaultTableModel) table.getModel();
				dfm.setRowCount(0);
			}
		});

		button_18 = new JButton("\u751F\u6210\u7D22\u5F15");
		button_18.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buildIndex();
			}
		});
		ButtonGroup thebg = new ButtonGroup();
		radioButton_content = new JRadioButton("\u6B63\u6587\u641C\u7D22");
		radioButton_content.setSelected(true);
		radioButton_title = new JRadioButton("\u6807\u9898\u641C\u7D22");
		thebg.add(radioButton_title);
		thebg.add(radioButton_content);
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4
				.setHorizontalGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
								.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING, false)
										.addGroup(Alignment.LEADING,
												gl_panel_4.createSequentialGroup().addGap(116).addComponent(
														scrollPane_7))
						.addGroup(Alignment.LEADING, gl_panel_4.createSequentialGroup().addGroup(gl_panel_4
								.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_panel_4.createSequentialGroup().addContainerGap()
										.addComponent(radioButton_title).addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(radioButton_content).addGap(18))
								.addGroup(Alignment.TRAILING, gl_panel_4.createSequentialGroup()
										.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_panel_4.createSequentialGroup().addContainerGap()
														.addComponent(label_19))
										.addGroup(gl_panel_4.createSequentialGroup().addGap(124).addComponent(label_20,
												GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
										.addGap(52)))
								.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
										.addComponent(textField_search_key, GroupLayout.DEFAULT_SIZE, 267,
												Short.MAX_VALUE)
										.addComponent(textField_search_indexdir, GroupLayout.DEFAULT_SIZE, 267,
												Short.MAX_VALUE)
										.addComponent(textField_search_srcdir, GroupLayout.DEFAULT_SIZE, 267,
												Short.MAX_VALUE))
								.addGap(15)
								.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panel_4.createSequentialGroup().addGap(60)
														.addComponent(button_16))
										.addGroup(
												gl_panel_4.createSequentialGroup().addGap(30).addComponent(button_15)))
										.addComponent(button_14))
								.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel_4.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(button_18))
										.addGroup(gl_panel_4.createSequentialGroup().addGap(31)
												.addComponent(button_17)))))
								.addContainerGap(167, Short.MAX_VALUE)));
		gl_panel_4.setVerticalGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup().addGap(44)
						.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE).addComponent(button_14)
								.addComponent(textField_search_srcdir, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_19))
				.addGap(18)
				.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_search_indexdir, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_20))
						.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE).addComponent(button_15)
								.addComponent(button_18)))
				.addGap(18)
				.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_search_key, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(radioButton_content).addComponent(radioButton_title)).addComponent(button_16)
						.addComponent(button_17)).addGap(28)
				.addComponent(scrollPane_7, GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(95, Short.MAX_VALUE)));

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					openFile();
				}
			}
		});
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "\u6587\u4EF6\u540D", "\u8DEF\u5F84" }) {
			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane_7.setViewportView(table);
		panel_4.setLayout(gl_panel_4);

		panel_5 = new JPanel();
		tabbedPane.addTab("测试", null, panel_5, null);

		JLabel lblXml = new JLabel("xml\u6587\u4EF6\u9009\u62E9");

		textField_test_filePath = new JTextField();
		textField_test_filePath.setEnabled(false);
		textField_test_filePath.setColumns(10);

		JButton button_9 = new JButton("\u9009\u62E9\u6587\u4EF6");
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser c = new JFileChooser();
				c.setFileSelectionMode(JFileChooser.FILES_ONLY);
				c.setFileFilter(new MyFileFilter(".xml"));
				c.setDialogTitle("选择需要解析的文件");
				c.setAcceptAllFileFilterUsed(false);
				int result = c.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					textField_test_filePath.setText(c.getSelectedFile().getAbsolutePath());
				}
			}
		});

		JButton button_10 = new JButton("\u6267\u884C\u6587\u4EF6");
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excutCmdFile();
			}
		});

		JLabel label_17 = new JLabel("\u5355\u884C\u547D\u4EE4");

		textField_test_cmd = new JTextField();
		textField_test_cmd.setColumns(10);

		JButton button_11 = new JButton("\u6267\u884C\u547D\u4EE4");
		button_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exeutCmd();
			}
		});

		JScrollPane scrollPane_5 = new JScrollPane();

		scrollPane_6 = new JScrollPane();

		JButton button_12 = new JButton("\u6E05\u9664\u8BB0\u5F55");
		button_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_test_cmd.setText("");
				textArea_test_ext.setText("");
			}
		});

		label_18 = new JLabel("\u8FDB\u7A0B\u6570");

		textField_test_threadNum = new JTextField();
		textField_test_threadNum.setColumns(10);

		button_13 = new JButton("\u505C\u6B62\u6267\u884C");
		button_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shoutdownCmd();
			}
		});

		checkBox_text_clean = new JCheckBox("\u81EA\u52A8\u6E05\u7A7A");
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_5.createSequentialGroup().addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_5.createSequentialGroup().addGap(35)
								.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_panel_5.createSequentialGroup().addComponent(lblXml).addGap(28))
										.addGroup(gl_panel_5.createSequentialGroup().addComponent(checkBox_text_clean)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(label_17)
												.addPreferredGap(ComponentPlacement.UNRELATED)))
						.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING, false)
								.addComponent(textField_test_cmd)
								.addComponent(textField_test_filePath, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE))
						.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_5.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(label_18).addGap(6).addComponent(textField_test_threadNum,
												GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel_5.createSequentialGroup().addGap(26).addComponent(button_11)))
						.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_5.createSequentialGroup().addGap(18).addComponent(button_9)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(button_10))
								.addGroup(gl_panel_5.createSequentialGroup().addGap(12).addComponent(button_12)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(button_13)))
						.addPreferredGap(ComponentPlacement.RELATED)).addGroup(
								gl_panel_5.createSequentialGroup().addGap(125)
										.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
												.addComponent(scrollPane_6, GroupLayout.DEFAULT_SIZE, 727,
														Short.MAX_VALUE)
										.addComponent(scrollPane_5, GroupLayout.DEFAULT_SIZE, 727, Short.MAX_VALUE))))
				.addGap(103)));
		gl_panel_5.setVerticalGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup().addGap(51)
						.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE).addComponent(lblXml)
								.addComponent(textField_test_filePath, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(button_10).addComponent(button_9).addComponent(label_18)
								.addComponent(textField_test_threadNum, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(30)
				.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_test_cmd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(button_11).addComponent(button_13).addComponent(button_12).addComponent(label_17)
						.addComponent(checkBox_text_clean)).addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(scrollPane_5, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE).addGap(18)
				.addComponent(scrollPane_6, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(77, Short.MAX_VALUE)));

		textArea_test_ext = new JTextArea();
		textArea_test_ext.setEditable(false);
		scrollPane_5.setViewportView(textArea_test_ext);

		textArea_test_cmd = new JTextArea();
		textArea_test_cmd.setEditable(false);
		scrollPane_6.setViewportView(textArea_test_cmd);
		panel_5.setLayout(gl_panel_5);

		JScrollPane scrollPane_8 = new JScrollPane();

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(24)
						.addComponent(scrollPane_8, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
						.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 960, GroupLayout.PREFERRED_SIZE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
										.addComponent(scrollPane_8))
								.addComponent(tabbedPane, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 587,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(53, Short.MAX_VALUE)));

		tree = new JTree();
		baseNode = new DefaultMutableTreeNode("MyTools");
		tree.setModel(new DefaultTreeModel(baseNode));
		scrollPane_8.setViewportView(tree);
		contentPane.setLayout(gl_contentPane);
		setResizable(false);
		setLocationRelativeTo(null);
		initFrame();
	
	}

	protected void toImgLinksTest() {
		// TODO Auto-generated method stub
		linksTest(textField_img_url.getText(), textField_img_links.getText());
	}

	protected void toLinksTest() {
		// TODO Auto-generated method stub
		linksTest(textField_text_url.getText(), textField_text_links.getText());
	}

	protected void linksTest(String url, String inks) {
		// TODO Auto-generated method stub

		if (StringTools.StringIsNotEmpty(url) && StringTools.StringIsNotEmpty(inks)) {
			LinksSelecterTest linksTest = new LinksSelecterTest();
			DownLoadConfig config = new DownLoadConfig();
			config.setCatalogPath(url);
			config.setLinksSelecter(inks);
			linksTest.setConfig(config);
			ShowLinks showLinks = new ShowLinks();
			showLinks.setVisible(true);
			linksTest.setInksBacker(showLinks);
			Thread thread = new Thread(linksTest);
			thread.start();
		} else {
			showTheTip(this, "请输入种子URL，以及链接选择器");
		}

	}

	protected void openFile() {
		// TODO Auto-generated method stub
		int row = table.getSelectedRow();
		String path = (String) table.getValueAt(row, 1);
		String strCmd = "notepad ";
		if(StringTools.StringIsNotEmpty(editor))
		{
			strCmd = editor+" ";
		}
		RunCmd cmd = new RunCmd(strCmd + path);
		cmd.start();
	}

	protected void toseach() {
		// TODO Auto-generated method stub

		String indexdir = textField_search_indexdir.getText();
		File dir = new File(indexdir);
		if (dir.listFiles().length == 0) {
			showTheTip(this, "索引文件夹为空，请先生成索引");
			return;
		}
		String searchWord = textField_search_key.getText();

		if (StringTools.StringIsNotEmpty(indexdir) && StringTools.StringIsNotEmpty(searchWord)) {
			Thread thread = new Thread() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (radioButton_title.isSelected()) {
						seacher.searchIndex(indexdir, searchWord, LuceneSeacher.SELECT_TITLE);
					} else if (radioButton_content.isSelected()) {
						seacher.searchIndex(indexdir, searchWord, LuceneSeacher.SELECT_CONTENT);
					}
				}

			};
			thread.start();
		} else {
			showTheTip(this, "请确认搜索条件与索引目录不为空");
		}

	}

	protected void buildIndex() {
		// TODO Auto-generated method stub
		String srcdir = textField_search_srcdir.getText();
		String indexdir = textField_search_indexdir.getText();
		File index = new File(indexdir);
		for (File file : index.listFiles()) {
			file.delete();
		}
		if (StringTools.StringIsNotEmpty(srcdir) && StringTools.StringIsNotEmpty(indexdir)) {
			Thread thread = new Thread() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					seacher.createIndex(srcdir, indexdir);
				}

			};
			thread.start();
		} else {
			showTheTip(this, "请选择索引文件夹与资源文件夹");
		}
	}

	protected void shoutdownCmd() {
		// TODO Auto-generated method stub
		if (this.cmdReader.getPool() != null) {
			this.cmdReader.getPool().shutdown();
		}
	}

	protected void excutCmdFile() {
		// TODO Auto-generated method stub

		if (StringTools.StringIsNotEmpty(textField_test_filePath.getText())) {
			int threadNum;
			try {
				threadNum = Integer.parseInt(textField_test_threadNum.getText());
			} catch (Exception e) {
				// TODO: handle exception
				threadNum = 3;
			}
			threadNum = threadNum > 3 ? threadNum : 3;
			this.cmdReader.setFilePath(textField_test_filePath.getText());
			this.cmdReader.setThreadNum(threadNum);
			Thread thread = new Thread() {

				@Override
				public void run() {
					getCmdReader().Init();
					getCmdReader().reader();
				}

			};
			thread.start();
		} else {
			showTheTip(this, "请选择需要执行的xml文件");
		}
	}

	protected void exeutCmd() {
		// TODO Auto-generated method stub

		if (StringTools.StringIsNotEmpty(textField_test_cmd.getText())) {
			RunCmd cmd = new RunCmd(textField_test_cmd.getText());
			cmd.setCmdListener(this);
			cmd.start();
		} else {
			showDialog("输入的命令");
		}
	}

	private void showTheTip(Component parentComponent, String data) {
		JOptionPane.showMessageDialog(parentComponent, data, "提示", JOptionPane.INFORMATION_MESSAGE);
	}

	protected void toExtImg() {
		// TODO Auto-generated method stub
		if (!StringTools.StringIsNotEmpty(textField_ext_filePath.getText())) {
			showTheTip(this, "请选择需要提取的图片");

			return;
		} else {
			ocr.setBackOCR(this);
			ocr.setFilePath(textField_ext_filePath.getText());
			Thread thread = new Thread() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						ocr.toRun();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			};
			thread.start();
		}
	}

	private String toChooseFolder() {
		JFileChooser c = new JFileChooser();
		c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		c.setDialogTitle("选择文件夹");
		int result = c.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			return c.getSelectedFile().getAbsolutePath();
		} else {
			return null;
		}
	}

	protected void toChooseFile() {
		// TODO Auto-generated method stub
		JFileChooser c = new JFileChooser();
		c.setFileSelectionMode(JFileChooser.FILES_ONLY);
		c.setFileFilter(new MyFileFilter(".png"));
		c.setFileFilter(new MyFileFilter(".jpg"));
		c.setFileFilter(new MyFileFilter(".bmp"));
		c.setDialogTitle("选择需要识别的图片");
		c.setAcceptAllFileFilterUsed(false);
		int result = c.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			textField_ext_filePath.setText(c.getSelectedFile().getAbsolutePath());
		}
	}

	protected void toImgs() {
		// TODO Auto-generated method stub
		String url = textField_img_url.getText();
		String folder = textField_img_folder.getText();
		folder = addSuffix(folder);
		String links = textField_img_links.getText();
		String imgss = textField_img_imgs.getText();
		if (!StringTools.StringIsNotEmpty(url)) {
			showDialog("种子URL");
			return;
		}
		if (!StringTools.StringIsNotEmpty(folder)) {
			showDialog("下载文件夹");
			return;
		}
		if (!StringTools.StringIsNotEmpty(links)) {
			showDialog("链接选择器");
			return;
		}
		if (!StringTools.StringIsNotEmpty(imgss)) {
			showDialog("图片选择器");
			return;
		}
		File file = new File(folder);
		if (!file.exists()) {
			file.mkdir();
		}
		DownLoadConfig config = new DownLoadConfig();
		config.setCatalogPath(url);
		config.setFolderPath(folder);
		config.setLinksSelecter(links);
		config.setImgSelecter(imgss);
		String num = textField_img_num.getText();
		if (StringTools.StringIsNotEmpty(num)) {
			int numInt = stringToInt(num);
			if (numInt < 0) {
				showTheTip(this, "请输入正确的线程数");
				return;
			} else {
				config.setThreadNum(numInt);
			}
		}
		String delay = textField_img_delay.getText();
		if (StringTools.StringIsNotEmpty(delay)) {
			int delayInt = stringToInt(delay);
			if (delayInt < 0) {
				showTheTip(this, "请输入正确的延迟");
				return;
			} else {
				config.setDelay(delayInt);
			}
		}
		String deep = textField_img_deep.getText();
		if (StringTools.StringIsNotEmpty(deep)) {
			int deepInt = stringToInt(deep);
			if (deepInt < 0) {
				showTheTip(this, "请输入正确的深度");
				return;
			} else {
				config.setDeep(deepInt);
			}
		}
		String fileName = textField_img_fileName.getText();
		if (StringTools.StringIsNotEmpty(fileName)) {
			config.setFileName(fileName);
		}
		config.setIsFinish(this);
		if (imgs == null) {
			imgs = new DownLoadImage();
		}
		imgs.setConfig(config);
		Thread thread = new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				imgs.beginWork();
			}

		};
		thread.start();
	}

	private void showDialog(String tip) {
		JOptionPane.showMessageDialog(this, tip + "不能为空", "提示", JOptionPane.INFORMATION_MESSAGE);
	}

	private String addSuffix(String folder) {
		if (!folder.endsWith("\\")) {
			return folder + "\\";
		}
		return folder;
	}

	protected void toDocs() {
		// TODO Auto-generated method stub
		String url = textField_text_url.getText();
		String folder = textField_text_folder.getText();
		folder = addSuffix(folder);
		String links = textField_text_links.getText();
		String titile = textField_text_title.getText();
		String data = textField_text_data.getText();
		if (!StringTools.StringIsNotEmpty(url)) {
			showDialog("种子URL");
			return;
		}
		if (!StringTools.StringIsNotEmpty(folder)) {
			showDialog("下载文件夹");
			return;
		}
		if (!StringTools.StringIsNotEmpty(links)) {
			showDialog("链接选择器");
			return;
		}
		if (!StringTools.StringIsNotEmpty(titile)) {
			showDialog("标题选择器");
			return;
		}
		if (!StringTools.StringIsNotEmpty(data)) {
			showDialog("内容选择器");
			return;
		}
		File file = new File(folder);
		if (!file.exists()) {
			file.mkdir();
		}
		DownLoadConfig config = new DownLoadConfig();
		config.setCatalogPath(url);
		config.setFolderPath(folder);
		config.setLinksSelecter(links);
		config.setTitleSelecter(titile);
		config.setDataSelecter(data);
		String num = textField_text_num.getText();
		if (StringTools.StringIsNotEmpty(num)) {
			int numInt = stringToInt(num);
			if (numInt < 0) {
				showTheTip(this, "请输入正确的线程数");
				return;
			} else {
				config.setThreadNum(numInt);
			}
		}
		String delay = textField_text_delay.getText();
		if (StringTools.StringIsNotEmpty(delay)) {
			int delayInt = stringToInt(delay);
			if (delayInt < 0) {
				showTheTip(this, "请输入正确的延迟");
				return;
			} else {
				config.setDelay(delayInt);
			}
		}
		String deep = textField_text_deep.getText();
		if (StringTools.StringIsNotEmpty(deep)) {
			int deepInt = stringToInt(deep);
			if (deepInt < 0) {
				showTheTip(this, "请输入正确的深度");
				return;
			} else {
				config.setDeep(deepInt);
			}
		}
		config.setIsFinish(this);
		if (docs == null) {
			docs = new DownloadDocs();
		}
		docs.setConfig(config);
		Thread thread = new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				docs.beginWork();
			}
		};
		thread.start();
	}

	private int stringToInt(String src) {
		try {
			return Integer.parseInt(src);
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	}

	protected void trans() {
		// TODO Auto-generated method stub

		if (radioButton_to_en.isSelected()) {
			String src = api.getTransResult(textArea_need_show.getText(), "auto", "en");
			textArea_to_trans.setText(tryTans(src));
		} else if (radioButton_to_zh.isSelected()) {
			String src = api.getTransResult(textArea_need_show.getText(), "auto", "zh");
			textArea_to_trans.setText(tryTans(src));
		}

	}

	private String tryTans(String src) {
		JSONObject obj;
		StringBuffer buffer = new StringBuffer();
		try {
			obj = new JSONObject(src);
			JSONArray array = obj.getJSONArray("trans_result");
			for (int index = 0; index < array.length(); index++) {
				buffer.append(array.getJSONObject(index).getString("dst") + "\n");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer.toString();
	}

	private void initFrame() {
		// TODO Auto-generated method stub
		hotKey= new RegistHotKey(this);
		hotKey.startHotKey();
		this.cmdReader.setCmdListener(this);
		this.cmdReader.setExcBackListener(this);
		initSearchPath();
	
		seacher.setBackSercher(this);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
						if(modules!=null)
						{
							for(IPlugModule module:modules)
							{
									module.onWindowClose();
							}
						}
						
								CmdListenerController con = CmdListenerController.getCmdListenerController();
								if(con!=null)
								for(Entry<String, CmdListener> cmd:con.getMap().entrySet())
								{
									cmd.getValue().windowClose();
								}
			}
			
		});
		this.tree.addTreeSelectionListener( new TreeSelectionListener()
				{
					@Override
					public void valueChanged(TreeSelectionEvent e) {
						// TODO Auto-generated method stub
			              DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			              if(node==null)
			            	  return;
			              Object   obj = node.getUserObject();
			                if (node.isLeaf()) {
			                	ModuleNode mynode = (ModuleNode)obj;
			                	
			                	tryToaddPanel(mynode);
			                	
			                }
					}});
	}

	protected void tryToaddPanel(ModuleNode mynode) {
		// TODO Auto-generated method stub
			
			if(this.tabbedPane.getTabCount()==9)
			{
				for(int index=8;index>5;index--)
				{
					this.tabbedPane.remove(index);
					for(ModuleNode node :nodes)
					{
						if(node.getIndexId()==index)
						{
							node.setExistence(false);
						}
					}
					
				}
			}
			if(!mynode.isFirstLoad())
			{
				this.tabbedPane.add(mynode.toString(),mynode.getPanel());
				mynode.setFirstLoad(true);
				mynode.setExistence(true);
				mynode.setIndexId(this.tabbedPane.getTabCount()-1);
			}else 
			{
				if(mynode.isExistence())
				{
					tabbedPane.setSelectedIndex(mynode.getIndexId());
				}else 
				{
					this.tabbedPane.add(mynode.toString(),mynode.getPanel());
					mynode.setExistence(true);
					mynode.setIndexId(this.tabbedPane.getTabCount()-1);
				}
			}
			

	}

	private void initSearchPath() {
		// TODO Auto-generated method stub
		File config = new File(Config.WORK_TMP_FOLDER_PATH + Config.CONFIG_FILE_NAME);
		
		if (config.exists()) {
			Document doc = null;
			try {
				doc = Jsoup.parse(config, "utf-8");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (doc != null) {
				Element srcdir = doc.getElementsByTag("srcdir").get(0);
				if (StringTools.StringIsNotEmpty(srcdir.text())) {
					textField_search_srcdir.setText(srcdir.text());
				}
				Element indexdir = doc.getElementsByTag("indexdir").get(0);
				if (StringTools.StringIsNotEmpty(indexdir.text())) {
					textField_search_indexdir.setText(indexdir.text());
				}
				Element editor = doc.getElementsByTag("editor").get(0);
				if (StringTools.StringIsNotEmpty(editor.text())) {
					this.editor = editor.text();
				}
			}
		}
	}

	private void fileTable(List<FileInfo> files) {
		DefaultTableModel dfm = (DefaultTableModel) this.table.getModel();
		dfm.setRowCount(0);
		for (FileInfo fileInfo : files) {
			Vector<Object> v = new Vector<Object>();
			v.add(fileInfo.getFileName());
			v.add(fileInfo.getPath());
			dfm.addRow(v);
		}
	}

	private void attendText(JTextArea area, String data) {
		if (StringTools.StringIsNotEmpty(data)) {
			area.setText(area.getText() + data);
		}
	}

	@Override
	public void fileIsFinish(boolean arg0, String arg1) {
		// TODO Auto-generated method stub
		synchronized (this) {
			if (tabbedPane.getSelectedComponent() instanceof JPanel) {
				if (this.panel_1 == tabbedPane.getSelectedComponent()) {
					if (arg0) {
						attendText(textArea_text, arg1 + "\t" + "下载成功\n");
					} else {
						attendText(textArea_text, arg1 + "\t" + "下载失败\n");
					}
				} else if (this.panel_2 == tabbedPane.getSelectedComponent()) {
					if (arg0) {
						attendText(textArea_img, arg1 + "\t" + "下载成功\n");
					} else {
						attendText(textArea_img, arg1 + "\t" + "下载失败\n");
					}
				}
			}
		}

	}

	@Override
	public void callBack(String backText) {
		// TODO Auto-generated method stub
		textArea_ext.setText(backText);
	}

	@Override
	public void tryToexc(String cmd) {
		// TODO Auto-generated method stub
		synchronized (this) {
			attendText(textArea_test_ext, cmd + "\n");
		}
	}

	@Override
	public void cmdBacker(String backText) {
		// TODO Auto-generated method stub
		synchronized (this) {
			if (checkBox_text_clean.isSelected()) {
				cmdBackNum++;
			}
			if (cmdBackNum > Config.flashMax) {
				cmdBackNum = 0;
				textArea_test_cmd.setText("");
			}
			attendText(textArea_test_cmd, backText + "\n");
		}
	}

	@Override
	public void fileInfoCallBack(List<FileInfo> fileInfos) {
		// TODO Auto-generated method stub
		fileTable(fileInfos);
	}

	@Override
	public void createIndexFinsh() {
		// TODO Auto-generated method stub
		showTheTip(this, "建立索引完成!");
	}

	@Override
	public void plugsBack(IPlugModule module) {
		// TODO Auto-generated method stub
		this.modules.add(module);
	}

	@Override
	public void loadOver() {
		// TODO Auto-generated method stub
		if(this.modules.size()>0)
		{
			this.tree.expandPath(new TreePath(baseNode.getPath()));
		}
			CmdListenerController con = CmdListenerController.getCmdListenerController();
			con.setCmdbacker(this);
			if(con!=null)
			for(Entry<String, CmdListener> cmd:con.getMap().entrySet())
			{
				cmd.getValue().setCmdbacker(this);
			}

	}

	@Override
	public void addNode(ModuleNode node) {
		// TODO Auto-generated method stub
		this.nodes.add(node);
	}
}
