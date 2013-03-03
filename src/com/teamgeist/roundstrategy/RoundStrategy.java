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


	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		new RoundStrategy(800, 640);
	}

	public RoundStrategy(int w, int h)
	{
		this.setPreferredSize(new Dimension(w, h));
		frame = new JFrame("Game");
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		frame.setVisible(true);

		doInitialization();

		Thread th = new Thread(this);
		th.start();
	}

	private void checkKeys()
	{
		// TODO Auto-generated method stub
		
	}

	private void cloneObjects()
	{
		gameField.cloneTerrains();
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
			gameField = new GameField(terrains);
			int[][] levelData = {
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 1, 1, 0, 0, 0, 1, 0, 0},
					{0, 0, 1, 1, 0, 0, 1, 1, 1, 0},
					{0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
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

	private BufferedImage[][] loadTerrains() throws ImageLoadException
	{
		BufferedImage tileset = null;
		URL imgUrl = getClass().getResource("/resource/tileset.png");
		try
		{
			tileset = ImageIO.read(imgUrl);
		}
		catch (IOException e)
		{
			throw new ImageLoadException(
					"Could not load '/resource/tileset.png'");
		}

		int tw = 80;
		BufferedImage[][] terrains = {
			loadFromTileset(tileset, 5, 0, tw),		// grass 1
			loadFromTileset(tileset, 4, 1, tw),		// grass 2
			loadFromTileset(tileset, 5, 1, tw),		// grass 3
			loadFromTileset(tileset, 4, 0, tw),		// grass with green

			loadFromTileset(tileset, 0, 0, tw),		// mud in green nw
			loadFromTileset(tileset, 3, 0, tw),		// mud in green ne
			loadFromTileset(tileset, 1, 0, tw),		// mud in green n 1
			loadFromTileset(tileset, 2, 0, tw),		// mud in green n 2
			loadFromTileset(tileset, 0, 1, tw),		// mud in green w 1
			loadFromTileset(tileset, 0, 2, tw),		// mud in green w 2
			loadFromTileset(tileset, 3, 1, tw),		// mud in green e 1
			loadFromTileset(tileset, 3, 2, tw),		// mud in green e 2
			loadFromTileset(tileset, 0, 3, tw),		// mud in green sw
			loadFromTileset(tileset, 3, 3, tw),		// mud in green se
			loadFromTileset(tileset, 1, 3, tw),		// mud in green s 1
			loadFromTileset(tileset, 2, 3, tw),		// mud in green s 2
			loadFromTileset(tileset, 6, 0, tw),		// mud in green alone 1
			loadFromTileset(tileset, 6, 1, tw),		// mud in green alone 2
			loadFromTileset(tileset, 5, 2, tw),		// mud in green alone n
			loadFromTileset(tileset, 5, 3, tw),		// mud in green alone e
			loadFromTileset(tileset, 4, 2, tw),		// mud in green alone s
			loadFromTileset(tileset, 4, 3, tw),		// mud in green alone w

			loadFromTileset(tileset, 2, 1, tw),		// mud 1
			loadFromTileset(tileset, 1, 2, tw),		// mud 2
			loadFromTileset(tileset, 2, 2, tw),		// mud 3
			loadFromTileset(tileset, 1, 1, tw),		// mud with stone
		};
		return terrains;
	}

	private BufferedImage[] loadFromTileset(BufferedImage source,
			int posX,int posY, int width)
	{
		BufferedImage[] subimage = {
				source.getSubimage(
						posX * width,
						posY * width,
						width, width)
		};
		return subimage;
	}

	private void moveObjects()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, 640, 480);

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
