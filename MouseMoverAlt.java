package Data;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class MouseMoverAlt extends JFrame implements ActionListener
{
	private JButton startButton;
	private volatile boolean start = false;
	private Robot mousie;
	
	public MouseMoverAlt(){
		new JFrame("MouseMoverAlt");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(120, 80));
		startButton = new JButton();
		startButton.setText("Start");
		startButton.addActionListener(this);
		this.add(startButton);
		this.pack();
		this.setVisible(true);
	}
	public static void main(String[] args) throws AWTException{
		new MouseMoverAlt();
	}
	public void actionPerformed(ActionEvent e){
		Runnable runnable = new Runnable(){
			public void run(){
				try{mousie = new Robot();} catch(AWTException a){}
				int modifier = 1;
				while(start){
					mousie.delay(50);
					mousie.mouseMove(MouseInfo.getPointerInfo().getLocation().x + modifier, MouseInfo.getPointerInfo().getLocation().y + modifier);
					modifier *= -1;
				}
			}
		};
		Thread thread = new Thread(runnable);
		if(start == false){
			start = true;
			startButton.setText("Stop");
			thread.start();
		}
		else if(start == true){
			start = false;
			startButton.setText("Start");
		}
	}
}