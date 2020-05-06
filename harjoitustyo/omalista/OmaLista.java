package harjoitustyo.omalista;

import java.util.LinkedList;

import harjoitustyo.apulaiset.Ooperoiva;

/**
*OmaLista-luokka joka on peritty javan LinkedLististä 
*<p>
*Harjoitustyö, OOPE2, kevät 2020
*<p>
*@author Aarre Leinonen (leinonen.aarre@tuni.fi)
*/

public class OmaLista<E> extends LinkedList<E> implements Ooperoiva<E> {


    //Toteutetaan lisää-metodi
    @SuppressWarnings({"unchecked"})
    public void lisää(E uusi) throws IllegalArgumentException {
        if(uusi != null && uusi instanceof Comparable) {
            int indlaskuri = 0;
            int koko = size();
            Comparable viimeinen;
            if(koko == 0) {
                add(0, uusi);
                indlaskuri = 2;
            }
            while(indlaskuri <= koko) {
                viimeinen = (Comparable)get(koko-1);
                Comparable nykyinen = (Comparable)get(indlaskuri);
                if(viimeinen.compareTo(uusi)<=0) {
                    add(koko, uusi);
                    indlaskuri = koko+1;
                } else if(nykyinen.compareTo(uusi)>0) {
                    add(indlaskuri, uusi);
                    indlaskuri = koko +1;
                } else {
                    indlaskuri++;
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}