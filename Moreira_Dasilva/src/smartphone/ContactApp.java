/**
* TP Week2
*Author: Joao Silva
*Date creation : 22 mai 2018
*/
package smartphone;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ContactApp extends AppTemplate{
	
	private ArrayList<Contact> contacts = new ArrayList<Contact>();
	private ArrayList<JPanel> contactPanel= new ArrayList<JPanel>();
	
	//cardLayout manager
	private CardLayout cardlayout = new CardLayout();
	private JPanel myPanel = new JPanel();
	private MainContact maincontact = new MainContact();
	private NewContact newcontact = new NewContact();
	private ContactInfo contactinfo = new ContactInfo();
	
	private JButton add = new JButton ("+");
	private ImageIcon contactIcon =new ImageIcon("image/icon/contact.png");
	private ImageIcon contactIconHover =new ImageIcon("image/icon/contactHOVER.png");
	
	//for contactInfo
	
	public ImageIcon getContactIcon() {
		return contactIcon;
	}

	public ImageIcon getContactIconHover() {
		return contactIconHover;
	}

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
		CreateLabels();
		insertLabels();
	}
	
	private void insertLabels() {//rajoute un panel a l'arraylist pour chaque contact trouvé
		for (int i = 0; i < contactPanel.size(); i++) {
			maincontact.add(contactPanel.get(i));
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
			System.out.println("serialization");
			FileOutputStream fichier;
			fichier = new FileOutputStream("serials/contacts.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fichier);
			oos.writeObject(contacts);
			oos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private void CreateLabels() {//rajoute un panel a l'arraylist pour chaque contact trouvé
		for (int i = 0; i < contacts.size(); i++) {
			JPanel panel = new JPanel();
			JLabel label = new JLabel(contacts.get(i).getNom()+" "+contacts.get(i).getPrenom());
			label.setBackground(Color.RED);
			label.addMouseListener(new getInfo());
			panel.add(label);
			contactPanel.add(panel);
		}
	}
	
	class MainContact extends JPanel{//le panel d'affichage de tous les contacts
		
		MainContact(){
			paint();
		}
		
		public  void paint() {
			setLayout(new BoxLayout(this,1));
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
	
	class getInfo implements MouseListener{//affiche le contact pour la case correspondante

		@Override
		public void mouseClicked(MouseEvent arg0) {
		int id=0;
		
		for (int i = 0; i < contactPanel.size(); i++) {
			if(contactPanel.get(i)==arg0.getSource()) {
				id=i;
				break;
			}
		}
		
			contactinfo.lnom1.setText("Nom: " + contacts.get(id).getNom());
			contactinfo.lprenom1.setText("Prenom: " + contacts.get(id).getPrenom());
			contactinfo.lnumero1.setText("Numero: " + contacts.get(id).getNumero());
			cardlayout.show(myPanel, "info");
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
			contactPanel= new ArrayList<JPanel>();	
			
			maincontact.removeAll();
			contacts.add(p1);//l'ajouter le contact a la fin
			
			Contact[] sort = new Contact[contacts.size()];
			
//			for(char i='a'; 'a'<='z';i++) {
//				
//			}
			
			serializeContacts();
			CreateLabels();
			insertLabels();
			maincontact.paint();
			cardlayout.show(myPanel, "main");
		}
		
	}

}
