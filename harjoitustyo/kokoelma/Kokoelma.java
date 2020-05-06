package harjoitustyo.kokoelma;

import harjoitustyo.omalista.*;
import harjoitustyo.dokumentit.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import harjoitustyo.apulaiset.*;

/**
*Kokoelma-luokka, jolla hallitaan tekstikokoelmasta luotua osaoliota
*<p>
*Harjoitustyö, OOPE2, kevät 2020
*<p>
*@author Aarre Leinonen (leinonen.aarre@tuni.fi)
*/

public class Kokoelma implements Kokoava<Dokumentti> {

    private OmaLista<Dokumentti> dokumentit;
    String dokutiedosto;

    //Rakentaja
    public Kokoelma(String uusitiedosto) {
        dokumentit = new OmaLista<Dokumentti>();
        dokutiedosto = uusitiedosto;
    }

    //Aksessori
    public OmaLista<Dokumentti> dokumentit() {
        return dokumentit;

    }

    public void lisää(Dokumentti uusi) throws IllegalArgumentException {
        int laskuri = 0;
        while(laskuri<dokumentit.size()) {
            if(uusi.equals(dokumentit.get(laskuri))) {
                throw new IllegalArgumentException();
            }
            laskuri++;
        }
        dokumentit.lisää(uusi);
    }

    public Dokumentti hae(int tunniste) {
        int laskuri = 0;
        while(laskuri<dokumentit.size()) {
            Dokumentti verrattava = dokumentit.get(laskuri);
            int verrattavaid = verrattava.tunniste();
            if(verrattavaid == tunniste) {
                return verrattava;
            }
            laskuri++;
        }
        return null;
    }

    public void lataaTiedosto() throws FileNotFoundException {
        File tiedosto = new File("\"" + dokutiedosto + "\"");
        Scanner lataaja = new Scanner(tiedosto);

        
    }
}