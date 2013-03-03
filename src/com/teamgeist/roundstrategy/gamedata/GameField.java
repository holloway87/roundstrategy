package com.teamgeist.roundstrategy.gamedata;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Vector;

import com.teamgeist.roundstrategy.RoundStrategy;
import com.teamgeist.roundstrategy.engine.Drawable;
import com.teamgeist.roundstrategy.engine.Movable;
import com.teamgeist.roundstrategy.engine.terrain.Terrain;

public class GameField implements Drawable, Movable
{

	private int offsetX = 0;
	private int offsetY = 0;
	private BufferedImage[][] terrainImages;
	private Terrain[][] terrains;
	private Terrain[][] paintTerrains;
	private int[][] levelData;
	private Vector<Integer> lastSuperiorTerrain;


	/*
	 * Terrain infos where to find which direction on what terrain
	 *
	 * Order:
	 * 0: full
	 * to grass,
	 * to mud
	 * and then to is diveded in:
	 * 1: n, 2: ne, 3: e, 4: se, 5: s, 6: sw, 7: w, 8: nw,
	 * 9: alone, 10: alone n, 11: alone e, 12: alone s, 13: alone w
	 */
	private static final int[][][] terrainInfo = {
			/*
			 * 0: grass
			 */
			{
				// full
				{0, 1, 2, 3},
			},
			/*
			 * 1: mud
			 */
			{
				// full
				{22, 23, 24, 25},
				// to grass n
				{6, 7},
				// to grass ne
				{5},
				// to grass e
				{10, 11},
				// to grass se
				{13},
				// to grass s
				{14, 15},
				// to grass sw
				{12},
				// to grass w
				{8, 9},
				// to grass nw
				{4},
				// to grass alone
				{16, 17},
				// to grass alone n
				{18},
				// to grass alone e
				{19},
				// to grass alone s
				{20},
				// to grass alone w
				{21},
			},
	};


	public GameField(BufferedImage[][] terrains)
	{
		this.terrainImages = terrains;
	}

	public int calcTerrain(int x, int y)
	{
		int type = levelData[y][x];
		int[] terrain = {};
		int top = levelData[y][x];
		int right = levelData[y][x];
		int bottom = levelData[y][x];
		int left = levelData[y][x];

		if (0 == x)
		{
			lastSuperiorTerrain = new Vector<Integer>();
			lastSuperiorTerrain.add(type);
		}
		int length = lastSuperiorTerrain.size();

		if (0 < y)
		{
			top = levelData[y -1][x];
		}
		if (0 < x)
		{
			left = levelData[y][x -1];
		}
		if (y < getFieldsHeight() -1)
		{
			bottom = levelData[y +1][x];
		}
		if (x < getFieldsWidth() -1)
		{
			right = levelData[y][x +1];
		}

		int offset = 0;
		if (lastSuperiorTerrain.lastElement() != type &&
				(2 > length || lastSuperiorTerrain.elementAt(length - 2) != type))
		{
			lastSuperiorTerrain.add(type);
			// nw
			if (type != left && type != top && type == right)
			{
				terrain = terrainInfo[type][offset + 8];
			}
			// w
			else if (type == top && type == bottom && type != left) {
				terrain = terrainInfo[type][offset + 7];
			}
			// sw
			else if (type != bottom && type != left && type == right) {
				terrain = terrainInfo[type][offset + 6];
			}
			// alone
			else if (type != top && type != right && type != bottom && type != left)
			{
				lastSuperiorTerrain.remove(length);
				terrain = terrainInfo[type][offset + 9];
			}
			// alone n
			else if (type != top && type != right && type == bottom && type != left)
			{
				lastSuperiorTerrain.remove(length);
				terrain = terrainInfo[type][offset + 10];
			}
			// alone e
			else if (type != top && type != right && type != bottom && type == left)
			{
				lastSuperiorTerrain.remove(length);
				terrain = terrainInfo[type][offset + 11];
			}
			// alone s
			else if (type == top && type != right && type != bottom && type != left)
			{
				lastSuperiorTerrain.remove(length);
				terrain = terrainInfo[type][offset + 12];
			}
			// alone w
			else if (type != top && type == right && type != bottom && type != left)
			{
				lastSuperiorTerrain.remove(length);
				terrain = terrainInfo[type][offset + 13];
			}
		}
		else if (2 <= length &&
				lastSuperiorTerrain.elementAt(length - 2) != type &&
				lastSuperiorTerrain.elementAt(length - 2) == right)
		{
			lastSuperiorTerrain.remove(length -1);
			// ne
			if (type != top && type != right) {
				terrain = terrainInfo[type][offset + 2];
			}
			// e
			else if (type == bottom && type != right) {
				terrain = terrainInfo[type][offset + 3];
			}
			// se
			else if (type != bottom && type != right) {
				terrain = terrainInfo[type][offset + 4];
			}
		}
		// n
		else if (2 <= length &&
				lastSuperiorTerrain.elementAt(length - 2) == top &&
				type != top && type == left && type == right) {
			terrain = terrainInfo[type][offset + 1];
		}
		// s
		else if (2 <= length &&
				lastSuperiorTerrain.elementAt(length - 2) == bottom &&
				type != bottom && type == left && type == right) {
			terrain = terrainInfo[type][offset + 5];
		}

		if (0 == terrain.length)
		{
			terrain = terrainInfo[type][0];
		}

		int index = (int)(Math.random() * terrain.length);

		return terrain[index];
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
	}

	@Override
	public void doLogic(long delta)
	{
	}

	@Override
	public void move(long delta) {}

	public void loadLevel(int[][] data, RoundStrategy parent)
	{
		if (0 == data.length)
		{
			return;
		}

		levelData = data;
		lastSuperiorTerrain = new Vector<Integer>();

		terrains = new Terrain[data.length][data[0].length];
		for (int y = 0; y < data.length; y++)
		{
			for (int x = 0; x < data[y].length; x++)
			{
				int terrainInfo = calcTerrain(x, y);
				BufferedImage[] image = terrainImages[terrainInfo];
				terrains[y][x] = new Terrain(
						image,
						offsetX + (x * image[0].getWidth()),
						offsetY + (y * image[0].getHeight()),
						0, parent);
			}
		}
	}

}
