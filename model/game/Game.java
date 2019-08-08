package alsid.model.game;

import alsid.model.chance.Chance;

import java.util.*;


public class Game
{
	private Board 				gameBoard;
	private ArrayList <Player> 	players;
	private Deck 				chanceDeck;

	//SHARMAINE
	private double 				cashBank;
	private int 				currentPlayer = 0;
	private Random 				dice = new Random();

	public static final int		PLAYER_BANKRUPT = 1;
	public static final int 	TWO_FULL_SETS = 2;
	public static final int 	BANK_EMPTY = 3;
	
	public Game ()
	{
		players = new ArrayList<>();
		chanceDeck = new Deck();
	}

	public void initBoard()
	{
		gameBoard = new Board(players.size());
	}

	public Board getBoard()
	{
		return gameBoard;
	}

	public ArrayList<Player> getPlayers()
	{
		return players;
	}

	public int getCurrentPlayer()
	{
		return currentPlayer;
	}

	public double getCashBank()
	{
		return cashBank;
	}

	public void setCashBank(double cash)
	{
		this.cashBank = cash;
	}

	public void addPlayer(Player player)
	{
		players.add(player);
	}

	public void removePlayer(Player player)
	{
		players.remove(player);
	}

	public void nextTurn()
	{
		currentPlayer = (currentPlayer + 1) % players.size();
	}

	public void playerTurn()
	{
		/* Steps:
		0. In jail?
			i. Has Get-Out-Of-Jail-Free Card: Use it TODO: Give option to use GOJF or use immediately?
			ii: :( : Pay 50, Check for win condition
		*/
		Player player = players.get(currentPlayer);
		if (player.getPosition() == Board.JAIL)
		{
			int hasGOJF = -1; /* GOJF = Get Out of Jail Free */
			for	(int i = 0; i < player.getChanceCards().size() && hasGOJF == -1; i++)
			{
				if (player.getChanceCards().get(i).isType(Chance.GET_OUT_OF_JAIL)) {
					hasGOJF = i;
				}
			}

			if (hasGOJF != -1) {
				player.add(-50);
				if (checkGameEnd() != 0) {
					return;
				}
			} else {
				player.getChanceCards().get(hasGOJF).useEffect(player);
			}
		}

		/*
		1. Roll
		*/
		int nDice = dice.nextInt();
		int prevSpace = player.getPosition();

		/*
		2. Advance that many steps
		*/
		player.move(nDice);

		/*
		3. Passed go? Add 200: Do nothing
		*/

		if (player.getPosition() - prevSpace <= 0)
		{
			player.add(200);
		}

		/*
		4. Check model.space landed:
			a. START / Free Parking: Do nothing
			b. Tax Space / Community Service: Pay fine
			c. model.game.Chance: Draw card; apply effect
			d. Property:
				i. Unowned: Purchase / Do Nothing
				ii. Owned by c.p.: Develop
				iii. Owned by a.p.: Pay Rent / Trade
			e. Utility / Railroad:
				i. Unowned: Purchase / Do Nothing
				ii. Owned by c.p.: Do Nothing
				iii. Owned by a.p.: Pay Rent / Trade
		5. if 4.b,c,d happen: Check for win condition
		 */
		gameBoard.getSpaces().get(player.getPosition()).onLand(player);
	}
	
	public int checkGameEnd ()
	{
		for (int i = 0; i < players.size(); i++)
		{
			//check if a player does not have enough money to pay for rent, tax or fine
			if (players.get(i).isBankrupt())
				return PLAYER_BANKRUPT;

			//check if a player owns 2 full sets of properties with the same color
			if(players.get(i).getFullSetCount() >= 2)
				return TWO_FULL_SETS;
		}

		//check if the bank is out of cash
		if(cashBank <= 0)
			return BANK_EMPTY;

		return 0;
	}
}