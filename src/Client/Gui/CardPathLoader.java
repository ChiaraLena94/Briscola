package Client.Gui;

import java.util.HashMap;
import java.util.Map;

public class CardPathLoader {
    private Map<Integer, String> cardMap;

    public CardPathLoader(){
        initializeCardMap();
    }

    private void initializeCardMap() {
        cardMap=new HashMap<>();
        cardMap.put(1, "Spade_1.jpg");
        cardMap.put(2,"Spade_2.jpg");
        cardMap.put(3,"Spade_3.jpg");
        cardMap.put(4,"Spade_4.jpg");
        cardMap.put(5,"Spade_5.jpg");
        cardMap.put(6,"Spade_6.jpg");
        cardMap.put(7,"Spade_7.jpg");
        cardMap.put(8,"Spade_8.jpg");
        cardMap.put(9,"Spade_9.jpg");
        cardMap.put(10,"Spade_10.jpg");
        cardMap.put(11,"Coppe_1.jpg");
        cardMap.put(12,"Coppe_2.jpg");
        cardMap.put(13,"Coppe_3.jpg");
        cardMap.put(14,"Coppe_4.jpg");
        cardMap.put(15,"Coppe_5.jpg");
        cardMap.put(16,"Coppe_6.jpg");
        cardMap.put(17,"Coppe_7.jpg");
        cardMap.put(18,"Coppe_8.jpg");
        cardMap.put(19,"Coppe_9.jpg");
        cardMap.put(20,"Coppe_10.jpg");
        cardMap.put(21,"Denari_1.jpg");
        cardMap.put(22,"Denari_2.jpg");
        cardMap.put(23,"Denari_3.jpg");
        cardMap.put(24,"Denari_4.jpg");
        cardMap.put(25,"Denari_5.jpg");
        cardMap.put(26,"Denari_6.jpg");
        cardMap.put(27,"Denari_7.jpg");
        cardMap.put(28,"Denari_8.jpg");
        cardMap.put(29,"Denari_9.jpg");
        cardMap.put(30,"Denari_10.jpg");
        cardMap.put(31,"Bastoni_1.jpg");
        cardMap.put(32,"Bastoni_2.jpg");
        cardMap.put(33,"Bastoni_3.jpg");
        cardMap.put(34,"Bastoni_4.jpg");
        cardMap.put(35,"Bastoni_5.jpg");
        cardMap.put(36,"Bastoni_6.jpg");
        cardMap.put(37,"Bastoni_7.jpg");
        cardMap.put(38,"Bastoni_8.jpg");
        cardMap.put(39,"Bastoni_9.jpg");
        cardMap.put(40,"Bastoni_10.jpg");
    }

    public String getPath(int i){
        for (Map.Entry<Integer, String> entry: cardMap.entrySet()) {
            if(entry.getKey() == i)
                return entry.getValue();
        }
        return null;
    }
}
