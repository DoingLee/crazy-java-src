package org.crazyit.transaction.ui.table;

import javax.swing.ImageIcon;

import org.crazyit.transaction.model.TransactionState;

/**
 * 事务状态下拉的元素对象
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class State {

	public final static String TRANSFER = "transfer";
	
	//状态对象
	public final static State PROCESS_STATE = new State("进行中的事务", TransactionState.PROCESSING);
	public final static State FINISHED_STATE = new State("已完成的事务", TransactionState.FINISHED);
	public final static State TRANSFER_STATE = new State("我转发的事务", TRANSFER);
	public final static State FOR_A_WHILE_STATE = new State("暂时不做的事务", TransactionState.FOR_A_WHILE);
	public final static State NOT_TO_DO_STATE = new State("不做的事务", TransactionState.NOT_TO_DO);
	public final static State INVALID_STATE = new State("无效的事务", TransactionState.INVALID);
	
	//状态图片
	public final static ImageIcon PROCESS_IMAGE = new ImageIcon("images/state/processing.gif", "进行中");
	public final static ImageIcon FINISHED_IMAGE = new ImageIcon("images/state/finished.gif", "已完成");
	public final static ImageIcon FOR_A_WHILE_IMAGE = new ImageIcon("images/state/forAWhile.gif", "暂时不做");
	public final static ImageIcon NOT_TO_DO_IMAGE = new ImageIcon("images/state/notToDo.gif", "不做");
	public final static ImageIcon INVALID_IMAGE = new ImageIcon("images/state/invalid.gif", "无效");	
	
	public State(String text, String value) {
		this.text = text;
		this.value = value;
	}
	
	private String text;
	
	private String value;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.text;
	}
	
	
}
