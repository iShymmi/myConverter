import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;


@SuppressWarnings({"rawtypes", "unchecked"})
/*
 * class GUI building user interface
 * GUI includes one JFrame and panels 
 * <ul>
 * 	<li>menuPanel
 * 	<li>converterPanel
 * 	<li>warningPanel (used only to set up background of warningText)
 *</ul>
 */

class GUI extends JFrame{
	private static final long serialVersionUID = 4519627974310830116L;
	private final JTextField inputA;
	private final JTextField inputB;
	private final JComboBox comboList;
	private DocumentListener docListener;
	private KeyListener keyListener;
	private final JLabel warningText;
	private int choosenConverter;
	private int whichWay;
	private int converterOptionID;
	private final JPanel converterPanel = new JPanel();
	private final JPanel welcomePanel = new JPanel();
	private final JPanel fromFilePanel = new JPanel();
	private final ButtonGroup optionsGroup;
	private final JRadioButton option1;
	private final JRadioButton option2;
	private final JRadioButton option3;
	private final JRadioButton option4;
	private final JRadioButton option5;
	private final JRadioButton option6;
	private String option1String,option2String,option3String,option4String,option5String,option6String;
	private final JButton convertButton ;
	private File file;
	private final JLabel animatedImg;
	private int converterId;
	private final double[] currency = new double[3];
	private AnimateLabel animateAreaImage;
	private AnimateLabel animateWeightImage;
	private AnimateLabel animateCurrencyImage;
	private JTextPane helpDialog;
	private final JLabel history;
	private final JLabel history2;
	private final JLabel history3;
	private final JLabel clearHistory;
	private final JLabel clearHistory2;
	private final JLabel clearHistory3;
	private final JLabel addToHistory;
	private final JLabel savedTextLabel ;
	ConversionFromFile convertFromFile;
	private ArrayList<String> inputsArray= new ArrayList<String>();
	private ArrayList<String> outputsArray = new ArrayList<String>();
	private ArrayList<String> inConvertingLables = new ArrayList<String>();
	private boolean saveEnabled;
	private JFrame popUpInstructions; 
	private final String[] converterName= {
			"Area", 
			"Weight", 
			"Currency" 
	};
	private final String[][] weightString= {
			{"KG","LB"},  
			{"KG","OZ"},  
			{"LB","OZ"}  
	};
	private final String[][] areaString= {
			{"M^2","FT^2"},  
			{"M^2","yd^2"},  
			{"yd^2","FT^2"}  
	};
	private final String[][] currencyString= {
			{"PLN","EURO"},  
			{"PLN","USD"},  
			{"EURO","USD"}  
	};
	ResourceBundle r;
	GUI(Locale locale, ArrayList<String> reloadedInputs, ArrayList<String> reloadedOutputs, ArrayList<String> reloadedLables) {
		if (reloadedInputs != null) {
			inputsArray = reloadedInputs;
			outputsArray = reloadedOutputs;
			inConvertingLables = reloadedLables;
		}
		r = ResourceBundle.getBundle("messages", locale);
		JPanel headerPanel = new JPanel();
		JPanel mainPanel = new JPanel();
		JPanel menuPanel = new JPanel();
		/*
		 * screenSize is used to center of following windows
		 */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenHeight = (int) screenSize.getHeight();
		int screenWidth = (int) screenSize.getWidth();
		
		
		
		
		
		mainPanel.setLayout(null);
		
//---------------------------headerPanel-------------------------------------------------------------
		JLabel headerLabel = new JLabel(r.getString("GUI.TITLE"), SwingConstants.CENTER); 
		headerLabel.setFont(headerLabel.getFont().deriveFont(Font.BOLD | Font.ITALIC, 16));
		headerLabel.setForeground(Color.black);
		headerPanel.setSize(600,30);
		headerPanel.add(headerLabel);
		headerPanel.setBackground(new Color(145,145,180));
		mainPanel.add(headerPanel);
		
		/*
		 * CONVERTER PANEL SETTINGS
		 * <hr>
		 * After user choose converter he is interested in converterPanel depending on the parameters received adapts
		 * converterPanel includes JTextFields inputA and inputB which converts entered values using DocumentListener
		 * and changeListener method (defined below)
		 * The only button backMenu return back to Menu by setting menuPanel as leading content
		 * @param whichWay send to converter in which box user is making changes. Thanks to this the user has the opportunity
		 * to convert in both ways without changing converter
		 */
		converterPanel.setLayout(null); //395
		converterPanel.setBounds(205,30,395,260);
		
		popUpInstructions = new JFrame();
		JButton convertingInstructions = new JButton("?"); 
		convertingInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog(popUpInstructions, "");
				dialog.setLayout(new GridBagLayout());
				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.HORIZONTAL;
				String helpText = 
						r.getString("GUI.Instructions1") 
						+ r.getString("GUI.Instructions2") 
						+ r.getString("GUI.Instructions3") 
						+ r.getString("GUI.Instructions4") 
						+ r.getString("GUI.Instructions5") 
						+ r.getString("GUI.Instructions6") 
						+ r.getString("GUI.Instructions7") 
						+ r.getString("GUI.Instructions8") 
						+ r.getString("GUI.Instructions9") 
						+ r.getString("GUI.Instructions10") 
						+ r.getString("GUI.Instructions11") 
						+ r.getString("GUI.Instructions12"); 
				helpDialog = new JTextPane();
				helpDialog.setContentType("text/html"); 
				helpDialog.setText(helpText);
				helpDialog.setEditable(false);
				helpDialog.setBackground(null);
			
				JButton exitHelp = new JButton("OK"); 
				exitHelp.setPreferredSize(new Dimension(100,100));
				exitHelp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dialog.dispose();				
					}
					
				});
				helpDialog.setSize(new Dimension(350,500));
				helpDialog.setPreferredSize(new Dimension(350, 500));
				c.gridx = 0;
				c.gridy = 0;
				c.weightx = 1;
				c.weighty = 1;
				c.insets= new Insets(5,0,0,0);
				dialog.add(helpDialog, c);
				
				c.weighty = 1;
				c.fill = GridBagConstraints.BOTH;
				c.ipady = 0;
				c.insets = new Insets(10,0,0,0);
				c.gridx = 0;
				c.gridy = 1;
				exitHelp.setSize(new Dimension(100, 100));
				exitHelp.setPreferredSize(new Dimension(100, 100));
				dialog.add(exitHelp, c);
				dialog.setVisible(true);
				dialog.setResizable(false);
				dialog.pack();
				dialog.setBounds((screenWidth-400)/2,(screenHeight-500)/2,350,(helpDialog.getSize().height + exitHelp.getSize().height)+50);
				
			}
			
		});
		convertingInstructions.setFont(convertingInstructions.getFont().deriveFont(Font.BOLD, 12));
		convertingInstructions.setBounds(319, 12, 45, 25);
		converterPanel.add(convertingInstructions);
		
      	JLabel mainText = new  JLabel(r.getString("GUI.22"));  
		mainText.setBounds(10,10,360,25);
		mainText.setFont(mainText.getFont().deriveFont(Font.BOLD | Font.ITALIC, 18));
		converterPanel.add(mainText);
				
		warningText = new JLabel(r.getString("GUI.23"), SwingConstants.CENTER); 
		warningText.setFont(new Font("Serif", Font.BOLD, 16)); 
		warningText.setBackground(Color.green);
		warningText.setOpaque(true);
		warningText.setBounds(10,100,360,45);
		converterPanel.add(warningText);
		
		animatedImg = new JLabel();
		animatedImg.setBounds(318, 45, 48, 48);
		converterPanel.add(animatedImg);
		
		JLabel descriptionA = new JLabel("",SwingConstants.LEFT); 
		descriptionA.setBounds(125,165,45,45);
		converterPanel.add(descriptionA);
		
		JLabel descriptionB = new JLabel("",SwingConstants.RIGHT); 
		descriptionB.setBounds(220,165,35,45);
		converterPanel.add(descriptionB);
		
		JLabel equalsLabel = new JLabel(scaleImage(new ImageIcon("images/leftrightarrows.png"), 36, 24), SwingConstants.CENTER); 
		equalsLabel.setFont(equalsLabel.getFont().deriveFont(Font.BOLD | Font.ITALIC, 18));
		equalsLabel.setBounds(125,165,135,45);
		converterPanel.add(equalsLabel);
		
		
		comboList = new JComboBox();
		comboList.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							int itemId = comboList.getSelectedIndex();
							if(converterId == 0) {
								descriptionA.setText(areaString[itemId][0]);
								descriptionB.setText(areaString[itemId][1]);
							}else if(converterId == 1) {
								descriptionA.setText(weightString[itemId][0]);
								descriptionB.setText(weightString[itemId][1]);
							}else if(converterId == 2) {
								descriptionA.setText(currencyString[itemId][0]);
								descriptionB.setText(currencyString[itemId][1]);
							}
						}catch(Exception e1) {
							descriptionA.setText(weightString[0][0]);
							descriptionB.setText(weightString[0][1]);
						}
						if(whichWay == 1) {
							changeListener(comboList, inputB, inputA);
						}
		            	if(whichWay == 2) {
		            		changeListener(comboList, inputA, inputB);
		            	}
					}
				}				
		);
		comboList.setBounds(10, 45, 300, 48);
		converterPanel.add(comboList);
		
		inputA = new JTextField(20);
		inputA.setBounds(10,165,114,45);
		converterPanel.add(inputA);
		inputA.setText(""); 
		inputA.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            	savedTextLabel.setVisible(false);
            	whichWay = 1;
            	inputA.selectAll();
            	inputA.getDocument().addDocumentListener(docListener = new DocumentListener() {
	            		public void insertUpdate(DocumentEvent e) {
	            			changeListener(comboList, inputB, inputA);
	            			savedTextLabel.setVisible(false);
							addToHistory.setEnabled(!inputA.getText().isEmpty() && saveEnabled);
	    				}
	    				public void removeUpdate(DocumentEvent e) {
	    					changeListener(comboList, inputB, inputA);
	    					savedTextLabel.setVisible(false);
							addToHistory.setEnabled(!inputA.getText().isEmpty() && saveEnabled);
	    				}
	    				public void changedUpdate(DocumentEvent e) {
	    					changeListener(comboList, inputB, inputA);
	    					savedTextLabel.setVisible(false);
							addToHistory.setEnabled(!inputA.getText().isEmpty() && saveEnabled);
	    				}
    			});
            	inputA.addKeyListener(keyListener = new KeyListener() {
					public void keyTyped(KeyEvent e) {					
					}
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode() == KeyEvent.VK_ENTER){
	    					if(!inputA.getText().isEmpty() && saveEnabled) {
	    						if(!inputA.getText().isEmpty()) {
	    								savedTextLabel.setVisible(true);
		            					inputsArray.add(inputA.getText());
		            					outputsArray.add(inputB.getText());
		            					inConvertingLables.add(comboList.getSelectedItem().toString());
		            					history.setEnabled(true);
		            					history2.setEnabled(true);
		            					history3.setEnabled(true);
		            					clearHistory.setEnabled(true);
				            			clearHistory2.setEnabled(true);
				            			clearHistory3.setEnabled(true);
		            				}
		            			}
	    					}
	    					else addToHistory.setEnabled(false);
						}
					public void keyReleased(KeyEvent e) {
					}
            	});
            }
            public void focusLost(FocusEvent e) {
            	inputA.getDocument().removeDocumentListener(docListener);
            	inputA.removeKeyListener(keyListener);
            }
        });
		
		inputA.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
            	if(Character.isDigit(e.getKeyChar()) || e.getKeyChar() == '.') {
            		e.setKeyChar(e.getKeyChar());
                }else if(e.getKeyChar() == ',') {
                	e.setKeyChar('.');
                }else {
                	e.consume();
                }
                
            }
		});
		
		inputB = new JTextField(20);
		inputB.setBounds(258,165,114,45);
		converterPanel.add(inputB);
		inputB.setText(""); 
		
		inputB.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            	savedTextLabel.setVisible(false);
            	whichWay = 2;
            	inputB.getDocument().addDocumentListener(docListener = new DocumentListener() {
					public void insertUpdate(DocumentEvent e) {
						savedTextLabel.setVisible(false);
						changeListener(comboList, inputA, inputB);
						addToHistory.setEnabled(!inputB.getText().isEmpty() && saveEnabled);
					}
					public void removeUpdate(DocumentEvent e) {
						savedTextLabel.setVisible(false);
						changeListener(comboList, inputA, inputB);
						addToHistory.setEnabled(!inputB.getText().isEmpty() && saveEnabled);
					}
					public void changedUpdate(DocumentEvent e) {
						savedTextLabel.setVisible(false);
						changeListener(comboList, inputA, inputB);
						addToHistory.setEnabled(!inputB.getText().isEmpty() && saveEnabled);
					}
				});
            	inputB.selectAll();
            	
            	inputB.addKeyListener(keyListener = new KeyListener() {
					public void keyTyped(KeyEvent e) {					
					}
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode() == KeyEvent.VK_ENTER){
	    					if(!inputA.getText().isEmpty() && saveEnabled) {
	    						if(!inputB.getText().isEmpty()) {
	    								savedTextLabel.setVisible(true);
		            					String changedWayString = "";	
			            				String[] split = comboList.getSelectedItem().toString().split("\\s+");
			            				changedWayString += split[2] + " ";
			            				changedWayString += split[1] + " ";
			            				changedWayString += split[0];
			            				inConvertingLables.add(changedWayString);
			            				inputsArray.add(inputB.getText());
		            					outputsArray.add(inputA.getText());
		            					history.setEnabled(true);
		            					history2.setEnabled(true);
		            					clearHistory.setEnabled(true);
				            			clearHistory2.setEnabled(true);
				            			clearHistory3.setEnabled(true);
		            			}
	    					}
						}
	    					else addToHistory.setEnabled(false);
					}
					public void keyReleased(KeyEvent e) {
					}
            	});
            }
            public void focusLost(FocusEvent e) {
            	savedTextLabel.setVisible(false);
            	inputB.getDocument().removeDocumentListener(docListener);
            	inputB.removeKeyListener(keyListener);
            }
        });	
		inputB.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
            	if(Character.isDigit(e.getKeyChar()) || e.getKeyChar() == '.') {
            		e.setKeyChar(e.getKeyChar());
                }else if(e.getKeyChar() == ',') {
                	e.setKeyChar('.');
                }else {
                	e.consume();
                }
            }
		});
		
		savedTextLabel = new JLabel(r.getString("GUI.SavedLabel"),SwingConstants.CENTER);
		savedTextLabel.setBorder(BorderFactory.createDashedBorder(Color.black));
		savedTextLabel.setVisible(false);
		savedTextLabel.setOpaque(true);
		savedTextLabel.setBackground(new Color(190,220,190));
		savedTextLabel.setBounds(120,225,150,28);
		converterPanel.add(savedTextLabel);
		
		addToHistory = createHistoryButton(2);
		addToHistory.setBounds(276,223,25,30);
		converterPanel.add(addToHistory);
		addToHistory.setEnabled(false);
		
		converterPanel.setVisible(false);
		mainPanel.add(converterPanel);
		welcomePanel.setLayout(null);
		welcomePanel.setBounds(205,30,395,260);
		JLabel welcomeLabel = new JLabel(r.getString("GUI.30"), SwingConstants.CENTER); 
		welcomeLabel.setBounds(0,0,400,100);
		welcomePanel.add(welcomeLabel);
//	-----------------------------------------	
		
		JLabel selectLang = new JLabel(r.getString("GUI.LANG"), SwingConstants.CENTER);
		selectLang.setFont(selectLang.getFont().deriveFont(Font.BOLD, 18));
		selectLang.setBounds(0,140,395,26);
		welcomePanel.add(selectLang);
				
		JButton plLang = new JButton(r.getString("GUI.LANG1"));
		plLang.setBounds(20,175, 170, 40);
		plLang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
					Locale l = new Locale("pl", "PL");
					GUI reloadedLangGui = new GUI(l, inputsArray, outputsArray, inConvertingLables);
					reloadedLangGui.showGUI();
					for(Window window : Window.getWindows()){ 
						window.dispose();
					}		
			}
		});
		
		welcomePanel.add(plLang);
		
		JButton enLang = new JButton(r.getString("GUI.LANG2"));
		enLang.setBounds(205,175, 170, 40);
		welcomePanel.add(enLang);
		enLang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale l = new Locale("en", "EN");
				GUI reloadedLangGui = new GUI(l, inputsArray, outputsArray, inConvertingLables);
				reloadedLangGui.showGUI();
				for(Window window : Window.getWindows()){ 
					window.dispose();
				} 
			}
		});
		if(r.getLocale().getLanguage() == "pl") {
			plLang.setEnabled(false);
			enLang.setEnabled(true);
		}else if(r.getLocale().getLanguage() == "en") {
			plLang.setEnabled(true);
			enLang.setEnabled(false);
		}
		mainPanel.add(welcomePanel);
		

//---------------------------fromFilePanel-------------------------------------------------------------
		
		fromFilePanel.setLayout(null);
		fromFilePanel.setVisible(false);
		fromFilePanel.setBounds(205,30,395,265);
		
		JLabel fromFileLabel = new JLabel(r.getString("GUI.31"), SwingConstants.CENTER); 
		fromFileLabel.setFont(fromFileLabel.getFont().deriveFont(Font.BOLD | Font.ITALIC, 18));		
		fromFileLabel.setBounds(41,15,293,25);
		fromFilePanel.add(fromFileLabel);
		
		popUpInstructions = new JFrame();
		
		
/*
 * instruction pop up a frame with help dialog. It was build with html markers		
 */
		JButton instructions = new JButton("?"); 
		instructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog(popUpInstructions, "");
				dialog.setLayout(new GridBagLayout());
				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.HORIZONTAL;
				String helpText = 
						r.getString("GUI.34") 
						+ r.getString("GUI.35") 
						+ r.getString("GUI.36") 
						+ r.getString("GUI.37") 
						+ r.getString("GUI.38") 
						+ r.getString("GUI.39") 
						+ r.getString("GUI.40") 
						+ r.getString("GUI.41") 
						+ r.getString("GUI.42") 
						+ r.getString("GUI.43") 
						+ r.getString("GUI.44") 
						+ r.getString("GUI.45"); 
				helpDialog = new JTextPane();
				helpDialog.setContentType("text/html"); 
				helpDialog.setText(helpText);
				helpDialog.setEditable(false);
				helpDialog.setBackground(null);
			
				JButton exitHelp = new JButton("OK"); 
				exitHelp.setPreferredSize(new Dimension(100,100));
				exitHelp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dialog.dispose();				
					}
					
				});
				helpDialog.setSize(new Dimension(350,500));
				helpDialog.setPreferredSize(new Dimension(350, 500));
				c.gridx = 0;
				c.gridy = 0;
				c.weightx = 1;
				c.weighty = 1;
				c.insets= new Insets(5,0,0,0);
				dialog.add(helpDialog, c);
				
				c.weighty = 1;
				c.fill = GridBagConstraints.BOTH;
				c.ipady = 0;
				c.insets = new Insets(10,0,0,0);
				c.gridx = 0;
				c.gridy = 1;
				exitHelp.setSize(new Dimension(100, 100));
				exitHelp.setPreferredSize(new Dimension(100, 100));
				dialog.add(exitHelp, c);
				dialog.setVisible(true);
				dialog.setResizable(false);
				dialog.pack();
				dialog.setBounds((screenWidth-400)/2,(screenHeight-500)/2,350,(helpDialog.getSize().height + exitHelp.getSize().height)-10);
				
			}
			
		});
		instructions.setFont(instructions.getFont().deriveFont(Font.BOLD, 12));
		instructions.setBounds(334, 15, 41, 25);
		fromFilePanel.add(instructions);

/*---------------------------optionsGroup-------------------------------------------------------------
 * 				Uses radioButtonCreator() to create 6 avaible option
 * 				of conversion. Adding it to optionGroup
 */
		optionsGroup = new ButtonGroup();
		option1 = radioButtonCreator("Option 1", 1, 0); 
		option1.setLocation(30,140);
		option1.setSelected(true);
		fromFilePanel.add(option1);
		
		option2 = radioButtonCreator("Option 2", 2, 0); 
		option2.setLocation(227,140);
		fromFilePanel.add(option2);
		
		option3 = radioButtonCreator("Option 3", 1, 1); 
		option3.setLocation(30,166);
		fromFilePanel.add(option3);
		
		option4 = radioButtonCreator("Option 4", 2, 1); 
		option4.setLocation(227,166);
		fromFilePanel.add(option4);
		
		option5 = radioButtonCreator("Option 5", 1, 2); 
		option5.setLocation(30,192);
		fromFilePanel.add(option5);
		
		option6 = radioButtonCreator("Option 6", 2, 2); 
		option6.setLocation(227,192);
		fromFilePanel.add(option6);
		
		optionsGroup.add(option1);
		optionsGroup.add(option2);
		optionsGroup.add(option3);
		optionsGroup.add(option4);
		optionsGroup.add(option5);
		optionsGroup.add(option6);
				
/*-------------------------------converterNameBox-------------------------------------------------------
			converterNameBox is list with avaible converters
			It build a list with specific converter and avaible options
*/
		JComboBox converterNameBox= new JComboBox(converterName);
		converterNameBox.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int converterIndex = converterNameBox.getSelectedIndex();
						choosenConverter = converterIndex;
						if(converterIndex == 0) {
							optionChanger(areaString);
						}
						if(converterIndex == 1) {
							optionChanger(weightString);
						}
						if(converterIndex == 2) {
							optionChanger(currencyString);
						}
					}
				}				
		);
/*---------------------------selectFileButton---------------------------------------------------------
				selectFileButton opens a file chooser that provides
				to select .txt file. File chooser starts at home dir
				of current program
*/
		converterNameBox.setBounds(10, 50, 177, 35);
		fromFilePanel.add(converterNameBox);
		
		JButton selectFileButton = new JButton(r.getString("GUI.54")); 
		selectFileButton.setBounds(197, 50, 177, 35);
		
		fromFilePanel.add(selectFileButton);
		
		JTextField filePathLabel = new JTextField();
		filePathLabel.setBounds(10,90,365,20);
		filePathLabel.setText(r.getString("GUI.55")); 
		filePathLabel.setHorizontalAlignment(SwingConstants.CENTER);
		filePathLabel.setBackground(new Color(204,255,255));
		filePathLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		filePathLabel.setEditable(false);
		filePathLabel.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		fromFilePanel.add(filePathLabel);
		
		selectFileButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFileChooser jfc = new JFileChooser(System.getProperty("user.dir")); 
						FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILE", "txt", "text");   //$NON-NLS-3$
						jfc.addChoosableFileFilter(filter);
						jfc.setAcceptAllFileFilterUsed(false);		
							if(jfc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
								file = jfc.getSelectedFile();
								if(file.length() != 0) {							
									String correctFileExtension = ".txt"; 
									String fileExtension = file.getName().substring(file.getName().lastIndexOf("."));
									if(fileExtension.equals(correctFileExtension)) {
										filePathLabel.setText(file.getPath()); 
										filePathLabel.setBackground(new Color(204,255,204));
										convertButton.setEnabled(true);									
									}else {
										filePathLabel.setText(r.getString("GUI.63")); 
										filePathLabel.setBackground(new Color(255,204,204));
										convertButton.setEnabled(false);
									}
								}else {
									JOptionPane.showMessageDialog(fromFilePanel,r.getString("GUI.64"));
									file = null;
									filePathLabel.setText(r.getString("GUI.55"));
									convertButton.setEnabled(false);
								}
							}else {
								System.out.println(r.getString("GUI.65")); 
							}
					}
				}				
		);
/*---------------------------ConvertButton---------------------------------------------------------
			When user met the conditions to convert (file is txt fiile) convertButton
			becomes enabled. This button call convertFromFile() method of class ConversionFromFile()
*/		
		history = createHistoryButton(0);
		history.setBounds(310,223,25,30);

		history2 = createHistoryButton(0);
		history2.setBounds(310,223,25,30);
		
		history3 = createHistoryButton(0);
		history3.setBounds(310,223,25,30);
		
		clearHistory = createHistoryButton(1);
		clearHistory.setBounds(340,223,25,30);
		
		clearHistory2 = createHistoryButton(1);
		clearHistory2.setBounds(340,223,25,30);
		
		clearHistory3 = createHistoryButton(1);
		clearHistory3.setBounds(340,223,25,30);
		
		convertButton = new JButton(r.getString("GUI.66")); 
		convertButton.setBounds(10,220, 290, 35);
		convertButton.setEnabled(false);
		convertButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						history.setEnabled(true);
						history2.setEnabled(true);
						history3.setEnabled(true);
						clearHistory.setEnabled(true);
						clearHistory2.setEnabled(true);
						clearHistory3.setEnabled(true);
						convertFromFile = new ConversionFromFile();
						convertFromFile.convert(file, choosenConverter, whichWay, converterOptionID, optionsGroup.getSelection().getActionCommand(), currency, r.getLocale());
						if(convertFromFile != null) {
							ArrayList<String> inputs = convertFromFile.getInputArray();
		        			ArrayList<String> outputs = convertFromFile.getResultArrayArray();
		        			ArrayList<String> convertingLables = convertFromFile.getInConvertingLables();
		        			for (int i = 0 ; i < inputs.size(); i++) {
		        				inputsArray.add(inputs.get(i));
		        				outputsArray.add(outputs.get(i));
		        				inConvertingLables.add(convertingLables.get(i));
		        			}
		        			convertFromFile = null;
						}
				}
		});
		fromFilePanel.add(convertButton);
		
		fromFilePanel.add(history);
		converterPanel.add(history2);
		welcomePanel.add(history3);
		
		fromFilePanel.add(clearHistory);
		converterPanel.add(clearHistory2);
		welcomePanel.add(clearHistory3);
		
		mainPanel.add(fromFilePanel);
//---------------------------describe------------------------------------------------------------------
		
	JLabel description2Label = new JLabel(r.getString("GUI.67"), SwingConstants.CENTER); 
	description2Label.setBounds(10,113,365,20);
	description2Label.setFont(description2Label.getFont().deriveFont(Font.BOLD, 16));
	fromFilePanel.add(description2Label);
		
	
		
		
	/*
	 * SETTINGS OF MENU PANEL
	 * <hr>
	 * menuPanel is shown first to the user
	 * menuPanel includes buttons reffering to converterPanel with the appropriate parameters
	 * <ul>
	 * 	<li>setting converterPanel as leading content
	 * 	<li>@param chosenConverter - defines which converter has been selected
	 * 	<li>rebuilding list of used items in comboList using one from areaString, weightString or currencyString
	 * </ul>
	 */
		menuPanel.setLayout(null);
		menuPanel.setBounds(0,30,205,260);
		 
		JLabel startButton = createButton(areaString, animatedImg, 4, "START", welcomePanel, converterPanel);
		startButton.setBackground(new Color(160,200,160));
		startButton.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
            		startButton.setBackground(new Color(160,200,160));
            }
 
            @Override
            public void mouseEntered(MouseEvent e) {
            		startButton.setBackground(new Color(180,220,180));
            }
		});
		
		startButton.setLocation(0,0);
		menuPanel.add(startButton);
		
		JLabel areaButton = createButton(areaString, animatedImg, 0, r.getString("GUI.69"), converterPanel, welcomePanel); 
		areaButton.setLocation(0,52);
		menuPanel.add(areaButton);
		areaButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { 
            	if(!animateAreaImage.isRunning()) animateAreaImage.start();
            	if(animateCurrencyImage.isRunning()) animateCurrencyImage.stop();
            	if(animateWeightImage.isRunning()) animateWeightImage.stop();
            }
		});
		
		JLabel weightButton = createButton(weightString, animatedImg, 1, r.getString("GUI.70"), converterPanel, welcomePanel); 
		weightButton.setLocation(0,104);
		menuPanel.add(weightButton);
		
		weightButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { 
            	if(animateAreaImage.isRunning()) animateAreaImage.stop();
            	if(animateCurrencyImage.isRunning()) animateCurrencyImage.stop();
            	if(!animateWeightImage.isRunning()) animateWeightImage.start();
            }
		});

		JLabel currencyButton = createButton(currencyString, animatedImg, 2, r.getString("GUI.71"), converterPanel, welcomePanel); 
		currencyButton.setLocation(0,156);
		menuPanel.add(currencyButton);
		
		currencyButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { 
            	if(animateAreaImage.isRunning()) animateAreaImage.stop();
            	if(!animateCurrencyImage.isRunning()) animateCurrencyImage.start();
            	if(animateWeightImage.isRunning()) animateWeightImage.stop();
            }
		});
		
		JLabel fromFileButton = createButton(currencyString, animatedImg, 3, r.getString("GUI.72"), welcomePanel, converterPanel); 
		fromFileButton.setLocation(0,208);
		menuPanel.add(fromFileButton);
		
		mainPanel.add(menuPanel);
		setBounds((screenWidth-600)/2,(screenHeight-200)/2,600,320);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(r.getString("GUI.73")); 	 
		add(mainPanel);
		setResizable(false);
		
	}

/*
 * createButton() makes a JLabel that imitate a Hyperlink (Java Swing does not support hyperlinks)
 * Button is managed by arguments relating to chosen converter and chosen converting option
 */
	private JLabel createButton(String[][] itemList, JLabel imgAnimatedIcon, int converterID, String labelText, JPanel visiblePanel, JPanel invisiblePanel) {	
		JLabel hyperlinkButton = new JLabel(labelText, SwingConstants.CENTER);
		hyperlinkButton.setOpaque(true);
		hyperlinkButton.setBackground(new Color(200,200,220));
		Border border = BorderFactory.createSoftBevelBorder(SoftBevelBorder.RAISED,new Color(168,168,168),new Color(67,67,67));	
		hyperlinkButton.setBorder(border);
		hyperlinkButton.setSize(205,52);
		hyperlinkButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		animateAreaImage = new AnimateLabel(animatedImg, 0);
		animateWeightImage = new AnimateLabel(animatedImg, 1);
		animateCurrencyImage = new AnimateLabel(animatedImg, 2);
		if(converterID == 2) {
			try {
				getCurrency("EURPLN"); 
				getCurrency("EURUSD"); 
				getCurrency("USDPLN"); 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		hyperlinkButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {            	
            	inputA.setText(""); 
				inputB.setText(""); 
				converterId = converterID;
            	visiblePanel.setVisible(true);
            	invisiblePanel.setVisible(false);
            	if(converterID !=3 && converterID !=4) {
					comboList.removeAllItems();
					for(int i = 0; i < itemList.length; i++) {
						String[] someText = new String[2];
						for(int j = 0 ; j < itemList[i].length; j++) {
							someText[j] = itemList[i][j];
						}
						comboList.insertItemAt(someText[0] +r.getString("GUI.76")  + someText[1], i); 
					}
					comboList.setSelectedIndex(0);
					choosenConverter = converterID;
	            	fromFilePanel.setVisible(false);
            	} else if (converterID == 3) {
            		invisiblePanel.setVisible(false);
            		visiblePanel.setVisible(false);
            		fromFilePanel.setVisible(true);
            		choosenConverter = 0;
            		whichWay = 1;
            		optionChanger(areaString);
					
            	}else if (converterID == 4) {
            		hyperlinkButton.setBackground(new Color(160,200,160));
            		fromFilePanel.setVisible(false);
            	}
            }
 
            @Override
            public void mouseExited(MouseEvent e) {
            		hyperlinkButton.setBackground(new Color(200,200,220));
            }
 
			@Override
            public void mouseEntered(MouseEvent e) {
            		hyperlinkButton.setBackground(new Color(220,220,255));
			}
 
        });
		return hyperlinkButton;
	}
/*
 * radioButtonCreator() is a method  that create and formats radioButton
 */
	private JRadioButton radioButtonCreator(String name, int conversionWay, int optionID) {
		JRadioButton radioButton;
		radioButton = new JRadioButton("Option 1"); 
		radioButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		radioButton.setFont(radioButton.getFont().deriveFont(Font.ITALIC, 15));
		radioButton.setSize(157,16);
		radioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				whichWay = conversionWay;
				converterOptionID = optionID;
			}
		});
	return radioButton;
	}
	
	/*
	 * changeListener()
	 * <hr>
	 * <ul>
	 * 	<li>creates Menu object 
	 * 	<li>catches Exceptions - in this case it's entering the value which is not a number or is devided with a comma instead of dot
	 * 	<li>receives result and replace comma on dot in received value
	 *	<li>setting result in appropriate window
	 * </ul> 
	 */
	void changeListener(JComboBox comboBox, JTextField textField, JTextField selectedInput) {
		Menu menu = new Menu();
		try {
			if(selectedInput.getText().isEmpty()) {
				textField.setText(""); 
			}else if(Double.parseDouble(selectedInput.getText()) < 0){
				warningText.setText(r.getString("GUI.79")); 
				warningText.setBackground(Color.yellow);
				warningText.setForeground(Color.red);
				saveEnabled = false;
			}else {
				warningText.setText(r.getString("GUI.80")); 
				warningText.setBackground(Color.green);
				saveEnabled = true;
				String resultText = menu.menu(comboBox.getSelectedIndex(), Double.parseDouble(selectedInput.getText()), choosenConverter, whichWay, currency);
				resultText = resultText.replaceAll(",",".");  
				textField.setText(resultText);
				warningText.setForeground(Color.black);
			}
		}catch(NumberFormatException e) {
				warningText.setText(r.getString("GUI.83"));
				saveEnabled = false;
				textField.setText("");
				addToHistory.setEnabled(false);
				warningText.setBackground(Color.yellow);
				warningText.setForeground(Color.red);
		}
	}
	
	void optionChanger(String[][] optionString) {
		option1String = optionString[0][0] + r.getString("GUI.84") + optionString[0][1]; 
		option1.setText(option1String);
		option1.setActionCommand(option1String);
		
		option2String =optionString[0][1] + r.getString("GUI.85") + optionString[1][0]; 
		option2.setText(option2String);
		option2.setActionCommand(option2String);
		
		option3String = optionString[1][0] + r.getString("GUI.86") + optionString[1][1]; 
		option3.setText(option3String);
		option3.setActionCommand(option3String);
		
		option4String = optionString[1][1] + r.getString("GUI.87") + optionString[1][0]; 
		option4.setText(option4String);
		option4.setActionCommand(option4String);
		
		option5String = optionString[2][0] + r.getString("GUI.88") + optionString[2][1]; 
		option5.setText(option5String);
		option5.setActionCommand(option5String);
		
		option6String = optionString[2][1] + r.getString("GUI.89") + optionString[2][0]; 
		option6.setActionCommand(option6String);
		option6.setText(option6String);
	}

	void getCurrency(String currencyInfo){
		URL url = null;
		try {
			url = new URL("https://www.bankier.pl/waluty/kursy-walut/forex/" +currencyInfo); 
		} catch (MalformedURLException e1) {
			System.out.println(r.getString("GUI.91")); 
		}
		BufferedReader reader = null;
		String values = null;
		try {
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line = null;
			while((line = reader.readLine())!= null) {
				String startingText = "<div class=\"profilLast\">"; 
				String endingText = "</div>"; 
				int startIndex = line.indexOf(startingText);
				int end = line.indexOf(endingText);			
				if (startIndex != -1) {
                   values = line.substring(startIndex + startingText.length(), end);
                }
			}
			values = values.replaceAll(",", ".");  
			if(currencyInfo.equals("EURPLN")) 
				currency[0] = Double.parseDouble(values);
			else if(currencyInfo.equals("EURUSD")) 
				currency[1] = Double.parseDouble(values);
			else if(currencyInfo.equals("USDPLN")) 
				currency[2] = Double.parseDouble(values);
			
		}catch(Exception e) {
			if(currencyInfo.equals("EURPLN")) 
				currency[0] = 4.54115;
			else if(currencyInfo.equals("EURUSD")) 
				currency[1] = 4.24405;
			else if(currencyInfo.equals("USDPLN")) 
				currency[2] = 1.07;
		}finally {
            if (reader != null) {
                try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
		}
	}
	ImageIcon scaleImage(ImageIcon imgToScale, int x, int y) {
		Image img = imgToScale.getImage();
		Image imgScale = img.getScaledInstance(x, y, Image.SCALE_SMOOTH);
		ImageIcon scaledIMG = new ImageIcon(imgScale);
		return scaledIMG;
	}
	
	JLabel createHistoryButton(int taskID) {
		JLabel historyButton = new JLabel();
		historyButton.setIcon(scaleImage(new ImageIcon("images/history" +taskID +".png"),25,30));
		historyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		if(inputsArray.isEmpty()) {
			historyButton.setEnabled(false);
		}
		historyButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if(historyButton.isEnabled()) {
	            	if(inputsArray != null) {						
	            		if(taskID == 0) {
	            			TableOfConversion tableOfConversion = new TableOfConversion(inputsArray, outputsArray, inConvertingLables, r.getLocale());
	            			tableOfConversion.showGUI();
	            		}else if(taskID == 1) {
	            			int dialogButton = JOptionPane.YES_NO_OPTION;
							int dialogResult = JOptionPane.showConfirmDialog (null, r.getString("GUI.Dialog"),r.getString("TableOfConversion.WarningMsg"),dialogButton);
							if(dialogResult == JOptionPane.YES_OPTION) {
								historyButton.setIcon(scaleImage(new ImageIcon("images/history" +taskID +".png"),25,30));
								inputsArray.clear();
		            			outputsArray.clear();
		            			inConvertingLables.clear();
		            			history.setEnabled(false);
		            			history2.setEnabled(false);
		            			history3.setEnabled(false);
		            			clearHistory.setEnabled(false);
		            			clearHistory2.setEnabled(false);
		            			clearHistory3.setEnabled(false);
							}
	            		}else if(taskID == 2) {
	            			if(!inputA.getText().isEmpty()) {
	            				savedTextLabel.setVisible(true);
	            				if(whichWay == 1) {
	            					inputsArray.add(inputA.getText());
	            					outputsArray.add(inputB.getText());
	            					inConvertingLables.add(comboList.getSelectedItem().toString());
	            					history.setEnabled(true);
	            					history2.setEnabled(true);
	            					history3.setEnabled(true);
	            					clearHistory.setEnabled(true);
			            			clearHistory2.setEnabled(true);
			            			clearHistory3.setEnabled(true);
	            				}else if (whichWay == 2) {
	            					String changedWayString = "";	
		            				String[] split = comboList.getSelectedItem().toString().split("\\s+");
		            				changedWayString += split[2] + " ";
		            				changedWayString += split[1] + " ";
		            				changedWayString += split[0];
		            				inConvertingLables.add(changedWayString);
		            				inputsArray.add(inputB.getText());
	            					outputsArray.add(inputA.getText());
	            					history.setEnabled(true);
	            					history2.setEnabled(true);
	            					clearHistory.setEnabled(true);
			            			clearHistory2.setEnabled(true);
			            			clearHistory3.setEnabled(true);
	            				}
	            			}
	            		}
	            	}	
            	}
            }
            public void mouseExited(MouseEvent e) {
            	if(historyButton.isEnabled()) {
            		historyButton.setIcon(scaleImage(new ImageIcon("images/history" +taskID +".png"),25,30));
            	}
            }
 
			@Override
            public void mouseEntered(MouseEvent e) {
				if(historyButton.isEnabled()) {
					historyButton.setIcon(scaleImage(new ImageIcon("images/history" +taskID +"w.png"),25,30));    		
				}
			}
        });
		return historyButton;
	}
	/*
	 * showGUI() is method used to set our GUI visible
	 */
	
	void showGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
					setVisible(true);
			}
		});
	}
}  

