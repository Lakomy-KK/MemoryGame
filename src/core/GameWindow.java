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
 * Klasa tworz�ca okno gry oraz inicjalizuje dodaje panel gry i komponenty
 * */
public class GameWindow extends JFrame {

	
	/**
	 * Utworzenie okna gry
	 *  inicjacja ramki gry
	 * @param width szeroko�� okna
	 * @param height wysoko�� okna
	 * @param x pozycja szeroko�ci okna
	 * @param y pozycja wysoko�ci okna
	 */
	public GameWindow(int width, int height, int x, int y) {

		super();
		setSize(width, height); // wymiary okna gry
		setLocation(x, y); // pozycja okna
		setResizable(false); // blokada rozmiaru okna
		setUndecorated(true); // ukryj ramk� okna i przyciski kontrolne
		initComponents(width, height);// funkcja inicjalizacji komponentow

		setVisible(true); // poka� okno

	}
/**
 * inicjacja panelu gry, komponent�w sta�ych, licznika czasu poziomu gry
 * @param width parametry szeroko�ci okna pobrane z konstruktora GameWindow
 * @param height parametry wysoko�ci okna pobrane z konstruktora GameWindow
 */
	public void initComponents(int width, int height) {

		// inicjacja panelu gry, komponent�w sta�ych, licznika czasu poziomu
		// gry,
		Constants.initialImages();// inicjalizacja zasob�w obrazkowych
		Constants.initDictionary();// inicjalizacja s�ownika dla t�umacze� s��w
		DrawGraphic bGround = new DrawGraphic(width, height);// utworzenie obiektu panelu gry
		Items item = new Items(bGround);// utworzenie obiektu klasy Item, przekazanie do jego konstruktora panelu gry
										
		item.createItems();// inicjacja przedmiot�w do schowania (labels)
		this.getContentPane().add(bGround); // dodanie panelu gry do okna gry
		JLabel labelTimeLabel = Constants.createTimeLabel(); // utworzenie labela odpowiedzialnego za wy�wietlanie czasu pozosta�ego w etapie gry
															
		GameStatus.newGame();// inicjacja nowej gry
		Timer timer = new Timer(1, new ActionListener() { // p�tla etapu gry(domy�lnie 30 sekund(Constants.countDownTime)
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
		// dodanie s�uchaczy akcji dla przycisku "Nowa Gra" i "Koniec"

		JLabel labelNewGame = new JLabel();
		labelNewGame.setName("nowa");
		labelNewGame.setBounds(720, 698, 120, 60);
		labelNewGame.addMouseListener(new MouseAdapter() {

			//akcja dla nowej gry,odrysowanie panelu, 
			//odysowanie odpowiedniej liczby przedmiot�w do schowania w zale�no�ci od etapu gry
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

		timer.start();// start timera odmie�aj�cego czas etapu gry po uruchomieniu gry
		bGround.setTimer(timer); //dodanie timera do panelu
		bGround.add(labelTimeLabel); //dodanie komponent�w sta�ych do panelu
		bGround.add(labelNewGame);
		bGround.add(labelEndGame);
		bGround.setLabels(item.itemList); //ustawienie przedmiot�w do schowania
		bGround.addComponentToDraw(1); // od�wie�enie ilo�ci komponent�w do schowania, odrysowanie ich
		bGround.setLayout(null); // layout panelu gry

	}

}
