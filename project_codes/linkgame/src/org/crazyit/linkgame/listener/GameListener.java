package org.crazyit.linkgame.listener;

import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import javax.swing.JLabel;

import org.crazyit.linkgame.commons.LinkInfo;
import org.crazyit.linkgame.commons.Piece;
import org.crazyit.linkgame.service.GameService;
import org.crazyit.linkgame.utils.ImageUtil;
import org.crazyit.linkgame.view.GamePanel;

/**
 * 游戏区域(GamePanel)监听器
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class GameListener extends MouseInputAdapter {
	// 处理游戏的逻辑接口
	private GameService gameService;

	// 代表已经被选的Piece集合
	private List<Piece> selects;

	// 这个监听器所监听并需要处理的对象
	private GamePanel gamePanel;

	private JLabel gradeLabel;

	// 设置任务对象
	private BeginListener beginListener;

	public GameListener(GameService gameService, GamePanel gamePanel,
			JLabel gradeLabel, BeginListener beginListener) {
		// 在本类构造时就为这两个对象设值
		this.gameService = gameService;
		this.gamePanel = gamePanel;
		// 本类构造时初始化选择的Piece集合
		this.selects = new ArrayList<Piece>();
		this.gradeLabel = gradeLabel;
		this.beginListener = beginListener;
	}

	// 重写MouseInputAdapter类的mousePressed方法, 表示鼠标按下时的动作
	public void mousePressed(MouseEvent event) {
		if (gamePanel.getOverImage() != null) {
			// 游戏已经胜利
			return;
		}
		// 获取GameServiceImpl中的棋盘数组
		Piece[][] pieces = gameService.getPieces();

		// 获取鼠标的x坐标
		int mouseX = event.getX();
		// 获取鼠标的y坐标
		int mouseY = event.getY();
		// 获取当前选择的Piece对象
		Piece currentPiece = gameService.findPiece(mouseX, mouseY);
		// 如果没有选中任何Piece对象(即鼠标点击的地方没有棋子), 不再往下执行
		if (currentPiece == null)
			return;
		// 让GamePanel对象设置选择的棋盘对象
		gamePanel.setSelectPiece(currentPiece);
		// 表示之前没有选中任何一个Piece
		if (this.selects.size() == 0) {
			// 将当前的Piece对象放到这个集合中, 重新将GamePanel绘制, 并不再往下执行
			this.selects.add(currentPiece);
			gamePanel.repaint();
			return;
		}
		// 表示之前已经选择了一个
		if (this.selects.size() == 1) {
			// 可以将之前选择的一个Piece对象拿回
			Piece prePiece = this.selects.get(0);
			// 在这里就要对currentPiece和prePiece进行判断并进行连接
			LinkInfo linkInfo = this.gameService.link(prePiece, currentPiece);
			// 两个Piece不可连,
			if (linkInfo == null) {
				// 如果不成功连接, 就要将之前选择的一个Piece从集合中删除, 再把本次选择的Piece加到集合中
				this.selects.remove(0);
				this.selects.add(currentPiece);
			} else {
				// 它们可以相连, 让GamePanel处理LinkInfo
				gamePanel.setLinkInfo(linkInfo);
				// 去掉选择图标
				gamePanel.setSelectPiece(null);
				// 设置分数
				long grade = gameService.countGrade();
				gradeLabel.setText(String.valueOf(grade));
				// 将两个Piece对象从数组中删除
				pieces[prePiece.getIndexX()][prePiece.getIndexY()] = null;
				pieces[currentPiece.getIndexX()][currentPiece.getIndexY()] = null;
				this.selects.remove(0);// 清空集合
				// 判断棋盘中是否还有棋子, 如果没有, 游戏胜利
				if (!gameService.hasPieces(pieces)) {
					// 将游戏设为胜利, 并显示图片
					gamePanel
							.setOverImage(ImageUtil.getImage("images/win.gif"));
					// 取消任务
					this.beginListener.getTimer().cancel();
				}
			}
		}

		gamePanel.repaint();
	}

	public void mouseReleased(MouseEvent event) {
		gamePanel.repaint();
	}
}
