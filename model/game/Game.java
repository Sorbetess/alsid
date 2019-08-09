package alsid.model.game;

import alsid.model.chance.Chance;

import java.util.*;


public class Game
{
	private Board 				gameBoard;
	private ArrayList <Player> 	players;
	private Deck 				chanceDeck;

	private Bank				bank;
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

	public void initBank()
	{
		bank = new Bank(players.size());
	}



	//...GETTERS

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

	public Bank getBank()
	{
		return bank;
	}

	public Deck getChanceDeck()
	{
		return chanceDeck;
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
		if(bank.isBankrupt())
			return BANK_EMPTY;

		return 0;
	}

	public void rankPlayers()
	{
		boolean hasTie = false;

		for(int i = 0; i < getPlayers().size(); i++)
		{
			for (int j = 0; j < getPlayers().size(); j++)
			{
				if (i != j && getPlayers().get(i).getNetWorth() == getPlayers().get(j).getNetWorth())
				{
					hasTie = true;
				}
			}
		}

		if(!hasTie)
		{
			getPlayers().sort((o1, o2) ->
			{
				if (o1.getNetWorth() == o2.getNetWorth()) {
					return 0;
				} else if (o1.getNetWorth() > o2.getNetWorth()) {
					return 1;
				}

				return -1;
			});
		}

		else if(hasTie)
		{
			getPlayers().sort((o1, o2) ->
			{
				if (o1.getMoney() == o2.getMoney()) {
					return 0;
				} else if (o1.getMoney() > o2.getMoney()) {
					return 1;
				}

				return -1;
			});
		}

		Collections.reverse(getPlayers());
	}
}