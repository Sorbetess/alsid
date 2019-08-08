package alsid.model.game;

import java.util.*;
import alsid.model.chance.Chance;

public class Deck {
	
	//...ATTRIBUTES

    private ArrayList <Chance> cards = new ArrayList <Chance>();
    private ArrayList <Chance> used = new ArrayList <Chance>();

	
	
	//...CONSTRUCTOR

    /**
     * Constructor for the starting deck in the game.
     */
    public Deck() {
        Random rand = new Random();
/*
        // Get out of jail
        for (int i = 0; i < 2; i++)
            cards.add(new Chance(1));

        // Proceed to nearest property, utility or railroad
        for (int i = 0; i < 6; i++)
            cards.add(new Chance(rand.nextInt(3) + 2)); // Possible values: 2, 3, 4

        // Get money
        for (int i = 0; i < 6; i++)
            cards.add(new Chance(rand.nextInt(5) + 5)); // Possible values: 5, 6, 7, 8, 9
        
        // Move to jail/property
        for (int i = 0; i < 4; i++)
            cards.add(new Chance(rand.nextInt(2) + 10)); // Possible values: 10, 11  

        // Modifier cards
        for (int i = 0; i < 7; i++)
        cards.add(new Chance(rand.nextInt(5) + 12)); // Possible values: 12, 13, 14, 15, 16

        // Lose money
        for (int i = 0; i < 3; i++)
            cards.add(new Chance(rand.nextInt(2) + 17)); // Possible values: 17, 18
        
        shuffle();
  */
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
        cards.addAll(used);
        shuffle();
        used.clear();
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
            if (chnTemp.getEffect() != 1)
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
