package com.mcbain.wyatt.blackjack;

import android.media.Image;

/**
 * Created by wyattmcbain on 2/28/15.
 */
public class Card {
    private int value;
    private String cardPath;

    /**
     * Default constructor
     */
    public Card() {}

    /**
     * Constructors with parameters
     * @param value The card value
     * @param cardPath The image path
     */
    public Card(int value, String cardPath) {
        this.value = value;
        this.cardPath = cardPath;
    }

    /**
     * Gets the card value
     * @return the card value
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the card value
     * @param value the value
     */
    public void setValue(int value) {
        this.value = value;
    }


}
