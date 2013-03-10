package com.teamgeist.roundstrategy;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.teamgeist.roundstrategy.exceptions.ImageLoadException;
import com.teamgeist.roundstrategy.gamedata.GameField;
import com.teamgeist.roundstrategy.gamedata.ResourceManager;

public class RoundStrategy extends JPanel implements Runnable
{

	private static final long serialVersionUID = 5156191450140937526L;
	private JFrame frame;
	private long delta = 0;
	private long last = 0;
	private long fps = 0;
	private GameField gameField;
	private InputManager input;
	private ResourceManager resources;


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
		resources = new ResourceManager();

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
			resources.loadTerrains();
			resources.loadObjects();
			resources.loadHexagon();
			gameField = new GameField(resources, this);
			int[][] levelData = {
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 1, 0, 2, 0, 0, 0, 2, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 3, 0, 0, 0, 3, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			};
			int[][] objectData = {
				{0, 2, 6},
				{1, 10, 13},
				{2, 1, 20},
				{3, 2, 18},
			};
			gameField.loadLevel(levelData, objectData);
		}
		catch (ImageLoadException e) {}
	}

	private void doLogic()
	{
	}

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
		g.drawString("Show/hide grid: G", 20, 40);
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
