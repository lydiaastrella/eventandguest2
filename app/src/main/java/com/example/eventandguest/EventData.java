package com.example.eventandguest;

import java.util.ArrayList;

class EventData {
    private static String[][] data = new String[][]{
            {"1", "Paskah", "12 April 2020", "https://api.time.com/wp-content/uploads/2019/04/gettyimages-617733080.jpg"},
            {"2", "Hari Buruh Internasional", "1 Mei 2020", "https://i.pinimg.com/736x/7c/7b/92/7c7b92b99e522cb8d327dbf35f71beb0.jpg"},
            {"3", "Hari Raya Waisak", "7 Mei 2020", "https://assets-a1.kompasiana.com/items/album/2016/05/23/waisak-1-5742b01dba9373080c4ab318.jpg"},
            {"4", "Kenaikan Isa Almasih", "21 Mei 2020", "https://pbs.twimg.com/media/DczHo6XXkAAKXls.png"}
    };

    static ArrayList<Event> getListData(){
        Event event = null;
        ArrayList<Event> list = new ArrayList<>();
        for (String[] aData : data) {
            event = new Event();
            event.setId(Integer.parseInt(aData[0]));
            event.setName(aData[1]);
            event.setDate(aData[2]);
            event.setImage(aData[3]);

            list.add(event);
        }
        return list;
    }
}
