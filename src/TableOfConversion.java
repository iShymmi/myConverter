import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/*
 * TableOfConversion() is class that process reveived values and presents it into Table
 * creates a JFrame which content is Table with 4 cells of Object[] columns
 * 
 */

class TableOfConversion extends JFrame{
	private static final long serialVersionUID = 1L;
	private String[] splitStr = new String[2];
	TableOfConversion(ArrayList<String> inputArray,ArrayList<String> resultArray, ArrayList<String> convertingLables, Locale l){
			ArrayList<String> innerInputArray = inputArray;
			ArrayList<String> innerResultArray = resultArray;
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			
			ResourceBundle r = ResourceBundle.getBundle("messages", l);
			
			int screenHeight = (int) screenSize.getHeight();
			int screenWidth = (int) screenSize.getWidth();
			JTable table = new JTable();
			Object[] columns = {r.getString("TableOfConversion.columns1"),r.getString("TableOfConversion.columns2"),r.getString("TableOfConversion.columns3"),r.getString("TableOfConversion.columns4")};
			DefaultTableModel model = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
				       return false;
				   }
			};
			JScrollPane scrollPane = new JScrollPane(table, 
	                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setPreferredSize(new Dimension(580,230));
			
			table.setCellSelectionEnabled(true);
			table.setRowSelectionAllowed(true);
			model.setColumnIdentifiers(columns);
			table.setModel(model);
			table.setBackground(Color.white);
			for(int i = 0 ; i < innerInputArray.size() ; i++) {
				splitStr = convertingLables.get(i).split("\\s+");
				String convertingFromLabel = splitStr[0];
				String convertingToLabel = splitStr[2];
				Object[] row = new Object[4];
				row [0] = convertingFromLabel;
				row [1] = convertingToLabel;
				row [2] = innerInputArray.get(i);
				row [3] = innerResultArray.get(i);
				model.addRow(row);
			}
			scrollPane.setBounds(5,0,585,220);

			
			JPanel bottomPanel = new JPanel();
			bottomPanel.setLayout(null);
			
			bottomPanel.setBounds(0,220,600,80);
			JButton saveConversion = new JButton(r.getString("TableOfConversion.Export"));
			saveConversion.setBounds(140,10,150,35);
			saveConversion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
					FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILE", "txt", "text");
					fileChooser.addChoosableFileFilter(filter);
					int result = fileChooser.showSaveDialog(null);
					if(result == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						try {
							if(file.length() != 0) {
								int dialogButton = JOptionPane.YES_NO_OPTION;
								int dialogResult = JOptionPane.showConfirmDialog (null, r.getString("TableOfConversion.Dialog"),r.getString("TableOfConversion.WarningMsg"),dialogButton);
								if(dialogResult == JOptionPane.YES_OPTION) {
									FileWriter fileWriter;
									fileWriter = new FileWriter(file.getPath());
									for(int i = 0; i < innerInputArray.size(); i++) {
											String[] splitStr = convertingLables.get(i).split("\\s+");
											String convertingFromLabel = splitStr[0];
											String convertingToLabel = splitStr[2];
										  fileWriter.write(r.getString("TableOfConversion.Write1") + convertingFromLabel +r.getString("TableOfConversion.Write2") +convertingToLabel +" => " +innerInputArray.get(i) +" = " +innerResultArray.get(i) +System.lineSeparator());
										}
									fileWriter.flush();
									fileWriter.close();
									JOptionPane.showMessageDialog(scrollPane,r.getString("TableOfConversion.Succes"));
								}else JOptionPane.showMessageDialog(scrollPane,r.getString("TableOfConversion.Canceled"));
							}
							else {
								FileWriter fileWriter;
								fileWriter = new FileWriter(file.getPath());
								for(int i = 0; i < innerInputArray.size(); i++) {
									String[] splitStr = convertingLables.get(i).split("\\s+");
									String convertingFromLabel = splitStr[0];
									String convertingToLabel = splitStr[2];
									  fileWriter.write(r.getString("TableOfConversion.Write1") + convertingFromLabel +r.getString("TableOfConversion.Write2") +convertingToLabel +" => " +innerInputArray.get(i) +" = " +innerResultArray.get(i) +System.lineSeparator());
									}
								fileWriter.flush();
								fileWriter.close();
								JOptionPane.showMessageDialog(scrollPane,r.getString("TableOfConversion.Succes"));
							}
						} catch(Exception e1) {
							JOptionPane.showMessageDialog(scrollPane,r.getString("TableOfConversion.ErrorMsg"),r.getString("TableOfConversion.Warning"), JOptionPane.WARNING_MESSAGE);
						}
					}
				}				
			});
			bottomPanel.add(saveConversion);
			
			JButton exitButton = new JButton(r.getString("TableOfConversion.ExitBtn"));
			exitButton.setBounds(300,10,150,35);
			bottomPanel.add(exitButton);
			exitButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();		
				}
			});
			
			setTitle(r.getString("TableOfConversion.Title"));
			add(scrollPane);
			setLayout(null);
		    add(bottomPanel);
		    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    setResizable(false);
		    setBounds((screenWidth-600)/2,(screenHeight-200)/2,600,320);
		}
		void showGUI() {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
}
