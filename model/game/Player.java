package alsid.model.game;

import alsid.model.asset.*;
import alsid.model.chance.Chance;
import javafx.scene.image.Image;

import java.util.*;

public class Player implements Payable
{
	//...ATTRIBUTES
	
	private String 	strName;
	private int 	nNumber;
	private int 	nPosition = 0;
	private Image 	img;
	
	private double 				dMoney = 1500;
	private ArrayList <Asset> 	assets;
	private ArrayList <Chance> 	ownedChance;	
	
	//...CONSTRUCTOR
	
	/**
     * Constructor for a Player.
     * @param 	name	Name of <code>this</code> player.
     * @param 	number	Corresponding number (for ordering) of <code>this</code> player.
     */
	public Player (String name, int number)
	{
		strName = name;
		nNumber = number;
		nPosition = 0;
		
		assets = new ArrayList <Asset> ();
		ownedChance = new ArrayList <Chance> ();
	}
	
	
	
	//...GETTERS
	
	/**
     * Gets the name of <code>this</code> player.
     * @return Name of player.
     */
	public String getName ()
	{
		return strName;
	}
	
	/**
     * Gets the number assigned to <code>this</code> player.
     * @return Number of <code>this</code> player.
     */
	public int getNumber ()
	{
		return nNumber;
	}
	
	/**
     * Gets the position of <code>this</code> player with respect to the board.
     * @return The position of <code>this</code> player.
     */
	public int getPosition ()
	{
		return nPosition;
	}

	/**
	 * Gets the image/sprite assigned to the <code>this</code> player.
	 * @return The image/sprite assigned to the <code>this</code> player.
	 */
	public Image getImage()
	{
		return this.img;
	}

	/**
     * Gets the amount of money <code>this</code> player has.
     * @return Amount of money <code>this</code> player has.
     */
	public double getMoney ()
	{
		return dMoney;
	}
	
	/**
     * Gets the <code>ArrayList</code> of assets <code>this</code> player has.
     * @return <code>ArrayList</code> of <code>this</code> player's assets.
     */
	public ArrayList <Asset> getAssets ()
	{
		return assets;
	}

	/**
     * Gets the <code>ArrayList</code> of properties <code>this</code> player has.
     * @return <code>ArrayList</code> of <code>this</code> player's properties.
     */
	public ArrayList <Property> getProperties ()
	{
		ArrayList <Property> propTemp = new ArrayList <>();
		assets.forEach(asset -> {
			if (asset instanceof Property)
				propTemp.add((Property) asset);
		});
		
		return propTemp;
	}

	/**
     * Gets the <code>ArrayList</code> of Utilities <code>this</code> player has.
     * @return <code>ArrayList</code> of <code>this</code> player's utilities.
     */
	public ArrayList <Utility> getUtilities ()
	{
		ArrayList <Utility> utilTemp = new ArrayList <>();
		assets.forEach(asset -> {
			if (asset instanceof Utility)
				utilTemp.add((Utility) asset);
		});
		
		return utilTemp;
	}

	/**
     * Gets the <code>ArrayList</code> of Railroads <code>this</code> player has.
     * @return <code>ArrayList</code> of <code>this</code> player's railroads.
     */
	public ArrayList <Railroad> getRailroads ()
	{
		ArrayList <Railroad> railTemp = new ArrayList <>();
		assets.forEach(asset -> {
			if (asset instanceof Railroad)
				railTemp.add((Railroad) asset);
		});
		
		return railTemp;
	}

	/**
     * Gets the <code>ArrayList</code> of chance cards <code>this</code> player has.
     * @return <code>ArrayList</code> of <code>this</code> player's chance cards.
     */
	public ArrayList <Chance> getChanceCards()
	{
		return ownedChance;
	}



	//...SETTERS

	public void setImage(/**Image img*/)
	{
		img = new Image("/alsid/assets/sprite-lasalle-red.png");
	}


	
	//...METHODS
	
	/**
     * Modifies the amount of money <code>this</code> player has.
     * @param amount How much <code>this</code> player's money will be increased or decreased.
     */
	@Override
	public void add (double amount)
	{
		dMoney += amount;
	}
	
	/**
     * Adds the model.asset.asset to the list of assets <code>this</code> player has.
     * @param asset The model.asset.asset to be owned by <code>this</code> player.
     */
	public void add (Asset asset)
	{
		assets.add(asset);
		asset.setOwner(this);
	}
	
	/**
     * Adds the chance card to <code>this</code> player's chance cards.
     * @param card The card to be added to <code>this</code> player.
     */
	public void add (Chance card)
	{
		ownedChance.add(card);
		card.giveTo(this);
	}
	
	/**
     * Removes the model.asset.asset from <code>this</code> player's <code>ArrayList</code> of assets.
     * @param 	asset 	Asset to be removed from <code>this</code> player.
	 * @return	<code>true</code> if removal is successful
     */
	public boolean removeAsset (Asset asset)
	{
		if (assets.contains(asset))
		{
			assets.remove(asset);
			return true;
		}
		return false;
	}
	
	
	/**
     * Decreases <code>this</code> player's money after purchasing the property,
	 * adds the property to <code>this</code> player's model.asset.asset <code>ArrayList</code>, and
	 * sets the ownership of <code>this</code> property to <code>this</code> player.
	 * @param prop The model.asset.asset being purchased by <code>this</code> player.
	 * @return 	<code>true</code> if purchase is successful
     */
	public boolean purchase (Property prop)
	{
		if (!prop.isOwned())
		{
			add(-1 * prop.getPrice());
			add(prop);
			prop.setOwner(this);
			return true;
		}
		return false;
	}

	/**
     * Decreases <code>this</code> player's money after purchasing the railroad,
	 * adds the railroad to <code>this</code> player's model.asset.asset <code>ArrayList</code>, and
	 * sets the ownership of the railroad to <code>this</code> player.
	 * @param 	rail	The railroad being purchased by <code>this</code> player.
	 * @return 	<code>true</code> if purchase is successful
     */
	public boolean purchase (Railroad rail)
	{
		if (!rail.isOwned())
		{
			add(-1 * rail.getPrice());
			add(rail);
			rail.setOwner(this);
			return true;
		}
		return false;
	}

	/**
     * Decreases <code>this</code> player's money after purchasing the utility,
	 * adds the utility to <code>this</code> player's model.asset.asset <code>ArrayList</code>, and
	 * sets the ownership of the utility to <code>this</code> player.
	 * @param 	util	The utility being purchased by <code>this</code> player.
	 * @return 	<code>true</code> if purchase is successful
     */
	public boolean purchase (Utility util)
	{
		if (!util.isOwned())
		{
			add(-1 * util.getPrice());
			add(util);
			util.setOwner(this);
			return true;
		}
		return false;
	}
	
	/**
     * Develops the property (constructs one house).
	 * @param 	prop 	The property to be developed by the owner.
     * @return 	<code>true</code> if development is successful
     */
	public boolean develop (Property prop)
	{
		if (prop.canDevelop() && prop.getOwner().equals(this)) {
			prop.incHouseCount();
			this.add(-1 * prop.getHousePrice());
			return true;
		}	
		return false;
	}
	
	/**
     * Transfers corresponding rent money from <code>this</code> player to the property's owner.
	 * @param 	prop 				The property <code>this</code> player is currently located at.
	 * @return 	<code>true</code> if the transaction is successful
     */
	public boolean payRent (Property prop)
	{
		if (prop.isOwned() && !prop.getOwner().equals(this))
		{
			this.add(-1 * prop.getRent());
			prop.getOwner().add(prop.getRent());
			prop.incRentCollected(prop.getRent());
			prop.resetTempMod();
			return true;
		}
		return false;
	}

	/**
     * Transfers corresponding rent money from <code>this</code> player to the <code>rail</code>'s owner.
	 * @param 	rail 	The railroad <code>this</code> player is currently located at.
	 * @return 	<code>true</code> if the transaction is successful
     */
	public boolean payRent (Railroad rail)
	{	
		if(rail.isOwned() && !rail.getOwner().equals(this))
		{
			this.add(-1 * rail.getRent());
			rail.getOwner().add(rail.getRent());
			rail.resetTempMod();
			return true;
		}
		return false;
	}

	/**
     * Transfers corresponding rent money from <code>this</code> player to the <code>util</code>'s owner.
	 * @param 	util 	The utility <code>this</code> player is currently located at.
	 * @param	nDiceRoll	Current dice roll this turn.
	 * @return 	<code>true</code> if the transaction is successful
     */
	public boolean payRent (Utility util, int nDiceRoll)
	{	
		if (util.isOwned() && !util.getOwner().equals(this))
		{
			this.add(-1 * util.getRent() * nDiceRoll);
			util.getOwner().add(util.getRent() * nDiceRoll);
			util.resetTempMod();
			return true;
		}
		return false;
	}

	/**
     * Checks if <code>this</code> player is bankrupt.
     * @return 	<code>true</code> if <code>this</code> player's money is less than or equal to 0
     */
	@Override
	public boolean isBankrupt ()
	{
		return dMoney <= 0;
	}

	/**
	 * Gets the number of full sets of properties that <code>this</code> player has.
	 * @return Full sets of properties.
	 */
	public int getFullSetCount() {
		int fullSetCount = 0;
		ArrayList <Integer> colorScanHistory = new ArrayList <>();
		ArrayList <Property> properties = getProperties();

		for (int i = 0; i < properties.size(); i++) {
			Integer color = properties.get(i).getColor();
			if (!colorScanHistory.contains(color)) {
				colorScanHistory.add(color);
				switch (color){
					case Property.COLOR_GRAY:
					case Property.COLOR_RED:
					case Property.COLOR_ORANGE: 
						{
							if (properties.get(i).getOwnerCount() == 2)
								fullSetCount++;
						} break;
					case Property.COLOR_PURPLE:
					case Property.COLOR_PINK:
					case Property.COLOR_BLUE:
					case Property.COLOR_GREEN:
						{
							if (properties.get(i).getOwnerCount() == 3)
								fullSetCount++;
						} break;
				}
			}
		}

		return fullSetCount;
	}

	/**
	 * Advances the player's position by <code>spaces</code>. If <code>nPosition</code> reaches
	 * Game.SPACE_COUNT, it will loop position back.
	 * @param spaces Number of spaces to advance by.
	 * @return <code>true</code> if <code>this</code> player passed START or not.
	 */
	public boolean move(int spaces) {
		int prevPosition = this.nPosition;

		this.nPosition = (this.nPosition + spaces) % Board.SPACE_COUNT;

		return this.nPosition < prevPosition;
	}

	/**
	 * Moves the player's position to a certain <code>space</code> in the board. If <code>nPosition</code> reaches
	 * Game.SPACE_COUNT, it will loop position back.
	 * @param space Number of spaces to advance by.
	 * @return <code>true</code> if <code>this</code> player passed START.
	 */
	public boolean moveTo(int space) {
		int prevPosition = this.nPosition;

		this.nPosition = space % Board.SPACE_COUNT;

		return this.nPosition < prevPosition;
	}

	//tbd
	public void setPosition (int position)
	{
		this.nPosition = position;
	}

	/**
	 * Changes the ownership of the Asset <code>toTrade</code> with the <code>trader</code>'s Asset <code>offer</code>.
	 * @param toTrade Asset that <code>this</code> player owns to trade
	 * @param trader Player to trade with
	 * @param offer Asset that the <code>trader</code> is offering
	 */
	public void trade(Asset toTrade, Player trader, Asset offer) // Can they trade any type of ownable or is it just Property and Property, etc. ANS: Up to you
	{
		toTrade.setOwner(trader);
		if (toTrade instanceof Property)
		{
			((Property) toTrade).resetFootTraffic();
		}

		offer.setOwner(this);
		if (offer instanceof Property)
		{
			((Property) offer).resetFootTraffic();
		}
	}

	//TODO sharmaine

	/**
	 * Changes the ownership of the Asset <code>toTrade</code> with the <code>trader</code>'s Asset <code>offer</code>.
	 * @param otherPlayer Player to trade with
	 * @param currentSpace Asset <code>this</code> player is currently on
	 * @param offer Asset that <code>this</code> player is offering
	 */
	public void trade(Player otherPlayer, Asset currentSpace, Asset offer) // Can they trade any type of ownable or is it just Property and Property, etc. ANS: Up to you
	{
		this.add(currentSpace);
		this.removeAsset(offer);

		otherPlayer.add(offer);
		otherPlayer.removeAsset(currentSpace);

		((Property) offer).resetFootTraffic();
		((Property) currentSpace).resetFootTraffic();
	}

	/**
	 * Pays the given <code>amount</code> to the <code>payee</code>.
	 * @param payee Player or bank to receive amount.
	 * @param amount Money to give.
	 *
	 * @return
	 */
	@Override
	public String payTo(Payable payee, double amount) { // TODO Lance: New Function
		dMoney -= amount;
		payee.add(amount);

		return getName() + " paid $" + amount + " to " + payee.toString() + ".";
	}
}