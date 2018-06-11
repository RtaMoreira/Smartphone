/**
* --------------------------------------------------------------------------<br/>
* Classe : Meteo <br/>
* --------------------------------------------------------------------------<br/>
* Auteur: Joao Silva <br/>
* Extension : AppTemplate <br/>
* Description : utilise API de  {@link}OpenWeatherMap.org <br/>
* --------------------------------------------------------------------------<br/>
*/
package smartphone;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.LineBorder;

import GUI.composants.ImagePanel;

public class Meteo extends AppTemplate 
{
	//icones d'appli
	private ImageIcon meteoIcon = new ImageIcon("image/icon/meteo.png");
	private ImageIcon meteoIconHover = new ImageIcon("image/icon/meteoHOVER.png");
	
	//sources d'info et image
	private ImageIcon[] status = new ImageIcon[9];
	private URL weather;
	private String infoJSON;
	File fichier=new File("serials/bddVillesMeteo.txt");
	private String citiesBdd;
	
	//formats de date et heure
	private DateFormat dmy = new SimpleDateFormat("dd MMMM yyyy");
	private DateFormat hhmm = new SimpleDateFormat("HH : mm");
	
	//ou je mets l'image par rapport au temps qu'il fait
	private ImagePanel image = new ImagePanel();
	
	//meteoPanel vient par dessous l'image et regroupe les 3 autres
	private JPanel meteoPanel = new JPanel(new BorderLayout());
	private JPanel tempPanel = new JPanel(new GridBagLayout());
	private JPanel northPanel = new JPanel(new GridLayout(3, 0));
	private JPanel infoPanel = new JPanel(new GridBagLayout());
	
	//pour northPanel
	private JTextField search = new JTextField();
	private JButton ok = new JButton("ok");
	private JPanel searchPanel = new JPanel(new BorderLayout());
	private JPanel datePanel = new JPanel(new FlowLayout());
	private JPanel cityPanel = new JPanel(new FlowLayout());
	
	public Meteo() 
	{
		super("meteo", Color.WHITE);
		//ajout ActionListenner pour changer la ville
		ok.addActionListener(new ChangeCity());
		
		//mettre les images
		status[0]=new ImageIcon("image/icon/meteo/0.png");
		status[1]=new ImageIcon("image/icon/meteo/1.png");
		status[2]=new ImageIcon("image/icon/meteo/2.png");
		status[3]=new ImageIcon("image/icon/meteo/3.png");
		status[4]=new ImageIcon("image/icon/meteo/4.png");
		status[5]=new ImageIcon("image/icon/meteo/5.png");
		status[6]=new ImageIcon("image/icon/meteo/6.png");
		status[7]=new ImageIcon("image/icon/meteo/7.png");
		
		FileReader fread;
		try{
			fread= new FileReader(fichier);//ouvrir un reader dans le fichier par contre il lit en binaire
			BufferedReader bfread = new BufferedReader(fread);//on va lire le reader
			citiesBdd=bfread.readLine();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		getInfo(2658606);
		initializeCity();	

		image.setLayout(new BorderLayout());
		image.add(meteoPanel, BorderLayout.CENTER);
		add(image);
		
		meteoPanel.setOpaque(false);
		
		infoPanel.setOpaque(false);
		meteoPanel.add(infoPanel,BorderLayout.AFTER_LAST_LINE);
		
		tempPanel.setOpaque(false);
		meteoPanel.add(tempPanel,BorderLayout.CENTER);
		
		northPanel.setOpaque(false);
		meteoPanel.add(northPanel,BorderLayout.BEFORE_FIRST_LINE);
		
		search.setOpaque(false);
		search.setBorder(new LineBorder(Color.WHITE));
		search.setForeground(Color.WHITE);
		searchPanel.add(search,BorderLayout.CENTER);
		ok.setBackground(Color.WHITE);
		ok.setBorderPainted(false);
		searchPanel.add(ok, BorderLayout.EAST);
		searchPanel.setPreferredSize(new Dimension(300, 10));
		searchPanel.setMinimumSize(new Dimension(300, 10));
		searchPanel.setMaximumSize(new Dimension(300, 10));
		searchPanel.setOpaque(false);
		
		northPanel.add(searchPanel);
		datePanel.setOpaque(false);
		northPanel.add(datePanel);
		cityPanel.setOpaque(false);
		northPanel.add(cityPanel);	
		
	}
	/**
	 * utilise toutes les methodes
	 * getDate pour la date du jour
	 * detCity pour recupérer la ville
	 * getStatus pour recup l'id de l'icon a presenter
	 * getInfos pour recup les autres Infos
	 * @author jcfds
	 */
	private void initializeCity() 
	{
		getDate();
		getCity();
		getStatus();
		getTemp();
		getInfos();
	}

	private void getDate() 
	{
		datePanel.removeAll();
		Calendar cal = Calendar.getInstance();
		JLabel date = new JLabel(dmy.format(cal.getTime()));
		date.setForeground(Color.WHITE);
		date.setFont(new Font(date.getFont().getFontName(), date.getFont().getStyle(), (date.getFont().getSize()+5)));
		datePanel.add(date);
	}
	
	private void getCity() 
	{
		String s=infoJSON.substring((infoJSON.indexOf("name")+7),(infoJSON.indexOf("cod")-3))+", "+
				infoJSON.substring((infoJSON.indexOf("country")+10),(infoJSON.indexOf("sunrise")-3));
		cityPanel.removeAll();
		JLabel city = new JLabel(s);
		city.setForeground(Color.WHITE);
		city.setFont(new Font(city.getFont().getFontName(), city.getFont().getStyle(), (city.getFont().getSize()+5)));
		cityPanel.add(city);
	}
	
	private void getStatus() 
	{
		String status;
		status=infoJSON.substring((infoJSON.indexOf("icon")+7),(infoJSON.indexOf("base")-5));
		
		switch (status) 
		{
			case "01d":
			case "01n":
				image.setImage("image/icon/meteo/0.png");
				break;
			case "02d":
			case "02n":
				image.setImage("image/icon/meteo/1.png");
				break;
			case "03d":
			case "03n":
				image.setImage("image/icon/meteo/2.png");
				break;
			case "04d":
			case "04n":
				image.setImage("image/icon/meteo/3.png");
				break;
			case "09d":
			case "09n":
				image.setImage("image/icon/meteo/4.png");
				break;
			case "10d":
			case "10n":
				image.setImage("image/icon/meteo/5.png");
				break;
			case "11d":
			case "11n":
				image.setImage("image/icon/meteo/6.png");
				break;
			case "13d":
			case "13n":
				image.setImage("image/icon/meteo/7.png");
				break;
			case "50d":
			case "50n":
				image.setImage("image/icon/meteo/8.png");
				break;
			default:
				image.setImage("image/icon/meteo/9.png");
				break;
		}
		updateUI();
	}
	
	private void getTemp() 
	{
		JLabel actual;
		if(infoJSON.indexOf(".",infoJSON.indexOf("temp"))<infoJSON.indexOf("pressure",infoJSON.indexOf("temp")))
			actual=new JLabel(infoJSON.substring((infoJSON.indexOf("temp")+6),(infoJSON.indexOf(".",infoJSON.indexOf("temp")))));
		else
			actual=new JLabel(infoJSON.substring((infoJSON.indexOf("temp")+6),(infoJSON.indexOf("pressure")-2)));
		
		JLabel minMax=new JLabel("Max: "+ infoJSON.substring((infoJSON.indexOf("temp_min")+10),(infoJSON.indexOf("temp_max")-2))+"      "
								+"Min: "+ infoJSON.substring((infoJSON.indexOf("temp_max")+10),(infoJSON.indexOf("visibility")-3)));
		tempPanel.removeAll();
		
		GridBagConstraints c = new GridBagConstraints();
		
		actual.setFont(new Font(actual.getFont().getFontName(), actual.getFont().getStyle(), (actual.getFont().getSize()+50)));
		actual.setForeground(Color.WHITE);
		c.insets= new Insets(300, 0, 0, 350);
		c.gridx=0;
		c.gridy=0;
		tempPanel.add(actual,c);
		c.insets= new Insets(0, 0, 0, 0);
		c.anchor=GridBagConstraints.LAST_LINE_START;
		c.gridy=1;
		minMax.setForeground(Color.WHITE);
		tempPanel.add(minMax,c);
	}
	
	private void getInfos() 
	{
		infoPanel.removeAll();
		JLabel pressure =new JLabel( "Pression:                 "+ infoJSON.substring((infoJSON.indexOf("pressure")+10),(infoJSON.indexOf("humidity")-2))+" hpa");
		JLabel humidity =new JLabel( "Humidité:                 "+ infoJSON.substring((infoJSON.indexOf("humidity")+10),(infoJSON.indexOf("temp_min")-2))+"%");
		JLabel windSpeed;
		JLabel sunRise =new JLabel(  "Lever du soleil :       "+ hhmm.format(new Date((Integer.parseInt(infoJSON.substring((infoJSON.indexOf("sunrise")+9),(infoJSON.indexOf("sunset")-2))))*1000L)));
		JLabel sunSet =new JLabel(   "Coucher du soleil :  "+ hhmm.format(new Date(Integer.parseInt(infoJSON.substring((infoJSON.indexOf("sunset")+8),(infoJSON.lastIndexOf("\"id\"")-3)))*1000L)));
		
		if(infoJSON.indexOf("deg")!=-1)//parce que le degré du vent n'est pas toujours présent
			windSpeed =new JLabel(   "Vitesse vent:           "+ infoJSON.substring((infoJSON.indexOf("speed")+7),(infoJSON.indexOf(",",infoJSON.indexOf("speed")+7)))+" m/s");
		else
			windSpeed = new JLabel(  "Vitesse vent:           "+ infoJSON.substring((infoJSON.indexOf("speed")+7),(infoJSON.indexOf("}",infoJSON.indexOf("speed")+7)))+" m/s");

		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor=GridBagConstraints.FIRST_LINE_START;
		
		c.insets=new Insets(0, 30, 0, 50);
		c.gridx=0;
		c.gridy=0;
		pressure.setForeground(Color.WHITE);
		infoPanel.add(pressure,c);
		
		c.gridy=1;
		humidity.setForeground(Color.WHITE);
		infoPanel.add(humidity,c);
		
		c.insets=new Insets(0, 30, 50, 50);
		c.gridy=2;
		windSpeed.setForeground(Color.WHITE);
		infoPanel.add(windSpeed,c);
		
		c.insets=new Insets(0, 30, 0, 50);
		c.gridy=0;
		c.gridx=1;
		sunRise.setForeground(Color.WHITE);
		infoPanel.add(sunRise,c);
		
		c.gridy=1;
		sunSet.setForeground(Color.WHITE);
		infoPanel.add(sunSet,c);
		
	}
	
	private void getInfo(int cityCode) 
	{
		try
		{
			weather = new URL("http://api.openweathermap.org/data/2.5/weather?id="+cityCode+"&units=metric&APPID=a4a10487d22d8f957424c1b2c4b645f2");
			InputStreamReader in = new InputStreamReader(weather.openStream());
			BufferedReader bin = new BufferedReader(in);
	        infoJSON=bin.readLine();
	        in.close();
		} catch (IOException e) 
			{e.printStackTrace();}
	}
	
	public ImageIcon getMeteoIcon() 
	{
		return meteoIcon;
	}

	public ImageIcon getMeteoIconHover() 
	{
		return meteoIconHover;
	}

	class ChangeCity implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			String input=search.getText().toLowerCase();
			
			if(citiesBdd.indexOf(input)==-1) 
			{
				getInfo(2658606);
				initializeCity();
			}
			else
			{
				getInfo(Integer.parseInt(citiesBdd.substring(citiesBdd.indexOf(":", citiesBdd.indexOf(input))+1, citiesBdd.indexOf(";", citiesBdd.indexOf(input)))));
				initializeCity();
			}
				
		}
		
	}
}
