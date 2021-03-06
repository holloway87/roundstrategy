package com.teamgeist.roundstrategy.gamedata;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.teamgeist.roundstrategy.exceptions.ImageLoadException;

public class ResourceManager
{

	private BufferedImage[][] terrains;
	private BufferedImage[] hexagon;
	private BufferedImage[][] objects;

	public ResourceManager()
	{
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

	public void loadHexagon()
	{
		hexagon = loadFromFile("/resource/hud/hexagon_one.png");
	}

	public void loadObjects()
	{
		BufferedImage[][] objects = {
			loadFromFile("/resource/objects/nebulae/nebular_static_blue_01.png"),
			loadFromFile("/resource/objects/other/pulsar_01.png"),
			loadFromFile("/resource/objects/planets/planet_yellow_01.png"),
			loadFromFile("/resource/objects/stars/stars_red_dwarf_01.png"),
		};
		this.objects = objects;
	}

	public void loadTerrains()
	{
		BufferedImage[][] terrains = {
			loadFromFile("/resource/tiles/space01.png"),
			loadFromFile("/resource/tiles/space02.png"),
			loadFromFile("/resource/tiles/space03.png"),
			loadFromFile("/resource/tiles/asteroids01.png"),
		};
		this.terrains = terrains;
	}

	public BufferedImage[] getHexagon()
	{
		if (null == hexagon)
		{
			loadHexagon();
		}
		return hexagon;
	}

	public BufferedImage[][] getObjects()
	{
		if (null == objects)
		{
			loadObjects();
		}
		return objects;
	}

	public BufferedImage[][] getTerrains()
	{
		if (null == terrains)
		{
			loadTerrains();
		}
		return terrains;
	}

}
