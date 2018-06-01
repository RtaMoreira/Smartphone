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
	
	//cardLayout management
	private CardLayout cardlayout = new CardLayout();
	private JPanel myPanel = new JPanel();
	private MainContact maincontact = new MainContact();
	private NewContact newcontact = new NewContact();
	
	//boutons pour NavigationBar
	private JButton add = new JButton ("+");
	private JButton delete = new JButton ("d");
	private JPanel buttonHolder = new JPanel();
	
	//icones d'appli
	private ImageIcon contactIcon =new ImageIcon("image/icon/contact.png");
	private ImageIcon contactIconHover =new ImageIcon("image/icon/contactHOVER.png");
	
	//gerer quel contact est activé
	private int enabled;
	
	public ContactApp() {
		super("contacts", Color.GREEN);
		
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
	
	private void insertLabels() {//rajoute un panel a liste pour chaque contact trouvé
		for (int i = 0; i < contacts.size(); i++) {
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
		private JLabel lnom = new JLabel("Nom: ");
		private JLabel lprenom = new JLabel("Prenom: ");
		private JLabel lnatel = new JLabel("Numéro natel: ");
		private JLabel ltelephone = new JLabel("Numéro téléphone: ");
		private JLabel lmail = new JLabel("Mail: ");
		private JLabel ladresse = new JLabel("Adresse: ");
		private JTextField tnom = new JTextField();
		private JTextField tprenom = new JTextField();
		private JTextField tnatel = new JTextField();
		private JTextField ttelephone = new JTextField();
		private JTextField tmail = new JTextField();
		private JTextField tadresse = new JTextField();
		
		//boutons possibles pour cahque état
		private JButton save = new JButton("enregistrer");
		private JButton modify = new JButton("modifier");
		private JButton update = new JButton("modifier");
		
		//gestion des panels et espaces vides
		private JPanel imagePanel = new JPanel();
		private JPanel infoPanel = new JPanel();
		private ImageIcon image=new ImageIcon("image/icon/backicon.png");
		
		NewContact(){
			setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
			
			//image de contact
			imagePanel.setMaximumSize(new Dimension(200,200));
			imagePanel.setMinimumSize(new Dimension(200,200));
			imagePanel.setPreferredSize(new Dimension(200,200));
			imagePanel.add(new JLabel(image));
/**			ImageChoice mouselistener = new ImageChoice();
*/			add(imagePanel);
			
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
			add(infoPanel);
			
			//listeners das boutons possibles
			save.addActionListener(new addContact());
			modify.addActionListener(new showModify());
			update.addActionListener(new addContact());
			update.addActionListener(new erase());
			
			add(new JPanel());
		}
		
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
/**			newcontact.image.addMouseListener(newcontact.mouselistener);
 * 
*/			cardlayout.show(myPanel, "new");
		}
		
	}
	
	
	class returnMain implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			buttonHolder.removeAll();
			buttonHolder.add(add);
			cardlayout.show(myPanel, "main");
		}
		
	}
	
	
	class addContact implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Contact p1 = new Contact(newcontact.tnom.getText(), newcontact.tprenom.getText(), newcontact.tnatel.getText(), newcontact.ttelephone.getText(),
										newcontact.tmail.getText(), newcontact.tadresse.getText(),newcontact.image);
			
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
			
			System.out.println(p1.getNom()+"added");
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
				System.out.println("enabled in getInfo: "+selected);
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
				newcontact.image=contacts.get(selected).photo;
	/**			newcontact.image.removeMouseListenner(mouseListener);
	*/			
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
/**			newcontact.image.addMouseListenner(newcontact.mouselistener);
*/			newcontact.infoPanel.remove(newcontact.modify);
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
			System.out.println(contacts.get(enabled).getNom()+"deleted from liste");
			

			System.out.println(contacts.get(enabled).getNom()+"deleted from contacts");
			contacts.remove(enabled);
			serializeContacts();
			
			maincontact.scroll.repaint();
		}
	}

}
