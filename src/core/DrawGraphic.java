package core;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
/**
 * Klasa odpowiedzialna za t³o gry oraz nas³uchiwanie akcji dla obs³ugi przycisków nowej gry oraz zakoñczenia gry
 * */
class DrawGraphic extends JPanel {
	
	//inicjalizacja zmiennych klasy 
	//** szerokoœæ pola gry*/
	private int gWidth = 1024;
	//** wysokoœæ pola gry*/
	private int gHeight = 768;
	//**zmienna licz¹ca iloœæ elementów do wyœwietlenie w panelu */
		private int visibleElement = 0;
	//** kolor schowków */
	Color rectangleColor = new Color(143, 0, 179, 150);
	//** kolor pasków górnego i dolnego panelu gry*/
	Color bar = new Color(22, 229, 229, 150);
	//** kolor t³a komponentów sta³ych */
	Color menuButtons = new Color(0, 87, 124, 150);
	//** instancja klasy timer*/
	Timer timer;
	//utworzenie instancji obiektu klasy Item haha
	Items items = new Items(this);
	// utworzenie listy labeli odpowiadaj¹cych za przedmioty do chowania. 
	//potrzebna do sprawdzania ilosci przedmiotow do odrysowania w panelu w zaleznosci od etapu gry
	private ArrayList<JLabel> itemList;

	/**
	 * 
	 * @param width parametry szerokoœci okna pobrane z konstruktora GameWindow
	 * @param height parametry wysokoœci okna pobrane z konstruktora GameWindow
	 */
	public DrawGraphic(int width, int height) {
		this.gWidth = width;
		this.gHeight = height;

		GameStatus.newGame();

	}
/**
 * ustawienie timera
 * @param timer inicjalizacja obiektu timer Javy Swing
 */
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
	/**
	 *  odrysowanie komponentow na panelu gry
	 * @param level zmienna odpowiedzialna za pozio gry
	 */
	
	public void addComponentToDraw(int level) {
		List<String> namesConstComponents = new ArrayList<>();
		namesConstComponents.add("czas");
		namesConstComponents.add("nowa");
		namesConstComponents.add("koniec");
		for (Component c : getComponents()) { // sprawdzenie po wszystkich komponentach czy nie s¹ to komponenty stale(label czasu, koniec i nowa gra)
			if (GameStatus.foundsLabel.contains((JLabel) c) == false
					&& !namesConstComponents.contains(c.getName())) {
				remove(c);
			}
		}

		int counter = 1; //warunek wyswietlajacy odpowiedni¹ iloœæ przedmiotów dla danego poziomu gry
		visibleElement = level + 1;
		for (JLabel lab : itemList) {
			if (counter <= level + 1) {
				add(lab);
				lab.setVisible(true);
				lab.setLocation(lab.getLocation());
			}
			counter++;
		}
		repaint();
	}
/**
 * inicjalizacja wszystkich labeli przedmiotów w panelu gry
 * @param itemList
 */
	public void setLabels(ArrayList<JLabel> itemList) { 
		this.itemList = itemList;
	}
/**
 * pobranie widocznych elementów
 * @return
 */
	public int getNumberOfVisibleElement() {
		return visibleElement;
	}
/**
 * wyœwietlenie elementu do wskazania gdzie zosta³ schowany(oprócz elementów sta³ych)
 */
	public void showElement() { 
		List<String> nazwyKomponentowStalych = new ArrayList<>();
		nazwyKomponentowStalych.add("czas");
		nazwyKomponentowStalych.add("nowa");
		nazwyKomponentowStalych.add("koniec");
		for (Component c : getComponents()) {
			if (GameStatus.foundsLabel.contains((JLabel) c) == false
					&& !nazwyKomponentowStalych.contains(c.getName())) {
				c.setVisible(true);
				break;
			}
		}
	}
	/**
	 *  odrysowanie elementów t³a gry 
	 */
	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		super.paintComponent(g2d);
		Font font1 = new Font("arial", Font.BOLD, 30);

		g.drawImage(Constants.room, 0, 0, null);
		g.setFont(font1);
		g2d.setColor(bar);
		g2d.fillRect(0, gHeight - 80, gWidth, 80);

		g2d.setColor(bar);
		g2d.fillRect(0, 0, gWidth, 80);

		g2d.setColor(menuButtons);// rysowanie przycisku Koniec
		g2d.fillRoundRect(860, 698, 120, 60, 20, 20);
		g2d.setColor(Color.BLACK);
		g.drawString("Koniec", 874, 737);

		g2d.setColor(menuButtons);// rysowanie przycisku Nowa Gra
		g2d.fillRoundRect(720, 698, 120, 60, 20, 20);
		g2d.setColor(Color.BLACK);
		g.drawString("Nowa", 734, 737);

		g2d.setColor(menuButtons);// rysowanie przycisku Punkty
		g2d.fillRoundRect(44, 10, 200, 60, 20, 20);
		g2d.setColor(Color.BLACK);
		g.drawString("Punkty: " + GameStatus.points, 64, 50);

		g2d.setColor(menuButtons);// rysowanie pola wyœwietlaj¹cego pozosta³y czas
		g2d.fillRoundRect(780, 10, 200, 60, 20, 20);
		g2d.setColor(Color.BLACK);
		g.drawString("Czas: ", 800, 50);

		g.drawImage(Constants.heart, 430, 15, null);
		g.setColor(Color.black);
		g.drawString("¯ycia: " + GameStatus.nrOfLifes + " x ", 280, 50);
		
		g.setColor(Color.black); //rysowanie informacji o poziomie gry
		g.drawString("Poziom: " + GameStatus.level , 550, 50);
		if(GameStatus.hide){
		g.setColor(Color.black); //rysowanie informacji o poziomie gry
		g.drawString("Schowaj przedmiot!" , 400, 737);
		}else{
			g.setColor(Color.black); //rysowanie informacji o poziomie gry
			g.drawString("Gdzie ukry³eœ?" , 400, 737);
		}

		// rysowanie miejsc do chowania
		g2d.setColor(rectangleColor);
		g2d.fillRoundRect(110, 527, 102, 108, 20, 20);

		g2d.setColor(rectangleColor);
		g2d.fillRoundRect(182, 150, 46, 216, 20, 20);

		g2d.setColor(rectangleColor);
		g2d.fillRoundRect(292, 500, 281, 50, 20, 20);

		g2d.setColor(rectangleColor);
		g2d.fillRoundRect(329, 420, 212, 50, 20, 20);

		g2d.setColor(rectangleColor);
		g2d.fillRoundRect(405, 140, 130, 235, 20, 20);

		g2d.setColor(rectangleColor);
		g2d.fillRoundRect(657, 229, 90, 110, 20, 20);

		g2d.setColor(rectangleColor);
		g2d.fillRoundRect(773, 220, 40, 254, 20, 20);

	}
}