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
import java.awt.MouseInfo;
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


	// JPanel principal mainPanel
	private CardLayout cardLayout = new CardLayout();
	private JPanel mainPanel = new JPanel();

	// Jpanel qui contient la galerie
	private JPanel mainGalerie = new JPanel(new BorderLayout());
	private JPanel galerie = new JPanel(new GridLayout(0,3,0,5));	
	private JScrollPane scroll = new JScrollPane();
	
	//JPanel avec bouton d'ajout d'image et message de modification
	private JPanel south = new JPanel(new GridLayout(0, 1));
	private JLabel ajout = new JLabel(new ImageIcon("image/icon/Plusicon.png"));
	private JLabel message = new JLabel("") ;
	private JPanel msgPanel = new JPanel();
	private Timer timer = new Timer();	//pour la durée des messages

	//JPanel qui affiche l'image en entier
	private ShowPanel apercu = new ShowPanel(this);

	
	/**
	 * Constructeur GalerieApp <br/>
	 * --------------------------- <br/>
	 * Description : il récupère le Layout de AppTemplate. 
	 * Il génère la galerie en allant chercher les images depuis le dossier.
	 * --------------------------- <br/>
	 * Il comprend un mainPanel en cardLayout qui contient : <br/>
	 *  - mainGalerie : contient la liste d'images et un JPanel south pour le bouton d'ajout + message de gestion <br/>
	 *  - aperçu : ShowPanel qui affiche l'image en grand <br/>
	 */
	public GalerieApp() 
	{
		super("Galerie", new Color(66,164,93));
		mainPanel.setLayout(cardLayout);
		
		creationGalerie(recupImages());	//première génération de la galerie (création de ArrayList)
		refreshGalerie();
		
		super.getNavigation().getBackButton().addActionListener(new ResetGalerie());
	
		//Panel South
		ajout.addMouseListener(new AddImage());
		Border border = ajout.getBorder();
		Border margin = new EmptyBorder(5, 10, 5, 10);
		ajout.setBorder(new CompoundBorder(border, margin));
		
		msgPanel.add(message);	//ajout à south uniquement lorsque doit s'afficher
		
		south.setBackground(new Color(66,164,93));
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
 * @return ArrayList<File> contenant toutes les photos du dossier
 * @author Rita Moreira
 * 
 * Partie classant les images du plus récent ajout au plus vieux : <br />
 * copyright : @cwick - 14.10.08 <br />
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
	 * Selon les photos récupérées (ArrayList photos),
	 * crée des MiniPhoto qu'on ajoute à un ArrayList
	 * @param photos récupérées auparavant avec recupImages()
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
	 * Méthode qui ajoute les MiniPhoto à la galerie
	 * @param icons : ArrayList avec toutes les icons
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
	 * Uniquement accepté : JPEG, JPG, PNG
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
	 * Création d'un MiniIcon et ajout à ArrayList BoutonIcon
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
	
	/**
	 * Méthode qui gère les messages d'erreur et leur visuel
	 */
	public void styleMsgPanel() 
	{
		if(message.getText().equals("Suppression réussie") ||message.getText().equals("Ajout réussi") )
			msgPanel.setBackground(Color.GREEN);
		else if (message.getText().equals("Erreur : Fichier jpg,jpeg et png uniquement")) {
			msgPanel.setBackground(Color.RED);
		}
	}
	

	/**
	 * Calcule le nombre de doublon qu'une image possède dans le dossier image
	 * 
	 * (S'il y a des doublons, récupère le numero entre parenthèse le plus grand)
	 * Exemple : photo.jpg - photo(1).jpg - photo(3).jpg -> retourne 3
	 * 
	 * @param file
	 * @param nomFChoisi : nom du fichier choisi
	 * @return int
	 * @author Rita Moreira
	 */
	public int checkNumDoublon(File file, String nomFChoisi) 
	{
		int cptExistantImage= 0;
		int numDoublonExistant;
		for (int j = 0; j < getBoutonsIcons().size(); j++) //vérifie si fichier déjà présent dans tout le dossier
		{	
			
			String nomFExistant = getBoutonsIcons().get(j).getNomPhoto();
			nomFExistant = nomFExistant.substring(0,nomFExistant.lastIndexOf("."));
			
			String nomFExistantSSNum="";
			
			if(nomFExistant.contains("(")) 
			{
			 nomFExistantSSNum = nomFExistant.substring(0,nomFExistant.lastIndexOf("("));
			}
			
			if(nomFChoisi.equals(nomFExistant)== true || nomFChoisi.equals(nomFExistantSSNum)) //vérifie les doublons
			{		

				if(nomFExistant.contains("(") && nomFExistant.contains(")"))
				{
					numDoublonExistant = Character.getNumericValue(nomFExistant.charAt(nomFExistant.lastIndexOf("(")+1)); //récup valeur entre parenthèse
					
					if(numDoublonExistant>cptExistantImage) 
						cptExistantImage = numDoublonExistant;	
				}else
					cptExistantImage++;	
			}
		}
		return cptExistantImage;
	};

	
	
	
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
	 * Listener qui affiche l'image suivante ou précédente selon où on clique sur l'image
	 * 
	 * @author Rita Moreira
	 */
	class NextImage extends MouseAdapter 
	{
			
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			
		    int x=e.getX();  //Lieu du click (pour déterminer si affiche l'image précédente ou suivante)

				int positionNext = getBoutonsIcons().indexOf(photoTemp);
				
				if(x>=(480/2)) //Affiche la suivante
				{
					if(positionNext != getBoutonsIcons().size()-1) 
					{
						positionNext += 1;
					}else{
						positionNext = 0; //recommence à zero à fin de la galerie
					}
					
				}else{ //Affiche la précédente
				
					if(positionNext == 0) 
					{
						positionNext = getBoutonsIcons().size()-1;
					}else{
						positionNext -= 1;		//recommence depuis la fin
					}
				}	
				
				photoTemp = getBoutonsIcons().get(positionNext);
					
				ImageIcon nextPhoto = new ImageIcon(photoTemp.getPathPhoto()); //Récupère le chemin de l'icon
					
				nextPhoto = Resizable.resizePhotoRatio(480, 600, nextPhoto); //Affichage nextPhoto en grde taille
				JLabel nextPhotoGrd = new JLabel(nextPhoto);
				getApercu().remove(2);
				getApercu().updateUI();
				getApercu().add(nextPhotoGrd);
				nextPhotoGrd.addMouseListener(new NextImage());	//Ajout du même ActionListener

		}
			
	}

	/**
	 * Listener d'ajout d'image

	 * Contrôle fait durant l'ajout : <br/ >
	 * 	1) Extension du fichier valide <br/ > 
	 * 	2) Nom du fichier pas déjà existant (si c'est le cas : ajoute un "(1)" par exemple) <br/ >
	 * 
	 * Durant l'ajout : <br/ >
	 * - Copy le fichier dans un fichier destination (le nom aura été corrigé s'il est déjà existant) <br/ >
	 * - Modification de "lastModified" Date du fichier afin de classer la galerie <br/ >
	 * - Affichage d'un message si succès ou erreur <br/ >
	 * 
	 * @author Rita Moreira 
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

					//CONTROLE 1) extension (ajout uniquement si true)
					if(checkExtension(fs[i])== true) 
					{
						String nomFChoisi  = fs[i].getName().substring(0, fs[i].getName().lastIndexOf("."));

						//CONTROLE 2 : les doublons
						cptExistantImage = checkNumDoublon(fs[i], nomFChoisi);
						if(cptExistantImage>0) 
						{
								String recupExt = getFileExtension(fs[i]); //Récupère extension
								destination= new File(location+nomFChoisi+"("+(cptExistantImage+1)+")."+recupExt); //création fichier à double avec "(cpt)"
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

					timer.schedule(new TimerTask() //timer pour l'affichage du message
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
