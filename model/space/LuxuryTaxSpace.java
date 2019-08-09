package alsid.model.space;

import alsid.model.game.Player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class for the luxury tax space.
 */
public class LuxuryTaxSpace implements Space {

    private int         nPosition;
    private ImageView imgView;

    public LuxuryTaxSpace()
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

    public double getTax()
    {
        return 75;
    }

    /**
     * Deducts $75 from the <code>player</code> that lands on <code>this</code> space.
     * @param player Player that landed on this model.space.
     */
    @Override
    public String onLand(Player player)
    {
        return player.getName() + " landed on the " + toString() + " space. ";
    }

    @Override
    public String toString()
    {
        return "Luxury Tax";
    }
}