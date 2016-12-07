package org.crazyit.tetris.object;

public interface PieceCreator {

	/**
	 * 在x和y座标中创建一个Square对象
	 * @return
	 */
	Piece createPiece(int x, int y);
	
	/**
	 * 返回一个Square对象
	 * @return
	 */
	Piece getPiece();
	
}
