/**
* --------------------------------------------------------------------------<br/>
* Classe : SouthPanel <br/>
* --------------------------------------------------------------------------<br/>
* Auteur: Joao Silva <br/>
* Description : Barre noire en bas du natel <br/>
* --------------------------------------------------------------------------<br/>
*/
package GUI.composants;

import java.awt.*;
import java.util.Calendar;
import javax.swing.*;

public class SouthPanel extends JPanel
{
	public SouthPanel() 
	{
		this.setSize(700, 30);
		this.setLayout(new GridLayout(1,3));
		this.setBackground(Color.BLACK);
		this.add(new JLabel(" "));
	}
}
