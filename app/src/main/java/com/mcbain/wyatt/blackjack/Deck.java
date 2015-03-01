package com.mcbain.wyatt.blackjack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Context;

/**
 * Created by wyattmcbain on 2/28/15.
 */
public class Deck {
    private Card placeholder;
    private Context context;
    private int cardIndex;
    private List deckOrder;
    private Map<String, Integer> valueAssociations;
    private Map<Integer, Card> deck;

    /**
     * Default constructor
     */
    public Deck(Context context) {
        this.context = context;
        this.createValueAssociations();
        this.createDeck();
    }

    /**
     * Shuffles the deck of cards
     */
    public void shuffle() {
        deckOrder = new ArrayList(deck.keySet());
        Collections.shuffle(deckOrder);
        cardIndex = 0;
    }

    /**
     * Gets the next card in the deck
     * @return
     */
    public Card getNextCard() {
        if (cardIndex == 51) {
            this.shuffle();
            cardIndex = 0;
        }
        Card temp = deck.get(deckOrder.get(cardIndex));
        cardIndex++;
        return temp;
    }

    /**
     * Gets the placeholder, or back facing card
     * @return the placeholder card
     */
    public Card getPlaceholder() {
        return placeholder;
    }

    /**
     * Initializes the deck and placeholder card from files
     */
    private void createDeck() {
        deck = new HashMap<Integer, Card>();
        Card card;

        try {
            String[] files = context.getAssets().list("cards");

            for (int i = 0; i < files.length; i++) { // iterate through files
                int delim = files[i].indexOf("_");
                if (delim == -1) placeholder = new Card(0, files[i]); // back card
                else {
                    // get value association for card, put it in the deck
                    card = new Card(valueAssociations.get(files[i].substring(0, delim)), files[i]);
                    deck.put(i, card);
                }
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    /**
     * Creates the value associations for the cards
     *  To be matched when creating the deck
     */
    private void createValueAssociations() {
        valueAssociations = new HashMap<String, Integer>();
        int[] values = {11,10,9,8,7,6,5,4,3,2};
        String[] tokens = {"A","K","Q","J","10","9","8","7","6","5","4","3","2"};

        for (int i = 0; i < values.length; i++) { // iterate through arrays and add to map
            valueAssociations.put(tokens[i], values[i]);
        }
    }
}
