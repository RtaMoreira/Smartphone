/**
* --------------------------------------------------------------------------<br/>
* Classe : FlatField <br/>
* --------------------------------------------------------------------------<br/>
* Auteur: Joao Silva <br/>
* Description : Field dans un panel pour faciliter les changements de taille <br/>
* --------------------------------------------------------------------------<br/>
*/

package GUI.composants;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class FlatField extends JPanel{
	
private JTextField t1 = new JTextField();
private JPasswordField p1 = new JPasswordField();
private MouseListener ml = new RemoveError();
	
	public FlatField()
	{
		add(t1);
		t1.setMinimumSize(new Dimension(200,30));
		t1.setMaximumSize(new Dimension(200,30));
		t1.setPreferredSize(new Dimension(200, 30));
		t1.setBorder(null);
		setOpaque(false);
	}
	
	/**field est en passwordField en gris*/
	public FlatField(int i)
	{
		add(p1);
		p1.setMinimumSize(new Dimension(200,30));
		p1.setMaximumSize(new Dimension(200,30));
		p1.setPreferredSize(new Dimension(200, 30));
		p1.setBorder(null);
		p1.setBackground(Color.GRAY);
		setOpaque(false);
	}
	
	public JPasswordField getField() 
	{
		return p1;
	}
	
	public String getText() 
	{
		return t1.getText();
	}
	
	/**method pour setter le textField directement*/
	public void setText(String s) 
	{
		t1.setText(s);
	}
	
	/**method pour setter le textField directement*/
	public void setEditable(Boolean bol) 
	{
		t1.setEditable(bol);
		t1.setBackground(Color.WHITE);
	}
	
	/**method pour setter le textField directement*/
	public void setFieldBackground(Color bg) 
	{
		t1.setBackground(bg);
	}
	
	public void error() 
	{
		t1.setBorder(new LineBorder(Color.RED));
		t1.addMouseListener(ml);
	}
	
	class RemoveError implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0) 
		{
			t1.setBorder(null);
			t1.removeMouseListener(ml);
		}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
		
	}
}
