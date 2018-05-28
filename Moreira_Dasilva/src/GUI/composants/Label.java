/**
* TP Week2
*Author: Joao Silva
*Date creation : 20 avr. 2018
*/
package GUI.composants;

import java.awt.Color;
import javax.swing.JLabel;

public class Label extends JLabel{

	public Label(String string, Color couleur, int position) {
		this.setText(string);
		this.setForeground(couleur);
		this.setHorizontalAlignment(position);
	}
	
}
