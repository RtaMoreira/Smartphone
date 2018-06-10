/**
* TP Week2
*Author: Joao Silva
*Date creation : 22 mai 2018
*/
package smartphone;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

import GUI.composants.FlatButton;
import GUI.composants.FlatField;
import GUI.composants.FlatLabel;
import GUI.composants.Resizable;


public class ContactApp extends AppTemplate implements Resizable
{
	
	private ArrayList<Contact> contacts = new ArrayList<Contact>();
	
	/**cardLayout management*/
	private CardLayout cardlayout = new CardLayout();
	private JPanel myPanel = new JPanel();
	private MainContact maincontact = new MainContact();
	private NewContact newcontact = new NewContact();
	
	/**pour ouvrir la galerie*/
	private GalerieApp galerieapp;
	private JScrollPane galerie;
	
	/**boutons pour NavigationBar*/
	private FlatButton add = new FlatButton (new ImageIcon("image/icon/Plusicon.png"),new ImageIcon("image/icon/PlusiconHOVER.png"));
	private FlatButton delete = new FlatButton (new ImageIcon("image/icon/delete.png"),new ImageIcon("image/icon/deleteHOVER.png"));
	private JPanel buttonHolder = new JPanel();
	
	/**icones d'appli*/
	private ImageIcon contactIcon =new ImageIcon("image/icon/contact.png");
	private ImageIcon contactIconHover =new ImageIcon("image/icon/contactHOVER.png");
	
	/**gerer quel contact est activé*/
	private int enabled;
	
	public ContactApp() 
	{
		super("contacts", Color.GREEN);
		maincontact.setOpaque(false);
		newcontact.setOpaque(false);
		
		delete.addActionListener(new ReturnMain());
		delete.addActionListener(new Erase());
		add.addActionListener(new ShowAddContact());
		buttonHolder.setOpaque(false);
		super.getNavigation().add(buttonHolder, BorderLayout.EAST);
		super.getNavigation().getBackButton().addActionListener(new ReturnMain());
		buttonHolder.add(add);
		
		myPanel.setLayout(cardlayout);
		myPanel.add(maincontact, "main");
		myPanel.add(newcontact, "new");
		add(myPanel);
		
		deserializeContacts();
		for (int i = 0; i < contacts.size(); i++) {
			contacts.get(i).getButton().addMouseListener(new GetInfo());
		}
		insertLabels();
	}
	
	public ImageIcon getContactIcon() 
	{
		return contactIcon;
	}

	public ImageIcon getContactIconHover() 
	{
		return contactIconHover;
	}
	
	private void insertLabels() 
	/**rajoute un panel a liste pour chaque contact trouvé*/
	{
		for (int i = 0; i < contacts.size(); i++) 
		{
			contacts.get(i).getImageLabel().setIcon(Resizable.resizePhotoIcon(50, new ImageIcon(contacts.get(i).getPhotoPath())));
			maincontact.liste.add(contacts.get(i).getButton());
		}
	}

	public void deserializeContacts() 
	/**affiche tous les panels dans l'appli*/
	{ 
		try {
			FileInputStream fichier;
			fichier = new FileInputStream("serials/contacts.ser");
			BufferedInputStream bfichier=new BufferedInputStream(fichier);
			ObjectInputStream obfichier = new ObjectInputStream(bfichier);
			this.contacts = (ArrayList<Contact>) obfichier.readObject();
			obfichier.close();
		} catch (IOException | ClassNotFoundException e) 
			{e.printStackTrace();}
		
		for (int i = 0; i < contacts.size(); i++) {
			contacts.get(i).createButton();
		}
	}
	
	public void serializeContacts() 
	{
		try {
			FileOutputStream fichier;
			fichier = new FileOutputStream("serials/contacts.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fichier);
			oos.writeObject(contacts);
			oos.close();
		} catch (IOException e1) 
			{e1.printStackTrace();}
	}
	
	/**le panel d'affichage de tous les contacts
	 * @author jcfds
	 */
	class MainContact extends JPanel
	{
		private JTextField recherche = new JTextField();
		private JPanel liste = new JPanel();
		private JScrollPane scroll = new JScrollPane(liste,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		MainContact()
		{
			paint();
		}

		public  void paint() 
		{
			liste.setLayout(new BoxLayout(liste, BoxLayout.Y_AXIS));//boxLayout vertical a ma liste de contacts
			liste.setBackground(Color.WHITE);			
			
			setLayout(new BorderLayout());
			recherche.addKeyListener(new Recherche());
			add(recherche,BorderLayout.BEFORE_FIRST_LINE);
			
			add(scroll, BorderLayout.CENTER);
		}	
	}
	
	/**panel avec les informations
	 * rempli avec les informations qunad modify ou showAperçu
	 * textfield enabled quand showInfo et addContact
	 * @author jcfds
	 */
	class NewContact extends JPanel
	{
		private FlatLabel lnom = new FlatLabel("Nom: ");
		private FlatLabel lprenom = new FlatLabel("Prenom: ");
		private FlatLabel lnatel = new FlatLabel("Numéro natel: ");
		private FlatLabel ltelephone = new FlatLabel("Numéro téléphone: ");
		private FlatLabel lmail = new FlatLabel("Mail: ");
		private FlatLabel ladresse = new FlatLabel("Adresse: ");
		private FlatField tnom = new FlatField();
		private FlatField tprenom = new FlatField();
		private FlatField tnatel = new FlatField();
		private FlatField ttelephone = new FlatField();
		private FlatField tmail = new FlatField();
		private FlatField tadresse = new FlatField();
		
		//boutons possibles pour cahque état
		private FlatButton save = new FlatButton("enregistrer");
		private FlatButton modify = new FlatButton("modifier");
		private FlatButton update = new FlatButton("modifier");
		
		//gestion des panels
		private JPanel imagePanel = new JPanel();
		private JPanel infoPanel = new JPanel();
		private String imagePath="image/icon/contactIcon.png";
		
		//Listener pour l'image
		private MouseListener mouselistener = new MouseListener() 
		{
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				galerieapp=new GalerieApp();
				galerie=galerieapp.getScroll();
				
				for (int i = 0; i < galerieapp.getBoutonsIcons().size(); i++) 
				{
					galerieapp.getBoutonsIcons().get(i).addMouseListener(new GetURL());
				}
				
				myPanel.add(galerie, "galerie");
				cardlayout.show(myPanel, "galerie");
			}
		};
		
		NewContact()
		{
			setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
			
			//image de contact
			imagePanel.setMaximumSize(new Dimension(480,200));
			imagePanel.setMinimumSize(new Dimension(480,200));
			imagePanel.setPreferredSize(new Dimension(480,200));
			imagePanel.setBackground(Color.DARK_GRAY);
			imagePanel.add(new JLabel(new ImageIcon(imagePath)));
			add(imagePanel);
			
			//panel avec infos
			infoPanel.setLayout(new GridLayout(7,2));
			infoPanel.add(lnom);
			infoPanel.add(tnom);
			infoPanel.add(lprenom);
			infoPanel.add(tprenom);
			infoPanel.add(lnatel);
			infoPanel.add(tnatel);
			infoPanel.add(ltelephone);
			infoPanel.add(ttelephone);
			infoPanel.add(lmail);
			infoPanel.add(tmail);
			infoPanel.add(ladresse);
			infoPanel.add(tadresse);
			
			infoPanel.add(new JLabel());
			infoPanel.setMaximumSize(new Dimension(400,250));
			infoPanel.setMinimumSize(new Dimension(400,250));
			infoPanel.setPreferredSize(new Dimension(400,250));
			add(infoPanel);
			
			//listeners das boutons possibles
			save.addActionListener(new addContact());
			modify.addActionListener(new ShowModify());
			update.addActionListener(new addContact());
			update.addActionListener(new Erase());
		}
		
	}
	
	/**
	 * action pour recuperer URL de la galerie photo
	 * @author jcfds
	 */
	class GetURL implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0) 
		{
			for (int i = 0; i < galerieapp.getBoutonsIcons().size(); i++) 
			{
				if(galerieapp.getBoutonsIcons().get(i)==arg0.getSource()) 
				{
					newcontact.imagePath=galerieapp.getBoutonsIcons().get(i).getPathPhoto();
					break;
				}
			}
			newcontact.imagePanel.removeAll();
			newcontact.imagePanel.add(new JLabel(Resizable.resizePhotoRatio(480, 200, new ImageIcon(newcontact.imagePath))));
			cardlayout.show(myPanel, "new");
		}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}	
	}
	
	class ShowAddContact implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			newcontact.tnom.setText("");
			newcontact.tnom.setEditable(true);
			newcontact.tnom.setBorder(null);
			newcontact.tprenom.setText("");
			newcontact.tprenom.setEditable(true);
			newcontact.tprenom.setBorder(null);
			newcontact.tnatel.setText("");
			newcontact.tnatel.setEditable(true);
			newcontact.tnatel.setBorder(null);
			newcontact.ttelephone.setText("");
			newcontact.ttelephone.setEditable(true);
			newcontact.tmail.setText("");
			newcontact.tmail.setEditable(true);
			newcontact.tadresse.setText("");
			newcontact.tadresse.setEditable(true);
			
			buttonHolder.removeAll();
			
			//choix des boutons a afficher
			newcontact.infoPanel.remove(newcontact.modify);
			newcontact.infoPanel.remove(newcontact.update);
			newcontact.infoPanel.add(newcontact.save);
			
			//ajouter le listenner a l'image
			newcontact.imagePanel.removeAll();
			newcontact.imagePanel.add(new JLabel(new ImageIcon("image/icon/contactIcon.png")));
			newcontact.imagePanel.addMouseListener(newcontact.mouselistener);
			newcontact.imagePath="image/icon/contactIcon.png";
			cardlayout.show(myPanel, "new");
		}
		
	}
	
	
	class ReturnMain implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			buttonHolder.removeAll();
			buttonHolder.add(add);
			updateUI();
			cardlayout.show(myPanel, "main");
		}
	}
	
	private boolean onlyContainsNumbers(String text) {
	    try {
	        Long.parseLong(text);
	        return true;
	    } catch (NumberFormatException ex) {
	        return false;
	    }
	}
	
	/**
	 * classe va ajouter le contact qu'on a créé dans le bon androit en ordre alphabetiquement
	 * teste si le numero c'est bien des numeros et qu'il y ait un nom ou prenom
	 * @author jcfds
	 */
	class addContact implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			
			if(!(onlyContainsNumbers(newcontact.tnatel.getText())) && !(newcontact.tnatel.getText().equals("")))
			{
				newcontact.tnatel.error();
				return;
			}
			
			if(!(onlyContainsNumbers(newcontact.ttelephone.getText())) && !(newcontact.ttelephone.getText().equals("")))
			{
				newcontact.ttelephone.error();
				return;
			}
	
			if((newcontact.tnom.getText()+newcontact.tprenom.getText()).equals(""))
			{
				newcontact.tnom.error();
				newcontact.tprenom.error();
				return;
			}
				
				
			Contact p1 = new Contact(newcontact.tnom.getText(), newcontact.tprenom.getText(), newcontact.tnatel.getText(), newcontact.ttelephone.getText(),
										newcontact.tmail.getText(), newcontact.tadresse.getText(),newcontact.imagePath);
			
			maincontact.removeAll();
			
		//ajouter le nouveau contact au bon endroit alphabetiquement
			String newPers=newcontact.tnom.getText() +" "+ newcontact.tprenom.getText();
			int next=0;
			boolean last=true;
			
			if (contacts.size()>0) 
			{
				for (int j = 0; j < contacts.size(); j++) 
				{
					last=true;
					if(newPers.compareToIgnoreCase(contacts.get(j).getNom()+" "+contacts.get(j).getPrenom())<0) 
					{
						next=j;
						last=false;//si je trouve un négatif c'est que le nouveau contact n'est pas dernier
						break;
					}
				}
				
				if(last)
					contacts.add(contacts.size(),p1);
				else
					contacts.add(next,p1);
			}
			else
				contacts.add(next,p1);
			
			p1.getButton().addMouseListener(new GetInfo());
			
			
			newcontact.tnom.setText("");
			newcontact.tprenom.setText("");
			newcontact.tnatel.setText("");
			newcontact.ttelephone.setText("");
			newcontact.tmail.setText("");
			newcontact.tadresse.setText("");
			
			serializeContacts();
			maincontact.paint();
			insertLabels();
			
			buttonHolder.removeAll();
			buttonHolder.add(add);
			updateUI();
			cardlayout.show(myPanel, "main");
		}
	}
	
	class GetInfo implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			int selected=0;
			
			for (int i = 0; i < contacts.size(); i++) 
			{
				if(e.getSource()==contacts.get(i).getButton()) 
				{
					selected=i;
					break;
				}
			}
				enabled=selected;
				newcontact.tnom.setText(contacts.get(selected).getNom());
				newcontact.tnom.setEditable(false);
				newcontact.tprenom.setText(contacts.get(selected).getPrenom());
				newcontact.tprenom.setEditable(false);
				newcontact.tnatel.setText(contacts.get(selected).getNatel());
				newcontact.tnatel.setEditable(false);
				newcontact.ttelephone.setText(contacts.get(selected).getTelephone());
				newcontact.ttelephone.setEditable(false);
				newcontact.tmail.setText(contacts.get(selected).getMail());
				newcontact.tmail.setEditable(false);
				newcontact.tadresse.setText(contacts.get(selected).getAdresse());
				newcontact.tadresse.setEditable(false);
				newcontact.imagePath=contacts.get(selected).getPhotoPath();
				newcontact.imagePanel.removeAll();
				ImageIcon image = new ImageIcon(contacts.get(selected).getPhotoPath());
				image=Resizable.resizePhotoRatio(480, 200, image);
				newcontact.imagePanel.add(new JLabel(image));
				newcontact.imagePanel.removeMouseListener(newcontact.mouselistener);
				
				newcontact.infoPanel.remove(newcontact.update);
				newcontact.infoPanel.remove(newcontact.save);
				newcontact.infoPanel.add(newcontact.modify);
				
				buttonHolder.removeAll();
				buttonHolder.add(delete);
				updateUI();
				cardlayout.show(myPanel, "new");
		}
		@Override
		public void mouseEntered(MouseEvent e) 
		{
			for (int i = 0; i < contacts.size(); i++) 
			{
				if(e.getSource()==contacts.get(i).getButton()) 
				{
					contacts.get(i).getButton().setBackground(Color.GRAY);
					break;
				}
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			for (int i = 0; i < contacts.size(); i++) {
				if(e.getSource()==contacts.get(i).getButton()) {
					contacts.get(i).getButton().setBackground(Color.WHITE);
					break;
				}
			}
		}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
	
	
	class ShowModify implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			newcontact.tnom.setEditable(true);
			newcontact.tnom.setBorder(null);
			newcontact.tprenom.setEditable(true);
			newcontact.tprenom.setBorder(null);
			newcontact.tnatel.setEditable(true);
			newcontact.tnatel.setBorder(null);
			newcontact.ttelephone.setEditable(true);
			newcontact.tmail.setEditable(true);
			newcontact.tadresse.setEditable(true);
			newcontact.imagePanel.addMouseListener(newcontact.mouselistener);
			newcontact.infoPanel.remove(newcontact.modify);
			newcontact.infoPanel.add(newcontact.update);
			
			buttonHolder.removeAll();
		}
	}
	
	/**
	 * quand on rajoute une lettre dans la recherche 
	 * il cherche ou existe le substring
	 * @author jcfds
	 *
	 */
	class Recherche implements KeyListener
	{
		public void keyPressed(KeyEvent arg0) {}
		public void keyReleased(KeyEvent arg0) 
		{
			maincontact.liste.removeAll();
			
			String sub = maincontact.recherche.getText().toLowerCase();
			
			for (int i = 0; i < contacts.size(); i++) 
			{
				if((contacts.get(i).getNom()+contacts.get(i).getPrenom()).toLowerCase().indexOf(sub)!=-1) 
				{
					maincontact.liste.add(contacts.get(i).getButton());
				}
			}
			maincontact.scroll.repaint();
			maincontact.recherche.requestFocus();
		}
		public void keyTyped(KeyEvent arg0) {}
	}
	
	/**
	 * pour effacer un contact
	 * @author jcfds
	 */
	class Erase implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			
			if(!(onlyContainsNumbers(newcontact.tnatel.getText())) && !(newcontact.tnatel.getText().equals("")))
				return;
			if(!(onlyContainsNumbers(newcontact.ttelephone.getText())) && !(newcontact.ttelephone.getText().equals("")))
				return;
			if(((newcontact.tnom.getText()+newcontact.tprenom.getText()).equals("")))
				return;
			
			maincontact.liste.remove(contacts.get(enabled).getButton());
			contacts.remove(enabled);
			serializeContacts();
			maincontact.scroll.repaint();
		}
	}
}
