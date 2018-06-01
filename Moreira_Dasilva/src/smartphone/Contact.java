/**
* TP Week2
*Author: Joao Silva
*Date creation : 22 mai 2018
*/
package smartphone;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import javax.swing.*;
import javax.swing.border.LineBorder;

/* a ajouter:
 * long click insert cercle dans la 3e case du panel et si on click il devient vert et bool selected devienttrue
 * 	apres le long click le bouton plus change et on met celui de la poubelle qui efface tous ceux que selected=true
 * 
 * set image change l'image de base en une choisie
 * avec un new galerie et ActionListenner dans chaque image pour l'ajouter au contact et fermer galerie
 * 
 * modify contact avec le new contact qui s'ouvre mais avec les champs remplis
 * 
 * tests si pas num valide ou champs obligatoires vides le textField devient rouge jusqu'a ce qu'on clique dessous
 */

public class Contact implements Serializable{

	String nom;
	String prenom;
	String natel;
	String telephone;
	String mail;
	String adresse;
	ImageIcon photo = new ImageIcon("image/icon/backicon.png");
	JPanel button=new JPanel();
	JButton erase = new JButton();
	boolean selected = false;
	
	public Contact(String nom, String prenom, String natel, String telephone,String mail,String adresse, ImageIcon photo) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.natel = natel;
		this.telephone=telephone;
		this.mail=mail;
		this.adresse=adresse;
		this.photo=photo;
		erase.setIcon(new ImageIcon("image/icon/delete.png"));
		createButton();
	}

	private void createButton() {
		button.setLayout(new GridLayout(0,3));
		button.add(new JLabel(photo));
		button.add(new JLabel(nom+" "+prenom));
		button.setBackground(Color.WHITE);
		button.setBorder(new LineBorder(Color.GRAY));
		button.setPreferredSize(new Dimension(485, 55));
		button.setMinimumSize(new Dimension(485, 55));
		button.setMaximumSize(new Dimension(485, 55));
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNatel() {
		return natel;
	}

	public void setNatel(String natel) {
		this.natel = natel;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public ImageIcon getPhoto() {
		return photo;
	}

	public void setPhoto(ImageIcon photo) {
		this.photo = photo;
	}

	public JPanel getButton() {
		return button;
	}

	public void setButton(JPanel button) {
		this.button = button;
	}

	
	
}

