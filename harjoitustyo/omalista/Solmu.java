package harjoitustyo.omalista;

public class Solmu {

    private Object alkio;
    private Solmu seuraava;
    private Solmu edellinen;

    public Solmu(Object uusialkio) {
        seuraava = null;
        edellinen = null;
        alkio = uusialkio;
    }

    public Object lueArvo() {
        return alkio;
    }
    public void asetaArvo(Object uusiarvo) {
        alkio = uusiarvo;
    }
    public Solmu haeSeur() {
        return seuraava;
    }
    public Solmu haeEd() {
        return edellinen;
    }
    public void asetaSeur(Solmu uusiseuraava) {
        seuraava = uusiseuraava;
    }
    public void asetaEd(Solmu uusiedellinen) {
        edellinen = uusiedellinen;
    }
}