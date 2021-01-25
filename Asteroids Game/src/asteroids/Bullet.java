package asteroids;

import java.awt.Color;
import java.awt.Graphics;

import shapes.Lineseg;
import shapes.Point;
import shapes.Polygon;

public class Bullet extends MovingObject {
	
	private static Point[] bulletModel = {
			new Point(1,1),
			new Point(1,-1),
			new Point(-1,-1),
			new Point(-1,1),
			new Point(1,1),
	};
	private static Point[] devBullet = {
			new Point(1,100),
			new Point(1,-100),
			new Point(-1,-100),
			new Point(-1,100),
			new Point(1,100),
	};
	private static Point[] superBullet = {
			new Point(0,0),
	};

	public Bullet(Player player) {
		super(new Polygon(bulletModel, new Point(player.getCannonPosition())));
		rotation = player.getRotation();
		scale = 2;
		v = new Point(player.v);
		v.add(getDirection().mult(13,13));
	}
	
	public Bullet(Player player, Boolean useDevBullet) {
		super(new Polygon(devBullet, new Point(player.getCannonPosition())));
		rotation = player.getRotation();
		scale = 2;
		v = new Point(player.v);
		v.add(getDirection().mult(15,15));
	}

}
