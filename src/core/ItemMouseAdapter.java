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
 * Klasa odpowiedzialna za ob³sugê zdarzeñ przedmiotów do schowania oraz logikê przechodzenia poziomów gry
 */
public class ItemMouseAdapter extends MouseAdapter {

	private DrawGraphic draw;

	public ItemMouseAdapter(DrawGraphic draw) {
		this.draw = draw;
		r = new Random();
	}
	//** zmienna klasy point wskazuj¹ca aktualn¹ lokalizacjê w panelu gry*/
	private Point initialLoc;
	//**zmienna klasy point wskazuj¹ca aktualn¹ lokalizacjê na ekranie */
	private Point initialLocOnScreen;
	/** zmienna klasy Radnom dla losowego zwracania s³ów ze s³ownika */
	private Random r;
	/** flaga czy przedmiot natrafi³ podczas chowania na pole do schowania*/
	boolean found = false;
	/**flaga dla sprawdzenia czy przedmiot nie zosta³ ju¿ schowany w danym schowku*/
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
	
	//s³uchacze dla akcji myszk¹ wobec przedmiotów(labeli) do schowania
	/** akcja dla upuszczenia przedmiotów*/
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

		//glowna pêtla akcji chowania i odnajdowania przedmiotów oraz t³umaczenia wyrazów
		for (Rectangle holder : placeHolder) {
			if (holder.intersects(comp.getBounds())) {

				if (GameStatus.hide) {  // pierwszy etap poziomu, chowanie przedmiotow do schowków
					isHolderAlreadyUse = false;		//sprawdzenie czy nie zosta³ ju¿ schowany przedmiot w schowku
					for (Rectangle h : GameStatus.rememmber.values()) {
						if (h.toString().equals(holder.toString())) {
							isHolderAlreadyUse = true;
							break;
						}
					} // jeœli schowek jest wolny to wywo³aj akcjê dla t³umaczenia s³owa
					if (!isHolderAlreadyUse) {
						 String randomWord = "";
						 int counterTmp = 0;
						// losowe wybieranie s³owa i przyk³adów ze s³ownika do przet³umaczenia 
						int nrOfRandomWord = r.nextInt(Constants.dictionary.keySet().size());
						for (String slowo : Constants.dictionary.keySet()) {
							if (nrOfRandomWord == counterTmp) {
								randomWord = slowo;
								break;
							}
							counterTmp++;

						}
						// lista przyk³adowych odpowiedzi
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
						Collections.rotate(randomAnswer, r.nextInt(3)); // wyœwietlanie odpowiedzi w panelu w ró¿nych kolejnoœciach

						boolean isTransalateCorrect = false; //flaga dla t³umaczenia
						int answear = JOptionPane.showOptionDialog(null, "Przet³umacz s³owo :  " + randomWord,
								"Wyzwanie!", JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, randomAnswer.toArray(), null);

						if (answear != -1) {
							isTransalateCorrect = randomAnswer.get(answear).equals(Constants.dictionary.get(randomWord));
						}
						// akcja dla poprawnego przet³umaczenia s³owa - schowanie i zapamiêtanie przedmiotu w schowku
						if (isTransalateCorrect) {

							JOptionPane.showMessageDialog(null, "Przedmiot ukryty !");
							GameStatus.rememeberLabel(comp, holder);

							comp.setLocation(initialLoc);
							comp.setVisible(false);
							// warunek koñcz¹cy etap chowania przedmiotów
							if (GameStatus.rememmber.size() == draw.getNumberOfVisibleElement()) { 
								GameStatus.setHide(false);
								JOptionPane.showMessageDialog(null, "Wska¿ gdzie ukry³eœ przedmioty!");
								//wyœwietlenie przedmiotów w drugim etapie poziomu gry
								draw.showElement();
							}
							found = true;
							break;
						}
					} else {
						JOptionPane.showMessageDialog(null, "Schowek ju¿ zajêty !"); // akcja gdy schowek ju¿ przechowuje przedmiot
					}
				} 
				//drugi etap gry - przeci¹ganie przedmiotów do schowków, w które by³y schowane
				else {
					// sprawdzenie po nazwach komponentów z listy zapamiêtanych czy jest dopasowanie
					for (Component c : GameStatus.rememmber.keySet()) { 
						if (c.toString().equals(comp.toString())
								&& holder.toString().equals(GameStatus.rememmber.get(c).toString())) {
							GameStatus.holder_itemMatch((JLabel) c); //wywo³anie metody gdy przedmiot jest dopasowany do schowka
							GameStatus.points+=10;
							found = true;
							comp.setLocation(initialLoc);
							comp.setVisible(false);
							draw.showElement();
						}
					}
					//warunek okreœlaj¹cy kolejne poziomy gry a¿ do osi¹gniêcia wszystkich
					if (GameStatus.numberOfElementsToFind == 0) {
						if (GameStatus.level == 6) {
							draw.repaint();
							GameStatus.addBonusPoints();
							GameStatus.resetTimer();
							JOptionPane.showMessageDialog(null, "Wszystkie poziomy osi¹gniête!\n Bonusowe punkty: " 
							+ GameStatus.bonusPoints + " punktów\n £¹cznie gracz zdoby³: " +
									(GameStatus.points + GameStatus.bonusPoints) + " punktów !");

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
		// akcja dla wskazania przedmiotu do z³ego schowka
		if (!found && !GameStatus.hide) {
			GameStatus.nrOfLifes--;
			JOptionPane.showMessageDialog(null, "To nie ten schowek ! \nTracisz ¿ycie... \nSpróbuj jeszcze raz !");
		}
		if (GameStatus.nrOfLifes == 0) { //warunek koñcz¹cy grê

			int answear = JOptionPane.showOptionDialog(null, "Skoñczy³y siê ¿ycia !", "Koniec Gry !",
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
	/** przenoszenie przedmiotów po panelu gry*/
	@Override
	public void mouseDragged(MouseEvent e) {

		Component comp = (Component) e.getSource();
		Point locOnScreen = e.getLocationOnScreen();

		int x = locOnScreen.x - initialLocOnScreen.x + initialLoc.x;
		int y = locOnScreen.y - initialLocOnScreen.y + initialLoc.y;

		boundsSet(x, y, comp);

	}
	/**
	 *  metoda ograniczaj¹ca przenoszenie przedmiotów poza dozwolony obszar gry
	 * @param x pozycja szerokoœci danego komponentu
	 * @param y pozycja wysokoœci danego komponentu
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