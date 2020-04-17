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

    public int tunniste() {
        return tunniste;
    }
    public void tunniste(int uusiarvo) {
        tunniste = uusiarvo;
    }

    public String teksti() {
        return teksti;
    }
    public void teksti(String uusiarvo) {
        if(teksti!=null && teksti.length()<0) {
            teksti = uusiarvo;
        } else {
            throw new IllegalArgumentException();
        }
    }

    //Rakentajat

    public Dokumentti(int tunnistearvo, String tekstiarvo) {
        tunniste(tunnistearvo);
        teksti(tekstiarvo);
    }

    @Override
    public String toString() {
        String palautettava;
        palautettava = tunniste + "///" + teksti;
        return palautettava;
    }


    public boolean equals(Dokumentti verrattava) {
        if(tunniste == verrattava.tunniste) {
            return true;
        } else{
            return false;
        }

    }

    @Override
    public int compareTo(Dokumentti verrattava) {
        if(tunniste < verrattava.tunniste) {
            return -1;
        }else if(tunniste == verrattava.tunniste) {
            return 0;
        } else {
            return 1;
        }
    }

}