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

	/**liczba punkt�w*/
	public static int points; 
	/**poziom gry*/
	public static int level; 
	/**zmienna pomocnicza dla liczenia czasu */
	public static double time; 
	/**zmienna do liczenia ilo�ci �y�*/
	public static int nrOfLifes; 
	/** punkty bonusowe za utrzymanie �y�*/
	public static int bonusPoints;
	/**mapa dla zapami�tywania schowynych przedmiotow i ich miejsc schowania*/
	public static Map<Component, Rectangle> rememmber = new HashMap<>(); 
	/**flaga okreslajaca czy przedmiot zostal schowany*/
	public static boolean hide =true; 
	/**zmienna pomocnicza dla okre�lenia ilo�ci przedmiot�w do schowania*/
	public static int numberOfElementsToFind = 0;  
	/**lista przedmiot�w ju� dobrze wskazanych w schowkach w drugim etapie*/
	public static List<JLabel> foundsLabel = new ArrayList<>(); 
	/**zmienna pomocnicza dla przechowywania pocz�tku odmierzania czasu*/
	private static long timeStart;
	/**flaga pomocnicza dla p�tli etapu gry*/ 
	private static boolean isRunning; //

	/** metoda ustawiaj�ca parametry nowej gry*/
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
	/** metoda ustawiaj�ca parametry nast�pnego poziomu*/
	public static void nextLevel() {
		level++;
		rememmber = new HashMap<>();
		hide=true;
		foundsLabel = new ArrayList<>();
		isRunning = true;
		timeStart();
		
	}
	/**metoda resetuj�ca parametry na danym poziomie gry */
	public static void reset(){
		points = 0;
		nrOfLifes = 5;
		isRunning =false;
	}
	/**metoda ustawiaj�ca parametry w momencie zako�czenia si� czasu dla danego etapu*/
	public static void timesUp(){
		nrOfLifes--;
		timeStart();
		rememmber = new HashMap<>();
		foundsLabel = new ArrayList<>();
		hide = true;
	}
	/** metoda zeruj�ca czas etapu*/
	public static void resetTimer(){
		isRunning =false;
	}

	/** zapami�tanie pary schowek - przedmiot*/
	public static void rememeberLabel(Component label, Rectangle rect){
		rememmber.put(label, rect);
	}
	/** ustawianie flagi dla schowk�w , kontrola rozmiaru mapy dla zapami�tania par schowek - przedmiot. Uruchomienie zliczania czasu*/
	public static void setHide(boolean b) {
		hide =false;
		numberOfElementsToFind = rememmber.size();
		timeStart();
		
	}
	/**metoda obs�uguj�ca akcje dla dopasowania przedmiotu do schowka*/ 
	public static int holder_itemMatch(JLabel label){
		foundsLabel.add(label);
		numberOfElementsToFind--;
		return numberOfElementsToFind;
	}
	/** metoda pobieraj�ca aktualny czas*/
	public static long timeStart(){
		timeStart = System.currentTimeMillis();
		return timeStart;
	}
	/** metoda zwracaj�ca czas rozpocz�cia etapu*/
	public static long getStartingTime(){
		return timeStart;
	}
	/**metoda zwracaj�ca flag� czy etap trwa czy nie*/
	public static boolean gameIsRunning(){
		return isRunning;
	}
	/**metoda dodaj�ca bonusowe punkty za nie stracenie �y�*/
	public static void addBonusPoints(){
		bonusPoints = nrOfLifes*20;
	}
	
	
	
	
	

}
