package core;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.channels.ShutdownChannelGroupException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.w3c.dom.css.Rect;
/**
 * Klasa odpowiedzialna za ob�sug� zdarze� przedmiot�w do schowania oraz logik� przechodzenia poziom�w gry
 */
public class ItemMouseAdapter extends MouseAdapter {

	private DrawGraphic draw;

	public ItemMouseAdapter(DrawGraphic draw) {
		this.draw = draw;
		r = new Random();
	}
	//** zmienna klasy point wskazuj�ca aktualn� lokalizacj� w panelu gry*/
	private Point initialLoc;
	//**zmienna klasy point wskazuj�ca aktualn� lokalizacj� na ekranie */
	private Point initialLocOnScreen;
	/** zmienna klasy Radnom dla losowego zwracania s��w ze s�ownika */
	private Random r;
	/** flaga czy przedmiot natrafi� podczas chowania na pole do schowania*/
	boolean found = false;
	/**flaga dla sprawdzenia czy przedmiot nie zosta� ju� schowany w danym schowku*/
	private boolean isHolderAlreadyUse;		
	/** lista odpowiedzi*/
	List<String> randomAnswer;
	/** inicjacja obiektow Rectangle w celu przechowywania przedmiotow do schowania oraz interakcji  
	*na upuszczenie przedmiotu */
	Rectangle leftDesk = new Rectangle(110, 527, 102, 108);
	Rectangle leftPainting = new Rectangle(182, 150, 46, 216);
	Rectangle underBed = new Rectangle(292, 500, 281, 50);
	Rectangle onBed = new Rectangle(329, 420, 212, 50);
	Rectangle centerPainting = new Rectangle(405, 140, 130, 235);
	Rectangle window = new Rectangle(657, 229, 90, 110);
	Rectangle wardrobe = new Rectangle(773, 220, 40, 254);
	
	//s�uchacze dla akcji myszk� wobec przedmiot�w(labeli) do schowania
	/** akcja dla upuszczenia przedmiot�w*/
	@Override
	public void mousePressed(MouseEvent e) {
		Component comp = (Component) e.getSource();
		initialLoc = comp.getLocation();
		initialLocOnScreen = e.getLocationOnScreen();

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		found = false;
		Component comp = (Component) e.getSource();
		Point locOnScreen = e.getLocationOnScreen();
		int x = locOnScreen.x - initialLocOnScreen.x + initialLoc.x;
		int y = locOnScreen.y - initialLocOnScreen.y + initialLoc.y;
		boundsSet(x, y, comp);

		/**Tworzona lista obiektow Rectangle*/
		List<Rectangle> placeHolder = new ArrayList<Rectangle>();

		placeHolder.add(leftDesk);
		placeHolder.add(leftPainting);
		placeHolder.add(underBed);
		placeHolder.add(onBed);
		placeHolder.add(centerPainting);
		placeHolder.add(window);
		placeHolder.add(wardrobe);

		//glowna p�tla akcji chowania i odnajdowania przedmiot�w oraz t�umaczenia wyraz�w
		for (Rectangle holder : placeHolder) {
			if (holder.intersects(comp.getBounds())) {

				if (GameStatus.hide) {  // pierwszy etap poziomu, chowanie przedmiotow do schowk�w
					isHolderAlreadyUse = false;		//sprawdzenie czy nie zosta� ju� schowany przedmiot w schowku
					for (Rectangle h : GameStatus.rememmber.values()) {
						if (h.toString().equals(holder.toString())) {
							isHolderAlreadyUse = true;
							break;
						}
					} // je�li schowek jest wolny to wywo�aj akcj� dla t�umaczenia s�owa
					if (!isHolderAlreadyUse) {
						 String randomWord = "";
						 int counterTmp = 0;
						// losowe wybieranie s�owa i przyk�ad�w ze s�ownika do przet�umaczenia 
						int nrOfRandomWord = r.nextInt(Constants.dictionary.keySet().size());
						for (String slowo : Constants.dictionary.keySet()) {
							if (nrOfRandomWord == counterTmp) {
								randomWord = slowo;
								break;
							}
							counterTmp++;

						}
						// lista przyk�adowych odpowiedzi
						randomAnswer = new ArrayList<>();
						randomAnswer.add(Constants.dictionary.get(randomWord));

						while (randomAnswer.size() < 3) {
							int index = r.nextInt(Constants.dictionary.values().size());
							int licznik = 0;
							for (String mixer : Constants.dictionary.values()) {
								if (index == licznik) {
									if (!randomAnswer.contains(mixer)) {
										randomAnswer.add(mixer);
									}
								}
								licznik++;
							}
						}
						Collections.rotate(randomAnswer, r.nextInt(3)); // wy�wietlanie odpowiedzi w panelu w r�nych kolejno�ciach

						boolean isTransalateCorrect = false; //flaga dla t�umaczenia
						int answear = JOptionPane.showOptionDialog(null, "Przet�umacz s�owo :  " + randomWord,
								"Wyzwanie!", JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, randomAnswer.toArray(), null);

						if (answear != -1) {
							isTransalateCorrect = randomAnswer.get(answear).equals(Constants.dictionary.get(randomWord));
						}
						// akcja dla poprawnego przet�umaczenia s�owa - schowanie i zapami�tanie przedmiotu w schowku
						if (isTransalateCorrect) {

							JOptionPane.showMessageDialog(null, "Przedmiot ukryty !");
							GameStatus.rememeberLabel(comp, holder);

							comp.setLocation(initialLoc);
							comp.setVisible(false);
							// warunek ko�cz�cy etap chowania przedmiot�w
							if (GameStatus.rememmber.size() == draw.getNumberOfVisibleElement()) { 
								GameStatus.setHide(false);
								JOptionPane.showMessageDialog(null, "Wska� gdzie ukry�e� przedmioty!");
								//wy�wietlenie przedmiot�w w drugim etapie poziomu gry
								draw.showElement();
							}
							found = true;
							break;
						}
					} else {
						JOptionPane.showMessageDialog(null, "Schowek ju� zaj�ty !"); // akcja gdy schowek ju� przechowuje przedmiot
					}
				} 
				//drugi etap gry - przeci�ganie przedmiot�w do schowk�w, w kt�re by�y schowane
				else {
					// sprawdzenie po nazwach komponent�w z listy zapami�tanych czy jest dopasowanie
					for (Component c : GameStatus.rememmber.keySet()) { 
						if (c.toString().equals(comp.toString())
								&& holder.toString().equals(GameStatus.rememmber.get(c).toString())) {
							GameStatus.holder_itemMatch((JLabel) c); //wywo�anie metody gdy przedmiot jest dopasowany do schowka
							GameStatus.points+=10;
							found = true;
							comp.setLocation(initialLoc);
							comp.setVisible(false);
							draw.showElement();
						}
					}
					//warunek okre�laj�cy kolejne poziomy gry a� do osi�gni�cia wszystkich
					if (GameStatus.numberOfElementsToFind == 0) {
						if (GameStatus.level == 6) {
							draw.repaint();
							GameStatus.addBonusPoints();
							GameStatus.resetTimer();
							JOptionPane.showMessageDialog(null, "Wszystkie poziomy osi�gni�te!\n Bonusowe punkty: " 
							+ GameStatus.bonusPoints + " punkt�w\n ��cznie gracz zdoby�: " +
									(GameStatus.points + GameStatus.bonusPoints) + " punkt�w !");

						} else {
							draw.repaint();
							JOptionPane.showMessageDialog(null, "Odblokowany nowy poziom !");
							GameStatus.nextLevel();
							draw.addComponentToDraw(GameStatus.level);
						}

					}
				}

			}

		}
		// akcja dla wskazania przedmiotu do z�ego schowka
		if (!found && !GameStatus.hide) {
			GameStatus.nrOfLifes--;
			JOptionPane.showMessageDialog(null, "To nie ten schowek ! \nTracisz �ycie... \nSpr�buj jeszcze raz !");
		}
		if (GameStatus.nrOfLifes == 0) { //warunek ko�cz�cy gr�

			int answear = JOptionPane.showOptionDialog(null, "Sko�czy�y si� �ycia !", "Koniec Gry !",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					new Object[] { "Nowa Gra", "Zamknij" }, null);
			if (answear == 0) {
				GameStatus.newGame();
				draw.addComponentToDraw(GameStatus.level);
				draw.repaint();
			} else {
				GameStatus.reset();
			}
		}
		comp.setLocation(initialLoc);

		draw.revalidate();
		draw.repaint();
		// draw.update();

	}
	/** przenoszenie przedmiot�w po panelu gry*/
	@Override
	public void mouseDragged(MouseEvent e) {

		Component comp = (Component) e.getSource();
		Point locOnScreen = e.getLocationOnScreen();

		int x = locOnScreen.x - initialLocOnScreen.x + initialLoc.x;
		int y = locOnScreen.y - initialLocOnScreen.y + initialLoc.y;

		boundsSet(x, y, comp);

	}
	/**
	 *  metoda ograniczaj�ca przenoszenie przedmiot�w poza dozwolony obszar gry
	 * @param x pozycja szeroko�ci danego komponentu
	 * @param y pozycja wysoko�ci danego komponentu
	 * @param item dany komponent
	 */
	public void boundsSet(int x, int y, Component item) {
		if (x < 0)
			x = 0;
		if (x > Constants.gameWidth - 48)
			x = Constants.gameWidth - 48;
		if (y < 80)
			y = 80;
		if (y > Constants.gameHeight - 128)
			y = Constants.gameHeight - 128;
		item.setLocation(x, y);
	}

}