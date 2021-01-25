package top10;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import fileManager.FileExplorer;

public class TopTenPanel extends JPanel implements KeyListener {
	
	FileExplorer file = new FileExplorer(false);
	JTextArea list = new JTextArea();
	JLabel title = new JLabel("TOP TEN");
	JTextField input = new JTextField();
	JButton btn = new JButton("Add");
	JLabel lbl = new JLabel("Enter Name:");
	JLabel playAgain = new JLabel("<html>"
			+ "<div style='text-align: center;'>"
			+ "Play Again: "
			+ "<font color=rgb(147,58,22)>Enter</font><br/>"
			+ "Exit: "
			+ "<font color=rgb(147,58,22)>Esc</font>"
			+ "</div>"
			+ "</html>");
	JLabel exit = new JLabel("Exit: Esc");
	String[] names = new String[10];
	int[] scores = new int[10];
	List<String> info = new ArrayList<String>();
	TopTenListener listener;
	
	public TopTenPanel(int score) {
		playAgain.setForeground(Color.white);
		playAgain.setFont(new Font("monospaced", 0, 20));
		setFocusable(true);
		super.addKeyListener(this);
		super.requestFocus();
		for (int i = 0; i < 10; i++) {
			names[i] = "N/A";
		}
		
		info = file.openFile("topTen.txt");
		
		for (int i = 0; i < info.size(); i+=2) {
			names[i/2] = info.get(i);
			scores[i/2] = Integer.parseInt(info.get(i+1));
		}
		update();

		list.setFont(new Font("monospaced", 0, 35));
		title.setFont(new Font("monospaced", 0, 50));
		
		input.setFont(new Font("monospaced", 0, 20));
		lbl.setFont(new Font("monospaced", 0, 25));
		btn.setFont(new Font("monospaced", 0, 25));
		input.setDocument(new JTextFieldLimit(3));
		input.setHorizontalAlignment(JTextField.CENTER);
     
		
		super.setLayout(new GridBagLayout());
		super.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		list.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		list.setForeground(Color.gray);
		title.setForeground(Color.white);
		this.setBackground(Color.black);
		list.setBackground(Color.black);
		input.setBackground(Color.black);
		input.setForeground(Color.white);
		input.setBorder(null);
		input.setCaretColor(Color.white);
		btn.setBackground(Color.black);
		btn.setForeground(Color.white);
		btn.setBorder(null);
		lbl.setForeground(Color.white);
		list.setEditable(false);
		
		list.setPreferredSize(new Dimension(400,500));
		input.setPreferredSize(new Dimension(100,50));
		btn.setPreferredSize(new Dimension(50,50));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		
		c.gridx = 1; c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 3;
		add(title,c);
		c.gridx = 1; c.gridy = 2;
		c.gridheight = 2;
		c.gridwidth = 3;
		add(list,c);
		
		c.gridx = 4; c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(playAgain,c);
		
		if (isInTopTen(score)) {
			c.gridx = 1; c.gridy = 4;
			c.gridheight = 1;
			c.gridwidth = 1;
			add(lbl,c);
			c.gridx = 2; c.gridy = 4;
			c.gridheight = 1;
			c.gridwidth = 1;
			add(input,c);
			c.gridx = 3; c.gridy = 4;
			c.gridheight = 1;
			c.gridwidth = 1;
			add(btn,c);
			super.requestFocus();
		}
	
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (input.getText().length() != 3) return;
				try { // Open an audio input stream.            
		            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("win.wav"));            
		            // Get a sound clip resource.
		            Clip clip = AudioSystem.getClip();
		         // Open audio clip and load samples from the audio input stream.
		         clip.open(audioIn);
		         clip.start();
		      } catch (Exception huh) { }
				addToTopTen(input.getText(),score);
				update();
				remove(lbl);
				remove(input);
				remove(btn);
				revalidate();
				repaint();
				requestFocus();
			}
		});
	}
	
	public Boolean isInTopTen(int score) {
		for (int i = 0; i < 10; i++) {
			if (score > scores[i]) {
				return true;
			}
		}
		return false;
	}
	
	public void addToTopTen(String name, int score) {
		for (int i = 0; i < 10; i++) {
			if (score > scores[i]) {
				for (int x = 9; x > i; x--) {
					scores[x] = scores[x-1];
					names[x] = names[x-1];
				}
				scores[i] = score;
				names[i] = name;
				info.clear();
				for (int x = 0; x < 10; x++) {
					info.add(names[x]);
					info.add(String.valueOf(scores[x]));
				}
				file.saveFile("topTen.txt", info);
				return;
			}
		}
	}
	
	public void setListener(TopTenListener listener) {
		this.listener = listener;
	}
	
	public void update() {
		String topTen = "";
		for (int i = 0; i < 10; i++) {
			//String temp = (i+1) + " " + names[i] + " " + scores[i] + "\n";
			topTen += String.format("%2d", i+1);
			topTen += " " + names[i] + " ";
			topTen += String.format("%05d", scores[i]) + "\n";
		}
		list.setText(topTen);
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
		if (e.getKeyCode() == 27) System.exit(0);
		if (e.getKeyCode() == 10) {
			if (listener != null) listener.playAgain();
			else System.exit(0);
		}
	
		

		
	}
	
}
