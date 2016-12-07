package org.crazyit.image.tool;

import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Color;

/**
 * 所有工具的接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @author Kelvin Mak kelvin.mak125@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface Tool {
	// 工具类型
	public static final String ARROW_TOOL = "ArrowTool";
	public static final String PENCIL_TOOL = "PencilTool";
	public static final String BRUSH_TOOL = "BrushTool";
	public static final String CUT_TOOL = "CutTool";
	public static final String ERASER_TOOL = "EraserTool";
	public static final String LINE_TOOL = "LineTool";
	public static final String RECT_TOOL = "RectTool";
	public static final String POLYGON_TOOL = "PolygonTool";
	public static final String ROUND_TOOL = "RoundTool";
	public static final String ROUNDRECT_TOOL = "RoundRectTool";
	public static final String ATOMIZER_TOOL = "AtomizerTool";
	public static final String COLORPICKED_TOOL = "ColorPickedTool";

	/**
	 * 拖动鼠标
	 * 
	 * @param e
	 *            MouseEvent
	 * @param jg
	 *            Graphics
	 * @return void
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * 移动鼠标
	 * 
	 * @param e
	 *            MouseEvent
	 * @return void
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * 松开鼠标
	 * 
	 * @param e
	 *            MouseEvent
	 * @return void
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * 按下鼠标
	 * 
	 * @param e
	 *            MouseEvent
	 * @return void
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * 点击
	 * 
	 * @param e
	 *            MouseEvent
	 * @return void
	 */
	public void mouseClicked(MouseEvent e);
}