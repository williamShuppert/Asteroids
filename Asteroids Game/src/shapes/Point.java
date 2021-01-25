package shapes;

import java.awt.Graphics;

public class Point {

	protected double x,y,r = 4;
	
	/* Constructors */
	
	public Point() {
		x = 0;
		y = 0;
	}
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(Point pt) {
		x = pt.x;
		y = pt.y;
	}
	
	/* Methods */
	
	public void draw(Graphics g) {
		g.fillOval((int)(x-r/2), (int)(y-r/2), (int)r, (int)r);
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	/* Getters and Setters */
	
	public double getRadius() {
		return r;
	}

	public void setRadius(double r) {
		this.r = r;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	/* Maths */
	
	public Point add(double x, double y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Point add(Point pt) {
		this.x += pt.x;
		this.y += pt.y;
		return this;
	}
	
	public Point mult(double x, double y) {
		this.x *= x;
		this.y *= y;
		return this;
	}
	
	public Point mult(Point pt) {
		this.x *= pt.x;
		this.y *= pt.y;
		return this;
	}
	
	public Point div(double x, double y) {
		this.x /= x;
		this.y /= y;
		return this;
	}
	
	public Point div(Point pt) {
		this.x /= pt.x;
		this.y /= pt.y;
		return this;
	}
	
	
}
