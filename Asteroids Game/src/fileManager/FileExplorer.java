package fileManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FileExplorer extends JFrame {
	
	JTextField txtFilePath;
	JButton btnOpen;
	JButton btnSave;
	JLabel lblError;
	String filePath;
	FileListener listener;
	
	public FileExplorer(boolean visible) {
		super("File Explorer");
		
		lblError = new JLabel();
		txtFilePath = new JTextField(35);
		btnSave = new JButton("Save");
		btnSave.setPreferredSize(new Dimension(75,20));
		btnOpen = new JButton("Open");
		btnOpen.setPreferredSize(new Dimension(75,20));
		
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filePath = txtFilePath.getText();
				if (listener != null) listener.openPressed(filePath);
			}
		});
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filePath = txtFilePath.getText();
				if (listener != null) listener.savePressed(filePath);
			}
		});
		
		txtFilePath.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				lblError.setText("");
			}
			public void removeUpdate(DocumentEvent e) {
				lblError.setText("");
			}
			public void changedUpdate(DocumentEvent e) {
				lblError.setText("");
			}
		});
		
	    addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
        		lblError.setText("");
                e.getWindow().dispose();
            }
        });
		
		setupLayout();
		setSize(500,200);
		setGUIVisible(visible);
	}
	
	public void saveFile(String filePath, List<String> info) {
		saveFile(filePath,info,false);
	}
	
	public void saveFile(String filePath, String info) {
		saveFile(filePath,info,false);
	}
	
	public void saveFile(String filePath, List<String> info, Boolean append) {
		if (append == null) append = false;
		if (info.size() == 0) {
			lblError.setForeground(new Color(178,34,34));
			lblError.setText("Can't save empty File.");
			return;
		}
		
		if (filePath == "" || filePath == null) {
			lblError.setForeground(new Color(178,34,34));
			lblError.setText("No file path.");
			return;
		}
		
		File file = new File(filePath);
		  
		// Create / find the file
		try {
			if (file.createNewFile())
			{
				lblError.setForeground(new Color(0,128,0));
				lblError.setText("File created and saved.");
			} else {
				lblError.setForeground(new Color(0,128,0));
				lblError.setText("File saved.");
			}
			// write to file
			Writer writer = new FileWriter(file, append);
		    // writing to file
		    for (int i = 0; i < info.size(); i++) {
		    	if (i == info.size() - 1) writer.write(info.get(i));
		    	else writer.write(info.get(i) + "\n");
		    }
		    writer.close();
			
		} catch (Exception e) {

			lblError.setForeground(new Color(178,34,34));
			lblError.setText("Error saving File.");
			
		}
		
	}
	
	public void saveFile(String filePath, String info, Boolean append) {
		if (append == null) append = false;
		if (info == null) {
			lblError.setForeground(new Color(178,34,34));
			lblError.setText("Can't save empty File.");
			return;
		}
		
		if (filePath == "" || filePath == null) {
			lblError.setForeground(new Color(178,34,34));
			lblError.setText("No file path.");
			return;
		}
		
		File file = new File(filePath);
		  
		// Create / find the file
		try {
			if (file.createNewFile())
			{
				lblError.setForeground(new Color(0,128,0));
				lblError.setText("File created and saved.");
			} else {
				lblError.setForeground(new Color(0,128,0));
				lblError.setText("File saved.");
			}
			// write to file
			Writer writer = new FileWriter(file, append);
		    // writing to file
			writer.write(info);
			
		    writer.close();
			
		} catch (Exception e) {

			lblError.setForeground(new Color(178,34,34));
			lblError.setText("Error saving File.");
			
		}
		
	}
	
	public List<String> openFile(String filePath) {
		try {
			
			List<String> data = new ArrayList<String>();
			File file = new File(filePath);
			Scanner sc = new Scanner(file);
		  
			while (sc.hasNextLine()) {
				data.add(sc.nextLine());
			}
			sc.close();

			lblError.setForeground(new Color(0,128,0));
			lblError.setText("File opened.");
			
			return data;
	    
		} catch (FileNotFoundException e) {
			
			lblError.setForeground(new Color(178,34,34));
			lblError.setText("Could not find file.");
			return null;
			
		} catch (Exception e) {
			
			lblError.setForeground(new Color(178,34,34));
			lblError.setText("Unknown error.");
			return null;
	  }
	}
	
	private void setupLayout() {
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		////////////////////StartingRow////////////////////
		gc.gridy = 1;
		
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = new Insets(5,5,5,5);
		gc.gridx = 1;
		add(new JLabel("File Path: "), gc);
		
		gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 2;
		add(txtFilePath, gc);
		
		////////////////////AnotherRow////////////////////
		gc.gridy++;
		
		gc.gridx = 2;
		gc.anchor = GridBagConstraints.WEST;
		add(btnOpen, gc);
		
		gc.insets = new Insets(0,85,0,0);
		add(btnSave, gc);
		
		gc.insets = new Insets(0,0,0,7);
		gc.anchor = GridBagConstraints.EAST;
		add(lblError, gc);		
	}
	
	public void addActionListener(FileListener listener) {
		this.listener = listener;
	}
	
	public void setGUIVisible(boolean b) {
		setVisible(b);
		lblError.setText("");
	}
	
	public String getFilePath() {
		filePath = txtFilePath.getText();
		return filePath;
	}
}
