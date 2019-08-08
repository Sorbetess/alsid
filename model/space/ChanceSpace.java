package alsid.model.space;

import alsid.controller.GameScreenController;
import alsid.model.chance.Chance;
import alsid.model.chance.GetOutOfJailChance;
import alsid.model.game.Deck;
import alsid.model.game.Player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class for the Chance space.
 */
public class ChanceSpace implements Space {

    private Deck        deck;
    private int         nPosition;
    private ImageView imgView;

    public ChanceSpace()
    {
        //TODO sharmaine
        imgView = new ImageView(new Image("/alsid/assets/tile-chance.png"));
    }

    public ChanceSpace(Deck deck)
    {
        this.deck = deck;
        //TODO sharmaine
        imgView = new ImageView(new Image("/alsid/assets/tile-chance.png"));
    }

    /**
     * Method to get this ChanceSpace's position on the board.
     * @return integer position of this ChanceSpace.
     */
    public int getPosition()
    {
        return nPosition;
    }

    /**
     * Method to get the image assigned to this ChanceSpace.
     * @return ImageView of this ChanceSpace.
     */
    @Override
    public ImageView getImage()
    {
        return this.imgView;
    }

    /**
     * Changes this ChanceSpace's position on the board.
     * @param position   New position of the ChanceSpace.
     */
    public void setPosition(int position)
    {
        nPosition = position;
    }

    /**
     * Draws a Chance card and applies its effects to a player.
     * @param player Player that landed on <code>this</code> space.
     */
    @Override
    public String onLand(Player player) {
        Chance drawnCard = deck.draw();
        // TODO: Connect to controller
        drawnCard.useEffect(player);

        return player.getName() + " landed on the " + toString() + " space.";
    }

    @Override
    public String toString()
    {
        return "Chance";
    }
}
