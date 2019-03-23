package Server.Game;

import Core.Card;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FourPlayersGame extends Game {
    private Map<Player, Card> playersTemp;

    public FourPlayersGame(int numPlayers) {
        super(numPlayers);
    }

    //the method getBestNotBriscolaPlayer returns an object Player that is the winner of the turn if no one has played
    //a card with briscola seed. it analizes different cases based on the seeds of the cards
    @Override
    protected Player getBestNotBriscolaPlayer() {
        if (getTurnCards().get(getPlayersTurn().get(0)).getSeed() == getTurnCards().get(getPlayersTurn().get(1)).getSeed() &&
                getTurnCards().get(getPlayersTurn().get(1)).getSeed() == getTurnCards().get(getPlayersTurn().get(2)).getSeed() &&
                getTurnCards().get(getPlayersTurn().get(2)).getSeed() == getTurnCards().get(getPlayersTurn().get(3)).getSeed() )
            return getBestCardPlayer(getTurnCards());
        else {
            playersTemp= new HashMap<>();
            for (Map.Entry<Player, Card> entry : getTurnCards().entrySet()) {
                if(entry.getValue().getSeed() == getTurnCards().get(getPlayersTurn().get(0)).getSeed()) {
                    playersTemp.put(entry.getKey(), entry.getValue());
                }
                return getBestCardPlayer(playersTemp);
            }
        }
        return null;
    }

    //the method getBestBriscolaPlayer returns turn winner Player between all players
    // that have played a card with briscola seed
    @Override
    protected Player getBestBriscolaPlayer(){
        playersTemp= new HashMap<>();
        for (Map.Entry<Player, Card> entry : getTurnCards().entrySet()) {
            if(entry.getValue().getSeed() == getBriscola()) {
                playersTemp.put(entry.getKey(), entry.getValue());
            }
        }
        return getBestCardPlayer(playersTemp);
    }

    //the method getBestCardPlayer returns turn winner Player, the one who has played the card with highest score
    //or the highest number if there are more cards with the same score
    @Override
    protected Player getBestCardPlayer(Map<Player, Card> map) {
        int maxScore, numPlayerMaxScore, maxNum;
        maxScore=getMaxScoreCard(map);
        numPlayerMaxScore=getNumPlayerMaxScoreCard(maxScore, map);
        if (numPlayerMaxScore==1) return getMaxScorePlayer(maxScore, map);
        else if (numPlayerMaxScore==2 || numPlayerMaxScore==3 || numPlayerMaxScore==4) {
            maxNum=getMaxNumCard(map);
            return getMaxNumPlayer(maxNum , map);
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
            case 3:
                return getBestBriscolaPlayer();
            case 4:
                return getBestCardPlayer(getTurnCards());
            default:
                System.out.println("ERROR");
                return null;
        }
    }

    //the method reorderPlayersTurn returns an ordered list with the new order
    //this new order starts from the winner of the turn
    @Override
    protected List<Player> reorderPlayersTurn() {
        List<Player> newOrder= new ArrayList<>();
        newOrder.add(getWinner());
        if (getWinner() == getPlayersTurn().get(0)) {
            newOrder.add(getPlayersTurn().get(1));
            newOrder.add(getPlayersTurn().get(2));
            newOrder.add(getPlayersTurn().get(3));
        }

        else if (getWinner() == getPlayersTurn().get(1)) {
            newOrder.add(getPlayersTurn().get(2));
            newOrder.add(getPlayersTurn().get(3));
            newOrder.add(getPlayersTurn().get(0));
        }

        else if (getWinner() == getPlayersTurn().get(2)){
            newOrder.add(getPlayersTurn().get(3));
            newOrder.add(getPlayersTurn().get(0));
            newOrder.add(getPlayersTurn().get(1));
        }

        else {
            newOrder.add(getPlayersTurn().get(0));
            newOrder.add(getPlayersTurn().get(1));
            newOrder.add(getPlayersTurn().get(2));
        }
        return newOrder;
    }

    //the method getGameWinner returns an object Player who is the winner of the game.
    //it returns tha player who has the highest number of points at the end of the game
    //it returns null if the game ends in a tie
    @Override
    protected Player getGameWinner(){
        if (getPlayersTurn().get(0).getPoints()> getPlayersTurn().get(1).getPoints() &&
            getPlayersTurn().get(0).getPoints()> getPlayersTurn().get(2).getPoints() &&
            getPlayersTurn().get(0).getPoints()> getPlayersTurn().get(3).getPoints())
            return getPlayersTurn().get(0);
        else if (getPlayersTurn().get(1).getPoints()> getPlayersTurn().get(2).getPoints() &&
                getPlayersTurn().get(1).getPoints()> getPlayersTurn().get(0).getPoints() &&
                getPlayersTurn().get(1).getPoints()> getPlayersTurn().get(3).getPoints())
            return getPlayersTurn().get(1);
        else if (getPlayersTurn().get(2).getPoints()> getPlayersTurn().get(1).getPoints() &&
                getPlayersTurn().get(2).getPoints()> getPlayersTurn().get(0).getPoints() &&
                getPlayersTurn().get(2).getPoints()> getPlayersTurn().get(3).getPoints())
            return getPlayersTurn().get(2);
        else if (getPlayersTurn().get(3).getPoints()> getPlayersTurn().get(1).getPoints() &&
                getPlayersTurn().get(3).getPoints()> getPlayersTurn().get(0).getPoints() &&
                getPlayersTurn().get(3).getPoints()> getPlayersTurn().get(2).getPoints())
            return getPlayersTurn().get(3);
        return null;
    }

}
