package shapes;

import java.awt.Graphics;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Lineseg {

	protected Point sp, ep;
	
	/* Constructors */
	
	public Lineseg() {
		sp = new Point();
		ep = new Point();
	}
	
	public Lineseg(double x1, double y1, double x2, double y2) {
		sp = new Point(x1,y1);
		sp = new Point(x2,y2);
	}
	
	public Lineseg(Point sp, Point ep) {
		this.sp = sp;
		this.ep = ep;
	}
	
	/* Methods */
	
	public void draw(Graphics g) {
		g.drawLine((int)sp.x, (int)sp.y, (int)ep.x, (int)ep.y);
	}
	
	public double findDistance() {
		//double distance = Math.sqrt(Math.pow(sp.y - ep.y, 2) + Math.pow(sp.x - ep.x, 2));
		double distance = Math.sqrt(Math.pow(ep.y, 2) + Math.pow(ep.x, 2));
		return distance;
	}
	
	public String toString() {
		return sp.toString() + " - " + ep.toString();
	}
	
	public Point getIntersection(Lineseg l){
    	
        double x1 = sp.getX(); double x2 = ep.getX();
        double y1 = sp.getY(); double y2 = ep.getY();
        double x3 = l.getSp().getX(); double x4 = l.getEp().getX();
        double y3 = l.getSp().getY(); double y4 = l.getEp().getY();

        final double den = (x1 - x2)*(y3 - y4) - (y1 - y2)*(x3 - x4); // IF den == 0 ray and wall are parallel

        if (den == 0) return null; // they don't intersect

        final double t = (((x1 - x3)*(y3 - y4)) - ((y1 - y3)*(x3 - x4))) / den;
        final double u = -((((x1 - x2)*(y1 - y3)) - ((y1 - y2)*(x1 - x3))) / den);

        if (t >= 0 && t <= 1 && u >= 0 && u <= 1) { // IF they do intersect
            Point pt = new Point();
            pt.setX(x1 + t * (x2 - x1));
            pt.setY(y1 + t * (y2 - y1));
            return pt;
        }

        return null; // they don't intersect

    }
	
	/* Getters and Setters */
	
	public Point getSp() {
		return sp;
	}

	public void setSp(Point sp) {
		this.sp = sp;
	}

	public Point getEp() {
		return ep;
	}

	public void setEp(Point ep) {
		this.ep = ep;
	}
	
}
