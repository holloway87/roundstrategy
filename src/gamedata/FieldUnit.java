package gamedata;

import java.awt.Graphics2D;

public class FieldUnit
{

	private String name;


	public FieldUnit(String name)
	{
		this.name = name;
	}

	public void render(Graphics2D g, int x, int y)
	{
		g.drawString(name, x, y);
	}

}
