/**
* TP Week2
*Author: Joao Silva
*Date creation : 28 mai 2018
*/
package smartphone;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Demineur extends JPanel implements ActionListener{

	int[][] cases = new int[10][10];
	JButton[][] boutons = new JButton[cases.length][cases[0].length];
	int nbBombes=5;
	
	Demineur() {
		bombes();
		numeros();
		setLayout(new GridLayout(cases.length,cases[0].length));
		for (int i = 0; i < boutons.length; i++) {
			for (int j = 0; j < boutons[0].length; j++) {
				boutons[i][j]=new JButton();
				boutons[i][j].addActionListener(this);
				add(boutons[i][j]);
			}
		}
		for (int i = 0; i < cases.length; i++) {
			for (int j = 0; j < cases[0].length; j++) {
				System.out.print(cases[i][j]);
			}
			System.out.println();
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		for (int i = 0; i < boutons.length; i++) {
			for (int j = 0; j < boutons[0].length; j++) {
				if (arg0.getSource()==boutons[i][j]) {
					boutons[i][j].setEnabled(false);
					if(cases[i][j]>0)
						boutons[i][j].setText(String.valueOf(cases[i][j]));
					if(cases[i][j]==9)
						boutons[i][j].setText("*");
					if(cases[i][j]==0) {
					}
				}
			}
		}
	}
	
	private void bombes() {
		for (int i = 0; i<nbBombes; i++)
		{
			//tirage aléatoire d'une ligne et d'une colonne pour mettre les bombes
			int ligne = (int) ((cases.length)*Math.random());
			int colonne = (int) ((cases[0].length)*Math.random());
			do
			{
				//Nouveau tirage aléatoire si dans la case du premier tirage il y a déjà une bombe
				ligne = (int) ((cases.length)*Math.random());
				colonne = (int) ((cases[0].length)*Math.random());
			}
			while (cases[ligne][colonne] == 9); //Tant qu'il y a une bombe dans la case
			cases[ligne][colonne] = 9; //Affectation d'une bombe dans la case
		}
	}
	
	private void numeros() {
		for (int i = 0; i < cases.length; i++) {
			for (int j = 0; j < cases[0].length; j++) {
				if(cases[i][j]==9) {//quand on trouve une bombe
					for (int k = i-1; k <=(i+1); k++) {//on va regarder tout le tableau de 3 sur 3 autour
						for (int l = j-1; l<=(j+1); l++) {
							if(k>= 0 && l>=0 && k<cases.length && l<cases[i].length) {//si  sa depasse pas le tableau
								if(cases[k][l] != 9) {//si c'est pas la bombe
									cases[k][l]++;//on ajoute un 
								}
							}
						}
					}
				}
			}	
		}	
	}

	
	
	
}
