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
*@author Aarre Leinonen (aarre.leinonen@tuni.fi)
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
     * @param tunniste haettava tunniste
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
     * @throws FileNotFoundException jos annettu tiedosto on virheellinen
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

    /**
     * Käyttöliittymästä käynnistettävä metodi 
     * jolla lisätään listalle käyttäjän antama 
     * uusi alkio, hyödyntäen lisää-metodeja kokoelma, 
     * ja omasta luokasta.
     *  
     * @param komennonosat käyttäjän antama komento eriteltynä taulukkoon
     * @param korpus luotu kokoelma jolle alkio lisätään
     * @param tiednimi tiedoston nimi
     * @return muokattu kokoelma
     */
    public Kokoelma lisääUusi(String[] komennonosat, Kokoelma korpus, String tiednimi) {
         //Jos osia olisi ollut enemmän kuin 2, mennään tulostamaan error elsessä
         if(komennonosat.length == 2) {
             String lisättävä = komennonosat[1];
             String[] laatutarkistus = tiednimi.split("_");
             String[] lisäystiedot = lisättävä.split("///");
             try {
                 int tunniste = Integer.parseInt(lisäystiedot[0]);
                 String teksti = lisäystiedot[2];
                 /*Käsitellään vitsien lisäys ja poikkeustapaus jossa olisi yritetty lisätä
                 *uutista vitsien joukkoon siten että yritetään luoda annetuista
                 *tiedoista uutiselle sopiva LocalDate, mikäli tämä onnistuu
                 *tulostetaan error. Muussa tapauksessa napataan poikkeus
                 *ja todetaan että koska uutisen luonti ei onnistu pitää kyseessä
                 *olla vitsi, joka sitten catchissa lisätään listalle.
                 */
                 if(laatutarkistus[0].equals("jokes")) {
                     try {
                         LocalDate päiväm = LocalDate.parse(lisäystiedot[1], 
                         DateTimeFormatter.ofPattern("d.M.yyyy"));
                         System.out.println("Error!");
                     } catch(Exception e) {
                         String laji = lisäystiedot[1];
                         Vitsi uusi = new Vitsi(tunniste, laji, teksti);
                         korpus.lisää(uusi);
                     }
                 }
                 else {
                     //Lisätään uutinen listalle. Vitsin lisäämisen
                     //yrittäminen heittää poikkeuksen
                     try {
                         LocalDate päiväm = LocalDate.parse(lisäystiedot[1], 
                         DateTimeFormatter.ofPattern("d.M.yyyy"));
                         Uutinen uusi = new Uutinen(tunniste, päiväm, teksti);
                         korpus.lisää(uusi);
                     } catch(Exception e) {
                         System.out.println("Error!");
                     }
                 }
             } catch(Exception e) {
                 System.out.println("Error!");
             }
         }
         else {
             System.out.println("Error!");
         }
        return korpus;
    }

    /**
     * Käyttöliittymästä käynnistettävä metodi jolla haetaan kokoelmasta 
     * halutut dokumentit tietyillä hakusanoilla.
     * 
     * @param komennonosat käyttäjän antama komento
     * @param korpus käsiteltävä kokoelma
     */
    public void haeAlkio(String[] komennonosat, Kokoelma korpus) {
        int hakusanalaskuri = 1;
        int hakusanamäärä = komennonosat.length - 1;

        //Luodaan annetuista hakusanoista taulukko joka voidaan
        //välittää eteenpäin metodeille.
        LinkedList<String> hakusanat = new LinkedList<String>();
        while (hakusanalaskuri<=hakusanamäärä) {
            hakusanat.add(komennonosat[hakusanalaskuri]);
            hakusanalaskuri++;
        }
        int dokkarilaskuri = 0;

        //Haetaan hakusanoihin täsmääviä dokumentteja
        while(dokkarilaskuri < korpus.dokumentit().size()) {
            if(korpus.dokumentit().get(dokkarilaskuri).sanatTäsmäävät(hakusanat)) {
                System.out.println(korpus.dokumentit().get(dokkarilaskuri).tunniste());
            }
            dokkarilaskuri++;
        }
    }

    /**
     * Käyttöliittymästä käynnistettävä metodi jolla tulostetaan
     * joko kaikki tai vaan valittu dokumentti kokoelmasta.
     * 
     * @param komennonosat Käyttäjän antama komento 
     * @param korpus Käsiteltävä kokoelma
     */
    public void tulosta(String[] komennonosat, Kokoelma korpus) {
        //Tulostetaan tietty dokumentti
        if(komennonosat.length == 2) {
            int tunniste = Integer.parseInt(komennonosat[1]);
            String tulostettava = korpus.hae(tunniste).toString();
            System.out.println(tulostettava);

        }
        //Tulostetaan kaikki dokumentit
        else if(komennonosat.length == 1) {
            int apulaskuri = 0;
            while(apulaskuri<korpus.dokumentit().size()) {
                String tulostettava = korpus.dokumentit().get(apulaskuri).toString();
                System.out.println(tulostettava);
                apulaskuri++;
            }
        }
        else {
            System.out.println("Error!");
        }
    }
}