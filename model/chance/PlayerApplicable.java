package alsid.model.chance;

import alsid.model.game.Player;

/**
 * Interface for objects that apply its effect to another player.
 */
public interface PlayerApplicable {

    /**
     * Abstract method that applies this chance card's effect to the <code>player</code>.
     * @return <code>true</code> if effect is applied successfully.
     */
    public abstract String useEffect(Player player);

}
