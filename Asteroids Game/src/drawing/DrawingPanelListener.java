package drawing;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public interface DrawingPanelListener {
	public void paintComponent(Graphics g, JPanel panel);
	public void mouseMoved(MouseEvent e);
	public void mouseDragged(MouseEvent e);
	public void mouseClicked(MouseEvent e);
	public void mousePressed(MouseEvent e);
	public void mouseReleased(MouseEvent e); 
	public void mouseEntered(MouseEvent e);
	public void mouseExited(MouseEvent e);
	public void keyPressed(KeyEvent e);
	public void keyReleased(KeyEvent e);
	public void keyTyped(KeyEvent e);
	public void componentResized(int width, int height);
}
