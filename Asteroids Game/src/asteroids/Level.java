package asteroids;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;

import shapes.Point;

public class Level {

	private Random rand = new Random();
	private Player player;
	private int level, width, height;
	private List<Asteroid> asteroids;
	private Collisions collisions;
	private Boolean levelStarting;
	private int count;
	
	public Level(List<Asteroid> asteroids, Collisions collisions, Player player) {
		this.asteroids = asteroids;
		this.collisions = collisions;
		this.player = player;
		levelStarting = false;
	}
	
	public void start(int level) {
		try { // Open an audio input stream.            
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("win.wav"));            
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioIn);
         clip.start();
      } catch (Exception huh) { }
		this.level = level;
		levelStarting = true;
		levelTitleTimer();
	}
	
	public void draw(Graphics g) {
		g.setFont(new Font("monospaced",0, 20));
		g.drawString("Level: " + level, 5, 55);
		g.setFont(new Font("monospaced",0, 25));
		if (levelStarting) {
			if (level > 1) {
				drawCenteredString(g,"Level " + (level-1) + " Complete:",new Rectangle(width,height),new Point(0,-100));
				drawCenteredString(g,"+"+(level-1)*10+" points",new Rectangle(width,height),new Point(0,-50));
				drawCenteredString(g,"Level: " + level,new Rectangle(width,height),new Point(0,50));
			} else {
				drawCenteredString(g,"Level: " + level,new Rectangle(width,height),new Point());
			}
			
			//g.drawString("Level: " + level, width/2, height/2);
		}
		if (player.getLives() <= 0) drawGameOver(g);
	}
	
	public void drawCenteredString(Graphics g, String text, Rectangle rect, Point offset) {
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics();
	    // Determine the X coordinate for the text
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2 + (int)offset.getX();
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent() + (int)offset.getY();
	    // Draw the String
	    g.drawString(text, x, y);
	}
	
	public void drawGameOver(Graphics g) {
		Color color = g.getColor();
		g.setColor(Color.red);
		g.setColor(new Color(147,58,22));
		g.setFont(new Font("monospaced",0, 50));
		drawCenteredString(g,"Game Over",new Rectangle(width,height),new Point(0,-25));
		g.setFont(new Font("monospaced",0, 25));
		drawCenteredString(g,"Press Enter to Continue",new Rectangle(width,height),new Point(0,25));
		g.setColor(color);
	}
	
	private void levelTitleTimer() {
		count = 0;
		Timer tmr = new Timer(1000,null);
		tmr.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				count++;
				if (level == 1 && count > 2) {
					addAsteroids();
					levelStarting = false;
					tmr.stop();
				} else if (count > 4) {
					addAsteroids();
					levelStarting = false;
					tmr.stop();
				}
			}
			
		});
		tmr.start();
	    
		
	}
	
	private void addAsteroids() {
		Asteroid asteroid;
		for (int i = 0; i < level; i++) {
			do {
				
				//asteroid = new Asteroid(30, new Point(rand.nextInt(width), rand.nextInt(height)));
				asteroid = new Asteroid(30, new Point(0, 0));
				if (rand.nextBoolean()) {
					asteroid.addToPosition(rand.nextInt(width),0);
				} else {
					asteroid.addToPosition(0,rand.nextInt(height));
				}
			} while (collisions.asteroidOnPlayer(asteroid));
			asteroids.add(new Asteroid(asteroid));
		}
	}
	
	/* Getters and Setters */
	
	public void setBounds(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public Boolean isStarting() {
		return levelStarting;
	}
	
	public int getLevel() {
		return level;
	}
	
}
