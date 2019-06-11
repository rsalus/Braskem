import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

public class MouseMover extends JFrame implements MouseListener
{
	public MouseMover() throws AWTException{
		new JFrame("MouseMover");
		this.setPreferredSize(new Dimension(20, 20));
		this.addMouseListener(this);
		this.setUndecorated(true);
		this.pack();
		this.setVisible(true);

		Robot mousie = new Robot();
		mousie.mouseMove(10, 10);
		int modifier = 1;
		while(true){
			mousie.delay(50);
			mousie.mouseMove(MouseInfo.getPointerInfo().getLocation().x + modifier, MouseInfo.getPointerInfo().getLocation().y + modifier);
			modifier = modifier*-1;
		}
	}
	public static void main(String[] args) throws AWTException{
		new MouseMover();
	}
	public void mouseClicked(MouseEvent arg0){}
	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0) {System.exit(0);}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0){}
}
