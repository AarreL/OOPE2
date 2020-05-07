import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;

import harjoitustyo.dokumentit.*;
import harjoitustyo.kokoelma.Kokoelma;

/**
*Käyttöliittymä-luokka jolla pidetään huoli käyttäjän kanssa
*vuorovaikuttamisesta ja ohjataan ohjelman toimintaa.
*<p>
*Harjoitustyö, OOPE2, kevät 2020
*<p>
*@author Aarre Leinonen (leinonen.aarre@tuni.fi)
*/

public class Kayttoliittyma {

    /**
     * Luokan ainoa metodi jossa pääsilmukka, vuorovaikutus käyttäjän kanssa
     * ja ohjelman toiminnallisuus
     * 
     * 
     * @param tiednimi tekstikokoelmatiedoston nimi
     * @param sulkusanat sulkusanatiedoston nimi
     * @throws FileNotFoundException
     */

    public void käynnistä(String tiednimi, String sulkusanat) {
        //Catchissa otetaan kiinni FileNotFoundException
        try {
            Kokoelma korpus = new Kokoelma(tiednimi);
            LinkedList<String> sulkusanalista = new LinkedList<String>();
            korpus.lataaTiedosto();
            
            File sulut = new File(sulkusanat);

            Scanner sulkulukija = new Scanner(sulut);
            Scanner syote = new Scanner(System.in);

            boolean jatketaan = true;
            boolean echo = false;

            while (jatketaan) {
                //Tarkistus mahdollisille virheille jotka ovat päässeet läpi mutta joiden ei tarvi
                //pysäyttää pääsilmukkaa. Catchissa otetaan kiinni Exception
                try {
                    //Aloitetaan komentojen kysely
                    System.out.println("Please, enter a command:");
                    String täysikomento = syote.nextLine(); 
                    String[] komennonosat = täysikomento.split(" ");
                    String komento = komennonosat[0];

                    //Tutkitaan onko echo voimassa ja toimitaan sen mukaan
                    if(echo) {
                        String kaiku = komento;
                        int apulaskuri = 1;
                        while (apulaskuri<komennonosat.length) {
                            kaiku = kaiku + " " + komennonosat[apulaskuri];
                            apulaskuri++;
                        }
                        System.out.println(kaiku);
                    }

                    //Tutkitaan oliko kyseessä lopetuskomento
                    if (komento.equals("quit") && komennonosat.length == 1) {
                        jatketaan = false;
                    }
                    
                    //ADD-komento
                    else if (komento.equals("add")) {
                        //Lisätessä halutaan jakaa komento vain kahteen osaan
                        komennonosat = täysikomento.split(" ", 2);
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
                    }
                    
                    //PRINT-KOMENTO
                    else if (komento.equals("print")) {
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
                    
                    //FIND-komento
                    else if (komento.equals("find")) {
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
                    
                    //POLISH-komento
                    else if (komento.equals("polish") && komennonosat.length == 2) {
                        //Otetaan käyttäjän antamat merkit ja sulkusanat
                        //sekä aloitetaan siivoaminen
                        String merkit = komennonosat[1];
                        while(sulkulukija.hasNext()) {
                            sulkusanalista.add(sulkulukija.nextLine());
                        }
                        korpus.siivoaTekstit(sulkusanalista, merkit);
                    } 
                    
                    //REMOVE-komento
                    else if (komento.equals("remove") && komennonosat.length == 2) {
                        //poistetaan käyttäjän määräämä dokumentti jos sellainen on olemassa
                        int tunniste = Integer.parseInt(komennonosat[1]);
                        Dokumentti poistettava = korpus.hae(tunniste);
                        if(poistettava != null) {
                            korpus.dokumentit().remove(poistettava);
                        }
                        else {
                            System.out.println("Error!");
                        }
                    }
                    
                    //RESET-komento
                    //Ladataan dokumenttitiedosto uudelleen
                    else if(komento.equals("reset") && komennonosat.length == 1) {
                        korpus.lataaTiedosto();
                    }
                    
                    //ECHO-komento
                    //muutetaan echo-muuttujan arvo ja tulsotetaan echo kerran
                    else if(komento.equals("echo") && komennonosat.length == 1) {
                        System.out.println("echo");
                        echo = !echo;
                    }
                    else {
                        System.out.println("Error!");
                    }
                } catch(Exception e) {
                    System.out.println("Error!");
                }
            }

        } catch(FileNotFoundException e) {
            System.out.println("Missing file!");
            e.printStackTrace();
        } 
    } 
}