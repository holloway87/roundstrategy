package roundStrategy;


import javax.swing.JFrame;

public class RoundStrategy extends JFrame
{

	private static final long serialVersionUID = 5156191450140937526L;

	public RoundStrategy()
	{
		GameBoard board = new GameBoard();
		add(board);
		setTitle("RoundStrategy");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(640, 480);
		setResizable(false);
		setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println(System.getProperty("user.dir"));
		new RoundStrategy();
	}

}
