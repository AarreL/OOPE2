import java.io.File;
import java.io.FileNotFoundException;
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

    public void käynnistä(String tiednimi, String sulkusanat) throws FileNotFoundException {
        Kokoelma korpus = new Kokoelma(tiednimi);

        
        File sulut = new File("\"" + sulkusanat + "\"");

        Scanner sulkulukija = new Scanner(sulut);
        Scanner syote = new Scanner(System.in);

        boolean jatketaan = true;
        boolean echo = false;

        while (jatketaan) {
            System.out.println("Please, enter a command:");
            String[] komennonosat = syote.nextLine().split(" ");
            String komento = komennonosat[0];

            if (komento.equals("quit")) {
                jatketaan = false;
            }
            
            else if (komento.equals("add")) {

            }
            
            else if (komento.equals("print")) {
                int tunniste = Integer.parseInt(komennonosat[1]);
                String tulostettava = korpus.hae(tunniste).toString();
                System.out.println(tulostettava);
            } 
            
            else if (komento.equals("find")) {
                int hakusanalaskuri = 1;
                int hakusanamäärä = komennonosat.length - 1;
                LinkedList<String> hakusanat = new LinkedList<String>();
                while (hakusanalaskuri<=hakusanamäärä) {
                    hakusanat.add(komennonosat[hakusanalaskuri]);
                }
                int dokkarilaskuri = 0;

                while(dokkarilaskuri < korpus.dokumentit().size()) {

                }

            }
            
            else if (komento.equals("polish")) {

            } 
            
            else if (komento.equals("remove")) {
                int tunniste = Integer.parseInt(komennonosat[1]);
                Dokumentti poistettava = korpus.hae(tunniste);
                korpus.dokumentit().remove(poistettava);
            }
            
            else if(komento.equals("reset")) {

            }
            
            else if(komento.equals("echo")) {
                System.out.println("echo");
                echo = !echo;
            }
        }
    }

    
}