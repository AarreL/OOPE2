package harjoitustyo.omalista;

import java.util.LinkedList;

import harjoitustyo.apulaiset.Ooperoiva;

public class OmaLista<E> extends LinkedList<E> implements Ooperoiva<E> {

    Solmu pää;
    Solmu häntä;

    @SuppressWarnings({"unchecked"})
    public void lisää(E uusi) throws IllegalArgumentException {
        Solmu lisattava = new Solmu(uusi);
        if(pää.haeSeur() == null) {
            pää.asetaSeur(lisattava);
            häntä.asetaEd(lisattava);
        }
        else {
            int listaindeksi = 0;
            boolean paikkaEiLoydetty = true;
            while(paikkaEiLoydetty) {
                Comparable nykyinen = (Comparable)get(listaindeksi);
                Solmu apu = new Solmu(get(listaindeksi));
                if(nykyinen.compareTo(lisattava)>0) {
                    lisattava.asetaEd(apu.haeEd());
                    lisattava.asetaSeur(apu);
                    apu.asetaEd(lisattava);
                    paikkaEiLoydetty = false;
                }
                else {
                    if(apu.haeSeur() == häntä) {
                        apu.asetaSeur(lisattava);
                        lisattava.asetaSeur(häntä);
                        lisattava.asetaEd(apu);
                        paikkaEiLoydetty = false;
                    }
                    listaindeksi ++;
                }
            }
        }
    }

}