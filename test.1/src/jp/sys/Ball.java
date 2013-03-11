package jp.sys;

public class Ball implements Data{

	private int x, y, wx, wy, speedX, speedY;

	// コンストラクタ
	Ball() {

		x = 140;		// X座標設定
		y = 200;		// Y座標設定
		speedX = 5;		// X移動量設定
		speedY = 5;		// Y移動量設定

	}

	// x座標の値を返すメソッド
	public int getX() {

		return x;

	}

	// y座標の値を返すメソッド
	public int getY() {

		return y;

	}

	// ブロックに衝突した際、y軸の移動方向を逆にする
	public void setSigne() {

		speedY *= -1;

	}


	// 座標計算処理
	void xy_calc() {

		if(x > 300 - BallW) {

			speedX *= -1;

		}

		if(x < 0) {

			speedX *= -1;

		}

		if(y > 400 - BallH) {

			speedY *= -1;

		}

		if(y < 0) {

			speedY *= -1;

		}

		x = x + speedX;
		y = y + speedY;

	}

	// ボールとパドルが衝突した際の処理
	void BallVSPaddle(int px, int py) {

		// ボールの中心のx座標
		wx = x + (int)(BallW / 2);
		// ボールの中心のy座標
		wy = y + (int)(BallH / 2);

		if(px <= wx && wx <= px + PaddleW) {		// 範囲確認

			if(py <= wy && wy <= py + PaddleH) {	// 範囲確認

				y = y - 10;			//微調整のため
				speedY *= -1;

			}

		}

	}


}
