package asteroids;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import shapes.Lineseg;
import shapes.Point;
import shapes.Polygon;

public class Player extends MovingObject {

	private Boolean dead = false, invincible = false, render = true, renderThruster = false;
	private PlayerGUI playerGUI;
	private int playerDeathSpacing = 0;
	private Point cannonPos;
	private double cannonAngle;
	private Point thrustPos;
	private double thrustAngle;
	static Point[] model = {
			new Point(11,.5),
			new Point(7,.5),
			new Point(7,1.5),
			new Point(3,1.5),
			new Point(3,2.5),
			new Point(2,2.5),
			new Point(2,3.5),
			new Point(5,3.5),
			new Point(5,4.5),
			new Point(-1,4.5),
			new Point(-1,5.5),
			new Point(-2,5.5),
			new Point(-2,6.5),
			new Point(2,6.5),
			new Point(2,7.5),
			new Point(-6,7.5),
			new Point(-6,6.5),
			new Point(-5,6.5),
			new Point(-5,5.5),
			new Point(-4,5.5),
			new Point(-4,4.5),
			new Point(-3,4.5),
			new Point(-3,3.5),
			new Point(-5,3.5),
			new Point(-5,1.5),
			new Point(-4,1.5),
			new Point(-4,.5),
			new Point(-6,.5),
			
			new Point(-6,-.5),
			new Point(-4,-.5),
			new Point(-4,-1.5),
			new Point(-5,-1.5),
			new Point(-5,-3.5),
			new Point(-3,-3.5),
			new Point(-3,-4.5),
			new Point(-4,-4.5),
			new Point(-4,-5.5),
			new Point(-5,-5.5),
			new Point(-5,-6.5),
			new Point(-6,-6.5),
			new Point(-6,-7.5),
			new Point(2,-7.5),
			new Point(2,-6.5),
			new Point(-2,-6.5),
			new Point(-2,-5.5),
			new Point(-1,-5.5),
			new Point(-1,-4.5),
			new Point(5,-4.5),
			new Point(5,-3.5),
			new Point(2,-3.5),
			new Point(2,-2.5),
			new Point(3,-2.5),
			new Point(3,-1.5),
			new Point(7,-1.5),
			new Point(7,-.5),
			new Point(11,-.5),
			new Point(11,.5)
		};
	
	public Player() {
		super(new Polygon(model, new Point()));
		cannonPos = new Point(11,0);
		thrustPos = new Point(-6,0);
		setThrustAngle();
		setCannonAngle();
		scale = 4;
		position = new Point(50,50);
		v = new Point();
		playerGUI = new PlayerGUI();
	}
	
	public Player(Polygon model, Point cannonPosition, Point thrustPosition) {
		super(model);
		thrustPos = thrustPosition;
		cannonPos = cannonPosition;
		setThrustAngle();
		setCannonAngle();
		v = new Point();
		playerGUI = new PlayerGUI();
	}
	
	/* Methods */
	
	private void setCannonAngle() {
		double angle;
		angle = Math.atan(Math.abs(cannonPos.getY())/Math.abs(cannonPos.getX()));
		if (cannonPos.getX() < 0) {
			angle = Math.PI - angle;
		}
		if (cannonPos.getY() > 0) {
			angle = angle * -1;
		}
		cannonAngle = angle;
	}
	
	private void setThrustAngle() {
		double angle;
		angle = Math.atan(Math.abs(thrustPos.getY())/Math.abs(thrustPos.getX()));
		if (thrustPos.getX() < 0) {
			angle = Math.PI - angle;
		}
		if (thrustPos.getY() > 0) {
			angle = angle * -1;
		}
		thrustAngle = angle;
	}
	
	public void draw(Graphics g) {
		if (!render || (playerGUI.getLives() <= 0 && render == false)) return;
		if (!dead) {
			super.draw(g);
			if (renderThruster) {
				//TODO render thruster
			}
		}
		else {
			for (int i = 0; i < points.size() - 1; i++) {
				double d = new Lineseg(new Point(0,0), points.get(i)).findDistance();
				double y = Math.sin(rotation + angles.get(i)) * d;
				double x = Math.cos(rotation + angles.get(i)) * d;
				if (Double.isNaN(x)) x = 0;
				if (Double.isNaN(y)) y = 0;
				x *= scale + playerDeathSpacing;
				y *= scale + playerDeathSpacing;
				x += position.getX();
				y += position.getY();
				
				double d2 = new Lineseg(new Point(0,0), points.get(i+1)).findDistance();
				double y2 = Math.sin(rotation + angles.get(i+1)) * d2;
				double x2 = Math.cos(rotation + angles.get(i+1)) * d2;
				if (Double.isNaN(x2)) x2 = 0;
				if (Double.isNaN(y2)) y2 = 0;
				x2 *= scale + playerDeathSpacing;
				y2 *= scale + playerDeathSpacing;
				x2 += position.getX();
				y2 += position.getY();
				
				g.drawLine((int)x, (int)y, (int)x2, (int)y2);
			}
		}
	}
	
	public void drawCannonPosition(Graphics g) {
		Color prevColor = g.getColor();
		double d = new Lineseg(new Point(0,0), cannonPos).findDistance();
		double y = Math.sin(rotation + cannonAngle) * d;
		double x = Math.cos(rotation + cannonAngle) * d;
		if (Double.isNaN(x)) x = 0;
		if (Double.isNaN(y)) y = 0;
		x *= scale;
		y *= scale;
		x += position.getX();
		y += position.getY();
		g.setColor(Color.red);
		g.fillOval((int)x-2, (int)y-2, 4, 4);
		g.drawString("Cannon", (int)x, (int)y);
		g.setColor(prevColor);
	}
	
	public void drawThrustPosition(Graphics g) {
		Color prevColor = g.getColor();
		double d = new Lineseg(new Point(0,0), thrustPos).findDistance();
		double y = Math.sin(rotation + thrustAngle) * d;
		double x = Math.cos(rotation + thrustAngle) * d;
		if (Double.isNaN(x)) x = 0;
		if (Double.isNaN(y)) y = 0;
		x *= scale;
		y *= scale;
		x += position.getX();
		y += position.getY();
		g.setColor(Color.orange);
		g.fillOval((int)x-2, (int)y-2, 4, 4);
		g.drawString("Thruster", (int)x, (int)y);
		g.setColor(prevColor);
	}
	
	public void drawCenterPosition(Graphics g) {
		Color prevColor = g.getColor();
		double x = position.getX();
		double y = position.getY();
		g.setColor(Color.blue);
		g.fillOval((int)x-2, (int)y-2, 4, 4);
		g.drawString("Center", (int)x, (int)y);
		g.setColor(prevColor);
	}
	
	public void drawDebugMode(Graphics g) {
		drawCannonPosition(g);
		drawCenterPosition(g);
		drawThrustPosition(g);
	}
	
	public void thrustTimer() {
		
	}
	
	public void deathTimer() {
		dead = true;
		
		final Timer tmr = new Timer(2, null);
		tmr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerDeathSpacing += 1;
				if (playerDeathSpacing >= 50) {
					invincible = true;
					playerDeathSpacing = 0;
					v = new Point();
					if (playerGUI.getLives() > 0) {
						invincibleTimer();
						dead = false;
					} else {
						render = false;
					}
					tmr.stop();
				}
			}
		});
		tmr.start();
		
	}
	
	private void invincibleTimer() {
		final Timer tmr = new Timer(55, null);
		tmr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerDeathSpacing++;
				if (playerDeathSpacing % 2 == 0) render = false;
				else if (playerDeathSpacing % 2 != 0) render = true;
				if (playerDeathSpacing >= 40) {
					render = true;
					playerDeathSpacing = 0;
					invincible = false;
					tmr.stop();
				}
			}
		});
		tmr.start();
	}
	
	public void keyInput(boolean[] keysDown) {
		if (dead) return;
		// Arrows
		/*
		if (keysDown[39]) addToRotation(Math.PI/32);
		if (keysDown[37]) addToRotation(-Math.PI/32);
		if (keysDown[38]) v.add(getDirection().div(5,5));
		if (keysDown[40]) v.add(getDirection().mult(-1,-1).div(10,10));
		addToPosition(v.mult(.993,.993));
		*/
		if (keysDown[16]) {
			v.mult(.95,.95); // Brake
			if (keysDown['D']) rotation += Math.PI/64;
			if (keysDown['A']) rotation -= Math.PI/64;
		} else {
			if (keysDown['D']) rotation += Math.PI/32;
			if (keysDown['A']) rotation -= Math.PI/32;
		}
		if (keysDown['W']) {
			v.add(getDirection().div(5,5));
			renderThruster = true;
		} else {
			renderThruster = false;
		}
		if (keysDown['S']) v.add(getDirection().mult(-1,-1).div(10,10));
		v.mult(.993,.993); // slight friction
		update();
		
		//if (keysDown['W']) addToPosition(getDirection());
		//if (keysDown['S']) addToPosition(getDirection().mult(-1,-1));
		//if (keysDown['A']) addToPosition(getDirection(-Math.PI/2));
		//if (keysDown['D']) addToPosition(getDirection(Math.PI/2));
	}
	
	public void drawGUI(Graphics g) {
		playerGUI.draw(g);
	}
	
	/* Getters and Setters */
	
	public void addToScore(int value) {
		playerGUI.addToScore(value);
	}
	
	public void removeLife() {
		playerGUI.removeLife();
	}
	
	public int getLives() {
		return playerGUI.getLives();
	}
	
	public int getScore() {
		return playerGUI.getScore();
	}
	
	public void addToLives(int lives) {
		playerGUI.addToLives(lives);
	}
	
	public Point getCannonPosition() {
		double d = new Lineseg(new Point(0,0), cannonPos).findDistance();
		double y = Math.sin(rotation + cannonAngle) * d;
		double x = Math.cos(rotation + cannonAngle) * d;
		if (Double.isNaN(x)) x = 0;
		if (Double.isNaN(y)) y = 0;
		x *= scale;
		y *= scale;
		x += position.getX();
		y += position.getY();
		return new Point(x,y);
	}
	
	public Boolean isDead() {
		return dead;
	}
	
	public Boolean isInvincible() {
		return invincible;
	}
	
}
