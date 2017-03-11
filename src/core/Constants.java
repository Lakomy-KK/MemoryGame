package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
/**
 * Klasa inicjalizuj�ca sta�e 
 */
public class Constants {

	private static Image image; // zmienna pomocnicza dla �adowania zasob�w graficznych
	/**zmienna dla zasob�w graficznych*/
	public static Image calendar;
	/**zmienna dla zasob�w graficznych*/
	public static Image candy;
	/**zmienna dla zasob�w graficznych*/
	public static Image envelope;
	/**zmienna dla zasob�w graficznych*/
	public static Image heart;
	/**zmienna dla zasob�w graficznych*/
	public static Image keys;
	/**zmienna dla zasob�w graficznych*/
	public static Image mug;
	/**zmienna dla zasob�w graficznych*/
	public static Image pen;
	/**zmienna dla zasob�w graficznych*/
	public static Image pencil;
	/**zmienna dla zasob�w graficznych*/
	public static Image room;
	/**zmienna okre�laj�ce szeroko�� okna gry*/
	public static int gameWidth = 1024;
	/**zmienna okre�laj�ce wysoko�� okna gry*/
	public static int gameHeight = 768;
	/**zmienna okre�laj�ca czas pojedynczego etapu gry*/
	public static long countDownTime = 45; 

	public static void initialImages() {
		calendar = loadImage("src/resources/calendar.png");
		candy = loadImage("src/resources/candy.png");
		envelope = loadImage("src/resources/envelope.png");
		heart = loadImage("src/resources/heart.png");
		keys = loadImage("src/resources/keys.png");
		mug = loadImage("src/resources/mug.png");
		pen = loadImage("src/resources/pen.png");
		pencil = loadImage("src/resources/pencil.png");
		room = loadImage("src/resources/room.png");
	}
	/** metoda pobieraj�ca obrazki do zmiennych*/
	public static Image loadImage(String path) {
		try {
			image = new ImageIcon(path).getImage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}
	/** tworzenie labela do wy�wietlania odliczania czasu etapu*/
	public static JLabel createTimeLabel(){
		JLabel labelTimeLabel = new JLabel();
		labelTimeLabel.setBounds(900, 10, 200, 60);
		labelTimeLabel.setFont(new Font("Arial", Font.BOLD, 45));
		labelTimeLabel.setName("czas");
		return labelTimeLabel;
	}
	
	/** instancja s�ownika dla s��w do przet�umaczenia*/
	public static Map<String, String> dictionary = new HashMap<>();
	/**metoda dodaj�ca par� s��w do s�ownika*/
	public static void initDictionary() {
		dictionary.put("drzwi","door");
		dictionary.put("dzwonek","bell");
		dictionary.put("okno","window");
		dictionary.put("samoch�d","car");
		dictionary.put("opona","tyre");
		dictionary.put("og�rek","cucumber");
		dictionary.put("jab�ko", "apple");
		dictionary.put("pomara�cza", "orange");
		dictionary.put("s�o�ce", "sun");
		dictionary.put("kubek", "mug");
		dictionary.put("ekran", "screen");
		dictionary.put("widmo", "spectrum");
		dictionary.put("zegarek", "watch");
		dictionary.put("lato", "summer");
		dictionary.put("truskawki", "strawberries");
		dictionary.put("gra", "game");
		dictionary.put("kalendarz", "calendar");
		dictionary.put("ksi�yc", "moon");
		dictionary.put("biurko", "desk");
		dictionary.put("d�ugopis", "pen");
		dictionary.put("o��wek", "pencil");
		dictionary.put("muzyka", "music");
		dictionary.put("�ciana", "wall");
		dictionary.put("cena", "price");
		dictionary.put("oszacowanie", "assessment");
		dictionary.put("szybowiec", "glider");
		dictionary.put("duma", "pride");
		dictionary.put("chleb", "bread");
		dictionary.put("m�zg", "brain");
		dictionary.put("lustro", "mirror");
		dictionary.put("wsp�bie�no��","concurrency");
		dictionary.put("przycisk", "button");
		dictionary.put("woda", "water");
		dictionary.put("sprz�g�o", "clutch");
		dictionary.put("benzyna", "gasoline");
		dictionary.put("wyzwanie", "chalange");
		
		
	}
}