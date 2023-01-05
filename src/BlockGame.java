import java.awt.*;
import java.awt.Event.*;
import javax.swing.*;

public class BlockGame {

	// JFrame을 상속받는 클래스를 생성
	static class MyFrame extends JFrame {

		// constant(상수지정)
		static int BALL_WIDTH = 20;
		static int BALL_HEIGHT = 20;
		static int BLOCK_ROWS = 5;
		static int BLOCK_COLUMNS = 10;
		static int BLOCK_WIDTH = 40;
		static int BLOCK_HEIGHT = 20;
		static int BLOCK_GAP = 3;
		static int BAR_WIDTH = 80;
		static int BAR_HEIGHT = 20;
		static int CANVAS_WIDTH = 400 + (BLOCK_GAP * BLOCK_COLUMNS) - BLOCK_GAP;
		static int CANVAS_HEIGHT = 600;

		// variable(변수지정)
		static MyPanel myPanel = null;
		static int score = 0;
		static Timer time = null;
		static Block[][] blocks = new Block[BLOCK_ROWS][BLOCK_COLUMNS]; // 세로,가로
		static Bar bar = new Bar();
		static Ball ball = new Ball();
		static int barXTarget = bar.x; // Target Value - interpolation
		static int dir = 0; // 0: Up-Right / 1: Down-Right / 2 : Up-left 3: Down-Left
		static int ballSpeed = 5;

		// 초기화
		static class Ball {
			int x = CANVAS_WIDTH / 2 - BALL_WIDTH / 2;
			int y = CANVAS_HEIGHT / 2 - BALL_HEIGHT / 2;
			int width = BALL_WIDTH;
			int height = BALL_HEIGHT;
		}

		static class Bar {
			int x = CANVAS_WIDTH / 2 - BAR_WIDTH / 2;
			int y = CANVAS_HEIGHT - 100;
			int width = BAR_WIDTH;
			int height = BAR_HEIGHT;
		}

		static class Block {
			int x = 0;
			int y = 0;
			int width = BLOCK_WIDTH;
			int height = BLOCK_HEIGHT;
			int color = 0; // 0: white, 1: yellow, 2:blue, 3: mazanta, 4:red
			boolean isHidden = false; // 충돌후에 블록이 화면에서 사라지는
		}

		static class MyPanel extends JPanel {
			// 그리기
		}

		public MyFrame(String title) {
			super(title);
			this.setVisible(true);
			this.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
			this.setLocation(400, 300);
			this.setLayout(new BorderLayout());
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 윈도우창이 닫히는 속성

			
			initData();
			
			myPanel = new MyPanel();
			this.add("Center", myPanel);
			
			setKeyListener();
			startTimer();
		}

		public void initData() {
			
			for(int i = 0; i < BLOCK_ROWS; i++) {
				for(int j = 0; j<BLOCK_COLUMNS; j++) {
					blocks[i][j] = new Block();
					blocks[i][j].x = BLOCK_WIDTH*j + BLOCK_GAP*j;
					blocks[i][j].y = 100 + i* BLOCK_HEIGHT + BLOCK_GAP*i;
					blocks[i][j].height = BLOCK_HEIGHT;
					blocks[i][j].color = 4 - i; // 0: white, 1: yellow, 2:blue, 3: mazanta, 4:red
					blocks[i][j].isHidden = false;
					
				}
			}
		}

		public void setKeyListener() {

		}
		
		public void startTimer() {

		}

	}

	public static void main(String[] args) {

		new MyFrame("Block Game"); // 타이틀

	}

}
