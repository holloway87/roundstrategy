package com.teamgeist.roundstrategy;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener
{

	public static final int SCROLL_UP = 0;
	public static final int SCROLL_DOWN = 1;
	public static final int SCROLL_LEFT = 2;
	public static final int SCROLL_RIGHT = 3;
	public static final int SHOW_GRID = 4;

	private boolean[] states;

	public InputManager()
	{
		int length = 5;
		states = new boolean[length];
		for (int i = 0; i < length; i++)
		{
			states[i] = false;
		}
		states[SHOW_GRID] = true;
	}

	public boolean getState(int key)
	{
		return states[key];
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_UP:
			states[SCROLL_UP] = true;
			break;
		case KeyEvent.VK_DOWN:
			states[SCROLL_DOWN] = true;
			break;
		case KeyEvent.VK_LEFT:
			states[SCROLL_LEFT] = true;
			break;
		case KeyEvent.VK_RIGHT:
			states[SCROLL_RIGHT] = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_UP:
			states[SCROLL_UP] = false;
			break;
		case KeyEvent.VK_DOWN:
			states[SCROLL_DOWN] = false;
			break;
		case KeyEvent.VK_LEFT:
			states[SCROLL_LEFT] = false;
			break;
		case KeyEvent.VK_RIGHT:
			states[SCROLL_RIGHT] = false;
			break;
		case KeyEvent.VK_G:
			states[SHOW_GRID] = !states[SHOW_GRID];
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}

}
