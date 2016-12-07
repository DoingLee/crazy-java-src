package org.crazyit.transaction.model;

import java.util.List;

public class Transaction extends ValueObject {

	//事务标题 
	private String TS_TITLE;
	//事务内容
	private String TS_CONTENT;
	//目标完成日期
	private String TS_TARGETDATE;
	//实际完成日期
	private String TS_FACTDATE;
	//开始日期(创建日期)
	private String TS_CREATEDATE;
	
	//发起人ID
	private String INITIATOR_ID;
	private User initiator;//发起人与事务的关系
	
	//当前处理人ID
	private String HANDLER_ID;
	private User handler;
	
	//上一个处理人ID
	private String PRE_HANDLER_ID;
	private User preHandler;
	
	//事务状态
	private String TS_STATE;
	
	//是否需要紧急处理, 0为不用, 1需要紧急处理
	private String IS_HURRY;
	
	private List<Log> logs;

	public String getTS_TITLE() {
		return TS_TITLE;
	}

	public void setTS_TITLE(String ts_title) {
		TS_TITLE = ts_title;
	}

	public String getTS_CONTENT() {
		return TS_CONTENT;
	}

	public void setTS_CONTENT(String ts_content) {
		TS_CONTENT = ts_content;
	}

	public String getINITIATOR_ID() {
		return INITIATOR_ID;
	}

	public void setINITIATOR_ID(String initiator_id) {
		INITIATOR_ID = initiator_id;
	}



	public User getInitiator() {
		return initiator;
	}

	public void setInitiator(User initiator) {
		this.initiator = initiator;
	}

	public String getHANDLER_ID() {
		return HANDLER_ID;
	}

	public void setHANDLER_ID(String handler_id) {
		HANDLER_ID = handler_id;
	}

	public User getHandler() {
		return handler;
	}

	public void setHandler(User handler) {
		this.handler = handler;
	}

	public String getPRE_HANDLER_ID() {
		return PRE_HANDLER_ID;
	}

	public void setPRE_HANDLER_ID(String pre_handler_id) {
		PRE_HANDLER_ID = pre_handler_id;
	}

	public User getPreHandler() {
		return preHandler;
	}

	public void setPreHandler(User preHandler) {
		this.preHandler = preHandler;
	}

	public String getTS_TARGETDATE() {
		return TS_TARGETDATE;
	}

	public void setTS_TARGETDATE(String TS_TARGETDATE) {
		this.TS_TARGETDATE = TS_TARGETDATE;
	}

	public String getTS_FACTDATE() {
		return TS_FACTDATE;
	}

	public void setTS_FACTDATE(String ts_factdate) {
		TS_FACTDATE = ts_factdate;
	}

	public String getTS_CREATEDATE() {
		return TS_CREATEDATE;
	}

	public void setTS_CREATEDATE(String ts_createdate) {
		TS_CREATEDATE = ts_createdate;
	}

	public String getTS_STATE() {
		return TS_STATE;
	}

	public void setTS_STATE(String ts_state) {
		TS_STATE = ts_state;
	}

	public String getIS_HURRY() {
		return IS_HURRY;
	}

	public void setIS_HURRY(String is_hurry) {
		IS_HURRY = is_hurry;
	}

	public List<Log> getLogs() {
		return logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}


}
