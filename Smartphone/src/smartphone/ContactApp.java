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

public class ContactApp extends AppTemplate{
	
	private ArrayList<Contact> contacts = new ArrayList<Contact>();
	
	//cardLayout manager
	private CardLayout cardlayout = new CardLayout();
	private JPanel myPanel = new JPanel();
	private MainContact maincontact = new MainContact();
	private NewContact newcontact = new NewContact();
	private ContactInfo contactinfo = new ContactInfo();
	
	private JButton add = new JButton ("+");
	private ImageIcon contactIcon =new ImageIcon("image/icon/contact.png");
	private ImageIcon contactIconHover =new ImageIcon("image/icon/contactHOVER.png");
	private Timer timer = new Timer(2000,new Selection());
	
	public ContactApp() {
		super("contacts", Color.GREEN);
		
		add.addActionListener(new showAddContact());
		super.getNavigation().add(add, BorderLayout.EAST);
		
		//manage CardLayout avec tous les panels possibles
		myPanel.setLayout(cardlayout);
		myPanel.add(maincontact, "main");
		myPanel.add(newcontact, "new");
		myPanel.add(contactinfo, "info");
		add(myPanel);
		
		deserializeContacts();
		for (int i = 0; i < contacts.size(); i++) {
			contacts.get(i).getbutton().addMouseListener(new getInfo());
		}
		insertLabels();
	}
	
	public ImageIcon getContactIcon() {
		return contactIcon;
	}

	public ImageIcon getContactIconHover() {
		return contactIconHover;
	}
	
	private void insertLabels() {//rajoute un panel a l'arraylist pour chaque contact trouvé
		for (int i = 0; i < contacts.size(); i++) {
			maincontact.liste.add(contacts.get(i).getbutton());
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
		private JScrollPane scroll = new JScrollPane(liste,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
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
		private JLabel lnom = new JLabel("Nom: ");
		private JLabel lprenom = new JLabel("Prenom: ");
		private JLabel lnumero = new JLabel("Numéro: ");
		private JTextField tnom = new JTextField();
		private JTextField tprenom = new JTextField();
		private JTextField tnumero = new JTextField();
		private JButton save = new JButton("save");
		
		NewContact(){
			setLayout(new GridLayout(4,2));
			add(lnom);
			add(tnom);
			add(lprenom);
			add(tprenom);
			add(lnumero);
			add(tnumero);
			add(new JLabel());
			save.addActionListener(new addContact());
			add(save);
		}
		
	}
	
	class ContactInfo extends JPanel{//panel pour afficher l'info d'un contact
		private JLabel lnom1=new JLabel();
		private JLabel lprenom1=new JLabel();
		private JLabel lnumero1=new JLabel();
		private JButton returnToMain = new JButton("retour");
		
		ContactInfo(){
			setLayout(new BoxLayout(this,1));
			add(lnom1);
			add(lprenom1);
			add(lnumero1);
			returnToMain.addActionListener(new returnMain());
			add(returnToMain);
		}
		
	}
	
	class showAddContact implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			cardlayout.show(myPanel, "new");
		}
		
	}
	
	
	class returnMain implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			cardlayout.show(myPanel, "main");
		}
		
	}
	
	class addContact implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Contact p1 = new Contact(newcontact.tnom.getText(), newcontact.tprenom.getText(), newcontact.tnumero.getText());
			
			maincontact.removeAll();
			
		//ajouter le nouveau contact au bon endroit alphabetiquement
			String newPers=newcontact.tnom.getText() + newcontact.tprenom.getText();
			int next=0;
			boolean last=true;
			
			if (contacts.size()>0) {
				for (int j = 0; j < contacts.size(); j++) {
					last=true;
					if(newPers.compareToIgnoreCase(contacts.get(j).getNom()+contacts.get(j).getPrenom())<0) {
						next=j;
						last=false;//si je trouve un négatif c'est que le nouveau contact n'est pas dernier
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
			
			p1.getbutton().addMouseListener(new getInfo());
			
			
			newcontact.tnom.setText("");
			newcontact.tprenom.setText("");
			newcontact.tnumero.setText("");
			
			serializeContacts();
			maincontact.paint();
			insertLabels();
			cardlayout.show(myPanel, "main");
		}
		
	}
	
	class getInfo implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			
			Contact contact=null;
			
			for (int i = 0; i < contacts.size(); i++) {
				if(e.getSource()==contacts.get(i).getbutton()) {
					contact=contacts.get(i);
					break;
				}
			}
			
			contactinfo.lnom1.setText(contact.getNom());
			contactinfo.lprenom1.setText(contact.getPrenom());
			contactinfo.lnumero1.setText(contact.getNumero());
			cardlayout.show(myPanel, "info");
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {
			timer.start();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			timer.stop();
		}
		
	}
	
	class Selection implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			for (int i = 0; i < contacts.size(); i++) {
				JPanel panel=contacts.get(i).getbutton();
				panel.add(new JLabel(new ImageIcon("image/icon/backicon.png")));
				contacts.get(i).setbutton(panel);
			}
			cardlayout.show(myPanel, "main");
		}
		
	}
	
	class Recherche implements KeyListener{

		@Override
		public void keyPressed(KeyEvent arg0) {}

		@Override
		public void keyReleased(KeyEvent arg0) {
			maincontact.liste.removeAll();
			
			String sub = maincontact.recherche.getText();
			
			for (int i = 0; i < contacts.size(); i++) {
				if(contacts.get(i).getNom().indexOf(sub)!=-1) {
					maincontact.liste.add(contacts.get(i).getbutton());
				}
			}
			maincontact.scroll.repaint();
			maincontact.recherche.requestFocus();
		}

		@Override
		public void keyTyped(KeyEvent arg0) {}
		
	}

}
