/**
* TP Week2
*Author: Joao Silva
*Date creation : 20 avr. 2018
*/
package GUI.composants;

import java.awt.*;
import java.util.Calendar;
import javax.swing.*;

public class SouthPanel extends JPanel{
	
	public SouthPanel() {
		this.setSize(700, 30);
		this.setLayout(new GridLayout(1,3));

		this.setBackground(Color.BLACK);
//		setOpaque(false);
		
		this.add(new Label("screens", Color.WHITE, JLabel.CENTER));
		this.add(new Label("menu", Color.WHITE, JLabel.CENTER));
		this.add(new Label("back", Color.WHITE, JLabel.CENTER));
	}

}
