package jp.sys;

public class Block implements Data{

	private int x, y, sw;


	//コンストラクタ
	Block(int a, int b) {

		x = a;		// X座標設定
		y = b;		// Y座標設定
		sw = 1;		// sw設定
	}

	// ブロックのx座標を返すメソッド
	public int getX() {

		return x;

	}

	// ブロックのy座標を返すメソッド
	public int getY() {

		return y;

	}

	// ブロックの状態フラグを返すメソッド
	public int getSW() {

		return sw;

	}

	// ブロックの状態フラグを設定するメソッド
	public void setSW() {

		sw = 0;

	}

	// ブロックとボールが衝突した際の処理
	boolean BlockVSBall(int bx, int by) {

		int wx = bx + (int)(BallW / 2);
		int wy = by + (int)(BallH / 2);

		if(x <= wx && wx <= x + BlockW) {

			if(y <= wy && wy <= y + BlockH) {

				sw = 0;				// 当たった為swを0に変更する
				return true;		// あたり(true)を返す

			}

		}

		return false;				// はずれ(false)を返す

	}


}
