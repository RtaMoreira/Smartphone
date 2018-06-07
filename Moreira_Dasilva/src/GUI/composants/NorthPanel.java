/**
* NorthPanel
*Author: Joao Silva
*Date creation : 20 avr. 2018
*panel avec l'heure an haut du natel
*/
package GUI.composants;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.*;
import java.util.Calendar;

import javax.swing.*;

public class NorthPanel extends JPanel{
	
	DateFormat hhmm = new SimpleDateFormat("HH:mm:ss");
	JLabel time = new JLabel();
	Timer timer = new Timer(0,new CurrentTime());
	
	public NorthPanel() 
	{
		this.setSize(700, 30);
		this.setBackground(Color.BLACK);
		time.setForeground(Color.WHITE);
		
		timer.start();
		this.add(time);
	}
	
	class CurrentTime implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Calendar cal = Calendar.getInstance();
			time.setText(hhmm.format(cal.getTime()));
		}
	}
}
