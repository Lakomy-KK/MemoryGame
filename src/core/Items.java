package core;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
/**
 * Klasa tworz¹ca przedmioty do chowania
 */
public class Items {

	private DrawGraphic draw;
	private ItemMouseAdapter itemListener;
	
	public Items(DrawGraphic draw) {
		this.draw = draw;
		itemListener = new ItemMouseAdapter(draw);

	}
	/**inicjalizacja listy przedmiotów*/
	ArrayList<JLabel> itemList = new ArrayList<JLabel>();
	/**
	 * inicjalizacja przedmiotów do schowania jako labeli i za³adowanie do nich zasobów z klasy Constants.
	 * Konieczne nadanie unikalnych nazw dla dopasowania przy sprawdzaniu czy przedmiot zosta³ schowany w danym schowku
	 */
	 
	public void createItems() {
		JLabel calendar = new JLabel(new ImageIcon(Constants.calendar));
		calendar.setBounds(50, 700, 48, 48);
		calendar.addMouseListener(itemListener);
		calendar.setName("cal");
		calendar.addMouseMotionListener(itemListener);

		JLabel candy = new JLabel(new ImageIcon(Constants.candy));
		candy.setBounds(100, 700, 48, 48);
		candy.addMouseListener(itemListener);
		candy.setName("candy");
		candy.addMouseMotionListener(itemListener);

		JLabel envelope = new JLabel(new ImageIcon(Constants.envelope));
		envelope.setBounds(150, 700, 48, 48);
		envelope.addMouseListener(itemListener);
		envelope.setName("envelope");
		envelope.addMouseMotionListener(itemListener);

		JLabel keys = new JLabel(new ImageIcon(Constants.keys));
		keys.setBounds(200, 700, 48, 48);
		keys.addMouseListener(itemListener);
		keys.setName("klucz");
		keys.addMouseMotionListener(itemListener);

		JLabel mug = new JLabel(new ImageIcon(Constants.mug));
		mug.setBounds(250, 700, 48, 48);
		mug.addMouseListener(itemListener);
		mug.setName("mug");
		mug.addMouseMotionListener(itemListener);

		JLabel pen = new JLabel(new ImageIcon(Constants.pen));
		pen.setBounds(300, 700, 48, 48);
		pen.addMouseListener(itemListener);
		pen.setName("pen");
		pen.addMouseMotionListener(itemListener);

		JLabel pencil = new JLabel(new ImageIcon(Constants.pencil));
		pencil.setBounds(350, 700, 48, 48);
		pencil.addMouseListener(itemListener);
		pencil.addMouseMotionListener(itemListener);
		//dodanie zainicjalizowanych przediotów do listy

		itemList.add(calendar);
		itemList.add(candy);
		itemList.add(envelope);
		itemList.add(keys);
		itemList.add(mug);
		itemList.add(pen);
		itemList.add(pencil);
	}

}
