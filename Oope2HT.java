
/**
*Oope2HT-luokka jolla käynnistetään ohjelma
*<p>
*Harjoitustyö, OOPE2, kevät 2020
*<p>
*@author Aarre Leinonen (aarre.leinonen@tuni.fi)
*/

public class Oope2HT {

    /**
     * Ensin tervehditään käyttäjään, sitten tarkistetaan
     * komentoriviparametrien määrä, käynnistetään
     * main metodi josta käynnistetään ohjelma
     * ja lopuksi kerrotaan ohjelman päättymisestä
     * 
     * @param args komentoriviparametrit
     */
    public static void main (String[] args) {
        System.out.println("Welcome to L.O.T.");
        if(args.length == 2) {
            String tiednimi = args[0];
            String stopit = args[1];

            Kayttoliittyma käyttis = new Kayttoliittyma();

            käyttis.käynnistä(tiednimi, stopit);
        }
        else {
            System.out.println("Wrong number of command-line arguments!");
        }
        System.out.println("Program terminated.");
    }
}