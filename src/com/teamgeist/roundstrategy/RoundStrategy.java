package com.teamgeist.roundstrategy;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.teamgeist.roundstrategy.exceptions.ImageLoadException;
import com.teamgeist.roundstrategy.gamedata.GameField;

public class RoundStrategy extends JPanel implements Runnable
{

	private static final long serialVersionUID = 5156191450140937526L;
	private JFrame frame;
	private long delta = 0;
	private long last = 0;
	private long fps = 0;
	private GameField gameField;
	private InputManager input;


	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		new RoundStrategy(800, 640);
	}

	public RoundStrategy(int w, int h)
	{
		input = new InputManager();

		this.setPreferredSize(new Dimension(w, h));
		frame = new JFrame("Game");
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.addKeyListener(input);
		frame.pack();
		frame.setVisible(true);

		doInitialization();

		Thread th = new Thread(this);
		th.start();
	}

	private void checkKeys()
	{
		gameField.checkKeys(input);
	}

	private void cloneObjects()
	{
		gameField.cloneObjects();
	}

	private void computeDelta()
	{
		delta = System.nanoTime() - last;
		last = System.nanoTime();
		fps = ((long) 1e9) / delta;
	}

	private void doInitialization()
	{
		last = System.nanoTime();
		try
		{
			BufferedImage[][] terrains = loadTerrains();
			BufferedImage[] hexagon = loadFromFile("/resource/hexagon_one.png");
			gameField = new GameField(terrains, hexagon, this);
			int[][] levelData = {
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				};
			gameField.loadLevel(levelData, this);
		}
		catch (ImageLoadException e) {}
	}

	private void doLogic()
	{
		// TODO Auto-generated method stub
		
	}

	private BufferedImage[] loadFromFile(String filename) throws ImageLoadException
	{
		BufferedImage image = null;
		URL imgUrl = getClass().getResource(filename);
		try
		{
			image = ImageIO.read(imgUrl);
		}
		catch (IOException e)
		{
			throw new ImageLoadException(
					String.format("Could not load '%s'", filename));
		}
		BufferedImage[] data = {
			image
		};
		return data;
	}

	private BufferedImage[][] loadTerrains() throws ImageLoadException
	{
		/*BufferedImage tileset = null;
		URL imgUrl = getClass().getResource("/resource/tileset.png");
		try
		{
			tileset = ImageIO.read(imgUrl);
		}
		catch (IOException e)
		{
			throw new ImageLoadException(
					"Could not load '/resource/tileset.png'");
		}*/

		BufferedImage[][] terrains = {
			loadFromFile("/resource/space.png")
		};
		return terrains;
	}

	/*private BufferedImage[] loadFromTileset(BufferedImage source,
			int posX,int posY, int width)
	{
		BufferedImage[] subimage = {
				source.getSubimage(
						posX * width,
						posY * width,
						width, width)
		};
		return subimage;
	}*/

	private void moveObjects()
	{
		gameField.move(delta);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());

		if (null != gameField)
		{
			gameField.drawObjects(g);
		}

		g.setColor(Color.white);
		g.drawString("FPS: " + Long.toString(fps), 20, 20);
	}

	@Override
	public void run()
	{
		while (frame.isVisible())
		{
			computeDelta();

			checkKeys();
			doLogic();
			moveObjects();
			cloneObjects();

			repaint();

			try
			{
				Thread.sleep(10);
			}
			catch (InterruptedException e) {}
		}
	}

}
