package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JPanel;

import brick.BrickManager;


public class GamePanel extends JPanel implements Runnable {

	final static public int SCREEN_WIDTH = 720;//1280;
	final static public int SCREEN_HEIGHT = 720;
	
	final static public int MAX_BRICKS_IN_ROW = 16;
	final static public int MAX_BRICKS_IN_COL = 20;
	final static public int TILESIZE = 32;
	final int WIDTH =TILESIZE * MAX_BRICKS_IN_ROW;
	
	final int FPS = 60;
	Thread gameThread;
	InputHandler inp = new InputHandler();
	KeyHandler keyH = new KeyHandler();
	
	BrickManager bm;
	public PlayManager pm;
	public UIPanel ui;
	
	public static long score = 0;
	public static long hscore = 0;
	private int difficulty = 3;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setLayout(null);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.addMouseListener(inp);
		this.addKeyListener(keyH);
		
		pm = new PlayManager();
		bm = new BrickManager(this, inp, keyH, difficulty);
	}
	
	public void launchGame() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double drawInterval = 1_000_000_000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		
		while (gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta > 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if (timer >= 1_000_000_000) {
				//System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
			
			
		}
		
	}
	
	public void update() {
		pm.update();
		bm.update();
		
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		pm.draw(g2);
		
		bm.draw(g2);
		g2.dispose();
	}
	
	public void changeScore(long l) {
	    score = l;
	    if (score > hscore) {
	        hscore = score;
	    }

	    if (ui != null) {
	        ui.updateScore(score, hscore);
	    }

	}

	public void resetGame(int difficulty) {
        pm.reset();
        bm.reset(difficulty);
        score = 0;
        if (ui != null) {
            ui.updateScore(score, hscore);
            ui.updateDirection("down");
        }


	}

	public void setUIPanel(UIPanel uiPanel) {
		ui = uiPanel;
		
	}
	
	
}
