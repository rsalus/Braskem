package Data;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class InviteExporter extends JPanel implements ActionListener{
	static private final String newLine = "\n";
	private JButton openButton;
	private JTextArea log;
	private JFileChooser fc;
	ArrayList<String> toExport;
	
	public InviteExporter(){
		super(new BorderLayout());
		
		log = new JTextArea(5, 20);
		log.setMargin(new Insets(5,5,5,5));
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);
		
		fc = new JFileChooser();
		
		URL url = InviteExporter.class.getResource("/resources/Open16.gif");
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
			int returnVal = fc.showOpenDialog(InviteExporter.this);
			
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
		ArrayList<String> data = new ArrayList<String>();
		toExport = new ArrayList<String>();
		
		try{
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()){
				data.add(scan.nextLine());
			}
			
			for(int i = 0; i < data.size(); i++){
				if(data.get(i).contains("js-data-filter ember-view")){
					String temp = data.get(i).substring(210, data.get(i).length());
					if(temp.substring(0, 1).equals(" ")){
						temp = temp.substring(1, temp.length());
					}
					toExport.add(temp);
				}
				if(data.get(i).contains("js-expected-arrival-time")){
					toExport.set(toExport.size()-1, toExport.get(toExport.size()-1) + ", " + data.get(i+1).replaceAll("\\s",""));
				}
			}
			scan.close();
		} catch(FileNotFoundException e){}
		try{
			String dir = System.getProperty("user.home");
			dir = dir + "\\Desktop\\" + file.getName().substring(0, file.getName().indexOf(".html")) + ".csv";
			PrintWriter out = new PrintWriter(dir);
			for(int i = 0; i < toExport.size(); i++){
				out.println(toExport.get(i));
			}
			out.close();
		} catch(FileNotFoundException e){}
	}
	private static void createAndShowGUI(){
		JFrame frame = new JFrame("InviteExporter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new InviteExporter());
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