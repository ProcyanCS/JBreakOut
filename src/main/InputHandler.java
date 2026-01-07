package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputHandler implements MouseListener {

	public boolean upPressed, downPressed, rightPressed, leftPressed;
	public boolean click;
	public int x, y;
	
	public void mouseClicked(MouseEvent e) {
        //System.out.println("Mouse clicked at: " + e.getPoint());
		x = e.getPoint().x;
		y = e.getPoint().y;
        click = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //System.out.println("Mouse pressed at: " + e.getPoint());
		x = e.getPoint().x;
		y = e.getPoint().y;
        click = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //System.out.println("Mouse released");
		//x = e.getPoint().x;
		//y = e.getPoint().y;
        //click = true;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //System.out.println("Mouse entered panel");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //System.out.println("Mouse exited panel");
    }

	
	
	

}
