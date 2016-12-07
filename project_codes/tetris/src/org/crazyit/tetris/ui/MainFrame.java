package org.crazyit.tetris.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.crazyit.tetris.object.Piece;
import org.crazyit.tetris.object.PieceCreator;
import org.crazyit.tetris.object.Square;
import org.crazyit.tetris.object.impl.PieceCreatorImpl;
import org.crazyit.tetris.util.ImageUtil;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	//游戏的Panel
	private GamePanel gamePanel;
	

	//级别
	private JLabel levelTextLabel = new JLabel("级     别");
	private JLabel levelLabel = new JLabel();
	private Box levelTextBox = Box.createHorizontalBox();
	private Box levelBox = Box.createHorizontalBox();
	
	//分数
	private Box scoreTextBox = Box.createHorizontalBox();
	private Box scoreBox = Box.createHorizontalBox();
	private JLabel scoreTextLabel = new JLabel("分     数");
	private JLabel scoreLabel = new JLabel();
	
	//下一个
	private Box nextTextBox = Box.createHorizontalBox();
	private JLabel nextTextLabel = new JLabel("下一个");
	
	//继续
	private Box resumeBox = Box.createHorizontalBox();
	private JLabel resumeLabel = new JLabel();
	//暂停
	private Box pauseBox = Box.createHorizontalBox();
	private JLabel pauseLabel = new JLabel();
	//开始
	private Box startBox = Box.createHorizontalBox();
	private JLabel startLabel = new JLabel();
	
	private JPanel toolPanel = new JPanel();
	private Box blankBox = Box.createHorizontalBox();
	
	private PieceCreator creator = new PieceCreatorImpl();
	
	//当前正在运动的对象
	private Piece currentPiece;
	//下一个大方块对象
	private Piece nextPiece;
	
	TetrisTask tetrisTask;
	
	private Timer timer;
	//当前游戏级别
	private int currentLevel;
	
	private Square[][] squares;
	
	//分数
	private int score = 0;
	
	//暂停的标识, true为暂停
	private boolean pauseFlag = false;

	public MainFrame() {
		this.currentLevel = 1;

		this.gamePanel = new GamePanel(this);
		
		BoxLayout toolPanelLayout = new BoxLayout(this.toolPanel, BoxLayout.Y_AXIS); 
		this.toolPanel.setLayout(toolPanelLayout);
		this.toolPanel.setBorder(new EtchedBorder());
		this.toolPanel.setBackground(Color.gray);
		//分数
		this.scoreTextBox.add(this.scoreTextLabel);
		this.scoreLabel.setText(String.valueOf(this.score));
		this.scoreBox.add(this.scoreLabel);
		//级别
		this.levelTextBox.add(this.levelTextLabel);
		this.levelLabel.setText(String.valueOf(this.currentLevel));
		this.levelBox.add(this.levelLabel);
		//继续按钮
		this.resumeLabel.setIcon(RESUME_ICON);
		this.resumeLabel.setPreferredSize(new Dimension(3, 25));
		this.resumeBox.add(this.resumeLabel);
		//暂停按钮
		this.pauseLabel.setIcon(PAUSE_ICON);
		this.pauseLabel.setPreferredSize(new Dimension(3, 25));
		this.pauseBox.add(this.pauseLabel);
		//开始
		this.startLabel.setIcon(START_ICON);
		this.startLabel.setPreferredSize(new Dimension(3, 25));
		this.startBox.add(this.startLabel);
		//下一个
		this.nextTextBox.add(this.nextTextLabel);

		this.toolPanel.add(Box.createVerticalStrut(10));
		this.toolPanel.add(scoreTextBox);
		this.toolPanel.add(Box.createVerticalStrut(10));
		this.toolPanel.add(scoreBox);
		this.toolPanel.add(Box.createVerticalStrut(10));
		this.toolPanel.add(levelTextBox);
		this.toolPanel.add(Box.createVerticalStrut(10));
		this.toolPanel.add(levelBox);
		this.toolPanel.add(Box.createVerticalStrut(15));
		this.toolPanel.add(this.resumeBox);
		this.toolPanel.add(Box.createVerticalStrut(15));
		this.toolPanel.add(this.pauseBox);
		this.toolPanel.add(Box.createVerticalStrut(15));
		this.toolPanel.add(this.startBox);
		this.toolPanel.add(Box.createVerticalStrut(30));
		this.toolPanel.add(this.nextTextBox);

		this.blankBox.add(Box.createHorizontalStrut(99));
		this.toolPanel.add(blankBox);
		
		this.add(this.gamePanel, BorderLayout.CENTER);
		this.add(this.toolPanel, BorderLayout.EAST);
		this.setPreferredSize(new Dimension(349, 416));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(350, 200);
		this.setResizable(false);
		this.setTitle("俄罗斯方块");
		this.pack();
		initListeners();
	}
	
	//返回当前运动的Piece对象
	public Piece getCurrentPiece() {
		return this.currentPiece;
	}
	
	//当鼠标经过暂停按钮时显示的图片
	private final static ImageIcon PAUSE_ON_ICON = new ImageIcon("images/button-bg-pause-on.gif");
	//暂停按钮图片
	private final static ImageIcon PAUSE_ICON = new ImageIcon("images/button-bg-pause.gif");
	//继续按钮图片
	private final static ImageIcon RESUME_ICON = new ImageIcon("images/button-bg-resume.gif");
	//鼠标经过时的按钮图片
	private final static ImageIcon RESUME_ON_ICON = new ImageIcon("images/button-bg-resume-on.gif");
	//开始按钮图片
	private final static ImageIcon START_ICON = new ImageIcon("images/button-bg-start.gif");
	//鼠标经过时的按钮图片
	private final static ImageIcon START_ON_ICON = new ImageIcon("images/button-bg-start-on.gif");
	
	private void initListeners() {
		this.resumeLabel.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				resumeLabel.setIcon(RESUME_ON_ICON);
			}
			public void mouseExited(MouseEvent e) {
				resumeLabel.setIcon(RESUME_ICON);
			}
			public void mouseClicked(MouseEvent e) {
				resume();
			}
		});
		this.pauseLabel.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				pauseLabel.setIcon(PAUSE_ON_ICON);
			}
			public void mouseExited(MouseEvent e) {
				pauseLabel.setIcon(PAUSE_ICON);
			}
			public void mouseClicked(MouseEvent e) {
				pause();
			}
		});
		this.startLabel.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				startLabel.setIcon(START_ON_ICON);
			}
			public void mouseExited(MouseEvent e) {
				startLabel.setIcon(START_ICON);
			}
			public void mouseClicked(MouseEvent e) {
				start();
			}
		});
		//添加键盘监听器
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				//上
				if (e.getKeyCode() == 38) change();
				//下
				if (e.getKeyCode() == 40) down();
				//左
				if (e.getKeyCode() == 37) left(1);
				//右
				if (e.getKeyCode() == 39) right(1);
			}
		});
	}
	
	//按键盘上时触发的方法
	public void change() {
		if (this.pauseFlag) return;
		if (this.currentPiece == null) return;
		this.currentPiece.change();
		//判断转换后左边是否越界
		//得到当前方块最小的X座标
		int minX = this.currentPiece.getMinXLocation();
		//左边越界
		if (minX < 0) {
			//右移超过的部分
			this.currentPiece.setSquaresXLocation(-minX);
		}
		//判断转换后右边是否越界
		int maxX = this.currentPiece.getMaxXLocation();
		int gamePanelWidth = this.gamePanel.getWidth();
		//右边越界
		if (maxX > gamePanelWidth) {
			//左移超过GamePanel宽的部分
			this.currentPiece.setSquaresXLocation(-(maxX - gamePanelWidth));
		}
		this.gamePanel.repaint();
	}

	
	//右, 参数为距离(一格)
	public void right(int size) {
		if (this.pauseFlag) return;
		if (this.currentPiece == null) return;
		//判断右边是否有Square
		if (isRightBolck()) return;
		//判断是否超过GamePanel的宽
		if (this.currentPiece.getMaxXLocation() >= this.gamePanel.getWidth()) return;
		int distance = Piece.SQUARE_BORDER * size;
		this.currentPiece.setSquaresXLocation(distance);
		this.gamePanel.repaint();
	}
	
	//左, 参数为距离(一格)
	public void left(int size) {
		if (this.pauseFlag) return;
		if (this.currentPiece == null) return;
		//判断左边是否有Square
		if (isLeftBlock()) return;
		//判断是否已经在最左边边界
		if (this.currentPiece.getMinXLocation() <= 0) return;
		//得出移动距离
		int distance = -(Piece.SQUARE_BORDER * size);
		this.currentPiece.setSquaresXLocation(distance);
		this.gamePanel.repaint();
	}
	
	//判断当前的Piece对象左边是否有障碍, 返回true表示有, 返回false表示没有
	private boolean isLeftBlock() {
		List<Square> squares = this.currentPiece.getSquares();
		for (int i = 0; i < squares.size(); i++) {
			Square s = squares.get(i);
			//查找界面数组中的Square对象
			Square block = getSquare(s.getBeginX() - Piece.SQUARE_BORDER, s.getBeginY());
			//block非空表示遇到障碍
			if (block != null) return true;
		}
		return false;
	}
	
	//判断当前的Piece对象右边是否有障碍, 返回true表示有, 返回false表示没有
	private boolean isRightBolck() {
		List<Square> squares = this.currentPiece.getSquares();
		for (int i = 0; i < squares.size(); i++) {
			Square s = squares.get(i);
			//查找界面数组中的Square对象
			Square block = getSquare(s.getBeginX() + Piece.SQUARE_BORDER, s.getBeginY());
			//block非空表示遇到障碍
			if (block != null) return true;
		}
		return false;
	}
	
	//下加速
	public void down() {
		if (this.pauseFlag) return;
		if (this.currentPiece == null) return;
		//判断快整下降后是否有障碍或者到底部
		if (isBlock() || isBottom()) return;
		int distance = Piece.SQUARE_BORDER;
		this.currentPiece.setSquaresYLocation(distance);
		//改变位置后再判断是否需要显示下一个
		showNext();
		this.gamePanel.repaint();
	}
	
	
	//创建下一个
	private void createNextPiece() {
		this.nextPiece = this.creator.createPiece(NEXT_X, NEXT_Y);
		this.repaint();
	}
	
	//下一个Piece的位置
	private final static int NEXT_X = 270;
	private final static int NEXT_Y = 320;
	//当前Piece的开始X座标
	private final static int BEGIN_X = Piece.SQUARE_BORDER * 6;
	//当前Piece的开始Y座标
	private final static int BEGIN_Y = -32;
	
	//开始游戏
	public void start() {
		//初始化界面二维数组
		initSquares();
		if (this.timer != null) this.timer.cancel();
		createNextPiece();
		this.currentPiece = creator.createPiece(BEGIN_X, BEGIN_Y);
		this.timer = new Timer();
		//初始化定时器
		this.tetrisTask = new TetrisTask(this);
		int time = 1000 / this.currentLevel;
		this.timer.schedule(this.tetrisTask, 0, time);
		this.pauseFlag = false;
		this.currentLevel = 1;
		this.score = 0;
		this.scoreLabel.setText(String.valueOf(this.score));
	}
	
	//暂停游戏
	public void pause() {
		this.pauseFlag = true;
		if (this.timer != null) this.timer.cancel();
		this.timer = null;
	}
	
	//继续游戏
	public void resume() {
		if (!this.pauseFlag) return;
		this.timer = new Timer();
		this.tetrisTask = new TetrisTask(this);
		int time = 1000 / this.currentLevel;
		timer.schedule(this.tetrisTask, 0, time);
		this.pauseFlag = false;
	}
	
	/************************************/
	//初始化界面二维数组
	private void initSquares() {
		//得到宽可以存放的方块个数
		int xSize = this.gamePanel.getWidth()/Piece.SQUARE_BORDER;
		//得到高可以存放的方块个数
		int ySize = this.gamePanel.getHeight()/Piece.SQUARE_BORDER;
		//构造界面的二维数组
		this.squares = new Square[xSize][ySize];
		for(int i = 0; i < this.squares.length; i++) {
			for (int j = 0; j < this.squares[i].length; j++) {
				this.squares[i][j] = new Square(Piece.SQUARE_BORDER * i, 
						Piece.SQUARE_BORDER * j);
			}
		}
	}
	
	public Square[][] getSquares() {
		return this.squares;
	}
	
	public GamePanel getGamePanel() {
		return this.gamePanel;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		if (this.nextPiece == null) return;
		ImageUtil.paintPiece(g, nextPiece);
	}

	
	//进行下一个
	public void showNext() {
		if (isBlock() || isBottom()) {
			//将当前的Piece中的所有Square加入加界面二维数组中
			appendToSquares();
			//判断是否失败
			if (isLost()) {
				this.repaint();
				this.timer.cancel();
				this.currentPiece = null;
				JOptionPane.showConfirmDialog(this, "游戏失败", "警告", 
						JOptionPane.OK_CANCEL_OPTION);
				return;
			}
			//消除行
			cleanRows();
			finishDown();
		}
	}
	
	//得到可以清理行集合
	private void cleanRows() {
		//使用一个集合保存被删除的行的索引
		List<Integer> rowIndexs = new ArrayList<Integer>();
		for (int j = 0; j < this.squares[0].length; j++) {
			int k = 0;
			for (int i = 0; i < this.squares.length; i++) {
				Square s = this.squares[i][j];
				//如果该格有图片, 则k+1
				if (s.getImage() != null) k++;
			}
			//如果整行都有图片, 则消除
			if (k == this.squares.length) {
				//再次对该行进行遍历, 设置该行所有格的图片为null
				for (int i = 0; i < this.squares.length; i++) {
					Square s = this.squares[i][j];
					s.setImage(null);
				}
				rowIndexs.add(j);
				addScore();
			}
		}
		//处理悬浮的Square
		handleDown(rowIndexs);
	}
	
	//加入分数
	private void addScore() {
		//加分
		this.score += 10;
		this.scoreLabel.setText(String.valueOf(score));
		//如果可以被100整除, 则加一级
		if ((this.score % 100) == 0) {
			this.currentLevel += 1;
			this.levelLabel.setText(String.valueOf(this.currentLevel));
			//重新设置定时器
			this.timer.cancel();
			this.timer = new Timer();
			this.tetrisTask = new TetrisTask(this);
			int time = 1000 / this.currentLevel;
			timer.schedule(this.tetrisTask, 0, time);
		}
	}
	
	//处理行消除后其他Square的"下降", 参数为被删除的行的索引集合
	private void handleDown(List<Integer> rowIndexs) {
		//从被删除的行中拿出索引最小的行
		if (rowIndexs.size() == 0) return;
		int minCleanRow = rowIndexs.get(0);
		int cleanRowSize = rowIndexs.size();
		//处理下降的Square
		for (int j = this.squares[0].length - 1; j >= 0; j--) {
			if (j < minCleanRow) {
				//遍历上面的行, 即悬浮的行
				for (int i = 0; i < this.squares.length; i++) {
					Square s = this.squares[i][j];
					if (s.getImage() != null) {
						//得到下降前的图片
						Image image = s.getImage();
						s.setImage(null);
						//得到下降后对应的Square对象, 数组的二维值要加上消除行的行数
						Square sdown = this.squares[i][j + cleanRowSize];
						sdown.setImage(image);
					}
				}
			}
		}
	}
	
	//判断是否到最底部
	public boolean isBottom() {
		return this.currentPiece.getMaxYLocation() >= this.gamePanel.getHeight();
	}
	
	//判断当前的Piece是否遇到障碍, 返回true表示遇到障碍, 返回false表示没有遇到
	public boolean isBlock() {
		List<Square> squares = this.currentPiece.getSquares();
		for (int i = 0; i < squares.size(); i++) {
			Square s = squares.get(i);
			//查找界面数组中的Square对象, 注意要将Y加上边距, 因为Y是开始座标
			//需要拿一个Square的最大Y座标
			Square block = getSquare(s.getBeginX(), s.getBeginY() + Piece.SQUARE_BORDER);
			//block非空表示遇到障碍
			if (block != null) return true;
		}
		return false;
	}
	
	//根据开始座标在当前界面数组中查找相应的Square
	private Square getSquare(int beginX, int beginY) {
		for (int i = 0; i < this.squares.length; i++) {
			for (int j = 0; j < this.squares[i].length; j++) {
				Square s = this.squares[i][j];
				//与参数的开始座标相同并且图片不为空
				if (s.getImage() != null && (s.getBeginX() == beginX) && 
						(s.getBeginY() == beginY)) {
					return s;
				}
			}
		}
		return null;
	}
	
	//判断是否失败, true为失败, false反之
	private boolean isLost() {
		for (int i = 0; i < this.squares.length; i++) {
			Square s = this.squares[i][0];
			if (s.getImage() != null) {
				return true;
			}
		}
		return false;
	}

	//一个Piece对象完成下降
	private void finishDown() {
		//将当前的Piece设置为下一个Piece
		this.currentPiece = this.nextPiece;
		//设置新的Piece座标
		this.currentPiece.setSquaresXLocation(-NEXT_X);
		this.currentPiece.setSquaresXLocation(BEGIN_X);
		this.currentPiece.setSquaresYLocation(-NEXT_Y);
		this.currentPiece.setSquaresYLocation(BEGIN_Y);
		//创建下一个Piece
		createNextPiece();
	}
	
	//将Piece中所有的Square都加入到二维数组中
	private void appendToSquares() {
		List<Square> squares = this.currentPiece.getSquares();
		//遍历Piece中的Square
		for(Square square : squares) {
			for(int i = 0; i < this.squares.length; i++) {
				for (int j = 0; j < this.squares[i].length; j++) {
					//得到当前界面中的Square
					Square s = this.squares[i][j];
					//判断Square是否一致
					if (s.equals(square)) this.squares[i][j] = square;
				}
			}
		}
		this.gamePanel.repaint();
	}
}

class TetrisTask extends TimerTask {
	//主界面对象
	private MainFrame mainFrame;
	public TetrisTask(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	public void run() {
		//得到当前正在运动的大方块
		Piece currentPiece = this.mainFrame.getCurrentPiece();
		//判断快整下降后是否有障碍或者到底部
		if (this.mainFrame.isBlock() || this.mainFrame.isBottom()) {
			this.mainFrame.showNext();
			return;
		}
		currentPiece.setSquaresYLocation(Piece.SQUARE_BORDER);
		this.mainFrame.getGamePanel().repaint();
	}
}
