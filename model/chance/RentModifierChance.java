package alsid.model.chance;

import alsid.model.asset.Asset;
import alsid.model.game.Player;

public class RentModifierChance extends Chance {

    private boolean isTemp;
    private double modifier;

    public RentModifierChance(int effect)
    {
        super(effect);

        switch(effect)
        {
            case Chance.DOUBLE_RENT:
            {
                isTemp = true;
                modifier = 2;
                setText("DOUBLE RENT!\nApply this card to a property you own to double its rent for the next player who lands on it!");
            } break;
            case Chance.RENOVATION:
            {
                isTemp = false;
                modifier = 1.5;
                setText("RENOVATE A PROPERTY!\nApply this card to a property you own and pay its renovation cost to increase its rent by 50%!");
            } break;
            case Chance.DILAPIDATED:
            {
                isTemp = false;
                modifier = 0.9;
                setText("Dilapidated houses...\nThe rent of a property you own decreases by 10%.");
            } break;
            case Chance.UTIL_RAIL_INC:
            {
                isTemp = false;
                modifier = 1.1;
                setText("Lucky!\nThe rent of a utility you own increases by 10%!");
            } break;
            case Chance.UTIL_RAIL_DEC:
            {
                isTemp = false;
                modifier = 0.9;
                setText("Unlucky...\nThe rent of a utility you own decreases by 10%.");
            } break;
        }
    }

    @Override
    public String useEffect(Player player) {
        return "RentModifierChance only used on Assets";
    }

    public String useEffect(Asset asset)
    {
        if (isTemp)
            asset.modifyRentTemp(modifier);
        else
            asset.modifyRentPerm(modifier);

        return asset.getName() + "'s rent is multiplied by " + modifier + "x.";
    }
}
