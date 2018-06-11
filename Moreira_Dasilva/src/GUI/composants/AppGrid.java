/**
* --------------------------------------------------------------------------<br/>
* Classe : AppGrid <br/>
* --------------------------------------------------------------------------<br/>
* Auteur: Joao Silva <br/>
* Description : Classe gérant la mise en page de icons<br/>
* --------------------------------------------------------------------------<br/>
*/
package GUI.composants;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**grille qui contient les applications*/
public class AppGrid extends JPanel
{
	
	public AppGrid () 
	{
		this.setLayout(new FlowLayout());
		this.setOpaque(false);
		this.setBorder(new EmptyBorder(20, 20, 20, 20));	
	}
	
	public void addIcon(JLabel icon) 
	{
		this.add(icon);
	}
}
