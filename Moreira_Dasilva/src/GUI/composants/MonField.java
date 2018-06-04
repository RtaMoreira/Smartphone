/**
* TP Week2
*Author: Joao Silva
*Date creation : 1 juin 2018
*/
package GUI.composants;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;

public class MonField extends JPanel{
	
private JTextField t1 = new JTextField();
	
	public MonField(){
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
	
	public void setEditable(Boolean bol) {
		t1.setEditable(bol);
		t1.setBackground(Color.WHITE);
	}
}
