package com.teamgeist.roundstrategy.engine;

import java.awt.image.BufferedImage;

import com.teamgeist.roundstrategy.RoundStrategy;
import com.teamgeist.roundstrategy.engine.terrain.Hexagon;

public abstract class SpaceObject extends Sprite
{

	private static final long serialVersionUID = -1166754566982119888L;

	public SpaceObject(BufferedImage[] i, double x, double y, long delay,
			RoundStrategy p)
	{
		super(i, x, y, delay, p);
		int width = i[0].getWidth();
		int height = i[0].getHeight();
		int hexWidth = Hexagon.getHexwidth();
		int hexHeight = Hexagon.getHexheight();

		this.x = this.x - (width / 2) + (hexWidth / 2);
		this.y = this.y - (height / 2) + (hexHeight / 2);
	}

}
