package com.jit.AgriIn.model.response;

import java.util.List;

public class HisResponse {

    /**
     * type : temp
     * sntvs : [{"snpid":169,"times":[1589990614,1589990914,1589991214,1589991514,1589991815,1589992115,1589992415,1589992715,1589993015,1589993315,1589993615,1589993916,1589994216,1589994516,1589994816,1589995116,1589995416,1589995716,1589996017,1589996317,1589996617,1589996917,1589997217,1589997517,1589997817,1589998117,1589998418,1589998718,1589999018,1589999318,1589999618,1589999918,1590000218,1590000519,1590000819,1590001119,1590001419,1590001719,1590002019,1590002319,1590002620,1590002920,1590003220,1590003520,1590003820,1590004120,1590004420,1590004721,1590005021,1590005321,1590005621,1590005921,1590006221,1590006521,1590006821,1590007122,1590007422,1590007722,1590008022,1590008322,1590008622,1590008922,1590009223,1590009523,1590009823,1590010123,1590010423,1590010723,1590011023,1590011324,1590011624,1590011924,1590012224,1590012524,1590012824,1590013124,1590013424,1590013725,1590014025,1590014325,1590014625,1590014925,1590015225,1590015525,1590015826,1590016126,1590016426,1590016726,1590017026,1590017326,1590017626,1590017927,1590018227,1590018527,1590018827,1590019127,1590019427,1590019727,1590020027,1590020328,1590020628,1590020928,1590021228,1590021528,1590021828,1590022128,1590022429,1590022729,1590023029,1590023329,1590023629,1590023929,1590024229,1590024530,1590024830,1590025130,1590025430,1590025730,1590026030,1590026330,1590026631,1590026931,1590027231,1590027531,1590027831,1590028131,1590028431,1590028731],"values":[0,48.6,0,0,0,0,0,0,0,49.3,49.3,49.2,49.6,49.5,0,49.3,49.6,49.4,0,0,50,0,0,0,0,0,0,0,49.6,49.7,49.8,0,49.8,0,0,0,0,0,0,0,50.4,0,50.5,50.5,50.6,0,0,0,0,0,0,0,51,0,51.1,0,0,0,51.2,0,0,0,0,0,0,51.7,0,0,0,51.8,0,51.8,0,0,0,52,0,0,52.1,0,0,52.4,52.4,0,0,52.6,0,52.7,0,0,0,0,0,53.1,0,0,0,0,0,0,53.3,0,0,53.3,53.2,53.4,0,0,53.6,0,0,0,0,0,0,54.5,54.8,0,54.9,0,0,55.2,0,0,0,0,0,55.9]}]
     */

    private String type;
    private List<HisValue> sntvs;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<HisValue> getSntvs() {
        return sntvs;
    }

    public void setSntvs(List<HisValue> sntvs) {
        this.sntvs = sntvs;
    }
}