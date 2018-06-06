/**
* TP Week2
*Author: Joao Silva
*Date creation : 7 mai 2018
*/
package GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import com.github.sarxos.webcam.WebcamPanel;

import GUI.composants.AppGrid;
import GUI.composants.ImagePanel;
import GUI.composants.NorthPanel;
import GUI.composants.SouthPanel;
import smartphone.Camera;
import smartphone.ContactApp;
import smartphone.GalerieApp;
import smartphone.GamesApp;
import smartphone.Meteo;
import smartphone.NotesApp;
import smartphone.Settings;

public class PhoneFrame extends JFrame {

	// panels
	private NorthPanel hour = new NorthPanel();// barre nord
	private SouthPanel buttons = new SouthPanel();// barre sud
	// forme du natel (PNJ)
	
//	private ImagePanel phoneLayout = new ImagePanel(new ImageIcon("image/icon/phone.png"));
	private String backgroundPath;
	private File settingsInfo = new File("serials/SettingsInfo.txt");
	private ImagePanel phoneLayout = new ImagePanel(new ImageIcon("image/icon/phone.png"));
	// wallpaper
	private ImagePanel wpp = new ImagePanel(new ImageIcon(backgroundPath));
	// menu
	private AppGrid appsPanel = new AppGrid();// JPanel ac GridLayout avec les icos

	//Timer pour quitter
	Timer timer = new Timer(3000, new calculeTimer());
	// private AppTemplate appTemplate = new AppTemplate();
	private GalerieApp galerie = new GalerieApp();
	private ContactApp contacts = new ContactApp();
	private GamesApp games = new GamesApp();
	private Settings settings = new Settings(this);
	private Camera camera = new Camera(galerie);
	private Meteo meteo = new Meteo();
	private NotesApp notes = new NotesApp();
	// ecran verrou
	LockScrean lockscrean = new LockScrean();

	// Panel d'affichage
	private CardLayout cardLayout = new CardLayout();
	private JPanel screen = new JPanel(cardLayout);

	// essayer de chopper le clique sur l'app
	private JLabel galerieIcon = new JLabel(galerie.getGalerieIcon());
	private JLabel contactIcon = new JLabel(contacts.getContactIcon());
	private JLabel gamesIcon = new JLabel(games.getGamesIcon());
	private JLabel settingsIcon = new JLabel(settings.getSettingsIcon());
	private JLabel cameraIcon = new JLabel(camera.getCameraIcon());
	private JLabel meteoIcon = new JLabel(meteo.getMeteoIcon());
	private JLabel notesIcon = new JLabel(notes.getNotesIcon());
	private JLabel turnOffIcon = new JLabel(new ImageIcon("image/icon/off.png"));

	public PhoneFrame() {// c'est la JFrame
		camera.getWebcam().close();
		
		// frame settings
		this.setSize(480, 860);
		this.setLocationRelativeTo(null);// fenetre au centre
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true); // pas afficher les boutons de la fenetre
		setBackground(new Color(0, 0, 0, 0));// fond transparent

		// forme natel(hardware)
		setContentPane(phoneLayout);// on va mettre du contenu dans le phoneLayout
		phoneLayout.setLayout(new BorderLayout());
		phoneLayout.add(screen, BorderLayout.CENTER); // l'�cran va au centre
		phoneLayout.setBorder(new EmptyBorder(30, 0, 30, 0));// pour que l'ecran ne couvre pas l'entier de la fenetre

		// barre nord et sud
		phoneLayout.add(hour, BorderLayout.NORTH);
		phoneLayout.add(buttons, BorderLayout.SOUTH);

		// on ajoute sur le wallpaper
		wpp.setLayout(new BorderLayout());
		wpp.add(appsPanel, BorderLayout.CENTER);

		// ici on ajoute les �crans possibles(menu, verou et applis)

		// Applications :

		galerieIcon.addMouseListener(new accesApp());
		appsPanel.addIcon(galerieIcon);

		contactIcon.addMouseListener(new accesApp());
		appsPanel.addIcon(contactIcon);
		
		gamesIcon.addMouseListener(new accesApp());
		appsPanel.addIcon(gamesIcon);
		
		settingsIcon.addMouseListener(new accesApp());
		appsPanel.addIcon(settingsIcon);
		
		cameraIcon.addMouseListener(new accesApp());
		appsPanel.addIcon(cameraIcon);
		
		meteoIcon.addMouseListener(new accesApp());
		appsPanel.addIcon(meteoIcon);
		
		notesIcon.addMouseListener(new accesApp());
		appsPanel.addIcon(notesIcon);
		
		//Fermeture Smartphone
		turnOffIcon.addMouseListener(new turnOff());
		appsPanel.addIcon(turnOffIcon);
		
		

		this.galerie.getNavigation().getBackButton().addMouseListener(new ReturnMenu());
		this.contacts.getNavigation().getBackButton().addMouseListener(new ReturnMenu());
		this.games.getNavigation().getBackButton().addMouseListener(new ReturnMenu());
		this.settings.getNavigation().getBackButton().addMouseListener(new ReturnMenu());
		this.camera.getNavigation().getBackButton().addMouseListener(new ReturnMenu());
		this.meteo.getNavigation().getBackButton().addMouseListener(new ReturnMenu());
		this.notes.getNavigation().getBackButton().addMouseListener(new ReturnMenu());
		
		screen.add(wpp, "menu");// add card to card panel
		screen.add(games, "games");
		screen.add(galerie, "galerie");
		screen.add(contacts, "contacts");
		screen.add(settings, "settings");
		screen.add(camera, "camera");
		screen.add(meteo, "meteo");
		screen.add(notes, "notes");
		
		screen.add(lockscrean, "lockscreen");
		lockscrean.getVerrou().addActionListener(new UnlockClick());

		this.setVisible(true);
	}

	class UnlockClick implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (lockscrean.getCode().equals("1234"))
				cardLayout.show(screen, "menu");
		}
	}

	class ReturnMenu implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			cardLayout.show(screen, "menu");
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

	}

	class accesApp implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent me) {
			Object event = me.getSource();
			JLabel app = (JLabel) event;
			if (app == galerieIcon)
				cardLayout.show(screen, "galerie");
			else {
				if (app == contactIcon)
					cardLayout.show(screen, "contacts");
				else {
					if (app == gamesIcon) 
						cardLayout.show(screen, "games");
					else {
						if(app==settingsIcon) 
							cardLayout.show(screen, "settings");
						else {
							if(app==cameraIcon) {
								camera.start();
								cardLayout.show(screen, "camera");
							}
							else {
								if(app==meteoIcon)
									cardLayout.show(screen, "meteo");
								else {
									if(app==notesIcon)
										cardLayout.show(screen, "notes");
								}
							}
						}
					}
				}
			}
				

		}

		public void mouseEntered(MouseEvent me) {
			Object event = me.getSource();
			JLabel app = (JLabel) me.getComponent();
			if (app == galerieIcon)
				app.setIcon(galerie.getGalerieIconHover());
			else {
				if (app == contactIcon)
					app.setIcon(contacts.getContactIconHover());
				else {
					if (app == gamesIcon)
						app.setIcon(games.getGamesIconHover());
					else {
						if(app==settingsIcon) 
							app.setIcon(settings.getSettingsIconHover());
						else {
							if(app==cameraIcon)
								app.setIcon(camera.getCameraIconHover());
							else {
								if(app==meteoIcon)
									app.setIcon(meteo.getMeteoIconHover());
								else {
									if(app==notesIcon)
										app.setIcon(notes.getNotesIconHover());
								}
							}
						}
					}
				}
			}
		}

		public void mouseExited(MouseEvent me) {
			Object event = me.getSource();
			JLabel app = (JLabel) me.getComponent();
			if (app == galerieIcon)
				app.setIcon(galerie.getGalerieIcon());
			else {
				if (app == contactIcon)
					app.setIcon(contacts.getContactIcon());
				else {
					if (app == gamesIcon)
						app.setIcon(games.getGamesIcon());
					else {
						if(app==settingsIcon) 
							app.setIcon(settings.getSettingsIcon());
						else {
							if(app==cameraIcon)
								app.setIcon(camera.getCameraIcon());
							else {
								if(app==meteoIcon)
									app.setIcon(meteo.getMeteoIcon());
								else {
									if(app==notesIcon)
										app.setIcon(notes.getNotesIcon());
								}
							}
						}
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
	}
	
	class turnOff implements MouseListener {

		//Au clic : v�rouillage du smartphone
		public void mouseClicked(MouseEvent arg0) {
			cardLayout.show(screen, "lockscreen");
		}

		public void mouseEntered(MouseEvent me) 
		{
			Object event = me.getSource();
			JLabel app = (JLabel) me.getComponent();
				app.setIcon(new ImageIcon("image/icon/offHOVER.png"));
		}

	
		public void mouseExited(MouseEvent me) 
		{
			Object event = me.getSource();
			JLabel app = (JLabel) me.getComponent();
				app.setIcon(new ImageIcon("image/icon/off.png"));
		}

		//Au presse de 3 secondes : quitter le logiciel
		public void mousePressed(MouseEvent arg0) {

		timer.start();

		}

		public void mouseReleased(MouseEvent arg0) {
		timer.stop();
		}
		

	}
	class calculeTimer implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}

	}
	
	String recupBackground(File settingFile) {
		
		return backgroundPath;
		
	}

	public File getSettingsInfo() {
		return settingsInfo;
	}

	public void setSettingsInfo(File settingsInfo) {
		this.settingsInfo = settingsInfo;
	}

	

}
