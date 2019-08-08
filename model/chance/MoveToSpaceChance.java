package alsid.model.chance;

import alsid.model.asset.Asset;
import alsid.model.game.Board;
import alsid.model.game.Player;
import alsid.model.space.Space;

import java.util.*;

/**
 * Class template for cards that move the player to a certain asset (property, utility, railroad).
 */
public class MoveToSpaceChance extends Chance implements PlayerApplicable{

    private Space space;
    private int spaceLocation;
    private boolean canCollectStart;

    public MoveToSpaceChance(int effect, Board board)
    {
        super(effect);

        switch (effect)
        {
            case Chance.GO_TO_PROP:
            {
                canCollectStart = false;
                assignRandomSpace(Board.PROPERTY, board);
                setText("You have a class at " + ((Asset) space).getName() + ". If you pass HENRY GROUNDS, do not collect $200.");
            } break;

            case Chance.GO_TO_UTIL: // TODO: Add function to get nearest utility
            {
                canCollectStart = true;
                assignRandomSpace(Board.UTILITY, board);
                setText("You need to request something from " + ((Asset) space).getName() + ". Collect $200 if you pass HENRY GROUNDS.");
            } break;

            case Chance.GO_TO_RAIL:
            {
                canCollectStart = true;
                assignRandomSpace(Board.RAILROAD, board);
                setText("Pass by " + ((Asset) space).getName() + " on your way to class! Collect $200 if you pass HENRY GROUNDS.");
            } break;

            case Chance.TRIP_TO_PROP:
            {
                canCollectStart = true;
                assignRandomSpace(Board.PROPERTY, board);
                setText("There's an event at " + ((Asset) space).getName() + ". Collect $200 if you pass HENRY GROUNDS.");
            } break;

            case Chance.GO_TO_START:
            {
                canCollectStart = true;
                space = board.getSpaces().get(Board.START);
                spaceLocation = Board.START;
                setText("Meet up with someone at HENRY GROUNDS! Collect $200.");
            } break;

            case Chance.GO_TO_JAIL:
            {
                canCollectStart = false;
                space = board.getSpaces().get(Board.JAIL);
                spaceLocation = Board.JAIL;
                setText("GO DIRECTLY TO SDFO\nDO NOT PASS HENRY GROUNDS\nDO NOT COLLECT $200\n");
            } break;
        }
    }

    private void assignRandomSpace(int spaceType, Board board)
    {
        Random rand = new Random();

        ArrayList <Space> filteredSpaces = board.filterSpaceType(spaceType);
        space = filteredSpaces.get(rand.nextInt(filteredSpaces.size()));
        spaceLocation = board.getSpaces().lastIndexOf(space);
    }

    /*

     */

    @Override
    public String useEffect(Player player) {
        if (!isUsed) {
            if (player.moveTo(spaceLocation) && canCollectStart) {
                player.add(200);
            }
            isUsed = true;
        }

        return player.getName() + " moved to " + space.toString() + ".";
    }
}
