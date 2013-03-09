package com.teamgeist.roundstrategy.engine.terrain;

import java.awt.image.BufferedImage;

import com.teamgeist.roundstrategy.RoundStrategy;
import com.teamgeist.roundstrategy.engine.Sprite;

public class Hexagon extends Sprite
{

	private static final long serialVersionUID = -3380389167694927490L;
	private static final int hexWidth = 120;
	private static final int hexHeight = 102;

	public Hexagon(BufferedImage[] i, double x, double y, long delay,
			RoundStrategy p)
	{
		super(i, x, y, delay, p);
	}

	public static int getHexwidth()
	{
		return hexWidth;
	}

	public static int getHexheight()
	{
		return hexHeight;
	}

}
