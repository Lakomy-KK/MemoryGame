package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * Klasa tworz¹ca okno gry oraz inicjalizuje dodaje panel gry i komponenty
 * */
public class GameWindow extends JFrame {

	
	/**
	 * Utworzenie okna gry
	 *  inicjacja ramki gry
	 * @param width szerokoœæ okna
	 * @param height wysokoœæ okna
	 * @param x pozycja szerokoœci okna
	 * @param y pozycja wysokoœci okna
	 */
	public GameWindow(int width, int height, int x, int y) {

		super();
		setSize(width, height); // wymiary okna gry
		setLocation(x, y); // pozycja okna
		setResizable(false); // blokada rozmiaru okna
		setUndecorated(true); // ukryj ramkê okna i przyciski kontrolne
		initComponents(width, height);// funkcja inicjalizacji komponentow

		setVisible(true); // poka¿ okno

	}
/**
 * inicjacja panelu gry, komponentów sta³ych, licznika czasu poziomu gry
 * @param width parametry szerokoœci okna pobrane z konstruktora GameWindow
 * @param height parametry wysokoœci okna pobrane z konstruktora GameWindow
 */
	public void initComponents(int width, int height) {

		// inicjacja panelu gry, komponentów sta³ych, licznika czasu poziomu
		// gry,
		Constants.initialImages();// inicjalizacja zasobów obrazkowych
		Constants.initDictionary();// inicjalizacja s³ownika dla t³umaczeñ s³ów
		DrawGraphic bGround = new DrawGraphic(width, height);// utworzenie obiektu panelu gry
		Items item = new Items(bGround);// utworzenie obiektu klasy Item, przekazanie do jego konstruktora panelu gry
										
		item.createItems();// inicjacja przedmiotów do schowania (labels)
		this.getContentPane().add(bGround); // dodanie panelu gry do okna gry
		JLabel labelTimeLabel = Constants.createTimeLabel(); // utworzenie labela odpowiedzialnego za wyœwietlanie czasu pozosta³ego w etapie gry
															
		GameStatus.newGame();// inicjacja nowej gry
		Timer timer = new Timer(1, new ActionListener() { // pêtla etapu gry(domyœlnie 30 sekund(Constants.countDownTime)
			@Override
			public void actionPerformed(ActionEvent e) {
				if (GameStatus.gameIsRunning()) {
					long elapsedTime = System.currentTimeMillis() - GameStatus.getStartingTime();
					labelTimeLabel.setText(Long.toString(Constants.countDownTime - elapsedTime / 1000));
					if (elapsedTime / 1000 >= Constants.countDownTime) {
						JOptionPane.showMessageDialog(null, "You lost. Koniec czasu");
						GameStatus.timesUp();
						labelTimeLabel.setText("-");
						bGround.addComponentToDraw(GameStatus.level);
					}
				} else {
					labelTimeLabel.setText("-");
				}
			}
		});
		// dodanie s³uchaczy akcji dla przycisku "Nowa Gra" i "Koniec"

		JLabel labelNewGame = new JLabel();
		labelNewGame.setName("nowa");
		labelNewGame.setBounds(720, 698, 120, 60);
		labelNewGame.addMouseListener(new MouseAdapter() {

			//akcja dla nowej gry,odrysowanie panelu, 
			//odysowanie odpowiedniej liczby przedmiotów do schowania w zale¿noœci od etapu gry
			//(addComponentToDraw(GameStatus.level);), uruchomienie ponownego odliczania czasu
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "Nowa gra"); 
				bGround.repaint();
				GameStatus.newGame();
				bGround.addComponentToDraw(GameStatus.level);
				timer.start();

			}
		});
		JLabel labelEndGame = new JLabel();
		labelEndGame.setName("koniec");
		labelEndGame.setBounds(860, 698, 120, 60);
		labelEndGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "Koniec gry");
				System.exit(1);

			}
		});

		timer.start();// start timera odmie¿aj¹cego czas etapu gry po uruchomieniu gry
		bGround.setTimer(timer); //dodanie timera do panelu
		bGround.add(labelTimeLabel); //dodanie komponentów sta³ych do panelu
		bGround.add(labelNewGame);
		bGround.add(labelEndGame);
		bGround.setLabels(item.itemList); //ustawienie przedmiotów do schowania
		bGround.addComponentToDraw(1); // odœwie¿enie iloœci komponentów do schowania, odrysowanie ich
		bGround.setLayout(null); // layout panelu gry

	}

}
