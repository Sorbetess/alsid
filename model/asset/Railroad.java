package alsid.model.asset;

import alsid.model.game.Player;
import javafx.scene.image.Image;

public class Railroad extends Asset {
	
	//...ATTRIBUTES

    private static final double PRICE = 200;
    private static final double[] RENT_LEVELS = {25, 50, 100};



	//...CONSTRUCTOR
	
    /**
     * Constructor for a railroad.
     * @param strName Name of railroad.
     */
    public Railroad(String strName) {
        super(strName, PRICE);
        //TODO sharmaine
        super.setImage(new Image("/alsid/assets/tile-railroad.png"));
    }
	
	//...GETTERS

    /**
     * Gets the rent to be paid when a player lands on <code>this</code> railroad.
     * @return Rent to be paid.
     */
    @Override
    public double getRent() {
        return Railroad.RENT_LEVELS[getOwnerCount() - 1] * getRentPermMod() * getRentTempMod();
    }

    /**
     * Gets the base rent to be paid when a player lands on a railroad.
     * @return Array of rent levels.
     */
    public double[] getRentLevels() {
        return Railroad.RENT_LEVELS;
    }

    /**
     * Gets the number of railroads that the owner has.
     * @return Number of railroads owner has.
     */
    public int getOwnerCount() {
        return getOwner().getRailroads().size();
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
            info += "\nNumber of railroads owned by " + this.getOwner().getName() + ": " + this.getOwnerCount() +
                    "\nRent: $" + this.getRent();

        return info;
    }

}
