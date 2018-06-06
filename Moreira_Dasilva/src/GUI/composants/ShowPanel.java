/**
 *	Exercise : GUI.composants ShowPanel.java
 *	Author : Rita Moreira
 *	Date : 25 mai 2018
 */

package GUI.composants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import smartphone.GalerieApp;


public class ShowPanel extends JPanel 
{

	private JLabel delete = new JLabel(new ImageIcon("image/icon/delete.png"));
	private JLabel quit = new JLabel(new ImageIcon("image/icon/crossBL.png"));
	
	private GalerieApp galerieApp;
	
	public ShowPanel(GalerieApp maGalerie) 
	{
		super();
		this.setLayout(new BorderLayout());
		this.galerieApp = maGalerie;
		
		JPanel gestion = new JPanel(new FlowLayout(FlowLayout.CENTER, 170, 5)); //Panel gestion
		gestion.setBackground(Color.CYAN);

		delete.addMouseListener(new OptionsApercu());
		quit.addMouseListener(new OptionsApercu());

		JPanel quitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5)); //Panel contenant croix (quitter)
		quitPanel.setOpaque(false);
		quitPanel.add(quit);

		gestion.add(delete);

		add(quitPanel, BorderLayout.NORTH);
		add(gestion, BorderLayout.SOUTH);
	}

	class OptionsApercu implements MouseListener 
	{

		public void mouseClicked(MouseEvent e) 
		{
			JLabel event = (JLabel) e.getSource();
			if (event == getDelete()) 
			{
				try {
						Files.delete((Paths.get(galerieApp.getPhotoTemp().getPathPhoto()))); //supprime fichier
					
						File recupNom = new File(galerieApp.getPhotoTemp().getPathPhoto()); //supprime MiniPhoto de ArrayListe 
						
						for (int i = 0; i < galerieApp.getBoutonsIcons().size(); i++) 
						{
	
							if(recupNom.getName().equals(galerieApp.getBoutonsIcons().get(i).getNomPhoto()) )
							{
								galerieApp.getGalerie().remove(galerieApp.getBoutonsIcons().get(i));
								galerieApp.getBoutonsIcons().remove(i);	
								
								remove(2);// reset le ShowPanel "aperçu"
								galerieApp.getCardLayout().show(galerieApp.getMainPanel(), "galerie");
								break;
							}
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.getMessage();
					}
			} else if (event == getQuit()) 
			{
				galerieApp.refreshGalerie();	
				galerieApp.getCardLayout().first(galerieApp.getMainPanel());
				remove(2);
			}

		}

		public void mouseEntered(MouseEvent e) 
		{
			JLabel event = (JLabel) e.getSource();
			if (event == getDelete())
				getDelete().setIcon(new ImageIcon("image/icon/deleteHOVER.png"));
		}

		public void mouseExited(MouseEvent e) 
		{
			JLabel event = (JLabel) e.getSource();
			if (event == getDelete())
				getDelete().setIcon(new ImageIcon("image/icon/delete.png"));
		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}

	public JLabel getDelete() 
	{
		return delete;
	}

	public void setDelete(JLabel delete) 
	{
		this.delete = delete;
	}

	public JLabel getQuit() 
	{
		return quit;
	}

	public void setQuit(JLabel quit) 
	{
		this.quit = quit;
	}

}