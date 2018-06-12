/**
* --------------------------------------------------------------------------<br/>
* Classe : FlatButton <br/>
* --------------------------------------------------------------------------<br/>
* Auteur: Joao Silva <br/>
* Description : button dans un panel pour faciliter les tailles <br/>
* --------------------------------------------------------------------------<br/>
*/

package GUI.composants;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class FlatButton extends JPanel
{
	private JButton b1 = new JButton();
	private ImageIcon im = new ImageIcon();
	private ImageIcon imh = new ImageIcon();
	
	/**pour les bouttons avec un string
	 * @param s: string qu'on insere dans le button
	 */
	public FlatButton(String s)
	{
		b1.setBackground(Color.DARK_GRAY);
		b1.setForeground(Color.WHITE);
		b1.setText(s);
		add(b1);
	}
	
	/**pour les boutons avec un icon
	 * @param im: icon pour le bouton
	 * @param imh: icon pour le boutom Hover
	 */
	public FlatButton(ImageIcon im, ImageIcon imh)
	{
		this.im=im;
		this.imh=imh;
		setOpaque(false);
		b1.setOpaque(false);
		b1.setContentAreaFilled(false);
		b1.setBorderPainted(false);
		
		b1.setIcon(im);
		add(b1);
		b1.addMouseListener(new MouseHover());
	}
	
	
	/*methode qui rajout l'actionListenner que je veux dans les autres classes*/
	public void addActionListener(ActionListener e) 
	{
		b1.addActionListener(e);
	}
	
	/**gestion du MouseHover*/
	class MouseHover implements MouseListener
	{
		public void mouseClicked(MouseEvent arg0) {}
		public void mouseEntered(MouseEvent arg0) 
		{
			b1.setIcon(imh);
		}

		@Override
		public void mouseExited(MouseEvent arg0) 
		{
			b1.setIcon(im);
		}
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) 
		{
			b1.setIcon(im);
		}
	}
}
