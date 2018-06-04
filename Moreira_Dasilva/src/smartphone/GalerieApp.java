/**
 *	Exercise : smartphone GalerieApp.java
 *	Author : Rita Moreira
 *	Date : 7 mai 2018
 */

package smartphone;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import GUI.composants.MiniPhoto;
import GUI.composants.Photo;
import GUI.composants.Resizable;
import GUI.composants.ShowPanel;

public class GalerieApp extends AppTemplate implements Resizable {
	// icon du menu
	private ImageIcon galerieIcon = new ImageIcon("image\\icon\\galerie.png");
	private ImageIcon galerieIconHover = new ImageIcon("image\\icon\\galerieHOVER.png");

	private File dossier = new File("image\\image");
	
//	private ArrayList<Photo> listePhotos = new ArrayList<>();
	private ArrayList<MiniPhoto> BoutonsIcons = new ArrayList<>();
	private MiniPhoto photoTemp; //photo cliqu�e gard�e en m�moire
	private JFileChooser chooser = new JFileChooser();

	// JPanel principal
	private CardLayout cardLayout = new CardLayout();
	private JPanel mainPanel = new JPanel();
	private JScrollPane scroll = new JScrollPane();

	// Jpanel qui contient la galerie et le JLabel d'Ajout de photo, etc.
	private JPanel mainGalerie = new JPanel(new BorderLayout());
	private JPanel galerie = new JPanel(new FlowLayout());
	private JLabel ajout = new JLabel(new ImageIcon("image/icon/Plusicon.png"));
	private ShowPanel apercu = new ShowPanel();

	public GalerieApp() 
	{
		super("Galerie Photo", Color.CYAN);

//		listePhotos = recupImages();
//		creationGalerie(listePhotos);
		creationGalerie(recupImages());
//		showGalery(listePhotos);
		showGalery(BoutonsIcons);
		
		super.getNavigation().getBackButton().addActionListener(new resetGalerie());

		mainPanel.setLayout(cardLayout);
		this.add(mainPanel);

		galerie.setPreferredSize(setDimension(480));
		
		ajout.addMouseListener(new AddImage());
		ajout.setBackground(Color.CYAN);
		ajout.setOpaque(true);
			Border border = ajout.getBorder();
			Border margin = new EmptyBorder(5, 10, 5, 10);
		ajout.setBorder(new CompoundBorder(border, margin));
		

		
		scroll.setViewportView(galerie);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		mainGalerie.add(scroll, BorderLayout.CENTER);	
		mainGalerie.add(ajout, BorderLayout.SOUTH);
		
		mainPanel.add(mainGalerie, "galerie");
		mainPanel.add(apercu, "aper�u");
	}

	// *******Autres classes*******

	public class ShowPanel extends JPanel {

		private JLabel delete = new JLabel(new ImageIcon("image/icon/delete.png"));
		private JLabel quit = new JLabel(new ImageIcon("image/icon/crossBL.png"));

		public ShowPanel() {
			super();
			this.setLayout(new BorderLayout());
			this.setBackground(new Color(000, 000, 000, 180));

			// Cr�ation partie gestion (supprimer, quitter aper�u)
			JPanel gestion = new JPanel(new FlowLayout(FlowLayout.CENTER, 170, 5));
			gestion.setBackground(Color.CYAN);

			delete.addMouseListener(new Options());
			quit.addMouseListener(new Options());

			// Panel qui contient la croix pour quitter aper�u
			JPanel quitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			quitPanel.setOpaque(false);
			quitPanel.add(quit);

			gestion.add(delete);

			add(quitPanel, BorderLayout.NORTH);
			add(gestion, BorderLayout.SOUTH);
		}

		class Options implements MouseListener {

			public void mouseClicked(MouseEvent e) {
				JLabel event = (JLabel) e.getSource();

				if (event == getDelete()) 
				{
					try {
						
						Files.delete((Paths.get(photoTemp.getPathPhoto()))); //supprime fichier
					
						File recupNom = new File(photoTemp.getPathPhoto()); //supprime de ArrayListe Liste
						for (int i = 0; i < BoutonsIcons.size(); i++) 
						{

							if(recupNom.getName().equals(BoutonsIcons.get(i).getNomPhoto()) ) {
								System.out.println("SUPPRRESSSOOOOON SA MERE");
								galerie.remove(BoutonsIcons.get(i));
								BoutonsIcons.remove(i);	
								// reset le ShowPanel "aper�u"
								getApercu().remove(2);
								cardLayout.show(mainPanel, "galerie");
							}
						}
						
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.getMessage();
						}
				} else if (event == getQuit()) 
				{
					refreshGalerie();
					
					// reset le reste du ShowPanel "aper�u" si on �tait sur aper�u
					getApercu().remove(2);
				}

			}

			public void mouseEntered(MouseEvent e) {
				JLabel event = (JLabel) e.getSource();
				if (event == getDelete())
					getDelete().setIcon(new ImageIcon("image/icon/deleteHOVER.png"));
			}

			public void mouseExited(MouseEvent e) {
				JLabel event = (JLabel) e.getSource();
				if (event == getDelete())
					getDelete().setIcon(new ImageIcon("image/icon/delete.png"));

			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		}

		public JLabel getDelete() {
			return delete;
		}

		public void setDelete(JLabel delete) {
			this.delete = delete;
		}

		public JLabel getQuit() {
			return quit;
		}

		public void setQuit(JLabel quit) {
			this.quit = quit;
		}

	}
	// FIN autres classes

	// *******M�thodes********
	/**
	 * Methode qui attribue une dimension � la galerie 
	 * Utilis�e � chaque affichage de galerie
	 * @return
	 */
	public Dimension setDimension(int width) 
	{
		
		int hauteur = CalculeHGal(); //D�pend du nb de photos
		Dimension nouvDimension = new Dimension(width,hauteur);
		return nouvDimension;
	}
	
	/**
	 * M�thode qui calcule la taille de la galerie
	 * Pour d�finir la taille du la galerie et de son scroll
	 * (Adapte le scroll � la galerie)
	 */
	public int CalculeHGal() {	//
				int nbPhotos = BoutonsIcons.size();
					//Calcule du nombre de lignes (3 photos par lignes)
				int nbLignes = nbPhotos/3;
					//v�rifie si derni�re ligne incompl�te 
					if(nbPhotos%3 != 0) 
					{
						nbLignes += 1;
					}
					
				//Calcule de la hauteur selon taille icons (setter � 120)
				int hauteur = nbLignes*(120+20);
				
			return hauteur;
			}

	public ArrayList<Photo> recupImages() {
		ArrayList<Photo> liste = new ArrayList<Photo>();
		Photo image;

		for (int i = 0; i < dossier.list().length; i++) {

			image = new Photo(dossier.list()[i]);
			liste.add(image);
		}
		return liste;

	}
	//Modifier en "G�n�reGalerie"
	//pour le refresh faire autre m�thodes qui travaille avec arraylist
//	public void showGalery(ArrayList<Photo> photos) {
//		// Supprime si des photos �taient d�j� dans galerie avant
//		galerie.removeAll();
//
//		// Cr�ations des miniPhotos � partir du ArrayList pass� en param�tre
//		for (int i = 0; i < photos.size(); i++) {
//
//			// Affichage des images taille icon
//			ImageIcon imageOriginale = new ImageIcon(listePhotos.get(i).getLocation());
//
//			ImageIcon imageIcon = Resizable.resizePhotoIcon(120, imageOriginale);
//			// cr�ation de la MiniPhoto (en JButton)
//			MiniPhoto miniBouton = new MiniPhoto(imageIcon, listePhotos.get(i).getPath());
//			BoutonsIcons.add(miniBouton);
//			// //ActionListener sur les icones
//			miniBouton.addActionListener(new ShowImage());
//			galerie.add(miniBouton);
//
//		}
//		System.out.println("SHOW GALERY FINI");
//	}
	public void showGalery(ArrayList<MiniPhoto> icons) {
		// Supprime si des photos �taient d�j� dans galerie avant
		galerie.removeAll();

		// Cr�ations des miniPhotos � partir du ArrayList pass� en param�tre
		for (int i = 0; i < icons.size(); i++) 
		{
			galerie.add(icons.get(i));

		}
	}
	/**
	 * Selon photos r�cup�r�es (ArrayList)
	 * Cr�e des MiniPhoto qu'on ajoute � ArrayList
	 * @param photos
	 */
	public void creationGalerie(ArrayList<Photo> photos) {
		// Supprime si des photos �taient d�j� dans galerie avant
		galerie.removeAll();

		// Cr�ations des miniPhotos � partir du ArrayList pass� en param�tre
		for (int i = 0; i < photos.size(); i++) {

			// Affichage des images taille icon
			ImageIcon imageOriginale = new ImageIcon(photos.get(i).getLocation());

			ImageIcon imageIcon = Resizable.resizePhotoIcon(120, imageOriginale);
			// cr�ation de la MiniPhoto (en JButton)
			MiniPhoto miniBouton = new MiniPhoto(imageIcon, photos.get(i).getPath());

			// //ActionListener sur les icones
			miniBouton.addActionListener(new ShowImage());
			BoutonsIcons.add(miniBouton);
//			galerie.add(miniBouton);

		}
	}

	/**
	 * R�cup�rer l'extension d'un Fichier
	 * 
	 * @param file
	 * @return
	 */
	private static String getFileExtension(File file) 
	{
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		else
			return "";
	}

	/**
	 * Rafra�chir la galerie photo (apr�s delete, ajout, retour)
	 *
	 */
	public void refreshGalerie() 
	{
		galerie.setPreferredSize(setDimension(480));
		showGalery(BoutonsIcons);
		//retour � la galerie principale
		cardLayout.first(getMainPanel());

	}

	// *******ActionListener & MouseListeners ********

	class ShowImage implements ActionListener {

		// Affiche l'image choisie dans aper�u en grand
		public void actionPerformed(ActionEvent e) {

			// r�cup�re l'url de l'image
			Object event = e.getSource();

			photoTemp = (MiniPhoto) event; //Enregistre chemin photo cliqu�e

			ImageIcon imageOriginal = new ImageIcon(photoTemp.getPathPhoto()); 

			ImageIcon photoChoisie = Resizable.resizePhotoRatio(480, 600, imageOriginal);

			JLabel photoZoom = new JLabel(photoChoisie);

			apercu.add(photoZoom);
			// apercu.add(app);
			cardLayout.show(mainPanel, "aper�u");

		}

	}

	class AddImage implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

			// filtre
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif", "jpeg",
					"png", "Images");

			chooser.setFileFilter(filter); // affiche que les fichiers
			chooser.setMultiSelectionEnabled(true); // Choix multipple

			int reponse = chooser.showOpenDialog(null);

			if (reponse == chooser.APPROVE_OPTION) {

				File[] fs = chooser.getSelectedFiles();
				String location = getDossier() + "\\";

				for (int i = 0; i < fs.length; i++) {
					String chemin = location + fs[i].getName();
					File destination = new File(chemin); 		//cr�ation fichier � la destination
					MiniPhoto icon;

					try {
						Files.copy(fs[i].toPath(), destination.toPath()); //copie fichier s�lectionner � la dest.
						
						ImageIcon creationIcon = Resizable.resizePhotoIcon(120, new ImageIcon(chemin)); //Cr�e icon
						icon = new MiniPhoto(creationIcon, chemin);
						
						icon.addActionListener(new ShowImage()); //ajoute action
						BoutonsIcons.add(icon);	//ajoute au tableau d'icons
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				refreshGalerie();
				cardLayout.first(getMainPanel());
			}
			if (reponse == chooser.CANCEL_OPTION) {
				chooser.cancelSelection();
				return;
			}
			return;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			getAjout().setIcon(new ImageIcon("image/icon/PlusiconHOVER.png"));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			getAjout().setIcon(new ImageIcon("image/icon/Plusicon.png"));
			}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

	}

	class resetGalerie implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (apercu.isShowing()) {
				getApercu().remove(2);
			}
			cardLayout.first(getMainPanel());
		}
	}

	// ****** Getter *******
	public ImageIcon getGalerieIcon() {
		return galerieIcon;
	}

	public ImageIcon getGalerieIconHover() {
		return galerieIconHover;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public JPanel getGalerie() {
		return galerie;
	}

	public void setGalerie(JPanel galerie) {
		this.galerie = galerie;
	}

	public ShowPanel getApercu() {
		return apercu;
	}

	public void setApercu(ShowPanel apercu) {
		this.apercu = apercu;
	}

	public JLabel getAjout() {
		return ajout;
	}

	public void setAjout(JLabel ajout) {
		this.ajout = ajout;
	}

	public JPanel getMainGalerie() {
		return mainGalerie;
	}

	public void setMainGalerie(JPanel mainGalerie) {
		this.mainGalerie = mainGalerie;
	}

	public File getDossier() {
		return dossier;
	}

	public void setDossier(File dossier) {
		this.dossier = dossier;
	}
//	public ArrayList<Photo> getListePhotos() {
//		return listePhotos;
//	}
//	public void setListePhotos(ArrayList<Photo> listePhotos) {
//		this.listePhotos = listePhotos;
//	}
	
	public ArrayList<MiniPhoto> getBoutonsIcons() {
		return BoutonsIcons;
	}
	public void setBoutonsIcons(ArrayList<MiniPhoto> boutonsIcons) {
		BoutonsIcons = boutonsIcons;
	}
	
	

}
