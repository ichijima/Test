package jp.sys;

public class Paddle implements Data{

	private int x, y, speedX;

	// コンストラクタ
	Paddle() {

		x = 120;		// X座標設定
		y = 350;		// Y座標設定
		speedX = 20;	// 移動量設定

	}

	// x座標の値を返すメソッド
	public int getX() {

		return x;

	}

	// y座標の値を返すメソッド
	public int getY() {

		return y;

	}

	// 右移動用座標計算メソッド
	void moveR() {

		x += speedX;
		if(x >= 300 - PaddleW) {

			x = 300 - PaddleW;

		}

	}

	// 左移動用座標計算メソッド
	void moveL() {

		x -= speedX;
		if(x <= 0) {

			x = 0;

		}

	}


}
