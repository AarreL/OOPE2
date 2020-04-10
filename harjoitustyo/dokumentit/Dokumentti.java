package harjoitustyo.dokumentit;

/*
*Abstrakti dokumentti-yliluokka vitsi ja uutinen luokille
*
*/


public abstract class Dokumentti implements Comparable<Dokumentti> {

    //Dokumenttien yhteiset tiedot
    private int tunniste;
    private String teksti;


    //Aksessorit


    //Rakentajat


    @Override
    public String toString() {
        String palautettava;
        palautettava = tunniste + "///" + teksti;
        return palautettava;
    }

    @Override
    public boolean equals(Object verrattava) {

        return false;

    }

    public int compareTo(Dokumentti verrattava) {

        return 1;
    }

}