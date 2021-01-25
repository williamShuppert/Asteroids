package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.net.http.WebSocket.Listener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import asteroids.Game;
import asteroids.GameListener;
import top10.TopTenListener;
import top10.TopTenPanel;

public class FrameManager extends JFrame implements KeyListener {
	
		Game game;
		TopTenPanel topTenPanel;
		int ans;
	
		public FrameManager() {
		
			
			// full screen
			super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			super.setSize(new Dimension(500,400));
			super.setSize(Toolkit.getDefaultToolkit().getScreenSize());
			setExtendedState(JFrame.MAXIMIZED_BOTH); 
			super.setUndecorated(true);
			
			
			game = new Game(new GameListener() {
				
				public void gameOver(int score, int level) {
					
					getContentPane().remove(game);
					revalidate();
					repaint();
					topTenPanel = new TopTenPanel(score);
					topTenPanel.setListener(new TopTenListener() {
						public void playAgain() {
							getContentPane().remove(topTenPanel);
							revalidate();
							game = new Game(new GameListener() {
								
								public void gameOver(int score, int level) {
									
									getContentPane().remove(game);
									revalidate();
									repaint();
									topTenPanel = new TopTenPanel(score);
									add(topTenPanel);
									
									topTenPanel.requestFocus();
									repaint();
									revalidate();
									
								}
								
							}); 
							add(game);
							revalidate();
							repaint();
							game.setFocusable(true);
							game.requestFocus();
							revalidate();
						}
					});
					
					add(topTenPanel);
					topTenPanel.requestFocus();
					revalidate();
					repaint();
					revalidate();
					
				}
				
			}); 
			
			
			
			add(game);
			//*/
			super.setVisible(true);
			
			
		}
		
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}
	
	
	
}
