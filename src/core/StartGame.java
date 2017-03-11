package core;

import java.awt.Toolkit;

import javax.swing.SwingUtilities;

/**
 * Gra pozwalaj¹ca æwiczyæ zarówno pamiêæ jak i refleks oraz wykonywania kilku czynnoœci jednoczeœnie
 * @author Krzysiek
 */
public class StartGame {

	public static void main(String[] args) {
		int gameWidth = 1024;
		int gameHeight = 768;

		// pobierz rozmiar ekranu
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

		// wyœrodkowanie pola gry

		int xCenter = (screenWidth - gameWidth) / 2;
		int yCenter = (screenHeight - gameHeight) / 2;

		/**
		 * uruchomienie w¹tku gry
		 */
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					GameWindow game = new GameWindow(gameWidth, gameHeight, xCenter, yCenter);
					game.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
