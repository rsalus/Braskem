package Data;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CSVConvert extends JPanel implements ActionListener{
	static private final String newLine = "\n";
	JButton openButton;
	JTextArea log;
	JFileChooser fc;
	
	public CSVConvert(){
		super(new BorderLayout());
		
		log = new JTextArea(5, 20);
		log.setMargin(new Insets(5,5,5,5));
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);
		
		fc = new JFileChooser();
		
		URL url = CSVConvert.class.getResource("/resources/Open16.gif");
		ImageIcon icon = new ImageIcon(url);
		
		openButton = new JButton("Open a File...", icon);
		openButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(openButton);
		add(buttonPanel, BorderLayout.PAGE_START);
		add(logScrollPane, BorderLayout.CENTER);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == openButton){
			int returnVal = fc.showOpenDialog(CSVConvert.this);
			
			if(returnVal == JFileChooser.APPROVE_OPTION){
				File file = fc.getSelectedFile();
				log.append("Converting: " + file.getName() + "." + newLine);
				log.append("Exported to Desktop." + newLine);
				dataConversion(file);
			}
			else
				log.append("Open command cancelled by user." + newLine);
			
			log.setCaretPosition(log.getDocument().getLength());
		}
	}
	private void dataConversion(File file){
		ArrayList<String> arr = new ArrayList<String>();
		
		try{
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()){
				String orig = scan.nextLine();
				String[] temp = orig.split("\t");
				ArrayList<String> tempArr = new ArrayList<String>();
				for(int i = 0; i < temp.length; i++){
					tempArr.add(temp[i]);
				}
				String phone = "", email = "", name = null;
				boolean found = false;
				
				for(int i = 0; i < tempArr.size(); i++)
				{
					String tester = tempArr.get(i);
					tester = tester.replaceAll("[^\\d]", "");
					if(tester.length() == 10){
						phone = tempArr.get(i);
					}
					else if(tempArr.get(i).contains("@")){
						if(found == false){
							email = tempArr.get(i);
							name = tempArr.get(i);
							found = true;
						}
					}
				}
				
				if(name != null) {
					name = name.substring(0, name.indexOf("@"));
					name = name.toLowerCase();
					String[] nameArr = name.split("\\.");
					nameArr[0] = nameArr[0].substring(0, 1).toUpperCase() + nameArr[0].substring(1);
					nameArr[1] = nameArr[1].substring(0, 1).toUpperCase() + nameArr[1].substring(1);
					name = nameArr[0] + " " + nameArr[1];
				}
				else{name = "";}
				arr.add(name + ", " + email + ", " + phone);
			}
			scan.close();
		} catch (FileNotFoundException e){}
		try{
			String dir = System.getProperty("user.home");
			dir = dir + "\\Desktop\\" + file.getName().substring(0, file.getName().indexOf(".txt")) +".csv";
			PrintWriter out = new PrintWriter(dir);
			for(int i = 0; i < arr.size(); i++){
				out.println(arr.get(i));
			}
			out.close();
		} catch (FileNotFoundException e){}
	}
	private static void createAndShowGUI(){
		JFrame frame = new JFrame("CSVConvert");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new CSVConvert());
		frame.pack();
		frame.setVisible(true);
	}
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				createAndShowGUI();
			}
		});
	}
}