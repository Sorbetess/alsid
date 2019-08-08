package alsid.model.asset;

import alsid.model.game.Player;
import javafx.scene.image.Image;

public class Utility extends Asset {
	
	//...ATTRIBUTES

    private static final double PRICE = 200;
	
	

	//...CONSTRUCTOR
	
    /**
     * Constructor for a utility.
     * @param strName Name of utility.
     */
    public Utility(String strName) {
        super(strName, PRICE);
        super.setImage(new Image("/alsid/assets/tile-utility.png"));
    }
	
	
	
	//...GETTERS

    /**
     * Gets the number of utilities that the owner has.
     * @return Number of utilities owner has.
     */
    public int getOwnerCount() {
        return getOwner().getUtilities().size();
    }

    /**
     * Gets the rent multiplier to be paid when a player lands on <code>this</code> utility. Note that
     * this value still needs to be multiplied to the dice roll to get the actual rent.
     * @return Rent to be paid.
     */
    @Override
    public double getRent() {
        return (getOwnerCount() >= 2 ? 10 : 4) * getRentPermMod() * getRentTempMod();
    }

    @Override
    public String toString()
    {
        return super.getName();
    }

    @Override
    public String getInfo() {
        String info = "";

        info += this.getName();

        if (this.isOwned())
            info += " (owned by " + this.getOwner().getName() + ")";
        else info += " (unowned)";

        info += "\nPrice: $" + this.getPrice();

        if (this.isOwned())
            info += "\nRent: $" + this.getRent();

        return info;
    }
}
