package Data;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MouseMoverAlt extends JFrame implements ActionListener
{
	private JButton startButton, altButton;
	private volatile boolean start = false;
	private Robot mousie;
	
	public MouseMoverAlt(){
		new JFrame("MouseMoverAlt");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(200, 80));
		startButton = new JButton();
		startButton.setText("Start");
		startButton.addActionListener(this);
		altButton = new JButton();
		altButton.setText("Simulate");
		altButton.addActionListener(this);
		this.setLayout(new GridLayout(0, 2));
		this.add(startButton);
		this.add(altButton);
		this.pack();
		this.setVisible(true);
	}
	public static void main(String[] args) throws AWTException{
		new MouseMoverAlt();
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == startButton){
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
		if(e.getSource() == altButton){
			Runnable runnable = new Runnable(){
				public void run(){
					try{mousie = new Robot();} catch(AWTException a){}
					while(start){
						mousie.delay(50);
						mousie.mousePress(InputEvent.BUTTON1_DOWN_MASK);
					}
				}
			};
			Thread thread = new Thread(runnable);
			if(start == false){
				start = true;
				altButton.setText("Stop");
				thread.start();
			}
			else if(start == true){
				start = false;
				altButton.setText("Simulate");
			}
		}
	}
}
