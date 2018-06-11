/**
* --------------------------------------------------------------------------<br/>
* Classe : PhoneFrame <br/>
* --------------------------------------------------------------------------<br/>
* Auteur: Joao Silva <br/>
* Description : regroupe le wallpaper et toutes les icones d'application <br/>
* aussi avec le cardLayout qui permet d'ouvrir les applications<br/>
* --------------------------------------------------------------------------<br/>
*/
package GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

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

	public class PhoneFrame extends JFrame 
	{
	/**barres haut et bas*/
	private NorthPanel hour = new NorthPanel();
	private SouthPanel buttons = new SouthPanel();
	
	/** forme du natel (PNG)*/
	private ImagePanel phoneLayout = new ImagePanel(new ImageIcon("image/icon/phone.png"));
	
	/** wallpaper*/
	private ImagePanel wpp = new ImagePanel();
	
	/** menu ou on rajoute les icones*/
	private AppGrid appsPanel = new AppGrid();

	/**Timer pour quitter*/
	private Timer timer = new Timer(3000, new CalculeTimer());
	
	/**applis*/
	private GalerieApp galerie = new GalerieApp();
	private ContactApp contacts = new ContactApp();
	private GamesApp games = new GamesApp();
	private Settings settings = new Settings(this);
	private Camera camera = new Camera(galerie);
	private Meteo meteo = new Meteo();
	private NotesApp notes = new NotesApp();
	
	/** ecran verrou*/
	private LockScrean lockscrean = new LockScrean();

	/** Panel d'affichage(principal)*/
	private CardLayout cardLayout = new CardLayout();
	private JPanel screen = new JPanel(cardLayout);

	/** icons des applis*/
	private JLabel galerieIcon = new JLabel(galerie.getGalerieIcon());
	private JLabel contactIcon = new JLabel(contacts.getContactIcon());
	private JLabel gamesIcon = new JLabel(games.getGamesIcon());
	private JLabel settingsIcon = new JLabel(settings.getSettingsIcon());
	private JLabel cameraIcon = new JLabel(camera.getCameraIcon());
	private JLabel meteoIcon = new JLabel(meteo.getMeteoIcon());
	private JLabel notesIcon = new JLabel(notes.getNotesIcon());
	private JLabel turnOffIcon = new JLabel(new ImageIcon("image/icon/off.png"));

	public PhoneFrame() 
	{
		/**je dois l'initializer au debut mais je ferme, elle s'ouvrent qund on clique dans l'icon*/
		camera.getWebcam().close();
		
		/** frame settings*/
		setSize(480, 860);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));

		/** forme natel(hardware)*/
		setContentPane(phoneLayout);// on va mettre du contenu dans le phoneLayout
		phoneLayout.setLayout(new BorderLayout());
		phoneLayout.add(screen, BorderLayout.CENTER); // l'écran va au centre
		phoneLayout.setBorder(new EmptyBorder(30, 0, 30, 0));// pour que l'ecran ne couvre pas l'entier de la fenetre

		/** barre nord et sud*/
		phoneLayout.add(hour, BorderLayout.NORTH);
		phoneLayout.add(buttons, BorderLayout.SOUTH);

		/**wallpaper*/
		wpp.setImage();	//ajout wpp sauvegardé
		wpp.setLayout(new BorderLayout());
		wpp.add(appsPanel, BorderLayout.CENTER);

		/** Applications :*/
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
		
		/**Fermeture Smartphone*/
		turnOffIcon.addMouseListener(new TurnOff());
		appsPanel.addIcon(turnOffIcon);
		
		
		/**ajouter le mouseListenner dans le bouton retour pour retourner au menu*/
		galerie.getNavigation().getBackButton().addMouseListener(new ReturnMenu());
		contacts.getNavigation().getBackButton().addMouseListener(new ReturnMenu());
		games.getNavigation().getBackButton().addMouseListener(new ReturnMenu());
		settings.getNavigation().getBackButton().addMouseListener(new ReturnMenu());
		camera.getNavigation().getBackButton().addMouseListener(new ReturnMenu());
		meteo.getNavigation().getBackButton().addMouseListener(new ReturnMenu());
		notes.getNavigation().getBackButton().addMouseListener(new ReturnMenu());
		
		/**ajouter le cards au cardLayout*/
		screen.add(wpp, "menu");
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

	/**verifie le code de lockscrean et si juste vient au menu*/
	class UnlockClick implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			if (lockscrean.getCode().equals("1234")) 
			{
				cardLayout.show(screen, "menu");
				lockscrean.refresh();
			}
				
			else 
			{
				lockscrean.getCodeError().setText("Code erroné! Essayez de nouveau:");
			}
		}
	}

	/**bouton de retour de chaque  appli*/
	class ReturnMenu implements MouseListener 
	{

		public void mouseClicked(MouseEvent e) 
		{
			cardLayout.show(screen, "menu");
		}

		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}

	}

	/**acceder l'appli depuis l'icon et effet mmouseHover*/
	class accesApp implements MouseListener 
	{
		@Override
		public void mouseClicked(MouseEvent me) 
		{
			Object event = me.getSource();
			JLabel app = (JLabel) event;
			if (app == galerieIcon)
				cardLayout.show(screen, "galerie");
			else 
			{
				if (app == contactIcon)
					cardLayout.show(screen, "contacts");
				else 
				{
					if (app == gamesIcon) 
						cardLayout.show(screen, "games");
					else 
					{
						if(app==settingsIcon)
							cardLayout.show(screen, "settings");
						else 
						{
							if(app==cameraIcon) 
							{
								camera.start();
								cardLayout.show(screen, "camera");
							}
							else 
							{
								if(app==meteoIcon)
									cardLayout.show(screen, "meteo");
								else 
								{
									if(app==notesIcon)
										cardLayout.show(screen, "notes");
								}
							}
						}
					}
				}
			}
		}

		public void mouseEntered(MouseEvent me) 
		{
			JLabel app = (JLabel) me.getComponent();
			if (app == galerieIcon)
				app.setIcon(galerie.getGalerieIconHover());
			else 
			{
				if (app == contactIcon)
					app.setIcon(contacts.getContactIconHover());
				else 
				{
					if (app == gamesIcon)
						app.setIcon(games.getGamesIconHover());
					else 
					{
						if(app==settingsIcon) 
							app.setIcon(settings.getSettingsIconHover());
						else 
						{
							if(app==cameraIcon)
								app.setIcon(camera.getCameraIconHover());
							else 
							{
								if(app==meteoIcon)
									app.setIcon(meteo.getMeteoIconHover());
								else 
								{
									if(app==notesIcon)
										app.setIcon(notes.getNotesIconHover());
								}
							}
						}
					}
				}
			}
		}

		public void mouseExited(MouseEvent me) 
		{
			JLabel app = (JLabel) me.getComponent();
			if (app == galerieIcon)
				app.setIcon(galerie.getGalerieIcon());
			else 
			{
				if (app == contactIcon)
					app.setIcon(contacts.getContactIcon());
				else 
				{
					if (app == gamesIcon)
						app.setIcon(games.getGamesIcon());
					else 
					{
						if(app==settingsIcon) 
							app.setIcon(settings.getSettingsIcon());
						else 
						{
							if(app==cameraIcon)
								app.setIcon(camera.getCameraIcon());
							else 
							{
								if(app==meteoIcon)
									app.setIcon(meteo.getMeteoIcon());
								else 
								{
									if(app==notesIcon)
										app.setIcon(notes.getNotesIcon());
								}
							}
						}
					}
				}
			}
		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
	
	/**eteindre ou verouiller le natel*/
	class TurnOff implements MouseListener 
	{
		public void mouseClicked(MouseEvent arg0) 
		{
			cardLayout.show(screen, "lockscreen");
		}

		public void mouseEntered(MouseEvent me) 
		{
			JLabel app = (JLabel) me.getComponent();
				app.setIcon(new ImageIcon("image/icon/offHOVER.png"));
		}
	
		public void mouseExited(MouseEvent me) 
		{
			JLabel app = (JLabel) me.getComponent();
				app.setIcon(new ImageIcon("image/icon/off.png"));
		}

		public void mousePressed(MouseEvent arg0) 
		{
		timer.start();
		}

		public void mouseReleased(MouseEvent arg0) 
		{
		timer.stop();
		}
	}
	
	/**timer si longClick dans bouton eteindre*/
	public class CalculeTimer implements ActionListener 
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			System.exit(0);
		}
	}
	


	public ImagePanel getWpp() 
	{
		return wpp;
	}

	public void setWpp(String bg) 
	{
		this.wpp = new ImagePanel(new ImageIcon(bg));
	}

	public JPanel getScreen() 
	{
		return screen;
	}

	public void setScreen(JPanel screen) 
	{
		this.screen = screen;
	}

	public AppGrid getAppsPanel() 
	{
		return appsPanel;
	}

	public void setAppsPanel(AppGrid appsPanel) 
	{
		this.appsPanel = appsPanel;
	}

	public GalerieApp getGalerie() {
		return galerie;
	}

	public void setGalerie(GalerieApp galerie) {
		this.galerie = galerie;
	}

	public ContactApp getContacts() {
		return contacts;
	}

	public void setContacts(ContactApp contacts) {
		this.contacts = contacts;
	}

	public GamesApp getGames() {
		return games;
	}

	public void setGames(GamesApp games) {
		this.games = games;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public Camera getCamera() 
	{
		return camera;
	}

	public void setCamera(Camera camera) 
	{
		this.camera = camera;
	}

	public Meteo getMeteo() 
	{
		return meteo;
	}

	public void setMeteo(Meteo meteo) 
	{
		this.meteo = meteo;
	}

	public NotesApp getNotes() 
	{
		return notes;
	}

	public void setNotes(NotesApp notes) 
	{
		this.notes = notes;
	}
	
	
}
