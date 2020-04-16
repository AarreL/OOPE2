package harjoitustyo.dokumentit;

/*
*Vitsi aliluokka jolla mallinnetaan uutista
*
*
*/

public class Vitsi extends Dokumentti {

    private String laji;

    //Aksessorit

    public String laji() {
        return laji;
    }
    public void laji(String uusiarvo) {
        if(uusiarvo!=null) {
            laji = uusiarvo;
        } else {
            throw new IllegalArgumentException();
        }
    }

    //Rakentaja

    public Vitsi(int uusitunniste, String laji, String teksti) {
        super(uusitunniste, teksti);
        laji(laji);
    }
}