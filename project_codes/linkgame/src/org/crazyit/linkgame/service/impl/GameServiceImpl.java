package org.crazyit.linkgame.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Random;
import javax.swing.JLabel;

import org.crazyit.linkgame.commons.GameConfiguration;
import org.crazyit.linkgame.commons.LinkInfo;
import org.crazyit.linkgame.commons.Piece;
import org.crazyit.linkgame.commons.Point;
import org.crazyit.linkgame.service.AbstractBoard;
import org.crazyit.linkgame.service.GameService;


/**
 * 游戏逻辑处理类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class GameServiceImpl implements GameService {
	// 定义一个棋盘数组，只提供getter方法
	private Piece[][] pieces;

	private GameConfiguration config;

	private AbstractBoard board;

	// 加入分数属性,初始值为0
	private long grade = 0;

	public GameServiceImpl(GameConfiguration config) {
		// 将游戏的配置对象设置本类中
		this.config = config;
	}

	public void start() {
		this.grade = 0;
		// 创建一个棋盘对象
		AbstractBoard board = createBoard();
		// 为本对象设置board
		this.board = board;
		// 获取棋盘数组
		this.pieces = board.create(config);
	}

	// 实现接口的hasPieces方法
	public boolean hasPieces(Piece[][] pieces) {
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				if (pieces[i][j] != null) {
					return true;
				}
			}
		}
		return false;
	}

	public Piece findPiece(int mouseX, int mouseY) {
		// 由于是在本类的(棋盘)board中找Piece对象, 如果board为空, 即棋盘中没有棋子
		if (this.board == null) {
			return null;
		}
		// 由于我们在创建Piece对象的时候, 将每个Piece的开始坐标加了
		// GameConfiguration中设置的beginImageX/beginImageY值, 因此这里要减去这个值
		int relativeX = mouseX - this.config.getBeginImageX();
		int relativeY = mouseY - this.config.getBeginImageY();
		// 如果鼠标点击的地方比棋盘中第一张图片的开始x坐标和开始y坐标要小, 即没有找到棋子
		if (relativeX < 0 || relativeY < 0) {
			return null;
		}
		// 获取relativeX坐标在棋盘数组中的一维值, 第二个参数为每张图片的宽
		int indexX = getIndex(relativeX, this.board.getCommonImageWidth());
		// 获取relativeY坐标在棋盘数组中的二维值, 第二个参数为每张图片的高
		int indexY = getIndex(relativeY, this.board.getCommonImageHeight());
		// 这两个索引比数组的最小索引还小, 返回null
		if (indexX < 0 || indexY < 0) {
			return null;
		}
		// 这两个索引比数组的最大索引还大(或者等于), 返回null
		if (indexX >= this.config.getXSize()
				|| indexY >= this.config.getYSize()) {
			return null;
		}
		// 返回本对象中棋盘数组的某个值
		return this.pieces[indexX][indexY];
	}

	// 实现接口的link方法
	public LinkInfo link(Piece p1, Piece p2) {
		// 两个Piece是同一个, 即在棋盘中选择了同一个Piece, 返回null
		if (p1.equals(p2))
			return null;
		// 如果p1的图片与p2的图片不相同, 则返回null
		if (!p1.isSameImage(p2))
			return null;
		// 如果p2在p1的左边, 则需要重新执行本方法, 两个参数互换
		if (p2.getIndexX() < p1.getIndexX())
			return link(p2, p1);
		// 获取p1的中心点
		Point p1Point = getPieceCenter(p1);
		// 获取p2的中心点
		Point p2Point = getPieceCenter(p2);
		// 获取每张图片的宽和高
		int pieceWidth = this.board.getCommonImageWidth();
		int pieceHeight = this.board.getCommonImageHeight();
		// 如果两个Piece在同一行
		if (p1.getIndexY() == p2.getIndexY()) {
			// 它们在同一行并之间可以连
			if (!isXBlock(p1Point, p2Point, pieceWidth)) {
				return new LinkInfo(p1Point, p2Point);
			}
		}
		// 如果两个Piece在同一列
		if (p1Point.getX() == p2Point.getX()) {
			if (!isYBlock(p1Point, p2Point, pieceHeight)) {// 它们之间没有真接障碍, 没有转折点
				return new LinkInfo(p1Point, p2Point);
			}
		}
		// 有一个转折点的情况
		// 获取两个点的直角相连的点, 即只有一个转折点
		Point cornerPoint = getCornerPoint(p1Point, p2Point, pieceWidth,
				pieceHeight);
		if (cornerPoint != null) {
			return new LinkInfo(p1Point, cornerPoint, p2Point);
		}
		// 该map的key存放第一个转折点, value存放第二个转折点, map的size说明有多少个可以连的方式
		Map<Point, Point> turns = getLinkPoints(p1Point, p2Point, pieceWidth,
				pieceHeight);
		if (turns.size() != 0) {
			return getShortcut(p1Point, p2Point, turns, getDistance(p1Point,
					p2Point));
		}
		return null;
	}

	public long countGrade() {
		this.grade += this.config.getPerGrade();
		return this.grade;
	}

	/**
	 * 获取p1和p2之间最短的连接信息
	 * 
	 * @param p1
	 * @param p2
	 * @param turns
	 *            放转折点的map
	 * @param shortDistance
	 *            两点之间的最短距离
	 * @return
	 */
	private LinkInfo getShortcut(Point p1, Point p2, Map<Point, Point> turns,
			int shortDistance) {
		List<LinkInfo> infos = new ArrayList<LinkInfo>();
		// 遍历结果map, 将转折点与选择的点封装成LinkInfo对象, 放到集合中
		for (Object info : turns.keySet()) {
			Point point1 = (Point) info;
			Point point2 = turns.get(point1);
			infos.add(new LinkInfo(p1, point1, point2, p2));
		}
		return getShortcut(infos, shortDistance);
	}

	/**
	 * 在infos中获取其四个点最短的那个LinkInfo对象
	 * 
	 * @param infos
	 * @return
	 */
	private LinkInfo getShortcut(List<LinkInfo> infos, int shortDistance) {
		int temp1 = 0;
		LinkInfo result = null;
		for (int i = 0; i < infos.size(); i++) {
			LinkInfo info = infos.get(i);
			// 计算出几个点的总距离
			int distance = countAll(info.getLinkPoints());
			// 将循环第一个的差距用temp1保存
			if (i == 0) {
				temp1 = distance - shortDistance;
				result = info;
			}
			// 如果下一次循环的值比temp1的还小, 则用当前的值作为temp1
			if (distance - shortDistance < temp1) {
				temp1 = distance - shortDistance;
				result = info;
			}
		}
		return result;
	}

	/**
	 * 计算points中所有点的距离总和
	 * 
	 * @param points
	 * @return
	 */
	private int countAll(List<Point> points) {
		int result = 0;
		for (int i = 0; i < points.size(); i++) {
			if (i == points.size() - 1) {// 循环到最后一个
				break;
			}
			Point point1 = points.get(i);
			Point point2 = points.get(i + 1);
			result += getDistance(point1, point2);
		}
		return result;
	}

	/**
	 * 获取两个LinkPoint之间的最短距离
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	private int getDistance(Point p1, Point p2) {
		int xDistance = Math.abs(p1.getX() - p2.getX());
		int yDistance = Math.abs(p1.getY() - p2.getY());
		return xDistance + yDistance;
	}

	/**
	 * 获取两个转折点的情况
	 * 
	 * @param point1
	 * @param point2
	 * @return
	 */
	private Map<Point, Point> getLinkPoints(Point point1, Point point2,
			int pieceWidth, int pieceHeight) {
		Map<Point, Point> result = new HashMap<Point, Point>();
		// 获取以point1为中心的向上, 向右, 向下的通道
		List<Point> p1UpChanel = getUpChanel(point1, point2.getY(), pieceHeight);
		List<Point> p1RightChanel = getRightChanel(point1, point2.getX(),
				pieceWidth);
		List<Point> p1DownChanel = getDownChanel(point1, point2.getY(),
				pieceHeight);
		// 获取以point2为中心的向下, 向左, 向上的通道
		List<Point> p2DownChanel = getDownChanel(point2, point1.getY(),
				pieceHeight);
		List<Point> p2LeftChanel = getLeftChanel(point2, point1.getX(),
				pieceWidth);
		List<Point> p2UpChanel = getUpChanel(point2, point1.getY(), pieceHeight);
		// 获取棋盘的最大值, 高和宽
		int heightMax = (this.config.getYSize() + 1) * pieceHeight
				+ this.config.getBeginImageY();
		int widthMax = (this.config.getXSize() + 1) * pieceWidth
				+ this.config.getBeginImageX();
		// 先确定两个点的关系
		// point2在point1的左上角或者左下角
		if (isLeftUp(point1, point2) || isLeftDown(point1, point2)) {
			// 参数换位, 调用本方法
			return getLinkPoints(point2, point1, pieceWidth, pieceHeight);
		}
		if (isInLine(point1, point2)) {// 在同一行
			// 向上遍历
			// 以p1的中心点向上遍历获取点集合
			p1UpChanel = getUpChanel(point1, 0, pieceHeight);
			// 以p2的中心点向上遍历获取点集合
			p2UpChanel = getUpChanel(point2, 0, pieceHeight);
			Map<Point, Point> upLinkPoints = getXLinkPoints(p1UpChanel,
					p2UpChanel, pieceHeight);
			// 向下遍历, 不超过棋盘(有棋子的地方)的边框
			// 以p1中心点向下遍历获取点集合
			p1DownChanel = getDownChanel(point1, heightMax, pieceHeight);
			// 以p2中心点向下遍历获取点集合
			p2DownChanel = getDownChanel(point2, heightMax, pieceHeight);
			Map<Point, Point> downLinkPoints = getXLinkPoints(p1DownChanel,
					p2DownChanel, pieceHeight);
			result.putAll(upLinkPoints);
			result.putAll(downLinkPoints);
		}
		if (isInColumn(point1, point2)) {// 在同一列
			// 向左遍历
			// 以p1的中心点向左遍历获取点集合
			List<Point> p1LeftChanel = getLeftChanel(point1, 0, pieceWidth);
			// 以p2的中心点向左遍历获取点集合
			p2LeftChanel = getLeftChanel(point2, 0, pieceWidth);
			Map<Point, Point> leftLinkPoints = getYLinkPoints(p1LeftChanel,
					p2LeftChanel, pieceWidth);
			// 向右遍历, 不得超过棋盘的边框
			// 以p1的中心点向右遍历获取点集合
			p1RightChanel = getRightChanel(point1, widthMax, pieceWidth);
			// 以p2的中心点向右遍历获取点集合
			List<Point> p2RightChanel = getRightChanel(point2, widthMax,
					pieceWidth);
			Map<Point, Point> rightLinkPoints = getYLinkPoints(p1RightChanel,
					p2RightChanel, pieceWidth);
			result.putAll(leftLinkPoints);
			result.putAll(rightLinkPoints);
		}
		if (isRightUp(point1, point2)) {// point2在point1的右上角
			// 获取point1向上遍历, point2向下遍历时横向可以连接的点
			Map<Point, Point> upDownLinkPoints = getXLinkPoints(p1UpChanel,
					p2DownChanel, pieceWidth);

			// 获取point1向右遍历, point2向左遍历时纵向可以连接的点
			Map<Point, Point> rightLeftLinkPoints = getYLinkPoints(
					p1RightChanel, p2LeftChanel, pieceHeight);

			// 获取以p1为中心的向上通道
			p1UpChanel = getUpChanel(point1, 0, pieceHeight);
			// 获取以p2为中心的向上通道
			p2UpChanel = getUpChanel(point2, 0, pieceHeight);
			// 获取point1向上遍历, point2向上遍历时横向可以连接的点
			Map<Point, Point> upUpLinkPoints = getXLinkPoints(p1UpChanel,
					p2UpChanel, pieceWidth);

			// 获取以p1为中心的向下通道
			p1DownChanel = getDownChanel(point1, heightMax, pieceHeight);
			// 获取以p2为中心的向下通道
			p2DownChanel = getDownChanel(point2, heightMax, pieceHeight);
			// 获取point1向下遍历, point2向下遍历时横向可以连接的点
			Map<Point, Point> downDownLinkPoints = getXLinkPoints(p1DownChanel,
					p2DownChanel, pieceWidth);

			// 获取以p1为中心的向右通道
			p1RightChanel = getRightChanel(point1, widthMax, pieceWidth);
			// 获取以p2为中心的向右通道
			List<Point> p2RightChanel = getRightChanel(point2, widthMax,
					pieceWidth);
			// 获取point1向右遍历, point2向右遍历时纵向可以连接的点
			Map<Point, Point> rightRightLinkPoints = getYLinkPoints(
					p1RightChanel, p2RightChanel, pieceHeight);

			// 获取以p1为中心的向左通道
			List<Point> p1LeftChanel = getLeftChanel(point1, 0, pieceWidth);
			// 获取以p2为中心的向左通道
			p2LeftChanel = getLeftChanel(point2, 0, pieceWidth);
			// 获取point1向左遍历, point2向右遍历时纵向可以连接的点
			Map<Point, Point> leftLeftLinkPoints = getYLinkPoints(p1LeftChanel,
					p2LeftChanel, pieceHeight);
			result.putAll(upDownLinkPoints);
			result.putAll(rightLeftLinkPoints);
			result.putAll(upUpLinkPoints);
			result.putAll(downDownLinkPoints);
			result.putAll(rightRightLinkPoints);
			result.putAll(leftLeftLinkPoints);
		}
		if (isRightDown(point1, point2)) {// point2在point1的右下角
			// 获取point1向下遍历, point2向上遍历时横向可连接的点
			Map<Point, Point> downUpLinkPoints = getXLinkPoints(p1DownChanel,
					p2UpChanel, pieceWidth);
			// 获取point1向右遍历, point2向左遍历时纵向可连接的点
			Map<Point, Point> rightLeftLinkPoints = getYLinkPoints(
					p1RightChanel, p2LeftChanel, pieceHeight);

			// 获取以p1为中心的向上通道
			p1UpChanel = getUpChanel(point1, 0, pieceHeight);
			// 获取以p2为中心的向上通道
			p2UpChanel = getUpChanel(point2, 0, pieceHeight);
			// 获取point1向上遍历, point2向上遍历时横向可连接的点
			Map<Point, Point> upUpLinkPoints = getXLinkPoints(p1UpChanel,
					p2UpChanel, pieceWidth);

			// 获取以p1为中心的向下通道
			p1DownChanel = getDownChanel(point1, heightMax, pieceHeight);
			// 获取以p2为中心的向下通道
			p2DownChanel = getDownChanel(point2, heightMax, pieceHeight);
			// 获取point1向下遍历, point2向下遍历时横向可连接的点
			Map<Point, Point> downDownLinkPoints = getXLinkPoints(p1DownChanel,
					p2DownChanel, pieceWidth);

			// 获取以p1为中心的向左通道
			List<Point> p1LeftChanel = getLeftChanel(point1, 0, pieceWidth);
			// 获取以p2为中心的向左通道
			p2LeftChanel = getLeftChanel(point2, 0, pieceWidth);
			// 获取point1向左遍历, point2向左遍历时纵向可连接的点
			Map<Point, Point> leftLeftLinkPoints = getYLinkPoints(p1LeftChanel,
					p2LeftChanel, pieceHeight);

			// 获取以p1为中心的向右通道
			p1RightChanel = getRightChanel(point1, widthMax, pieceWidth);
			// 获取以p2为中心的向右通道
			List<Point> p2RightChanel = getRightChanel(point2, widthMax,
					pieceWidth);
			// 获取point1向右遍历, point2向右遍历时纵向可以连接的点
			Map<Point, Point> rightRightLinkPoints = getYLinkPoints(
					p1RightChanel, p2RightChanel, pieceHeight);

			result.putAll(downUpLinkPoints);
			result.putAll(rightLeftLinkPoints);// 将可以连接的所有点都放到结果中
			result.putAll(upUpLinkPoints);
			result.putAll(downDownLinkPoints);
			result.putAll(leftLeftLinkPoints);
			result.putAll(rightRightLinkPoints);
		}
		return result;
	}

	/**
	 * 遍历两个集合, 判断它们其中元素的x座标与另外的集合中的元素x座标相同(纵向), 如果相同, 即在同一行, 再判断是否有障碍, 没有,
	 * 则加到结果的map中去
	 * 
	 * @param p1Chanel
	 * @param p2Chanel
	 * @param pieceHeight
	 * @return
	 */
	private Map<Point, Point> getYLinkPoints(List<Point> p1Chanel,
			List<Point> p2Chanel, int pieceHeight) {
		Map<Point, Point> result = new HashMap<Point, Point>();
		for (int i = 0; i < p1Chanel.size(); i++) {
			Point temp1 = p1Chanel.get(i);
			for (int j = 0; j < p2Chanel.size(); j++) {
				Point temp2 = p2Chanel.get(j);
				// 如果x座标相同(在同一列)
				if (temp1.getX() == temp2.getX()) {
					// 没有障碍, 放到map中去
					if (!isYBlock(temp1, temp2, pieceHeight)) {
						result.put(temp1, temp2);
					}
				}
			}
		}
		return result;
	}

	/**
	 * 判断两个点是否在同一列(x座标相同)
	 * 
	 * @param point1
	 * @param point2
	 * @return true point1和point2在同一列(x座标相同) false point1和point2不在同一列
	 */
	private boolean isInColumn(Point point1, Point point2) {
		return point1.getX() == point2.getX();
	}

	/**
	 * 判断两个点是否在同一行(y座标相同)
	 * 
	 * @param point1
	 * @param point2
	 * @return true point1和point2在同一行(y座标相同) false point1和point2不在同一行
	 */
	private boolean isInLine(Point point1, Point point2) {
		return point1.getY() == point2.getY();
	}

	/**
	 * 遍历两个集合, 判断它们其中元素的y座标与另外的集合中的元素y座标相同(横向), 如果相同, 即在同一行, 再判断是否有障碍, 没有,
	 * 则加到结果的map中去
	 * 
	 * @param p1Chanel
	 * @param p2Chanel
	 * @param pieceWidth
	 * @return 存放可以横向直线连接的连接点的键值对
	 */
	private Map<Point, Point> getXLinkPoints(List<Point> p1Chanel,
			List<Point> p2Chanel, int pieceWidth) {
		Map<Point, Point> result = new HashMap<Point, Point>();
		for (int i = 0; i < p1Chanel.size(); i++) {
			// 从第一通道中取一个点
			Point temp1 = p1Chanel.get(i);
			// 再遍历第二个通道, 看下第二通道中是否有点可以与temp1横向相连
			for (int j = 0; j < p2Chanel.size(); j++) {
				Point temp2 = p2Chanel.get(j);
				// 如果y座标相同(在同一行), 再判断它们之间是否有直接障碍
				if (temp1.getY() == temp2.getY()) {
					if (!isXBlock(temp1, temp2, pieceWidth)) {
						// 没有障碍则直接加到结果的map中
						result.put(temp1, temp2);
					}
				}
			}
		}
		return result;
	}

	/**
	 * 判断point2是否在point1的左上角
	 * 
	 * @param point1
	 * @param point2
	 * @return true point2在point1的左上角 false point2不在point1的左上角
	 */
	private boolean isLeftUp(Point point1, Point point2) {
		return (point2.getX() < point1.getX() && point2.getY() < point1.getY());
	}

	/**
	 * 判断point2是否在point1的左下角
	 * 
	 * @param point1
	 * @param point2
	 * @return true point2在point1的左下角 false point2不在point1的左下角
	 */
	private boolean isLeftDown(Point point1, Point point2) {
		return (point2.getX() < point1.getX() && point2.getY() > point1.getY());
	}

	/**
	 * 判断point2是否在point1的右上角
	 * 
	 * @param point1
	 * @param point2
	 * @return true point2在point1的右上角 false point2不在point1的右上角
	 */
	private boolean isRightUp(Point point1, Point point2) {
		return (point2.getX() > point1.getX() && point2.getY() < point1.getY());
	}

	/**
	 * 判断point2是否在point1的右下角
	 * 
	 * @param point1
	 * @param point2
	 * @return true point2在point1的右下角 false point2不在point1的右下角
	 */
	private boolean isRightDown(Point point1, Point point2) {
		return (point2.getX() > point1.getX() && point2.getY() > point1.getY());
	}

	/**
	 * 获取两个不在同一行或者同一列的座标点的直角连接点, 即只有一个转折点
	 * 
	 * @param point1
	 * @param point2
	 * @return
	 */
	private Point getCornerPoint(Point point1, Point point2, int pieceWidth,
			int pieceHeight) {
		// 先判断这两个点的位置关系
		// point2在point1的左上角, point2在point1的左下角
		if (isLeftUp(point1, point2) || isLeftDown(point1, point2)) {
			// 参数换位, 重新调用本方法
			return getCornerPoint(point2, point1, pieceWidth, pieceHeight);
		}
		// 获取p1向右, 向上, 向下的三个通道
		List<Point> point1RightChanel = getRightChanel(point1, point2.getX(),
				pieceWidth);
		List<Point> point1UpChanel = getUpChanel(point1, point2.getY(),
				pieceHeight);
		List<Point> point1DownChanel = getDownChanel(point1, point2.getY(),
				pieceHeight);
		// 获取p2向下, 向左, 向下的三个通道
		List<Point> point2DownChanel = getDownChanel(point2, point1.getY(),
				pieceHeight);
		List<Point> point2LeftChanel = getLeftChanel(point2, point1.getX(),
				pieceWidth);
		List<Point> point2UpChanel = getUpChanel(point2, point1.getY(),
				pieceHeight);
		if (isRightUp(point1, point2)) {// point2在point1的右上角
			// 获取p1向右和p2向下的交点
			Point linkPoint1 = getWrapPoint(point1RightChanel, point2DownChanel);
			// 获取p1向上和p2向左的交点
			Point linkPoint2 = getWrapPoint(point1UpChanel, point2LeftChanel);
			// 返回其中一个交点, 如果没有交点, 则返回null
			return (linkPoint1 == null) ? linkPoint2 : linkPoint1;
		}
		if (isRightDown(point1, point2)) {// point2在point1的右下角
			// 获取p1向下和p2向左的交点
			Point linkPoint1 = getWrapPoint(point1DownChanel, point2LeftChanel);
			// 获取p1向右和p2向下的交点
			Point linkPoint2 = getWrapPoint(point1RightChanel, point2UpChanel);
			return (linkPoint1 == null) ? linkPoint2 : linkPoint1;
		}
		return null;
	}

	/**
	 * 遍历两个通道, 获取它们的交点
	 * 
	 * @param p1Chanel
	 * @param p2Chanel
	 * @param side
	 * @return
	 */
	private Point getWrapPoint(List<Point> p1Chanel, List<Point> p2Chanel) {
		for (int i = 0; i < p1Chanel.size(); i++) {
			Point temp1 = p1Chanel.get(i);
			for (int j = 0; j < p2Chanel.size(); j++) {
				Point temp2 = p2Chanel.get(j);
				if (temp1.equals(temp2)) {// 如果两个List中有元素有同一个, 表明这两个通道有交点
					return temp1;
				}
			}
		}
		return null;
	}

	/**
	 * 判断两个y座标相同的点对象之间是否有障碍, 以p1为中心向右遍历
	 * 
	 * @param p1
	 * @param p2
	 * @param pieceWidth
	 * @return true 它们之间有障碍 false 它们之间没有障碍
	 */
	private boolean isXBlock(Point p1, Point p2, int pieceWidth) {
		if (p2.getX() < p1.getX()) {// 如果p2在p1左边, 调换参数位置调用本方法
			return isXBlock(p2, p1, pieceWidth);
		}
		for (int i = p1.getX() + pieceWidth; i < p2.getX(); i = i + pieceWidth) {
			if (hasPiece(i, p1.getY())) {// 有障碍
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断两个x座标相同的点对象之间是否有障碍, 以p1为中心向下遍历
	 * 
	 * @param p1
	 * @param p2
	 * @param pieceHeight
	 * @return true 它们之间有障碍 false 它们之间没有障碍
	 */
	private boolean isYBlock(Point p1, Point p2, int pieceHeight) {
		if (p2.getY() < p1.getY()) {// 如果p2在p1的上面, 调换参数位置重新调用本方法
			return isYBlock(p2, p1, pieceHeight);
		}
		for (int i = p1.getY() + pieceHeight; i < p2.getY(); i = i
				+ pieceHeight) {
			if (hasPiece(p1.getX(), i)) {// 有障碍
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断GamePanel中的x, y座标中是否有Piece对象
	 * 
	 * @param x
	 * @param y
	 * @return true 表示有该座标有piece对象 false 表示没有
	 */
	private boolean hasPiece(int x, int y) {
		if (findPiece(x, y) == null)
			return false;
		return true;
	}

	/**
	 * 给一个Point对象,返回它的左边通道
	 * 
	 * @param p
	 * @param pieceWidth
	 *            piece图片的宽
	 * @param min
	 *            向左遍历时最小的界限
	 * @return
	 */
	private List<Point> getLeftChanel(Point p, int min, int pieceWidth) {
		List<Point> result = new ArrayList<Point>();
		// 获取向左通道, 由一个点向左遍历, 步长为Piece图片的宽
		for (int i = p.getX() - pieceWidth; i >= min; i = i - pieceWidth) {
			// 遇到障碍, 表示通道已经到尽头, 直接返回
			if (hasPiece(i, p.getY())) {
				return result;
			}
			result.add(new Point(i, p.getY()));
		}
		return result;
	}

	/**
	 * 给一个Point对象, 返回它的右边通道
	 * 
	 * @param p
	 * @param pieceWidth
	 * @param max
	 *            向右时的最右界限
	 * @return
	 */
	private List<Point> getRightChanel(Point p, int max, int pieceWidth) {
		List<Point> result = new ArrayList<Point>();
		// 获取向右通道, 由一个点向右遍历, 步长为Piece图片的宽
		for (int i = p.getX() + pieceWidth; i <= max; i = i + pieceWidth) {
			// 遇到障碍, 表示通道已经到尽头, 直接返回
			if (hasPiece(i, p.getY())) {
				return result;
			}
			result.add(new Point(i, p.getY()));
		}
		return result;
	}

	/**
	 * 给一个Point对象, 返回它的上面通道
	 * 
	 * @param p
	 * @param min
	 *            向上遍历时最小的界限
	 * @param pieceHeight
	 * @return
	 */
	private List<Point> getUpChanel(Point p, int min, int pieceHeight) {
		List<Point> result = new ArrayList<Point>();
		// 获取向上通道, 由一个点向右遍历, 步长为Piece图片的高
		for (int i = p.getY() - pieceHeight; i >= min; i = i - pieceHeight) {
			// 遇到障碍, 表示通道已经到尽头, 直接返回
			if (hasPiece(p.getX(), i)) {
				return result;// 如果一遇到障碍, 直接返回
			}
			result.add(new Point(p.getX(), i));
		}
		return result;
	}

	/**
	 * 给一个Point对象, 返回它的下面通道
	 * 
	 * @param p
	 * @param max
	 *            向上遍历时的最大界限
	 * @return
	 */
	private List<Point> getDownChanel(Point p, int max, int pieceHeight) {
		List<Point> result = new ArrayList<Point>();
		// 获取向下通道, 由一个点向右遍历, 步长为Piece图片的高
		for (int i = p.getY() + pieceHeight; i <= max; i = i + pieceHeight) {
			// 遇到障碍, 表示通道已经到尽头, 直接返回
			if (hasPiece(p.getX(), i)) {
				return result;// 如果一遇到障碍, 直接返回
			}
			result.add(new Point(p.getX(), i));
		}
		return result;
	}

	/**
	 * 获取一个Piece的中心座标点
	 * 
	 * @param piece
	 * @return
	 */
	private Point getPieceCenter(Piece piece) {
		return new Point((piece.getEndX() - piece.getBeginX()) / 2
				+ piece.getBeginX(), (piece.getEndY() - piece.getBeginY()) / 2
				+ piece.getBeginY());
	}

	// 工具方法, 根据relative坐标获取棋盘数组中一维或者二维的值, side为每张图片边的长或者宽
	private int getIndex(int relative, int side) {
		// 表示坐标relative不在该数组中
		int index = -1;
		// 让坐标除以边长, 没有余数, 索引减1
		// 例如点了x坐标为20, 边宽为10, 20 % 10 没有余数, index为1, 即在数组中的索引为1(第二个元素)
		if (relative % side == 0) {
			index = relative / side - 1;
		} else {
			// 有余数, 例如点了x坐标为21, 边宽为10, 21 % 10有余数, index为2
			// 即在数组中的索引为2(第三个元素)
			index = relative / side;
		}
		return index;
	}

	// 使用一个私有方法去创建棋盘对象
	private AbstractBoard createBoard() {
		Random random = new Random();
		// 获取一个随机数, 因为AbstractBoard只有两个子类, 因此这里为2
		int index = random.nextInt(2);
		switch (index) {
		// 0返回SquareBoard
		case 0:
			return new SquareBoard();
			// 1返回SimpleBoard
		case 1:
			return new SimpleBoard();
			// 默认返回SquareBoard
		default:
			return new SquareBoard();
		}
	}

	/*
	 * 直接返回本对象的piece数组
	 */
	public Piece[][] getPieces() {
		return this.pieces;
	}
}
