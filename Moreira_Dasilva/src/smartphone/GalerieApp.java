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
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
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
	
	private ArrayList<MiniPhoto> BoutonsIcons = new ArrayList<>();
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
	private ShowPanel apercu = new ShowPanel(this);

	public GalerieApp() 
	{
		super("Galerie Photo", Color.CYAN);


		creationGalerie(recupImages());
		refreshGalerie();
		
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

//SHOWPANEL
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
				int nbPhotos = BoutonsIcons.size();
					//Calcule du nombre de lignes (3 photos par lignes)
				int nbLignes = nbPhotos/3;
					//vérifie si dernière ligne incomplète 
					if(nbPhotos%3 != 0) 
					{
						nbLignes += 1;
					}
					
				//Calcule de la hauteur selon taille icons (setter à 135)
				int hauteur = nbLignes*(135+7);
				
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

	public void showGalery(ArrayList<MiniPhoto> icons) {
		// Supprime si des photos étaient déjà dans galerie avant
		galerie.removeAll();

		// Créations des miniPhotos à partir du ArrayList
		for (int i = 0; i < icons.size(); i++) 
		{
			galerie.add(icons.get(i));
		}
	}
	
	/**
	 * Selon photos récupérées (ArrayList)
	 * Crée des MiniPhoto qu'on ajoute à ArrayList
	 * @param photos
	 */
	public void creationGalerie(ArrayList<Photo> photos) {
		// Supprime si des photos étaient déjà dans galerie avant
		galerie.removeAll();

		// Créations des miniPhotos à partir du ArrayList passé en paramètre
		for (int i = 0; i < photos.size(); i++) {
			
			String path = photos.get(i).getLocation();
			createAddMiniIcon(path);
		}
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
		galerie.setPreferredSize(setDimension(480));
		showGalery(BoutonsIcons);

	}
	
	
	/**
	 * Contrôle des images ajoutées 
	 * uniquement accepté : JPEG, JPG, PNG
	 * 
	 * @author Rita Moreira
	 *@return boolean (true = extension valide)
	 */
	private boolean checkExtension(File fichier) {
		String ext = getFileExtension(fichier);
		if(ext.toLowerCase().equals("jpeg")|| ext.toLowerCase().equals("jpg") || ext.toLowerCase().equals("png")) {
			return true;
		}else 
			return false;
	}
	
	/**
	 * Création MiniIcon et ajout à ArrayListe BoutonIcon
	 * @author Rita Moreira
	 *
	 */
	public void createAddMiniIcon(String path) 
	{
		ImageIcon creationIcon = Resizable.resizePhotoIcon(135, new ImageIcon(path)); //crée icon
		MiniPhoto icon = new MiniPhoto(creationIcon, path);
		
		icon.addActionListener(new ShowImage()); //ajoute action
		BoutonsIcons.add(icon);	//ajoute au tableau d'icons
	}

	// *******ActionListener & MouseListeners ********

	class ShowImage implements ActionListener {

		// Affiche l'image choisie dans aperçu en grand
		public void actionPerformed(ActionEvent e) {

			// récupère l'url de l'image
			Object event = e.getSource();

			photoTemp = (MiniPhoto) event; //Enregistre chemin photo cliquée

			ImageIcon imageOriginal = new ImageIcon(photoTemp.getPathPhoto()); 

			ImageIcon photoChoisie = Resizable.resizePhotoRatio(480, 600, imageOriginal);
			
			JLabel photoZoom = new JLabel(photoChoisie);

			photoZoom.addMouseListener(new NextImage());
			getApercu().setBackground(Color.BLACK);
			getApercu().add(photoZoom);
			

			cardLayout.show(mainPanel, "aperçu");

		}
		
		class NextImage implements MouseListener
		{

			@Override
			public void mouseClicked(MouseEvent e) 
			{
				
				JLabel nextPhotoGrd = null;

				int j;
				for (j = 0; j < getBoutonsIcons().size(); j++) 
				{
					if(photoTemp.getNomPhoto().equals(getBoutonsIcons().get(j).getNomPhoto())) 
					{
						if(j != getBoutonsIcons().size()-1) 
						{
							photoTemp=getBoutonsIcons().get(j+1);
						}else{
							photoTemp=getBoutonsIcons().get(0);	//recommence à zero quand dernière image
						}
						break;
					}

				}
				ImageIcon nextPhoto = new ImageIcon(photoTemp.getPathPhoto()); //Récupère le chemin selon positions dans icons
				
				nextPhoto = Resizable.resizePhotoRatio(480, 600, nextPhoto); //Création image taille grande
				nextPhotoGrd = new JLabel(nextPhoto);
				nextPhotoGrd.setOpaque(false);
				
				getApercu().remove(2);
				getApercu().add(nextPhotoGrd);
				nextPhotoGrd.addMouseListener(new NextImage());	//Ajout du même ActionListener
				
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			
		}

	}

	class AddImage implements MouseListener 
	{

		@Override
		public void mouseClicked(MouseEvent e) 
		{

			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "jpeg","png", "Images");

			chooser.setFileFilter(filter);
			chooser.setMultiSelectionEnabled(true); // Choix multiple accepté

			int reponse = chooser.showOpenDialog(null);

			if (reponse == chooser.APPROVE_OPTION) 
			{

				File[] fs = chooser.getSelectedFiles();
				String location = getDossier() + "\\";
				int cptExistantImage=0;
				MiniPhoto icon;
				
				for (int i = 0; i < fs.length; i++) 
				{
					String chemin = location + fs[i].getName();
					File destination;
					
					Path source = fs[i].toPath();

					if(checkExtension(fs[i])== true) //CONTROLE: extension (ajout uniquement si ok)
					{
						String nomFChoisi  = fs[i].getName().substring(0, fs[i].getName().lastIndexOf("."));

						for (int j = 0; j < getBoutonsIcons().size(); j++) //vérifie si fichier déjà présent
						{	
							String nomFExistant = getBoutonsIcons().get(j).getNomPhoto();
							nomFExistant = nomFExistant.substring(0,nomFExistant.lastIndexOf("."));
							if(nomFChoisi.equals(nomFExistant)== true ||nomFChoisi.compareTo(nomFExistant)==-3) 
							{
								cptExistantImage++;	//compte nb de fois qu'il est déjà présent
							}
							
							int cpt=0;
						}
								try 
								{
									if(cptExistantImage>0) 
									{
										String recupNom = fs[i].getName().substring(0, fs[i].getName().lastIndexOf("."));
										String recupExt = getFileExtension(fs[i]);
										destination= new File(location+recupNom+"("+cptExistantImage+")."+recupExt); //création fichier à doublon
									}else{
										destination= new File(chemin); 		//création fichier normal
									}
									Files.copy(source, destination.toPath()); //copie fichier sélectionner à la dest.
									createAddMiniIcon(chemin);
									
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								break;
							
				}
			}
				refreshGalerie();
				cardLayout.first(getMainPanel());
			}
			if (reponse == chooser.CANCEL_OPTION) 
			{
				chooser.cancelSelection();
				return;
			}
			return;
		}

		public void mouseEntered(MouseEvent e) 
		{
			getAjout().setIcon(new ImageIcon("image/icon/PlusiconHOVER.png"));
		}

		public void mouseExited(MouseEvent e) 
		{
			getAjout().setIcon(new ImageIcon("image/icon/Plusicon.png"));
		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}

	class resetGalerie implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (apercu.isShowing()) 
			{
				getApercu().remove(2);
			}
			cardLayout.first(getMainPanel());
		}
	}

	// ****** Getter *******
	public ImageIcon getGalerieIcon() 
	{
		return galerieIcon;
	}

	public ImageIcon getGalerieIconHover() 
	{
		return galerieIconHover;
	}

	public JPanel getMainPanel() 
	{
		return mainPanel;
	}

	public JPanel getGalerie() 
	{
		return galerie;
	}

	public ShowPanel getApercu() 
	{
		return apercu;
	}

	public JLabel getAjout() 
	{
		return ajout;
	}

	public JPanel getMainGalerie() 
	{
		return mainGalerie;
	}

	public File getDossier() 
	{
		return dossier;
	}

	public ArrayList<MiniPhoto> getBoutonsIcons() 
	{
		return BoutonsIcons;
	}

	public MiniPhoto getPhotoTemp() 
	{
		return photoTemp;
	}

	public CardLayout getCardLayout() 
	{
		return cardLayout;
	}
}
