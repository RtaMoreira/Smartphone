/**
* --------------------------------------------------------------------------<br/>
* Classe : Snake <br/>
* --------------------------------------------------------------------------<br/>
* Auteur: Joao Silva <br/>
* Description : jeux du snake<br/>
* --------------------------------------------------------------------------<br/>
*/
package smartphone;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Snake extends JPanel implements ActionListener
{
	private ImageIcon snakeIcon = new ImageIcon("image/icon/snake.png");
	private ImageIcon snakeIconHover = new ImageIcon("image/icon/snakeHOVER.png");
	
	private final int DELAY = 100;
    private JPanel[][] pixels = new JPanel[100][60];

    /** The coordinates of the snake. */
    private int[] x = new int[pixels[0].length*pixels.length];//possible d'avoir le tout accupé par le snake
    private int[] y = new int[pixels[0].length*pixels.length];

    /** Coordinates for the food. */
    private int apple_x, apple_y;

    
    private int snakeSize = 3;
    private Timer t = new Timer(DELAY, this);
    private int direction = 1;
    private boolean over=false;
    
    public Snake() 
    {
    	addKeyListener(new ChangeDirect());
    	setFocusable(true);
    	setBackground(Color.GREEN);
    	setLayout(new BorderLayout());
    	setPreferredSize(new Dimension(200, 600));
    	setLayout(new GridLayout(pixels.length,pixels[0].length));
       	for (int i = 0; i <snakeSize; i++) 
       	{
			y[i]=50;
			x[i]=50-i;
		}
       	
       	for (int i = 0; i < pixels.length; i++) 
       	{
			for (int j = 0; j < pixels[0].length; j++) 
			{
				pixels[i][j]=new JPanel();
				pixels[i][j].setBackground(Color.BLACK);
				pixels[i][j].setOpaque(true);
				add(pixels[i][j]);
			}
    	}
       	randomFood();
    	paint();	
    }
    
    public ImageIcon getSnakeIcon() 
    {
		return snakeIcon;
	}

	public ImageIcon getSnakeIconHover() 
	{
		return snakeIconHover;
	}

	public Timer getTimer() 
	{
		return t;
	}
	
	public void paint() 
	{
    	for (int i = 0; i < pixels.length; i++) 
    	{
			for (int j = 0; j < pixels[0].length; j++) 
			{
				pixels[i][j].setBackground(Color.BLACK);
			}
		}
    	
    	pixels[apple_x][apple_y].setBackground(Color.GREEN);
    	
    	for (int i = 0; i < snakeSize; i++) 
    	{
			pixels[x[i]][y[i]].setBackground(Color.GREEN);
		}
    }
    
	public void restart() 
	{
		for (int i = 0; i <snakeSize; i++) 
		{
			y[i]=50;
			x[i]=50-i;
		}
		
		snakeSize=3;
		over=false;
		randomFood();
    	paint();
	}
    public void randomFood() 
    {
        apple_x=(int)(Math.random()*pixels.length);
        apple_y=(int)(Math.random()*pixels[0].length);
    }
    
    
    /**
     * @author jcfds
     * methode verifie si pixel ou snake se deplace est occupé
     * action par rapport a ce qu'il y a (food, body or border)
     * @return boolean
     */
    public boolean checkpixel() 
    {
    	//si c'es une pomme on rajoute 1 pixel de longueur
    	if(x[0]==apple_x&&y[0]==apple_y) 
    	{
    		snakeSize++;
    		randomFood();
    		return false;
    	}
    	
    	//si ça dépasse le tableau GAMEOVER
    	if(x[0]==pixels.length||x[0]<0||y[0]==pixels[0].length||y[0]<0) 
    	{
			return true;
    	}
    	
    
		int cpt=0;
		//si le serpent va contre lui meme GAMEOVER
		if(pixels[x[0]][y[0]].getBackground()==Color.GREEN)
			return true;
    		  
    	return false;
    }
    
    /**ouvre un JOptionPane avec l'option de recommencer*/
    public void gameover() 
    {
    	t.stop();
    	if(	JOptionPane.showConfirmDialog(null,"Voulez vous recommencer?")==JOptionPane.YES_OPTION)
    	{
    		for (int i = 0; i <snakeSize; i++) 
    		{
				y[i]=50;
				x[i]=50-i;
			}
			
			snakeSize=3;
			
			randomFood();
			paint();
	        t.start();
    	}
    }
    
	@Override/**action du Timer*/
	public void actionPerformed(ActionEvent e) 
	{
		for (int i = (snakeSize-1); i > 0; i--) 
		{
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		
		if (direction==1)
		x[0]++;
		if (direction==2)
		y[0]++;
		if (direction==3)
		x[0]--;
		if (direction==4)
		y[0]--;
		
		over = checkpixel();
		if(over) 
			gameover();
		else
			paint();
	}
	
	/**
	 * change la direction par rapport a la touche(fleche) clicked
	 */
	class ChangeDirect implements KeyListener
	{

		@Override
		public void keyPressed(KeyEvent arg0) 
		{
			if(arg0.getKeyCode()==37)/**gauche*/
				direction=4;
			if(arg0.getKeyCode()==39)/**droite*/
				direction=2;
			if(arg0.getKeyCode()==38)/**haut*/
				direction=3;
			if(arg0.getKeyCode()==40)/**bas*/
				direction=1;
		}
		public void keyReleased(KeyEvent arg0) {}
		public void keyTyped(KeyEvent arg0) {}
		
	}
}
