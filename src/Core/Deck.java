package Core;

import java.util.*;

public class Deck {
    private List<Card> deck;
    private Card briscolaCard;

    public Deck(Boolean normalDeck){
        initializeDeck();
        if(!normalDeck)
            deck.remove(2);
        shuffleDeck();
    }

    public Card getBriscola(){
        return deck.get(0);
    }

    private void shuffleDeck(){
        Collections.shuffle(deck);
    }

    public void moveBriscolaLast() {
        deck.add(deck.get(0));
        deck.remove(0);
    }

    public Card drawCard(){
        Card c = deck.get(0);
        deck.remove(0);
        return c;
    }

    private void initializeDeck() {
        deck = new ArrayList<>();
        deck.add(new Card(1, Seed.SWORD, Number.ACE, Score.ELEVEN));
        deck.add(new Card(2, Seed.SWORD, Number.TWO, Score.ZERO));
        deck.add(new Card(3, Seed.SWORD, Number.THREE, Score.TEN));
        deck.add(new Card(4, Seed.SWORD, Number.FOUR, Score.ZERO));
        deck.add(new Card(5, Seed.SWORD, Number.FIVE, Score.ZERO));
        deck.add(new Card(6, Seed.SWORD, Number.SIX, Score.ZERO));
        deck.add(new Card(7, Seed.SWORD, Number.SEVEN, Score.ZERO));
        deck.add(new Card(8, Seed.SWORD, Number.SOLDIER, Score.TWO));
        deck.add(new Card(9, Seed.SWORD, Number.KNIGHT, Score.THREE));
        deck.add(new Card(10, Seed.SWORD, Number.KING, Score.FOUR));
        deck.add(new Card(11, Seed.CUP, Number.ACE, Score.ELEVEN));
        deck.add(new Card(12, Seed.CUP, Number.TWO, Score.ZERO));
        deck.add(new Card(13, Seed.CUP, Number.THREE, Score.TEN));
        deck.add(new Card(14, Seed.CUP, Number.FOUR, Score.ZERO));
        deck.add(new Card(15, Seed.CUP, Number.FIVE, Score.ZERO));
        deck.add(new Card(16, Seed.CUP, Number.SIX, Score.ZERO));
        deck.add(new Card(17, Seed.CUP, Number.SEVEN, Score.ZERO));
        deck.add(new Card(18, Seed.CUP, Number.SOLDIER, Score.TWO));
        deck.add(new Card(19, Seed.CUP, Number.KNIGHT, Score.THREE));
        deck.add(new Card(20, Seed.CUP, Number.KING, Score.FOUR));
        deck.add(new Card(21, Seed.GOLD, Number.ACE, Score.ELEVEN));
        deck.add(new Card(22, Seed.GOLD, Number.TWO, Score.ZERO));
        deck.add(new Card(23, Seed.GOLD, Number.THREE, Score.TEN));
        deck.add(new Card(24, Seed.GOLD, Number.FOUR, Score.ZERO));
        deck.add(new Card(25, Seed.GOLD, Number.FIVE, Score.ZERO));
        deck.add(new Card(26, Seed.GOLD, Number.SIX, Score.ZERO));
        deck.add(new Card(27, Seed.GOLD, Number.SEVEN, Score.ZERO));
        deck.add(new Card(28, Seed.GOLD, Number.SOLDIER, Score.TWO));
        deck.add(new Card(29, Seed.GOLD, Number.KNIGHT, Score.THREE));
        deck.add(new Card(30, Seed.GOLD, Number.KING, Score.FOUR));
        deck.add(new Card(31, Seed.STICK, Number.ACE, Score.ELEVEN));
        deck.add(new Card(32, Seed.STICK, Number.TWO, Score.ZERO));
        deck.add(new Card(33, Seed.STICK, Number.THREE, Score.TEN));
        deck.add(new Card(34, Seed.STICK, Number.FOUR, Score.ZERO));
        deck.add(new Card(35, Seed.STICK, Number.FIVE, Score.ZERO));
        deck.add(new Card(36, Seed.STICK, Number.SIX, Score.ZERO));
        deck.add(new Card(37, Seed.STICK, Number.SEVEN, Score.ZERO));
        deck.add(new Card(38, Seed.STICK, Number.SOLDIER, Score.TWO));
        deck.add(new Card(39, Seed.STICK, Number.KNIGHT, Score.THREE));
        deck.add(new Card(40, Seed.STICK, Number.KING, Score.FOUR));
    }

    public boolean isEmpty(){
        if (deck.size()==0) return true;
        return false;
    }

}
