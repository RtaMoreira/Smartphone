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
	private Font titreFont = new Font("Arial Narrow", Font.BOLD, 18);
	private File dossier = new File("image//background//");
	
	private PhoneFrame phone;
	
	public Settings(PhoneFrame phone) 
	{
		super("Parametres", Color.WHITE);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.phone = phone;

		
		//Section fond d'écran
			//Affichage choix de fonds
			MiniPhoto[] choix = RecupBG(dossier);
			for (int i = 0; i < choix.length; i++) 
			{
				listeFond.add(choix[i]);
			}
		
			//Font
			JLabel FondEcranTitre = new JLabel("Fond d'écran");	
			FondEcranTitre.setFont(titreFont);
			FondEcranTitre.setBackground(Color.WHITE);
			
			fondEcran.add(FondEcranTitre,BorderLayout.NORTH);
			fondEcran.add(listeFond,BorderLayout.CENTER);
			
		//Section font titre
			//font
			JLabel fontAppTitre = new JLabel("Police des titres d'application");
			fontAppTitre.setFont(titreFont);
			fontApp.add(fontAppTitre,BorderLayout.NORTH);
			//2 boutons test
			JButton theme1 = new JButton("Arial Black");
			theme1.setFont(new Font("Arial Black", 1, 15));
			
			JButton theme2 = new JButton("Comic Sans MS");
			theme2.setFont(new Font("Comic Sans MS",1,10));
			JButton theme3 = new JButton("Courier New");
			theme3.setFont(new Font("Courier New", 1, 25));
			theme3.addActionListener(new ChangeTitleFont());
			listePolice.add(theme1);
			listePolice.add(theme2);
			listePolice.add(theme3);
			
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
	
	public class ChangeTitleFont implements ActionListener{

		public void actionPerformed(ActionEvent e) 
		{
			Object event = e.getSource();
			JButton boutonClick= (JButton) event;
			Font newFont = boutonClick.getFont();	
			try {
				BufferedReader br = new BufferedReader(new FileReader(phone.getWpp().getSettingsInfo()));
				String bg = br.readLine();
				System.out.println("bg sauvegardé : "+bg);
				BufferedWriter writer = new BufferedWriter(new FileWriter(phone.getWpp().getSettingsInfo()));
				writer.write(bg+System.lineSeparator()+newFont.toString());
				System.out.println("ce qui a été écrit:"+bg+System.lineSeparator()+newFont.toString());
				writer.close();
			} catch (IOException ioe) {
				// TODO Auto-generated catch block
				ioe.printStackTrace();
			}
			//Changer AppTemplate
			
			
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
