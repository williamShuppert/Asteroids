package asteroids;

import java.util.Random;

import shapes.Point;
import shapes.Polygon;

public class Asteroid extends MovingObject {
	
	protected static Random rand = new Random();
	private double rotationSpeed = Math.PI/(250+rand.nextInt(50));
	
	public Asteroid(double scale, Point position) {
		super(getRandomAsteroidModel(scale, position));
		v = getRandomV();
		if (rand.nextBoolean()) {
			rotationSpeed*=-1;
		}
	}
	
	public Asteroid(Polygon model) {
		super(model);
		v = getRandomV();
		if (rand.nextBoolean()) {
			rotationSpeed*=-1;
		}
	}
	
	/* Methods */
	
	public void update() {
		super.update();
		rotation += rotationSpeed;
	}
	
	public static Polygon getRandomAsteroidModel(double scale, Point position) {
		
		Point[] points = new Point[rand.nextInt(7) + 10];
		double interval = Math.PI * 2 / points.length;
		for (int i = 0; i < points.length; i++) {
			if (i != points.length - 1) {
				double r = rand.nextDouble()+1;
				points[i] = new Point(Math.cos(interval*i), Math.sin(interval*i)).mult(r,r);
			} else {
				points[i] = points[0];
			}
		}
		Polygon asteroid = new Asteroid(new Polygon(points, position));
		asteroid.setScale(scale);
		return asteroid;
	}
	
	public Point getRandomV() {
		Point ve = new Point((rand.nextDouble()-.5)*5,(rand.nextDouble()-.5)*5);
		return ve;
	}
	

	
}
