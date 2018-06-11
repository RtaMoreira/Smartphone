/**
* --------------------------------------------------------------------------<br/>
* Classe : FlatLabel <br/>
* --------------------------------------------------------------------------<br/>
* Auteur: Joao Silva <br/>
* Description : Label dans un panel pour faciliter les changements de taille <br/>
* --------------------------------------------------------------------------<br/>
*/
package GUI.composants;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class FlatLabel extends JPanel
{
	
	private JLabel t1 = new JLabel();
	
	public FlatLabel(String s)
	{
		setBorder(new EmptyBorder(0, 100, 0, 0));
		t1.setText(s);
		add(t1,BorderLayout.CENTER);
		add(t1);
		t1.setMinimumSize(new Dimension(200,30));
		t1.setMaximumSize(new Dimension(200,30));
		t1.setPreferredSize(new Dimension(200, 30));
		t1.setBorder(null);
		setOpaque(false);
	}
	
	public String getText() 
	{
		return t1.getText();
	}
	
	public void setText(String s)
	{
		t1.setText(s);
	}
}