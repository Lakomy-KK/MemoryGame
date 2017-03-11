package core;

import java.awt.Toolkit;

import javax.swing.SwingUtilities;

/**
 * Gra pozwalaj�ca �wiczy� zar�wno pami�� jak i refleks oraz wykonywania kilku czynno�ci jednocze�nie
 * @author Krzysiek
 */
public class StartGame {

	public static void main(String[] args) {
		int gameWidth = 1024;
		int gameHeight = 768;

		// pobierz rozmiar ekranu
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

		// wy�rodkowanie pola gry

		int xCenter = (screenWidth - gameWidth) / 2;
		int yCenter = (screenHeight - gameHeight) / 2;

		/**
		 * uruchomienie w�tku gry
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
