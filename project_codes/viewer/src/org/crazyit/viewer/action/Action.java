package org.crazyit.viewer.action;

import org.crazyit.viewer.ViewerFrame;
import org.crazyit.viewer.ViewerService;

/**
 * 图片浏览器的Action接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @author Kelvin Mak kelvin.mak125@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface Action {
	/**
	 * 具体执行的方法
	 * @param service 图片浏览器的业务处理类
	 * @param frame 主界面对象
	 */
	void execute(ViewerService service, ViewerFrame frame);
}
