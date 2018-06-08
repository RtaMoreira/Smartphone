/**
* TP Week2
*Author: Joao Silva
*Date creation : 4 juin 2018
*/
package smartphone;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.PhoneFrame;
import GUI.composants.MiniPhoto;
import GUI.composants.Resizable;

public class Settings extends AppTemplate implements Resizable{

	private ImageIcon settingsIcon = new ImageIcon("image/icon/reglage.png");
	private ImageIcon settingsIconHover = new ImageIcon("image/icon/reglageHOVER.png");

	private JPanel mainPanel;
	//Panel fond d'écran
	private JPanel fondEcran = new  JPanel(new BorderLayout());
	private JPanel listeFond = new JPanel(new GridLayout(1, 7));
	
	//Panel changement écriture des titre des App
	private JPanel fontApp = new JPanel(new BorderLayout());
	private JPanel listePolice = new JPanel(new GridLayout(2, 3));
	
	//Autres
	private File dossier = new File("image//background//");
	
	private PhoneFrame phone;
	
	public Settings(PhoneFrame phone) 
	{
		super("Parametres", Color.WHITE);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.phone = phone;

		
		//Section fond d'écran
		fondEcran.add(generateTitre("Fond d'écran"),BorderLayout.NORTH);
		
		fondEcran.add(listeFond,BorderLayout.CENTER);
			//Affichage choix de fonds
			MiniPhoto[] choix = RecupBG(dossier);
			
			for (int i = 0; i < choix.length; i++) 
			{
				listeFond.add(choix[i]);
			}	

			
		//Section font titre
		fontApp.add(generateTitre("Police des titres"),BorderLayout.NORTH);
		
	
		createButton();
		fontApp.add(listePolice);	
			
		mainPanel.add(fondEcran);
		mainPanel.add(fontApp);
		add(mainPanel);
	}
	
	public MiniPhoto[] RecupBG(File dossier)
	{
		MiniPhoto[] listeBG = new MiniPhoto[dossier.listFiles().length];
		
		for (int i = 0; i < dossier.listFiles().length; i++) 
		{
			String chemin = dossier.listFiles()[i].getPath();
			ImageIcon background= new ImageIcon(chemin);
			
			MiniPhoto icon = new MiniPhoto(Resizable.resizePhotoRatio(110, 130,background), chemin);
			
			icon.addActionListener(new ChangeBg());
			listeBG[i] = icon;
		}
		
		return listeBG;
	}

	public JButton[] createButton()
	{
		String[] choixFont = {"Arial Black","Rockwell", "Courier New", "Georgia", "Bauhaus 93","Impact"};
		JButton[] boutons = new JButton[choixFont.length];
		
		for (int i = 0; i < boutons.length; i++) 
		{
			JButton bouton = new JButton(choixFont[i]);
			bouton.setFont(new Font(choixFont[i], 1, 18));
			bouton.addMouseListener(new ChangeTitleFont());
			
			//Supprimer les effets par défaut bouton 
			bouton.setOpaque(false);
			bouton.setContentAreaFilled(false);
			bouton.setBorderPainted(true);
			
			listePolice.add(bouton);
		}

		return boutons;
	}
	
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
	
	public class ChangeBg implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Object event = e.getSource();

			MiniPhoto photoTemp = (MiniPhoto) event;

			String newBackground = photoTemp.getPathPhoto();
			
			//Ecriture dans fichier
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(phone.getWpp().getSettingsInfo()));
				writer.write(newBackground);
				writer.close();
			} catch (IOException ioe) {
				// TODO Auto-generated catch block
				ioe.printStackTrace();
			}
			phone.getWpp().setImage();

		}
	}
	
	public class ChangeTitleFont implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent me) 
		{
			Object event = me.getSource();
			JButton boutonClick= (JButton) event;
			Font newFont = boutonClick.getFont();	
			try {
				BufferedReader br = new BufferedReader(new FileReader(phone.getWpp().getSettingsInfo()));
				String bg = br.readLine();

				BufferedWriter writer = new BufferedWriter(new FileWriter(phone.getWpp().getSettingsInfo()));
				writer.write(bg+System.lineSeparator()+newFont.toString());
				writer.close();
				br.close();
			} catch (IOException ioe) {
				// TODO Auto-generated catch block
				ioe.printStackTrace();
			}
			//Changer AppTemplate
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
			setBackground(new Color(70,109,146));
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	public ImageIcon getSettingsIcon() 
	{
		return settingsIcon;
	}

	public ImageIcon getSettingsIconHover() 
	{
		return settingsIconHover;
	}

}
