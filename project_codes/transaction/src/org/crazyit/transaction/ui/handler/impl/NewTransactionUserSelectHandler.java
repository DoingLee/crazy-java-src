package org.crazyit.transaction.ui.handler.impl;

import org.crazyit.transaction.ui.dialog.NewTransactionDialog;
import org.crazyit.transaction.ui.handler.UserSelectHandler;

public class NewTransactionUserSelectHandler implements UserSelectHandler {
	//新建事务的窗口
	private NewTransactionDialog newDialog;
	public NewTransactionUserSelectHandler(NewTransactionDialog newDialog) {
		this.newDialog = newDialog;
	}
	public void confirm(String userId, String realName) {
		//改变新增窗口的处理人文本框的值
		this.newDialog.getHandlerField().setText(realName);
		this.newDialog.getHandlerIdField().setText(userId);
	}
}
