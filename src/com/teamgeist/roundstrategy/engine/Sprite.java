package com.teamgeist.roundstrategy.engine;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;

import com.teamgeist.roundstrategy.RoundStrategy;


public abstract class Sprite extends Double implements Drawable, Movable
{

	private static final long serialVersionUID = -4781211977135819909L;
	private BufferedImage[] pics;
	private long delay;
	@SuppressWarnings("unused")
	private RoundStrategy parent;
	private int currentpic = 0;
	private long animation = 0;
	protected double dx;
	protected double dy;


	public Sprite(BufferedImage[] i, double x, double y, long delay, RoundStrategy p)
	{
		pics = i;
		this.x = x;
		this.y = y;
		this.delay = delay;
		this.width = pics[0].getWidth();
		this.height = pics[0].getHeight();
		parent = p;
	}

	private void computeAnimation()
	{
		currentpic++;
		if (currentpic >= pics.length)
		{
			currentpic = 0;
		}
	}

	@Override
	public void doLogic(long delta)
	{
		if (1 < pics.length)
		{
			animation += delta / 1000000;
			if (animation > delay)
			{
				animation = 0;
				computeAnimation();
			}
		}
	}

	@Override
	public void drawObjects(Graphics g)
	{
		g.drawImage(pics[currentpic], (int) x, (int) y, null);
	}

	/**
	 * Get horizontal speed.
	 *
	 * @return double
	 */
	public double getDx()
	{
		return dx;
	}

	/**
	 * Get vertical speed.
	 *
	 * @return double
	 */
	public double getDy()
	{
		return dy;
	}

	@Override
	public void move(long delta)
	{
		if (0 != dx)
		{
			x += dx * (delta / 1e9);
		}

		if (0 != dy)
		{
			y += dy * (delta / 1e9);
		}
	}

	/**
	 * Set horizontal speed.
	 *
	 * @param dx
	 */
	public void setDx(double dx)
	{
		this.dx = dx;
	}

	/**
	 * Set vertical speed.
	 *
	 * @param dy
	 */
	public void setDy(double dy)
	{
		this.dy = dy;
	}

}
