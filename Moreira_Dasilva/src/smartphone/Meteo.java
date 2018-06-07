/**
* TP Week2
*Author: Joao Silva
*Date creation : 4 juin 2018
*/
package smartphone;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;

public class Meteo extends AppTemplate 
{

	private ImageIcon meteoIcon = new ImageIcon("image/icon/meteo.png");
	private ImageIcon meteoIconHover = new ImageIcon("image/icon/meteoHOVER.png");
	
	private ImageIcon[] status = new ImageIcon[9];
	private URL weather;
	private String infoJSON;
	
	private DateFormat dmy = new SimpleDateFormat("dd MMMM yyyy");
	private DateFormat hhmm = new SimpleDateFormat("HH : mm");
	
	private JPanel mainPanel = new JPanel();
	private JTextField search = new JTextField();
	private JButton ok = new JButton("ok");
	private JPanel searchPanel = new JPanel(new BorderLayout());
	private JPanel datePanel = new JPanel(new FlowLayout());
	private JPanel cityPanel = new JPanel(new FlowLayout());
	private JPanel imageTemp = new JPanel(new BorderLayout());
	private JPanel imagePanel = new JPanel(new BorderLayout());
	private JPanel tempPanel = new JPanel();
	private JPanel infoPanel = new JPanel(new GridLayout(3,2));
	private JPanel sunPanel = new JPanel(new BorderLayout());
	
	public Meteo() 
	{
		super("meteo", Color.WHITE);
		
		ok.addActionListener(new ChangeCity());
		
		status[0]=new ImageIcon("image/icon/meteo/0.png");
		status[1]=new ImageIcon("image/icon/meteo/1.png");
		status[2]=new ImageIcon("image/icon/meteo/2.png");
		status[3]=new ImageIcon("image/icon/meteo/3.png");
		status[4]=new ImageIcon("image/icon/meteo/4.png");
		status[5]=new ImageIcon("image/icon/meteo/5.png");
		status[6]=new ImageIcon("image/icon/meteo/6.png");
		status[7]=new ImageIcon("image/icon/meteo/7.png");
		
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		mainPanel.setBackground(new Color(104, 197, 212));
		tempPanel.setLayout(new BoxLayout(tempPanel,BoxLayout.Y_AXIS));
		
		searchPanel.add(search,BorderLayout.CENTER);
		searchPanel.add(ok, BorderLayout.EAST);
		searchPanel.setPreferredSize(new Dimension(450, 35));
		searchPanel.setMinimumSize(new Dimension(450, 35));
		searchPanel.setMaximumSize(new Dimension(450, 35));
		
		imagePanel.setOpaque(false);
		imageTemp.add(imagePanel,BorderLayout.WEST);
		tempPanel.setOpaque(false);
		imageTemp.add(tempPanel,BorderLayout.EAST);
		
		searchPanel.setOpaque(false);
		mainPanel.add(searchPanel);
		datePanel.setOpaque(false);
		mainPanel.add(datePanel);
		cityPanel.setOpaque(false);
		mainPanel.add(cityPanel);
		imageTemp.setOpaque(false);
		mainPanel.add(imageTemp);
		infoPanel.setOpaque(false);
		mainPanel.add(infoPanel);
		sunPanel.setOpaque(false);
		mainPanel.add(sunPanel);
		
		add(mainPanel);
		
		getInfo(2658606);
		initializeCity();	
	}
	
	public void initializeCity() 
	{
		getDate();
		getCity();
		getStatus();
		getTemp();
		getInfos();
	}

	public void getDate() 
	{
		datePanel.removeAll();
		Calendar cal = Calendar.getInstance();
		datePanel.add(new JLabel(dmy.format(cal.getTime())));
	}
	
	public void getCity() 
	{
		String city=infoJSON.substring((infoJSON.indexOf("name")+7),(infoJSON.indexOf("cod")-3))+", "+
				infoJSON.substring((infoJSON.indexOf("country")+10),(infoJSON.indexOf("sunrise")-3));
		cityPanel.removeAll();
		cityPanel.add(new JLabel(city));
	}
	
	public void getStatus() 
	{
		String status;
		status=infoJSON.substring((infoJSON.indexOf("icon")+7),(infoJSON.indexOf("base")-5));
		
		switch (status) 
		{
			case "01d":
			case "01n":
				imagePanel.removeAll();
				imagePanel.add(new JLabel(this.status[0]));
				break;
			case "02d":
			case "02n":
				imagePanel.removeAll();
				imagePanel.add(new JLabel(this.status[1]));
				break;
			case "03d":
			case "03n":
				imagePanel.removeAll();
				imagePanel.add(new JLabel(this.status[2]));
				break;
			case "04d":
			case "04n":
				imagePanel.removeAll();
				imagePanel.add(new JLabel(this.status[3]));
				break;
			case "09d":
			case "09n":
				imagePanel.removeAll();
				imagePanel.add(new JLabel(this.status[4]));
				break;
			case "10d":
			case "10n":
				imagePanel.removeAll();
				imagePanel.add(new JLabel(this.status[5]));
				break;
			case "11d":
			case "11n":
				imagePanel.removeAll();
				imagePanel.add(new JLabel(this.status[6]));
				break;
			case "13d":
			case "13n":
				imagePanel.removeAll();
				imagePanel.add(new JLabel(this.status[7]));
				break;
			case "50d":
			case "50n":
				imagePanel.removeAll();
				imagePanel.add(new JLabel(this.status[8]));
				break;
			default:
				imagePanel.removeAll();
				imagePanel.add(new JLabel(this.status[8]));
				break;
		}
	}
	
	public void getTemp() 
	{
		String actual="Temperature: "+ infoJSON.substring((infoJSON.indexOf("temp")+6),(infoJSON.indexOf("pressure")-2));
		String min="Maximum: "+ infoJSON.substring((infoJSON.indexOf("temp_min")+10),(infoJSON.indexOf("temp_max")-2));
		String max="Minimum: "+ infoJSON.substring((infoJSON.indexOf("temp_max")+10),(infoJSON.indexOf("visibility")-3));
		tempPanel.removeAll();
		
		JPanel vide = new JPanel();
		vide.setPreferredSize(new Dimension(150,250));
		vide.setMinimumSize(new Dimension(150,250));
		vide.setMaximumSize(new Dimension(150,250));
		vide.setOpaque(false);
		tempPanel.add(vide);
		
		tempPanel.add(new JLabel(actual));
		tempPanel.add(new JLabel(min));
		tempPanel.add(new JLabel(max));
	}
	
	public void getInfos() 
	{
		infoPanel.removeAll();
		String pressure = "Pression:                  "+ infoJSON.substring((infoJSON.indexOf("pressure")+10),(infoJSON.indexOf("humidity")-2))+" hpa";
		String humidity = "Humidité:                 "+ infoJSON.substring((infoJSON.indexOf("humidity")+10),(infoJSON.indexOf("temp_min")-2))+"%";
		String windSpeed;
		String sunRise =  "Lever du soleil :     "+ hhmm.format(new Date((Integer.parseInt(infoJSON.substring((infoJSON.indexOf("sunrise")+9),(infoJSON.indexOf("sunset")-2))))*1000L));
		String sunSet =   "Coucher du soleil :  "+ hhmm.format(new Date((Integer.parseInt(infoJSON.substring((infoJSON.indexOf("sunset")+8),(infoJSON.lastIndexOf("id")-3))))*1000L));
		
		if(infoJSON.indexOf("deg")!=-1)//parce que le degré du vent n'est pas toujours présent
			windSpeed =   "Vitesse vent:            "+ infoJSON.substring((infoJSON.indexOf("speed")+7),(infoJSON.indexOf(",",infoJSON.indexOf("speed")+7)));
		else
			windSpeed =   "Vitesse vent:            "+ infoJSON.substring((infoJSON.indexOf("speed")+7),(infoJSON.indexOf("}",infoJSON.indexOf("speed")+7)));
		windSpeed+=" m/s";

		
		infoPanel.add(new JLabel(pressure));
		infoPanel.add(new JLabel(humidity));
		infoPanel.add(new JLabel(windSpeed));
		infoPanel.add(new JLabel(sunRise));
		infoPanel.add(new JLabel(sunSet));
		
	}
	
	public void getInfo(int cityCode) 
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
			
			switch (input) {
			case "sierre":
				getInfo(2658606);
				initializeCity();
				break;
			case "sion":
				getInfo(2658576);
				initializeCity();
				break;
			case "vevey":
				getInfo(2658145);
				initializeCity();
				break;
			case "martigny":
				getInfo(2659752);
				initializeCity();
				break;
			case "montreux":
				getInfo(2659601);
				initializeCity();
				break;
			case "aigle":
				getInfo(2661834);
				initializeCity();
				break;
			case "collonges":
				getInfo(2661146);
				initializeCity();
				break;
			case "chatel-saint-denis":
				getInfo(2661202);
				initializeCity();
				break;
			case "lisbonne":
				getInfo(2267057);
				initializeCity();
				break;
			case "brasilia":
				getInfo(3459342);
				initializeCity();
				break;
			default:
				getInfo(2658606);
				initializeCity();
				break;
			}
		}
		
	}
}
