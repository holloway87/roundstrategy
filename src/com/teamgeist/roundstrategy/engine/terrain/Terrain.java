package com.teamgeist.roundstrategy.engine.terrain;

import java.awt.image.BufferedImage;

import com.teamgeist.roundstrategy.RoundStrategy;
import com.teamgeist.roundstrategy.engine.Sprite;

public class Terrain extends Sprite
{

	private static final long serialVersionUID = 1345112858280497480L;

	public Terrain(BufferedImage[] i, double x, double y, long delay, RoundStrategy p)
	{
		super(i, x, y, delay, p);
	}

}
