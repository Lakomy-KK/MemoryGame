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
 * Klasa inicjalizuj¹ca sta³e 
 */
public class Constants {

	private static Image image; // zmienna pomocnicza dla ³adowania zasobów graficznych
	/**zmienna dla zasobów graficznych*/
	public static Image calendar;
	/**zmienna dla zasobów graficznych*/
	public static Image candy;
	/**zmienna dla zasobów graficznych*/
	public static Image envelope;
	/**zmienna dla zasobów graficznych*/
	public static Image heart;
	/**zmienna dla zasobów graficznych*/
	public static Image keys;
	/**zmienna dla zasobów graficznych*/
	public static Image mug;
	/**zmienna dla zasobów graficznych*/
	public static Image pen;
	/**zmienna dla zasobów graficznych*/
	public static Image pencil;
	/**zmienna dla zasobów graficznych*/
	public static Image room;
	/**zmienna okreœlaj¹ce szerokoœæ okna gry*/
	public static int gameWidth = 1024;
	/**zmienna okreœlaj¹ce wysokoœæ okna gry*/
	public static int gameHeight = 768;
	/**zmienna okreœlaj¹ca czas pojedynczego etapu gry*/
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
	/** metoda pobieraj¹ca obrazki do zmiennych*/
	public static Image loadImage(String path) {
		try {
			image = new ImageIcon(path).getImage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}
	/** tworzenie labela do wyœwietlania odliczania czasu etapu*/
	public static JLabel createTimeLabel(){
		JLabel labelTimeLabel = new JLabel();
		labelTimeLabel.setBounds(900, 10, 200, 60);
		labelTimeLabel.setFont(new Font("Arial", Font.BOLD, 45));
		labelTimeLabel.setName("czas");
		return labelTimeLabel;
	}
	
	/** instancja s³ownika dla s³ów do przet³umaczenia*/
	public static Map<String, String> dictionary = new HashMap<>();
	/**metoda dodaj¹ca parê s³ów do s³ownika*/
	public static void initDictionary() {
		dictionary.put("drzwi","door");
		dictionary.put("dzwonek","bell");
		dictionary.put("okno","window");
		dictionary.put("samochód","car");
		dictionary.put("opona","tyre");
		dictionary.put("ogórek","cucumber");
		dictionary.put("jab³ko", "apple");
		dictionary.put("pomarañcza", "orange");
		dictionary.put("s³oñce", "sun");
		dictionary.put("kubek", "mug");
		dictionary.put("ekran", "screen");
		dictionary.put("widmo", "spectrum");
		dictionary.put("zegarek", "watch");
		dictionary.put("lato", "summer");
		dictionary.put("truskawki", "strawberries");
		dictionary.put("gra", "game");
		dictionary.put("kalendarz", "calendar");
		dictionary.put("ksiê¿yc", "moon");
		dictionary.put("biurko", "desk");
		dictionary.put("d³ugopis", "pen");
		dictionary.put("o³ówek", "pencil");
		dictionary.put("muzyka", "music");
		dictionary.put("œciana", "wall");
		dictionary.put("cena", "price");
		dictionary.put("oszacowanie", "assessment");
		dictionary.put("szybowiec", "glider");
		dictionary.put("duma", "pride");
		dictionary.put("chleb", "bread");
		dictionary.put("mózg", "brain");
		dictionary.put("lustro", "mirror");
		dictionary.put("wspó³bie¿noœæ","concurrency");
		dictionary.put("przycisk", "button");
		dictionary.put("woda", "water");
		dictionary.put("sprzêg³o", "clutch");
		dictionary.put("benzyna", "gasoline");
		dictionary.put("wyzwanie", "chalange");
		
		
	}
}