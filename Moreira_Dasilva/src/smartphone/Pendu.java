/**
* TP Week2
*Author: Joao Silva
*Date creation : 1 juin 2018
*/
package smartphone;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import GUI.composants.MonField;

public class Pendu extends JPanel{

	private ImageIcon penduIcon = new ImageIcon("image/icon/galerie.png");
	private ImageIcon penduIconHover = new ImageIcon("image/icon/galerieHOVER.png");
	private CardLayout cardlayout = new CardLayout();
	private Menu menu = new Menu();
	private Game game = new Game();
	private Gameover gameover = new Gameover();
	private JPanel mainPanel = new JPanel(cardlayout);
	
	public Pendu() {
		mainPanel.add(menu,"menu");
		mainPanel.add(game,"game");
		mainPanel.add(gameover,"gameover");
		add(mainPanel);
		cardlayout.show(mainPanel, "menu");
		setBackground(Color.WHITE);
		mainPanel.setOpaque(false);
	}
	
	public Icon getPenduIconHover() {
		return penduIconHover;
	}
	
	public Icon getPenduIcon() {
		return penduIcon;
	}
	
	class Menu extends JPanel{
		ImageIcon logo = new ImageIcon("image/icon/pendu/logo.png");
		JPanel vide = new JPanel();
		MonField field = new MonField(2);
		JButton ok = new JButton("ok");
		JPanel mot = new JPanel();
		
		Menu(){
			setLayout(new BorderLayout());
			setOpaque(false);
			
			ok.addActionListener(new startGame());
			vide.setPreferredSize(new Dimension(400,150));
			vide.setOpaque(false);
			field.setFieldBackground(Color.GRAY);
			
			mot.setLayout(new FlowLayout());
			mot.add(field);
			mot.add(ok);
			mot.setOpaque(false);
			
			add(new JLabel(logo),BorderLayout.BEFORE_FIRST_LINE);
			add(vide);
			add(mot);
		}
	}
	
	class Gameover extends JPanel{
		Gameover(){
			setOpaque(false);
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			addMouseListener(new restart());
		}
	}
	class Game extends JPanel{
		String mot;
		
		private JPanel letters = new JPanel(new FlowLayout());
		private JPanel imageHolder = new JPanel(new FlowLayout());
		private JTextField[] cases;
		private JPanel playerInput =new JPanel(new FlowLayout());
		private JTextField input = new JTextField();
		private JButton tryBut = new JButton("Try");
		private int fails=0;
		ImageIcon[] pendu=new ImageIcon[7];
		
		Game(){
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			pendu[0]=new ImageIcon("image/icon/pendu/pendu0.png");
			pendu[1]=new ImageIcon("image/icon/pendu/pendu1.png");
			pendu[2]=new ImageIcon("image/icon/pendu/pendu2.png");
			pendu[3]=new ImageIcon("image/icon/pendu/pendu3.png");
			pendu[4]=new ImageIcon("image/icon/pendu/pendu4.png");
			pendu[5]=new ImageIcon("image/icon/pendu/pendu5.png");
			pendu[6]=new ImageIcon("image/icon/pendu/pendu6.png");
			setOpaque(false);
			imageHolder.setOpaque(false);
			playerInput.setOpaque(false);
			letters.setOpaque(false);
			
			tryBut.addActionListener(new letter());
			
			input.setPreferredSize(new Dimension(20, 20));
			input.setMaximumSize(new Dimension(20, 20));
			input.setMinimumSize(new Dimension(20, 20));
			playerInput.add(input);
			playerInput.add(tryBut);
		}
		
		public void start() {
			removeAll();
			letters.removeAll();
			input.setText("");
			imageHolder.add(new JLabel(pendu[0]));
			fails=0;
			
			
			add(imageHolder);
			
			for (int i = 0; i < cases.length; i++) {
				cases[i]=new JTextField();
				cases[i].setEnabled(false);
				cases[i].setPreferredSize(new Dimension(20, 20));
				cases[i].setMaximumSize(new Dimension(20, 20));
				cases[i].setMinimumSize(new Dimension(20, 20));
				letters.add(cases[i]);
				
				input.removeAll();
			}
			add(letters);
			add(playerInput);
		}
		
	}
	
	class startGame implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			game.mot=String.valueOf(menu.field.getField().getPassword());
			System.out.println(menu.field.getField().getPassword());
			game.cases=new JTextField[game.mot.length()];
			game.start();
			cardlayout.show(mainPanel,"game");
		}
		
	}
	
	class restart implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			menu.field.getField().setText("");
			cardlayout.show(mainPanel,"menu");
			game.imageHolder.removeAll();
		}
		
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
		
	}
	
	class letter implements ActionListener{
		
		char letter;
		@Override
		public void actionPerformed(ActionEvent arg0) {
			letter = game.input.getText().charAt(0);
			
			if(game.mot.indexOf(letter)==-1) {
				game.fails++;
				if(game.fails>6) {
					gameover.removeAll();
					gameover.add(new JLabel("Vous avez perdu"));
					gameover.add(new JLabel("Le mot était : "+game.mot));
					gameover.add(new JLabel("Cliquez l'écran pour recommencer"));
					cardlayout.show(mainPanel,"gameover");
					return;
				}
				
				game.imageHolder.removeAll();
				game.imageHolder.add(new JLabel(game.pendu[game.fails]));
			}
			else
				game.cases[game.mot.indexOf(letter)].setText(String.valueOf(letter));
/**---------------------------------------------------------------------------------------------
 * see if multiple same letters
 */
		}
		
	}
	
}
