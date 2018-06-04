/**
* TP Week2
*Author: Joao Silva
*Date creation : 1 juin 2018
*/
package smartphone;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Pendu extends JPanel{

	private ImageIcon penduIcon = new ImageIcon("image/icon/galerie.png");
	private ImageIcon penduIconHover = new ImageIcon("image/icon/galerieHOVER.png");
	
	
	public Icon getPenduIconHover() {
		return penduIconHover;
	}
	
	public Icon getPenduIcon() {
		return penduIcon;
	}
	
}
