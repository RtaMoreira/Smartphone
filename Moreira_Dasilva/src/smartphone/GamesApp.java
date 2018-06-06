/**
* TP Week2
*Author: Joao Silva
*Date creation : 22 mai 2018
*/
package smartphone;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamesApp extends AppTemplate
{

	private ImageIcon gamesIcon =new ImageIcon("image/icon/icon_tictactoe.png");
	private ImageIcon gamesIconHover =new ImageIcon("image/icon/icon_tictactoeHOVER.png");
	
	private CardLayout cardlayout = new CardLayout();
	private JPanel gamePanel = new JPanel(cardlayout);
	private JPanel mainPanel = new JPanel();
	private Snake snake = new Snake();
	private Demineur demineur= new Demineur();
	private Pendu pendu= new Pendu();
	
	private JLabel snakeButton = new JLabel(snake.getSnakeIcon());
	private JLabel demineurButton = new JLabel(demineur.getDemineurIcon());
	private JLabel penduButton = new JLabel(pendu.getPenduIconHover());
	
	public ImageIcon getGamesIcon() 
	{
		return gamesIcon;
	}

	public ImageIcon getGamesIconHover() 
	{
		return gamesIconHover;
	}
	
	
	public GamesApp() 
	{
		super("Games", Color.BLUE);
		
		snakeButton.addMouseListener(new OpenSnake());
		demineurButton.addMouseListener(new OpenDemineur());
		penduButton.addMouseListener(new OpenPendu());
		mainPanel.add(snakeButton);
		mainPanel.add(demineurButton);
		mainPanel.add(penduButton);
		
		gamePanel.add(mainPanel,"main");
		gamePanel.add(snake,"snake");
		gamePanel.add(demineur,"demineur");
		gamePanel.add(pendu,"pendu");
		
		add(gamePanel);
		
		super.getNavigation().getBackButton().addActionListener(new TimerStop());
	}
	
	class TimerStop implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			snake.getTimer().stop();
			snake.restart();
			cardlayout.show(gamePanel, "main");
		}
	}
	
	class OpenSnake implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0) 
		{
			snake.getTimer().start();
			cardlayout.show(gamePanel, "snake");
		}

		@Override
		public void mouseEntered(MouseEvent arg0) 
		{
			snakeButton.setIcon(snake.getSnakeIconHover());
		}

		@Override
		public void mouseExited(MouseEvent arg0) 
		{
			snakeButton.setIcon(snake.getSnakeIcon());
		}

		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
		
	}
	
	class OpenDemineur implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent arg0) 
		{
			cardlayout.show(gamePanel, "demineur");
		}

		@Override
		public void mouseEntered(MouseEvent arg0) 
		{
			demineurButton.setIcon(demineur.getDemineurIconHover());
		}

		@Override
		public void mouseExited(MouseEvent arg0) 
		{
			demineurButton.setIcon(demineur.getDemineurIcon());
		}

		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
	}
	
	class OpenPendu implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0) 
		{
			cardlayout.show(gamePanel, "pendu");
		}

		@Override
		public void mouseEntered(MouseEvent arg0) 
		{
			penduButton.setIcon(pendu.getPenduIconHover());
		}

		@Override
		public void mouseExited(MouseEvent arg0) 
		{
			penduButton.setIcon(pendu.getPenduIcon());
		}

		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
	}
}
