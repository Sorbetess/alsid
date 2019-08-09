package alsid.model.chance;

import alsid.model.game.Player;

public interface PlayerApplicable {

    /**
     * Abstract method that applies this chance card's effect to the <code>player</code>.
     * @return <code>true</code> if effect is applied successfully.
     */
    String useEffect(Player player);

}
