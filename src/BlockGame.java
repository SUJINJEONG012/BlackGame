import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

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
		static Timer timer = null;
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
			int color = 0; // 0: white, 1: yellow, 2:blue, 3: mazenta, 4:red
			boolean isHidden = false; // 충돌후에 블록이 화면에서 사라지는
		}

		static class MyPanel extends JPanel {
			//생성자
			public MyPanel() {
				this.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
				this.setBackground(Color.BLACK);
			}
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				Graphics2D g2d = (Graphics2D)g;
				
				drawUI(g2d);
			}
			
			private void drawUI(Graphics2D g2d) {
				//draw Blocks
				
				for(int i = 0; i < BLOCK_ROWS; i++) {
					for(int j = 0; j<BLOCK_COLUMNS; j++) {
						if(blocks[i][j].isHidden) {
							continue;
						}
						if(blocks[i][j].color == 0) {
							g2d.setColor(Color.WHITE);
						}else if(blocks[i][j].color ==1) {
							g2d.setColor(Color.YELLOW);
						}else if(blocks[i][j].color == 2) {
							g2d.setColor(Color.BLUE);
						}else if(blocks[i][j].color ==3) {
							g2d.setColor(Color.MAGENTA);
						}else if(blocks[i][j].color ==4) {
							g2d.setColor(Color.RED);
						}
						g2d.fillRect(blocks[i][j].x ,blocks[i][j].y, blocks[i][j].width, blocks[i][j].height);
						
					}
					
					//draw score
					g2d.setColor(Color.WHITE);
					g2d.setFont(new Font("TimeRoman", Font.BOLD, 20));
					g2d.drawString("score : " + score, CANVAS_WIDTH/2 - 30, 20);
					
					//draw Ball
					g2d.setColor(Color.WHITE);
					g2d.fillOval(ball.x, ball.y, BALL_WIDTH, BALL_HEIGHT);
					
					//draw Bar
					g2d.setColor(Color.WHITE);
					g2d.fillRect(bar.x, bar.y, bar.width, bar.height);
				}
				
			}
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
			//JFrame에서 가지고 있는 함수를 추가 
		this.addKeyListener(new KeyAdapter() {
			 @Override
				public void keyPressed(KeyEvent e) { //key Event 
					if(e.getKeyCode()== KeyEvent.VK_LEFT) {
						System.out.println("Pressed Left Key");
						barXTarget -= 20;
						if(bar.x < barXTarget) { //예외처리, 계속 키보드를 눌렀을 경우
							barXTarget = bar.x; //현재 크기로 지정
 						}
					}else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
						System.out.println("Pressed Right Key");
					    barXTarget += 20;
					    if(bar.x > barXTarget) {
					    	barXTarget = bar.x;
					    }
					}
				}
		});
		

		}
		
		public void startTimer() {
			
			timer = new Timer(20, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					movement(); //움직이는 
					checkCollision(); //Wall, Bar 
					checkCollisionBlock(); //Blocks 50
					myPanel.repaint(); //Redrow
				}
			});
			timer.start(); 
		}
		
		public void movement(){
			if(bar.x < barXTarget) {
				bar.x += 5;
			}else if(bar.x > barXTarget) {
				bar.x -= 5;
			}
			
			if(dir == 0) { // Up-Right
				ball.x += ballSpeed;
				ball.y -= ballSpeed;
			}else if(dir == 1) { // Down-Right
				ball.x += ballSpeed;
				ball.y += ballSpeed;
			}else if(dir == 2) { // Up-Left
				ball.x -= ballSpeed;
				ball.y -= ballSpeed;
			}else if(dir == 3) { // Down-Left
				ball.x -= ballSpeed;
				ball.y += ballSpeed;
			}
		};
		
		public void checkCollision(){
			if(dir == 0) { // Up-Right
				//벽에 충돌됐을때 
				if(ball.y < 0 ) {
					dir = 1;
				}
				
				
				if(ball.x > CANVAS_WIDTH-BALL_WIDTH) { //wall right
					dir =2;
				}
				
				//Bar none
				
			}else if(dir == 1) { // Down-Right
				//벽에 충돌됐을때 
				if(ball.y > CANVAS_HEIGHT-BALL_HEIGHT - BALL_HEIGHT) { //wall bottom
					dir = 0;
				}
				if(ball.x > CANVAS_WIDTH-BALL_WIDTH) { //wall 
					dir =3; //방향이 꺾이도록
				}
				
				
				
			}else if(dir == 2) { // Up-Left
				//wall
				if(ball.y < 0 ) {
					dir = 3;
				}
				if(ball.x < 0 ) {
					dir = 0;
				}
				
				//bar
				
			}else if(dir == 3) { // Down-Left
				//wall
				
				if(ball.y > CANVAS_HEIGHT-BALL_HEIGHT - BALL_HEIGHT) { //wall bottom
					dir = 2;
				}
				if(ball.x < 0 ) { //wall left
					dir = 1;
				}
			}
		};
		
		
		public void checkCollisionBlock(){
			
		};
		
		
	}

	public static void main(String[] args) {

		new MyFrame("Block Game"); // 타이틀

	}

}
