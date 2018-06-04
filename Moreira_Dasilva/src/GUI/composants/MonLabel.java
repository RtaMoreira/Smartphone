/**
* TP Week2
*Author: Joao Silva
*Date creation : 1 juin 2018
*/
package GUI.composants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MonLabel extends JPanel{
	
	private JLabel t1 = new JLabel();
	
	public MonLabel(String s){
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
	
	public String getText() {
		return t1.getText();
	}
	
	public void setText(String s) {
		t1.setText(s);
	}
}
