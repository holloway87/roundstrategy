package com.teamgeist.roundstrategy.engine;

import java.awt.image.BufferedImage;

import com.teamgeist.roundstrategy.RoundStrategy;

public class FieldSprite extends Sprite
{

	private static final long serialVersionUID = 5541166151050382059L;
	private boolean isGrid;


	public FieldSprite(BufferedImage[] i, double x, double y, long delay,
			RoundStrategy p)
	{
		super(i, x, y, delay, p);
		setIsGrid(false);
	}

	public boolean isGrid()
	{
		return isGrid;
	}

	public void setIsGrid(boolean isGrid)
	{
		this.isGrid = isGrid;
	}

}
