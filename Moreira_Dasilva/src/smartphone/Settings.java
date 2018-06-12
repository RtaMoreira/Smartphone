/**
* --------------------------------------------------------------------------<br/>
* Classe : Settings <br/>
* --------------------------------------------------------------------------<br/>
* Auteur: Rita Moreira <br/>
* Extension : AppTemplate <br/>
* Interface : Resizable <br/>
* Description : Classe gérant les paramètres du smartphone (fond d'écran <br/>
* et police des titres) <br/>
* --------------------------------------------------------------------------<br/>
*/
package smartphone;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import GUI.PhoneFrame;
import GUI.composants.MiniPhoto;
import GUI.composants.Resizable;

public class Settings extends AppTemplate implements Resizable {

	private ImageIcon settingsIcon = new ImageIcon("image/icon/reglage.png");
	private ImageIcon settingsIconHover = new ImageIcon("image/icon/reglageHOVER.png");

	private PhoneFrame phone;
	private JPanel mainPanel;
	
	//Panel Option fond d'écran
	private JPanel fondEcran = new  JPanel(new BorderLayout());
	private JPanel listeFond = new JPanel(new GridLayout(2, 4));
	
	//Panel Option police
	private JPanel fontApp = new JPanel(new BorderLayout());
	private JPanel listePolice = new JPanel(new GridLayout(2, 3));

	private Timer timer = new Timer();
	private JPanel msgPanel = new JPanel();
	private JLabel message = new JLabel("changement sauvergardé");

	/**
	 * Constructeur
	 * @param phoneFrame
	 * @author Rita Moreira
	 */	
	public Settings(PhoneFrame phone) 
	{
		super("Paramètres", Color.WHITE);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.phone = phone;

		//Option : fond d'écran
		fondEcran.add(generateTitre("Fond d'écran"),BorderLayout.NORTH);
		fondEcran.add(listeFond,BorderLayout.CENTER);
 			//Affichage de chaque police
			MiniPhoto[] choix = RecupBG(new File("image//background//"));
			for (int i = 0; i < choix.length; i++) 
			{
				listeFond.add(choix[i]);
			}	
			
		//Option : Police
		fontApp.add(generateTitre("Police des titres"),BorderLayout.NORTH);
		createButton();
		fontApp.add(listePolice);	
	
		//message de validation de changement
		message.setForeground(Color.WHITE);
		msgPanel.setBackground(Color.GREEN);
		msgPanel.add(message);
		msgPanel.setVisible(false);

		mainPanel.add(msgPanel);	
		mainPanel.add(fondEcran);
		mainPanel.add(fontApp);
		add(mainPanel);
	}
	
/**
 * Méthode qui récupère les Background depuis un dossier	
 * @param dossier où se trouvent les fonds d'écran
 * @return tableau de MiniPhoto contenant tous les fonds d'écran
 */
	public MiniPhoto[] RecupBG(File dossier)
	{
		MiniPhoto[] listeBG = new MiniPhoto[dossier.listFiles().length];
		
		for (int i = 0; i < dossier.listFiles().length; i++) 
		{
			String chemin = dossier.listFiles()[i].getPath();
			ImageIcon background= new ImageIcon(chemin);
			
			MiniPhoto icon = new MiniPhoto(Resizable.resizePhotoRatio(150, 190,background), chemin);
	
			icon.setContentAreaFilled(false); //Supprimer l'effet bouton
			icon.setBorderPainted(true);
			icon.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			icon.addMouseListener(new ChangeBg());
			listeBG[i] = icon;
		}

		return listeBG;
	}

/**
 * Méthode qui crée des boutons pour chaque Police proposée (String[] choixFont)
 * Et les ajoute dans le tableau de JButton listePolice
 * @author Rita Moreira
 * @return boutons des polices proposées
 */
	private void createButton()
	{
		String[] choixFont = {"Arial Black","Rockwell", "Courier New", "Georgia", "Bauhaus 93","Impact"};
		JButton[] boutons = new JButton[choixFont.length];
		
		for (int i = 0; i < boutons.length; i++) 
		{
			JButton bouton = new JButton(choixFont[i]);
			bouton.setFont(new Font(choixFont[i], 1, 18));
			
			bouton.setContentAreaFilled(false); //Supprimer l'effet bouton
			bouton.setForeground(Color.BLACK);
			bouton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			
			bouton.addMouseListener(new ChangeTitleFont());
			listePolice.add(bouton);
		}
	}
	
/**
 * Génération des titres des options avec un visuel identique
 * @author Rita Moreira
 * @param titre de l'option
 * @return le titre de chaque option de règlage
 */
	public JPanel generateTitre(String titre) 
	{
		JLabel titreSetting = new JLabel(titre);	
			//texte
		titreSetting.setFont(new Font("Arial Narrow", Font.BOLD, 20));
		titreSetting.setForeground(Color.WHITE);
			
		JPanel titrePanel = new JPanel();
		titrePanel.setBackground(new Color(70,109,146));
		titrePanel.add(titreSetting);
		return titrePanel;
	}
	
/**
 * Listener pour changer de fond d'écran
 * @author Rita Moreira
 */
	public class ChangeBg extends MouseAdapter
	{
		@Override
		public void mouseClicked(MouseEvent me) 
		{
			Object event = me.getSource();
			MiniPhoto photoTemp = (MiniPhoto) event;

			String newBackground = photoTemp.getPathPhoto();
			
			//Ecriture dans fichier
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(phone.getWpp().getSettingsInfo()));
				writer.write(newBackground);
				writer.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

			msgPanel.setVisible(true);
			timer.schedule(new TimerTask() 		 //Timer pour l'affichage des msg
			{
				public void run() 
				{
					msgPanel.setVisible(false);
				}
			}, 2000);

			phone.getWpp().setImage();
		}
		
		@Override
		public void mouseEntered(MouseEvent me) 
		{
			Object event = me.getSource();
			MiniPhoto photoTemp = (MiniPhoto) event;
			photoTemp.setContentAreaFilled(true);
			photoTemp.setBackground(new Color(70,109,146));
		}

		@Override
		public void mouseExited(MouseEvent me) 
		{
			Object event = me.getSource();
			JButton photoTemp= (JButton) event;
			photoTemp.setContentAreaFilled(false);
			photoTemp.setBackground(null);
		}	
	}

/**
 * Listener pour changer de Police	
 * @author Rita Moreira
 */
	
	public class ChangeTitleFont extends MouseAdapter
	{

		@Override
		public void mouseClicked(MouseEvent me) 
		{
			Object event = me.getSource();
			JButton boutonClick= (JButton) event;
			Font newFont = boutonClick.getFont();
			
			try {
				BufferedReader br = new BufferedReader(new FileReader(phone.getWpp().getSettingsInfo())); //accès fichier Settings
				String bg = br.readLine();

				BufferedWriter writer = new BufferedWriter(new FileWriter(phone.getWpp().getSettingsInfo())); 
				writer.write(bg+System.lineSeparator()+newFont.toString());
				writer.close();
				br.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			
			msgPanel.setVisible(true);
			
			timer.schedule(new TimerTask() //timer message
			{
				public void run() 
				{
					msgPanel.setVisible(false);
				}
			}, 2000);
			
			//Changer chaque application fille de AppTemplate
			phone.getGalerie().getNavigation().setTitreFont();
			phone.getSettings().getNavigation().setTitreFont();
			phone.getCamera().getNavigation().setTitreFont();
			phone.getContacts().getNavigation().setTitreFont();
			phone.getGames().getNavigation().setTitreFont();
			phone.getNotes().getNavigation().setTitreFont();
			phone.getMeteo().getNavigation().setTitreFont();
			
		}

		@Override
		public void mouseEntered(MouseEvent me) 
		{
			Object event = me.getSource();
			JButton boutonClick= (JButton) event;
			boutonClick.setContentAreaFilled(true);
			boutonClick.setForeground(Color.WHITE);
			boutonClick.setBackground(new Color(70,109,146));
		}

		@Override
		public void mouseExited(MouseEvent me) 
		{
			Object event = me.getSource();
			JButton boutonClick= (JButton) event;
			boutonClick.setContentAreaFilled(false);
			boutonClick.setForeground(Color.BLACK);
			boutonClick.setBackground(null);
		}	
	}

	//******** Getter & Setters *********//
	public ImageIcon getSettingsIcon() 
	{
		return settingsIcon;
	}

	public ImageIcon getSettingsIconHover() 
	{
		return settingsIconHover;
	}

	public JPanel getMsgPanel() {
		return msgPanel;
	}
	
}
