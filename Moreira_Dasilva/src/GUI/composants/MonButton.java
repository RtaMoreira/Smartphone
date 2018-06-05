/**
* TP Week2
*Author: Joao Silva
*Date creation : 1 juin 2018
*/
package GUI.composants;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class MonButton extends JPanel{

	private JButton b1 = new JButton();
	private ImageIcon im = new ImageIcon();
	private ImageIcon imh = new ImageIcon();
	
	public MonButton(String s){
		b1.setBackground(Color.DARK_GRAY);
		b1.setForeground(Color.WHITE);
		b1.setText(s);
		add(b1);
	}
	
	public MonButton(ImageIcon im, ImageIcon imh){
		this.im=im;
		this.imh=imh;
		setOpaque(false);
		b1.setOpaque(false);
		b1.setContentAreaFilled(false);
		b1.setBorderPainted(false);
		
		b1.setIcon(im);
		add(b1);
		b1.addMouseListener(new MouseHover());
	}
	
	
	public void addActionListener(ActionListener e) {
		b1.addActionListener(e);
	}
	
	class MouseHover implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			b1.setIcon(imh);
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			b1.setIcon(im);
		}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			b1.setIcon(im);
		}
		
	}
}
