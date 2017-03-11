package core;

import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Klasa odpowiedzialna za aktualny status gry oraz za status dopasowania pary schowek-przedmiot
 */
public class GameStatus {

	/**liczba punktów*/
	public static int points; 
	/**poziom gry*/
	public static int level; 
	/**zmienna pomocnicza dla liczenia czasu */
	public static double time; 
	/**zmienna do liczenia iloœci ¿yæ*/
	public static int nrOfLifes; 
	/** punkty bonusowe za utrzymanie ¿yæ*/
	public static int bonusPoints;
	/**mapa dla zapamiêtywania schowynych przedmiotow i ich miejsc schowania*/
	public static Map<Component, Rectangle> rememmber = new HashMap<>(); 
	/**flaga okreslajaca czy przedmiot zostal schowany*/
	public static boolean hide =true; 
	/**zmienna pomocnicza dla okreœlenia iloœci przedmiotów do schowania*/
	public static int numberOfElementsToFind = 0;  
	/**lista przedmiotów ju¿ dobrze wskazanych w schowkach w drugim etapie*/
	public static List<JLabel> foundsLabel = new ArrayList<>(); 
	/**zmienna pomocnicza dla przechowywania pocz¹tku odmierzania czasu*/
	private static long timeStart;
	/**flaga pomocnicza dla pêtli etapu gry*/ 
	private static boolean isRunning; //

	/** metoda ustawiaj¹ca parametry nowej gry*/
	public static void newGame() {
		points = 0;
		level = 1;
		nrOfLifes = 5;
		isRunning = true;
		rememmber = new HashMap<>();
		foundsLabel= new ArrayList<>();
		hide = true;
		timeStart();
	}
	/** metoda ustawiaj¹ca parametry nastêpnego poziomu*/
	public static void nextLevel() {
		level++;
		rememmber = new HashMap<>();
		hide=true;
		foundsLabel = new ArrayList<>();
		isRunning = true;
		timeStart();
		
	}
	/**metoda resetuj¹ca parametry na danym poziomie gry */
	public static void reset(){
		points = 0;
		nrOfLifes = 5;
		isRunning =false;
	}
	/**metoda ustawiaj¹ca parametry w momencie zakoñczenia siê czasu dla danego etapu*/
	public static void timesUp(){
		nrOfLifes--;
		timeStart();
		rememmber = new HashMap<>();
		foundsLabel = new ArrayList<>();
		hide = true;
	}
	/** metoda zeruj¹ca czas etapu*/
	public static void resetTimer(){
		isRunning =false;
	}

	/** zapamiêtanie pary schowek - przedmiot*/
	public static void rememeberLabel(Component label, Rectangle rect){
		rememmber.put(label, rect);
	}
	/** ustawianie flagi dla schowków , kontrola rozmiaru mapy dla zapamiêtania par schowek - przedmiot. Uruchomienie zliczania czasu*/
	public static void setHide(boolean b) {
		hide =false;
		numberOfElementsToFind = rememmber.size();
		timeStart();
		
	}
	/**metoda obs³uguj¹ca akcje dla dopasowania przedmiotu do schowka*/ 
	public static int holder_itemMatch(JLabel label){
		foundsLabel.add(label);
		numberOfElementsToFind--;
		return numberOfElementsToFind;
	}
	/** metoda pobieraj¹ca aktualny czas*/
	public static long timeStart(){
		timeStart = System.currentTimeMillis();
		return timeStart;
	}
	/** metoda zwracaj¹ca czas rozpoczêcia etapu*/
	public static long getStartingTime(){
		return timeStart;
	}
	/**metoda zwracaj¹ca flagê czy etap trwa czy nie*/
	public static boolean gameIsRunning(){
		return isRunning;
	}
	/**metoda dodaj¹ca bonusowe punkty za nie stracenie ¿yæ*/
	public static void addBonusPoints(){
		bonusPoints = nrOfLifes*20;
	}
	
	
	
	
	

}
