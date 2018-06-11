/**
* --------------------------------------------------------------------------<br/>
* Classe : ShowPanel <br/>
* --------------------------------------------------------------------------<br/>
* Auteur: Rita Moreira <br/>
* Extension : JPanel <br/>
* Description : Classe gérant l'affichage en entier des images. Elle comprend <br/>
* la gestion de l'image affichée (suppression et retour à la galerie) <br/>
* --------------------------------------------------------------------------<br/>
*/
package GUI.composants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import smartphone.GalerieApp;


public class ShowPanel extends JPanel 
{

	private JLabel delete = new JLabel(new ImageIcon("image/icon/delete.png"));
	private JLabel quit = new JLabel(new ImageIcon("image/icon/crossBL.png"));
	private Timer timer = new Timer();

	private GalerieApp galerieApp;
	
	/**
	 * Constructeur
	 * @param GalerieApp
	 * @author Rita Moreira
	 */
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

	/**
	 * Listener pour les options de l'image:
	 * Si clique sur supprimer : supression photo
	 * Si clique sur quitter aperçu : retour à la galerie
	 * @author Rita Moreira
	 */
	class OptionsApercu extends MouseAdapter 
	{

		public void mouseClicked(MouseEvent e) 
		{
			JLabel event = (JLabel) e.getSource();
			
			//SUPPRESSION IMAGE
			if (event == getDelete()) 
			{
				try {
						Files.delete((Paths.get(galerieApp.getPhotoTemp().getPathPhoto()))); //supprime fichier
						File recupNom = new File(galerieApp.getPhotoTemp().getPathPhoto()); //supprime MiniPhoto de ArrayListe 
						System.out.println("recup nom:"+recupNom);
						for (int i = 0; i < galerieApp.getBoutonsIcons().size(); i++) 
						{
							if(recupNom.getName().equals(galerieApp.getBoutonsIcons().get(i).getNomPhoto()) )
							{
								System.out.println("vaut "+galerieApp.getBoutonsIcons().get(i).getNomPhoto());
								galerieApp.getGalerie().remove(galerieApp.getBoutonsIcons().get(i));
								galerieApp.getBoutonsIcons().remove(i);	
								
								remove(2);// reset le ShowPanel "aperçu"
								galerieApp.getCardLayout().show(galerieApp.getMainPanel(), "galerie");
								break;
							}
						}
						
						galerieApp.getMessage().setText("Suppression réussie");
						galerieApp.styleMsgPanel();
						galerieApp.getSouth().add(galerieApp.getMsgPanel());
						timer.schedule(new TimerTask() 
						{
							public void run() 
							{
								galerieApp.getMessage().setText("");
								galerieApp.getSouth().remove(1);
							}
						}, 2000);
					} catch (IOException e1) {
						e1.getMessage();
					}
				
			//RETOUR A LA GALERIE
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

	}
	
	
	/** 
	 * Getters & Setters
	 * Classe ShowPanel
	 */
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