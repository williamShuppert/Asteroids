package asteroids;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;

import shapes.Point;

public class Collisions {
	
	private int width, height;
	private Player player;
	private List<Asteroid> asteroids;
	private List<Bullet> bullets;
	private List<Point> hitPoints;
	private List<Integer> hitPointTmr;
	private List<Integer> hitPointValue;
	
	public Collisions(Player player, List<Asteroid> asteroids, List<Bullet> bullets, int width, int height) {
		this.player = player;
		this.asteroids = asteroids;
		this.bullets = bullets;
		this.width = width;
		this.height = height;
		hitPoints = new ArrayList<Point>();
		hitPointTmr = new ArrayList<Integer>();
		hitPointValue = new ArrayList<Integer>();
		Timer tmr = new Timer(15, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < hitPoints.size(); i++) {
					hitPoints.get(i).add(0,-1);
					hitPointTmr.set(i, hitPointTmr.get(i)+15);
					if (hitPointTmr.get(i) >= 1000) {
						hitPointTmr.remove(i);
						hitPoints.remove(i);
						hitPointValue.remove(i);
					}
				}
			}
		});
		tmr.start();
	}
	
	/* Methods */
	
	public void draw(Graphics g) {
		Color color = g.getColor();
		String value;
		g.setFont(new Font("monospaced",0, 15));
		
		for (int i = 0; i < hitPoints.size(); i++) {
			if (hitPointValue.get(i) > 0) {
				g.setColor(Color.green);
				value = "+";
			}
			else {
				g.setColor(Color.red);
				value = "-";
			}
			g.drawString(value + String.valueOf(Math.abs(hitPointValue.get(i))),(int)hitPoints.get(i).getX(),(int)hitPoints.get(i).getY());
		}
		g.setColor(color);
	}
	
	public void allCollisions() {
		asteroidOnPlayer();
		asteroidOnAsteroid();
		bulletOnAsteroid();
		borderMove();
		borderDestroy();
	}
	
	public Boolean asteroidOnPlayer(Asteroid asteroid) {
		if (!player.isDead() && !player.isInvincible() && player.getIntersection(asteroid) != null) {
			return true;
		}
		return false;
	}
	
	public void asteroidOnPlayer() {
		for (int i = 0; i < asteroids.size(); i++) {
			if (!player.isDead() && !player.isInvincible() && player.getIntersection(asteroids.get(i)) != null) {
				try { // Open an audio input stream.            
		            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("pop.wav"));            
		            // Get a sound clip resource.
		            Clip clip = AudioSystem.getClip();
		         // Open audio clip and load samples from the audio input stream.
		         clip.open(audioIn);
		         clip.start();
		      } catch (Exception huh) { }
				player.deathTimer();
				player.removeLife();
			}
		}
	}
	
	public void asteroidOnAsteroid() {
		for (int i = 0; i < asteroids.size()-1; i++) {
			for (int x = i+1; x < asteroids.size(); x++) {
				if (asteroids.get(i).getIntersection(asteroids.get(x)) != null) {
					//TODO
					Asteroid p = asteroids.get(i);
					Asteroid p1 = asteroids.get(x);
					
					 final double xVDif = p.getV().getX() - p1.getV().getX(); // difference in x velocity
					 final double yVDif = p.getV().getY() - p1.getV().getY(); // difference in yV
					 final double distanceX = p1.getPosition().getX() - p.getPosition().getX(); // find distance
					 final double distanceY = p1.getPosition().getY() - p.getPosition().getY();

					    if (xVDif * distanceX + yVDif * distanceY >= 0) { // stop overlap of balls
					        /*get angle between colliding balls*/
					    	final double a = -Math.atan2(p1.getPosition().getY() - p.getPosition().getY(), p1.getPosition().getX() - p.getPosition().getX());
					    	final double m1 = 1;
					    	final double m2 = 1;
					        /*current velocity rotated to 1d for equation*/
					    	final Point u1 = rotate(p.getV().getX(), p.getV().getY(), a);
					    	final Point u2 = rotate(p1.getV().getX(), p1.getV().getY(), a);
					        /*1d collision equation*/
					    	final Point v1 = new Point(u1.getX() * (m1 - m2) / (m1 + m2) + u2.getX() * 2 * m2 / (m1 + m2), u1.getY() );
					    	final Point v2 = new Point(u2.getX() * (m1 - m2) / (m1 + m2) + u1.getX() * 2 * m2 / (m1 + m2), u2.getY() );
					        /*rotate back and find velocity*/
					    	final Point vFinal1 = rotate(v1.getX(), v1.getY(), -a);
					        final Point vFinal2 = rotate(v2.getX(), v2.getY(), -a);
					        /*spawp velocity*/
					        p.setV(new Point(vFinal1.getX(),vFinal1.getY()));
					        p1.setV(new Point(vFinal2.getX(),vFinal2.getY()));
			
					    }
					
				}
			}
		}
	}
	
	private Point rotate(double xV, double yV, double a) {
	    Point rotatedVelocities = new Point(
	        xV * Math.cos(a) - yV * Math.sin(a),
	        xV * Math.sin(a) + yV * Math.cos(a)
	    );

	    return rotatedVelocities;
	}
	
	public void bulletOnAsteroid() {
		for (int i = 0; i < asteroids.size(); i++) {
			for (int x = 0; x < bullets.size(); x++) {
				if (asteroids.get(i).getIntersection(bullets.get(x)) != null || asteroids.get(i).contains(bullets.get(x).getPosition())) {
					try { // Open an audio input stream.            
			            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("death.wav"));            
			            // Get a sound clip resource.
			            Clip clip = AudioSystem.getClip();
			         // Open audio clip and load samples from the audio input stream.
			         clip.open(audioIn);
			         clip.start();
					} catch (Exception huh) { }
					hitPoints.add(bullets.get(x).getPosition());
					hitPointTmr.add(0);
					bullets.remove(x);
					player.addToScore(10);
					hitPointValue.add(10);
					double scale = asteroids.get(i).getScale()*.6;
					if (scale > 10) {
						asteroids.set(i, new Asteroid(scale,new Point(asteroids.get(i).getPosition())));
						asteroids.add(i, new Asteroid(scale,new Point(asteroids.get(i).getPosition())));
					} else {
						player.addToScore(5);
						hitPointValue.set(hitPointValue.size()-1,15);
						asteroids.remove(i);
					}
					if (i >= asteroids.size() || x >= bullets.size()) return;
					
				}
			}
		}
	}
	
	public void borderMove() {
		// Asteroids
		for (int i = 0; i < asteroids.size(); i++) {
			MovingObject asteroid = asteroids.get(i);
			if (asteroid.getPosition().getX() < -44) {
				asteroid.setPosition(new Point(width,asteroid.getPosition().getY()));
			} else if (asteroid.getPosition().getX() > width) {
				asteroid.setPosition(new Point(-44,asteroid.getPosition().getY()));
			} else if (asteroid.getPosition().getY() < -44) {
				asteroid.setPosition(new Point(asteroid.getPosition().getX(),height));
			} else if (asteroid.getPosition().getY() > height) {
				asteroid.setPosition(new Point(asteroid.getPosition().getX(),-44));
			}
		}
		// Player
		if (player.getPosition().getX() < -44) {
			player.setPosition(new Point(width,player.getPosition().getY()));
		} else if (player.getPosition().getX() > width) {
			player.setPosition(new Point(-44,player.getPosition().getY()));
		} else if (player.getPosition().getY() < -44) {
			player.setPosition(new Point(player.getPosition().getX(),height));
		} else if (player.getPosition().getY() > height) {
			player.setPosition(new Point(player.getPosition().getX(),-44));
		}
	}
	
	public void borderDestroy() {
		for (int i = 0; i < bullets.size(); i++) {
			MovingObject bullet = bullets.get(i);
			if (bullet.getPosition().getX() < -44) {
				bullets.remove(i);
				player.addToScore(-1);
				hitPoints.add(bullet.getPosition().add(50,0));
				hitPointValue.add(-1);
				hitPointTmr.add(0);
			} else if (bullet.getPosition().getX() > width) {
				bullets.remove(i);
				player.addToScore(-1);
				hitPoints.add(bullet.getPosition().add(-35,0));
				hitPointValue.add(-1);
				hitPointTmr.add(0);
			} else if (bullet.getPosition().getY() < -44) {
				bullets.remove(i);
				player.addToScore(-1);
				hitPoints.add(bullet.getPosition().add(0,70));
				hitPointValue.add(-1);
				hitPointTmr.add(0);
			} else if (bullet.getPosition().getY() > height) {
				bullets.remove(i);
				player.addToScore(-1);
				hitPoints.add(bullet.getPosition().add(0,-10));
				hitPointValue.add(-1);
				hitPointTmr.add(0);
			}
		}
	}
	
	/* Setters and Getters */
	
	public void setBounds(int width, int height) {
		setWidth(width);
		setHeight(height);
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
}
