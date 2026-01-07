package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PlayManager {
	// Main play area
	public int x_left;
	public int x_right;
	public int y_bottom;
	public int y_top;
	public int x_score, y_score;

	final int WIDTH = GamePanel.TILESIZE * GamePanel.MAX_BRICKS_IN_ROW;
	final int HEIGHT = GamePanel.TILESIZE * GamePanel.MAX_BRICKS_IN_COL;
	
	public PlayManager() {
		x_left = (GamePanel.SCREEN_WIDTH/2) - (WIDTH/2); // 1280/2 - 400/2 = 480 
		x_right = x_left + WIDTH;
		y_top = 50;
		y_bottom = y_top + HEIGHT;
		
		x_score = x_right + 50;
		y_score = y_bottom -150;
		
	}
	
	public void update() {
		
	}
	
	public void draw(Graphics2D g2) {
		// Draw Main Play Area
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(4f));
		g2.drawRect(x_left - 4, y_top - 4, WIDTH + 8, HEIGHT + 8);
		
	}

	public void reset() {
		
		
	}
	
}
