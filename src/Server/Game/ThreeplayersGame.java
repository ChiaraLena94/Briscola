        package Server.Game;

        import Core.Card;
        import Core.EnumHandler;
        import java.util.*;

        public class ThreeplayersGame extends Game {
            private int numPlayerMaxCard;
            private EnumHandler enumHandler;
            private Map<Player, Card> playersTemp;

            public ThreeplayersGame(int numPlayers) {
                super(numPlayers);
            }

            //il metodo getBestNotBriscolaPlayer restiruisce il giocatore vincitore nel caso in cui nessuno dei giocatori
            //abbia tirato una briscola. Distingue diversi casi in base al seme delle carte giocate
            private Player getBestNotBriscolaPlayer() {
                if (getTurnCards().get(getPlayersTurn().get(0)).getSeed() == getTurnCards().get(getPlayersTurn().get(1)).getSeed() &&
                        getTurnCards().get(getPlayersTurn().get(1)).getSeed() == getTurnCards().get(getPlayersTurn().get(2)).getSeed())
                    return getBestCardPlayer(getTurnCards());
                else if (!(getTurnCards().get(getPlayersTurn().get(0)).getSeed() == getTurnCards().get(getPlayersTurn().get(1)).getSeed()) &&
                        !(getTurnCards().get(getPlayersTurn().get(0)).getSeed() == getTurnCards().get(getPlayersTurn().get(2)).getSeed()))
                    return getPlayersTurn().get(0);
                else if ((getTurnCards().get(getPlayersTurn().get(0)).getSeed() == getTurnCards().get(getPlayersTurn().get(1)).getSeed()) &&
                        !(getTurnCards().get(getPlayersTurn().get(0)).getSeed() == getTurnCards().get(getPlayersTurn().get(2)).getSeed())){
                    playersTemp= new HashMap<>();
                    playersTemp.put(getPlayersTurn().get(0), getTurnCards().get(getPlayersTurn().get(0)));
                    playersTemp.put(getPlayersTurn().get(1), getTurnCards().get(getPlayersTurn().get(1)));
                    return getBestCardPlayer(playersTemp);
                }

                else if ((getTurnCards().get(getPlayersTurn().get(0)).getSeed() == getTurnCards().get(getPlayersTurn().get(2)).getSeed()) &&
                        !(getTurnCards().get(getPlayersTurn().get(0)).getSeed() == getTurnCards().get(getPlayersTurn().get(1)).getSeed())){
                    playersTemp= new HashMap<>();
                    playersTemp.put(getPlayersTurn().get(0), getTurnCards().get(getPlayersTurn().get(0)));
                    playersTemp.put(getPlayersTurn().get(2), getTurnCards().get(getPlayersTurn().get(2)));
                    return getBestCardPlayer(playersTemp);
                }
                return null;
            }

            //il metodo getBestBriscolaPlayer mi permette di ottenere il giocatore con la carta vincente tra i due
            // che hanno giocato una briscola
            private Player getBestBriscolaPlayer(){
               playersTemp= new HashMap<>();
               for (Map.Entry<Player, Card> entry : getTurnCards().entrySet()) {
                   if(entry.getValue().getSeed() == getBriscola()) {
                       playersTemp.put(entry.getKey(), entry.getValue());
                   }
               }
               return getBestCardPlayer(playersTemp);
           }

            //il metodo getBestCardPlayer mi permette di ottenere il giocatore che ha giocato la carta con score più alto
            //a parità di score restituisce il gioctore che ha tirato la carta con num più alto
            private Player getBestCardPlayer(Map<Player, Card> map) {
                int maxScore, numPlayerMaxScore, maxNum;
                maxScore=getMaxScoreCard(map);
                numPlayerMaxScore=getNumPlayerMaxScoreCard(maxScore, map);
                if (numPlayerMaxScore==1) return getMaxScorePlayer(maxScore, map);
                else if (numPlayerMaxScore==2) {
                    maxNum=getMaxNumCard(map);
                    return getMaxNumPlayer(maxNum , map);
                }
             return null;
           }

            //il metodo getMaxScoreCard mi restituisce lo score più alto tra le carte presenti nella mappa passata
            private int getMaxScoreCard(Map<Player, Card> map) {
                int maxScoreCard = 0;
                for (Map.Entry<Player, Card> entry : map.entrySet()) {
                    if ((enumHandler.getScoreMap().get(entry.getValue().getScore()))- maxScoreCard > 0) {
                        maxScoreCard = enumHandler.getScoreMap().get(entry.getValue().getScore());
                    }
                }
                return maxScoreCard;
            }

            //il metodo getScorePlayerMaxCard restituisce il numero di giocatori nella mappa passata che hanno tirato
            // una carta dello score massimo passato
            private int getNumPlayerMaxScoreCard (int maxScore, Map<Player, Card> map){
                numPlayerMaxCard = 0;
                map.forEach((player,card) -> {
                    if(enumHandler.getScoreMap().get(card.getScore())== maxScore) {
                        numPlayerMaxCard +=1;
                    }
                });
                return numPlayerMaxCard;
            }

            //il metodo getMaxScorePlayer restituisce il giocatore nella mappa passata che ha score massimo passato
            private Player getMaxScorePlayer (int maxScore, Map<Player, Card> map) {
                for (Map.Entry<Player, Card> entry : map.entrySet()) {
                    if(enumHandler.getScoreMap().get(entry.getValue().getScore()) == maxScore) {
                        return entry.getKey();
                    }
                }
                return null;
            }

            //il metodo getMaxNumCard mi restituisce il num più alto tra le carte presenti nella mappa passata
            private int getMaxNumCard(Map<Player, Card> map) {
                int  maxNumCard=0;
                for (Map.Entry<Player, Card> entry : map.entrySet()) {
                    if ((enumHandler.getNumberMap().get(entry.getValue().getNum()))- maxNumCard > 0) {
                        maxNumCard = enumHandler.getNumberMap().get(entry.getValue().getNum());
                    }
                }
                return maxNumCard;
           }

            //il metodo getMaxNumPlayer restituisce giocatore nella mappa passata che ha num massimo passato
            private Player getMaxNumPlayer (int maxNum, Map<Player, Card> map) {
                for (Map.Entry<Player, Card> entry : map.entrySet()) {
                    if(enumHandler.getNumberMap().get(entry.getValue().getNum()) == maxNum) {
                        return entry.getKey();
                    }
                }
                return null;
            }


            //il metodo getWinner mi permette di ottenere il giocatore vincitore del turno
           @Override
           protected Player getWinner(){
                switch(getNumBriscole()) {
                    case 0:
                        return getBestNotBriscolaPlayer();
                    case 1:
                        return getBriscolaPlayer();
                    case 2:
                        return getBestBriscolaPlayer();
                    case 3:
                        return getBestCardPlayer(getTurnCards());
                    default:
                        System.out.println("ERROR");
                        return null;
                }
            }

           //il metodo reorderPlayersTurn restituisce una lista ordinata secondo
           // il nuovo ordine stabilito dal vincitore del turno
           @Override
           protected List<Player> reorderPlayersTurn() {
               List<Player> newOrder= new ArrayList<>();
               newOrder.add(getWinner());
               if (getWinner() == getPlayersTurn().get(0)) {
                   newOrder.add(getPlayersTurn().get(1));
                   newOrder.add(getPlayersTurn().get(2));
               }

               else if (getWinner() == getPlayersTurn().get(1)) {
                   newOrder.add(getPlayersTurn().get(2));
                   newOrder.add(getPlayersTurn().get(0));
               }

               else {
                   newOrder.add(getPlayersTurn().get(0));
                   newOrder.add(getPlayersTurn().get(1));
               }

               return newOrder;
           }


           //il metodo getGameWinner restituiusce l'oggetto player vincitore, ovvero con più punti a fine partita
           //restituisce invece null se la partita finisce in pareggio
           @Override
           protected Player getGameWinner(){
                if (getPlayersTurn().get(0).getPoints()> getPlayersTurn().get(1).getPoints() &&
                    getPlayersTurn().get(0).getPoints()> getPlayersTurn().get(2).getPoints())
                     return getPlayersTurn().get(0);
                else if (getPlayersTurn().get(1).getPoints()> getPlayersTurn().get(2).getPoints() &&
                        getPlayersTurn().get(1).getPoints()> getPlayersTurn().get(0).getPoints())
                    return getPlayersTurn().get(1);
                else if (getPlayersTurn().get(2).getPoints()> getPlayersTurn().get(1).getPoints() &&
                       getPlayersTurn().get(2).getPoints()> getPlayersTurn().get(0).getPoints())
                   return getPlayersTurn().get(2);
                return null;
            }

        }
