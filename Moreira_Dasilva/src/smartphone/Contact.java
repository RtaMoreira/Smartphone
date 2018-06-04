/**
* TP Week2
*Author: Joao Silva
*Date creation : 22 mai 2018
*/
package smartphone;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.Serializable;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Contact implements Serializable{

	String nom;
	String prenom;
	String natel;
	String telephone;
	String mail;
	String adresse;
	String photoPath;
	JPanel button=new JPanel();
	JLabel imageLabel = new JLabel();
	boolean selected = false;
	
	public Contact(String nom, String prenom, String natel, String telephone,String mail,String adresse, String photoPath) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.natel = natel;
		this.telephone=telephone;
		this.mail=mail;
		this.adresse=adresse;
		this.photoPath=photoPath;
		createButton();
	}

	private void createButton() {
		button.setLayout(new GridLayout(0,3));
		imageLabel.setIcon(new ImageIcon(photoPath));
		button.add(imageLabel);
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
	
	public JLabel getImageLabel() {
		return imageLabel;
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

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhoto(String photoPath) {
		this.photoPath = photoPath;
	}

	public JPanel getButton() {
		return button;
	}

	public void setButton(JPanel button) {
		this.button = button;
	}

	
	
}

