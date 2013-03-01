package gamedata;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameField
{

	private int offsetX;
	private int offsetY;
	private int width;
	private int height;
	private int fieldWidth = 40;
	private int fieldHeight = 40;
	private int[][] terrain;
	private static BufferedImage fieldTileset;


	public GameField(int x, int y, int sizeX, int sizeY)
	{
		this.offsetX = x;
		this.offsetY = y;
		this.width = sizeX;
		this.height = sizeY;
		init();
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public void init()
	{
		try
		{
			fieldTileset = ImageIO.read(getClass().getResource("/resource/tileset.png"));
		}
		catch (IOException e) {}
		terrain = new int[height][width];
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				terrain[y][x] = (int) (Math.random() * 3);
			}
		}
	}

	public void render(Graphics2D g)
	{
		g.setColor(new Color(180, 180, 180));
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				g.drawImage(
						fieldTileset.getSubimage(
								terrain[y][x] * fieldWidth, 0,
								fieldWidth, fieldHeight),
						null,
						offsetX + (fieldWidth * x),
						offsetY + (fieldHeight * y));
				/*g.drawRect(
						offsetX + (fieldWidth * x),
						offsetY + (fieldHeight * y),
						fieldWidth,
						fieldHeight);*/
			}
		}
	}

}
