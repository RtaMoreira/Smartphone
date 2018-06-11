/**
* --------------------------------------------------------------------------<br/>
* Classe : Jeux Pendu <br/>
* --------------------------------------------------------------------------<br/>
* Auteur: Joao Silva <br/>
* Description : Jeux du pendu <br/>
* --------------------------------------------------------------------------<br/>
*/
package smartphone;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.border.LineBorder;

import GUI.composants.FlatField;

public class Pendu extends JPanel
{
	private ImageIcon penduIcon = new ImageIcon("image/icon/pendu.png");
	private ImageIcon penduIconHover = new ImageIcon("image/icon/penduHOVER.png");
	private CardLayout cardlayout = new CardLayout();
	private Menu menu = new Menu();
	private Game game = new Game();
	private Gameover gameover = new Gameover();
	private JPanel mainPanel = new JPanel(cardlayout);
	
	public Pendu() 
	{
		mainPanel.add(menu,"menu");
		mainPanel.add(game,"game");
		mainPanel.add(gameover,"gameover");
		add(mainPanel);
		cardlayout.show(mainPanel, "menu");
		setBackground(Color.WHITE);
		mainPanel.setOpaque(false);
	}
	
	public Icon getPenduIconHover() 
	{
		return penduIconHover;
	}
	
	public Icon getPenduIcon() 
	{
		return penduIcon;
	}
	
	//pour recommencer avec un nouveau mot
	public void restart() 
	{
		menu.field.getField().setText("");
		cardlayout.show(mainPanel,"menu");
		game.imageHolder.removeAll();
	}
	
	/**
	 * classe qui construit le pannel du menu
	 * @author jcfds
	 *
	 */
	class Menu extends JPanel
	{
		private ImageIcon logo = new ImageIcon("image/icon/pendu/logo.png");
		private JPanel vide = new JPanel();
		private FlatField field = new FlatField(2);
		private JButton ok = new JButton("ok");
		private JPanel mot = new JPanel();
		
		Menu()
		{
			setLayout(new BorderLayout());
			setOpaque(false);
			
			ok.addActionListener(new StartGame());
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
	
	/**
	 * @author jcfds
	 *classe qui represente le panel de gameove
	 */
	class Gameover extends JPanel
	{
		private String mot;
		private GridBagConstraints c = new GridBagConstraints();
		private JLabel perdu = new JLabel("Vous avez perdu");
		private JLabel gagne = new JLabel("Vous avez gagné");
		private JLabel motEtait = new JLabel("Le mot était : ");
		private JLabel cliquez = new JLabel("Cliquez l'écran pour recommencer");
		
		Gameover()
		{
			setOpaque(false);
			setLayout(new GridBagLayout());
			addMouseListener(new Restart());
		}
		
		public void win() 
		{
			c.gridx=0;
			c.gridy=0;
			add(gagne,c);
			c.gridy=1;
			motEtait.setText(motEtait.getText()+mot);
			add(motEtait,c);
			c.gridy=2;
			add(cliquez,c);
		}
		
		public void lose() 
		{
			c.gridx=0;
			c.gridy=0;
			add(perdu,c);
			c.gridy=1;
			motEtait.setText(motEtait.getText()+mot);
			add(motEtait,c);
			c.gridy=2;
			add(cliquez,c);
		}
	}
	
	/**
	 * classe du JPanel du jeu avec les images du pendu
	 * @author jcfds
	 */
	class Game extends JPanel
	{
		String mot;
		
		private JPanel letters = new JPanel(new FlowLayout());
		private JPanel imageHolder = new JPanel(new FlowLayout());
		private JTextField[] cases;
		private JPanel playerInput =new JPanel(new FlowLayout());
		private JTextField input = new JTextField();
		private JButton tryBut = new JButton("Try");
		private int fails=0;
		private int wins=0;
		ImageIcon[] pendu=new ImageIcon[7];
		
		Game()
		{
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
			
			tryBut.addActionListener(new Letter());
			
			input.setPreferredSize(new Dimension(20, 20));
			input.setMaximumSize(new Dimension(20, 20));
			input.setMinimumSize(new Dimension(20, 20));
			playerInput.add(input);
			playerInput.add(tryBut);
		}
		
		//pour comencer le jeu avec le pendu a l'état de début*/
		public void start() 
		{
			removeAll();
			letters.removeAll();
			input.setText("");
			imageHolder.add(new JLabel(pendu[0]));
			fails=0;
			wins=0;
			
			
			add(imageHolder);
			
			for (int i = 0; i < cases.length; i++) 
			{
				cases[i]=new JTextField();
				cases[i].setEditable(false);
				cases[i].setForeground(Color.BLACK);
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
	
	//action du bouton ok
	class StartGame implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			if(String.valueOf(menu.field.getField().getPassword())=="")
				menu.field.setBorder(new LineBorder(Color.RED));
			else
			{
			game.mot=String.valueOf(menu.field.getField().getPassword());
			game.cases=new JTextField[game.mot.length()];
			game.start();
			menu.field.setBorder(null);
			cardlayout.show(mainPanel,"game");
			}
		}
	}
	
	//action de restart le jeu
	class Restart implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0) 
		{
			menu.field.getField().setText("");
			cardlayout.show(mainPanel,"menu");
			game.imageHolder.removeAll();
		}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
	}
	
	//pour verifier la lettre qu'on ajoute
	class Letter implements ActionListener
	{
		char letter;
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			letter = game.input.getText().charAt(0);
			
			if(game.mot.indexOf(letter)==-1) 
			{
				game.fails++;
				if(game.fails>6) 
				{
					gameover.removeAll();
					gameover.mot=game.mot;
					gameover.lose();
					cardlayout.show(mainPanel,"gameover");
					return;
				}
				
				game.imageHolder.removeAll();
				game.imageHolder.add(new JLabel(game.pendu[game.fails]));
			}
			else 
			{
				for (int i = 0; i < game.mot.length(); i++) 
				{
					if (game.mot.charAt(i)==letter) 
					{
						game.cases[i].setText(String.valueOf(letter));
						game.wins++;
					}
				}
				
				if(game.wins==game.mot.length())
				{
					gameover.removeAll();
					gameover.mot=game.mot;
					gameover.win();
					cardlayout.show(mainPanel,"gameover");
					return;
				}
			}
				
			
		}
		
	}
	
}
