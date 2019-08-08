package alsid.model.chance;

import alsid.model.game.Player;

/**
 * Class template for the get out of jail free card.
 */
public class GetOutOfJailChance extends Chance implements PlayerApplicable{

    public GetOutOfJailChance()
    {
        super(Chance.GET_OUT_OF_JAIL,
         "Get out of jail for free!\n\nUse this card next time you're in the SDFO and skip paying $50!");
    }

    /**
     * Gives <code>this</code> card to the <code>player</code>.
     * @param player Player to give the card to.
     * @return <code>true</code> when operation is completed.
     */
    @Override
    public String useEffect(Player player) {
        this.giveTo(player);
        return player.toString() + " got a " + toString() + "!";
    }

    @Override
    public String toString()
    {
        return "Get Out of Jail for Free Card";
    }
}
