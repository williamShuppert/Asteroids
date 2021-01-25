package drawing;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import shapes.Point;

public class DrawingPanel extends JPanel implements MouseMotionListener, MouseListener, ComponentListener, KeyListener {
	
	DrawingPanelListener listener;
	Boolean repaintOnMouseAction;
	Point origin;
	double width = 1;
	double height = 1;
	public boolean[] keysDown = new boolean[525];
	
	public boolean[] getKeysDown() {
		return keysDown;
	}

	public Point getOrigin() {
		return origin;
	}

	public void setOrigin(Point origin) {
		this.origin = origin;
	}
	
	public Point toPanelPoint(Point cartesianPoint) {

		return new Point(
				//(cartesianPoint.getX() * width + origin.getX()),
				//(-cartesianPoint.getY() * height + origin.getY())
				BigDecimal.valueOf((cartesianPoint.getX() * width + origin.getX())).setScale(2,RoundingMode.HALF_UP).doubleValue(),
				BigDecimal.valueOf((-cartesianPoint.getY() * height + origin.getY())).setScale(2,RoundingMode.HALF_UP).doubleValue()
				);
	}
	
	public Point toCartesianPoint(Point panelPoint) {
		
		return new Point(
				//(((panelPoint.getX() - origin.getX())) / width),
				//(((panelPoint.getY() - origin.getY()) * -1) / height)
				BigDecimal.valueOf((((panelPoint.getX() - origin.getX())) / width)).setScale(2,RoundingMode.HALF_UP).doubleValue(),
				BigDecimal.valueOf((((panelPoint.getY() - origin.getY()) * -1) / height)).setScale(2,RoundingMode.HALF_UP).doubleValue()
				);
	}

	public DrawingPanel() {
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.addComponentListener(this);
		this.addKeyListener(this);
		this.setFocusable(true);
		repaintOnMouseAction = true;
	}
	
	public DrawingPanel(DrawingPanelListener listener) {
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.addKeyListener(this);
		this.setFocusable(true);
		repaintOnMouseAction = true;
		this.listener = listener;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (listener != null) listener.paintComponent(g, this);
	}
	
	public void setListener(DrawingPanelListener listener) {
		this.listener = listener;
	}
	
	public Boolean getRepaintOnMouseAction() {
		return repaintOnMouseAction;
	}

	public void setRepaintOnMouseAction(Boolean repaintOnMouseAction) {
		this.repaintOnMouseAction = repaintOnMouseAction;
	}

	
	
	public void mouseDragged(MouseEvent e) {
		if (listener != null) listener.mouseDragged(e);
		if (repaintOnMouseAction) repaint();
	}
	public void mouseMoved(MouseEvent e) {
		if (listener != null) listener.mouseMoved(e);
		if (repaintOnMouseAction) repaint();
	}
	public void mouseClicked(MouseEvent e) {
		if (listener != null) listener.mouseClicked(e);
		if (repaintOnMouseAction) repaint();
	}
	public void mousePressed(MouseEvent e) {
		if (listener != null) listener.mousePressed(e);
		if (repaintOnMouseAction) repaint();
	}
	public void mouseReleased(MouseEvent e) {
		if (listener != null) listener.mouseReleased(e);
		if (repaintOnMouseAction) repaint();
	}
	public void mouseEntered(MouseEvent e) {
		if (listener != null) listener.mouseEntered(e);
		if (repaintOnMouseAction) repaint();
	}
	public void mouseExited(MouseEvent e) {
		if (listener != null) listener.mouseExited(e);	
		if (repaintOnMouseAction) repaint();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		if (listener != null) listener.componentResized(super.getWidth(), super.getHeight());	
		if (repaintOnMouseAction) repaint();
		//test();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void test() {
		setOrigin(new Point(getWidth() / 2,getHeight() / 2));
		this.width = (getWidth() / 2.0) / 100.;
		this.height = (getHeight() / 2.) / 100.;
		//System.out.println((getWidth()/2.)/100. + " " + width);
	}
	
	public void keyTyped(KeyEvent e) {
		if (listener != null) listener.keyTyped(e);	
		if (repaintOnMouseAction) repaint();		
	}

	public void keyPressed(KeyEvent e) {
		keysDown[e.getKeyCode()] = true;
		if (listener != null) listener.keyPressed(e);	
		if (repaintOnMouseAction) repaint();		
	}

	public void keyReleased(KeyEvent e) {
		keysDown[e.getKeyCode()] = false;
		if (listener != null) listener.keyReleased(e);	
		if (repaintOnMouseAction) repaint();		
	}

}
