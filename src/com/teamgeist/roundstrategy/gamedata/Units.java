package com.teamgeist.roundstrategy.gamedata;

import java.awt.image.BufferedImage;

import com.teamgeist.roundstrategy.RoundStrategy;
import com.teamgeist.roundstrategy.engine.Sprite;

public class Units extends Sprite{

	private static final long serialVersionUID = -3081107821369995619L;

	public Units(BufferedImage[] i, double x, double y, long delay,
			RoundStrategy p) {
		super(i, x, y, delay, p);
	}

}
