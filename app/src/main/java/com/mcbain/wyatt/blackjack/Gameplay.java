package com.mcbain.wyatt.blackjack;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.mcbain.wyatt.blackjack.R;

/**
 * Created by wyattmcbain on 3/1/15.
 */
public class Gameplay {
    private Deck deck;
    private List playersCards, dealersCards;
    private View playersCardsView, dealersCardsView;
    private int playersCardsCnt, dealersCardsCnt;
    private final int[] imageViews = {R.id.cardImageView0, R.id.cardImageView1,
        R.id.cardImageView2, R.id.cardImageView3, R.id.cardImageView4, R.id.cardImageView5};

    /**
     * Default constructor
     * @param context The application's context
     */
    public Gameplay(Context context, View playersCardsView, View dealersCardsView) {
        this.deck = new Deck(context);
        this.playersCardsView = playersCardsView;
        this.dealersCardsView = dealersCardsView;
    }

    /**
     * Hit the player
     */
    public void hit() {
        Card temp = deck.getNextCard();
        playersCards.add(temp);
        addCardToPlayerLayout(temp);
        // check for bust
        if(isBust(getCardTotals(playersCards))) {
            // display bust message
            clearHands();
        }
    }

    /**
     * Starts a new game
     */
    public void startGame() {
        playersCards = new ArrayList<Card>();
        dealersCards = new ArrayList<Card>();
        deck.shuffle();
        dealInitialCards();
    }

    /**
     * Player decides to stay
     */
    public void stay() {
        dealDealerCards();
        int[] dealerTotals = getCardTotals(dealersCards);
        int[] playerTotals = getCardTotals(playersCards);
        determineWinner(dealerTotals, playerTotals);
    }

    /**
     * Adds the card to the dealers card layout
     * @param card the card
     */
    private void addCardToDealerLayout(Card card) {
        ImageView view = (ImageView)dealersCardsView.findViewById(imageViews[dealersCardsCnt]);
        view.setImageBitmap(BitmapFactory.decodeFile(card.getCardPath()));
        dealersCardsCnt++;
    }

    /**
     * Adds the card to the players card layout
     * @param card the card
     */
    private void addCardToPlayerLayout(Card card) {
        ImageView view = (ImageView)playersCardsView.findViewById(imageViews[playersCardsCnt]);
        view.setImageBitmap(BitmapFactory.decodeFile(card.getCardPath()));
        playersCardsCnt++;
    }

    /**
     * Clears the current hand and deals the next round
     */
    private void clearHands() {
        resetCounters();
        Timer timer = new Timer();
        timer.schedule(new ClearTask(), 5000);
    }

    /**
     * Deals the dealer's cards when user stays
     */
    private void dealDealerCards() {
        Card temp;
        // Dealer must hit under 17 and on a soft 17
        while(true) {
            int[] totals = getCardTotals(dealersCards);
            if ((totals[0] < 17 && totals[1] < 17) || totals[1] == 17) {
                temp = deck.getNextCard();
                dealersCards.add(temp);
                addCardToDealerLayout(temp);
                // check for dealer bust
                if (isBust(getCardTotals(dealersCards))) {
                    // display bust message
                    clearHands();
                    break;
                }
                continue;
            }
            break;
        }
    }

    /**
     * Deals the initial cards
     */
    private void dealInitialCards() {
        Card temp;
        resetCounters();
        temp = deck.getNextCard();
        playersCards.add(temp);
        addCardToPlayerLayout(temp);
        temp = deck.getNextCard();
        dealersCards.add(temp);
        addCardToDealerLayout(temp);
        temp = deck.getNextCard();
        playersCards.add(temp);
        addCardToPlayerLayout(temp);
        temp = deck.getNextCard();
        dealersCards.add(temp);
        addCardToDealerLayout(deck.getPlaceholder());
        // check for player blackjack
        int[] totals = getCardTotals(playersCards);
        if (totals[0] == 21 || totals[1] == 21) {
            // display blackjack message
            clearHands();
        }
    }

    /**
     * Determines the winner of the hand
     * @param dealerTotals the dealer's card totals
     * @param playerTotals the player's card totals
     */
    private void determineWinner(int[] dealerTotals, int[] playerTotals) {
        if (getBest(dealerTotals) > getBest(playerTotals)) {
            // display dealer winner
            clearHands();
            return;
        }
        // display player winner
        clearHands();
    }

    /**
     * Gets the best hand from a set of totals
     * @param totals the totals
     * @return the best total
     */
    private int getBest(int[] totals) {
        if (totals[1] == 0) return totals[0];
        if (totals[1] > totals[0]) return totals[1];
        return totals[0];
    }

    /**
     * Gets the card totals for a set
     * @param cardList the card list to be totaled
     * @return an array of possible totals
     */
    private int[] getCardTotals(List cardList) {
        int total1 = 0, total2 = 0;
        for (Card card : (ArrayList<Card>)cardList) {
            int value = card.getValue();
            if (card.getValue() == 11) { // ace
                total1 += value;
                total2 += 1;
            }
            total1 += value;
            total2 += value;
        }
        if (total1 - total2 == 10) return new int[] {total1, total2};
        return new int[] {total1, 0};
    }

    /**
     * Determines if hand is a bust
     * @param totals the two totals
     * @return true/false
     */
    private boolean isBust(int[] totals) {
        if ((totals[0] > 21 && totals[1] == 0) || totals[1] > 21) {
            return true;
        }
        return false;
    }

    /**
     * Reset the counters to 0
     */
    private void resetCounters() {
        playersCardsCnt = 0;
        dealersCardsCnt = 0;
    }

    /**
     * Task to be run when cards need to be cleared
     */
    private class ClearTask extends TimerTask {
        public void run() {
            playersCards.clear();
            dealersCards.clear();
            // remove cards from GUI and what not
            dealInitialCards();
        }
    }
}
