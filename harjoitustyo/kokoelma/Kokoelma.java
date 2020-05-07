package harjoitustyo.kokoelma;

import harjoitustyo.omalista.*;
import harjoitustyo.dokumentit.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
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

    /**
     * Metodi jolla lisätään uusi alkio omalle listalle
     * oman listan lisää-metodia hyödyntäen.
     * 
     * @param uusi lisättävä alkio
     * 
     * @throws IllegalArgumentException
     */

    public void lisää(Dokumentti uusi) throws IllegalArgumentException {
        int laskuri = 0;
        while(laskuri<dokumentit.size()) {
            //Jos lisättävä alkio on jo listalla
            if(uusi.equals(dokumentit.get(laskuri))) {
                throw new IllegalArgumentException();
            }
            laskuri++;
        }
        //Jos ei ollut vielä listalla, lisätään se sinne
        dokumentit.lisää(uusi);
    }

    /**
     * Mteodi jolla voidaan hakea omalta listalta tiettyä dokumenttia
     * dokumentin tunnisteen perusteella. 
     * Käytetään mm. tulostamisessa ja poistamisessa
     * 
     * @param tunniste 
     * @return Dokumentti, jos hakua vastaava löytyi, null jos ei löytynyt
     * 
     */

    public Dokumentti hae(int tunniste) {
        int laskuri = 0;
        while(laskuri<dokumentit.size()) {
            //Haetaan yksi kerrallaan dokumenttien tunnisteet ja verrataan niitä haluttuun
            Dokumentti verrattava = dokumentit.get(laskuri);
            int verrattavaid = verrattava.tunniste();
            if(verrattavaid == tunniste) {
                return verrattava;
            }
            laskuri++;
        }
        return null;
    }

    /**
     * Metodi joka käynnistyy kun ohjelma käynnistetään tai 
     * komennoksi annetaan reset. Metodilla ladataan tiedostossa
     * olevat vitsit tai uutiset omalle listalle
     * 
     * @throws FileNotFoundException
     */

    public void lataaTiedosto() throws FileNotFoundException {

        //alustetaan muuttujia, ja luodaan Scanner jolla tiedosto käydään läpi
        dokumentit = new OmaLista<Dokumentti>();
        Scanner lataaja = null;
        File tiedosto = new File(dokutiedosto);
        lataaja = new Scanner(tiedosto);

        String[] tyyppi = dokutiedosto.split("_");

        //Otetaan huomioon halutaanko tiedostosta tuoda vitsejä vai uutisia jotta
        //olioiden luonti onnistuu oikein
        if(tyyppi[0].equals("jokes")) {
            //Lisätään vitsit
            while(lataaja.hasNextLine()) {
                String lisättävä = lataaja.nextLine();
                String[] lisättävätosat = lisättävä.split("///");
                int tunniste = Integer.parseInt(lisättävätosat[0]);
                String laji = lisättävätosat[1];
                String vitsi = lisättävätosat[2];

                Vitsi uusivitsi = new Vitsi(tunniste, laji, vitsi);
                lisää(uusivitsi);
            }
        }
        else {
            //Lisätään uutiset
            while(lataaja.hasNextLine()) {
                String lisättävä = lataaja.nextLine();
                String[] lisättävätosat = lisättävä.split("///");
                int tunniste = Integer.parseInt(lisättävätosat[0]);
                LocalDate päiväm = LocalDate.parse(lisättävätosat[1], DateTimeFormatter.ofPattern("d.M.yyyy"));
                String uutinen = lisättävätosat[2];

                Uutinen uusiuutinen = new Uutinen(tunniste, päiväm, uutinen);
                lisää(uusiuutinen);
            }
        }
    }


    /**
     * Metodi jota käytetään Dokumentti-luokan oman siivousmetodin kutsuun
     * jokaiselle luokan senhetkiselle edustajalle
     * 
     * @param sulkusanalista lista sanoista jotka halutaan poistaa
     * @param merkit jotka halutaan poistaa
     * @throws IllegalArgumentException mikäli saa sellaisen kutsumaltaan metodilta
     */
    public void siivoaTekstit(LinkedList<String> sulkusanalista, String merkit) throws IllegalArgumentException {
        int laskuri = 0;
        while(laskuri<dokumentit.size()) {
            dokumentit.get(laskuri).siivoa(sulkusanalista, merkit);
            laskuri++;
        }
        
    }
}