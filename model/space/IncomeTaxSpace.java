package alsid.model.space;

import alsid.model.game.Player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class for the income tax space.
 */
public class IncomeTaxSpace implements Space {

    private int         nPosition;
    private ImageView imgView;

    /**
     * Constructor for an income tax space.
     */
    public IncomeTaxSpace()
    {
        imgView = new ImageView(new Image("/alsid/assets/tile-tax.png"));
    }

    /**
     * Method to get this IncomeTaxSpace's position on the board.
     * @return integer position of this IncomeTaxSpace.
     */
    public int getPosition()
    {
        return nPosition;
    }

    /**
     * Method to get the image assigned to this IncomeTaxSpace.
     * @return ImageView of this IncomeTaxSpace.
     */
    @Override
    public ImageView getImage()
    {
        return this.imgView;
    }

    /**
     * Changes this IncomeTaxSpace's position on the board.
     * @param position   New position of the IncomeTaxSpace.
     */
    public void setPosition(int position)
    {
        nPosition = position;
    }

    /**
     * Gets the tax to be paid when the player lands on this space.
     * If 10% of <code>player.getMoney()</code> is larger than $200, that amount
     * will be paid by the player.
     * @param player Player that landed on this space.
     * @return Tax to be paid by the player.
     */
    public double getTax(Player player)
    {
        if(player.getMoney() * 0.1 <= 200)
            return 200;
        else return player.getMoney() * 0.1;
    }

    /**
     * Returns the String version of this event.
     * @param player Player that landed on <code>this</code> space.
     * @return String version of event.
     */
    @Override
    public String onLand(Player player)
    {
        return player.getName() + " landed on the " + toString() + " space. ";
    }

    /**
     * Returns the String form of this space.
     * @return Name of this space.
     */
    @Override
    public String toString()
    {
        return "Income Tax";
    }
}