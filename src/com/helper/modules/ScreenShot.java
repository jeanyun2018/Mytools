package com.helper.modules;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JWindow;

import com.helper.inter.ICallBackJPopupMenu;
import com.helper.util.ClipboardTool;
/**
 * ��ͼģ���еĽ�ͼ���
 * @author jx
 *
 */
@SuppressWarnings("serial")
public class ScreenShot extends JPanel implements MouseListener, MouseMotionListener {
	private BufferedImage bi;
	private int width, height;
	private int startX, startY, endX, endY, tempX, tempY;
	private JWindow jwindow = new JWindow();
	private Rectangle select = new Rectangle(0, 0, 0, 0);// ��ʾѡ�е�����
	private Cursor cs = new Cursor(Cursor.CROSSHAIR_CURSOR);// ��ʾһ������µ����״̬
	private States current = States.DEFAULT;// ��ʾ��ǰ�ı༭״̬
	private Rectangle[] rec;// ��ʾ�˸��༭�������
	// �����ĸ�����,�ֱ��ʾ˭�Ǳ�ѡ�е��������ϵĶ˵�
	public static final int START_X = 1;
	public static final int START_Y = 2;
	public static final int END_X = 3;
	public static final int END_Y = 4;
	private int currentX, currentY;// ��ǰ��ѡ�е�X��Y,ֻ����������Ҫ�ı�
	private Point p = new Point();// ��ǰ����Ƶĵص�
	private boolean showTip = true;// �Ƿ���ʾ��ʾ.���������һ��,����ʾ��������
    private JPopupMenu popup = null;
    private ICallBackJPopupMenu callBackJPopupMenu = null;
    
    
	public void setCallBackJPopupMenu(ICallBackJPopupMenu callBackJPopupMenu) {
		this.callBackJPopupMenu = callBackJPopupMenu;
	}

	public JWindow getJWindow() {
		return this.jwindow;
	}

	public boolean getShowTip() {
		return this.showTip;
	}
	public BufferedImage getSelectImg()
	{
		if(!showTip)
		return bi.getSubimage(select.x, select.y, select.width, select.height);
		else 
		return null;
	}
	public ScreenShot(JPopupMenu popup) {
		this();
		getJWindow().add(popup);
		this.popup = popup;
	
		for(Component  c:popup.getComponents())
		{
			if(c instanceof JButton)
			{
				((JButton)c).addActionListener( new ActionListener()
						{

							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub

								if(callBackJPopupMenu!=null)
								{
									callBackJPopupMenu.CallBackJPopupMenu(e.getSource());
								}
			
							}
						
						});
			}
		}
	}

	public ScreenShot() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		initRecs();
	}
	public void startShot()
	{
		try {
			Robot ro = new Robot();
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension di = tk.getScreenSize();
			Rectangle rec = new Rectangle(0, 0, di.width, di.height);
			BufferedImage bi = ro.createScreenCapture(rec);
			this.bi = bi;
			this.width = di.width;
			this.height = di.height;
			this.jwindow.setSize(di);
			
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		initJf(jwindow);
	}
	private void initJf(JWindow jwindow) {
		jwindow.getContentPane().add(this, BorderLayout.CENTER);

		jwindow.setVisible(true);
		jwindow.setAlwaysOnTop(true);
	}

	private void initRecs() {
		rec = new Rectangle[8];
		for (int i = 0; i < rec.length; i++) {
			rec[i] = new Rectangle();
		}
	}

	public void paintComponent(Graphics g) {
		g.drawImage(bi, 0, 0, width, height, this);
		g.setColor(Color.RED);
		g.drawLine(startX, startY, endX, startY);
		g.drawLine(startX, endY, endX, endY);
		g.drawLine(startX, startY, startX, endY);
		g.drawLine(endX, startY, endX, endY);
		int x = startX < endX ? startX : endX;
		int y = startY < endY ? startY : endY;
		select = new Rectangle(x, y, Math.abs(endX - startX), Math.abs(endY - startY));
		int x1 = (startX + endX) / 2;
		int y1 = (startY + endY) / 2;
		g.fillRect(x1 - 2, startY - 2, 5, 5);
		g.fillRect(x1 - 2, endY - 2, 5, 5);
		g.fillRect(startX - 2, y1 - 2, 5, 5);
		g.fillRect(endX - 2, y1 - 2, 5, 5);
		g.fillRect(startX - 2, startY - 2, 5, 5);
		g.fillRect(startX - 2, endY - 2, 5, 5);
		g.fillRect(endX - 2, startY - 2, 5, 5);
		g.fillRect(endX - 2, endY - 2, 5, 5);
		rec[0] = new Rectangle(x - 5, y - 5, 10, 10);
		rec[1] = new Rectangle(x1 - 5, y - 5, 10, 10);
		rec[2] = new Rectangle((startX > endX ? startX : endX) - 5, y - 5, 10, 10);
		rec[3] = new Rectangle((startX > endX ? startX : endX) - 5, y1 - 5, 10, 10);
		rec[4] = new Rectangle((startX > endX ? startX : endX) - 5, (startY > endY ? startY : endY) - 5, 10, 10);
		rec[5] = new Rectangle(x1 - 5, (startY > endY ? startY : endY) - 5, 10, 10);
		rec[6] = new Rectangle(x - 5, (startY > endY ? startY : endY) - 5, 10, 10);
		rec[7] = new Rectangle(x - 5, y1 - 5, 10, 10);
		if (showTip) {
			g.setColor(Color.CYAN);
			g.fillRect(p.x, p.y, 150, 20);
			g.setColor(Color.RED);
			g.drawRect(p.x, p.y, 150, 20);
			g.setColor(Color.BLACK);
			g.drawString("�밴�����ѡ���ͼ��", p.x, p.y + 15);
		}
	}

	// ���ݶ��������Ȱ˸��������ѡ�е�Ҫ�޸ĵ�X��Y������
	private void initSelect(States state) {
		switch (state) {
		case DEFAULT:
			currentX = 0;
			currentY = 0;
			break;
		case EAST:
			currentX = (endX > startX ? END_X : START_X);
			currentY = 0;
			break;
		case WEST:
			currentX = (endX > startX ? START_X : END_X);
			currentY = 0;
			break;
		case NORTH:
			currentX = 0;
			currentY = (startY > endY ? END_Y : START_Y);
			break;
		case SOUTH:
			currentX = 0;
			currentY = (startY > endY ? START_Y : END_Y);
			break;
		case NORTH_EAST:
			currentY = (startY > endY ? END_Y : START_Y);
			currentX = (endX > startX ? END_X : START_X);
			break;
		case NORTH_WEST:
			currentY = (startY > endY ? END_Y : START_Y);
			currentX = (endX > startX ? START_X : END_X);
			break;
		case SOUTH_EAST:
			currentY = (startY > endY ? START_Y : END_Y);
			currentX = (endX > startX ? END_X : START_X);
			break;
		case SOUTH_WEST:
			currentY = (startY > endY ? START_Y : END_Y);
			currentX = (endX > startX ? START_X : END_X);
			break;
		default:
			currentX = 0;
			currentY = 0;
			break;
		}
	}

	public void mouseMoved(MouseEvent me) {
		doMouseMoved(me);
		initSelect(current);
		if (showTip) {
			p = me.getPoint();
			repaint();
		}
	}

	// ���ⶨ��һ��������������ƶ�,��Ϊ��ÿ�ζ��ܳ�ʼ��һ����Ҫѡ��ĵ���
	private void doMouseMoved(MouseEvent me) {
		if (select.contains(me.getPoint())) {
			this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			current = States.MOVE;
		} else {
			States[] st = States.values();
			for (int i = 0; i < rec.length; i++) {
				if (rec[i].contains(me.getPoint())) {
					current = st[i];
					this.setCursor(st[i].getCursor());
					return;
				}
			}
			this.setCursor(cs);
			current = States.DEFAULT;
		}
	}

	public void mouseExited(MouseEvent me) {
	}

	public void mouseEntered(MouseEvent me) {
	}

	public void mouseDragged(MouseEvent me) {
		int x = me.getX();
		int y = me.getY();
		if (current == States.MOVE) {
			startX += (x - tempX);
			startY += (y - tempY);
			endX += (x - tempX);
			endY += (y - tempY);
			tempX = x;
			tempY = y;
		} else if (current == States.EAST || current == States.WEST) {
			if (currentX == START_X) {
				startX += (x - tempX);
				tempX = x;
			} else {
				endX += (x - tempX);
				tempX = x;
			}
		} else if (current == States.NORTH || current == States.SOUTH) {
			if (currentY == START_Y) {
				startY += (y - tempY);
				tempY = y;
			} else {
				endY += (y - tempY);
				tempY = y;
			}
		} else if (current == States.NORTH_EAST || current == States.NORTH_EAST || current == States.SOUTH_EAST
				|| current == States.SOUTH_WEST) {
			if (currentY == START_Y) {
				startY += (y - tempY);
				tempY = y;
			} else {
				endY += (y - tempY);
				tempY = y;
			}
			if (currentX == START_X) {
				startX += (x - tempX);
				tempX = x;
			} else {
				endX += (x - tempX);
				tempX = x;
			}
		} else {
			startX = tempX;
			startY = tempY;
			endX = me.getX();
			endY = me.getY();
		}
		this.repaint();
	}

	public void mousePressed(MouseEvent me) {
		showTip = false;
		tempX = me.getX();
		tempY = me.getY();
	}

	public void mouseReleased(MouseEvent me) {
		if (me.isPopupTrigger()) {
			if (current == States.MOVE) {
				showTip = true;
				p = me.getPoint();
				startX = 0;
				startY = 0;
				endX = 0;
				endY = 0;
				repaint();
			} else {
				if(me.isMetaDown())
				{
					if(popup!=null)
					{
						popup.show(jwindow, me.getX(), me.getY());
					}else 
					{
						jwindow.dispose();
						callBackImage(null);
					}
				}


			}
		}
	}

	/**
	 * ����Ϊ������� ȡ����ͼ���� ���򷵻ؽ�ͼ����ͼƬ
	 * 
	 * @param img
	 */
	public void callBackImage(BufferedImage img) {
		
		if (img == null) {
			System.out.println("��δѡ���ͼ����");
		} else {
			ClipboardTool.setClipboardImage(img);
		}
	}
	
	public void mouseClicked(MouseEvent me) {
		if (me.getClickCount() == 2) {
			Point p = me.getPoint();
			if (select.contains(p)) {
				if (select.x + select.width < this.getWidth() && select.y + select.height < this.getHeight()) {
					jwindow.dispose();
					callBackImage(bi.getSubimage(select.x, select.y, select.width, select.height));

				} else {

					int wid = select.width, het = select.height;
					if (select.x + select.width >= this.getWidth()) {
						wid = this.getWidth() - select.x;
					}
					if (select.y + select.height >= this.getHeight()) {
						het = this.getHeight() - select.y;
					}
					jwindow.dispose();
					callBackImage(bi.getSubimage(select.x, select.y, wid, het));
				}
			}
		}
	}
}