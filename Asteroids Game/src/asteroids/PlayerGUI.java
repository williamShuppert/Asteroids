package asteroids;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class PlayerGUI {
	
	private int lives;
	private int score;
	
	public PlayerGUI() {
		lives = 5;
		score = 0;
	}
	
	public void draw(Graphics g) {
		g.setFont(new Font("monospaced",0, 20));
		g.drawString("Score: " + score, 5, 15);
		g.drawString("Lives: " + lives, 5, 35);
	}
	
	public void addToScore(int value) {
		score+=value;
		if (score < 0) score = 0;
	}
	
	public void removeLife() {
		lives--;
	}

	public int getLives() {
		return lives;
	}
	
	public int getScore() {
		return score;
	}
	
	public void addToLives(int lives) {
		this.lives += lives;
	}
}
