package Server.Game;

import Client.Client;
import Core.Card;
import Core.Deck;
import Core.EnumHandler;
import Core.Seed;
import javafx.application.Platform;

import java.rmi.RemoteException;
import java.util.*;

public abstract class  Game {
    private int numPlayers;
    private Boolean isFull=false;
    private Map<Integer, Player> players;
    private Seed briscola;
    private Deck deck;
    private Map<Player, Card> turnCards;
    private List<Player> playersTurn;
    private int pointsInTurn;
    private EnumHandler enumHandler;
    private int numBriscole;
    private int numPlayerMaxCard;
    int num=0, numOfFinalTurnsPlayed=0;
    private boolean finalTurns=false;

    //getter methods
    public int getNumBriscole() {
        return numBriscole;
    }

    public void setNumBriscole() {
        numBriscole=setNumberOfBriscoleInThisTurn();
        System.out.println("ho settato il numero di briscole che è: "+getNumBriscole());
    }

    public Map<Player, Card> getTurnCards() {
        return turnCards;
    }

    public List<Player> getPlayersTurn() {
        return playersTurn;
    }

    public void setFull(Boolean full) {
        isFull = full;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public Boolean getFull() {
        return isFull;
    }

    public Map<Integer, Player> getPlayers() {
        return players;
    }

    public Seed getBriscola() {
        return briscola;
    }

    public Deck getDeck() {
        return deck;
    }

    //Game constructor
    public Game(int numPlayers){
        this.numPlayers = numPlayers;
        this.players = new HashMap<>();
        enumHandler = new EnumHandler();
        createDeck();
    }

    private void createDeck() {
        deck = new Deck(isNormal());
        briscola = deck.getBriscolaCard().getSeed();
    }

    private Boolean isNormal() {
        if(numPlayers == 3)
            return false;
        return true;
    }

    public void addPlayer(Player p) {
        players.put(players.size()+1,p);
        if(players.size() == numPlayers) {
            setFull(true);
            startGame();
        }
    }

    protected void startGame() {
        turnCards = new HashMap<>();
        playersTurn = new ArrayList<>();
        getPlayers().forEach((k,v) -> playersTurn.add(v));
        sendDeckToPlayer();
        try {
            playTurn(playersTurn.get(0));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void sendDeckToPlayer() {
        players.forEach((k,v) -> {
            try {
                v.sendHand(generateHand());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    private List<Card> generateHand() {
        List<Card> hand= new ArrayList<>();
        for(int i=0; i<3; i++) {
            hand.add(getDeck().drawCard());
        }
        return hand;
    }

    public Player getBriscolaPlayer(){
        for (Map.Entry<Player, Card> entry : getTurnCards().entrySet()) {
            if(entry.getValue().getSeed()==briscola){
                return entry.getKey();
            }
        }
        System.out.println("non dovrei essere qui");
        return null;
    }

    public int getTurnPoints() {
        pointsInTurn = 0;
        for (Map.Entry<Player, Card> entry : getTurnCards().entrySet()) {
            pointsInTurn += enumHandler.getScoreMap().get(entry.getValue().getScore());
        }
        return pointsInTurn;
    }

    public int setNumberOfBriscoleInThisTurn() {
        getTurnCards().forEach((player,card) -> {
            System.out.println("sono in setnumbriscole, stampo la carta: "+card.getId()+card.getSeed()+card.getNum());
            if(card.getSeed()==getBriscola())
                num++;
        });
        System.out.println("sono in setnumberofbriscoleinthisturn. le briscole sono: "+num);
        return num;
    }

    //the method getMaxScoreCard returns the highest score between all the cards in a map
    //this map is passed as a parameter to the method
    public int getMaxScoreCard(Map<Player, Card> map) {
        int maxScoreCard = 0;
        for (Map.Entry<Player, Card> entry : map.entrySet()) {
            if ((enumHandler.getScoreMap().get(entry.getValue().getScore()))- maxScoreCard > 0) {
                maxScoreCard = enumHandler.getScoreMap().get(entry.getValue().getScore());
            }
        }
        return maxScoreCard;
    }

    //the method getScorePlayerMaxCard returns the number of players in the passed map who have played a card
    //with a certain score (that is passed to the method)
    public int getNumPlayerMaxScoreCard (int maxScore, Map<Player, Card> map){
        numPlayerMaxCard = 0;
        map.forEach((player,card) -> {
            if(enumHandler.getScoreMap().get(card.getScore())== maxScore) {
                numPlayerMaxCard +=1;
            }
        });
        return numPlayerMaxCard;
    }

    //the method getMaxScorePlayer returns the Player who has played the card with highest score
    public Player getMaxScorePlayer (int maxScore, Map<Player, Card> map) {
        for (Map.Entry<Player, Card> entry : map.entrySet()) {
            if(enumHandler.getScoreMap().get(entry.getValue().getScore()) == maxScore) {
                return entry.getKey();
            }
        }
        return null;
    }

    //the method getMaxNumCard returns the highest number between all the cards in a map
    //this map is passed as a parameter to the method
    public int getMaxNumCard(Map<Player, Card> map) {
        int  maxNumCard=0;
        for (Map.Entry<Player, Card> entry : map.entrySet()) {
            if ((enumHandler.getNumberMap().get(entry.getValue().getNum()))- maxNumCard > 0) {
                maxNumCard = enumHandler.getNumberMap().get(entry.getValue().getNum());
            }
        }
        return maxNumCard;
    }

    //the method getMaxNumPlayer returns the Player who has played the card with highest number
    public Player getMaxNumPlayer (int maxNum, Map<Player, Card> map) {
        for (Map.Entry<Player, Card> entry : map.entrySet()) {
            if(enumHandler.getNumberMap().get(entry.getValue().getNum()) == maxNum) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void endTurn() throws RemoteException {
        setNumBriscole();
        if (getNumPlayers()==4) {
            if (getWinner()==getPlayersTurn().get(0) || getWinner()==getPlayersTurn().get(2)){
                getPlayersTurn().get(0).addPoints(getTurnPoints());
                getPlayersTurn().get(2).addPoints(getTurnPoints());
                }
            else {
                getPlayersTurn().get(1).addPoints(getTurnPoints());
                getPlayersTurn().get(3).addPoints(getTurnPoints());
            }
        }
        else getWinner().addPoints(getTurnPoints());
        updateTurnWinner(getWinner().getUsername());

        if (!getDeck().isEmpty()) {
            playersTurn = reorderPlayersTurn();
            for (int i=0; i<getPlayersTurn().size(); i++)
                getPlayersTurn().get(i).getClientPlayer().drawCard();
            try {
                getTurnCards().clear();
                playTurn(getPlayersTurn().get(0));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            numBriscole=0;
            num=0;
        }
        else deckEmpty();
    }

    private void deckEmpty(){
        if(numOfFinalTurnsPlayed==3) finishGame();
        else {
            numOfFinalTurnsPlayed++;
            if(finalTurns==false){
                finalTurns=true;
                playersTurn = reorderPlayersTurn();
                getTurnCards().clear();
                try {
                    notifyPlayersEmptyDeck();
                    playTurn(getPlayersTurn().get(0));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            else {
                playersTurn = reorderPlayersTurn();
                getTurnCards().clear();
                try {
                    playTurn(getPlayersTurn().get(0));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    protected  void notifyPlayersEmptyDeck(){
        for(Player p : playersTurn){
            System.out.println("Sono in game-dekEmpty, il client è: "+p.getUsername());
            try {
                p.notifyEmptyDeck();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    public void endGame(String winner) {
        for (int i=0; i<getPlayersTurn().size(); i++) {
            try {
                getPlayersTurn().get(i).getClientPlayer().notifyEndGame(winner);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void finishGame(){
        try{
            if (getNumPlayers() == 4)
               endGame(getGameWinner().getTeam());
           else
               endGame(getGameWinner().getUsername());
        }
        catch (NullPointerException e){
            endGame("TIE");
        }
    }

    public void addCardToTurn(Card turnCard, Player p){
        getTurnCards().put(p, turnCard);
        try {
            updateWithPlayedCards(turnCard, p);
            playTurn(nextPlayer(p));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void updateWithPlayedCards(Card turnCard, Player p){
        playersTurn.forEach(player -> {
            if(!(player==p)) {
                try {
                    player.updateWithPlayedCard(turnCard.getId());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void updateTurnWinner(String turnWinnerPlayer){
            playersTurn.forEach(player -> {
                try {
                    player.updateTurnWinner(turnWinnerPlayer);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
    }

    private Player nextPlayer(Player p) {
        System.out.println("sono in game dentro next player, le carte in turno sono: "+getTurnCards().size());
        if(getTurnCards().size() == getNumPlayers())
            return null;
        else return getPlayersTurn().get(getTurnCards().size());
    }

    public void playTurn(Player p) throws RemoteException {
        if (p==null) endTurn();
        else p.yourTurn();
    }


    //the method getBestNotBriscolaPlayer returns an object Player that is the winner of the turn if no one has played
    //a card with briscola seed. it analizes different cases based on the seeds of the cards
    protected abstract Player getBestNotBriscolaPlayer();

    //the method getBestBriscolaPlayer returns turn winner Player between all players
    // that have played a card with briscola seed
    protected abstract Player getBestBriscolaPlayer();

    //the method getBestCardPlayer returns turn-winner Player, the one who has played the card with highest score
    //or the highest number if there are more cards with the same score
    protected abstract Player getBestCardPlayer(Map<Player, Card> map);

    //the method getWinner returns the player who has won the turn
    protected abstract Player getWinner();

    //the method reorderPlayersTurn returns an ordered list with the new order
    //this new order starts from the winner of the turn
    protected abstract List<Player> reorderPlayersTurn();

    //the method getGameWinner returns an object Player who is the winner of the game.
    //it returns tha player who has the highest number of points at the end of the game
    //it returns null if the game ends in a tie
    protected abstract Player getGameWinner();


}
