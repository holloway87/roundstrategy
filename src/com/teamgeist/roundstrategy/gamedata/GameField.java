package com.teamgeist.roundstrategy.gamedata;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ListIterator;
import java.util.Vector;

import com.teamgeist.roundstrategy.InputManager;
import com.teamgeist.roundstrategy.RoundStrategy;
import com.teamgeist.roundstrategy.engine.Drawable;
import com.teamgeist.roundstrategy.engine.FieldSprite;
import com.teamgeist.roundstrategy.engine.Movable;
import com.teamgeist.roundstrategy.engine.terrain.Hexagon;

public class GameField implements Drawable, Movable
{

	private int scrollSpeed = 250;
	private int[][] levelData;
	private Vector<FieldSprite> fieldObjects;
	private RoundStrategy parent;
	private ResourceManager resources;
	private boolean showGrid;


	public GameField(ResourceManager resources, RoundStrategy p)
	{
		this.resources = resources;
		this.parent = p;
		this.showGrid = true;
	}

	public void checkKeys(InputManager input)
	{
		// show grid?
		showGrid = input.getState(InputManager.SHOW_GRID);

		int newSpeedX = 0;
		int newSpeedY = 0;
		if (input.getState(InputManager.SCROLL_UP))
		{
			newSpeedY = scrollSpeed;
		}
		if (input.getState(InputManager.SCROLL_DOWN))
		{
			newSpeedY = -scrollSpeed;
		}
		if (input.getState(InputManager.SCROLL_RIGHT))
		{
			newSpeedX = -scrollSpeed;
		}
		if (input.getState(InputManager.SCROLL_LEFT))
		{
			newSpeedX = scrollSpeed;
		}

		for (ListIterator<FieldSprite> it = fieldObjects.listIterator();
				it.hasNext();)
		{
			FieldSprite field = it.next();
			field.setDx(newSpeedX);
			field.setDy(newSpeedY);
		}
	}

	public void cloneObjects()
	{
	}

	public FieldSprite createSpaceObject(BufferedImage[] i, double x, double y,
			long delay)
	{
		int width = i[0].getWidth();
		int height = i[0].getHeight();
		int hexWidth = Hexagon.getHexwidth();
		int hexHeight = Hexagon.getHexheight();

		x = x - (width / 2) + (hexWidth / 2);
		y = y - (height / 2) + (hexHeight / 2);

		return new FieldSprite(i, x, y, delay, parent);
	}

	public int getFieldsWidth()
	{
		if (0 == levelData.length)
		{
			return 0;
		}
		return levelData[0].length;
	}

	public int getFieldsHeight()
	{
		return levelData.length;
	}

	@Override
	public void drawObjects(Graphics g)
	{

		if (null != fieldObjects)
		{
			for (ListIterator<FieldSprite> it = fieldObjects.listIterator();
					it.hasNext();)
			{
				FieldSprite field = it.next();
				if ((!showGrid && field.isGrid()) ||
						0 > field.getX() + field.getWidth() ||
						0 > field.getY() + field.getHeight() ||
						parent.getWidth() < field.getX() ||
						parent.getHeight() < field.getY())
				{
					continue;
				}
				field.drawObjects(g);
			}
		}
	}

	@Override
	public void doLogic(long delta)
	{
		boolean resetX = false;
		boolean resetY = false;
		int lastField = getFieldsHeight() * getFieldsWidth() -1;
		FieldSprite first = fieldObjects.elementAt(0);
		FieldSprite last = fieldObjects.elementAt(lastField);
		int imageWidth = first.getImages()[0].getWidth();
		int imageHeight = first.getImages()[0].getHeight();
		double firstX = first.getX();
		double lastX = last.getX() + imageWidth;
		double firstY = first.getY();
		double lastY = last.getY() + imageHeight;
		
		if ((-(imageWidth * 0.75) < firstX && 0 < first.getDx()) ||
				(parent.getWidth() + (imageWidth * 0.75) > lastX && 0 > first.getDx()))
		{
			resetX = true;
		}
		if ((-imageHeight < firstY && 0 < first.getDy()) ||
				(parent.getHeight() + imageHeight > lastY && 0 > first.getDy()))
		{
			resetY = true;
		}
		if (resetX)
		{
			for (ListIterator<FieldSprite> it = fieldObjects.listIterator();
					it.hasNext();)
			{
				FieldSprite field = it.next();
				field.setDx(0);
			}
		}
		if (resetY)
		{
			for (ListIterator<FieldSprite> it = fieldObjects.listIterator();
					it.hasNext();)
			{
				FieldSprite field = it.next();
				field.setDy(0);
			}
		}
	}

	public void loadLevel(int[][] data, int[][] objects)
	{
		loadLevel(data, objects, (int)-(Hexagon.getHexwidth() * 0.75),
				-Hexagon.getHexheight());
	}

	public void loadLevel(int[][] data, int[][] objects, int offsetX, int offsetY)
	{
		if (0 == data.length)
		{
			return;
		}

		this.fieldObjects = new Vector<FieldSprite>();

		levelData = data;

		BufferedImage[][] terrainImages = resources.getTerrains();
		for (int y = 0; y < data.length; y++)
		{
			for (int x = 0; x < data[y].length; x++)
			{
				int hexXOffset = (1 == y % 2
						? (int)(Hexagon.getHexwidth() * 0.75)
						: 0);
				double fieldX = (double)(offsetX + hexXOffset + (x * Hexagon.getHexwidth() * 1.5));
				double fieldY = (double)(offsetY + (y * Hexagon.getHexheight() * 0.5));
				BufferedImage[] image = terrainImages[data[y][x]];
				fieldObjects.add(new FieldSprite(
						image, fieldX, fieldY, 0, parent));
			}
		}
		for (int i = 0; i < objects.length; i++)
		{
			int x = objects[i][1];
			int y = objects[i][2];
			int hexXOffset = (1 == y % 2
					? (int)(Hexagon.getHexwidth() * 0.75)
					: 0);
			double fieldX = (double)(offsetX + hexXOffset + (x * Hexagon.getHexwidth() * 1.5));
			double fieldY = (double)(offsetY + (y * Hexagon.getHexheight() * 0.5));
			fieldObjects.add(createSpaceObject(
					resources.getObjects()[objects[i][0]],
					fieldX, fieldY, 0));
		}
		for (int y = 0; y < data.length; y++)
		{
			for (int x = 0; x < data[y].length; x++)
			{
				int hexXOffset = (1 == y % 2
						? (int)(Hexagon.getHexwidth() * 0.75)
						: 0);
				double fieldX = (double)(offsetX + hexXOffset + (x * Hexagon.getHexwidth() * 1.5));
				double fieldY = (double)(offsetY + (y * Hexagon.getHexheight() * 0.5));
				FieldSprite grid = new FieldSprite(
						resources.getHexagon(),
						fieldX, fieldY, 0, parent);
				grid.setIsGrid(true);
				fieldObjects.add(grid);
			}
		}
	}

	@Override
	public void move(long delta)
	{
		for (ListIterator<FieldSprite> it = fieldObjects.listIterator();
				it.hasNext();)
		{
			FieldSprite field = it.next();
			field.move(delta);
		}
	}

}
