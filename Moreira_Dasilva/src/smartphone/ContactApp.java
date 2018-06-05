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

import GUI.composants.MonButton;
import GUI.composants.MonField;
import GUI.composants.MonLabel;
import GUI.composants.Resizable;


public class ContactApp extends AppTemplate implements Resizable{
	
	private ArrayList<Contact> contacts = new ArrayList<Contact>();
	
	//cardLayout management
	private CardLayout cardlayout = new CardLayout();
	private JPanel myPanel = new JPanel();
	private MainContact maincontact = new MainContact();
	private NewContact newcontact = new NewContact();
	
	//pour ouvrir la galerie
	private GalerieApp galerieapp;
	private JPanel galerie;
	
	//boutons pour NavigationBar
	private MonButton add = new MonButton (new ImageIcon("image/icon/Plusicon.png"),new ImageIcon("image/icon/PlusiconHOVER.png"));
	private MonButton delete = new MonButton (new ImageIcon("image/icon/delete.png"),new ImageIcon("image/icon/deleteHOVER.png"));
	private JPanel buttonHolder = new JPanel();
	
	//icones d'appli
	private ImageIcon contactIcon =new ImageIcon("image/icon/contact.png");
	private ImageIcon contactIconHover =new ImageIcon("image/icon/contactHOVER.png");
	
	//gerer quel contact est activ�
	private int enabled;
	
	public ContactApp() {
		super("contacts", Color.GREEN);
		maincontact.setOpaque(false);
		newcontact.setOpaque(false);
		
		delete.addActionListener(new returnMain());
		delete.addActionListener(new erase());
		add.addActionListener(new showAddContact());
		buttonHolder.setOpaque(false);
		super.getNavigation().add(buttonHolder, BorderLayout.EAST);
		super.getNavigation().getBackButton().addActionListener(new returnMain());
		buttonHolder.add(add);
		
		myPanel.setLayout(cardlayout);
		myPanel.add(maincontact, "main");
		myPanel.add(newcontact, "new");
		add(myPanel);
		
		deserializeContacts();
		for (int i = 0; i < contacts.size(); i++) {
			contacts.get(i).getButton().addMouseListener(new getInfo());
		}
		insertLabels();
	}
	
	public ImageIcon getContactIcon() {
		return contactIcon;
	}

	public ImageIcon getContactIconHover() {
		return contactIconHover;
	}
	
	private void insertLabels() {//rajoute un panel a liste pour chaque contact trouv�
		for (int i = 0; i < contacts.size(); i++) {
			contacts.get(i).getImageLabel().setIcon(Resizable.resizePhotoIcon(50, new ImageIcon(contacts.get(i).getPhotoPath())));
			maincontact.liste.add(contacts.get(i).getButton());
		}
	}

	public void deserializeContacts() { //affiche tous les panels dans l'appli
		try {
			FileInputStream fichier;
			fichier = new FileInputStream("serials/contacts.ser");
			BufferedInputStream bfichier=new BufferedInputStream(fichier);
			ObjectInputStream obfichier = new ObjectInputStream(bfichier);
			this.contacts = (ArrayList<Contact>) obfichier.readObject();
			obfichier.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void serializeContacts() {
		try {
			FileOutputStream fichier;
			fichier = new FileOutputStream("serials/contacts.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fichier);
			oos.writeObject(contacts);
			oos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	class MainContact extends JPanel{//le panel d'affichage de tous les contacts
		private JTextField recherche = new JTextField();
		private JPanel liste = new JPanel();
		private JScrollPane scroll = new JScrollPane(liste,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		MainContact(){
			paint();
		}

		public  void paint() {
			liste.setLayout(new BoxLayout(liste, BoxLayout.Y_AXIS));//boxLayout vertical a ma liste de contacts
			liste.setBackground(Color.WHITE);			
			
			setLayout(new BorderLayout());
			recherche.addKeyListener(new Recherche());
			add(recherche,BorderLayout.BEFORE_FIRST_LINE);
			
			add(scroll, BorderLayout.CENTER);
		}	
	}
	
	class NewContact extends JPanel{//panel pour inserer un nouveau contact
		
		//display infos
		private MonLabel lnom = new MonLabel("Nom: ");
		private MonLabel lprenom = new MonLabel("Prenom: ");
		private MonLabel lnatel = new MonLabel("Num�ro natel: ");
		private MonLabel ltelephone = new MonLabel("Num�ro t�l�phone: ");
		private MonLabel lmail = new MonLabel("Mail: ");
		private MonLabel ladresse = new MonLabel("Adresse: ");
		private MonField tnom = new MonField();
		private MonField tprenom = new MonField();
		private MonField tnatel = new MonField();
		private MonField ttelephone = new MonField();
		private MonField tmail = new MonField();
		private MonField tadresse = new MonField();
		
		//boutons possibles pour cahque �tat
		private MonButton save = new MonButton("enregistrer");
		private MonButton modify = new MonButton("modifier");
		private MonButton update = new MonButton("modifier");
		
		//gestion des panels
		private JPanel imagePanel = new JPanel();
		private JPanel infoPanel = new JPanel();
		private String imagePath="image/icon/contactIcon.png";
		
		//Listener pour l'image
		private MouseListener mouselistener = new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				galerieapp=new GalerieApp();
				galerie=galerieapp.getGalerie();
				
				for (int i = 0; i < galerieapp.getBoutonsIcons().size(); i++) {
					galerieapp.getBoutonsIcons().get(i).addMouseListener(new getURL());
				}
				myPanel.add(galerie, "galerie");
				cardlayout.show(myPanel, "galerie");
			}
		};
		
		NewContact(){
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
			modify.addActionListener(new showModify());
			update.addActionListener(new addContact());
			update.addActionListener(new erase());
		}
		
	}
	
	class getURL implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent arg0) {
			for (int i = 0; i < galerieapp.getBoutonsIcons().size(); i++) {
				if(galerieapp.getBoutonsIcons().get(i)==arg0.getSource()) {
					newcontact.imagePath=galerieapp.getBoutonsIcons().get(i).getPathPhoto();
					break;
				}
			}
			newcontact.imagePanel.removeAll();
			newcontact.imagePanel.add(new JLabel(Resizable.resizePhotoRatio(480, 200, new ImageIcon(newcontact.imagePath))));
			cardlayout.show(myPanel, "new");
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		@Override
		public void mousePressed(MouseEvent arg0) {}
		@Override
		public void mouseReleased(MouseEvent arg0) {}

		
	}
	class showAddContact implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			newcontact.tnom.setText("");
			newcontact.tnom.setEditable(true);
			newcontact.tprenom.setText("");
			newcontact.tprenom.setEditable(true);
			newcontact.tnatel.setText("");
			newcontact.tnatel.setEditable(true);
			
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
	
	
	class returnMain implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			buttonHolder.removeAll();
			buttonHolder.add(add);
			updateUI();
			cardlayout.show(myPanel, "main");
		}
		
	}
	
	
	class addContact implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Contact p1 = new Contact(newcontact.tnom.getText(), newcontact.tprenom.getText(), newcontact.tnatel.getText(), newcontact.ttelephone.getText(),
										newcontact.tmail.getText(), newcontact.tadresse.getText(),newcontact.imagePath);
			
			maincontact.removeAll();
			
		//ajouter le nouveau contact au bon endroit alphabetiquement
			String newPers=newcontact.tnom.getText() +" "+ newcontact.tprenom.getText();
			int next=0;
			boolean last=true;
			
			if (contacts.size()>0) {
				for (int j = 0; j < contacts.size(); j++) {
					last=true;
					if(newPers.compareToIgnoreCase(contacts.get(j).getNom()+" "+contacts.get(j).getPrenom())<0) {
						next=j;
						last=false;//si je trouve un n�gatif c'est que le nouveau contact n'est pas dernier
						break;
					}
				}
				
				if(last) {
					contacts.add(contacts.size(),p1);
				}
				else {
					contacts.add(next,p1);
				}
			}
			else
				contacts.add(next,p1);
			
			p1.getButton().addMouseListener(new getInfo());
			
			
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
	
	class getInfo implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			
			int selected=0;
			
			for (int i = 0; i < contacts.size(); i++) {
				if(e.getSource()==contacts.get(i).getButton()) {
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
				newcontact.imagePath=contacts.get(selected).photoPath;
				newcontact.imagePanel.removeAll();
				ImageIcon image = new ImageIcon(contacts.get(selected).photoPath);
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
		public void mouseEntered(MouseEvent e) {
			for (int i = 0; i < contacts.size(); i++) {
				if(e.getSource()==contacts.get(i).getButton()) {
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

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}
	}
	
	class showModify implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			newcontact.tnom.setEditable(true);
			newcontact.tprenom.setEditable(true);
			newcontact.tnatel.setEditable(true);
			newcontact.ttelephone.setEditable(true);
			newcontact.tmail.setEditable(true);
			newcontact.tadresse.setEditable(true);
			newcontact.imagePanel.addMouseListener(newcontact.mouselistener);
			newcontact.infoPanel.remove(newcontact.modify);
			newcontact.infoPanel.add(newcontact.update);
			
			buttonHolder.removeAll();
		}
		
	}
	
	class Recherche implements KeyListener{

		@Override
		public void keyPressed(KeyEvent arg0) {}

		@Override
		public void keyReleased(KeyEvent arg0) {
			maincontact.liste.removeAll();
			
			String sub = maincontact.recherche.getText().toLowerCase();
			
			for (int i = 0; i < contacts.size(); i++) {
				if((contacts.get(i).getNom()+contacts.get(i).getPrenom()).toLowerCase().indexOf(sub)!=-1) {
					maincontact.liste.add(contacts.get(i).getButton());
				}
			}
			maincontact.scroll.repaint();
			maincontact.recherche.requestFocus();
		}

		@Override
		public void keyTyped(KeyEvent arg0) {}
		
	}
	
	
	class erase implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			maincontact.liste.remove(contacts.get(enabled).getButton());
			

			contacts.remove(enabled);
			serializeContacts();
			
			maincontact.scroll.repaint();
		}
	}

}
