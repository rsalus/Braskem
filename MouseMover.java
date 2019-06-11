import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import javax.swing.JFrame;

public class MouseMover extends JFrame implements MouseListener
{
	public MouseMover() throws AWTException{
		new JFrame("MouseMover");
        	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	this.setPreferredSize(new Dimension(150, 150));
        	this.addMouseListener(this);
        	this.pack();
        	this.setVisible(true);
        
        	Robot mousie = new Robot();
		Random random = new Random();
		mousie.mouseMove(75, 75);
		while(true){
			mousie.delay(1000);
			mousie.mouseMove(random.nextInt(101) + 30, random.nextInt(101) + 30);
		}
	}
	public static void main(String[] args) throws Exception{
		new MouseMover();
	}
	public void mouseClicked(MouseEvent arg0){}
	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0) {System.exit(0);}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0){}
}
