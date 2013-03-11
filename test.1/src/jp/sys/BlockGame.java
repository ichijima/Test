package jp.sys;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

interface Data {
	int PaddleW = 80, PaddleH = 20;	  // パドルデータ
	int BlockW = 56, BlockH = 20;	  // ブロックデータ
	int BallW  = 15, BallH  = 15;	  // ボールデータ
}

public class BlockGame extends Applet implements Runnable, KeyListener {

	// 変数宣言
	Thread t;
	Font wfont;
	Paddle paddle;
	Ball ball;
	Block block[];
	int gameFlg;		// ゲームフラグを設定


	// 画像をメモリに読み込む
	Image backIm;		// 背景画像
	Image paddleIm;		// パドル画像
	Image ballIm;		// ボール画像
	Image blockIm;		// ブロック画像
	private Image offImage;


	// 初期処理を行うメソッド
	public void init() {
		// 各オブジェクトの作成等を行う

		// ゲームフラグの初期化
		// 0:ゲームプレイ中, 1:ゲームクリア, 2:ゲームオーバー

		gameFlg = 0;



		// 背景画像を読み込みbackIm変数に代入
		backIm = getImage( getDocumentBase(), "../res/backmap.png");
		// パドル画像を読み込みpaddleIm変数に代入
		paddleIm = getImage( getDocumentBase(), "../res/padle.png");
		// ボール画像を読み込みballIm変数に代入
		ballIm = getImage( getDocumentBase(), "../res/ball.png");
		// ブロック画像を blockIm読み込み変数に代入
		blockIm = getImage( getDocumentBase(), "../res/block.png");

		// スレッド作成
		t = new Thread( this, "Game");
		// フォントを設定
		wfont = new Font("MS Pゴシック", Font.PLAIN|Font.BOLD, 36);
		// パドルオブジェクトの作成
		paddle = new Paddle();
		// ボールオブジェクトの作成
		ball = new Ball();
		// ブロックオブジェクトの作成
		block = new Block[15];
		// ブロック10個それぞれのx座標
		int x[] = { 0, 60, 120, 180, 240,
					0, 60, 120, 180, 240, };
		// ブロック10個それぞれのy座標
		int y[] = { 20, 20, 20, 20, 20,
					50, 50, 50, 50, 50,};
		// ブロックオブジェクト10個を作成
		for(int i = 0; i < 10; i++) {

			block[i] = new Block( x[i], y[i] );

		}

		addKeyListener(this);

		offImage = createImage(300, 400);

	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

		int c;

		c = e.getKeyCode();
		if(c == e.VK_ENTER) {

			t.start();		// スレッド開始

		}

		if(c == e.VK_LEFT) {

			paddle.moveL();	// x座標計算

		}

		if(c == e.VK_RIGHT) {

			paddle.moveR();	// x座標計算

		}


	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}


	public void paint( Graphics OnScreen ) {
		// アプレットに描画を行う

		Graphics gv = offImage.getGraphics();
		gv.clearRect(0, 0, 300, 400);


		// 背景画像を描画する
		gv.drawImage( backIm, 0 , 0, this);
		// パドル画像を描画する
		gv.drawImage( paddleIm, paddle.getX(), paddle.getY(), this);
		// ボール画像を描画する
		gv.drawImage(ballIm, ball.getX(), ball.getY(), this);
		// ブロック画像を描画する
		for(int i = 0; i < 10; i++) {

			if(block[i].getSW() == 1) {

				gv.drawImage(blockIm, block[i].getX(), block[i].getY(), this);

			}

		}


		gv.setFont(wfont);
		gameMsg(gameFlg, gv);
		repaint();
		OnScreen.drawImage(offImage, 0, 0, this);


	}


	public void update( Graphics OnScreen) {

		paint( OnScreen );		// paintメッソドを呼び出しを行う

	}


	public void run() {
		// 座標計算、あたり判定、ゲーム処理メソッドの呼び出しを行う

		try {

			while(true){
				//ここに1回分の処理に必要なメソッドを記述(描画処理はpaintメソッドで実行)

				if( gameClearChk() == true) {		// ゲームクリア確認

					gameFlg = 1;	// クリア状態にgameFlgを変更させる
					break;

				}

				if( ball.getY() > 380 ) {			// ゲームオーバー確認

					gameFlg = 2;	// ゲームオーバー状態にgameFlgを変更させる
					break;

				}

				ball.xy_calc();
				ball.BallVSPaddle(paddle.getX(), paddle.getY());


				for(int i = 0; i < 10; i++) {

					if(block[i].getSW() == 1) {

						if(block[i].BlockVSBall( ball.getX(), ball.getY() ) == true) {

							ball.setSigne();
							break;

						}

					}

				}


				repaint();
				t.sleep(30);

			}

		} catch(InterruptedException e) {

		}


	}


	public boolean gameClearChk() {

		for(int i = 0; i < 10; i++) {

			if( block[i].getSW() == 1) {

				return false;		// まだブロック有り

			}

		}

		return true;				// ブロック無し

	}


	public void gameMsg(int w, Graphics gv) {

		switch(gameFlg){

		case 1:

			// ゲームクリア文字描画
			gv.setColor(Color.YELLOW);
			gv.drawString(" Game Clear ", 40, 150);
			break;

		case 2:

			// ゲームオーバー文字描画
			gv.setColor(Color.PINK);
			gv.drawString(" Game Over ", 40, 150);
			break;

		}

	}


}

