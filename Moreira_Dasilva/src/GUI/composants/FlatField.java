/**
* FlatField
*Author: Joao Silva
*Date creation : 1 juin 2018
*/
package GUI.composants;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;

public class FlatField extends JPanel{
	
private JTextField t1 = new JTextField();
private JPasswordField p1 = new JPasswordField();
	
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
}
