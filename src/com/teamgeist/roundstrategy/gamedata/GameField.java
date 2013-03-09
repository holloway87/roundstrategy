package com.teamgeist.roundstrategy.gamedata;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ListIterator;
import java.util.Vector;

import com.teamgeist.roundstrategy.InputManager;
import com.teamgeist.roundstrategy.RoundStrategy;
import com.teamgeist.roundstrategy.engine.Drawable;
//import com.teamgeist.roundstrategy.engine.HexagonGrid;
import com.teamgeist.roundstrategy.engine.Movable;
import com.teamgeist.roundstrategy.engine.objects.Nebula;
import com.teamgeist.roundstrategy.engine.terrain.Hexagon;
import com.teamgeist.roundstrategy.engine.terrain.Terrain;

public class GameField implements Drawable, Movable
{

	private int offsetX = 0;
	private int offsetY = 0;
	private int scrollSpeed = 250;
	private Terrain[][] terrains;
	private Terrain[][] paintTerrains;
	private Vector<Hexagon> hexGrid;
	private Vector<Nebula> nebulae;
	private Vector<Hexagon> paintHexGrid;
	@SuppressWarnings("unused")
	private RoundStrategy parent;
	private ResourceManager resources;
	private Vector<Nebula> paintNebulae;


	public GameField(ResourceManager resources, RoundStrategy p)
	{
		this.resources = resources;
		this.parent = p;
	}

	public void checkKeys(InputManager input)
	{
		// Terrains
		for (int y = 0; y < terrains.length; y++)
		{
			for (int x = 0; x < terrains[y].length; x++)
			{
				if (input.getState(InputManager.SCROLL_UP))
				{
					terrains[y][x].setDy(scrollSpeed);
				}
				if (input.getState(InputManager.SCROLL_DOWN))
				{
					terrains[y][x].setDy(-scrollSpeed);
				}
				if (input.getState(InputManager.SCROLL_RIGHT))
				{
					terrains[y][x].setDx(-scrollSpeed);
				}
				if (input.getState(InputManager.SCROLL_LEFT))
				{
					terrains[y][x].setDx(scrollSpeed);
				}
				if (!input.getState(InputManager.SCROLL_UP) &&
						!input.getState(InputManager.SCROLL_DOWN))
				{
					terrains[y][x].setDy(0);
				}
				if (!input.getState(InputManager.SCROLL_RIGHT) &&
						!input.getState(InputManager.SCROLL_LEFT))
				{
					terrains[y][x].setDx(0);
				}
			}
		}

		// Hexagon Grid
		for (ListIterator<Hexagon> it = hexGrid.listIterator();
				it.hasNext();)
		{
			Hexagon hex = it.next();
			if (input.getState(InputManager.SCROLL_UP))
			{
				hex.setDy(scrollSpeed);
			}
			if (input.getState(InputManager.SCROLL_DOWN))
			{
				hex.setDy(-scrollSpeed);
			}
			if (input.getState(InputManager.SCROLL_RIGHT))
			{
				hex.setDx(-scrollSpeed);
			}
			if (input.getState(InputManager.SCROLL_LEFT))
			{
				hex.setDx(scrollSpeed);
			}
			if (!input.getState(InputManager.SCROLL_UP) &&
					!input.getState(InputManager.SCROLL_DOWN))
			{
				hex.setDy(0);
			}
			if (!input.getState(InputManager.SCROLL_RIGHT) &&
					!input.getState(InputManager.SCROLL_LEFT))
			{
				hex.setDx(0);
			}
		}

		// Nebulae
		for (ListIterator<Nebula> it = nebulae.listIterator();
				it.hasNext();)
		{
			Nebula nebula = it.next();
			if (input.getState(InputManager.SCROLL_UP))
			{
				nebula.setDy(scrollSpeed);
			}
			if (input.getState(InputManager.SCROLL_DOWN))
			{
				nebula.setDy(-scrollSpeed);
			}
			if (input.getState(InputManager.SCROLL_RIGHT))
			{
				nebula.setDx(-scrollSpeed);
			}
			if (input.getState(InputManager.SCROLL_LEFT))
			{
				nebula.setDx(scrollSpeed);
			}
			if (!input.getState(InputManager.SCROLL_UP) &&
					!input.getState(InputManager.SCROLL_DOWN))
			{
				nebula.setDy(0);
			}
			if (!input.getState(InputManager.SCROLL_RIGHT) &&
					!input.getState(InputManager.SCROLL_LEFT))
			{
				nebula.setDx(0);
			}
		}
	}

	public void cloneObjects() {
		cloneHexagons();
		cloneNebulae();
		cloneTerrains();
	}

	@SuppressWarnings("unchecked")
	public void cloneHexagons()
	{
		paintHexGrid = (Vector<Hexagon>) hexGrid.clone();
	}

	@SuppressWarnings("unchecked")
	public void cloneNebulae()
	{
		paintNebulae = (Vector<Nebula>) nebulae.clone();
	}

	public void cloneTerrains()
	{
		paintTerrains = terrains.clone();
	}

	public int getFieldsWidth()
	{
		if (0 == terrains.length)
		{
			return 0;
		}
		return terrains[0].length;
	}

	public int getFieldsHeight()
	{
		return terrains.length;
	}

	@Override
	public void drawObjects(Graphics g)
	{
		if (0 == paintTerrains.length)
		{
			return;
		}

		for (int y = 0; y < paintTerrains.length; y++)
		{
			for (int x = 0; x < paintTerrains[y].length; x++)
			{
				paintTerrains[y][x].drawObjects(g);
			}
		}

		if (null != paintNebulae)
		{
			for (ListIterator<Nebula> it = paintNebulae.listIterator();
					it.hasNext();)
			{
				Nebula nebula = it.next();
				nebula.drawObjects(g);
			}
		}

		if (null != paintHexGrid)
		{
			for (ListIterator<Hexagon> it = paintHexGrid.listIterator();
					it.hasNext();)
			{
				Hexagon grid = it.next();
				grid.drawObjects(g);
			}
		}
	}

	@Override
	public void doLogic(long delta)
	{
		// Hier mach ich jetz was
	}

	public void loadLevel(int[][] data, RoundStrategy parent)
	{
		if (0 == data.length)
		{
			return;
		}

		this.hexGrid = new Vector<Hexagon>();
		this.nebulae = new Vector<Nebula>();

		//levelData = data;
		//lastSuperiorTerrain = new Vector<Integer>();

		terrains = new Terrain[data.length][data[0].length];
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
				terrains[y][x] = new Terrain(
						image, fieldX, fieldY, 0, parent);
				if (2 == x && 6 == y)
				{
					this.nebulae.add(new Nebula(
							resources.getNebula(),
							fieldX, fieldY, 0, parent));
				}
				this.hexGrid.add(new Hexagon(
						resources.getHexagon(),
						fieldX, fieldY, 0, parent));
			}
		}
	}

	@Override
	public void move(long delta)
	{
		for (int y = 0; y < terrains.length; y++)
		{
			for (int x = 0; x < terrains[y].length; x++)
			{
				terrains[y][x].move(delta);
			}
		}

		for (ListIterator<Nebula> it = nebulae.listIterator();
				it.hasNext();)
		{
			Nebula nebula = it.next();
			nebula.move(delta);
		}

		for (ListIterator<Hexagon> it = hexGrid.listIterator();
				it.hasNext();)
		{
			Hexagon hexGrid = it.next();
			hexGrid.move(delta);
		}
	}

}
