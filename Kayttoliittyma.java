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
*@author Aarre Leinonen (aarre.leinonen@tuni.fi)
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
                        korpus = korpus.lisääUusi(komennonosat, korpus, tiednimi);

                    }
                    
                    //PRINT-KOMENTO
                    else if (komento.equals("print")) {
                        korpus.tulosta(komennonosat, korpus);
                        
                    } 
                    
                    //FIND-komento
                    else if (komento.equals("find")) {
                        korpus.haeAlkio(komennonosat, korpus);
                        
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