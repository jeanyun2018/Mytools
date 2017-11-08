package com.helper.modules;

import java.awt.Cursor;
/**
 * ��ͼ״̬ö��
 * @author jx
 *
 */
enum States{
	 NORTH_WEST(new Cursor(Cursor.NW_RESIZE_CURSOR)),//��ʾ������
	 NORTH(new Cursor(Cursor.N_RESIZE_CURSOR)),
	 NORTH_EAST(new Cursor(Cursor.NE_RESIZE_CURSOR)),
	 EAST(new Cursor(Cursor.E_RESIZE_CURSOR)),
	 SOUTH_EAST(new Cursor(Cursor.SE_RESIZE_CURSOR)),
	 SOUTH(new Cursor(Cursor.S_RESIZE_CURSOR)),
	 SOUTH_WEST(new Cursor(Cursor.SW_RESIZE_CURSOR)),
	 WEST(new Cursor(Cursor.W_RESIZE_CURSOR)),
	 MOVE(new Cursor(Cursor.MOVE_CURSOR)),
	 DEFAULT(new Cursor(Cursor.DEFAULT_CURSOR));
	 private Cursor cs;
	 States(Cursor cs){
	  this.cs=cs;
	 }
	 public Cursor getCursor(){
	  return cs;
	 }
	}