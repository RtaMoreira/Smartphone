/**
* TP Week2
*Author: Joao Silva
*Date creation : 21 avr. 2018
*/
package GUI.composants;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AppGrid extends JPanel{
	
	public AppGrid () {
//		this.setLayout(new GridLayout(6,4));
		this.setLayout(new FlowLayout());
		this.setOpaque(false);
		this.setBorder(new EmptyBorder(20, 20, 20, 20));
		
		
	}
	
	public void addIcon(JLabel icon) {
		this.add(icon);
	}
 


}
