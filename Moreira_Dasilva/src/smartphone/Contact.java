/**
* TP Week2
*Author: Joao Silva
*Date creation : 22 mai 2018
*/
package smartphone;

import java.io.Serializable;

public class Contact implements Serializable{

	String nom;
	String prenom;
	String numero;
	
	public Contact(String nom, String prenom, String numero) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.numero = numero;
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

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
}

