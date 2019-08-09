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

    public double getTax(Player player)
    {
        if(player.getMoney() * 0.1 <= 200)
            return 200;
        else return player.getMoney() * 0.1;
    }

    /**
     * Deducts $200 or 10% of the income of the <code>player</code> that lands on <code>this</code> space, depending on
     * which one is higher.
     * @param player model.game.Player that landed on this space.
     */
    @Override
    public String onLand(Player player)
    {
        return player.getName() + " landed on the " + toString() + " space. ";
    }

    @Override
    public String toString()
    {
        return "Income Tax";
    }
}