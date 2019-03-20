package Core;

public class Card {
    private int id;
    private Seed seed;
    private Number num;
    private Score score;


    public Card(int id, Seed seed, Number number, Score score) {
        this.id = id;
        this.seed = seed;
        this.num = number;
        this.score = score;
    }

    public int id(){
        return id;
    }

    public Number getNum() {
        return num;
    }

    public Score getScore() {
        return score;
    }

    public Seed getSeed() {
        return seed;
    }

}
