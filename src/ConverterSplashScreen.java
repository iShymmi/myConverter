import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

/*
 * ConverterSplashScreen is a class that creates an overlay on my Converter
 * The purpose of this class is only for the needs of classes to display "animated logo"
 */
class ConverterSplashScreen extends JFrame {
	private static final long serialVersionUID = 1L;
	
	void paint() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenHeight = (int) screenSize.getHeight();
		int screenWidth = (int) screenSize.getWidth();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(null);
		titlePanel.setSize(600,320);
		titlePanel.setBackground(new Color(230,230,240));
		
		JTextPane title = new JTextPane();
		title.setBounds(120,40,440, 80);
		title.setEditable(false);
		title.setFocusable(false);
		title.setBackground(null);
		title.setFont(title.getFont().deriveFont(Font.ITALIC | Font.BOLD, 60));
		titlePanel.add(title);
		
		
		JProgressBar progressBar=new JProgressBar();
		progressBar.setBounds(100,140,400,60);
		progressBar.setVisible(false);
		progressBar.setBorderPainted(true);
        progressBar.setStringPainted(true);
        progressBar.setBackground(Color.WHITE);
        progressBar.setForeground(new Color(0,40,0));
        progressBar.setValue(0);
        
		titlePanel.add(progressBar);
		
		

        JLabel labelText = new JLabel("Conversion progress: 0%",SwingConstants.CENTER);
        labelText.setBounds(0,210,600,40);
        labelText.setFont(labelText.getFont().deriveFont(Font.ITALIC| Font.BOLD, 20));
        titlePanel.add(labelText);
        labelText.setVisible(false);
        
		
        setVisible(true);
		setResizable(false);
		setBounds((screenWidth-600)/2,(screenHeight-200)/2,600,320);
		add(titlePanel);
		
		DefaultHighlighter.DefaultHighlightPainter highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN);
		String[] lettersAnimation = new String[11];
		StringBuilder text= new StringBuilder(); 
		String titleString = "myConverter";
		
		Random rand = new Random();
		String alphabet = "abcdefghijklmnoprst123456789";
		for(int i = 0; i < 11; i++) {
			try {
				Thread.sleep(140);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lettersAnimation[i] = Character.toString(alphabet.charAt(rand.nextInt(alphabet.length())));
			text.insert(i, lettersAnimation[i]); //+= lettersAnimation[i];
			title.setText(text.toString());
		}
		/*for (int x = 0; x<120;x+=5) {
			title.setLocation(x, 40);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
		}*/
		progressBar.setVisible(true);
		labelText.setVisible(true);
		title.setText(text.toString());
		char c;
		int progress = 0;
		for(int j = 0; j < 11 ; j++) {
			for(int i = 0; i < 7; i++) {
				c = alphabet.charAt(rand.nextInt(alphabet.length()));
				text.setCharAt(j, c);
				title.setText(text.toString());
				if (i == 6) {
					text.setCharAt(j, titleString.charAt(j));
					title.setText(text.toString());
				}
					try {
						title.getHighlighter().addHighlight(j, j+1 , highlightPainter);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			
					try {
						Thread.sleep(60);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				progress+=1;
				progressBar.setValue(progress);
			}
			if(j%2 == 0) {
				progress+=4;
			}
			
			progressBar.setValue(progress);
			labelText.setText("Conversion progress: " +progressBar.getString());
			title.getHighlighter().removeAllHighlights();
		}
		labelText.setText("Converted");
	
	try {
		Thread.sleep(600);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	dispose();
	}
}
