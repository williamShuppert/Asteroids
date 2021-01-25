package asteroids;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import javax.swing.Timer;

import drawing.DrawingPanel;
import drawing.DrawingPanelListener;
import shapes.Point;
import shapes.Polygon;

public class Game extends JPanel {
	
	BufferedImage background;
	String soundFile = "shoot.wav";
	private int round;
	Player player = new Player();
	List<Asteroid> asteroids = new ArrayList<Asteroid>();
	List<Bullet> bullets = new ArrayList<Bullet>();
	Collisions collision = new Collisions(player, asteroids, bullets, getWidth(), getHeight());
	Level level = new Level(asteroids, collision,player);
	DrawingPanel dp = new DrawingPanel();
	Timer gameTmr = new Timer(10,null);
	private GameListener listener;
	Polygon cursor;
	Point[] cursorModel = {
			new Point(1,0),
			new Point(0,0),
			new Point(0,1),
			new Point(0,0),
			new Point(-1,0),
			new Point(0,0),
			new Point(0,-1)
	};
	
	public Game(GameListener listener) {
		try {
			background = ImageIO.read(new File("stars.jfif"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		setCursor( getToolkit().createCustomCursor(
                new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB ),
                new java.awt.Point(),
                "" ) );
                
		this.setBackground(Color.black);
		dp.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		dp.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		collision.setBounds(dp.getWidth(), dp.getHeight());
		level.setBounds(dp.getWidth(), dp.getHeight());
		
		round = 0;
		cursor = new Polygon(cursorModel, new Point());
		cursor.setScale(5);
		player.setRotation(-Math.PI/2); // face player up
		player.setPosition(dp.getWidth()/2,dp.getHeight()/2+75); // position player in middle
		
		/* Set up drawing panel */
		dp.setRepaintOnMouseAction(false);
		dp.setBackground(new Color(18,18,18));
		dp.setListener(new DrawingPanelListener() {
			public void paintComponent(Graphics g, JPanel panel) {
				//g.drawImage(background, 0, 0, dp.getWidth(), dp.getHeight(), 0, 0, background.getWidth(), background.getHeight(), null);
				g.setColor(new Color(139,69,19));
				for (int i = 0; i < asteroids.size(); i++) {
					asteroids.get(i).draw(g);
				}
				g.setColor(Color.gray);
				for (int i = 0; i < bullets.size(); i++) {
					bullets.get(i).draw(g);
				}
				player.draw(g);
				g.setColor(Color.gray);
				cursor.draw(g);
				g.setColor(Color.white);
				collision.draw(g);
				player.drawGUI(g);
				level.draw(g);
				//player.drawDebugMode(g);
			}
			public void mouseMoved(MouseEvent e) {
				player.setDirection(new Point(e.getX(),e.getY()));
				cursor.setPosition(e.getX(), e.getY());
			}
			public void mouseDragged(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == 1 && !player.isDead()) {
					try { // Open an audio input stream.            
			            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("shoot.wav"));            
			            // Get a sound clip resource.
			            Clip clip = AudioSystem.getClip();
			         // Open audio clip and load samples from the audio input stream.
			         clip.open(audioIn);
			         clip.start();
			      } catch (Exception huh) { }
					bullets.add(new Bullet(player));
					Point v = player.getV();
					Point bv = new Point(bullets.get(bullets.size()-1).getDirection());
					bv.mult(-1,-1);
					v.add(bv);
				}
			}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == ' ' && !player.isDead()) {
					try { // Open an audio input stream.            
			            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("shoot.wav"));            
			            // Get a sound clip resource.
			            Clip clip = AudioSystem.getClip();
			         // Open audio clip and load samples from the audio input stream.
			         clip.open(audioIn);
			         clip.start();
			      } catch (Exception huh) { }
					bullets.add(new Bullet(player));
					Point v = player.getV();
					Point bv = new Point(bullets.get(bullets.size()-1).getDirection());
					bv.mult(-1,-1);
					v.add(bv);
				}
				if (e.getKeyCode() == 'B') {
					try { // Open an audio input stream.            
			            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("shoot.wav"));            
			            // Get a sound clip resource.
			            Clip clip = AudioSystem.getClip();
			         // Open audio clip and load samples from the audio input stream.
			         clip.open(audioIn);
			         clip.start();
			      } catch (Exception huh) { }
					bullets.add(new Bullet(player,true));
					Point v = player.getV();
					Point bv = new Point(bullets.get(bullets.size()-1).getDirection());
					bv.mult(-1,-1);
					v.add(bv);
				}
				if (e.getKeyCode() == 27) {
					gameTmr.stop();
					if (listener != null) listener.gameOver(player.getScore(), 0);
					//System.exit(0);
				} else if (e.getKeyCode() == 'P') asteroids.clear();
				if (player.getLives() == 0 && e.getKeyCode() == 10) {
					gameTmr.stop();
					if (listener!=null) listener.gameOver(player.getScore(), level.getLevel());
					
				}
				if (e.getKeyCode() == 'O') player.addToLives(-5);
				
			}
			public void keyTyped(KeyEvent e) {}
			public void componentResized(int width, int height) {
				collision.setBounds(width, height);
				level.setBounds(width, height);
			}
		});
		
		// Set up game timer
		gameTmr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (asteroids.size() == 0 && !level.isStarting()) {
					player.addToScore(round*10);
					round++;
					level.start(round);
				}
				dp.repaint();
				player.keyInput(dp.keysDown);
				
				// update objects
				for (int i = 0; i < asteroids.size(); i++) {
					asteroids.get(i).update();
				}
				for (int i = 0; i < bullets.size(); i++) {
					bullets.get(i).update();
				}
				
				collision.allCollisions();
			}
		});
		gameTmr.start();
		
		add(dp);
	}
	
	public void setListener(GameListener listener) {
		this.listener = listener;
	}
}
