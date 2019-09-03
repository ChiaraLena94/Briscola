package Server.Game;

import Core.Card;
import java.util.*;

public class TwoPlayersGame extends Game {

    public TwoPlayersGame(int numPlayers) {
        super(numPlayers);
    }

    //the method getBestNotBriscolaPlayer returns an object Player that is the winner of the turn if no one has played
    //a card with briscola seed. it analizes different cases based on the seeds of the cards
    @Override
    protected Player getBestNotBriscolaPlayer() {
        if (!(getTurnCards().get(getPlayersTurn().get(0)).getSeed() == getBriscola()) &&
                !(getTurnCards().get(getPlayersTurn().get(1)).getSeed() == getBriscola()) &&
                getTurnCards().get(getPlayersTurn().get(0)).getSeed() == getTurnCards().get(getPlayersTurn().get(1)).getSeed())
            return getBestCardPlayer(getTurnCards());
        else return getPlayersTurn().get(0);
    }

    //the method getBestBriscolaPlayer returns null because just in TwoPlayersGame we can use getBestCardPlayer
    @Override
    protected Player getBestBriscolaPlayer() {
        return null;
    }

    //the method getBestCardPlayer returns turn winner Player, the one who has played the card with highest score
    //or the highest number if there are more cards with the same score
    @Override
    protected Player getBestCardPlayer(Map<Player, Card> map) {
        int max=getMaxScoreCard(getTurnCards());
        int numPlayerMaxBriscola= getNumPlayerMaxScoreCard(max, getTurnCards());

        if (numPlayerMaxBriscola == 1) {
            int  maxScore= getMaxScoreCard(getTurnCards());
            return getMaxScorePlayer(maxScore, getTurnCards());
        }

        if (numPlayerMaxBriscola == 2)    {
            int maxNum=getMaxNumCard(getTurnCards());
            return getMaxNumPlayer(maxNum, getTurnCards());
        }
        return null;
    }

    //the method getWinner returns the player who has won the turn
    @Override
    protected Player getWinner() {
        switch(getNumBriscole()) {
            case 0:
                return getBestNotBriscolaPlayer();
            case 1:
                return getBriscolaPlayer();
            case 2:
                return getBestCardPlayer(getTurnCards());
            default: System.out.println("ERROR");
                return null;
        }
    }

    //the method reorderPlayersTurn returns an ordered list with the new order
    //this new order starts from the winner of the turn
    @Override
    protected List<Player> reorderPlayersTurn() {
        if (getWinner() == getPlayersTurn().get(1)){
            Collections.reverse(getPlayersTurn());
        }

        return getPlayersTurn();
    }

    //the method getGameWinner returns an object Player who is the winner of the game.
    //it returns tha player who has the highest number of points at the end of the game
    //it returns null if the game ends in a tie
    @Override
    protected Player getGameWinner(){
        if (getPlayersTurn().get(0).getPoints()> getPlayersTurn().get(1).getPoints()) return getPlayersTurn().get(0) ;
        if (getPlayersTurn().get(0).getPoints()< getPlayersTurn().get(1).getPoints()) return getPlayersTurn().get(1);
        else return null;
    }

}
