package alsid.model.game;

import java.util.*;

import alsid.model.chance.*;

public class Deck {
	
	//...ATTRIBUTES

    private ArrayList <Chance> cards = new ArrayList <Chance>();
    private ArrayList <Chance> used = new ArrayList <Chance>();

	
	
	//...CONSTRUCTOR

    /**
     * Constructor for the starting deck in the game.
     */
    public Deck() {
    }
	
	public void initChance(Game game)
    {
        Random rand = new Random();

        Chance  /*GOJF1 = new GetOutOfJailChance(),
                GOJF2 = new GetOutOfJailChance(), */

                goToAsset1 = new MoveToSpaceChance(Chance.GO_TO_PROP + rand.nextInt(3), game.getBoard()),
                goToAsset2 = new MoveToSpaceChance(Chance.GO_TO_PROP + rand.nextInt(3), game.getBoard()),
                goToAsset3 = new MoveToSpaceChance(Chance.GO_TO_PROP + rand.nextInt(3), game.getBoard()),
                goToAsset4 = new MoveToSpaceChance(Chance.GO_TO_PROP + rand.nextInt(3), game.getBoard()),
                goToAsset5 = new MoveToSpaceChance(Chance.GO_TO_PROP + rand.nextInt(3), game.getBoard()),
                goToAsset6 = new MoveToSpaceChance(Chance.GO_TO_PROP + rand.nextInt(3), game.getBoard())//,

                /*getMoney1 = new ChangeMoneyChance(Chance.BANK_DIVIDEND + rand.nextInt(5), game.getBank()),
                getMoney2 = new ChangeMoneyChance(Chance.BANK_DIVIDEND + rand.nextInt(5), game.getBank()),
                getMoney3 = new ChangeMoneyChance(Chance.BANK_DIVIDEND + rand.nextInt(5), game.getBank()),
                getMoney4 = new ChangeMoneyChance(Chance.BANK_DIVIDEND + rand.nextInt(5), game.getBank()),
                getMoney5 = new ChangeMoneyChance(Chance.BANK_DIVIDEND + rand.nextInt(5), game.getBank()),
                getMoney6 = new ChangeMoneyChance(Chance.BANK_DIVIDEND + rand.nextInt(5), game.getBank()),

                moveToSpace1 = new MoveToSpaceChance(Chance.GO_TO_JAIL + rand.nextInt(2), game.getBoard()),
                moveToSpace2 = new MoveToSpaceChance(Chance.GO_TO_JAIL + rand.nextInt(2), game.getBoard()),
                moveToSpace3 = new MoveToSpaceChance(Chance.GO_TO_JAIL + rand.nextInt(2), game.getBoard()),
                moveToSpace4 = new MoveToSpaceChance(Chance.GO_TO_JAIL + rand.nextInt(2), game.getBoard()),

                rentModifier1 = new RentModifierChance(Chance.DOUBLE_RENT + rand.nextInt(5)),
                rentModifier2 = new RentModifierChance(Chance.DOUBLE_RENT + rand.nextInt(5)),
                rentModifier3 = new RentModifierChance(Chance.DOUBLE_RENT + rand.nextInt(5)),
                rentModifier4 = new RentModifierChance(Chance.DOUBLE_RENT + rand.nextInt(5)),
                rentModifier5 = new RentModifierChance(Chance.DOUBLE_RENT + rand.nextInt(5)),
                rentModifier6 = new RentModifierChance(Chance.DOUBLE_RENT + rand.nextInt(5)),
                rentModifier7 = new RentModifierChance(Chance.DOUBLE_RENT + rand.nextInt(5)),

                loseMoney1 = new ChangeMoneyChance(Chance.DONATE_MONEY + rand.nextInt(2), game.getBank()),
                loseMoney2 = new ChangeMoneyChance(Chance.DONATE_MONEY + rand.nextInt(2), game.getBank()),
                loseMoney3 = new ChangeMoneyChance(Chance.DONATE_MONEY + rand.nextInt(2), game.getBank())*/;


        cards.addAll(Arrays.asList (/*GOJF1, GOJF2, */
                goToAsset1, goToAsset2, goToAsset3, goToAsset4, goToAsset5, goToAsset6//,
                /*getMoney1, getMoney2, getMoney3, getMoney4, getMoney5, getMoney6,
                moveToSpace1, moveToSpace2, moveToSpace3, moveToSpace4,
                rentModifier1, rentModifier2, rentModifier3, rentModifier4, rentModifier5, rentModifier6, rentModifier7,
                loseMoney1, loseMoney2, loseMoney3*/));

        shuffle();
    }
	
	//...GETTERS
	
	/**
     * Gets the number of cards left in <code>this</code> deck.
     * @return Number of card that has not been drawn yet.
     */
    public int size() {
        return cards.size();
    }
	
	//...METHODS

    /**
     * Adds all of the cards that were drawn and not kept by players
     * (i.e. Get out of jail free cards) back into the deck.
     */
    public void resetUnowned() {
        used.forEach(card -> {
            if (!card.isOwned())
            {
                cards.add(card);
            }
        });
        used.removeAll(cards);
        shuffle();
    }

    /**
     * Shuffles <code>this</code> deck.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Removes the topmost item and returns it.
     * @return Top card of deck; <code>null</code> if deck is empty
     */
    public Chance draw() {
        if (!cards.isEmpty()) {
            Chance chnTemp = cards.remove(0);
            used.add(chnTemp);
            return chnTemp;
        }
        return null;
    }

    /**
     * Checks if the cards <code>ArrayList</code> is empty.
     * @return <code>true</code> if there are no more cards in the deck
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

}
