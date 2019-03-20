package Server.Game;

import Core.EnumHandler;
import Core.Card;
import java.util.*;
import java.util.Map.Entry;

public class TwoPlayersGame extends Game {
    private int numPlayerMaxCard;
    private EnumHandler enumHandler;

    public TwoPlayersGame(int numPlayers) {
        super(numPlayers);
    }

    //il metodo getBestNotBriscolaPlayer restiruisce il giocatore vincitore nel caso in cui nessuno dei giocatori
    //abbia tirato una briscola. Distingue diversi casi in base al seme delle carte giocate
    private Player getBestNotBriscolaPlayer() {
        if (!(getTurnCards().get(getPlayersTurn().get(0)).getSeed() == getBriscola()) &&
            !(getTurnCards().get(getPlayersTurn().get(1)).getSeed() == getBriscola()) &&
            getTurnCards().get(getPlayersTurn().get(0)).getSeed() == getTurnCards().get(getPlayersTurn().get(1)).getSeed())
            return getBestCardPlayer();
        else return getPlayersTurn().get(0);
    }

    //il metodo getBestCardPlayer mi permette di ottenere il giocatore che ha giocato la carta con score più alto
    //a parità di score restituisce il gioctore che ha tirato la carta con num più alto
    private Player getBestCardPlayer() {
        int numPlayerMaxBriscola= getNumPlayerMaxScoreCard();

        if (numPlayerMaxBriscola == 1) {
            int  maxScore= getMaxScoreCard();
            return getMaxScorePlayer(maxScore);
        }

        if (numPlayerMaxBriscola == 2)    {
            int maxNum=getMaxNumCard();
            return getMaxNumPlayer(maxNum);
        }
        return null;
    }

    //il metodo getMaxScoreCard mi restituisce lo score più alto tra le carte presenti nella mappa passata
    private int getMaxScoreCard() {
        int maxScoreCard=0;
        for (Map.Entry<Player, Card> entry : getTurnCards().entrySet()) {
            if ((enumHandler.getScoreMap().get(entry.getValue().getScore()))- maxScoreCard > 0) {
                maxScoreCard = enumHandler.getScoreMap().get(entry.getValue().getScore());
            }
        }
        return maxScoreCard;
    }

    //il metodo getScorePlayerMaxCard restituisce il numero di giocatori nella mappa passata che hanno tirato
    // una carta dello score massimo passato
    private int getNumPlayerMaxScoreCard (){
        numPlayerMaxCard = 0;
        int maxScoreBriscola= getMaxScoreCard();
        getTurnCards().forEach((player,card) -> {
            if(enumHandler.getScoreMap().get(card.getScore())== maxScoreBriscola) {
                numPlayerMaxCard +=1;
            }
        });
        return numPlayerMaxCard;
    }

    //il metodo getMaxScorePlayer restituisce il giocatore nella mappa passata che ha score massimo passato
    private Player getMaxScorePlayer (int maxScore) {
        for (Entry<Player, Card> entry : getTurnCards().entrySet()) {
            if(enumHandler.getScoreMap().get(entry.getValue().getScore()) == maxScore) {
                return entry.getKey();
            }
        }
        return null;
    }

    //il metodo getMaxNumCard mi restituisce il num più alto tra le carte presenti nella mappa passata
    private int getMaxNumCard() {
     int  maxNumCard=0;
        for (Map.Entry<Player, Card> entry : getTurnCards().entrySet()) {
           if ((enumHandler.getNumberMap().get(entry.getValue().getNum()))- maxNumCard > 0) {
               maxNumCard = enumHandler.getNumberMap().get(entry.getValue().getNum());
           }
       }
       return maxNumCard;
   }

    //il metodo getMaxNumPlayer restituisce giocatore nella mappa passata che ha num massimo passato
    private Player getMaxNumPlayer (int maxNum) {
        for (Entry<Player, Card> entry : getTurnCards().entrySet()) {
            if(enumHandler.getNumberMap().get(entry.getValue().getNum()) == maxNum) {
                return entry.getKey();
            }
        }
        return null;
    }


    //il metodo getWinner mi permette di ottenere il giocatore vincitore del turno
    @Override
    protected Player getWinner() {
        switch(getNumBriscole()) {
            case 0:
                return getBestNotBriscolaPlayer();
            case 1:
                return getBriscolaPlayer();
            case 2:
                return getBestCardPlayer();
            default: System.out.println("ERROR");
                return null;
        }
    }

    //il metodo reorderPlayersTurn restituisce una lista ordinata secondo
    // il nuovo ordine stabilito dal vincitore del turno
    @Override
    protected List<Player> reorderPlayersTurn() {
        if (getWinner() == getPlayersTurn().get(1))
            Collections.reverse(getPlayersTurn());

        return getPlayersTurn();
    }

    //il metodo getGameWinner restituiusce l'oggetto player vincitore, ovvero con più punti a fine partita
    //restituisce invece null se la partita finisce in pareggio
    @Override
    protected Player getGameWinner(){
        if (getPlayersTurn().get(0).getPoints()> getPlayersTurn().get(1).getPoints()) return getPlayersTurn().get(0) ;
        if (getPlayersTurn().get(0).getPoints()< getPlayersTurn().get(1).getPoints()) return getPlayersTurn().get(1);
        else return null;
    }

}
