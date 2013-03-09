package com.teamgeist.roundstrategy.engine.objects;

import java.awt.image.BufferedImage;

import com.teamgeist.roundstrategy.RoundStrategy;
import com.teamgeist.roundstrategy.engine.SpaceObject;

public class Nebula extends SpaceObject
{

	private static final long serialVersionUID = 8016000521853013979L;


	public Nebula(BufferedImage[] i, double x, double y, long delay,
			RoundStrategy p)
	{
		super(i, x, y, delay, p);
	}

}
