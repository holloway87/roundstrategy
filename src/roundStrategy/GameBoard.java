package roundStrategy;

import gamedata.GameField;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class GameBoard extends JPanel
{

	private Graphics2D g;
	private GameField field;
	private Timer timer;
	private static final long serialVersionUID = -309186845718864793L;

	public GameBoard()
	{
		setFocusable(true);
		setDoubleBuffered(true);

		field = new GameField(10, 10, 15, 10);

		timer = new Timer();
		timer.scheduleAtFixedRate(new GameTimer(), 1000, 10);
	}

	@Override
	protected void paintComponent(Graphics gr)
	{
		g = (Graphics2D)gr;
		super.paintComponent(g);

		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, 640, 480);

		field.render(g);
	}

	class GameTimer extends TimerTask
	{
		public void run()
		{
			repaint();
		}
	}

}
