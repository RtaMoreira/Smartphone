/**
 *	Exercise : smartphone GalerieApp.java
 *	Author : Rita Moreira
 *	Date : 7 mai 2018
 */

package smartphone;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
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
	
	private ArrayList<Photo> listePhotos = new ArrayList<>();
	private MiniPhoto photoTemp; //photo cliquée gardée en mémoire
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

		listePhotos = recupImages();
		showGalery(listePhotos);

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
		mainPanel.add(apercu, "aperçu");
	}

	// *******Autres classes*******

	public class ShowPanel extends JPanel {

		private JLabel delete = new JLabel(new ImageIcon("image/icon/delete.png"));
		private JLabel quit = new JLabel(new ImageIcon("image/icon/crossBL.png"));

		public ShowPanel() {
			super();
			this.setLayout(new BorderLayout());
			this.setBackground(new Color(000, 000, 000, 180));

			// Création partie gestion (supprimer, quitter aperçu)
			JPanel gestion = new JPanel(new FlowLayout(FlowLayout.CENTER, 170, 5));
			gestion.setBackground(Color.CYAN);

			delete.addMouseListener(new Options());
			quit.addMouseListener(new Options());

			// Panel qui contient la croix pour quitter aperçu
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
						for (int i = 0; i < listePhotos.size(); i++) 
						{
							if(recupNom.getName() == listePhotos.get(i).getNom())
								listePhotos.remove(i);
						}
						
						refreshGalerie();
						
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.getMessage();
						}
				} else if (event == getQuit()) 
				{
					refreshGalerie();
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

	// *******Méthodes********
	/**
	 * Methode qui attribue une dimension à la galerie 
	 * Utilisée à chaque affichage de galerie
	 * @return
	 */
	public Dimension setDimension(int width) 
	{
		
		int hauteur = CalculeHGal(); //Dépend du nb de photos
		Dimension nouvDimension = new Dimension(width,hauteur);
		return nouvDimension;
	}
	/**
	 * Méthode qui calcule la taille de la galerie
	 * Pour définir la taille du la galerie et de son scroll
	 * (Adapte le scroll à la galerie)
	 */
	public int CalculeHGal() {	//
				int nbPhotos = listePhotos.size();
					//Calcule du nombre de lignes (3 photos par lignes)
				int nbLignes = nbPhotos/3;
					//vérifie si dernière ligne incomplète 
					if(nbPhotos%3 != 0) 
					{
						nbLignes += 1;
					}
					
				//Calcule de la hauteur selon taille icons (setter à 120)
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
		showListe(liste);
		System.out.println("RECUP IMAGE FINI");
		return liste;

	}

	public void showGalery(ArrayList<Photo> photos) {
		// Supprime si des photos étaient déjà dans galerie avant
		galerie.removeAll();

		// Créations des miniPhotos à partir du ArrayList passé en paramètre
		for (int i = 0; i < photos.size(); i++) {

			// Affichage des images taille icon
			ImageIcon imageOriginale = new ImageIcon(listePhotos.get(i).getLocation());

			ImageIcon imageIcon = Resizable.resizePhotoIcon(120, imageOriginale);
			// création de la MiniPhoto (en JButton)
			MiniPhoto miniBouton = new MiniPhoto(imageIcon, listePhotos.get(i).getPath());

			// //ActionListener sur les icones
			miniBouton.addActionListener(new ShowImage());
			galerie.add(miniBouton);

		}
		System.out.println("SHOW GALERY FINI");
	}
	/**TEST GALERIE DELETE
	 * Affiche contenu de arrayListe Liste
	 */
	private void showListe(ArrayList<Photo> maListe) {
		System.out.println("CONTROLE SHOWLISTE DEBUT");
		for (int i = 0; i < maListe.size(); i++) {
			System.out.println(maListe.get(i).getNom());
		}
		System.out.println("CONTROLE FIN");
	}
	/**
	 * Récupérer l'extension d'un Fichier
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
	 * Rafraîchir la galerie photo (après delete, ajout, retour)
	 *
	 */
	public void refreshGalerie() 
	{
		//réaffiche les images après modif.
		galerie.setPreferredSize(setDimension(480));
//		galerie.repaint();
		showGalery(recupImages());
		
		//retour à la galerie principale
		cardLayout.first(getMainPanel());

		// reset le reste du ShowPanel "aperçu" si on était sur aperçu
////		if(cardLayout.) {
//			System.out.println("APERçU IS SHOWING"+apercu.isShowing());
//			getApercu().remove(2);
//		}
	}

	// *******ActionListener & MouseListeners ********

	class ShowImage implements ActionListener {

		// Affiche l'image choisie dans aperçu en grand
		public void actionPerformed(ActionEvent e) {

			// récupère l'url de l'image
			Object event = e.getSource();
			// MiniPhoto bouton = (MiniPhoto) event;
			// XXXXXXXXXXXXXX
			photoTemp = (MiniPhoto) event;
			System.out.println(photoTemp.getPathPhoto());
			ImageIcon imageOriginal = new ImageIcon(photoTemp.getPathPhoto());

			ImageIcon photoChoisie = Resizable.resizePhotoRatio(480, 600, imageOriginal);

			JLabel photoZoom = new JLabel(photoChoisie);

			apercu.add(photoZoom);
			System.out.println("APERCU IS SHOWING?"+apercu.isShowing());
			// apercu.add(app);
			cardLayout.show(mainPanel, "aperçu");

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
					File destination = new File(location + fs[i].getName());
					Photo photo;

					try {
						Files.copy(fs[i].toPath(), destination.toPath());
						photo = new Photo(fs[i].getName());
						listePhotos.add(photo);
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
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

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
	public ArrayList<Photo> getListePhotos() {
		return listePhotos;
	}
	public void setListePhotos(ArrayList<Photo> listePhotos) {
		this.listePhotos = listePhotos;
	}
	
	

}
