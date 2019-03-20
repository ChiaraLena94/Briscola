package Server.Game;

import Core.Card;
import Core.Deck;
import Core.EnumHandler;
import Core.Seed;

import java.rmi.RemoteException;
import java.util.*;

public abstract class  Game {
    private int numPlayers;
    private Boolean isFull;
    private Map<Integer, Player> players;
    private Seed briscola;
    private Deck deck;
    private Map<Player, Card> turnCards;
    private List<Player> playersTurn;
    private int pointsInTurn;
    private EnumHandler enumHandler;
    private int numBriscole;

    public int getNumBriscole() {
        return numBriscole;
    }

    public void setNumBriscole(int numBriscole) {
        this.numBriscole = numBriscole;
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

    public Game(int numPlayers){
        this.numPlayers = numPlayers;
        this.players = new HashMap<>();
        createDeck();
    }

    private void createDeck() {
        deck = new Deck(isNormal());
        briscola = getBriscola();
        deck.moveBriscolaLast();
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
            if(entry.getValue().getSeed() == getBriscola())
                return entry.getKey();
        }
        return null;
    }

    public int getTurnPoints() {
        pointsInTurn = 0;
        getTurnCards().forEach((player,card) -> {
            pointsInTurn += enumHandler.getScoreMap().get(card.getScore());
        });
        return pointsInTurn;
    }

    public void setNumberOfBriscoleInThisTurn() {
        getTurnCards().forEach((player,card) -> {
            if(card.getSeed() == getBriscola())
                numBriscole++;
        });
    }

    public void endTurn() throws RemoteException {
        setNumBriscole(0);
        setNumberOfBriscoleInThisTurn();
        getWinner().addPoints(getTurnPoints());
        if (!getDeck().isEmpty()) {
            playersTurn = reorderPlayersTurn();
            for (int i=0; i<getPlayersTurn().size(); i++)
                getPlayersTurn().get(i).getClientPlayer().drawCard();
            try {
                playTurn(getPlayersTurn().get(0));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        else finishGame();
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
            endGame(getGameWinner().getUsername());
        }
        catch (NullPointerException e){
            endGame("TIE");
        }
    }

    public void addCardToTurn(Card turnCard, Player p){
        getTurnCards().put(p, turnCard);
        try {
            playTurn(nextPlayer(p));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public Player nextPlayer(Player p) {
        if(getTurnCards().size() == getNumPlayers())
            return null;
        else return getPlayersTurn().get(getTurnCards().size());
    }

    public void playTurn(Player p) throws RemoteException {
        if (p==null) endTurn();
        else p.yourTurn();
    }


    protected abstract Player getGameWinner();
    protected abstract List<Player> reorderPlayersTurn();
    protected abstract Player getWinner();

}
