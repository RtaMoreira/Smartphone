/**
* --------------------------------------------------------------------------<br/>
* Classe : GalerieApp <br/>
* --------------------------------------------------------------------------<br/>
* Auteur: Rita Moreira <br/>
* Extension : AppTemplate <br/>
* Interface : Resizable <br/>
* Description : Classe gérant la galerie photo <br/>
* --------------------------------------------------------------------------<br/>
*/
package smartphone;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
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
import GUI.composants.Resizable;
import GUI.composants.ShowPanel;

public class GalerieApp extends AppTemplate implements Resizable {
	
	// icon du menu
	private ImageIcon galerieIcon = new ImageIcon("image\\icon\\galerie.png");
	private ImageIcon galerieIconHover = new ImageIcon("image\\icon\\galerieHOVER.png");

	private File dossier = new File("image\\image");	
	
	private ArrayList<MiniPhoto> BoutonsIcons = new ArrayList<>();
	private MiniPhoto photoTemp; 	//photo cliquée gardée en mémoire
	private JFileChooser chooser = new JFileChooser();


	// JPanel principal
	private CardLayout cardLayout = new CardLayout();
	private JPanel mainPanel = new JPanel();
	private JScrollPane scroll = new JScrollPane();

	// Jpanel qui contient la galerie
	private JPanel mainGalerie = new JPanel(new BorderLayout());
	private JPanel galerie = new JPanel(new GridLayout(0,3,0,5));
	
	//JPanel avec bouton d'ajout d'image et Message de confirmation
	private JPanel south = new JPanel(new GridLayout(0, 1));
	private JLabel ajout = new JLabel(new ImageIcon("image/icon/Plusicon.png"));
	private JLabel message = new JLabel("") ;
	private JPanel msgPanel = new JPanel();
	private Timer timer = new Timer();	//pour la durée des messages

	//Panel qui affiche l'image en entier
	private ShowPanel apercu = new ShowPanel(this);

	/**
	 * Constructeur
	 * @author Rita Moreira
	 */
	public GalerieApp() 
	{
		super("Galerie Photo", Color.CYAN);
		mainPanel.setLayout(cardLayout);
		
		creationGalerie(recupImages());	//première génération de la galerie (création de ArrayList)
		refreshGalerie();
		
		super.getNavigation().getBackButton().addActionListener(new ResetGalerie());
	
		//Panel South
		ajout.addMouseListener(new AddImage());
		Border border = ajout.getBorder();
		Border margin = new EmptyBorder(5, 10, 5, 10);
		ajout.setBorder(new CompoundBorder(border, margin));
		
		south.setBackground(Color.cyan);
		msgPanel.add(message);
		south.add(ajout);

		scroll.setViewportView(galerie);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		mainGalerie.add(scroll, BorderLayout.CENTER);	
		mainGalerie.add(south, BorderLayout.SOUTH);
		
		mainPanel.add(mainGalerie, "galerie");
		mainPanel.add(apercu, "aperçu");
		this.add(mainPanel);
	}

	// *********** Méthodes **********//

/**
 * 	Méthode qui récupère les images du dossier au lancement du smartphone
 *  
 * @return ArrayList<Photo> contenant toutes les photos du dossier
 * @author Rita Moreira
 * 
 * Partie classant les images du plus récent ajout au plus vieux :
 * copyright : @cwick - 14.10.08
 * https://stackoverflow.com/questions/203030/best-way-to-list-files-in-java-sorted-by-date-modified?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
 */
	private ArrayList<File> recupImages() 
	{

		ArrayList<File> liste = new ArrayList<File>();
		File image;
		for (int i = 0; i < dossier.list().length; i++) //Ajout à arrayList
		{	
			image = new File(dossier+"\\"+dossier.list()[i]);
			liste.add(image);
		}
		
		//classement de Arraylist (du + récent ajout au + vieux)
		try {	
			Collections.sort(liste, new Comparator<File>() 
			{ 
		
		        public int compare(File p1, File p2)
		        {
		        		return  Long.valueOf(p1.lastModified()).compareTo(p2.lastModified());
		        }
			});
		}catch (ArrayIndexOutOfBoundsException aie) {
			System.out.println(aie.getMessage());
		}
		
		return liste;
		
	}

	/**
	 * Méthode qui ajoute les MiniPhoto à la galerie
	 * @param icons : ArralyList de toutes les icons
	 * @author Rita Moreira
	 */
	private void showGalery(ArrayList<MiniPhoto> icons) 
	{
		// Supprime si des photos étaient déjà dans galerie avant
		galerie.removeAll();

		// Créations des miniPhotos à partir du ArrayList
		for (int i = 0; i < icons.size(); i++) 
		{
			galerie.add(icons.get(i));
		}
	}
	
	
	/**
	 * Selon les photos récupérées (ArrayList photos)
	 * Crée des MiniPhoto qu'on ajoute un ArrayList
	 * @param photos récupérées auparavant
	 * @author Rita Moreira
	 */
	
	private void creationGalerie(ArrayList<File> photos) 
	{

		// Créations des miniPhotos à partir du ArrayList passé en paramètre
		for (int i = 0; i < photos.size(); i++) 
		{
			String path = photos.get(i).getPath();

			createAddMiniIcon(path);
		}
	}

	/**
	 * Rafraîchir la galerie photo (après suppression, ajout, retour à la galerie)
	 *@author Rita Moreira
	 */
	
	public void refreshGalerie() 
	{
		showGalery(BoutonsIcons);
		message.setText("");
	}
	
	
	/**
	 * Méthode qui récupère l'extension d'un Fichier
	 * @param file
	 * @return l'extension en String
	 * @author Rita Moreira
	 */
	
	public String getFileExtension(File file) 
	{
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		else
			return "";
	}
	
	
	/**
	 * Contrôle de l'extension des images ajoutées.
	 * uniquement accepté : JPEG, JPG, PNG
	 *@return boolean (true = extension valide)
	 * @author Rita Moreira
	 */
	public boolean checkExtension(File fichier) 
	{
		String ext = getFileExtension(fichier);
		if(ext.toLowerCase().equals("jpeg")|| ext.toLowerCase().equals("jpg") || ext.toLowerCase().equals("png")) 
			return true;
		else
			return false;
	}
	
	
	/**
	 * Création d'un MiniIcon et ajout à ArrayListe BoutonIcon
	 * @author Rita Moreira
	 * @param path de la photo source
	 */
	public void createAddMiniIcon(String path) 
	{

		ImageIcon creationIcon = Resizable.resizePhotoIcon(150, new ImageIcon(path)); //crée icon
		MiniPhoto icon = new MiniPhoto(creationIcon, path);
		
		icon.addActionListener(new ShowImage()); //ajoute action
		BoutonsIcons.add(0,icon);	//ajoute au tableau d'icons
	}
	
	
	public void styleMsgPanel() 
	{
		if(message.getText().equals("Suppression réussie") ||message.getText().equals("Ajout réussi") )
			msgPanel.setBackground(Color.GREEN);
		else if (message.getText().equals("Erreur : Fichier jpg,jpeg et png uniquement")) {
			msgPanel.setBackground(Color.RED);
		}
	}

	// *******ActionListener & MouseListeners ********

	/**
	 * Listener qui affiche l'image cliquée en grand
	 * @author Rita Moreira
	 */
	class ShowImage implements ActionListener 
	{
		// Affiche l'image choisie dans aperçu en grand
		public void actionPerformed(ActionEvent e) 
		{

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
	}
	
	/**
	 * Listener qui affiche l'image suivante
	 * @author Rita Moreira
	 */
	class NextImage extends MouseAdapter 
	{
			
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			int positionNext = getBoutonsIcons().indexOf(photoTemp);
			
			if(positionNext != getBoutonsIcons().size()-1) 
			{
				positionNext += 1;
			}else{
				positionNext = 0;		//recommence à zero à fin de la galerie
			}
			photoTemp = getBoutonsIcons().get(positionNext);
				
			ImageIcon nextPhoto = new ImageIcon(photoTemp.getPathPhoto()); //Récupère le chemin de l'icon
				
			nextPhoto = Resizable.resizePhotoRatio(480, 600, nextPhoto); //Affichage nextPhoto en grde taille
			JLabel nextPhotoGrd = new JLabel(nextPhoto);
				
			getApercu().remove(2);
			getApercu().add(nextPhotoGrd);
				
			nextPhotoGrd.addMouseListener(new NextImage());	//Ajout du même ActionListener
				
		}
			
	}

	/**
	 * Listener d'ajout d'image
	 * @author Rita Moreira
	 * 
	 * Contrôle fait durant l'ajout :
	 * 	1) extension du fichier valide
	 * 	2) Nom du fichier pas déjà existant (si c'est le cas : ajoute un "(1)" par exemple)
	 */

	class AddImage extends MouseAdapter
	{
		@Override
		public void mouseClicked(MouseEvent e) 
		{

			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "jpeg","png");

			chooser.setFileFilter(filter);
			chooser.setMultiSelectionEnabled(true); // Choix multiple accepté

			int reponse = chooser.showOpenDialog(null);
			boolean error = true; 
			if (reponse == chooser.APPROVE_OPTION) 
			{

				File[] fs = chooser.getSelectedFiles();
				String location = getDossier() + "\\";
				int cptExistantImage=0;
				
				for (int i = 0; i < fs.length; i++) 
				{
					String chemin = location + fs[i].getName();
					File destination;
					
					Path source = fs[i].toPath();

					//CONTROLE: extension (ajout uniquement si ok)
					if(checkExtension(fs[i])== true) 
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
							
						}
						if(cptExistantImage>0) 
						{
								String recupExt = getFileExtension(fs[i]); //Récupère extension
								destination= new File(location+nomFChoisi+"("+(cptExistantImage+1)+")."+recupExt); //création fichier à doublon
						}else{
								destination= new File(chemin); 		//création fichier normal
						}
						try {
							
							Files.copy(source, destination.toPath()); //copie fichier sélectionner à la dest.
							
							Date newDate = new Date();	//nouvelle date de modif (pour ordrer la galerie)
							destination.setLastModified(newDate.getTime());
							createAddMiniIcon(destination.getPath());
							
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						error = false;
						
					}else {
						error = true;	//erreur si mauvaise extension
						break;
					}
				}
				refreshGalerie();

				if(error==false)
				{
					message.setText("Ajout réussi");
					styleMsgPanel();
					south.add(msgPanel);

					timer.schedule(new TimerTask() 
					{
						public void run() 
						{
							message.setText("");
							south.remove(msgPanel);
						}
					}, 2000);
				}
				else
				{
					message.setText("Erreur : Fichier jpg,jpeg et png uniquement");
					styleMsgPanel();
					south.add(msgPanel);
					timer.schedule(new TimerTask() 
					{
						public void run() 
						{
							message.setText("");
							south.remove(msgPanel);
						}
					}, 2000);
				}


				cardLayout.first(getMainPanel());
			}
			if (reponse == chooser.CANCEL_OPTION) 
			{
				chooser.cancelSelection();
				return;
			}

		}

		public void mouseEntered(MouseEvent e) 
		{
			getAjout().setIcon(new ImageIcon("image/icon/PlusiconHOVER.png"));
		}

		public void mouseExited(MouseEvent e) 
		{
			getAjout().setIcon(new ImageIcon("image/icon/Plusicon.png"));
		}

	}

	/** 
	 * Listener qui remet la galerie à Zero quand clique sur backButton de l'application
	 * @author Rita Moreira
	 */
	class ResetGalerie implements ActionListener 
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

	
	//******** Getter & Setters *********//
	
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

	public JScrollPane getScroll() 
	{
		return scroll;
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

	public JLabel getMessage() 
	{
		return message;
	}

	public void setMessage(JLabel message) 
	{
		this.message = message;
	}

	public JPanel getSouth() 
	{
		return south;
	}

	public JPanel getMsgPanel() 
	{
		return msgPanel;
	}

}
