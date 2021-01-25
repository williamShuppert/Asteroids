package shapes;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Polygon {

	protected Point position;
	protected double rotation, scale;
	protected List<Double> angles; // angle from positive x axis
	protected List<Point> points;
	protected List<Lineseg> lines;
	
	/* Constructors */
	
	public Polygon(List<Point> points, Point position) {
		this.points = new ArrayList<Point>(points);
		lines = new ArrayList<Lineseg>();
		angles = new ArrayList<Double>();
		scale = 1; rotation = 0;
		this.position = position;
		setAngles();
		setLines();
	}
	
	public Polygon(Point[] points, Point position) {
		this.points = Arrays.asList(points);
		lines = new ArrayList<Lineseg>();
		angles = new ArrayList<Double>();
		scale = 1; rotation = 0;
		this.position = position;
		setAngles();
		setLines();
	}
	
	public Polygon(Polygon poly) {
		rotation = poly.rotation;
		scale = poly.scale;
		position = poly.position;
		angles = poly.angles;
		points = poly.points;
		lines = poly.lines;
	}
	
	/* Methods */
	
	public Point getIntersection(Polygon poly) {
		setLines();
		Point pt;
		for (int i = 0; i < lines.size(); i++) {
			List<Lineseg> ployLines = poly.getLines();
			for (int y = 0; y < ployLines.size(); y++) {
				pt = lines.get(i).getIntersection(ployLines.get(y));
				if (pt != null) {
					return pt;
				}
			}
		}
		return null;
	}
	
	public Boolean contains(Point pt) {
		Point p = new Point(pt);
		final int INF = 1000;
		Lineseg[] lines = {
			new Lineseg(p,new Point(p).add(0,INF)),
			new Lineseg(p,new Point(p).add(0,-INF)),
			new Lineseg(p,new Point(p).add(INF,0)),
			new Lineseg(p,new Point(p).add(-INF,0))
		};
		setLines();
		for (int i = 0; i < 4; i++) {
			int count = 0;
			for (int x = 0; x < this.lines.size(); x++) {
				if (lines[i].getIntersection(this.lines.get(x)) != null) {
					count ++;
					if (count > 1) return false;
				}
			}
			if (count == 0) return false;
		}
		return true;
	}
	
	private void setAngles() {
		angles.clear();
		for (int i = 0; i < points.size(); i++) {
			Point pt = (Point) points.get(i);
			double angle;
			angle = Math.atan(Math.abs(pt.getY())/Math.abs(pt.getX()));
			if (pt.getX() < 0) {
				angle = Math.PI - angle;
			}
			if (pt.getY() > 0) {
				angle = angle * -1;
			}
			angles.add(i, angle);
		}
	}
	
	private void setLines() {
		lines.clear();
		
		for (int i = 0; i < points.size() - 1; i++) {
			double d = new Lineseg(new Point(0,0), points.get(i)).findDistance();
			double y = Math.sin(rotation + angles.get(i)) * d;
			double x = Math.cos(rotation + angles.get(i)) * d;
			if (Double.isNaN(x)) x = 0;
			if (Double.isNaN(y)) y = 0;
			x *= scale;
			y *= scale;
			x += position.getX();
			y += position.getY();
			
			double d2 = new Lineseg(new Point(0,0), points.get(i+1)).findDistance();
			double y2 = Math.sin(rotation + angles.get(i+1)) * d2;
			double x2 = Math.cos(rotation + angles.get(i+1)) * d2;
			if (Double.isNaN(x2)) x2 = 0;
			if (Double.isNaN(y2)) y2 = 0;
			x2 *= scale;
			y2 *= scale;
			x2 += position.getX();
			y2 += position.getY();
			
			lines.add(new Lineseg(new Point(x,y), new Point(x2,y2)));
		}
	}
	public void draw(Graphics g) {
	for (int i = 0; i < points.size() - 1; i++) {
		double d = new Lineseg(new Point(0,0), points.get(i)).findDistance();
		double y = Math.sin(rotation + angles.get(i)) * d;
		double x = Math.cos(rotation + angles.get(i)) * d;
		if (Double.isNaN(x)) x = 0;
		if (Double.isNaN(y)) y = 0;
		x *= scale;
		y *= scale;
		x += position.getX();
		y += position.getY();
		
		double d2 = new Lineseg(new Point(0,0), points.get(i+1)).findDistance();
		double y2 = Math.sin(rotation + angles.get(i+1)) * d2;
		double x2 = Math.cos(rotation + angles.get(i+1)) * d2;
		if (Double.isNaN(x2)) x2 = 0;
		if (Double.isNaN(y2)) y2 = 0;
		x2 *= scale;
		y2 *= scale;
		x2 += position.getX();
		y2 += position.getY();
		
			g.drawLine((int)x, (int)y, (int)x2, (int)y2);
		}
	}
	
	public String toString() {
		String info = "Polygon is valid: " + isValid();
		info += "\nSides: " + findSides();
		info += "\nType: " + findType();
		info += "\nArea: " + findArea();
		info += "\nPerimeter: " + findPerimeter();
		info += "\n\nLines:";
		for (int i = 0; i < lines.size(); i++) {
			info += "\n" + lines.get(i).toString();
		}
		return info;
	}
	
	public int findSides() {
		int sides;
		lines.clear();
		for (int i = 0; i < points.size() - 1; i++) {
			lines.add(new Lineseg(points.get(i), points.get(i+1)));
		}
		sides = lines.size();
		return sides;
	}
	
	public String findType() {
		lines.clear();
		for (int i = 0; i < points.size() - 1; i++) {
			lines.add(new Lineseg(points.get(i), points.get(i+1)));
		}
		if (!isValid()) return "null";
		switch (lines.size()) {
			case 0:
				return "null";
			case 1:
				return "null";
			case 2:
				return "null";
			case 3:
				return "Triangle";
			case 4:
				return "Quadrilateral";
			case 5:
				return "Pentagon";
			case 6:
				return "Hexagon";
			case 7:
				return "Heptagon";
			case 8:
				return "Octagon";
			case 9:
				return "Nonagon";
			case 10:
				return "Decagon";
			default:
				return "Polygon";
		}
	}
	
	public double findArea() {
		if (!isValid()) return 0;
		double sumLR = 0, sumRL = 0;
		for (int i = 0; i < points.size() - 1; i++) {
			sumLR += points.get(i).getX() * points.get(i+1).getY();
			sumRL += points.get(i).getY() * points.get(i+1).getX();
		}
		sumLR += points.get(0).getX() * points.get(points.size()-1).getY();
		sumRL += points.get(0).getY() * points.get(points.size()-1).getX();
		double dif = Math.abs(sumLR - sumRL);
		return .5 * dif;
	}
	
	public double findPerimeter() {
		if (!isValid()) return 0;
		lines.clear();
		double perimeter = 0;
		for (int i = 0; i < points.size() - 1; i++) {
			lines.add(new Lineseg(points.get(i), points.get(i+1)));
			perimeter += lines.get(i).findDistance();
		}
		return perimeter;
	}
	
	private boolean isValid() {
		lines.clear();
		for (int i = 0; i < points.size() - 1; i++) {
			lines.add(new Lineseg(points.get(i), points.get(i+1)));
		}
		for (int i = 0; i < lines.size(); i++) {
			for (int x = 0; x < lines.size(); x++) {
				if (x != i) {
					Point pt = lines.get(i).getIntersection(lines.get(x));
					if (pt != null) {
						return false;
					}
				}
			}
		}
		if (points.get(0) != points.get(points.size()-1) || points.size() == 1) return false;
		double sumLR = 0, sumRL = 0;
		for (int i = 0; i < points.size() - 1; i++) {
			sumLR += points.get(i).getX() * points.get(i+1).getY();
			sumRL += points.get(i).getY() * points.get(i+1).getX();
		}
		sumLR += points.get(0).getX() * points.get(points.size()-1).getY();
		sumRL += points.get(0).getY() * points.get(points.size()-1).getX();
		double dif = Math.abs(sumLR - sumRL);
		dif = .5 * dif;
		if (dif == 0) return false;
		return true;
	}
	
	/* Getters and Setters */
	
	public List<Lineseg> getLines() {
		setLines();
		return lines;
	}
	
	public Point getPosition() {
		return position;
	}
	
	public void setPosition(Point position) {
		this.position = position;
	}
	
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
	
	public void setScale(double scale) {
		this.scale = scale;
	}
	
	public double getScale() {
		return scale;
	}
	
	public double getRotation() {
		return rotation;
	}
	
	public void setPosition(int x, int y) {
		this.position = new Point(x,y);
	}
	
	public void addToPosition(double x, double y) {
		position.add((int)x,(int)y);
	}
	
	public void addToPosition(Point direction) {
		position.add(direction);
	}
	
	public void addToRotation(double radians) {
		rotation += radians;
	}
	
}
