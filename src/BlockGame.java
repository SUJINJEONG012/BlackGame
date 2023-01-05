import java.awt.*;
import java.awt.Event.*;
import javax.swing.*;

public class BlockGame {

	//JFrame을 상속받는 클래스를 생성 
	static class MyFrame extends JFrame{
		
		//constant(상수지정)
		static int BALL_WIDTH = 20;
		static int BALL_HEIGHT = 20;
		static int BLOCK_ROWS = 5;
		static int BLOCK_COLUMNS = 10;
		static int BLOCK_WIDTH = 40;
		static int BLOCK_HEIGHT = 20;
		static int BLOCK_GAP = 3;
		static int BAR_WIDTH = 80;
		static int BAR_HEIGHT = 20;
		
		
		//variable(변수지정)
		
		
		public MyFrame(String title) {
			super(title);
			
		}
	}
	
	
	public static void main(String[] args) {
		
		new MyFrame("Block Game"); //타이틀

	}

}
