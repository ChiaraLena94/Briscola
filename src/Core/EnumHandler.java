package Core;

import java.util.HashMap;
import java.util.Map;

public class EnumHandler {
    private Map<Score,Integer> scoreMap;
    private Map<Number,Integer> numberMap;

    public EnumHandler() {
        initializeScoreMap();
        initializeNumberMap();
    }

    private void initializeNumberMap() {
        numberMap= new HashMap<>();
        numberMap.put(Number.ACE, 1);
        numberMap.put(Number.TWO, 2);
        numberMap.put(Number.THREE, 3);
        numberMap.put(Number.FOUR, 4);
        numberMap.put(Number.FIVE, 5);
        numberMap.put(Number.SIX, 6);
        numberMap.put(Number.SEVEN, 7);
        numberMap.put(Number.SOLDIER, 8);
        numberMap.put(Number.KNIGHT, 9);
        numberMap.put(Number.KING, 10);
    }

    private void initializeScoreMap(){
        scoreMap = new HashMap<>();
        scoreMap.put(Score.ZERO, 0);
        scoreMap.put(Score.TWO, 2);
        scoreMap.put(Score.THREE, 3);
        scoreMap.put(Score.FOUR, 4);
        scoreMap.put(Score.TEN, 10);
        scoreMap.put(Score.ELEVEN, 11);
    }

    public Map<Score, Integer> getScoreMap() {
        return scoreMap;
    }

    public Map<Number, Integer> getNumberMap() {
        return numberMap;
    }



}
