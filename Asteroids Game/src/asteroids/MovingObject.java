package asteroids;

import java.util.Random;

import shapes.Lineseg;
import shapes.Point;
import shapes.Polygon;

public class MovingObject extends Polygon {
	
	protected Point v;
	
	public MovingObject(Polygon model) {
		super(model);
		v = new Point();
	}
	
	/* Methods */
	
	public void update() {
		position.add(v);
	}
	
	protected Point getDirection() {
		return new Point(Math.cos(rotation), Math.sin(rotation));
	}
	
	public Point getDirection(double rotation) {
		return new Point(Math.cos(this.rotation+rotation), Math.sin(this.rotation+rotation));
	}
	
	public void setDirection(Point pt) {
		double y = position.getY() - pt.getY();
		double x = position.getX() - pt.getX();
		double angle = Math.atan(y/x);
		if (x > 0) angle += Math.PI;
		rotation = angle;
	}
	
	/* Getters and Setters */
	
	public Point getV() {
		return v;
	}

	public void setV(Point v) {
		this.v = v;
	}
	
}
