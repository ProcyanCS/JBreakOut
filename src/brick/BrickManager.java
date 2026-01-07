package brick;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.InputHandler;
import main.KeyHandler;
import main.Sound;

public class BrickManager {
	
	GamePanel gp;
	InputHandler inp;
	KeyHandler keyh;
	
	Sound sound = new Sound();
	
	public Brick brick[];
	public int brickGrid[][];
	public static String direction = "down";
	public int numColors = 3;
	
	public BrickManager(GamePanel gp, InputHandler inp, KeyHandler keyh, int diff) {
		this.gp = gp;
		this.inp = inp;
		this.keyh = keyh;
		this.numColors = diff;
		brick = new Brick[8];
		brickGrid = new int[GamePanel.MAX_BRICKS_IN_COL][GamePanel.MAX_BRICKS_IN_ROW];
		
		loadBrickImages();
		generateRandomGrid(this.numColors);
	}
	
	public void loadBrickImages() {
		try {
			brick[0] = new Brick();
			brick[0].image = ImageIO.read(getClass().getResourceAsStream("/bricks/brick_blue.png"));
			
			brick[1] = new Brick();
			brick[1].image = ImageIO.read(getClass().getResourceAsStream("/bricks/brick_green.png"));
			
			brick[2] = new Brick();
			brick[2].image = ImageIO.read(getClass().getResourceAsStream("/bricks/brick_orange.png"));
			
			brick[3] = new Brick();
			brick[3].image = ImageIO.read(getClass().getResourceAsStream("/bricks/brick_magenta.png"));
			
			brick[4] = new Brick();
			brick[4].image = ImageIO.read(getClass().getResourceAsStream("/bricks/brick_yellow.png"));
			
			brick[5] = new Brick();
			brick[5].image = ImageIO.read(getClass().getResourceAsStream("/bricks/brick_cyan.png"));
			
			brick[6] = new Brick();
			brick[6].image = ImageIO.read(getClass().getResourceAsStream("/bricks/brick_red.png"));
			
			brick[7] = new Brick();
			brick[7].image = ImageIO.read(getClass().getResourceAsStream("/bricks/brick_purple.png"));
			
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void generateRandomGrid( int numBrickColors ) {
		Random rand = new Random();
		for (int row = 0; row < GamePanel.MAX_BRICKS_IN_COL; row++) {
			for (int col = 0; col < GamePanel.MAX_BRICKS_IN_ROW; col++) {
				brickGrid[row][col] = rand.nextInt(numBrickColors);
			}
		}
		
	}
	
	public void draw(Graphics2D g2) {
		for (int row = 0; row < GamePanel.MAX_BRICKS_IN_COL; row++) {
			for (int col = 0; col < GamePanel.MAX_BRICKS_IN_ROW; col++) {
				if (!(brickGrid[row][col] < 0)) {
				g2.drawImage(
						brick[brickGrid[row][col]].image, 
						col * GamePanel.TILESIZE + gp.pm.x_left,
						row * GamePanel.TILESIZE + gp.pm.y_top,
						GamePanel.TILESIZE,
						GamePanel.TILESIZE,
						null);
				}
			}
		}
	}
	
	public void update() {
		if (keyh.upPressed == true || keyh.downPressed == true 
				|| keyh.leftPressed == true || keyh.rightPressed == true ) {
			//System.out.println(direction);
			if (keyh.upPressed == true) {
				direction = "up";
			}
			else if (keyh.downPressed == true) {
				direction = "down";			
				}
			else if (keyh.leftPressed == true) {
				direction = "left";
			}
			else if (keyh.rightPressed == true) {
				direction = "right";
			}
			applyGravity(brickGrid);
			playSound(0);
			gp.ui.updateDirection(direction);
		}

		
		if (inp.click == true) {
			int col = Math.floorDiv( inp.x - gp.pm.x_left , GamePanel.TILESIZE );
			int row = Math.floorDiv( inp.y - gp.pm.y_top, GamePanel.TILESIZE );
			
			if (((col >= 0) && (col < GamePanel.MAX_BRICKS_IN_ROW) &&
					(row >= 0) && (row < GamePanel.MAX_BRICKS_IN_COL)) && 
					(brickGrid[row][col] != -1)) {
				
				List<int[]> connBlocks = connectedRegion(brickGrid, row, col);
				if (connBlocks.size() >= 3) {
					if (connBlocks.size() > 5) {
						playSound(1);
					}
					else {
						playSound(2);
					}
					for (int[] cell : connBlocks) {
						int r2 = cell[0];
						int c2 = cell[1];
						brickGrid[r2][c2] = -1;
						}
					
					updateScore(connBlocks.size());
					
					applyGravity(brickGrid);
					}
				}
			
				else {
					// make bad noise
				}
			inp.click = false;
		}
				
	}
	
	public static List<int[]> connectedRegion(int[][] grid, int r, int c) {
        int rows = grid.length;
        int cols = grid[0].length;
        int value = grid[r][c];

        boolean[][] visited = new boolean[rows][cols];
        List<int[]> region = new ArrayList<>();
        Deque<int[]> stack = new ArrayDeque<>();

        stack.push(new int[]{r, c});

        while (!stack.isEmpty()) {
            int[] cell = stack.pop();
            int x = cell[0], y = cell[1];

            if (visited[x][y]) continue;
            visited[x][y] = true;
            region.add(new int[]{x, y});

            // 4-directional neighbors
            int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};

            for (int[] d : dirs) {
                int nx = x + d[0];
                int ny = y + d[1];

                if (nx >= 0 && nx < rows && ny >= 0 && ny < cols) {
                    if (!visited[nx][ny] && grid[nx][ny] == value) {
                        stack.push(new int[]{nx, ny});
                    }
                }
            }
        }

        return region;
    }

	
	public static void applyGravity(int[][] grid) {
	    int rows = grid.length;
	    int cols = grid[0].length;
	    	    
	    switch(direction) {
	    // DOWN DIRECTION -------------------------------------------- //
	    case "down":
	    	for (int c = 0; c < cols; c++) {
	    		int writeRow = rows - 1;
	    		
	    		for (int readRow = rows - 1; readRow >= 0; readRow--) {
	    			if (grid[readRow][c] != -1) {
	    				grid[writeRow][c] = grid[readRow][c];
	    				writeRow--;
	    			}
	    		}
		        for (int r = writeRow; r >= 0; r--) {
		            grid[r][c] = -1;
		        }
	    	}
	    	break;
	    // UP DIRECTION -------------------------------------------- //
	    case "up":
	    	for (int c = 0; c < cols; c++) {
	    		int writeRow = 0;
	    		for (int readRow = 0; readRow <= rows - 1; readRow++) {
	    			if (grid[readRow][c] != -1) {
	    				grid[writeRow][c] = grid[readRow][c];
	    				writeRow++;
	    			}
	    		}
	    		for (int r = writeRow; r <= rows - 1; r ++) {
	    			grid[r][c] = -1;
	    		}
	    	}
	    	break;
	    // LEFT DIRECTION -------------------------------------------- //
	    case "left":
	    	for (int r = 0; r < rows; r++) {
	    		int writeCol = 0;
	    		for (int readCol = 0; readCol < cols; readCol++) {
	    			if (grid[r][readCol] != -1 ) {
	    				grid[r][writeCol] = grid[r][readCol];
	    				writeCol++;
	    			}
	    		}
	    		for (int c = writeCol; c <= cols - 1; c++) {
	    			grid[r][c] = -1;
	    		}
	    	}
	    	break;
	    // RIGHT DIRECTION -------------------------------------------- //
	    case "right":
	    	for (int r = 0; r < rows; r++) {
	            int writeCol = cols - 1;

	            // move non -1 values to the right
	            for (int readCol = cols - 1; readCol >= 0; readCol--) {
	                if (grid[r][readCol] != -1) {
	                    grid[r][writeCol] = grid[r][readCol];
	                    writeCol--;
	                }
	            }

	            // fill the rest with -1
	            for (int c = writeCol; c >= 0; c--) {
	                grid[r][c] = -1;
	            }
	    	}
	    	break;
	    }
	    
	}
	
	public void updateScore( int val ) {
		
		int score = val * (100 *(this.numColors - 2) + 25*(val - 3));
		//System.out.println("score=" + score);
		this.gp.changeScore(GamePanel.score + score);
	}

	public void reset(int difficulty) {
		updateScore(0);
		direction = "down";
		generateRandomGrid(difficulty);
		
	}
	
	public void playSound(int i) {
		sound.setFile(i);
		sound.play();
	}
	


}
